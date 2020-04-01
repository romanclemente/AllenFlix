package Logica;

import java.sql.*;
import java.util.ArrayList;

public class BBDD_SQL {
    private Connection conexion = null;
    private ArrayList<Usuario> persona;

    public BBDD_SQL() throws SQLException {
        conexion = DriverManager.getConnection("jdbc:mysql://localhost/allenflix", "root", "");
    }

    public int Create(Usuario u) throws SQLException {
        int filas;
        String sql = "INSERT INTO `usuarios`(`Correo_electronico`, `Nombre_Usuario`, `Contraseña`, `Telefono`, `Recibir_info`) VALUES(?,?,?,?,?)";

        PreparedStatement insert = conexion.prepareStatement(sql);

        insert.setString(1, u.getCorreo());
        insert.setString(2, u.getUsuario());
        insert.setString(3, u.getContraseña());
        insert.setString(4, u.getTelefono());
        insert.setBoolean(5, u.isRecibir());

        filas = insert.executeUpdate();
        return filas;
    }

    public Usuario update(String contrasenia, String nombre) throws SQLException {
        String sql = "UPDATE `usuarios` SET `Contraseña`= \"" + contrasenia + "\" WHERE usuarios.Nombre_Usuario = ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setString(1, nombre);
        int rs = ps.executeUpdate();
        return null;
    }

    public Usuario Read(String condiciones) throws SQLException {

        PreparedStatement ps = conexion.prepareStatement("SELECT * FROM usuarios " + condiciones);
        ResultSet rs = ps.executeQuery();
        Usuario p = null;
        while (rs.next()) {
            String contraseña = rs.getString("Contraseña");
            String correo = rs.getString("Correo_electronico");
            String nombre = rs.getString("Nombre_Usuario");
            boolean info = rs.getBoolean("Recibir_info");
            String telefono = rs.getString("Telefono");

            p = new Usuario(correo, nombre, telefono, contraseña, info);
        }

        return p;
    }

    public Usuario delete(Integer Clave) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE Clave = ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setInt(1, Clave);
        int rs = ps.executeUpdate();
        return null;
    }

    public void Close() throws SQLException {
        conexion.close();
    }

    public int CreateUserSerie(Usuario_Serie usuario_serie) throws SQLException {
        int filas;
        String sql = "INSERT INTO `series_usuarios`(`Correo_electronico`, `Name`, `Like`, `Dislike`) VALUES (?,?,?,?)";

        PreparedStatement insert = conexion.prepareStatement(sql);

        insert.setString(1, usuario_serie.getUser());
        insert.setString(2, usuario_serie.getName_serie());
        insert.setBoolean(3, usuario_serie.isLike());
        insert.setBoolean(4, usuario_serie.isDislike());


        filas = insert.executeUpdate();
        return filas;
    }

    public int contadorValoraciones(String condicion) throws SQLException {
        String sentencia = "SELECT COUNT(series_usuarios.Name) as \"numero\" FROM series_usuarios " + condicion;
        PreparedStatement ps = conexion.prepareStatement(sentencia);
        ResultSet rs = ps.executeQuery();
        int conteo = 0;
        while (rs.next()) {
            conteo = rs.getInt("numero");
        }

        return conteo;
    }
}
