package Modelo.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Partida {


    public Partida(){
        Scanner scanner = new Scanner(System.in);
        boolean parejas;
        System.out.println("Ingrese la cantidad de participantes: ");
        int cantParticipantes = scanner.nextInt();
        if (cantParticipantes != 4){
            parejas = false;
        }
        else {
            System.out.println("Desea jugar por parejas? (S/N): ");
            String respuesta = scanner.next();
            parejas = respuesta.toLowerCase().equals("s");
        }

        ArrayList<Jugador> jugadores = new ArrayList<>();
        Equipo[] equipos = new Equipo[2];

        if (parejas){
            ArrayList<Jugador> T1 = new ArrayList<>();
            ArrayList<Jugador> T2 = new ArrayList<>();

            System.out.println("Introduzca el nombre del primer equipo: ");
            String n1 = scanner.next();

            System.out.println("Introduzca el nombre del jugador 1");
            T1.add(new Jugador(scanner.next()));

            System.out.println("Introduzca el nombre del jugador 2");
            T1.add(new Jugador(scanner.next()));

            System.out.println("Introduzca el nombre del segundo equipo: ");
            String n2 = scanner.next();

            System.out.println("Introduzca el nombre del jugador 3");
            T2.add(new Jugador(scanner.next()));

            System.out.println("Introduzca el nombre del jugador 4");
            T2.add(new Jugador(scanner.next()));

            equipos[0] = new Equipo(T1,n1);
            equipos[1] = new Equipo(T2,n2);
            jugadores.addAll(T1);
            jugadores.addAll(T2);
        }
        else{
            for (int i = 0; i < cantParticipantes; i++) {
                System.out.println("Introduzca el nombre del jugador " + (i + 1) + ": ");
                String nombre = scanner.next();
                jugadores.add(new Jugador(nombre));
            }
        }
        Collections.shuffle(jugadores);
        Ronda ronda = new Ronda(jugadores);
        ronda.jugar();

        System.out.println("Desea jugar otra ronda? (S/N)");
        String continuar = scanner.next();

        while (continuar.equalsIgnoreCase("s")){
            ronda = new Ronda(jugadores);
            ronda.jugar();
            System.out.println("Desea jugar otra ronda? (S/N)");
            continuar = scanner.next();
        }

        System.out.println("Puntaje: ");

        if (parejas){
            System.out.println(equipos[0]);
            System.out.println(equipos[1]);
        }
        else {
            for (Jugador j : jugadores){
                System.out.println(j + ": " + j.getPuntos());
            }
        }


    }
}
