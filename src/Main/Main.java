package Main;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("Nico"));
        jugadores.add(new Jugador("Miau"));

        Ronda ronda = new Ronda(jugadores);
        ronda.jugar();
    }
}
