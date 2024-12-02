package Vista.Grafica;

import Controlador.Controlador;
import Interfaces.IVentana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreguntarParejas implements IVentana {
    private final Controlador controlador;
    private final Grafica grafica;
    private JPanel panelPrincipal;
    private JButton siButton;
    private JButton noButton;
    private JLabel lPregunta;

    public PreguntarParejas(Controlador controlador, Grafica grafica, SiONo tipo) {
        if (tipo == SiONo.preguntarContinuar){
            lPregunta.setText("Desea Continuar el juego?");
        }
        this.controlador = controlador;
        this.grafica = grafica;
        siButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desactivarBotones();
                if (tipo == SiONo.preguntarParejas) {
                    controlador.responderParejas(true);
                }
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desactivarBotones();
                if (tipo == SiONo.preguntarParejas) {
                    controlador.responderParejas(false);
                }
            }
        });
    }

    private void desactivarBotones() {
        noButton.setEnabled(false);
        siButton.setEnabled(false);
    }

    private void createUIComponents() {
        PanelConFondo panel = new PanelConFondo();
        panel.setImagen("/Pizarra.jpg");
        panelPrincipal = panel;
    }

    @Override
    public Container getPanel() {
        return panelPrincipal;
    }

    @Override
    public void updateUI() {
        panelPrincipal.updateUI();
    }
}
