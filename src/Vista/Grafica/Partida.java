package Vista.Grafica;

import Interfaces.IJugador;
import Interfaces.IVentana;
import Modelo.Cartas.Carta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static Vista.Grafica.Utils.botonCarta;
import static Vista.Grafica.Utils.cartaToPath;

public class Partida implements IVentana {
    private final Grafica grafica;
    private final ArrayList<JButton> botonesMano;
    private JPanel panel1;
    private JPanel pMesa;
    private JPanel pJugador;
    private ArrayList<pRival> pRivales;
    private JPanel pRival1;
    private JPanel pRival2;
    private JPanel pRival3;
    private JButton lPozo;
    private JPanel pMano;
    private final IJugador nombreJugador;
    private final ArrayList<JButton> botonesJugadas;
    private int selected;
    private boolean soplar = false;
    private ArrayList<JButton> cartasMesa;

    public Partida(Grafica grafica, ArrayList<IJugador> jugadors) {
        this.botonesJugadas = new ArrayList<>();
        this.botonesMano = new ArrayList<>();
        this.cartasMesa = new ArrayList<>();
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
        botonesMano.clear();
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
                public void actionPerformed(ActionEvent e) {

                    // Agarrar una carta de la mesa

                    if(selected == -1){
                        soplar = true;
                        selected = finalI;
                    }
                    else if (soplar) {
                        soplar(finalI);
                    }
                    else {
                        activarJugadas(false);
                        System.out.println("Robar de la mesa: " + finalI + " con la carta: " + selected);
                        grafica.agarrarCartaMesa(finalI, selected);
                        selected = -1;
                    }

                }
            });
            cartaMesa.setEnabled(false);
            pMesa.add(cartaMesa);
            cartasMesa.add(cartaMesa);
            botonesJugadas.add(cartaMesa);
            i++;
        }

        // boton dejar carta
        // "images/pozo_Vacio.png"
        JButton cartaMesa = botonCarta(new JButton(), "images/pozo_Vacio.png",true);
        cartaMesa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activarJugadas(false);
                grafica.dejarCarta(selected);
                selected = -1;
            }
        });
        cartaMesa.setEnabled(false);
        pMesa.add(cartaMesa);
        botonesJugadas.add(cartaMesa);


        pMesa.updateUI();
    }

    private void soplar(int indice) {
        grafica.soplar(indice,selected);
        soplar = false;
        selected = -1;
        activarJugadas(false);
        activarMano(true);
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
        selected = -1;
    }

    private void activarMano(Boolean mano,boolean activarJugadas) {
        for (JButton b : botonesMano){
            b.setEnabled(mano);
        }
        for (JButton b : cartasMesa){
            b.setEnabled(mano);
        }
        if (activarJugadas) {
            activarJugadas(true);
        }
    }
    private void activarMano(Boolean mano) {
        activarMano(mano,false);
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
        PanelConFondo pan = new PanelConFondo();
        pan.setImagen("/partidaBG.png");
        panel1 = pan;
        panel1.updateUI();
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
        soplar = false;
        selected = -1;
    }

    public void actualizarCartas(ArrayList<IJugador> jugadores) {
        crearBotonesMesa();
        crearCartasRivales(jugadores);
        crearCartasMano();
        panel1.updateUI();
    }

}
