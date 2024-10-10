package Cartas;

import java.util.ArrayList;

public class Mazo {
    ArrayList<Carta> cartas;

    public Mazo() {
        cartas = new ArrayList<>();
        ArrayList<Palo> palos = new ArrayList<>();
        palos.add(Palo.diamante);
        palos.add(Palo.corazon);
        palos.add(Palo.trebol);
        palos.add(Palo.pica);

        for (int i = 0; i < 14; i++) {
            for (Palo palo : palos) {
                cartas.add(new Carta(palo,i));
            }
        }
    }
}
