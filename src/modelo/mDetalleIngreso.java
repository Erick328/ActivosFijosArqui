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
public class mDetalleIngreso {
    int idIngreso;
    int idBien;

    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public mDetalleIngreso() {
    }

    public mDetalleIngreso(int idIngreso, int idBien) {
        this.idIngreso = idIngreso;
        this.idBien = idBien;
    }

    public int getIdIngreso() {
        return idIngreso;
    }

    public int getIdBien() {
        return idBien;
    }

    public void setIdIngreso(int idIngreso) {
        this.idIngreso = idIngreso;
    }

    public void setIdBien(int idBien) {
        this.idBien = idBien;
    }
    
    public List listar() {
        List<mDetalleIngreso> datos = new ArrayList<>();
        String sql = "select * from detalleingreso";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                mDetalleIngreso di = new mDetalleIngreso();
                di.setIdIngreso(rs.getInt(1));
                di.setIdBien(rs.getInt(2));
                datos.add(di);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return datos;
    }
    
    public int agregar(mDetalleIngreso detalleIngreso) {
        String sql = "insert into ingreso(fechaIngreso,descripcion,idOperador) values(?,?,?)";
        String sql2 = "update bien set bien.estado='I' where bien.id=" + detalleIngreso.getIdBien();
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, detalleIngreso.getIdIngreso());
            ps.setInt(2, detalleIngreso.getIdBien());
            ps.executeUpdate();
            ps=con.prepareStatement(sql2);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }
        return 1;
    }
    public void delete(int id) {
        //String sql = "delete from detalleingreso where id=" + id;
        String sql2 = "update bien set bien.estado='N' where bien.id=" + id;
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql2);
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
