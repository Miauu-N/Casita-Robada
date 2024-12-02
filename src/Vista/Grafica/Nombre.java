package Vista.Grafica;

import Controlador.Controlador;
import Interfaces.IVentana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Nombre implements IVentana {
    private JPanel panelPrincipal;
    private JLabel lIntroducir;
    private JButton bAceptar;
    private JPanel pBoton;
    private JPanel pNombreAceptar;
    private JTextField textField1;
    private JLabel lError;

    public Nombre(Controlador controlador, Grafica grafica, boolean error) {
        bAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textField1.getText();

                if (!name.isEmpty()) {


                    if (controlador.addJugador(name)) {
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
