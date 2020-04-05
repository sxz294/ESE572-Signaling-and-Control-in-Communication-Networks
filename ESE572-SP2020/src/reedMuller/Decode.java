package reedMuller;

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

            }
            if (a==booleanList.get(booleanList.size()-1)) {
             for(int i =0; i<8;i++){
                    a.remove(a.size()-1);
                }
            }
        }
        return booleanList;
    }
	public static boolean getA0(Boolean A1,Boolean A2,Boolean A3,ArrayList<Boolean> r) {
		Boolean A0 = true;
		ArrayList<Boolean> temp= new ArrayList<Boolean>();
		ArrayList<Boolean> list_v1 = new ArrayList<Boolean>();
		ArrayList<Boolean> list_v2 = new ArrayList<Boolean>();
		ArrayList<Boolean> list_v3 = new ArrayList<Boolean>();
		list_v1.add(false);list_v1.add(false);list_v1.add(false);list_v1.add(false);list_v1.add(true);list_v1.add(true);list_v1.add(true);list_v1.add(true);
		list_v2.add(false);list_v2.add(false);list_v2.add(true);list_v2.add(true);list_v2.add(false);list_v2.add(false);list_v2.add(true);list_v2.add(true);
		list_v3.add(false);list_v3.add(true);list_v3.add(false);list_v3.add(true);list_v3.add(false);list_v3.add(true);list_v3.add(false);list_v3.add(true);
		int isTrue=0;
		int isFalse=0;
		for(int i = 0; i < r.size()-1; i++) {
			if (r.get(i)^(A1&list_v1.get(i))^(A2&list_v2.get(i))^(A3&list_v3.get(i))) {
				isTrue++;
			}
			else isFalse++;
		}
		if (isTrue<isFalse) {
			A0=false;
		}else {
			A0=true;
		}
		return A0;
	}

    public static ArrayList<ArrayList<Boolean>> errorCorrect(ArrayList<ArrayList<Boolean>> FECframes){
        ArrayList<ArrayList<Boolean>> resultList = new ArrayList<>();
        ArrayList<Boolean> tempList = new ArrayList<Boolean>();
        ArrayList<Boolean> outputList = new ArrayList<Boolean>();
        int j = 0;
        int isTrue;
    	int isFalse;
      	Boolean temp0;Boolean temp1;Boolean temp2;Boolean temp3;
    	Boolean A0=null;Boolean A1;Boolean A2;Boolean A3;
        for(ArrayList<Boolean> a : FECframes){
//        	System.out.println(a.size());
        	while(j < a.size()-7) {
            	for(int i=j;i<j+8;i++) {
            		tempList.add(a.get(i));
            	}
            	//majority for A1
            	isTrue = 0;
            	isFalse = 0;
            	temp0 = tempList.get(0)^tempList.get(4);
            	temp1 = tempList.get(1)^tempList.get(5);
            	temp2 = tempList.get(2)^tempList.get(6);
            	temp3 = tempList.get(3)^tempList.get(7);
            	if(temp0 == true) 	isTrue++;
            	else isFalse++;
            	if(temp1 == true) 	isTrue++;
            	else isFalse++;
            	if(temp2 == true) 	isTrue++;
            	else isFalse++;
            	if(temp3 == true) 	isTrue++;
            	else isFalse++;

            	if(isTrue > isFalse) {
            		A1 = true;
            	}else {
            		A1=false;
            	}
            	//majority for A2
            	isTrue = 0;
            	isFalse = 0;
            	temp0 = tempList.get(0)^tempList.get(2);
            	temp1 = tempList.get(1)^tempList.get(3);
            	temp2 = tempList.get(4)^tempList.get(6);
            	temp3 = tempList.get(5)^tempList.get(7);
            	if(temp0 == true) 	isTrue++;
            	else isFalse++;
            	if(temp1 == true) 	isTrue++;
            	else isFalse++;
            	if(temp2 == true) 	isTrue++;
            	else isFalse++;
            	if(temp3 == true) 	isTrue++;
            	else isFalse++;
//            	if(isTrue > isFalse) {
//            		A2 = true;
//            	}else if(isTrue<isFalse) {
//            		A2 = false;
//            	}else {
//            		A2=null;
//            	}
            	if(isTrue > isFalse) {
            		A2 = true;
            	}else {
            		A2=false;
            	}
            	// majority for A3
            	isTrue = 0;
            	isFalse = 0;
            	temp0 = tempList.get(0)^tempList.get(1);
            	temp1 = tempList.get(2)^tempList.get(3);
            	temp2 = tempList.get(4)^tempList.get(5);
            	temp3 = tempList.get(6)^tempList.get(7);
            	if(temp0 == true) 	isTrue++;
            	else isFalse++;
            	if(temp1 == true) 	isTrue++;
            	else isFalse++;
            	if(temp2 == true) 	isTrue++;
            	else isFalse++;
            	if(temp3 == true) 	isTrue++;
            	else isFalse++;
            	if(isTrue > isFalse) {
            		A3 = true;
            	}else {
            		A3=false;
            	}
            	// calculate A0
            	if (A1!=null && A2!=null && A3!=null) {
            		A0 = getA0(A1,A2,A3,tempList);
            	}
            	if (A0!=null && A1!=null && A2!=null && A3!=null) {
            	outputList.add(A0);
            	outputList.add(A1);
            	outputList.add(A2);
            	outputList.add(A3);
            	tempList = new ArrayList<Boolean>();
            	j += 8 ;
            	}else {
            		tempList = new ArrayList<Boolean>();
                	j += 8 ;
            	}

            }
        	System.out.println(outputList.size());
            resultList.add(outputList);
            outputList = new ArrayList<Boolean>();
            tempList = new ArrayList<Boolean>();
            j = 0;
        }
        return resultList;
    }

    
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

		    		}
		    	
		    }

		    in.close();
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		// add noise during transmission
  	  for(ArrayList<Boolean> a:booleanList){
	      for(int i = 0;i<a.size();i++){
	          if(Math.random()>0.999){
	              a.set(i,!a.get(i));
	          }
	      }
	  }

	   	removeBits(booleanList);
	   	removeZero(booleanList);
 
	   	
		System.out.println(booleanList.get(2));
	   	
	   	
        ArrayList<ArrayList<Boolean>> bitFrames = errorCorrect(booleanList);
		System.out.println(bitFrames.get(2));
       
        
        
        ArrayList<ArrayList<String>> stringList = new ArrayList<>();
        ArrayList<ArrayList<String>> crcFlag = new ArrayList<>();

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
				}
				
				if (i == bb.size()-1) {
					crcFlag.add(temp);
					temp = new ArrayList<String>();
					
				}
			}
        }
        
        int b=0b10001000000100001;
    	String split="";
    	String mix="";
    	int step=0;
    	int total = 0;
    	int flag = 1;
    	int count = 0;
    	ArrayList<String> wCRCArray = new ArrayList<String>();
    	for (int j=0; j<stringList.size();j++) {
    		ArrayList<String> charArray=new ArrayList<String>();
    		ArrayList<String> crcCheck=new ArrayList<String>();
    	
    		charArray = stringList.get(j);
    		crcCheck = crcFlag.get(j);
    		String msg="";
    		String crc="";
    		
    	for(int i=0;i<charArray.size();i++) {
    		
    		msg=msg+charArray.get(i);
    		}
			int remainder=m2Div(msg,b);
			String result=Integer.toBinaryString(remainder);
			while(result.length()<16)
			{
				result="0"+result;
			}
			// check CRC
			wCRCArray.add(result);
		 	for(int k = 0; k< 15;k++) {
		 		char cc = wCRCArray.get(j).charAt(k);
	    		String kk = Character.toString(cc);

		 		if(!kk.equals(crcCheck.get(k))) {
		    	flag = 0;
		    	}	
		 	}
		 	if(flag == 1) {
		 		count ++ ;
		 	}else {
		 		flag = 1;
		 	}
    	}

    	System.out.println(count);
	}

}
