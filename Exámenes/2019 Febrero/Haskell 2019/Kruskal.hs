----------------------------------------------
-- Estructuras de Datos.  2018/19
-- 2º Curso del Grado en Ingeniería [Informática | del Software | de Computadores].
-- Escuela Técnica Superior de Ingeniería en Informática. UMA
--
-- Examen 4 de febrero de 2019
--
-- ALUMNO/NAME:
-- GRADO/STUDIES:
-- NÚM. MÁQUINA/MACHINE NUMBER:
--
----------------------------------------------

module Kruskal(kruskal, kruskals) where

import qualified DataStructures.Dictionary.AVLDictionary as D
import qualified DataStructures.PriorityQueue.LinearPriorityQueue as Q
import DictionaryWeightedGraph

kruskal :: (Ord a, Ord w) => WeightedGraph a w -> [WeightedEdge a w]
kruskal wg = alg (dic (vertices wg)) (pq (edges wg)) []
    where
        dic [] = D.empty
        dic (x:xs) = D.insert x x (dic xs)

        pq [] = Q.empty
        pq (x:xs) = Q.enqueue x (pq xs)

        alg d p t 
            | Q.isEmpty p = t 
            | otherwise = alg (checkDic d (Q.first p)) (Q.dequeue p) (checkT t d (Q.first p))

        checkDic d (WE src w dst)
            | representante d src /= representante d dst = D.insert (representante d dst) src d 
            | otherwise = d 

        checkT t d ed@(WE src w dst)
            |representante d src /= representante d dst = (ed : t)
            |otherwise = t

        representante dic elem
            | u == elem = elem
            | otherwise = representante dic u

            where 
                Just u = D.valueOf elem dic


-- Solo para evaluación continua / only for part time students
kruskals :: (Ord a, Ord w) => WeightedGraph a w -> [[WeightedEdge a w]]
kruskals = undefined
