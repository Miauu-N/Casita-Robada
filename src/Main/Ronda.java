package Main;

import java.util.ArrayList;

public class Ronda {
    Mesa mesa;
    ArrayList<Jugador> jugadores;

    public Ronda(ArrayList<Jugador> jugadores){
        mesa = new Mesa();
        this.jugadores = jugadores;
    }
}
