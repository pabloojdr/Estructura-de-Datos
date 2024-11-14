module TwoStacksDock where
import qualified Stack as S

data Dock a = D (S.Stack a) (S.Stack a)

d1 :: Dock Int
d1 = D (S.push 3 (S.push 2 (S.push 1 S.empty))) (S.push 4 (S.push 5 S.empty))


empty :: Dock a
empty = D (S.empty) (S.empty)

isEmpty :: Dock a -> Bool
isEmpty (D s1 s2) = S.isEmpty s1 && S.isEmpty s2

sign :: Dock a -> a
sign (D _ a) = S.top a 

isFirst :: Dock a -> Bool
isFirst (D s1 s2) = S.isEmpty s1 && S.isEmpty s2

isLast :: Dock a -> Bool
isLast (D s1 s2) = undefined

left :: Dock a -> Dock a 
left = undefined

right :: Dock a -> Dock a 
right = undefined

delete :: Dock a -> Dock A
delete = undefined






showD :: (Show a) => Dock a -> String
showD (D s1 s2) = "Dock:" ++ m
    where m = (show (stack2List s1))  ++ (show (stack2List s2)) 


stack2List :: (Show a) => S.Stack a -> [a]
stack2List s 
    | S.isEmpty s = []
    | otherwise = S.top s : (stack2List (S.pop s))