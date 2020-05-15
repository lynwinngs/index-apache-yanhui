package com.index.apache.thinking.in.java.leetcode.topic6;

/**
 * <b>Z 字形变换</b>
 * <p>
 * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
 * <p>
 * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。
 * <p>
 * 请你实现这个将字符串进行指定行数变换的函数：
 * <p>
 * string convert(string s, int numRows);
 * 示例 1:
 * <p>
 * 输入: s = "LEETCODEISHIRING", numRows = 3
 * 输出: "LCIRETOESIIGEDHN"
 * 示例 2:
 * <p>
 * 输入: s = "LEETCODEISHIRING", numRows = 4
 * 输出: "LDREOEIIECIHNTSG"
 * 解释:
 * <p>
 * L     D     R
 * E   O E   I I
 * E C   I H   N
 * T     S     G
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/zigzag-conversion
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/11 21:26
 * @Version： 1.0
 */
class Solution {

    public static void main(String[] args) {
        System.out.println(convert("SB", 1));
    }

    public static String convert(String s, int numRows) {
        if (s == null) {
            return "";
        }
        if(numRows == 1){
            return s;
        }
        int length = s.length();
        if (s.length() < numRows) {
            numRows = s.length();
        }
        boolean director = false;
        int count = 0;
        String[] strings = new String[numRows];
        for (int i = 0; i < length; i++) {
            if (strings[i % numRows] == null) {
                strings[i % numRows] = "";
            }
            strings[count] += s.charAt(i);

            if (count == 0 || count == numRows-1) {
                director = !director;
            }
            if (director) {
                count++;
            } else {
                count--;
            }
        }
        String result = "";
        for (int i = 0; i < strings.length; i++) {
            result += strings[i];
        }
        return result;
    }
}
