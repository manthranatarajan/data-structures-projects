package mxn220038;

import mxn220038.Graph;
import mxn220038.Graph.Vertex;
import mxn220038.Graph.Edge;
import mxn220038.Graph.GraphAlgorithm;
import mxn220038.Graph.Factory;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

/**
 * Implementation of the PERT (Program Evaluation and Review Technique) algorithm
 * for directed acyclic graphs (DAGs)
 */
public class PERT extends GraphAlgorithm<PERT.PERTVertex> {
    LinkedList<Vertex> finishList;
	
    /**
     * Inner class representing a PERT-specific vertex with attributes for scheduling
     */
    public static class PERTVertex implements Factory {
        public int es; // Earliest start time
        public int ef; // Earliest finish time
        public int ls; // Latest start time
        public int lf; // Latest finish time
        public int sl; // Slack time
        public int duration; // Task duration
    	
        /**
         * Constructor for PERTVertex.
         *
         * @param u Vertex in the graph.
         */
		public PERTVertex(Vertex u) {
			
		}
		
        /**
         * Creates a new PERTVertex instance for a given vertex.
         *
         * @param u Vertex in the graph.
         * @return A new PERTVertex instance.
         */
		public PERTVertex make(Vertex u) { 
			return new PERTVertex(u); 
		}
    }

    /**
     * Private constructor for PERT. Use the static `pert()` method to create an instance.
     *
     * @param g Input graph.
     */
    private PERT(Graph g) {
		super(g, new PERTVertex(null));
    }

    /**
     * Sets the duration of a task represented by a vertex.
     *
     * @param u Vertex representing the task.
     * @param d Duration of the task.
     */
    public void setDuration(Vertex u, int d) {
    	get(u).duration = d;
    }

    /**
     * Runs the PERT algorithm on the graph. Calculates earliest and latest times,
     * slack times, and checks for a valid topological order.
     *
     * @return True if the graph is a DAG and the algorithm runs successfully, false otherwise.
     */
    public boolean pert() {
    	// Check if the graph has a topological order (DAG)
        
    	finishList = topologicalOrder();
        if (finishList == null || finishList.isEmpty()) {
            //not a DAG
        	return false; 
        }

    	// Check if the graph has a topological order (DAG)
        for (Vertex u : g) {
            get(u).es = 0;
        }

        // calculating earliest start and finish time
        for (Vertex vertex : finishList) {
            PERTVertex pvert = get(vertex);
            pvert.ef = pvert.es + pvert.duration;
            for (Edge e : g.outEdges(vertex)) {
                Vertex vert = e.toVertex();
                PERTVertex pv = get(vert);
                if (pv.es < pvert.ef) {
                    pv.es = pvert.ef;
                }
            }
        }

        // calculating the critical path length (CPL)
        int criticalPathLength = 0;
        for (Vertex vert : g) {
            criticalPathLength = Math.max(criticalPathLength, get(vert).ef);
        }

        // latest finish times
        for (Vertex vert : g) {
            get(vert).lf = criticalPathLength;
        }

        // calculating latest start times and slack in reverse topological order
        ListIterator<Vertex> li = finishList.listIterator(finishList.size());
        while (li.hasPrevious()) {
            Vertex vert = li.previous();
            PERTVertex pu = get(vert);
            pu.ls = pu.lf - pu.duration;
            pu.sl = pu.lf - pu.ef;

            for (Edge e : g.inEdges(vert)) {
                Vertex v = e.fromVertex();
                PERTVertex pv = get(v);
                if (pv.lf > pu.ls) {
                    pv.lf = pu.ls;
                }
            }
        }
        return true;
    }

    
    /**
     * Computes a topological order of the graph using depth-first search.
     *
     * @return A linked list of vertices in topological order, or null if the graph has a cycle.
     */
    LinkedList<Vertex> topologicalOrder() {
		
        LinkedList<Vertex> finishList = new LinkedList<>();
        boolean[] visited = new boolean[g.size()];
        
        // tracks nodes in the current DFS stack
        boolean[] recStack = new boolean[g.size()]; 
        
        for (Vertex vert : g) {
            if (!visited[vert.getIndex()]) {
            	// if cycle detected, return null
                if (dfs(vert, visited, recStack, finishList)) {
                    return null; 
                }
            }
        }
        
        Collections.reverse(finishList);
        return finishList;
    }
    
    /**
     * Helper method to perform DFS and detect cycles.
     *
     * @param vertex Current vertex.
     * @param visited Array to track visited vertices.
     * @param recStack Array to track vertices in the recursion stack.
     * @param finishList List to store the topological order.
     * @return True if a cycle is detected, false otherwise.
     */
    private boolean dfs(Vertex vertex, boolean[] visited, boolean[] recStack, LinkedList<Vertex> finishList) {
        visited[vertex.getIndex()] = true;
        // mark node as part of recursion stack
        recStack[vertex.getIndex()] = true; 
        
        for (Edge e : g.outEdges(vertex)) {
            Vertex vert = e.toVertex();
            if (!visited[vert.getIndex()]) {
                if (dfs(vert, visited, recStack, finishList)) {
                	// cycle detected
                	return true; 
                }
             // back edge detected, cycle exists
            } else if (recStack[vert.getIndex()]) {
                return true; 
            }
        }
        
        // get rid of node from the recursion stack
        recStack[vertex.getIndex()] = false;
        finishList.add(vertex);
        
        return false;
    }


    // The following methods are called after calling pert().

    /**
     * Calculates the earliest completion time for a task.
     *
     * @param u Vertex representing the task.
     * @return Earliest completion time.
     */
    public int ec(Vertex u) {
    	return get(u).ef;
    }

    /**
     * Calculates the latest completion time for a task.
     *
     * @param u Vertex representing the task.
     * @return Latest completion time.
     */
    public int lc(Vertex u) {
    	return get(u).lf;
    }

    /**
     * Calculates the slack time for a task
     *
     * @param u Vertex representing the task
     * @return Slack time.
     */
    public int slack(Vertex u) {
    	return get(u).sl;
    }

    /**
     * Calculates the length of the critical path.
     *
     * @return Critical path length.
     */
    public int criticalPath() {
        int criticalPathLength = 0;
        
        for (Vertex vert : g) {
            criticalPathLength = Math.max(criticalPathLength, get(vert).ef);
        }
        
        return criticalPathLength;
    }

    /**
     * Checks if a vertex is critical.
     *
     * @param u Vertex representing the task.
     * @return True if the task is critical, false otherwise.
     */
    public boolean critical(Vertex u) {
    	return get(u).sl == 0;
    }

    /**
     * Counts the number of critical tasks in the graph.
     *
     * @return Number of critical tasks.
     */
    public int numCritical() {
		int count = 0;
        for (Vertex vert : g) {
            if (critical(vert)) {
                count++;
            }
        }
        return count;
    }

    /* Create a PERT instance on g, runs the algorithm.
     * Returns PERT instance if successful. Returns null if G is not a DAG.
     */
    public static PERT pert(Graph g, int[] duration) {
		PERT p = new PERT(g);
		for(Vertex u: g) {
		    p.setDuration(u, duration[u.getIndex()]);
		}
		// Run PERT algorithm.  Returns false if g is not a DAG
		if(p.pert()) {
		    return p;
		} else {
		    return null;
		}
    }
    
    public static void main(String[] args) throws Exception {
		String graph = "10 13   1 2 1   2 4 1   2 5 1   3 5 1   3 6 1   4 7 1   5 7 1   5 8 1   6 8 1   6 9 1   7 10 1   8 10 1   9 10 1      0 3 2 3 2 1 3 2 4 1";
		Scanner in;
		// If there is a command line argument, use it as file from which
		// input is read, otherwise use input from string.
		in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(graph);
		Graph g = Graph.readDirectedGraph(in);
		g.printGraph(false);
	
		int[] duration = new int[g.size()];
		for(int i=0; i<g.size(); i++) {
		    duration[i] = in.nextInt();
		}
		PERT p = pert(g, duration);
		if(p == null) {
		    System.out.println("Invalid graph: not a DAG");
		} else {
		    System.out.println("Number of critical vertices: " + p.numCritical());
		    System.out.println("u\tEC\tLC\tSlack\tCritical");
		    for(Vertex u: g) {
			System.out.println(u + "\t" + p.ec(u) + "\t" + p.lc(u) + "\t" + p.slack(u) + "\t" + p.critical(u));
		    }
		}
    }
}
