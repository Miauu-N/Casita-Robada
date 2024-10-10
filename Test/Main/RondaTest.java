package Main;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RondaTest {

    @Test
    public void pruebaRonda(){
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("Nico"));
        jugadores.add(new Jugador("Miau"));

        Ronda ronda = new Ronda(jugadores);
        ronda.jugar();
    }

}