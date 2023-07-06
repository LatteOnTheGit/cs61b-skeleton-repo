package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>,Deque<T>{
    private int size;
    public DoubleLink sentinel;

    private class DoubleLink{
        public T item;
        public DoubleLink pre;
        public DoubleLink next;

        public DoubleLink(DoubleLink m, T i, DoubleLink n){
            pre = m;
            item = i;
            next = n;
        }
    }
    /** Use circuit sentinel,
     * invariant:
     * first = sentinel.next
     * last = sentinel.pre
     */
    public LinkedListDeque(T X){
        sentinel = new DoubleLink(null,X,null);
        sentinel.next = new DoubleLink(sentinel,X,sentinel);
        sentinel.pre = sentinel.next;
        size = 1;
    }

    public LinkedListDeque(){
        sentinel = new DoubleLink(null,null,null);
        sentinel.next = new DoubleLink(sentinel,null,sentinel);
        sentinel.pre = sentinel.next;
        size = 0;
    }

    @Override
    public void addFirst(T item){
        if(isEmpty()){
            sentinel.item = item;
            sentinel.next.item = item;
            size = 1;
        } else {
            sentinel.next = new DoubleLink(sentinel,item,sentinel.next);
            sentinel.next.pre = sentinel.next;
            size += 1;
        }
    }

    @Override
    public void addLast(T item){
        if(isEmpty()){
            sentinel.item = item;
            sentinel.next.item = item;
            size = 1;
        } else {
            sentinel.pre.next = new DoubleLink(sentinel.pre,item,sentinel);
            sentinel.pre = sentinel.pre.next;
            size += 1;
        }
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void printDeque(){
        StringBuilder returnSB = new StringBuilder();
        if(!isEmpty()){
            for(int i = 0; i < size - 1; i++){
                returnSB.append(this.get(i));
                returnSB.append(" ");
            }
            returnSB.append(this.get(size - 1));
        } else {
            System.out.println();
        }
        System.out.println(returnSB);
    }

    @Override
    public T removeFirst(){
        if(isEmpty()){
            return null;
        } else {
            DoubleLink itemToDelete = sentinel.next;
            sentinel.next.next.pre = sentinel;
            sentinel.next = sentinel.next.next;
            size -= 1;
            return itemToDelete.item;

        }
    }

    @Override
    public T removeLast(){
        if (isEmpty()){
            return null;
        } else {
            DoubleLink itemToDelete = sentinel.pre;
            sentinel.pre.pre.next = sentinel;
            sentinel.pre = sentinel.pre.pre;
            size -= 1;
            return itemToDelete.item;
        }
    }

    @Override
    public T get(int index){
        if (index > size){
            return null;
        } else {
            DoubleLink pointer = sentinel.next;
            for(int i = 0; i < index; i++){
                pointer = pointer.next;
            }
            return pointer.item;
        }
    }

    @Override
    public Iterator<T> iterator(){
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T>{
        private int wizPos;

        public LinkedListDequeIterator(){
            wizPos = 0;
        }

        @Override
        public boolean hasNext(){
            return wizPos < size;
        }

        @Override
        public T next(){
            T returnItem = get(wizPos);
            wizPos++;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof LinkedListDeque){
            if (((LinkedListDeque<?>) o).size != size){
                return false;
            }

            for (T i:this){
                if (!((LinkedListDeque<?>) o).containsHelper(i)){
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean containsHelper(Object item){
        for(T i : this){
            if (item.equals(i)){
                return true;
            }
        }
        return false;
    }

    public T getRecursive(int index){
        if (index > size){
            return null;
        } else {
            DoubleLink node = sentinel.next;
            return getRecursiveHelper(node,index);
        }
    }

    private T getRecursiveHelper(DoubleLink node,int index){
        if(index == 1){
            return node.item;
        } else {
            return getRecursiveHelper(node.next,index - 1);
        }
    }
}
