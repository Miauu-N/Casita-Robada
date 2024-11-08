package Vista.Grafica;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PanelConFondo extends JPanel {
    private Image imagen;

    @Override
    public void paint(Graphics g)
    {
        g.drawImage(imagen,0, 0, getWidth(), getHeight(),this);

        setOpaque(false);

        super.paint(g);
    }

    public void setImagen(String url) {
        imagen = new ImageIcon(Objects.requireNonNull(getClass().getResource(url))).getImage();
    }
}
