package Modelo.Main;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

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

    public void sumarPuntos(){
        int resultado = 0;
        for (Jugador j : jugadores){
            resultado += j.getPuntos();
        }
        this.puntos = resultado;
    }

    @Override
    public String toString() {
        sumarPuntos();
        return nombre + ": " + puntos;
    }
}
