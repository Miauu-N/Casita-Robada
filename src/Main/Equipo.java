package Main;

import java.util.ArrayList;

public class Equipo {
    ArrayList<Jugador> jugadores;
    String nombre;
    int puntos;

    public Equipo(ArrayList<Jugador> jugadores, String nombre) {
        this.jugadores = jugadores;
        this.nombre = nombre;
        this.puntos = 0;
    }

    public ArrayList<Jugador> getJugadores() {
        return this.jugadores;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void agregarPuntos(int cantidad){
        this.puntos += cantidad;
    }
}
