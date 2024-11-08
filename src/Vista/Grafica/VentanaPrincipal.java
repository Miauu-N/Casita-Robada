package Vista.Grafica;

import Controlador.ControladorGrafico;
import Interfaces.IVentana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal implements IVentana {
    private Grafica grafica;
    private JPanel pantallaMenu;
    private JPanel north;
    private JPanel titulo;
    private JPanel botones;
    private JButton Jugar;
    private JButton botonReglas;
    private JButton salir;


    public VentanaPrincipal(ControladorGrafico controlador, Grafica grafica) {
        this.grafica = grafica;
        Jugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.ready();
                Jugar.setEnabled(false); // TODO Poner algun indicador sobre que ya estas listo
            }
        });
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        botonReglas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grafica.reglas();
            }
        });
    }

    public Container getPanel() {
        return pantallaMenu;
    }

    private void createUIComponents() {
        PanelConFondo panel = new PanelConFondo();
        panel.setImagen("/Designer.jpeg");
        pantallaMenu = panel;
    }
}
