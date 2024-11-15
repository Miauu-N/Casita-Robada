package Vista.Grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reglas implements Interfaces.IVentana {
    private Grafica grafica;
    private JPanel panel1;
    private JPanel Top;
    private JPanel Mid;
    private JLabel reglas;
    private JLabel reglasEscritas;
    private JButton botonVolver;

    public Reglas(Grafica grafica) {
        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grafica.mostrarMenuPrincipal();
            }
        });
    }

    @Override
    public Container getPanel() {
        return panel1;
    }

    @Override
    public void updateUI() {
        panel1.updateUI();
    }

    private void createUIComponents() {
        PanelConFondo panel = new PanelConFondo();
        panel.setImagen("/Pizarra.jpg");
        panel1 = panel;
    }
}
