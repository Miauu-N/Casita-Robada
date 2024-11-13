package Vista.Grafica;

import Interfaces.IJugador;
import Modelo.Main.Jugador;

import javax.swing.*;

public class pRival extends JPanel {
    private IJugador jugador;
    private boolean rotar = false;

    public boolean isUsado() {
        return this.usado;
    }

    private boolean usado;

    public IJugador getJugador() {
        return this.jugador;
    }

    public String getNombre(){
        return this.jugador.getNombre();
    }

    public void setJugador(IJugador jugador) {
        this.jugador = jugador;
        this.usado = true;
        add(new JButton(
                jugador.getTope() != null ? jugador.getTope().toString() : "Pozo Vacio"
        ));
    }

    public void rotar() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.rotar = true;
    }
}
