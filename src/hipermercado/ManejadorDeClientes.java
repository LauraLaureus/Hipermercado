package hipermercado;

public class ManejadorDeClientes extends Thread {

    private final int clientesTotales;
    private final Cola cola;
    public ManejadorDeClientes(int clientesTotales, Cola cola) {
        this.clientesTotales = clientesTotales - 3;
        this.cola = cola;
        
        añadeClientesIniciales();
    }
    
    public void run(){
        
        for (int i = 0; i < clientesTotales; i++) {
            cola.añadirFinal(new Cliente());
            try {
                Thread.sleep((long) (5000*Math.random()));
            } catch (InterruptedException ex) {}
        }
        
    }

    private void añadeClientesIniciales() {
        cola.añadirFinal(new Cliente());
        cola.añadirFinal(new Cliente());
        cola.añadirFinal(new Cliente());
    }
}
