package Interfaces;

import Modelo.Cartas.Carta;
import Modelo.Main.Jugador;

import java.io.Serializable;

public interface IJugador extends Serializable {

    boolean compararNombre(IJugador p2);

    String getNombre();

    int getCantCartasEnMano();

    Carta getTope();

    Boolean getReady();

    int getCantidadCartasPozo();

}
