/**
 * Represents a managed memory space. The memory space manages a list of allocated 
 * memory blocks, and a list free memory blocks. The methods "malloc" and "free" are 
 * used, respectively, for creating new blocks and recycling existing blocks.
 */
public class MemorySpace {

    // A list of the memory blocks that are presently allocated
    private LinkedList allocatedList;

    // A list of memory blocks that are presently free
    private LinkedList freeList;

    /**
     * Constructs a new managed memory space of a given maximal size.
     * 
     * @param maxSize the size of the memory space to be managed
     */
    public MemorySpace(int maxSize) {
        allocatedList = new LinkedList();
        freeList = new LinkedList();
        freeList.addLast(new MemoryBlock(0, maxSize));
    }

    /**
     * Allocates a memory block of a requested length (in words). Returns the
     * base address of the allocated block, or -1 if unable to allocate.
     * 
     * @param length the length (in words) of the memory block that has to be allocated
     * @return the base address of the allocated block, or -1 if unable to allocate
     */
    public int malloc(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Invalid memory block size.");
        }

        Node current = freeList.getFirst();
        Node previous = null;

        while (current != null) {
            MemoryBlock block = current.block;

            if (block.length >= length) {
                int originalBase = block.baseAddress;

                if (block.length == length) {
                    if (previous == null) {
                        freeList.remove(0);
                    } else {
                        freeList.remove(block);
                    }
                } else {
                    block.baseAddress += length;
                    block.length -= length;
                }

                MemoryBlock newBlock = new MemoryBlock(originalBase, length);
                allocatedList.addLast(newBlock);
                return originalBase;
            }

            previous = current;
            current = current.next;
        }

        return -1;
    }

    /**
     * Frees the memory block whose base address equals the given address.
     * 
     * @param address the starting address of the block to free
     */
    public void free(int address) {
        Node current = allocatedList.getFirst();
        Node previous = null;

        while (current != null) {
            MemoryBlock block = current.block;

            if (block.baseAddress == address) {
                if (previous == null) {
                    allocatedList.remove(0);
                } else {
                    allocatedList.remove(block);
                }

                freeList.addLast(block);
                return;
            }

            previous = current;
            current = current.next;
        }

        throw new IllegalArgumentException("Memory block with base address " + address + " not found in allocated list.");
    }

    /**
     * A textual representation of the free list and the allocated list of this memory space.
     * 
     * @return a string representation of the memory space
     */
    public String toString() {
        return freeList.toString() + "\n" + allocatedList.toString();
    }

    /**
     * Performs defragmentation of this memory space.
     */
    public void defrag() {
        Node current = freeList.getFirst();

        while (current != null && current.next != null) {
            MemoryBlock currentBlock = current.block;
            MemoryBlock nextBlock = current.next.block;

            if (currentBlock.baseAddress + currentBlock.length == nextBlock.baseAddress) {
                currentBlock.length += nextBlock.length;
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
    }
}

