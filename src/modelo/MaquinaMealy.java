package modelo;

public class MaquinaMealy {

	private String[][] incidenceMatrix;
	private String[] states;
	
	public MaquinaMealy(int nStates, int nInputs) {
		
		incidenceMatrix =  new String[nStates][nInputs];
		states = new String[nStates];
	}

	public String[][] getIncidenceMatrix() {
		return incidenceMatrix;
	}

	public void setIncidenceMatrix(String[][] incidenceMatrix) {
		this.incidenceMatrix = incidenceMatrix;
	}
	
	

}
