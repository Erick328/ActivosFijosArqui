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
public class mMantenimiento {

    int id;
    String problema;
    String solucion;
    Date fechaInicion;
    Date fechaFin;
    int costo;
    int idRevisionTecnica;

    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public mMantenimiento() {
    }

    public mMantenimiento(int id, String problema, String solucion, Date fechaInicion, Date fechaFin, int costo, int idRevisionTecnica) {
        this.id = id;
        this.problema = problema;
        this.solucion = solucion;
        this.fechaInicion = fechaInicion;
        this.fechaFin = fechaFin;
        this.costo = costo;
        this.idRevisionTecnica = idRevisionTecnica;
    }

    public int getId() {
        return id;
    }

    public String getProblema() {
        return problema;
    }

    public String getSolucion() {
        return solucion;
    }

    public Date getFechaInicion() {
        return fechaInicion;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public int getCosto() {
        return costo;
    }

    public int getIdRevisionTecnica() {
        return idRevisionTecnica;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public void setFechaInicion(Date fechaInicion) {
        this.fechaInicion = fechaInicion;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public void setIdRevisionTecnica(int idRevisionTecnica) {
        this.idRevisionTecnica = idRevisionTecnica;
    }

    public List listar() {
        List<mMantenimiento> datos = new ArrayList<>();
        String sql = "select * from mantenimiento";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                mMantenimiento m = new mMantenimiento();
                m.setId(rs.getInt(1));
                m.setProblema(rs.getString(2));
                m.setSolucion(rs.getString(3));
                m.setFechaInicion(rs.getDate(4));
                m.setFechaFin(rs.getDate(5));
                m.setCosto(rs.getInt(6));
                m.setIdRevisionTecnica(rs.getInt(7));
                datos.add(m);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return datos;
    }

    public int agregar(mMantenimiento mantenimiento) {
        String sql = "insert into mantenimiento(problema,solucion,fechaInicion,fechaFin,costo,idRevisionTecnica) values(?,?,?,?,?,?)";
        String sql2 = "update bien, revisiontecniva set bien.estado='M' where bien.id=revisiontecniva.idBien and revisiontecniva.id=" + mantenimiento.getIdRevisionTecnica();
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, mantenimiento.getProblema());
            ps.setString(2, mantenimiento.getSolucion());
            ps.setDate(3, mantenimiento.getFechaInicion());
            ps.setDate(4, mantenimiento.getFechaFin());
            ps.setInt(5, mantenimiento.getCosto());
            ps.setInt(6, mantenimiento.getIdRevisionTecnica());

            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql2);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }
        return 1;
    }

    public int actualizar(mMantenimiento mantenimiento) {
        int respuesta = 0;
        String sql = "update mantenimiento set problema=?,solucion=?,fechaInicion=?,fechaFin=?,costo=?,idRevisionTecnica=? where id=?";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, mantenimiento.getProblema());
            ps.setString(2, mantenimiento.getSolucion());
            ps.setDate(3, (Date) mantenimiento.getFechaInicion());
            ps.setDate(4, (Date) mantenimiento.getFechaFin());
            ps.setInt(5, mantenimiento.getCosto());
            ps.setInt(6, mantenimiento.getIdRevisionTecnica());
            ps.setInt(7, mantenimiento.getId());
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

    public void delete(int id, int idRevisionTecnica) {
        String sql = "delete from mantenimiento where id=" + id;
        String sql2 = "update bien,revisiontecniva set bien.estado='R' where bien.id=revisiontecniva.idBien and revisiontecniva.id=" + idRevisionTecnica;
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println(e);
        }
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql2);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
