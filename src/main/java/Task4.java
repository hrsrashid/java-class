import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JPanel;


class Vector {
  public static int norm(int x, int y) {
    return Math.max(Math.abs(x), Math.abs(y));
  }

  public static int distance(int x1, int y1, int x2, int y2) {
    return norm(x1 - x2, y1 - y2);
  }
}

class Line {
  protected Point p1 = new Point(), p2 = new Point();

  Line() {}

  Line(int x1, int y1, int x2, int y2) {
    setPoints(x1, y1, x2, y2);
  }

  public void setPoints(int x1, int y1, int x2, int y2) {
    p1.x = x1;
    p1.y = y1;
    p2.x = x2;
    p2.y = y2;
  }

  public int x(int i) {
    switch ((i - 1) % 2) {
      case 0:
        return p1.x;
      case 1:
        return p2.x;
      default:
        return 0;
    }
  }

  public int y(int i) {
    switch ((i - 1) % 2) {
      case 0:
        return p1.y;
      case 1:
        return p2.y;
      default:
        return 0;
    }
  }

  public void moveTo(int x, int y) {
    p1.setLocation(x, y);
    p2.setLocation(x, y);
  }

  public void paint(Graphics g) {
    g.drawLine(p1.x, p1.y, p2.x, p2.y);
  }

  public int distanceTo(int x, int y) {
    return Integer.MAX_VALUE;
  }

  public Optional<Point> intersect(Line l) {
    int denominator = (x(1) - x(2)) * (l.y(3) - l.y(4))  -  (y(1) - y(2)) * (l.x(3) - l.x(4));

    if (denominator == 0) {
      return Optional.empty();
    }

    int x = (
      (x(1)*y(2) - y(1)*x(2)) * (l.x(3) - l.x(4))
         -
      (x(1) - x(2)) * (l.x(3)*l.y(4) - l.y(3)*l.x(4))
    ) / denominator;

    int y = (
      (x(1)*y(2) - y(1)*x(2))  *  (l.y(3) - l.y(4))
        -
      (y(1) - y(2)) * (l.x(3)*l.y(4) - l.y(3)*l.x(4))
    ) / denominator;

    return Optional.of(new Point(x, y));
  }
}

class HorizontalLine extends Line {

  HorizontalLine(int x1, int y1, int x2, int y2) {
    super(x1, y1, x2, y2);
  }

  @Override
  public void moveTo(int x, int y) {
    p1.y = y;
    p2.y = y;
  }

  @Override
  public int distanceTo(int x, int y) {
    return Vector.distance(x, y, x, p2.y);
  }
}

class VerticalLine extends Line {

  VerticalLine(int x1, int y1, int x2, int y2) {
    super(x1, y1, x2, y2);
  }

  @Override
  public void moveTo(int x, int y) {
    p1.x = x;
    p2.x = x;
  }

  @Override
  public int distanceTo(int x, int y) {
    return Vector.distance(x, y, p2.x, y);
  }
}


class Octagon {

  private ArrayList<Point> points = new ArrayList<Point>(8);

  Octagon() {
    for (int i = 0, angle = 0; i < 8; i++, angle += 45) {
      Point p = new Point();
      p.x = (int)Math.round(100 * Math.sin(Math.toRadians(angle))) + 150;
      p.y = (int)Math.round(100 * Math.cos(Math.toRadians(angle))) + 150;
      points.add(p);
    }
  }

  public void paint(Graphics g) {
    int[] xs = points.stream().map(p -> p.x).mapToInt(Integer::intValue).toArray();
    int[] ys = points.stream().map(p -> p.y).mapToInt(Integer::intValue).toArray();
    g.drawPolygon(xs, ys, points.size());

    for (Point p : points) {
      paintPoint(g, p.x, p.y);
    }
  }

  private void paintPoint(Graphics g, int x, int y) {
    g.fillArc(x - 8, y - 8, 16, 16, 0, 360);
  }

  public Optional<Point> getVertex(int x, int y) {
    return points.stream()
      .filter(p -> Vector.distance(p.x, p.y, x, y) <= 8)
      .findFirst();
  }
}


class OctagonPanel extends JPanel implements MouseMotionListener, MouseListener {

  final private int x_0 = 15, y_0 = 15, x_n = 585, y_n = 485;
  final private Line top, left, right, bottom;
  private ArrayList<Rectangle2> rectangles = new ArrayList<Rectangle2>();
  private ArrayList<Line> lines = new ArrayList<Line>();
  private Octagon octagon = new Octagon();
  private Optional<Point> draggingVertex = Optional.empty();
  private Optional<Line> draggingLine = Optional.empty();

  OctagonPanel() {
    setPreferredSize(new Dimension(x_0 + x_n, y_0 + y_n));
    addMouseMotionListener(this);
    addMouseListener(this);

    top = new HorizontalLine(x_0, y_0, x_n, y_0);
    left = new VerticalLine(x_0, y_0, x_0, y_n);
    right = new VerticalLine(x_n, y_0, x_n, y_n);
    bottom = new HorizontalLine(x_0, y_n, x_n, y_n);
  }

  @Override
  public void paintComponent(Graphics g) {
    for (int i = 0; i < rectangles.size(); i++) {
      g.setColor(Color.getHSBColor((float)i/10, .5f, .5f));
      rectangles.get(i).paint(g);
    }

    g.setColor(Color.black);
    g.drawRect(x_0, y_0, x_n - x_0, y_n - y_0);
    lines.forEach(r -> r.paint(g));
    g.setColor(Color.blue);
    octagon.paint(g);
  }

  private Optional<Line> getLine(int x, int y) {
    if (top.distanceTo(x, y) < 5 || bottom.distanceTo(x, y) < 5) {
      Line l = new HorizontalLine(x_0, y, x_n, y);
      lines.add(l);
      return Optional.of(l);
    }

    if (left.distanceTo(x, y) < 5 || right.distanceTo(x, y) < 5) {
      Line l = new VerticalLine(x, y_0, x, y_n);
      lines.add(l);
      return Optional.of(l);
    }

    return lines.stream()
      .filter(l -> l.distanceTo(x, y) < 5)
      .findFirst();
  }

  private LinkedList<Point> getAllLinesIntersections() {
    LinkedList<Point> points = new LinkedList<Point>();

    for (Line l1 : lines) {
      for (Line l2 : lines) {
        l1.intersect(l2).ifPresent(p -> points.add(p));
      }
      l1.intersect(top).ifPresent(p -> points.add(p));
      l1.intersect(left).ifPresent(p -> points.add(p));
      l1.intersect(right).ifPresent(p -> points.add(p));
      l1.intersect(bottom).ifPresent(p -> points.add(p));
    }

    return points;
  }

  private void createRectsFromLines() {
    rectangles.clear();

    LinkedList<Point> points = getAllLinesIntersections();

    Queue<Point> rectsStarts = new LinkedList<Point>();
    rectsStarts.add(new Point(x_0, y_0));

    for (int y = y_0; y <= y_n; y++) {
      for (int x = x_0; x <= x_n; x++) {
        Point currentPoint = new Point(x, y);

        if (points.contains(currentPoint)) {
          if (currentPoint.x != x_n) {
            rectsStarts.offer(currentPoint);
          }

          Point rectStartPoint = rectsStarts.peek();

          if (rectStartPoint.x != currentPoint.x && rectStartPoint.y != currentPoint.y) {
            rectsStarts.poll();
            rectangles.add(new Rectangle2(rectStartPoint.x, rectStartPoint.y, currentPoint.x, currentPoint.y));
          }
        }
      }
    }

    Point rectStartPoint = rectsStarts.poll();
    if (rectStartPoint != null) {
      rectangles.add(new Rectangle2(rectStartPoint.x, rectStartPoint.y, x_n, y_n));
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
    draggingVertex.ifPresent(p -> p.setLocation(x, y));
    draggingLine.ifPresent(l -> l.moveTo(x, y));
    repaint();
  }

  @Override
  public void mouseClicked(MouseEvent e) {  }

  @Override
  public void mouseMoved(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();

    draggingVertex = octagon.getVertex(x, y);

    if (draggingVertex.isPresent()) {
      return;
    }

    draggingLine = getLine(x, y);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    draggingVertex = Optional.empty();

    if (draggingLine.isPresent()) {
      draggingLine = Optional.empty();
      createRectsFromLines();
      repaint();
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}
}


class Task4 {
  public static void main(String[] args) {
    JFrame frame = new JFrame("Octagon");
    JPanel panel = new OctagonPanel();
    frame.add(panel);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.pack();
  }
}
