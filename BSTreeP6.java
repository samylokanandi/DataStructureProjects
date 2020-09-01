import java.io.*;
import java.util.*;

///////////////////////////////////////////////////////////////////////////////
class BSTNode<T>
{	T key;
	BSTNode<T> left,right;
	BSTNode( T key, BSTNode<T> left, BSTNode<T> right )
	{	this.key = key;
		this.left = left;
		this.right = right;
	}
}
///////////////////////////////////////////////////////////////////////////////////////
class Queue<T>
{	LinkedList<BSTNode<T>> queue;
	Queue() { queue =  new LinkedList<BSTNode<T>>(); }
	boolean empty() { return queue.size() == 0; }
	void enqueue( BSTNode<T>  node ) { queue.addLast( node ); }
	BSTNode<T>  dequeue() { return queue.removeFirst(); }
	// THROWS NO SUCH ELEMENT EXCEPTION IF Q EMPTY
}
////////////////////////////////////////////////////////////////////////////////
class BSTreeP6<T>
{
	private BSTNode<T> root;
	private int nodeCount;
	private boolean addAttemptWasDupe=false;

	public BSTreeP6()
	{
		nodeCount = 0;
		root=null;
	}

	@SuppressWarnings("unchecked")
	public BSTreeP6( String infileName ) throws Exception
	{
		nodeCount = 0;
		root=null;
		Scanner infile = new Scanner( new File( infileName ) );
		while ( infile.hasNext() )
			add( (T) infile.next() ); // THIS CAST RPODUCES THE WARNING
		infile.close();
	}

	// DUPES BOUNCE OFF & RETURN FALSE ELSE INCR COUNT & RETURN TRUE
	@SuppressWarnings("unchecked")
	public boolean add( T key )
	{	addAttemptWasDupe=false;
		root = addHelper( this.root, key );
		return !addAttemptWasDupe;
	}
	@SuppressWarnings("unchecked")
	private BSTNode<T> addHelper( BSTNode<T> root, T key )
	{
		if (root == null) return new BSTNode<T>(key,null,null);
		int comp = ((Comparable)key).compareTo( root.key );
		if ( comp == 0 )
			{ addAttemptWasDupe=true; return root; }
		else if (comp < 0)
			root.left = addHelper( root.left, key );
		else
			root.right = addHelper( root.right, key );

		return root;
  } // END addHelper

	public int size()
	{
		return nodeCount; // LOCAL VAR KEEPING COUNT
	}

	public int countNodes() // DYNAMIC COUNT ON THE FLY TRAVERSES TREE
	{
		return countNodes( this.root );
	}
	private int countNodes( BSTNode<T> root )
	{
		if (root==null) return 0;
		return 1 + countNodes( root.left ) + countNodes( root.right );
	}

	// INORDER TRAVERSAL REQUIRES RECURSION
	public void printInOrder()
	{
		printInOrder( this.root );
		System.out.println();
	}
	private void printInOrder( BSTNode<T> root )
	{
		if (root == null) return;
		printInOrder( root.left );
		System.out.print( root.key + " " );
		printInOrder( root.right );
	}

	public void printLevelOrder()
	{
		if (this.root == null) return;
		Queue<T> q = new Queue<T>();
		q.enqueue( this.root ); // this. just for emphasis/clarity
		while ( !q.empty() )
		{	BSTNode<T> n = q.dequeue();
			System.out.print( n.key + " " );
			if ( n.left  != null ) q.enqueue( n.left );
			if ( n.right != null ) q.enqueue( n.right );
		}
	}

  public int countLevels()
  {
    return countLevels( root );
  }
  private int countLevels( BSTNode root)
  {
    if (root==null) return 0;
    return 1 + Math.max( countLevels(root.left), countLevels(root.right) );
  }

  public int[] calcLevelCounts()
  {
    int levelCounts[] = new int[countLevels()];
    calcLevelCounts( root, levelCounts, 0 );
    return levelCounts;
  }
  private void calcLevelCounts( BSTNode root, int levelCounts[], int level )
  {
    if (root==null)return;
    ++levelCounts[level];
    calcLevelCounts( root.left, levelCounts, level+1 );
    calcLevelCounts( root.right, levelCounts, level+1 );
  }

  /////////////////////////////////////////////////
  // WRITE THE REMOVE METHOD 
  // return true only if it finds/removes the node
  public boolean remove( T key2remove )
  {

	return removeHelper(null, root, key2remove);
  }

  @SuppressWarnings("unchecked")
  private boolean removeHelper (BSTNode<T> parent, BSTNode<T> current, T key2remove){

  	// base case 
  	if (current == null ){
  		return false; 
  	} 

  	// makes comapre a value 
  	int compare = (((Comparable)key2remove).compareTo(current.key));


  	if (compare == 0){

  		// leaf node 
  		if (current.left == null && current.right == null){
  			if (parent == null){
  				root = null;
  			} else if (parent.right == current){
  				parent.right = null;
  			} else{ 
  				parent.left = null; 
  			}
  			return true; 
  		}

  	// left of current is empty 
  	 if (current.left ==null){
  	 	if (parent == null){
  	 		root = current.right; 
  	 	} else if (parent.left == current){
  	 		parent.left = current.right; 
  	 	} else {
  	 		parent.right = current.right;
  	 	}
  	 	return true; 
  	 }

  	// left of current has a value 
  	BSTNode<T> replacementNode = current.left;

  	while (replacementNode.right != null){
  		replacementNode = replacementNode.right; 

  	}

  	// gets rid of the replacement key as the last key to the right 
  	remove (replacementNode.key);

  	replacementNode.left = current.left;
  	replacementNode.right = current.right; 

  	if (parent == null){
  		root = replacementNode;
  	} else if (parent.left == current){
  		parent.left = replacementNode;
  	} else {
  		parent.right = replacementNode; 
  	}

 	return true; 

  	// recursive method for checking left and right 
  	} else if (compare > 0 ){
  		
  		return removeHelper(current, current.right, key2remove); 

  	} else {

  		return removeHelper(current, current.left, key2remove); 

  	}

  }


} // END BSTREEP6 CLASS