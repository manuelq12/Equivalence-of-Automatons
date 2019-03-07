package modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Control {
	public static String MEALY= "M";
	public static String MOORE= "O";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		try {
			BufferedReader sr= new BufferedReader(new InputStreamReader(System.in));
			
			String maquina= sr.readLine();
			if(maquina.equals(MEALY)){
				String estados= sr.readLine();
				String entradas= sr.readLine();
				// INT
				int nEstados= Integer.parseInt(estados);
				// INT
				int nEntradas= Integer.parseInt(entradas);
				String alfabetoEstados= sr.readLine();
				
				//  A,B,C,D
				String[] arregloAlfabetos= alfabetoEstados.split(",");
				
				MaquinaMealy maquinaUno= new MaquinaMealy(nEstados, nEntradas);
				
				String[][] equivalenteUno= new String[nEstados][nEntradas];
				// A1 A3 D3 Q2 C0 D0
				String mensaje= sr.readLine();
				int i=0;
				while(!mensaje.equals("---")) {
					String[] linea= mensaje.split(" ");
					for (int j = 0; j < linea.length; j++) {
						if ((i == 0 && j == 0)||(i == 0 && j == linea.length-1)) equivalenteUno[i][j] = "null";
						 
						else {
							equivalenteUno[i][j]= linea[j]; 						
						}
					}
					mensaje= sr.readLine();
				}
			}
			else if(maquina.equals(MOORE)) {
				String estados= sr.readLine();
				String entradas= sr.readLine();
				// INT
				int nEstados= Integer.parseInt(estados);
				// INT
				int nEntradas= Integer.parseInt(entradas);
				String alfabetoEstados= sr.readLine();
				
				//  A,B,C,D
				String[] arregloAlfabetos= alfabetoEstados.split(",");
				
				MaquinaMealy maquinaUno= new MaquinaMealy(nEstados, nEntradas);
				
				String[][] equivalenteUno= new String[nEstados][nEntradas];
				// A1 A3 D3 Q2 C0 OUT
				String mensaje= sr.readLine();
				
				String[] salidas= new String[arregloAlfabetos.length];
				int i=0;
				while (!mensaje.equals("---")) {
					String[] linea = mensaje.split(" ");
					for (int j = 0; j < linea.length; j++) {
						if ((i == 0 && j == 0) ||(i == 0 && j == linea.length-1))
							equivalenteUno[i][j] = "null";
						else {
							if (j != linea.length - 1) {
								equivalenteUno[i][j] = linea[j];
							} else {
								if (i != 0)salidas[i] = linea[j];
							}
						}
					}
					mensaje= sr.readLine();
				}
			}
			sr.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static boolean equivalenteMealy() {
		boolean equivalente= false;
		return equivalente;
	}
public static boolean equivalenteMoore() {
		boolean equivalente= false;
		return equivalente;
	}

}
