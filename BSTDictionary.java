import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BSTDictionary implements BSTDictionaryADT {
    private BinarySearchTree tree;

    public BSTDictionary() {
        this.tree = new BinarySearchTree();
    }

    @Override
    public Record get(Key k) {
        BSTNode node = tree.get(tree.getRoot(), k);
        return node == null ? null : node.getRecord();
    }

    @Override
    public void put(Record d) throws DictionaryException {
        tree.insert(tree.getRoot(), d);
    }

    @Override
    public void remove(Key k) throws DictionaryException {
        tree.remove(tree.getRoot(), k);
    }

    @Override
    public Record successor(Key k) {
        BSTNode node = tree.get(tree.getRoot(), k);
        if (node == null) return null;
        BSTNode successorNode = tree.successor(node);
        return successorNode == null ? null : successorNode.getRecord();
    }

    @Override
    public Record predecessor(Key k) {
        BSTNode node = tree.get(tree.getRoot(), k);
        if (node == null) return null;
        BSTNode predecessorNode = tree.predecessor(node);
        return predecessorNode == null ? null : predecessorNode.getRecord();
    }

    @Override
    public Record smallest() {
        BSTNode smallestNode = tree.smallest(tree.getRoot());
        if (smallestNode == null) {
            throw new NoSuchElementException("The dictionary is empty.");
        }
        return smallestNode.getRecord();
    }

    @Override
    public Record largest() {
        BSTNode largestNode = tree.largest(tree.getRoot());
        if (largestNode == null) {
            throw new NoSuchElementException("The dictionary is empty.");
        }
        return largestNode.getRecord();
    }

    // This method returns all records in the dictionary in sorted order
    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<>();
        inOrderTraversal(tree.getRoot(), records);
        return records;
    }

    private void inOrderTraversal(BSTNode node, List<Record> records) {
        if (node == null) return;
        inOrderTraversal(node.getLeftChild(), records);
        records.add(node.getRecord());
        inOrderTraversal(node.getRightChild(), records);
    }
}
