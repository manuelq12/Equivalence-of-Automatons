package modelo;

public class MaquinaMealy {

	private String[][] incidenceMatrix;
	
	public MaquinaMealy(int nEstados, int nEntradas) {
		
		incidenceMatrix =  new String[nEstados][nEntradas];
	}

	public String[][] getIncidenceMatrix() {
		return incidenceMatrix;
	}

	public void setIncidenceMatrix(String[][] incidenceMatrix) {
		this.incidenceMatrix = incidenceMatrix;
	}
	
	

}
