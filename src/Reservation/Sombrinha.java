package Reservation;

/*
    * Sombrinhas e espreguiçadeiras:
    Praia A: (20*)
	• 10 Sombrinhas e espreguiçadeiras para máximo 2 pessoas (A1 até A10);
	• 5 Sombrinhas e espreguiçadeiras para máximo 3 pessoas (A11 até A15);
	• 5 Sombrinhas e espreguiçadeiras para máximo 4 pessoas (A16 até A20);
    Praia B: (11*)
	• 5 Sombrinhas e espreguiçadeiras para máximo 2 pessoas (B1 até B5);
	• 5 Sombrinhas e espreguiçadeiras para máximo 3 pessoas (B6 até B10);
	• 1 Sombrinha e espreguiçadeiras para máximo 4 pessoas (B11);
    Praia C: (10*)
	• 10 Sombrinhas e espreguiçadeiras para máximo 2 pessoas (C1 até C10);
 */

public class Sombrinha {
    private final String idPraia;
    private final int idSombrinha;
    private final int maxPessoas;

    public Sombrinha(String idPraia, int idSombrinha) {
        if (!(idPraia.equals("A") || idPraia.equals("B") || idPraia.equals("C"))) {
            throw new IllegalArgumentException("Invalid idPraia. Só existem as localizações: 'A', 'B', 'C' ");
        }
        if (idSombrinha < 1) {
            throw new IllegalArgumentException("Invalid idSombrinha. Tem que ser maior que 0.");
        }
        // Se for Praia A temos 20 sombrinhas
        if (idPraia.equals("A") && idSombrinha > 20) {
            throw new IllegalArgumentException("Invalid idSombrinha. Praia A só tem 20 sombrinhas.");
        }
        // Se for Praia B temos 11 sombrinhas
        if (idPraia.equals("B") && idSombrinha > 11) {
            throw new IllegalArgumentException("Invalid idSombrinha. Praia B só tem 11 sombrinhas.");
        }
        // Se for Praia C temos 10 sombrinhas
        if (idPraia.equals("C") && idSombrinha > 10) {
            throw new IllegalArgumentException("Invalid idSombrinha. Praia C só tem 10 sombrinhas.");
        }

        this.idPraia = idPraia;
        this.idSombrinha = idSombrinha;
        this.maxPessoas = switch (idPraia+idSombrinha) {
            case "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8" , "A9" , "A10", "B1", "B2", "B3", "B4", "B5", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10" -> 2;
            case "A11", "A12", "A13", "A14", "A15", "B6", "B7", "B8", "B9", "B10" -> 3;
            default -> 4;
        };
    }

    public String getIdPraia() {
        return idPraia;
    }

    public int getIdSombrinha() {
        return idSombrinha;
    }

    public int getMaxPessoas() {
        return maxPessoas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sombrinha sombrinha = (Sombrinha) o;
        return idSombrinha == sombrinha.idSombrinha &&
                maxPessoas == sombrinha.maxPessoas &&
                idPraia.equals(sombrinha.idPraia);
    }

    @Override
    public String toString() {
        return "Sombrinha{" +
                "idPraia='" + idPraia + '\'' +
                ", idSombrinha=" + idSombrinha +
                ", maxPessoas=" + maxPessoas +
                '}';
    }
}
