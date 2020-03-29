package hash_IPLookup;

public class Node {
	public String address;
    public String hopvalue;  // store next hop value 0~255, set to 256 for blank node

    // constructor
    public Node() {
        this.address="";
        this.hopvalue = "256";
    }

}
