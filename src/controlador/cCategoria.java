
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.mCategoria;
//import modelo.mCategoriaDAO;
import vista.vCategoria;

/**
 *
 * @author Erick Vidal
 */
public class cCategoria implements ActionListener{
    //mCategoriaDAO dao=new mCategoriaDAO();
    mCategoria categoria=new mCategoria();
    vCategoria vista= new vCategoria();
    DefaultTableModel modelo= new DefaultTableModel();

    
    public cCategoria(vCategoria v){
        this.vista=v;
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        listar(vista.Tabla);
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
                String nombre=(String)vista.Tabla.getValueAt(fila, 1);
                String descripcion=(String)vista.Tabla.getValueAt(fila, 2);
                vista.txtId.setText(""+id);
                vista.txtNombre.setText(nombre);
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
    public void agregar(){
        String nombre=vista.txtNombre.getText();
        String descripcion=vista.txtDescripcion.getText();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        int respuesta=categoria.agregar(categoria);
        if (respuesta==1) {
            JOptionPane.showMessageDialog(vista, "CATEGORIA AGREGADA CON EXITO");
        }else{
            JOptionPane.showMessageDialog(vista, "ERROR, POR FAVOR INTENTE NUEVAMENTE");    
        }
    }
    
    public void actualizar(){
        int id=Integer.parseInt(vista.txtId.getText());
        String nombre=vista.txtNombre.getText();
        String descripcion=vista.txtDescripcion.getText();
        categoria.setId(id);
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        int respuesta=categoria.actualiszar(categoria);
        if (respuesta==1) {
            JOptionPane.showMessageDialog(vista, "CATEGORIA EDITADA CON EXITO");
        }else{
            JOptionPane.showMessageDialog(vista, "ERROR, POR FAVOR INTENTELO NUEVAMENTE");
        }
    }
    void limpiarTabla(){
        for (int i = 0; i < vista.Tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i=i-1;
        }
    }
    
    public void listar(JTable tabla){
        modelo=(DefaultTableModel)tabla.getModel();
        List<mCategoria> lista=categoria.listar();
        Object[]object=new Object[3];
        for (int i = 0; i < lista.size(); i++) {
            object[0]=lista.get(i).getId();
            object[1]=lista.get(i).getNombre();
            object[2]=lista.get(i).getDescripcion();
            modelo.addRow(object);
        }
    }
    public void delete(){
        int fila=vista.Tabla.getSelectedRow();
        if (fila==-1) {
                JOptionPane.showMessageDialog(vista, "DEBE SELECIONAR UNA FILA");
        }else{
            int id=Integer.parseInt((String)vista.Tabla.getValueAt(fila, 0).toString());
            categoria.delete(id);
            JOptionPane.showMessageDialog(vista, "CATEGORIA ELIMINADA CON EXITO");
        }      
    }
    
}
