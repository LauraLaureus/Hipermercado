
package hipermercado;


public class Cola {

    private int tamaño;
    private ClienteEnCola porElPrincipio;
    private ClienteEnCola porElFinal;
    
    private class ClienteEnCola{
        public Cliente cl;
        public ClienteEnCola siguiente;
    }
    
    public synchronized Cliente sacar() {
        /*Saca y extrae si no hay nada devolver null*/
    }
    
    public synchronized void añadirFinal(Cliente cl){}
    public synchronized void añadirPrincipio(Cliente cl){}
    
    public int tamañoMaximo(){}
    
    private void modificaElTamañoMaximo(int nuevo){}
}
