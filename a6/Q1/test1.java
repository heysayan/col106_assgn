public class test1 {
    public static void main(String[] args) {
    try{
        PowerGrid pg = new PowerGrid("C:/Users/mohds/Desktop/a6/Q1/input2.txt");
/*
        System.out.println(pg.criticalLines().get(0).cityB); // returns empty ArrayList
        pg.preprocessImportantLines(); // must be called before numImportantLines()
        System.out.println(pg.numImportantLines("Delhi","Chennai")); // returns 0
        System.out.println(pg.numImportantLines("Mumbai","Kolkata")); // returns 0
*/
    System.out.println(pg.criticalLines().size()); // returns returns ArrayList<PowerLine> arr = {(A-E),(B-F),(B-G),(B-S),(S-C),(L-N)}
    pg.preprocessImportantLines(); // must be called before numImportantLines()
    System.out.println(pg.numImportantLines("D","E")); // returns 1
    System.out.println(pg.numImportantLines("K","N")); // returns 4
    System.out.println(pg.numImportantLines("H","O")); // returns 0
    System.out.println(pg.numImportantLines("G","J")); // returns 2

    }catch(Exception en){en.printStackTrace();}
    }
}
