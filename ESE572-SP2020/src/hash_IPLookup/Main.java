package hash_IPLookup;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {
	public static void main(String[] args) throws IOException {
		// read prefix for building binary tree
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream("prefix.csv")
						)
				);
		String line;

		long start,end;

 
		MultiValueMap<String, Node> IpMultiValueMap = new LinkedMultiValueMap<>();
		
		//test writing the result to the output 
//		File f = new File("output.txt");
//        FileOutputStream fop = new FileOutputStream(f);
//        OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
		
		while ( (line = br.readLine()) != null ) {
			Node ipnode=new Node();
			String[] info = line.split(",");
			ipnode.address = info[0].trim();
			ipnode.hopvalue = info[1].trim();
//			System.out.println(info[1].trim());
			IpMultiValueMap.add(String.valueOf(ipnode.address.length()),ipnode);
//			System.out.println(String.valueOf(ipnode.address.length()));

			 
//			 writer.append(ipnode.hopvalue+"\r\n");
	           
	           
}
//		System.out.println("signal1");
//  	close writing
//		 writer.close();
//		 fop.close();
		
//		System.out.println("third value：" + IpMultiValueMap.getValue("11",2 ).hopvalue);
		// read the input file
		BufferedReader brInput = new BufferedReader(
				new InputStreamReader(
						new FileInputStream("input.csv")
						)
				);
			
		String lineInput;
		String IP="";
		
		start = System.nanoTime();
		
		while ( (lineInput = brInput.readLine()) != null ) {
			 List<Node> ipnodeList = new ArrayList<Node>();
			 
			String[] infoInput = lineInput.split(",");
			 
//			System.out.println(infoInput[0].trim());	
			 
			IP=infoInput[0].trim();
			 

			String temphop="";
			
			//compare the whole ip address
			for (int i=31;i>7;i--) {
				ipnodeList=IpMultiValueMap.getValues(String.valueOf(i));
				String subIP=IP.substring(0,i);
				 for(int j = 0; j < ipnodeList.size(); j++){
//					 System.out.println(ipnodeList.get(j).address);
					 if(subIP.equals(ipnodeList.get(j).address)) {
						 temphop=ipnodeList.get(j).hopvalue; 
						 break;
				 }
				 if(temphop.length()!=0) {
//					 System.out.println("find"); 
				break;	 
				 }
				 }
			}
			System.out.println(temphop);
		}
		end = System.nanoTime(); 
		System.out.println("Run Time:" + (end - start)/1000+"microsecond");
	}

}
