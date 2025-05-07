package com.gradescope.assignment1;

import com.gradescope.assignment1.AbstractParenthesisMatching;
import com.gradescope.assignment1.DemoStack;
import java.util.EmptyStackException;
public class ParenthesisMatching extends AbstractParenthesisMatching {
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
                string_stack.push(s.charAt(i));continue;}
            if (s.charAt(i)=='['){
                string_stack.push(s.charAt(i));continue;}
            if (s.charAt(i)=='{'){
                string_stack.push(s.charAt(i));continue;}
            if (s.charAt(i)==')'){
                try{
                if (string_stack.pop()!='('){
                    return false;}}
                catch(EmptyStackException en1){return false;}}
            if (s.charAt(i)==']'){
                try{
                if (string_stack.pop()!='['){
                    return false;}}
                catch(EmptyStackException en2){return false;}}           
            if (s.charAt(i)=='}'){
                try{
                if (string_stack.pop()!='{'){
                    return false;}}
                catch(EmptyStackException en3){return false;}}              
        }
        if (string_stack.size()!=0){return false;}
        return true;
    }
}
