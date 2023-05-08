package BinarySearchTree;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

/**
 *
 * @author jaime
 * @param <T>
 */
public class BinarySearchTree<T extends Comparable<T>> implements BinarySearchTreeADT<T>{
    private BSTNode<T> root;
    private int count;
    
    public BinarySearchTree(){
        root = null;
        count = 0;
    }
    
    //Método de fuerza bruta
    @Override
    public BSTNode<T> searchRec(T element){
        if(element == null) throw
                new RuntimeException("Element is null");
        
        return search(root, element);
    }
    
    private BSTNode<T> search(BSTNode<T> actual, T element){
        if(actual == null)
            return null;
        if(actual.getElement().equals(element))
            return actual;
        
        BSTNode<T> res = search( actual.getLeft(), element);
            if(res == null)
                res = search( actual.getRight(), element);
        return res;
    }
    
    @Override
    public void add(T element){
        BSTNode<T> actual;
        BSTNode<T> parent = new BSTNode<>();
        BSTNode<T> nuevo = new BSTNode<>(element);
        
        if(root == null){
            root = nuevo;
            count++;
            return;
        }
        
        actual = root;
        while(actual != null){
            parent = actual;
            if(element.compareTo(actual.getElement()) <= 0 )
                actual = actual.getLeft();
            else
                actual = actual.getRight();
        }
        parent.hang(nuevo);
        count++;
    }
    
    @Override
    public T remove(T element){
        if(element == null) throw
                new RuntimeException("Element is null");
        
        BSTNode<T> actual = search(element);
        BSTNode<T> parent;
        BSTNode<T> child;
        T aux = null;
        
        if(actual == null)
            throw new RuntimeException("No such element in BST");
        
        count--;
        
        //Caso 1: 0 hijos, hoja sola
        if(actual.getLeft() == null && actual.getRight() == null){
            //Caso 1a
            if(actual.equals(root)){
                aux = root.getElement();
                root = null;
            }
            //Caso 1b
            else{
                parent = actual.getParent();

                if(parent.getElement().compareTo(element) < 0){
                    aux = actual.getElement();
                    parent.setLeft(null);

                }
                else{
                    aux = actual.getElement();
                    parent.setRight(null);

                }    
            }
        }
        else{
            //Caso 2: 2 hijos
            if(actual.getLeft() != null && actual.getRight() != null){
                aux = actual.getElement();
                parent = actual;
                actual = actual.getRight();

                while(actual.getLeft() != null)
                    actual = actual.getLeft();

                if(actual.getRight() != null)
                    actual.getParent().setRight(actual.getRight());

                parent.setElement(actual.getElement());
                actual.getParent().setLeft(null);
            }
            //Caso 3: 1 hijo
            else{
                if(actual.getLeft() != null || actual.getRight() != null){
                    if(actual.getLeft() != null)
                        child = actual.getLeft();
                    else
                        child = actual.getRight();

                    if(actual.equals(root)){
                        aux = actual.getElement();
                        root = child;
                    }
                    else{
                        aux = actual.getElement();
                        parent = actual.getParent();
                        
                        if(actual.getElement().compareTo(parent.getElement())>0)
                            parent.setRight(null);
                        else
                            parent.setLeft(null);
                        
                        parent.hang(child);
                    }
                }
            }
        }
        
        return aux;
    }
    
    private BSTNode<T> search(T element){
        if(element == null)
            throw new RuntimeException("Element is null");
        
        BSTNode<T> actual = root;
        
        while(actual != null && !actual.getElement().equals(element)){
            if(element.compareTo(actual.getElement()) < 0)
                actual = actual.getLeft();
            else
                actual = actual.getRight();
        }
            
        return actual;
    }
   
    @Override
    public T findMin(){
        BSTNode<T> actual;
        
        if(root == null) throw 
                new RuntimeException("Binary Tree is empty");
        
        actual = root;
        while(actual.getLeft() != null)
            actual = actual.getLeft();
        
        
        return actual.getElement();
    }
    
    @Override
    public T findMax(){
        BSTNode<T> actual;
        
        if(root == null) throw 
            new RuntimeException("Binary Tree is empty");
        
        actual = root;
        while(actual.getRight() != null)
            actual = actual.getRight();
                
        return actual.getElement();
    }
    
    @Override
    public boolean isEmpty(){
        return size() == 0;
    }

    @Override
    public int size() {
        return count;
    }
    
    @Override
    public boolean contains(T element) {
        if(element == null)
            throw new RuntimeException("Element is null");
         
        return contains(element, root);
    }  
    
    //Métodos iterativos
    /*@Override
    public Iterator<T> preOrderIterator(){
        Stack<BinaryNode<T>> stackAux = new Stack<>();
        ArrayList<T> list = new ArrayList<>();
        BSTNode<T> actual;
        stackAux.push(root);
        
        while(!isEmpty()){
            actual = stackAux.pop();
            list.add(actual.getElement());
            if(actual.getRight() != null)
                stackAux.push(actual.getRight());
            if(actual.getLeft() != null)
                stackAux.push(actual.getLeft());
        }
       
        return list.iterator();
    }*/
    
    /*@Override
    public Iterator<T> postOrderIterator(){
        Queue<BinaryNode<T>> queueAux = new ArrayDeque<>();
        ArrayList<T> list = new ArrayList<>();
        BSTNode<T> actual;
        queueAux.add(root);
        
        while(!queueAux.isEmpty()){
            actual = queueAux.remove();
            list.add(actual.getElement());
            if(actual.getLeft() != null)
                queueAux.add(actual.getLeft());
            if(actual.getRight() != null)
                queueAux.add(actual.getRight());
        }
        
        return list.iterator();
    }*/

    @Override
    public int height(){
        if(root == null)
            return 0;
        
        int sizeIzq = 0, sizeDer = 0;
        return height(root, sizeIzq,sizeDer);
    }
    
    private int height(BSTNode<T> actual, int size1, int size2){
        
        if(actual.getLeft() != null)
            size1 = height(actual.getLeft(),size1+1,size2);
        
        if(actual.getRight() != null)
                size2 = height(actual.getRight(),size1, size2+1);
                
        if(size1 > size2)
            return size1;
        else
            return size2;
    }
    
    private boolean contains(T element, BSTNode<T> actual){
        if(actual == null) 
            throw new RuntimeException("Element is not in BT");
        if(element.equals(actual.getElement()))
            return true;
            
        contains(element, actual.getLeft());
        contains(element, actual.getRight());
        
        return false;
    }
    
    @Override
    public Iterator<T> preOrderIterator() {
        ArrayList<T> lista = new ArrayList<>();
        
        preOrder(root,lista);
        return lista.iterator();
    }

    @Override
    public Iterator<T> postOrderIterator() {
        ArrayList<T> lista = new ArrayList<>();
        
        postOrder(root,lista);
        return lista.iterator();
    }

    @Override
    public Iterator<T> inOrderIterator() {
        ArrayList<T> lista = new ArrayList<>();
        
        inOrder(root,lista);
        return lista.iterator();
    }
    
    private void preOrder(BSTNode<T> actual, ArrayList<T> list){
        if(actual == null)
            return;
        
        list.add(actual.getElement());
        preOrder(actual.getLeft(),list);
        preOrder(actual.getRight(),list);
    }
    
    private void postOrder(BSTNode<T> actual, ArrayList<T> list){
        if(actual == null)
            return;
        
        postOrder(actual.getLeft(),list);
        postOrder(actual.getRight(),list);
        list.add(actual.getElement());
        
    }
    
    private void inOrder(BSTNode<T> actual, ArrayList<T> list){
        if(actual == null)
            return;
        
        inOrder(actual.getLeft(),list);
        list.add(actual.getElement());
        inOrder(actual.getRight(),list);
    }
    
    @Override
    public BSTNode getRoot(){
        return root;
    }
    
    
    public void checkBalance(){
        if(count > 3)
            if(height() > (2*Math.log(count)))
                balanceTree();
    }
    
    private void balanceTree(){
        Iterator<T> itAux = preOrderIterator();
        ArrayList arr = new ArrayList<>();
        
        while(itAux.hasNext()){
            arr.add(itAux.next());
        }
        
        Collections.sort(arr);
        
        if(root.getRight() != null)
            root.setRight(null);
        if(root.getLeft() != null)
            root.setLeft(null);
        root = null;
        count = 0;
        
        int n = arr.size();
        if(arr.size()%2 != 0)
            n++;
        int tam = n;
        int numerador = 2;
        add((T) arr.get(n/2));
        while(numerador < arr.size()){
            n = n/numerador;
            
            for(int j = 1; j < numerador; j++){
                if(j*n-1 != tam/2)
                    add((T) arr.get(j*n-1));
            }
            n = tam;
            numerador *= 2;
        }
        for(int k = 0; k < arr.size(); k+=2){
            if(k != tam/2)
                add((T) arr.get(k));
        }
        
        for(Object aux: arr)
            if(!contains((T) aux))
                add((T) aux);
    }
    
    public <T extends Comparable<T>> void mergeSort(T arr[], int left, int right){
        if(left < right){
	int mitad = left+(right-left)/2;

	mergeSort(arr,left,mitad);
	mergeSort(arr,mitad+1,right);

	merge(arr,left,mitad,right);
	}
    }

    private <T extends Comparable<T>> void merge(T arr[], int left, int mitad, int right){
	int n1 = mitad - left + 1;
	int n2 = right - mitad;

        T [] leftArr;
            leftArr = (T[])(new Comparable[n1]);
	T [] rightArr; 
            rightArr = (T[])(new Comparable[n2]);

	for(int i = 0; i < n1; i++)
            leftArr[i] = arr[left+i];

	for(int j = 0; j<n2; j++)
            rightArr[j] = arr[mitad+1+j];

	int contL = 0; int contR = 0;
	int contArr = left;

	while(contL < n1 && contR < n2){
            if(leftArr[contL].compareTo(rightArr[contR])<=0){
		arr[contArr] = leftArr[contL];
		contL++;
            }
            else{
		arr[contArr] = rightArr[contR];
		contR++;
            }
            contArr++;
        }

	while(contL < n1){
            arr[contArr] = leftArr[contL];
            contArr++;
            contL++;
	}

	while(contR < n2){
            arr[contArr] = rightArr[contR];
            contArr++;
            contR++;
	}
    }
}
