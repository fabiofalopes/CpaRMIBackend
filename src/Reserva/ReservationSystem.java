package Reserva;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationSystem {
    private List<Reservation> reservations;

    public ReservationSystem() {
        this.reservations = new ArrayList<>();
    }

    public void loadRentalsFromFile(String filePath) {
        List<Reservation> rentals = new ArrayList<>();
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



    public void makeReservation(String customerName, LocalDateTime startTime, LocalDateTime endTime) {
        if (isReservationPossible(startTime, endTime)) {
            Reservation reservation = new Reservation(customerName, startTime, endTime);
            reservations.add(reservation);

            System.out.println("Reservation made successfully:");
            System.out.println("Customer: " + customerName);
            System.out.println("Start Time: " + startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            System.out.println("End Time: " + endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } else {
            System.out.println("Reservation is not possible. There is a conflict with existing reservations.");
        }
    }

    public boolean isReservationPossible(LocalDateTime startTime, LocalDateTime endTime) {
        for (Reservation existingReservation : reservations) {
            if (existingReservation.conflictsWith(startTime, endTime)) {
                return false; // Conflict found
            }
        }
        return true; // No conflicts
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public static void main(String[] args) {
        ReservationSystem reservationSystem = new ReservationSystem();

        // Example usage
        reservationSystem.makeReservation("John Doe", LocalDateTime.of(2023, 12, 15, 10, 0), LocalDateTime.of(2023, 12, 15, 12, 0));
        reservationSystem.makeReservation("Jane Smith", LocalDateTime.of(2023, 12, 16, 14, 0), LocalDateTime.of(2023, 12, 16, 16, 0));

        // Try to make a conflicting reservation
        reservationSystem.makeReservation("Alice", LocalDateTime.of(2023, 12, 15, 11, 0), LocalDateTime.of(2023, 12, 15, 13, 0));

        // Display the reservations
        System.out.println("\nReservations:");
        for (Reservation reservation : reservationSystem.getReservations()) {
            System.out.println(reservation);
        }
    }
}
