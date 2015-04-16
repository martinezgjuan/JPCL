package Juan;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * @author Juan Martínez (https://www.linkedin.com/in/martinezgjuan)
 * @tested UVA_10107
 */
class MedianBagCode {

  static class MedianBag {

    PriorityQueue<Integer> pqMin;
    PriorityQueue<Integer> pqMax;

    public MedianBag() {
      pqMin = new PriorityQueue<Integer>();
      pqMax = new PriorityQueue<Integer>(Collections.reverseOrder());
    }

    // O(lg n)
    public void add(Integer a) {
      pqMin.add(a);
      pqMax.add(pqMin.remove());
      pqMin.add(pqMax.remove());
      if (pqMin.size() > pqMax.size() + 1)
        pqMax.add(pqMin.remove());
    }

    // O(1)
    public boolean isEmpty() {
      return pqMin.size() == 0;
    }

    // O(1)
    public int size() {
      return pqMin.size() + pqMax.size();
    }

    // O(1)
    public int getMedian() {
      if (pqMin.size() + pqMax.size() == 0)
        throw new NoSuchElementException();
      if ((pqMin.size() + pqMax.size()) % 2 == 0)
        return (pqMin.peek() + pqMax.peek()) / 2;
      else
        return pqMin.peek();
    }

    // O(n)
    public boolean remove(Integer a) {
      if (pqMin.remove(a)) {
        if (pqMax.size() > pqMin.size())
          pqMin.add(pqMax.remove());
        return true;
      } else if (pqMax.remove(a)) {
        if (pqMin.size() > pqMax.size() + 1)
          pqMax.add(pqMin.remove());
        return true;
      } else {
        return false;
      }
    }

  }
}