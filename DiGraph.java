package A6_Dijkstra;

import java.util.*;
import A6_Dijkstra.MinBinHeap;



public class DiGraph implements DiGraph_Interface {

	// in here go all your data and methods for the graph
	// and the topo sort operation


	public DiGraph ( ) { // default constructor
		// explicitly include this
		// we need to have the default constructor
		// if you then write others, this one will still be there
	}


	Map<Long, String> nodeMap = new HashMap<Long, String>();
	Map<String, Node> nodeMap2 = new HashMap<String, Node>();
	Map<Long, String> edgeMap = new HashMap<Long, String>();
	Map<String, Edge> edgeMap2 = new HashMap<String, Edge>();
	


	@Override
	public boolean addNode(long idNum, String label) {
		if ((idNum < 0) || (nodeMap.containsKey(idNum))){  //node number must be greater than or equal to 0 and must be unique
			return false;
		}

		if (nodeMap2.containsKey(label) || (label == null)){ //label must be unique and not null
			return false;
	
		} else {
			Node newNode = new Node(idNum, label); 
			nodeMap.put(idNum, label);
			nodeMap2.put(label, newNode);
			return true;   //successfully add node
		}
	}

	@Override
	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		// TODO Auto-generated method stub


		if ((idNum < 0) || (edgeMap.containsKey(idNum))){ //edge number must be greater than or equal to 0 and must be unique
			return false;
		}

		if (!nodeMap2.containsKey(dLabel) || !nodeMap2.containsKey(sLabel)){ //source node and destination node must be in graph
			return false;
		}

		if (edgeMap2.containsKey(sLabel + dLabel)){ //edge cannot be between source and destination node
			return false;
		}

		if (nodeMap2.get(sLabel).label.equals(sLabel) || nodeMap2.get(dLabel).label.equals(dLabel)){
			Edge newEdge = new Edge(idNum, sLabel, dLabel, weight, eLabel);
			newEdge.destinationNode = nodeMap2.get(dLabel);
			newEdge.destinationNode.inDegree.add(newEdge);
			newEdge.sourceNode = nodeMap2.get(sLabel);
			newEdge.sourceNode.outDegree.add(newEdge);
			edgeMap.put(idNum, eLabel);
			edgeMap2.put(sLabel + dLabel, newEdge);	
			return true; //successfully add edge
		}
		return false;

	}

	@Override
	public boolean delNode(String label) {
		if (nodeMap2.containsKey(label)){  //if node is in graph
			Node newNode = nodeMap2.remove(label);
			long newIDNum = newNode.idNum;
			nodeMap.remove(newIDNum);
			for (Edge newEdge : newNode.inDegree){ //removing edges into the node
				delEdge(newEdge.sourceNode.label, label);
				edgeMap.remove(newNode.idNum);
			}

			for (Edge newEdge : newNode.outDegree){ //removing edges out of the node
				delEdge(label, newEdge.destinationNode.label);
				edgeMap.remove(newNode.idNum);
			}
			return true; //node is found and successfully removed
		}
		return false;  //if node was not in graph
	}

	@Override
	public boolean delEdge(String sLabel, String dLabel) {
		// TODO Auto-generated method stub
		if (edgeMap2.containsKey(sLabel + dLabel)) { //pass in labels for source and destination nodes
			Edge newEdge = edgeMap2.get(sLabel + dLabel);
			newEdge.sourceNode.outDegree.remove(newEdge);
			newEdge.destinationNode.inDegree.remove(newEdge);
			edgeMap.remove(newEdge.idNum);
			edgeMap2.remove(sLabel + dLabel);
			return true; //edge is found and successfully removed
		}
		return false;  //edge does not exist
	}

	@Override
	public long numNodes() {
		// TODO Auto-generated method stub
		return nodeMap.size(); //reports number of nodes in the graph
	}

	@Override
	public long numEdges() {
		// TODO Auto-generated method stub
		return edgeMap.size(); //reports number of edges in the graph
	}

	@Override
	public String[] topoSort() {		
		Queue<String> queue = new LinkedList<String>();
		String[] sortedStrings = new String[nodeMap2.size()];
		ArrayList<Node> nodeList = new ArrayList<Node>();
		
		for (String newLabel: nodeMap2.keySet()) { //check if there is cycle
			Node newNode = nodeMap2.get(newLabel);
			if (newNode.inDegree.isEmpty()){
				queue.add(newLabel);
			}
		}
	
		int i = 0;
		while (!queue.isEmpty()) { 
			sortedStrings[i] = queue.poll();
			Node newNode = nodeMap2.get(sortedStrings[i]);
			nodeList.add(newNode);
			for (Edge edge: newNode.outDegree){
				Node destination = edge.destinationNode;
				destination.inDegree.remove(edge); //if a node exists as a destination, remove it

				if(destination.inDegree.isEmpty()){ 
					queue.add(destination.label);
				}
			}
		}
		
		for (Node newNode : nodeList){ //increment the counter
			sortedStrings[i] = newNode.label;
			i++;
		}

		for (String newLabel : nodeMap2.keySet()){
			Node newNode = nodeMap2.get(newLabel);
			if (!newNode.inDegree.isEmpty()){
				return null; //if there is a cycle
			}
		}

		return sortedStrings;
		
		
		
	}

	@Override
	public ShortestPathInfo[] shortestPath(String label) {
		// TODO Auto-generated method stub
		MinBinHeap binaryHeap = new MinBinHeap();
		ArrayList<ShortestPathInfo> shortestPathList = new ArrayList<ShortestPathInfo>();
		ShortestPathInfo[] path = new ShortestPathInfo[shortestPathList.size()]; //length of this array should be list of shortest paths, including from source to itself
		binaryHeap.insert(new EntryPair(label, 0));
		
		while ( binaryHeap.size() > 0 ){
			Node newNode = nodeMap2.get(binaryHeap.getMin().value);
			binaryHeap.delMin();
			newNode.isVisited = true;
			for (Edge newEdge: newNode.outDegree){
				if ((newEdge.destinationNode.distance > newNode.distance + newEdge.weight)){
					newEdge.destinationNode.distance = (int) (newNode.distance + newEdge.weight);
					binaryHeap.insert(new EntryPair(newEdge.destinationNode.label, newEdge.destinationNode.distance));
				}
			}
		}

		
		for (Node newNode2 : nodeMap2.values()){
			if (newNode2.isVisited == false){ //if path from source node to destination node does not exist, set the weight to -1
				newNode2.distance = -1;
			}
			shortestPathList.add(new ShortestPathInfo(newNode2.label, newNode2.distance));
		}
		shortestPathList.toArray(path);
		return path;
	
	}
}