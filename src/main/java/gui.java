import java.awt.*;
import java.util.Arrays;

import javax.swing.*;

class Gui extends JFrame {
  Gui(int width, int height) {
    setSize(width, height);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }
}

class TrianglePanel extends JPanel {
  private Triangle2 tr1, tr2;

  TrianglePanel(Triangle2 t1, Triangle2 t2) {
    tr1 = t1;
    tr2 = t2;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    double tr1Area = tr1.square();
    double tr2Area = tr2.square();
    double maxArea = Math.max(tr1Area, tr2Area);
    tr1.paint(g2, tr1Area > tr2Area);
    tr2.paint(g2, tr2Area > tr1Area);
    g2.drawString(String.format("Max square is %.4f", maxArea), 15, 15);
  }
}


class GuiApp {
  public static void main(String[] args) {
    int[] initT1Xs = {10, 100, 150};
    int[] initT1Ys = {40, 300, 200};
    int[] initT2Xs = {100, 0, 300};
    int[] initT2Ys = {200, 150, 50};

    int[] t1Values = {initT1Xs[0], initT1Ys[0], initT1Xs[1], initT1Ys[1], initT1Xs[2], initT1Ys[2]};
    int[] t2Values = {initT1Xs[0], initT1Ys[0], initT1Xs[1], initT1Ys[1], initT1Xs[2], initT1Ys[2]};

    Triangle2 tr1 = new Triangle2(initT1Xs, initT1Ys);
    Triangle2 tr2 = new Triangle2(initT2Xs, initT2Ys);

    TrianglePanel triPanel = new TrianglePanel(tr1, tr2);
    triPanel.setPreferredSize(new Dimension(300, 600));

    Gui frame = new Gui(600, 600);
    frame.add(addForm(300, 600, triPanel, tr1, tr2, t1Values, t2Values), BorderLayout.WEST);
    frame.add(triPanel, BorderLayout.EAST);
    frame.pack();
  }

  private static JPanel addForm(int w, int h, JPanel triPanel, Triangle2 tr1, Triangle2 tr2, int[] values1, int[] values2) {
    JPanel form = new JPanel(new SpringLayout());
    form.setPreferredSize(new Dimension(w, h));

    String[] labels = {"x1", "y1", "x2", "y2", "x3", "y3"};
    form.add(new JLabel("1st triangle"));
    form.add(new JLabel(""));

    for (int i = 0; i < labels.length; i++) {
      String label = labels[i];
      JLabel l = new JLabel(label, JLabel.TRAILING);
      form.add(l);
      JTextField field = new JTextField();
      l.setLabelFor(field);
      form.add(field);
      field.setText(Integer.toString(values1[i]));

      field.addActionListener(e -> {
        tr1.set(label, Integer.decode(field.getText()));
        triPanel.repaint();
      });
    }

    form.add(new JLabel("2nd triangle"));
    form.add(new JLabel(""));

    for (int i = 0; i < labels.length; i++) {
      String label = labels[i];
      JLabel l = new JLabel(label, JLabel.TRAILING);
      form.add(l);
      JTextField field = new JTextField();
      l.setLabelFor(field);
      form.add(field);
      field.setText(Integer.toString(values2[i]));

      field.addActionListener(e -> {
        tr2.set(label, Integer.decode(field.getText()));
        triPanel.repaint();
      });
    }

    SpringUtilities.makeCompactGrid(form, 14, 2, 15, 15, 10, 10);

    return form;
  }
}
