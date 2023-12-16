package Reservation;

public record Reserva<List>(
                    String idPraia,
                    int idSombrinha,
                    int maxPessoas,
                    int disponivel,
                    String horaInicioAluguerAtual,
                    String horaFimAluguerAtual
                    //List<String> marcacoes
                    ){}
