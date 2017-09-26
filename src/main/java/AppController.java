import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.Label;

public class AppController implements Initializable {

  private Map<String, String> dictionary = new BtreeMap<String, String>();

  @FXML private TableView<Entry<String, String>> dictionaryTable;
  @FXML private TableColumn<Entry<String, String>, String> englishColumn;
  @FXML private TableColumn<Entry<String, String>, String> russianColumn;

  @FXML private TextField englishField;
  @FXML private TextField russianField;
  @FXML private TextField searchField;
  @FXML private Label searchResultLabel;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    englishColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
    russianColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));

    englishColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    englishColumn.setOnEditCommit(evt -> editEnglish(evt.getOldValue(), evt.getNewValue()));

    russianColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    russianColumn.setOnEditCommit(evt -> editRussian(evt.getRowValue().getKey(), evt.getNewValue()));

    englishColumn.prefWidthProperty().bind(dictionaryTable.widthProperty().divide(2));
    russianColumn.prefWidthProperty().bind(dictionaryTable.widthProperty().divide(2));


    // example data set
    dictionary.put("dog", "собака");
    dictionary.put("cat", "кошка");
    dictionary.put("hat", "шляпа");
    dictionary.put("python", "питон");
    dictionary.put("reconnoiter", "разведка");

    ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(dictionary.entrySet());
    dictionaryTable.getItems().setAll(items);
  }

  protected void editEnglish(String oldValue, String newValue) {
    dictionary.put(newValue, dictionary.get(oldValue));
    dictionary.remove(oldValue);
    updateTableView();
  }

  protected void editRussian(String englishValue, String newValue) {
    dictionary.put(englishValue, newValue);
    updateTableView();
  }

  @FXML protected void addTranslation(ActionEvent event) {
    String eng = englishField.getText(),
      rus = russianField.getText();

    if (eng.isEmpty() || rus.isEmpty()) {
      return;
    }

    dictionary.put(eng, rus);
    updateTableView();
    englishField.setText("");
    russianField.setText("");
  }

  protected void updateTableView() {
    dictionaryTable.getItems().setAll(dictionary.entrySet());
  }

  @FXML protected void searchTranslation(ActionEvent event) {
    String eng = searchField.getText();

    if (eng.isEmpty()) {
      searchResultLabel.setText("Search result.");
    } else {
      searchResultLabel.setText(dictionary.getOrDefault(eng, "No results were found."));
    }
  }

  @FXML protected void deleteTranslation(ActionEvent event) {
    dictionaryTable
      .selectionModelProperty()
      .getValue()
      .getSelectedItems()
      .forEach(entry -> dictionary.remove(entry.getKey()));
    updateTableView();
  }
}
