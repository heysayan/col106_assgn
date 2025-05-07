public class ParenthesisMatching {
    public Boolean match_parenthesis(String s){
        /*
         * To be filled in by the student
         * Input: String made up of characters ‘(’, ‘{’, ‘[’, ‘)’, ‘}’, and ‘]’ 
         * Return: Whether input string is a matching parenthesis
         */
        if (s.length()==0){return false;}
        DemoStack string_stack = new DemoStack();
        for (int i = 0;i<s.length();i++){
            if (s.charAt(i)=='('){
                string_stack.push(s.charAt(i));}
            if (s.charAt(i)=='['){
                string_stack.push(s.charAt(i));}
            if (s.charAt(i)=='{'){
                string_stack.push(s.charAt(i));}
            if (s.charAt(i)==')'){
                if (string_stack.size()==0){return false;}
                if (string_stack.pop()!='('){
                    return false;}}
            if (s.charAt(i)==']'){
                if (string_stack.size()==0){return false;}
                if (string_stack.pop()!='['){
                    return false;}}            
            if (s.charAt(i)=='}'){
                if (string_stack.size()==0){return false;}
                if (string_stack.pop()!='{'){
                    return false;}}               
        }
        if (string_stack.size()!=0){return false;}
        return true;
    }
}
