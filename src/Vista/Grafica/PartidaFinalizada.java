package Vista.Grafica;

import Interfaces.IJugador;
import Interfaces.IVentana;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PartidaFinalizada implements IVentana {
    private JPanel panelPrincipal;
    private Grafica grafica;

    public PartidaFinalizada(Grafica grafica, boolean gano, ArrayList<IJugador> ganadores) {
        if (gano){
            panelPrincipal.setBackground(new Color(1, 92, 1));
        }
        else {
            panelPrincipal.setBackground(new Color(96, 0, 0));
        }
        StringBuilder resultado = new StringBuilder("El/Los ganador/es fueron: ");
        for (IJugador j : ganadores){
            resultado.append("\n").append(j.getNombre());
        }
        JLabel ganador = new JLabel(resultado.toString());
        ganador.setFont(new Font("ArialBlack",Font.BOLD,18));
        ganador.setOpaque(false);
        ganador.setForeground(Color.WHITE);
        panelPrincipal.add(ganador);

        JTextArea ar = new JTextArea(grafica.getRanking());
        ar.setOpaque(false);
        panelPrincipal.add(ar);

        panelPrincipal.updateUI();
    }

    @Override
    public Container getPanel() {
        return panelPrincipal;
    }

    @Override
    public void updateUI() {
        panelPrincipal.updateUI();
    }


    private void createUIComponents() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
    }
}
