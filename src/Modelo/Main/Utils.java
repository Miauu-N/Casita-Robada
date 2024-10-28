package Modelo.Main;

import Modelo.Exceptions.NoCardsException;

import java.util.ArrayList;

public class Utils {
    static public void mostrarTopes(ArrayList<Jugador> jugadores){
        for (Jugador j : jugadores){
            System.out.println("Jugador: " + j.getNombre());
            try {
                System.out.println("Tope: " + j.getPozo().getTope());
            } catch (NoCardsException e) {
                System.out.println("Sin cartas");
            }
            System.out.println("Cantidad de cartas: " + j.getPozo().getCantidad());
            System.out.println();
        }
    }
}
