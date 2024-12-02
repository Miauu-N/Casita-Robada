package Vista.Grafica;

import Interfaces.IJugador;
import Interfaces.IVentana;
import Modelo.Main.Jugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ElegirEquipos implements IVentana {
    private JPanel panelPrincipal;
    private JPanel pJugadores;

    public ElegirEquipos(Grafica grafica,ArrayList<IJugador> jugadors) {
        for (IJugador jugador : jugadors){
            if (!grafica.pedirJugador().compararNombre(jugador)){
                JButton bot = new JButton(jugador.getNombre());
                bot.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        grafica.elegirEquipo(jugador);
                    }
                });
                pJugadores.add(bot);
            }
        }
    }


    private void createUIComponents() {
        PanelConFondo panel = new PanelConFondo();
        panel.setImagen("/Pizarra.jpg");
        panelPrincipal = panel;
    }

    @Override
    public Container getPanel() {
        return this.panelPrincipal;
    }

    @Override
    public void updateUI() {
        panelPrincipal.updateUI();
    }
}
