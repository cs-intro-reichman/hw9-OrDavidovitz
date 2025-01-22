/**
 * Represents a list of Nodes. 
 */
public class LinkedList {
	
	private Node first; // pointer to the first element of this list
	private Node last;  // pointer to the last element of this list
	private int size;   // number of elements in this list
	
	/**
	 * Constructs a new list.
	 */ 
	public LinkedList () {
		first = null;
		last = first;
		size = 0;
	}
	
	/**
	 * Gets the first node of the list
	 * @return The first node of the list.
	 */		
	public Node getFirst() {
		return this.first;
	}

	/**
	 * Gets the last node of the list
	 * @return The last node of the list.
	 */		
	public Node getLast() {
		return this.last;
	}
	
	/**
	 * Gets the current size of the list
	 * @return The size of the list.
	 */		
	public int getSize() {
		return this.size;
	}
	public void setFirst(Node node) {
		this.first = node;
	}
	
	public void setLast(Node node) {
		this.last = node;
	}
	
	/**
	 * Gets the node located at the given index in this list. 
	 * 
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */		
	public Node getNode(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		if (index==0)
		return this.first;
		Node current = this.first;
			for (int i = 0; i < index; i++) {
				current = current.next;

			}
			return current;
	}
	
	/**
	 * Creates a new Node object that points to the given memory block, 
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last 
	 * node in this list.
     * <p>
	 * The method implementation is optimized, as follows: if the given 
	 * index is either 0 or the list's size, the addition time is O(1). 
	 * 
	 * @param block
	 *        the memory block to be inserted into the list
	 * @param index
	 *        the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("index must be between 0 and " + size);
		}
	
		Node newOne = new Node(block);
	
		if (index == 0) {
			newOne.next = first;
			first = newOne;
			if (size == 0) {
				last = newOne;
			}
		} else if (index == size) {
			last.next = newOne;
			last = newOne;
		} else {
			Node prevNode = getNode(index - 1);
			newOne.next = prevNode.next;
			prevNode.next = newOne;
		}
	
		size++;
	}
	

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the end of this list (the node will become the list's last element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addLast(MemoryBlock block) {
		Node newOne = new Node(block);
		if (size == 0) {
			first = last = newOne;
		} else {
			last.next = newOne;
			last = newOne;
		}
		size++;
		return;
	}
	
	/**
	 * Creates a new node that points to the given memory block, and adds it 
	 * to the beginning of this list (the node will become the list's first element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addFirst(MemoryBlock block) {
		Node newOne = new Node(block);
		newOne.next = first;  
		first = newOne;       
	
		if (size == 0) {     
			last = newOne;
		}
		size++;               
	}
	

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *        the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("Index must be between 0 and size" );
		}

		Node node = getNode(index);
		return node.block;
	}	

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *        the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		for (int i = 0; i < size; i++) {
			if (getBlock(i).equals(block)) {  
				return i;
			}
		}
		return -1;
	}
	

	/**
	 * Removes the given node from this list.	
	 * 
	 * @param node
	 *        the node that will be removed from this list
	 */
	public void remove(Node node) {
		if (node == null) {
			throw new NullPointerException();
		}
		if (first == null) return;  
	
		if (first == node) {
			first = first.next;  
			if (first == null) last = null;  
		} else {
			Node current = first;
			while (current.next != null && current.next != node) {
				current = current.next;
			}
			if (current.next == node) {
				current.next = node.next;  
				if (current.next == null) last = current;  
			}
		}
		node.next = null; 
		size--; 
	}
	

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("index must be between 0 and size");
		}
	
		if (index == 0) {
			first = first.next;
			if (first == null) {
				last = null;  
			}
		} else {
			Node prevNode = getNode(index - 1);
			Node nodeToRemove = prevNode.next;
			prevNode.next = nodeToRemove.next;
			if (nodeToRemove.next == null) {
				last = prevNode; 
			}
		}
		size--;  
	}
	

	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
		if (first == null) {
			throw new IllegalArgumentException("index must be between 0 and size");
		}
	
		Node current = first;
		Node previous = null;
	
		while (current != null && !current.block.equals(block)) {
			previous = current;
			current = current.next;
		}
	
		if (current == null) {
			throw new IllegalArgumentException("index must be between 0 and size");
		}
	
		if (previous == null) {
			first = first.next;
			if (first == null) {
				last = null;
			}
		} else {
			previous.next = current.next;
			if (current.next == null) {
				last = previous; 
			}
		}
	
		size--; 
	}
	

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public ListIterator iterator(){
		return new ListIterator(first);
	}
	
	/**
	 * A textual representation of this list, for debugging.
	 */
	@Override
	public String toString() {
		if (first == null) {
			return "";
		}
	
		StringBuilder result = new StringBuilder();
		Node current = first;
		while (current != null) {
			result.append(current.block.toString()); 
			if (current.next != null) {
				result.append(" -> "); 
			}
			current = current.next;
		}
		return result.toString();
	}
}
	