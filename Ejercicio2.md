# Ejercicio 2
### Clase ArbolBinarioBusqueda 
```java
package ABB;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa un Árbol Binario de Búsqueda (ABB)
 * @author angellunaperez
 */
public class ArbolBinarioBusqueda {
    private NodoABB raiz;
    private List<Integer> recorridoInorden;
    private List<Integer> recorridoPreorden;
    private List<Integer> recorridoPostorden;
    private StringBuilder representacionVisual;
    
    public ArbolBinarioBusqueda() {
        this.raiz = null;
        this.recorridoInorden = new ArrayList<>();
        this.recorridoPreorden = new ArrayList<>();
        this.recorridoPostorden = new ArrayList<>();
        this.representacionVisual = new StringBuilder();
    }
    
    // ========== OPERACIONES BÁSICAS ==========
    
    /**
     * Inserta un nuevo valor en el árbol
     * @param valor Valor a insertar
     * @return true si se insertó correctamente
     */
    public boolean insertar(int valor) {
        if (buscar(valor) != null) {
            return false; // No permite duplicados
        }
        
        raiz = insertarRecursivo(raiz, valor);
        return true;
    }
    
    private NodoABB insertarRecursivo(NodoABB actual, int valor) {
        if (actual == null) {
            return new NodoABB(valor);
        }
        
        if (valor < actual.getValor()) {
            actual.hijoIzquierdo = insertarRecursivo(actual.hijoIzquierdo, valor);
        } else if (valor > actual.getValor()) {
            actual.hijoDerecho = insertarRecursivo(actual.hijoDerecho, valor);
        }
        
        return actual;
    }
    
    /**
     * Elimina un valor del árbol
     * @param valor Valor a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(int valor) {
        if (buscar(valor) == null) {
            return false; // Valor no encontrado
        }
        
        raiz = eliminarRecursivo(raiz, valor);
        return true;
    }
    
    private NodoABB eliminarRecursivo(NodoABB actual, int valor) {
        if (actual == null) {
            return null;
        }
        
        if (valor < actual.getValor()) {
            actual.hijoIzquierdo = eliminarRecursivo(actual.hijoIzquierdo, valor);
        } else if (valor > actual.getValor()) {
            actual.hijoDerecho = eliminarRecursivo(actual.hijoDerecho, valor);
        } else {
            // Nodo encontrado
            // Caso 1: Nodo sin hijos o con un solo hijo
            if (actual.hijoIzquierdo == null) {
                return actual.hijoDerecho;
            } else if (actual.hijoDerecho == null) {
                return actual.hijoIzquierdo;
            }
            
            // Caso 2: Nodo con dos hijos
            // Obtener el sucesor inorden (mínimo en el subárbol derecho)
            actual.setValor(encontrarMinimo(actual.hijoDerecho));
            
            // Eliminar el sucesor
            actual.hijoDerecho = eliminarRecursivo(actual.hijoDerecho, actual.getValor());
        }
        
        return actual;
    }
    
    private int encontrarMinimo(NodoABB nodo) {
        int minimo = nodo.getValor();
        while (nodo.hijoIzquierdo != null) {
            minimo = nodo.hijoIzquierdo.getValor();
            nodo = nodo.hijoIzquierdo;
        }
        return minimo;
    }
    
    /**
     * Busca un valor en el árbol
     * @param valor Valor a buscar
     * @return El nodo encontrado o null si no existe
     */
    public NodoABB buscar(int valor) {
        return buscarRecursivo(raiz, valor);
    }
    
    private NodoABB buscarRecursivo(NodoABB actual, int valor) {
        if (actual == null || actual.getValor() == valor) {
            return actual;
        }
        
        if (valor < actual.getValor()) {
            return buscarRecursivo(actual.hijoIzquierdo, valor);
        } else {
            return buscarRecursivo(actual.hijoDerecho, valor);
        }
    }
    
    /**
     * Limpia todo el árbol
     */
    public void limpiarArbol() {
        raiz = null;
    }
    
    // ========== RECORRIDOS ==========
    
    /**
     * Realiza recorrido Inorden (Izq, Raíz, Der)
     * @return Lista de valores en orden
     */
    public List<Integer> recorridoInorden() {
        recorridoInorden.clear();
        recorridoInordenRecursivo(raiz);
        return new ArrayList<>(recorridoInorden);
    }
    
    private void recorridoInordenRecursivo(NodoABB nodo) {
        if (nodo != null) {
            recorridoInordenRecursivo(nodo.hijoIzquierdo);
            recorridoInorden.add(nodo.getValor());
            recorridoInordenRecursivo(nodo.hijoDerecho);
        }
    }
    
    /**
     * Realiza recorrido Preorden (Raíz, Izq, Der)
     * @return Lista de valores en preorden
     */
    public List<Integer> recorridoPreorden() {
        recorridoPreorden.clear();
        recorridoPreordenRecursivo(raiz);
        return new ArrayList<>(recorridoPreorden);
    }
    
    private void recorridoPreordenRecursivo(NodoABB nodo) {
        if (nodo != null) {
            recorridoPreorden.add(nodo.getValor());
            recorridoPreordenRecursivo(nodo.hijoIzquierdo);
            recorridoPreordenRecursivo(nodo.hijoDerecho);
        }
    }
    
    /**
     * Realiza recorrido Postorden (Izq, Der, Raíz)
     * @return Lista de valores en postorden
     */
    public List<Integer> recorridoPostorden() {
        recorridoPostorden.clear();
        recorridoPostordenRecursivo(raiz);
        return new ArrayList<>(recorridoPostorden);
    }
    
    private void recorridoPostordenRecursivo(NodoABB nodo) {
        if (nodo != null) {
            recorridoPostordenRecursivo(nodo.hijoIzquierdo);
            recorridoPostordenRecursivo(nodo.hijoDerecho);
            recorridoPostorden.add(nodo.getValor());
        }
    }
    
    // ========== REPRESENTACIÓN VISUAL ==========
    
    /**
     * Genera una representación visual del árbol
     * @return Cadena con la representación del árbol
     */
    public String generarRepresentacionVisual() {
        representacionVisual.setLength(0);
        representacionVisual.append("REPRESENTACIÓN DEL ÁRBOL BINARIO DE BÚSQUEDA\n");
        representacionVisual.append("=============================================\n\n");
        
        if (raiz == null) {
            representacionVisual.append("El árbol está vacío.\n");
        } else {
            generarRepresentacionRecursivo(raiz, 0, "Raíz: ");
        }
        
        return representacionVisual.toString();
    }
    
    private void generarRepresentacionRecursivo(NodoABB nodo, int nivel, String prefijo) {
        if (nodo != null) {
            // Espacios para indentación
            String espacios = "  ".repeat(nivel);
            String conector = nivel == 0 ? "" : "├─ ";
            
            representacionVisual.append(espacios).append(conector).append(prefijo);
            representacionVisual.append(nodo.getValor()).append("\n");
            
            // Procesar hijos
            if (nodo.hijoIzquierdo != null || nodo.hijoDerecho != null) {
                if (nodo.hijoIzquierdo != null) {
                    generarRepresentacionRecursivo(nodo.hijoIzquierdo, nivel + 1, "Izq: ");
                } else {
                    representacionVisual.append("  ".repeat(nivel + 1)).append("├─ Izq: null\n");
                }
                
                if (nodo.hijoDerecho != null) {
                    generarRepresentacionRecursivo(nodo.hijoDerecho, nivel + 1, "Der: ");
                } else {
                    representacionVisual.append("  ".repeat(nivel + 1)).append("└─ Der: null\n");
                }
            }
        }
    }
    
    /**
     * Genera representación en formato de lista con niveles
     * @return Cadena con árbol por niveles
     */
    public String generarArbolPorNiveles() {
        StringBuilder sb = new StringBuilder();
        sb.append("ÁRBOL POR NIVELES\n");
        sb.append("=================\n");
        
        if (raiz == null) {
            sb.append("Árbol vacío\n");
            return sb.toString();
        }
        
        List<List<Integer>> niveles = new ArrayList<>();
        obtenerNivelesRecursivo(raiz, 0, niveles);
        
        for (int i = 0; i < niveles.size(); i++) {
            sb.append("Nivel ").append(i).append(": ");
            for (int valor : niveles.get(i)) {
                sb.append(valor).append(" ");
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    private void obtenerNivelesRecursivo(NodoABB nodo, int nivel, List<List<Integer>> niveles) {
        if (nodo == null) return;
        
        if (niveles.size() <= nivel) {
            niveles.add(new ArrayList<>());
        }
        
        niveles.get(nivel).add(nodo.getValor());
        
        obtenerNivelesRecursivo(nodo.hijoIzquierdo, nivel + 1, niveles);
        obtenerNivelesRecursivo(nodo.hijoDerecho, nivel + 1, niveles);
    }
    
    // ========== GETTERS Y VERIFICACIONES ==========
    
    public NodoABB getRaiz() {
        return raiz;
    }
    
    public boolean estaVacio() {
        return raiz == null;
    }
    
    public int obtenerAltura() {
        return calcularAltura(raiz);
    }
    
    private int calcularAltura(NodoABB nodo) {
        if (nodo == null) {
            return 0;
        }
        
        int alturaIzq = calcularAltura(nodo.hijoIzquierdo);
        int alturaDer = calcularAltura(nodo.hijoDerecho);
        
        return Math.max(alturaIzq, alturaDer) + 1;
    }
    
    public int obtenerTamaño() {
        return calcularTamaño(raiz);
    }
    
    private int calcularTamaño(NodoABB nodo) {
        if (nodo == null) {
            return 0;
        }
        
        return calcularTamaño(nodo.hijoIzquierdo) + calcularTamaño(nodo.hijoDerecho) + 1;
    }
    
    /**
     * Verifica si el árbol cumple la propiedad de ABB
     * @return true si es un ABB válido
     */
    public boolean esABBValido() {
        return esABBValidoRecursivo(raiz, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private boolean esABBValidoRecursivo(NodoABB nodo, int min, int max) {
        if (nodo == null) {
            return true;
        }
        
        if (nodo.getValor() <= min || nodo.getValor() >= max) {
            return false;
        }
        
        return esABBValidoRecursivo(nodo.hijoIzquierdo, min, nodo.getValor()) &&
               esABBValidoRecursivo(nodo.hijoDerecho, nodo.getValor(), max);
    }
}
```
### NodoABB
```java

package ABB;

/**
 * @author angellunaperez
 *  Esta Clase  representa un nodo del Árbol Binario de Búsqueda (ABB)
 * @author angellunaperez
 */
public class NodoABB {
    private int valor;
    public NodoABB hijoIzquierdo;
    public NodoABB hijoDerecho;
    
    public NodoABB(int valor) {
        this.valor = valor;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }
    
    // Getters y Setters
    public int getValor() {
        return valor;
    }
    
    public void setValor(int valor) {
        this.valor = valor;
    }
    
    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}
```
### Clase PruebaABB
```java
package ABB;

import java.util.List;

/**
 * Clase de prueba para el Árbol Binario de Búsqueda
 * @author angellunaperez
 */
public class PruebaABB {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DEL ÁRBOL BINARIO DE BÚSQUEDA ===");
        
        // 1. Crear una instancia del árbol ABB
        ArbolBinarioBusqueda arbol = new ArbolBinarioBusqueda();
        
        System.out.println("\n1. Insertando valores: 50, 30, 70, 20, 40, 60, 80");
        
        // 2. Insertar valores
        arbol.insertar(50);
        arbol.insertar(30);
        arbol.insertar(70);
        arbol.insertar(20);
        arbol.insertar(40);
        arbol.insertar(60);
        arbol.insertar(80);
        
        // 3. Mostrar representación visual
        System.out.println("\n2. Representación del árbol:");
        System.out.println(arbol.generarRepresentacionVisual());
        
        // 4. Recorridos
        System.out.println("\n3. Recorridos del árbol:");
        
        List<Integer> inorden = arbol.recorridoInorden();
        System.out.println("   Inorden (Izq, Raíz, Der): " + inorden);
        
        List<Integer> preorden = arbol.recorridoPreorden();
        System.out.println("   Preorden (Raíz, Izq, Der): " + preorden);
        
        List<Integer> postorden = arbol.recorridoPostorden();
        System.out.println("   Postorden (Izq, Der, Raíz): " + postorden);
        
        // 5. Búsqueda
        System.out.println("\n4. Pruebas de búsqueda:");
        int valorBuscado = 40;
        System.out.println("   Buscando " + valorBuscado + ": " + 
                (arbol.buscar(valorBuscado) != null ? "ENCONTRADO" : "NO ENCONTRADO"));
        
        valorBuscado = 99;
        System.out.println("   Buscando " + valorBuscado + ": " + 
                (arbol.buscar(valorBuscado) != null ? "ENCONTRADO" : "NO ENCONTRADO"));
        
        // 6. Eliminación
        System.out.println("\n5. Prueba de eliminación:");
        System.out.println("   Eliminando valor 20...");
        arbol.eliminar(20);
        
        System.out.println("\n   Árbol después de eliminar 20:");
        System.out.println(arbol.generarArbolPorNiveles());
        
        // 7. Verificar propiedad ABB
        System.out.println("\n6. Verificación de propiedad ABB:");
        System.out.println("   ¿Es un ABB válido? " + 
                (arbol.esABBValido() ? "SÍ ✓" : "NO ✗"));
        
        // 8. Estadísticas
        System.out.println("\n7. Estadísticas del árbol:");
        System.out.println("   Total de nodos: " + arbol.obtenerTamaño());
        System.out.println("   Altura del árbol: " + arbol.obtenerAltura());
        System.out.println("   ¿Está vacío? " + arbol.estaVacio());
        
        // 9. Ejercicio específico
        System.out.println("\n8. EJERCICIO ESPECÍFICO:");
        System.out.println("   Insertando valores: 20, 23, 67, 40, 70");
        
        ArbolBinarioBusqueda arbolEjercicio = new ArbolBinarioBusqueda();
        arbolEjercicio.insertar(20);
        arbolEjercicio.insertar(23);
        arbolEjercicio.insertar(67);
        arbolEjercicio.insertar(40);
        arbolEjercicio.insertar(70);
        
        List<Integer> postordenEjercicio = arbolEjercicio.recorridoPostorden();
        System.out.println("\n   Recorrido Postorden (Izq, Der, Raíz):");
        System.out.println("   Secuencia: " + postordenEjercicio);
        System.out.print("   Valores: ");
        for (int valor : postordenEjercicio) {
            System.out.print(valor + " ");
        }
        System.out.println();
        
        System.out.println("\n=== PRUEBA COMPLETADA ===");
    }
}
```
### Clase VisualizadorABBSwing
```java
package ABB;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Interfaz gráfica para visualizar y manipular Árboles Binarios de Búsqueda
 * @author angellunaperez
 */
public class VisualizadorABBSwing extends JFrame {
    private ArbolBinarioBusqueda arbol;
    private JTextArea areaArbol;
    private JTextArea areaRecorridos;
    private JTextField txtInsertar;
    private JTextField txtEliminar;
    private JTextField txtBuscar;
    private JLabel lblResultadoBusqueda;
    private JLabel lblEstadisticas;
    
    public VisualizadorABBSwing() {
        arbol = new ArbolBinarioBusqueda();
        inicializarComponentes();
        configurarVentana();
    }
    
    private void inicializarComponentes() {
        // Configurar el frame principal
        setTitle("Visualizador de Árbol Binario de Búsqueda (ABB)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior: Estadísticas e información
        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 10, 5));
        panelSuperior.setBorder(new TitledBorder("Información del Árbol"));
        
        lblEstadisticas = new JLabel("Árbol vacío | Nodos: 0 | Altura: 0");
        lblEstadisticas.setFont(new Font("Arial", Font.BOLD, 12));
        
        JPanel panelVerificacion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnVerificarABB = new JButton("Verificar Propiedad ABB");
        btnVerificarABB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarPropiedadABB();
            }
        });
        panelVerificacion.add(btnVerificarABB);
        
        panelSuperior.add(lblEstadisticas);
        panelSuperior.add(panelVerificacion);
        
        // Panel izquierdo: Visualización del árbol
        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 5));
        panelIzquierdo.setBorder(new TitledBorder("Representación del Árbol"));
        
        areaArbol = new JTextArea(25, 35);
        areaArbol.setEditable(false);
        areaArbol.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaArbol.setBackground(new Color(255, 255, 240));
        JScrollPane scrollArbol = new JScrollPane(areaArbol);
        
        // Botones para diferentes vistas del árbol
        JPanel panelVistas = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JButton btnVistaJerarquica = new JButton("Vista Jerárquica");
        JButton btnVistaNiveles = new JButton("Vista por Niveles");
        JButton btnDibujarArbol = new JButton("Dibujo del Árbol");
        
        btnVistaJerarquica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVistaJerarquica();
            }
        });
        
        btnVistaNiveles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVistaPorNiveles();
            }
        });
        
        btnDibujarArbol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDibujoArbol();
            }
        });
        
        panelVistas.add(btnVistaJerarquica);
        panelVistas.add(btnVistaNiveles);
        panelVistas.add(btnDibujarArbol);
        
        panelIzquierdo.add(scrollArbol, BorderLayout.CENTER);
        panelIzquierdo.add(panelVistas, BorderLayout.SOUTH);
        
        // Panel derecho: Controles y recorridos
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        
        // Panel de operaciones básicas
        JPanel panelOperaciones = new JPanel(new GridBagLayout());
        panelOperaciones.setBorder(new TitledBorder("Operaciones del ABB"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Insertar
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelOperaciones.add(new JLabel("INSERTAR NODO:"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelOperaciones.add(new JLabel("Valor:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        txtInsertar = new JTextField(10);
        panelOperaciones.add(txtInsertar, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 1;
        JButton btnInsertar = new JButton("Insertar");
        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertarNodo();
            }
        });
        panelOperaciones.add(btnInsertar, gbc);
        
        // Eliminar
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelOperaciones.add(new JLabel("ELIMINAR NODO:"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panelOperaciones.add(new JLabel("Valor:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        txtEliminar = new JTextField(10);
        panelOperaciones.add(txtEliminar, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 3;
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarNodo();
            }
        });
        panelOperaciones.add(btnEliminar, gbc);
        
        // Buscar
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelOperaciones.add(new JLabel("BUSCAR NODO:"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panelOperaciones.add(new JLabel("Valor:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        txtBuscar = new JTextField(10);
        panelOperaciones.add(txtBuscar, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 5;
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarNodo();
            }
        });
        panelOperaciones.add(btnBuscar, gbc);
        
        // Resultado de búsqueda
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        lblResultadoBusqueda = new JLabel(" ");
        lblResultadoBusqueda.setForeground(Color.BLUE);
        panelOperaciones.add(lblResultadoBusqueda, gbc);
        
        // Limpiar árbol
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        JButton btnLimpiar = new JButton("Limpiar Árbol Completo");
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarArbol();
            }
        });
        panelOperaciones.add(btnLimpiar, gbc);
        
        // Panel de recorridos
        JPanel panelRecorridos = new JPanel(new BorderLayout(5, 5));
        panelRecorridos.setBorder(new TitledBorder("Recorridos del Árbol"));
        
        areaRecorridos = new JTextArea(15, 25);
        areaRecorridos.setEditable(false);
        areaRecorridos.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollRecorridos = new JScrollPane(areaRecorridos);
        
        // Botones para recorridos
        JPanel panelBotonesRecorridos = new JPanel(new GridLayout(1, 3, 5, 5));
        JButton btnInorden = new JButton("Inorden");
        JButton btnPreorden = new JButton("Preorden");
        JButton btnPostorden = new JButton("Postorden");
        
        btnInorden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRecorridoInorden();
            }
        });
        
        btnPreorden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRecorridoPreorden();
            }
        });
        
        btnPostorden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRecorridoPostorden();
            }
        });
        
        panelBotonesRecorridos.add(btnInorden);
        panelBotonesRecorridos.add(btnPreorden);
        panelBotonesRecorridos.add(btnPostorden);
        
        panelRecorridos.add(scrollRecorridos, BorderLayout.CENTER);
        panelRecorridos.add(panelBotonesRecorridos, BorderLayout.SOUTH);
        
        // Panel de ejemplo del ejercicio
        JPanel panelEjercicio = new JPanel(new BorderLayout(5, 5));
        panelEjercicio.setBorder(new TitledBorder("Ejemplo del Ejercicio"));
        
        JTextArea areaEjemplo = new JTextArea(5, 25);
        areaEjemplo.setEditable(false);
        areaEjemplo.setFont(new Font("Arial", Font.PLAIN, 11));
        areaEjemplo.setText("Ejercicio 02:\n" +
                          "Insertar valores: 20, 23, 67, 40, 70\n\n" +
                          "Recorrido Postorden (Izq, Der, Raíz):\n" +
                          "40 70 67 23 20");
        
        JButton btnCargarEjemplo = new JButton("Cargar Ejemplo");
        btnCargarEjemplo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarEjercicio();
            }
        });
        
        panelEjercicio.add(areaEjemplo, BorderLayout.CENTER);
        panelEjercicio.add(btnCargarEjemplo, BorderLayout.SOUTH);
        
        // Agregar paneles al panel derecho
        panelDerecho.add(panelOperaciones);
        panelDerecho.add(Box.createVerticalStrut(10));
        panelDerecho.add(panelRecorridos);
        panelDerecho.add(Box.createVerticalStrut(10));
        panelDerecho.add(panelEjercicio);
        
        // Agregar todos los paneles al frame
        add(panelSuperior, BorderLayout.NORTH);
        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);
        
        // Mostrar vista inicial
        mostrarVistaJerarquica();
        actualizarEstadisticas();
    }
    
    private void configurarVentana() {
        pack();
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // ========== MÉTODOS DE OPERACIONES ==========
    
    private void insertarNodo() {
        try {
            String texto = txtInsertar.getText().trim();
            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un valor numérico", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int valor = Integer.parseInt(texto);
            boolean insertado = arbol.insertar(valor);
            
            if (insertado) {
                JOptionPane.showMessageDialog(this, 
                        "Valor " + valor + " insertado correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                mostrarVistaJerarquica();
                actualizarEstadisticas();
                txtInsertar.setText("");
                txtInsertar.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, 
                        "El valor " + valor + " ya existe en el árbol",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarNodo() {
        try {
            String texto = txtEliminar.getText().trim();
            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un valor numérico", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int valor = Integer.parseInt(texto);
            
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar el valor " + valor + "?",
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = arbol.eliminar(valor);
                
                if (eliminado) {
                    JOptionPane.showMessageDialog(this, 
                            "Valor " + valor + " eliminado correctamente",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    mostrarVistaJerarquica();
                    actualizarEstadisticas();
                    txtEliminar.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, 
                            "No se encontró el valor " + valor + " en el árbol",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarNodo() {
        try {
            String texto = txtBuscar.getText().trim();
            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un valor numérico", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int valor = Integer.parseInt(texto);
            NodoABB encontrado = arbol.buscar(valor);
            
            if (encontrado != null) {
                lblResultadoBusqueda.setText("✓ VALOR " + valor + " ENCONTRADO en el árbol");
                lblResultadoBusqueda.setForeground(new Color(0, 128, 0)); // Verde
                
                // Resaltar el nodo en la vista (simulado)
                String representacion = areaArbol.getText();
                String resaltada = representacion.replace(
                        String.valueOf(valor), 
                        "[" + valor + "]"
                );
                areaArbol.setText(resaltada);
            } else {
                lblResultadoBusqueda.setText("✗ VALOR " + valor + " NO ENCONTRADO en el árbol");
                lblResultadoBusqueda.setForeground(Color.RED);
            }
            
            txtBuscar.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarArbol() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de limpiar todo el árbol?\nSe perderán todos los datos.",
                "Confirmar Limpieza", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            arbol.limpiarArbol();
            mostrarVistaJerarquica();
            actualizarEstadisticas();
            lblResultadoBusqueda.setText(" ");
            areaRecorridos.setText("");
            JOptionPane.showMessageDialog(this, 
                    "Árbol limpiado correctamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // ========== MÉTODOS DE RECORRIDOS ==========
    
    private void mostrarRecorridoInorden() {
        List<Integer> recorrido = arbol.recorridoInorden();
        mostrarRecorrido("RECORRIDO INORDEN (Izq, Raíz, Der)", recorrido);
    }
    
    private void mostrarRecorridoPreorden() {
        List<Integer> recorrido = arbol.recorridoPreorden();
        mostrarRecorrido("RECORRIDO PREORDEN (Raíz, Izq, Der)", recorrido);
    }
    
    private void mostrarRecorridoPostorden() {
        List<Integer> recorrido = arbol.recorridoPostorden();
        mostrarRecorrido("RECORRIDO POSTORDEN (Izq, Der, Raíz)", recorrido);
    }
    
    private void mostrarRecorrido(String titulo, List<Integer> recorrido) {
        StringBuilder sb = new StringBuilder();
        sb.append(titulo).append("\n");
        sb.append("=".repeat(titulo.length())).append("\n\n");
        
        if (recorrido.isEmpty()) {
            sb.append("El árbol está vacío.\n");
        } else {
            sb.append("Secuencia: ");
            for (int i = 0; i < recorrido.size(); i++) {
                sb.append(recorrido.get(i));
                if (i < recorrido.size() - 1) {
                    sb.append(" → ");
                }
            }
            
            sb.append("\n\nValores: ");
            for (int valor : recorrido) {
                sb.append(valor).append(" ");
            }
            
            sb.append("\n\nCantidad de nodos: ").append(recorrido.size());
        }
        
        areaRecorridos.setText(sb.toString());
    }
    
    // ========== MÉTODOS DE VISUALIZACIÓN ==========
    
    private void mostrarVistaJerarquica() {
        areaArbol.setText(arbol.generarRepresentacionVisual());
    }
    
    private void mostrarVistaPorNiveles() {
        areaArbol.setText(arbol.generarArbolPorNiveles());
    }
    
    private void mostrarDibujoArbol() {
        if (arbol.estaVacio()) {
            areaArbol.setText("El árbol está vacío.\nNo se puede dibujar.");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("DIBUJO DEL ÁRBOL BINARIO DE BÚSQUEDA\n");
        sb.append("====================================\n\n");
        
        // Dibujo ASCII simple del árbol
        dibujarArbolRecursivo(arbol.getRaiz(), "", true, sb);
        
        areaArbol.setText(sb.toString());
    }
    
    private void dibujarArbolRecursivo(NodoABB nodo, String prefijo, boolean esIzquierdo, StringBuilder sb) {
        if (nodo == null) return;
        
        sb.append(prefijo);
        sb.append(esIzquierdo ? "├── " : "└── ");
        sb.append(nodo.getValor()).append("\n");
        
        // Continuar con los hijos
        String nuevoPrefijo = prefijo + (esIzquierdo ? "│   " : "    ");
        
        // Procesar ambos hijos
        if (nodo.hijoIzquierdo != null || nodo.hijoDerecho != null) {
            if (nodo.hijoIzquierdo != null) {
                dibujarArbolRecursivo(nodo.hijoIzquierdo, nuevoPrefijo, true, sb);
            }
            if (nodo.hijoDerecho != null) {
                dibujarArbolRecursivo(nodo.hijoDerecho, nuevoPrefijo, false, sb);
            }
        }
    }
    
    // ========== MÉTODOS AUXILIARES ==========
    
    private void actualizarEstadisticas() {
        int nodos = arbol.obtenerTamaño();
        int altura = arbol.obtenerAltura();
        
        String estado = arbol.estaVacio() ? "Árbol vacío" : "Árbol con datos";
        lblEstadisticas.setText(estado + " | Nodos: " + nodos + " | Altura: " + altura);
    }
    
    private void verificarPropiedadABB() {
        if (arbol.estaVacio()) {
            JOptionPane.showMessageDialog(this, 
                    "El árbol está vacío.", 
                    "Verificación ABB", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        boolean esValido = arbol.esABBValido();
        
        if (esValido) {
            JOptionPane.showMessageDialog(this, 
                    "✓ EL ÁRBOL CUMPLE CON LA PROPIEDAD DE ABB\n\n" +
                    "Todos los nodos del subárbol izquierdo son menores\n" +
                    "y todos los nodos del subárbol derecho son mayores.", 
                    "Verificación Exitosa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                    "✗ EL ÁRBOL NO CUMPLE CON LA PROPIEDAD DE ABB\n\n" +
                    "Algún nodo viola la propiedad:\n" +
                    "• Izquierda < Raíz < Derecha", 
                    "Error en Propiedad ABB", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarEjercicio() {
        // Limpiar el árbol actual
        arbol.limpiarArbol();
        
        // Insertar valores del ejercicio: 20, 23, 67, 40, 70
        int[] valores = {20, 23, 67, 40, 70};
        
        for (int valor : valores) {
            arbol.insertar(valor);
        }
        
        // Mostrar resultados
        mostrarVistaJerarquica();
        actualizarEstadisticas();
        
        // Mostrar recorrido Postorden del ejercicio
        List<Integer> postorden = arbol.recorridoPostorden();
        StringBuilder sb = new StringBuilder();
        sb.append("EJEMPLO DEL EJERCICIO CARGADO\n");
        sb.append("==============================\n\n");
        sb.append("Valores insertados: 20, 23, 67, 40, 70\n\n");
        sb.append("Recorrido Postorden (Izq, Der, Raíz):\n");
        
        for (int i = 0; i < postorden.size(); i++) {
            sb.append(postorden.get(i));
            if (i < postorden.size() - 1) {
                sb.append(" → ");
            }
        }
        
        sb.append("\n\nSecuencia: ");
        for (int valor : postorden) {
            sb.append(valor).append(" ");
        }
        
        areaRecorridos.setText(sb.toString());
        
        JOptionPane.showMessageDialog(this, 
                "Ejercicio cargado correctamente.\n\n" +
                "Se insertaron los valores: 20, 23, 67, 40, 70\n" +
                "Verifique el recorrido Postorden en la sección correspondiente.",
                "Ejercicio Cargado", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VisualizadorABBSwing();
            }
        });
    }
}
```
