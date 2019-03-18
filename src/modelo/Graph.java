package modelo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Graph {
	/**
	 * indica el vertice inicial
	 */
	private int V; 
	/**
	 * LinkedList que indica los aristas entre las diferentes aristas
	 */
	private LinkedList<String> adj[]; 
	/**
	 * Lista de vertices del grafo
	 */
	private List<String> index;

	/**
	 * Contructor del grafo, define el vertice junto con los estados
	 * @param v el vertice o raiz del grafo
	 * @param states los estados que contiene el grafo
	 */
	@SuppressWarnings("unchecked")
	Graph(int v, String[] states) {
		V = v;
		index= new LinkedList<String>();
		
		adj = new LinkedList[v];
		for (int i = 0; i < v; ++i) {
			adj[i] = new LinkedList<>();
		}
		for (int i = 0; i < states.length; i++) {
			index.add(states[i]);
		}
	}
	/**
	 * Metodo que agrega una arista entre dos vertices
	 * @param v arista de origen
	 * @param w arista de destino
	 */
	public void addEdge(String v, String w) {
		adj[index.indexOf(v)].add(w);
	}
	/**
	 * metodo BFS apartir del grafo estructurado 
	 * @param s el vertice inicial en el cual se va realizar BFS
	 * @return un String donde se retorna el camino BFS que toma apartir de un vertice por parametro, cada vertice separado por un espacio.
	 */
	public String BFS(String s) {
		String retorno="";
		boolean visited[] = new boolean[V];

		LinkedList<String> queue = new LinkedList<String>();

		visited[index.indexOf(s)] = true;
		queue.add(s);

		while (queue.size() != 0) {
			s = queue.poll();
			retorno+=s + " ";
			Iterator<String> i = adj[index.indexOf(s)].listIterator();
			while (i.hasNext()) {
				String n = i.next();
				if (!visited[index.indexOf(n)]) {
					visited[index.indexOf(n)] = true;
					queue.add(n);
				}
			}
		}
		return retorno;
	}

}
