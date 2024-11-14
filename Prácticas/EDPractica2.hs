-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º ETSI Informática. UMA
-- Práctica 2
--
-- Alumno: APELLIDOS, NOMBRE
-------------------------------------------------------------------------------

module Practica2 where

import           Data.List       (nub, (\\))
import           Test.QuickCheck

-------------------------------------------------------------------------------
-- Ejercicio 4 - distintos
-------------------------------------------------------------------------------

--O(n^2)
distintos :: Eq a => [a] -> Bool
distintos [] = True
distintos (x:xs) = x `notElem` xs && distintos xs --Podría hacerse como un plegado, pero no de forma directa, y, aunque reduce la complejidad, es muy complicado

-------------------------------------------------------------------------------
-- Ejercicio 3 - reparte
-------------------------------------------------------------------------------

reparte :: [a] -> ([a], [a])
reparte [] = ([], [])
reparte [x] = ([x], [])
reparte (x:y:ys) = (x:primeros, y:segundos)  
    where (primeros, segundos) = reparte ys

-------------------------------------------------------------------------------
-- Ejercicio [empareja] de la lista de ejercicios extra
-------------------------------------------------------------------------------

-- Hoogle (https://www.haskell.org/hoogle/) es un buscador para el API de Haskell.
-- Usa Hoogle para encontrar información sobre la función 'zip'.

empareja :: [a] -> [b] -> [(a, b)] -- predefinida como zip
empareja [] _ = []
empareja _ [] = []
empareja (x:xs) (y:ys) = (x,y) : empareja xs ys

prop_empareja_OK :: (Eq b, Eq a) => [a] -> [b] -> Bool --Debemos tipar la propiedad porque si no trabaja con listas vacías
prop_empareja_OK xs ys = empareja xs ys == zip xs ys --quickCheck (prop_empareja_OK :: String -> [Int] -> Bool)

-------------------------------------------------------------------------------
-- Ejercicio 13 - desconocida
-------------------------------------------------------------------------------

-- Usa Hoogle para consultar la función 'and'.

desconocida :: Ord a => [a] -> Bool
desconocida xs = and [ x <= y | (x, y) <- zip xs (tail xs) ]

--Devuelve True si la lista está ordenada.

-------------------------------------------------------------------------------
-- Ejercicio 14 - inserta y ordena
-------------------------------------------------------------------------------

-- Usa Hoogle para consultar las funciones 'takeWhile' y 'dropWhile'.

-- 14.a - usando takeWhile y dropWhile
inserta :: (Ord a) => a -> [a] -> [a]
inserta x xs = (takeWhile (<x) xs ++ [x]) ++ dropWhile (<x) xs --también se puede poner takeWhile (<x) xs ++ x : dropWhile(<x) xs

-- 14.b - mediante recursividad
insertaRec :: (Ord a) => a -> [a] -> [a]
insertaRec x [] = [x]
insertaRec x (y:ys) 
    | y >= x = x:y:ys
    | otherwise = y:insertaRec x ys

-- 14.c

-- Las líneas de abajo no compilan hasta que completes los apartados
-- anteriores. Cuando los completes, elimina los guiones del comentario
-- y comprueba tus funciones.

prop_inserta :: Ord a => a -> [a] -> Property
prop_inserta x xs = desconocida xs ==> desconocida (inserta x xs)

prop_insertaRec :: Ord a => a -> [a] -> Property
prop_insertaRec x xs = desconocida xs ==> desconocida (insertaRec x xs)

-- 14.e - usando foldr
ordena :: Ord a => [a] -> [a]
ordena xs = foldr inserta [] xs 
    --where 
        --f cabeza solCola = --[5,1,3,1,2,6] ===> f 5 [1,1,2,3,6]  (Hace la misma función que inserta)
                             --[1,3,1,2,6] ===> [1,1,2,3,6]


-- Para definir prop_ordena_OK tendrás que usar el operador sobre listas (\\).
-- Consulta Hoogle.

-- 14.f
prop_ordena_OK :: untyped
prop_ordena_OK = undefined

-------------------------------------------------------------------------------
-- Ejercicio [mezcla] de la lista de ejercicios extra
-------------------------------------------------------------------------------

mezcla :: Ord a => [a] -> [a] -> [a]
--mezcla xs ys = ordena(xs++ys)
mezcla [] ys = ys
mezcla xs [] = xs
mezcla (x:xs) (y:ys)
    | x <= y = x:mezcla xs (y:ys)
    | otherwise = y:mezcla (x:xs) ys
-------------------------------------------------------------------------------
-- Ejercicio [pares] de la lista de ejercicios extra
-------------------------------------------------------------------------------

cotizacion :: [(String, Double)]
cotizacion = [("apple", 116), ("intel", 35), ("google", 824), ("nvidia", 67)]

buscarRec :: Eq a => a -> [(a,b)] -> [b]
buscarRec x ys = undefined

buscarC :: Eq a => a -> [(a, b)] -> [b]
buscarC x ys = undefined

buscarP :: Eq a => a -> [(a, b)] -> [b]
buscarP x ys = undefined

prop_buscar_OK :: (Eq a, Eq b) => a -> [(a, b)] -> Bool
prop_buscar_OK x ys = undefined

{-

Responde las siguientes preguntas si falla la propiedad anterior.

¿Por qué falla la propiedad prop_buscar_OK?

Realiza las modificaciones necesarias para que se verifique la propiedad.

-}

valorCartera :: [(String, Double)] -> [(String, Double)] -> Double
valorCartera cartera mercado = undefined

-------------------------------------------------------------------------------
-- Ejercicio 12 - concat
-------------------------------------------------------------------------------

concatP :: [[a]] -> [a]
concatP xss = undefined

concatC :: [[a]] -> [a]
concatC xss = undefined
