package Juan;

import java.util.Arrays;

/**
 * @author Juan Martínez (https://www.linkedin.com/in/martinezgjuan)
 * @tested SPOJ_HORRIBLE, SPOJ_GSS1, SPOJ_GSS3, CF #275 Div1 B
 */
public class SegmentTreeCode {

  public static class SegmentTree {

    int N; // Power of 2. Size of the array covered by the tree.
    long[] tree; // Tree made of 2 * N nodes. Space O(2 * N)
    long[] lazy; // Stores the values to be pushed-down the tree only when
                 // needed. Space O(2 * N).

    /**********************************************************************************************/
    /** Modify this block for non-trivial sum updates */
    /**********************************************************************************************/

    /*
     * Identities of binary operations f(IDENTITY,x) = x, 0 for sum, +INF for
     * min, -INF for max, 0 for gcd, 0 for OR, +INF for AND
     */
    long[] IDENTITY = { 0 }; // The index of the identity is the number of the
                             // operation type

    // Associative binary operation used in the tree
    private long operation(long first, long sec, int type) {
      switch (type) {
      case 0:
        return first + sec; // Addition
        // case 0: return first |= sec; // OR
        // case 1: return first &= sec; // AND
        // case 0: return Math.max(first,sec); // Maximum
      default:
        return sec;
      }
    }

    // Accumulate the operation "type" to be done to the sub-tree without
    // actually pushing it down
    private long integrate(long act, long val, int nodeFrom, int nodeTo, int type) {
      switch (type) {
      case 0:
        return act + val * (nodeTo - nodeFrom + 1); // Addition
        // case 0: return act |= val; // OR
        // case 1: return act &= val; // AND
      default:
        return val;
      }
    }

    /**********************************************************************************************/

    // Propagate the lazily accumulated values down the tree with query
    // operator. O(1)
    public void propagate(int node, int leftChild, int rightChild, int nodeFrom, int mid,
            int nodeTo, int type) {
      // Calculates the value of the children by taking into account the lazy
      // values
      tree[leftChild] = integrate(tree[leftChild], lazy[node], nodeFrom, mid, type);
      tree[rightChild] = integrate(tree[rightChild], lazy[node], mid + 1, nodeTo, type);
      lazy[leftChild] = operation(lazy[leftChild], lazy[node], type);
      lazy[rightChild] = operation(lazy[rightChild], lazy[node], type);
      lazy[node] = IDENTITY[type];
    }

    // Constructor for the tree with the maximum expected value. O(maxExpected)
    public SegmentTree(int maxExpected, int type) {
      maxExpected++; // The tree will have a number of leaves equal to the
                     // lowest power of 2 greater that the maximum expected
                     // value
      int len = Integer.bitCount(maxExpected) > 1 ? Integer.highestOneBit(maxExpected) << 2
              : Integer.highestOneBit(maxExpected) << 1;
      N = len / 2;
      tree = new long[len];
      lazy = new long[len];
      if (tree[0] != IDENTITY[type]) {
        Arrays.fill(tree, IDENTITY[type]);
        Arrays.fill(lazy, IDENTITY[type]);
      }
    }

    // Constructor with the default operation type
    public SegmentTree(int maxExpected) {
      this(maxExpected, 0);
    }

    // Assign the initial values of the underlying array before the build in
    // O(1) time
    public void assignPreBuild(int index, long value) {
      tree[N + index] = value;
    }

    // Build the tree after assigning the initial values in O(N) time
    public void build() {
      build(1, 0, N - 1, 0);
    }

    // Build the tree after assigning the initial values in O(N) time
    public void build(int type) {
      build(1, 0, N - 1, type);
    }

    private long build(int node, int nodeFrom, int nodeTo, int type) {
      if (nodeTo != nodeFrom) {
        int leftChild = 2 * node;
        int rightChild = leftChild + 1;
        int mid = (nodeFrom + nodeTo) / 2;
        long a = build(leftChild, nodeFrom, mid, type); // Search left child
        long b = build(rightChild, mid + 1, nodeTo, type); // Search right child
        tree[node] = operation(a, b, type);
      }
      return tree[node];
    }

    // Update the values from the position i to j. Time complexity O(lg N)
    public void update(int i, int j, long newValue, int type) {
      update(1, 0, N - 1, i, j, newValue, type);
    }

    public void update(int i, int j, long newValue) {
      update(1, 0, N - 1, i, j, newValue, 0);
    }

    private void update(int node, int nodeFrom, int nodeTo, int i, int j, long v, int type) {
      if (j < nodeFrom || i > nodeTo) // No part of the range needs to be
                                      // updated
        return;
      else if (i <= nodeFrom && j >= nodeTo) { // Whole range needs to be
                                               // updated
        tree[node] = integrate(tree[node], v, nodeFrom, nodeTo, type);
        lazy[node] = operation(lazy[node], v, type);
      } else { // Some part of the range needs to be updated
        int leftChild = 2 * node;
        int rightChild = leftChild + 1;
        int mid = (nodeFrom + nodeTo) / 2;
        if (lazy[node] != IDENTITY[type]) // Propagate lazy operations if
                                          // necessary
          propagate(node, leftChild, rightChild, nodeFrom, mid, nodeTo, type);
        update(leftChild, nodeFrom, mid, i, j, v, type); // Search left child
        update(rightChild, mid + 1, nodeTo, i, j, v, type); // Search right
                                                            // child
        tree[node] = operation(tree[leftChild], tree[rightChild], type); // Merge
                                                                         // with
                                                                         // the
                                                                         // operation
      }
    }

    // Query the range from i to j. Time complexity O(lg N)
    public long query(int i, int j, int type) {
      return query(1, 0, N - 1, i, j, type);
    }

    public long query(int i, int j) {
      return query(1, 0, N - 1, i, j, 0);
    }

    private long query(int node, int nodeFrom, int nodeTo, int i, int j, int type) {
      if (j < nodeFrom || i > nodeTo) // No part of the range belongs to the
                                      // query
        return IDENTITY[type];
      else if (i <= nodeFrom && j >= nodeTo) // The whole range is part of the
                                             // query
        return tree[node];
      else { // Partially within the range
        int leftChild = 2 * node;
        int rightChild = leftChild + 1;
        int mid = (nodeFrom + nodeTo) / 2;
        if (lazy[node] != IDENTITY[type]) // Propagate lazy operations if
                                          // necessary
          propagate(node, leftChild, rightChild, nodeFrom, mid, nodeTo, type);
        long a = query(leftChild, nodeFrom, mid, i, j, type); // Search left
                                                              // child
        long b = query(rightChild, mid + 1, nodeTo, i, j, type); // Search right
                                                                 // child
        return operation(a, b, type);
      }
    }
  }

  public static void main(String args[]) {

    SegmentTree rt = new SegmentTree(4);
    int[] A = { 1, 2, 3, 4 };
    for (int i = 0; i < 4; i++)
      rt.assignPreBuild(i, A[i]);
    rt.build();

    rt.update(2, 4, 5, 0);
    // rt.updateR(0,2,2,-1);
    System.out.println(rt.query(0, 0));
    System.out.println(rt.query(1, 2));
    System.out.println(rt.query(0, 3));
    System.out.println(rt.query(0, 2));
  }

}
