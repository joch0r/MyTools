package myTools;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LazyPowersetIterator<T> implements Iterator<Set<T>> {
    private List<T> alphabet;
    private long index;
    protected final long maxValue;

    public LazyPowersetIterator(List<T> alphabet) {
        this.alphabet = alphabet;
        this.index = 0;
        this.maxValue = myTools.MyMath.pow(2, alphabet.size());
    }

    @Override
    public boolean hasNext() {
        return index < maxValue;
    }

    @Override
    public Set<T> next() {
        Set<T> set = new HashSet<T>();
        for (int j = 0; j < alphabet.size(); j++) {
            if ((index / (myTools.MyMath.pow(2, j))) % 2 == 1)
                set.add(alphabet.get(j));
        }

        index++;

        return set;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
