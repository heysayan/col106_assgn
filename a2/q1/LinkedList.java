public class LinkedList{ 
    
    public Node head;
    public int size;
    
    public LinkedList(){
        head = null;
        size = 0;
    }

    public void insert(int c){
        //to be completed by the student
        if ((this.head!=null)&&(this.head.data==0)&&(c==0)) {
            return;
        }
        Node newNode = new Node(c);
        if (this.head==null){
            this.head = newNode;
            size+=1;}
        else{
            Node last = head;
            while (last.next != null){
                last = last.next;
            }
            last.next = newNode;
            size +=1;
        }
    }

    public int len(){
        //to be completed by the student
        return size;
    }
}


