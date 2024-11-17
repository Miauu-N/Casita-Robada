package Vista.Grafica;

import Interfaces.IJugador;

import javax.swing.*;

import java.awt.*;

import static Modelo.Main.Utils.*;

public class pRival extends JPanel {
    private IJugador jugador;
    private boolean rotar = false;
    private JButton pozo;

    public boolean isUsado() {
        return this.usado;
    }

    private boolean usado = false;

    public IJugador getJugador() {
        return this.jugador;
    }

    public String getNombre(){
        return this.jugador.getNombre();
    }

    /**
     * Crea tdo lo necesario en el panel con respecto al jugador al iniciar la partida y devuelve el boton usado como pozo para el rival
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
        this.pozo = pozo;
        return pozo;
    }

    public void rotar() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.rotar = true;
    }

    public void actualizar(IJugador j) {
        ImageIcon defaultIcon = new ImageIcon(cartaToPath(j.getTope()));
        defaultIcon.setImage(defaultIcon.getImage().getScaledInstance(91,127, Image.SCALE_SMOOTH));
        pozo.setIcon(defaultIcon);
        updateUI();
    }
}
