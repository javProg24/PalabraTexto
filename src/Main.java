import javax.swing.*;
import vista.frmTablaPalabra;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frmTablaPalabra ventana = new frmTablaPalabra();
                ventana.setVisible(true);
            }
        });
    }
}
