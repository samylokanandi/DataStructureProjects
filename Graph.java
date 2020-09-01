/* This class was borrowed and modified as needed with permission from it's original author
   Mark Stelhik ( http:///www.cs.cmu.edu/~mjs ).  You can find Mark's original presentation of
   this material in the links to his S-01 15111,  and F-01 15113 courses on his home page.
*/

import java.io.*;
import java.util.*;

public class Graph
{
	private final int NO_EDGE = -1; // all real edges are positive
	private int G[][];              // will point to a 2D array to hold our graph data

	private int numEdges;
	public Graph( String graphFileName ) throws Exception  // since readFild doesn't handle them either
	{
		loadGraphFile( graphFileName );
	}

	///////////////////////////////////// LOAD GRAPH FILE //////////////////////////////////////////
	//
	// FIRST NUMBER IN GRAPH FILE IS THE SQUARE DIMENSION OF OUR 2D ARRAY
	// THE REST OF THE LINES EACH CONTAIN A TRIPLET <ROW,COL,WEIGHT> REPRESENTING AN EDGE IN THE GRAPH

	private void loadGraphFile( String graphFileName ) throws Exception
	{
		Scanner graphFile = new Scanner( new File( graphFileName ) );

		int dimension = graphFile.nextInt();   	// THE OF OUR N x N GRAPH
		G = new int[dimension][dimension]; 		// N x N ARRAY OF ZEROS
		numEdges=0;

		// WRITE A LOOP THAT PUTS NO_EDGE VALUE EVERYWHERE EXCPT ON THE DIAGONAL
		for(int row = 0; row < G.length; row++)
		{
			for(int col = 0; col < G[row].length; col++)
			{
				if( row != col)
					G[row][col] = -1;
			}
		}
	

		// NOW LOOP THRU THE FILE READING EACH TRIPLET row col weight FROM THE LINE
		// USE row & col AS WHERE TO STORE THE weight
		// i.e. g[row][col] = w;

		while ( graphFile.hasNextInt() )
		{
			int row = graphFile.nextInt();
			int col = graphFile.nextInt();
			int weight = graphFile.nextInt();
			addEdge(row, col, weight);

			// read in the row,col,weight // that eat us this line
			// call add edge
		}

	} // END readGraphFile

	private void addEdge( int r, int c, int w )
	{
		G[r][c] = w;
		++numEdges; // only this method adds edges so we do increment counter here only
	}
	
	private boolean hasEdge(int fromNode, int toNode)
	{
		return false;
	}

	// IN DEGREE IS NUMBER OF ROADS INTO THIS CITY
	// NODE IS THE ROW COL#. IN DEGREE IS HOW MANY POSITIVE NUMBERS IN THAT COL
	private int inDegree(int node)
	{
		int count = 0;
		for(int i = 0; i < G.length; i++)
		{
			if(G[i][node] > 0)
				++count;
		}
		return count;	
	}

	// OUT DEGREE IS NUMBER OF ROADS OUT OF THIS CITY
	// NODE IS THE ROW #. IN DEGREE IS HOW MANY POSITIVE NUMBERS IN THAT ROW
	private int outDegree(int node)
	{
		int count = 0;
		for(int i = 0; i < G[node].length; i++)
		{
			if(G[node][i] > 0)
				++count;
		}
		return count;
	}

	// DEGREE IS TOTAL NUMBER OF ROAD BOTH IN AND OUT OFR THE CITY 
	private int degree(int node)
	{
		return (inDegree(node) + outDegree(node));
	}

	// PUBLIC METHODS 
	
	public int maxOutDegree()
	{
		int maxOutDegree = outDegree(0);
		for(int i = 0; i < G.length; i++)
			if(outDegree(i) > maxOutDegree)
				maxOutDegree = outDegree(i);
		return maxOutDegree;
	}

	public int maxInDegree()
	{
		int maxInDegree = inDegree(0);
		for(int i = 0; i < G.length; i++)
			if(inDegree(i) > maxInDegree)
				maxInDegree = inDegree(i);
		return maxInDegree;
	}

	public int minOutDegree()
	{
		int minOutDegree = outDegree(0);
		for(int i = 0; i < G.length; i++)
			if(outDegree(i) < minOutDegree)
				minOutDegree = outDegree(i);
		return minOutDegree;
	}
	public int minInDegree()
	{
		int minInDegree = inDegree(0);
		for(int i = 0; i < G.length; i++)
			if(inDegree(i) < minInDegree)
				minInDegree = inDegree(i);
		return minInDegree;
	}	
	
	public int maxDegree()
	{
		return maxInDegree() + maxOutDegree();
	}

	public int minDegree()
	{
		return minInDegree() + minOutDegree();
	}
	
	public void removeEdge(int fromNode, int toNode)
	{
		/* if caller passes in a row col pair that 
		   out of bound or has no edge there, you must 
		   throw and catch an exception. See my output.
		
		   if the edge is there then remove it by writing 
		   in a NO_EDGE value at that coordinate in the grid	
		*/
		 try
		 {
		 	G[fromNode][toNode] = NO_EDGE;
		 }
		 catch(ArrayIndexOutOfBoundsException e)
		 {
		 	System.out.println("java.lang.Exception: Non Existent Edge Exception: removeEdge("+ fromNode +", "+ toNode + ")");
		 }
		 catch(Exception e)
		 {
		 	System.out.println("Exception" + e + "found");
		 }
	}

	
	// TOSTRING
	public String toString()
	{	String the2String = "";
		for (int r=0 ; r < G.length ;++r )
		{
			for ( int c=0 ; c < G[r].length ; ++c )
				the2String += String.format("%3s",G[r][c] + " ");
			the2String += "\n";
		}
		return the2String;
	} // END TOSTRING

} //EOF