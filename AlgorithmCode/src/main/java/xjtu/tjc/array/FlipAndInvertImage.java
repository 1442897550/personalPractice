package xjtu.tjc.array;

/**
 * 给定一个二进制矩阵 A，我们想先水平翻转图像，然后反转图像并返回结果。
 *
 * 水平翻转图片就是将图片的每一行都进行翻转，即逆序。例如，水平翻转 [1, 1, 0] 的结果是 [0, 1, 1]。
 *
 * 反转图片的意思是图片中的 0 全部被 1 替换， 1 全部被 0 替换。例如，反转 [0, 1, 1] 的结果是 [1, 0, 0]。
 */
public class FlipAndInvertImage {
    /**
     * 第一种方法，遍历反转并且改值
     * @param A
     * @return
     */
    public int[][] flipAndInvertImage(int[][] A) {
        int width = A[0].length;
        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < width/2; j++){
                int temp = A[i][j];
                A[i][j] = A[i][width-j-1];
                A[i][width-j-1] = temp;
            }
            for(int k = 0; k < width; k++){
                if(A[i][k] == 0){
                    A[i][k] = 1;
                }else{
                    A[i][k] = 0;
                }
            }
        }
        return A;

    }

    /**
     * 第二种方法，一次遍历，把所有工作全部完成
     * @param A
     * @return
     */
    public int[][] flipAndInvertImage1(int[][] A) {
        int width = A[0].length;
        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < width/2; j++){
                int temp = A[i][j];
                if(A[i][width-j-1] == 0){
                    A[i][j] = 1;
                }else{
                    A[i][j] = 0;
                }
                if(temp == 0){
                    A[i][width-j-1] = 1;
                }else{
                    A[i][width-j-1] = 0;
                }
            }
            if(width%2 == 1){
                if(A[i][width/2] == 0){
                    A[i][width/2] = 1;
                }else{
                    A[i][width/2] = 0;
                }
            }
        }
        return A;

    }
}
