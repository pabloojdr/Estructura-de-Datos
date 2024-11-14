-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
--
-- Data Structures. February 2018. BSc. Computer Science. UMA.
-------------------------------------------------------------------------------

module DisjointSet
                  ( DisjointSet
                  , empty
                  , isEmpty
                  , isElem
                  , numElements
                  , add
                  , areConnected
                  , kind
                  , union
                  , flatten
                  , kinds
                  ) where

import           Data.List                               (intercalate, nub)
import           Data.Maybe                              (fromJust)
import qualified DataStructures.Dictionary.AVLDictionary as D

data DisjointSet a = DS (D.Dictionary a a)

-- | Exercise 1. empty

empty :: DisjointSet a
empty = (DS (D.empty)) 

-- | Exercise 2.a isEmpty

isEmpty :: DisjointSet a -> Bool
isEmpty (DS dic) = D.isEmpty dic

-- | Exercise 2.b isElem

isElem :: (Ord a) => a -> DisjointSet a -> Bool
isElem x (DS dic) = D.isDefinedAt x dic

-- | Exercise 3. numElements

numElements :: DisjointSet a -> Int
numElements (DS dic) = D.size dic 

-- | Exercise 4. add

add :: Ord a => a -> DisjointSet a -> DisjointSet a
add x (DS dic)
  |D.isDefinedAt x dic = DS dic
  |otherwise = DS (D.insert x x dic) 

-- | Exercise 5. root

root :: Ord a => a -> DisjointSet a -> Maybe a
root x (DS dic)
  |not(D.isDefinedAt x dic) = Nothing
  |x == x' = Just x
  |otherwise = root x' (DS dic)
    where
      Just x' = D.valueOf x dic 

-- | Exercise 6. isRoot

isRoot :: Ord a => a -> DisjointSet a -> Bool
isRoot x (DS dic)
  |D.isEmpty dic = False
  |x == x' = True
  |otherwise = False
    where
      Just x' = root x (DS dic)

-- | Exercise 7. areConnected

areConnected :: Ord a => a -> a -> DisjointSet a -> Bool
areConnected x y (DS dic) = root x (DS dic) == root y (DS dic)

-- | Exercise 8. kind

kind :: Ord a => a -> DisjointSet a -> [a]
kind x (DS dic)
  |D.isDefinedAt x dic = [s | s <- D.keys dic, areConnected s x (DS dic)]
  |otherwise = []

-- | Exercise 9. union

union :: Ord a => a -> a -> DisjointSet a -> DisjointSet a
union x y (DS dic)
  |D.isDefinedAt x dic && D.isDefinedAt y dic = (DS (D.insert maxi mini dic))
  |otherwise = error "falta un elemento"
    where
      maxi = max rx ry
      mini = min rx ry
      Just rx = root x (DS dic)
      Just ry = root y (DS dic)

-- |------------------------------------------------------------------------

flatten :: Ord a => DisjointSet a -> DisjointSet a
flatten = undefined

kinds :: Ord a => DisjointSet a -> [[a]]
kinds (DS dic) = nub[kind x (DS dic) | x <- D.keys dic]

-- |------------------------------------------------------------------------

instance (Ord a, Show a) => Show (DisjointSet a) where
  show (DS d)  = "DictionaryDisjointSet(" ++ intercalate "," (map show (D.keysValues d)) ++ ")"


{-

-- Examples

-- | Exercise 1. empty

>>> empty
DictionaryDisjointSet()

-- | Exercise 2.a isEmpty

>>> isEmpty empty
True

>>> isEmpty (add 1 empty)
False

-- | Exercise 2.b isElem

>>> isElem 1 empty
False

>>> isElem 1 (add 1 empty)
True

>>> isElem 2 (add 1 empty)
False

>>> isElem 1 (add 2 (add 1 empty))
True

-- | Exercise 3. numElements

>>> numElements empty
0

>>> numElements (add 1 empty)
1

>>> numElements (add 2 (add 1 empty))
2

-- | Exercise 4. add

>>> add 1 empty
DictionaryDisjointSet((1,1))

>>> add 2 (add 1 empty)
DictionaryDisjointSet((1,1),(2,2))

>>> add 1 (add 2 (add 1 empty))
DictionaryDisjointSet((1,1),(2,2))

-- | Exercise 5. root

>>> root 1 empty
Nothing

>>> root 1 (add 1 empty)
Just 1

>>> root 2 (add 2 (add 1 empty))
Just 2

>>> root 1 (union 1 2 (add 2 (add 1 empty)))
Just 1

>>> root 2 (union 1 2 (add 2 (add 1 empty)))
Just 1

>>> root 1 (union 1 3 (add 3 (add 2 (add 1 empty))))
Just 1

>>> root 2 (union 1 3 (add 3 (add 2 (add 1 empty))))
Just 2

>>> root 3 (union 1 3 (add 3 (add 2 (add 1 empty))))
Just 1

>>> root 4 (union 1 3 (add 3 (add 2 (add 1 DisjointSet.empty))))
Nothing

-- | Exercise 6. isRoot

>>> isRoot 1 empty
False

>>> isRoot 1 (add 1 empty)
True

>>> isRoot 1 (union 1 2 (add 2 (add 1 empty)))
True

>>> isRoot 2 (union 1 2 (add 2 (add 1 empty)))
False

>>> isRoot 1 (union 1 3 (add 3 (add 2 (add 1 empty))))
True

>>> isRoot 2 (union 1 3 (add 3 (add 2 (add 1 empty))))
True

>>> isRoot 3 (union 1 3 (add 3 (add 2 (add 1 empty))))
False

-- | Exercise 7. areConnected

>>> areConnected 1 3 (union 1 3 (add 3 (add 2 (add 1 empty))))
True

>>> areConnected 3 1 (union 1 3 (add 3 (add 2 (add 1 empty))))
True

>>> areConnected 1 1 (union 1 3 (add 3 (add 2 (add 1 empty))))
True

>>> areConnected 1 2 (union 1 3 (add 3 (add 2 (add 1 empty))))
False

>>> areConnected 1 2 (union 2 3 (union 1 3 (add 3 (add 2 (add 1 empty)))))
True

>>> areConnected 1 5 (union 2 3 (union 1 3 (add 3 (add 2 (add 1 empty)))))
False

-- | Exercise 8. kind

>>> kind 1 (add 2 (add 1 empty))
[1]

>>> kind 2 (add 2 (add 1 empty))
[2]

>>> kind 3 (add 2 (add 1 empty))
[]

>>> kind 1 (union 1 3 (add 3 (add 2 (add 1 empty))))
[1,3]

>>> kind 3 (union 1 3 (add 3 (add 2 (add 1 empty))))
[1,3]

>>> kind 2 (union 1 3 (add 3 (add 2 (add 1 empty))))
[2]

>>> kind 2 (union 2 3 (union 1 3 (add 3 (add 2 (add 1 empty)))))
[1,2,3]

-- | Exercise 9. union

>>> union 1 2 (add 2 (add 1 empty))
DictionaryDisjointSet((1,1),(2,1))

>>> union 2 1 (add 2 (add 1 empty))
DictionaryDisjointSet((1,1),(2,1))

>>> union 1 1 (add 2 (add 1 empty))
DictionaryDisjointSet((1,1),(2,2))

>>> union 1 3 (add 3 (add 2 (add 1 empty)))
DictionaryDisjointSet((1,1),(2,2),(3,1))

>>> union 1 2 (add 1 empty)
*** Exception: union: missing element(s)

-}
