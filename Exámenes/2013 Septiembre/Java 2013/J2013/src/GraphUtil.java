/** ------------------------------------------------------------------------------
  * Estructuras de Datos. 2º Curso. ETSI Informática. UMA
  *
  * Control del día 4-9-2013
  * 
  * Diámetro de un grafo conexo 
  *
  * (completa y sustituye los siguientes datos)
  * Titulación: Grado en Ingeniería …………………………………… [Informática | del Software | de Computadores].
  *
  * Alumno: APELLIDOS, NOMBRE
  *
  * -------------------------------------------------------------------------------
  */

import dataStructures.graph.BreadthFirstTraversal;
import dataStructures.graph.Graph;

public class GraphUtil {

	/**
	 * LENGTH: Calcula el número de elementos que contiene un iterable
	 * 
	 * @param it  El iterador
	 * @return   Número de elementos en el iterador
	 */
	public static <T> int length(Iterable<T> it) {
		// TO DO
		int cont = 0;
		for(T t : it){
			cont++;
		}
	    return cont;
	}

	/**
	 * ECCENTRICITY: Calcula la excentricidad de un vértice en un grafo El algoritmo toma la
	 * longitud del camino máximo en un recorrido en profundidad del grafo
	 * comenzando en el vértice dado.
	 * 
	 * @param graph    Grafo
	 * @param v        Vértice del grafo
	 * @return         Excentricidad del vértice
	 */
	public static <T> int eccentricity(Graph<T> graph, T v) {
		// TO DO
		BreadthFirstTraversal a = new BreadthFirstTraversal<>(graph, v);
		int max = -1;
		for(Object x : a.paths()){
			if(length((Iterable<T>) x) > max){
				max = length((Iterable<T>) x);
			}
		}
	    return max;
	}

	/**
	 * DIAMETER: Se define como la máxima excentricidad de los vértices del grafo.
	 * 
	 * @param graph
	 * @return
	 */

	public static <T> int diameter(Graph<T> graph) {
		// TO DO
		int max = -1;
		for(T v : graph.vertices()){
			if(max < eccentricity(graph, v)){
				max = eccentricity(graph, v);
			}
		}
	    return max;
	}
	
	/** 
	 * Estima y justifica la complejidad del método diameter
	 */
}
