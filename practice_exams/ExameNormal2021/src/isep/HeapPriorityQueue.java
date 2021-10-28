/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isep;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * An implementation of a priority queue using an array-based heap.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 * 
 * @author antoniosilva
 * 
 */
public class HeapPriorityQueue<K extends Comparable,V> extends AbstractPriorityQueue<K,V> {
  /** primary collection of priority queue entries */
  protected ArrayList<Entry<K,V>> heap = new ArrayList<>();

  /** Creates an empty priority queue based on the natural ordering of its keys. */
  public HeapPriorityQueue() { super(); }

  /**
   * Creates an empty priority queue using the given comparator to order keys.
   * @param comp comparator defining the order of keys in the priority queue
   */
  public HeapPriorityQueue(Comparator<K> comp) { super(comp); }

  /**
   * Creates a priority queue initialized with the respective
   * key-value pairs.  The two arrays given will be paired
   * element-by-element. They are presumed to have the same
   * length. (If not, entries will be created only up to the length of
   * the shorter of the arrays)
   * @param keys an array of the initial keys for the priority queue
   * @param values an array of the initial values for the priority queue
   */
  public HeapPriorityQueue(K[] keys, V[] values) {
    super();
    for (int j=0; j < Math.min(keys.length, values.length); j++) 
      insert(keys[j], values[j]);
  }

  // protected utilities
  protected int parent(int j) { return (j-1) / 2; }     // truncating division
  protected int left(int j) { return 2*j + 1; }
  protected int right(int j) { return 2*j + 2; }
  protected boolean hasLeft(int j) { return left(j) < heap.size(); }
  protected boolean hasRight(int j) { return right(j) < heap.size(); }

  /** Exchanges the entries at indices i and j of the array list. */
  protected void swap(int i, int j) {
    Entry<K,V> temp = heap.get(i);
    heap.set(i, heap.get(j));
    heap.set(j, temp);
  }

  /** Moves the entry at index j higher, if necessary, to restore the heap property. */
  protected void percolateUp(int j) {
    K child, parent;
    do {
      swap(j, parentIndex(j));
      j = parentIndex(j);
      child = heap.get(j).getKey();
      parent = heap.get(parentIndex(j)).getKey();
    } while (child.compareTo(parent) < 0);
  }

  /** Moves the entry at index j lower, if necessary, to restore the heap property. */
  protected void percolateDown(int j) {
    K parent, child;

    parent = heap.get(j).getKey();

    for (int i = 2 * j + 1; i <= Math.min(2 * j + 2, size() - 1); i++) {
      child = heap.get(i).getKey();
      if (parent.compareTo(child) > 0) {
        swap(j, i);
        j = i;
        i = 2 * j;
      }
    }

  }

  /** Performs a batch bottom-up construction of the heap in O(n) time. */
  protected void buildHeap() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  // public methods

  /**
   * Returns the number of items in the priority queue.
   * @return number of items
   */
  @Override
  public int size() { return heap.size(); }

  /**
   * Returns (but does not remove) an entry with minimal key.
   * @return entry having a minimal key (or null if empty)
   */
  @Override
  public Entry<K,V> min() {
   return heap.get(0);
  }

  /**
   * Inserts a key-value pair and return the entry created.
   * @param key     the key of the new entry
   * @param value   the associated value of the new entry
   * @return the entry storing the new key-value pair
   * @throws IllegalArgumentException if the key is unacceptable for this queue
   */
  @Override
  public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
    Entry<K,V> entry = new PQEntry<>(key, value);
    heap.add(entry);
    
    if (entry.getKey().compareTo(heap.get(parentIndex(heap.size() - 1)).getKey()) < 0) percolateUp(heap.size() - 1);
    return entry;
  }

  private int parentIndex(int childIndex) {
    return heap.size() > 1 && childIndex != 0 ? (childIndex - ((childIndex + 1) % 2 + 1))/2 : 0;
  }

  /**
   * Removes and returns an entry with minimal key.
   * @return the removed entry (or null if empty)
   */
  @Override
  public Entry<K,V> removeMin() {
    Entry<K, V> min = min();
    swap(0, size() - 1);
   percolateDown(0);
   return min;
  }
  
  @Override
    public String toString() {
        return ("Ok.");
    }
    
  @Override
      public HeapPriorityQueue<K,V> clone() {
        throw new UnsupportedOperationException("Not supported yet.");
      }
    
}

