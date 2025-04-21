/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebassantiago;

import java.util.Scanner;
import java.util.Random;
public class Pruebassantiago {

    static Scanner INPUT = new Scanner(System.in);
    static boolean[][] sociedadMatriz = new boolean [15][15];
    static boolean celulaViva = true;
    static boolean celulaMuerta = false;
    static Random Randomizador = new Random();

    public static void main(String[] args) {
         mostrarMenu();
    }
    
    public static void mostrarMenu(){
        int opcion;
        do {
            System.out.println("Bienvenido al Juego de la Vida.");
            System.out.println("En este juego simularas el crecimiento de un Grupo de Celulas de acuerdo a reglas de Reproduccion, Soledad y Sobrepoblacion.");
            System.out.println("1. Iniciar Juego.");
            System.out.println("2. Mostrar Reglas.");
            System.out.println("3. Salir del Programa.");

            opcion = validador("");

            switch(opcion) {
                case 1:
                    iniciarMatriz();
                    break;
                case 2:
                     mostrarReglas();
                     break;
                 case 3:
                     System.out.println("Saliendo del programa");
                     break;
                 default:
                     System.out.println("opcion no valida");
            }
        }while(opcion !=3);
    }
    
    public static int validador(String mensaje) {
    int numero;
    while (true) {
        System.out.print(mensaje);

        if (INPUT.hasNextInt()) {
            numero = INPUT.nextInt();
        

            if (numero > 0) {
                return numero;            
            } else {
                System.out.println("Ingrese un numero mayor a cero.");
            }
        } else {
           
            System.out.println(" Ingrese un numero entero.");
            INPUT.nextLine();
           
        }
    }
}
       
    public static void iniciarMatriz(){
        int piso = 0;
        int techo = 0;
        int salir = 0;
        int cantidadCelulas = 0;
       
        do{
            
            techo = validador("La simulacion se desarrollara en una Matriz cuadrada, indique la extension maxima de la Matriz\n El numero debe ser positivo, mayor a cero.\n");
            
        }while(techo <= 0);
        
        boolean[][] MatrizParametrizada = new boolean [techo][techo];/////Se declara la Matriz
        sociedadMatriz = MatrizParametrizada;///// Se reasigna las dimensiones a la matriz
        
        do{
            cantidadCelulas = validador("Con cuentas celulas iniciara la simulacion?\n El numero debe ser positivo, mayor a cero.\n");
        }while(cantidadCelulas <= 0 || cantidadCelulas > (techo * techo) );
        int AsignacionPosicion = 0;

        AsignacionPosicion = validador("Elija el modo de asignacion de las Celulas en la Matriz:\n 1. Aleatorio. \n 2. Manual.\n");
        int fila = 0;
        int columna = 0; 
        
        switch(AsignacionPosicion){
            case 1:
                salir = 0;
                    
                do{
                    int ValorAleatorio1 = Randomizador.nextInt(techo);
                    fila = ValorAleatorio1;
                    int ValorAleatorio2 = Randomizador.nextInt(techo);
                    columna = ValorAleatorio2;
                        
                    if(sociedadMatriz[fila][columna] == false){
                        sociedadMatriz[fila][columna] = true;
                        salir++;
                    }
                }while(salir != cantidadCelulas); 
                matrizEnPantalla(sociedadMatriz, techo);
                INPUT.nextLine();
                
                break;
            case 2: 

                salir = 0;
                                    
                do{
                    do{
                        System.out.printf("Rango: "+techo+"\n");
                        fila = validador("Indique la Fila(Debe estar en el rango asignado de la Matriz)\n");
                        
                    }while(fila >= techo || fila < 0);
                    do{
                        System.out.printf("Rango: "+techo+"\n");
                        columna =validador("Indique la Columna(Debe estar en el rango asignado de la Matriz)\n");
                        
                    }while(columna >= techo || columna < 0);
                    
                    sociedadMatriz[fila][columna]= celulaViva;
                    
                    salir++;
                }while(salir !=cantidadCelulas);
                
                matrizEnPantalla(sociedadMatriz, techo);
                INPUT.nextLine();
                break;  
            default:
                System.out.print("Opcion Invalida.");
        }
    }
    
    public static void matrizEnPantalla(boolean sociedadMatriz [][], int techo){
        String interruptor = "";
        int primeraVez = 0; 
        INPUT.nextLine/////Limpia el buffer
        ();
        do {
            if(primeraVez == 0){

                System.out.println("Generacion Celular inicial.");
            }else {
                System.out.println("Siguiente Generacion Celular.");
                sociedadMatriz = siguienteGeneracion(sociedadMatriz, techo);

            }  
            
            mostrarEstadoActual(sociedadMatriz);
            System.out.println("Desea continuar la simulacion? \n n, para Detener.\n Cualquier otro para continuar ");
            interruptor = INPUT.nextLine();
            primeraVez++;   
  

        }while(!interruptor.equalsIgnoreCase("n"));
    }   
    
    public static void mostrarEstadoActual(boolean sociedadMatriz [][]){
        for(int i = 0; i < sociedadMatriz.length; i++) {
                    for(int j = 0; j < sociedadMatriz[i].length; j++){
                        if (sociedadMatriz[i][j] == false){
                            System.out.print("[ ]\t");
                        }else{
                            System.out.print("[O]\t");
                        }
                    }
                    System.out.println();
                }
    }
    
    public static boolean[][] siguienteGeneracion(boolean sociedadMatriz [][], int techo ){
        boolean[][] generacionNueva = new boolean [techo][techo];
        
        for (int i = 0; i < techo; i++ ){
            for (int j = 0; j < techo; j++){
                int celulasVivas = revisarCelulasAlrededor(i, j,techo,sociedadMatriz);////Esta seccion del Codigo recibe la cantidad de Celulas analizadas en el metodo "revisarCelulasAlrededor", con esta inforrmacion determina el estado de la Matriz en la posicion indicada
                
                if (sociedadMatriz[i][j]==true) {
                    if(celulasVivas == 2 || celulasVivas == 3){///Aqui analiza cuantas celulas aadyacents hay vivas, para determinar si el estado es true y lo mantiene.
                        generacionNueva[i][j] = true ;
                    }
                    else{
                       generacionNueva[i][j] = false ;//// de no cumplir l condicion cambia el estaod de la Celula, que equivale a su muerte. 
                    }
                    
                }else{
                    if(celulasVivas ==3)
                    generacionNueva[i][j] = true;
                    else{
                        generacionNueva[i][j] = false ; 
                    }
                }
            }
        }
          
        return generacionNueva;
    }
    
    public static int revisarCelulasAlrededor (int fila, int columna, int techo, boolean sociedadMatriz [][]){
        int contadorCelulas= 0;
        
        if (fila > 0) { //ESTE BLOQUE DE CODIGO VERIFICA EL LIMITE INICIAL DE LA MATRIZ, POR ENDE, EMPIEZA ASEGURANDO QUE EL PARAMETRO FILA SEA MAYOR A CERO DE CUMPLIR
            if (columna > 0 && sociedadMatriz[fila - 1][columna - 1]==true) contadorCelulas ++;
            if (sociedadMatriz[fila - 1][columna]==true) contadorCelulas++;
            if (columna < techo-1 && sociedadMatriz[fila - 1][columna + 1]==true) contadorCelulas++;
        }
        
        if (columna > 0 && sociedadMatriz[fila][columna - 1]==true) contadorCelulas++;
        if (columna < techo-1 && sociedadMatriz[fila][columna + 1]==true) contadorCelulas++;
        
        if (fila < techo-1) {
            if (columna > 0 && sociedadMatriz[fila + 1][columna - 1]==true) contadorCelulas ++;
            if (sociedadMatriz[fila + 1][columna]==true) contadorCelulas++;
            if (columna < techo-1 && sociedadMatriz[fila + 1][columna + 1]==true) contadorCelulas++;
        }
        return contadorCelulas;
    }
    
    public static void mostrarReglas() {
        System.out.println("\n Reglas: ");
        System.out.println("1- Una célula viva con menos de 2 vecinos vivos muere por soledad ");
        System.out.println("2- Una célula viva con 2 o 3 vecinos vivos sobrevive ");
        System.out.println("3-una célula viva con más de 3 vecinos vivos muere por superpoblacion");
        System.out.println("4-una célula muerta con exactamente 3 vecinos vivos revive por reproduccion");
        System.out.println();
    }    
}