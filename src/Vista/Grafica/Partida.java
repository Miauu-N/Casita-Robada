package Vista.Grafica;

import Interfaces.IVentana;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Partida implements IVentana {
    private Grafica grafica;
    private JPanel panel1;
    private JPanel pMesa;
    private JPanel pJugador;
    private JPanel pRival1;
    private JPanel pRival2;
    private JPanel pRival3;
    private JButton lPozo;
    private JPanel pMano;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private Image dorso;

    public Partida(Grafica grafica) {

        //TODO: guardar el dorso

        this.grafica = grafica;

        botonCarta(lPozo, "/grande.png");
        botonCarta(button1,"/grande.png");
        botonCarta(button2,"/chica.png");
        botonCarta(button3,"/grande.png");
    }

    private void botonCarta(JButton boton, String string) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(string)));
        Image image = icon.getImage().getScaledInstance(91,127,Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(image));
        boton.setBorderPainted(false);   // Quitar el borde
        boton.setContentAreaFilled(false); // Quitar el fondo
        boton.setFocusPainted(false);    // Quitar el enfoque al hacer clic
        boton.updateUI();
    }

    @Override
    public Container getPanel() {
        return panel1;
    }

    private Image rotarImagen(ImageIcon icono, int angulo) { // by ChatGPT :(
        BufferedImage bufferedImage = new BufferedImage(
                icono.getIconWidth(),
                icono.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        // Dibujar la imagen original en un BufferedImage
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(icono.getImage(), 0, 0, null);
        g2d.dispose();

        // Crear la transformación de rotación
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angulo), bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);

        // Aplicar la rotación
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        bufferedImage = op.filter(bufferedImage, null);

        return bufferedImage;
    }
}
