/**----------------------------------------------
 * -- Estructuras de Datos.  2018/19
 * -- 2º Curso del Grado en Ingeniería [Informática | del Software | de Computadores].
 * -- Escuela Técnica Superior de Ingeniería en Informática. UMA
 * --
 * -- Examen 4 de febrero de 2019
 * --
 * -- ALUMNO/NAME:
 * -- GRADO/STUDIES:
 * -- NÚM. MÁQUINA/MACHINE NUMBER:
 * --
 * ----------------------------------------------
 */

import dataStructures.graph.WeightedGraph;
import dataStructures.graph.WeightedGraph.WeightedEdge;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.priorityQueue.LinkedPriorityQueue;
import dataStructures.set.Set;
import dataStructures.set.HashSet;

public class Kruskal {
	public static <V,W> Set<WeightedEdge<V,W>> kruskal(WeightedGraph<V,W> g) {
		// COMPLETAR
		Dictionary<V, V> dict = new HashDictionary<>();
		for(V v : g.vertices()){
			dict.insert(v, v);
		}
		PriorityQueue<WeightedEdge<V, W>> pq = new LinkedPriorityQueue<>();
		for(WeightedEdge<V, W> ed : g.edges()){
			pq.enqueue(ed);
		}
		Set<WeightedEdge<V, W>> T = new HashSet<>();

		while(!pq.isEmpty()){
			WeightedEdge<V, W> ari = pq.first();
			pq.dequeue();
			V org = ari.source();
			V dst = ari.destination();
			W w = ari.weight();

			if(!representante(dict, org).equals(representante(dict, dst))){
				dict.insert(representante(dict,dst), org);
				T.insert(ari);
			}
		}
		return T;
	}

	private static <V> V representante (Dictionary<V, V> dic, V v){
		if(dic.valueOf(v).equals(v)){
			return v;
		} else {
			V rep = representante(dic, dic.valueOf(v));
			return rep;
		}
	}

	// Sólo para evaluación continua / only for part time students
	public static <V,W> Set<Set<WeightedEdge<V,W>>> kruskals(WeightedGraph<V,W> g) {

		// COMPLETAR
		
		return null;
	}
}
