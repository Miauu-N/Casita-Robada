package Vista.Grafica;

import Modelo.Cartas.Carta;
import Modelo.Exceptions.NoCardsException;
import Modelo.Main.Jugador;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Utils {

    // TODO: PASAR A LA VISTA

    static public void mostrarTopes(ArrayList<Jugador> jugadores){
        for (Jugador j : jugadores){
            System.out.println("Jugador: " + j.getNombre());
            try {
                System.out.println("Tope: " + j.getPozo().getTope());
            } catch (NoCardsException e) {
                System.out.println("Sin cartas");
            }
            System.out.println("Cantidad de cartas: " + j.getPozo().getCantidad());
            System.out.println();
        }
    }

    public static JButton botonCarta(JButton boton, String string) {
        ImageIcon icon = new ImageIcon(string);
        Image image = icon.getImage().getScaledInstance(91,127,Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(image));
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.updateUI();
        return boton;
    }
    public static JButton botonCarta(JButton boton, String string,boolean x) {
        ImageIcon icon = new ImageIcon(string);
        Image image = icon.getImage().getScaledInstance(91,127,Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(image));
        boton.setDisabledIcon(new ImageIcon(image));
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.updateUI();
        return boton;
    }
    public static JButton botonCartaRotada(JButton boton, String string) {
        ImageIcon icon = new ImageIcon(string);
        Image image = icon.getImage().getScaledInstance(91,127,Image.SCALE_SMOOTH);
        Image imageRotada = rotarImagen(new ImageIcon(image),90);
        boton.setIcon(new ImageIcon(imageRotada));
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.updateUI();
        return boton;
    }

    public static String cartaToPath(Carta c){
        try {
            return "images/cartas/" + c.toString() + ".png";
        } catch (Exception e) {
            return "images/pozo_Vacio.png";
        }
    }
    public static String cartaToPath(String c){
        return "images/cartas/" + c + ".png";
    }

    public static Image rotarImagen(ImageIcon icono, int angulo) {
        int w = icono.getIconWidth();
        int h = icono.getIconHeight();

        // Crear un BufferedImage más grande para evitar que se corte
        BufferedImage bufferedImage = new BufferedImage(h, w, BufferedImage.TYPE_INT_ARGB);

        // Crear la transformación de rotación
        AffineTransform transform = new AffineTransform();
        transform.translate(h / 2.0, w / 2.0);
        transform.rotate(Math.toRadians(angulo));
        transform.translate(-w / 2.0, -h / 2.0);

        // Aplicar la rotación
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        bufferedImage = op.filter(toBufferedImage(icono.getImage()), bufferedImage);

        return bufferedImage;
    }

    // Método auxiliar para convertir Image a BufferedImage
    private static BufferedImage toBufferedImage(Image img) {
        BufferedImage bufferedImage = new BufferedImage(
                img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bufferedImage;
    }
}
