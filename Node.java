package A6_Dijkstra;

import java.util.*;

public class Node {
	
	public long idNum;
	public String label;
	
	
	public HashSet<Edge> inDegree;
	public HashSet<Edge> outDegree;
	int distance = Integer.MAX_VALUE;
	public boolean isVisited = false;
	
	public Node(long idNum, String label){
		this.idNum = idNum;
		this.label = label;
		
		inDegree = new HashSet<Edge>();
		outDegree = new HashSet<Edge>();
	}
	
}
