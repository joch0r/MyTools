package myTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LazySetOfSizedSubsets<T> implements Iterable<Set<T>> {
    private LazySetOfSizedSubsetsIterator<T> iterator;
    private int size;
    private List<T> alphabet;

    public LazySetOfSizedSubsets(List<T> alphabet, int size) {
        this.size = size;
        this.alphabet = new ArrayList<T>(alphabet);
        this.iterator = new LazySetOfSizedSubsetsIterator<T>(this.alphabet, this.size);
    }

    @Override
    public Iterator<Set<T>> iterator() {
        return iterator;
    }

    public long size() {
        return iterator.count();
    }

    public Set<T> randomElement() {
        Set<T> result = new HashSet<T>();
        for (int i = 0; i < size; i++) {
            T e;
            do {
                e = alphabet.get((int) Math.floor(Math.random() * alphabet.size()));
            } while (result.contains(e));
            result.add(e);
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        for (Set<String> s : new LazySetOfSizedSubsets<String>(list, 2)) {
            System.out.println(s);
        }
        System.out.println(new LazySetOfSizedSubsets<String>(list, 2).size());

        list = new ArrayList<String>();
        for (int i = 0; i < 500; i++)
            list.add(Integer.toString(i));
        int c = 0;
        for (Set<String> s : new LazySetOfSizedSubsets<String>(list, 3)) {
            c++;
        }
        System.out.println(c);
    }
}
