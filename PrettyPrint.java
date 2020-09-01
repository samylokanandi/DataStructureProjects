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

class PrettyPrint<T>{
	private BSTNode<T> root;
	private int nodeCount;
	private boolean addDupe = false;

	public PrettyPrint()
	{
		nodeCount = 0;
		root = null;
	}

	@SuppressWarnings("unchecked")
	public PrettyPrint(String infileName) throws Exception
	{
		nodeCount = 0;
		root = null;
		BufferedReader infile = new BufferedReader(new FileReader(infileName));
		while (infile.ready())
			add((T) infile.readLine()); // THIS CAST RPODUCES THE WARNING
		infile.close();
	}

	public PrettyPrint(PrettyPrint<T> other)
	{
		nodeCount = 0;
		root = null;

		addNodesInPrOrder(other.root);
	}

	private void addNodesInPrOrder(BSTNode<T> otherBSTNode)
	{
		if (otherBSTNode == null)
			return;
		this.add(otherBSTNode.key);
		this.addNodesInPrOrder(otherBSTNode.left);
		this.addNodesInPrOrder(otherBSTNode.right);
	}

	@SuppressWarnings("unchecked")
	public boolean add(T key)
	{
		addDupe = false;
		root = addHelper(this.root, key);
		if (!addDupe)
		{
			++nodeCount;
		}

		return !addDupe;
	}

	@SuppressWarnings("unchecked")
	private BSTNode<T> addHelper(BSTNode<T> root, T key)
	{
		if (root == null)
		{
			return new BSTNode<T>(key, null, null);
		}
		int comp = ((Comparable) key).compareTo(root.key);
		if (comp == 0)
		{
			addDupe = true;
			return root;
		}
		else if (comp < 0)
		{
			root.left = addHelper(root.left, key);
		}
		else
		{
			root.right = addHelper(root.right, key);
		}

		return root;
	} 

	public int size()
	{
		return nodeCount; 
	}

	public int countNodes() 
	{
		return countNodes(this.root);
	}

	private int countNodes(BSTNode<T> root)
	{
		if (root == null)
		{
			return 0;
		}
		return 1 + countNodes(root.left) + countNodes(root.right);
	}

	public void printInOrder()
	{
		printInOrder(this.root);
		System.out.println();
	}

	private void printInOrder(BSTNode<T> root)
	{
		if (root == null)
		{
			return;
		}
		printInOrder(root.left);
		System.out.print(root.key + " ");
		printInOrder(root.right);
	}

	public void printPreOrder()
	{
		printPreOrder(this.root);
		System.out.println();
	}

	private void printPreOrder(BSTNode<T> root)
	{
		if (root == null)
			return;
		System.out.print(root.key + " ");

		printPreOrder(root.left);

		printPreOrder(root.right);
	}

	public void printPostOrder()
	{
		printPostOrder(this.root);

		System.out.println();
	}

	private void printPostOrder(BSTNode<T> root)
	{
		if (root == null)
		{
			return;
		}
		printPostOrder(root.left);
		printPostOrder(root.right);
		System.out.print(root.key + " ");
	}

	public void printLevelOrder()
	{
		if (this.root == null)
			return;
		Queue<T> q = new Queue<T>();
		q.enqueue(this.root);
		while (!q.empty())
		{
			BSTNode<T> n = q.dequeue();

			System.out.print(n.key + " ");

			if (n.left != null)
			{
				q.enqueue(n.left);
			}
			if (n.right != null)
			{
				q.enqueue(n.right);
			}
		}
		System.out.println();
	}

	public int countLevels()
	{
		return countLevels(root);
	}

	private int countLevels(BSTNode root)
	{
		if (root == null)
			return 0;
		return 1 + Math.max(countLevels(root.left), countLevels(root.right));
	}

	public int[] calcLevelCounts()
	{
		int levelCounts[] = new int[countLevels()];
		calcLevelCounts(root, levelCounts, 0);
		return levelCounts;
	}

	private void calcLevelCounts(BSTNode root, int levelCounts[], int level)
	{
		if (root == null)
			return;
		++levelCounts[level];
		calcLevelCounts(root.left, levelCounts, level + 1);
		calcLevelCounts(root.right, levelCounts, level + 1);
	}

	public PrettyPrint<T> makeBalancedCopyOf()
	{
		// define an ArrayList<T>
		ArrayList<T> keys = new ArrayList<T>();
		inOrder(keys, root);

		PrettyPrint<T> balancedBST = new PrettyPrint<T>();
		if (keys.size() > 0)
		{
			addKeysInBalancedOrder(keys, 0, keys.size() - 1, balancedBST);
		}
		return balancedBST;
	}

	private void inOrder(ArrayList<T> keys, BSTNode<T> root)
	{
		if (root == null)
			return;

		inOrder(keys, root.left);
		keys.add(root.key);
		inOrder(keys, root.right);
	}

	void addKeysInBalancedOrder(ArrayList<T> keys, int lo, int hi, PrettyPrint<T> balancedBST)
	{
		int midIndex = lo + (int) Math.floor((hi - lo) / 2.0);
		balancedBST.add(keys.get(midIndex));

		if (lo < midIndex)
			addKeysInBalancedOrder(keys, lo, midIndex - 1, balancedBST);
		if (hi > midIndex)
			addKeysInBalancedOrder(keys, midIndex + 1, hi, balancedBST);
	}

	public boolean equals(PrettyPrint<T> other)
	{
		return this.equals(this.root, other.root);
	}

	private boolean equals(BSTNode<T> thisRoot, BSTNode<T> otherRoot)
	{
		if (thisRoot == null && otherRoot == null)
			return true;
		if (thisRoot == null || otherRoot == null)
			return false;
		if (!thisRoot.key.equals(otherRoot.key))
			return false;
		return this.equals(thisRoot.left, otherRoot.left) && this.equals(thisRoot.right, otherRoot.right);
	}
	
	public void prettyPrint()
	{
		ArrayList<StringBuffer> levelLines = new ArrayList<StringBuffer>();
		this.prettyPrintHelper(this.root, 0, 0, levelLines);
		
		for (int i = 0; i < levelLines.size(); i++)
			System.out.println(levelLines.get(i).toString());
	}
	
	private int prettyPrintHelper(BSTNode<T> currNode, int currLevel, int startingLoc, ArrayList<StringBuffer>levelLines)
	{
		if (currNode == null)
			return 0;
		
		if (currLevel >= levelLines.size())
			levelLines.add(new StringBuffer());
		StringBuffer currLevelLine = levelLines.get(currLevel);
		
		int loc = currLevelLine.length();
		for (int i = loc; i < startingLoc; i++)
			currLevelLine.append(" ");
		
		int leftNodeCount = prettyPrintHelper(currNode.left, currLevel + 1, startingLoc, levelLines);
		for (int i = 0; i < leftNodeCount; i++)
			currLevelLine.append(" ");
		
		currLevelLine.append(currNode.key);
		
		int rightNodeCount = prettyPrintHelper(currNode.right, currLevel + 1, startingLoc + leftNodeCount + 1, levelLines);
		for (int i = 0; i < rightNodeCount; i++)
			currLevelLine.append(" ");
		
		return leftNodeCount + rightNodeCount + 1;
	}
} 