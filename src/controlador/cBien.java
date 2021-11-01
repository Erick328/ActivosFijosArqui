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
import modelo.mCategoria;
//import modelo.mCategoriaDAO;
import vista.vBien;

/**
 *
 * @author Erick Vidal
 */
public class cBien implements ActionListener {

    mBien bien = new mBien();
    vBien vista = new vBien();
    mCategoria categoria = new mCategoria();
    ArrayList<mCategoria> listaCategoria = (ArrayList<mCategoria>) categoria.listar();
    DefaultTableModel modelo = new DefaultTableModel();

    public cBien(vBien v) {
        this.vista = v;
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        listar(vista.Tabla);
        llenarCategoria();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnGuardar) {
            agregar();
            limpiarTabla();
            listar(vista.Tabla);
        }
        if (e.getSource() == vista.btnEditar) {
            int fila = vista.Tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Debe Seleccionar una fila");
            } else {
                int id = Integer.parseInt((String) vista.Tabla.getValueAt(fila, 0).toString());
                String nombre = (String) vista.Tabla.getValueAt(fila, 1);
                int valorCompra = Integer.parseInt((String) vista.Tabla.getValueAt(fila, 2).toString());
                Date fechaAdquisicion = (Date) vista.Tabla.getValueAt(fila, 3);
                int vidaUtil = Integer.parseInt((String) vista.Tabla.getValueAt(fila, 4).toString());
                int depreciacion = Integer.parseInt((String) vista.Tabla.getValueAt(fila, 5).toString());
                String estado = (String) vista.Tabla.getValueAt(fila, 6);
                //int idCategoria=Integer.parseInt((String)vista.Tabla.getValueAt(fila,7).toString());
                vista.txtId.setText("" + id);
                vista.txtNombre.setText(nombre);
                vista.txtValorCompra.setText("" + valorCompra);
                vista.txtFechaAdquisicion.setDate(fechaAdquisicion);
                vista.txtVidaUtil.setText("" + vidaUtil);
                vista.txtDepreciacion.setText("" + depreciacion);
                vista.txtEstado.setText(estado);

            }
        }
        if (e.getSource() == vista.btnActualizar) {
            actualizar();
            limpiarTabla();
            listar(vista.Tabla);
        }
        if (e.getSource() == vista.btnEliminar) {
            delete();
            limpiarTabla();
            listar(vista.Tabla);
        }
    }

    private void llenarCategoria() {
        vista.cboCategoria.removeAllItems();
        for (int i = 0; i < listaCategoria.size(); i++) {
            vista.cboCategoria.addItem(listaCategoria.get(i).getNombre());
        }
    }

    public void listar(JTable Tabla) {
        modelo = (DefaultTableModel) Tabla.getModel();
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

    private void limpiarTabla() {
        for (int i = 0; i < vista.Tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    private void agregar() {
        String nombre = vista.txtNombre.getText();
        int valorCompra = Integer.parseInt((String) vista.txtValorCompra.getText());
        Date fechaAdquisicion = vista.txtFechaAdquisicion.getDate();
        int vidaUtil = Integer.parseInt((String) vista.txtVidaUtil.getText());
        int depreciacion = valorCompra / vidaUtil;
        String estado = vista.txtEstado.getText();
        int idCategoria = buscar(vista.cboCategoria.getItemAt(vista.cboCategoria.getSelectedIndex()));
        bien.setNombre(nombre);
        bien.setValorCompra(valorCompra);
        bien.setFechaAdquisicion(new java.sql.Date(fechaAdquisicion.getYear(), fechaAdquisicion.getMonth(), fechaAdquisicion.getDay()));
        bien.setVidaUtil(vidaUtil);
        bien.setDepreciacion(depreciacion);
        bien.setEstado(estado);
        bien.setIdCategoria(idCategoria);

        int respuesta = bien.agregar(bien);
        if (respuesta == 1) {
            JOptionPane.showMessageDialog(vista, "BIEN AGREGADA CON EXITO");
        } else {
            JOptionPane.showMessageDialog(vista, "ERROR, POR FAVOR INTENTE NUEVAMENTE");
        }

    }

    private void actualizar() {
        int id = Integer.parseInt(vista.txtId.getText());
        String nombre = vista.txtNombre.getText();
        int valorCompra = Integer.parseInt((String) vista.txtValorCompra.getText());
        Date fechaAdquisicion = vista.txtFechaAdquisicion.getDate();
        int vidaUtil = Integer.parseInt((String) vista.txtVidaUtil.getText());
        int depreciacion = valorCompra / vidaUtil;
        String estado = vista.txtEstado.getText();
        int idCategoria = buscar(vista.cboCategoria.getItemAt(vista.cboCategoria.getSelectedIndex()));
        bien.setId(id);
        bien.setNombre(nombre);
        bien.setValorCompra(valorCompra);
        bien.setFechaAdquisicion(new java.sql.Date(fechaAdquisicion.getYear(), fechaAdquisicion.getMonth(), fechaAdquisicion.getDay()));
        bien.setVidaUtil(vidaUtil);
        bien.setDepreciacion(depreciacion);
        bien.setEstado(estado);
        bien.setIdCategoria(idCategoria);

        int respuesta = bien.actualizar(bien);
        if (respuesta == 1) {
            JOptionPane.showMessageDialog(vista, "BIEN EDITADA CON EXITO");
        } else {
            JOptionPane.showMessageDialog(vista, "ERROR, POR FAVOR INTENTELO NUEVAMENTE");
        }
    }

    private void delete() {
        int fila = vista.Tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "DEBE SELECIONAR UNA FILA");
        } else {
            int id = Integer.parseInt((String) vista.Tabla.getValueAt(fila, 0).toString());
            bien.delete(id);
            JOptionPane.showMessageDialog(vista, "BIEN ELIMINADA CON EXITO");
        }

    }

    private int buscar(String itemAt) {
        for (int i = 0; i < listaCategoria.size(); i++) {
            if (listaCategoria.get(i).getNombre() == itemAt) {
                return listaCategoria.get(i).getId();
            }
        }
        return -1;
    }
}
