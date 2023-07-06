package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T>{
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque(){
        items = (T[])new Object[15];
        size = 0;
        nextLast = 0;
        nextFirst = items.length-1;
    }

    @Override
    public void addFirst(T item){
        if(size == items.length){
            resize((int) Math.ceil(size * 1.6));
        }
        items[nextFirst] = item;
        if(nextFirst - 1 < 0){
            nextFirst = items.length - 1;
        } else {
            nextFirst--;
        }
        size++;
    }

    @Override
    public void addLast(T item){
        if(size == items.length){
            resize((int) Math.ceil(size * 1.6));
        }
        items[nextLast] = item;
        if(nextLast + 1 > items.length - 1){
            nextLast = 0;
        } else {
            nextLast++;
        }
        size++;
    }

    public void resize(int capacity){
        T[] a = (T[]) new Object[capacity];
        if(lastHelper(nextLast) <= firstHelper((nextFirst))){
            System.arraycopy(items, nextFirst+1, a, 0, Math.max(0, items.length - nextFirst - 1));
            System.arraycopy(items, 0, a, Math.max(0, items.length - nextFirst), nextLast);
        } else {
            System.arraycopy(items, firstHelper((nextFirst)), a, 0, size);
        }
        nextFirst = a.length-1;
        nextLast = size;
        items = a;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void printDeque(){
        StringBuilder printSB = new StringBuilder();
        for(int i = 0; i < size - 1; i++){
            printSB.append(get(i).toString());
            printSB.append(" ");
        }
        printSB.append(get(size - 1));
        System.out.println(printSB);
    }

    @Override
    public T removeFirst(){
        if(isEmpty()){
            return null;
        } else {
            if(nextFirst + 1 < items.length){
                nextFirst++;
                size--;
            } else {
                nextFirst = 0;
                size--;
            }
            T removed = items[nextFirst];
            if(items.length > 15 && (double)size/items.length < 0.25){
                resize((int) Math.ceil(size * 1.6));
            }
            return removed;
        }
    }

    @Override
    public T removeLast(){
        if(isEmpty()){
            return null;
        } else {
            if(nextLast - 1 < 0){
            nextLast = items.length - 1;
            size--;
        } else {
                nextLast--;
                size--;
            }
            T removed = items[nextLast];
            if(items.length > 15 && (double)size/items.length < 0.25){
                resize((int) Math.ceil(size * 1.6));
            }
            return removed;
        }
    }

    @Override
    public T get(int index){
        if(index > size){
            return null;
        } else {
            int count = 0;
            if(lastHelper(nextLast) < firstHelper(nextFirst)){
                for(int i = nextFirst + 1; i <= items.length - 1; i++){
                    if(count == index){
                        return items[i];
                    }
                    count++;
                }
                for(int i = 0; i <= nextLast - 1; i++){
                    if(count == index){
                        return items[i];
                    }
                    count++;
                }
                return null;
            } else {
                for(int i = firstHelper(nextFirst); i <= lastHelper(nextLast); i++){
                    if(count == index){
                        return items[i];
                    }
                    count ++;
                }
                return null;
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder returnSB = new StringBuilder("{");
        for (int i = 0; i < size - 1; i++){
            returnSB.append(get(i).toString());
            returnSB.append(", ");
        }
        returnSB.append(get(size - 1));
        returnSB.append("}");
        return returnSB.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;

        public ArrayDequeIterator(){
            wizPos = 0;
        }
        @Override
        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T returnItem = get(wizPos);
            wizPos++;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof ArrayDeque){
            if(((ArrayDeque<?>) o).size != size){
                return false;
            }

            for (T x : this){
                if (!((ArrayDeque<?>) o).containsHelper(x)){
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean containsHelper(Object item){
        for(T i:this){
            if(item.equals(i)){
                return true;
            }
        }
        return false;
    }

    private int firstHelper(int first){
        if(first + 1 > items.length - 1){
            return 0;
        } else {
            return first + 1;
        }
    }

    private  int lastHelper(int last){
        if(last - 1 < 0){
            return items.length - 1;
        } else {
            return last - 1;
        }
    }
}
