package Controlador;

import Interfaces.IJugador;
import Interfaces.Observer;
import Modelo.Exceptions.InvalidInputException;
import Modelo.Events.GameEvent;
import Modelo.Main.Partida;
import Vista.Grafica.Grafica;

public class ControladorGrafico implements Observer {

    private Grafica grafica;

    private Partida partida;

    private IJugador jugador;

    public ControladorGrafico(Partida partida) {
        grafica = new Grafica(this);
        this.partida = partida;
        this.partida.addObserver(this);
    }

    public void addJugador(String nombre) {
        try {
            this.jugador = partida.addJugador(nombre);
        } catch (InvalidInputException e) {
            partida.removeObserver(this);
            grafica.kill();
        }
    }

    @Override
    public void update(GameEvent e) {

    }

    public void ready() {
        partida.ready(jugador);
    }
}
