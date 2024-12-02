package Modelo.Main;

import Modelo.Cartas.Carta;

import java.util.ArrayList;

public class Mesa {
    private final ArrayList<Carta> cartas;

    public Mesa() {
        this.cartas = new ArrayList<>();
    }

    public void agregarCarta(Carta carta){
        cartas.add(carta);
    }

    public ArrayList<Carta> getCartas() {
        return (ArrayList<Carta>) cartas.clone();
    }

    public Carta verCarta(int seleccionada) {
        return cartas.get(seleccionada);
    }

    public Carta tomarCarta(int seleccionada) {
        return cartas.remove(seleccionada);
    }

    public boolean tiene(Carta carta) {
        for (Carta c : cartas){
            if (c.equals(carta)){
                return  true;
            }
        }
        return false;
    }
}
