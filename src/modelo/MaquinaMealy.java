package modelo;

public class MaquinaMealy {

	/**
	 * Atributo que representa la matriz de adyacencia entre estados.
	 */
	private String[][] adyacencyMatrix;
	/**
	 * Atributo que representa el arreglo de salidas de cada estado.
	 */
	private String[] states;
	/**
	 * Atributo que representa los estados de la máquina
	 */
	private String[] outputs;
	/**
	 * Método constructor de la máquina de Mealy
	 * @param nStates Número de estados de la máquina
	 * @param nInputs Número de posibles entradas de la máquina
	 */
	public MaquinaMealy(int nStates, int nInputs) {
		
		adyacencyMatrix =  new String[nStates][nInputs];
		states = new String[nStates];
		outputs = new String [nStates];
	}

	/**
	 * Método que retorna la matriz de adyacencia de la máquina actual
	 * @return La matriz de incidencia de la máquina actual
	 */
	public String[][] getAdyacencyMatrix() {
		return adyacencyMatrix;
	}
	/**
	 * Método que cambia la matriz de adyacencia de la máquina actual por una nueva
	 * @param incidenceMatrix Una matriz de adyacencia tipo String
	 */
	public void setAdyacencyMatrix(String[][] incidenceMatrix) {
		this.adyacencyMatrix = incidenceMatrix;
	}
	/**
	 * Método que retorna el arreglo con los estados de la máquina actual
	 * @return arreglo de estados
	 */
	public String[] getStates() {
		return states;
	}
/**
 * Método que modifica el arreglo con los estados de la máquina actual.
 * @param states El arreglo con los estados que se desea asignar a la máquina actual.
 */
	public void setStates(String[] states) {
		this.states = states;
	}
	/**
	 * Método que retorna el arreglo con las salidas de cada estado en su debida posición
	 * @return Arreglo con las salidas de cada estado ubicados en su debida posición
	 */

	public String[] getOutputs() {
		return outputs;
	}
	/**
	 * Método que asigna un nuevo arreglo de salidas a la máquina actual.
	 * @param outputs Arreglo con las salidas nuevas a asignar.
	 */
	public void setOutputs(String[] outputs) {
		this.outputs = outputs;
	}
}
