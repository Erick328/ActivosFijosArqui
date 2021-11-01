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
public class mIngreso {

    int id;
    Date fechaIngreso;
    String descripcion;
    int idOperador;
    //List<mBien> listaBien = new ArrayList<>();
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public mIngreso() {
    }

    public mIngreso(int id, Date fechaIngreso, String descripcion, int idOperador) {
        this.id = id;
        this.fechaIngreso = fechaIngreso;
        this.descripcion = descripcion;
        this.idOperador = idOperador;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdOperador(int idOperador) {
        this.idOperador = idOperador;
    }

    public int getId() {
        return id;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getIdOperador() {
        return idOperador;
    }
    
    
    public List listar() {
        List<mIngreso> datos = new ArrayList<>();
        String sql = "select * from ingreso";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                mIngreso i = new mIngreso();
                i.setId(rs.getInt(1));
                i.setFechaIngreso(rs.getDate(2));
                i.setDescripcion(rs.getString(3));
                i.setIdOperador(rs.getInt(4));
                datos.add(i);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return datos;
    }

    public int agregar(mIngreso ingreso) {
        String sql = "insert into ingreso(fechaIngreso,descripcion,idOperador) values(?,?,?)";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDate(1, ingreso.getFechaIngreso());
            ps.setString(2, ingreso.getDescripcion());
            ps.setInt(3, ingreso.getIdOperador());
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }
        return 1;
    }

    public int actualizar(mIngreso ingreso) {
        int respuesta = 0;
        String sql = "update ingreso set fechaIngreso=?,descripcion=?,idOperador=? where id=?";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDate(1, ingreso.getFechaIngreso());
            ps.setString(2, ingreso.getDescripcion());
            ps.setInt(3, ingreso.getIdOperador());
            ps.setInt(4, ingreso.getId());
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
        String sql = "delete from detalleingreso where id=" + id;
        //String sql2 = "update bien set bien.estado='R' where bien.id=" + idBien;
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            //ps=con.prepareStatement(sql2);
            //ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
