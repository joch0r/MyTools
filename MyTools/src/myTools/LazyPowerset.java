package myTools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LazyPowerset<T> implements Iterable<Set<T>> {

    private LazyPowersetIterator<T> iterator;

    public LazyPowerset(List<T> alphabet) {
        iterator = new LazyPowersetIterator<T>(alphabet);
    }

    @Override
    public Iterator<Set<T>> iterator() {
        return iterator;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        for (Set<String> s : new LazyPowerset<String>(list)) {
            System.out.println(s);
        }
    }
}
