import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

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

  @Override
  public void removeChild(Node<V> node) {
    replaceChild(node, new Leaf<V>());
  }

  @Override
  public void replaceChild(Node<V> oldNode, Node<V> newNode) {
    setChildren(getChildren()
      .stream()
      .map(n -> n == oldNode ? newNode : n)
      .collect(Collectors.toList())
    );
  }

  @Override
  public boolean hasLeaf() {
    return getChildren().stream().anyMatch(n -> n.isLeaf());
  }

  @Override
  public boolean hasOnlyLeaves() {
    return getChildren().stream().allMatch(n -> n.isLeaf());
  }
}
