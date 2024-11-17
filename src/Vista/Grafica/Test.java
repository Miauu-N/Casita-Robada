package Vista.Grafica;

import Controlador.ControladorGrafico;
import Modelo.Main.Partida;

import javax.swing.*;

public class Test {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Partida partida = new Partida();
            ControladorGrafico controladorGrafico1 = new ControladorGrafico(partida);
            ControladorGrafico controladorGrafico2 = new ControladorGrafico(partida);
        });
    }
}
