package Interfaces;

import Modelo.Cartas.Carta;

import java.io.Serializable;

public interface IJugador extends Serializable {

    boolean compararNombre(IJugador p2);

    boolean compararNombre(String p2);

    String getNombre();

    int getCantCartasEnMano();

    Carta getTope();

    Boolean getReady();

    int getCantidadCartasPozo();

    int getPuntos();
}
