-------------------------------------------------------------------------------
-- Apellidos, Nombre: 
-- Titulacion, Grupo: 
--
-- Estructuras de Datos. Grados en Informatica. UMA.
-------------------------------------------------------------------------------

module AVLBiDictionary( BiDictionary
                      , empty
                      , isEmpty
                      , size
                      , insert
                      , valueOf
                      , keyOf
                      , deleteByKey
                      , deleteByValue
                      , toBiDictionary
                      , compose
                      , isPermutation
                      , orbitOf
                      , cyclesOf
                      ) where

import qualified DataStructures.Dictionary.AVLDictionary as D
import qualified DataStructures.Set.BSTSet               as S

import           Data.List                               (intercalate, nub,
                                                          (\\))
import           Data.Maybe                              (fromJust, fromMaybe,
                                                          isJust)
import           Test.QuickCheck


data BiDictionary a b = Bi (D.Dictionary a b) (D.Dictionary b a)

-- | Exercise a. empty, isEmpty, size

empty :: (Ord a, Ord b) => BiDictionary a b
empty = Bi (D.empty) (D.empty)

isEmpty :: (Ord a, Ord b) => BiDictionary a b -> Bool
isEmpty (Bi x y) = D.isEmpty x && D.isEmpty y 

size :: (Ord a, Ord b) => BiDictionary a b -> Int
size (Bi x _) = D.size x

-- | Exercise b. insert

insert :: (Ord a, Ord b) => a -> b -> BiDictionary a b -> BiDictionary a b
insert x y (Bi dic1 dic2)
  |D.isDefinedAt x dic1 = Bi (D.insert x y dic1) (D.insert y x (D.delete x' dic2))
  |otherwise = Bi (D.insert x y dic1) (D.insert y x dic2)
    where 
     Just x' = D.valueOf x dic1

-- | Exercise c. valueOf

valueOf :: (Ord a, Ord b) => a -> BiDictionary a b -> Maybe b
valueOf x (Bi dic1 _) = D.valueOf x dic1

-- | Exercise d. keyOf

keyOf :: (Ord a, Ord b) => b -> BiDictionary a b -> Maybe a
keyOf y (Bi _ dic2 ) = D.valueOf y dic2

-- | Exercise e. deleteByKey

deleteByKey :: (Ord a, Ord b) => a -> BiDictionary a b -> BiDictionary a b
deleteByKey x (Bi dic1 dic2)
  |not(D.isDefinedAt x dic1) = (Bi dic1 dic2)
  |otherwise = Bi (D.delete x dic1) (D.delete b' dic2)
    where
      Just b' = (D.valueOf x dic1)

-- | Exercise f. deleteByValue

deleteByValue :: (Ord a, Ord b) => b -> BiDictionary a b -> BiDictionary a b
deleteByValue y (Bi dic1 dic2)
  |not(D.isDefinedAt y dic2) = (Bi dic1 dic2)
  |otherwise = Bi (D.delete a' dic1) (D.delete y dic2)
    where
      Just a' = D.valueOf y dic2

-- | Exercise g. toBiDictionary

toBiDictionary :: (Ord a, Ord b) => D.Dictionary a b -> BiDictionary a b
toBiDictionary dic
  |not(inyectivo dic) = error "el diccionario no es inyectivo"
  |otherwise = foldr (uncurry insert) empty (D.keysValues dic)


inyectivo :: (Ord a, Ord b) => D.Dictionary a b -> Bool
inyectivo dic = length lista1 == length lista2
    where
      lista1 = nub[x | x <- D.keys dic]
      lista2 = nub[y | y <- D.values dic]
-- | Exercise h. compose

compose :: (Ord a, Ord b, Ord c) => BiDictionary a b -> BiDictionary b c -> BiDictionary a c
compose (Bi dic1 dic2) (Bi dic3 dic4) = foldr (uncurry insert) empty (list)
  where
    list = [(r,d) | r <- D.keys dic1, d <- D.values dic3, (D.valueOf r dic1) == (D.valueOf d dic4)]

-- | Exercise i. isPermutation

isPermutation :: Ord a => BiDictionary a a -> Bool
isPermutation (Bi dic1 dic2) = D.keys dic1 == D.keys dic2



-- |------------------------------------------------------------------------


-- | Exercise j. orbitOf

orbitOf :: Ord a => a -> BiDictionary a a -> [a]
orbitOf = undefined

-- | Exercise k. cyclesOf

cyclesOf :: Ord a => BiDictionary a a -> [[a]]
cyclesOf = undefined

-- |------------------------------------------------------------------------


instance (Show a, Show b) => Show (BiDictionary a b) where
  show (Bi dk dv)  = "BiDictionary(" ++ intercalate "," (aux (D.keysValues dk)) ++ ")"
                        ++ "(" ++ intercalate "," (aux (D.keysValues dv)) ++ ")"
   where
    aux kvs  = map (\(k,v) -> show k ++ "->" ++ show v) kvs
