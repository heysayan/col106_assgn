package heap_package;
import java.util.ArrayList;

 
public class Heap{

	protected Node root;								// root of the heap
	protected Node[] nodes_array;                    // Stores the address of node corresponding to the keys
	private int max_size;                           // Maximum number of nodes heap can have 
	private static final String NullKeyException = "NullKey";      // Null key exception
	private static final String NullRootException = "NullRoot";    // Null root exception
	private static final String KeyAlreadyExistsException = "KeyAlreadyExists";   // Key already exists exception

	/* 
	   1. Can use helper methods but they have to be kept private. 
	   2. Not allowed to use any data structure. 
	*/
	public Heap(int max_size, int[] keys_array, int[] values_array) throws Exception{

		/* 
		   1. Create Max Heap for elements present in values_array.
		   2. keys_array.length == values_array.length and keys_array.length number of nodes should be created. 
		   3. Store the address of node created for keys_array[i] in nodes_array[keys_array[i]].
		   4. Heap should be stored based on the value i.e. root element of heap should 
		      have value which is maximum value in values_array.
		   5. max_size denotes maximum number of nodes that could be inserted in the heap. 
		   6. keys will be in range 0 to max_size-1.
		   7. There could be duplicate keys in keys_array and in that case throw KeyAlreadyExistsException. 
		*/

		/* 
		   For eg. keys_array = [1,5,4,50,22] and values_array = [4,10,5,23,15] : 
		   => So, here (key,value) pair is { (1,4), (5,10), (4,5), (50,23), (22,15) }.
		   => Now, when a node is created for element indexed 1 i.e. key = 5 and value = 10, 
		   	  that created node address should be saved in nodes_array[5]. 
		*/ 

		/*
		   n = keys_array.length
		   Expected Time Complexity : O(n).
		*/

		this.max_size = max_size;
		this.nodes_array = new Node[this.max_size];

		// To be filled in by the student
		int noofnodes = keys_array.length;
		this.root = new Node(keys_array[0],values_array[0],null);
		nodes_array[keys_array[0]] = this.root;
		//updating height of root node
		float f = noofnodes/2;
		while (f>=1){this.root.height+=1;f=f/2;}
		int index = 0;		//index of node whose children are going to be next two keys in keys_array
		for (int i=1;i<noofnodes;i++){		//traversing the key_array and adding sequentialy adding keys to the tree from left to right
			if (nodes_array[keys_array[i]]!=null) throw new Exception(KeyAlreadyExistsException);	//if key already exists
			//initialising new Node
			Node cur = new Node(keys_array[i],values_array[i],nodes_array[keys_array[index]]);
			//updating height of subtree rooted at cur
			float test = noofnodes/(2*(i+1));
			while (test>=1){cur.height+=1;test=test/2;}

			nodes_array[keys_array[i]] = cur;		//adding newNode to nodes_array
			if (nodes_array[keys_array[index]].left==null){		//if (index)th key of key_array is left unfilled
				nodes_array[keys_array[index]].left= cur;	continue;
			}
			else if (nodes_array[keys_array[index]].right==null){		//if (index)th key of key_array is right unfilled
				nodes_array[keys_array[index]].right= cur;
			}
			//if (index)th key of key_array is filled
			if (nodes_array[keys_array[index]].left!=null && nodes_array[keys_array[index]].right!=null) index+=1;
		}
		//restructuring the tree so that it becomes a heap
		for (int j = index;j>=0;j--){
			heapify(nodes_array[keys_array[j]]);
		}
		//checking all nodes is subtree rooted at them is full
		check_complete(this.root);
	}
	public ArrayList<Integer> getMax() throws Exception{

		/* 
		   1. Returns the keys with maximum value in the heap.
		   2. There could be multiple keys having same maximum value. You have
		      to return all such keys in ArrayList (order doesn't matter).
		   3. If heap is empty, throw NullRootException.

		   Expected Time Complexity : O(1).
		*/
		// To be filled in by the student
		if (this.root==null) throw new Exception(NullRootException);
		ArrayList<Integer> max_keys = new ArrayList<Integer>();    // Keys with maximum values in heap.
		addmax(max_keys,this.root);
		return max_keys;
	}

	public void insert(int key, int value) throws Exception{

		/* 
		   1. Insert a node whose key is "key" and value is "value" in heap 
		      and store the address of new node in nodes_array[key]. 
		   2. If key is already present in heap, throw KeyAlreadyExistsException.

		   Expected Time Complexity : O(logn).
		*/
		// To be filled in by the student
		if (nodes_array[key]!=null) throw new Exception(KeyAlreadyExistsException);		//key already exists!
		//initialising the new node and adding it to the nodes_array
		Node newNode = new Node(key, value, null);
		nodes_array[key] = newNode;
		//if heap is empty
		if (this.root==null){
			this.root = newNode;
		}else{	//heap is not empty
			// navigating down to the last layer rightmost side to add the new node there
			insert_into_tree(this.root, newNode);
			//regaining heap property
			percolate_up(newNode);
		}
	}

	public ArrayList<Integer> deleteMax() throws Exception{

		/* 
		   1. Remove nodes with the maximum value in the heap and returns their keys.
		   2. There could be multiple nodes having same maximum value. You have
		      to delete all such nodes and return all such keys in ArrayList (order doesn't matter).
		   3. If heap is empty, throw NullRootException.

		   Expected Average Time Complexity : O(logn).
		*/

		ArrayList<Integer> max_keys = new ArrayList<Integer>();   // Keys with maximum values in heap that will be deleted.

		// To be filled in by the student
		if(this.root==null) throw new Exception(NullRootException);
		int max_value = this.root.value;	//max value in the heap
		while (this.root.value==max_value){
			max_keys.add(this.root.key);
			Node lastNode = decrease_tree(this.root);	//removes the last node from tree returns it
			if (lastNode!=this.root){		//if root was not the last and only element of tree
				nodes_array[lastNode.key] = this.root;	//updating the nodes_array as we'll swap root with this last node
				nodes_array[this.root.key] = null; 	//removing root key from nodes_array
				this.root.key=lastNode.key;this.root.value=lastNode.value;
				//restructuring to regain heap property
				heapify(this.root);
			}
			else{	//if root was the only element of tree
				this.root=null;break;
			}
		}
		return max_keys;
	}
	public void update(int key, int diffvalue) throws Exception{

		/* 
		   1. Update the heap by changing the value of the node whose key is "key" to value+diffvalue.
		   2. If key doesn't exists in heap, throw NullKeyException.

		   Expected Time Complexity : O(logn).
		*/

		// To be filled in by the student
		if (this.root==null) throw new Exception(NullRootException);
		if (nodes_array[key]==null) throw new Exception(NullKeyException);		//if 'key' doesn't exists
		//updating the interest node
		Node interest_node = nodes_array[key];
		interest_node.value+=diffvalue;
		// fixing the structure so that the tree becomes heap again
		if (diffvalue>0){	//the priority of the key is increased so might need ot percolate upwards
			percolate_up(interest_node);
		}
		else if (diffvalue<0){		//the priority of the key is decreased so might need to percolate down
			percolate_down(interest_node);
		}
	}

	public int getMaxValue() throws Exception{

		/* 
		   1. Returns maximum value in the heap.
		   2. If heap is empty, throw NullRootException.

		   Expected Time Complexity : O(1).
		*/

		// To be filled in by the student
		if(this.root==null) throw new Exception(NullRootException);
		return this.root.value;
	}
	public ArrayList<Integer> getKeys() throws Exception{

		/*
		   1. Returns keys of the nodes stored in heap.
		   2. If heap is empty, throw NullRootException.
		 
		   Expected Time Complexity : O(n).
		*/
		if (this.root==null) throw new Exception(NullRootException);
		ArrayList<Integer> keys = new ArrayList<Integer>();   // Stores keys of nodes in heap

		// To be filled in by the student
		DFT(keys,this.root);
		return keys;
	}

	// Write helper functions(if any) here (They have to be private).


	//helper functions for all func
	private void swap_nodes(Node a,Node b){		//swaps (key and value) of nodes a and b,but retains the same Node object
		int tmpkeyA = a.key;int tmpkeyB = b.key;
		a.key=tmpkeyB;b.key = tmpkeyA;		//interchanging the keys of the nodes
		int tmpvalA = a.value;
		a.value = b.value;b.value = tmpvalA;	//interchanging the values of the nodes
		nodes_array[tmpkeyA]=b;nodes_array[tmpkeyB]=a; //remapping the nodes_array indices to appropriate nodes
	}
	private void heapify(Node head){

		if ((head.left!=null && head.left.value>head.value) || (head.right!=null && head.right.value>head.value)){
			Node greaterchildNode;		//greater child node of the head
			if (head.left==null) greaterchildNode = head.right;
			else if (head.right==null) greaterchildNode = head.left;
			else if (head.left.value>head.right.value) greaterchildNode = head.left;
			else greaterchildNode = head.right;
			swap_nodes(head,greaterchildNode);	//swapping with greater child node key and value
			heapify(greaterchildNode);	//heapifying the swapped child side
		}
	}
	
	//helper function for insert
	private void insert_into_tree(Node head,Node newNode){
		if (head.is_complete){
			head.is_complete=false;		//subtree rooted at head won't be full after insertion
			head.height+=1;		//height of subtree rooted at head would increase
			if (head.left==null) {head.left = newNode;newNode.parent = head;}	//if bottom layer is reached add there newNode
			else insert_into_tree(head.left,newNode);
		}
		else{	//subtree rooted at head is not full
			if (!head.left.is_complete){		//if left subtree is not full(i.e. incomplete leftside), go there
				insert_into_tree(head.left,newNode);
				//if (head.left.is_complete) head.is_complete = true; //if left-subtree is complete after insertion
			}
			else{	//if left subtree is already full,we'll go towards right
				if (head.right==null) {	//at 2ndlast layer,left side is full
					head.right = newNode;newNode.parent = head;
					head.is_complete = true;
				}
				else{
					insert_into_tree(head.right, newNode);
					if (head.right.is_complete) head.is_complete=true; //if after insertion, right sub-tree is full then head is full
				}	
			}
		}
	}

	//helper function for delete_max
	private Node decrease_tree(Node head){	//removes the last node of tree and returns it
		if (head.is_complete){	//if head is full then steer to right child side for removal
			//head won't be full after removal of last node, and if this head is the leaf node then it is going to be removed,so won't be problem anymore
			head.is_complete = false;

			if (head.right!=null){	//if we're not at last layer, move to the right
				return decrease_tree(head.right);
			}
			else{	//we're at the last layer and at the last node
				if (head.parent!=null){	//if this is not root node
					if (head==head.parent.left) {head.parent.left=null;head.parent.height=1;}
					else if (head==head.parent.right) head.parent.right = null;
				}
				head.parent = null;
				return head;
			}
		}
		else{	//subtree rooted at head is not full
			if (!head.left.is_complete){		//if left-subtree is not full, remove from left sub-tree
				Node lastNode = decrease_tree(head.left);
				if (head.left.is_complete){	//after removal if leftsub-tree become full,then current node becomes full
					head.is_complete = true;head.height-=1;
				}
				return lastNode;
			}
			else{	//if left sub-tree is full
				if (head.right==null){	//if rightchild side is empty and left-subtree is full, then remove leftchild
					head.left.parent = null;Node lastNode = head.left;head.left=null;
					head.is_complete = true;	//head has become single node, thus a full tree
					head.height-=1;	//height of sub-tree rooted at head is reduced
					return lastNode;
				}
				if (head.right.is_complete){	//if right sub-tree is also full, then go to left subtree for removal
					return decrease_tree(head.left);
				}
				else{	//if right sub-tree is not full but left sub-tree is full,then remove from right subtree
					return decrease_tree(head.right);
				}
			}
		}
	}
	//helper functions for update
	private void percolate_down(Node head){heapify(head);}
	private void percolate_up(Node head){
		if(head.parent!=null){
			if (head.value>head.parent.value){
				swap_nodes(head, head.parent);
				percolate_up(head.parent);
			}
		}
	}


	//helper function for getmax
	private void addmax(ArrayList<Integer> max_keys,Node head){	//helper function for getmax
		max_keys.add(head.key);		//adding the key of head(having max value) to the max_keys list
		if (head.left!=null && head.left.value==head.value) addmax(max_keys,head.left);		//if head.left has the same max value as head
		if (head.right!=null && head.right.value==head.value) addmax(max_keys,head.right);		//if head.right has the same max value as head
	}

	//helper function for getkeys
	private void DFT(ArrayList<Integer> keys,Node cur){
		//does Depth-first traversal and adds to the arraylist keys
		keys.add(cur.key);
		if (cur.left!=null) DFT(keys,cur.left);
		if (cur.right!=null) DFT(keys,cur.right);
	}
	//helper function for heap
	private void check_complete(Node head){	//checks each node if subtree rooted at it is complete
		if (head.left!=null && head.right!=null){
			check_complete(head.left);check_complete(head.right);
			if (head.left.is_complete && head.right.is_complete && head.left.height==head.right.height){//both sides are full and height same
				head.is_complete = true;
			}
			else head.is_complete=false;
		}
		else{
			if (head.left==null && head.right==null) head.is_complete=true;		//empty leaf node
			else head.is_complete=false;	//one side is completely empty while other is not
		}
	}

}
