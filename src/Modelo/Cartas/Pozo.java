package Modelo.Cartas;

import Modelo.Exceptions.NoCardsException;

import java.io.Serializable;
import java.util.Stack;

public class Pozo implements Serializable {
    private final Stack<Carta> pozo;
    int cantidad;

    public Pozo() {
        pozo = new Stack<>();
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public Carta getTope() throws NoCardsException {
        if (pozo.isEmpty()){
            throw new NoCardsException();
        }
        else {
            return pozo.peek();
        }
    }

    public void agregarCarta(Carta carta){
        pozo.push(carta);
        cantidad++;
    }

    public void pasarCartas(Pozo pozo) {
        while (!this.pozo.isEmpty()){
            pozo.pozo.add(this.pozo.removeFirst());
        }
    }

    public void limpiarCartas() {
        pozo.clear();
    }
}
