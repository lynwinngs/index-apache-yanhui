package com.index.apache.thinking.in.java.leetcode.topic5;

/**
 * <b>最长回文子串</b>
 * <p>
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 * <p>
 * 示例 1：
 * <p>
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * 示例 2：
 * <p>
 * 输入: "cbbd"
 * 输出: "bb"
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/11 21:26
 * @Version： 1.0
 */
class Solution {

    public static void main(String[] args) {
        System.out.println(longestPalindrome("aaabaaaa"));
    }

    public static String longestPalindrome(String s) {
        int length = s.length();
        if (length < 2) {
            return s;
        }
        String newStr = "$#";
        for (int i = 0; i < length; i++) {
            newStr += (s.charAt(i) + "#");
        }
        newStr += "%";

        int newLength = newStr.length() - 1;
        int maxStep = -1;
        int id = 2;
        int position = 2;
        int mx = 0;
        int[] p = new int[newLength];
        for (int i = 1; i < newLength; i++) {
            if (i < mx) {
                p[i] = Math.min(p[2 * id - i], mx - i);
            } else {
                p[i] = 1;
            }

            while (newStr.charAt(i - p[i]) == newStr.charAt(i + p[i])) {
                p[i]++;
            }

            if (mx < i + p[i]) {
                mx = i + p[i];
                id = i;
            }

            if (p[i] > maxStep) {
                maxStep = p[i];
                position = i;
            }
        }

        newStr = newStr.substring(position - (maxStep - 1), position + maxStep);
        return newStr.replace("#", "");
    }
}
