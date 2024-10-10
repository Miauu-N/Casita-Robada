package Main;

import Cartas.Carta;

import java.util.ArrayList;

public class Mesa {
    private final ArrayList<Carta> cartas;

    public Mesa() {
        this.cartas = new ArrayList<>();
    }

    public void agregarCarta(Carta carta){
        cartas.add(carta);
    }
}
