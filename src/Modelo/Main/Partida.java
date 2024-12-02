package Modelo.Main;

import Interfaces.IJugador;
import Interfaces.Observable;
import Interfaces.Observer;
import Modelo.Cartas.Carta;
import Modelo.Cartas.Mazo;
import Modelo.Events.EventType;
import Modelo.Events.GameEvent;
import Modelo.Exceptions.InvalidInputException;
import Modelo.Exceptions.InvalidNameException;
import Modelo.Exceptions.TipoInputInvalido;
import Vista.Grafica.Utils;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.rmi.RemoteException;
import java.util.*;

public class Partida extends ObservableRemoto implements IModelo {

    private final ArrayList<Jugador> jugadores;

    private boolean parejas = false;

    private Jugador turno;

    private boolean partidaEmpezada = false;

    private Mesa mesa;

    private Mazo mazo;
    private final ArrayList<Equipo> equipos;
    private IJugador host;


    public Partida(){
        this.jugadores = new ArrayList<>();
        this.equipos = new ArrayList<>();

    }


    @Override
    public IJugador addJugador(String nombre) throws InvalidInputException, InvalidNameException, RemoteException {
        if (jugadores.size() >= 4){
            throw new InvalidInputException(TipoInputInvalido.salaLlena);
        }
        if (partidaEmpezada){
            throw new InvalidNameException();
        }
        if (nombre.isEmpty() || nombre.isBlank()){
            throw new InvalidNameException();
        }
        for (Jugador j : jugadores){
            if (j.compararNombre(nombre)){
                throw new InvalidNameException();
            }
        }
        Jugador r = new Jugador(nombre);
        jugadores.add(r);
        notificarObservadores(new GameEvent(EventType.jugadorListo,getIJugadores()));
        return (IJugador) r;
    }

    @Override
    public void empezarJuego() throws RemoteException {
        partidaEmpezada = true;
        this.host = jugadores.getFirst();
        notificarObservadores(new GameEvent(EventType.todosListos, this.host));
    }

    @Override
    public void modoParejas(boolean equipos) throws RemoteException {
        if (equipos){
            notificarObservadores(new GameEvent(EventType.seleccionDeEquipos,host));
            parejas = true;
        }
        else{
            parejas = false;
            empezarPartida();
        }
    }

    @Override
    public void armarEquipos(String elegido) throws InvalidInputException, RemoteException {
        if (buscarJugador(elegido) == null){
            throw new InvalidInputException(TipoInputInvalido.nombreInvalido);
        }
        ArrayList<Jugador> equipo1 = new ArrayList<>();
        ArrayList<Jugador> equipo2 = new ArrayList<>();

        for (Jugador j : jugadores){
            if (j.compararNombre(host) || j.compararNombre(elegido)){
                equipo1.add(j);
            }
            else {
                equipo2.add(j);
            }
        }
        Equipo team1 = new Equipo(equipo1,1 + "");
        this.equipos.add(team1);
        Equipo team2 = new Equipo(equipo2,2 + "");
        this.equipos.add(team2);
        empezarPartida();
    }

    @Override
    public void soplar(int indice1, int indice2) throws RemoteException {
        if (indice1 != indice2 && mesa.verCarta(indice1).equals(mesa.verCarta(indice2))){
            if (indice1 < indice2){
                int aux = indice2;
                indice2 = indice1;
                indice1 = aux;
            }
            turno.getPozo().agregarCarta(mesa.tomarCarta(indice1));
            turno.getPozo().agregarCarta(mesa.tomarCarta(indice2));
            notificarObservadores(new GameEvent(EventType.actualizacionDeCartas,getIJugadores()));
        }
        else {
            System.out.println("Jugada incorrecta");
            turno.quitarPuntos(1);
        }
    }

    @Override
    public void ligarPozo(int carta, Jugador target) throws RemoteException {
        Carta select = turno.getCarta(carta);
        if (select.equals(target.getTope()) && !mesa.tiene(select)){
            target.getPozo().agregarCarta(select);
            if (!target.compararNombre(turno)){
                target.getPozo().pasarCartas(turno.getPozo());
            }
            terminarTurno();
        }
        else{
            System.out.println("Jugada invalida");
            dejarCarta(select);
        }
    }

    @Override
    public void ligarPozo(int carta, String jugador) throws RemoteException {
        Jugador j = buscarJugador(jugador);
        ligarPozo(carta,j);
    }

    private Jugador buscarJugador(String jugador) {
        for (Jugador j : jugadores){
            if (j.compararNombre(jugador)){
                return j;
            }
        }
        return null;
    }

    private void terminarTurno() throws RemoteException {

        if (!tienenCartas()) {
            if (mazo.cantCartas() > jugadores.size() * 3){
                repartir();
                pasarTurno();
                notificarObservadores(new GameEvent(EventType.actualizacionDeCartas,getIJugadores()));
                notificarObservadores(new GameEvent(EventType.AsignarTurno,(IJugador) turno));
            }
            else {
                terminarPartida();
            }
        }
        else {
            pasarTurno();
            notificarObservadores(new GameEvent(EventType.actualizacionDeCartas,getIJugadores()));
            notificarObservadores(new GameEvent(EventType.AsignarTurno,(IJugador) turno));
        }
    }

    private boolean tienenCartas() {
        for (Jugador j : jugadores){
            if (j.getCantCartasEnMano() > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public void respuestaPreguntarNuevaRonda(boolean x) throws RemoteException {
        if (x) {
            nuevaRonda();
            pasarTurno();
            repartir();
            notificarObservadores(new GameEvent(EventType.actualizacionDeCartas,getIJugadores()));
        }
        else {
            terminarPartida();
        }
    }

    private void terminarPartida() throws RemoteException {
        if (parejas){
            if(equipos.get(0).sumarPuntos() > equipos.get(1).sumarPuntos()){
                notificarObservadores(new GameEvent(EventType.ganador,equipos.get(0).getJugadoresEncapsulados()));
            }
            else if(equipos.get(0).sumarPuntos() < equipos.get(1).sumarPuntos()){
                notificarObservadores(new GameEvent(EventType.ganador,equipos.get(1).getJugadoresEncapsulados()));
            }
            else {
                notificarObservadores(new GameEvent(EventType.ganador,getIJugadores()));
            }
        }
        else {
            int max = jugadores.getFirst().getPuntos();
            ArrayList<IJugador> ganadores = new ArrayList<>();
            for (Jugador j : jugadores){
                if (j.getPuntos() > max){
                    max = j.getPuntos();
                    ganadores.clear();
                    ganadores.add((IJugador)j);
                } else if (j.getPuntos() == max) {
                    ganadores.add((IJugador)j);
                }
            }
            notificarObservadores(new GameEvent(EventType.ganador,ganadores));
        }

    }

    @Override
    public void dejarCarta(int select) throws RemoteException {
        dejarCarta(turno.getCarta(select));
    }
    private void dejarCarta(Carta select) throws RemoteException {
        mesa.agregarCarta(select);
        terminarTurno();
    }



    /**
     * Asigna a la variable turno el siguiente jugador de la lista
     */
    private void pasarTurno() {
        turno = jugadores.removeFirst();
        jugadores.addLast(turno);
    }

    private void repartir() {
        for (int i = 0; i < 3; i++) {
            for (Jugador j : jugadores){
                j.darCarta(mazo.agarrarCarta());
            }
        }
    }



    private void empezarPartida() throws RemoteException {
        Collections.shuffle(jugadores);
        nuevaRonda();
        pasarTurno();
        repartir();
        notificarObservadores(new GameEvent(EventType.empezoElJuego,(IJugador)turno));
    }



    /**
     * Crea mesa, mazo y limpia las cartas de los jugadores
     */
    private void nuevaRonda() {
        this.mesa = new Mesa();
        this.mazo = new Mazo();
        mazo.mezclar();
        for (Jugador j : jugadores){
            j.limpiarCartas();
        }
        for (int i = 0; i < 4; i++) {
            mesa.agregarCarta(mazo.agarrarCarta());
        }
    }


    @Override
    public void ready(IJugador ijugador) throws RemoteException {
        Jugador jugador = buscarJugador(ijugador.getNombre());
        jugador.setReady();

        if (jugadores.size() >= 2 && allReady() ){
            empezarJuego();
        }
        else {
            notificarObservadores(new GameEvent(EventType.jugadorListo, getIJugadores()));
        }
    }

    private boolean allReady() {
        for (Jugador jugador : jugadores){
            if (!jugador.getReady()){
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<IJugador> getIJugadores() throws RemoteException{
        ArrayList<IJugador> resultado = new ArrayList<>();
        for (Jugador j : jugadores){
            resultado.add((IJugador) j);
        }
        return resultado;
    }


    @Override
    public ArrayList<Carta> getCartasMesa() throws RemoteException{
        return mesa.getCartas();
    }

    @Override
    public ArrayList<Carta> getCartasJugador(String nombre)  throws RemoteException{

        try {
            return buscarJugador(nombre).getMano();
        } catch (Exception e) {
            throw new RuntimeException("Jugador no encontrado");
        }
    }

    @Override
    public void ligarCarta(int cartaMesa, int mano) throws RemoteException{
        try {
            Carta select = turno.getCarta(mano);

            boolean buenaJugada = false;

            if (select.getNumero() == mesa.verCarta(cartaMesa).getNumero()){
                turno.agregarPozo(mesa.tomarCarta(cartaMesa));
                turno.agregarPozo(select);
                terminarTurno();
                buenaJugada = true;
            }

            if (!buenaJugada){
                System.out.println("Jugada equivocada");
                dejarCarta(select);
            }
        } catch (Exception e) {
            System.out.println("indice incorrecto");
            dejarCarta(0);
        }

    }

    @Override
    public boolean canParejas() throws RemoteException{
        return jugadores.size() > 2 && jugadores.size() % 2 == 0;
    }
}
