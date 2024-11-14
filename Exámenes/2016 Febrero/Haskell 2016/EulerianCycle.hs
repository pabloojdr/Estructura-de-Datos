-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module EulerianCycle(isEulerian, eulerianCycle) where

import DataStructures.Graph.Graph
import Data.List

--H.1)
isEulerian :: Eq a => Graph a -> Bool
isEulerian g 
    | length lista == length (vertices g) = True
    | otherwise = False
    where
        lista = [v | v <- vertices g, mod (degree g v) 2 == 0]


-- H.2)
remove :: (Eq a) => Graph a -> (a,a) -> Graph a
remove g (v,u) = mkGraphEdges lista (edges grafo1)
    where 
        grafo1 = deleteEdge g (v, u)
        lista = [v | v <- vertices g, degree grafo1 v /= 0]

-- H.3)
extractCycle :: (Eq a) => Graph a -> a -> (Graph a, Path a)
extractCycle g v0 = aux g v0 [v0]
    where
        aux g v xs 
            |v == sucesor = (elim, sucesor:xs)
            |otherwise = aux g sucesor (sucesor:xs)
                where
                    sucesor = head (successors g v)
                    elim = remove g (v, sucesor)


-- H.4)
connectCycles :: (Eq a) => Path a -> Path a -> Path a
connectCycles [] ys = ys
connectCycles (x:xs) (y:ys)
    | x == y = [x] ++ ys ++ xs
    | otherwise = [x] ++ connectCycles xs (y:ys)

-- H.5)
vertexInCommon :: Eq a => Graph a -> Path a -> a
vertexInCommon g cycle
    |length list /= 0 = head list
    |otherwise = error "no tienen vertices en comun"
        where 
            list = [v | v <- vertices g, v `elem` cycle]

-- H.6) 
eulerianCycle :: Eq a => Graph a -> Path a
eulerianCycle g 
    |not (isEulerian g) = error "el grafo no es euleriano"
    |isEmpty g = []
    |length (vertices g) == 1 = [head (vertices g)]
    |otherwise = eulerianCycle' g (head (vertices g))
        where
            eulerianCycle' g v
                | isEmpty g = []
                | otherwise = connectCycles cycle (eulerianCycle' newGraph newVertex)
                    where
                        cycle = snd (extractCycle g v)
                        newGraph = fst (extractCycle g v)
                        newVertex = vertexInCommon newGraph cycle 
