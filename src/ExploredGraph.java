import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;

/**
 * 
 */

/**
 *Randi Mendel and Riley Andert
 * Extra Credit Options Implemented, if any:  A5E1
 * 
 * Solution to Assignment 5 in CSE 373, Autumn 2016
 * University of Washington.
 * 
 * (Based on starter code v1.3. By Steve Tanimoto.)
 *
 * Java version 8 or higher is recommended.
 *
 */

// Here is the main application class:
public class ExploredGraph {
        LinkedList<Vertex> Ve; // collection of explored vertices
        LinkedList<Edge> Ee;   // collection of explored edges
        int count; //count of moves in search 
        Stack<Vertex> s = new Stack<Vertex>(); //stack of vertices to be explored
        Queue<Vertex> q = new LinkedList<Vertex>(); //queue of vertices to be explored
        
        public ExploredGraph() {
             Ve = new LinkedList<Vertex>();
             Ee = new LinkedList<Edge>();
        }

        //Empties Explored Graph's explored vertices and edges
        public void initialize() {
            Ve.clear();
            s.clear();
            q.clear();
            Ee.clear();
        }
        
        //returns explored vertices
        public int nvertices() {
            return Ve.size();
        }
        
        //returns number of edges
        public int nedges() {
            return Ee.size();
        } 
        
        //checks precondition of v to see if valid move and if so adds it as a successor to later explore.
        public void checkMove (int startPeg, int endPeg, Vertex v, String searchType, LinkedList<Vertex> successors) {
            Operator op = new Operator(startPeg, endPeg);
            if(op.precondition(v)) {
                Vertex nextAdj = op.transition(v);
                successors.add(nextAdj);
            }
        }
        
        //returns true if the given vertex has been added to the explored vertex LinkedList
        public Boolean containsVertex (Vertex v){
            for(Vertex explored:Ve){
                if(explored.toString().equals(v.toString())){
                    return true;
                }
            }
            return false;
        }
        
      //returns true if the given vertex has been added to the stack of vertices to explore
        public Boolean stackContainsVertex (Vertex v){
            for(Vertex explored:s){
                if(explored.toString().equals(v.toString())){
                    return true;
                }
            }
            return false;
        }
        
      //returns true if the given vertex, v, has been added to the queue of vertices to explore
        public Boolean queueContainsVertex (Vertex v){
            for(Vertex explored:q){
                if(explored.toString().equals(v.toString())){
                    return true;
                }
            }
            return false;
        }
        
        //iterative depth first search that searches from
        public void idfs(Vertex vi, Vertex vj) {
        	int count = 0;
                int npegs = vi.pegs.size();
        	s.push(vi); //Open = V0
        	Ve.clear();

        	vi.predecessor = null;
        	Vertex v = vi;
        	while(!s.isEmpty() && !v.toString().equals(vj.toString())){
        		
        	    System.out.println("");
        	    v = s.pop();
        	    LinkedList<Vertex> successors = new LinkedList<Vertex>();
        	    v.label = count;
        		
        	   
        	    for(int i=0; i<npegs; i++){
                        for(int j=0; j<npegs; j++){
                            if(i != j){
                                checkMove(i,j,v,"depth first", successors);// s= successors of v?
                            }
                        }
                    }
        	    for(Vertex successor : successors){
        		if (containsVertex(successor) || stackContainsVertex(successor)){
        		    //continue
        		    successor.predecessor = v;
        		} else {
        		    s.push(successor);
        		    Edge e = new Edge(v, successor);
        		    Ee.push(e);
        		    successor.predecessor = v;
        		}
        	    }
        	    Ve.add(v);//insert into closed
        		
        	    System.out.println(v.toString() + ": count=" + count);
        	    count += 1;
        	    //System.out.println("OPEN = " + s.toString());
        		
        	}
        	//System.out.println("Length of CLOSED = " + Ve.size());
        }

        //breadth first search starting from vi and stopping once reaching vj
        public void bfs(Vertex vi, Vertex vj) {
            int count = 0;
            int npegs = vi.pegs.size();
            //open = v0
            q.add(vi);
            Ve.clear();
            vi.predecessor = null;
            Vertex v = vi;
            while (!q.isEmpty() && !v.toString().equals(vj.toString())){
                System.out.println("");
        	v = q.remove();
        	LinkedList<Vertex> successors = new LinkedList<Vertex>();
        	v.label = count;
        	for(int i=0; i<npegs; i++){
                    for(int j=0; j<npegs; j++){
                        if(i != j){
                            checkMove(i,j,v,"breadth first", successors);// s= successors of v?
                        }
                    }
                }
        	for(Vertex successor : successors){
        	    if (containsVertex(successor) || queueContainsVertex(successor)){
        		//continue
        		successor.predecessor = v;
        	    } else {
        		q.add(successor);
        		Edge e = new Edge(v, successor);
        		Ee.push(e);
        		successor.predecessor = v;
        	    }
        	}
        	System.out.println(v.toString() + ": count=" + count);
        	count += 1;
        	Ve.add(v);//insert into closed
        	//System.out.println("OPEN = " + q.toString());
            }
            //System.out.println("Length of CLOSED = " + Ve.size());
        }
        
        //Retrieves the path to the Vertex vj, then returns a LinkedList of the 
        //vertices in this path
        public LinkedList<Vertex> retrievePath(Vertex vj) {
            int pathLength = -1;
            Stack<Vertex> temp = new Stack<Vertex>();
            LinkedList<Vertex> tempNodes = new LinkedList<Vertex>();
            tempNodes = Ve;
            Vertex v = tempNodes.pop();
            
            //find this vertex in the searched Ve
            while(!v.toString().equals(vj.toString())){
            	v = tempNodes.pop();
            }
            
            //Now find all predecessors and push these to a stack
            while (v != null){
            	temp.push(v);
            	v = v.predecessor;               	
            }
            
            //Now we need to reverse this to get in correct order
            tempNodes.clear();
            int size = temp.size();
            for (int i = 0; i < size; i++ ){
            	tempNodes.add(temp.pop());
            	pathLength += 1;
            }
            System.out.println(pathLength);
            //return LinkedList of all vertexes in path
            return tempNodes;
        }
        
        //First does breadth first search for given Vertex
        //Next retrieves the path and returns an ArrayList of this path
        public ArrayList<Vertex> shortestPath(Vertex vi, Vertex vj) {
        	ArrayList<Vertex> path = new ArrayList<Vertex>();
        	ExploredGraph eg = new ExploredGraph();
        	eg.bfs(vi, vj);
        	LinkedList <Vertex> tempPath = eg.retrievePath(vj); 
        	path.addAll(tempPath);
        	return path;
        }
        
        //Returns a linkedList of all the explored Vertices
        public LinkedList<Vertex> getVertices() {return Ve;} 
      //Returns a linkedList of all the explored edges
        public LinkedList<Edge> getEdges() {return Ee;} 

        public static void main(String[] args) {      	
                ExploredGraph eg = new ExploredGraph();
                // Test the vertex constructor: 
                Vertex v0 = eg.new Vertex("[[3,2,1],[],[],[]]");
                //System.out.println(v0);
                Vertex v1 = eg.new Vertex("[],[],[],[3,2,1]");
                //System.out.println("iterative depth first search");
                //eg.idfs(v0,v1);
               // System.out.println("path");
                //eg.retrievePath(v1);
                //Vertex v2 = eg.new Vertex("[[],[4,3,1],[2]]");
                //Operator op = eg.new Operator(0,1);
                //eg.idfs(v0,v1);
                //System.out.println("breadth first search");
                eg.bfs(v0,v1);
                //System.out.println("shortest path");
                System.out.println(eg.shortestPath(v0, v1));
                //eg.retrievePath(v1);
                //eg.retrievePath(v2);
                // Add your own tests here.
                // The autograder code will be used to test your basic functionality later.

        }
        
        class Vertex {
                ArrayList<Stack<Integer>> pegs; // Each vertex will hold a Towers-of-Hanoi state.
                // There will be 3 pegs in the standard version, but more if you do extra credit option A5E1.
                Vertex predecessor; 
                int label;
                
                // Constructor that takes a string such as "[[4,3,2,1],[],[]]":
                public Vertex(String vString) {
                        String[] parts = vString.split("\\],\\[");
                        pegs = new ArrayList<Stack<Integer>>();
                        for (int i=0; i<parts.length;i++) {
                                pegs.add(new Stack<Integer>());
                                try {
                                        parts[i]=parts[i].replaceAll("\\[","");
                                        parts[i]=parts[i].replaceAll("\\]","");
                                        List<String> al = new ArrayList<String>(Arrays.asList(parts[i].split(",")));
                                        //System.out.println("ArrayList al is: "+al);
                                        Iterator<String> it = al.iterator();
                                        while (it.hasNext()) {
                                                String item = it.next();
                        if (!item.equals("")) {
                                //System.out.println("item is: "+item);
                                pegs.get(i).push(Integer.parseInt(item));
                        }
                                        }
                                }
                                catch(NumberFormatException nfe) { nfe.printStackTrace(); }
                        }               
                }
                @Override
                public String toString() {
                        String ans = "[";
                        for (int i=0; i<pegs.size(); i++) {
                            ans += pegs.get(i).toString().replace(" ", "");
                                if (i<pegs.size()-1) { ans += ","; }
                        }
                        ans += "]";
                        return ans;
                }
        }
        
        class Edge {
            Vertex start;
            Vertex end;
                public Edge(Vertex vi, Vertex vj) {
                    start = vi;
                    end = vj;
                        // Add whatever you need to here.
                }
                
                @Override
                public String toString() {
                    return "Edge from [" + getEndpoint1() + "] to [" + getEndpoint2() + "]";
                }
                
                public Vertex getEndpoint1() {
                    return start;
                }
                
                public Vertex getEndpoint2() {
                    return end;
                }
        }
        
        class Operator {
                private int i, j;

                public Operator(int i, int j) { // Constructor for operators.
                        this.i = i;
                        this.j = j;
                }

                public boolean precondition(Vertex v) {
                    // it would possible and legal to move a disk from peg i to peg j
                    //System.out.println(v.pegs.get(i));
                    if(v.pegs.get(i).isEmpty()){
                        return false;
                    } else if (v.pegs.get(j).isEmpty() || v.pegs.get(i).peek() < v.pegs.get(j).peek()){
                        //System.out.println("true");
                        return true;
                    } else {
                        return false;
                    }
                }

                public Vertex transition(Vertex v) {
                    Vertex res = new Vertex(v.toString());
                    int disk = res.pegs.get(i).pop();
                    res.pegs.get(j).push(disk);
                    // return a new vertex that represents the state reached by applying the operator
                    return res; // Placeholder.  change this.
                }

                @Override
                public String toString() {
                        // TODO: Add code to return a string good enough
                        // to distinguish different operators
                        return "attempt to move from peg " + i + " to peg " + j;
                }
        }

}