/*
 * Copyright (C) 2016 Digital River, Inc. All Rights Reserved
 *
 */
package dsaa.array;

import java.util.Arrays;
import java.util.Random;

/**
 * @author <a href="mailto:iliao@digitalriver.com">Isaac Liao</a>
 */
public class QuickSort {

    public static void main(String[] params) {
        int[] ary = new int[10];
        Random r = new Random();
        for ( int i = 0; i < ary.length; i++ ) {
            ary[i] = r.nextInt(ary.length);
        }
        System.out.println("before sort:" + Arrays.toString(ary));
        sort(ary, 0, ary.length-1);
        System.out.println("after sort:" + Arrays.toString(ary));
    }

    private static void sort(int[] ary, int left, int right) {
        if (left > right) {
            return;
        }
        int start = left;
        int end = right;
        int pivot = ary[left];
        while (start != end) {
            while (ary[end] >= pivot && start < end) {
                end--;
            }
            while (ary[start] <= pivot && start < end) {
                start++;
            }
            if (start < end) {
                int tmp = ary[start];
                ary[start] = ary[end];
                ary[end] = tmp;
            }
        }
        ary[left] = ary[start];
        ary[start] = pivot;
        sort(ary, left, start-1);
        sort(ary, start+1, right);
    }

    private static void swap(int[] ary, int leftIdx, int rightIdx) {
        int tmp = ary[leftIdx];
        ary[leftIdx] = ary[rightIdx];
        ary[rightIdx] = tmp;
    }
}
