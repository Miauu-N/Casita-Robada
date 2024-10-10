package Cartas;

import java.util.ArrayList;
import java.util.Collections;

public class Mazo {
    private ArrayList<Carta> cartas;

    public Mazo() {
        cartas = new ArrayList<>();
        ArrayList<Palo> palos = new ArrayList<>();
        palos.add(Palo.diamante);
        palos.add(Palo.corazon);
        palos.add(Palo.trebol);
        palos.add(Palo.pica);

        for (int i = 1; i < 14; i++) {
            for (Palo palo : palos) {
                cartas.add(new Carta(palo,i));
            }
        }
    }

    public Carta agarrarCarta() {
        return cartas.removeFirst();
    }

    public void mezclar() {
        Collections.shuffle(cartas);
    }

    public int cantCartas(){
        return cartas.size();
    }
}
