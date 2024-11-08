package Vista.Grafica;

import Controlador.ControladorGrafico;
import Interfaces.IVentana;

import javax.swing.*;
import java.awt.*;

public class Grafica {
    JFrame vent;
    ControladorGrafico controlador;
    boolean activo = true;

    public Grafica(ControladorGrafico controladorGrafico){
        this.controlador = controladorGrafico;
        IVentana menu = new Nombre(controlador,this);
        crearVentana(menu);
    }

    private void crearVentana(IVentana panel){
        if (vent != null){
            vent.setVisible(false);
        }
        this.vent = new JFrame("Casita Robada");
        this.vent.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.vent.setContentPane(panel.getPanel());
        this.vent.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.vent.setVisible(activo);
    }

    public void reglas(){
        IVentana menu = new Reglas(this);
        crearVentana(menu);
    }

    public void mostrarMenuPrincipal() {
        IVentana menu = new VentanaPrincipal(controlador,this);
        crearVentana(menu);
    }

    public void kill() {
        System.out.println("Cliente cerrado");
        activo = false; // TODO Preguntar si esta bien
    }

    public void preguntarParejas(){
        IVentana menu = new PreguntarParejas(controlador,this);
        crearVentana(menu);
    }
}
