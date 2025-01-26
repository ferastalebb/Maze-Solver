public class GraphNode {

	// Variables to store the name and mark of the node
	private int name;        // The unique identifier for the node
	private boolean marked;  // Indicates whether the node is marked (true/false)

	// Constructor
	public GraphNode(int name) {
		this.name = name;    // Assign the given name to the node
		this.marked = false; // Nodes are unmarked by default
	}

	// Marks the node with the specified value
	public void mark(boolean mark) {
		this.marked = mark;
	}

	// Returns the value with which the node has been marked
	public boolean isMarked() {
		return this.marked;
	}

	// Returns the name of the node
	public int getName() {
		return this.name;
	}
}
