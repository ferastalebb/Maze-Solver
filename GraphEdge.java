public class GraphEdge {

	// Variables to store the endpoints, type, and label of the edge
	private GraphNode u;       // First endpoint
	private GraphNode v;       // Second endpoint
	private int type;          // Type of the edge (e.g., number of coins needed)
	private String label;      // Label of the edge (e.g., "corridor", "door")

	// Constructor
	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
		this.u = u;            // Initialize the first endpoint
		this.v = v;            // Initialize the second endpoint
		this.type = type;      // Initialize the type
		this.label = label;    // Initialize the label
	}

	// Returns the first endpoint of the edge
	public GraphNode firstEndpoint() {
		return this.u;
	}

	// Returns the second endpoint of the edge
	public GraphNode secondEndpoint() {
		return this.v;
	}

	// Returns the type of the edge
	public int getType() {
		return this.type;
	}

	// Sets the type of the edge to the specified value
	public void setType(int newType) {
		this.type = newType;
	}

	// Returns the label of the edge
	public String getLabel() {
		return this.label;
	}

	// Sets the label of the edge to the specified value
	public void setLabel(String newLabel) {
		this.label = newLabel;
	}
}
