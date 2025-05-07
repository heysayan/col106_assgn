
public class test{
    public static void main(String args[]){
        String f = "dummy.txt";
        FreqOfLetters obj = new FreqOfLetters();
        Integer[] chars;
        try{
        chars = obj.count_letters(f);
        for (int x = 0;x <= chars.length-1 ; x++){
            System.out.println(chars[x]);
        }

        }
        catch(Exception en1){
            System.out.println(en1);
        }
    }
}