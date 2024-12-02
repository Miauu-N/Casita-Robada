package Vista.Grafica;

import Controlador.Controlador;
import Interfaces.IJugador;
import Interfaces.IVentana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VentanaPrincipal implements IVentana {
    private JPanel pantallaMenu;
    private JPanel north;
    private JPanel titulo;
    private JPanel botones;
    private JButton Jugar;
    private JButton botonReglas;
    private JButton salir;
    private JPanel pJugadoresListos;
    private boolean listo = false;


    public VentanaPrincipal(Controlador controlador, Grafica grafica) {
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

        GridBagLayout ly = (GridBagLayout) pJugadoresListos.getLayout();

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
        actualizarListos(grafica.pedirListos());
    }

    public Container getPanel() {
        return pantallaMenu;
    }

    @Override
    public void updateUI() {
        pantallaMenu.updateUI();
    }

    private void createUIComponents() {
        PanelConFondo panel = new PanelConFondo();
        panel.setImagen("/Designer.jpeg");
        pantallaMenu = panel;

    }

    public void actualizarListos(ArrayList<IJugador> jugadores) {
        Component lJugadores = pJugadoresListos.getComponent(0);
        pJugadoresListos.removeAll();
        pJugadoresListos.setLayout(new BoxLayout(pJugadoresListos, BoxLayout.Y_AXIS));
        pJugadoresListos.add(lJugadores);
        for (IJugador j : jugadores){
            boolean listo = j.getReady();
            String nombre = j.getNombre();
            JLabel label = new JLabel(nombre);
            label.setForeground(listo ? Color.green : Color.white);
            pJugadoresListos.add(label);
        }
        pJugadoresListos.updateUI();
    }
}
