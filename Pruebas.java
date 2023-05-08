package PruebasArboles;
import BinarySearchTree.BinarySearchTree;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;
import java.io.IOException;


/**
 *
 * @author jaime
 */
public class Pruebas {
    
    public static void writeFile(String nomArch, int arr[]) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomArch))) {
            int i = 1;
            for(int a: arr){
                writer.write(a+","+i);
                i++;
            }
            writer.close();
        }
    }
    
    public static void main(String[] args) throws IOException{
        BinarySearchTree<Integer> bstPrueba = new BinarySearchTree<>();
        
        bstPrueba.add(1); bstPrueba.add(2); bstPrueba.add(3);
        bstPrueba.add(4); bstPrueba.add(5); bstPrueba.add(6);
        bstPrueba.checkBalance();
        
        System.out.println(bstPrueba.height());
        System.out.println(bstPrueba.getRoot().getElement());
        
        /*Random r = new Random();
        int sizes []= {128,256,512,1024,2048,4096,8192};
        int heights[] = new int[sizes.length]; 
        int sum = 0;
        int k = 0;
        
        
        for(int size: sizes){
            for(int i = 0; i < 100; i++){
                for(int j = 0 ; j < size; j++)
                    bstPrueba.add(r.nextInt(10000));
                
                sum += bstPrueba.height()/size;
            }
            heights[k] = sum;
            sum = 0;
            k++;
        }
        
        for(int height: heights)
            System.out.println(height);
        */
    }
}
