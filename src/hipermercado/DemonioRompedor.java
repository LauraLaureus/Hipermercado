package hipermercado;

import java.util.TreeSet;

public class DemonioRompedor extends Thread implements StatusVisible{

    private TreeSet<Integer> cajasRotas;
    private int numTotal;
    private final ManejadorDeCajas directorEmpleados;

    public DemonioRompedor(int numCajas, ManejadorDeCajas directorEmpleados) {
        this.numTotal = numCajas;
        this.directorEmpleados = directorEmpleados;
        this.cajasRotas = new TreeSet<>();
    }

    public void run() {

        while (true) {
            try {
                int randomInRange = (int) (Math.random() * numTotal);
                if (siNoEstaYaRota(randomInRange)
                        && directorEmpleados.cuentaCajasVivas() > 0) {
                    directorEmpleados.interrupeCajaEnElIndice(randomInRange);
                    unaMásRota(randomInRange);
                    System.out.println("DEMONIO: Se rompe la caja:" + randomInRange);
                }
                Thread.sleep((long) (randomInRange*10));
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

    @Override
    public void showStatus() {
        String cajasRotasS = "";
        for (Integer cajasRota : cajasRotas) {
            cajasRotasS += cajasRota;
        }
        System.out.println("Número de cajas rotas: " + cajasRotasS);
    }
}
