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

import ExploredGraph.Vertex;

/**
 * 
 */

/**
 * @author your name(s) here.
 * Extra Credit Options Implemented, if any:  (mention them here.)
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
        int count;
        
        public ExploredGraph() {
             Ve = new LinkedList<Vertex>();
             Ee = new LinkedList<Edge>();
        }

        public void initialize(Vertex v) {
            ExploredGraph eg = new ExploredGraph(); //??????
            Ve.add(v);
            // Implement this
        }
        
        //returns explored vertices
        public int nvertices() {
            return Ve.size();
        } // Implement this.
        
        //returns number of edges
        public int nedges() {
            return Ee.size();
        }    // Implement this.
        
        //depth first search 
        public void idfs(Vertex vi, Vertex vj) {
            //
            Stack<Vertex> s = new Stack<Vertex>(); //stack of places to explore
            s.push(vi);
            Vertex next;
            count = 0;
            //Ve.add(vi); 
            while(!s.isEmpty() && !next.equals(vj)){
                next = s.pop(); //need to go to all children of each vertex in "next"
                Ve.add(next); //next has been explored
                
                //should these be in the while loop to reset what Vertex they are operating on
                //or should they go before to avoid repetition
                Operator op = new Operator(0,1); 
                Operator op1 = new Operator(1,2);
                Operator op2 = new Operator(2,0);
                if(op.precondition(next)){
                    Vertex nextAdj = op.transition(next);
                    s.push(nextAdj);
                    Edge e = new Edge(next,nextAdj);
                    Ee.add(e);
                }
                if(op1.precondition(next)){
                    Vertex nextAdj = op1.transition(next);
                    s.push(nextAdj);
                    Edge e = new Edge(next,nextAdj);
                    Ee.add(e);
                }
                if(op2.precondition(next)){
                    Vertex nextAdj = op2.transition(next);
                    s.push(nextAdj);
                    Edge e = new Edge(next,nextAdj);
                    Ee.add(e);
                }             
            }

        } // Implement this. (Iterative Depth-First Search)
        
        public void bfs(Vertex vi, Vertex vj) {
            Queue<Vertex> q = new LinkedList<Vertex>();
            q.add(vi);
            Vertex next;
            count = 0;
            while(!q.isEmpty() && !next.equals(vj)){
                next = q.poll(); //get element in q
                Ve.add(next); //next has been explored
                
                //should these be in the while loop to reset what Vertex they are operating on
                //or should they go before to avoid repetition
                Operator op = new Operator(0,1); 
                Operator op1 = new Operator(1,2);
                Operator op2 = new Operator(2,0);
                if(op.precondition(next)){
                    Vertex nextAdj = op.transition(next);
                    q.push(nextAdj);
                    Edge e = new Edge(next,nextAdj);
                    Ee.add(e);
                }
                if(op1.precondition(next)){
                    Vertex nextAdj = op1.transition(next);
                    q.push(nextAdj);
                    Edge e = new Edge(next,nextAdj);
                    Ee.add(e);
                }
                if(op2.precondition(next)){
                    Vertex nextAdj = op2.transition(next);
                    q.push(nextAdj);
                    Edge e = new Edge(next,nextAdj);
                    Ee.add(e);
                }             
            }

            
        } // Implement this. (Breadth-First Search)
        public ArrayList<Vertex> retrievePath(Vertex vi) {return null;} // Implement this.
        public ArrayList<Vertex> shortestPath(Vertex vi, Vertex vj) {return null;} // Implement this.
        public Set<Vertex> getVertices() {return Ve;} 
        public Set<Edge> getEdges() {return Ee;} 
        /**
         * @param args
         */
        public static void main(String[] args) {
                ExploredGraph eg = new ExploredGraph();
                // Test the vertex constructor: 
                Vertex v0 = eg.new Vertex("[[4,3,2,1],[],[]]");
                System.out.println(v0);
                Operator op = eg.new Operator(0,1);
                if(op.precondition(v0)) {
                    System.out.println(v0.toString());
                    v0 = op.transition(v0);
                    System.out.println(v0.toString());
                }
                // Add your own tests here.
                // The autograder code will be used to test your basic functionality later.

        }
        
        class Vertex {
                ArrayList<Stack<Integer>> pegs; // Each vertex will hold a Towers-of-Hanoi state.
                // There will be 3 pegs in the standard version, but more if you do extra credit option A5E1.
                
                // Constructor that takes a string such as "[[4,3,2,1],[],[]]":
                public Vertex(String vString) {
                        String[] parts = vString.split("\\],\\[");
                        pegs = new ArrayList<Stack<Integer>>(3);
                        for (int i=0; i<3;i++) {
                                pegs.add(new Stack<Integer>());
                                try {
                                        parts[i]=parts[i].replaceAll("\\[","");
                                        parts[i]=parts[i].replaceAll("\\]","");
                                        List<String> al = new ArrayList<String>(Arrays.asList(parts[i].split(",")));
                                        System.out.println("ArrayList al is: "+al);
                                        Iterator<String> it = al.iterator();
                                        while (it.hasNext()) {
                                                String item = it.next();
                        if (!item.equals("")) {
                                System.out.println("item is: "+item);
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
                        for (int i=0; i<3; i++) {
                            ans += pegs.get(i).toString().replace(" ", "");
                                if (i<2) { ans += ","; }
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
                    System.out.println(v.pegs.get(i).peek().toString());
                    if(v.pegs.get(i).isEmpty()){
                        return false;
                    } else if (v.pegs.get(j).isEmpty() || v.pegs.get(i).peek() < v.pegs.get(j).peek()){
                        System.out.println("true");
                        return true;
                    } else {
                        return false;
                    }
                }

                public Vertex transition(Vertex v) {
                    Vertex res = new Vertex(v.toString());
                    int disk = res.pegs.get(i).pop();
                    res.pegs.get(j).push(disk);
                    
                        // TODO: Add code to return a "successor" of v, according
                        // to this operator.
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