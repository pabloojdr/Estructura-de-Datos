-----------------------------------------------------------------------------
--APUNTES TEMA 2
-----------------------------------------------------------------------------

{-
RECURSIVIDAD
suma[7,3,5,1]
       ------
       Supongo la solución de la cola ==> 7 + 9


RECURSIVIDAD CON ACUMULADOR
suma'[7,3,5,1]

acumulador ==> 10 + 5 =15 + 1 = 16 (conozco la solución de los ya visitados)

suma'[7,3,5,1]
      ------- (xs) ==> suma = 0

suma'[*,3,5,1]
                   ==> suma = 7

suma'[*,*,5,1]
                   ==> suma = 10

...





}