package Modelo.Main;

import Interfaces.IJugador;

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

    public int sumarPuntos(){
        int resultado = 0;
        for (Jugador j : jugadores){
            resultado += j.getPuntos();
            j.reiniciarPuntos();
        }
        this.puntos += resultado;
        return puntos;
    }

    @Override
    public String toString() {
        sumarPuntos();
        return nombre + ": " + puntos;
    }

    public ArrayList<IJugador> getJugadoresEncapsulados() {
        ArrayList<IJugador> resultado = new ArrayList<>();
        for (Jugador j : jugadores){
            resultado.add((IJugador) j);
        }
        return resultado;
    }
}
