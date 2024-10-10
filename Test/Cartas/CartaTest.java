package Cartas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartaTest {

    @Test
    public void cartaToString(){
        Carta carta = new Carta(Palo.corazon,11);
        System.out.println(carta);
    }

}