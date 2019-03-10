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
				while (!mensaje.equals("---") && i != nEstados) {
					String[] linea = mensaje.split(" ");
					for (int j = 0; j < linea.length - 1; j++) {
						equivalenteUno[i][j] = linea[j + 1];
					}
					i++;
					mensaje = sr.readLine();
				}
				maquinaUno.setIncidenceMatrix(equivalenteUno);
				maquinaUno.setStates(arregloAlfabetos);
				imprimirMealy(maquinaUno);
				System.out.println("---");
				imprimirMealy(eliminarEstadosInaccesibles(maquinaUno));
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
				while (!mensaje.equals("---") && i != nEstados) {
					String[] linea = mensaje.split(" ");
					for (int j = 0; j < linea.length - 2; j++) {
						equivalenteDos[i][j] = linea[j + 1];
						if (j + 1 == linea.length - 2) {
							salidas[i] = linea[j + 2];
						}
					}
					i++;
					mensaje = sr.readLine();
				}
				maquinaDos.setIncidenceMatrix(equivalenteDos);
				maquinaDos.setStates(arregloAlfabetos);
				maquinaDos.setOutputs(salidas);
				imprimirMoore(maquinaDos);
				System.out.println("---");
				imprimirMoore(eliminarEstadosInaccesibles(maquinaDos));
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
		String[][] matrix = maquina.getIncidenceMatrix();

		for (int i = 0; i < matrix.length; i++) {
			String imprimir = "";
			for (int j = 0; j < matrix[0].length; j++) {
				imprimir += matrix[i][j] + " ";
			}
			System.out.println(imprimir);
		}
	}

	public static void imprimirMoore(MaquinaMoore maquina) {
		String[][] matrix = maquina.getIncidenceMatrix();

		for (int i = 0; i < matrix.length; i++) {
			String imprimir = "";
			for (int j = 0; j < matrix[0].length; j++) {
				imprimir += matrix[i][j] + " ";
			}
			System.out.println(imprimir);
		}
		String[] salidas = maquina.getOutputs();
		for (int i = 0; i < salidas.length; i++) {
			System.out.println(salidas[i]);
		}
	}

	public static MaquinaMealy eliminarEstadosInaccesibles(MaquinaMealy maquina) {
		String[] estados= maquina.getStates();
		// Create graph to do bfs 
		String[][] incide= maquina.getIncidenceMatrix();
		Graph m = new Graph(estados.length,estados);
		
		for (int i = 0; i < incide.length; i++) {
			for (int j = 0; j < incide[0].length; j++) {
				m.addEdge(estados[i].substring(0), incide[i][j].charAt(0)+"");
			}
		}
		String[] bfs= m.BFS(estados[0]).split(" ");
		
		if(bfs.length== estados.length) return maquina;
		else return crearNuevaMaquina(bfs, maquina);
	}
	public static MaquinaMealy crearNuevaMaquina(String[] acessibles, MaquinaMealy maquina) {
		int nEstados = acessibles.length;
		int nEntradas = maquina.getIncidenceMatrix()[0].length;
		String[] arregloAlfabetos = acessibles;
		MaquinaMealy maquinaUno = new MaquinaMealy(nEstados, nEntradas);

		String[][] equivalenteUno = new String[nEstados][nEntradas];
		String[][] anterior= maquina.getIncidenceMatrix();
		int k=0;
		for (int i = 0; i < anterior.length; i++) {
			if(contenido(maquina.getStates()[i], arregloAlfabetos)) {
			for (int j = 0; j < anterior[0].length; j++) {
			equivalenteUno[k][j]= anterior[i][j];
			}
			k++;
			}
		}
		
		maquinaUno.setIncidenceMatrix(equivalenteUno);
		maquinaUno.setStates(arregloAlfabetos);
		return maquinaUno;
	}
	public static boolean contenido(String a, String[] lista) {
		for (int i = 0; i < lista.length; i++) {
			if(a.equals(lista[i]))return true;
		}
		return false;
	}
	
//	O
//	5
//	2
//	A,B,C,D,E
//	null 0 1 null
//	A B A 0
//	B D B 1
//	C A C 0
//	D A D 1
//	E E D 0
//	---

	public static MaquinaMoore eliminarEstadosInaccesibles(MaquinaMoore maquina) {
		String[] estados= maquina.getStates();
		// Create graph to do bfs 
		String[][] incide= maquina.getIncidenceMatrix();
		Graph m = new Graph(estados.length,estados);
		for (int i = 0; i < incide.length; i++) {
			for (int j = 0; j < incide[0].length; j++) {
				m.addEdge(estados[i].substring(0), incide[i][j].charAt(0)+"");
			}
		}
		String[] bfs= m.BFS(estados[0]).split(" ");
		if(bfs.length== estados.length) return maquina;
		else return crearNuevaMaquina(bfs, maquina);
	}
	public static MaquinaMoore crearNuevaMaquina(String[] acessibles, MaquinaMoore maquina) {
		int nEstados = acessibles.length;
		int nEntradas = maquina.getIncidenceMatrix()[0].length;
		String[] arregloAlfabetos = acessibles;
		MaquinaMoore maquinaUno = new MaquinaMoore(nEstados, nEntradas);

		String[][] equivalenteUno = new String[nEstados][nEntradas];
		String[][] anterior= maquina.getIncidenceMatrix();
		String[] salidas= new String[nEstados];
		int k=0;
		for (int i = 0; i < anterior.length; i++) {
			if(contenido(maquina.getStates()[i], arregloAlfabetos)) {
			for (int j = 0; j < anterior[0].length; j++) {
			equivalenteUno[k][j]= anterior[i][j];
			}
			salidas[k]= maquina.getOutputs()[i];
			k++;
			}
		}
		maquinaUno.setIncidenceMatrix(equivalenteUno);
		maquinaUno.setStates(arregloAlfabetos);
		maquinaUno.setOutputs(salidas);
		return maquinaUno;
	}

}
