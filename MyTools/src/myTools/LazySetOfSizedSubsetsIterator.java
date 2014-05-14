package myTools;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LazySetOfSizedSubsetsIterator<T> implements Iterator<Set<T>> {
    private List<T> alphabet;
    private int[] indices;
    private int size;
    private boolean canAdvanceIndices;

    public LazySetOfSizedSubsetsIterator(List<T> alphabet, int size) {
        if (size < 0 || size > alphabet.size()) {
            throw new IllegalArgumentException("size");
        }
        this.alphabet = alphabet;
        this.size = size;
        initializeIndices();
        this.canAdvanceIndices = size != 0;
    }

    public int count() {
        return (int) MyMath.choose(alphabet.size(), size);
    }

    @Override
    public boolean hasNext() {
        return canAdvanceIndices;
    }

    public void advanceIndices() {
        for (int i = indices.length - 1; i >= 0; i--) {
            if (i == indices.length - 1) {
                if (indices[i] < alphabet.size() - 1) {
                    indices[i]++;
                    return;
                }
            } else if (indices[i] < indices[i + 1] - 1) {
                indices[i]++;
                for (int j = i + 1; j < indices.length; j++)
                    indices[j] = indices[j - 1] + 1;
                return;
            }
        }
        canAdvanceIndices = false;
    }

    public void initializeIndices() {
        this.indices = new int[size];
        for (int i = 0; i < size; i++) {
            indices[i] = i;
        }
    }

    @Override
    public Set<T> next() {
        Set<T> set = new HashSet<T>();
        for (int i = 0; i < size; i++) {
            set.add(alphabet.get(indices[i]));
        }

        advanceIndices();

        return set;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();

    }
}
