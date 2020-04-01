package Logica;

import java.io.Serializable;

public class Usuario implements Serializable {
    String correo, usuario, telefono, contraseña;
    boolean recibir, resgistro;

    public Usuario(String usuario, String contraseña) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.resgistro = false;
    }

//https://api.themoviedb.org/3/tv/popular?api_key=4ef66e12cddbb8fe9d4fd03ac9632f6e&language=en-US&page=1

    public Usuario(String correo, String usuario, String telefono, String contraseña, boolean recibir) {
        this.correo = correo;
        this.usuario = usuario;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.recibir = recibir;
        this.resgistro = true;
    }
    public boolean isResgistro() {
        return resgistro;
    }
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public boolean isRecibir() {
        return recibir;
    }

    public void setRecibir(boolean recibir) {
        this.recibir = recibir;
    }

    @Override
    public String toString() {
        return String.format("%s", getUsuario());
    }
}
