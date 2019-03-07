package modelo;

public class MaquinaMoore {

	private String[][] incidenceMatrix;
	private String[] outputs;
	
	public MaquinaMoore(int nEstados, int nEntradas) {
		incidenceMatrix = new String[nEstados][nEntradas];
		outputs = new String [nEstados];
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
	
	
}
