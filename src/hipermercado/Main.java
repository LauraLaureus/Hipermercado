package hipermercado;

import java.util.ArrayList;

public class Main{
    public static void main(String[] args){
        
        int numCajas = new CLIUserInterface().preguntaNumCajas();
        int clientesTotales = new CLIUserInterface().preguntaNumClientes();
        
        Cola cola = new Cola();
        Contabilidad cajaFuerte = new Contabilidad();
        
        ManejadorDeClientes directorDeClientes = 
                new ManejadorDeClientes(clientesTotales,cola);
        
        
        
    }
}
