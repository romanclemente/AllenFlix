package Logica;

import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

public class BBDD {
    SeriesExistentes ser;
    Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public BBDD() {
    }


    public boolean comprobarLoggin(Usuario log) {
        Usuario usuariocompleto = null;
        String Host = "192.168.1.114";
        int Puerto = 6000;
        try {
            Socket cliente = new Socket(Host, Puerto);
            ObjectOutputStream perSal = new ObjectOutputStream(cliente.getOutputStream());
            perSal.writeObject(log);

            ObjectInputStream perEnt = new ObjectInputStream(cliente.getInputStream());
            usuariocompleto = (Usuario) perEnt.readObject();

            ObjectInputStream serEnt = new ObjectInputStream(cliente.getInputStream());
            ser = (SeriesExistentes) serEnt.readObject();
            perEnt.close();
            perSal.close();
            cliente.close();

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        if (usuariocompleto != null) {
            usuario = usuariocompleto;
            return true;
        } else {
            return false;
        }

    }

    public boolean comprobarConcidencia(Usuario reg) {
        Usuario usuariocompleto = null;
        boolean existencia = false;
        String Host = "192.168.1.114";
        int Puerto = 6000;
        try {
            Socket cliente = new Socket(Host, Puerto);

            ObjectOutputStream perSal = new ObjectOutputStream(cliente.getOutputStream());
            perSal.writeObject(reg);

            ObjectInputStream perEnt = new ObjectInputStream(cliente.getInputStream());
            existencia = (Boolean) perEnt.readObject();
            perEnt.close();
            perSal.close();
            cliente.close();

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return existencia;

    }

    public boolean enviarApreciacion(Usuario_Serie usuario_serie) {
        boolean validado = false;
        String Host = "192.168.1.114";
        int Puerto = 6000;
        try {
            Socket cliente = new Socket(Host, Puerto);

            ObjectOutputStream perSal = new ObjectOutputStream(cliente.getOutputStream());
            perSal.writeObject(usuario_serie);

            ObjectInputStream perEnt = new ObjectInputStream(cliente.getInputStream());
            Object o = perEnt.readObject();
            validado = (boolean) o;
            perEnt.close();

            perSal.close();
            cliente.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return validado;
    }

    public SeriesExistentes getSer() {
        return ser;
    }

    public void setSer(SeriesExistentes ser) {
        this.ser = ser;
    }


    public int seriesLike() {
        String Host = "192.168.1.114";
        int Puerto = 6000;
        int liked = 0;
        try {
            Socket cliente = new Socket(Host, Puerto);

            ObjectOutputStream perSal = new ObjectOutputStream(cliente.getOutputStream());
            perSal.writeObject(liked);

            ObjectInputStream perEnt = new ObjectInputStream(cliente.getInputStream());
            Object o = perEnt.readObject();
            liked = (int) o;
            perEnt.close();
            perSal.close();
            cliente.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return liked;
    }

    public int seriesDislike() {
        String Host = "192.168.1.114";
        int Puerto = 6000;
        int disliked = -1;
        try {
            Socket cliente = new Socket(Host, Puerto);

            ObjectOutputStream perSal = new ObjectOutputStream(cliente.getOutputStream());
            perSal.writeObject(disliked);

            ObjectInputStream perEnt = new ObjectInputStream(cliente.getInputStream());
            Object o = perEnt.readObject();
            disliked = (int) o;
            perEnt.close();
            perSal.close();
            cliente.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return disliked;
    }

    public boolean recuperarPass(Usuario usuario) {
        boolean actualizacion = false;
        String Host = "192.168.1.114";
        int Puerto = 6000;
        try {
            Socket cliente = new Socket(Host, Puerto);
            ObjectOutputStream perSal = new ObjectOutputStream(cliente.getOutputStream());
            perSal.writeObject(usuario);

            TextInputDialog dialog = new TextInputDialog("default");
            dialog.setTitle("Nueva contraseña");
            dialog.setHeaderText("Escriba la nueva contraseña, por favor");
            dialog.setContentText("Contaseña:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String newPass = result.get();
                ObjectOutputStream pass = new ObjectOutputStream(cliente.getOutputStream());
                pass.writeObject(newPass);
            } else {
                String newPass = dialog.getDefaultValue();
                ObjectOutputStream pass = new ObjectOutputStream(cliente.getOutputStream());
                pass.writeObject(newPass);
            }
            ObjectInputStream perEnt = new ObjectInputStream(cliente.getInputStream());
            Object o = perEnt.readObject();
             actualizacion = (boolean) o;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return actualizacion;
    }
}
