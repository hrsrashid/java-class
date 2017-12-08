import java.util.Queue;
import java.util.LinkedList;

class Graph {

  private int n;
  private int[][] nodes;

  Graph() {
    n = 8;
    nodes = new int[n][n];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        nodes[i][j] = -1;
      }
    }

    nodes[0][1] = 0;
    nodes[0][2] = 0;
    nodes[1][3] = 0;
    nodes[1][6] = 0;
    nodes[2][4] = 0;
    nodes[2][5] = 0;
    nodes[3][4] = 0;
    nodes[3][6] = 0;
    nodes[3][7] = 0;
    nodes[5][4] = 0;
  }

  public void markDepths() {
    Queue<Integer> q = new LinkedList<>();
    q.offer(0);
    nodes[0][0] = 1;

    while (!q.isEmpty()) {
      int current = q.poll();

      for (int child = 0; child < n; child++) {
        if (child != current && nodes[current][child] >= 0 && nodes[child][child] < 1) {
          nodes[child][child] = nodes[current][current] + 1;
          q.offer(child);
        }
      }
    }
  }

  public void print() {
    for (int i = 0; i < n; i++) {
      System.out.println(String.format("#%d = %d", i + 1, nodes[i][i]));
    }
  }

  public static void main(String[] args) {
    Graph g = new Graph();
    g.markDepths();
    g.print();
  }
}
