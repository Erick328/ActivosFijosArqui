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
import modelo.mMantenimiento;
import modelo.mRevisionTecnica;
import vista.vMantenimiento;

/**
 *
 * @author Erick Vidal
 */
public class cMantenimiento implements ActionListener{
    mMantenimiento mantenimiento=new mMantenimiento();
    vMantenimiento vista= new vMantenimiento();
    mRevisionTecnica revision=new mRevisionTecnica();
    ArrayList<mRevisionTecnica> listaRevision=(ArrayList<mRevisionTecnica>) revision.listar();
    DefaultTableModel modelo= new DefaultTableModel();
    
    
    public cMantenimiento(vMantenimiento v){
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
                String problema=vista.Tabla.getValueAt(fila, 1).toString();
                String solucion=vista.Tabla.getValueAt(fila, 2).toString();
                Date fechaInicio=(Date)vista.Tabla.getValueAt(fila, 3);
                Date fechaFin=(Date)vista.Tabla.getValueAt(fila, 4);
                int costo=Integer.parseInt((String)vista.Tabla.getValueAt(fila, 5).toString());
                
                vista.txtId.setText(""+id);
                vista.txtProblema.setText(problema);
                vista.txtSolucion.setText(solucion);
                vista.txtFechaInicio.setDate(fechaInicio);
                vista.txtFechaFin.setDate(fechaFin);
                vista.txtCosto.setText(""+costo);
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
        String problema=vista.txtProblema.getText();
        String solucion=vista.txtSolucion.getText();
        Date fechaInicio=vista.txtFechaInicio.getDate();
        Date fechaFin=vista.txtFechaFin.getDate();
        int costo=Integer.parseInt((String)vista.txtCosto.getText());
        int idRevisionTecnica=buscar(vista.cboRevisionTecnica.getItemAt(vista.cboRevisionTecnica.getSelectedIndex()));
        mantenimiento.setProblema(problema);
        mantenimiento.setSolucion(solucion);
        mantenimiento.setFechaInicion(new java.sql.Date(fechaInicio.getYear(), fechaInicio.getMonth(), fechaInicio.getDay()));
        mantenimiento.setFechaFin(new java.sql.Date(fechaFin.getYear(), fechaFin.getMonth(), fechaFin.getDay()));
        mantenimiento.setCosto(costo);
        mantenimiento.setIdRevisionTecnica(idRevisionTecnica);
        
        int respuesta=mantenimiento.agregar(mantenimiento);
        if (respuesta==1) {
            JOptionPane.showMessageDialog(vista, "MANTENIMIENTO AGREGADA CON EXITO");
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
        List<mMantenimiento> lista=mantenimiento.listar();
        Object[]object=new Object[7];
        for (int i = 0; i < lista.size(); i++) {
            object[0]=lista.get(i).getId();
            object[1]=lista.get(i).getProblema();
            object[2]=lista.get(i).getSolucion();
            object[3]=lista.get(i).getFechaInicion();
            object[4]=lista.get(i).getFechaFin();
            object[5]=lista.get(i).getCosto();
            object[6]=lista.get(i).getIdRevisionTecnica();
            modelo.addRow(object);
        }
    }

    private void actualizar() {
        int id=Integer.parseInt(vista.txtId.getText());
        String problema=vista.txtProblema.getText();
        String solucion=vista.txtSolucion.getText();
        Date fechaInicio=vista.txtFechaInicio.getDate();
        Date fechaFin=vista.txtFechaFin.getDate();
        int costo=Integer.parseInt((String)vista.txtCosto.getText());
        int idRevisionTecnica=buscar(vista.cboRevisionTecnica.getItemAt(vista.cboRevisionTecnica.getSelectedIndex()));
        mantenimiento.setId(id);
        mantenimiento.setProblema(problema);
        mantenimiento.setSolucion(solucion);
        mantenimiento.setFechaInicion(new java.sql.Date(fechaInicio.getYear(), fechaInicio.getMonth(), fechaInicio.getDay()));
        mantenimiento.setFechaFin(new java.sql.Date(fechaFin.getYear(), fechaFin.getMonth(), fechaFin.getDay()));
        mantenimiento.setCosto(costo);
        mantenimiento.setIdRevisionTecnica(idRevisionTecnica);
        
        int respuesta=mantenimiento.actualizar(mantenimiento);
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
            int idRevisionTecnica=Integer.parseInt((String)vista.Tabla.getValueAt(fila, 6).toString());
            mantenimiento.delete(id,idRevisionTecnica);
            JOptionPane.showMessageDialog(vista, "MANTENIMIENTO ELIMINADA CON EXITO");
        }
    }

    private void llenarRevisionTecnica() {
        vista.cboRevisionTecnica.removeAllItems();
        for (int i = 0; i < listaRevision.size(); i++) {
            vista.cboRevisionTecnica.addItem(listaRevision.get(i).getId());
        }
    }

    private int buscar(int itemAt) {
        for (int i = 0; i < listaRevision.size(); i++) {
            if (listaRevision.get(i).getId()==itemAt){
                return listaRevision.get(i).getId();
            }
        }
        return -1;
    }
    
}
