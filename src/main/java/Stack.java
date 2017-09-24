import java.util.LinkedList;


public class Stack {
  private LinkedList<Object> elements;

  public Stack() {
    this.elements = new LinkedList<Object>();
  }

  public int size() {
    return this.elements.size();
  }

  public boolean empty() {
    return this.size() == 0;
  }
  
  public void push(Object element) {
    this.elements.push(element);
  }

  public Object pop() {
    if (this.empty()) {
      return null;
    }

    return this.elements.pop();
  }
}
