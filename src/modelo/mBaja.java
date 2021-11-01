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
public class mBaja {

    int id;
    Date fecha;
    String descripcion;
    int idRevisionTecnica;

    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public mBaja() {
    }

    public mBaja(int id, Date fecha, String descripcion, int idRevisionTecnica) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.idRevisionTecnica = idRevisionTecnica;
    }

    public int getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getIdRevisionTecnica() {
        return idRevisionTecnica;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdRevisionTecnica(int idRevisionTecnica) {
        this.idRevisionTecnica = idRevisionTecnica;
    }

    public List listar() {
        List<mBaja> datos = new ArrayList<>();
        String sql = "select * from baja";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                mBaja b = new mBaja();
                b.setId(rs.getInt(1));
                b.setFecha(rs.getDate(2));
                b.setDescripcion(rs.getString(3));
                b.setIdRevisionTecnica(rs.getInt(4));
                datos.add(b);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return datos;
    }

    public int agregar(mBaja baja) {
        String sql = "insert into baja(fecha,descripcion,idRevisionTecnica) values(?,?,?)";
        String sql2 = "update bien, revisiontecniva set bien.estado='B' where bien.id=revisiontecniva.idBien and revisiontecniva.id=" + baja.getIdRevisionTecnica();
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDate(1, baja.getFecha());
            ps.setString(2, baja.getDescripcion());
            ps.setInt(3, baja.getIdRevisionTecnica());
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

    public int actualizar(mBaja baja) {
        int respuesta = 0;
        String sql = "update baja set fecha=?,descripcion=?,idRevisionTecnica=? where id=?";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDate(1, (Date) baja.getFecha());
            ps.setString(2, baja.getDescripcion());
            ps.setInt(3, baja.getIdRevisionTecnica());
            ps.setInt(4, baja.getId());
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
        String sql = "delete from baja where id=" + id;
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
