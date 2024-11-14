module Polinomios where 
import Test.QuickCheck

type Grade = Int
type Coefficient = Integer

data Pol  = Nil 
           | P  Grade Coefficient (Pol)
           deriving Show

s1 = P 2 1 (P 1 3 (P 0 8 Nil))
s2 = P 3 2 (P 1 2 (P 0 10 Nil))

empty :: Pol
empty = Nil

grade :: Pol -> Grade
grade (P x _ _) = x

coeff :: Grade -> Pol -> Coefficient
coeff _ Nil = error "grado en un polinomio vacío"
coeff x (P g c xs)
    | x == g = c 
    | otherwise = coeff x xs

insert :: Grade -> Coefficient -> Pol -> Pol
insert _ 0 p = p
insert g c Nil = (P g c Nil)
insert g c (P g1 c1 xs)
    | g == g1 = P g1 (c1+c) xs
    | g > g1 = P g c (P g1 c1 xs)
    | g < g1 = P g1 c1 (insert g c xs)

remove :: Grade -> Pol -> Pol
remove g (P g1 c1 xs)
    | g > g1 = P g1 c1 xs
    | g == g1 = xs
    | g < g1 = P g1 c1 (remove g xs)

list2Pol :: [Integer] -> Pol
list2Pol l = foldr (\(i,x) solResto -> insert i x solResto) Nil (zip(reverse[0..(length l-1)]) l)


sumP :: Pol -> Pol -> Pol 
sumP p Nil = p 
sumP Nil p = p 
sumP (P g1 c1 xs) (P g2 c2 ys)
    | g1 == g2 = P g1 (c1+c2) (sumP xs ys)
    | g1 < g2 = P g2 c2 (sumP (P g1 c1 xs) ys)
    | g1 > g2 = P g1 c1 (sumP xs (P g2 c2 ys))

foldPol :: (Grade -> Coefficient -> b -> b) -> b -> Pol -> b
foldPol f solBase p = plegPol p
    where 
        plegPol Nil = solBase
        plegPol (P g c xs) = P g c (plegPol p)

eval :: Integer -> Pol -> Integer
eval _ Nil = 0
eval _ (P 0 c Nil) = c 
eval x (P g1 c1 xs) = (x^g1 * c1) + eval x xs 
    
