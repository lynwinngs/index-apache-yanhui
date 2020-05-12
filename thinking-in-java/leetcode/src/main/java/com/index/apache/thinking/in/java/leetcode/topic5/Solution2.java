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
class Solution2 {

    public static void main(String[] args) {
        System.out.println(longestPalindrome("bananas"));
    }

    public static String longestPalindrome(String s) {
        if(s == null || s.length() == 0){
            return "";
        }

        char[] values = s.toCharArray();
        int[] range = new int[2];

        for (int i = 0; i < values.length; i++) {
            i = findLongest(values, i, range);
        }

        return s.substring(range[0], range[1] + 1);
    }

    private static int findLongest(char[] values, int cursor, int[] range) {
        int high = cursor;
        int low = cursor;

        // 通过 cursor 与 cursor + 1 比较，判断是奇回文还是偶回文
        // 如果不相等，则不是偶回文，以 cursor 作为中心点进行扩散即可
        // 如果相等，则 high 再 + 1，即 cursor 与 cursor + 2进行比较
        // 如果不相等则是偶回文，cursor 与 cursor + 1 是中心点
        // 如果相等则继续推进，进而判断出初始范围
        while (high < values.length - 1 && values[low] == values[high + 1]) {
            high++;
        }

        // 把连续重复字符当做一个字符，下一个游标就是 high，中间其他的可以直接跳过
        cursor = high;

        // 以 low 和 high 的范围作为初始坐标，向外扩散，如果两头相等，则是回文
        while (low > 0 && high < values.length - 1 && values[low - 1] == values[high + 1]) {
            low--;
            high++;
        }

        if (high - low > range[1] - range[0]) {
            range[0] = low;
            range[1] = high;
        }

        return cursor;
    }
}
