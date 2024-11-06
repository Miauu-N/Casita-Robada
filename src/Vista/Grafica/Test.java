package Vista.Grafica;

import Controlador.ControladorGrafico;
import Modelo.Main.Partida;

public class Test {

    public static void main(String[] args) {
        Partida partida = new Partida();
        ControladorGrafico controladorGrafico1 = new ControladorGrafico(partida);
        ControladorGrafico controladorGrafico2 = new ControladorGrafico(partida);
        ControladorGrafico controladorGrafico3 = new ControladorGrafico(partida);
        ControladorGrafico controladorGrafico4 = new ControladorGrafico(partida);
        ControladorGrafico controladorGrafico5 = new ControladorGrafico(partida);
    }
}
