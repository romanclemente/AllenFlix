package Logica;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class Controladora {
    private BBDD_SQL bbdd_sql;

    public Controladora() {
        try {
            this.bbdd_sql = new BBDD_SQL();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void insertarUsuario(Usuario usuario) {


        try {
            bbdd_sql.Create(usuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public boolean existenciaUsuario(Usuario usuario) {
        boolean existe;
        String condicion = " WHERE usuarios.Correo_electronico=\"" + usuario.getCorreo() + "\" OR usuarios.Nombre_Usuario=\"" + usuario.getUsuario() + "\"";
        Usuario respuesta = null;
        try {
            respuesta = bbdd_sql.Read(condicion);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (respuesta == null) {
            existe = false;
        } else {
            existe = true;
        }
        return existe;
    }

    public Usuario usuarioCompleto(Usuario usuario) {
        String condicion = "WHERE usuarios.Nombre_Usuario = \"" + usuario.getUsuario() + "\" AND usuarios.Contraseña= \"" + usuario.getContraseña() + "\"";
        try {
            return bbdd_sql.Read(condicion);
        } catch (SQLException e) {
            return null;
        }

    }

    public boolean insertarApreciacionUsuario_Serie(Usuario_Serie usuario_serie) {
        try {
            bbdd_sql.CreateUserSerie(usuario_serie);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public int contadorLikes() {
        int likes = 0;
        String condicion = "WHERE series_usuarios.Like = true";
        try {
            likes = bbdd_sql.contadorValoraciones(condicion);
        } catch (SQLException e) {

        }
        return likes;
    }

    public int contadorDislikes() {
        int dislikes = 0;
        String condicion = "WHERE series_usuarios.Dislike = true";
        try {
            dislikes = bbdd_sql.contadorValoraciones(condicion);
        } catch (SQLException e) {

        }
        return dislikes;
    }

    public boolean actualizarPass(String toString, String usuario) {
        try {
            bbdd_sql.update(toString, usuario);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
