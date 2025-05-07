//import heap_package.Heap;
//import heap_package.Heap.Node;
import java.lang.Math;

import java.util.ArrayList;
public class test {
    public static void main(String[] args) {
        int[] keys_arr = {1,2,3,4,5,6,7,10};int[] val_arr = {45,12,67,45,3,99,86,84};
        try{
            Heap heap = new Heap(50, keys_arr, val_arr);
            heap_printer(heap.root);System.out.println();
            heap.insert(11,99);heap_printer(heap.root);System.out.println();
            System.out.println("Deleting max_elements: "+heap.deleteMax());System.out.println();heap_printer(heap.root);
            System.out.println("Getting max value: "+heap.getMaxValue());
            System.out.println("Updating (4,109)!");heap.update(4, 109);System.out.println();heap_printer(heap.root);
        }
        catch(Exception en){en.printStackTrace();}


    }
    private static void heap_printer(Node head){   //prints level order traversal
        ArrayList<Node> nodes_queue = new ArrayList<Node>();
        nodes_queue.add(head);int count = 1; int level = 0;
        while(nodes_queue.size()!=0){
            if (count>Math.pow(2,level+1)-1){
                System.out.println();level+=1;
            }
            Node cur = nodes_queue.remove(0);if(cur==null) continue;
            nodes_queue.add(cur.left);nodes_queue.add(cur.right);
            System.out.print(cur.height);System.out.print("("+cur.key+","+cur.value+")");System.out.print("   ");count+=1;
        }System.out.println();
    }
}
