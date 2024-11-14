------------------------------------------------------------------
-- TAD's Roullete
--
-- ISABEL TORRES-PAROD LÓPEZ    
-------------------------------------------------------------------


import      Data.List(intercalate)
import      qualified DataStructures.Queue.LinearQueue as Q
import      Test.QuickCheck


data Roullete a = R (Q.Queue a) Integer
                 deriving Eq

sample1 = R (foldl (flip Q.enqueue) Q.empty [3,4,5,6,7,8,9,10,1,2]) 10

-- EJERCICIO 1 (0.05 ptos)
--Crea una ruleta vacía
empty :: Roullete a
empty = R Q.empty 0

-- EJERCICIO 2 (0.05 ptos)
--Determinar si una ruleta vacía
isEmpty :: Roullete a -> Bool
isEmpty (R _ s) = s == 0 

--EJERCICIO 3 (0.10 ptos)
-- devuelve el dato apuntado
sign :: Roullete a -> a
sign (R q s) = Q.first q

--EJERCICIO 4 (0.20 ptos)
--gira la rulera un deterinado numero de elementos
turn :: Integer -> Roullete a -> Roullete a
turn x (R q 0) = empty
turn x (R q s) 
    | x `mod` s == 0 = R q s 
    | x `mod` s /= 0 = turn (x-1) (R (Q.dequeue (Q.enqueue (Q.first q) q)) s)
    

--EJERCICIO 5 (0.10 ptos)
-- elimina el eleemntos situados en el puntero y coloca el puntero en el siguiente
delete :: Roullete a -> Roullete a
delete (R q s) = R (Q.dequeue q) (s-1) 

--EJERCICIO 6 (0.15 ptos)
-- INSERTA EL ELEEMTNOS EN LA POSICION DEL PUNTERO Y CORRE EL RESTO EN SENTIDO HORARIO
insert :: a -> Roullete a -> Roullete a
insert x (R q 0) = R (Q.enqueue x q) 1
insert x (R q s) = turn s (R (Q.enqueue x q) (s+1))

--EJERCIO 7 (0.15 ptos)
-- genera una ruleta con los objetos de la lsita situados en orden horario y con el puntero apuntando ...
listToRoullete :: [a] -> Roullete a
listToRoullete xs = foldr insert empty xs

--EJERCICIO 8 (0.15 ptos)
--geenra una liita con los elementos de una ruleta. El primero serña el pauntado por el puntero y luego siguiendo el sentido horario
roulleteToList :: Roullete a -> [a]
roulleteToList = undefined

--EJERCICIO 9 (0.20 ptos)
-- mapRoullette toma una funcion de a->b y se le applica a todos los elementos de la ruleta quedando la ruelta en la misma posicion
mapRoullete :: (a -> b) -> Roullete a -> Roullete b
mapRoullete = undefined

--EJERCICIO 10 (0.10 ptos)
-- probar con quickchek que para cualquier n y cualquier ruleta qirar n a la derecha luego n a la izquierda resulta en la misma ruleta
p1 :: Integer -> Roullete Integer -> Bool
p1 = undefined


--showing a roullete
instance (Show a) => Show (Roullete a) where
    show (R q size) = "QueueRoullete:"++show size++"("++(intercalate "," (aux q))++")"
        where
        aux q1
            |Q.isEmpty q1 = []
            | otherwise = show x : aux q'
                where
                    x = Q.first q1
                    q' = Q.dequeue q1

-- Arbitrary instance
instance Arbitrary a => Arbitrary (Roullete a) where
    arbitrary = do
        xs <- listOf arbitrary
        return (foldr insert empty xs)