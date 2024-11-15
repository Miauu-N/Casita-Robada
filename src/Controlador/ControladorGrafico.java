package Controlador;

import Interfaces.IJugador;
import Interfaces.Observer;
import Modelo.Cartas.Carta;
import Modelo.Events.EventType;
import Modelo.Exceptions.InvalidInputException;
import Modelo.Events.GameEvent;
import Modelo.Main.Partida;
import Vista.Grafica.Grafica;

import java.util.ArrayList;

public class ControladorGrafico implements Observer {

    private Grafica grafica;

    private Partida partida;


    private IJugador jugador;

    public IJugador getJugador() {
        return this.jugador;
    }
    public ControladorGrafico(Partida partida) {
        grafica = new Grafica(this);
        this.partida = partida;
        this.partida.addObserver(this);
    }

    public void addJugador(String nombre) {
        try {
            this.jugador = partida.addJugador(nombre);
            this.grafica.addtoTitle(jugador.getNombre());
        } catch (InvalidInputException e) {
            partida.removeObserver(this);
            grafica.kill();
        }
    }

    @Override
    public void update(GameEvent e) {
        switch (e.getTipo()){

            case EventType.preguntarModoParejas -> {
                grafica.preguntarParejas();
            }
            case updateCartas -> {}

            case empezoElJuego -> {
                IJugador turno = (IJugador) e.getContenido();
                grafica.mostrarPartida();
                if (turno.compararNombre(jugador)) {
                    grafica.asignarTurno();
                }
            }

            case preguntarNuevaRonda -> {}

            case jugadorListo -> {
                ArrayList<IJugador> jugadores = (ArrayList<IJugador>) e.getContenido();
                grafica.actualizarListos(jugadores);
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
        partida.respuestaParejas(b);
    }

    public ArrayList<IJugador> pedirListos() {
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
}
