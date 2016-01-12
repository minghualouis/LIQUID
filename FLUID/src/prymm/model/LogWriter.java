package prymm.model;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

import prymm.controller.UsrDataConfig;

public class LogWriter {

	public static void writeLog(Flow currentFlow)
	 {
		 File fileName = new File(".\\log.txt");	 
	     PrintWriter outFile = null;
	     
	     try {
	    	 if (!fileName.exists()) {
	    		 fileName.createNewFile();
	    		}
	        outFile = new PrintWriter( new BufferedWriter(new FileWriter(fileName,true)));
			UsrDataConfig usrData = UsrDataConfig.getUsrDataConfig();
			int bp1xIndex = usrData.getLength() / 2;
			int bp1yIndex = usrData.getWidth() / 4;
			
			int bp2xIndex = usrData.getLength() / 2;
			int bp2yIndex = usrData.getWidth()* 3 / 4;
			
			int bp3xIndex = usrData.getLength() / 4;
			int bp3yIndex = usrData.getWidth() / 2;
			
			int bp4xIndex = usrData.getLength() * 3 / 4;
			int bp4yIndex = usrData.getWidth() / 2;
	        outFile.println("X-Velocity : " + currentFlow.getAllDrops()[bp1xIndex][bp1yIndex].getxVel());  
//	        System.out.println(bp1xIndex + "--" + bp1yIndex + "--" + );
	        outFile.println("Y-Velocity : " + currentFlow.getAllDrops()[bp1xIndex][bp1yIndex].getyVel());
	        outFile.println("Density : " + currentFlow.getAllDrops()[bp1xIndex][bp1yIndex].getDensity());
	        outFile.println("----------------------------------------------------------------");
	        outFile.println("X-Velocity : " + currentFlow.getAllDrops()[bp2xIndex][bp2yIndex].getxVel());   
	        outFile.println("Y-Velocity : " + currentFlow.getAllDrops()[bp2xIndex][bp2yIndex].getyVel());
	        outFile.println("Density : " + currentFlow.getAllDrops()[bp2xIndex][bp2yIndex].getDensity());
	        outFile.println("----------------------------------------------------------------");
	        outFile.println("X-Velocity : " + currentFlow.getAllDrops()[bp3xIndex][bp3yIndex].getxVel());   
	        outFile.println("Y-Velocity : " + currentFlow.getAllDrops()[bp3xIndex][bp3yIndex].getyVel());
	        outFile.println("Density : " + currentFlow.getAllDrops()[bp3xIndex][bp3yIndex].getDensity());
	        outFile.println("----------------------------------------------------------------");
	        outFile.println("X-Velocity : " + currentFlow.getAllDrops()[bp4xIndex][bp4yIndex].getxVel());   
	        outFile.println("Y-Velocity : " + currentFlow.getAllDrops()[bp4xIndex][bp4yIndex].getyVel());
	        outFile.println("Density : " + currentFlow.getAllDrops()[bp4xIndex][bp4yIndex].getDensity());
	        outFile.println("****************************************************************");
	        
	        
	        //add flow meters
	        LinkedList<SingleDrop> flowMetersList = currentFlow.getFlowMeters();
	        Iterator<SingleDrop> flowMeterIterator = flowMetersList.iterator();
	        while(flowMeterIterator.hasNext())
	        {
	        	SingleDrop currentFlowMeter = flowMeterIterator.next();
	        	outFile.println("FlowMeter X-index : " + currentFlowMeter.getxIndex()); 
	        	outFile.println("FlowMeter Y-index : " + currentFlowMeter.getyIndex());
		        outFile.println("FlowMeter X-Velocity : " + currentFlowMeter.getxVel());   
		        outFile.println("FlowMeter Y-Velocity : " + currentFlowMeter.getyVel());
		        outFile.println("FlowMeter Density : " + currentFlowMeter.getDensity());
	        }
	        
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

 