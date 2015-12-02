package prymm.model;

import java.io.*;

public class LogWriter {

	public static void writeLog(SingleDrop[][] alldrops)
	 {
		 File fileName = new File("D:\\log.txt");	 
	     PrintWriter outFile = null;
	     
	     try {
	    	 if (!fileName.exists()) {
	    		 fileName.createNewFile();
	    		}
	        outFile = new PrintWriter( new BufferedWriter(new FileWriter(fileName,true)));
	        outFile.println("X-Velocity : " + alldrops[200][150].getxVel());   
	        outFile.println("Y-Velocity : " + alldrops[200][150].getyVel());
	        outFile.println("Density : " + alldrops[200][150].getDensity());
	        outFile.println("----------------------------------------------------------------");
	        outFile.println("X-Velocity : " + alldrops[300][120].getxVel());   
	        outFile.println("Y-Velocity : " + alldrops[300][120].getyVel());
	        outFile.println("Density : " + alldrops[300][120].getDensity());
	        outFile.println("----------------------------------------------------------------");
	        outFile.println("X-Velocity : " + alldrops[250][200].getxVel());   
	        outFile.println("Y-Velocity : " + alldrops[250][200].getyVel());
	        outFile.println("Density : " + alldrops[250][200].getDensity());
	        outFile.println("----------------------------------------------------------------");
	        outFile.println("X-Velocity : " + alldrops[100][100].getxVel());   
	        outFile.println("Y-Velocity : " + alldrops[100][100].getyVel());
	        outFile.println("Density : " + alldrops[100][100].getDensity());
	        outFile.println("****************************************************************");
	        outFile.flush();
	     } catch (IOException ex) {
	        ex.printStackTrace();
	      } finally {
	        if (outFile != null) {
	           try {
	              outFile.close();
	           } catch (Exception e) {

	           }
	        }
	     }
	 }
}

 