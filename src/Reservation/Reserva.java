package Reservation;

import java.time.LocalDateTime;

// "As reservas decorrem das 8:00 até às 20:00 por períodos de 1 hora."
public class Reserva {
    public static final int HORA_INICIO_RESERVAS = 8;
    public static final int HORA_FIM_RESERVAS = 20;
    private int idReserva;
    private LocalDateTime hora;
    private Sombrinha sombrinha;

    public Reserva(int idReserva, LocalDateTime hora, Sombrinha sombrinha) {
        if (!(hora.getHour() >= HORA_INICIO_RESERVAS - 1 && hora.getHour() <= HORA_FIM_RESERVAS)
        ){
            throw new IllegalArgumentException("Hora inválida. As reservas são das 8:00 às 20:00.");
        }
        if (hora.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Data inválida. A data da reserva não pode ser anterior à data atual.");
        }
        this.idReserva = idReserva;
        this.hora = hora;
        this.sombrinha = sombrinha;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public LocalDateTime getHora() {
        return hora;
    }

    public void setHora(LocalDateTime hora) {
        this.hora = hora;
    }

    public Sombrinha getSombrinha() {
        return sombrinha;
    }

    public void setSombrinha(Sombrinha sombrinha) {
        this.sombrinha = sombrinha;
    }
}

