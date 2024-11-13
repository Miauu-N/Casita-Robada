package Vista.Grafica;

import Controlador.ControladorGrafico;
import Interfaces.IJugador;
import Interfaces.IVentana;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Grafica {
    private JFrame vent;
    private ControladorGrafico controlador;
    private boolean activo = true;
    private VentanaPrincipal menu = null;

    public Grafica(ControladorGrafico controladorGrafico){
        this.controlador = controladorGrafico;
        IVentana menu = new Nombre(controlador,this);
        crearVentana(menu);
    }

    private void crearVentana(IVentana panel){
        if (vent != null){
            vent.setVisible(false);
            vent.setContentPane(panel.getPanel());
            vent.setVisible(true);
        }
        else {
            this.vent = new JFrame("Casita Robada");
            this.vent.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.vent.setContentPane(panel.getPanel());
            this.vent.pack();
            this.vent.setExtendedState(Frame.MAXIMIZED_BOTH);
            this.vent.setVisible(activo);
        }

    }

    public void reglas(){
        IVentana menu = new Reglas(this);
        crearVentana(menu);
    }

    public void mostrarMenuPrincipal() {
        VentanaPrincipal contenido;
        if (this.menu == null) {
            contenido = new VentanaPrincipal(controlador,this);
            this.menu = contenido;
        }
        else {
            contenido = this.menu;
        }
        crearVentana(contenido);
    }

    public void mostrarPartida(){
        IVentana partida = new Partida(this);
        crearVentana(partida);
    }

    public void kill() {
        System.out.println("Cliente cerrado");
        activo = false; // TODO Preguntar si esta bien
    }

    public void preguntarParejas(){
        IVentana menu = new PreguntarParejas(controlador,this, SiONo.preguntarParejas);
        crearVentana(menu);
    }

    public void actualizarListos(ArrayList<IJugador> jugadores) {
        if (menu != null){
            menu.actualizarListos(jugadores);
        }
    }

    public void addtoTitle(String nombre) {
        String titulo = vent.getTitle() + " ( " + nombre + " )";
        vent.setTitle(titulo);
    }

    public ArrayList<IJugador> pedirListos() {
        return controlador.pedirListos();
    }

}
