package Vista;

import Controlador.ControladorConsola;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Consola {
    private ControladorConsola controlador;

    public void play() {
        controlador = new ControladorConsola(this);
        JFrame principal = new JFrame("Casita robada");
        principal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        principal.setSize(1920,1080);
        principal.setVisible(true);
        principal.setLayout(new GridLayout(3,1));
        JLabel cartel = new JLabel("A");
        JTextField in = new JTextField("A");
        JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.in(in.getText());
                in.setText("");
            }
        });
        principal.add(cartel);
        principal.add(in);
        principal.add(ok);
    }
}
