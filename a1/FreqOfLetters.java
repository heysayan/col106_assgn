package com.gradescope.assignment1;

import com.gradescope.assignment1.AbstractFreqOfLetters;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FreqOfLetters extends AbstractFreqOfLetters {
    public Integer[] count_letters(String fname) throws FileNotFoundException, IOException {
        /*
         * To be filled in by the student
         * Input: File name
         * Return: Array of 26 length containing freq of characters present in the file
         * Note: Ignore ' ' and EOF characters from input file
         */
        FileReader inp_file;
        Integer[] result = new Integer[26];
        for (int m = 0;m<=result.length-1;m++){
            result[m]=0;
        }
        inp_file = new FileReader(fname);
        int read_char = inp_file.read();
        while(read_char!=-1){
            if (read_char>=97 && read_char<=122){
                result[read_char-97]+=1;
            }
            read_char = inp_file.read();
        }
        inp_file.close();
        return result;
    }
}
