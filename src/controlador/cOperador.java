/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.mOperador;
import vista.vOperador;

/**
 *
 * @author Erick Vidal
 */
public class cOperador implements ActionListener {

    //mOperadoDAO dao = new mOperadoDAO();
    mOperador operador = new mOperador();
    vOperador vista = new vOperador();
    DefaultTableModel modelo = new DefaultTableModel();

    public cOperador(vOperador v) {
        this.vista = v;
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        listar(vista.Tabla);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnGuardar) {
            agregar();
            limpiarTabla();
            listar(vista.Tabla);
        }
        if (e.getSource() == vista.btnEditar) {
            editar();
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

    public void agregar() {
        String nombre = vista.txtNombre.getText();
        String correo = vista.txtCorreo.getText();
        String direccion = vista.txtDireccion.getText();
        operador.setNombre(nombre);
        operador.setCorreo(correo);
        operador.setDireccion(direccion);
        int respuesta = operador.agregar(operador);
        if (respuesta == 1) {
            JOptionPane.showMessageDialog(vista, "OPERADOR AGREGADA CON EXITO");
        } else {
            JOptionPane.showMessageDialog(vista, "ERROR, POR FAVOR INTENTE NUEVAMENTE");
        }
    }

    public void editar() {
        int fila = vista.Tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Debe Seleccionar una fila");
        } else {
            int id = Integer.parseInt((String) vista.Tabla.getValueAt(fila, 0).toString());
            String nombre = (String) vista.Tabla.getValueAt(fila, 1);
            String correo = (String) vista.Tabla.getValueAt(fila, 2);
            String direccion = (String) vista.Tabla.getValueAt(fila, 3);
            vista.txtId.setText("" + id);
            vista.txtNombre.setText(nombre);
            vista.txtCorreo.setText(correo);
            vista.txtDireccion.setText(direccion);
        }
    }

    public void actualizar() {
        int id = Integer.parseInt(vista.txtId.getText());
        String nombre = vista.txtNombre.getText();
        String correo = vista.txtCorreo.getText();
        String direccion = vista.txtDireccion.getText();
        operador.setId(id);
        operador.setNombre(nombre);
        operador.setCorreo(correo);
        operador.setDireccion(direccion);
        int respuesta = operador.actualizar(operador);
        if (respuesta == 1) {
            JOptionPane.showMessageDialog(vista, "OPERADOR EDITADO CON EXITO");
        } else {
            JOptionPane.showMessageDialog(vista, "ERROR, POR FAVOR INTENTELO NUEVAMENTE");
        }
    }
    public void limpiarTabla(){
        for (int i = 0; i < vista.Tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i=i-1;
        }
    }
    public void listar(JTable tabla){
        modelo=(DefaultTableModel)tabla.getModel();
        List<mOperador> lista=operador.listar();
        Object[]object=new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            object[0]=lista.get(i).getId();
            object[1]=lista.get(i).getNombre();
            object[2]=lista.get(i).getCorreo();
            object[3]=lista.get(i).getDireccion();
            modelo.addRow(object);
        }
    }
    public void delete(){
        int fila=vista.Tabla.getSelectedRow();
        if (fila==-1) {
            JOptionPane.showMessageDialog(vista, "DEBE SELECCIONAR UNA FILA");
        }else{
            int id=Integer.parseInt((String)vista.Tabla.getValueAt(fila, 0).toString());
            operador.delete(id);
            JOptionPane.showMessageDialog(vista, "OPERADOR ELIMINADO CON EXITO");
        }
    }
}
