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
import dataStructures.queue.LinkedQueue;
import dataStructures.queue.Queue;
import dataStructures.set.HashSet;
import dataStructures.set.Set;

public class TopologicalSortingDic<V> {

    private final List<V> topSort;
    private boolean hasCycle;

    public TopologicalSortingDic(DiGraph<V> graph) {
        // TODO
        hasCycle = false;
        topSort = new ArrayList<>();

        Dictionary<V, Integer> dict = new HashDictionary<>();
        Set<V> vertices = graph.vertices();

        for(V vert : vertices){
            dict.insert(vert, graph.inDegree(vert));
        }

        while(!hasCycle && !dict.isEmpty()){
            Set<V> fuentes = new HashSet<>();
            for(V verti : dict.keys()){
                if(dict.valueOf(verti) == 0){
                    fuentes.insert(verti);
                }
            }

            if(!fuentes.isEmpty()){
                for(V verti : fuentes){
                    topSort.append(verti);
                    for(V verts : graph.successors(verti)){
                        int value = dict.valueOf(verts);
                        dict.delete(verts);
                        dict.insert(verts, value-1);
                        }
                    dict.delete(verti);
                }

            } else {
                hasCycle = true;
            }
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public List<V> order() {
        return hasCycle ? null : topSort;
    }
}
