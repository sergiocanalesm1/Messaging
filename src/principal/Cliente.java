package principal;

import java.util.ArrayList;

public class Cliente extends Thread {
	
	/**
	 * El identificador del Cliente
	 */
	private int identificador;
	
	/**
	 * La cantidad de mensajes que tiene el cliente
	 */
	private int cantidadMensajes;
	
	/**
	 * El buffer en el cual se guardan los mensajes del cliente. 
	 */
	private Buffer buffer;
	
	/**
	 * La lista en la que se encuentran los mensajes del cliente. 
	 */
	private ArrayList<Mensaje> mensajes;
	
	/**
	 * Construye un cliente con los valores dados por parámetro. 
	 * @param pIdentificador - El id del cliente
	 * @param pCantidadMensajes - La cantidad de mensajes que va a mandar
	 * @param pBuffer - El buffer que pasa los mensajes al servidor. 
	 */
	public Cliente(int pIdentificador, int pCantidadMensajes, Buffer pBuffer){
		identificador = pIdentificador;
		cantidadMensajes = pCantidadMensajes;
		buffer = pBuffer;
		mensajes = new ArrayList<Mensaje>();
		// Crea los mensajes y los agrega a la lista de mensajes del cliente. 
		for(int i = 1; i < cantidadMensajes + 1; i++){
			double idm = identificador + (i * 0.01);
			Mensaje m = new Mensaje(idm);
			mensajes.add(m);
			System.out.println("Se creó el mensaje del cliente " + identificador + " con idm : " +  idm);
		}
	}
	
	/**
	 * Método que se encarga de mandar todos los mensajes al buffer
	 */
	public void run(){
		// Manda los mensajes al método consultar del buffer
		System.out.println("El cliente " + identificador + " empieza a consultar sus mensajes");
		for(Mensaje m : mensajes){
			buffer.consultar(m);
		}
	}
	
	public int getIdentificador() {
		return identificador;
	}

	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}

	public int getCantidadMensajes() {
		return cantidadMensajes;
	}

	public void setCantidadMensajes(int cantidadMensajes) {
		this.cantidadMensajes = cantidadMensajes;
	}

	public Buffer getBuffer() {
		return buffer;
	}

	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}
	
	

}
