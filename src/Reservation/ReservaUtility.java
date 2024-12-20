package Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

public class ReservaUtility {
    private TreeMap<Integer, Reserva> reservas = new TreeMap<>();
    private static int maxIdReservaFromFile = 0;

    public ReservaUtility(){
        // Se existir uma sessão anterior, carregar do ficheiro de output
        if (Files.exists(Paths.get("output-files/reservas.txt"))) {
            maxIdReservaFromFile = loadFromFile("output-files/reservas.txt");
        }
        else {
            // Caso contrário, carregar do ficheiro de input e iniciar sessão teste
            maxIdReservaFromFile = loadFromFile("input-files/persistence.txt");
        }
    }
    public void updateStateFromFile() {
        // Se existir uma sessão anterior, carregar do ficheiro de output
        if (Files.exists(Paths.get("output-files/reservas.txt"))) {
            loadFromFile("output-files/reservas.txt");
        }
    }
    public int loadFromFile(String filePath) {
        // load retorna maior idReserva presente no ficheiro para ser usado como idReservaCounter e evitar ids repetidos
        // Format: idReserva|idPraia|idSombrinha|hora|data
        reservas.clear();
        ArrayList<Integer> ids_reservas_ficheiro = new ArrayList<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //int idReserva = 1;
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split("\\|");
                int idReservaFromFile = Integer.parseInt(parts[0]);
                if (ids_reservas_ficheiro.contains(idReservaFromFile)){
                    throw new IllegalArgumentException("O ficheiro de reservas contém ids repetidos.");
                }
                ids_reservas_ficheiro.add(idReservaFromFile);
                String idPraia = parts[1];
                int idSombrinha = Integer.parseInt(parts[2]);
                //int maxPessoas = Integer.parseInt(parts[2]);
                LocalTime time = LocalTime.parse(parts[3], timeFormatter);
                LocalDate date = LocalDate.parse(parts[4], dateFormatter);
                LocalDateTime hora = LocalDateTime.of(date, time);
                Sombrinha sombrinha = new Sombrinha(idPraia, idSombrinha);
                Reserva reserva = new Reserva(idReservaFromFile, hora,  sombrinha);
                reservas.put(idReservaFromFile, reserva);
                // inserirReserva(hora, idReservaFromFile, idPraia, idSombrinha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Save current state to output-files/reservas.txt e output-files/reservas-bk.txt
        backup();
        // Return max idReserva do ficheiro
        return ids_reservas_ficheiro.isEmpty() ? 1 : Collections.max(ids_reservas_ficheiro);
    }
    public void saveToFile(String filePath) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm|dd-MM-yyyy");
        try {
            List<String> lines = reservas.values().stream()
                    .map(reserva -> {
                        Sombrinha sombrinha = reserva.getSombrinha();
                        LocalDateTime hora = reserva.getHora();
                        return reserva.getIdReserva() + "|" +
                                sombrinha.getIdPraia() + "|" +
                                sombrinha.getIdSombrinha() + "|" +
                                hora.format(formatter);
                    })
                    .collect(Collectors.toList());
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void backup() {
        // Save to files
        saveToFile("output-files/reservas.txt");
        saveToFile("output-files/reservas-bk.txt");
    }
    private int inserirReserva(LocalDateTime hora, int idReserva ,String idPraia , int idSombrinha) {
        // Caso: do ficheiro de input ou de output
        Sombrinha sombrinha = new Sombrinha(idPraia, idSombrinha);
        if (verificarDisponibilidade(hora, idPraia, idSombrinha)){
            Reserva reserva = new Reserva(
                    idReserva,
                    hora,
                    sombrinha
            );
            reservas.put(reserva.getIdReserva(), reserva);
            // Save to files
            backup();
            updateStateFromFile();
            return idReserva;
        }
        return -1;
    }
    public int inserirNovaReserva(LocalDateTime hora, String idPraia, int idSombrinha) {
        // Caso: de uma nova reserva usa o idReservaCounter
        return inserirReserva(hora, maxIdReservaFromFile++, idPraia, idSombrinha);
    }
    public boolean verificarDisponibilidade(LocalDateTime hora, String idPraia, int idSombrinha) {
        Sombrinha sombrinha = new Sombrinha(idPraia, idSombrinha);
        if (reservas.isEmpty()) {
            return true;
        }
        for (Integer i : reservas.keySet()) {
            Reserva reserva = reservas.get(i);
            if (reserva.getHora().equals(hora) && reserva.getSombrinha().equals(sombrinha)) {
                return false;
            }
        }
        return true;
    }
    public int removeReserva(LocalDateTime hora , String idPraia, int idSombrinha) {
        for (Map.Entry<Integer, Reserva> entry : reservas.entrySet()) {
            if (entry.getValue().getHora().equals(hora) &&
                entry.getValue().getSombrinha().equals(new Sombrinha(idPraia, idSombrinha)))
            {
                reservas.remove(entry.getKey());
                // Save to files
                backup();
                updateStateFromFile();
                return entry.getKey(); // Devolve idReserva cancelada
            }
        }
        return -1; // Reserva não encontrada
    }
    public int removeReserva(int idReserva){
        if (reservas.containsKey(idReserva)) {
            reservas.remove(idReserva);
            // Save to files
            backup();
            updateStateFromFile();
            return idReserva; // Devolve idReserva cancelada
        }
        return -1; // Reserva não encontrada
    }
    public void listarReservas() {
        reservas.forEach((key, value) -> System.out.println(value));
    }
    public ArrayList<String> listaSombrinhasDisponiveis(LocalDateTime hora , String idPraia) {
        ArrayList<String> sombrinhasDisponiveis = new ArrayList<>();

        final int[] idSombrinhas_PraiaA = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        final int[] idSombrinhas_PraiaB = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 ,15 };
        final int[] idSombrinhas_PraiaC = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        switch (idPraia) {
            case "A" -> {
                for (int j : idSombrinhas_PraiaA) {
                    if (verificarDisponibilidade(hora, idPraia, j)) {
                        sombrinhasDisponiveis.add("A" + j);
                    }
                }
            }
            case "B" -> {
                for (int j : idSombrinhas_PraiaB) {
                    if (verificarDisponibilidade(hora, idPraia, j)) {
                        sombrinhasDisponiveis.add("B" + j);
                    }
                }
            }
            case "C" -> {
                for (int j : idSombrinhas_PraiaC) {
                    if (verificarDisponibilidade(hora, idPraia, j)) {
                        sombrinhasDisponiveis.add("C" + j);
                    }
                }
            }
        }
        return sombrinhasDisponiveis;
    }
    public String listaSombrinhasDisponiveis2(LocalDateTime hora , String idPraia) {
        String sombrinhasDisponiveis = "";
        final int[] idSombrinhas_PraiaA = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        final int[] idSombrinhas_PraiaB = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 ,15 };
        final int[] idSombrinhas_PraiaC = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        switch (idPraia) {
            case "A" -> {
                for (int j : idSombrinhas_PraiaA) {
                    if (verificarDisponibilidade(hora, idPraia, j)) {
                        //sombrinhasDisponiveis.add("A" + j);
                        sombrinhasDisponiveis += "A" + j + ":"; //.append("A").append(j).append(":");
                    }
                }
            }
            case "B" -> {
                for (int j : idSombrinhas_PraiaB) {
                    if (verificarDisponibilidade(hora, idPraia, j)) {
                        //sombrinhasDisponiveis.toString().add("B" + j);
                        sombrinhasDisponiveis += "B" + j + ":";
                    }
                }
            }
            case "C" -> {
                for (int j : idSombrinhas_PraiaC) {
                    if (verificarDisponibilidade(hora, idPraia, j)) {
                        //sombrinhasDisponiveis.toString().add("C" + j);
                        sombrinhasDisponiveis += "C" + j + ":";
                    }
                }
            }
        }
        assert sombrinhasDisponiveis != null;
        return sombrinhasDisponiveis.isEmpty() ? "Não existem sombrinhas disponíveis para a data e hora indicadas." : sombrinhasDisponiveis;
    }

    public String listaSombrinhasDisponiveis3(LocalDateTime hora , String idPraia) {
        StringBuilder sombrinhasDisponiveis = new StringBuilder();
        final int[] idSombrinhas_PraiaA = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        final int[] idSombrinhas_PraiaB = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 ,15 };
        final int[] idSombrinhas_PraiaC = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // Table header
        String headerFormat = "%-10s | %-12s%n";
        sombrinhasDisponiveis.append(String.format(headerFormat, "Praia", "Sombrinha"));
        sombrinhasDisponiveis.append(String.format(headerFormat, "----------", "------------"));

        // Table rows
        String rowFormat = "%-10s | %-12s%n";

        switch (idPraia) {
            case "A" -> {
                for (int j : idSombrinhas_PraiaA) {
                    if (verificarDisponibilidade(hora, idPraia, j)) {
                        sombrinhasDisponiveis.append(String.format(rowFormat, "A", j));
                    }
                }
            }
            case "B" -> {
                for (int j : idSombrinhas_PraiaB) {
                    if (verificarDisponibilidade(hora, idPraia, j)) {
                        sombrinhasDisponiveis.append(String.format(rowFormat, "B", j));
                    }
                }
            }
            case "C" -> {
                for (int j : idSombrinhas_PraiaC) {
                    if (verificarDisponibilidade(hora, idPraia, j)) {
                        sombrinhasDisponiveis.append(String.format(rowFormat, "C", j));
                    }
                }
            }
        }

        return sombrinhasDisponiveis.toString();
    }


    public String toStringTable() {
        // Devolve String com uma tabela ordenada por datas de marcação
        TreeMap<LocalDateTime, Reserva> newMap = new TreeMap<>();

        for (Map.Entry<Integer, Reserva> entry : reservas.entrySet()) {
            Reserva reserva = entry.getValue();
            newMap.put(reserva.getHora(), reserva);
        }

        StringBuilder sb = new StringBuilder();
        // Table header
        String headerFormat = "%-10s | %-12s | %-11s | %-11s | %-22s%n";
        sb.append(String.format(headerFormat, "idReserva", "idPraia", "idSombrinha", "maxPessoas", "marcacao"));
        sb.append(String.format(headerFormat, "----------", "------------", "-----------", "-----------", "----------------------"));

        // Table rows
        String rowFormat = "%-10d | %-12s | %-11d | %-11d | %-22s%n";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy ");
        for (Map.Entry<LocalDateTime, Reserva> entry : newMap.entrySet()) {
            Reserva reserva = entry.getValue();
            sb.append(String.format(rowFormat,
                    reserva.getIdReserva(),
                    reserva.getSombrinha().getIdPraia(),
                    reserva.getSombrinha().getIdSombrinha(),
                    reserva.getSombrinha().getMaxPessoas(),
                    reserva.getHora().format(formatter)
            ));
        }
        return sb.toString();
    }
    public String toStringTableDaylyCounter(LocalDateTime hora, String idPraia){
        TreeMap<LocalDateTime, Integer> newMap = new TreeMap<>();

        // Initialize newMap with all hours of the day from 08:00 to 20:00
        LocalDateTime startOfDay = hora.withHour(8).withMinute(0).withSecond(0).withNano(0);
        for (int i = 0; i < 13; i++) {
            newMap.put(startOfDay.plusHours(i), 0);
        }

        for (Map.Entry<Integer, Reserva> entry : reservas.entrySet()) {
            Reserva reserva = entry.getValue();
            if (reserva.getHora().toLocalDate().equals(hora.toLocalDate()) &&
                    reserva.getSombrinha().getIdPraia().equals(idPraia))
            {
                LocalDateTime hour = reserva.getHora().withMinute(0).withSecond(0).withNano(0);
                newMap.put(hour, newMap.getOrDefault(hour, 0) + 1);
            }
        }
        StringBuilder sb = new StringBuilder();
        // Table header
        String headerFormat = "%-10s | %-12s | %-12s%n";
        sb.append(String.format(headerFormat, "hora", "ocupacao", "disponiveis"));
        sb.append(String.format(headerFormat, "----------", "------------", "------------"));

        // Table rows
        String rowFormat = "%-10s | %-12s | %-12s%n";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:00");

        int maxSombrinhas;
        switch (idPraia) {
            case "A":
                maxSombrinhas = Sombrinha.MAX_SOMBRINHAS_PRAIA_A;
                break;
            case "B":
                maxSombrinhas = Sombrinha.MAX_SOMBRINHAS_PRAIA_B;
                break;
            case "C":
                maxSombrinhas = Sombrinha.MAX_SOMBRINHAS_PRAIA_C;
                break;
            default:
                maxSombrinhas = 0;
        }

        for (Map.Entry<LocalDateTime, Integer> entry : newMap.entrySet()) {
            String reservationCount = String.valueOf(entry.getValue());
            String availableCount = String.valueOf(maxSombrinhas - entry.getValue());
            sb.append(String.format(rowFormat,
                    entry.getKey().format(formatter),
                    reservationCount,
                    availableCount
            ));
        }

        return sb.toString();
    }




}

/*
    public String toStringReserva(String idPraia, int id_sombrinha){
        for (Reserva reserva : reservas) {
            if(reserva.idPraia().equals(idPraia) && reserva.idSombrinha() == id_sombrinha){
                return reserva.toString();
            }
        }
        return "Reserva não encontrada!";
    }
 */