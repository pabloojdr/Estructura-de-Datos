import DataStructures.Graph.DiGraph as G
import DataStructures.Graph.DiGraphDFT as DFT


reverseDiGraph :: Eq a => DiGraph a -> DiGraph a
reverseDiGraph g = mkDiGraphEdges (vertices g) maricones
    where
        maricones = [ v :-> w | w :-> v <- diEdges g]



restrictDiGraph :: Eq a => DiGraph a -> [a] -> DiGraph a
restrictDiGraph dg vert = mkDiGraphEdges vert putilla
    where
        putilla = [v :-> w | v :-> w <- diEdges dg, v `elem` vert, w `elem` vert]


type SCC a = [a]
sccOf :: Ord a => DiGraph a -> a -> SCC a
sccOf dg v0 = vertices (reverseDiGraph (restrictDiGraph dg vs))
    where 
        vs = dft dg v0

sccs :: Ord a => DiGraph a -> [SCC a]
sccs dg = [sccOf dg v | v <- vertices dg]



vacio :: DiGraph a -> Bool
vacio dg = null (G.vertices dg)


g1 :: DiGraph Char
g1 = mkDiGraphEdges ['A','B','C'] [('A':->'B'), ('B':->'C'), ('C':->'A')]


g1' :: DiGraph Char
g1' = mkDiGraphEdges ['A','B','C','D', 'E','F','G','H'] [('A':->'B'), ('B':->'E'), ('E':->'A'),
                                                        ('B':->'F'),('E':->'F'), ('F':->'G'), ('G':->'F'),
                                                        ('C':->'D'),('C':->'G'), ('D':->'C'),('D':->'H'),
                                                        ('H':->'D'),('H':->'G')]
