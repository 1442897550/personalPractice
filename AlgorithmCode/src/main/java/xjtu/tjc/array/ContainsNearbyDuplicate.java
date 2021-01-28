package xjtu.tjc.array;

import java.util.HashSet;
import java.util.Set;

public class ContainsNearbyDuplicate {
    public static void main(String[] args) {
        int[] array = new int[2];
        array[0] = 99;
        array[1] = 99;
        boolean b = containsNearbyDuplicate(array, 2);
        System.out.println(b);
    }

    /**
     * 第一种方法，二次循环，时间复杂度O(n方)
     * @param nums
     * @param k
     * @return
     */
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        for(int i = 0; i < nums.length; i++){
            for(int j = i+1; j <= k+i; j++){
                if (j >= nums.length){
                    break;
                }
                if(nums[j] == nums[i]){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 第二种方法，减少了几次循环，但是时间复杂度还是O(n方)
     * @param nums
     * @param k
     * @return
     */
    public boolean containsNearbyDuplicate1(int[] nums, int k) {
        while(k > 0){
            for(int i = 0; i < nums.length-k; i++){
                if(nums[i] == nums[i+k]){
                    return true;
                }
            }
            k--;
        }
        return false;
    }

    /**
     * 第三种方法，利用其他的数据结构进行
     * 利用set，向set种注入元素，如果注入的元素已经存在那么就说明长度小于k
     * 如果set的长度大于k，那么就删除第一个注入的元素
     * @param nums
     * @param k
     * @return
     */
    public boolean containsNearbyDuplicate2(int[] nums, int k) {
        Set s = new HashSet();
        for(int i = 0; i < nums.length; i++){
            if(s.contains(nums[i])) return true;
            s.add(nums[i]);
            if(s.size() > k){
                s.remove(nums[i-k]);
            }
        }
        return false;
    }
}
