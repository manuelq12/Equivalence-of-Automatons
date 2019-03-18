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
	 * Atributo que representa los estados de la m�quina
	 */
	private String[] outputs;
	/**
	 * M�todo constructor de la m�quina de Mealy
	 * @param nStates N�mero de estados de la m�quina
	 * @param nInputs N�mero de posibles entradas de la m�quina
	 */
	public MaquinaMealy(int nStates, int nInputs) {
		
		adyacencyMatrix =  new String[nStates][nInputs];
		states = new String[nStates];
		outputs = new String [nStates];
	}

	/**
	 * M�todo que retorna la matriz de adyacencia de la m�quina actual
	 * @return La matriz de incidencia de la m�quina actual
	 */
	public String[][] getAdyacencyMatrix() {
		return adyacencyMatrix;
	}
	/**
	 * M�todo que cambia la matriz de adyacencia de la m�quina actual por una nueva
	 * @param incidenceMatrix Una matriz de adyacencia tipo String
	 */
	public void setAdyacencyMatrix(String[][] incidenceMatrix) {
		this.adyacencyMatrix = incidenceMatrix;
	}
	/**
	 * M�todo que retorna el arreglo con los estados de la m�quina actual
	 * @return arreglo de estados
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
}
