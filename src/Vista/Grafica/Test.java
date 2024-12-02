package Vista.Grafica;

import Interfaces.iVista;
import Modelo.Main.Partida;
import Vista.Consola.Consola;

import javax.swing.*;

public class Test {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Partida partida = new Partida();
            iVista vista1 = new Consola(partida);
            iVista vista2 = new Consola(partida);
//            ControladorGrafico controladorGrafico3 = new ControladorGrafico(partida);
//            ControladorGrafico controladorGrafico4 = new ControladorGrafico(partida);
        });
    }
}
