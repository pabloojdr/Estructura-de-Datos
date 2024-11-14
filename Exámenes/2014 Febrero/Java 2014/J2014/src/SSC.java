import java.util.Dictionary;
import java.util.Iterator;

import dataStructures.dictionary.HashDictionary;
import dataStructures.graph.DepthFirstTraversal;
import dataStructures.graph.DiGraph;
import dataStructures.graph.DictionaryDiGraph;
import dataStructures.set.HashSet;
import dataStructures.set.Set;

public class SSC {

	public static <V> DiGraph<V> reverseDiGraph(DiGraph<V> g){
		//TODO
		Set<V> vertices = g.vertices();
		DiGraph gf = new DictionaryDiGraph();
		for(V verti : vertices){
			gf.addVertex(verti);
		}
		for(V verti : vertices){
			for(V suc : g.successors(verti)){
				gf.addDiEdge(suc, verti);
			}
		}
		return gf;
	}
	
	public static <V> DiGraph<V> restrictDiGraph(DiGraph<V> g, Set<V> vs){
		//TODO
		DiGraph gf = new DictionaryDiGraph();
		for(V verti : vs){
			gf.addVertex(verti);
		}
		for(V verti : vs){
			for(V suc : g.successors(verti)){
				gf.addDiEdge(verti, suc);
			}
		}
		return gf;
	}
	
	public static <V> Set<V> sccOf (DiGraph<V> g, V src){
		//TODO
		DepthFirstTraversal gf = new DepthFirstTraversal(g, src);
		Set<V> vs = new HashSet<>();
		Iterator<V> it = gf.verticesIterator();
		while(it.hasNext()){
			vs.insert(it.next());
		}
		DiGraph fin = restrictDiGraph(g, vs);
		fin = reverseDiGraph(fin);


		return fin.vertices();
	}

	
	
	public static <V> Set<Set<V>> sscs(DiGraph<V> g){
		//TODO
		Set<V> vertices = g.vertices();
		Set<Set<V>> fin = new HashSet<>();
		for(V vert : vertices){
			fin.insert(sccOf(g, vert));
		}
		return fin;
	}
	



	
	public static void main (String args[]) {
		DiGraph<Character> dg = new DictionaryDiGraph<>();
		dg.addVertex('A');
		dg.addVertex('B');
		dg.addVertex('E');
		dg.addVertex('F');
		dg.addVertex('G');
		
		dg.addVertex('C');
		dg.addVertex('D');
		dg.addVertex('H');
		
		
		dg.addDiEdge('E', 'F');
		dg.addDiEdge('B', 'F');
		dg.addDiEdge('F', 'G');
		dg.addDiEdge('G', 'F');
		
		
		dg.addDiEdge('A', 'B');
		dg.addDiEdge('B', 'E');
		dg.addDiEdge('E', 'A');
		
		dg.addDiEdge('C', 'D');
		dg.addDiEdge('C', 'G');
		dg.addDiEdge('D', 'C');

		dg.addDiEdge('D', 'H');
		dg.addDiEdge('H', 'D');
		dg.addDiEdge('H', 'G');

		
		
		
		
		System.out.println(dg.toString());
		
		//System.out.println(reverseDiGraph(dg).toString());
		Set<Character> vs= new HashSet<>();
		vs.insert('A');
		vs.insert('B');
		vs.insert('E');
		
		
		/*DiGraph<Integer> dgg = new DictionaryDiGraph<>();
		
		dgg.addVertex(1);
		dgg.addVertex(2);
		dgg.addVertex(3);
		
		dgg.addDiEdge(1, 2);
		dgg.addDiEdge(3, 2);
		//dgg.addDiEdge('E', 'A');*/
		/*DepthFirstTraversal<Integer> df= new DepthFirstTraversal(dgg,1);
		Set<V> vers2=new HashSet<>();
		Iterator<V> it2= df.verticesIterator();
		
		while(it2.hasNext()) {
			vers2.insert(it2.next());
		}
		
		
		System.out.println(df.vertices().toString());*/
		
		
		//System.out.println(restrictDiGraph(dg,vs).toString());
		System.out.println(sscs(dg).toString());
		
		
	}
}
