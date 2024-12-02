package Controlador;

import Interfaces.IJugador;
import Interfaces.Observer;
import Interfaces.iVista;
import Modelo.Cartas.Carta;
import Modelo.Events.EventType;
import Modelo.Exceptions.InvalidInputException;
import Modelo.Events.GameEvent;
import Modelo.Exceptions.InvalidNameException;
import Modelo.Main.IModelo;
import Modelo.Main.Partida;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Controlador implements IControladorRemoto {

    private final iVista grafica;

    private IModelo partida;


    private IJugador jugador;

    public IJugador getJugador() {
        return this.jugador;
    }
    public Controlador(iVista grafica) {
        this.grafica =grafica;
    }

    public boolean addJugador(String nombre) {
        try {
            this.jugador = partida.addJugador(nombre);
            this.grafica.addtoTitle(jugador.getNombre());
            return true;
        } catch (InvalidInputException e) {
            try {
                partida.removerObservador(this);
            } catch (RemoteException ex) {
                e.printStackTrace();
            }
            grafica.kill();
            return false;
        } catch (InvalidNameException e) {
            grafica.menuNombre(true);
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void ready() {
        try {
            partida.ready(this.jugador);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public void responderParejas(boolean b) {
        try {
            partida.modoParejas(b);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<IJugador> pedirJugadores() {
        try {
            return partida.getIJugadores();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Carta> pedirCartasMesa() {
        try {
            return  partida.getCartasMesa();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Carta> pedirCartasJugador(String nombre) {
        try {
            return partida.getCartasJugador(nombre);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void robarPozo(String jugador, int selected) {
        try {
            partida.ligarPozo(selected, jugador);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void agarrarCartaMesa(int mesa, int mano) {
        try {
            partida.ligarCarta(mesa,mano);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void soplar(int c1, int c2) {
        try {
            partida.soplar(c1,c2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void dejarCarta(int selected) {
        try {
            partida.dejarCarta(selected);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void elegirEquipo(String jugador) throws InvalidInputException{
        try {
            partida.armarEquipos(jugador);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {
        this.partida = (IModelo) t;
    }

    @Override
    public void actualizar(IObservableRemoto modelo, Object o) throws RemoteException {
        GameEvent e = (GameEvent) o;
        switch (e.getTipo()){

            case EventType.todosListos -> {
                if ( ((IJugador) e.getContenido()).compararNombre(jugador) ) {
                    if (partida.canParejas()){
                        grafica.preguntarParejas();
                    }
                    else {
                        partida.modoParejas(false);
                    }
                }
            }
            case actualizacionDeCartas -> {
                ArrayList<IJugador> jugadores = (ArrayList<IJugador>) e.getContenido();
                grafica.actualizarCartas(jugadores);
            }

            case empezoElJuego -> {
                IJugador turno = (IJugador) e.getContenido();
                grafica.mostrarPartida();
                if (turno.compararNombre(jugador)) {
                    grafica.asignarTurno();
                }
            }

            case AsignarTurno -> {
                IJugador turno = (IJugador) e.getContenido();
                if (jugador.compararNombre(turno)) {
                    grafica.asignarTurno();
                }
            }

            case jugadorListo -> {
                ArrayList<IJugador> jugadores = (ArrayList<IJugador>) e.getContenido();
                grafica.actualizarListos(jugadores);
            }

            case seleccionDeEquipos -> {
                if ( ((IJugador) e.getContenido()).compararNombre(jugador) ) {
                    grafica.seleccionarEquipos();
                }
            }

            case ganador -> grafica.ganador((ArrayList<IJugador>) e.getContenido());
        }
    }
}
