/**
 * 
 */
/**
 * @author radhika
 * read 
 * write log
 * This program demonstrates how to write characters to a text file. 
 */

package prymm.controller;
import prymm.controller.UsrDataConfig;
import prymm.model.Flow;
import prymm.model.FluidFactory;
import prymm.model.RenderingMachine;
import prymm.model.SingleDrop;
import java.io.*;
import java.util.Scanner;



public class Test {
    public static void main(String [] args) throws IOException {

    	

    	//reading the first line, always have header
    	//I suppose
    	
InputStream ins = null; // raw byte-stream
Reader r = null; // cooked reader

BufferedReader br = null; // buffered for readLine()
try {
    String s;
    String a[]=new String[10];
    int i = 0 ;
	ins = new FileInputStream("C:\\Users\\parth\\Desktop\\radhika.txt");
        r = new InputStreamReader(ins, "UTF-8"); // leave charset out for default
    br = new BufferedReader(r);
    
    while ((s = br.readLine()) != null) {
    	 String[] columns = s.split(" ");
    	 
        System.out.println(columns[0] + "  " + columns[1]);
       
        a[i] = columns[1];
       // System.out.println(a[i]);
       i++;
        }
	UsrDataConfig usrcDataConfig = UsrDataConfig.getUsrDataConfig();

	// barrier setting
//	usrcDataConfig.setViscosity(a[0]);
	
	   System.out.println(usrcDataConfig.getViscosity());
	   
}
catch (Exception e)
{
    System.err.println(e.getMessage()); // handle exception
}
finally {
    if (br != null) { try { br.close(); } catch(Throwable t) { /* ensure close happens */ } }
    if (r != null) { try { r.close(); } catch(Throwable t) { /* ensure close happens */ } }
    if (ins != null) { try { ins.close(); } catch(Throwable t) { /* ensure close happens */ } }
}
}
    }