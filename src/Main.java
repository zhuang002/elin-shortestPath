import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Graph graph = Graph.loadGraph();
		
		int shortestPath = graph.getShortestPath(1, 14);
		
		System.out.println(shortestPath);
	}

}

class Graph {
	int[][] graph = null;

	public static Graph loadGraph() throws FileNotFoundException {
		// TODO Auto-generated method stub
		Graph g = new Graph();
		Scanner sc = new Scanner(new File("graph2.dat"));
		
		int noOfNodes = sc.nextInt();
		g.graph = new int[noOfNodes][noOfNodes];
		for (int i=0;i<noOfNodes;i++) {
			for (int j=0;j<noOfNodes;j++) {
				g.graph[i][j] = 0;
			}
		}
		
		int noOfPaths = sc.nextInt();
		
		for (int i=0;i<noOfPaths;i++) {
			int n1 = sc.nextInt();
			int n2 = sc.nextInt();
			int len = sc.nextInt();
			
			g.graph[n1][n2] = len;
			g.graph[n2][n1] = len;
		}
		return g;
	}

	public int getShortestPath(int start, int target) {
		// TODO Auto-generated method stub
		
		boolean[] checkedNodes = new boolean[this.graph.length];
		int[] distances = new int[this.graph.length];
		for (int i=0;i<this.graph.length;i++) {
			checkedNodes[i] = false;
			distances[i] = -1;
		}
		
		Set<Integer> current = new HashSet<>();
		current.add(start);
		distances[start] = 0;
		
		
		while (!current.isEmpty()) {
			int node = getNearestNode(current, distances);
			ArrayList<Integer> neighbours = getUncheckedNeighbours(node, checkedNodes);
			calculateDistances(node, neighbours, distances);
			current.addAll(neighbours);
			checkedNodes[node] = true;
			current.remove(Integer.valueOf(node));
		}
		
		return distances[target];
	}

	

	private void calculateDistances(int node, ArrayList<Integer> neighbours, int[] distances) {
		// TODO Auto-generated method stub
		for (int neighbour:neighbours) {
			if (distances[neighbour] == -1) {
				distances[neighbour] = this.graph[node][neighbour] + distances[node];
			} else {
				int newDistance =  this.graph[node][neighbour] + distances[node];
				if (newDistance < distances[neighbour]) distances[neighbour] = newDistance;
			}
		}
	}

	private ArrayList<Integer> getUncheckedNeighbours(int node, boolean[] checkedNodes) {
		// TODO Auto-generated method stub
		ArrayList<Integer> neighbours = new ArrayList<>();
		
		for (int i=0;i<this.graph.length;i++) {
			if (i == node) continue;
			if (checkedNodes[i]) continue;
			if (this.graph[node][i] == 0) continue;
			neighbours.add(i);
		}
		return neighbours;
	}

	private int getNearestNode(Set<Integer> current, int[] distances) {
		// TODO Auto-generated method stub
		int minDistance = Integer.MAX_VALUE;
		int shortestNode = -1;
		for (int node:current) {
			if (minDistance > distances[node]) {
				minDistance = distances[node];
				shortestNode = node;
			}
		}
		
		return shortestNode;
	}
	
	
}
