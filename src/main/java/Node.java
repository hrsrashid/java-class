import java.util.Collection;

public interface Node<V> {
  public V getValue();
  public void setValue(V value);
  public Collection<Node<V>> getChildren();
  public void setChildren(Collection<Node<V>> children);
  public void removeChild(Node<V> node);
  public void replaceChild(Node<V> oldNode, Node<V> newNode);
  public boolean isLeaf();
  public boolean hasLeaf();
  public boolean hasOnlyLeaves();
}
