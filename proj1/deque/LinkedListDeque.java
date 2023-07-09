package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>,Deque<T>{
    private int size;
    private DoubleLink sentinel;

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
//    private LinkedListDeque(T X){
//        sentinel = new DoubleLink(null,X,null);
//        sentinel.next = new DoubleLink(sentinel,X,sentinel);
//        sentinel.pre = sentinel.next;
//        size = 1;
//    }

    public LinkedListDeque(){
        sentinel = new DoubleLink(null,null,null);
//        sentinel.next = new DoubleLink(sentinel,null,sentinel);
        sentinel.next = sentinel;
        sentinel.pre = sentinel.next;
        size = 0;
    }

//    @Override
//    public void addFirst(T item){
//        if(isEmpty()){
//            sentinel.item = item;
//            sentinel.next.item = item;
//            size = 1;
//        } else {
//            sentinel.next = new DoubleLink(sentinel,item,sentinel.next);
//            sentinel.next.pre = sentinel.next;
//            size += 1;
//        }
//    }

    @Override
    public void addFirst(T item){
        /** Use circuit sentinel,
         * invariant:
         * first = sentinel.next
         * last = sentinel.pre
         */
        DoubleLink temp = sentinel.next;
        sentinel.next = new DoubleLink(sentinel,item,temp);
        temp.pre = sentinel.next;
        size += 1;
    }

//    @Override
//    public void addLast(T item){
//        if(isEmpty()){
//            sentinel.item = item;
//            sentinel.next.item = item;
//            size = 1;
//        } else {
//            sentinel.pre.next = new DoubleLink(sentinel.pre,item,sentinel);
//            sentinel.pre = sentinel.pre.next;
//            size += 1;
//        }
//    }
    @Override
    public void addLast(T item){
    sentinel.pre.next = new DoubleLink(sentinel.pre,item,sentinel);
    sentinel.pre = sentinel.pre.next;
    size += 1;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void printDeque(){
        StringBuilder returnSB = new StringBuilder();
        for(DoubleLink nodeT = sentinel.next; nodeT != sentinel ; nodeT = nodeT.next){
            if(nodeT.next == sentinel){
                returnSB.append(nodeT.item);
            } else {
                returnSB.append(nodeT.item + " ")
            }
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
        if (index >= size || index < 0){
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
        if (o instanceof Deque<?>){
            if (((LinkedListDeque<?>) o).size != size){
                return false;
            }

            for (int i = 0; i < size; i ++){
                if (!(get(i).equals(((LinkedListDeque<?>) o).get(i)))){
                    return false;
                }

            }
            return true;
        }
        return false;

    }

    private boolean containsHelper(Object item){
        for(T i : this){
            if (!item.equals(i)){
                return false;
            }
        }
        return true;
    }

    public T getRecursive(int index){
        if (index >= size || index < 0){
            return null;
        }
        else {
            DoubleLink node = sentinel.next;
            return getRecursiveHelper(node,index);
        }
    }

    private T getRecursiveHelper(DoubleLink node,int index){
        if(index == 0){
            return node.item;
        } else {
            return getRecursiveHelper(node.next,index - 1);
        }
    }
}
