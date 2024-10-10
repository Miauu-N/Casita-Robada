package Main;

import Exceptions.NoCardsException;

import java.util.ArrayList;

public class Utils {
    static public void mostrarTopes(ArrayList<Jugador> jugadores){
        for (Jugador j : jugadores){
            System.out.println("Jugador: " + j.getNombre());
            try {
                System.out.println(j.getPozo().getTope());
            } catch (NoCardsException e) {
                System.out.println("Sin cartas");
            }
            System.out.println(j.getPozo().getCantidad());
        }
    }
}
