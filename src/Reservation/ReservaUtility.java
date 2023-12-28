package Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.IOException;
import java.util.stream.Collectors;

public class ReservaUtility {
    private TreeMap<Integer, Reserva> reservas = new TreeMap<>();

    public ReservaUtility(){
        // Se existir uma sessão anterior, carregar do ficheiro de output
        if (Files.exists(Paths.get("output-files/reservas.txt"))) {
            loadFromFile("output-files/reservas.txt");
        }
        else {
            // Caso contrário, carregar do ficheiro de input e iniciar sessão teste
            loadFromFile("input-files/persistence.txt");
        }
    }

    public void updateStateFromFile() {
        // Se existir uma sessão anterior, carregar do ficheiro de output
        if (Files.exists(Paths.get("output-files/reservas.txt"))) {
            loadFromFile("output-files/reservas.txt");
        }
    }

    public void loadFromFile(String filePath) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        int idReserva = 1;
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split("\\|");
                String idPraia = parts[0];
                int idSombrinha = Integer.parseInt(parts[1]);
                //int maxPessoas = Integer.parseInt(parts[2]);
                LocalTime time = LocalTime.parse(parts[3], timeFormatter);
                LocalDate date = LocalDate.parse(parts[4], dateFormatter);
                LocalDateTime hora = LocalDateTime.of(date, time);
                Sombrinha sombrinha = new Sombrinha(idPraia, idSombrinha);
                Reserva reserva = new Reserva(idReserva++, hora,  sombrinha);
                //reservas.put(idReserva, reserva);
                inserirReserva(hora, idPraia, idSombrinha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Save current state to output-files/reservas.txt e output-files/reservas-bk.txt
        backup();
    }

    public void saveToFile(String filePath) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm|dd-MM-yyyy");
        try {
            List<String> lines = reservas.values().stream()
                    .map(reserva -> {
                        Sombrinha sombrinha = reserva.getSombrinha();
                        LocalDateTime hora = reserva.getHora();
                        return sombrinha.getIdPraia() + "|" +
                                sombrinha.getIdSombrinha() + "|" +
                                sombrinha.getMaxPessoas() + "|" +
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
    // Inserir reserva deve retornar id da reserva
    public boolean inserirReserva(LocalDateTime hora, String idPraia , int idSombrinha) {
        int generateId = 1;
        if (!reservas.isEmpty()){  // Se não estiver vazio temos que gerar um id único para a reserva
            generateId = reservas.lastKey() + 1;
            Sombrinha sombrinha = new Sombrinha(idPraia, idSombrinha);
            if (verificarDisponibilidade(hora, sombrinha) ||
                reservas.isEmpty()
            ){
                Reserva reserva = new Reserva(
                        generateId,
                        hora,
                        sombrinha
                );
                reservas.put(reserva.getIdReserva(), reserva);
                return true;
            }
        }
        else {
            Sombrinha sombrinha = new Sombrinha(idPraia, idSombrinha);
            Reserva reserva = new Reserva(
                    generateId,
                    hora,
                    sombrinha
            );
            reservas.put(reserva.getIdReserva(), reserva);
            return true;
        }

        return false;
    }
    public boolean verificarDisponibilidade(LocalDateTime hora, Sombrinha sombrinha) {
        for (Integer i : reservas.keySet()) {
            Reserva reserva = reservas.get(i);
            if (reserva.getHora().equals(hora) && reserva.getSombrinha().equals(sombrinha)) {
                return false;
            }
        }
        return true;
    }
    public boolean cancelarReserva(int idReserva) {
        for (Map.Entry<Integer, Reserva> entry : reservas.entrySet()) {
            if (entry.getValue().getIdReserva() == idReserva) {
                reservas.remove(entry.getKey());
                backup();
                updateStateFromFile();
                return true;
            }
        }
        return false;
    }
    public boolean cancelarReserva(LocalDateTime hora , String idPraia, int idSombrinha) {
        for (Map.Entry<Integer, Reserva> entry : reservas.entrySet()) {
            if (entry.getValue().getHora().equals(hora) &&
                entry.getValue().getSombrinha().equals(new Sombrinha(idPraia, idSombrinha)))
            {
                reservas.remove(entry.getKey());
                // Save to files
                backup();
                updateStateFromFile();
                return true;
            }
        }
        return false;
    }

    public void listarReservas() {
        reservas.forEach((key, value) -> System.out.println(value));
    }

    // Devolve String com uma tabela ordenada por datas de marcação
    public String toStringTable() {

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



}

/*

    public String toStringAll() {
        // idPraia | idSombrinha | maxPessoas | disponivel | horaInicioAluguerAtual | horaFimAluguerAtual
        String sb = "";
        for (Reserva reserva : reservas) {
            sb += reserva.idPraia() + "|" + reserva.idSombrinha() + "|" + reserva.maxPessoas() + "|" + reserva.disponivel() + "|" + reserva.horaInicioAluguerAtual() + "|" + reserva.horaFimAluguerAtual() + "\n";
        }
        return sb;
    }

    public String toStringReserva(String idPraia, int id_sombrinha){
        for (Reserva reserva : reservas) {
            if(reserva.idPraia().equals(idPraia) && reserva.idSombrinha() == id_sombrinha){
                return reserva.toString();
            }
        }
        return "Reserva não encontrada!";
    }

public String toStringALL() {
        StringBuilder sb = new StringBuilder();
        // Table header
        String headerFormat = "%-10s | %-12s | %-11s | %-11s | %-22s | %-22s%n";
        sb.append(String.format(headerFormat, "idPraia", "idSombrinha", "maxPessoas", "disponivel", "horaInicioAluguerAtual", "horaFimAluguerAtual"));
        sb.append(String.join("", Collections.nCopies(95, "-"))); // Separator line
        sb.append("\n");

        // Table rows
        String rowFormat = "%-10s | %-12d | %-11d | %-11s | %-22s | %-22s%n";
        for (Reserva reserva : reservas) {
            sb.append(String.format(rowFormat,
                    reserva.idPraia(),
                    reserva.idSombrinha(),
                    reserva.maxPessoas(),
                    reserva.disponivel() == 1 ? "Sim" : "Não", // Assuming 1 is true for available
                    reserva.horaInicioAluguerAtual(),
                    reserva.horaFimAluguerAtual()));
        }
        return sb.toString();
    }

    public String toStringTable() {
        StringBuilder sb = new StringBuilder();
        // Table header
        String headerFormat = "%-10s | %-12s | %-11s | %-11s | %-22s | %-22s%n";
        sb.append(String.format(headerFormat, "idPraia", "idSombrinha", "maxPessoas", "disponivel", "horaInicioAluguerAtual", "horaFimAluguerAtual"));
        sb.append(String.format(headerFormat, "----------", "------------", "-----------", "-----------", "----------------------", "----------------------"));

        // Table rows
        String rowFormat = "%-10s | %-12d | %-11d | %-11d | %-22s | %-22s%n";
        for (Reserva reserva : reservas) {
            sb.append(String.format(rowFormat,
                    reserva.idPraia(),
                    reserva.idSombrinha(),
                    reserva.maxPessoas(),
                    reserva.disponivel(),
                    reserva.horaInicioAluguerAtual(),
                    reserva.horaFimAluguerAtual()
            ));
        }
        return sb.toString();
    }

 */