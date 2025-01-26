public class Key implements Comparable<Key> {
    private String label;
    private int type;

    public Key(String theLabel, int theType) {
        this.label = theLabel.toLowerCase();
        this.type = theType;
    }

    public String getLabel() {
        return label;
    }

    public int getType() {
        return type;
    }

    @Override
    public int compareTo(Key k) {
        int labelComparison = this.label.compareTo(k.label);
        if (labelComparison != 0) {
            return labelComparison;
        }
        return Integer.compare(this.type, k.type);
    }
}
