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
public class mRevisionTecnica {
    int id;
    Date fecha;
    int idBien;

    Conexion conectar=new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public mRevisionTecnica() {
    
    }

    
    public mRevisionTecnica(int id, Date fecha, int idBien) {
        this.id = id;
        this.fecha = fecha;
        this.idBien = idBien;
    }

    public int getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public int getIdBien() {
        return idBien;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setIdBien(int idBien) {
        this.idBien = idBien;
    }
    
    public List listar(){
        List<mRevisionTecnica> datos=new ArrayList<>();
        String sql="select * from revisiontecniva";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                mRevisionTecnica r=new mRevisionTecnica();
                r.setId(rs.getInt(1));
                r.setFecha(rs.getDate(2));
                r.setIdBien(rs.getInt(3));
                datos.add(r);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return datos;
    }
    public int agregar(mRevisionTecnica revision){
        String sql="insert into revisiontecniva(fecha,idBien) values(?,?)";
        String sql2="update bien set estado='R' where id="+revision.getIdBien();
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            ps.setDate(1, revision.getFecha());
            ps.setInt(2, revision.getIdBien());
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql2);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }
        return 1;
    }
    public int actualizar (mRevisionTecnica revision){
        int respuesta=0;
        String sql="update revisiontecniva set fecha=?,idBien=? where id=?";
            try {
                con=conectar.getConnection();
                ps=con.prepareStatement(sql);
                ps.setDate(1,(Date)revision.getFecha());
                ps.setInt(2,revision.getIdBien());
                ps.setInt(3,revision.getId());
                respuesta=ps.executeUpdate();
                if (respuesta==1) {
                    return 1;
                }else{
                    return 0;
                }
            } catch (Exception e) {
                System.err.println(e);
            }
            return respuesta;
    }
    
    public void delete(int id, int idBien){
        String sql="delete from revisiontecniva where id="+id;
        String sql2="update bien set estado='N' where id="+idBien;
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            ps.executeUpdate();
            
        } catch (Exception e) {
            System.err.println(e);
        }
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql2);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
