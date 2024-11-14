{------------------------------------------------------------------------------
 - Student's name:
 -
 - Student's group:
 -----------------------------------------------------------------------------}

module AVL 
  ( 
    Weight
  , Capacity
  , AVL (..)
  , Bin
  , emptyBin
  , remainingCapacity
  , addObject
  , maxRemainingCapacity
  , height
  , nodeWithHeight
  , node
  , rotateLeft
  , addNewBin
  , addFirst
  , addAll
  , toList
  , linearBinPacking
  , seqToList
  , addAllFold
  ) where

type Capacity = Int
type Weight= Int

data Bin = B Capacity [Weight] 

data AVL = Empty | Node Bin Int Capacity AVL AVL deriving Show


emptyBin :: Capacity -> Bin
emptyBin c
  | c < 0 = error "La capacidad no puede ser negativa"
  | otherwise = B c []

remainingCapacity :: Bin -> Capacity
remainingCapacity (B c _) = c

addObject :: Weight -> Bin -> Bin
addObject w (B c xs)
  | w > c = error "el objeto no cabe dentro del cubo"
  | otherwise = B (c-w) (xs ++ [w])

maxRemainingCapacity :: AVL -> Capacity
maxRemainingCapacity Empty = 0
maxRemainingCapacity (Node b h c lt rt) = c


height :: AVL -> Int
height Empty = 0
height (Node b h c lt rt) = h 


 
nodeWithHeight :: Bin -> Int -> AVL -> AVL -> AVL
nodeWithHeight b h lt rt = Node b h (1+(max (maxRemainingCapacity lt) (maxRemainingCapacity rt))) lt rt


node :: Bin -> AVL -> AVL -> AVL
node b lt rt = Node b (max (height lt) (height rt)) (max (maxRemainingCapacity lt) (maxRemainingCapacity rt)) lt rt 

rotateLeft :: Bin -> AVL -> AVL -> AVL
rotateLeft c l (Node x h2 c2 r1 r2) = node x (node c l r1) r2

addNewBin :: Bin -> AVL -> AVL
addNewBin b Empty = node b Empty Empty
addNewBin b (Node x h c lt rt) 
  | (height rt) - (height lt) > 1 = rotateLeft x lt (addNewBin b rt)
  | otherwise = node x lt (addNewBin b rt)
 
addFirst :: Capacity -> Weight -> AVL -> AVL
addFirst c w Empty = (addNewBin (B (c-w) [w]) Empty)
addFirst c w nod@(Node x h c2 lt rt)
  |w > maxRemainingCapacity nod = addNewBin (B (c-w) [w]) nod
  |w <= maxRemainingCapacity lt = node x (addFirst c w lt) rt
  |w <=remainingCapacity x = node (addObject w x) lt rt
  |otherwise = node x lt (addFirst c w rt)

addAll:: Capacity -> [Weight] -> AVL
addAll c xs = aux c xs Empty
  where
    aux c [] sol = sol
    aux c (x:xs) sol = aux c xs (addFirst c x sol) 

toList :: AVL -> [Bin]
toList Empty = []
toList (Node b h c lt rt) = toList lt ++ [b] ++ toList rt 

{-
	SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
 -}

data Sequence = SEmpty | SNode Bin Sequence deriving Show  

linearBinPacking:: Capacity -> [Weight] -> Sequence
linearBinPacking _ _ = undefined

seqToList:: Sequence -> [Bin]
seqToList _ = undefined

addAllFold:: [Weight] -> Capacity -> AVL 
addAllFold _ _ = undefined



{- No modificar. Do not edit -}

objects :: Bin -> [Weight]
objects (B _ os) = reverse os

  
instance Show Bin where
  show b@(B c os) = "Bin("++show c++","++show (objects b)++")"