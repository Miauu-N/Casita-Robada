package Vista.Grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal {
    private Grafica grafica;
    private JPanel pantallaMenu;
    private JPanel north;
    private JPanel titulo;
    private JPanel botones;
    private JButton Jugar;
    private JPanel CentrarCasita;
    private JLabel casitaLabel;
    private JButton botonReglas;
    private JButton salir;


    public VentanaPrincipal(Grafica grafica) {
        this.grafica = grafica;
        Jugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola Mundo");
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
}
