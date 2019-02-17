package offer;

import java.util.Arrays;
import java.util.Stack;

/**
 * 输入一个数组
 * 输出数组中哪些数字出现次数多余一次
 */
public class Three {
    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 0, 2, 5, 3};
        printRepeatNumber(arr);
    }

    private static void printRepeatNumber(int[] arr) {
        // 1. 排序
        Arrays.sort(arr);
        // 2. 遍历数组,并输出
        Stack<Integer> stack = new Stack<Integer>();
        int len = arr.length;
        int i = 1;
        if (len <= 1) {
            return;
        }

        stack.push(arr[0]);
        boolean printFlag = false;
        while (i < len) {
            if (stack.peek() == arr[i] && !printFlag) {
                System.out.print(arr[i] + " ");
                printFlag = true;
            } else {
                stack.push(arr[i]);
                printFlag = false;
            }
            i++;
        }
    }
}
