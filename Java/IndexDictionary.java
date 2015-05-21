import java.util.HashMap;

/**
 * @author Juan Martínez (https://www.linkedin.com/in/martinezgjuan)
 * @tested UVa_247, UVA_429, UVA_11367
 */
public class IndexDictionary<T> {
  int count = 0;
  HashMap<T, Integer> indices = new HashMap<T, Integer>();
  HashMap<Integer, T> values = new HashMap<Integer, T>();

  void add(T val) {
    if (!indices.containsKey(val)) {
      indices.put(val, count);
      values.put(count, val);
      count++;
    }
  }

  int getIndex(T val) {
    return indices.get(val);
  }

  T getValue(int index) {
    return values.get(index);
  }

  @Override
  public String toString() {
    return indices.toString();
  }
}
