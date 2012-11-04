import java.io.*;
import jade.util.Logger;

class FileRead {
  public static void main(String args[]) {
//Object[] args= getArguments();
	 String fileName="";
 	 byte[] fileContent= new byte[10];
	String copyFile = "";	    
			
	if(args!=null && args.length>0){
	    fileName= (String) args[0];
	    
	    //addBehaviour(new SendFileBehaviour(this));
	    System.out.println(fileName);
	}else{
	    System.out.println("No se especifico el nombre del archivo");
	    //doDelete();
	}    

		try {
	      FileInputStream fstream = new FileInputStream(fileName);
	      DataInputStream in = new DataInputStream(fstream);
	      BufferedReader br = new BufferedReader(new InputStreamReader(in));
	      String str;
	      StringBuffer strContent = new StringBuffer(""); 
		int i=0;	
	     while ((str = br.readLine()) != null) {
			strContent.append(str);	     
		   
			fileContent = str.getBytes("UTF-16LE");
		System.out.println(fileContent);
		
			
	      }
		copyFile = new String(fileContent);
		File f;		
		f=new File("/home/juli/lohizo.txt");
		FileWriter fw = new FileWriter(f.getAbsoluteFile());			
		BufferedWriter out = new BufferedWriter(fw);			
		copyFile = new String(fileContent);		    
		out.write(copyFile);	    
		
		System.out.println(copyFile);
	      in.close();
	    } catch (Exception e) {
	      System.err.println(e);
	    }
		
  }
}
