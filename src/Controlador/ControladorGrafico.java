package Controlador;

import Interfaces.IJugador;
import Interfaces.Observer;
import Interfaces.iVista;
import Modelo.Cartas.Carta;
import Modelo.Events.EventType;
import Modelo.Exceptions.InvalidInputException;
import Modelo.Events.GameEvent;
import Modelo.Exceptions.InvalidNameException;
import Modelo.Main.Partida;
import Vista.Grafica.Grafica;

import java.util.ArrayList;

public class ControladorGrafico implements Observer {

    private iVista grafica;

    private Partida partida;


    private IJugador jugador;

    public IJugador getJugador() {
        return this.jugador;
    }
    public ControladorGrafico(Partida partida,iVista grafica) {
        this.grafica =grafica;
        this.partida = partida;
        this.partida.addObserver(this);
    }

    public boolean addJugador(String nombre) {
        try {
            this.jugador = partida.addJugador(nombre);
            this.grafica.addtoTitle(jugador.getNombre());
            return true;
        } catch (InvalidInputException e) {
            partida.removeObserver(this);
            grafica.kill();
            return false;
        } catch (InvalidNameException e) {
            grafica.menuNombre(true);
            return false;
        }
    }

    @Override
    public void update(GameEvent e) {
        switch (e.getTipo()){

            case EventType.todosListos -> {
                if ( ((IJugador) e.getContenido()).compararNombre(jugador) ) {
                    if (partida.canParejas()){
                        grafica.preguntarParejas();
                    }
                    else {
                        partida.modoParejas(false);
                    }
                }
            }
            case actualizacionDeCartas -> {
                ArrayList<IJugador> jugadores = (ArrayList<IJugador>) e.getContenido();
                grafica.actualizarCartas(jugadores);
            }

            case empezoElJuego -> {
                IJugador turno = (IJugador) e.getContenido();
                grafica.mostrarPartida();
                if (turno.compararNombre(jugador)) {
                    grafica.asignarTurno();
                }
            }

            case AsignarTurno -> {
                IJugador turno = (IJugador) e.getContenido();
                if (jugador.compararNombre(turno)) {
                    grafica.asignarTurno();
                }
            }

            case jugadorListo -> {
                ArrayList<IJugador> jugadores = (ArrayList<IJugador>) e.getContenido();
                grafica.actualizarListos(jugadores);
            }

            case seleccionDeEquipos -> {
                if ( ((IJugador) e.getContenido()).compararNombre(jugador) ) {
                    grafica.seleccionarEquipos();
                }
            }

            case ganador -> {
                grafica.ganador((ArrayList<IJugador>) e.getContenido());
            }


//            case null, default -> {
//                System.out.println("Evento invalido");
//            }
        }
    }

    public void ready() {
        partida.ready(jugador);
    }

    public void responderParejas(boolean b) {
        partida.modoParejas(b);
    }

    public ArrayList<IJugador> pedirJugadores() {
        return partida.getIJugadores();
    }

    public ArrayList<Carta> pedirCartasMesa() {
        return  partida.getCartasMesa();
    }

    public ArrayList<Carta> pedirCartasJugador(String nombre) {
        return partida.getCartasJugador(nombre);
    }

    public void robarPozo(String jugador, int selected) {
        partida.ligarPozo(selected, jugador);
    }

    public void agarrarCartaMesa(int mesa, int mano) {
        partida.ligarCarta(mesa,mano);
    }

    public void soplar(int c1, int c2) {
        partida.soplar(c1,c2);
    }

    public void dejarCarta(int selected) {
        partida.dejarCarta(selected);
    }

    public void elegirEquipo(String jugador) throws InvalidInputException{
        partida.armarEquipos(jugador);
    }
}
