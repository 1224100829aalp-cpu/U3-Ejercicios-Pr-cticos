# Clase ConjuntoManager
```java
package APP;

/**
 *
 * @author angellunaperez
 */

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ConjuntoManager {
    private Set<Producto> conjuntoProductos;
    private Set<Producto> conjuntoBajasExistencias;

    public ConjuntoManager() {
        conjuntoProductos = new HashSet<>();
        conjuntoBajasExistencias = new HashSet<>();
        inicializarDatos();
    }

    private void inicializarDatos() {
        // Agregar productos iniciales
        conjuntoProductos.add(new Producto("P001", "Laptop", "Electrónica", 1200.00, 15));
        conjuntoProductos.add(new Producto("P002", "Mouse", "Electrónica", 25.50, 50));
        conjuntoProductos.add(new Producto("P003", "Teclado", "Electrónica", 45.00, 30));
        conjuntoProductos.add(new Producto("P004", "Monitor", "Electrónica", 350.00, 20));
        conjuntoProductos.add(new Producto("P005", "Impresora", "Oficina", 200.00, 5));
        conjuntoProductos.add(new Producto("P006", "Silla", "Muebles", 150.00, 3));
        conjuntoProductos.add(new Producto("P007", "Mesa", "Muebles", 300.00, 8));
        conjuntoProductos.add(new Producto("P008", "Tablet", "Electrónica", 450.00, 2));
        conjuntoProductos.add(new Producto("P009", "Teléfono", "Electrónica", 600.00, 12));
        conjuntoProductos.add(new Producto("P010", "Altavoces", "Electrónica", 80.00, 25));

        // Productos con bajas existencias (stock menor a 5)
        for (Producto p : conjuntoProductos) {
            if (p.getStock() < 5) {
                conjuntoBajasExistencias.add(p);
            }
        }
    }

    // Operación 1: Agregar producto
    public boolean agregarProducto(Producto producto) {
        return conjuntoProductos.add(producto);
    }

    // Operación 2: Eliminar producto
    public boolean eliminarProducto(String id) {
        return conjuntoProductos.removeIf(p -> p.getId().equals(id));
    }

    // Operación 3: Buscar producto por nombre
    public Set<Producto> buscarPorNombre(String nombre) {
        return conjuntoProductos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toSet());
    }

    // Operación 4: Productos por categoría
    public Set<Producto> productosPorCategoria(String categoria) {
        return conjuntoProductos.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toSet());
    }

    // Operación 5: Unión de conjuntos (todos los productos)
    public Set<Producto> unionConjuntos() {
        Set<Producto> union = new HashSet<>(conjuntoProductos);
        union.addAll(conjuntoBajasExistencias);
        return union;
    }

    // Operación 6: Intersección (productos con bajas existencias)
    public Set<Producto> interseccionConjuntos() {
        Set<Producto> interseccion = new HashSet<>(conjuntoProductos);
        interseccion.retainAll(conjuntoBajasExistencias);
        return interseccion;
    }

    // Operación 7: Diferencia (productos sin bajas existencias)
    public Set<Producto> diferenciaConjuntos() {
        Set<Producto> diferencia = new HashSet<>(conjuntoProductos);
        diferencia.removeAll(conjuntoBajasExistencias);
        return diferencia;
    }

    // Operación 8: Verificar si un producto existe
    public boolean contieneProducto(String id) {
        return conjuntoProductos.stream()
                .anyMatch(p -> p.getId().equals(id));
    }

    // Operación 9: Obtener todos los productos ordenados por nombre
    public Set<Producto> productosOrdenados() {
        return new TreeSet<>((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()));
    }

    // Operación 10: Contar productos por categoría
    public long contarPorCategoria(String categoria) {
        return conjuntoProductos.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .count();
    }

    // Getters
    public Set<Producto> getConjuntoProductos() {
        return conjuntoProductos;
    }

    public Set<Producto> getConjuntoBajasExistencias() {
        return conjuntoBajasExistencias;
    }

    public int getTotalProductos() {
        return conjuntoProductos.size();
    }
}
```
# Clase Producto
```java
package APP;

import java.util.Objects;


/**
 *
 * @author angellunaperez
 */

public class Producto {
    private String id;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;

    public Producto(String id, String nombre, String categoria, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    @Override
    public String toString() {
        return nombre + " (ID: " + id + ") - $" + precio + " - Stock: " + stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(id, producto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
```
# Main Frame
```java
package APP;

/**
 *
 * @author angellunaperez
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Set;

public class MainFrame extends JFrame {
    private ConjuntoManager manager;
    private JTable tablaProductos;
    private DefaultTableModel tableModel;
    private JTextArea resultadoArea;
    private JTextField buscarField;
    private JComboBox<String> categoriaCombo;

    public MainFrame() {
        manager = new ConjuntoManager();
        initComponents();
        cargarProductos();
    }

    private void initComponents() {
        setTitle("Gestión de Conjuntos de Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel superior
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Operaciones de Conjuntos"));
        
        String[] operaciones = {
            "Mostrar Todos",
            "Productos Bajos Stock",
            "Unión de Conjuntos",
            "Intersección",
            "Diferencia",
            "Buscar por Nombre",
            "Filtrar por Categoría",
            "Ordenar por Nombre",
            "Estadísticas"
        };
        
        for (String op : operaciones) {
            JButton btn = new JButton(op);
            btn.addActionListener(this::ejecutarOperacion);
            topPanel.add(btn);
        }

        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buscarField = new JTextField(20);
        JButton buscarBtn = new JButton("Buscar");
        buscarBtn.addActionListener(e -> buscarProducto());
        
        searchPanel.add(new JLabel("Buscar:"));
        searchPanel.add(buscarField);
        searchPanel.add(buscarBtn);
        
        // Categorías
        categoriaCombo = new JComboBox<>(new String[]{"Todas", "Electrónica", "Oficina", "Muebles"});
        JButton filtrarBtn = new JButton("Filtrar");
        filtrarBtn.addActionListener(e -> filtrarPorCategoria());
        
        searchPanel.add(new JLabel("Categoría:"));
        searchPanel.add(categoriaCombo);
        searchPanel.add(filtrarBtn);

        // Panel central con tabla
        String[] columnNames = {"ID", "Nombre", "Categoría", "Precio", "Stock"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tablaProductos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        
        // Panel de resultados
        resultadoArea = new JTextArea(8, 50);
        resultadoArea.setEditable(false);
        JScrollPane resultScroll = new JScrollPane(resultadoArea);
        resultScroll.setBorder(BorderFactory.createTitledBorder("Resultados de Operaciones"));

        // Panel de acciones
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton agregarBtn = new JButton("Agregar Producto");
        agregarBtn.addActionListener(e -> agregarProducto());
        
        JButton eliminarBtn = new JButton("Eliminar Seleccionado");
        eliminarBtn.addActionListener(e -> eliminarProducto());
        
        actionPanel.add(agregarBtn);
        actionPanel.add(eliminarBtn);

        // Organizar componentes
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(topPanel, BorderLayout.NORTH);
        northPanel.add(searchPanel, BorderLayout.SOUTH);
        
        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(resultScroll, BorderLayout.SOUTH);
        add(actionPanel, BorderLayout.EAST);

        setSize(1000, 700);
        setLocationRelativeTo(null);
    }

    private void cargarProductos() {
        cargarProductosEnTabla(manager.getConjuntoProductos());
    }

    private void cargarProductosEnTabla(Set<Producto> productos) {
        tableModel.setRowCount(0);
        for (Producto p : productos) {
            tableModel.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getCategoria(),
                p.getPrecio(),
                p.getStock()
            });
        }
        resultadoArea.setText("Total de productos: " + productos.size());
    }

    private void ejecutarOperacion(ActionEvent e) {
        String comando = ((JButton) e.getSource()).getText();
        
        switch (comando) {
            case "Mostrar Todos":
                cargarProductos();
                resultadoArea.setText("Mostrando todos los productos (" + manager.getTotalProductos() + " elementos)");
                break;
                
            case "Productos Bajos Stock":
                cargarProductosEnTabla(manager.getConjuntoBajasExistencias());
                resultadoArea.setText("Productos con stock menor a 5 unidades: " + 
                    manager.getConjuntoBajasExistencias().size() + " elementos");
                break;
                
            case "Unión de Conjuntos":
                cargarProductosEnTabla(manager.unionConjuntos());
                resultadoArea.setText("Unión de todos los productos y productos con bajas existencias");
                break;
                
            case "Intersección":
                Set<Producto> interseccion = manager.interseccionConjuntos();
                cargarProductosEnTabla(interseccion);
                resultadoArea.setText("Intersección (productos comunes): " + interseccion.size() + " elementos");
                break;
                
            case "Diferencia":
                Set<Producto> diferencia = manager.diferenciaConjuntos();
                cargarProductosEnTabla(diferencia);
                resultadoArea.setText("Diferencia (productos sin bajas existencias): " + diferencia.size() + " elementos");
                break;
                
            case "Ordenar por Nombre":
                // Convertir a TreeSet para ordenar
                Set<Producto> ordenados = manager.productosOrdenados();
                ordenados.addAll(manager.getConjuntoProductos());
                cargarProductosEnTabla(ordenados);
                resultadoArea.setText("Productos ordenados alfabéticamente por nombre");
                break;
                
            case "Estadísticas":
                mostrarEstadisticas();
                break;
        }
    }

    private void buscarProducto() {
        String texto = buscarField.getText().trim();
        if (!texto.isEmpty()) {
            Set<Producto> resultados = manager.buscarPorNombre(texto);
            cargarProductosEnTabla(resultados);
            resultadoArea.setText("Búsqueda: '" + texto + "' - Encontrados: " + resultados.size() + " productos");
        }
    }

    private void filtrarPorCategoria() {
        String categoria = (String) categoriaCombo.getSelectedItem();
        if (!"Todas".equals(categoria)) {
            Set<Producto> filtrados = manager.productosPorCategoria(categoria);
            cargarProductosEnTabla(filtrados);
            resultadoArea.setText("Categoría: " + categoria + " - Total: " + filtrados.size() + " productos");
        } else {
            cargarProductos();
        }
    }

    private void agregarProducto() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        
        JTextField idField = new JTextField();
        JTextField nombreField = new JTextField();
        JTextField categoriaField = new JTextField();
        JTextField precioField = new JTextField();
        JTextField stockField = new JTextField();
        
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Categoría:"));
        panel.add(categoriaField);
        panel.add(new JLabel("Precio:"));
        panel.add(precioField);
        panel.add(new JLabel("Stock:"));
        panel.add(stockField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Agregar Nuevo Producto", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String id = idField.getText();
                String nombre = nombreField.getText();
                String categoria = categoriaField.getText();
                double precio = Double.parseDouble(precioField.getText());
                int stock = Integer.parseInt(stockField.getText());
                
                Producto nuevo = new Producto(id, nombre, categoria, precio, stock);
                boolean agregado = manager.agregarProducto(nuevo);
                
                if (agregado) {
                    cargarProductos();
                    resultadoArea.setText("Producto agregado exitosamente: " + nombre);
                } else {
                    resultadoArea.setText("Error: El ID ya existe");
                }
            } catch (NumberFormatException ex) {
                resultadoArea.setText("Error: Precio y Stock deben ser números válidos");
            }
        }
    }

    private void eliminarProducto() {
        int selectedRow = tablaProductos.getSelectedRow();
        if (selectedRow >= 0) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            String nombre = (String) tableModel.getValueAt(selectedRow, 1);
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Eliminar producto: " + nombre + "?", 
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                boolean eliminado = manager.eliminarProducto(id);
                if (eliminado) {
                    cargarProductos();
                    resultadoArea.setText("Producto eliminado: " + nombre);
                }
            }
        } else {
            resultadoArea.setText("Seleccione un producto para eliminar");
        }
    }

    private void mostrarEstadisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== ESTADÍSTICAS ===\n");
        stats.append("Total de productos: ").append(manager.getTotalProductos()).append("\n");
        stats.append("Productos con bajas existencias: ").append(manager.getConjuntoBajasExistencias().size()).append("\n");
        stats.append("\nPor categoría:\n");
        stats.append("- Electrónica: ").append(manager.contarPorCategoria("Electrónica")).append("\n");
        stats.append("- Oficina: ").append(manager.contarPorCategoria("Oficina")).append("\n");
        stats.append("- Muebles: ").append(manager.contarPorCategoria("Muebles")).append("\n");
        
        resultadoArea.setText(stats.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MainFrame().setVisible(true);
        });
    }
}
```
