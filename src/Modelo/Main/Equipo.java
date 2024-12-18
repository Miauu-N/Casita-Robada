package Modelo.Main;

import Interfaces.IJugador;

import java.util.ArrayList;

public class Equipo {
    final ArrayList<Jugador> jugadores;
    final String nombre;
    int puntos;

    public Equipo(ArrayList<Jugador> jugadores, String nombre) {
        this.jugadores = jugadores;
        this.nombre = nombre;
        this.puntos = 0;
    }

    public int sumarPuntos(){
        int resultado = 0;
        for (Jugador j : jugadores){
            resultado += j.calcularPuntos();
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
        ArrayList<IJugador> resultado = new ArrayList<>(jugadores);
        return resultado;
    }
}
