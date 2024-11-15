package Vista.Grafica;

import javax.swing.*;
import java.awt.*;

public class LabelVertical extends JLabel {
    public LabelVertical(String text) {
        super(text);
        setForeground(new Color(201,217,5));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(90), (double) getWidth() / 2, (double) getHeight() / 2); // Rotación de 90 grados
        g2d.drawString(getText(), 0, getHeight() / 2 + getFont().getSize() / 2); // Dibujar el texto centrado
    }

    @Override
    public Dimension getPreferredSize() {
        // Ajustar el tamaño del JLabel para que se acomode al texto rotado
        FontMetrics fm = getFontMetrics(getFont());
        return new Dimension(fm.getHeight(), fm.stringWidth(getText()));
    }
}