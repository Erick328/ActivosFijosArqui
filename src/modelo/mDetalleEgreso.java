/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Erick Vidal
 */
public class mDetalleEgreso {
    int idEgreso;
    int idBien;

    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    
    public mDetalleEgreso() {
    }

    public mDetalleEgreso(int idEgreso, int idBien) {
        this.idEgreso = idEgreso;
        this.idBien = idBien;
    }

    public int getIdEgreso() {
        return idEgreso;
    }

    public int getIdBien() {
        return idBien;
    }

    public void setIdEgreso(int idEgreso) {
        this.idEgreso = idEgreso;
    }

    public void setIdBien(int idBien) {
        this.idBien = idBien;
    }
    
    public int agregar(mDetalleEgreso detalleEgreso) {
        String sql = "insert into detalleegreso(idEgreso,idBien) values(?,?)";
        String sql2 = "update bien set bien.estado='E' where bien.id=" + detalleEgreso.getIdBien();
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, detalleEgreso.getIdEgreso());
            ps.setInt(2, detalleEgreso.getIdBien());
            ps.executeUpdate();
            ps=con.prepareStatement(sql2);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("mDetalle"+e);
        }
        return 1;
    }
    public void delete(int id) {
        String sql = "delete from detalleegreso where idEgreso=" + id;
        //String sql2 = "update bien set bien.estado='N' where bien.id=" + id;
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
