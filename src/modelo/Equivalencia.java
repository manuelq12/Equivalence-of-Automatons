package modelo;

import java.util.ArrayList;
import java.util.Random;

public class Equivalencia {

	/**
	 * Atributo de tipo MaquinaMealy que indica la primera maquina a comparar por el usuario
	 */
	public MaquinaMealy m1;
	/**
	 * Atributo de tipo MaquinaMealy que indica la segunda maquina a comparar por el usuario
	 */
	public MaquinaMealy m2;
	/**
	 * Atributo de tipo MaquinaMealy que indica la suma directa las maquinas m1 y m2
	 */
	public MaquinaMealy m3Suma;
	/**
	 * Atributo de tipo MaquinaMoore que indica la primera maquina a comparar por el usuario
	 */
	public MaquinaMoore o1;
	/**
	 * Atributo de tipo MaquinaMoore que indica la segunda maquina a comparar por el usuario
	 */
	public MaquinaMoore o2;
	/**
	 * Atributo de tipo MaquinaMealy que indica la suma directa las maquinas o1 y o2
	 */
	public MaquinaMoore o3Suma;
	/**
	 * Atributo que indica si las dos maquinas son equivalentes
	 */
	public boolean equivalent;

	/**
	 * Contructor de la equivalencia, en este caso es para cuando se van a comparar dos MaquinaMealy , debido a 
	 * que a diferencia del otro constructor este no tiene un arreglo de salidas indicado.este solo
	 * funciona para dos maquina mealy.
	 * @param estadoM1 indica el numero de estados que contiene la maquina M1
	 * @param entradasM1 indica el numero de entradas que contiene la maquina M1
	 * @param estadosM1 arreglo donde se contiene el alfabetos de entrada de la maquina M1, este debe ser una sola letra
	 * @param equivalenteUno indica la matriz de estados de adyacencias que contiene la maquina M1
	 * @param estadoM2 indica el numero de estados que contiene la maquina M2
	 * @param entradasM2 indica el numero de entradas que contiene la maquina M2
	 * @param estadosM2 arreglo donde se contiene el alfabetos de entrada de la maquina M2, este debe ser una sola letra
	 * @param equivalenteDos indica la matriz de estados de adyacencias que contiene la maquina M2
	 */
	public Equivalencia(int estadoM1, int entradasM1, String[] estadosM1, String[][] equivalenteUno, int estadoM2,
			int entradasM2, String[] estadosM2, String[][] equivalenteDos) {

		MaquinaMealy maquinaUno = new MaquinaMealy(estadoM1, entradasM1);
		MaquinaMealy maquinaDos = new MaquinaMealy(estadoM2, entradasM2);

		maquinaUno.setAdyacencyMatrix(equivalenteUno);
		maquinaUno.setStates(estadosM1);
		
		maquinaDos.setAdyacencyMatrix(equivalenteDos);
		maquinaDos.setStates(estadosM2);
		
		m1=maquinaUno;
		m2=maquinaDos;
		
		
		// Prueba particiones 
		
		//Paso 1
		m1= eliminarEstadosInaccesibles(m1);
		m2= eliminarEstadosInaccesibles(m2);
		//Paso 2
		String[] nuevasVariables= renombrarVariablesB(m1.getStates(), m2.getStates());
		m2= nuevaMaquinaRenombrada(m2,nuevasVariables);
		//Paso 3
		m3Suma= sumaDirectaMealy(m1, m2);
		
		particionesMealy(m3Suma);
		
		ArrayList<ArrayList<String>> lastPartition = particionesMealy(m3Suma);
		String stateOne = m1.getStates()[0];
		String stateTwo = m2.getStates()[0];
		equivalent = equivalentMachines(stateOne, stateTwo, lastPartition);
		
	}

	/**
	 * Contructor de la equivalencia, en este caso es para cuando se van a comparar dos MaquinaMoore, este constructor de sobrecarga uno,
	 * se diferencia del otro porque contiene un parametro de entrada de tipo contenedor que contiene un arreglo de las salidas, este solo
	 * funciona para dos maquina moore.
	 * @param estadoM1 indica el numero de estados que contiene la maquina M1
	 * @param entradasM1 indica el numero de entradas que contiene la maquina M1
	 * @param estadosM1 arreglo donde se contiene el alfabetos de entrada de la maquina M1, este debe ser una sola letra
	 * @param equivalenteUno indica la matriz de estados de adyacencias que contiene la maquina M1
	 * @param salidasM1
	 * @param estadoM2 indica el numero de estados que contiene la maquina M2
	 * @param entradasM2 indica el numero de entradas que contiene la maquina M2
	 * @param estadosM2 arreglo donde se contiene el alfabetos de entrada de la maquina M2, este debe ser una sola letra
	 * @param equivalenteDos indica la matriz de estados de adyacencias que contiene la maquina M2
	 * @param salidasM2
	 */
	public Equivalencia(int estadoM1, int entradasM1, String[] estadosM1, String[][] equivalenteUno, String[] salidasM1,
			int estadoM2, int entradasM2, String[] estadosM2, String[][] equivalenteDos, String[] salidasM2) {

		MaquinaMoore maquinaUno = new MaquinaMoore(estadoM1, entradasM1);
		
		maquinaUno.setAdyacencyMatrix(equivalenteUno);
		maquinaUno.setStates(estadosM1);
		maquinaUno.setOutputs(salidasM1);
		
		MaquinaMoore maquinaDos = new MaquinaMoore(estadoM2, entradasM2);
		
		maquinaDos.setAdyacencyMatrix(equivalenteDos);
		maquinaDos.setStates(estadosM2);
		maquinaDos.setOutputs(salidasM2);
		
		o1=maquinaUno;
		o2=maquinaDos;
		
		
		//Paso 1
		o1= eliminarEstadosInaccesibles(o1);
		o2= eliminarEstadosInaccesibles(o2);
		//Paso 2
		String[] nuevasVariables= renombrarVariablesB(o1.getStates(), o2.getStates());
		o2= nuevaMaquinaRenombrada(o2,nuevasVariables);
		
		//Paso 3
		o3Suma= sumaDirectaMoore(o1, o2);
		//Paso 4
		
		ArrayList<ArrayList<String>> lastPartition = particionesMoore(o3Suma);
		String stateOne = o1.getStates()[0];
		String stateTwo = o2.getStates()[0];
		equivalent = equivalentMachines(stateOne, stateTwo, lastPartition);
	}

	/**
	 * Metodo auxiliar para imprimir mealy
	 * @param maquina MaquinaMealy que se desea el imprimir.
	 */
	public void imprimirMealy(MaquinaMealy maquina) {
		String[][] matrix = maquina.getAdyacencyMatrix();

		for (int i = 0; i < matrix.length; i++) {
			String imprimir = "";
			for (int j = 0; j < matrix[0].length; j++) {
				imprimir += matrix[i][j] + " ";
			}
		}
	}
	/**
	 * Metodo auxiliar para imprimir Moore
	 * @param maquina MaquinaMoore que se desea imprimir
	 */
	public void imprimirMoore(MaquinaMoore maquina) {
		String[][] matrix = maquina.getAdjacencyMatrix();

		for (int i = 0; i < matrix.length; i++) {
			String imprimir = "";
			for (int j = 0; j < matrix[0].length; j++) {
				imprimir += matrix[i][j] + " ";
			}
		}
		String[] salidas = maquina.getOutputs();
		for (int i = 0; i < salidas.length; i++) {
			System.out.println(salidas[i]);
		}
	}
	/**
	 * Metodo que retorna una MaquinaMealy sin los estados estados inaccesibles 
	 * @param maquina Indica la maquina que se desea analizar que contiene o no estados inaccesibles
	 * @return Un objetivo de tipo MaquinaMealy sin estados insaccesibles 
	 */
	public MaquinaMealy eliminarEstadosInaccesibles(MaquinaMealy maquina) {
		String[] estados = maquina.getStates();
		String[][] incide = maquina.getAdyacencyMatrix();
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
	/**
	 * Metodo Auxiliar que crea una nueva maquinaMealy apartir de un arreglo de tipo string que indica los estados accesibles
	 * @param acessibles un arreglo de tipo string[] que indica los estados accesibles 
	 * @param maquina MaquinaMealy a la que se le desea eliminar las filas con los estados inaccesibles
	 * @return una MaquinaMealy con los estados inaccesibles eliminados
	 */

	public MaquinaMealy crearNuevaMaquina(String[] acessibles, MaquinaMealy maquina) {
		int nEstados = acessibles.length;
		int nEntradas = maquina.getAdyacencyMatrix()[0].length;
		String[] arregloAlfabetos = acessibles;
		MaquinaMealy maquinaUno = new MaquinaMealy(nEstados, nEntradas);
		String[][] equivalenteUno = new String[nEstados][nEntradas];
		String[][] anterior = maquina.getAdyacencyMatrix();
		int k = 0;
		for (int i = 0; i < anterior.length; i++) {
			if (contenido(maquina.getStates()[i], arregloAlfabetos)) {
				for (int j = 0; j < anterior[0].length; j++) {
					equivalenteUno[k][j] = anterior[i][j];
				}
				k++;
			}
		}
		maquinaUno.setAdyacencyMatrix(equivalenteUno);
		maquinaUno.setStates(arregloAlfabetos);
		return maquinaUno;
	}
	/**
	 * Metodo auxiliar que indica si un determinado String se encuentra en una lista de String[] de tamaño determinado 
	 * @param a el String que se desea buscar
	 * @param lista la lista donde se va buscar el String a 
	 * @return un boolean indicando si el String a se ecnuentra en la lista de String[]
	 */
	public boolean contenido(String a, String[] lista) {
		for (int i = 0; i < lista.length; i++) {
			if (a.equals(lista[i]))
				return true;
		}
		return false;
	}
	/**
	 * Metodo que retorna una MaquinaMoore sin los estados estados inaccesibles 
	 * @param maquina Indica la maquina que se desea analizar que contiene o no estados inaccesibles
	 * @return Un objetivo de tipo MaquinaMealy sin estados insaccesibles 
	 */
	public MaquinaMoore eliminarEstadosInaccesibles(MaquinaMoore maquina) {
		String[] estados = maquina.getStates();
		String[][] incide = maquina.getAdjacencyMatrix();
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
	/**
	 * Metodo Auxiliar que crea una nueva maquinaMoore apartir de un arreglo de tipo string que indica los estados accesibles
	 * @param acessibles un arreglo de tipo string[] que indica los estados accesibles 
	 * @param maquina MaquinaMoore a la que se le desea eliminar las filas con los estados inaccesibles
	 * @return una MaquinaMoore con los estados inaccesibles eliminados
	 */
	public MaquinaMoore crearNuevaMaquina(String[] acessibles, MaquinaMoore maquina) {
		int nEstados = acessibles.length;
		int nEntradas = maquina.getAdjacencyMatrix()[0].length;
		String[] arregloAlfabetos = acessibles;
		MaquinaMoore maquinaUno = new MaquinaMoore(nEstados, nEntradas);
		String[][] equivalenteUno = new String[nEstados][nEntradas];
		String[][] anterior = maquina.getAdjacencyMatrix();
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
		maquinaUno.setAdyacencyMatrix(equivalenteUno);
		maquinaUno.setStates(arregloAlfabetos);
		maquinaUno.setOutputs(salidas);
		return maquinaUno;
	}
	/**
	 * Metodo axuliar que renombra los String de un arreglo b apartir de un arreglo de String a , si es el caso que tenga variables con el mismo nombre
	 * @param a lista de contenedora de tipo String[] que se utiliza como referencia 
	 * @param b lista de contenedora de tipo String[] que se utiliza como referencia  
	 * @return una lista con las variables renombras
	 */
	public String[] renombrarVariablesB(String[] a, String[] b) {
		String[] retorno = new String[b.length];
		Random rand = new Random();
		for (int i = 0; i < b.length; i++) {

			if (contenido(b[i], a)) {
				int n = rand.nextInt(25) + 65;
				char nuevaLetra = (char) n;
				while(contenido(nuevaLetra+"",a)) {
					n=rand.nextInt(25) + 65;
					nuevaLetra= (char) n;
				}
				retorno[i] = nuevaLetra + "";
			} else {
				retorno[i] = b[i];
			}
		}
		return retorno;
	}
	/**
	 * Metodo que retorna una maquinaMoore con estados renombrados , habiendo asi nombre de estados totalmente diferentes dependiendo de la lista
	 * @param maquina maquinaMoore que desea ser renombrada 
	 * @param nuevosEstados indica la lista de estados renombrados 
	 * @return MaquinaMoore que tiene los estados renombrados apartir de la lista
	 */
	public MaquinaMoore nuevaMaquinaRenombrada(MaquinaMoore maquina, String[] nuevosEstados) {
		MaquinaMoore retorno = new MaquinaMoore(maquina.getStates().length, maquina.getAdjacencyMatrix()[0].length);
		String[] estadosViejos = maquina.getStates();
		retorno.setStates(nuevosEstados);
		String[][] adya = maquina.getAdjacencyMatrix();
		for (int i = 0; i < adya.length; i++) {
			for (int j = 0; j < adya[0].length; j++) {
				adya[i][j] = nuevosEstados[getIndex(adya[i][j], estadosViejos)];
			}
		}
		retorno.setAdyacencyMatrix(adya);
		retorno.setOutputs(maquina.getOutputs());
		return retorno;
	}
	/**
	 * Metodo que retorna una maquinaMealy con estados renombrados , habiendo asi nombre de estados totalmente diferentes dependiendo de la lista
	 * @param maquina maquinaMealy que desea ser renombrada 
	 * @param nuevosEstados indica la lista de estados renombrados 
	 * @return MaquinaMealy que tiene los estados renombrados apartir de la lista
	 */
	public MaquinaMealy nuevaMaquinaRenombrada(MaquinaMealy maquina, String[] nuevosEstados) {
		MaquinaMealy retorno = new MaquinaMealy(maquina.getStates().length, maquina.getAdyacencyMatrix()[0].length);
		String[] estadosViejos = maquina.getStates();
		retorno.setStates(nuevosEstados);
		String[][] adya = maquina.getAdyacencyMatrix();
		for (int i = 0; i < adya.length; i++) {
			for (int j = 0; j < adya[0].length; j++) {
				adya[i][j] = nuevosEstados[getIndex(adya[i][j].charAt(0) + "", estadosViejos)] + adya[i][j].charAt(1);
			}
		}
		retorno.setAdyacencyMatrix(adya);
		retorno.setOutputs(maquina.getOutputs());
		return retorno;
	}
	/**
	 * Metodo auxiliar que indica el index donde se encuentra en un elemento de tipo String en una lista
	 * @param estado es el String que se desea buscar 
	 * @param estados indica la lista donde se desea buscar
	 * @return retornar un int indicando la posicion del arreglo, retornaria -1 en caso de que no se encuentre en la lista
	 */
	public int getIndex(String estado, String[] estados) {
		for (int i = 0; i < estados.length; i++) {
			if (estados[i].equals(estado))
				return i;
		}
		return -1;
	}
	/**
	 * Metodo que retornna una maquinaMealy concatenada apartir de dos maquinas recibidas por parametro
	 * @param a parametro del objeto tipo maquinaMealy que indica la primera maquina a sumar 
	 * @param b parametro del objeto tipo maquinaMealy que indica la segunda maquina a sumar 
	 * @return MaquinaMealy que indica la nueva maquina teniendo las dos maquinas concatenadas.
	 */
	public MaquinaMealy sumaDirectaMealy(MaquinaMealy a, MaquinaMealy b) {
		MaquinaMealy retorno= new MaquinaMealy(a.getStates().length + b.getStates().length, a.getAdyacencyMatrix()[0].length);
		String[][] alist= a.getAdyacencyMatrix(); 
		String[][] blist= b.getAdyacencyMatrix();
		String[][] adya= new String[a.getStates().length + b.getStates().length][a.getAdyacencyMatrix()[0].length];
		String[] estados= concatenarListas(a.getStates(), b.getStates());
		int k=0;
		
		if(blist.length== 0 || blist[0].length== 0) return a;
		if(alist.length== 0 || alist[0].length== 0) return a;
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
		retorno.setAdyacencyMatrix(adya);
		retorno.setStates(estados);
		return retorno;
	}
	/**
	 * Metodo que retornna una maquinaMoore concatenada apartir de dos maquinas recibidas por parametro
	 * @param a parametro del objeto tipo maquinaMoore que indica la primera maquina a sumar 
	 * @param b parametro del objeto tipo maquinaMoore que indica la segunda maquina a sumar 
	 * @return maquinaMoore que indica la nueva maquina teniendo las dos maquinas concatenadas.
	 */
	public MaquinaMoore sumaDirectaMoore(MaquinaMoore a, MaquinaMoore b) {
		MaquinaMoore retorno= new MaquinaMoore(a.getStates().length + b.getStates().length, a.getAdjacencyMatrix()[0].length);
		String[][] alist= a.getAdjacencyMatrix(); 
		String[][] blist= b.getAdjacencyMatrix();
		String[][] adya= new String[a.getStates().length + b.getStates().length][a.getAdjacencyMatrix()[0].length];
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
		retorno.setAdyacencyMatrix(adya);
		retorno.setStates(estados);
		retorno.setOutputs(salidas);
		return retorno;
	}
	/**
	 * Metodo auxiliar que concatena dos listas apartir de dos recibidas por parametro 
	 * @param a es primera lista a concatenar
	 * @param b es la segunda lista a concatenar
	 * @return una lista de tamano a.length + b.length , donde se encuentran la lista a y b concatenadas.
	 */
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
	
	/**
	 * Metodo que retorna las particiones finales apartir de una maquina mealy
	 * @param maquina la maquina a la cual se le desea sacar las particiones
	 * @return una lista de listas de tipo ArrayList, indicando los conjuntos y subConjuntos de cada particion
	 */
	public ArrayList<ArrayList<String>> particionesMealy(MaquinaMealy maquina){
		ArrayList<ArrayList<String>> partitions= new ArrayList<ArrayList<String>>();
		partitions = firstPartitionMealy(maquina);
		
		ArrayList<ArrayList<String>> partitions1 = clonarPartition(partitions);
		ArrayList<ArrayList<String>> bpartition= new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<ArrayList<String>>> newPart = new ArrayList<ArrayList<ArrayList<String>>> ();
		boolean done = false;
		int x = 0;
		while (!done) {
			bpartition = partitions;
			x++;
			for (int i = 0; i < partitions.size(); i++) {
				ArrayList<String> temp = partitions.get(i);
				ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
				//"Problema esta aca en partitions"
				result = partitionKplus1Mealy(temp, partitions1, maquina);
				if(result.size() == 0) {
					result.add(temp);
				}
				//Problema esta aca
				newPart.add(result);
			}
			partitions = newPartition(newPart);
			
			newPart = new ArrayList<ArrayList<ArrayList<String>>> ();
			
			done = finish(bpartition, partitions);
		}
		return partitions;
	}
	/**
	 * Método para generar la primera partición de la máquina de Mealy
	 * @param m máquina de Moore a la que se le realizara la primera partición
	 * @return ArrayList que contiene ArrayList<String> los cuales cada uno representan un conjunto de estados que tienen el mismo output.
	 */
	public ArrayList<ArrayList<String>> firstPartitionMealy(MaquinaMealy maquina){
	ArrayList<ArrayList<String>> partitions= new ArrayList<ArrayList<String>>();
	
   DisJoinSet conjunto=new DisJoinSet(maquina.getStates().length);
   
   String[][] m= maquina.getAdyacencyMatrix();
   
	for (int i = 0; i < m.length; i++) {
		String[] line = new String[m[0].length];

		for (int j = 0; j < m[0].length; j++) {
			line[j] = m[i][j].charAt(m[i][j].length() - 1) + "";
		}

		for (int k = 0; k < m.length; k++) {
			int t= 0;
			for (int j = 0; j < m[0].length; j++) {
				if(  (m[k][j].charAt(m[k][j].length() - 1) + "").equals(line[j])) {
					t++;
				}
			}
			if (t==line.length) {
				conjunto.union(i, k);
			}
		}
	}
	ArrayList<Integer> numPadres= new ArrayList<Integer>(); 
	String estados[] =maquina.getStates();
	for (int i = 0; i < m.length; i++) {
		if(!numPadres.contains(conjunto.find(i))) numPadres.add(conjunto.find(i));
	}
	
	for (int i = 0; i < numPadres.size(); i++) {
		ArrayList<String> nueva= new ArrayList<>();
		
		for (int j = 0; j < m.length; j++) {
			
			if(conjunto.find(j)== numPadres.get(i)) {
				nueva.add(estados[j]);
			}
		}
		partitions.add(nueva);
	}
	return partitions;
  }
	/**
	 * Método para realizar las particiones que iran después de la primera partición.
	 * @param firstSet conjunto de estados donde se evaluara la equivalencia de esos estados.
	 * @param otherSets ArrayList que contiene todos los conjuntos de dicha partición.
	 * @param m Máquina de Mealy la cual se le esta realizando la partición.
	 * @return ArrayList representando la partición del firstSet
	 */
	public ArrayList<ArrayList<String>> partitionKplus1Mealy (ArrayList<String> firstSet, ArrayList<ArrayList<String>> otherSets, MaquinaMealy m){
	int size = firstSet.size();
	ArrayList<ArrayList<String>> salida = new ArrayList<ArrayList<String>>();
	ArrayList<String> noEquivalente = new ArrayList<String>();
	  if(size > 2) {
		  String x = firstSet.get(0);
		  for (int i = 1; i < size; i++) {
			  String y = firstSet.get(i);
			  if(!areEquivalentMealy(otherSets,x,y,m)) {
				  noEquivalente.add(y);
			  }  
		  }
		  if(noEquivalente.size() != 0) {
			  firstSet = borrarNoEquivalente(firstSet, noEquivalente);
			  salida.add(firstSet);
			  salida.add(noEquivalente);			  
		  }
	  }
	  return salida;
	}
	/**
	 * Método que determina si dos estados son equivalentes entre si.
	 * @param otherSets ArrayList que contiene todos los conjuntos de dicha partición.
	 * @param x Primer estado que se desea evaluar
	 * @param y Segundo estado que se desea evaluar
	 * @param m Máquina de Mealy a la que se le esta realizando el proceso de partición.
	 * @return un valor booleano que determina si X y Y son equivalentes. 
	 */
		public boolean areEquivalentMealy (ArrayList<ArrayList<String>> otherSets, String x, String y, MaquinaMealy m) {
		boolean equivalent = false;
		int l = 0;
			equivalent = auxAreEquivalentMealy(otherSets, x, y, m, l);
		return equivalent;
	}
		/**
		 * Método auxiliar para determinar si dos estados son equivalentes entre si.
		 * @param otherSets ArrayList que contiene todos los conjuntos de dicha partición.
	     * @param x Primer estado que se desea evaluar
		 * @param y Segundo estado que se desea evaluar
		 * @param m Máquina de Mealy a la que se le esta realizando el proceso de partición.
		 * @return un valor booleano que determina si X y Y son equivalentes. 
		 */
		public boolean auxAreEquivalentMealy(ArrayList<ArrayList<String>> otherSets, String x , String y , MaquinaMealy m, int n) {
		
		String[][] matrix = m.getAdyacencyMatrix();
		int x1 = getIndex(x, m.getStates());
		int y1 = getIndex(y, m.getStates());
		
		boolean equivalent = true;
		boolean stop = false;
			for (int j = 0; j < matrix[0].length && equivalent; j++) {
				String value1 = matrix[x1][j];
				String value2 = matrix[y1][j];
				for (int i = 0; i < otherSets.size() && !stop; i++) {
					ArrayList<String> set = otherSets.get(i);
					boolean fx = containsMealy(set, value1);
					boolean fy = containsMealy(set, value2);
					if(fx == true && fy == true) {
						stop = true;
						equivalent = true;
					}
					else if ((fx == true && fy == false) || (fy == true && fx == false) ) {
						equivalent = false;
						stop = true;
					}
				}
				if(j < matrix[0].length) stop = false;
				
			}
			
		 return equivalent;
		}
	
	
	/**
	 * Método que genera las particiones de estados para la máquina Moore
	 * @param maquina Máquina la cual se le desea realizar el proceso de partición
	 * @return Un ArrayList que contiene varios ArrayList<String> los cuales cada uno representa un conjunto con estados equivalentes.
	 */
	public ArrayList<ArrayList<String>> particionesMoore(MaquinaMoore maquina){
		ArrayList<ArrayList<String>> partitions= new ArrayList<ArrayList<String>>();
		partitions = firstPartitionMoore(maquina);
		ArrayList<ArrayList<String>> partitions1 = clonarPartition(partitions);
		ArrayList<ArrayList<String>> bpartition= new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<ArrayList<String>>> newPart = new ArrayList<ArrayList<ArrayList<String>>> ();
		boolean done = false;
		int x = 0;
		while (!done) {
			bpartition = partitions;
			x++;
			for (int i = 0; i < partitions.size(); i++) {
				ArrayList<String> temp = partitions.get(i);
				ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
				result = partitionKplus1(temp, partitions1, maquina);
				if(result.size() == 0) {
					result.add(temp);
				}
				newPart.add(result);
			}
			partitions = newPartition(newPart);
			
			newPart = new ArrayList<ArrayList<ArrayList<String>>> ();
			
			done = finish(bpartition, partitions);
		}
		return partitions;
	}
	/**
	 * Método para crear una copia de la última partición. Esta será usada para hacer el proceso de generar
	 * una nueva partición.
	 * @param partitions última partición generada
	 * @return una copia de la última partición generada.
	 */
	private ArrayList<ArrayList<String>> clonarPartition(ArrayList<ArrayList<String>> partitions) {
		ArrayList<ArrayList<String>> p1 = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < partitions.size(); i++) {
			p1.add(partitions.get(i));
		}
		return p1;
	}
	/**
	 * Método para generar la primera partición de la máquina de Moore
	 * @param m máquina de Moore a la que se le realizara la primera partición
	 * @return ArrayList que contiene ArrayList<String> los cuales cada uno representan un conjunto de estados que tienen el mismo output.
	 */
	public ArrayList<ArrayList<String>> firstPartitionMoore (MaquinaMoore m){
		String[] states = m.getStates();
		String[] output = m.getOutputs();
		ArrayList<String> outputs = new ArrayList<String>();
		ArrayList<ArrayList<String>> salida = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < output.length; i++) {
			if(!contains(outputs, output[i])) {
				outputs.add(output[i]);
			} 
		}
	  for (int i = 0; i < outputs.size(); i++) {
		  ArrayList<String> nueva = auxFirstPartition(states, output, outputs.get(i));
		  salida.add(nueva);
	   }
	  return salida;	
	}
	/**
	 * Método auxiliar que permite realizar la primera partición de una máquina de moore.
	 * @param states Arreglo de estados de la máquina de Moore
	 * @param output Arreglo de salidas de la máquina de Moore
	 * @param i Salida que se va a evaluar para determinar que estados la proveen.
	 * @return ArrayList<String> que contiene los estados que proveen esa salida.
	 */
	public ArrayList<String> auxFirstPartition(String[] states,String[] output, String i){
		ArrayList<String> salida = new ArrayList<String>();
		for (int j = 0; j < output.length; j++) {
			if(output[j].equals(i)) {
				salida.add(states[j]);
			}
		}
		return salida;
	}
	/**
	 * Método para realizar las particiones que iran después de la primera partición.
	 * @param firstSet conjunto de estados donde se evaluara la equivalencia de esos estados.
	 * @param otherSets ArrayList que contiene todos los conjuntos de dicha partición.
	 * @param m Máquina de Moore la cual se le esta realizando la partición.
	 * @return ArrayList representando la partición del firstSet
	 */
	public ArrayList<ArrayList<String>> partitionKplus1 (ArrayList<String> firstSet, ArrayList<ArrayList<String>> otherSets, MaquinaMoore m){
		int size = firstSet.size();
		ArrayList<ArrayList<String>> salida = new ArrayList<ArrayList<String>>();
		ArrayList<String> noEquivalente = new ArrayList<String>();
	  if(size > 2) {
		  String x = firstSet.get(0);
		  for (int i = 1; i < size; i++) {
			  String y = firstSet.get(i);
			  if(!areEquivalent(otherSets,x,y,m)) {
				  noEquivalente.add(y);
			  }  
		  }
		  if(noEquivalente.size() != 0) {
			  firstSet = borrarNoEquivalente(firstSet, noEquivalente);
			  salida.add(firstSet);
			  salida.add(noEquivalente);			  
		  }
	  }
	  return salida;
	}
	/**
	 * Método para borrar los estados no equivalentes de un conjunto.
	 * @param f Conjunto de estados que se le desea borrar los estados no equivalentes.
	 * @param n Conjunto de estados que no son equivalentes dentro de f
	 * @return Conjunto de estados en f que si son equivalentes.
	 */
	public ArrayList<String> borrarNoEquivalente(ArrayList<String> f , ArrayList<String> n){
		for (int i = 0; i < n.size(); i++) {
			if(contains(f, n.get(i))) {
				int r = f.indexOf(n.get(i));
				f.remove(r);
			}
		}
		return f;
	}
	/**
	 * Método que determina si dos estados son equivalentes entre si.
	 * @param otherSets ArrayList que contiene todos los conjuntos de dicha partición.
	 * @param x Primer estado que se desea evaluar
	 * @param y Segundo estado que se desea evaluar
	 * @param m Máquina de Moore a la que se le esta realizando el proceso de partición.
	 * @return un valor booleano que determina si X y Y son equivalentes. 
	 */
	public boolean areEquivalent (ArrayList<ArrayList<String>> otherSets, String x, String y, MaquinaMoore m) {
		boolean equivalent = false;
			equivalent = auxAreEquivalent(otherSets, x, y, m);
		return equivalent;
	}
	/**
	 * Método auxiliar para determinar si dos estados son equivalentes entre si.
	 * @param otherSets ArrayList que contiene todos los conjuntos de dicha partición.
     * @param x Primer estado que se desea evaluar
	 * @param y Segundo estado que se desea evaluar
	 * @param m Máquina de Moore a la que se le esta realizando el proceso de partición.
	 * @return un valor booleano que determina si X y Y son equivalentes. 
	 */
	public boolean auxAreEquivalent(ArrayList<ArrayList<String>> otherSets, String x , String y , MaquinaMoore m) {
		
		String[][] matrix = m.getAdjacencyMatrix();
		int x1 = m.getIndex(x);
		int y1 = m.getIndex(y);
		
		boolean equivalent = true;
		boolean stop = false;
			for (int j = 0; j < matrix[0].length && equivalent; j++) {
				String value1 = matrix[x1][j];
				String value2 = matrix[y1][j];
				for (int i = 0; i < otherSets.size() && !stop; i++) {
					ArrayList<String> set = otherSets.get(i);
					boolean fx = contains(set, value1);
					boolean fy = contains(set, value2);
					if(fx == true && fy == true) {
						stop = true;
						equivalent = true;
					}
					else if ((fx == true && fy == false) || (fy == true && fx == false) ) {
						equivalent = false;
						stop = true;
					}
				}
				if(j < matrix[0].length) stop = false;
				
			}
			
		 return equivalent;
		}
	
	/**
	 * Método que determina si un estado esta contenido en un conjunto.
	 * @param n Conjunto de estados
	 * @param out Estado que se desea evaluar.
	 * @return valor booleano que define si el estado se encuentra contenido en ese conjunto.
	 */
	
	public boolean contains(ArrayList<String> n, String out) {
		if(n.size() == 0) {
			return false;
		}
		else {
			boolean exist = false;
			for (int i = 0; i < n.size() && !exist; i++) {
				if(n.get(i).equals(out)) {
					exist = true;
				}
			}
		 return exist;
		}
	}
	/**
	 * Método que determina si un estado esta contenido en un conjunto con base a las reglas de las máquinas Mealy.
	 * @param n Conjunto de estados
	 * @param out Estado que se desea evaluar.
	 * @return valor booleano que define si el estado se encuentra contenido en ese conjunto.
	 */
	public boolean containsMealy(ArrayList<String> n, String out) {
		if(n.size() == 0) {
			return false;
		}
		else {
			boolean exist = false;
			for (int i = 0; i < n.size() && !exist; i++) {
				if(n.get(i).equals(""+out.charAt(0))){
					exist = true;
				}
			}
		 return exist;
		}
	}
	/**
	 * Método que determina si dos conjuntos son iguales entre si.
	 * @param one Primer conjunto a evaluar
	 * @param two Segundo conjunto a evaluar
	 * @return valor booleano que determina si los dos conjuntos son iguales.
	 */
	public boolean verifyDifferentSets(ArrayList<String> one, ArrayList<String> two) {
		boolean dif = false;
		if(one.size() != two.size()) {
			dif = true;
		}
		else {
			for (int i = 0; i < one.size() && !dif; i++) {
				if(!contains(two, one.get(i))) {
					dif = true;
				}
			}
		}
		
		return dif;
	}
	/**
	 * Método que agrupa todas las particiones generadas en cada conjunto de la partición anterior, para formar la nueva partición.
	 * @param u Arreglo con todas las particiones generadas en cada conjunto de la partición anterior.
	 * @return ArrayList que contiene ArrayList<String> que representan la nueva participn.
	 */
	public ArrayList<ArrayList<String>> newPartition(ArrayList<ArrayList<ArrayList<String>>> u){
		ArrayList<ArrayList<String>> newP = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < u.size(); i++) {
			int size = u.get(i).size();
			for (int j = 0; j < size; j++) {
				ArrayList<String> temp = new ArrayList<String>();
				for (int k = 0; k < u.get(i).get(j).size(); k++) {
					temp.add(u.get(i).get(j).get(k));
				}
				newP.add(temp);
			}		
		}
		return newP;
	}
	/**
	 * Método que determina si el proceso de partición acabo.
	 * @param p Última partición generada
	 * @param bp Penúltima partición generada
	 * @return valor booleano que determina si p y bp son iguales.
	 */
	public boolean finish(ArrayList<ArrayList<String>> p, ArrayList<ArrayList<String>>bp) {
		if(p.size() != bp.size()) {
			return false;
		}
		else {
			boolean stop = false;
			for (int i = 0; i < p.size() && !stop; i++) {
				if(verifyDifferentSets(p.get(i), bp.get(i))) stop = true;
			}
			
			if(!stop) return true;
			return false;
		}
	}
	/**
	 * Método para generar un mensaje con la estructura de una partición.
	 * @param p ArrayList que representa la partición.
	 * @return Un String que representa la estructura de la partición.
	 */
	public String reportPartition (ArrayList<ArrayList<String>> p) {
		String mensaje = "{ ";
		for (int i = 0; i < p.size(); i++) {
			if(i == p.size()-1) {
				mensaje +=  p.get(i) + " }";
			}
			else {				
				mensaje += auxReportPartition(p.get(i)) + "; ";
			}
		}
		return mensaje;
	} 
	/**
	 * Método auxiliar para generar el mensaje con la estructura de la partición.
	 * @param n ArrayList<String> que representa uno de los conjuntos dentro de la partición.
	 * @return Un mensaje con la estructura del conjunto.
	 */
	public String auxReportPartition (ArrayList<String> n) {
		String mensaje = "{ ";
		for (int i = 0; i < n.size(); i++) {
			if(i == n.size()-1) {
				mensaje += n.get(i) + " }";
			}
			else {
				mensaje += n.get(i) + ", ";			
			}
			
		}
		return mensaje;
	}
	/**
	 * Método que se encarga de determinar si las dos máquinas son equivalentes.
	 * @param stateOne Estado inicial de la máquina 1
	 * @param stateTwo Estado inicial de la máquina 2
	 * @param lastPartition Ultima partición generada
	 * @return Valor booleano que determina si las dos máquinas son equivalentes.
	 */
	
	public boolean equivalentMachines (String stateOne , String stateTwo, ArrayList<ArrayList<String>> lastPartition) {
		ArrayList<String> first = lastPartition.get(0);
		boolean one = contains(first, stateOne);
		boolean two = contains(first, stateTwo);
		return one && two;
	}
	/**
	 * Método que retorna el valor boolean de si las dos máquinas ingresadas son equivalentes.
	 * @return Valor booleano que determina si las dos máquinas son equivalentes
	 */
	public boolean isEquivalent() {
		return equivalent;
	}

	/**
	 * Método que modifica el valor booleano de si las dos máquinas ingresadas son equivalentes
	 * @param equivalent Valor booleano con el que se modificara el valor de si las dos máquinas son equivalentes.
	 */
	public void setEquivalent(boolean equivalent) {
		this.equivalent = equivalent;
	}
	
}