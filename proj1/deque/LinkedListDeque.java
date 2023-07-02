package deque;

public class LinkedListDeque<T> {
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

    public boolean isEmpty(){
        if(size==0){
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
            for(DoubleLink i = sentinel.next; i == sentinel; i = i.next){
                System.out.print(i.item + " ");
            }
        }
        System.out.println();
    }

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

    public T get(int index){
        if (index > size){
            return null;
        } else {
            DoubleLink pointer = sentinel.next;
            for(int i = 1; i < index; i++){
                pointer = pointer.next;
            }
            return pointer.item;
        }
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
