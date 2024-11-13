package Vista.Grafica;

import Interfaces.IJugador;
import Interfaces.IVentana;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Partida implements IVentana {
    private final Grafica grafica;
    private JPanel panel1;
    private JPanel pMesa;
    private JPanel pJugador;
    private ArrayList<pRival> pRivales;
    private JPanel pRival1;
    private JPanel pRival2;
    private JPanel pRival3;
    private JButton lPozo;
    private JPanel pMano;
    private Image dorso;
    private IJugador jugador;

    public Partida(Grafica grafica) {

        //TODO: guardar el dorso


        this.grafica = grafica;

        ArrayList<IJugador> jugadors = grafica.pedirListos();

        jugador = grafica.pedirJugador();
        int i = 0;
        for (IJugador j : jugadors){
            if (!j.compararNombre(jugador)){
                pRival panel = pRivales.get(i++);
                panel.setJugador(j);
                panel.add(new JLabel(j.getNombre()));
            }
        }

        botonCarta(lPozo, "/grande.png");
    }

    private void botonCarta(JButton boton, String string) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(string)));
        Image image = icon.getImage().getScaledInstance(91,127,Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(image));
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.updateUI();
    }

    @Override
    public Container getPanel() {
        return panel1;
    }

    private void createUIComponents() {
        pRival pRival1 = new pRival();
        pRival pRival2 = new pRival();
        pRival pRival3 = new pRival();
        pRival2.rotar();
        pRival3.rotar();

        pRivales = new ArrayList<>();
        pRivales.add(pRival1);
        pRivales.add(pRival2);
        pRivales.add(pRival3);
        this.pRival1 = pRival1;
        this.pRival2 = pRival2;
        this.pRival3 = pRival3;
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
        transform.rotate(Math.toRadians(angulo), (double) bufferedImage.getWidth() / 2, (double) bufferedImage.getHeight() / 2);

        // Aplicar la rotación
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        bufferedImage = op.filter(bufferedImage, null);

        return bufferedImage;
    }
}
