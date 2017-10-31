import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


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
}


class OctagonPanel extends JPanel implements MouseMotionListener, MouseListener {

  private int x = 0, y = 0;
  final private int x_0 = 15, y_0 = 15, x_n = 585, y_n = 485;
  private ArrayList<Rectangle2> rectangles = new ArrayList<Rectangle2>();
  private Octagon octagon = new Octagon();

  OctagonPanel() {
    setPreferredSize(new Dimension(x_0 + x_n, y_0 + y_n));
    addMouseMotionListener(this);
    addMouseListener(this);
  }

  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.black);
    g.drawRect(x_0, y_0, x_n - x_0, y_n - y_0);
    rectangles.forEach(r -> r.paint(g));
    g.setColor(Color.blue);
    octagon.paint(g);
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    x = e.getX();
    y = e.getY();
    repaint();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
    rectangles.add(new Rectangle2(x_0, y_0, x, y));
    rectangles.add(new Rectangle2(x_0, y, x, y_n));
    rectangles.add(new Rectangle2(x, y, x_n, y_n));
    rectangles.add(new Rectangle2(x, y_0, x_n, y_0));
    repaint();
  }

  @Override
  public void mouseMoved(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}

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
