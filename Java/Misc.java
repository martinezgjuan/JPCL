import java.io.IOException; 
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Juan Martínez (https://www.linkedin.com/in/martinezgjuan)
 */
public class Misc {

  /**
   * Bijection between Pairs of Integers and Integers
   * @tested UVA_11367, UVA_11380
   */
  static int maxSecond;

  static int from2to1(int first, int second) {
    return first * maxSecond + second;
  }

  static int[] from1to2(int index) {
    return new int[] { index / maxSecond, index % maxSecond };
  }

  /**
   * Swaps two given elements in an array
   * @time O(1)
   */
  static void swap(int[] array, int indA, int indB) {
    int temp = array[indA];
    array[indA] = array[indB];
    array[indB] = temp;
  }

  /**
   * Finds the kth largest element in an array
   * @time O(n)
   */
  static int quickSelect(int[] array, int k) {
    Random r = new Random(0);

    int left = 0;
    int right = array.length - 1;

    while (left < right) {
      int indexPivot = r.nextInt(right - left) + left;
      int pivot = array[indexPivot];

      swap(array, indexPivot, right);

      int firstNotLowerP = left;
      for (int i = left; i <= right; i++) {
        if (array[i] < pivot) {
          swap(array, firstNotLowerP, i);
          firstNotLowerP++;
        }
      }

      swap(array, right, firstNotLowerP);

      if (firstNotLowerP == k) {
        return array[firstNotLowerP];
      } else if (firstNotLowerP < k) {
        left = firstNotLowerP + 1;
      } else {
        right = firstNotLowerP;
      }
    }
    return left;
  }
  
  /**
   * Maximum subarray sum 
   * @time O(n)
   * @tested UVA_105 UVA_507 UVA_10684 UVA_10827
   */
  static int[] kadane(int[] nums) {
    int maxSum = Integer.MIN_VALUE;
    int curSum = 0;
    int start = 0;
    int end = 0;
    int bestStart = start;
    int bestEnd = end;

    for (int i = 0; i < nums.length; i++) {
      curSum += nums[i];
      if (curSum > maxSum) {
        maxSum = curSum;
        bestStart = start;
        bestEnd = end;
      }
      if (curSum < 0) {
        curSum = 0;
        start = i + 1;
        end = i + 1;
      } else {
        end = i;
      }
    }
    return new int[] { maxSum, bestStart, bestEnd };
  }
  
  /**
   * Maximum subrectangle sum 
   * @time O(n ^ 3)
   * @tested UVA_105 UVA_10827
   */
  public static int[] kadane2D(int[][] nums) {
    int maxSum = Integer.MIN_VALUE;
    int bestStartRow = -1;
    int bestEndRow = -1;
    int bestStartCol = -1;
    int bestEndCol = -1;

    for (int curStartRow = 0; curStartRow < nums.length; curStartRow++) {
      int[] accumRow = new int[nums[0].length]; // Partial sum of rows
      // Try adding new rows one by one with the same start
      for (int curEndRow = curStartRow; curEndRow < nums.length; curEndRow++) {
        for (int k = 0; k < nums[0].length; k++) {
          accumRow[k] += nums[curEndRow][k];
        }
        // O(n) obtain maximal sum subarray of the accumulated row
        int[] kadaneColumns = kadane(accumRow);
        if (kadaneColumns[0] > maxSum) {
          bestStartRow = curStartRow;
          bestEndRow = curEndRow;
          bestStartCol = kadaneColumns[1];
          bestEndCol = kadaneColumns[2];
          maxSum = kadaneColumns[0];
        }
      }
    }
    return new int[] { maxSum, bestStartRow, bestEndRow, bestStartCol, bestEndCol };
  }
  
  /**
   * Maximum (non-empty) subarray product
   * @time O(n) (Note: time consuming BigInteger multiplications)
   * @tested UVA_787
   */
  public static BigInteger maxSubProduct(long[] nums) {
    BigInteger best = BigInteger.valueOf(nums[0]);

    int n = nums.length;

    boolean negFound = false;
    boolean nextNeg = false;

    for (int begIndex = 0; begIndex < n; begIndex++) {
      BigInteger cur = BigInteger.valueOf(nums[begIndex]);
      if (cur.compareTo(best) > 0) {
        best = cur;
      }

      if (nums[begIndex] == 0) {
        negFound = false;
        nextNeg = false;
        continue;
      }

      if (nextNeg) {
        continue;
      }

      for (int extra = begIndex + 1; extra < n && nums[extra] != 0; extra++) {
        cur = cur.multiply(BigInteger.valueOf(nums[extra]));
        if (cur.compareTo(best) > 0) {
          best = cur;
        }
      }

      if (negFound) {
        nextNeg = true;
      }

      if (nums[begIndex] < 0) {
        negFound = true;
      }
    }

    return best;
  }

  /**
   * Generates all the permutations of the given array
   * Recursive collection input version - O(n!) about 4.2 seconds for size 10
   * and 100ms for size 9
   */ 
  static <T> ArrayList<ArrayList<T>> permutations(ArrayList<T> arr) {
    T out = arr.remove(0);
    ArrayList<ArrayList<T>> resIn;
    if (arr.size() <= 1) {
      resIn = new ArrayList<ArrayList<T>>();
      resIn.add(arr);
    } else {
      resIn = permutations(arr);
    }
    ArrayList<ArrayList<T>> res = new ArrayList<ArrayList<T>>();
    for (ArrayList<T> act : resIn) {
      for (int j = 0; j <= act.size(); j++) {
        ArrayList<T> tmp2 = new ArrayList<T>(act);
        tmp2.add(j, out);
        res.add(tmp2);
      }
    }
    return res;
  }

  /** 
   * Generates all the permutations of the given array
   * Iterative collection input version - O(n!) about 2 seconds for size 10
   * and 100ms for size 9
   */ 
  static <T> ArrayList<ArrayList<T>> permute(ArrayList<T> arr) {
    ArrayList<ArrayList<T>> result = new ArrayList<ArrayList<T>>();
    result.add(new ArrayList<T>());
    for (T act : arr) {
      ArrayList<ArrayList<T>> current = new ArrayList<ArrayList<T>>();
      for (ArrayList<T> l : result) {
        for (int j = 0; j < l.size() + 1; j++) {
          ArrayList<T> temp = new ArrayList<T>(l);
          temp.add(j, act);
          current.add(temp);
        }
      }
      result = new ArrayList<ArrayList<T>>(current);
    }
    return result;
  }

  /** 
   * Generates all the permutations of the given array
   * Iterative array input version - O(n!) about 2 seconds for size 10 and
   * 100ms for size 9
   */
  static <T> ArrayList<ArrayList<T>> permute(T[] arr) {
    ArrayList<ArrayList<T>> result = new ArrayList<ArrayList<T>>();
    result.add(new ArrayList<T>());
    for (T act : arr) {
      ArrayList<ArrayList<T>> current = new ArrayList<ArrayList<T>>();
      for (ArrayList<T> l : result) {
        for (int j = 0; j < l.size() + 1; j++) {
          ArrayList<T> temp = new ArrayList<T>(l);
          temp.add(j, act);
          current.add(temp);
        }
      }
      result = new ArrayList<ArrayList<T>>(current);
    }
    return result;
  }

  /**
   * Modifies a Collection of Objects into the next lexicographic permutation of
   * its elements if it is possible.
   * 
   * @param list Collection of elements to be modified
   * @return <code>true</code> if another permutation was possible
   * @time O(|arr|). Performance test: 0.58 seconds to traverse through all the
   *       permutations of an array of 11 elements
   * @tested UVa_146
   */
  public static <T extends Comparable<T>> boolean nextPermutation(List<T> list) {
    for (int i = list.size() - 2; i >= 0; --i) {
      // Search for the last increasing pair of consecutive numbers
      if (list.get(i).compareTo(list.get(i + 1)) < 0) { 
        for (int j = list.size() - 1;; --j) {
          // Search for the last number bigger than list[i] and swap them
          if (list.get(j).compareTo(list.get(i)) > 0) { 
            T temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
            // Reverse the (already decreasing) sublist after the position i
            for (++i, j = list.size() - 1; i < j; ++i, --j) { 
              temp = list.get(i);
              list.set(i, list.get(j));
              list.set(j, temp);
            }
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Modifies an array of Objects into the next lexicographic permutation of its
   * elements if it is possible.
   * 
   * @param arr
   *          Array of elements to be modified
   * @return <code>true</code> if another permutation was possible
   * @time O(|arr|). Performance test: 0.58 seconds to traverse through all the
   *       permutations of an array of 11 elements
   * @tested UVa_146
   */
  public static <T extends Comparable<T>> boolean nextPermutation(T[] arr) {
    for (int i = arr.length - 2; i >= 0; --i) {
      // Search for the last increasing pair of consecutive numbers
      if (arr[i].compareTo(arr[i + 1]) < 0) {
        for (int j = arr.length - 1;; --j) {
          // Search for the last number bigger than arr[i] and swap them
          if (arr[j].compareTo(arr[i]) > 0) {
            T temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            // Reverse the (already decreasing) subarray after the position i
            for (++i, j = arr.length - 1; i < j; ++i, --j) {
              temp = arr[i];
              arr[i] = arr[j];
              arr[j] = temp;
            }
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Modifies an array or primitives into the next lexicographic permutation of
   * its elements if it is possible.
   * 
   * @param arr
   *          Array of elements to be modified
   * @return <code>true</code> if another permutation was possible
   * @time O(|arr|). Performance test: 0.58 seconds to traverse through all the
   *       permutations of an array of 11 elements
   * @tested UVa_146
   */
  static boolean nextPermutation(int[] arr) {
    for (int i = arr.length - 2; i >= 0; --i) {
      // Search for the last increasing pair of consecutive numbers
      if (arr[i] < arr[i + 1]) {
        for (int j = arr.length - 1;; --j) {
          // Search for the last number bigger than arr[i] and swap them
          if (arr[j] > arr[i]) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            // Reverse the (already decreasing) subarray after the position i
            for (++i, j = arr.length - 1; i < j; ++i, --j) {
              temp = arr[i];
              arr[i] = arr[j];
              arr[j] = temp;
            }
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Generates all the subsets of the given set 
   * @time O((2 ^ n) * n)
   */
  static <T> ArrayList<ArrayList<T>> subsets(ArrayList<T> arr) {
    ArrayList<ArrayList<T>> res = new ArrayList<ArrayList<T>>();
    int pow = (int) Mathematics.power((long) 2, (long) arr.size());
    for (int k = 0; k < pow; k++) {
      ArrayList<T> act = new ArrayList<T>();
      for (int i = 0; i < arr.size(); i++) {
        if (((1 << i) & k) != 0) {
          act.add(arr.get(i));
        }
      }
      res.add(act);
    }
    return res;
  }

  /**
   * Reverts the order of the given array between the indexes provided
   * @time O(to - from)
   * @extraSpace O(1)
   */
  public static void reverse(int[] array, int from, int to) {
    for (int i = from, j = to; i < j; i++, j--) {
      int temp = array[i];
      array[i] = array[j];
      array[j] = temp;
    }
  }

  /**
   * Converts the given collection into an array
   * @time O(n)
   */
  public static int[] toArray(Collection<Integer> collection) {
    int[] array = new int[collection.size()];
    int index = 0;
    for (int element : collection)
      array[index++] = element;
    return array;
  }

  /**
   * Converts the given iterable into an ArrayList
   * @time O(n)
   */
  public static <T> ArrayList<T> toArrayList(Iterable<T> iterable) {
    ArrayList<T> list = new ArrayList<T>();
    for (T element : iterable)
      list.add(element);
    return list;
  }

  /**
   * Counts the number of elements in the iterable equal to specified element
   * @time O(n)
   */
  public static <T> int count(Iterable<T> iterable, T sample) {
    int result = 0;
    for (T element : iterable) {
      if (element.equals(sample))
        result++;
    }
    return result;
  }

  /**
   * Finds the minimum element in the given iterable
   * @time O(n)
   */
  @SuppressWarnings("unchecked")
  public static <T> T min(Iterable<T> iterable) {
    T res = null;
    for (T act : iterable) {
      if (res == null || ((Comparable<T>) act).compareTo(res) < 0)
        res = act;
    }
    return res;
  }

  /**
   * Finds the maximum element in the given iterable
   * @time O(n)
   */
  @SuppressWarnings("unchecked")
  public static <T> T max(Iterable<T> array) {
    T res = null;
    for (T act : array) {
      if (res == null || ((Comparable<T>) act).compareTo(res) > 0)
        res = act;
    }
    return res;
  }

  /**
   * Returns a bit mask representing the digits used by the input number
   * @tested UVA_725
   */
  public static int digitsUsed(int number) {
    int used = 0;
    while (number > 0) {
      used |= (1 << (number % 10));
      number /= 10;
    }
    return used;
  }

  public static void main(String[] args) throws IOException {
    // Test for Quick select
    Random r = new Random(0L);
    int n = 1 + r.nextInt(20);
    int[] a = new int[n];

    ArrayList<Integer> x = new ArrayList<Integer>();
    for (int i = 0; i < n; i++) {
      x.add(i / 4);
    }

    Collections.shuffle(x);
    for (int i = 0; i < n; i++) {
      a[i] = x.get(i);
    }

    System.out.println(x.toString());

    for (int i = 0; i < a.length - 1; i++) {
      System.out.println(quickSelect(a.clone(), i));
    }
  }
}
