package hipermercado;


public class SimulacionTimer extends Thread {
    
    public void run(){
        try {
            Thread.sleep((long)60000);
        } catch (InterruptedException ex) {}
        finally{
            System.out.println("---EL TIEMPO DE SIMULACIÓN HA CONCLUIDO---");
        }
    }
}
