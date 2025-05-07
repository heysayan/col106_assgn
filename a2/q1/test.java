public class test{
    public static void main(String args[]){
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();
        p1.insert(4);p1.insert(0);p1.insert(0);p1.insert(0);p1.insert(3);
        p2.insert(4);p2.insert(1);p2.insert(8);p2.insert(2);p2.insert(5);
        Polynomial p3 = p1.add(p2);
        Polynomial p4 = p1.mult(p2);
        Node ptr1 = p1.head;Node ptr2 = p2.head;
        Node ptr3 = p3.head;Node ptr4 = p4.head;
        while (ptr1!=null){
            System.out.print(ptr1.data+"-->");
            ptr1 = ptr1.next;
        }
        System.out.print("\n");
        while (ptr2!=null){
            System.out.print(ptr2.data+"-->");
            ptr2 = ptr2.next;
        }
        System.out.print("\n");
        while (ptr3!=null){
            System.out.print(ptr3.data+"-->");
            ptr3 = ptr3.next;
        }
        System.out.print("\n");
        while (ptr4!=null){
            System.out.print(ptr4.data+"-->");
            ptr4 = ptr4.next;
        }

    }
}