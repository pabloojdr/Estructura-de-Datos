/**
 * Student's name:
 *
 * Student's group:
 */

package dataStructures.graph;

import dataStructures.list.ArrayList;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.set.HashSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

public class FordFulkerson<V> {
    private WeightedDiGraph<V,Integer> g; // Initial graph 
    private List<WDiEdge<V,Integer>> sol; // List of edges representing maximal flow graph
    private V src; 			  // Source
    private V dst; 		  	  // Sink
	
    /**
     * Constructors and methods
     */

    public static <V> int maxFlowPath(List<WDiEdge<V,Integer>> path) {
        // TO DO
        int peso = Integer.MAX_VALUE; //infinito :P
        for(WDiEdge<V,Integer> ed : path){
            if(peso > ed.getWeight()){
                peso = ed.getWeight();
            }
        }
        return peso;
    }

    public static <V> List<WDiEdge<V,Integer>> updateEdge(V x, V y, Integer p, List<WDiEdge<V,Integer>> edges) {
        // TO DO
        List<WDiEdge<V, Integer>> lista = new ArrayList<>();

        for(WDiEdge<V, Integer> edge : edges){
            lista.append(edge);
        }

        int i = contains(x, y, edges);
        if( i >= 0){
            lista.insert(i, new WDiEdge<>(x, lista.get(i).getWeight() + p, y));
            if(lista.get(i).getWeight() == 0){
                lista.remove(i);
            }
        } else {
            lista.append(new WDiEdge<>(x, p, y));
        }
        return lista;
    }

    private static <V> int contains(V x, V y, List<WDiEdge<V, Integer>> edges){
        for(int i = 0; i < edges.size(); i++){
            if(x.equals(edges.get(i).getSrc()) && y.equals(edges.get(i).getDst())){
                return i;
            }
        }
        return -1;
    }

    public static <V> List<WDiEdge<V,Integer>> updateEdges(List<WDiEdge<V,Integer>> path, Integer p, List<WDiEdge<V,Integer>> edges) {
        // TO DO
        List<WDiEdge<V, Integer>> lista = new ArrayList<>();

        for(WDiEdge<V, Integer> edge : path){
           lista = updateEdge(edge.getSrc(), edge.getDst(), p, edges);
        }

        return lista;
    }

    public static <V> List<WDiEdge<V,Integer>> addFlow(V x, V y, Integer p, List<WDiEdge<V,Integer>> sol) {
        // TO DO
        List<WDiEdge<V, Integer>> lista = new ArrayList<>();
        for(WDiEdge<V, Integer> edge : sol){
            lista.append(edge);
        }

        int i = contains(x, y, lista);
        int j = contains(y, x, lista);

        if(i >= 0){
            lista = updateEdge(x, y, p+sol.get(i).getWeight(), lista);
        } else if(j >= 0){
            int w = lista.get(j).getWeight();
            if(w == p){
                lista.remove(j);
            } else if(w < p){
                lista.set(j, new WDiEdge<>(x, p-w, y));
            } else {
                lista.set(j, new WDiEdge<>(y, w-p, x));
            }
        } else if(i < 0){
            lista.append(new WDiEdge<>(x, p, y));
        }
        return lista;
    }

    public static <V> List<WDiEdge<V,Integer>> addFlows(List<WDiEdge<V,Integer>> path, Integer p, List<WDiEdge<V,Integer>> sol) {
        // TO DO
        List<WDiEdge<V, Integer>> lista = new ArrayList<>();
        for(WDiEdge<V, Integer> edge : path){
            lista = addFlow(edge.getSrc(), edge.getDst(), p, sol);
        }
        return lista;
    }

    public FordFulkerson(WeightedDiGraph<V,Integer> g, V src, V dst) {
        // TO DO
        List<WDiEdge<V, Integer>> sol = new ArrayList<>();
        WeightedDiGraph<V, Integer> wdg = g;
        WeightedBreadthFirstTraversal pt = new WeightedBreadthFirstTraversal<>(g, src);
        List<WDiEdge<V, Integer>> path = pt.pathTo(dst);
        while(path != null){
            int mf = maxFlowPath(path);

            List<WDiEdge<V, Integer>> edges = wdg.wDiEdges();
            edges = updateEdges(path, mf, edges);

            List<WDiEdge<V, Integer>> pathinv = inverso(path);
            edges = updateEdges(pathinv, mf, edges);

            wdg = new WeightedDictionaryDiGraph<>(wdg.vertices(), edges);

            sol = addFlows(path, mf, sol);
        }
    }

    private static <V> List<WDiEdge<V, Integer>> inverso(List<WDiEdge<V, Integer>> path){
        List<WDiEdge<V, Integer>> inverso = new ArrayList<>();

        for(WDiEdge<V, Integer> edge : path){
            inverso.append(new WDiEdge<>(edge.getDst(), edge.getWeight(), edge.getSrc()));
        }
        return inverso;
    }

    public int maxFlow() {
        // TO DO

        WeightedBreadthFirstTraversal pt = new WeightedBreadthFirstTraversal<>(g, src);
        int maxFlow = Integer.MAX_VALUE;
        for(V dest : g.vertices()) {
            List<WDiEdge<V, Integer>> path = pt.pathTo(dest);
            if(maxFlow < maxFlowPath(path)){
                maxFlow = maxFlowPath(path);
            }

        }

        return maxFlow;
    }

    public int maxFlowMinCut(Set<V> set) {
        // TO DO
        return 0;
    }

    /**
     * Provided auxiliary methods
     */
    public List<WDiEdge<V, Integer>> getSolution() {
        return sol;
    }
	
    /**********************************************************************************
     * A partir de aquí SOLO para estudiantes a tiempo parcial sin evaluación continua.
     * ONLY for part time students.
     * ********************************************************************************/

    public static <V> boolean localEquilibrium(WeightedDiGraph<V,Integer> g, V src, V dst) {
        // TO DO
        return false;
    }
    public static <V,W> Tuple2<List<V>,List<V>> sourcesAndSinks(WeightedDiGraph<V,W> g) {
        // TO DO
        return null;
    }

    public static <V> void unifySourceAndSink(WeightedDiGraph<V,Integer> g, V newSrc, V newDst) {
        // TO DO
    }
}
