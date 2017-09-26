import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.stream.Collectors;

public class Btree<V> implements Iterable<Node<V>> {
  private Node<V> root;

  Btree() {
    root = new Leaf<V>();
  }

  Btree(Node<V> root) {
    this.root = root;
  }

  public void setRoot(Node<V> root) {
    this.root = root;
  }

  public Node<V> getRoot() {
    return root;
  }

  @Override
  public Iterator<Node<V>> iterator() {
    final Stack<Node<V>> stack = new Stack<Node<V>>();

    if (!root.isLeaf()) {
      stack.push(root);
    }

    return new Iterator<Node<V>>() {

      @Override
      public boolean hasNext() {
        return !stack.empty();
      }

      @Override
      public Node<V> next() {
        if (hasNext()) {
          Node<V> current = stack.pop();
          stack.addAll(current.getChildren()
            .stream()
            .filter((Node<V> node) -> !node.isLeaf())
            .collect(Collectors.toList())
          );
          return current;
        }

        throw new NoSuchElementException("No more nodes available");
      }
    };
  }
}
