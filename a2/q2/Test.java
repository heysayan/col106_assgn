import Includes.KeyAlreadyExistException;
import Includes.KeyNotFoundException;
import Includes.NullKeyException;

public class Test<K, V>{
    public static void main(String args[]){
        COL106Dictionary<String, Integer> newDict = new COL106Dictionary<String, Integer>(1001);
        try{
            newDict.insert("alpha",45);
            System.out.println("Inserted! (alpha,45)");
            newDict.insert("beta",56);
            System.out.println("Inserted! (beta,56)");
            newDict.insert("gamma",90);
            System.out.println("Inserted! (gamma,90)");
            newDict.update("beta",77);
            System.out.println("updated! (beta,77)");
            int a = newDict.delete("beta");
            System.out.println("deleted (beta,"+a+")");}
        catch(KeyAlreadyExistException e1){}
        catch(NullKeyException e2){}
        catch(KeyNotFoundException e3){}
        try{
            System.out.println("Retrieving key 'ming':");
            newDict.get("ming");}
        catch(NullKeyException e1){}
        catch(KeyNotFoundException e2){}
        String[] keys = newDict.keys(String.class);
        Integer[] vals = newDict.values(Integer.class);
        System.out.println("Retrieving keys of the dict: ");
        for (String s:keys){System.out.print(s+" ");}
        System.out.println("\nRetrieving values of the dict: ");
        for (Integer i:vals){System.out.print((Integer.toString(i)+" "));}
        System.out.println();
        System.out.println(newDict.hash("a"));

    }
}