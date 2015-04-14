package hipermercado;

import java.util.TreeSet;

public class DemonioRompedor extends Thread {

    private TreeSet<Integer> cajasRotas;
    private int numTotal;
    private final ManejadorDeCajas directorEmpleados;

    public DemonioRompedor(int numCajas, ManejadorDeCajas directorEmpleados) {
        this.numTotal = numCajas;
        this.directorEmpleados = directorEmpleados;
    }

    public void run() {

        while (true) {
            try {
                int randomInRange = (int) (Math.random() * numTotal);
                if (siNoEstaYaRota(randomInRange)
                        && directorEmpleados.cuentaCajasVivas() > 0) {
                    directorEmpleados.interrupeCajaEnElIndice(randomInRange);
                    unaMásRota(randomInRange);
                }
                this.wait((long) (randomInRange*100));
            } catch (InterruptedException ex) {
                break;
            }
        }
    }

    private boolean siNoEstaYaRota(int randomInRange) {
        return cajasRotas.contains(randomInRange);
    }

    private void unaMásRota(int randomInRange) {
        cajasRotas.add(randomInRange);
    }
}
