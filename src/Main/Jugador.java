package Main;

import Cartas.*;

public class Jugador {
    String nombre;
    Mazo mazo;
    Pozo pozo;
    int puntos;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mazo = null;
        this.pozo = new Pozo();
        puntos = 0;
    }

    public void darMazo(Mazo mazo){
        this.mazo = mazo;
    }

    public void quitarMazo(){
        this.mazo = null;
    }

    public String getNombre() {
        return nombre;
    }

    public Pozo getPozo() {
        return pozo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void agregarPuntos(int cantidad){
        this.puntos += cantidad;
    }

    public void quitarPuntos(int cantidad){
        agregarPuntos(-cantidad);
    }
}
