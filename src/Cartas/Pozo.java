package Cartas;

import java.util.Stack;

public class Pozo {
    Stack<Carta> pozo;

    public Pozo() {
        pozo = new Stack<>();
    }

    public Carta getTope(){
        if (pozo.isEmpty()){
            return null;
        }
        else {
            return pozo.peek();
        }
    }

    public void agregarCarta(Carta carta){
        pozo.push(carta);
    }
}
