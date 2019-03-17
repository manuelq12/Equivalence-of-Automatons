package modelo;

public class MaquinaMoore {

	private String[][] incidenceMatrix;
	private String[] outputs;
	private String[] states;
	
	public MaquinaMoore(int nStates, int nInputs) {
		incidenceMatrix = new String[nStates][nInputs];
		outputs = new String [nStates];
		states = new String[nStates];
	}

	public String[][] getIncidenceMatrix() {
		return incidenceMatrix;
	}

	public void setIncidenceMatrix(String[][] incidenceMatrix) {
		this.incidenceMatrix = incidenceMatrix;
	}

	public String[] getOutputs() {
		return outputs;
	}

	public void setOutputs(String[] outputs) {
		this.outputs = outputs;
	}
	
	public String[] getStates() {
		return states;
	}

	public void setStates(String[] states) {
		this.states = states;
	}	
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
