package Modelo.Main;

import Interfaces.IJugador;
import Modelo.Cartas.Carta;
import Modelo.Exceptions.InvalidInputException;
import Modelo.Exceptions.InvalidNameException;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IModelo extends IObservableRemoto {
    IJugador addJugador(String nombre) throws InvalidInputException, InvalidNameException, RemoteException;

    void modoParejas(boolean equipos) throws RemoteException;

    void armarEquipos(String elegido) throws InvalidInputException, RemoteException;

    void soplar(int indice1, int indice2) throws RemoteException;

    void ligarPozo(int carta, String jugador) throws RemoteException;

    void dejarCarta(int select) throws RemoteException;

    void ready(IJugador ijugador) throws RemoteException;

    ArrayList<IJugador> getIJugadores() throws RemoteException;

    ArrayList<Carta> getCartasMesa() throws RemoteException;

    ArrayList<Carta> getCartasJugador(String nombre) throws RemoteException;

    void ligarCarta(int cartaMesa, int mano) throws RemoteException;

    boolean canParejas() throws RemoteException;

    public String getRanking() throws RemoteException;

}
