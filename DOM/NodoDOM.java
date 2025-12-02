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