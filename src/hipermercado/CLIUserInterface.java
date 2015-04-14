package hipermercado;

import java.util.Scanner;

public class CLIUserInterface implements UserInterface{

    @Override
    public int preguntaNumCajas() {

        Scanner teclado = new Scanner(System.in);
        
        int respuesta;
        System.out.println("¿Cuántas cajas tiene el hipermercado?");
        respuesta = teclado.nextInt();
        
        return respuesta;
    }

    @Override
    public int preguntaNumClientes() {
        
        Scanner teclado = new Scanner(System.in);
        
        int respuesta;
        System.out.println("¿Cuántas clases pasaron durante el día?");
        respuesta = teclado.nextInt();
        
        return respuesta;
    }
    
}
