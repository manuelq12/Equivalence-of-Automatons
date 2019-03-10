package modelo;

import java.awt.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public class Equivalencia {


	public MaquinaMealy m1;
	public MaquinaMealy m2;
	public MaquinaMealy m3Suma;
	
	public MaquinaMoore o1;
	public MaquinaMoore o2;
	public MaquinaMoore o3Suma;

	// Constructor para una equivalencia entre dos maquinas MEALY
	public Equivalencia(int estadoM1, int entradasM1, String[] estadosM1, String[][] equivalenteUno, int estadoM2,
			int entradasM2, String[] estadosM2, String[][] equivalenteDos) {

		MaquinaMealy maquinaUno = new MaquinaMealy(estadoM1, entradasM1);
		MaquinaMealy maquinaDos = new MaquinaMealy(estadoM2, entradasM2);

		maquinaUno.setIncidenceMatrix(equivalenteUno);
		maquinaUno.setStates(estadosM1);
		
		maquinaDos.setIncidenceMatrix(equivalenteDos);
		maquinaDos.setStates(estadosM2);
		
		m1=maquinaUno;
		m2=maquinaDos;
		
		imprimirMealy(m1);
		System.out.println("--");
		imprimirMealy(m2);
		
		System.out.println("//");
		//Paso 1
		m1= eliminarEstadosInaccesibles(m1);
		m2= eliminarEstadosInaccesibles(m2);
		imprimirMealy(m1);
		System.out.println("--");
		imprimirMealy(m2);
		System.out.println("//");
		//Paso 2
		String[] nuevasVariables= renombrarVariablesB(m1.getStates(), m2.getStates());
		m2= nuevaMaquinaRenombrada(m2,nuevasVariables);
		imprimirMealy(m1);
		System.out.println("--");
		imprimirMealy(m2);
		System.out.println("//");
		//Paso 3
		m3Suma= sumaDirectaMealy(m1, m2);
		imprimirMealy(m3Suma);
	}

	// Constructor para una equivalencia entre dos maquinas MOORE
	public Equivalencia(int estadoM1, int entradasM1, String[] estadosM1, String[][] equivalenteUno, String[] salidasM1,
			int estadoM2, int entradasM2, String[] estadosM2, String[][] equivalenteDos, String[] salidasM2) {

		MaquinaMoore maquinaUno = new MaquinaMoore(estadoM1, entradasM1);
		
		maquinaUno.setIncidenceMatrix(equivalenteUno);
		maquinaUno.setStates(estadosM1);
		maquinaUno.setOutputs(salidasM1);
		
		MaquinaMoore maquinaDos = new MaquinaMoore(estadoM2, entradasM2);
		
		maquinaDos.setIncidenceMatrix(equivalenteDos);
		maquinaDos.setStates(estadosM2);
		maquinaDos.setOutputs(salidasM2);
		
		o1=maquinaUno;
		o2=maquinaDos;
		
		imprimirMoore(o1);
		System.out.println("--");
		imprimirMoore(o2);
		
		System.out.println("//");
		//Paso 1
		o1= eliminarEstadosInaccesibles(o1);
		o2= eliminarEstadosInaccesibles(o2);
		imprimirMoore(o1);
		System.out.println("--");
		imprimirMoore(o2);
		System.out.println("//");
		//Paso 2
		String[] nuevasVariables= renombrarVariablesB(o1.getStates(), o2.getStates());
		o2= nuevaMaquinaRenombrada(o2,nuevasVariables);
		imprimirMoore(o1);
		System.out.println("--");
		imprimirMoore(o2);
		System.out.println("//");
		
		//Paso 3
		o3Suma= sumaDirectaMoore(o1, o2);
		imprimirMoore(o3Suma);
	}

	public boolean equivalenteMealy() {
		boolean equivalente = false;
		return equivalente;
	}

	public boolean equivalenteMoore() {
		boolean equivalente = false;
		return equivalente;
	}

	public void imprimirMealy(MaquinaMealy maquina) {
		String[][] matrix = maquina.getIncidenceMatrix();

		for (int i = 0; i < matrix.length; i++) {
			String imprimir = "";
			for (int j = 0; j < matrix[0].length; j++) {
				imprimir += matrix[i][j] + " ";
			}
			System.out.println(imprimir);
		}
	}

	public void imprimirMoore(MaquinaMoore maquina) {
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

	public MaquinaMealy eliminarEstadosInaccesibles(MaquinaMealy maquina) {
		String[] estados = maquina.getStates();
		String[][] incide = maquina.getIncidenceMatrix();
		Graph m = new Graph(estados.length, estados);
		for (int i = 0; i < incide.length; i++) {
			for (int j = 0; j < incide[0].length; j++) {
				m.addEdge(estados[i].substring(0), incide[i][j].charAt(0) + "");
			}
		}
		String[] bfs = m.BFS(estados[0]).split(" ");

		if (bfs.length == estados.length)
			return maquina;
		else
			return crearNuevaMaquina(bfs, maquina);
	}

	public MaquinaMealy crearNuevaMaquina(String[] acessibles, MaquinaMealy maquina) {
		int nEstados = acessibles.length;
		int nEntradas = maquina.getIncidenceMatrix()[0].length;
		String[] arregloAlfabetos = acessibles;
		MaquinaMealy maquinaUno = new MaquinaMealy(nEstados, nEntradas);
		String[][] equivalenteUno = new String[nEstados][nEntradas];
		String[][] anterior = maquina.getIncidenceMatrix();
		int k = 0;
		for (int i = 0; i < anterior.length; i++) {
			if (contenido(maquina.getStates()[i], arregloAlfabetos)) {
				for (int j = 0; j < anterior[0].length; j++) {
					equivalenteUno[k][j] = anterior[i][j];
				}
				k++;
			}
		}
		maquinaUno.setIncidenceMatrix(equivalenteUno);
		maquinaUno.setStates(arregloAlfabetos);
		return maquinaUno;
	}

	public boolean contenido(String a, String[] lista) {
		for (int i = 0; i < lista.length; i++) {
			if (a.equals(lista[i]))
				return true;
		}
		return false;
	}

	public MaquinaMoore eliminarEstadosInaccesibles(MaquinaMoore maquina) {
		String[] estados = maquina.getStates();
		String[][] incide = maquina.getIncidenceMatrix();
		Graph m = new Graph(estados.length, estados);
		for (int i = 0; i < incide.length; i++) {
			for (int j = 0; j < incide[0].length; j++) {
				m.addEdge(estados[i].substring(0), incide[i][j].charAt(0) + "");
			}
		}
		String[] bfs = m.BFS(estados[0]).split(" ");
		if (bfs.length == estados.length)
			return maquina;
		else
			return crearNuevaMaquina(bfs, maquina);
	}

	public MaquinaMoore crearNuevaMaquina(String[] acessibles, MaquinaMoore maquina) {
		int nEstados = acessibles.length;
		int nEntradas = maquina.getIncidenceMatrix()[0].length;
		String[] arregloAlfabetos = acessibles;
		MaquinaMoore maquinaUno = new MaquinaMoore(nEstados, nEntradas);
		String[][] equivalenteUno = new String[nEstados][nEntradas];
		String[][] anterior = maquina.getIncidenceMatrix();
		String[] salidas = new String[nEstados];
		int k = 0;
		for (int i = 0; i < anterior.length; i++) {
			if (contenido(maquina.getStates()[i], arregloAlfabetos)) {
				for (int j = 0; j < anterior[0].length; j++) {
					equivalenteUno[k][j] = anterior[i][j];
				}
				salidas[k] = maquina.getOutputs()[i];
				k++;
			}
		}
		maquinaUno.setIncidenceMatrix(equivalenteUno);
		maquinaUno.setStates(arregloAlfabetos);
		maquinaUno.setOutputs(salidas);
		return maquinaUno;
	}

	public String[] renombrarVariablesB(String[] a, String[] b) {
		String[] retorno = new String[b.length];
		Random rand = new Random();
		for (int i = 0; i < b.length; i++) {

			if (contenido(b[i], a)) {
				int n = rand.nextInt(25) + 65;
				char nuevaLetra = (char) n;
				retorno[i] = nuevaLetra + "";
			} else {
				retorno[i] = b[i];
			}
		}
		return retorno;
	}

	public MaquinaMoore nuevaMaquinaRenombrada(MaquinaMoore maquina, String[] nuevosEstados) {
		MaquinaMoore retorno = new MaquinaMoore(maquina.getStates().length, maquina.getIncidenceMatrix()[0].length);
		String[] estadosViejos = maquina.getStates();
		retorno.setStates(nuevosEstados);
		String[][] adya = maquina.getIncidenceMatrix();
		for (int i = 0; i < adya.length; i++) {
			for (int j = 0; j < adya[0].length; j++) {
				adya[i][j] = nuevosEstados[getIndex(adya[i][j], estadosViejos)];
			}
		}
		retorno.setIncidenceMatrix(adya);
		retorno.setOutputs(maquina.getOutputs());
		return retorno;
	}

	public MaquinaMealy nuevaMaquinaRenombrada(MaquinaMealy maquina, String[] nuevosEstados) {
		MaquinaMealy retorno = new MaquinaMealy(maquina.getStates().length, maquina.getIncidenceMatrix()[0].length);
		String[] estadosViejos = maquina.getStates();
		retorno.setStates(nuevosEstados);
		String[][] adya = maquina.getIncidenceMatrix();
		for (int i = 0; i < adya.length; i++) {
			for (int j = 0; j < adya[0].length; j++) {
				adya[i][j] = nuevosEstados[getIndex(adya[i][j].charAt(0) + "", estadosViejos)] + adya[i][j].charAt(1);
			}
		}
		retorno.setIncidenceMatrix(adya);
		retorno.setOutputs(maquina.getOutputs());
		return retorno;
	}

	public int getIndex(String estado, String[] estados) {
		for (int i = 0; i < estados.length; i++) {
			if (estados[i].equals(estado))
				return i;
		}
		return -1;
	}
	public MaquinaMealy sumaDirectaMealy(MaquinaMealy a, MaquinaMealy b) {
		MaquinaMealy retorno= new MaquinaMealy(a.getStates().length + b.getStates().length, a.getIncidenceMatrix()[0].length);
		String[][] alist= a.getIncidenceMatrix(); 
		String[][] blist= b.getIncidenceMatrix();
		String[][] adya= new String[a.getStates().length + b.getStates().length][a.getIncidenceMatrix()[0].length];
		String[] estados= concatenarListas(a.getStates(), b.getStates());
		int k=0;
		for (int i = 0; i < adya.length; i++) {
			
			if(i< alist.length) {
			for (int j = 0; j < adya[0].length; j++) {
				adya[i][j]=alist[i][j];
			}
			}
			else {
				for (int j = 0; j < adya[0].length; j++) {
					adya[i][j]=blist[k][j];
				}
				k++;
			}
		}
		retorno.setIncidenceMatrix(adya);
		retorno.setStates(estados);
		return retorno;
	}
	
	public MaquinaMoore sumaDirectaMoore(MaquinaMoore a, MaquinaMoore b) {
		MaquinaMoore retorno= new MaquinaMoore(a.getStates().length + b.getStates().length, a.getIncidenceMatrix()[0].length);
		String[][] alist= a.getIncidenceMatrix(); 
		String[][] blist= b.getIncidenceMatrix();
		String[][] adya= new String[a.getStates().length + b.getStates().length][a.getIncidenceMatrix()[0].length];
		String[] estados= concatenarListas(a.getStates(), b.getStates());
		String[] salidas= concatenarListas(a.getOutputs(), b.getOutputs());
		int k=0;
		for (int i = 0; i < adya.length; i++) {
			
			if(i< alist.length) {
			for (int j = 0; j < adya[0].length; j++) {
				adya[i][j]=alist[i][j];
			}
			}
			else {
				for (int j = 0; j < adya[0].length; j++) {
					adya[i][j]=blist[k][j];
				}
				k++;
			}
		}
		retorno.setIncidenceMatrix(adya);
		retorno.setStates(estados);
		retorno.setOutputs(salidas);
		return retorno;
	}
	
	public String[] concatenarListas(String[] a, String[] b) {
		String[] retorno= new String[a.length+b.length];
		int j=0;
		for (int i = 0; i < retorno.length; i++) {
			if(i<a.length) {
				retorno[i]= a[i];
			}
			else {
				retorno[i]= b[j];
				j++;
			}
		}
		return retorno;
	}
}
