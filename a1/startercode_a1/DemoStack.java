package com.gradescope.assignment1;

import java.util.EmptyStackException;

import com.gradescope.assignment1.AbstractDemoStack;

public class DemoStack extends AbstractDemoStack {
    Character[] base;

    public DemoStack(){
        super();
        base = new Character[10];
    }
    public void push(Character i){
        /*
         * To be filled in by the student
         * Input: Character to be inserted onto top of stack
         */
    }

    public Character pop() throws EmptyStackException{
        /*
         * To be filled in by the student
         * Return: Character present at the top of the stack
         */
        return 't';
    }
    
    public Boolean is_empty(){
        /*
         * To be filled in by the student
         * Return: Stack is empty or not
         */
        return true;
    }
    
    public Integer size(){
        /*
         * To be filled in by the student
         * Return: Number of elements which are present in stack
         */
        return 0;
    }
    
    public Character top() throws EmptyStackException{
        /*
         * To be filled in by the student
         * Return: Character present at the top of the stack
         */
        return 't';
    }

    public Character[] return_base_array(){
       /*
        * To be filled in the by the student
        * Return: Return reference to the base array storing the elements of stack
        */
        return base;
    }
}
