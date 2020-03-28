package binaryTree_IPLookup;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

	
	
	public static void main(String[] args) throws IOException {
		// read prefix for building binary tree
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream("prefix.csv")
						)
				);
		String line;
		String prefix;
		String nextHop;
		
		// build binary tree to store all prefix entries
		TreeNode root = new TreeNode();
		TreeNode currentNode = root;
		int n=1;
		while ( (line = br.readLine()) != null ) {
			currentNode = root;
			String[] info = line.split(",");
			prefix = info[0].trim();
			nextHop = info[1].trim();
			for (int i=0;i<prefix.length();i++) {
				if (prefix.charAt(i)=='0') {
					if (currentNode.left==null) {
						currentNode.left=new TreeNode();
						n++;
					}
					currentNode=currentNode.left;
				}else if (prefix.charAt(i)=='1'){
					if (currentNode.right==null) {
						currentNode.right=new TreeNode();
						n++;
					}
					currentNode=currentNode.right;
				}
			}
			currentNode.hopvalue=nextHop;
		}
		System.out.print(n);
		
		// read input IP for look-up
		BufferedReader brInput = new BufferedReader(
				new InputStreamReader(
						new FileInputStream("input.csv")
						)
				);
		String lineInput;
		String IP;
		String nextHopOutput;
		
		// create cvs file for output
		FileWriter csvWriter = new FileWriter("output.csv");
		csvWriter.append("Input IP");
		csvWriter.append(",");
		csvWriter.append("Output Next Hop");
		csvWriter.append("\n");
		
		long startTime = System.nanoTime();

		// search binary tree to find next hop and write to output file
		while ( (lineInput = brInput.readLine()) != null ) {
			TreeNode tempNode = root;
			String tempValue = "256";
			String[] infoInput = lineInput.split(",");
			IP = infoInput[0].trim();
			for (int j=0;j<IP.length();j++) {
				if (IP.charAt(j)=='0') {	
					if (tempNode.left!=null) {
						tempNode=tempNode.left;
						if (tempNode.hopvalue!="256") {
							tempValue=tempNode.hopvalue;
						}
					}else break;	
				}else if(IP.charAt(j)=='1'){
					if (tempNode.right!=null) {
						tempNode=tempNode.right;
						if (tempNode.hopvalue!="256") {
							tempValue=tempNode.hopvalue;
						}
					}else break;
				}
			}
			nextHopOutput=tempValue;
			System.out.print(nextHopOutput+"\n");

//			csvWriter.append(IP);
//			csvWriter.append(",");
//			csvWriter.append(nextHopOutput);
//			csvWriter.append("\n");
		}
		long endTime = System.nanoTime();
		long timeElapsed = endTime-startTime;
		System.out.print(timeElapsed/1000+"microsecond");
		csvWriter.flush();
		csvWriter.close();
 	}

}
