import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a maze, constructs its graph representation, and provides functionality
 * to solve the maze using a depth-first search (DFS) approach.
 */
public class Maze {

	private Graph graph; // Graph representing the maze
	private GraphNode entrance; // Starting node of the maze
	private GraphNode exit; // Ending node of the maze
	private int coins; // Number of coins available to traverse doors

	/**
	 * Constructs the Maze by reading the input file and building its graph representation.
	 *
	 * @param inputFile The input file describing the maze.
	 * @throws MazeException If the input file cannot be read or processed.
	 */
	public Maze(String inputFile) throws MazeException {
		try (BufferedReader inputReader = new BufferedReader(new FileReader(inputFile))) {
			readInput(inputReader);
		} catch (IOException | GraphException e) {
			throw new MazeException("Error reading input file: " + e.getMessage());
		}
	}

	/**
	 * Retrieves the graph representation of the maze.
	 *
	 * @return The graph of the maze.
	 * @throws MazeException If the graph has not been initialized.
	 */
	public Graph getGraph() throws MazeException {
		if (this.graph == null) {
			throw new MazeException("Graph is not initialized.");
		}
		return this.graph;
	}

	/**
	 * Solves the maze using a depth-first search (DFS) approach.
	 *
	 * @return An iterator over the solution path, or null if no solution exists.
	 */
	public Iterator<GraphNode> solve() {
		try {
			return DFS(coins, entrance);
		} catch (GraphException e) {
			return null;
		}
	}

	/**
	 * Performs depth-first search to find a path from the entrance to the exit.
	 *
	 * @param k Number of coins available to traverse doors.
	 * @param go The current node being visited.
	 * @return An iterator over the solution path, or null if no path is found.
	 * @throws GraphException If an error occurs while accessing the graph.
	 */
	private Iterator<GraphNode> DFS(int k, GraphNode go) throws GraphException {
		List<GraphNode> path = new ArrayList<>();
		List<GraphNode> visited = new ArrayList<>();
		boolean pathFound = dfsHelper(k, go, path, visited);
		return pathFound ? path.iterator() : null;
	}

	/**
	 * Recursive helper for DFS traversal.
	 *
	 * @param k Remaining coins available for traversing doors.
	 * @param currentNode The current node being visited.
	 * @param path List storing the solution path.
	 * @param visited List tracking visited nodes to prevent cycles.
	 * @return True if a valid path to the exit is found, false otherwise.
	 * @throws GraphException If an error occurs while accessing the graph.
	 */
	private boolean dfsHelper(int k, GraphNode currentNode, List<GraphNode> path, List<GraphNode> visited) throws GraphException {
		path.add(currentNode);
		visited.add(currentNode);

		if (currentNode.equals(exit)) {
			return true;
		}

		Iterator<GraphEdge> edges = graph.incidentEdges(currentNode);
		while (edges != null && edges.hasNext()) {
			GraphEdge edge = edges.next();
			GraphNode neighbor = edge.firstEndpoint().equals(currentNode) ? edge.secondEndpoint() : edge.firstEndpoint();
			int cost = edge.getType();

			if (!visited.contains(neighbor) && k >= cost) {
				if (dfsHelper(k - cost, neighbor, path, visited)) {
					return true;
				}
			}
		}

		path.remove(currentNode);
		visited.remove(currentNode);
		return false;
	}

	/**
	 * Reads and processes the input file to build the maze graph.
	 *
	 * @param inputReader BufferedReader to read the input file.
	 * @throws IOException If an error occurs while reading the file.
	 * @throws GraphException If an error occurs while building the graph.
	 */
	private void readInput(BufferedReader inputReader) throws IOException, GraphException {
		int scale = Integer.parseInt(inputReader.readLine().trim());
		int width = Integer.parseInt(inputReader.readLine().trim());
		int length = Integer.parseInt(inputReader.readLine().trim());
		coins = Integer.parseInt(inputReader.readLine().trim());

		graph = new Graph(width * length);

		for (int row = 0; row < length * 2 - 1; row++) {
			String line = inputReader.readLine().trim();
			parseConnections(line, row, width);
		}
	}

	/**
	 * Parses a single line of input to establish connections in the graph.
	 *
	 * @param line The input line describing connections.
	 * @param row The row index of the maze.
	 * @param width The width of the maze.
	 * @throws GraphException If an error occurs while adding edges to the graph.
	 */
	private void parseConnections(String line, int row, int width) throws GraphException {
		if (row % 2 == 0) {
			handleHorizontalConnections(line, row / 2, width);
		} else {
			handleVerticalConnections(line, row, width);
		}
	}

	/**
	 * Processes horizontal connections between rooms in the maze.
	 *
	 * @param line The input line to process.
	 * @param rowIndex The row index of the rooms.
	 * @param width The width of the maze.
	 * @throws GraphException If an error occurs while adding edges.
	 */
	private void handleHorizontalConnections(String line, int rowIndex, int width) throws GraphException {
		for (int colIndex = 0; colIndex < line.length(); colIndex++) {
			char symbol = line.charAt(colIndex);

			if (symbol == 'c' || Character.isDigit(symbol)) {
				int leftRoom = rowIndex * width + colIndex / 2;
				int rightRoom = rowIndex * width + (colIndex / 2) + 1;
				insertEdge(leftRoom, rightRoom, symbol);
			} else if (symbol == 's') {
				entrance = graph.getNode(rowIndex * width + colIndex / 2);
			} else if (symbol == 'x') {
				exit = graph.getNode(rowIndex * width + colIndex / 2);
			}
		}
	}

	/**
	 * Processes vertical connections between rooms in the maze.
	 *
	 * @param line The input line to process.
	 * @param row The current row index in the input.
	 * @param width The width of the maze.
	 * @throws GraphException If an error occurs while adding edges.
	 */
	private void handleVerticalConnections(String line, int row, int width) throws GraphException {
		int rowAbove = (row - 1) / 2;
		for (int colIndex = 0; colIndex < line.length(); colIndex++) {
			char symbol = line.charAt(colIndex);

			if (symbol == 'c' || Character.isDigit(symbol)) {
				int aboveRoom = rowAbove * width + colIndex / 2;
				int belowRoom = (rowAbove + 1) * width + colIndex / 2;
				insertEdge(aboveRoom, belowRoom, symbol);
			}
		}
	}

	/**
	 * Adds an edge to the graph between two nodes.
	 *
	 * @param node1 The first node in the edge.
	 * @param node2 The second node in the edge.
	 * @param type  The type of connection ('c' for corridor, digit for doors).
	 * @throws GraphException If an error occurs while adding the edge.
	 */
	private void insertEdge(int node1, int node2, char type) throws GraphException {
		int weight = (type == 'c') ? 0 : Character.getNumericValue(type);
		graph.insertEdge(graph.getNode(node1), graph.getNode(node2), weight, String.valueOf(type));
	}
}
