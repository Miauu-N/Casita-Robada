package Modelo.Cartas;

import java.util.Objects;

/**
 * Clase contenedora (se puede retornar sin problemas de aliasing)
 */

public class Carta {
    private Palo palo;
    private int numero;

    public Carta(Palo palo, int i) {
        this.palo = palo;
        this.numero = i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carta carta = (Carta) o;
        return numero == carta.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numero);
    }

    @Override
    public String toString() {
        String valor = switch (this.numero) {
            case 1 -> "A";
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            default -> Integer.toString(numero);
        };
        return  valor + " de " + palo;
    }

    public int getNumero() {
        return this.numero;
    }
}
