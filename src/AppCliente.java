
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Controlador.Controlador;
import Interfaces.iVista;
import Vista.Consola.Consola;
import Vista.Grafica.Grafica;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;

public class AppCliente {

    public static void main(String[] args) {
        ArrayList<String> ips = Util.getIpDisponibles();
        String[] tipos = new String[2];
        tipos[0] = "Consola";
        tipos[1] = "Grafica";
        String tipo = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IU en la que Jugara", "Tipo de IU",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipos,
                "127.0.0.1"
        );
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchará peticiones el cliente", "IP del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                "127.0.0.1"
        );
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchará peticiones el cliente", "Puerto del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                9999
        );
        String ipServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la corre el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                "127.0.0.1"
        );
        String portServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que corre el servidor", "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                8888
        );
        iVista vista = null;
        if (tipo.equals("Consola")) {
            vista = new Consola();
        }
        else{
            vista = new Grafica();
        }
        Controlador controlador = vista.getControlador();
        Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));
        try {
            c.iniciar(controlador);
        } catch (RemoteException | RMIMVCException e) {
            e.printStackTrace();
        }
    }

}