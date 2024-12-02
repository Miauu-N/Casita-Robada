package Interfaces;

import Controlador.Controlador;

import java.util.ArrayList;

public interface iVista {

    void menuNombre(boolean error);

    void mostrarPartida();

    void ganador(ArrayList<IJugador> ganadores);

    void kill();

    void preguntarParejas();

    void seleccionarEquipos();

    void actualizarListos(ArrayList<IJugador> jugadores);

    void addtoTitle(String nombre);

    void asignarTurno();

    void actualizarCartas(ArrayList<IJugador> jugadores);

    Controlador getControlador();
}
