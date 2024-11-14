/**
 * Estructuras de Datos
 * Práctica 9 - Orden topológico en digrafos (sin clonar el grafo)
 */

package topsort;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.graph.DiGraph;
import dataStructures.list.ArrayList;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.set.HashSet;
import dataStructures.set.Set;

public class TopologicalSortingDicPar<V> {

    private final List<Set<V>> topSort;
    private boolean hasCycle;

    public TopologicalSortingDicPar(DiGraph<V> graph) {
        // TODO
        topSort = new ArrayList<>();
        hasCycle = false;

        Dictionary<V, Integer> dict = new HashDictionary<>();
        Set<V> vertices = graph.vertices();

        for(V vert : vertices){
            dict.insert(vert, graph.inDegree(vert));
        }

        while(!hasCycle && !dict.isEmpty()){
            Set<V> fuentes = new HashSet<>();
            for(V vert : dict.keys()){
                if(dict.valueOf(vert) == 0){
                    fuentes.insert(vert);
                }
            }

            if(!fuentes.isEmpty()){
                Set<V> conj = new HashSet<>();
                for(V vert : fuentes){
                    conj.insert(vert);
                    for(V verts : graph.successors(vert)) {
                        int value = dict.valueOf(verts);
                        dict.delete(verts);
                        dict.insert(verts, value - 1);
                    }
                    dict.delete(vert);
                }
                topSort.append(conj);
            }
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public List<Set<V>> order() {
        return hasCycle ? null : topSort;
    }
}
