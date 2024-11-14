/**
 * Student's name:
 * Student's group:
 *
 * Data Structures. Grado en Informática. UMA.
 */

package dataStructures.graph;

import dataStructures.list.*;
import dataStructures.set.HashSet;
import dataStructures.set.Set;

import java.util.Iterator;

public class EulerianCycle<V> {
    private List<V> eCycle;

    @SuppressWarnings("unchecked")
    public EulerianCycle(Graph<V> g) {
        Graph<V> graph = (Graph<V>) g.clone();
        eCycle = eulerianCycle(graph);
    }

    public boolean isEulerian() {
        return eCycle != null;
    }

    public List<V> eulerianCycle() {
        return eCycle;
    }

    // J.1
    private static <V> boolean isEulerian(Graph<V> g) {
        // TO DO
        boolean res = false;
        Set<V> vertices = new HashSet<>();
        for(V vert : g.vertices()){
            if(g.degree(vert) % 2 == 0){
                vertices.insert(vert);
            }
        }
        return vertices.size() == g.numVertices();
    }

    // J.2
    private static <V> void remove(Graph<V> g, V v, V u) {
        // TO DO
        g.deleteEdge(v, u);
        for(V vert : g.vertices()){
            if(g.degree(vert) == 0){
                g.deleteVertex(vert);
            }
        }
    }

    // J.3
    private static <V> List<V> extractCycle(Graph<V> g, V v0) {
        // TO DO
        List<V> ciclo = new ArrayList<>();
        Set<V> sucesores = g.successors(v0);
        ciclo.append(v0);
        V sucesor = sucesores.iterator().next();
        V v = v0;
        while(!sucesor.equals(v0)){
            ciclo.append(sucesor);
            remove(g, v, sucesor);
            v = sucesor;
            sucesor = g.successors(v).iterator().next();
        }
        return ciclo;
    }

    // J.4
    private static <V> void connectCycles(List<V> xs, List<V> ys) {
        if(xs.isEmpty()){
            xs = ys;
        }else{
            List<V> lista = new ArrayList<>();
            Iterator<V> it = xs.iterator();
            while(it.hasNext()){
                V v = it.next();
                lista.append(v);
                if(v==ys.get(0)){
                    for(V y : ys){
                        lista.append(y);
                    }
                }
            }
            xs = lista;
        }
    }

    // J.5
    private static <V> V vertexInCommon(Graph<V> g, List<V> cycle) {
    		// TO DO
            Iterator<V> it = cycle.iterator();
            V vert = null;

            while(it.hasNext()){
                V v= it.next();
                if(g.vertices().isElem(v)){
                    vert = v;
                }
            }
    		return vert;
    }

    // J.6
    private static <V> List<V> eulerianCycle(Graph<V> g) {
    		// TO DO
        if(!isEulerian(g)){
            System.out.println("Grafo no euleriano");
            return null;
        } else {
            V vert = g.vertices().iterator().next();
            List<V> ciclo = extractCycle(g, vert);
            while(!g.isEmpty()){
                V u = vertexInCommon(g, ciclo);
                List<V> aux = extractCycle(g, u);
                connectCycles(ciclo, aux);
            }
            return ciclo;
        }
    }
}
