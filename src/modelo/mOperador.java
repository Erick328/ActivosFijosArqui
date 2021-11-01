/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Erick Vidal
 */
public class mOperador {

    int id;
    String nombre;
    String correo;
    String direccion;

    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public mOperador() {
    }

    public mOperador(int id, String nombre, String correo, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List listar() {
        List<mOperador> datos = new ArrayList<>();
        String sql = "select * from Operador";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                mOperador operador = new mOperador();
                operador.setId(rs.getInt(1));
                operador.setNombre(rs.getString(2));
                operador.setCorreo(rs.getString(3));
                operador.setDireccion(rs.getString(4));
                datos.add(operador);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return datos;
    }

    public int agregar(mOperador operador) {
        String sql = "insert into operador(nombre,correo,direccion) values (?,?,?)";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, operador.getNombre());
            ps.setString(2, operador.getCorreo());
            ps.setString(3, operador.getDireccion());
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }
        return 1;
    }

    public int actualizar(mOperador operador) {
        int respuesta = 0;
        String sql = "update operador set nombre=?,correo=?,direccion=? where id=?";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, operador.getNombre());
            ps.setString(2, operador.getCorreo());
            ps.setString(3, operador.getDireccion());
            ps.setInt(4, operador.getId());
            respuesta = ps.executeUpdate();
            if (respuesta == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return respuesta;
    }

    public void delete(int id) {
        String sql = "delete from operador where id=" + id;
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
