package Juan;

public class Strings {

  // Left pad a string to length n >= |s| with the character provided
  public static String padLeft(String s, int n, char c) {
    if (s.length() >= n)
      return s;
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n - s.length(); i++)
      sb.append(c);
    sb.append(s);
    return sb.toString();
  }

  // Right pad a string to length n >= |s| with the character provided
  public static String padRight(String s, int n, char c) {
    if (s.length() >= n)
      return s;
    StringBuilder sb = new StringBuilder();
    sb.append(s);
    for (int i = 0; i < n - s.length(); i++)
      sb.append(c);
    return sb.toString();
  }

  // Reverse a string
  public static String reverse(String sample) {
    StringBuilder result = new StringBuilder(sample);
    result.reverse();
    return result.toString();
  }

  // Counts the appearances of the specified character in the String
  public static int count(String string, char c) {
    int count = 0;
    for (int i = 0; i < string.length(); i++) {
      if (string.charAt(i) == c) {
        count++;
      }
    }
    return count;
  }

  // Concatenates several strings in O(total characters)
  public static String concat(String[] array) {
    StringBuilder result = new StringBuilder();
    for (String s : array) {
      result.append(s);
    }
    return result.toString();
  }

  // Number of differences between same-sized Strings
  public static int differs(String first, String second) {
    int result = 0;
    for (int i = 0; i < first.length(); i++) {
      if (first.charAt(i) != second.charAt(i)) {
        result++;
      }
    }
    return result;
  }

}
