import Logica.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class main {


    public static void main(String[] args) {
        ArrayList<Serie> serie = null;
        try {
            Series series = new Series();
            serie = series.Read();
        } catch (SQLException e) {
        }
        SeriesExistentes ser = new SeriesExistentes(serie);
        Controladora controladora = new Controladora();
        while (true) {
            int numeroPuerto = 6000; // Puerto
            String ip = "192.168.1.114";
            ServerSocket servidor = null;
            try {
                Usuario_Serie usuario_serie = null;
                servidor = new ServerSocket();
                InetSocketAddress addr = new InetSocketAddress(ip, numeroPuerto);
                servidor.bind(addr);

                Socket cliente = servidor.accept();

                ObjectInputStream inObjeto = new ObjectInputStream(cliente.getInputStream());
                Object o = inObjeto.readObject();
                Usuario log_reg = null;
                int val = 1;
                try {
                    log_reg = (Usuario) o;
                } catch (Exception ex) {
                    try {
                        usuario_serie = (Usuario_Serie) o;
                    } catch (Exception ex2) {
                        val = (int) o;
                    }
                }
                if (log_reg != null) {
                    if (log_reg.isResgistro()) {

                        boolean existe = controladora.existenciaUsuario(log_reg);
                        if (!existe) {
                            controladora.insertarUsuario(log_reg);
                        }
                        ObjectOutputStream perSal = new ObjectOutputStream(cliente.getOutputStream());
                        perSal.writeObject(existe);
                    } else {
                        if (log_reg.getContraseña().equals("forget")) {
                            ObjectInputStream pass = new ObjectInputStream(cliente.getInputStream());
                            Object newPass = pass.readObject();
                            boolean acutalizacion = controladora.actualizarPass(newPass.toString(), log_reg.getUsuario());
                            ObjectOutputStream perSal = new ObjectOutputStream(cliente.getOutputStream());
                            perSal.writeObject(acutalizacion);
                        } else {
                            Usuario usuario_existente = controladora.usuarioCompleto(log_reg);
                            ObjectOutputStream perSal = new ObjectOutputStream(cliente.getOutputStream());
                            perSal.writeObject(usuario_existente);

                            ObjectOutputStream serSal = new ObjectOutputStream(cliente.getOutputStream());
                            serSal.writeObject(ser);
                            serSal.close();
                            perSal.close();
                        }
                    }
                } else if (usuario_serie != null) {
                    boolean valor = controladora.insertarApreciacionUsuario_Serie(usuario_serie);
                    ObjectOutputStream perSal = new ObjectOutputStream(cliente.getOutputStream());
                    perSal.writeObject(valor);
                    perSal.close();
                } else {
                    int valoraciones;
                    if (val == 0) {
                        valoraciones = controladora.contadorLikes();
                    } else {
                        valoraciones = controladora.contadorDislikes();
                    }
                    ObjectOutputStream perSal = new ObjectOutputStream(cliente.getOutputStream());
                    perSal.writeObject(valoraciones);
                    perSal.close();
                }

                inObjeto.close();
                cliente.close();
                servidor.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


}
