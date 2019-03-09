package modelo;

public class MaquinaMealy {

	private String[][] incidenceMatrix;
	private String[] states;
	private String[] outputs;
	
	public MaquinaMealy(int nStates, int nInputs) {
		
		incidenceMatrix =  new String[nStates][nInputs];
		states = new String[nStates];
		outputs = new String [nStates];
	}

	public String[][] getIncidenceMatrix() {
		return incidenceMatrix;
	}

	public void setIncidenceMatrix(String[][] incidenceMatrix) {
		this.incidenceMatrix = incidenceMatrix;
	}

	public String[] getStates() {
		return states;
	}

	public void setStates(String[] states) {
		this.states = states;
	}

	public String[] getOutputs() {
		return outputs;
	}

	public void setOutputs(String[] outputs) {
		this.outputs = outputs;
	}
	
	
	
	
	

}
