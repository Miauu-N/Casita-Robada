package Controlador;

import Vista.Consola;

public class ControladorConsola {

    private Consola consola;

    public ControladorConsola(Consola c) {
        this.consola = c;
    }

    public void in(String text) {
        System.out.println(text);
    }
    public void update(String string){

    }
}
