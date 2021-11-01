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
import modelo.mDetalleIngreso;
import modelo.mIngreso;
import modelo.mOperador;

import vista.vIngreso;

/**
 *
 * @author Erick Vidal
 */
public class cIngreso implements ActionListener {

    mIngreso ingreso = new mIngreso();
    vIngreso vista = new vIngreso();
    mBien bien = new mBien();
    mOperador operador = new mOperador();
    mDetalleIngreso detalleIngreso = new mDetalleIngreso();
    ArrayList<mOperador> listaOperador = (ArrayList<mOperador>) operador.listar();
    DefaultTableModel modelo = new DefaultTableModel();
    List<mBien> listaBienes = new ArrayList<>();

    public cIngreso(vIngreso v) {
        this.vista = v;
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnBien.addActionListener(this);
        this.vista.btnEliminarBien.addActionListener(this);
        listarBien(vista.TablaBien);
        llenarOperador();
        listarIngreso(vista.TablaIngreso);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnGuardar) {
            agregarIngreso();
            limpiarTabla();
            listarBien(vista.TablaBien);
            listarIngreso(vista.TablaIngreso);
            //agregarDetalleIngreso();
        }
        if (e.getSource() == vista.btnActualizar) {
            actualizar();
            limpiarTabla();
            listarBien(vista.TablaBien);
        }
        if (e.getSource()==vista.btnBien) {
            agregarBien();
            limpiarTabla();
            listarIngreso(vista.TablaIngreso);
            
        }
        if (e.getSource() == vista.btnEliminar) {
            delete();
            limpiarTabla();
            listarBien(vista.TablaBien);
            listarIngreso(vista.TablaIngreso);
        }
        if (e.getSource()==vista.btnEliminarBien) {
            eliminarBien();
            limpiarTabla();
            //listarBien(vista.TablaBien);
            listarIngreso(vista.TablaIngreso);
        }
    }

    private void listarBien(JTable TablaBien) {
        modelo = (DefaultTableModel) TablaBien.getModel();
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
            modelo.addRow(object);
        }
    }

    private void llenarOperador() {
        vista.cboOperador.removeAllItems();
        for (int i = 0; i < listaOperador.size(); i++) {
            vista.cboOperador.addItem(listaOperador.get(i).getNombre());
        }
    }

    private void agregarIngreso() {
        Date fechaIngreso = vista.txtFechaIngreso.getDate();
        String descripcion = vista.txtDescripcion.getText();
        int idOperador = buscar(vista.cboOperador.getItemAt(vista.cboOperador.getSelectedIndex()));
        ingreso.setFechaIngreso(new java.sql.Date(fechaIngreso.getYear(), fechaIngreso.getMonth(), fechaIngreso.getDay()));
        ingreso.setDescripcion(descripcion);
        ingreso.setIdOperador(idOperador);
        int respuesta = ingreso.agregar(ingreso);
        
        if (respuesta == 1) {
            JOptionPane.showMessageDialog(vista, "Ingreso agregado correctamente");
        }
    }
    private void agregarDetalleIngreso(){
        int idIngreso = Integer.parseInt((String)vista.TablaIngreso.getValueAt(vista.TablaIngreso.getRowCount(), 0).toString());
        for (int i = 0; i < listaBienes.size(); i++) {
            detalleIngreso.setIdIngreso(idIngreso);
            detalleIngreso.setIdBien(listaBienes.get(i).getId());
            detalleIngreso.agregar(detalleIngreso);
        }
        listaBienes = new ArrayList<>();
    }
    private void limpiarTabla() {
        for (int i = 0; i < vista.TablaIngreso.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
        for (int i = 0; i < vista.TablaBien.getRowCount(); i++) {
            modelo.removeRow(i);
            i=i-1;
        }
    }

    private void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void delete() {
        int fila=vista.TablaIngreso.getSelectedRow();
        if (fila==-1) {
            JOptionPane.showMessageDialog(vista, "Seleccione una fila");
        }else{
            int id=Integer.parseInt((String)vista.TablaIngreso.getValueAt(fila, 0));
            detalleIngreso.delete(id);
            ingreso.delete(id);
            JOptionPane.showMessageDialog(vista, "Ingreso Eliminado con exito");
        }
    }

    private void listarIngreso(JTable TablaIngreso) {
        modelo = (DefaultTableModel) TablaIngreso.getModel();
        List<mIngreso> lista = ingreso.listar();
        Object[] object = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getFechaIngreso();
            object[2] = lista.get(i).getDescripcion();
            object[3] = lista.get(i).getIdOperador();
            modelo.addRow(object);
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
            if (!contiene(nuevoBien.getId())) {
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
            }else{
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
