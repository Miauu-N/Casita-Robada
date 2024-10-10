package Main;

import Cartas.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Ronda {
    private Mesa mesa;
    private ArrayList<Jugador> jugadores;
    private Mazo mazo;

    public Ronda(ArrayList<Jugador> jugadores){
        mesa = new Mesa();
        this.jugadores = jugadores;
        this.mazo = new Mazo();
    }

    public void jugar(){
        Jugador mano = jugadores.removeLast();
        jugadores.addFirst(mano);
        mano.darMazo(mazo);

        mazo.mezclar();

        repartir();

        for (int i = 0; i < 4; i++) {
            mesa.agregarCarta(mazo.agarrarCarta());
        }

        // Juego
        for (int turno = 1; turno < 4; turno++) {
            for (Jugador j : jugadores){

                System.out.flush();

                System.out.println("Turno de " + j.getNombre() + "!!");

                System.out.println("Mano: ");
                j.mostrarCartas();
                System.out.println();

                System.out.println("Mesa: ");
                mesa.mostrarCartas();
                System.out.println();

                System.out.println("Pozos: ");
                Utils.mostrarTopes(jugadores);
                System.out.println();

                mostrarMenu();
                Scanner scanner = new Scanner(System.in);
                int seleccion = scanner.nextInt();
                while (seleccion < 1 || seleccion > 3){
                    System.out.println("Ingrese un numero valido");
                    seleccion = scanner.nextInt();
                }
                switch (seleccion){
                    case 1:
                        ligarCarta(j);
                        break;
                    case 2:
                        ligarPozo(j);
                        break;
                    case 3:
                        //dejarCarta(j);
                        break;
                }

            }
        }
    }

    private void ligarPozo(Jugador jugador) {

    }

    private void ligarCarta(Jugador jugador) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione la carta que desea usar (1 - 3):");
        jugador.mostrarCartas();
        int seleccionada = scanner.nextInt();
        while (seleccionada < 0 || seleccionada > jugador.getMano().size()){
            seleccionada = scanner.nextInt();
        }
        seleccionada--; // (1-3) -> (0-2)
        Carta select = jugador.getCarta(seleccionada);

        System.out.flush(); // no se si esto limpia la consola

        System.out.println("Tu carta es:");
        System.out.println(select);
        System.out.println("Las cartas de la mesa son: ");
        mesa.mostrarCartas();
        System.out.println("Seleccione el indeice de la carta que desea levantar :");
        seleccionada = scanner.nextInt();
        seleccionada--;
        if (seleccionada > 0){
            if (select.getNumero() == mesa.verCarta(seleccionada).getNumero()){
                jugador.agregarPozo(mesa.tomarCarta(seleccionada));
                jugador.agregarPozo(select);
            }
        }


    }

    private void mostrarMenu() {
        System.out.println("""
            1 - ligar carta
            2 - ligar pozo
            3 - dejar carta"""
        );
    }

    private void repartir() {
        for (int i = 0; i < 3; i++) {
            for (Jugador j : jugadores){
                j.darCarta(mazo.agarrarCarta());
            }
        }
    }
}
