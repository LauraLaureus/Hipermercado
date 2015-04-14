package hipermercado;

public class ManejadorDeClientes extends Thread implements StatusVisible {

    private int clientesAtendidos;
    private final int clientesTotales;
    private final Cola cola;

    public ManejadorDeClientes(int clientesTotales, Cola cola) {
        this.clientesTotales = clientesTotales;
        this.cola = cola;
        this.clientesAtendidos = 0;
    }

    public void run() {

        for (int i = 0; i < clientesTotales; i++) {
            cola.añadirFinal(new Cliente());
            clientesAtendidos = i;
            try {
                Thread.sleep((long) (5000 * Math.random()));
            } catch (InterruptedException ex) {
                break;
            }
        }
        System.out.println("MANEJADOR-CLIENTES:FIN");
    }

    @Override
    public void showStatus() {
        System.out.println("Clientes que quedan por ser atendidos:" + (clientesTotales - clientesAtendidos));
    }
}
