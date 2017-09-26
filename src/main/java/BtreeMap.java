import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Map.Entry;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


public class BtreeMap<K,V> implements Map<K,V>, Iterable<Entry<K,V>> {

	private Btree<Entry<K,V>> tree;

	BtreeMap() {
		tree = new Btree<Entry<K,V>>(new Leaf<Entry<K,V>>());
	}

	BtreeMap(Btree<Entry<K,V>> fromTree) {
		tree = fromTree;
	}

	BtreeMap(Node<Entry<K,V>> fromSubTree) {
		tree = new Btree<Entry<K,V>>(fromSubTree);
	}

	@Override
	public int size() {
		return this.entries().size();
	}

	@Override
	public boolean isEmpty() {
		return tree.getRoot().isLeaf();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.keySet().contains(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.values().contains(value);
	}

	private SearchResult findNode(Object key) {
		Node<Entry<K,V>> parent = new Leaf<Entry<K,V>>(), current = tree.getRoot();

		while (!current.isLeaf()) {
			K current_key = current.getValue().getKey();

			if (current_key.equals(key)) {
				return new SearchResult(current, parent);
			}

			parent = current;
			current = Iterables.get(current.getChildren(), chooseChildNode(current_key, key));
		}

		return new SearchResult(new Leaf<Entry<K,V>>(), parent);
	}

	private SearchResult findMinimalNode(Node<Entry<K,V>> startingNode) {
		Node<Entry<K,V>> current = startingNode,
								 minimalNode = new Leaf<Entry<K,V>>(),
											parent = new Leaf<Entry<K,V>>();

		do {
			parent = minimalNode;
			minimalNode = current;
			current = Iterables.get(current.getChildren(), 0);
		}
		while (!current.isLeaf());

		return new SearchResult(minimalNode, parent);
	}

	private class SearchResult {
		public final Node<Entry<K,V>> result, parent;
		SearchResult(Node<Entry<K,V>> result, Node<Entry<K,V>> parent) {
			this.result = result;
			this.parent = parent;
		}
	}

	private int chooseChildNode(K parent_key, Object key) {
		return parent_key.hashCode() > key.hashCode() ? 0 : 1;
	}

	@Override
	public V get(Object key) {
    SearchResult searchResult = findNode(key);
		return searchResult.result.isLeaf() ? null : searchResult.result.getValue().getValue();
  }

	@Override
	public V put(K key, V value) {
		if (isEmpty()) {
			tree.setRoot(createNode(key, value));
			return null;
		}

		SearchResult searchResult = findNode(key);

		if (searchResult.result.isLeaf()) {
			ArrayList<Node<Entry<K,V>>> children = new ArrayList<Node<Entry<K,V>>>(searchResult.parent.getChildren());
			children.set(chooseChildNode(searchResult.parent.getValue().getKey(), key), createNode(key, value));
			searchResult.parent.setChildren(children);
		} else {
			V old_value = searchResult.result.getValue().getValue();
			searchResult.result.getValue().setValue(value);
			return old_value;
		}

		return null;
	}

	private Node<Entry<K,V>> createNode(K key, V value) {
		return new BinNode<Entry<K,V>>(new SimpleEntry<K,V>(key, value));
	}

	@Override
	public V remove(Object key) {
		SearchResult deletingNode = findNode(key);

		if (deletingNode.result.isLeaf()) {
			return null;
		}

		V returnValue = deletingNode.result.getValue().getValue();

		if (!deletingNode.result.hasLeaf()) {
			SearchResult minimalNodeSearchResult = findMinimalNode(Iterables.get(deletingNode.result.getChildren(), 1));
			deletingNode.result.setValue(minimalNodeSearchResult.result.getValue());
			deletingNode = minimalNodeSearchResult;
		}

		if (deletingNode.result.hasOnlyLeaves()) {

			if (deletingNode.parent.isLeaf()) {
				clear();
			} else {
				deletingNode.parent.removeChild(deletingNode.result);
			}

			return returnValue;
		}

		ArrayList<Node<Entry<K,V>>> children = new ArrayList<Node<Entry<K,V>>>(deletingNode.result.getChildren());
		int childNumber = children.get(0).isLeaf() ? 1 : 0;

		if (deletingNode.parent.isLeaf()) {
			tree.setRoot(children.get(childNumber));
		} else {
			deletingNode.parent.replaceChild(deletingNode.result, children.get(childNumber));
		}

		return returnValue;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		m.forEach((k, v) -> this.put(k, v));
	}

	@Override
	public void clear() {
		tree.setRoot(new Leaf<Entry<K,V>>());
	}

	@Override
	public Set<K> keySet() {
		return Sets.newHashSet(Iterables.transform(this, e -> e.getKey()));
	}

	@Override
	public Collection<V> values() {
		return Lists.transform(this.entries(), e -> e.getValue());
	}

	private LinkedList<Entry<K,V>> entries() {
		return Lists.newLinkedList(this);
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return Sets.newHashSet(this.iterator());
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new Iterator<Entry<K,V>>() {
			private Iterator<Node<Entry<K,V>>> treeIterator = tree.iterator();

			@Override
			public boolean hasNext() {
				return treeIterator.hasNext();
			}

			@Override
			public Entry<K, V> next() {
				return treeIterator.next().getValue();
			}
		};
	}
}
