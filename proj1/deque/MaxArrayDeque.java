package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;
    public MaxArrayDeque(Comparator<T> c){
        comparator = c;
    }

    public T max(Comparator<T> c){
        T max = get(0);

        for (int i = 1; i < size(); i++){
            T current = get(i);
            if (c.compare(max, current) < 0){
                max = current;
            }
        }
        return max;
    }

    public T max(){
        return max(comparator);
    }
}
