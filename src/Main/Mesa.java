package Main;

import Cartas.Carta;

import java.util.ArrayList;

public class Mesa {
    private final ArrayList<Carta> cartas;

    public Mesa() {
        this.cartas = new ArrayList<>();
    }

    public void agregarCarta(Carta carta){
        cartas.add(carta);
    }

    public void mostrarCartas() {
        for (Carta c : cartas){
            System.out.println(c);
        }
    }

    public Carta verCarta(int seleccionada) {
        return cartas.get(seleccionada);
    }

    public Carta tomarCarta(int seleccionada) {
        return cartas.remove(seleccionada);
    }

    public int cantCartas() {
        return cartas.size();
    }

    public boolean tiene(int numero) {
        for (Carta c : cartas){
            if (c.getNumero() == numero){
                return  true;
            }
        }
        return false;
    }
}
