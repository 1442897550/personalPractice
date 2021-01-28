package xjtu.tjc.array;

import java.util.HashMap;
import java.util.Map;

/**
 * 主要元素
 * 数组中占比超过一半的元素称之为主要元素。给定一个整数数组，找到它的主要元素。若没有，返回-1。
 */

public class MainElement {
    public static void main(String[] args) {

    }
    /**
     * 第一种方法，时间复杂度O(n),空间复杂度O(n)
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < nums.length; i++){
            if(map.containsKey(nums[i])){
                Integer c = map.get(nums[i]);
                c++;
                map.put(nums[i],c);
            }else{
                map.put(nums[i],1);
            }
        }
        for(Integer key : map.keySet()){
            if(map.get(key) > nums.length/2){
                return key;
            }
        }
        return -1;
    }

    /**
     * 投票算法，找出数组中出现最多的元素，验证元素出现次数
     * 时间复杂度O(n),空间复杂度O(1)
     * @param nums
     * @return
     */
    public int majorityElement1(int[] nums) {
        //投票算法
        //找出数组中最多的元素
        int k = nums[0];
        int count = 1;
        for(int i = 1;i < nums.length; i++){
            if(k == nums[i]){
                count++;
            }else{count--;}
            if(count == 0){
                k = nums[i];
                count = 1;
            }
        }
        //验证是否大于长度的一半
        int l = nums.length/2 + 1;
        count = 0;
        for(int i : nums){
            if(i == k){
                count++;
            }
            if(count == l){
                return k;
            }
        }
        return -1;
    }
}
