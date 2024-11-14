-------------------------------------------------------------------------------
-- Estructuras de Datos. Grado en Informática, IS e IC. UMA.
-- Examen de Febrero 2015.
--
-- Implementación del TAD Deque
--
-- Apellidos:
-- Nombre:
-- Grado en Ingeniería ...
-- Grupo:
-- Número de PC:
-------------------------------------------------------------------------------

module TwoListsDoubleEndedQueue
   ( DEQue
   , empty
   , isEmpty
   , first
   , last
   , addFirst
   , addLast
   , deleteFirst
   , deleteLast
   ) where

import Prelude hiding (last)
import Data.List(intercalate)
import Test.QuickCheck

data DEQue a = DEQ [a] [a]

-- Complexity: 0(1)
empty :: DEQue a
empty = DEQ [] []

-- Complexity: 0(1)
isEmpty :: DEQue a -> Bool
isEmpty (DEQ [][]) = True
isEmpty _ = False

-- Complexity:
addFirst :: a -> DEQue a -> DEQue a
addFirst x (DEQ fs ls) = DEQ (x:fs) ls 

-- Complexity:
addLast :: a -> DEQue a -> DEQue a
addLast x (DEQ fs ls) = DEQ fs (x:ls) 

-- Complexity:
first :: DEQue a -> a
first (DEQ [] []) = error "first sobre vacio"
first (DEQ [] ls) = head (reverse ls)
first (DEQ (f:fs) ls) = f


-- Complexity:
last :: DEQue a -> a
last (DEQ [] []) = error "last sobre vacio"
last (DEQ fs []) = head(reverse fs)
last (DEQ fs (l:ls)) = l

-- Complexity:
deleteFirst :: DEQue a -> DEQue a
deleteFirst (DEQ [] []) = error "delete sobre vacio"
deleteFirst (DEQ (f:fs) ls) = DEQ fs ls 
deleteFirst (DEQ [] ls) = deleteFirst (DEQ x y)
   where 
      (primera, segunda) = splitAt mitad ls
      x = reverse segunda
      y = primera
      mitad = div (length ls) 2
-- Complexity:
deleteLast :: DEQue a -> DEQue a
deleteLast (DEQ [] []) = error "delete sobre vacio"
deleteLast (DEQ fs (l:ls)) = DEQ fs ls
deleteLast (DEQ fs []) = deleteLast (DEQ x y)
   where 
      mitad = div (length fs) 2
      (primera, segunda) = splitAt mitad fs
      x = reverse segunda
      y = primera



instance (Show a) => Show (DEQue a) where
   show q = "TwoListsDoubleEndedQueue(" ++ intercalate "," [show x | x <- toList q] ++ ")"

toList :: DEQue a -> [a]
toList (DEQ xs ys) =  xs ++ reverse ys

instance (Eq a) => Eq (DEQue a) where
   q == q' =  toList q == toList q'

instance (Arbitrary a) => Arbitrary (DEQue a) where
   arbitrary =  do
      xs <- listOf arbitrary
      ops <- listOf (oneof [return addFirst, return addLast])
      return (foldr id empty (zipWith ($) ops xs))
