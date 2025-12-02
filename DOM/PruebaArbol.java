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