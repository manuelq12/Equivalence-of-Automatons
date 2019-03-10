package modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	public static String MEALY = "M";
	public static String MOORE = "O";
	public static void main(String[] args) {
		try {
			BufferedReader sr = new BufferedReader(new InputStreamReader(System.in));

			String maquina = sr.readLine();
			if (maquina.equals(MEALY)) {
				String estadoM1 = sr.readLine();
				String entradasM1 = sr.readLine();
				int nEstadosM1 = Integer.parseInt(estadoM1);
				int nEntradasM1 = Integer.parseInt(entradasM1);
				String alfabetoEstados = sr.readLine();
				String[] arregloAlfabetosM1 = alfabetoEstados.split(",");
				String[][] equivalenteUnoM1 = new String[nEstadosM1][nEntradasM1];
				sr.readLine();
				String mensaje = sr.readLine();
				int i = 0;
				while (!mensaje.equals("---") && i != nEstadosM1) {
					String[] linea = mensaje.split(" ");
					for (int j = 0; j < linea.length - 1; j++) {
						equivalenteUnoM1[i][j] = linea[j + 1];
					}
					i++;
					mensaje = sr.readLine();
				}
				String estadoM2 = sr.readLine();
				String entradasM2 = sr.readLine();
				int nEstadosM2 = Integer.parseInt(estadoM2);
				int nEntradasM2 = Integer.parseInt(entradasM2);
				String alfabetoEstadosM2 = sr.readLine();
				String[] arregloAlfabetosM2 = alfabetoEstadosM2.split(",");
				String[][] equivalenteUnoM2 = new String[nEstadosM2][nEntradasM2];
				sr.readLine();
				String mensajeM2 = sr.readLine();
				int iM2 = 0;
				while (!mensajeM2.equals("---") && iM2 != nEstadosM2) {
					String[] lineaM2 = mensajeM2.split(" ");
					for (int j = 0; j < lineaM2.length - 1; j++) {
						equivalenteUnoM2[iM2][j] = lineaM2[j + 1];
					}
					iM2++;
					mensajeM2 = sr.readLine();
				}
				Equivalencia nueva= new Equivalencia(nEstadosM1, nEntradasM1, arregloAlfabetosM1, equivalenteUnoM1, nEstadosM2, nEntradasM2, arregloAlfabetosM2, equivalenteUnoM2);
			} else if (maquina.equals(MOORE)) {
				String estadosM1 = sr.readLine();
				String entradasM1 = sr.readLine();
				int nEstadosM1 = Integer.parseInt(estadosM1);
				int nEntradasM1 = Integer.parseInt(entradasM1);
				String alfabetoEstadosM1 = sr.readLine();
				String[] arregloAlfabetosM1 = alfabetoEstadosM1.split(",");
				String[][] equivalenteDosM1 = new String[nEstadosM1][nEntradasM1];
				sr.readLine();
				String mensajeM1 = sr.readLine();
				String[] salidasM1 = new String[arregloAlfabetosM1.length];
				int i = 0;
				while (!mensajeM1.equals("---") && i != nEstadosM1) {
					String[] linea = mensajeM1.split(" ");
					for (int j = 0; j < linea.length - 2; j++) {
						equivalenteDosM1[i][j] = linea[j + 1];
						if (j + 1 == linea.length - 2) {
							salidasM1[i] = linea[j + 2];
						}
					}
					i++;
					mensajeM1 = sr.readLine();
				}
				String estadosM2 = sr.readLine();
				String entradasM2 = sr.readLine();
				int nEstadosM2 = Integer.parseInt(estadosM2);
				int nEntradasM2 = Integer.parseInt(entradasM2);
				String alfabetoEstadosM2 = sr.readLine();
				String[] arregloAlfabetosM2 = alfabetoEstadosM2.split(",");
				String[][] equivalenteDosM2 = new String[nEstadosM2][nEntradasM2];
				sr.readLine();
				String mensajeM2 = sr.readLine();
				String[] salidasM2 = new String[arregloAlfabetosM2.length];
				int iM2 = 0;
				while (!mensajeM2.equals("---") && iM2 != nEstadosM2) {
					String[] linea = mensajeM2.split(" ");
					for (int j = 0; j < linea.length - 2; j++) {
						equivalenteDosM2[iM2][j] = linea[j + 1];
						if (j + 1 == linea.length - 2) {
							salidasM2[iM2] = linea[j + 2];
						}
					}
					iM2++;
					mensajeM2 = sr.readLine();
				}
				Equivalencia nueva= new Equivalencia(nEstadosM1, nEntradasM1, arregloAlfabetosM1, equivalenteDosM1,salidasM1, nEstadosM2, nEntradasM2, arregloAlfabetosM2, equivalenteDosM2,salidasM2);
			}
			sr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
