public class test3{
    public static void main(String args[]){
        ParenthesisMatching p = new ParenthesisMatching();
        System.out.println("1. "+p.match_parenthesis("{[]}{[]({[]}){{{{}}}}"));
        System.out.println("2. "+p.match_parenthesis(""));
        System.out.println("3. "+p.match_parenthesis("()(){}[]{[()]}[]({[]})"));
        System.out.println("4. "+p.match_parenthesis("[{({}{}{}[[{()}]])}"));

    }
}