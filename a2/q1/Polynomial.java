public class Polynomial extends LinkedList{
    
    public Polynomial add(Polynomial p){
        //to be implemented by the student
        Polynomial newP = new Polynomial();
        Polynomial greaterP;Polynomial smallerP;
        if (p.size>=this.size){
            greaterP = p;smallerP = this;
        }
        else{
            greaterP = this;smallerP = p;
        }
        int sizeDiff = greaterP.size - smallerP.size;
        Node gPptr = greaterP.head;
        Node sPptr = smallerP.head;
        while (sizeDiff>0){
            newP.insert(gPptr.data);
            gPptr = gPptr.next;
            sizeDiff-=1;
        }
        while((gPptr != null) && (sPptr != null)){
            newP.insert(sPptr.data+gPptr.data);
            sPptr = sPptr.next;
            gPptr = gPptr.next;
        }
        if(newP.head!=null){
            while((newP.head.data==0) && (newP.head.next!=null)){
                newP.head=newP.head.next;
            }
        }
        return newP;
    }

    public Polynomial mult(Polynomial p){
        //to be implemented by the student
        Polynomial newP = new Polynomial();
        Polynomial greaterP;Polynomial smallerP;
        if (p.size>=this.size){
            greaterP = p;smallerP = this;
        }
        else{
            greaterP = this;smallerP = p;
        }
        //what if p is not valid polynomial like with no coeff
        Node gPptr = greaterP.head;
        Node sPptr = smallerP.head;
        Polynomial temp;
        for (int count = smallerP.size-1;count>=0;count--){
            temp = new Polynomial();
            while(gPptr!=null){
                temp.insert(gPptr.data*sPptr.data);
                gPptr=gPptr.next;
            }
            for (int m = count;m>0;m--){
                temp.insert(0);
            }
            newP = newP.add(temp);
            sPptr = sPptr.next;gPptr = greaterP.head;
        }
        return newP;
    }


}