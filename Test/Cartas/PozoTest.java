package Cartas;

import Modelo.Cartas.Carta;
import Modelo.Cartas.Palo;
import Modelo.Cartas.Pozo;
import org.junit.jupiter.api.Test;

class PozoTest {

    @Test
    void pasarCartas() {
        Pozo p1 = new Pozo();
        Pozo p2 = new Pozo();
        p1.agregarCarta(new Carta(Palo.pica,2));
        p1.agregarCarta(new Carta(Palo.corazon,2));
        p2.agregarCarta(new Carta(Palo.trebol,2));
        p2.agregarCarta(new Carta(Palo.diamante,2));
        p1.pasarCartas(p2);
        System.out.println(p1.getCantidad());
            System.out.println(p2.getCantidad());
    }
}