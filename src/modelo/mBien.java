/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Erick Vidal
 */
public class mBien {

    int id;
    String nombre;
    int valorCompra;
    java.sql.Date fechaAdquisicion;
    int vidaUtil;
    int depreciacion;
    String estado;
    int idCategoria;

    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public mBien(int id, String nombre, int valorCompra, java.sql.Date fechaAdquisicion, int vidaUtil, int depreciacion, String estado, int idCategoria) {
        this.id = id;
        this.nombre = nombre;
        this.valorCompra = valorCompra;
        this.fechaAdquisicion = fechaAdquisicion;
        this.vidaUtil = vidaUtil;
        this.depreciacion = depreciacion;
        this.estado = estado;
        this.idCategoria = idCategoria;
    }

    public mBien() {
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getValorCompra() {
        return valorCompra;
    }

    public java.sql.Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public int getVidaUtil() {
        return vidaUtil;
    }

    public int getDepreciacion() {
        return depreciacion;
    }

    public String getEstado() {
        return estado;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setValorCompra(int valorCompra) {
        this.valorCompra = valorCompra;
    }

    public void setFechaAdquisicion(java.sql.Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public void setVidaUtil(int vidaUtil) {
        this.vidaUtil = vidaUtil;
    }

    public void setDepreciacion(int depreciacion) {
        this.depreciacion = depreciacion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return "mBien{" + "id=" + id + ", nombre=" + nombre + '}';
    }
    
    
    public List listar() {
        List<mBien> datos = new ArrayList<>();
        String sql = "select * from bien";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                mBien b = new mBien();
                b.setId(rs.getInt(1));
                b.setNombre(rs.getString(2));
                b.setValorCompra(rs.getInt(3));
                b.setFechaAdquisicion(rs.getDate(4));
                b.setVidaUtil(rs.getInt(5));
                b.setDepreciacion(rs.getInt(6));
                b.setEstado(rs.getString(7));
                b.setIdCategoria(rs.getInt(8));
                datos.add(b);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return datos;
    }

    public int agregar(mBien bien) {
        String sql = "insert into bien(nombre,valorCompra,fechaAdquisicion,vidaUtil,depreciacion,idCategoria) values(?,?,?,?,?,?)";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, bien.getNombre());
            ps.setInt(2, bien.getValorCompra());
            ps.setDate(3, bien.getFechaAdquisicion());
            ps.setInt(4, bien.getVidaUtil());
            ps.setInt(5, bien.getDepreciacion());
            ps.setInt(6, bien.getIdCategoria());

            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }
        return 1;
    }

    public int actualizar(mBien bien) {
        int respuesta = 0;
        String sql = "update bien set nombre=?,valorCompra=?,fechaAdquisicion=?,vidaUtil=?,depreciacion=?,estado=?,idCategoria=? where id=?";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, bien.getNombre());
            ps.setInt(2, bien.getValorCompra());
            ps.setDate(3, (Date) bien.getFechaAdquisicion());
            ps.setInt(4, bien.getVidaUtil());
            ps.setInt(5, bien.getDepreciacion());
            ps.setString(6, bien.getEstado());
            ps.setInt(7, bien.getIdCategoria());
            ps.setInt(8, bien.getId());
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
        String sql = "delete from bien where id=" + id;
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
