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
public class mEgreso {
    int id;
    Date fechaEgreso;
    String descripcion;
    int idOperador;

    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public mEgreso() {
    }

    public mEgreso(int id, Date fechaEgreso, String descripcion, int idOperador) {
        this.id = id;
        this.fechaEgreso = fechaEgreso;
        this.descripcion = descripcion;
        this.idOperador = idOperador;
    }

    public int getId() {
        return id;
    }

    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getIdOperador() {
        return idOperador;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdOperador(int idOperador) {
        this.idOperador = idOperador;
    }
    
    public List listar() {
        List<mEgreso> datos = new ArrayList<>();
        String sql = "select * from egreso";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                mEgreso e = new mEgreso();
                e.setId(rs.getInt(1));
                e.setFechaEgreso(rs.getDate(2));
                e.setDescripcion(rs.getString(3));
                e.setIdOperador(rs.getInt(4));
                datos.add(e);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return datos;
    }

    public int agregar(mEgreso egreso) {
        String sql = "insert into egreso(fechaEgreso,descripcion,idOperador) values(?,?,?)";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDate(1, egreso.getFechaEgreso());
            ps.setString(2, egreso.getDescripcion());
            ps.setInt(3, egreso.getIdOperador());
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("mEgresos"+e);
        }
        return 1;
    }

    public int actualizar(mEgreso egreso) {
        int respuesta = 0;
        String sql = "update Egreso set fechaEgreso=?,descripcion=?,idOperador=? where id=?";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDate(1, egreso.getFechaEgreso());
            ps.setString(2, egreso.getDescripcion());
            ps.setInt(3, egreso.getIdOperador());
            ps.setInt(4, egreso.getId());
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
        String sql = "delete from egreso where id=" + id;
        //String sql2 = "update bien set bien.estado='N' where bien.id=" + idBien;
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
