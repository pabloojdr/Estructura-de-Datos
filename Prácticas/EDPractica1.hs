-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º ETSI Informática. UMA
-- Práctica 1
--
-- Alumno: CAMPOY FERNÁNDEZ, PABLO JULIÁN
-------------------------------------------------------------------------------

module EDPractica1 where

import           Test.QuickCheck


-------------------------------------------------------------------------------
-- Ejercicio 1 - terna pitagórica
-------------------------------------------------------------------------------

esTerna :: Integer -> Integer -> Integer -> Bool
esTerna x y z = x*x + y*y == z*z

terna :: Integer -> Integer -> (Integer, Integer, Integer)
terna x y = (x*x - y*y, 2*x*y, x*x + y*y)

--p_ternas :: Integer -> Integer -> Bool
--p_ternas x y = x>0 && y>0 && x>y ==> esTerna l1 l2 h
  -- where
      --(l1,l2,h) = terna x y

-------------------------------------------------------------------------------
-- Ejercicio 2 - intercambia
-------------------------------------------------------------------------------

intercambia :: (a,b) -> (b,a)
intercambia (x,y) = (y,x) 

-------------------------------------------------------------------------------
-- Ejercicio 3 - ordena2 y ordena3
-------------------------------------------------------------------------------

-- 3.a
ordena2 :: Ord a => (a,a) -> (a,a)
ordena2 (x,y) 
   | x >= y = (y,x)
   | otherwise = (x,y)

p1_ordena2 x y = enOrden (ordena2 (x,y))
   where enOrden (x,y) = x <= y

p2_ordena2 x y = mismosElementos (x,y) (ordena2 (x,y))
   where
      mismosElementos (x,y) (x',y') =
           (x == x' && y == y') || (x == y' && y == x')

-- 3.b
ordena3 :: Ord a => (a,a,a) -> (a,a,a)
ordena3 (x,y,z) = inserta x (ordena2 (y,z))
   where 
      inserta u (v,w) -- v <= w
         | u <= v && u <= w = (u,v,w)
         | u <= w = (v,u,w) -- No es necesario poner u > v, pues al no cumplirse en la anterior salta a la segunda posibilidad
         | otherwise = (v,w,u)

   {-
   |x>y = ordena3(y,x,z)
   |y>z = ordena3(x,z,y)
   |x>z = ordena3(z,y,x)
   |otherwise = (x,y,z)
   -}

-- 3.c
{-p1_ordena3 x y z = ordenados(x,y,z) 
   where 
      ordenados(x,y,z) = x <= y && y <= z
-}

p1_ordena3 x y z = enOrden(ordena3 (x,y,z))
   where  
      enOrden (u,v,w) = u <= v && v <= w

-- hay que poner tipos cuando hagamos el quickCheck (p1_ordena3 :: Int -> Int -> Int -> Bool)
p2_ordena3 x y z =  x `pertenece`  t && y `pertenece` t && z `pertenece` t
   
   
   --mismosElementos (x,y,z) (ordena3 (x,y,z))
   where
      t = ordena3 (x,y,z)
      pertenece n (u,v,w) = n == u || n == v || n == w
      {-
      mismosElementos (x,y,z) (x',y',z') =
         (x,y,z)==(x',y',z') || (x,y,z) == (y',x',z') || (x,y,z) == (x',z',y') ||
            (x,y,z) == (z',y',x') || (x,y,z) == (z',x',y') || (x,y,z) == (y',z',x')
      -}


-------------------------------------------------------------------------------
-- Ejercicio 4 - max2
-------------------------------------------------------------------------------

-- 4.a
max2 :: Ord a => a -> a -> a
max2 x y
   | x >= y = x
   | y >= x = y

-- 4.b
-- p1_max2: el máximo de dos números x e y coincide o bien con x o bien con y.

p1_max2 x y =  max x y == x || max x y == y
       

-- p2_max2: el máximo de x e y es mayor o igual que x y mayor o igual que y.

p2_max2 x y = max x y >= x || max x y >= y 

-- p3_max2: si x es mayor o igual que y, entonces el máximo de x e y es x.

p3_max2 x y = x >= y ==> m == x
   where m = max2 x y

-- p3_max2 :: Int -> Int -> Property

-- p4_max2: si y es mayor o igual que x, entonces el máximo de x e y es y.

p4_max2 x y = y >= x ==> m == y
   where m = max2 x y

-------------------------------------------------------------------------------
-- Ejercicio 5 - entre (resuelto, se usa en el ejercicio 7)
-------------------------------------------------------------------------------

entre :: Ord a => a -> (a, a) -> Bool
entre x (inf,sup) = inf <= x && x <= sup

-------------------------------------------------------------------------------
-- Ejercicio 6 - tres iguales
-------------------------------------------------------------------------------

iguales3 :: Eq a => (a,a,a) -> Bool
iguales3 (x, y, z) = x == y && y == z

-------------------------------------------------------------------------------
-- Ejercicio 7 - descomponer
-------------------------------------------------------------------------------

-- Para este ejercicio nos interesa utilizar la función predefinida:
--
--              divMod :: Integral a => a -> a -> (a, a)
--
-- que calcula simultáneamente el cociente y el resto de la división entera:
--
--   *Main> divMod 30 7
--   (4,2)

-- 7.a
type TotalSegundos = Integer -- Sinónimo de tipo
type Horas         = Integer
type Minutos       = Integer
type Segundos      = Integer

descomponer :: TotalSegundos -> (Horas,Minutos,Segundos)
descomponer x = (h,m,s)
   where 
      (h,r) = divMod x 3600
      (m,s) = divMod r 60
{-
descomponer x = (horas, mins, segs)
   where 
      primero (x,y) = x
      segundo (x,y) = y
      horas = primero (divMod x 3600)
      mins = segundo (divMod (primero (divMod x 60)) 60)
      segs = segundo (divMod x 60)
-}      

-- 7.b
p_descomponer x = x>=0 ==> h*3600 + m*60 + s == x
                           && m `entre` (0,59)
                           && s `entre` (0,59)
          where (h,m,s) = descomponer x
-- No especifica los tipos cuando llama a quickCheck porque es monomórfica
-------------------------------------------------------------------------------
-- Ejercicio 8 - pesetas y euros
-------------------------------------------------------------------------------

unEuro :: Double
unEuro = 166.386

-- 8.a
pesetasAEuros :: Double -> Double
pesetasAEuros x = x / unEuro

-- 8.b
eurosAPesetas :: Double -> Double 
eurosAPesetas x = x * unEuro

-- 8.c
p_inversas x = eurosAPesetas (pesetasAEuros x) ~= x 

-------------------------------------------------------------------------------
-- Ejercicio 9 - aproximación
-------------------------------------------------------------------------------

infix 4 ~=
(~=) :: Double -> Double -> Bool
x ~= y = abs (x-y) < epsilon
   where 
      epsilon = 1/1000 

-------------------------------------------------------------------------------
-- Ejercicio 10 - raíces reales
-------------------------------------------------------------------------------

-- 10.a
raíces :: Double -> Double -> Double -> (Double, Double)
raíces x y z
   | y*y-4*x*z < 0 = error "Exception: Raíces no reales"
   | x == 0 = error "Exception: el coeficiente 'a' no puede ser cero"
   | otherwise = ((-y+sqrt(y*y-4*x*z))/2*x, (-y-sqrt(y*y-4*x*z))/2*x)
   
-- 10.b
p1_raíces a b c = esRaíz r1 && esRaíz r2
   where
      (r1,r2) = raíces a b c
      esRaíz r = a*r^2 + b*r + c ~= 0

p2_raíces a b c = a /= 0 && b*b-4*a*c >= 0 ==> esRaíz r1 && esRaíz r2
   where
      (r1,r2) = raíces a b c
      esRaíz r = a*r^2 + b*r + c ~= 0

-------------------------------------------------------------------------------
-- Ejercicio 11 - múltiplo
-------------------------------------------------------------------------------

esMúltiplo :: Integer -> Integer -> Bool
esMúltiplo x y 
   | x > y = mod x y == 0
   | y > x = mod x y == 0

-------------------------------------------------------------------------------
-- Ejercicio 12 - comparación
-------------------------------------------------------------------------------
infixl 1 ==>>
(==>>) :: Bool -> Bool -> Bool 
x ==>> y = if x && y || not x && not y then True
            else False


-------------------------------------------------------------------------------
-- Ejercicio 13 - años bisiestos 
-------------------------------------------------------------------------------

esBisiesto :: Integer -> Bool
esBisiesto x = mod x 4 == 0 ||(mod x 100 == 0 && mod x 400 == 0)

-------------------------------------------------------------------------------
-- Ejercicio 14 - potencia
-------------------------------------------------------------------------------

-- 14.a
potencia :: Integer -> Integer -> Integer
potencia b n 
   |n == 0 = 1
   |n > 0 = b * potencia b (n-1) 

-- 14.b
potencia' :: Integer -> Integer -> Integer
potencia' b n 
   | mod n 2 == 0 = pot*pot
   | n == 0 = 1
   |otherwise = b*poti*poti

   where 
      pot = potencia' b (div 2 n)
      poti = potencia' b (div (n-1) 2)

-- 14.c
p_pot b n =
   potencia b m == sol && potencia' b m == sol
   where sol = b^m
         m = abs n
-- 14.d
{-

Escribe en este comentario tu razonamiento: 

-}

-------------------------------------------------------------------------------
-- Ejercicio 15 - factorial
-------------------------------------------------------------------------------

factorial :: Integer -> Integer
factorial x
   |x == 0 = 1
   |x > 0 = x * factorial (x-1) 

-------------------------------------------------------------------------------
-- Ejercicio 16 - división exacta
-------------------------------------------------------------------------------

-- 16.a
divideA :: Integer -> Integer -> Bool
divideA x y = mod y x == 0
   -- |y/= 0 && x /=0 = mod y x == 0
   -- |y == 0 = error "Parámetro no válido"                    (Por lo visto no hay que aplicar condiciones)
   -- |x == 0 = error "No se puede dividir por 0"

-- 16.b
p1_divideA x y = y/=0 && y `divideA` x ==> div x y * y == x

-- 16.c
p2_divideA x y z = z/=0 && z `divideA` x && z `divideA` y ==> z `divideA` (x+y)

-------------------------------------------------------------------------------
-- Ejercicio 17 - división exacta
-------------------------------------------------------------------------------

mediana :: Ord a => (a,a,a,a,a) -> a
mediana (x,y,z,t,u)
   | x > z = mediana(z,y,x,t,u)
   | y > z = mediana(x,z,y,t,u)
   | t < z = mediana(x,y,t,u,z)
   | u < z = mediana(x,y,u,t,z)
   | otherwise = z