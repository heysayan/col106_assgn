public class test2{
    public static void main(String args[]){
        MethodOverloading a = new MethodOverloading();
        System.out.println("1: "+a.calculate(2));
        System.out.println("2: "+a.calculate(2,3));
        System.out.println("3: "+a.calculate(3,4,5));
    }
}