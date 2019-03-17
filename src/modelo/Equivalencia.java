package modelo;

import java.util.ArrayList;
import java.util.*;
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
		// Prueba particiones 
		
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
		
		particionesMealy(m3Suma);
		
		
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
		//Paso 4
		particionesMoore(o3Suma);
		
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
	
	
	public ArrayList<ArrayList<String>> particionesMealy(MaquinaMealy maquina){
		ArrayList<ArrayList<String>> partitions= new ArrayList<ArrayList<String>>();
		partitions = firstPartitionMealy(maquina);
		System.out.println(reportPartition(partitions));
		
		ArrayList<ArrayList<String>> partitions1 = clonarPartition(partitions);
		System.out.println("First:\n" +reportPartition(partitions));
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
				 System.out.println("X:"+ reportPartition(result));
				if(result.size() == 0) {
					result.add(temp);
				}
				//Problema esta aca
				newPart.add(result);
			}
			partitions = newPartition(newPart);
			System.out.println("Before:\n" +reportPartition(bpartition));
			 System.out.println("m:"+ reportPartition(partitions));
			
			newPart = new ArrayList<ArrayList<ArrayList<String>>> ();
			
			done = finish(bpartition, partitions);
		}
		return partitions;
	}
	
	public ArrayList<ArrayList<String>> firstPartitionMealy(MaquinaMealy maquina){
	ArrayList<ArrayList<String>> partitions= new ArrayList<ArrayList<String>>();
	
   DisJoinSet conjunto=new DisJoinSet(maquina.getStates().length);
   
   String[][] m= maquina.getIncidenceMatrix();
   
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
	public ArrayList<ArrayList<String>> partitionKplus1Mealy (ArrayList<String> firstSet, ArrayList<ArrayList<String>> otherSets, MaquinaMealy m){
	int size = firstSet.size();
	System.out.println("OtherSets: " + otherSets.size());
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
		public boolean areEquivalentMealy (ArrayList<ArrayList<String>> otherSets, String x, String y, MaquinaMealy m) {
		boolean equivalent = false;
		int l = 0;
		System.out.println("Other Sets: " + otherSets.size());
			equivalent = auxAreEquivalentMealy(otherSets, x, y, m, l);
		return equivalent;
	}
		public boolean auxAreEquivalentMealy(ArrayList<ArrayList<String>> otherSets, String x , String y , MaquinaMealy m, int n) {
		
		String[][] matrix = m.getIncidenceMatrix();
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
	
	//--------------------------------------------------------------------------------------------
	
	public ArrayList<ArrayList<String>> particionesMoore(MaquinaMoore maquina){
		ArrayList<ArrayList<String>> partitions= new ArrayList<ArrayList<String>>();
		partitions = firstPartition(maquina);
		ArrayList<ArrayList<String>> partitions1 = clonarPartition(partitions);
		System.out.println("First:\n" +reportPartition(partitions));
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
				 System.out.println("X:"+ reportPartition(result));
				if(result.size() == 0) {
					result.add(temp);
				}
				newPart.add(result);
			}
			partitions = newPartition(newPart);
			System.out.println("Before:\n" +reportPartition(bpartition));
			 System.out.println("m:"+ reportPartition(partitions));
			
			newPart = new ArrayList<ArrayList<ArrayList<String>>> ();
			
			done = finish(bpartition, partitions);
		}
		return partitions;
	}
	
	private ArrayList<ArrayList<String>> clonarPartition(ArrayList<ArrayList<String>> partitions) {
		ArrayList<ArrayList<String>> p1 = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < partitions.size(); i++) {
			p1.add(partitions.get(i));
		}
		return p1;
	}

	public ArrayList<ArrayList<String>> firstPartition (MaquinaMoore m){
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
	public ArrayList<String> auxFirstPartition(String[] states,String[] output, String i){
		ArrayList<String> salida = new ArrayList<String>();
		for (int j = 0; j < output.length; j++) {
			if(output[j].equals(i)) {
				salida.add(states[j]);
			}
		}
		return salida;
	}
	
	public ArrayList<ArrayList<String>> partitionKplus1 (ArrayList<String> firstSet, ArrayList<ArrayList<String>> otherSets, MaquinaMoore m){
		int size = firstSet.size();
		System.out.println("OtherSets: " + otherSets.size());
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
	public ArrayList<String> borrarNoEquivalente(ArrayList<String> f , ArrayList<String> n){
		for (int i = 0; i < n.size(); i++) {
			if(contains(f, n.get(i))) {
				int r = f.indexOf(n.get(i));
				f.remove(r);
			}
		}
		return f;
	}
	public boolean areEquivalent (ArrayList<ArrayList<String>> otherSets, String x, String y, MaquinaMoore m) {
		boolean equivalent = false;
		int l = 0;
		System.out.println("Other Sets: " + otherSets.size());
			equivalent = auxAreEquivalent(otherSets, x, y, m, l);
		return equivalent;
	}
	public boolean auxAreEquivalent(ArrayList<ArrayList<String>> otherSets, String x , String y , MaquinaMoore m, int n) {
		
		String[][] matrix = m.getIncidenceMatrix();
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
	
	public ArrayList<ArrayList<String>> getDifferentSets(ArrayList<String> n, ArrayList<ArrayList<String>> u){
		ArrayList<ArrayList<String>> salida = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < salida.size(); i++) {
			ArrayList<String> dif = salida.get(i);
			if(verifyDifferentSets(n, dif)) {
				salida.add(dif);
			}
		}
		return salida;
	}
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
}