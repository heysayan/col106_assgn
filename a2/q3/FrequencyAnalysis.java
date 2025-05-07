import java.util.ArrayList;
import java.util.LinkedList;

import Includes.*;

public class FrequencyAnalysis {
    //sizes of hash-tables are updated
    static final int[] hashTableSizes = {173, 6733, 100003};
    COL106Dictionary<String, Integer> dict1 = new COL106Dictionary<String, Integer>(hashTableSizes[0]);
    COL106Dictionary<String, Integer> dict2 = new COL106Dictionary<String, Integer>(hashTableSizes[1]);
    COL106Dictionary<String, Integer> dict3 = new COL106Dictionary<String, Integer>(hashTableSizes[2]);

    void fillDictionaries(String inputString) throws NullKeyException, KeyAlreadyExistException, KeyNotFoundException {
        /*
         * To be filled in by the student
         */
        if (inputString==null) throw new NullKeyException();
        int strLength = inputString.length();
        String temp ="";
        for (int i = 0;i<strLength;i++){
            char cur = inputString.charAt(i);
            if ((cur==' ')||(i==strLength-1)){
                if (temp.equals("")) continue;
                temp = temp.toLowerCase();
                if (dict1.exists(temp)){//updates the dicts
                    dict1.update(temp,dict1.get(temp)+1);
                    dict2.update(temp,dict2.get(temp)+1);
                    dict3.update(temp,dict3.get(temp)+1);
                }
                else{//inserts into the dict
                    dict1.insert(temp,Integer.valueOf(1));
                    dict2.insert(temp,Integer.valueOf(1));
                    dict3.insert(temp,Integer.valueOf(1));
                }
                temp = "";
                continue;  
            }
            //collects letters of a word to constitute a word
            if ((cur<=90&&cur>=65)||(cur<=122&&cur>=97)){
                temp = temp+String.valueOf(cur);
            }
        }
    }
    
    long[] profile() {
        /*
         * To be filled in by the student
         */
        return new long[4];
    }

    int[] noOfCollisions() {
        /*
         * To be filled in by the student
         */
        int[] noOfCollisions = {0,0,0};
        //counting collisions for 1st dict
        for (LinkedList<HashTableEntry<String, Integer>> hashEntry :dict1.hashTable){
            if (hashEntry == null) continue;
            int Size = hashEntry.size();
            if (Size>0 )noOfCollisions[0]+=(Size-1);
        }
        for (LinkedList<HashTableEntry<String, Integer>> hashEntry :dict2.hashTable){
            if (hashEntry == null) continue;
            int Size = hashEntry.size();
            if (Size>0) noOfCollisions[1]+=(Size-1);

        }
        for (LinkedList<HashTableEntry<String, Integer>> hashEntry :dict3.hashTable){
            if (hashEntry == null) continue;
            int Size = hashEntry.size();
            if (Size>0) noOfCollisions[2]+=(Size-1);
        }
        return noOfCollisions;
    }

    String[] hashTableHexaDecimalSignature() {
        /*
         * To be filled in by the student
         */
        String[] hthexsign = {"","",""};
        String temp = "";int fourCounter = 4;
        COL106Dictionary<String,String> dict4 = new COL106Dictionary<String,String>(16);
        try{
            dict4.insert("0000","0");dict4.insert("0001","1");dict4.insert("0010","2");
            dict4.insert("0011","3");dict4.insert("0100","4");dict4.insert("0101","5");
            dict4.insert("0110","6");dict4.insert("0111","7");dict4.insert("1000","8");
            dict4.insert("1001","9");dict4.insert("1010","A");dict4.insert("1011","B");
            dict4.insert("1100","C");dict4.insert("1101","D");dict4.insert("1110","E");
            dict4.insert("1111","F");
        }
        catch(Exception e1){}
        for (int i = dict1.hashTable.length-1;i>=0;i--){
            if (dict1.hashTable[i]!=null){
                if (dict1.hashTable[i].size()>0){
                    temp = "1"+temp;
                    fourCounter-=1;
                }
                else {
                    temp = "0"+temp;
                    fourCounter-=1;
                }
            }
            else{
                temp = "0"+temp;
                fourCounter-=1;
            }
            if (i==0){
                while (fourCounter!=0){
                    temp="0"+temp;fourCounter-=1;
                }}
            if (fourCounter==0){
                try{hthexsign[0]=dict4.get(temp)+hthexsign[0];}
                catch(Exception e1){}
                temp = "";
                fourCounter = 4;
            }
        }
        for (int i = dict2.hashTable.length-1;i>=0;i--){
            if (dict2.hashTable[i]!=null){
                if (dict2.hashTable[i].size()>0){
                    temp = "1"+temp;
                    fourCounter-=1;
                }
                else {
                    temp = "0"+temp;
                    fourCounter-=1;
                }
            }
            else{
                temp = "0"+temp;
                fourCounter-=1;
            }
            if (i==0){
                while (fourCounter!=0){
                    temp="0"+temp;fourCounter-=1;
                }}
            if (fourCounter==0){
                try{hthexsign[1]=dict4.get(temp)+hthexsign[1];}
                catch(Exception e1){}
                temp = "";
                fourCounter = 4;
            }
        }
        for (int i = dict3.hashTable.length-1;i>=0;i--){
            if (dict3.hashTable[i]!=null){
                if (dict3.hashTable[i].size()>0){
                    temp = "1"+temp;
                    fourCounter-=1;
                }
                else {
                    temp = "0"+temp;
                    fourCounter-=1;
                }
            }
            else{
                temp = "0"+temp;
                fourCounter-=1;
            }
            if (i==0){
                while (fourCounter!=0){
                    temp="0"+temp;fourCounter-=1;
                }}
            if (fourCounter==0){
                try{hthexsign[2]=dict4.get(temp)+hthexsign[2];}
                catch(Exception e1){}
                temp = "";
                fourCounter = 4;
            }
        }
        return hthexsign;
    }
    
    String[] distinctWords() {
        /*
         * To be filled in by the student
         */
        return dict1.keys(String.class);
    }

    Integer[] distinctWordsFrequencies() {
        /*
         * To be filled in by the student
         */
        return dict1.values(Integer.class);
    }
}
