------------------------------------------------------------
-- Estructuras de Datos
-- Tema 4. Árboles
-- Pablo López
--
-- Árboles de búsqueda en Haskell
------------------------------------------------------------

module BST where

import           Data.Maybe                        (isJust)
import           DataStructures.Graphics.DrawTrees
import           Test.QuickCheck

-- árboles binarios de búsqueda en Haskell
data BST a = Empty
           | Node a (BST a) (BST a) deriving Show
            --  root left   right

{-
   Aunque usamos deriving Show, no es muy útil; estas instancias son
   para pintar árboles binarios en formato gráfico. No es necesario
   saber crear estas instancias, se facilitan cuando sea necesario.
-}

instance Subtrees (BST a) where
    isEmptyTree Empty = True
    isEmptyTree _     = False

    subtrees Empty        = []
    subtrees (Node _ i d) = [i,d]

instance Show a => ShowNode (BST a) where
    showNode Empty        = ""
    showNode (Node r _ _) = show r

mkBST :: Ord a => [a] -> BST a
mkBST xs = foldr insert empty xs

tree1 :: BST Int
tree1 = mkBST [7, 4, 10, 17, 19, 18, 27, 45, 90, 55, 15, 20, 25, 40, 12,  68, 73, 75, 60, 70, 80,50]

inOrder :: BST a -> [a]
inOrder Empty        = []
inOrder (Node x i d) = inOrder i ++ x : inOrder d

treeSort :: Ord a => [a] -> [a]
treeSort xs = (inOrder . mkBST) xs

empty :: BST a
empty = Empty

insert :: Ord a => a -> BST a -> BST a
insert x Empty = Node x Empty Empty
insert x (Node y lt rt)
    | x < y = Node y (insert x lt) rt
    | x == y = Node x lt rt
    | x > y = Node y lt (insert x rt)

search :: Ord a => a -> BST a -> Maybe a
search x Empty = Nothing
search x (Node y lt rt)
    | x < y = search x lt
    | x == y = Just y
    | x > y = search x rt

isElem :: Ord a => a -> BST a -> Bool
isElem x t = aBool (search x t) -- Maybe
    where
        aBool Nothing = False
        aBool (Just _) = True

minim :: BST a -> a 
minim Empty = error "minim on empty tree"
minim (Node x Empty _) = x
minim (Node x lt rt) = minim lt 

maxim :: BST a -> a
maxim Empty = error "maxim on empty tree"
maxim (Node x _ Empty) = x
maxim (Node x lt rt) = maxim rt

delete :: Ord a => a -> BST a -> BST a
delete x Empty = Empty
delete x t@(Node y lt rt)
    | x < y = Node y (delete x lt) rt
    | x > y = Node y lt (delete x rt)
    | x == y = deleteRoot t

deleteRoot :: Ord a => BST a -> BST a
deleteRoot (Node _ lt Empty) = lt 
deleteRoot (Node _ Empty rt) = rt
deleteRoot (Node _ lt rt) = Node minRoot lt (delete minRoot rt)
    where 
        minRoot = minim rt 

--drawOn "nombre.svg" tree que queremos dibujar
--drawOn "tree1delete.svg" (delete 50 tree1)

split :: BST a -> (a,BST a) -- Función usada en las transparencias para deleteRoot (llamado "combine"). Realiza lo mismo que minRoot y minim pero en una sola función
split (Node x Empty rt) = (x,rt)
split (Node x lt rt) = (m,Node x lt' rt)
where (m,lt') = split lt