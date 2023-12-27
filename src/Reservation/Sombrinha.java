package Reservation;

import java.util.Objects;

public class Sombrinha {
    private final String idPraia;
    private final int idSombrinha;
    private final int maxPessoas;

    public Sombrinha(String idPraia, int idSombrinha, int maxPessoas) {
        if (!(idPraia.equals("A") || idPraia.equals("B") || idPraia.equals("C"))) {
            throw new IllegalArgumentException("Invalid idPraia. It must be 'A', 'B', or 'C'.");
        }
        if (idSombrinha < 1 || idSombrinha > 10000) {
            throw new IllegalArgumentException("Invalid idSombrinha. It must be between 1 and 1000.");
        }
        if (maxPessoas < 2 || maxPessoas > 4) {
            throw new IllegalArgumentException("Invalid maxPessoas. It must be between 2 and 4.");
        }
        this.idPraia = idPraia;
        this.idSombrinha = idSombrinha;
        this.maxPessoas = maxPessoas;
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
    public int hashCode() {
        return Objects.hash(idPraia, idSombrinha, maxPessoas);
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
