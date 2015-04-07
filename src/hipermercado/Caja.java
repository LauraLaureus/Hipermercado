
package hipermercado;

public class Caja extends Thread {
    
    private final Cola cola;
    private final Contabilidad cajaFuerte;
    private double recaudacion;
    
    public Caja(Cola cola, Contabilidad cajaFuerte) {
        this.cola = cola;
        this.cajaFuerte = cajaFuerte;
    }
    
    public void run(){
    
        try{
            /*
             mientras haya clientes en la cola:
             *  atendercliente (dormir el valor del carro/10);
             */
            Cliente cliente = cola.sacar();
            while(cliente != null){
                double recaudacionParcial = cliente.damePrecioCarro();
                this.recaudacion += recaudacionParcial;
                Thread.sleep((long)recaudacionParcial/10);
            }
            
        }
        catch(Exception e){
         //Me han interrumpido y.y
        }
        finally{
            pasarLaContabilidad();
        }
    }

    private void pasarLaContabilidad() {
        cajaFuerte.añadeSaldo(recaudacion);
    }

        
}
