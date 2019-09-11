package principal;

public class Mensaje {
//	public static int contadorFinal = 0;
	
	/**
	 * Contenido del mensaje
	 * Para la simplicidad del ejercicio es simplemente un entero
	 */
	private int contenido;
	
	/**
	 * Identificador del mensaje
	 */
	private double identificador;
	
	/**
	 * Construye un mensaje
	 * Establece el contenido del mensaje como un número aleatorio entre 1 y 20. 
	 */
	public Mensaje(double pIdentificador){
		identificador = pIdentificador;
		setContenido((int) Math.random()*20);
	}
	
	public void responderMensaje() {
		System.out.println("Se respondío a un mensaje");
		contenido ++;
//		contadorFinal++;
	}

	/**
	 * @return el contendio del mensaje 
	 */
	public int getContenido() {
		return contenido;
	}

	/**
	 * Cambia el contendio del mensaje por un valor dado por parámetro. 
	 * @param contenido
	 */
	public void setContenido(int contenido) {
		this.contenido = contenido;
	}

	public double getIdentificador() {
		return identificador;
	}

	public void setIdentificador(double identificador) {
		this.identificador = identificador;
	}

}
