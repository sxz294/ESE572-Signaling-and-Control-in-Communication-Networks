package layer1_2;

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
	public static ArrayList<ArrayList<Boolean>>  removeZero(ArrayList<ArrayList<Boolean>> inputBooleanList){
        byte temp=0;
        for(ArrayList<Boolean> a: inputBooleanList){
            for(int i = 0;i<a.size();i++){
                temp = (byte) ((temp<<1) + (a.get(i)?1:0));
                if((temp&0x3E)==0x3E){
                    a.remove(i);
                    i--;
                }
            }
        }
        return  inputBooleanList;
    }
    public static ArrayList<ArrayList<Boolean>> removeBits(ArrayList<ArrayList<Boolean>> booleanList){
        for(ArrayList<Boolean> a: booleanList){
            for(int i =0; i<8;i++){
                a.remove(0);
               a.remove(a.size()-1);
            }
//            if (a==booleanList.get(booleanList.size()-1)) {
//            	for(int i =0; i<8;i++){
//                    a.remove(a.size()-1);
//                }
//            }
        }
        return booleanList;
    }
    public static ArrayList<ArrayList<Byte>> bitToByte(ArrayList<ArrayList<Boolean>> bitFrames){
        ArrayList<ArrayList<Byte>> resultList = new ArrayList<>();
        for(ArrayList<Boolean> a : bitFrames){
            ArrayList<Byte> tempList = new ArrayList<>();
            for(int i = 0;i<=a.size()-8;i=i+8){
                byte tempByte =0;
                tempByte = (byte) (tempByte|(a.get(i)?0x01:0));
                tempByte = (byte) (tempByte|(a.get(i+1)?0x01<<1:0));
                tempByte = (byte) (tempByte|(a.get(i+2)?0x01<<2:0));
                tempByte = (byte) (tempByte|(a.get(i+3)?0x01<<3:0));
                tempByte = (byte) (tempByte|(a.get(i+4)?0x01<<4:0));
                tempByte = (byte) (tempByte|(a.get(i+5)?0x01<<5:0));
                tempByte = (byte) (tempByte|(a.get(i+6)?0x01<<6:0));
                tempByte = (byte) (tempByte|(a.get(i+7)?0x01<<7:0));
                tempList.add(tempByte);
            }
            resultList.add(tempList);
            //tempList.clear();
        }
        return resultList;
    }
    public static ArrayList<ArrayList<Boolean>> errorCorrect(ArrayList<ArrayList<Boolean>> FECframes){
        ArrayList<ArrayList<Boolean>> resultList = new ArrayList<>();
        for(ArrayList<Boolean> a : FECframes){
            ArrayList<Boolean> tempList = new ArrayList();
            for(int i = 0;i<=a.size()-3;i=i+3){
                int trueNum = 0;
                if(a.get(i)==true){
                    trueNum++;
                }
                if(a.get(i+1)==true){
                    trueNum++;
                }
                if(a.get(i+2)==true){
                    trueNum++;
                }
                if(trueNum>=2){
                    tempList.add(true);
                }else{
                    tempList.add(false);
                }
            }
            resultList.add(tempList);
            //tempList.clear();
        }
        return resultList;
    }
    // source: https://introcs.cs.princeton.edu/java/61data/CRC16CCITT.java.html
    public static int crc16ccitt(byte[] bytes) {
        int crc = 0xFFFF;          // initial value
        int polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)

        for (byte b : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b   >> (7-i) & 1) == 1);
                boolean c15 = ((crc >> 15    & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
            }
        }
        crc &= 0xffff;
        return crc;
    }
    
	public static int m2Div(String a,int b) {
		
		int lengtha=a.length();
		int lengthb=17;
	
		//d is the remainder
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
		
		int get_index=a.charAt(a.length()-index);
		return get_index&1;
	}

	
	
	public static void main(String[] args)  {
	
	ArrayList<String> charArray=new ArrayList<String>();
	//convert text file to ASCII
	File file = new File("history.txt");
	try {
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
	
	 	  }
	 	  while(s.length()<8) {
	 		  s="0"+s;
	 		  }
	 	  
	 	  charArray.add(s);
	 	  }
		
	 	reader.close();
	}
	catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}	
	File fch = new File("ascii.txt");
	try {
		FileOutputStream fop = new FileOutputStream(fch);
		OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
		for(int i = 0; i < charArray.size(); i++){
				writer.append(charArray.get(i));
		}
	
		writer.close();
		fop.close();
	}
	catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	
	// generate CRC
	ArrayList<String> wCRCArray=new ArrayList<String>();
	// set CRC generator
	int b=0b10001000000100001;
	String split="";
	String mix="";
//	count the number of segments
	int step=0;
	for(int i=0;i<charArray.size();i++) {
		
		split=split+charArray.get(i);
		//cut off segments every 25 chars (200 bits)
		if((i+1)%25==0) {
			int remainder=m2Div(split,b);
			String result=Integer.toBinaryString(remainder);
			while(result.length()<16)
			{
				result="0"+result;
			}
			mix=split+result;
//			System.out.println(mix);
			System.out.println(mix.length());
			wCRCArray.add(mix);
			split="";
			mix="";
			step++;
		}
		//the last segment 
		if(charArray.size()-i==1) {
			System.out.println(split);
			int remainder=m2Div(split,b);
			String result=Integer.toBinaryString(remainder);
			while(result.length()<16)
			{
				result="0"+result;
			}
			mix=split+result;
			System.out.println(mix.length());
			wCRCArray.add(mix);
//			System.out.println(mix);
			split="";
			mix="";
			step++;
		}
	}
	System.out.print(wCRCArray.size());
	File fcrc = new File("crc.txt");
	try {
		FileOutputStream fop = new FileOutputStream(fcrc);
		OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
		for(int i = 0; i < wCRCArray.size(); i++){
				writer.append(wCRCArray.get(i));
		}
	
		writer.close();
		fop.close();
	}
	catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
//	System.out.println(wCRCArray);
	//apply FEC
	ArrayList<String> wFECArray=new ArrayList<String>();
	for(int i=0;i<wCRCArray.size();i++) {
		String s =wCRCArray.get(i);
		String newString="";
	 	for (int j=0;j<s.length();j++) {
	 		//triplicate each bit
	 		newString=newString+Character.toString(s.charAt(j))+Character.toString(s.charAt(j))+Character.toString(s.charAt(j));
//	 		System.out.println(triS);
	 	}
	 	wFECArray.add(newString);
	}
	System.out.print(wFECArray.size());
	File ffec = new File("fec.txt");
	try {
		FileOutputStream fop = new FileOutputStream(ffec);
		OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
		for(int i = 0; i < wFECArray.size(); i++){
				writer.append(wFECArray.get(i));
		}
	
		writer.close();
		fop.close();
	}
	catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	
	
	//add frame flag and apply bit stuffing
	ArrayList<String> frameArray=new ArrayList<String>();
	String flag="01111110";
	for (int i=0;i<wFECArray.size();i++) {
		String s =wFECArray.get(i);
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
//	 		if there are 5 continuous "1", add "0"
	 		if (count==5) {
	 			newString=newString+"0";
	 			count=0;
	 		}
	 	}
	 	newString=newString;
//	 	System.out.println(newString.length());
//	 	System.out.print("\n");
	 	frameArray.add(newString);
	}
	frameArray.add(flag);
	System.out.print(frameArray.size());
	
	File f = new File("frame.txt");
	try {
		FileOutputStream fop = new FileOutputStream(f);
		OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
		for(int i = 0; i < frameArray.size(); i++){
				writer.append(frameArray.get(i));
		}
	
		writer.close();
		fop.close();
	}
	catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	}

}
