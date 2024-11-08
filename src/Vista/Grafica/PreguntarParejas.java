package Vista.Grafica;

import Controlador.ControladorGrafico;
import Interfaces.IVentana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreguntarParejas implements IVentana {
    private final ControladorGrafico controlador;
    private final Grafica grafica;
    private JPanel panelPrincipal;
    private JButton siButton;
    private JButton noButton;

    public PreguntarParejas(ControladorGrafico controlador, Grafica grafica) {
        this.controlador = controlador;
        this.grafica = grafica;
        siButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desactivarBotones();
                controlador.responderParejas(true);
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desactivarBotones();
                controlador.responderParejas(false);
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
}
