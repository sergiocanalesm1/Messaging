package principal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Buffer {
	
	/**
	 * para revisar que se respondieron todos los mensajes
	 */
	
	/**
	 * El tama�o del buffer
	 */
	private static int tamanoBuffer;

	/**
	 * El arreglo con los mensajes que hay actualmente en el buffer
	 */
	private ArrayList<Mensaje> buzon;
	
	/**
	 * Objetos en donde se van a guardar los threads que llegan a recibir mensajes y a entregarlos
	 */
	private Object lleno, vacio;
	
	/**
	 * Construye un buffer con el tama�o dado por par�metro. 
	 * @param pTamanoBuffer
	 */
	public Buffer(int pTamanoBuffer){
		 tamanoBuffer = pTamanoBuffer;
		 buzon = new ArrayList<Mensaje>();
		 lleno = new Object();
		 vacio = new Object();
	}
	
	/**
	 * @return el tama�o actual del buz�n
	 */
	public int size() {
		return buzon.size();
	}
	
	/**
	 * M�todo que se encarga de depositar una nueva consulta en el buz�n
	 * Revisa si el buz�n est� lleno y si lo est�, hace esperar al thread
	 * @param m
	 */
	public void consultar(Mensaje m){
		System.out.println("Llega al buffer el mensaje: " + m.getIdentificador() );
		// Pone al thread a esperar hasta que el buz�n tenga un espacio libre 
		System.out.println("El tama�o del buffer en este momento es: " + buzon.size());
		
		synchronized(lleno) {
			while(buzon.size() == tamanoBuffer) {
				try {
					System.out.println("est� lleno");
					lleno.wait();
					System.out.println("Se puso a esperar al cliente : " + (int) m.getIdentificador() );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// Cuando sale del while, le permite al buz�n agregar el mensaje que lleg� por par�metro
		synchronized(this) {
			buzon.add(m);
		}
		// Le avisa al objeto que contiene los servidores que esperan, que lleg� un mensaje. 
		synchronized(vacio) {
			vacio.notifyAll();
		}
	}

	/**
	 * M�todo que se encarga de procesar una consulta
	 * @return el mensaje que sac� del buz�n para que el servidor pueda hacer el cambio. 
	 */
	public Mensaje procesarConsulta(int id){
		// Pone a un thread de servidor a dormir hasta que llegue un mensaje
		System.out.println("Llego el servidor " +  id + " a contestar el mensaje");
		synchronized(vacio) {
			while(buzon.size() == 0) {
				try {
					vacio.wait();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// Cuando sale de este while, le permite al buz�n sacar el elemento que hab�a llegado
		Mensaje m;
		synchronized(this) {
			m = buzon.remove(0);
		}
		// Le avisa a los mensajes que est�n esperando que ya hay un servidor esperando. 
		synchronized(lleno) {
			lleno.notifyAll();
		}
		return m;
	}
	
	public static void main(String[] args){
		// C�digo para la lectura del archivo con la opci�n de ingresar ruta o usar "Ejemplo1.txt"
		String ruta = "";
		System.out.println("1 - Ingresar la ruta del archivo con los datos de configuraci�n: ");
		System.out.println("2 - Usar el archivo 'Ejemplo1.txt");
		Scanner in = new Scanner(System.in);
		int respuesta = in.nextInt();
		//Si el usuario decide ingresar la ruta:
		if(respuesta == 1) {
			System.out.println("Ingresar la ruta del archivo con los datos de configuraci�n:");
			ruta = in.nextLine();
		}
		// Si el usuario decide usar "Ejemplo1.txt"
		else if(respuesta == 2){
			System.out.println("Se usar� el archivo Ejemplo1.txt");
			ruta = "src/data/Ejemplo1.txt";
		}
		else {
			System.out.println("Respuesta no v�lida");
		}
		// Lee los datos de un archivo que siga las convenciones y asigna los valores a los atributos con base en eso
		try {
			FileReader reader = new FileReader(ruta);
			BufferedReader br = new BufferedReader(reader);
			int numeroClientes = Integer.parseInt(br.readLine().split(":")[1]);
			int numeroServidores = Integer.parseInt(br.readLine().split(":")[1]);
			int numeroConsultas = Integer.parseInt(br.readLine().split(":")[1]);
			int tb = Integer.parseInt(br.readLine().split(":")[1]);
			Buffer buff = new Buffer(tb);
			Cliente[] clientes = new Cliente[numeroClientes];
			Servidor[] servidores = new Servidor[numeroServidores];
			//Imprime los resultados en la consola para informar al usuario y comprueba que se guardaron en los atributos.
			System.out.println("El n�mero de clientes en el archivo es de: " + numeroClientes);
			System.out.println("El n�mero de servidores en el archivo es de: " + numeroServidores);
			System.out.println("El n�mero de consultas en el archivo es de: " + numeroConsultas);
			System.out.println("El tama�o del buffer en el archivo es de: " + tamanoBuffer + "\n");
			br.close();
			//Se empeizan a crear los threads con los valores que entraron por el archivo
			for(int i = 1; i < numeroClientes + 1; i++) {
				System.out.println("Se cre� el cliente con identificador: " + i);
				clientes[i-1] = new Cliente(i, numeroConsultas, buff);
			}
			for(int i = 1; i < numeroServidores + 1; i++) {
				System.out.println("Se cre� el servidor con identificador: " + i);
				servidores[i-1] = new Servidor(i, buff);
			}
			System.out.println();
//			for(int i = 0; i < numeroClientes; i++)
//				clientes[i].run();
//			for(int i = 0; i < numeroServidores; i++)
//				servidores[i].run();
			for(int i = 0; i < Math.max(numeroServidores, numeroClientes); i++) {
				if(i < numeroClientes)
					clientes[i].start();
				if(i < numeroServidores)
					servidores[i].start();		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		in.close();
	}

}
