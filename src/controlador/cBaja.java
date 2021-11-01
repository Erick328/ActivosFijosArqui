/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.mBaja;
import modelo.mRevisionTecnica;
import vista.vBaja;

/**
 *
 * @author Erick Vidal
 */
public class cBaja implements ActionListener{
    mBaja baja=new mBaja();
    vBaja vista= new vBaja();
    mRevisionTecnica revision=new mRevisionTecnica();
    ArrayList<mRevisionTecnica> listaRevision=(ArrayList<mRevisionTecnica>) revision.listar();
    DefaultTableModel modelo= new DefaultTableModel();
    
    
    public cBaja(vBaja v){
        this.vista=v;
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        listar(vista.Tabla);
        llenarRevisionTecnica();
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
                String descripcion=vista.Tabla.getValueAt(fila, 2).toString();
                
                vista.txtId.setText(""+id);
                vista.txtFecha.setDate(fecha);
                vista.txtDescripcion.setText(descripcion);
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

    private void listar(JTable Tabla) {
        modelo=(DefaultTableModel)Tabla.getModel();
        List<mBaja> lista=baja.listar();
        Object[]object=new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            object[0]=lista.get(i).getId();
            object[1]=lista.get(i).getFecha();
            object[2]=lista.get(i).getDescripcion();
            object[3]=lista.get(i).getIdRevisionTecnica();
            modelo.addRow(object);
        }
    }

    private void llenarRevisionTecnica() {
        vista.cboRevisionTecnica.removeAllItems();
        for (int i = 0; i < listaRevision.size(); i++) {
            vista.cboRevisionTecnica.addItem(listaRevision.get(i).getId());
        }
    }

    private void agregar() {
        Date fecha=vista.txtFecha.getDate();
        String descripcion=vista.txtDescripcion.getText();
        int idRevisionTecnica=buscar(vista.cboRevisionTecnica.getItemAt(vista.cboRevisionTecnica.getSelectedIndex()));
        baja.setFecha(new java.sql.Date(fecha.getYear(), fecha.getMonth(), fecha.getDay()));
        baja.setDescripcion(descripcion);
        baja.setIdRevisionTecnica(idRevisionTecnica);
        
        int respuesta=baja.agregar(baja);
        if (respuesta==1) {
            JOptionPane.showMessageDialog(vista, "BAJA AGREGADA CON EXITO");
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

    private void actualizar() {
        int id=Integer.parseInt(vista.txtId.getText());
        String descripcion=vista.txtDescripcion.getText();
        Date fecha=vista.txtFecha.getDate();
        int idRevisionTecnica=buscar(vista.cboRevisionTecnica.getItemAt(vista.cboRevisionTecnica.getSelectedIndex()));
        baja.setId(id);
        baja.setDescripcion(descripcion);
        baja.setFecha(new java.sql.Date(fecha.getYear(), fecha.getMonth(), fecha.getDay()));
        baja.setIdRevisionTecnica(idRevisionTecnica);
        
        int respuesta=baja.actualizar(baja);
        if (respuesta==1) {
            JOptionPane.showMessageDialog(vista, "BAJA ACTUALIZADA CON EXITO");
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
            int idRevisionTecnica=Integer.parseInt((String)vista.Tabla.getValueAt(fila, 3).toString());
            baja.delete(id,idRevisionTecnica);
            JOptionPane.showMessageDialog(vista, "BAJA ELIMINADA CON EXITO");
        }
    }

    private int buscar(Integer itemAt) {
        for (int i = 0; i < listaRevision.size(); i++) {
            if (listaRevision.get(i).getId()==itemAt){
                return listaRevision.get(i).getId();
            }
        }
        return -1;
    }
    
}
