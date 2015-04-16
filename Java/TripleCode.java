package Juan;

/**
 * @author Juan Martínez (https://www.linkedin.com/in/martinezgjuan)
 * @tested FBHC2015_Qual3, UVA_11367
 */
public class TripleCode {

  static public class Triple<U extends Comparable<U>, V extends Comparable<V>, W extends Comparable<W>>
          implements Comparable<Triple<U, V, W>> {
    public final U first;
    public final V second;
    public final W third;

    public Triple(U first, V second, W third) {
      this.first = first;
      this.second = second;
      this.third = third;
    }

    public int compareTo(Triple<U, V, W> that) {
      int value = this.first.compareTo(that.first);
      if (value != 0)
        return value;
      value = this.second.compareTo(that.second);
      if (value != 0)
        return value;
      return this.third.compareTo(that.third);
    }

    @Override
    public boolean equals(Object other) {
      if (!(other instanceof Triple)) {
        return false;
      }
      Triple that = (Triple<U, V, W>) other;
      return this.first.equals(that.first) && this.second.equals(that.second)
              && !this.third.equals(that.third);
    }

    @Override
    public int hashCode() {
      return 31 * (31 * (527 + first.hashCode()) + second.hashCode()) + third.hashCode();
    }

    @Override
    public String toString() {
      return "(" + first + ", " + second + ", " + third + ")";
    }
  }

}
