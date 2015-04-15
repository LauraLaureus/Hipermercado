package hipermercado;

public class Contabilidad implements StatusVisible{

    private double saldo;
    private final  Cola cola;
    private int numCajas;
    private ManejadorDeCajas jefe;
    
    public Contabilidad(int num, Cola cola){
        this.saldo = 0;
        this.cola = cola;
        this.numCajas = num;
    }
    
      
    public synchronized void añadeSaldo(double saldo, long ide){
        System.out.println("CONTABILIDAD:se añade un saldo de la caja:" + ide);
        this.saldo += saldo;
        numCajas--;
        if(numCajas == 0){
            cola.cerrar();
            System.out.println("CONTABILIDAD: Se han cerrado todas las cajas.");
            System.out.println("CONTABILIDAD: se espera que el hilo principal termine.");
        }
    }
    
    public double dameSaldo(){
        return this.saldo;
    }

    @Override
    public void showStatus() {
        System.out.println("Saldo actual: " + saldo);
        System.out.println("Número de cajas que NO han entregado su saldo: " + numCajas);
    }
    
    public void setManejadorCajas(ManejadorDeCajas mnj_cajas){
        if(jefe == null){
            this.jefe = mnj_cajas;
        }
    }
}
