package principal;

public class Servidor extends Thread {
	
	/**
	 * El identificador del servidor
	 */
	private int identificador;
	
	/**
	 * El buffer del cual el servidor extrae mensajes para procesar. 
	 */
	private Buffer buffer;
	
	/**
	 * Construye un servidor con los valores establecidos por parámetro. 
	 * @param pIdentificador - El identificador del servidor
	 * @param pBuffer - El buffer del cual se extraen los mensajes. 
	 */
	public Servidor(int pIdentificador, Buffer pBuffer){
		buffer = pBuffer;
		identificador = pIdentificador;
	}
	
	public void run(){
		
		
		System.out.println("se activa el servidor " + identificador);
		
		while(true){
			Mensaje m = buffer.procesarConsulta(identificador);
			m.responderMensaje();
			System.out.println("Se respondió el mensaje " + m.getIdentificador());
//			System.out.println("mensajes procesados: " + Mensaje.contadorFinal);
		}
	}

}
