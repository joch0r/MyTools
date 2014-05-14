package myTools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyCollectionTools {
    public static <T> Set<List<T>> getAllListsOver(Set<T> alphabet, int size) {
        if (size < 0) {
            throw new IllegalArgumentException("length must be > 0");
        }
        if (size == 0) {
            // java collection initialization lijkt niet zo lekker als c sharp.
            // return (List<ArrayList<CharValuation>>) Arrays.asList(new
            // ArrayList<CharValuation>());
            // geeft gezeik (geen list covariance)
            // andere oplossing met anonymous subclass met anonymous initializer
            // is lelijk en geeft warnings
            List<T> list = new ArrayList<T>();
            Set<List<T>> lists = new HashSet<List<T>>();
            lists.add(list);
            return lists;
        } else {
            Set<List<T>> lists = new HashSet<List<T>>();

            for (List<T> smallerList : getAllListsOver(alphabet, size - 1)) {
                for (T e : alphabet) {
                    List<T> list = new ArrayList<T>(smallerList);
                    list.add(e);
                    lists.add(list);
                }
            }

            return lists;
        }
    }

    public static <T> Set<Set<T>> getAllSetsOver(Set<T> alphabet, int size) {
        if (size < 0) {
            throw new IllegalArgumentException("length must be > 0");
        }
        if (size == 0) {
            // java collection initialization lijkt niet zo lekker als c sharp.
            // return (List<ArrayList<CharValuation>>) Arrays.asList(new
            // ArrayList<CharValuation>());
            // geeft gezeik (geen list covariance)
            // andere oplossing met anonymous subclass met anonymous initializer
            // is lelijk en geeft warnings
            Set<T> set = new HashSet<T>();
            Set<Set<T>> sets = new HashSet<Set<T>>();
            sets.add(set);
            return sets;
        } else {
            Set<Set<T>> smallerSets = getAllSetsOver(alphabet, size - 1);
            Set<Set<T>> sets = new HashSet<Set<T>>((int) ((smallerSets.size() * alphabet.size()) / 0.75 + 10));

            for (Set<T> smallerSet : smallerSets) {
                for (T e : alphabet) {
                    if (!smallerSet.contains(e)) {
                        Set<T> set = new HashSet<T>(smallerSet);
                        set.add(e);
                        sets.add(set);
                    }
                }
            }

            return sets;
        }
    }

    public static <T> Set<Set<T>> nonRecursiveGetAllSetsOver(List<T> alphabet, int length) {
        int size = alphabet.size();
        int count = MyMath.pow(size, length);
        Set<Set<T>> sets = new HashSet<Set<T>>((int) Math.ceil(count / 0.75) + 10);
        for (int i = 0; i < count; i++) {
            Set<T> set = new HashSet<T>(length);
            for (int j = 0; j < length; j++) {
                set.add(alphabet.get((int) (i / (MyMath.pow(size, j))) % size));
            }
            sets.add(set);
        }
        return sets;
    }

    public static void main(String[] args) {
        long nano = System.nanoTime();

        HashSet<String> set = new HashSet<String>();
        for (int i = 0; i < 5; i++)
            set.add(Integer.toString(i));
        System.out.println(getAllSetsOver(set, 3).size());

        System.out.println(String.format("%sms", (System.nanoTime() - nano) / 1000000));

        nano = System.nanoTime();

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 60; i++)
            list.add(Integer.toString(i));
        System.out.println(nonRecursiveGetAllSetsOver(list, 3).size());

        System.out.println(String.format("%sms", (System.nanoTime() - nano) / 1000000));

        set = new HashSet<String>();
        set.add("1");
        set.add("2");
        set.add("3");
        set.add("4");
        set.add("5");
        System.out.println(getAllSetsOver(set, 3));
    }
}
