package Juan;

import java.io.IOException;

public class ReaderCode {

  static class Reader {
    final private int BUFFER_SIZE = 1 << 12;
    private byte[] buffer;
    private int bufferPointer, bytesRead;
    private boolean eof = false;

    public Reader() {
      buffer = new byte[BUFFER_SIZE];
      bufferPointer = bytesRead = 0;
    }

    public boolean reachedEOF() {
      return eof;
    }

    private void fillBuffer() throws IOException {
      bytesRead = System.in.read(buffer, bufferPointer = 0, BUFFER_SIZE);
      if (bytesRead == -1) {
        buffer[0] = -1;
        eof = true;
      }
    }

    private byte read() throws IOException {
      if (bufferPointer == bytesRead)
        fillBuffer();
      return buffer[bufferPointer++];
    }

    public String next() throws IOException {
      StringBuilder sb = new StringBuilder();
      byte c;
      while ((c = read()) <= ' ') {
        if (c == -1)
          return null;
      }
      do {
        sb.append((char) c);
      } while ((c = read()) > ' ');
      if (sb.length() == 0)
        return null;
      return sb.toString();
    }

    public String nextLine() throws IOException {
      StringBuilder sb = new StringBuilder();
      byte c;
      boolean read = false;
      while ((c = read()) != -1) {
        if (c == '\n') {
          read = true;
          break;
        }
        if (c >= ' ')
          sb.append((char) c);
      }
      if (!read)
        return null;
      return sb.toString();
    }

    public int nextInt() throws IOException {
      int ret = 0;
      byte c = read();
      while (c <= ' ')
        c = read();
      boolean neg = (c == '-');
      if (neg)
        c = read();
      do {
        ret = ret * 10 + c - '0';
      } while ((c = read()) >= '0' && c <= '9');
      if (neg)
        return -ret;
      return ret;
    }

    public long nextLong() throws IOException {
      long ret = 0;
      byte c = read();
      while (c <= ' ')
        c = read();
      boolean neg = (c == '-');
      if (neg)
        c = read();
      do {
        ret = ret * 10L + c - '0';
      } while ((c = read()) >= '0' && c <= '9');
      if (neg)
        return -ret;
      return ret;
    }

    public double nextDouble() throws IOException {
      double ret = 0, div = 1;
      byte c = read();
      while (c <= ' ')
        c = read();
      boolean neg = (c == '-');
      if (neg)
        c = read();
      do {
        ret = ret * 10 + c - '0';
      } while ((c = read()) >= '0' && c <= '9');
      if (c == '.')
        while ((c = read()) >= '0' && c <= '9')
          ret += (c - '0') / (div *= 10);
      if (neg)
        return -ret;
      return ret;
    }

    public int[] nextIntArrray(int n) throws IOException {
      int[] a = new int[n];
      for (int i = 0; i < n; i++)
        a[i] = nextInt();
      return a;
    }

    public int[][] nextIntMatrix(int n, int m) throws IOException {
      int[][] map = new int[n][m];
      for (int i = 0; i < n; i++)
        map[i] = nextIntArrray(m);
      return map;
    }

    public char[][] nextCharacterMatrix(int n) throws IOException {
      char[][] a = new char[n][];
      for (int i = 0; i < n; i++)
        a[i] = next().toCharArray();
      return a;
    }

    public void close() throws IOException {
      if (System.in == null)
        return;
      System.in.close();
    }
  }

}
