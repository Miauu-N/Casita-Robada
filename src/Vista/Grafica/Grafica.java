package Vista.Grafica;

import javax.swing.*;

public class Grafica {
    JFrame vent;
    public Grafica(){
        vent = new JFrame("Casita Robada");
        vent.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        vent.setVisible(true);
        VentanaPrincipal menu = new VentanaPrincipal(this);
        vent.setContentPane(menu.getPanel());
        vent.setSize(1280,720);
    }

    public void reglas(){
        vent.setVisible(false);
        vent = new JFrame("Casita Robada");
        vent.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        vent.setVisible(true);
        Reglas menu = new Reglas(this);
        vent.setContentPane(menu.getPanel());
        vent.setSize(1280,720);
    }

    public void mostrarMenuPrincipal() {
        vent.setVisible(false);
        vent = new JFrame("Casita Robada");
        vent.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        vent.setVisible(true);
        VentanaPrincipal menu = new VentanaPrincipal(this);
        vent.setContentPane(menu.getPanel());
        vent.setSize(1280,720);
    }
}
