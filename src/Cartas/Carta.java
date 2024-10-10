package Cartas;

public class Carta {
    Palo palo;
    int numero;

    public Carta(Palo palo, int i) {
        this.palo = palo;
        this.numero = i;
    }

    @Override
    public String toString() {
        char valor = switch (this.numero) {
            case 1 -> 'A';
            case 11 -> 'J';
            case 12 -> 'Q';
            case 13 -> 'K';
            default -> (char) this.numero;
        };
        return  valor + " de " + palo;
    }
}
