package com.gradescope.assignment1;

import com.gradescope.assignment1.AbstractDemoStack;

import java.util.EmptyStackException;

public class DemoStack extends AbstractDemoStack {
    Character[] base;
    int Size;
    public DemoStack(){
        super();
        base = new Character[10];
        Size = 0;
    }
    public void push(Character i){
        /*
         * To be filled in by the student
         * Input: Character to be inserted onto top of stack
         */
        if (Size == base.length){
            Character[] temp = new Character[2*base.length];
            for (int x = 0;x<Size;x++){
                temp[x]=base[x];
            }
            base = temp;
        }
        base[Size]=i;
        Size+=1;
    }

    public Character pop() throws EmptyStackException{
        /*
         * To be filled in by the student
         * Return: Character present at the top of the stack
         */
        if (Size==0){
            throw new EmptyStackException();
        }
        Character t = base[Size-1];
        base[Size-1]=null;
        Size-=1;
        if (( Size<=((1/4)*base.length) ) && (base.length>=5)){
            int newlength = (base.length/2)+1;
            Character[] temp = new Character[newlength];
            for (int x = 0;x<Size;x++){
                temp[x]=base[x];
            }
            base = temp;
        }
        return t;
    }
    
    public Boolean is_empty(){
        /*
         * To be filled in by the student
         * Return: Stack is empty or not
         */
        if (Size ==0){
        return true;
        }
        else{return false;}
    }
    
    public Integer size(){
        /*
         * To be filled in by the student
         * Return: Number of elements which are present in stack
         */
        return Size;
    }
    
    public Character top() throws EmptyStackException{
        /*
         * To be filled in by the student
         * Return: Character present at the top of the stack
         */
        if (Size==0){
            throw new EmptyStackException();
        }
        return base[Size-1];
    }

    public Character[] return_base_array(){
       /*
        * To be filled in the by the student
        * Return: Return reference to the base array storing the elements of stack
        */
        return base;
    }
}
