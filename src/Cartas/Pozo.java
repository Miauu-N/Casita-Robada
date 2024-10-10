package Cartas;

import Exceptions.NoCardsException;

import java.util.Stack;

public class Pozo {
    private Stack<Carta> pozo;
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
            pozo.pozo.add(this.pozo.removeLast());
        }
    }
}
