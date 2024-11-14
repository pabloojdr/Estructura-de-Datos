package dataStructures.dictionary;
import dataStructures.list.List;

import dataStructures.set.AVLSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

import java.util.HashSet;

/**
 * Estructuras de Datos. Grados en Informatica. UMA.
 * Examen de septiembre de 2018.
 *
 * Apellidos, Nombre:
 * Titulacion, Grupo:
 */
public class HashBiDictionary<K,V> implements BiDictionary<K,V>{
	private Dictionary<K,V> bKeys;
	private Dictionary<V,K> bValues;
	
	public HashBiDictionary() {
		// TODO
		bKeys = new HashDictionary<>();
		bValues = new HashDictionary<>();
	}
	
	public boolean isEmpty() {
		// TODO
		return bKeys.isEmpty() && bValues.isEmpty();
	}
	
	public int size() {
		// TODO
		return bKeys.size();
	}
	
	public void insert(K k, V v) {
		// TODO
		bKeys.insert(k, v);
		bValues.insert(v, k);
	}
	
	public V valueOf(K k) {
		// TODO
		return bKeys.valueOf(k);
	}
	
	public K keyOf(V v) {
		// TODO
		return bValues.valueOf(v);
	}
	
	public boolean isDefinedKeyAt(K k) {
		return bKeys.isDefinedAt(k);
	}
	
	public boolean isDefinedValueAt(V v) {
		return bValues.isDefinedAt(v);
	}
	
	public void deleteByKey(K k) {
		// TODO
		if(bKeys.isDefinedAt(k)){
			V valueK = bKeys.valueOf(k);
			K key = k;
			bValues.delete(valueK);
			bKeys.delete(k);
		}
	}
	
	public void deleteByValue(V v) {
		// TODO
		if(bValues.isDefinedAt(v)){
			V value = v;
			K valueV = bValues.valueOf(v);
			bValues.delete(v);
			bKeys.delete(valueV);
		}
	}
	
	public Iterable<K> keys() {
		return bKeys.keys();
	}
	
	public Iterable<V> values() {
		return bValues.keys();
	}
	
	public Iterable<Tuple2<K, V>> keysValues() {
		return bKeys.keysValues();
	}
	
		
	public static <K,V extends Comparable<? super V>> BiDictionary<K, V> toBiDictionary(Dictionary<K,V> dict) {
		// TODO
		if(inyectivo(dict)){

		}
		return null;
	}

	private static <K, V extends Comparable<? super V>> boolean inyectivo(Dictionary<K,V> dict) {
		Set<K> keys = new HashSet<K>();
		Set<V> values = new AVLSet<>();

		Iterable<V> it1 = dict.values();
		Iterable<K> it2 = dict.keys();
		for(V v : it1) {
			values.insert(v);
		}
		for(K k : it2){
			keys.insert(k);
		}

		return keys.size() == values.size();
	}

	
	public <W> BiDictionary<K, W> compose(BiDictionary<V,W> bdic) {
		// TODO
		return null;
	}
		
	public static <K extends Comparable<? super K>> boolean isPermutation(BiDictionary<K,K> bd) {
		// TODO
		return false;
	}
	
	// Solo alumnos con evaluación por examen final.
    // =====================================
	
	public static <K extends Comparable<? super K>> List<K> orbitOf(K k, BiDictionary<K,K> bd) {
		// TODO
		return null;
	}
	
	public static <K extends Comparable<? super K>> List<List<K>> cyclesOf(BiDictionary<K,K> bd) {
		// TODO
		return null;
	}

    // =====================================
	
	
	@Override
	public String toString() {
		return "HashBiDictionary [bKeys=" + bKeys + ", bValues=" + bValues + "]";
	}
	
	
}
