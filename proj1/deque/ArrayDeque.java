package deque;

public class ArrayDeque<T>{
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

    public boolean isEmpty(){
        if(size == 0){
            return true;
        } else {
            return false;
        }
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        if(!isEmpty()){
//            print out First
            for(int index = nextFirst+1; index < items.length; index++){
                System.out.print(items[index] + " ");
            }
//            what if not start at 0?
            for(int index = 0; index < nextLast; index++){
                System.out.print(items[index] + " ");
            }
        }
        System.out.println();
    }

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

    public T get(int index){
        if(index > size){
            return null;
        } else {
            int count = 0;
            if(lastHelper(nextLast) < firstHelper(nextFirst)){
                for(int i = nextFirst + 1; i <= items.length; i++){
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
