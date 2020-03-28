package binaryTree_IPLookup;

public class TreeNode {
	public TreeNode left;   // left child
    public TreeNode right;  // right child
    public TreeNode parent; // parent ptr
    public String hopvalue;  // store next hop value 0~255, set to 256 for blank node

    
    // constructor
    public TreeNode() {
	
        this.left = null;
        this.right = null;
        this.parent = null;
        this.hopvalue = "256";
    }

}
