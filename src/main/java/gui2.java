import java.awt.Dimension;
import java.awt.Graphics;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class SumPanel extends JPanel {
  JTextField txtField1, txtField2, txtField3;
  JButton calcBtn;

  Complex a, b, c;
  Pattern parsingPattern = Pattern.compile("\\(?(?<re>\\+?-?[\\d\\.]+)\\s*((?<im>\\+?-?[\\d\\.]+)i)?\\)?");

  SumPanel() {
    setPreferredSize(new Dimension(500, 100));

    a = new Complex(0);
    b = new Complex(0);
    c = new Complex(0);

    txtField1 = new JTextField(10);
    txtField2 = new JTextField(10);
    txtField3 = new JTextField(10);
    calcBtn = new JButton("Calculate");

    add(txtField1);
    add(new JLabel(" + "));
    add(txtField2);
    add(new JLabel(" = "));
    add(txtField3);
    add(calcBtn);

    calcBtn.addActionListener(e -> calculate());
    txtField1.addActionListener(e -> calculate());
    txtField2.addActionListener(e -> calculate());
    txtField3.addActionListener(e -> {
      c = parse(txtField3.getText());

      b = new Complex(0);
      b.add(c);
      b.sub(a);
      txtField2.setText(String.valueOf(b));
    });
  }

  private void calculate() {
    a = parse(txtField1.getText());
    b = parse(txtField2.getText());

    c = new Complex(0);
    c.add(a);
    c.add(b);

    txtField3.setText(String.valueOf(c));
  }

  private Complex parse(String s) {
    double re = 0, im = 0;
    Matcher m = parsingPattern.matcher(s);

    if (m.matches()) {
      try {
        re = Double.parseDouble(m.group("re"));
        im = Double.parseDouble(m.group("im"));
      } catch (NumberFormatException e) {
      } catch (NullPointerException e) {}
    }

    return new Complex(re, im);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}

class GuiApp2 {
  public static void main(String[] args) {
    JFrame frame = new JFrame("App #2");
    SumPanel sp = new SumPanel();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(sp);
    frame.pack();
    frame.repaint();
  }
}
