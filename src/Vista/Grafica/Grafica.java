package Vista.Grafica;

import Controlador.Controlador;
import Interfaces.IJugador;
import Interfaces.IVentana;
import Interfaces.iVista;
import Modelo.Cartas.Carta;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Grafica implements iVista {
    private JFrame vent;
    private final Controlador controlador;
    private boolean activo = true;
    private VentanaPrincipal menu = null;
    private Vista.Grafica.Partida partida = null;

    public Grafica(){
        this.controlador = new Controlador(this);
        menuNombre();
    }

    public Controlador getControlador() {
        return this.controlador;
    }

    public void menuNombre() {
        menuNombre(false);
    }
    @Override
    public void menuNombre(boolean error) {
        IVentana menu = new Nombre(controlador,this,error);
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

    @Override
    public void mostrarPartida(){
        Vista.Grafica.Partida partida = new Partida(this, pedirListos());
        this.partida = partida;
        crearVentana(partida);
    }

    @Override
    public void ganador(ArrayList<IJugador> ganadores) {
        boolean gano = false;
        for (IJugador j : ganadores){
            if (j.compararNombre(controlador.getJugador())){
                gano = true;
            }
        }
        crearVentana(new PartidaFinalizada(this,gano ,ganadores));
    }

    @Override
    public void kill() {
        System.out.println("Cliente cerrado");
        activo = false; // TODO Preguntar si esta bien
    }

    @Override
    public void preguntarParejas(){
        IVentana menu = new PreguntarParejas(controlador,this, SiONo.preguntarParejas);
        crearVentana(menu);
    }

    @Override
    public void seleccionarEquipos() {
        IVentana menu = new ElegirEquipos(this, controlador.pedirJugadores());
        crearVentana(menu);
    }

    @Override
    public void actualizarListos(ArrayList<IJugador> jugadores) {
        if (menu != null){
            menu.actualizarListos(jugadores);
        }
    }

    @Override
    public void addtoTitle(String nombre) {
        String titulo = vent.getTitle() + " ( " + nombre + " )";
        vent.setTitle(titulo);
    }

    public ArrayList<IJugador> pedirListos() {
        return controlador.pedirJugadores();
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

    @Override
    public void asignarTurno() {
        partida.asignarTurno();
    }

    public void robarPozo(String jugador, int selected) {
        controlador.robarPozo(jugador,selected);
    }

    @Override
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

    public void elegirEquipo(IJugador jugador) {
        try {
            controlador.elegirEquipo(jugador.getNombre());
        } catch (Exception e) {
            throw new RuntimeException("Error al seleccionar equipo");
        }
    }

}
