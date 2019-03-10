package modelo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Graph {
	private int V; 
	private LinkedList<String> adj[]; 
	private List<String> index;

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

	public void addEdge(String v, String w) {
		adj[index.indexOf(v)].add(w);
	}

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
