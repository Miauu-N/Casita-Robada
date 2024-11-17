package Modelo.Main;

import Interfaces.IJugador;
import Modelo.Cartas.*;
import Modelo.Cartas.Carta;
import Modelo.Cartas.Mazo;
import Modelo.Cartas.Pozo;
import Modelo.Exceptions.NoCardsException;

import java.util.ArrayList;
import java.util.Objects;

public class Jugador implements IJugador {
    private String nombre;
    private Mazo mazo;
    private Pozo pozo;
    private int puntos;
    private ArrayList<Carta> mano;
    private Boolean ready = false;

    public Boolean getReady() {
        return this.ready;
    }

    public void setReady() {
        this.ready = !ready;
    }

    public ArrayList<Carta> getMano() {
        return (ArrayList<Carta>) this.mano.clone();
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

    @Override
    public int getCantCartasEnMano() {
        return mano.size();
    }

    @Override
    public Carta getTope() {
        try {
            return pozo.getTope();
        } catch (NoCardsException e) {
            return null;
        }
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

    /**
     * devuelve y elimina de la mano una carta
     */
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

    @Override
    public boolean compararNombre(IJugador p2) {
        return this.nombre.equals(p2.getNombre());
    }
    public boolean compararNombre(String p2) {
        return this.nombre.equals(p2);
    }

    public void limpiarCartas() {
        mano.clear();
        pozo.limpiarCartas();
    }
}
