package Vista.Consola;

import Controlador.ControladorGrafico;
import Interfaces.IJugador;
import Interfaces.iVista;
import Modelo.Exceptions.InvalidInputException;
import Modelo.Main.Jugador;
import Modelo.Main.Partida;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class Consola extends JFrame implements iVista {
    private ControladorGrafico controlador;
    private JPanel panelPrincipal;
    private JTextArea aJuego;
    private JTextField fConsola;
    private JButton bAceptar;
    private boolean activo = true;
    private Esperando esperando;
    private boolean ready = false;

    public Consola(Partida partida) {
        this.controlador = new ControladorGrafico(partida,this);

        setTitle("Casita Robada");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(panelPrincipal);
        this.pack();
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setVisible(activo);

        bAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesar(fConsola.getText());
                fConsola.setText("");
            }
        });

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
                        controlador.ready();
                        ready = !ready;
                        if (ready) {
                            aJuego.append("\nEstas listo");
                        } else {
                            aJuego.append("\nListo Cancelado");
                        }
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
        }
    }

    @Override
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

    @Override
    public void reglas() {
        aJuego.append("""
                
                _Objetivo:
                 El juego consiste en ligar cualquiera de las cartas que se tienen en la mano con otra de igual número que
                 se halle sobre la mesa, o en la parte superior del pozo de cada jugador; en este último caso se le roba todo el pozo.
                 
                _Jugadores:
                 Pueden intervenir entre 2 a 4 Jugadores, en forma individual o por parejas.
                 
                _Inicio del juego:
                 se reparten tres cartas a cada jugador, descubriendo luego cuatro cartas boca arriba sobre la mesa. Cuando cada jugador
                 haya jugado sus tres cartas,se deben repartir otras tres a cada uno.
                 
                _Desarrollo del juego:
                 El juego es comenzado por el mano, quien, si tiene entre sus cartas alguna que liga con las de la
                 mesa, baja la suya reuniéndola con la otra y diciendo qué cartas liga.
                 Si hubiera sobre la mesa dos cartas de igual número, sólo podrá robar una de ellas. Las dos cartas que así
                 reúne las coloca hacia arriba a su lado iniciando así su pozo) y pasa el turno al siguiente; las cartas que en
                 sucesivas manos vaya reuniendo, las ubicará sobre las que ya tenga.  El siguiente jugador podrá, con alguna de sus cartas,
                 ligar con cualquiera de las de la mesa o con la superior del pozo del contrario. Esto último, siempre y cuando ambas sean
                 de diferente número, ya que si la carta superior del pozo del contrario tuviera, por ejemplo, un cuatro, habiendo otro cuatro
                 sobre la mesa, no podrá robarle al jugador, debiéndose limitar a ligar su cuatro con el de la mesa.  Hecha cada jugada, el
                 turno pasa al siguiente jugador, procediendo de la misma manera. Una vez que todos han jugado sus tres cartas, se reparten
                 otras tantas a cada jugador.  Acabado el mazo y jugadas las tres últimas cartas por cada jugador, finaliza
                 la partida, resultando ganador aquél que más cartas haya logrado reunir en su tesoro (pozo).
                """);
        panelPrincipal.updateUI();
    }

    @Override
    public void mostrarMenuPrincipal() {
        aJuego.setText("""
                Casita robada
                Opciones:
                1.Listo
                2.Reglas
                0.Salir
                """);

        esperando = Esperando.menu;

    }

    @Override
    public void mostrarPartida() {
        // todo preguntar que pasa aca porque yo no pondria nada o podria poner la situacion de partida
    }

    @Override
    public void ganador(ArrayList<IJugador> ganadores) {
        aJuego.setText("""
                Ganador/es:
                """);
        for (IJugador j : ganadores){
            aJuego.append("\n" + j.getNombre());
        };
    }

    @Override
    public void kill() {
        activo = false;
        panelPrincipal.setVisible(false);
        // todo volver en rmi
        // System.exit(0);
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
        esperando = Esperando.seleccionarCarta;
        mostrarSituacionPartida(controlador.pedirJugadores());
        aJuego.append("""
                seleccione la accion a realizar:
                0_ ligar carta de la mesa
                1_ ligar carta de un pozo
                2_ soplar
                """); // todo aca quede :p
    }

    @Override
    public void actualizarCartas(ArrayList<IJugador> jugadores) {
        mostrarSituacionPartida(jugadores);
    }

    private void mostrarSituacionPartida(ArrayList<IJugador> jugadores) {

    }
}
