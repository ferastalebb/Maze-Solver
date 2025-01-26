import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Graph implements GraphADT {

	// Use a map to represent the adjacency list
	private Map<GraphNode, List<GraphEdge>> adjacencyList;
	private Map<Integer, GraphNode> nodes; // To keep track of nodes by name

	// Constructor: creates an empty graph with n nodes and no edges
	public Graph(int n) {
		adjacencyList = new HashMap<>();
		nodes = new HashMap<>();

		for (int i = 0; i < n; i++) {
			GraphNode node = new GraphNode(i);
			adjacencyList.put(node, new ArrayList<>());
			nodes.put(i, node);
		}
	}

	@Override
	public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException {
		if (!adjacencyList.containsKey(nodeu) || !adjacencyList.containsKey(nodev)) {
			throw new GraphException("One or both nodes do not exist.");
		}

		// Check if the edge already exists
		for (GraphEdge edge : adjacencyList.get(nodeu)) {
			if (edge.secondEndpoint().equals(nodev)) {
				throw new GraphException("Edge already exists between the given nodes.");
			}
		}

		// Create and add the edge to both nodes' adjacency lists
		GraphEdge edge = new GraphEdge(nodeu, nodev, type, label);
		adjacencyList.get(nodeu).add(edge);
		adjacencyList.get(nodev).add(edge);
	}

	@Override
	public GraphNode getNode(int u) throws GraphException {
		if (!nodes.containsKey(u)) {
			throw new GraphException("Node does not exist.");
		}
		return nodes.get(u);
	}

	@Override
	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
		if (!adjacencyList.containsKey(u)) {
			throw new GraphException("Node does not exist.");
		}

		List<GraphEdge> edges = adjacencyList.get(u);
		if (edges.isEmpty()) {
			return null;
		}

		return edges.iterator();
	}

	@Override
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		if (!adjacencyList.containsKey(u) || !adjacencyList.containsKey(v)) {
			throw new GraphException("One or both nodes do not exist.");
		}

		for (GraphEdge edge : adjacencyList.get(u)) {
			if (edge.secondEndpoint().equals(v) || edge.firstEndpoint().equals(v)) {
				return edge;
			}
		}

		throw new GraphException("No edge exists between the given nodes.");
	}

	@Override
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		if (!adjacencyList.containsKey(u) || !adjacencyList.containsKey(v)) {
			throw new GraphException("One or both nodes do not exist.");
		}

		for (GraphEdge edge : adjacencyList.get(u)) {
			if (edge.secondEndpoint().equals(v) || edge.firstEndpoint().equals(v)) {
				return true;
			}
		}

		return false;
	}
}
