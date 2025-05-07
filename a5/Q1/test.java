import java.util.ArrayList;
public class test {
    public static void main(String[] args) {
        PointQuadtree obj = new PointQuadtree();
        CellTower c1 = new CellTower(0,0,5);
        CellTower c2 = new CellTower(-2,0,4);
        CellTower c3 = new CellTower(2,3,10);
        CellTower c4 = new CellTower(-4,6,9);
        System.out.println(obj.insert(c1));
        System.out.println( obj.insert(c2));
        System.out.println( obj.insert(c3));
        printer(obj.root);
        System.out.println(obj.cellTowerAt(-2,0)); // returns true
        System.out.println(obj.cellTowerAt(2,4)); // returns false
        System.out.println("("+obj.chooseCellTower(0, 6, 5).x+","+obj.chooseCellTower(0, 6, 5).y+")");  //returns c3
        obj.insert(c4);
        System.out.println("("+obj.chooseCellTower(0, 6, 5).x+","+obj.chooseCellTower(0, 6, 5).y+")");  //returns c4
        CellTower c5 = new CellTower(-3,7,5);
        CellTower c6 = new CellTower(-3,3,4);
        CellTower c7 = new CellTower(-6,7,2);
        CellTower c8 = new CellTower(-5,4,9);
        System.out.println( obj.insert(c5));
        System.out.println( obj.insert(c6));
        System.out.println( obj.insert(c7));
        System.out.println(obj.insert(c8));
        System.out.println( obj.insert(c3)); // should fail
        System.out.println( obj.chooseCellTower(-3, 7, 0).x+","+obj.chooseCellTower(-3, 7, 0).y); // returns c5
        printer(obj.root);
    }
    public static void printer(PointQuadtreeNode head){
        //prints the quadtree level wise
        System.out.println("Printing the quadtree : ");
        ArrayList<PointQuadtreeNode> nodes_queue = new ArrayList<PointQuadtreeNode>();
        nodes_queue.add(head);
        int nodes_in_current_layer = 1;int nodes_in_next_layer = 0;int node_child_counter=1;System.out.print("[");
        while(!nodes_queue.isEmpty()){
            if(node_child_counter==4) System.out.print("[");
            PointQuadtreeNode cur = nodes_queue.remove(0);nodes_in_current_layer-=1;node_child_counter-=1;
            
            if (cur!=null) {for (int i = 0;i<4;i++) nodes_queue.add(cur.quadrants[i]);nodes_in_next_layer+=4;}
            
            if(cur!=null) System.out.print("("+cur.celltower.x+","+cur.celltower.y+","+cur.celltower.cost+"), ");
            else System.out.print("null, ");
            if (node_child_counter==0) {System.out.print("] ");node_child_counter=4;}
            if (nodes_in_current_layer==0){
                nodes_in_current_layer=nodes_in_next_layer;nodes_in_next_layer=0;
                System.out.println();
            }
        }
    }
}
