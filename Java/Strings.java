/**
 * @author Juan Martínez (https://www.linkedin.com/in/martinezgjuan)
 */
public class Strings {

  /**
   * Calculates the Z array where each position <code>i</code> represents the longest suffix match
   * between the substring of <code>str</code> starting from <code>i</code> and the string
   * <code>str</code>
   * 
   * @param str
   *          String used to calculate the Z array
   * @return Z array
   * @time O(n) where n is the length of <code>input</code>
   * @tested CF1_299B (Fastest execution time with Java 8 by 16/04/15)
   */
  public static int[] zFunction(String str) {
    int len = str.length();
    int[] z = new int[len];

    for (int i = 1, left = 0, right = 0; i < len; i++) {
      if (i <= right) { // Take advantage of previous calculations
        z[i] = Math.min(right - i + 1, z[i - left]);
      }
      // Calculate by brute force the rest of the answer for z[i]
      while (i + z[i] < len && str.charAt(z[i]) == str.charAt(i + z[i])) {
        z[i]++;
      }
      // If a match was found update the [l,r] rightmost segment
      if (i + z[i] - 1 > right) {
        left = i;
        right = i + z[i] - 1;
      }
    }
    z[0] = len; // No rotation produces a full length match
    return z;
  }

  /**
   * Left pads a string to a length <code>n</code> (<code>n</code> >= |<code>str</code>|) with the
   * character <code>c</code>
   * 
   * @time O(n)
   */
  public static String padLeft(String str, int n, char c) {
    if (str.length() >= n) {
      return str;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n - str.length(); i++) {
      sb.append(c);
    }
    sb.append(str);
    return sb.toString();
  }

  /**
   * Right pads a string to a length <code>n</code> (<code>n</code> >= |<code>str</code>|) with the
   * character <code>c</code>
   * 
   * @time O(n)
   */
  public static String padRight(String str, int n, char c) {
    if (str.length() >= n) {
      return str;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(str);
    for (int i = 0; i < n - str.length(); i++) {
      sb.append(c);
    }
    return sb.toString();
  }

  /**
   * Reverses a String
   * 
   * @time O(length of <code>str</code>)
   */
  public static String reverse(String str) {
    StringBuilder result = new StringBuilder(str);
    result.reverse();
    return result.toString();
  }

  /**
   * Counts the appearances of the character <code>c</code> in the String <code>str</code>
   * 
   * @time O(length of <code>str</code>)
   */
  public static int count(String str, char c) {
    int count = 0;
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == c) {
        count++;
      }
    }
    return count;
  }

  /**
   * Returns the concatenation of several Strings
   * 
   * @time O(total number of characters)
   */
  public static String concat(String[] array) {
    StringBuilder result = new StringBuilder();
    for (String str : array) {
      result.append(str);
    }
    return result.toString();
  }

  /**
   * Number of differences between same-sized Strings
   * 
   * @time O(length of <code>str1</code>)
   */
  public static int differs(String str1, String str2) {
    int result = 0;
    for (int i = 0; i < str1.length(); i++) {
      if (str1.charAt(i) != str2.charAt(i)) {
        result++;
      }
    }
    return result;
  }

}
