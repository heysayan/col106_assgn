import java.util.ArrayList;


public class Tree {

    public TreeNode root;

    public Tree() {
        root = null;
    }
    //insert helper functions
    private ArrayList<TreeNode> splitNode(TreeNode node){  //splits the given 3-Node into leftmost key 2-Node and rightmost key 2-Node

        ArrayList<TreeNode> newNodes = new ArrayList<>(2);
        //creating separate Node for leftmost key of node
        TreeNode leftNode = new TreeNode();leftNode.s.add(node.s.get(0));leftNode.val.add(node.val.get(0));
        if (node.children.size()!=0) {leftNode.children.add(node.children.get(0));leftNode.children.add(node.children.get(1));}
        //updating height of newly created leftnode
        leftNode.height = node.height;

        //creating separate Node for the rightmost 2 keys of node
        TreeNode rightNode = new TreeNode();
        rightNode.s.add(node.s.get(2));rightNode.s.add(node.s.get(3));
        rightNode.val.add(node.val.get(2));rightNode.val.add(node.val.get(3));
        if (node.children.size()!=0) {
            rightNode.children.add(node.children.get(2));rightNode.children.add(node.children.get(3));
            rightNode.children.add(node.children.get(4));}
        //uppdating height of newly created rightnode
        rightNode.height = node.height;
        //adding into newNodes arraylist
        newNodes.add(leftNode);newNodes.add(rightNode);
        return newNodes;
    }


    private TreeNode insert_into_node(TreeNode node, TreeNode n){
        String key = n.s.get(0);        //key of the 2-node to be inserted
        if (node.s.size()==3){ // a 3-Node or full node
            if (key.compareTo(node.s.get(1))<0){
                if (key.compareTo(node.s.get(0))<0){    //adding n to the leftmost
                    //adding s to the leftmost of node's all keys
                    node.s.add(0,key);
                    //adding val to the leftmost of  node's all vals
                    node.val.add(0,n.val.get(0));
                    if(node.children.size()!=0){    //if node is not a leaf node
                        //setting rightchild of n in place of the leftmostchild of node
                        node.children.set(0,n.children.get(1));
                        //adding leftchild of n to the leftmost of node children
                        node.children.add(0,n.children.get(0));  
                    }
                } else{
                    //adding s to the right of node's first key
                    node.s.add(1,key);
                    //adding val to the right of node's first val
                    node.val.add(1,n.val.get(0));
                    if(node.children.size()!=0){    //if node is not a leaf node
                        //setting leftchild of n in place of right child of leftNode's only key
                        node.children.set(1,n.children.get(0));
                        //adding right child of n to the right of  children of leftNode
                        node.children.add(2,n.children.get(1));
                    }
                }
            } else{
                if (key.compareTo(node.s.get(2))<0){
                    //adding s to the left of  third key
                    node.s.add(2,key);
                    //adding val to the left of the third val
                    node.val.add(2,n.val.get(0));
                    if(node.children.size()!=0){    //if node is not a leaf node
                        //setting rightchild of n in place of the leftchild of rightNode's only key
                        node.children.set(2,n.children.get(1));
                        //adding leftchild of n as the third child of node
                        node.children.add(2,n.children.get(0));
                    }  
                } else{
                    //adding s to the rightmost of node's all keys
                    node.s.add(key);
                    //adding val to the rightmost of node's all vals
                    node.val.add(n.val.get(0));
                    if(node.children.size()!=0){    //if node is not a leaf node
                        //setting leftchild of n in place of rightmost child of node
                        node.children.set(3,n.children.get(0));
                        //adding right child of n to the rightmost of  children of node
                        node.children.add(n.children.get(1));
                    }
                } 
            }
            ArrayList<TreeNode> newNodes= splitNode(node);          //splitting the node into two 2-Nodes ,newNodes don't include 2nd key
            TreeNode midNode = new TreeNode();      //the mid-key is stored in it with the new splitted keys as child
            midNode.s.add(node.s.get(1));midNode.val.add(node.val.get(1));
            midNode.children.add(newNodes.get(0));midNode.children.add(newNodes.get(1));
            midNode.height = node.height+1;
            return midNode;
        }
        else if(node.s.size()==2){      // a 3-Node case
            if (key.compareTo(node.s.get(0))<0){
                //adding key to the leftmost of the keys of node
                node.s.add(0,key);
                //adding n.val to the leftmost of the values of node
                node.val.add(0,n.val.get(0));
                if (node.children.size()!=0){
                    //setting leftmost child of node to right child of n
                    node.children.set(0,n.children.get(1));
                    //adding leftchild of n to the left of all children of node
                    node.children.add(0,n.children.get(0));
                }
            }
            else if (key.compareTo(node.s.get(1))<0){
                //adding key to the second position of keys list of node
                node.s.add(1,key);
                //adding n.val to the second position of val list of node
                node.val.add(1,n.val.get(0));
                if (node.children.size()!=0){
                    //setting the left child of n as the second child of node
                    node.children.set(1,n.children.get(0));
                    //adding right child of n as the third child of node
                    node.children.add(2,n.children.get(1));
                }
            }
            else{
                //adding key to the right of all keys of node
                node.s.add(key);
                //adding n.val to the right of all values of node
                node.val.add(n.val.get(0));
                if (node.children.size()!=0){
                    //setting left child of n as the third child of node
                    node.children.set(2,n.children.get(0));
                    //adding right child of n as the last child of node
                    node.children.add(n.children.get(1));
                }
            }
        }
        else{       // a 2-Node case or a 1-Node(which is assumed to be not given)
            if (key.compareTo(node.s.get(0))<0){
                //adding key to the left of key of node
                node.s.add(0,key);
                //adding n.val to the left of values of node
                node.val.add(0,n.val.get(0));
                if (node.children.size()!=0){
                    //setting rightchild of n as the first child of node
                    node.children.set(0,n.children.get(1));
                    //adding leftchild of n to the left of all children of node
                    node.children.add(0,n.children.get(0));
                }
            }
            else{
                //adding key to the right of key of node
                node.s.add(key);
                //adding n.val to the right of value of node
                node.val.add(n.val.get(0));
                if (node.children.size()!=0){
                    //setting leftchild of n as the 2nd child of node
                    node.children.set(1,n.children.get(0));
                    //adding rightchild of n as the last child of node
                    node.children.add(n.children.get(1));
                }
            }
        }


        return node;
    }


    private TreeNode insert_helper(TreeNode node, String val){   //inserts val into sub-tree rooted at node and returns head of that new sub-tree
        if (node==null){       //empty tree case
            TreeNode newNode = new TreeNode();
            newNode.s.add(val);newNode.val.add(1);
            node = newNode;return node;
        }
        if (node.children.size()==0){       //at the leaf 
            // preparing a 2-node for new element
            TreeNode newNode = new TreeNode();newNode.s.add(val);newNode.val.add(1);
            //merging this 2-node to the current leaf node
            return insert_into_node(node,newNode);
        } 
        else{
            int index = 0; //the index of interest child node
            // retrieving the correct position of the interest child node
            while ((index<node.s.size()) && (val.compareTo(node.s.get(index))>0)) index+=1; //figuring out index of suitable child

            TreeNode newNode = insert_helper(node.children.get(index),val);     //recursive call to go downlevel, returns the modified child node

            if((newNode.s.size()==1) && (!newNode.s.get(0).equals(node.children.get(index).s.get(0)))){
                //executes if the newNode is the head formed due to splitting
                return insert_into_node(node,newNode);
            }
            else{
                return node;
            }
        }
    }


    public void insert(String s) {
        // TO be completed by students
        // in this insert function the tree always grows upwards and thus remains balanced
        this.root = insert_helper(this.root,s);
    }


//delete helper functions
    private void merge_left(TreeNode head,int index/*index of head */){    //merges head to the leftchild
        TreeNode leftchild = head.children.get(index);
        //moving head's key and val to the leftchild
        leftchild.s.add(head.s.get(index));leftchild.val.add(head.val.get(index));
        head.s.remove(index);head.val.remove(index);
        if (leftchild.children.size()!=0){      //if leftchild is not a leaf node
            //adding rightchild's only child as the rightmost child of left child
            leftchild.children.add(head.children.get(index+1).children.get(0));
        }
        //removing rightchild pointer from head
        head.children.remove(index+1);
    }
    private void merge_right(TreeNode head,int index /*index of head */){   //merges head to the rightchild
        TreeNode rightchild = head.children.get(index+1);
        //moving head's key and val to the rightchild
        rightchild.s.add(0,head.s.get(index));rightchild.val.add(0,head.val.get(index));
        head.s.remove(index);head.val.remove(index);
        if(rightchild.children.size()!=0){   //if rightchild is not a leaf node
            //adding leftchild's only child as the leftmost child of the rightchild
            rightchild.children.add(0,head.children.get(index).children.get(0));
        }
        //removing leftchild from head
        head.children.remove(index);
    }
    private void borrow_left(TreeNode head,int index /*index of head */){       //borrows a key from left child to the right child of head
        TreeNode leftchild = head.children.get(index);TreeNode rightchild = head.children.get(index+1);
        int leftchildsize = leftchild.s.size();
        //moving head key and val to the rightchild side
        rightchild.s.add(0,head.s.get(index));rightchild.val.add(0,head.val.get(index));
        head.s.remove(index);head.val.remove(index);
        //moving leftchild key and val to the head
        head.s.add(index,leftchild.s.get(leftchildsize-1));head.val.add(index,leftchild.val.get(leftchildsize-1));
        leftchild.s.remove(leftchildsize-1);leftchild.val.remove(leftchildsize-1);
        if (leftchild.children.size()!=0){  //if leftchild and rightchild are not leaf nodes
            //moving leftchild rightmost child as the leftmost child of rightchild
            rightchild.children.add(0,leftchild.children.get(leftchildsize));
            leftchild.children.remove(leftchildsize);
        }
    }
    private void borrow_right(TreeNode head,int index /*index of head */){      //borrows a key from right child to the left child of head
        TreeNode leftchild = head.children.get(index);TreeNode rightchild = head.children.get(index+1);
        //moving head key and val to the leftchild side
        leftchild.s.add(head.s.get(index));leftchild.val.add(head.val.get(index));
        head.s.remove(index);head.val.remove(index);
        //moving rightchild key and val to the head
        head.s.add(index,rightchild.s.get(0));head.val.add(index,rightchild.val.get(0));
        rightchild.s.remove(0);rightchild.val.remove(0);
        if (rightchild.children.size()!=0){     //if leftchild and rightchild are not leaf nodes
            //moving rightchild leftmost child as the rightmost child of leftchild
            leftchild.children.add(rightchild.children.get(0));
            rightchild.children.remove(0);
        }
    }
    private TreeNode find_max_key_node(TreeNode head){//finds max key in sub-tree rooted at head
        while(head.children.size()!=0){
            head=head.children.get(head.children.size()-1);
        }
        return head;           //returning the max key node of tree
    }
    private TreeNode delete_helper(TreeNode node,String key,boolean[] found){    //deletes 'key' from sub-tree rooted at head
        int index=0;    //index of the suitable child
        while ((index<node.s.size()) && (key.compareTo(node.s.get(index))>0)) index+=1; //figuring out index of suitable child
        if (node.children.size()!=0){    //if node is an internal node
            if ((index==node.s.size()) || (node.s.get(index).compareTo(key)!=0)){       //if 'key ' is not in current node
                TreeNode subtree_head = delete_helper(node.children.get(index), key,found);
                if (subtree_head.s.size()==0){  //a propagated underflow
                    if (index!=0){      //if key is not along leftmost side of tree
                        int index2;     //index of interest key of current node
                        index2 = index-1;
                        if (node.children.get(index-1).s.size()>1){     //when sibling is not a 2-Node
                            borrow_left(node,index2);
                            return node;
                        }
                        else{       //when sibling is a 2-Node
                            merge_left(node,index2);
                            return node;
                        }
                    }
                    else{       // 'key' is along leftmost side of sub-tree
                        if (node.children.get(index+1).s.size()>1){     //when sibling is not a 2-Node
                            borrow_right(node,index);
                            return node;
                        }
                        else{           //when sibling is a 2-Node
                            merge_right(node,index);
                            return node;
                        }
                    }
                }
                else return node;       //return the current node(the head of current sub-tree)
            }
            else{               //if 'key' is in current node
                found[0] = true;
                TreeNode suitable_predecessor = find_max_key_node(node.children.get(index));   //predecessor for swap
                // setting suitable_predecessor key and value in place of current node deleted key
                String predecessor_key = suitable_predecessor.s.get(suitable_predecessor.s.size()-1);
                node.s.set(index,predecessor_key);  //setting the suitable predecessor's key in place of removed key of current node
                node.val.set(index,suitable_predecessor.val.get(suitable_predecessor.val.size()-1));
                TreeNode subtree_head = delete_helper(node.children.get(index),predecessor_key,found);    //delete predecessor key from the left subtree
                if (subtree_head.s.size()==0){      //propagated underflow
                    if (index==0){      //the underflow is at the leftmost childside of node
                        if (node.children.get(index+1).s.size()>1){     //when sibling is not a 2-Node
                            borrow_right(node,index);
                            return node;
                        }
                        else{       //when sibling is a 2-Node
                            merge_right(node,index);
                            return node;
                        }
                    }
                    else{
                        if (node.children.get(index-1).s.size()>1){   //when sibling is not a 2-Node
                            borrow_left(node,index-1);
                            return node;
                        }
                        else{   //when sibling is a 2-Node
                            merge_left(node, index-1);
                            return node;
                        }
                    }
                }
                else return node;
            }
        }

        else {      //leaf node case
            if ((index==node.s.size()) || (node.s.get(index).compareTo(key)!=0)){   //if the 'key ' is not found
                found[0]=false;return node;
            }
            else{
                found[0] = true;
                node.s.remove(index);node.val.remove(index);        //removing the key and its value
                return node;
            }
        }
        //return node;
    }

    public boolean delete(String s) {
        // TO be completed by students
        if (this.root==null) return false;
        boolean[] found = {false};
        TreeNode newNode = delete_helper(this.root,s,found);
        if(found[0]){
            if (newNode.s.size()==0){   //root is empty
                if (newNode.children.size()==0) this.root=null;
                else this.root = newNode.children.get(0); //change root to the only child of the empty root node
            }
            return true;
        }

        return false;
    }

    public boolean search(String s) {
        // TO be completed by students
        if (this.root!=null){
            TreeNode cur = this.root;       //current node
            while (true){
                int index=0;
                while ((index<cur.s.size()) && (s.compareTo(cur.s.get(index))>0)) index+=1;   //figuring out index of suitable child node
                if (index==cur.s.size()) {     //if "s" is largest than all keys of  cur Node
                    if (cur.children.size()!=0){cur=cur.children.get(index);continue;}   //if cur is not a leaf node
                    else break;     //if cur is a leaf node i.e. we've traversed the whole tree but couldn't find 's'
                }        
                if (s.compareTo(cur.s.get(index))==0) return true;      //the string s is a (index)th key of cur
                else if (cur.children.size()==0) break;      //if cur is leaf node i.e. we've traversed the whole tree but couldn't find 's'
                else cur=cur.children.get(index);   //'s' is not in cur node,going downlevel
            }
        }
        return false;
    }
    
    public int increment(String s) {
        // TO be completed by students
        TreeNode cur = this.root;       //current node
        while (true){
            int index=0;
            while ((index<cur.s.size()) && (s.compareTo(cur.s.get(index))>0)) index+=1;   //figuring out index of suitable child node
            if (index==cur.s.size()) {     //if "s" is largest than all keys of  cur Node
                if (cur.children.size()!=0){cur=cur.children.get(index);continue;}   //if cur is not a leaf node
                else break;     //if cur is a leaf node i.e. we've traversed the whole tree but couldn't find 's'
            }        
            if (s.compareTo(cur.s.get(index))==0) {  //the string s is a (index)th key of cur
                cur.val.set(index,cur.val.get(index)+1);return cur.val.get(index);
            }     
            else if (cur.children.size()==0) break;      //if cur is leaf node i.e. we've traversed the whole tree but couldn't find 's'
            else cur=cur.children.get(index);   //'s' is not in cur node,going downlevel
        }
        return 0;
    }

    public int decrement(String s) {
        // TO be completed by students
        TreeNode cur = this.root;       //current node
        while (true){
            int index=0;
            while ((index<cur.s.size()) && (s.compareTo(cur.s.get(index))>0)) index+=1;   //figuring out index of suitable child node
            if (index==cur.s.size()) {     //if "s" is largest than all keys of  cur Node
                if (cur.children.size()!=0){cur=cur.children.get(index);continue;}   //if cur is not a leaf node
                else break;     //if cur is a leaf node i.e. we've traversed the whole tree but couldn't find 's'
            }        
            if (s.compareTo(cur.s.get(index))==0) {  //the string s is a (index)th key of cur
                cur.val.set(index,cur.val.get(index)-1);return cur.val.get(index);
            }     
            else if (cur.children.size()==0) break;      //if cur is leaf node i.e. we've traversed the whole tree but couldn't find 's'
            else cur=cur.children.get(index);   //'s' is not in cur node,going downlevel
        }
        return 0;
    }

    public int getHeight() {
        // TO be completed by students

        int height = this.root.height;
        return height;
    }

    public int getVal(String s) {
        // TO be completed by students
        TreeNode cur = this.root;       //current node
        while (true){
            int index=0;
            while ((index<cur.s.size()) && (s.compareTo(cur.s.get(index))>0)) index+=1;   //figuring out index of suitable child node
            if (index==cur.s.size()) {     //if "s" is largest than all keys of  cur Node
                if (cur.children.size()!=0){cur=cur.children.get(index);continue;}   //if cur is not a leaf node
                else break;     //if cur is a leaf node i.e. we've traversed the whole tree but couldn't find 's'
            }        
            if (s.compareTo(cur.s.get(index))==0) {  //the string s is a (index)th key of cur
                return cur.val.get(index);
            }     
            else if (cur.children.size()==0) break;      //if cur is leaf node i.e. we've traversed the whole tree but couldn't find 's'
            else cur=cur.children.get(index);   //'s' is not in cur node,going downlevel
        }

        return 0;
    }
}
