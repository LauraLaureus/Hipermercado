package hipermercado;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int numCajas = new CLIUserInterface().preguntaNumCajas();
        int clientesTotales = new CLIUserInterface().preguntaNumClientes();

        Cola cola = new Cola();
        Contabilidad cajaFuerte = new Contabilidad(numCajas, cola);
        ManejadorDeCajas jefe = new ManejadorDeCajas(numCajas, cola, cajaFuerte);
        SimulacionTimer timer = new SimulacionTimer();
        ManejadorDeClientes directorDeClientes
                = new ManejadorDeClientes(clientesTotales, cola, jefe,timer);

        
        directorDeClientes.start();
        jefe.arrancaCajas();

        jefe.join();
        directorDeClientes.join();
        
        
        System.out.println("FIN DE LA SIMULACIóN");
        System.out.println("STATUS COLA________");
        cola.showStatus();
        System.out.println("STATUS CONTABILIDAD_________");
        cajaFuerte.showStatus();
        System.out.println("STATUS MANEJADOR DE CLIENTES______");
        directorDeClientes.showStatus();
        System.out.println("STATUS MANEJADOR DE CAJA___________");
        jefe.showStatus();
    }
}
