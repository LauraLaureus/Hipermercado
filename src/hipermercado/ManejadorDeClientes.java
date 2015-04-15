package hipermercado;

public class ManejadorDeClientes extends Thread implements StatusVisible {

    private final ManejadorDeCajas caja_mnj;
    private int clientesAtendidos;
    private final int clientesTotales;
    private final Cola cola;
    private final SimulacionTimer simulacion;
    
    public ManejadorDeClientes(int clientesTotales, Cola cola, ManejadorDeCajas mnj,
            SimulacionTimer simulacion) {
        this.clientesTotales = clientesTotales;
        this.cola = cola;
        this.clientesAtendidos = 0;
        this.caja_mnj = mnj;
        this.simulacion = simulacion;
    }

    public void run() {

        for (int i = 0; i < clientesTotales; i++) {
            if(caja_mnj.cuentaCajasVivas() == 0 && !simulacion.isAlive()){
                caja_mnj.interrupt();
                break;
            }
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
