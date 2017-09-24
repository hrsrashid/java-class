import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class BinNode<V> implements Node<V> {
  private Node<V> left, right;
  private V value;

  BinNode(V value) {
    this.value = value;
    this.left = new Leaf<V>();
    this.right = new Leaf<V>();
  }

  BinNode(V value, Node<V> left, Node<V> right) {
    this.left = left;
    this.right = right;
    this.value = value;
  }

  @Override
  public V getValue() {
    return value;
  }

  @Override
  public void setValue(V value) {
    this.value = value;
  }

  @Override
  public Collection<Node<V>> getChildren() {
    return Arrays.asList(left, right);
  }

  @Override
  public void setChildren(Collection<Node<V>> children) {
    Iterator<Node<V>> it = children.iterator();

    if (it.hasNext()) {
      left = it.next();
    } else {
      left = new Leaf<V>();
    }

    if (it.hasNext()) {
      right = it.next();
    } else {
      right = new Leaf<V>();
    }
  }

  @Override
  public boolean isLeaf() {
    return false;
  }
}