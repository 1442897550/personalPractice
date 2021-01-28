package xjtu.tjc.array;

import java.util.Arrays;

/**
 * 三个数的最大乘积
 */

public class MaxProductByThree {
    /**
     * 第一种方法，简单的列举法
     * 时间复杂度O(nlogn),主要为排序的时间
     * @param nums
     * @return
     */
    public int maximumProduct(int[] nums) {
        if(nums.length == 3){
            return nums[0]*nums[1]*nums[2];
        }
        Arrays.sort(nums);
        //数组中全部为大于0的情况或者全部小于等于0的情况
        if(nums[0] > 0 || nums[nums.length-1] <= 0){
            return nums[nums.length-1]*nums[nums.length-2]*nums[nums.length-3];
        }else{
            if(nums[0]*nums[1] > nums[nums.length-2]*nums[nums.length-3]){
                return nums[0]*nums[1]*nums[nums.length-1];
            }else{
                return nums[nums.length-1]*nums[nums.length-2]*nums[nums.length-3];
            }
        }
    }

    /**
     * 第二种方法，一次扫描找出五个值，求出最大的
     * 注意如何求出最大，第二大，第三大，最小，第二小的方法是关键
     * @param nums
     * @return
     */
    public int maximumProduct2(int[] nums) {
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int max3 = Integer.MIN_VALUE;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] < min1){
                min2= min1;
                min1 = nums[i];
            }else if(nums[i] < min2){
                min2 = nums[i];
            }
            if(nums[i] > max1){
                max3 = max2;
                max2 = max1;
                max1 = nums[i];
            }else if(nums[i] > max2){
                max3 = max2;
                max2 = nums[i];
            }else if(nums[i] > max3){
                max3 = nums[i];
            }
        }
        return Math.max(min1*min2*max1,max1*max2*max3);
    }
}

