package Interfaces;

import java.util.ArrayList;

public interface iVista {
    void menuNombre();

    void menuNombre(boolean error);

    void reglas();

    void mostrarMenuPrincipal();

    void mostrarPartida();

    void ganador(ArrayList<IJugador> ganadores);

    void kill();

    void preguntarParejas();

    void seleccionarEquipos();

    void actualizarListos(ArrayList<IJugador> jugadores);

    void addtoTitle(String nombre);

    void asignarTurno();

    void actualizarCartas(ArrayList<IJugador> jugadores);
}
