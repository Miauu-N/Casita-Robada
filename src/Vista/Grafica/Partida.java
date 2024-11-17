package Vista.Grafica;

import Interfaces.IJugador;
import Interfaces.IVentana;
import Modelo.Cartas.Carta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static Modelo.Main.Utils.botonCarta;
import static Modelo.Main.Utils.cartaToPath;

public class Partida implements IVentana {
    private final Grafica grafica;
    private ArrayList<JButton> botonesMano;
    private JPanel panel1;
    private JPanel pMesa;
    private JPanel pJugador;
    private ArrayList<pRival> pRivales;
    private JPanel pRival1;
    private JPanel pRival2;
    private JPanel pRival3;
    private JButton lPozo;
    private JPanel pMano;
    private IJugador nombreJugador;
    private ArrayList<JButton> botonesJugadas;
    private int selected;

    public Partida(Grafica grafica, ArrayList<IJugador> jugadors) {
        this.botonesJugadas = new ArrayList<>();
        this.botonesMano = new ArrayList<>();
        this.grafica = grafica;

        IJugador jugador = grafica.pedirJugador();
        this.nombreJugador = jugador;

        int i = 0;
        for (IJugador j : jugadors){
            if (!j.compararNombre(jugador)){
                pRival panel = pRivales.get(i++);
                JButton e = panel.setJugador(j);
                e.addActionListener(new ActionListener() {

                    // Robar un pozo

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Robar pozo de: " + j.getNombre() ); // todo ...
                        robarPozo(j.getNombre());
                    }
                });
                e.setEnabled(false);
                botonesJugadas.add(e);
            }
        }

        crearBotonesMesa();

        botonCarta(lPozo, cartaToPath(jugador.getTope())).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Robar pozo de: " + nombreJugador.getNombre() ); // todo ...
                robarPozo(nombreJugador.getNombre());

            }
        });
        lPozo.setEnabled(false);
        botonesJugadas.add(lPozo);

        crearCartasMano();
    }

    private void crearCartasMano() {
        pMano.removeAll();
        pMano.updateUI();
        int i;
        ArrayList<Carta> mano = grafica.pedirCartasJugador(nombreJugador.getNombre());
        i = 0;
        for (Carta c : mano){
            JButton boton = botonCarta(new JButton(), cartaToPath(c));
            pMano.add(boton);
            int finalI1 = i;
            boton.addActionListener(new ActionListener() {

                // seleccionar carta en la mano

                @Override
                public void actionPerformed(ActionEvent e) {
                    selected = finalI1;
                    activarMano(false,true);
                    System.out.println("Carta seleccionada: " + finalI1);
                }

            });
            boton.setEnabled(false);
            i++;
            this.botonesMano.add(boton);
        }
    }

    private void crearBotonesMesa() {
        pMesa.removeAll();
        int i = 0;
        for (Carta c : grafica.pedirCartasMesa()){
            JButton cartaMesa = botonCarta(new JButton(), cartaToPath(c));
            int finalI = i;
            cartaMesa.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {  // Agarrar una carta de la mesa

                    activarJugadas(false);
                    System.out.println("Robar de la mesa: " + finalI + " con la carta: " + selected);
                    grafica.agarrarCartaMesa(finalI,selected);
                }
            });
            cartaMesa.setEnabled(false);
            pMesa.add(cartaMesa);
            botonesJugadas.add(cartaMesa);
            i++;
        }
        pMesa.updateUI();
    }

    private void crearCartasRivales(ArrayList<IJugador> jugadors){
        int i = 0;

        for (IJugador j : jugadors){
            if (j.compararNombre(nombreJugador)){
                ImageIcon defaultIcon = new ImageIcon(cartaToPath(j.getTope()));
                defaultIcon.setImage(defaultIcon.getImage().getScaledInstance(91,127,Image.SCALE_SMOOTH));
                lPozo.setIcon(defaultIcon);
            }
            else {
                int indicePRival = 0;
                pRival panel = pRivales.get(indicePRival++);
                while (!panel.isUsado() || !panel.getJugador().compararNombre(j)) {
                    panel = pRivales.get(indicePRival++);
                }
                panel.actualizar(j);
            }
        }
        updateUI();
    }

    private void robarPozo(String jugador) {
        activarJugadas(false);
        System.out.println("Robar mazo a : " + nombreJugador.getNombre());
        grafica.robarPozo(jugador,selected);
    }

    private void activarMano(Boolean mano,boolean activarJugadas) {
        for (JButton b : botonesMano){
            b.setEnabled(mano);
        }
        if (activarJugadas) {
            activarJugadas(true);
        }
    }
    private void activarMano(Boolean mano) {
        for (JButton b : botonesMano){
            b.setEnabled(mano);
        }
    }

    private void activarJugadas(boolean x) {
        for (JButton b : botonesJugadas){
            b.setEnabled(x);
        }
    }


    @Override
    public Container getPanel() {
        return panel1;
    }

    @Override
    public void updateUI() {
        panel1.updateUI();
    }

    private void createUIComponents() {
        pRival pRival1 = new pRival();
        pRival pRival2 = new pRival();
        pRival pRival3 = new pRival();
        pRival2.rotar();
        pRival3.rotar();

        pRivales = new ArrayList<>();
        pRivales.add(pRival1);
        pRivales.add(pRival2);
        pRivales.add(pRival3);
        this.pRival1 = pRival1;
        this.pRival2 = pRival2;
        this.pRival3 = pRival3;
    }

    public void asignarTurno() {
        activarMano(true);
    }

    public void actualizarCartas(ArrayList<IJugador> jugadores) {
        crearBotonesMesa();
        crearCartasRivales(jugadores);
        crearCartasMano();
    }

}
