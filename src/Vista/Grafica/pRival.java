package Vista.Grafica;

import Interfaces.IJugador;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Modelo.Main.Utils.botonCarta;
import static Modelo.Main.Utils.botonCartaRotada;

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

    /**
     * Crea todo lo necesario en el panel con respecto al jugador al iniciar la partida y devuelve el boton usado como pozo para el rival
     */
    public JButton setJugador(IJugador jugador) {
        this.jugador = jugador;
        this.usado = true;
        JButton pozo;
        add(new JLabel(jugador.getNombre()));
        if (!rotar) {
            pozo = botonCarta(new JButton(), jugador.getTope() != null ? jugador.getTope().toString() + ".png" : "images/pozo_Vacio.png");
        }
        else {
            pozo = botonCartaRotada(new JButton(), jugador.getTope() != null ? jugador.getTope().toString() + ".png" : "images/pozo_Vacio.png");
        }
        add(pozo);
        for (int i = 0 ; i < jugador.getCantCartasEnMano(); i++) {
            if (!rotar){
                add(botonCarta(new JButton(),"images/cartas/dorso.jpg"));
            }
            else{
                add(botonCartaRotada(new JButton(),"images/cartas/dorso.jpg"));
            }
        }
        return pozo;
    }

    public void rotar() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.rotar = true;
    }
}
