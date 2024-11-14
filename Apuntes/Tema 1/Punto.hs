module Punto where

unPunto :: (Int, Int)
unPunto = (5,2)

esOrigen :: (Int, Int) -> Bool
esOrigen (x,y) = x==0 && y==0

-- Patrón variable
esOrigen1 :: (Int, Int) -> Bool
esOrigen1 x = fst x == 0 && snd x == 0 

-- Patrón tupla
esOrigen2 :: (Int, Int) -> Bool
esOrigen2 (x,y) = x == 0 && y == 0
-- si unsamos unPunto, tenemos que x = 5 e y = 2

-- Patrón constante (Es la mejor opción)
esOrigen3 :: (Int, Int) -> Bool
esOrigen3 (0,0) = True -- Primero se comprueba la primera condición
esOrigen3 x = False -- Solo si la primera ecuación falla, se ejecuta la segunda

-- Patrón subrayado
esOrigen4 :: (Int, Int) -> Bool
esOrigen4 (0,0) = True
esOrigen4 _ = False -- Patrón subrayado

espejo :: (Int, Int) -> (Int, Int)
espejo (x,y) = (y,x)

inverso :: Double -> Double
inverso x
    | x == 0 = error "No se puede dividir por 0"
    |otherwise = 1/x

paridad :: Int -> String
paridad x = if even x then "es par"
            else "es impar"

estaEn :: (Int, Int) -> String
estaEn (0,0) = "en el origen"
estaEn (0,_) = "en el eje de ordenadas"
estaEn (_,0) = "en el eje de abscisas"
estaEn (x,y) | x == y = "en la bisectriz" -- Haciendo esto no compila porque no se pueden repetir variables en un patrón (tenemos que añadir el |, que significa "siempre que")
estaEn _ = "en cualquier otro sitio" -- También compila usando estaEn (_,_)

