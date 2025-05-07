import java.util.ArrayList;

public class test {
    
    public static Tree tree = new Tree();
    public static void main(String[] args) {
        
        // Insert random keys with random values
        for (int i = 0; i < 20; i++) {
            System.out.println("Inserting key"+i+" !");
            String key = "key" + i; // generate a random key
            tree.insert(key);System.out.println("new tree: ");
            print2D_main();
        }
        for (int i = 20;i>-1;i--){
        System.out.println("deleting key"+i+"!");System.out.println("deletion successful? "+tree.delete("key"+i));
        print2D_main();
        }
        tree.insert("o");print2D_main();
        tree.insert("c");print2D_main();
        tree.insert("j");print2D_main();
        tree.insert("b");print2D_main();
        tree.insert("n");print2D_main();
        tree.insert("d");print2D_main();
        tree.insert("h");print2D_main();
        tree.insert("k");print2D_main();
        tree.insert("p");print2D_main();

        System.out.println("deleting b! "+tree.delete("b"));print2D_main();
        System.out.println("deleting n! "+tree.delete("n"));print2D_main();
        System.out.println("searching be! "+tree.search("be"));
        System.out.println("searching fe! "+tree.search("fe"));
        System.out.println("deleting ae!");System.out.println("deletion successful? "+tree.delete("ae"));print2D_main();
        System.out.println("getheight "+tree.getHeight());
        System.out.println("getval 'a' :"+tree.getVal("a"));
        System.out.println("increment 'a' : "+tree.increment("a"));
        System.out.println("getval 'ce' :"+tree.getVal("ce"));
    }
/* 
    // A method to print the tree in a visually appealing way
    public static void printTree(TreeNode node, String prefix, boolean isTail) {
        // Print the current node
        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.getKey() + ":" + node.getValue());
        
        // If the node has children, recursively print them
        ArrayList<Node> children = node.getChildren();
        for (int i = 0; i < children.size() - 1; i++) {
            Node child = children.get(i);
            printTree(child, prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            Node child = children.get(children.size() - 1);
            printTree(child, prefix + (isTail ?"    " : "│   "), true);
        }
    }*/
    private static void print2D(ArrayList<TreeNode> level){
        if(level.size()!=0){
            ArrayList<TreeNode> new_level = new ArrayList<TreeNode>();
            for(int i = 0 ; i< level.size(); i++){
                System.out.print(level.get(i).height);
                System.out.print(level.get(i).s);
                for(int j = 0 ; j< level.get(i).children.size() ; j++){
                    new_level.add(level.get(i).children.get(j));
                }
            }
            System.out.println();
            print2D(new_level);
        }
    }
    private static void  print2D_main(){
        ArrayList<TreeNode> init = new ArrayList<TreeNode>();
        if (tree.root==null) return;
        init.add(tree.root);
        print2D(init);
    }
}
/*
inserting o
inserting c
inserting j
inserting b
inserting n
inserting d
inserting h
inserting k
inserting p
deleting b
deleting n
 */