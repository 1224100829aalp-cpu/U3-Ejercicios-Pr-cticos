# Ejercicio 1
### Clase ArbolDOM 
```java
package DOM;


import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa un árbol binario para simular la estructura DOM
 * @author angellunaperez
 */

public class ArbolDOM {
    private NodoDOM raiz;
    private List<NodoDOM> nodosInorden;
    
    public ArbolDOM() {
        this.raiz = null;
        this.nodosInorden = new ArrayList<>();
    }
    
    // Método público para insertar un nodo
    public boolean insertar(String etiqueta, String contenido, String estilo) {
        if (etiqueta == null || etiqueta.trim().isEmpty()) {
            return false;
        }
        
        NodoDOM nuevoNodo = new NodoDOM(etiqueta, contenido, estilo);
        
        if (raiz == null) {
            raiz = nuevoNodo;
            return true;
        }
        
        return insertarRecursivo(raiz, nuevoNodo);
    }
    
    // Método privado recursivo para inserción
    private boolean insertarRecursivo(NodoDOM actual, NodoDOM nuevo) {
        // Comparar por etiqueta para mantener orden alfabético
        int comparacion = nuevo.getEtiqueta().compareToIgnoreCase(actual.getEtiqueta());
        
        if (comparacion < 0) {
            if (actual.hijoIzquierdo == null) {
                actual.hijoIzquierdo = nuevo;
                return true;
            } else {
                return insertarRecursivo(actual.hijoIzquierdo, nuevo);
            }
        } else if (comparacion > 0) {
            if (actual.hijoDerecho == null) {
                actual.hijoDerecho = nuevo;
                return true;
            } else {
                return insertarRecursivo(actual.hijoDerecho, nuevo);
            }
        }
        
        // Si la etiqueta es igual, no insertamos duplicados
        return false;
    }
    
    // Método público para eliminar un nodo
    public boolean eliminar(String etiqueta) {
        if (raiz == null || etiqueta == null) {
            return false;
        }
        
        NodoDOM padre = null;
        NodoDOM actual = raiz;
        boolean esHijoIzquierdo = false;
        
        // Buscar el nodo a eliminar
        while (actual != null && !actual.getEtiqueta().equalsIgnoreCase(etiqueta)) {
            padre = actual;
            if (etiqueta.compareToIgnoreCase(actual.getEtiqueta()) < 0) {
                actual = actual.hijoIzquierdo;
                esHijoIzquierdo = true;
            } else {
                actual = actual.hijoDerecho;
                esHijoIzquierdo = false;
            }
        }
        
        if (actual == null) {
            return false; // Nodo no encontrado
        }
        
        // Caso 1: Nodo sin hijos
        if (actual.hijoIzquierdo == null && actual.hijoDerecho == null) {
            if (actual == raiz) {
                raiz = null;
            } else if (esHijoIzquierdo) {
                padre.hijoIzquierdo = null;
            } else {
                padre.hijoDerecho = null;
            }
        }
        // Caso 2: Nodo con un hijo
        else if (actual.hijoDerecho == null) {
            if (actual == raiz) {
                raiz = actual.hijoIzquierdo;
            } else if (esHijoIzquierdo) {
                padre.hijoIzquierdo = actual.hijoIzquierdo;
            } else {
                padre.hijoDerecho = actual.hijoIzquierdo;
            }
        } else if (actual.hijoIzquierdo == null) {
            if (actual == raiz) {
                raiz = actual.hijoDerecho;
            } else if (esHijoIzquierdo) {
                padre.hijoIzquierdo = actual.hijoDerecho;
            } else {
                padre.hijoDerecho = actual.hijoDerecho;
            }
        }
        // Caso 3: Nodo con dos hijos
        else {
            NodoDOM sucesor = obtenerSucesor(actual);
            if (actual == raiz) {
                raiz = sucesor;
            } else if (esHijoIzquierdo) {
                padre.hijoIzquierdo = sucesor;
            } else {
                padre.hijoDerecho = sucesor;
            }
            sucesor.hijoIzquierdo = actual.hijoIzquierdo;
        }
        
        return true;
    }
    
    // Método para obtener el sucesor (menor de los mayores)
    private NodoDOM obtenerSucesor(NodoDOM nodo) {
        NodoDOM padreSucesor = nodo;
        NodoDOM sucesor = nodo;
        NodoDOM actual = nodo.hijoDerecho;
        
        while (actual != null) {
            padreSucesor = sucesor;
            sucesor = actual;
            actual = actual.hijoIzquierdo;
        }
        
        if (sucesor != nodo.hijoDerecho) {
            padreSucesor.hijoIzquierdo = sucesor.hijoDerecho;
            sucesor.hijoDerecho = nodo.hijoDerecho;
        }
        
        return sucesor;
    }
    
    // Método para recorrido inorden
    public List<NodoDOM> recorrerInorden() {
        nodosInorden.clear();
        recorrerInordenRecursivo(raiz);
        return new ArrayList<>(nodosInorden);
    }
    
    private void recorrerInordenRecursivo(NodoDOM nodo) {
        if (nodo != null) {
            recorrerInordenRecursivo(nodo.hijoIzquierdo);
            nodosInorden.add(nodo);
            recorrerInordenRecursivo(nodo.hijoDerecho);
        }
    }
    
    // Método para generar HTML desde el árbol
    public String generarHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html>\n<head>\n");
        html.append("  <meta charset='UTF-8'>\n");
        html.append("  <title>Página Generada por DOM Simulado</title>\n");
        html.append("  <style>\n");
        html.append("    body { font-family: Arial, sans-serif; margin: 20px; }\n");
        html.append("    .contenedor { max-width: 800px; margin: 0 auto; }\n");
        html.append("  </style>\n");
        html.append("</head>\n<body>\n");
        html.append("  <div class='contenedor'>\n");
        
        generarHTMLRecursivo(raiz, html, 2);
        
        html.append("  </div>\n");
        html.append("</body>\n</html>");
        return html.toString();
    }
    
    private void generarHTMLRecursivo(NodoDOM nodo, StringBuilder html, int nivel) {
        if (nodo != null) {
            // Recorrido pre-orden para generar HTML en orden natural
            String indentacion = "    ".repeat(nivel);
            String etiqueta = nodo.getEtiqueta().toLowerCase();
            String estilo = nodo.getEstilo();
            
            html.append(indentacion).append("<").append(etiqueta);
            
            if (!estilo.isEmpty()) {
                html.append(" style=\"").append(estilo).append("\"");
            }
            
            html.append(">");
            html.append(nodo.getContenido());
            
            // Si tiene hijos, procesarlos
            if (nodo.hijoIzquierdo != null || nodo.hijoDerecho != null) {
                html.append("\n");
                generarHTMLRecursivo(nodo.hijoIzquierdo, html, nivel + 1);
                generarHTMLRecursivo(nodo.hijoDerecho, html, nivel + 1);
                html.append(indentacion);
            }
            
            html.append("</").append(etiqueta).append(">\n");
        }
    }
    
    // Método para buscar un nodo
    public NodoDOM buscar(String etiqueta) {
        return buscarRecursivo(raiz, etiqueta);
    }
    
    private NodoDOM buscarRecursivo(NodoDOM nodo, String etiqueta) {
        if (nodo == null) {
            return null;
        }
        
        if (nodo.getEtiqueta().equalsIgnoreCase(etiqueta)) {
            return nodo;
        }
        
        if (etiqueta.compareToIgnoreCase(nodo.getEtiqueta()) < 0) {
            return buscarRecursivo(nodo.hijoIzquierdo, etiqueta);
        } else {
            return buscarRecursivo(nodo.hijoDerecho, etiqueta);
        }
    }
    
    // Método para obtener la raíz
    public NodoDOM getRaiz() {
        return raiz;
    }
    
    // Método para verificar si el árbol está vacío
    public boolean estaVacio() {
        return raiz == null;
    }
}
```
### Clase NodoDOM
```java
package DOM;

/**
 *
 * @author angellunaperez
 * Clase que representa un nodo del árbol DOM simulado
 * @author angellunaperez
 */

public class NodoDOM {
    private String etiqueta;
    private String contenido;
    private String estilo;
    
    public NodoDOM hijoIzquierdo;
    public NodoDOM hijoDerecho;
    
    public NodoDOM(String etiqueta, String contenido) {
        this.etiqueta = etiqueta;
        this.contenido = contenido;
        this.estilo = "";
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }
    
    public NodoDOM(String etiqueta, String contenido, String estilo) {
        this.etiqueta = etiqueta;
        this.contenido = contenido;
        this.estilo = estilo;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }
    
    // Getters y Setters
    public String getEtiqueta() {
        return etiqueta;
    }
    
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
    
    public String getContenido() {
        return contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public String getEstilo() {
        return estilo;
    }
    
    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }
    
    @Override
    public String toString() {
        return etiqueta + ": " + contenido;
    }
}
```
### Clase PruebaArbol
```java
package DOM;


/**
 * Clase de prueba para el árbol binario DOM
 * @author angellunaperez
 */

public class PruebaArbol {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DEL ÁRBOL BINARIO DOM ===");
        
        // 1. Crear una instancia del árbol DOM
        ArbolDOM arbol = new ArbolDOM();
        
        System.out.println("\n1. Insertando elementos DOM:");
        System.out.println("   h1, div, p, span, footer");
        
        // 2. Insertar elementos usando el método público
        arbol.insertar("h1", "Título Principal", "color: blue;");
        arbol.insertar("div", "Contenedor Div", "border: 1px solid black;");
        arbol.insertar("p", "Párrafo de ejemplo", "color: #333;");
        arbol.insertar("span", "Texto en línea", "font-weight: bold;");
        arbol.insertar("footer", "Pie de página", "background: #eee;");
        
        // 3. Ejecutar el recorrido para verificar el orden
        System.out.println("\n2. Recorrido Inorden del árbol:");
        java.util.List<NodoDOM> nodos = arbol.recorrerInorden();
        for (NodoDOM nodo : nodos) {
            System.out.println("   - " + nodo);
        }
        
        // 4. Generar HTML
        System.out.println("\n3. HTML generado:");
        System.out.println(arbol.generarHTML());
        
        // 5. Prueba de eliminación
        System.out.println("\n4. Eliminando elemento 'div':");
        boolean eliminado = arbol.eliminar("div");
        System.out.println("   Resultado: " + (eliminado ? "Eliminado correctamente" : "No encontrado"));
        
        // 6. Recorrido después de eliminar
        System.out.println("\n5. Recorrido después de eliminar:");
        nodos = arbol.recorrerInorden();
        for (NodoDOM nodo : nodos) {
            System.out.println("   - " + nodo);
        }
        
        System.out.println("\n=== PRUEBA COMPLETADA ===");
    }
}
```
### SimuladorDOMSwing
```java

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
```

