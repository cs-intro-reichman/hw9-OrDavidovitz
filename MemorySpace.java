public class MemorySpace {
    private LinkedList freeList;
    private LinkedList allocatedList;

    public MemorySpace(int maxSize) {
        freeList = new LinkedList();
        allocatedList = new LinkedList();
        freeList.addLast(new MemoryBlock(0, maxSize));
    }

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
                        previous.next = current.next;
                    }
                } else {
                    block.baseAddress += length;
                    block.length -= length;
                }

                allocatedList.addLast(new MemoryBlock(originalBase, length));
                return originalBase;
            }

            previous = current;
            current = current.next;
        }

        return -1;
    }

    public void free(int address) {
        Node current = allocatedList.getFirst();
        Node previous = null;

        while (current != null) {
            MemoryBlock block = current.block;

            if (block.baseAddress == address) {
                if (previous == null) {
                    allocatedList.remove(0);
                } else {
                    previous.next = current.next;
                }

                freeList.addLast(block);
                return;
            }

            previous = current;
            current = current.next;
        }

        throw new IllegalArgumentException("Memory block with base address " + address + " not found in allocated list.");
    }

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

    @Override
    public String toString() {
        return freeList.toString() + "\n" + allocatedList.toString();
    }
}
