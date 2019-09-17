package A6_Dijkstra;

public class DiGraphPlayground {

  public static void main (String[] args) {
  
      // thorough testing is your responsibility
      //
      // you may wish to create methods like 
      //    -- print
      //    -- sort
      //    -- random fill
      //    -- etc.
      // in order to convince yourself your code is producing
      // the correct behavior
    exTest();
    }
  
    public static void exTest(){
      DiGraph d = new DiGraph();
      d.addNode(0, "a");
      d.addNode(1, "b");
      d.addNode(2,  "c");
      d.addEdge(0,  "a", "b", 3, null);
      d.addEdge(1,  "b", "c", 4, null);
      d.addEdge(2,  "a", "c", 5, null);
      
      printShortestPath(d.shortestPath("a"));
//      
//      System.out.println("numEdges: "+d.numEdges());
//      System.out.println("numNodes: "+d.numNodes());
//      
//      printTOPO(d.topoSort());
      
    }
    
    public static void printShortestPath(ShortestPathInfo[] u){
    	System.out.print("Shortest Path: ");
    	DiGraph d = new DiGraph();
    	for  (ShortestPathInfo s: u){
    		System.out.println("(" + d.nodeMap.get(s.getDest()) + "," + d.nodeMap2.get(Long.toString(s.getTotalWeight())) + ")");
    	}
    	
    	
    }
    public static void printTOPO(String[] toPrint){
      System.out.print("TOPO Sort: ");
      for (String string : toPrint) {
      System.out.print(string+" ");
    }
      System.out.println();
    }
    
//    System.out.println(d.shortestPath("1").toString());
//    for(Vertex verts:d.vertList){
//  	  System.out.println(d.vertMap.get(verts.idNum)+"    "+d.vertexComp.get(verts.label).distance);
//    }

}