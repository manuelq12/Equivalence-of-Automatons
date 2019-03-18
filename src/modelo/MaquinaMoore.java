package modelo;

public class MaquinaMoore {

	/**
	 * Atributo que representa la matriz de adyacencia entre estados.
	 */
	private String[][] adjacencyMatrix;
	/**
	 * Atributo que representa el arreglo de salidas de cada estado.
	 */
	private String[] outputs;
	/**
	 * Atributo que representa los estados de la máquina
	 */
	private String[] states;
	
	/**
	 * Método constructor de la máquina de Moore
	 * @param nStates Número de estados de la máquina
	 * @param nInputs Número de posibles entradas de la máquina
	 */
	public MaquinaMoore(int nStates, int nInputs) {
		adjacencyMatrix = new String[nStates][nInputs];
		outputs = new String [nStates];
		states = new String[nStates];
	}
	
	/**
	 * Método que retorna la matriz de adyacencia de la máquina actual
	 * @return La matriz de incidencia de la máquina actual
	 */

	public String[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}
	
	/**
	 * Método que cambia la matriz de adyacencia de la máquina actual por una nueva
	 * @param incidenceMatrix Una matriz de adyacencia tipo String
	 */

	public void setAdyacencyMatrix(String[][] incidenceMatrix) {
		this.adjacencyMatrix = incidenceMatrix;
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
	/**
	 * Método que retorna el arreglo con los estados de la máquina actual
	 * @return Arreglo con los estados de la máquina actual
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
	 * Método que retorna la posición en la que se encuentra un estado 
	 * en el arreglo de estados y en las filas de la matriz de adyacencia.
	 * @param state el nombre del estado a buscar.
	 * @return La posición donde se encuentra el estado a buscar.
	 */
	public int getIndex(String state) {
		int result =0;
		boolean found = false;
		for(int i = 0; i < states.length && !found; i++) {
			if(state.equals(states[i])) {
				result = i;
				found = true;
			}
		}
		return result;
	}
	
}