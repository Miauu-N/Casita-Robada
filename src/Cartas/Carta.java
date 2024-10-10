package Cartas;

public class Carta {
    private Palo palo;
    private int numero;

    public Carta(Palo palo, int i) {
        this.palo = palo;
        this.numero = i;
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
