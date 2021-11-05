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
import modelo.mBien;
import modelo.mDetalleEgreso;
import modelo.mEgreso;
import modelo.mOperador;
import vista.vEgreso;

/**
 *
 * @author Erick Vidal
 */
public class cEgreso implements ActionListener{
    mEgreso egreso = new mEgreso();
    vEgreso vista = new vEgreso();
    mBien bien = new mBien();
    mOperador operador = new mOperador();
    mDetalleEgreso detalleEgreso = new mDetalleEgreso();
    ArrayList<mOperador> listaOperador = (ArrayList<mOperador>) operador.listar();
    DefaultTableModel modeloEgreso = new DefaultTableModel();
    DefaultTableModel modeloBien=new DefaultTableModel();
    List<mBien> listaBienes = new ArrayList<>();
    
    public cEgreso(vEgreso v) {
        this.vista = v;
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnAgregarBien.addActionListener(this);
        this.vista.btnEliminarBien.addActionListener(this);
        listarBien(vista.TablaBien);
        llenarOperador();
        listarEgreso(vista.TablaEgreso);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnGuardar) {
            agregarEgreso();
            limpiarTabla();
            listarBien(vista.TablaBien);
            listarEgreso(vista.TablaEgreso);
        }
        if (e.getSource() == vista.btnActualizar) {
            actualizar();
            limpiarTabla();
            listarBien(vista.TablaBien);
            listarEgreso(vista.TablaEgreso);
        }
        if (e.getSource()==vista.btnAgregarBien) {
            agregarBien();
            limpiarTabla();
            listarEgreso(vista.TablaEgreso);
            listarBien(vista.TablaBien);
            
        }
        if (e.getSource()==vista.btnEditar) {
            int fila=vista.TablaEgreso.getSelectedRow();
            if (fila==-1) {
                JOptionPane.showMessageDialog(vista, "PORFAVOR SELECCIONE UNA FILA");
            }else{
                int id=Integer.parseInt((String)vista.TablaEgreso.getValueAt(fila, 0).toString());
                Date fechaEgreso=(Date)vista.TablaEgreso.getValueAt(fila, 1);
                String descripcion=(String)vista.TablaEgreso.getValueAt(fila, 2);
                vista.txtId.setText(""+id);
                vista.txtFechaEgreso.setDate(fechaEgreso);
                vista.txtDescripcion.setText(descripcion);
            }
        }
        if (e.getSource() == vista.btnEliminar) {
            delete();
            limpiarTabla();
            listarBien(vista.TablaBien);
            listarEgreso(vista.TablaEgreso);
        }
        if (e.getSource()==vista.btnEliminarBien) {
            eliminarBien();
            limpiarTabla();
            listarBien(vista.TablaBien);
            listarEgreso(vista.TablaEgreso);
        }
    }
    
    private void listarBien(JTable TablaBien) {
        modeloBien = (DefaultTableModel) TablaBien.getModel();
        List<mBien> lista = bien.listar();
        Object[] object = new Object[8];
        for (int i = 0; i < lista.size(); i++) {
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getNombre();
            object[2] = lista.get(i).getValorCompra();
            object[3] = lista.get(i).getFechaAdquisicion();
            object[4] = lista.get(i).getVidaUtil();
            object[5] = lista.get(i).getDepreciacion();
            object[6] = lista.get(i).getEstado();
            object[7] = lista.get(i).getIdCategoria();
            modeloBien.addRow(object);
        }
    }

    private void llenarOperador() {
        vista.cboOperador.removeAllItems();
        for (int i = 0; i < listaOperador.size(); i++) {
            vista.cboOperador.addItem(listaOperador.get(i).getNombre());
        }
    }

    private void agregarEgreso() {
        Date fechaIngreso = vista.txtFechaEgreso.getDate();
        String descripcion = vista.txtDescripcion.getText();
        int idOperador = buscar(vista.cboOperador.getItemAt(vista.cboOperador.getSelectedIndex()));
        egreso.setFechaEgreso(new java.sql.Date(fechaIngreso.getYear(), fechaIngreso.getMonth(), fechaIngreso.getDay()));
        egreso.setDescripcion(descripcion);
        egreso.setIdOperador(idOperador);
        int respuesta = egreso.agregar(egreso);
        agregarDetalleIngreso();
        if (respuesta == 1) {
            JOptionPane.showMessageDialog(vista, "Egreso agregado correctamente");
        }
    }
    private void agregarDetalleIngreso(){
        List<mEgreso> listaEgreso=egreso.listar();
        int idEgreso=listaEgreso.get(listaEgreso.size()-1).getId();
        for (int i = 0; i < listaBienes.size(); i++) {
            detalleEgreso.setIdEgreso(idEgreso);
            detalleEgreso.setIdBien(listaBienes.get(i).getId());
            detalleEgreso.agregar(detalleEgreso);
        }
        listaBienes = new ArrayList<>();
    }
    private void limpiarTabla() {
        for (int i = 0; i < vista.TablaEgreso.getRowCount(); i++) {
            modeloEgreso.removeRow(i);
            i = i - 1;
        }
        for (int i = 0; i < vista.TablaBien.getRowCount(); i++) {
            modeloBien.removeRow(i);
            i=i-1;
        }
    }

    private void actualizar() {
        int id=Integer.parseInt(vista.txtId.getText());
        Date fechaEgreso=vista.txtFechaEgreso.getDate();
        String descripcion=vista.txtDescripcion.getText();
        int idOperador=buscar(vista.cboOperador.getItemAt(vista.cboOperador.getSelectedIndex()));
        egreso.setId(id);
        egreso.setFechaEgreso(new java.sql.Date(fechaEgreso.getYear(),fechaEgreso.getMonth(), fechaEgreso.getDay()));
        egreso.setDescripcion(descripcion);
        egreso.setIdOperador(idOperador);
        int respuesta=egreso.actualizar(egreso);
        if (respuesta==1) {
            JOptionPane.showMessageDialog(vista, "EGRESO ACTUALIZADO CON EXITO");
        }else{
            JOptionPane.showMessageDialog(vista, "ERROR, POR FAVOR INTENTE NUEVAMENTE");
        }
    }

    private void delete() {
        int fila=vista.TablaEgreso.getSelectedRow();
        if (fila==-1) {
            JOptionPane.showMessageDialog(vista, "Seleccione una fila");
        }else{
            int id=Integer.parseInt((String)vista.TablaEgreso.getValueAt(fila, 0).toString());
            detalleEgreso.delete(id);
            egreso.delete(id);
            JOptionPane.showMessageDialog(vista, "Egreso Eliminado con exito");
        }
    }

    private void listarEgreso(JTable TablaEgreso) {
        modeloEgreso = (DefaultTableModel) TablaEgreso.getModel();
        List<mEgreso> lista = egreso.listar();
        Object[] object = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getFechaEgreso();
            object[2] = lista.get(i).getDescripcion();
            object[3] = lista.get(i).getIdOperador();
            modeloEgreso.addRow(object);
        }
    }

    private int buscar(String itemAt) {
        for (int i = 0; i < listaOperador.size(); i++) {
            if (listaOperador.get(i).getNombre().equals(itemAt)) {
                return listaOperador.get(i).getId();
            }
        }
        return -1;
    }

    private void agregarBien() {
        int fila=vista.TablaBien.getSelectedRow();
        if (fila==-1) {
            JOptionPane.showMessageDialog(vista, "Porfavor seleccione un Bien");
        }else{
            int id=Integer.parseInt((String)vista.TablaBien.getValueAt(fila, 0).toString());
            String nombre=vista.TablaBien.getValueAt(fila, 1).toString();
            int valorCompra=Integer.parseInt((String)vista.TablaBien.getValueAt(fila, 2).toString());
            Date fechaAdquisicion=(Date)vista.TablaBien.getValueAt(fila, 3);
            int vidaUtil=Integer.parseInt((String)vista.TablaBien.getValueAt(fila, 4).toString());
            int depreciacion=Integer.parseInt((String)vista.TablaBien.getValueAt(fila, 5).toString());
            String estado=vista.TablaBien.getValueAt(fila, 6).toString();
            int idCategoria =Integer.parseInt((String)vista.TablaBien.getValueAt(fila, 7).toString());
            mBien nuevoBien=new mBien(id, nombre, valorCompra, (java.sql.Date)fechaAdquisicion, vidaUtil, depreciacion, estado, idCategoria);
            if ((nuevoBien.getEstado().equals("I") || nuevoBien.getEstado().equals("N")) && !contiene(nuevoBien.getId())) {
                listaBienes.add(nuevoBien);
                vista.areaBienes.setText(listaBienes.toString());
                JOptionPane.showMessageDialog(vista, "Bien agregado con exito");
            }else{
                JOptionPane.showMessageDialog(vista, "Seleccione otro bien");
            }
        }
    }
    private void eliminarBien(){
        int fila=vista.TablaBien.getSelectedRow();
        if (fila==-1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un Bien porfavor");
        }else{
            int id=Integer.parseInt((String)vista.TablaBien.getValueAt(fila, 0).toString());
            if (contiene(id)) {
                for (int i = 0; i < listaBienes.size(); i++) {
                    if (listaBienes.get(i).getId()==id) {
                        listaBienes.remove(i);
                    }
                }
                vista.areaBienes.setText(listaBienes.toString());
                JOptionPane.showMessageDialog(vista, "Eliminado con exito");
            }else {
                JOptionPane.showMessageDialog(vista, "Bien no agregado");
            }
        }
    }
    private boolean contiene(int id){
        for (int i = 0; i < listaBienes.size(); i++) {
            if (listaBienes.get(i).getId()==id) {
                return true;
            }
        }
        return false;
    }
    
    
}
