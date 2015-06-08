import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Template {

	static Reader sc = new Reader();
	static PrintWriter out = new PrintWriter(System.out); 

	public static void main(String[] args) throws IOException {				

		out.close();
	}

  static class Reader {
    final private int BUFFER_SIZE = 1 << 12;private byte[] buffer;private int bufferPointer, bytesRead;private boolean reachedEnd = false;public Reader() {buffer = new byte[BUFFER_SIZE];bufferPointer = 0;bytesRead = 0;}
    public boolean hasNext() {return !reachedEnd;} private void fillBuffer() throws IOException {bytesRead = System.in.read(buffer, bufferPointer = 0, BUFFER_SIZE);if (bytesRead == -1) {buffer[0] = -1;reachedEnd = true;}}
    private void consumeSpaces() throws IOException {while (read() <= ' ' && reachedEnd == false);bufferPointer--;} private byte read() throws IOException {if (bufferPointer == bytesRead) {fillBuffer();}return buffer[bufferPointer++];}
    public String next() throws IOException {StringBuilder sb = new StringBuilder();consumeSpaces();byte c = read();do {sb.append((char) c);} while ((c = read()) > ' ');consumeSpaces();if (sb.length() == 0) {return null;}return sb.toString();}
    public String nextLine() throws IOException {StringBuilder sb = new StringBuilder();byte c;boolean read = false;while ((c = read()) != -1) {if (c == '\n') {read = true;break;}if (c >= ' ')sb.append((char) c);}if (!read) {return null;}return sb.toString();}
    public int nextInt() throws IOException {consumeSpaces();int ret = 0;      byte c = read();boolean neg = (c == '-');if (neg) {c = read();}do {ret = ret * 10 + c - '0';} while ((c = read()) >= '0' && c <= '9');consumeSpaces();if (neg) {return -ret;}return ret;}
    public long nextLong() throws IOException {consumeSpaces();long ret = 0;byte c = read();boolean neg = (c == '-');if (neg) {c = read();}do {ret = ret * 10L + c - '0';} while ((c = read()) >= '0' && c <= '9');consumeSpaces();if (neg) {return -ret;}return ret;}
    public double nextDouble() throws IOException {consumeSpaces();double ret = 0;double div = 1;byte c = read();boolean neg = (c == '-');if (neg) {c = read();}do {ret = ret * 10 + c - '0';} while ((c = read()) >= '0' && c <= '9');if (c == '.') {while ((c = read()) >= '0' && c <= '9') {ret += (c - '0') / (div *= 10);}}consumeSpaces();if (neg) {return -ret;}return ret;}    public int[] nextIntArray(int n) throws IOException {int[] a = new int[n];for (int i = 0; i < n; i++) {a[i] = nextInt();}return a;}
    public int[][] nextIntMatrix(int n, int m) throws IOException {int[][] grid = new int[n][m];for (int i = 0; i < n; i++) {grid[i] = nextIntArray(m);}return grid;}
    public char[][] nextCharacterMatrix(int n) throws IOException {char[][] a = new char[n][];for (int i = 0; i < n; i++) {a[i] = next().toCharArray();}return a;}
    public void close() throws IOException {if (System.in == null) {return;} else {System.in.close();}}
  }
  
  static void print(int[] A) {for (int i = 0; i < A.length; i++) {if (i != 0) {out.print(' ');}out.print(A[i]);}out.println();}
  static <T> void print(Iterable<T> A) {int i = 0;for (T act : A) {if (i != 0) {out.print(' ');}out.print(act);i++;}out.println();}
  static void printPlus1(Iterable<Number> A) {int i = 0;for (Number act : A) {if (i != 0) {out.print(' ');}out.print(act.longValue() + 1L);i++;}out.println();  }
  static void debug(Object... o) {System.err.println(Arrays.deepToString(o));}
  
  /*
  long s = System.currentTimeMillis();
  debug(System.currentTimeMillis() - s + "ms");
   */
}
