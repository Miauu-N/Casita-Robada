package Interfaces;

import Modelo.Cartas.Carta;
import Modelo.Main.Jugador;

import java.io.Serializable;

public interface IJugador extends Serializable {

    public boolean compararNombre(IJugador p2);

    public String getNombre();

    public int getCantCartasEnMano();

    public Carta getTope();

    public Boolean getReady();

    public int getCantidadCartasPozo();

}
