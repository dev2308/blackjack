package main;

public class Utils {
	public static String formatSpaces(String s){
	   for(int i = s.length(); i < 15; i++){
		   s+= " ";
	   }
	   s+="\t";
	   return s;
	}

	public static void printArray(String[] arr){
       for(String asdf: arr){
           System.out.print(asdf + " ");
       }
   }
}
