package Vista.Grafica;

import Controlador.ControladorGrafico;
import Interfaces.IJugador;
import Interfaces.IVentana;
import Modelo.Cartas.Carta;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Grafica {
    private JFrame vent;
    private final ControladorGrafico controlador;
    private boolean activo = true;
    private VentanaPrincipal menu = null;
    private Vista.Grafica.Partida partida = null;

    public Grafica(ControladorGrafico controladorGrafico){
        this.controlador = controladorGrafico;
        IVentana menu = new Nombre(controlador,this);
        crearVentana(menu);
    }

    private void crearVentana(IVentana panel){
        if (vent != null){
            vent.setContentPane(panel.getPanel());
            panel.updateUI();
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
        Vista.Grafica.Partida partida = new Partida(this, pedirListos());
        this.partida = partida;
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

    public IJugador pedirJugador() {
        return controlador.getJugador();
    }

    public ArrayList<Carta> pedirCartasMesa() {
        return controlador.pedirCartasMesa();
    }

    public ArrayList<Carta> pedirCartasJugador(String nombre) {
        return controlador.pedirCartasJugador(nombre);
    }

    public void asignarTurno() {
        partida.asignarTurno();
    }

    public void robarPozo(String jugador, int selected) {
        controlador.robarPozo(jugador,selected);
    }

    public void actualizarCartas(ArrayList<IJugador> jugadores) {
        partida.actualizarCartas(jugadores);
    }

    public void agarrarCartaMesa(int mesa, int mano) {
        controlador.agarrarCartaMesa(mesa,mano);
    }

    public void soplar(int c1, int c2) {
        controlador.soplar(c1,c2);
    }

    public void dejarCarta(int selected) {
        controlador.dejarCarta(selected);
    }
}
