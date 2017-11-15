import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


class ArmamentBook {
  Pattern parsingPattern = Pattern.compile("#(?<id>\\d+)\\s+(?<name>[\\w\\s]+)\\s?\\-\\s?(?<owner>[\\w\\s]+)");
  final ArrayList<ArmamentBookRecord> records = new ArrayList<>();

  class ArmamentBookRecord {
    public final int id;
    public final String name;
    public final String owner;

    ArmamentBookRecord(int id, String name, String owner) {
      this.id = id;
      this.name = name;
      this.owner = owner;
    }

    public String toString() {
      return String.format("#%2d %20s - %15s\n", id, name, owner);
    }
  }

  public boolean addRecord(String record) {
    return parse(record).map(r -> records.add(r)).orElse(false);
  }

  private Optional<ArmamentBookRecord> parse(String record) {
    Matcher m = parsingPattern.matcher(record);

    if (m.matches()) {
      return Optional.of(new ArmamentBookRecord(Integer.parseInt(m.group("id")), m.group("name"), m.group("owner")));
    }

    return Optional.empty();
  }

  public Stream<ArmamentBookRecord> getStream() {
    return records.stream();
  }

  public void sort() {
    records.sort((a, b) -> b.id - a.id);
  }
}


class ArmamentBookApp {
  public static void main(String[] args) {
    int width = 500;
    ArmamentBook book = new ArmamentBook();

    book.addRecord("#1 Makarov pistol - Ivan Ivanov");
    book.addRecord("#3 rifle - Sergey");
    book.addRecord("#2 RPG - Igor");

    JFrame frame = new JFrame("Armament book");
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(width, 300));
    frame.add(panel);

    JTextArea ta = new JTextArea(5, 40);
    panel.add(ta);
    populate(ta, book.getStream());

    panel.add(new JLabel("Add record: "));
    JTextField addField = new JTextField(30);
    panel.add(addField);

    addField.addActionListener(e -> {
      if (book.addRecord(addField.getText())) {
        addField.setText("");
        populate(ta, book.getStream());
      }
    });

    panel.add(new JLabel("Search: "));
    JTextField searchField = new JTextField(30);
    panel.add(searchField);

    searchField.addActionListener(e -> {
      String term = searchField.getText().toLowerCase();
      populate(ta, book.getStream().filter(rec -> rec.name.toLowerCase().contains(term) || rec.owner.toLowerCase().contains(term)));
    });

    JButton sortBtn = new JButton("Sort: newest on top");
    panel.add(sortBtn);

    sortBtn.addActionListener(e -> {
      book.sort();
      populate(ta, book.getStream());
    });

    frame.pack();
  }

  static void populate(JTextArea ta, Stream<ArmamentBook.ArmamentBookRecord> records) {
    ta.setText("");
    records.forEach(rec -> ta.append(rec.toString()));
  }
}
