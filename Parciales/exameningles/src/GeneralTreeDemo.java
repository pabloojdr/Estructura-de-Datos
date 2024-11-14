import dataStructures.list.ArrayList;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.tree.GeneralTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class GeneralTreeDemo {

    public static void main(String[] args) {
    /*
        List<GeneralTree<Integer>> trees = buildTrees();
        String[] treeNames = {"empty", "singleton", "tree1", "tree2", "tree3"};
        int[] rotateValues = {5, 5, 6, 8, 4};
        int[] decorateValues = {1, 4, 3, 2, 7};

        for (int i = 0; i < trees.size(); i++) {
            System.out.println("Tests for tree " + treeNames[i]);
            //saveTreeToDot(treeNames[i], trees.get(i));
            testMaximum(trees.get(i));
            testNumBranches(trees.get(i));
            testAtLevel(trees.get(i));
            testRotate(treeNames[i], trees.get(i), rotateValues[i]);
            trees = buildTrees();
            testDecorate(treeNames[i], trees.get(i), decorateValues[i]);
            System.out.println("====================================================");
        }
    }

    private static <T extends Comparable<? super T>> void testMaximum(GeneralTree<T> tree) {
        try {
            System.out.println("maximum = " + tree.maximum());
        } catch (Exception e) {
            System.out.println("maximum = " + e.getMessage());
        }
    }

    private static <T extends Comparable<? super T>> void testNumBranches(GeneralTree<T> tree) {
        System.out.println("number of branches = " + tree.numBranches());
    }

    private static <T extends Comparable<? super T>> void testAtLevel(GeneralTree<T> tree) {
        System.out.println("show tree by levels:");
        int i = 0;
        List<T> level;
        do {
            level = tree.atLevel(i);
            System.out.println("\tat level " + i + " = " + level);
            i++;
        } while (!level.isEmpty());
    }

    private static <T extends Comparable<? super T>> void testRotate(String name, GeneralTree<T> tree, T x) {
        tree.rotateLeftAt(x);
        System.out.println(name + "_RotatedAt_" + x + ":" + tree.toString());
        //saveTreeToDot(name + "_RotatedAt_" + x, tree);
    }

    private static <T extends Comparable<T>> void testDecorate(String name, GeneralTree<T> tree, T x) {
        tree.decorate(x);
        System.out.println(name + "_DecoratedWith_" + x + ":" + tree.toString());
        //saveTreeToDot(name + "_DecoratedWith_" + x, tree);
    }

    private static List<GeneralTree<Integer>> buildTrees() {
        GeneralTree<Integer> empty = new GeneralTree<>();

        GeneralTree<Integer> singleton = new GeneralTree<>(5);

        List<?> l1 = new LinkedList<>();
        l1.append(3);
        l1.append(8);
        GeneralTree<Integer> tree1 = new GeneralTree<>((Integer) 6, l1);

        GeneralTree<Integer> tree2 =
                new GeneralTree<>(8,
                        new GeneralTree<>(27,
                                new GeneralTree<>(),
                                new GeneralTree<>(3)),
                        new GeneralTree<>(15,
                                new GeneralTree<>(21),
                                new GeneralTree<>(6)));
        GeneralTree<Integer> tree3;
        tree3 = new GeneralTree<>(16,
                new GeneralTree<>(8,
                        new GeneralTree<>(4,
                                new GeneralTree<>(),
                                new GeneralTree<>(6)),
                        new GeneralTree<>(12)),
                new GeneralTree<>(32,
                        new GeneralTree<>(24,
                                new GeneralTree<>(20),
                                new GeneralTree<>()),
                        new GeneralTree<>(64,
                                new GeneralTree<>(48),
                                new GeneralTree<>(82))));

        List<GeneralTree<Integer>> trees = new ArrayList<>();
        trees.append(empty);
        trees.append(singleton);
        trees.append(tree1);
        trees.append(tree2);
        trees.append(tree3);

        return trees;
    }

    private static <T extends Comparable<? super T>> void saveTreeToDot(String name, GeneralTree<T> tree) {
        try (PrintWriter pw = new PrintWriter(new File("dot/" + name + ".dot"))) {
            pw.println(tree.toDot(name));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

     */
}
}
