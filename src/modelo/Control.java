package modelo;

import java.awt.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Control {
	public static String MEALY = "M";
	public static String MOORE = "O";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			BufferedReader sr = new BufferedReader(new InputStreamReader(System.in));

			String maquina = sr.readLine();
			if (maquina.equals(MEALY)) {
				String estados = sr.readLine();
				String entradas = sr.readLine();
				// INT
				int nEstados = Integer.parseInt(estados);
				// INT
				int nEntradas = Integer.parseInt(entradas);
				String alfabetoEstados = sr.readLine();

				// A,B,C,D
				String[] arregloAlfabetos = alfabetoEstados.split(",");

				MaquinaMealy maquinaUno = new MaquinaMealy(nEstados, nEntradas);

				String[][] equivalenteUno = new String[nEstados][nEntradas];
				// A1 A3 D3 Q2 C0 D0
				sr.readLine();
				String mensaje = sr.readLine();
				int i = 0;
				while (!mensaje.equals("---")&& i!=nEstados) {
					String[] linea = mensaje.split(" ");
					for (int j = 0; j < linea.length -1; j++) {
						equivalenteUno[i][j]= linea[j+1];
					}
					i++;
					mensaje = sr.readLine();
				}
				maquinaUno.setIncidenceMatrix(equivalenteUno);
				maquinaUno.setStates(arregloAlfabetos);
				imprimirMealy(maquinaUno);
			} else if (maquina.equals(MOORE)) {
				String estados = sr.readLine();
				String entradas = sr.readLine();
				// INT
				int nEstados = Integer.parseInt(estados);
				// INT
				int nEntradas = Integer.parseInt(entradas);
				String alfabetoEstados = sr.readLine();

				// A,B,C,D
				String[] arregloAlfabetos = alfabetoEstados.split(",");

				MaquinaMoore maquinaDos = new MaquinaMoore(nEstados, nEntradas);

				String[][] equivalenteDos = new String[nEstados][nEntradas];
				// A1 A3 D3 Q2 C0 OUT
				sr.readLine();
				String mensaje = sr.readLine();

				String[] salidas = new String[arregloAlfabetos.length];
				int i = 0;
				while (!mensaje.equals("---")&& i!=nEstados) {
					String[] linea = mensaje.split(" ");
					for (int j = 0; j < linea.length-2; j++) {
						equivalenteDos[i][j]= linea[j+1];
						if(j+1 == linea.length-2) {
							salidas[i]=linea[j+2];
						}
					}
					i++;
					mensaje = sr.readLine();
				}
				maquinaDos.setIncidenceMatrix(equivalenteDos);
				maquinaDos.setStates(arregloAlfabetos);
				maquinaDos.setOutputs(salidas);
				imprimirMoore(maquinaDos);
			}
			sr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean equivalenteMealy() {
		boolean equivalente = false;
		return equivalente;
	}

	public static boolean equivalenteMoore() {
		boolean equivalente = false;
		return equivalente;
	}

	public static void imprimirMealy(MaquinaMealy maquina) {
		String[][] matrix= maquina.getIncidenceMatrix();
		
		for (int i = 0; i < matrix.length; i++) {
			String imprimir="";
			for (int j = 0; j < matrix[0].length; j++) {
				imprimir+=matrix[i][j]+" ";
			}
			System.out.println(imprimir);
		}
	}
	
	public static void imprimirMoore(MaquinaMoore maquina) {
		String[][] matrix= maquina.getIncidenceMatrix();
		
		for (int i = 0; i < matrix.length; i++) {
			String imprimir="";
			for (int j = 0; j < matrix[0].length; j++) {
				imprimir+=matrix[i][j]+" ";
			}
			System.out.println(imprimir);
		}
		String[] salidas= maquina.getOutputs();
		for (int i = 0; i < salidas.length; i++) {
			System.out.println(salidas[i]);
		}
	}
	
	

}
