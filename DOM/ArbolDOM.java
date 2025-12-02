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