package Modelo.Main;

import Interfaces.IJugador;
import Interfaces.Observable;
import Interfaces.Observer;
import Modelo.Cartas.Carta;
import Modelo.Cartas.Mazo;
import Modelo.Events.EventType;
import Modelo.Events.GameEvent;
import Modelo.Exceptions.InvalidInputException;
import Modelo.Exceptions.TipoInputInvalido;

import java.util.*;

public class Partida implements Observable {
    private ArrayList<Observer> observers;

    private ArrayList<Jugador> jugadores;

    private boolean parejas = false;

    private Jugador turno;

    private boolean partidaEmpezada = false;

    private Mesa mesa;

    private Mazo mazo;


    public Partida(){
        observers = new ArrayList<>();
        this.jugadores = new ArrayList<>();

//        Collections.shuffle(jugadores);
//        Ronda ronda = new Ronda(jugadores);
//        ronda.jugar();
//
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
//        if (parejas){
//            System.out.println(equipos[0]);
//            System.out.println(equipos[1]);
//        }
//        else {
//            for (Jugador j : jugadores){
//                System.out.println(j + ": " + j.getPuntos());
//            }
//        }


    }

    @Override
    public void notificar(GameEvent e) {
        for (Observer o : observers){
            o.update(e);
        }
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public IJugador addJugador(String nombre) throws InvalidInputException {
        if (jugadores.size() >= 4){
            throw new InvalidInputException(TipoInputInvalido.salaLlena);
        }
        if (partidaEmpezada){
            throw new InvalidInputException(TipoInputInvalido.partidaComenzada);
        }
        Jugador r = new Jugador(nombre);
        jugadores.add(r);
        notificar(new GameEvent(EventType.jugadorListo,getIJugadores()));
        return (IJugador) r;
    }

    public void empezarJuego() {
        partidaEmpezada = true;
        if (jugadores.size() == 4){
            notificar(new GameEvent(EventType.preguntarModoParejas));
        }
        else{
            parejas = false;
            empezarPartida();
        }
    }

    public void respuestaParejas(boolean equipos){
        if (equipos){
            // TODO: formar equipos
        }
        else{
            parejas = false;
            empezarPartida();
        }
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
            notificar(new GameEvent(EventType.updateCartas));
        }
        else {
            System.out.println("Jugada incorrecta");
            turno.quitarPuntos(1);
        }
    }

    public void ligarPozo(int carta,Jugador target){
        boolean jugadaCorrecta = false;

        Carta select = turno.getCarta(carta);
        if (turno.getCarta(carta).equals(target.getTope()) && !mesa.tiene(select)){
            target.getPozo().agregarCarta(select);
            if (target != turno){
                target.getPozo().pasarCartas(turno.getPozo());
            }
            terminarTurno();
        }
        if (!jugadaCorrecta){
            System.out.println("Jugada invalida");
            dejarCarta(carta);
        }
    }

    private void terminarTurno() {

        mostrarSituacionPartida(turno);

        if (mazo.cantCartas() > jugadores.size() * 3){
            pasarTurno();
            repartir();
            notificar(new GameEvent(EventType.updateCartas));
        }
        else {
            notificar(new GameEvent(EventType.preguntarNuevaRonda));
        }
    }

    public void respuestaPreguntarNuevaRonda(boolean x){
        if (x) {
            nuevaRonda();
            pasarTurno();
            repartir();
            notificar(new GameEvent(EventType.updateCartas));
        }
        else {
            terminarPartida();
        }
    }

    private void terminarPartida() {
        //TODO
    }

    private void dejarCarta(int select) {
        mesa.agregarCarta(turno.getCarta(select));
        terminarTurno();
    }

    private void mostrarSituacionPartida(Jugador j) {
        System.out.println("Turno de " + j.getNombre() + "!!");

        System.out.println("Mano: ");
        j.mostrarCartas();
        System.out.println();

        System.out.println("Mesa: ");
        mesa.mostrarCartas();
        System.out.println();

        System.out.println("Pozos: ");
        Utils.mostrarTopes(jugadores);
        System.out.println();
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


}
