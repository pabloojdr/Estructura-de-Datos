/**
 * Estructuras de Datos. Grados en Informatica. UMA.
 * Examen de febrero de 2018.
 *
 * Apellidos, Nombre:
 * Titulacion, Grupo:
 */

package dataStructures.set;

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.list.ArrayList;
import dataStructures.list.List;

import java.util.Iterator;

public class DisjointSetDictionary<T extends Comparable<? super T>> implements DisjointSet<T> {

    private Dictionary<T, T> dic;

    /**
     * Inicializa las estructuras necesarias.
     */
    public DisjointSetDictionary() {
        // TODO
        dic = new AVLDictionary<>();
    }

    /**
     * Devuelve {@code true} si el conjunto no contiene elementos.
     */
    @Override
    public boolean isEmpty() {
        // TODO
        return dic.isEmpty();
    }

    /**
     * Devuelve {@code true} si {@code elem} es un elemento del conjunto.
     */
    @Override
    public boolean isElem(T elem) {
        // TODO
        return dic.isDefinedAt(elem);
    }

    /**
     * Devuelve el numero total de elementos del conjunto.
     */

    @Override
    public int numElements() {
        // TODO
        return dic.size();
    }

    /**
     * Agrega {@code elem} al conjunto. Si {@code elem} no pertenece al
     * conjunto, crea una nueva clase de equivalencia con {@code elem}. Si
     * {@code elem} pertencece al conjunto no hace nada.
     */
    @Override
    public void add(T elem) {
        // TODO
        if(!isElem(elem)){
            dic.insert(elem, elem);
        }
    }

    /**
     * Devuelve el elemento canonico (la raiz) de la clase de equivalencia la
     * que pertenece {@code elem}. Si {@code elem} no pertenece al conjunto
     * devuelve {@code null}.
     */
    private T root(T elem) {
        // TODO
        if(!isElem(elem)){
            return null;
        } else {
            T r = dic.valueOf(elem);
            if(r.equals(elem)){
                r = elem;
            } else {
                r = root(dic.valueOf(elem));
            }
            return r;
        }
    }

    /**
     * Devuelve {@code true} si {@code elem} es el elemento canonico (la raiz)
     * de la clase de equivalencia a la que pertenece.
     */
    private boolean isRoot(T elem) {
        // TODO
        return elem == root(elem);
    }

    /**
     * Devuelve {@code true} si {@code elem1} y {@code elem2} estan en la misma
     * clase de equivalencia.
     */
    @Override
    public boolean areConnected(T elem1, T elem2) {
        // TODO
        if(!isElem(elem1) || !isElem(elem2)){
            System.out.println("alguno de los elementos no está definido en el conjunto");
            return root(elem1).equals(root(elem2);
        }else {
            if (root(elem1).equals(root(elem2))) {
                return true;
            } else {
                return false;
            }
        }

    }

    /**
     * Devuelve una lista con los elementos pertenecientes a la clase de
     * equivalencia en la que esta {@code elem}. Si {@code elem} no pertenece al
     * conjunto devuelve la lista vacia.
     */
    @Override
    public List<T> kind(T elem) {
        // TODO
        if(!dic.isDefinedAt(elem)){
            return null;
        } else {
            List<T> lista = new ArrayList<>();
            Iterator<T> it = dic.keys().iterator();
            while (it.hasNext()) {
                if (areConnected(it.next(), elem)) {
                    lista.append(it.next());
                }
            }
            return lista;
        }
    }

    /**
     * Une las clases de equivalencias de {@code elem1} y {@code elem2}. Si
     * alguno de los dos argumentos no esta en el conjunto lanzara una excepcion
     * {@code IllegalArgumenException}.
     */
    @Override
    public void union(T elem1, T elem2) {
        // TODO
        if(!dic.isDefinedAt(elem1) || !dic.isDefinedAt(elem2)){
            throw new IllegalArgumentException("alguno de los elementos no está definido en el conjunto");
        } else {
            T maxi;
            T mini;
            if(root(elem1).compareTo(root(elem2)) > 0){
                maxi = elem1;
                mini = elem2;
            } else {
                maxi = elem2;
                mini = elem1;
            }

            dic.insert(maxi, mini);
        }
    }

    // ====================================================
    // A partir de aqui solo para alumnos a tiempo parcial
    // que no sigan el proceso de evaluacion continua.
    // ====================================================

    /**
     * Aplana la estructura de manera que todos los elementos se asocien
     * directamente con su representante canonico.
     */
    @Override
    public void flatten() {
        // TODO
    }

    /**
     * Devuelve una lista que contiene las clases de equivalencia del conjunto
     * como listas.
     */
    @Override
    public List<List<T>> kinds() {
        // TODO
        if(dic.isEmpty()){
            return null;
        } else {
            List<List<T>> listakinds = new ArrayList<>();
            Iterable<T> it = dic.keys();
            for (T elem : it) {
                listakinds.append(kind(elem));
            }

            return listakinds;
        }
    }

    /**
     * Devuelve una representacion del conjunto como una {@code String}.
     */
    @Override
    public String toString() {
        return "DisjointSetDictionary(" + dic.toString() + ")";
    }
}
