package Main;

import Modelo.Main.Jugador;
import Modelo.Main.Ronda;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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