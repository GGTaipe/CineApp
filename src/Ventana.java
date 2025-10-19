import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Ventana {
    private JPanel principal;
    private JComboBox cmbPeliculas;
    private JTextField txtNumEntradas;
    private JTextField txtApellido;
    private JTextField txtNombre;
    private JButton btnComprar;
    private JTextArea txtListaCompras;
    private JTextArea txtEntradasRestantes;


    private List<CineApp.Pelicula> catalogoPeliculas;
    private Queue<CineApp.Compra> registroDeCompras;
    private double totalRecaudado;


    private CineApp cineApp = new CineApp();

    public Ventana() {
        catalogoPeliculas = new ArrayList<>();
        registroDeCompras = new LinkedList<>();
        totalRecaudado = 0.0;
        cargarPeliculas();

        btnComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String nombrePelicula = (String) cmbPeliculas.getSelectedItem();
                int cantidad;

                if (nombre.isEmpty() || apellido.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar nombre y apellido.");
                    return;
                }

                try {
                    cantidad = Integer.parseInt(txtNumEntradas.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un número válido (1 - 4).");
                    return;
                }

                if (cantidad <= 0 || cantidad > 4) {
                    JOptionPane.showMessageDialog(null, "Solo puede comprar hasta 4 boletos.");
                    return;
                }

                CineApp.Pelicula peliculaSeleccionada = buscarPelicula(nombrePelicula);

                if (peliculaSeleccionada != null) {
                    if (peliculaSeleccionada.getCapacidadDisponible() < cantidad) {
                        JOptionPane.showMessageDialog(null, "No hay suficientes espacios disponibles.");
                        return;
                    }

                    procesarCompra(peliculaSeleccionada, nombre, apellido, cantidad);

                    txtNombre.setText("");
                    txtApellido.setText("");
                    txtNumEntradas.setText("");
                    JOptionPane.showMessageDialog(null, "¡Compra exitosa!");

                    actualizarLabelsInformativos();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró la película seleccionada.");
                }
            }
        });
    }

    private void cargarPeliculas() {
        catalogoPeliculas.add(cineApp.new Pelicula("XMEN", 2.25));
        catalogoPeliculas.add(cineApp.new Pelicula("MARIO", 3.25));
        catalogoPeliculas.add(cineApp.new Pelicula("BATMAN", 3.75));

        for (CineApp.Pelicula p : catalogoPeliculas) {
            cmbPeliculas.addItem(p.getTituloPelicula());
        }
    }

    private CineApp.Pelicula buscarPelicula(String nombrePelicula) {
        for (CineApp.Pelicula p : catalogoPeliculas) {
            if (p.getTituloPelicula().equals(nombrePelicula)) {
                return p;
            }
        }
        return null;
    }

    private void procesarCompra(CineApp.Pelicula p, String nombre, String apellido, int cantidad) {
        CineApp.Cliente nuevoCliente = cineApp.new Cliente(nombre, apellido);
        CineApp.Compra nuevaCompra = cineApp.new Compra(p, nuevoCliente, cantidad);

        registroDeCompras.add(nuevaCompra);
        p.registrarVenta(cantidad);
        totalRecaudado += nuevaCompra.getTotalPago();
        actualizarListaDeCompras(nuevaCompra);
    }

    private void actualizarListaDeCompras(CineApp.Compra compra) {
        String infoCompra = "Película: " + compra.getPeliculaComprada().getTituloPelicula() +
                " | Cliente: " + compra.getComprador().getNombreCliente() + " " + compra.getComprador().getApellidoCliente() +
                " | Boletos: " + compra.getCantidadBoletos() +
                " | Total: $" + String.format("%.2f", compra.getTotalPago()) + "\n";

        txtListaCompras.append(infoCompra);
    }

    private void actualizarLabelsInformativos() {
        StringBuilder sb = new StringBuilder();
        for (CineApp.Pelicula p : catalogoPeliculas) {
            sb.append(String.format("%s: Vendidos %d | Disponibles: %d%n",
                    p.getTituloPelicula(),
                    p.getBoletosVendidos(),
                    p.getCapacidadDisponible()
            ));
        }
        txtEntradasRestantes.setText(sb.toString());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana de Cine");
        frame.setContentPane(new Ventana().principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
