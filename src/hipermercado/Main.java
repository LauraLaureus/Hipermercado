package hipermercado;



public class Main{
    public static void main(String[] args){
        
        int numCajas = new CLIUserInterface().preguntaNumCajas();
        int clientesTotales = new CLIUserInterface().preguntaNumClientes();
        
        Cola cola = new Cola();
        Contabilidad cajaFuerte = new Contabilidad(numCajas,cola);
        
        ManejadorDeClientes directorDeClientes = 
                new ManejadorDeClientes(clientesTotales,cola);
        ManejadorDeCajas jefe = new ManejadorDeCajas(numCajas, cola, cajaFuerte);
        cajaFuerte.setManejadorCajas(jefe);
        DemonioRompedor demon = new DemonioRompedor(numCajas, jefe);
        
        directorDeClientes.start();
        jefe.arrancaCajas();
        demon.start();
        
        try {
            Thread.sleep((long)60000);
        } catch (InterruptedException ex) {}
        finally{
            jefe.interrupt();
            directorDeClientes.interrupt();
            demon.interrupt();
            System.out.println("FIN DE LA SIMULACIóN");
            System.out.println("STATUS COLA________");
            cola.showStatus();
            System.out.println("STATUS CONTABILIDAD_________");
            cajaFuerte.showStatus();
            System.out.println("STATUS MANEJADOR DE CLIENTES______");
            directorDeClientes.showStatus();
            System.out.println("STATUS MANEJADOR DE CAJA___________");
            jefe.showStatus();
            System.out.println("STATUS DEMONIO ROMPEDOR______");
            demon.showStatus();
        }
    }
}
