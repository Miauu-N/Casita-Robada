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

import java.util.*;

public class Partida extends ObservableRemoto implements Observable {

    private ArrayList<Jugador> jugadores;

    private boolean parejas = false;

    private Jugador turno;

    private boolean partidaEmpezada = false;

    private Mesa mesa;

    private Mazo mazo;
    private ArrayList<Equipo> equipos;
    private IJugador host;


    public Partida(){
        this.jugadores = new ArrayList<>();
        this.equipos = new ArrayList<>();

//        System.out.println("Desea jugar otra ronda? (S/N)");
//        String continuar = scanner.next();
//
//        while (continuar.equalsIgnoreCase("s")){
//            ronda = new Ronda(jugadores);
//            ronda.jugar();
//            System.out.println("Desea jugar otra ronda? (S/N)");
//            continuar = scanner.next();
//        }
//
//        System.out.println("Puntaje: ");
//


    }
/*
    @Override
    public void notificar(GameEvent e) {
        for (Observer o : observers){
            o.update(e);
        }
    }
*/

    public IJugador addJugador(String nombre) throws InvalidInputException, InvalidNameException {
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
        notificar(new GameEvent(EventType.jugadorListo,getIJugadores()));
        return (IJugador) r;
    }

    public void empezarJuego() {
        partidaEmpezada = true;
        this.host = jugadores.getFirst();
        notificar(new GameEvent(EventType.todosListos, this.host));
    }

    public void modoParejas(boolean equipos){
        if (equipos){
            notificar(new GameEvent(EventType.seleccionDeEquipos,host));
            parejas = true;
        }
        else{
            parejas = false;
            empezarPartida();
        }
    }

    public void armarEquipos(String elegido) throws InvalidInputException {
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

    public void soplar(int indice1,int indice2){
        if (indice1 != indice2 && mesa.verCarta(indice1).equals(mesa.verCarta(indice2))){
            if (indice1 < indice2){
                int aux = indice2;
                indice2 = indice1;
                indice1 = aux;
            }
            turno.getPozo().agregarCarta(mesa.tomarCarta(indice1));
            turno.getPozo().agregarCarta(mesa.tomarCarta(indice2));
            notificar(new GameEvent(EventType.actualizacionDeCartas,getIJugadores()));
        }
        else {
            System.out.println("Jugada incorrecta");
            turno.quitarPuntos(1);
        }
    }

    public void ligarPozo(int carta,Jugador target){
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

    public void ligarPozo(int carta,String jugador){
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

    private void terminarTurno() {

        if (!tienenCartas()) {
            if (mazo.cantCartas() > jugadores.size() * 3){
                repartir();
                pasarTurno();
                notificar(new GameEvent(EventType.actualizacionDeCartas,getIJugadores()));
                notificar(new GameEvent(EventType.AsignarTurno,(IJugador) turno));
            }
            else {
                terminarPartida();
            }
        }
        else {
            pasarTurno();
            notificar(new GameEvent(EventType.actualizacionDeCartas,getIJugadores()));
            notificar(new GameEvent(EventType.AsignarTurno,(IJugador) turno));
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

    public void respuestaPreguntarNuevaRonda(boolean x){
        if (x) {
            nuevaRonda();
            pasarTurno();
            repartir();
            notificar(new GameEvent(EventType.actualizacionDeCartas,getIJugadores()));
        }
        else {
            terminarPartida();
        }
    }

    private void terminarPartida() {
        if (parejas){
            if(equipos.get(0).sumarPuntos() > equipos.get(1).sumarPuntos()){
                notificar(new GameEvent(EventType.ganador,equipos.get(0).getJugadoresEncapsulados()));
            }
            else if(equipos.get(0).sumarPuntos() < equipos.get(1).sumarPuntos()){
                notificar(new GameEvent(EventType.ganador,equipos.get(1).getJugadoresEncapsulados()));
            }
            else {
                notificar(new GameEvent(EventType.ganador,getIJugadores()));
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
            notificar(new GameEvent(EventType.ganador,ganadores));
        }

    }

    public void dejarCarta(int select) {
        mesa.agregarCarta(turno.getCarta(select));
        terminarTurno();
    }
    private void dejarCarta(Carta select) {
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



    private void empezarPartida() {
        Collections.shuffle(jugadores);
        nuevaRonda();
        pasarTurno();
        repartir();
        notificar(new GameEvent(EventType.empezoElJuego,(IJugador)turno));
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


    public void ready(IJugador ijugador) {
        Jugador jugador = (Jugador) ijugador;
        jugador.setReady();

        if (jugadores.size() >= 2 && allReady() ){
            empezarJuego();
        }
        else {
            notificar(new GameEvent(EventType.jugadorListo, getIJugadores()));
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

    public ArrayList<IJugador> getIJugadores(){
        ArrayList<IJugador> resultado = new ArrayList<>();
        for (Jugador j : jugadores){
            resultado.add((IJugador) j);
        }
        return resultado;
    }


    public ArrayList<Carta> getCartasMesa() {
        return mesa.getCartas();
    }

    public ArrayList<Carta> getCartasJugador(String nombre) {

        for (Jugador j : jugadores){
            if (j.compararNombre(nombre)){
                return  j.getMano();
            }
        }
        throw new RuntimeException("no se encontro el jugador");
    }

    public void ligarCarta(int cartaMesa, int mano) {
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

    public boolean canParejas() {
        return jugadores.size() > 2 && jugadores.size() % 2 == 0;
    }
}
