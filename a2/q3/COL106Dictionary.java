import java.util.LinkedList;

import Includes.DictionaryEntry;
import Includes.HashTableEntry;
import Includes.KeyAlreadyExistException;
import Includes.KeyNotFoundException;
import Includes.NullKeyException;

import java.lang.reflect.Array;
//student imported classes

import java.util.Iterator;


public class COL106Dictionary<K, V> {

    private LinkedList<DictionaryEntry<K, V>> dict;
    /*
     * dict is a Linked-List, where every node of linked-list is of type DictionaryEntry.
     * DictionaryEntry is a key-value pair, where the type of key and value is K and V respectively.
     */ 
    public LinkedList<HashTableEntry<K, V>>[] hashTable;
    /*
     * hashTable is an array of Linked-Lists which is initialized by the COL106Dictionary constructor.
     * Each index of hashTable stores a linked-list whose nodes are of type HashTableEntry
     * HashTableEntry is a key-address pair, where the type of key is K and the corresponding address is the address of the DictionaryEntry in the linked-list corresponding to the key of HashTableEntry
     */
    public int hashTableSize;
    public int dictSize;
 
    
    @SuppressWarnings("unchecked")
    COL106Dictionary(int hashTableSize) {
        dict = new LinkedList<DictionaryEntry<K, V>>();
        // This statement initiailizes a linked-list where each node is of type DictionaryEntry with key and value of type K and V respectively.
        hashTable = (LinkedList<HashTableEntry<K, V>>[]) Array.newInstance(LinkedList.class, hashTableSize);
        // This statement initiailizes the hashTable with an array of size hashTableSize where at each index the element is an instance of LinkedList class and
        // this array is type-casted to an array of LinkedList where the LinkedList contains nodes of type HashTableEntry with key of type K.
        this.hashTableSize = hashTableSize;
        this.dictSize = 0; 
    }

    public void insert(K key, V value) throws KeyAlreadyExistException, NullKeyException {
        /*
         * To be filled in by the student
         * Input: A key of type K and it corresponding value of type V
         * Working: Inserts the argumented key-value pair in the Dictionary in O(1)
         */
        //checking if key is not null
        if (key==null) throw new NullKeyException();

        //computing hash value of key
        int hashValue = this.hash(key);

        //checking if key doesn't exists already
        if (hashTable[hashValue]!=null){
            Iterator<HashTableEntry<K, V>> iter = hashTable[hashValue].iterator();
            HashTableEntry<K, V> element;
            while(iter.hasNext()){
                element = iter.next();
                if (element.key.equals(key)) throw new KeyAlreadyExistException();
            }
        }
        else{
            LinkedList<HashTableEntry<K, V>> newHashEntryList = new LinkedList<HashTableEntry<K, V>>();
            hashTable[hashValue] = newHashEntryList;
        }

        //inserting into the 'dict' linked-list
        DictionaryEntry<K, V> newPair = new DictionaryEntry<K, V>(key,value);
        dict.addLast(newPair);dictSize+=1;

        //inserting into the 'hash-table'
        HashTableEntry<K, V> newHashTableEntry = new HashTableEntry<K, V>(key,newPair);
        hashTable[hashValue].addLast(newHashTableEntry);
    }

    public V delete(K key) throws NullKeyException, KeyNotFoundException{
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the associated value of type V with the argumented key
         * Working: Deletes the key-value pair from the Dictionary in O(1)
         */
        V val;
        //checking if key is not null
        if (key==null) throw new NullKeyException();

        //computing the hash value of key
        int hashValue = this.hash(key);

        //deleting the key-value from 'dict' list and hashTable
        if (hashTable[hashValue]==null) throw new KeyNotFoundException();
        Iterator<HashTableEntry<K, V>> iter = hashTable[hashValue].iterator();
        HashTableEntry<K, V> element;
        int keyFound = 0;
        while (iter.hasNext()){
            element = iter.next();
            if (element.key.equals(key)){
                val = element.dictEntry.value;
                dict.remove(element.dictEntry);dictSize-=1; //removes entry from dict
                iter.remove(); //removes Hashentry from the hashTable list
                keyFound+=1;
                return val;
                
            }
        }
        if (keyFound==0){throw new KeyNotFoundException();}
        return null;
    }

    public V update(K key, V value) throws NullKeyException, KeyNotFoundException{
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the previously associated value of type V with the argumented key
         * Working: Updates the value associated with argumented key with the argumented value in O(1)
         */
        V val;
        //checking if key is not null
        if (key==null) throw new NullKeyException();

        //computing the hash value of key
        int hashValue = this.hash(key);

        //updating value
        if (hashTable[hashValue]==null) throw new KeyNotFoundException();
        Iterator<HashTableEntry<K, V>> iter = hashTable[hashValue].iterator();
        HashTableEntry<K, V> element;
        int keyFound = 0;
        while (iter.hasNext()){
            element = iter.next();
            if (element.key.equals(key)){
                val = element.dictEntry.value;
                element.dictEntry.value = value; //modifying the value
                keyFound+=1;
                return val;
            }
        }
        if (keyFound==0) throw new KeyNotFoundException();
        return null;
    }

    public V get(K key) throws NullKeyException, KeyNotFoundException {
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the associated value of type V with the argumented key in O(1)
         */
        V val;
        //checking if key is not null
        if (key==null) throw new NullKeyException();

        //computing the hash value of key
        int hashValue = this.hash(key);

        //retreival
        if (hashTable[hashValue]==null) throw new KeyNotFoundException();
        Iterator<HashTableEntry<K, V>> iter = hashTable[hashValue].iterator();
        HashTableEntry<K, V> element;
        int keyFound = 0;
        while (iter.hasNext()){
            element = iter.next();
            if (element.key.equals(key)){
                val = element.dictEntry.value;  //retrieval
                keyFound+=1;
                return val;
            }
        }
        if (keyFound ==0) throw new KeyNotFoundException();
        return null;
    }

    public int size() {
        /*
         * To be filled in by the student
         * Return: Returns the size of the Dictionary in O(1)
         */

         return this.dictSize;
    }

    @SuppressWarnings("unchecked")
    public K[] keys(Class<K> cls) {
        /*
         * To be filled in by the student
         * Return: Returns array of keys stored in dictionary.
         */
        //creating the array and the pointer index
        K[] keysArray = (K[]) Array.newInstance(cls, dict.size());
        int ptr = 0;

        //iterating over dict and storing keys
        Iterator<DictionaryEntry<K, V>> iter = dict.iterator();
        DictionaryEntry<K, V> dictptrElement;
        while (iter.hasNext()){
            dictptrElement = iter.next();
            keysArray[ptr] = dictptrElement.key;
            ptr+=1;
        }
        return keysArray;
    }

    @SuppressWarnings("unchecked")
    public V[] values(Class<V> cls) {
        /*
         * To be filled in by the student
         * Return: Returns array of keys stored in dictionary.
         */
        V[] valuesArray = (V[]) Array.newInstance(cls, dict.size());
        int ptr = 0;

        //iterating over dict and storing keys
        Iterator<DictionaryEntry<K, V>> iter = dict.iterator();
        DictionaryEntry<K, V> dictptrElement;
        while (iter.hasNext()){
            dictptrElement = iter.next();
            valuesArray[ptr] = dictptrElement.value;
            ptr+=1;
        }
        return valuesArray;
    }

    public int hash(K key) {
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the hash of the argumented key using the Polynomial Rolling
         * Hash Function.
         */
        int p = 131; //initialising value p
        String key1 = (String) key;
        int hashValue = 0;
        for (int n = 0;n<key1.length();n++){
            int s = key1.charAt(n);s+=1;
            hashValue+=(COL106Dictionary.modulo(s,p,n,hashTableSize));
        }
        hashValue = hashValue%hashTableSize;
        return hashValue;
    }
    //student defined methods
    public boolean exists(K key){
        //if key exists then return true, else return false
        int hashValue = this.hash(key);
        if (hashTable[hashValue]==null) return false;
        Iterator<HashTableEntry<K, V>> iter= hashTable[hashValue].iterator();
        HashTableEntry<K, V> hashElement;
        while (iter.hasNext()){
            hashElement = iter.next();
            if (hashElement.key.equals(key)) return true;
        }
        return false;
    }
    public static int modulo(int b,int a,int power,int div){
        //this function makes use of modulo arithmetic to bypass storage
        //limitation of int datatype
        int res = b;
        for (int i =0;i<power;i++){
            res = ((res%div)*(a%div));
        }
        return res;
    }

}
