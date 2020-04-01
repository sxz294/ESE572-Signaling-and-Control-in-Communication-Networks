package layer1_2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Decode {
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
//                a.remove(a.size()-1);
            }
            if (a==booleanList.get(booleanList.size()-1)) {
             for(int i =0; i<8;i++){
                    a.remove(a.size()-1);
                }
            }
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
	
     
    
    
	public static void main(String[] args) {
		
		BufferedReader in = null;
		ArrayList<ArrayList<Boolean>> booleanList = new ArrayList<>();
		try {   
		    in = new BufferedReader(new FileReader("frame.txt"));
		    String str=in.readLine();
		    Boolean str1;
		
		    ArrayList<Boolean> temp=new ArrayList<Boolean>();
		    for(int i=0; i<str.length() - 8;i++) {
		    	char e=str.charAt(i);
		    		if (e == '0') {
		    			str1 = false;
		    		}else {
		    			str1 = true;
		    		}
		    		if (str.charAt(i) == '0' && 
		    				str.charAt(i+1) == '1' &&
		    				str.charAt(i+2) == '1' && 
		    				str.charAt(i+3) == '1' && 
		    				str.charAt(i+4) == '1' && 
		    				str.charAt(i+5) == '1' && 
		    				str.charAt(i+6) == '1' && 
		    				str.charAt(i+7) == '0'
		    				) {
		    			temp.add(false);	
		    			temp.add(true);
		    			temp.add(true);
		    			temp.add(true);
		    			temp.add(true);
		    			temp.add(true);
		    			temp.add(true);
		    			temp.add(false);	
		    			i = i+7;
		    		}
		    		else {
		    			temp.add(str1);
		    			if (i == str.length() - 9 ) {
		    				temp.add(false);	
			    			temp.add(true);
			    			temp.add(true);
			    			temp.add(true);
			    			temp.add(true);
			    			temp.add(true);
			    			temp.add(true);
			    			temp.add(false);
			    			booleanList.add(temp);
			    			break;
			    		
		    			}
		    			else if (str.charAt(i+1) == '0' && 
			    				str.charAt(i+2) == '1' &&
			    				str.charAt(i+3) == '1' && 
			    				str.charAt(i+4) == '1' && 
			    				str.charAt(i+5) == '1' && 
			    				str.charAt(i+6) == '1' && 
			    				str.charAt(i+7) == '1' && 
			    				str.charAt(i+8) == '0'
			    				) {
		    				booleanList.add(temp);
//		    			System.out.println(temp.size());
		    			temp=new ArrayList<Boolean>();
		    			}
		    			else {
		    				continue;
		    			}
		    		}
		    	
		    }

		    in.close();
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
//
	  for(ArrayList<Boolean> a:booleanList){
	      for(int i = 0;i<a.size();i++){
	          if(Math.random()>0.999){
	              a.set(i,!a.get(i));
	          }
	      }
	      System.out.println(a.size());
	  }
	  
	   	removeBits(booleanList);
	   	
	   	removeZero(booleanList);
//		  for(ArrayList<Boolean> a:booleanList){
//		      System.out.println(a.size());
//		  }

        ArrayList<ArrayList<Boolean>> bitFrames = errorCorrect(booleanList);
//		  for(ArrayList<Boolean> a:bitFrames){
////		      System.out.println(a);
//		      System.out.println(a.size());
//		  }
        ArrayList<ArrayList<String>> stringList = new ArrayList<>();
        ArrayList<ArrayList<String>> crcFlag = new ArrayList<>();
//    	System.out.println(bitFrames.size());
        for (int j=0; j<bitFrames.size();j++) {
			
        	ArrayList<Boolean> bb=new ArrayList<Boolean>();
        	ArrayList<String> temp = new ArrayList<String>();
        	
			bb=bitFrames.get(j);
			String s="";

			for (int i=0; i<bb.size();i++) {
		
				
				
						if (bb.get(i)==false) {
							s="0";
							temp.add(s);
						}
						else {
							s="1";
							temp.add(s);
						}				
				if (i == bb.size()-17) {
					stringList.add(temp);
					 temp = new ArrayList<String>();
//						System.out.println(stringList);
				}
				
				if (i == bb.size()-1) {
					crcFlag.add(temp);
					temp = new ArrayList<String>();
//					System.out.println(crcFlag);
					
				}
			}
        }

        
        
    int b=0b10001000000100001;
  	String split="";
  	String mix="";
//  	count the number of segments
  	int step=0;
  	int total = 0;
  	int flag = 1;
  	int count = 0;
  	ArrayList<String> wCRCArray = new ArrayList<String>();
//  	System.out.println(stringList.size());
  	for (int j=0; j<stringList.size();j++) {
  		ArrayList<String> charArray=new ArrayList<String>();
  		ArrayList<String> crcCheck=new ArrayList<String>();
  	
  		charArray = stringList.get(j);
  		crcCheck = crcFlag.get(j);
//  		System.out.println(charArray);
//  		System.out.println(crcCheck);
  		String msg="";
  		String crc="";
  		
  	for(int i=0;i<charArray.size();i++) {
  		
  		msg=msg+charArray.get(i);
  		}
  	for(int k=0;k<crcCheck.size();k++) {
  		crc=crc+crcCheck.get(k);
  		}
			int remainder=m2Div(msg,b);
			String result=Integer.toBinaryString(remainder);
			while(result.length()<16)
			{
				result="0"+result;
			}
			
			wCRCArray.add(result);
			if (result.equals(crc)) {
				count++;
			}
  		
  	}
//  	count++;
//  	System.out.println(wCRCArray);
  	System.out.println(count);
	}

}
