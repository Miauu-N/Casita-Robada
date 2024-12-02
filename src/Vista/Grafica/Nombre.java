package Vista.Grafica;

import Controlador.ControladorGrafico;
import Interfaces.IVentana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Nombre implements IVentana {
    private ControladorGrafico controlador;
    private JPanel panelPrincipal;
    private JLabel lIntroducir;
    private JButton bAceptar;
    private JPanel pBoton;
    private JPanel pNombreAceptar;
    private JTextField textField1;
    private JLabel lError;

    public Nombre(ControladorGrafico controladorGrafico, Grafica grafica, boolean error) {
        this.controlador = controladorGrafico;
        bAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textField1.getText();

                if (!name.isEmpty()) {


                    if (controladorGrafico.addJugador(name)) {
                        grafica.mostrarMenuPrincipal();
                    }
                }

            }
        });
        lError.setVisible(error);
    }

    public Container getPanel() {
        return panelPrincipal;
    }

    @Override
    public void updateUI() {
        panelPrincipal.updateUI();
    }

    private void createUIComponents() {
        PanelConFondo panel = new PanelConFondo();
        panel.setImagen("/Designer.jpeg");
        panelPrincipal = panel;
    }
}
