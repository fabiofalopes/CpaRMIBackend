package Reservation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ReservaUtility {
    private List<Reserva> reservas;
    //private HashMap<String, Reserva> res;

    public ReservaUtility() {
        this.reservas = new ArrayList<>();
        //reservas = loadRentalsFromFile("input-files/init.txt");
        loadRentalsFromFile("input-files/init.txt");
    }

    public List<Reserva> getReservas() {
        return reservas;
    }


    public void loadRentalsFromFile(String filePath) {
        List<Reserva> rentals = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\\|");
                if (fields.length == 6) {
                    String idPraia = fields[0]; // id da praia: A, B, C, D, E...
                    int idSombrinha = Integer.parseInt(fields[1]); // id da sombrinha : 1, 2, 3, 4, 5...
                    int maxPessoas = Integer.parseInt(fields[2]); // 2, 3 ou 4
                    int disponivel = Integer.parseInt(fields[3]); // 0 - false, 1 - true
                    String horaInicioAluguerAtual = fields[4];  // formato HH:MM
                    String horaFimAluguerAtual = fields[5];    // formato HH:MM
                    // Adiciona a reserva à lista de reservas
                    reservas.add(new Reserva(idPraia, idSombrinha, maxPessoas, disponivel, horaInicioAluguerAtual, horaFimAluguerAtual));
                } else {
                    // Handle error or invalid line format
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void insertReserva(String idPraia, int idSombrinha, int maxPessoas, int disponivel, String horaInicioAluguerAtual, String horaFimAluguerAtual) {
        reservas.add(new Reserva(idPraia, idSombrinha, maxPessoas, disponivel, horaInicioAluguerAtual, horaFimAluguerAtual));
    }
    public void SaveRentalsToFile(String filePath, List<Reserva> rentals) {
        // TODO: implement this method
    }
    public String toStringAll() {
        // idPraia | idSombrinha | maxPessoas | disponivel | horaInicioAluguerAtual | horaFimAluguerAtual
        String sb = "";
        for (Reserva reserva : reservas) {
            sb += reserva.idPraia() + "|" + reserva.idSombrinha() + "|" + reserva.maxPessoas() + "|" + reserva.disponivel() + "|" + reserva.horaInicioAluguerAtual() + "|" + reserva.horaFimAluguerAtual() + "\n";
        }
        return sb;
    }
    public boolean reservaSombrinha(String idPraia, int id_sombrinha, String horaInicio, String horaFim){
        if (verificaDisponibilidade(idPraia, id_sombrinha, horaInicio, horaFim)){
            for (Reserva reserva : reservas) {
                if(reserva.idPraia().equals(idPraia) &&
                    reserva.idSombrinha() == id_sombrinha &&
                    reserva.disponivel() == 1
                ){

                }
            }
        }
        return false;
    }
    public boolean cancelaReserva(String idPraia, int id_sombrinha){
        //TODO: implement this method
        return false;
    }
    public String toStringReserva(String idPraia, int id_sombrinha){
        for (Reserva reserva : reservas) {
            if(reserva.idPraia().equals(idPraia) && reserva.idSombrinha() == id_sombrinha){
                return reserva.toString();
            }
        }
        return "Reserva não encontrada!";
    }

    public String getIdSombrinhasDisponivel(){
        String sb = "";
        for (Reserva reserva : reservas) {
            if(reserva.disponivel() == 1){
                sb += reserva.idPraia() + "|" + reserva.idSombrinha() +  "\n";
            }
        }
        return sb;
    }

    public String getInfoSombrinha(String idPraia, int id_sombrinha){
        for (Reserva reserva : reservas) {
            if(reserva.idPraia().equals(idPraia) && reserva.idSombrinha() == id_sombrinha){
                return reserva.toString();
            }
        }
        return "Sombrinha não encontrada!";
    }

    public String getSombrinhasOcupadas(){
        String sb = "";
        // idPraia | idSombrinha | maxPessoas | disponivel | horaInicioAluguerAtual | horaFimAluguerAtual
        for (Reserva reserva : reservas) {
            if(reserva.disponivel() == 0){
                sb += reserva.idPraia() + "|" + reserva.idSombrinha() + "|" + reserva.maxPessoas() + "|" + reserva.horaInicioAluguerAtual() + "|" + reserva.horaFimAluguerAtual() + "\n";
            }
        }
        return sb;
    }

    public boolean verificaDisponibilidade(String idPraia, int id_sombrinha, String horaInicio, String horaFim){
        for (Reserva reserva : reservas) {
            if(reserva.idPraia().equals(idPraia) && reserva.idSombrinha() == id_sombrinha){
                if(reserva.disponivel() == 1){
                    return true;
                }
            }
        }
        return false;
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

}
