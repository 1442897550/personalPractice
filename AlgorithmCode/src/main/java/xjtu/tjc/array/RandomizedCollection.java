package xjtu.tjc.array;

import java.util.ArrayList;
import java.util.Random;

public class RandomizedCollection {
    ArrayList<Integer> list;
    /** Initialize your data structure here. */
    public RandomizedCollection() {
        list = new ArrayList<Integer>();
    }

    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        list.add(val);
        if(list.contains(val)){
        return false;
    }
        return true;
}

    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        int index = list.indexOf(val);
        if(index == -1){
            return false;
        }else{
            list.remove(index);
            return true;
        }
    }

    /** Get a random element from the collection. */
    public int getRandom() {
        Random random = new Random();
        int index = random.nextInt(list.size()-1);
        return list.get(index);
    }
}
