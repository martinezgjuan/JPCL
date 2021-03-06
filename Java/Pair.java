package Juan;

/**
 * @author Juan Martínez (https://www.linkedin.com/in/martinezgjuan)
 * @tested UVA_10765, UVA_10986, UVA_11367, UVA_315
 */
public class PairCode {

  static public class Pair<U extends Comparable<U>, V extends Comparable<V>> implements
          Comparable<Pair<U, V>> {
    public final U first;
    public final V second;

    public Pair(U first, V second) {
      this.first = first;
      this.second = second;
    }

    public int compareTo(Pair<U, V> that) {
      int value = this.first.compareTo(that.first);
      if (value != 0)
        return value;
      return this.second.compareTo(that.second);
    }

    @Override
    public boolean equals(Object other) {
      if (!(other instanceof Pair)) {
        return false;
      }
      Pair that = (Pair<U, V>) other;
      return this.first.equals(that.first) && this.second.equals(that.second);
    }

    @Override
    public int hashCode() {
      return 31 * (527 + first.hashCode()) + second.hashCode();
    }

    @Override
    public String toString() {
      return "(" + first + ", " + second + ")";
    }
  }

}
