import java.util.Collection;

public interface Node<V> {
  public V getValue();
  public void setValue(V value);
  public Collection<Node<V>> getChildren();
  public void setChildren(Collection<Node<V>> children);
  public boolean isLeaf();
}
