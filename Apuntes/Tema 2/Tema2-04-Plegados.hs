-------------------------------------------------------------------
-- Estructuras de Datos
-- Grados en Ingeniería Informática, del Software y de Computadores
-- Tema 2. Características de la Programación Funcional
-- Pablo López
-------------------------------------------------------------------

module Tema2Plegados where

import           Data.Char

{-
   Contenido:

    15. Plegado a la derecha (foldr)
    16. Plegado a la izquierda (foldl)
-}

-- | 14. Plegado a la derecha (foldr)
------------------------------------------------------------

{-
   Recuerda que la mayoría de las funciones recursivas sobre listas
   obedecen el siguiente esquema:


          fun (x:xs) ----->  "añadir" x a solCola
             |
          fun    xs  ----->   solCola
             :                   :
             :                   :
          fun    []   ---->  solCasoBase


    En Haskell este esquema se expresa con dos ecuaciones:

          fun []     = solCasoBase
          fun (x:xs) = añadir x (fun xs)
                                    \
                                 solCola

   La parte creativa consiste en determinar:

      - 'solCasoBase', la solución del caso base (suele ser inmediato)

      - 'añadir', la función que "añade" la cabeza 'x' a la solución
        de la cola '(fun xs)'

   Recuerda también que los operadores binarios se pueden escribir
   como funciones prefijas; por ejemplo:

                  x + suma xs

   se puede escribir como:

                  (+) x (suma xs)

   Este truco permite escribir todas las siguientes funciones
   recursivas aplicando el mismo esquema.
-}

-- |
-- >>> suma [1,2,3,4,5]
-- 15
suma :: [Int] -> Int -- predefinida como sum
suma []     = 0 --solBase
suma (x:xs) = (+) x (suma xs) -- f x solXs (Es mejor escribirla como x + suma xs)

-- |
-- >>> producto [1,2,3,4,5]
-- 120
producto :: [Int] -> Int -- predefinida como product
producto []     = 1 --solBase
producto (x:xs) = (*) x (producto xs) -- f x SolXs (mejor escribirla como x * producto xs)

-- |
-- >>> conjunción [True, 1 < 2, 'a' == 'a']
-- True
-- >>> conjunción [True, 1 < 2, 'a' == 'b']
-- False
conjunción :: [Bool] -> Bool -- predefinida como and
conjunción [] = True -- solBase
conjunción (x:xs) = (&&) x (conjunción xs) -- f x solBase (mejor escribirlo  x && (conjunción xs)) 

-- |
-- >>> aplana [ [1,2], [3], [], [4,5,6]]
-- [1,2,3,4,5,6]
aplana :: [[a]] -> [a] -- predefinida como concat
aplana []       = [] --solBase
aplana (xs:xss) =  (++) xs (aplana xss) -- f x solBase

{-
   Es obvio que las funciones anteriores se aparecen mucho. Podemos
   hacer como con 'map' y 'filter' y abstraer las diferencias:

     - todas las funciones tienen el tipo [a] -> b

     - todas las funciones necesitan un valor para solCasoBase

     - todas las funciones necesitan una función que ""añada"
       la cabeza `x` a la solución de la cola
-}
--       f --> solBase --> xs --> resultado
plegar :: (a -> b -> b) -> b -> [a] -> b -- predefinida como foldr (el tipo de función tiene como parametros uno de tipo a y otro de tipo b, este último es así porque se trata de la solución de otra función)
plegar f solBase xs = recLista xs
  where
    recLista [] = solBase
    recLista (x:xs) = f x (recLista xs)

-- Todas las funciones anteriores pueden definirse mediante 'foldr'

-- |
-- >>> sumaR [1,2,3,4,5]
-- 15
sumaR :: [Int] -> Int -- predefinida como sum
sumaR xs = plegar (+) 0 xs

-- |
-- >>> productoR [1,2,3,4,5]
-- 120
productoR :: [Int] -> Int -- predefinida como product
productoR xs = plegar (*) 1 xs

-- |
-- >>> conjunciónR [True, 1 < 2, 'a' == 'a']
-- True
-- >>> conjunciónR [True, 1 < 2, 'a' == 'b']
-- False
conjunciónR :: [Bool] -> Bool -- predefinida como and
conjunciónR xs = plegar (&&) True xs

-- |
-- >>> aplanaR [ [1,2], [3], [], [4,5,6]]
-- [1,2,3,4,5,6]
aplanaR :: [[a]] -> [a]  -- predefinida como concat
aplanaR xs = plegar (++) [] xs

--Podemos usar foldr (++) [] [ [1,2], [3], [], [4,5,6]] en lugar de la función
{-
   A veces la función que "añade" la cabeza a la solución de la cola
   es un poco más complicada. Por ejemplo, puede incluir un
   condicional. En tal caso, podemos definirla localmente en un where:

           fun []     = solCasoBase
           fun (x:xs) = f x (fun xs)
             where
                 f cabeza cola = ...
-}

-- |
-- >>> borraTodas 1 [1,2,1,3,3,1,2,1]
-- [2,3,3,2]
-- >>> borraTodas 't' "Haskell"
-- "Haskell"
borraTodas :: Eq a => a -> [a] -> [a]
borraTodas _ [] = []
borraTodas x (y:ys) = f y (borraTodas x ys)
  where
    f cabeza solCola
      | cabeza == x = solCola
      | otherwise   = cabeza:solCola

-- Ejercicio: Define la funcion borraTodas usando `foldr`

borraTodasP :: Eq a => a -> [a] -> [a]
borraTodasP x (y:ys) = foldr (borraTodas) [] ys
      

-- | 15. Plegado a la izquierda (foldl)
------------------------------------------------------------

{-
   Recuerda que la mayoría de las funciones recursivas con acumulador
   sobre listas siguen el siguiente esquema

                    xs                  ac      valor inicial del acumulador
                                               /
         fun [x1,x2,x3,...,xn]      solCasoBase
                   |                    |
         fun    [x2,x3,...,xn]      "añade" x1 a acumulador
                   |                    |
         fun       [x3,...,xn]      "añade" x2 a acumulador
                   :                    :
                   :                    :
         fun               []        el acumulador tiene la solución

   En Haskell este esquema se expresa con las siguientes ecuaciones:

          fun xs = funAc xs solCasoBase
             where
               funAc []     ac = ac
               funAc (x:xs) ac = funAc x (actualiza ac x)

   La parte creativa consiste en determinar:

      - 'solCasoBase', la solución del caso base (suele ser inmediato)

      - 'actualiza', la función que "actualiza" el acumulador 'ac'
        para que tenga en cuenta la cabeza 'x' (el acumulador debe ser
        la solución de la prefijo visitado)

   Las siguientes funciones aplican recursión con acumulador (de nuevo aplicamos
   el truco de escribir los operadores como funciones prefijas).
-}

-- |
-- >>> suma' [1,2,3,4,5]
-- 15
suma' :: [Int] -> Int -- predefinida como sum
suma' xs = sumaAc xs 0
  where
    sumaAc [] ac     = ac
    sumaAc (x:xs) ac = sumaAc xs ((+) ac x)

-- |
-- >>> producto' [1,2,3,4,5]
-- 120
producto' :: [Int] -> Int -- predefinida como product
producto' xs = productoAc xs 1
  where
    productoAc [] ac     = ac
    productoAc (x:xs) ac = productoAc xs ((*) ac x)

-- |
-- >>> conjunción' [True, 1 < 2, 'a' == 'a']
-- True
-- >>> conjunción' [True, 1 < 2, 'a' == 'b']
-- False
conjunción' :: [Bool] -> Bool -- predefinida como and
conjunción' xs = conjunciónAc xs True
  where
    conjunciónAc [] ac     = ac
    conjunciónAc (x:xs) ac = conjunciónAc xs ((&&) ac x)

{-
   Es obvio que las funciones anteriores se aparecen mucho. Podemos
   abstraer las diferencias:

     - todas las funciones tienen el tipo [a] -> b

     - todas las funciones necesitan un valor para solCasoBase

     - todas las funciones necesitan una función que actualice el
       acumulador con el valor de la cabeza
-}

plegarAc :: (b -> a -> b) -> b -> [a] -> b -- predefinida como foldl
plegarAc f solBase xs = recLista xs
  where 
    recLista [] = solBase
    recLista (x:xs) = f (recLista xs) x
-- |
-- >>> sumaL [1,2,3,4,5]
-- 15
sumaL :: [Int] -> Int
sumaL xs = plegarAc (+) 0 xs

-- |
-- >>> productoL [1,2,3,4,5]
-- 120
productoL :: Num a => [a] -> a
productoL xs = plegarAc (*) 1 xs 

-- |
-- >>> conjunciónL [True, 1 < 2, 'a' == 'a']
-- True
-- >>> conjunciónL [True, 1 < 2, 'a' == 'b']
-- False
conjunciónL :: [Bool] -> Bool
conjunciónL xs = plegarAc (&&) True xs

-- Ejercicio: Define las siguientes funciones usando un plegado (foldr o foldl).

-- |
-- >>> longitud "haskell"
-- 7
longitud :: [a] -> Integer -- predefinida como length
longitud xs = foldr (\ _ n -> n+1) 0 xs

-- Una cadena es una palabra si está formada solo por letras.

-- |
-- >>> esPalabra "haskell"
-- True
-- >>> esPalabra "haskell2015"
-- False
esPalabra :: String -> Bool
esPalabra xs = foldr f True xs
  where
    f cabeza solCola = isLetter cabeza && solCola

-- |
-- >>> todasMayúsculas "Haskell"
-- False
todasMayúsculas :: String -> Bool
todasMayúsculas xs = foldr f True xs
  where
    f cabeza solCola = isUpper cabeza && solCola

-- |
-- >>> pares [1..10]
-- [2,4,6,8,10]
pares :: [Integer] -> [Integer]
pares xs = foldr f [] xs
  where
    f cabeza solCola 
      | even cabeza = cabeza:solCola
      | otherwise = solCola

-- |
-- >> apariciones 'a' "Abracadabra"
-- 4
apariciones :: Eq a => a -> [a] -> Integer
apariciones x xs = foldr f 0 xs
  where 
    -- a -> Integer -> Integer
    f cabeza solCola 
      | cabeza == x  = solCola + 1
      | otherwise = solCola

-- Purgar una lista es quitar las repeticiones.

-- |
-- >>> purga "Abracadabra"
-- "Abracd"
purga :: Eq a => [a] -> [a] -- predefinida como nub
purga xs = foldr f [] xs
  where 
    f cabeza (x:xs)
    | cabeza == x = xs
    | otherwise = cabeza:solCola
-- |
-- >>> pertenece 4 [1..5]
-- True
-- >>> pertenece 't' "Haskell"
-- False
pertenece :: Eq a => a -> [a] -> Bool -- predefinida como elem
pertenece x xs = undefined

-- |
-- >>> paresImpares [1..9]
-- ([2,4,6,8],[1,3,5,7,9])
paresImpares :: [Int] -> ([Int], [Int])
paresImpares xs = undefined
