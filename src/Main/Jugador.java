package Main;

import Cartas.*;

import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private Mazo mazo;
    private Pozo pozo;
    private int puntos;
    private ArrayList<Carta> mano;

    public ArrayList<Carta> getMano() {
        return this.mano;
    }

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mazo = null;
        this.pozo = new Pozo();
        this.puntos = 0;
        this.mano = new ArrayList<>();
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

    public void darCarta(Carta carta){
        this.mano.add(carta);
    }

    public void mostrarCartas(){
        for (Carta c : mano){
            System.out.println(c);
        }
    }

    public Carta getCarta(int indice) {
        return mano.remove(indice);
    }

    public void agregarPozo(Carta carta) {
        pozo.agregarCarta(carta);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
