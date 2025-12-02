
package DOM;

/**
 *
 * @author angellunaperez
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Clase principal que implementa la interfaz gráfica del simulador DOM
 * @author angellunaperez
 */
public class SimuladorDOMSwing extends JFrame {
    private ArbolDOM arbol;
    private JTextArea areaArbol;
    private JTextArea areaHTML;
    private JTextField txtEtiqueta;
    private JTextField txtContenido;
    private JTextField txtEstilo;
    private JTextField txtEliminar;
    private DefaultListModel<String> listModel;
    private JList<String> listaNodos;
    
    public SimuladorDOMSwing() {
        arbol = new ArbolDOM();
        inicializarComponentes();
        configurarVentana();
    }
    
    private void inicializarComponentes() {
        // Configurar el frame principal
        setTitle("Simulador DOM con Árbol Binario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Panel izquierdo: Árbol y controles
        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 5));
        panelIzquierdo.setBorder(new TitledBorder("Estructura del Árbol DOM"));
        
        areaArbol = new JTextArea(20, 25);
        areaArbol.setEditable(false);
        areaArbol.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollArbol = new JScrollPane(areaArbol);
        
        // Panel de controles para agregar nodos
        JPanel panelControles = new JPanel(new GridBagLayout());
        panelControles.setBorder(new TitledBorder("Agregar Elemento DOM"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelControles.add(new JLabel("Etiqueta HTML:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        txtEtiqueta = new JTextField(15);
        panelControles.add(txtEtiqueta, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelControles.add(new JLabel("Contenido:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        txtContenido = new JTextField(15);
        panelControles.add(txtContenido, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelControles.add(new JLabel("Estilo CSS:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        txtEstilo = new JTextField(15);
        txtEstilo.setText("color: black;");
        panelControles.add(txtEstilo, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton btnAgregar = new JButton("Agregar Elemento");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarNodo();
            }
        });
        panelControles.add(btnAgregar, gbc);
        
        // Panel para eliminar nodos
        JPanel panelEliminar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelEliminar.setBorder(new TitledBorder("Eliminar Elemento"));
        
        panelEliminar.add(new JLabel("Etiqueta a eliminar:"));
        txtEliminar = new JTextField(10);
        panelEliminar.add(txtEliminar);
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarNodo();
            }
        });
        panelEliminar.add(btnEliminar);
        
        // Lista de nodos en orden
        JPanel panelLista = new JPanel(new BorderLayout(5, 5));
        panelLista.setBorder(new TitledBorder("Nodos en Orden"));
        
        listModel = new DefaultListModel<>();
        listaNodos = new JList<>(listModel);
        JScrollPane scrollLista = new JScrollPane(listaNodos);
        panelLista.add(scrollLista, BorderLayout.CENTER);
        
        // Panel para botones adicionales
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 5, 5));
        JButton btnActualizar = new JButton("Actualizar Vista");
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarVista();
            }
        });
        
        JButton btnLimpiar = new JButton("Limpiar Todo");
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarTodo();
            }
        });
        
        JButton btnEjemplo = new JButton("Cargar Ejemplo");
        btnEjemplo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarEjemplo();
            }
        });
        
        panelBotones.add(btnActualizar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnEjemplo);
        
        // Agregar componentes al panel izquierdo
        panelIzquierdo.add(scrollArbol, BorderLayout.CENTER);
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.add(panelControles);
        panelSuperior.add(panelEliminar);
        panelSuperior.add(panelLista);
        panelSuperior.add(panelBotones);
        
        panelIzquierdo.add(panelSuperior, BorderLayout.SOUTH);
        
        // Panel derecho: Vista HTML
        JPanel panelDerecho = new JPanel(new BorderLayout(5, 5));
        panelDerecho.setBorder(new TitledBorder("Vista Previa HTML"));
        
        areaHTML = new JTextArea(20, 40);
        areaHTML.setEditable(false);
        areaHTML.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaHTML.setBackground(new Color(240, 240, 240));
        JScrollPane scrollHTML = new JScrollPane(areaHTML);
        
        // Botón para copiar HTML
        JButton btnCopiarHTML = new JButton("Copiar HTML al Portapapeles");
        btnCopiarHTML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copiarHTML();
            }
        });
        
        panelDerecho.add(scrollHTML, BorderLayout.CENTER);
        panelDerecho.add(btnCopiarHTML, BorderLayout.SOUTH);
        
        // Agregar paneles al frame
        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);
        
        // Agregar algunos elementos por defecto
        cargarElementosPorDefecto();
        actualizarVista();
    }
    
    private void configurarVentana() {
        pack();
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void agregarNodo() {
        String etiqueta = txtEtiqueta.getText().trim();
        String contenido = txtContenido.getText().trim();
        String estilo = txtEstilo.getText().trim();
        
        if (etiqueta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La etiqueta no puede estar vacía", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (contenido.isEmpty()) {
            contenido = "Contenido de " + etiqueta;
        }
        
        boolean insertado = arbol.insertar(etiqueta, contenido, estilo);
        
        if (insertado) {
            JOptionPane.showMessageDialog(this, 
                    "Elemento '" + etiqueta + "' agregado correctamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarVista();
            txtEtiqueta.setText("");
            txtContenido.setText("");
            txtEtiqueta.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this, 
                    "No se pudo agregar el elemento. ¿Ya existe?",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarNodo() {
        String etiqueta = txtEliminar.getText().trim();
        
        if (etiqueta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una etiqueta para eliminar", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el elemento '" + etiqueta + "'?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = arbol.eliminar(etiqueta);
            
            if (eliminado) {
                JOptionPane.showMessageDialog(this, 
                        "Elemento '" + etiqueta + "' eliminado correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarVista();
                txtEliminar.setText("");
            } else {
                JOptionPane.showMessageDialog(this, 
                        "No se encontró el elemento '" + etiqueta + "'",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarVista() {
        // Actualizar representación textual del árbol
        actualizarAreaArbol();
        
        // Actualizar HTML generado
        actualizarHTML();
        
        // Actualizar lista de nodos en orden
        actualizarListaNodos();
    }
    
    private void actualizarAreaArbol() {
        StringBuilder sb = new StringBuilder();
        sb.append("ESTRUCTURA DEL ÁRBOL DOM\n");
        sb.append("========================\n\n");
        
        if (arbol.estaVacio()) {
            sb.append("El árbol está vacío.\n");
            sb.append("Agrega elementos usando el formulario.\n");
        } else {
            sb.append("Raíz: ").append(arbol.getRaiz().toString()).append("\n\n");
            sb.append("Recorrido Inorden:\n");
            List<NodoDOM> nodos = arbol.recorrerInorden();
            for (NodoDOM nodo : nodos) {
                sb.append("  • ").append(nodo.toString()).append("\n");
            }
        }
        
        areaArbol.setText(sb.toString());
    }
    
    private void actualizarHTML() {
        if (arbol.estaVacio()) {
            areaHTML.setText("<!-- El árbol está vacío -->\n" +
                            "<!-- Agrega elementos para generar HTML -->");
        } else {
            areaHTML.setText(arbol.generarHTML());
        }
    }
    
    private void actualizarListaNodos() {
        listModel.clear();
        List<NodoDOM> nodos = arbol.recorrerInorden();
        
        for (NodoDOM nodo : nodos) {
            String info = nodo.getEtiqueta() + " - " + nodo.getContenido();
            if (!nodo.getEstilo().isEmpty()) {
                info += " [" + nodo.getEstilo() + "]";
            }
            listModel.addElement(info);
        }
    }
    
    private void cargarElementosPorDefecto() {
        arbol.insertar("h1", "Mi Página Web Generada", "color: #2c3e50; text-align: center;");
        arbol.insertar("p", "Esta es una página web generada dinámicamente usando un árbol binario que simula el DOM.", "color: #34495e;");
        arbol.insertar("div", "Contenedor principal", "border: 1px solid #bdc3c7; padding: 15px;");
    }
    
    private void cargarEjemplo() {
        limpiarTodo();
        
        // Ejemplo de estructura web completa
        arbol.insertar("html", "Documento HTML", "");
        arbol.insertar("head", "Cabecera del documento", "");
        arbol.insertar("title", "Mi Página Web - Ejemplo", "");
        arbol.insertar("body", "Cuerpo del documento", "background-color: #f8f9fa;");
        arbol.insertar("header", "Encabezado principal", "background: #3498db; color: white; padding: 20px;");
        arbol.insertar("h1", "Bienvenido al Simulador DOM", "text-align: center;");
        arbol.insertar("nav", "Menú de navegación", "background: #2c3e50; padding: 10px;");
        arbol.insertar("main", "Contenido principal", "padding: 20px;");
        arbol.insertar("section", "Sección de artículos", "");
        arbol.insertar("article", "Artículo sobre árboles binarios", "border: 1px solid #ddd; margin: 10px; padding: 15px;");
        arbol.insertar("h2", "Árboles Binarios en Java", "");
        arbol.insertar("p", "Los árboles binarios son estructuras de datos fundamentales en informática...", "");
        arbol.insertar("footer", "Pie de página", "background: #34495e; color: white; text-align: center; padding: 15px;");
        arbol.insertar("p", "© 2024 Simulador DOM - Todos los derechos reservados", "");
        
        actualizarVista();
        JOptionPane.showMessageDialog(this, 
                "Ejemplo cargado correctamente.\n" +
                "Observa cómo se estructura el HTML a partir del árbol.",
                "Ejemplo Cargado", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void limpiarTodo() {
        arbol = new ArbolDOM();
        actualizarVista();
        JOptionPane.showMessageDialog(this, 
                "Todo el contenido ha sido eliminado.",
                "Árbol Limpiado", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void copiarHTML() {
        String html = areaHTML.getText();
        java.awt.Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(new java.awt.datatransfer.StringSelection(html), null);
        JOptionPane.showMessageDialog(this, 
                "HTML copiado al portapapeles.",
                "Copiado", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimuladorDOMSwing();
            }
        });
    }
}