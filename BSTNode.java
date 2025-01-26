public class BSTNode {
    private Record record;
    private BSTNode left, right, parent;

    public BSTNode(Record item) {
        this.record = item;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record d) {
        this.record = d;
    }

    public BSTNode getLeftChild() {
        return left;
    }

    public BSTNode getRightChild() {
        return right;
    }

    public BSTNode getParent() {
        return parent;
    }

    public void setLeftChild(BSTNode u) {
        this.left = u;
    }

    public void setRightChild(BSTNode u) {
        this.right = u;
    }

    public void setParent(BSTNode u) {
        this.parent = u;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}
