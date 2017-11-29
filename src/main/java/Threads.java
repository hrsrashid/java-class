import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class Worker extends Thread {
  int id, sum;
  int[][] mx;

  Worker(int id, int[][] mx) {
    this.id = id;
    this.mx = mx;
    sum = 0;
  }

  public int getSum() {
    return sum;
  }

  public int getWorkerId() {
    return id;
  }

  @Override
  public void run() {
    sum = Arrays.stream(mx)
      .map(xs -> Arrays.stream(xs).sum())
      .reduce(0, Integer::sum);
  }
}


class ThreadsApp {
  public static void main(String[] args) {
    int [][] mx;
    int n = 0, m = 0;
    Scanner sin = new Scanner(System.in);

    try {
      System.out.print("n> ");
      n = sin.nextInt();
      System.out.print("m> ");
      m = sin.nextInt();

      mx = new int[n][m];

      Random rand = new Random();

      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          mx[i][j] = rand.nextInt(100);
          // System.out.print(mx[i][j] + " ");
        }
        // System.out.println();
      }

      System.out.print("number of jobs> ");
      int jobs = sin.nextInt();

      if (jobs < 1) {
        jobs = 1;
      }

      int rows = (int)Math.ceil(1. * n / jobs);

      Worker[] ws = new Worker[jobs];

      long timeStart = System.currentTimeMillis();

      int row = 0;

      for (int i = 0; i < jobs; i++) {
        int[][] mp = new int[rows][m];

        for (int r = 0; r < rows; r++) {
          for (int j = 0; j < m; j++) {
            if (row + r < n) {
              mp[r][j] = mx[row + r][j];
            } else {
              mp[r][j] = 0;
            }
            // System.out.print(mp[r][j] + " ");
          }
          // System.out.println();
        }
        row += rows;

        ws[i] = new Worker(i, mp);
        System.out.println("Starting " + i + " thread");
        ws[i].start();
      }

      int sum = 0;

      for (int i = 0; i < jobs; i++) {
        ws[i].join();
        sum += ws[i].getSum();
        System.out.println("Thread " + i + " terminated with result " + ws[i].getSum());
      }

      long timeEnd = System.currentTimeMillis();

      System.out.println("Sum = " + sum);
      System.out.println((timeEnd - timeStart) + "ms elapsed.");

    } catch (InterruptedException e) {
      System.out.println("thread interrupted" + e);
    } finally {
      sin.close();
    }
  }
}
