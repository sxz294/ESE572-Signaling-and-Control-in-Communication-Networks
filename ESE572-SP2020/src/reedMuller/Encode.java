package reedMuller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Encode {
	// next three functions are used for CRC coding
			public static int m2Div(String a,int b) {
				int lengtha=a.length();
				int lengthb=17;
				int d=0;
				for(int i=lengtha;i>0;i--) {
					int c=getIndex(a,i); 
					d=(d<<1)+c;
					if(intLength(d, 2)==lengthb) {
						d=d^b;
					}
				}
				return d;
			}
			
		public static int intLength(int num,int radix) {
				for(int i=0;i<32;i++) {
					int c=(int) Math.pow(radix, i);
					if(num%c==num) {
						
						return i;
					}
				}
				return 0;
			}
		public static int getIndex(String a,int index) {
//				System.out.println(a>>(index-1)&1);
				int get_index=a.charAt(a.length()-index);
				return get_index&1;
			}
			
			
				
		public static void main(String[] args) throws IOException {
			
				
			// CRC coding begin
			int b=0b10001000000100001;
			ArrayList<String> charArray=new ArrayList<String>();
			ArrayList<String> outArray=new ArrayList<String>();
			ArrayList<String> RMArray=new ArrayList<String>();
			File file = new File("history.txt");
			BufferedReader reader = new BufferedReader(
			 	    new InputStreamReader(
			 	        new FileInputStream(file),
			 	        Charset.forName("UTF-8")));
			 	int c;
			 	while((c = reader.read()) != -1) {
			 	  char character = (char) c;
			 	  String s=Integer.toBinaryString((int)character);
			 	  if(s.length()>=8) {
			 		  //extended ascii table, 196 represents horizontal slash.
			 		  s=Integer.toBinaryString(196);
//			 		  System.out.println((int)character);  
			 	  }
			 	 while(s.length()<8) {
			 		  s="0"+s;
			 		  }
			 	  
			 	  charArray.add(s);
			 	  }
				
			 	reader.close();
//			 	System.out.println(charArray.size());
			 	String split="";
			 	String mix="";
			 	int step=0;
			 	for(int i=0;i<charArray.size();i++) {
			 		
			 		split=split+charArray.get(i);
			 		
			 		if((i+1)%25==0) {
			 			int remainder=m2Div(split,b);
			 			String result=Integer.toBinaryString(remainder);
			 			while(result.length()<16)
			 			{
			 				result="0"+result;
			 			}
			 			mix=split+result;
			 			outArray.add(mix);
//			 			System.out.println(mix.length());
			 			split="";
			 			mix="";
			 			step++;
			 		}
			 		if(charArray.size()-i==1) {
			 			int remainder=m2Div(split,b);
			 			String result=Integer.toBinaryString(remainder);
			 			while(result.length()<16)
			 			{
			 				result="0"+result;
			 			}
			 			mix=split+result;
//			 			System.out.println(mix.length());
			 			outArray.add(mix);
//			 			System.out.println(result);
			 			split="";
			 			mix="";
			 		}
			 	}
//			 	System.out.println(outArray.size());
			 	File f = new File("crc.txt");
			 	
			 	FileOutputStream fop = new FileOutputStream(f);
			 	OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
			 	for(int i = 0; i < outArray.size(); i++){
			 	writer.append(outArray.get(i));
			 	}

			 	writer.close();
			 	fop.close();              //CRC coding end
			 
			 	
			 // generate reed_muller matrix R(8,4)
			 	ArrayList<ArrayList<Integer>> rm_list = new ArrayList<>();
			 	for (int i=0;i<8;i++) {
			 		ArrayList<Integer> tempList = new ArrayList<>();
			 		tempList.add(1);
			 		tempList.add(Integer.parseInt(Integer.toBinaryString((i>>2)%2)));
			 		tempList.add(Integer.parseInt(Integer.toBinaryString((i>>1)%2)));
					tempList.add(Integer.parseInt(Integer.toBinaryString(i%2)));
			 		rm_list.add(tempList);
			 	}
//			 			System.out.println(rm_list);
			 	for (int i=0;i<outArray.size();i++) {
			 		String seg="";
			 		int col_result=0;
			 		String row_result="";
			 		String temp="";
			 		for(int j=0;j<outArray.get(i).length();j++) {
			 			temp=temp+outArray.get(i).charAt(j);
			 			if((j+1)%4==0) {

			 				for (int k=0;k<8;k++) {
			 				
			 				col_result=Integer.parseInt(String.valueOf(temp.charAt(0)))*1;
			 				col_result=col_result^(Integer.parseInt(String.valueOf(temp.charAt(1)))*rm_list.get(k).get(1));
			 				col_result=col_result^(Integer.parseInt(String.valueOf(temp.charAt(2)))*rm_list.get(k).get(2));
			 				col_result=col_result^(Integer.parseInt(String.valueOf(temp.charAt(3)))*rm_list.get(k).get(3));
			 				row_result=row_result+Integer.toString(col_result);
			 				}
			 				seg=seg+row_result;
			 				row_result="";
			 				temp="";
			 				
			 			}
			 		}
//			 		System.out.println(seg.length());
			 		RMArray.add(seg);
			 	}
			
//			 	System.out.println(RMArray.size());
			
		File file2 = new File("Reed_Muller.txt");
			 	
			 	FileOutputStream fop2 = new FileOutputStream(file2);
			 	OutputStreamWriter writer2 = new OutputStreamWriter(fop2, "UTF-8");
			 	for(int i = 0; i < RMArray.size(); i++){
			 	writer2.append(RMArray.get(i));
			 	}

			 	writer2.close();
			 	fop2.close();              //RM coding end
			

				//add frame flag and apply bit stuffing
				ArrayList<String> frameArray=new ArrayList<String>();
				String flag="01111110";
				for (int i=0;i<RMArray.size();i++) {
					String s =RMArray.get(i);
					String newString=flag;
					int count=0;
				 	for (int j=0;j<s.length();j++) {
				 		if(s.charAt(j)=='0'){
				 			count=0;
				 			newString=newString+"0";
				 		}
				 		else {
				 			count++;
				 			newString=newString+"1";
				 		}
//				 		if there are 5 continuous "1", add "0"
				 		if (count==5) {
				 			newString=newString+"0";
				 			count=0;
				 		}
				 	}
				 	newString=newString;
//				 	System.out.println(newString.length());
//				 	System.out.print("\n");
				 	frameArray.add(newString);
				}
				frameArray.add(flag);
				System.out.println(frameArray.size());
				
				File frame = new File("frame.txt");
				try {
					FileOutputStream fop3 = new FileOutputStream(frame);
					OutputStreamWriter writer3 = new OutputStreamWriter(fop3, "UTF-8");
					for(int i = 0; i < frameArray.size(); i++){
							writer3.append(frameArray.get(i));
					}
				
					writer3.close();
					fop3.close();
				}
				catch (FileNotFoundException e) {
				    e.printStackTrace();
				} catch (IOException e) {
				    e.printStackTrace();
				}
			}


}
