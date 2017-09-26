import java.util.Collection;
import java.util.Collections;

public class Leaf<V> implements Node<V> {

  @Override
  public V getValue() {
    return null;
  }

  @Override
  public void setValue(V value) {}

  @Override
  public Collection<Node<V>> getChildren() {
    return Collections.<Node<V>>emptyList();
  }

  @Override
  public void setChildren(Collection<Node<V>> children) {}

  @Override
  public boolean equals(Object other) {
    return other instanceof Leaf;
  }

  @Override
  public boolean isLeaf() {
    return true;
  }

  @Override
  public void removeChild(Node<V> node) {}

  @Override
  public void replaceChild(Node<V> oldNode, Node<V> newNode) {}

  @Override
  public boolean hasLeaf() {
    return false;
  }

  @Override
  public boolean hasOnlyLeaves() {
    return false;
  }
}
