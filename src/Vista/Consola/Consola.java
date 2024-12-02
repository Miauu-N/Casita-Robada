package Vista.Consola;

import Controlador.Controlador;
import Interfaces.IJugador;
import Interfaces.iVista;
import Modelo.Cartas.Carta;
import Modelo.Exceptions.InvalidInputException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Consola extends JFrame implements iVista {
    private final Controlador controlador;
    private JPanel panelPrincipal;
    private JTextArea aJuego;
    private JTextField fConsola;
    private boolean activo = true;
    private Esperando esperando;
    private boolean ready = false;

    public Consola() {
        this.controlador = new Controlador(this);

        setTitle("Casita Robada");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(panelPrincipal);
        this.pack();
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setVisible(activo);

        ActionListener l = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesar(fConsola.getText());
                fConsola.setText("");
            }
        };
        fConsola.addActionListener(l);

        menuNombre();
    }

    private void procesar(String comando) {
        switch (this.esperando){
            case nombre -> {
                if (controlador.addJugador(comando)){
                    mostrarMenuPrincipal();
                }
            }

            case menu -> {
                switch (comando) {
                    case "1" -> {
                        ready = !ready;
                        if (ready) {
                            aJuego.append("\nEstas listo");
                        } else {
                            aJuego.append("\nListo Cancelado");
                        }
                        controlador.ready();
                    }
                    case "2" -> reglas();
                    case "0" -> System.exit(0);
                }
            }

            case respuestaParejas -> {
                if (comando.equals("0")){
                    controlador.responderParejas(false);
                } else if (comando.equals("1")) {
                    controlador.responderParejas(true);
                }
            }

            case armarEquipo -> {
                try {
                    controlador.elegirEquipo(comando);
                }
                catch (InvalidInputException e){
                    aJuego.append("\nJugador no existente");
                }
            }

            case jugada -> {
                switch (comando) {
                    case "0" -> ligarMesa();
                    case "1" -> ligarPozo();
                    case "2" -> soplar();
                    case "3" -> dejarCarta();
                }
            }

            case ligarMesa -> {
                String[] indices = comando.split(" ");
                if (indices.length == 2){
                    try {
                        controlador.agarrarCartaMesa(Integer.parseInt(indices[1]),Integer.parseInt(indices[0]));
                        esperando = null;
                    } catch (Exception e) {
                        aJuego.append("\n comando incorrecto");
                    }
                }
                else {
                    aJuego.append("\n comando incorrecto");
                }
            }

            case soplar -> {
                String[] indices = comando.split(" ");
                if (indices.length == 2){
                    try {
                        controlador.soplar(Integer.parseInt(indices[1]),Integer.parseInt(indices[0]));
                    } catch (Exception e) {
                        aJuego.append("\n comando incorrecto");
                    }
                }
                else {
                    aJuego.append("\n comando incorrecto");
                }
                asignarTurno();
            }

            case ligarPozo -> {
                String[] indices = comando.split(" ");
                if (indices.length == 2){
                    try {
                        controlador.robarPozo(indices[1],Integer.parseInt(indices[0]));
                        esperando = null;
                    } catch (Exception e) {
                        aJuego.append("\n comando incorrecto");
                    }
                }
                else {
                    aJuego.append("\n comando incorrecto");
                }
            }

            case dejarCarta -> {
                if (comando.length() == 1){
                    try {
                        controlador.dejarCarta(Integer.parseInt(comando));
                        esperando = null;
                    } catch (Exception e) {
                        aJuego.append("\n comando incorrecto");
                    }
                }
                else {
                    aJuego.append("\n comando incorrecto");
                }
            }

            case null -> {}
        }
    }

    private void dejarCarta() {
        esperando = Esperando.dejarCarta;
        aJuego.append("""
                
                seleccione la carta que desea dejar:
                """);
    }

    private void soplar() {
        esperando = Esperando.soplar;
        aJuego.append("""
                
                Seleccione las cartas de la mesa que desea agarrar separadas por un espacio:
                """);
    }

    private void ligarPozo() {
        esperando = Esperando.ligarPozo;
        aJuego.append("""
                
                Seleccione la carta de su mano con la que quiere ligar y el nombre de la persona
                 a la cual quiere robar separadas por un espacio:
                """);
    }

    private void ligarMesa() {
        esperando = Esperando.ligarMesa;
        aJuego.append("""
                
                Seleccione la carta de su mano con la que quiere ligar y la carta de la mesa que
                 quiere ligar separadas por un espacio:
                """);
    }

    public void menuNombre() {
        menuNombre(false);
    }

    @Override
    public void menuNombre(boolean error) {
        aJuego.setText("""
                Ingrese su nombre para empezar el juego:
                """);
        if (error){
            aJuego.append("\nNombre no valido, intente con otro:");
        }
        esperando = Esperando.nombre;
        panelPrincipal.updateUI();
    }

    public void reglas() {
        aJuego.append("""
               \s
                _Objetivo:
                 El juego consiste en ligar cualquiera de las cartas que se tienen en la mano con
                  otra de igual número que se halle sobre la mesa, o en la parte superior del pozo
                  de cada jugador; en este último caso se le roba todo el pozo.
                \s
                _Jugadores:
                 Pueden intervenir entre 2 a 4 Jugadores, en forma individual o por parejas.
                \s
                _Inicio del juego:
                 se reparten tres cartas a cada jugador, descubriendo luego cuatro cartas boca
                  arriba sobre la mesa. Cuando cada jugador haya jugado sus tres cartas,se deben
                  repartir otras tres a cada uno.
                \s
                _Desarrollo del juego:
                 El juego es comenzado por el mano, quien, si tiene entre sus cartas alguna que liga
                  con las de la  mesa, baja la suya reuniéndola con la otra y diciendo qué cartas liga.
                  Si hubiera sobre la mesa dos cartas de igual número, sólo podrá robar una de ellas.
                  Las dos cartas que así reúne las coloca hacia arriba a su lado iniciando así su pozo)
                  y pasa el turno al siguiente; las cartas que en sucesivas manos vaya reuniendo, las
                  ubicará sobre las que ya tenga.  El siguiente jugador podrá, con alguna de sus cartas,
                  ligar con cualquiera de las de la mesa o con la superior del pozo del contrario. Esto
                  último, siempre y cuando ambas sean de diferente número, ya que si la carta superior
                  del pozo del contrario tuviera, por ejemplo, un cuatro, habiendo otro cuatro sobre la
                  mesa, no podrá robarle al jugador, debiéndose limitar a ligar su cuatro con el de la
                  mesa.  Hecha cada jugada, el turno pasa al siguiente jugador, procediendo de la misma
                  manera. Una vez que todos han jugado sus tres cartas, se reparten otras tantas a cada
                  jugador.  Acabado el mazo y jugadas las tres últimas cartas por cada jugador, finaliza
                 la partida, resultando ganador aquél que más cartas haya logrado reunir en su tesoro (pozo).
               \s""");
        panelPrincipal.updateUI();
    }

    public void mostrarMenuPrincipal() {
        aJuego.setText("""
                Casita robada
                Opciones:
                1.Listo
                2.Reglas
                0.Salir
                """);
        actualizarListos(controlador.pedirJugadores());

        esperando = Esperando.menu;

    }

    @Override
    public void mostrarPartida() {
        mostrarSituacionPartida(controlador.pedirJugadores());
    }

    @Override
    public void ganador(ArrayList<IJugador> ganadores) {
        aJuego.setText("""
                Ganador/es:
                """);
        for (IJugador j : ganadores){
            aJuego.append("\n" + j.getNombre());
        }
    }

    @Override
    public void kill() {
        activo = false;
        panelPrincipal.setVisible(false);
        // todo volver en rmi
        System.exit(0);
    }

    @Override
    public void preguntarParejas() {
        aJuego.setText("""
                ¿Desea Jugar en modo parejas?
                0_ No
                1_ Si
                """);

        esperando = Esperando.respuestaParejas;
    }

    @Override
    public void seleccionarEquipos() {
        ArrayList<IJugador> jugadors = controlador.pedirJugadores();
        aJuego.setText("""
                Seleccione quien quiere que sea su equipo:
                """);
        for (IJugador j : jugadors){
            if (!j.compararNombre(controlador.getJugador())){
                aJuego.append("\n" + j.getNombre());
            }
        }
        esperando = Esperando.armarEquipo;
    }

    @Override
    public void actualizarListos(ArrayList<IJugador> jugadores) {
        short l = 0;
        for (IJugador j : jugadores){
            if (j.getReady()){
                l++;
            }
        }
        aJuego.append("\nJugadores listos: " + l + "/" + jugadores.size());
    }

    @Override
    public void addtoTitle(String nombre) {
        setTitle(getTitle() + " ( " + nombre + " )" );
    }

    @Override
    public void asignarTurno() {
        esperando = Esperando.jugada;
        mostrarSituacionPartida(controlador.pedirJugadores());
        aJuego.append("""
                \n
                ES SU TURNO!!
                
                seleccione la accion a realizar:
                0_ ligar carta de la mesa
                1_ ligar carta de un pozo
                2_ soplar
                3_ dejar una carta
                """);
    }

    @Override
    public void actualizarCartas(ArrayList<IJugador> jugadores) {
        mostrarSituacionPartida(jugadores);
    }

    private void mostrarSituacionPartida(ArrayList<IJugador> jugadores) {
        aJuego.setText("");
        IJugador nombre = controlador.getJugador();

        aJuego.append("\nTu mano: ");
        mostrarCartas(controlador.pedirCartasJugador(nombre.getNombre()));

        aJuego.append("\nMesa: ");
        mostrarCartas(controlador.pedirCartasMesa());

        aJuego.append("\nPozos: ");
        mostrarPozos(jugadores);
    }

    private void mostrarPozos(ArrayList<IJugador> jugadores) {
        for (IJugador j : jugadores){
            aJuego.append("\n   Jugador: " + j.getNombre());
            try {
                aJuego.append("\n       Tope: " + j.getTope().toString());
            } catch (Exception e) {
                aJuego.append("\n       Sin cartas");
            }
            aJuego.append("\n       Cantidad de cartas: " + j.getCantidadCartasPozo());
        }
    }

    private void mostrarCartas(ArrayList<Carta> cartas) {
        for (int i = 0 ; i< cartas.size() ; i++){
            aJuego.append("\n   " + i + "_ " + cartas.get(i).toString());
        }
    }

    public Controlador getControlador() {
        return this.controlador;
    }
}
