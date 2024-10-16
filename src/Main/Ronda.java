package Main;

import Cartas.*;
import Exceptions.NoCardsException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

//TODO: testear robar pozo
//TODO: crear partida
//TODO: robar pozo no anda
//TODO: ligar mesa test fail (3 carta - 1 mesa)


public class Ronda {
    private final Mesa mesa;
    private final ArrayList<Jugador> jugadores;
    private final Mazo mazo;

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


        for (int i = 0; i < 4; i++) {
            mesa.agregarCarta(mazo.agarrarCarta());
        }

        // Juego
        while (mazo.cantCartas() > (jugadores.size() * 3 + 4)) { // 3 cartas para cada jugador y 4 en mesa
            repartir();
            for (int turno = 1; turno < 4; turno++) {
                for (Jugador j : jugadores){

                    System.out.flush();

                    mostrarSituacionPartida(j);

                    mostrarMenu();

                    Scanner scanner = new Scanner(System.in);
                    int seleccion = scanner.nextInt();
                    while (seleccion < 1 || seleccion > 4){
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
                            dejarCarta(j);
                            break;
                        case 4:
                            soplar(j);
                            break;
                    }

                }
            }
        }

    }

    private void soplar(Jugador j) {
        System.out.println("Las cartas de la mesa son: ");
        mesa.mostrarCartas();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleccione el indice de la primera carta que desea soplar :");
        int seleccionada1 = scanner.nextInt();
        System.out.println("Seleccione el indice de la segunda carta que desea soplar :");
        int seleccionada2 = scanner.nextInt();
        seleccionada1--;
        seleccionada2--;

        if (seleccionada1 != seleccionada2 && mesa.verCarta(seleccionada1).getNumero() == mesa.verCarta(seleccionada2).getNumero()){
            if (seleccionada1 < seleccionada2){
                int aux = seleccionada2;
                seleccionada2 = seleccionada1;
                seleccionada1 = aux;
            }
            j.getPozo().agregarCarta(mesa.tomarCarta(seleccionada1));
            j.getPozo().agregarCarta(mesa.tomarCarta(seleccionada2));
        }
        else {
            System.out.println("Jugada incorrecta");
            j.quitarPuntos(1);
        }

    }

    private void mostrarSituacionPartida(Jugador j) {
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
    }

    private void ligarPozo(Jugador jugador) {
        Carta select = elegirCarta(jugador);

        System.out.println("Tu carta es:");
        System.out.println(select);

        System.out.println("Pozos: ");
        Utils.mostrarTopes(jugadores);
        System.out.println();

        System.out.println("Seleccione el indice del pozo que desea levantar :");
        Scanner scanner = new Scanner(System.in);
        int seleccionado = scanner.nextInt();
        seleccionado--;
        boolean jugadaCorrecta = false;

        try {
            Jugador robado = jugadores.get(seleccionado);
            if (select.getNumero() == robado.getPozo().getTope().getNumero() && !mesa.tiene(select.getNumero())){
                robado.getPozo().agregarCarta(select);
                if (robado != jugador){
                    robado.getPozo().pasarCartas(jugador.getPozo());
                }
            }
        } catch (NoCardsException ignored){  // entra aca en caso de que quiera robar un pozo sin cartas o un indice incorrecto
        }

        if (!jugadaCorrecta){
            System.out.println("Jugada invalida");
            dejarCarta(select);
        }

    }

    private void dejarCarta(Carta select) {
        mesa.agregarCarta(select);
    }

    private void dejarCarta(Jugador jugador) {
        Carta select = elegirCarta(jugador);
        dejarCarta(select);
    }

    private void ligarCarta(Jugador jugador) {
        Carta select = elegirCarta(jugador);

        System.out.println("Tu carta es:");
        System.out.println(select);

        System.out.println("Las cartas de la mesa son: ");
        mesa.mostrarCartas();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleccione el indice de la carta que desea levantar :");
        int seleccionada = scanner.nextInt();
        seleccionada--;

        boolean buenaJugada = false;

        if (seleccionada >= 0 && seleccionada < mesa.cantCartas()){
            if (select.getNumero() == mesa.verCarta(seleccionada).getNumero()){
                jugador.agregarPozo(mesa.tomarCarta(seleccionada));
                jugador.agregarPozo(select);
                buenaJugada = true;
            }
        }

        if (!buenaJugada){
            System.out.println("Jugada equivocada");
            dejarCarta(select);
        }

    }

    private static Carta elegirCarta(Jugador jugador) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione la carta que desea usar (1 - 3):");
        jugador.mostrarCartas();
        int seleccionada = scanner.nextInt();
        while (seleccionada < 0 || seleccionada > jugador.getMano().size()) {
            seleccionada = scanner.nextInt();
        }
        seleccionada--; // (1-3) -> (0-2)
        return jugador.getCarta(seleccionada);
    }

    private void mostrarMenu() {
        System.out.println("""
            1 - ligar carta
            2 - ligar pozo
            3 - dejar carta
            4 - soplar"""
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
