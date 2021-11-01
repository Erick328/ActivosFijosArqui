/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.mBien;
//import modelo.mBienDAO;
import modelo.mRevisionTecnica;
//import modelo.mRevisionTecnicaDAO;
import vista.vRevisionTecnica;

/**
 *
 * @author Erick Vidal
 */
public class cRevisionTecnica implements ActionListener {
    //mRevisionTecnicaDAO dao=new mRevisionTecnicaDAO();
    mRevisionTecnica revision=new mRevisionTecnica();
    vRevisionTecnica vista= new vRevisionTecnica();
    mBien bien=new mBien();
    ArrayList<mBien> listaBien=(ArrayList<mBien>) bien.listar();
    DefaultTableModel modelo= new DefaultTableModel();
    
    public cRevisionTecnica(vRevisionTecnica v){
        this.vista=v;
        //this.vista.btnListar.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        listar(vista.Tabla);
        llenarBien();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==vista.btnGuardar) {
            agregar();
            limpiarTabla();
            listar(vista.Tabla);
        }
        if (e.getSource()==vista.btnEditar) {
            int fila=vista.Tabla.getSelectedRow();
            if (fila==-1) {
                JOptionPane.showMessageDialog(vista, "Debe Seleccionar una fila");
            }else{
                int id=Integer.parseInt((String)vista.Tabla.getValueAt(fila, 0).toString());
                Date fecha=(Date)vista.Tabla.getValueAt(fila, 1);
                vista.txtId.setText(""+id);
                vista.txtFecha.setDate(fecha);
            }
        }
        if (e.getSource()==vista.btnActualizar) {
            actualizar();
            limpiarTabla();
            listar(vista.Tabla);
        }
        if (e.getSource()==vista.btnEliminar){
            delete();
            limpiarTabla();
            listar(vista.Tabla);
        }
    }

    private void agregar() {
        Date fecha=vista.txtFecha.getDate();
        int idBien=buscar(vista.cboBien.getItemAt(vista.cboBien.getSelectedIndex()));
        revision.setFecha(new java.sql.Date(fecha.getYear(), fecha.getMonth(), fecha.getDay()));
        revision.setIdBien(idBien);
        
        int respuesta=revision.agregar(revision);
        if (respuesta==1) {
            JOptionPane.showMessageDialog(vista, "REVISION TECNICA AGREGADA CON EXITO");
        }else{
            JOptionPane.showMessageDialog(vista, "ERROR, POR FAVOR INTENTE NUEVAMENTE");    
        }
    }

    private void limpiarTabla() {
        for (int i = 0; i < vista.Tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i=i-1;
        }
    }

    private void listar(JTable Tabla) {
        modelo=(DefaultTableModel)Tabla.getModel();
        List<mRevisionTecnica> lista=revision.listar();
        Object[]object=new Object[3];
        for (int i = 0; i < lista.size(); i++) {
            object[0]=lista.get(i).getId();
            object[1]=lista.get(i).getFecha();
            object[2]=lista.get(i).getIdBien();
            modelo.addRow(object);
        }
    }

    private void actualizar() {
        int id=Integer.parseInt(vista.txtId.getText());
        Date fecha=vista.txtFecha.getDate();
        int idBien=buscar(vista.cboBien.getItemAt(vista.cboBien.getSelectedIndex()));
        revision.setId(id);
        revision.setFecha(new java.sql.Date(fecha.getYear(),fecha.getMonth(),fecha.getDay()));
        revision.setIdBien(idBien);
        
        int respuesta=revision.actualizar(revision);
        if (respuesta==1) {
            JOptionPane.showMessageDialog(vista, "REVISION TECNICA ACTUALIZADA CON EXITO");
        }else{
            JOptionPane.showMessageDialog(vista, "ERROR, POR FAVOR INTENTELO NUEVAMENTE");
        }
    }

    private void delete() {
        int fila=vista.Tabla.getSelectedRow();
        if (fila==-1) {
                JOptionPane.showMessageDialog(vista, "DEBE SELECIONAR UNA FILA");
        }else{
            int id=Integer.parseInt((String)vista.Tabla.getValueAt(fila, 0).toString());
            int idBien=Integer.parseInt((String)vista.Tabla.getValueAt(fila, 2).toString());
            revision.delete(id,idBien);
            JOptionPane.showMessageDialog(vista, "REVISION TECNICA ELIMINADA CON EXITO");
        }
    }

    private void llenarBien() {
        vista.cboBien.removeAllItems();
        for (int i = 0; i < listaBien.size(); i++) {
            vista.cboBien.addItem(listaBien.get(i).getNombre());
        }
    }

    private int buscar(String itemAt) {
        for (int i = 0; i < listaBien.size(); i++) {
            if (listaBien.get(i).getNombre() == null ? itemAt == null : listaBien.get(i).getNombre().equals(itemAt)) {
                return listaBien.get(i).getId();
            }
        }
        return -1;
    }
}
