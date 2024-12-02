package Vista.Grafica;

import Interfaces.IJugador;

import javax.swing.*;

import java.awt.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;

import static Vista.Grafica.Utils.*;

public class pRival extends JPanel {
    private IJugador jugador;
    private boolean rotar = false;
    private JButton pozo;
    private final JButton[] dorsos = new JButton[3];

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
        JPanel pane = new JPanel(new FlowLayout());
        JLabel lnombre = new JLabel(jugador.getNombre());
        lnombre.setForeground(Color.WHITE);
        pane.add(lnombre);
        pane.setOpaque(false);
        add(pane);
        if (!rotar) {
            pozo = botonCarta(new JButton(), jugador.getTope() != null ? jugador.getTope().toString() + ".png" : "images/pozo_Vacio.png");
        }
        else {
            pozo = botonCartaRotada(new JButton(), jugador.getTope() != null ? jugador.getTope().toString() + ".png" : "images/pozo_Vacio.png");
        }
        add(pozo);
        for (int i = 0 ; i < 3; i++) {
            JButton dorso;
            if (!rotar){
                dorso = botonCarta(new JButton(), "images/cartas/dorso.jpg");
            }
            else{
                dorso = botonCartaRotada(new JButton(), "images/cartas/dorso.jpg");
            }
            add(dorso);
            dorsos[i] = dorso;
        }
        this.pozo = pozo;
        return pozo;
    }

    public void rotar() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.rotar = true;
    }

    public void actualizar(IJugador j) {
        activarDorsos(j.getCantCartasEnMano());
        ImageIcon imageIcon = new ImageIcon(cartaToPath(j.getTope()));
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(91,127, Image.SCALE_SMOOTH));
        if (rotar){
            Image ima = rotarImagen(imageIcon,90);
            imageIcon.setImage(ima);
        }
        pozo.setIcon(imageIcon);
        updateUI();
    }

    public void activarDorsos(int i){
        for (JButton d : dorsos){
            if (i != 0){
                d.setVisible(true);
                i--;
            }
            else {
                d.setVisible(false);
            }
        }
    }
}
