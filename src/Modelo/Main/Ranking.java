package Modelo.Main;

import Interfaces.IJugador;
import Modelo.Serializacion.Serializador;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Ranking implements Serializable {
    ArrayList<String> jugadores;
    ArrayList<Integer> puntos;

    public Ranking() {
        jugadores = new ArrayList<>();
        puntos = new ArrayList<>();
    }

    static public Ranking getInstanceOf() {
            Serializador s = new Serializador("./ranking");
            File f = new File("./ranking");
            if (f.exists()){
                return (Ranking) s.readFirstObject();
            }
            else {
                return new Ranking();
            }
    }

    public void update(ArrayList<IJugador> players){
        for (int i = 0 ; i < players.size() ; i++){
            boolean encontro = false;
            IJugador actualizar = players.get(i);
            for (int j = 0 ; j < jugadores.size() && !encontro ; j++){
                String rank = jugadores.get(j);
                if (actualizar.compararNombre(rank)){
                    encontro = true;
                    if (puntos.get(j) < actualizar.getPuntos()){
                        puntos.set(j, actualizar.getPuntos());
                    }
                }
            }
            if (!encontro){
                jugadores.add(players.get(i).getNombre());
                puntos.add(players.get(i).getPuntos());
            }
        }
        ordenar();
        Serializador s = new Serializador("./ranking");
        s.writeOneObject(this);
    }

    private void ordenar() {
        for (int i = 0; i < puntos.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < puntos.size(); j++) {
                if (puntos.get(j) < puntos.get(minIndex)) {
                    minIndex = j;
                }
            }
            // Intercambio los puntos
            int tempPunto = puntos.get(i);
            puntos.set(i, puntos.get(minIndex));
            puntos.set(minIndex, tempPunto);

            // Intercambio los jugadores
            String tempJugador = jugadores.get(i);
            jugadores.set(i, jugadores.get(minIndex));
            jugadores.set(minIndex, tempJugador);
        }
    }

    public String getFist10(){
        StringBuilder s = new StringBuilder("Ranking:");
        for (int i = 0; i < jugadores.size() && i < 10; i++) {
            s.append("\n").append(i + 1).append(".  ").append(jugadores.get(i)).append(":   ").append(puntos.get(i));
        }
        return s.toString();
    }
}
