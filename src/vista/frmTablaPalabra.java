package vista;

import controlador.Palabra_Controller;
import modelo.Palabra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class frmTablaPalabra extends JFrame{
    private String texto;
    private boolean isEditar;
    private int id;
    private JPanel PanelMain;
    private JTable tablaPalabras;
    private JButton nuevoButton;
    private JButton eliminarButton;
    private JTextField textID;
    private JTextField textPalabra;
    private JButton guardarButton;
    private JButton buscarButton;
    private JButton actualizarButton;
    Palabra_Controller manejador;
    public frmTablaPalabra(){
        super("Gestion de Palabras");
        id=0;
        isEditar=false;
        texto=null;
        setContentPane(PanelMain);
        setSize(600,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        manejador=new Palabra_Controller();
        DeshabilitarBotones();
        InicializarTabla();
        asignarEventos();
    }
    private void asignarEventos(){
        nuevoButton.addActionListener(_->Habilitar());
        guardarButton.addActionListener(_ -> GuardarPalabra());
        eliminarButton.addActionListener(_->EliminarPalabra());
        buscarButton.addActionListener(_->BuscarPalabra());
        actualizarButton.addActionListener(_->ActualizarTabla());
        tablaPalabras.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarFila();
            }
        });
    }
    private void InicializarTabla(){
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Palabra"},0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPalabras.setModel(model);
    }
    private void Habilitar(){
        textPalabra.setEnabled(true);
        guardarButton.setEnabled(true);
        eliminarButton.setEnabled(true);
        buscarButton.setEnabled(true);
        actualizarButton.setEnabled(true);
    }
    private void DeshabilitarBotones(){
        textPalabra.setEnabled(false);
        guardarButton.setEnabled(false);
        eliminarButton.setEnabled(false);
        buscarButton.setEnabled(false);
        actualizarButton.setEnabled(false);
    }
    private Palabra crearPalabra(){
        Palabra miPalabra=new Palabra();
        miPalabra.setPalabra(textPalabra.getText().trim());
        return miPalabra;
    }
    private void GuardarPalabra(){
        texto = textPalabra.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo de palabra no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Palabra nuevaPalabra=crearPalabra();
        if(isEditar){
            nuevaPalabra.setId(id);
            manejador.ModificarPalabra(nuevaPalabra);
            JOptionPane.showMessageDialog(this,"La palabra fue actualizada");
            isEditar=false;
        }
        else {
            int nuevoID=manejador.getLista_Palabras().size()+1;
            nuevaPalabra.setId(nuevoID);
            manejador.AgregarPalabra(nuevaPalabra);
            JOptionPane.showMessageDialog(this,"La palabra fue agregada");
        }
        LimpiarCampos();
        ActualizarTabla();
    }
    private void ActualizarTabla(){
        DefaultTableModel model = (DefaultTableModel) tablaPalabras.getModel();
        model.setRowCount(0);
        for(Palabra p:manejador.getLista_Palabras()){
            model.addRow(new Object[]{p.getId(), p.getPalabra()});
        }
    }
    private void EliminarPalabra(){
        try{
            int codigo = Integer.parseInt(textID.getText().trim());
            manejador.EliminarPalabra(codigo);
            JOptionPane.showMessageDialog(this, "Se ha eliminado la palabra");
            LimpiarCampos();
            ActualizarTabla();
            isEditar=false;
        }
        catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una palabra para eliminar.");
        }
    }
    private void seleccionarFila(){
        isEditar=true;
        int fila=tablaPalabras.getSelectedRow();
        if(fila!=-1){
            id=(int)tablaPalabras.getValueAt(fila,0);
            String palabra=(String) tablaPalabras.getValueAt(fila,1);
            textID.setText(String.valueOf(id));
            textPalabra.setText(palabra);
        }
    }
    private void BuscarPalabra(){
        texto = textPalabra.getText().trim();
        if(texto.isEmpty()){
            JOptionPane.showMessageDialog(this,"Por favor ingrese una palabra a buscar");
            return;
        }
        Palabra encontrada = manejador.BuscarPalabra(texto);
        DefaultTableModel model = (DefaultTableModel) tablaPalabras.getModel();
        model.setRowCount(0); // Limpia la tabla

        if (encontrada != null) {
            model.addRow(new Object[]{encontrada.getId(), encontrada.getPalabra()});
        } else {
            JOptionPane.showMessageDialog(this, "La palabra no fue encontrada.");
            ActualizarTabla();
        }
        LimpiarCampos();
    }
    private void LimpiarCampos(){
        textID.setText("");
        textPalabra.setText("");

    }
}