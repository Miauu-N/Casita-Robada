package Vista.Grafica;

import Controlador.ControladorGrafico;
import Interfaces.IVentana;
import Modelo.Main.Jugador;

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
    private boolean listo = false;


    public VentanaPrincipal(ControladorGrafico controlador, Grafica grafica) {
        this.grafica = grafica;
        Jugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.ready();
                if (!listo){
                    listo = true;
                    Jugar.setBackground(Color.RED);
                    Jugar.setText("Cancelar");
                }
                else {
                    listo = false;
                    Jugar.setBackground(Color.BLUE);
                    Jugar.setText("Jugar");
                }
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
