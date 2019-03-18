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
	 * Atributo que representa los estados de la m�quina
	 */
	private String[] states;
	
	/**
	 * M�todo constructor de la m�quina de Moore
	 * @param nStates N�mero de estados de la m�quina
	 * @param nInputs N�mero de posibles entradas de la m�quina
	 */
	public MaquinaMoore(int nStates, int nInputs) {
		adjacencyMatrix = new String[nStates][nInputs];
		outputs = new String [nStates];
		states = new String[nStates];
	}
	
	/**
	 * M�todo que retorna la matriz de adyacencia de la m�quina actual
	 * @return La matriz de incidencia de la m�quina actual
	 */

	public String[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}
	
	/**
	 * M�todo que cambia la matriz de adyacencia de la m�quina actual por una nueva
	 * @param incidenceMatrix Una matriz de adyacencia tipo String
	 */

	public void setAdyacencyMatrix(String[][] incidenceMatrix) {
		this.adjacencyMatrix = incidenceMatrix;
	}
	/**
	 * M�todo que retorna el arreglo con las salidas de cada estado en su debida posici�n
	 * @return Arreglo con las salidas de cada estado ubicados en su debida posici�n
	 */

	public String[] getOutputs() {
		return outputs;
	}
	
	/**
	 * M�todo que asigna un nuevo arreglo de salidas a la m�quina actual.
	 * @param outputs Arreglo con las salidas nuevas a asignar.
	 */
	public void setOutputs(String[] outputs) {
		this.outputs = outputs;
	}
	/**
	 * M�todo que retorna el arreglo con los estados de la m�quina actual
	 * @return Arreglo con los estados de la m�quina actual
	 */
	
	public String[] getStates() {
		return states;
	}
	/**
	 * M�todo que modifica el arreglo con los estados de la m�quina actual.
	 * @param states El arreglo con los estados que se desea asignar a la m�quina actual.
	 */

	public void setStates(String[] states) {
		this.states = states;
	}
	/**
	 * M�todo que retorna la posici�n en la que se encuentra un estado 
	 * en el arreglo de estados y en las filas de la matriz de adyacencia.
	 * @param state el nombre del estado a buscar.
	 * @return La posici�n donde se encuentra el estado a buscar.
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