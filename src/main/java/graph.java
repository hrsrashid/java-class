import java.util.Queue;
import java.util.Random;
import java.util.stream.Stream;
import java.util.LinkedList;
import java.util.List;

class Graph {

  private int n;
  private int[][] nodes;
  private int[] payload;

  Graph() {
    n = 8;
    nodes = new int[n][n];
    payload = new int[n];
    resetGraph();

    Random r = new Random();

    for (int i = 0; i < n; i++) {
      payload[i] = r.nextInt(100);
    }
  }

  private void resetGraph() {

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

  public void markDepths(int startVertex) {
    Queue<Integer> q = new LinkedList<>();
    q.offer(startVertex);
    nodes[startVertex][startVertex] = 0;

    while (!q.isEmpty()) {
      int current = q.poll();

      for (int child = 0; child < n; child++) {
        if (child != current && hasEdge(current, child) && nodes[child][child] < 0) {
          nodes[child][child] = nodes[current][current] + 1;
          q.offer(child);
        }
      }
    }
  }

  private boolean hasEdge(int i, int j) {
    return nodes[i][j] >= 0 || nodes[j][i] >= 0;
  }

  public List<Integer> getVerteces(int depth) {
    LinkedList<Integer> result = new LinkedList<>();

    for (int i = 0; i < n; i++) {
      if (getDepth(i) == depth) {
        result.add(i);
      }
    }

    return result;
  }

  public int getDepth(int vertex) {
    return nodes[vertex][vertex];
  }

  public int getMaxDepth() {
    int max = 0;

    for (int i = 0; i < n; i++) {
      max = Integer.max(getDepth(i), max);
    }

    return max;
  }

  public int minimizeGraphDepth() {
    int minStartVertex = 0;
    int min = Integer.MAX_VALUE;

    for (int startVertex = 0; startVertex < n; startVertex++) {
      resetGraph();
      markDepths(startVertex);
      int d = getMaxDepth();

      if (d < min) {
        min = d;
        minStartVertex = startVertex;
      }
    }

    resetGraph();
    markDepths(minStartVertex);

    return minStartVertex;
  }

  public void print() {
    for (int i = 0; i < n; i++) {
      System.out.println(String.format("#%d = %d", i + 1, getDepth(i)));
    }
  }

  public static void main(String[] args) {
    Graph g = new Graph();
    int minStartV = g.minimizeGraphDepth();
    System.out.println("Min start v: " + (minStartV + 1));
    System.out.println("Max depth = " + g.getMaxDepth());

    Queue<Worker> workers = new LinkedList<>();

    for (int d = 0; d <= g.getMaxDepth(); d++) {
      Worker w = g.new Worker(g.getVerteces(d).stream().map(v -> g.payload[v]));
      w.start();
      workers.offer(w);
    }

    int result = 0;

    while (!workers.isEmpty()) {
      try {
        Worker w = workers.poll();
        w.join();
        result += w.getResult();
      } catch (InterruptedException e) {
        System.out.println("interrupted: " + e);
      }
    }

    System.out.println("Result = " + result);
  }

  class Worker extends Thread implements Runnable {
    private int result = 0;
    private Stream<Integer> payload;

    Worker(Stream<Integer> payload) {
      this.payload = payload;
    }

    public int getResult() {
      return result;
    }

    @Override
    public void run() {
      result = payload.reduce(0, (a, b) -> a + b);
    }
  }
}

