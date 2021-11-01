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
public class mCategoria {

    int id;
    String nombre;
    String descripcion;
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public mCategoria() {
        
    }

    public mCategoria(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    public List listar(){
        List<mCategoria> datos=new ArrayList<>();
        String sql="select * from Categoria";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                mCategoria c=new mCategoria();
                c.setId(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setDescripcion(rs.getString(3));
                datos.add(c);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return datos;
    }
    
    public int agregar(mCategoria categoria){
        String sql="insert into categoria(nombre,descripcion) values(?,?)";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
        }
        return 1;
    }
    
    public int actualiszar (mCategoria categoria){
        int respuesta=0;
        String sql="update categoria set nombre=?,descripcion=? where id=?";
            try {
                con=conectar.getConnection();
                ps=con.prepareStatement(sql);
                ps.setString(1, categoria.getNombre());
                ps.setString(2, categoria.getDescripcion());
                ps.setInt(3,categoria.getId());
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
    
    public void delete(int id){
        String sql="delete from categoria where id="+id;
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            ps.executeUpdate();
            
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
