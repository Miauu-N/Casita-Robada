package Interfaces;

import Modelo.Cartas.Carta;
import Modelo.Main.Jugador;

public interface IJugador {

    public boolean compararNombre(Jugador p2);

    public String getNombre();

    public int getCantCartasEnMano();

    public Carta getTope();

    public Boolean getReady();


}
