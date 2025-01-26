public class BinarySearchTree {
    private BSTNode root;

    public BinarySearchTree() {
        this.root = null;
    }

    public BSTNode getRoot() {
        return root;
    }

    public BSTNode get(BSTNode r, Key k) {
        if (r == null || r.getRecord() == null) return null;
        int cmp = k.compareTo(r.getRecord().getKey());
        if (cmp == 0) return r;
        if (cmp < 0) return get(r.getLeftChild(), k);
        return get(r.getRightChild(), k);
    }

    public void insert(BSTNode r, Record d) throws DictionaryException {
        if (root == null) {
            root = new BSTNode(d);
            return;
        }
        int cmp = d.getKey().compareTo(r.getRecord().getKey());
        if (cmp == 0) throw new DictionaryException("Duplicate key");
        else if (cmp < 0) {
            if (r.getLeftChild() == null) {
                BSTNode newNode = new BSTNode(d);
                r.setLeftChild(newNode);
                newNode.setParent(r);
            } else {
                insert(r.getLeftChild(), d);
            }
        } else {
            if (r.getRightChild() == null) {
                BSTNode newNode = new BSTNode(d);
                r.setRightChild(newNode);
                newNode.setParent(r);
            } else {
                insert(r.getRightChild(), d);
            }
        }
    }

    public void remove(BSTNode r, Key k) throws DictionaryException {
        BSTNode node = get(r, k);
        if (node == null) throw new DictionaryException("Key not found");

        // Node with two children
        if (node.getLeftChild() != null && node.getRightChild() != null) {
            BSTNode successor = smallest(node.getRightChild());
            node.setRecord(successor.getRecord());
            node = successor;
        }

        // Node with one child or no children
        BSTNode replacement = (node.getLeftChild() != null) ? node.getLeftChild() : node.getRightChild();

        if (replacement != null) {
            replacement.setParent(node.getParent());
            if (node.getParent() == null) {
                root = replacement;
            } else if (node == node.getParent().getLeftChild()) {
                node.getParent().setLeftChild(replacement);
            } else {
                node.getParent().setRightChild(replacement);
            }
        } else if (node.getParent() == null) {
            root = null;
        } else {
            if (node == node.getParent().getLeftChild()) {
                node.getParent().setLeftChild(null);
            } else {
                node.getParent().setRightChild(null);
            }
        }
    }

    public BSTNode smallest(BSTNode r) {
        if (r == null) return null;
        while (r.getLeftChild() != null) {
            r = r.getLeftChild();
        }
        return r;
    }

    public BSTNode largest(BSTNode r) {
        if (r == null) return null;
        while (r.getRightChild() != null) {
            r = r.getRightChild();
        }
        return r;
    }

    public BSTNode successor(BSTNode node) {
        if (node.getRightChild() != null) {
            return smallest(node.getRightChild());
        }

        BSTNode parent = node.getParent();
        while (parent != null && node == parent.getRightChild()) {
            node = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    public BSTNode predecessor(BSTNode node) {
        if (node.getLeftChild() != null) {
            return largest(node.getLeftChild());
        }

        BSTNode parent = node.getParent();
        while (parent != null && node == parent.getLeftChild()) {
            node = parent;
            parent = parent.getParent();
        }
        return parent;
    }
}
