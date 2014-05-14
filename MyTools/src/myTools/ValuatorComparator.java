package myTools;

import java.util.Comparator;

public class ValuatorComparator<T> implements Comparator<T> {
    Valuator<T> valuator;

    public ValuatorComparator(Valuator<T> valuator) {
        this.valuator = valuator;
    }

    @Override
    public int compare(T o1, T o2) {
        return new Double(valuator.valuate(o2)).compareTo(new Double(valuator.valuate(o1)));
    }
}
