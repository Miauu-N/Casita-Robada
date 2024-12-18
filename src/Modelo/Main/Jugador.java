package Modelo.Main;

import Interfaces.IJugador;
import Modelo.Cartas.*;
import Modelo.Exceptions.NoCardsException;

import java.util.ArrayList;

public class Jugador  implements IJugador{
    private final String nombre;
    private final Pozo pozo;
    private int puntos;
    private final ArrayList<Carta> mano;
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
        this.pozo = new Pozo();
        this.puntos = 0;
        this.mano = new ArrayList<>();
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

    public int calcularPuntos() {
        puntos += pozo.getCantidad();
        pozo.limpiarCartas();
        return puntos;
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
        return this.nombre.equalsIgnoreCase(p2.getNombre());
    }
    public boolean compararNombre(String p2) {
        return this.nombre.equalsIgnoreCase(p2);
    }

    public void limpiarCartas() {
        mano.clear();
        pozo.limpiarCartas();
    }

    public void reiniciarPuntos(){
        puntos = 0;
    }

    public int getCantidadCartasPozo(){
        return pozo.getCantidad();
    }
}
