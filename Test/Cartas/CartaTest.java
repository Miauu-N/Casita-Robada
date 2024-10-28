package Cartas;

import Modelo.Cartas.Carta;
import Modelo.Cartas.Palo;
import org.junit.jupiter.api.Test;

class CartaTest {

    @Test
    public void cartaToString(){
        Carta carta = new Carta(Palo.corazon,11);
        System.out.println(carta);
    }

}