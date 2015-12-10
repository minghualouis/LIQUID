package prymm.model;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.image.MemoryImageSource;
//import java.text.DecimalFormat;
import java.awt.Color;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
//import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

import prymm.controller.StateController;
import prymm.controller.UsrDataConfig;
import prymm.gui.FluidDefaultPage;

/**
 * Class rendering machine is used for calculation of all the single drops of flow in the application
 * @author Yashraj
 *
 */
public class RenderingMachine implements Runnable{

	
	Flow currentFlow = null;
	double four9ths = 4.0 / 9;
	double one9th = 1.0 / 9;
	double one36th = 1.0 / 36;
	
	private static double[][] density = new double[UsrDataConfig.getUsrDataConfig().getLength()][UsrDataConfig.getUsrDataConfig().getWidth()];
	
	/**
	 * for thread manangement minghua
	 */
	private static boolean isRunning = true;
	
	public RenderingMachine(Flow initialFlow) {
		// TODO Auto-generated constructor stub
		currentFlow = initialFlow;
		
	}

	
	private void canvasInit() {
		
		//double n, one9thn, one36thn, vx, vy, vx2, vy2, vx3, vy3, vxvy2, v2, v215;
		int xdim = UsrDataConfig.getUsrDataConfig().getLength();
		int ydim = UsrDataConfig.getUsrDataConfig().getWidth();
		double xVel,yVel,v = Double.parseDouble(UsrDataConfig.getUsrDataConfig().getInitialSpeed().trim());
		String initForce = UsrDataConfig.getUsrDataConfig().getInitialForce(); 
		String fluidType = UsrDataConfig.getUsrDataConfig().getFluidType();
		SingleDrop[][] alldrops = currentFlow.getAllDrops();
		double density;
		//Set density of all the lattice before running simulation
		if ("Water".equals(fluidType)) 
		{
//			System.out.println("Fluid is water");
			density = 1;
		}
		else if("Glycerin".equals(fluidType))
		{
//			System.out.println("Fluid is glycerin");
			density = 1.26;
		}
		else
		{
			density = 1;
		}
		//Set velocity of all lattice before running simulation
		if ("Left".equals(initForce)) 
		{
//			System.out.println("Direction is from left");
			xVel = v;
			yVel = 0;
		}
		else if("Right".equals(initForce))
		{
			xVel = -v;
			yVel = 0;
		}
		else if("Top".equals(initForce))
		{
			xVel = 0;
			yVel = -v;
		}
		else
		{
			xVel  = 0;
			yVel = v;
		}
		for (int x=0; x<xdim; x++) {//ys9:Get viscosity
			for (int y=0; y<ydim; y++) {
				alldrops[x][y].setDensity(density);
				alldrops[x][y].setxVel(xVel);
				alldrops[x][y].setyVel(yVel);
				if("Right".equals(initForce)){
				alldrops[x][y].getDots()[1][1].setVal(four9ths * (1 - 1.5*v*v));
				alldrops[x][y].getDots()[0][1].setVal(one9th * (1 + 3*v + 3*v*v));
				alldrops[x][y].getDots()[2][1].setVal(one9th * (1 - 3*v + 3*v*v));
				alldrops[x][y].getDots()[1][2].setVal(one9th * (1 - 1.5*v*v));
				alldrops[x][y].getDots()[1][0].setVal(one9th * (1 - 1.5*v*v));
				alldrops[x][y].getDots()[0][2].setVal(one36th * (1 + 3*v + 3*v*v));
				alldrops[x][y].getDots()[0][0].setVal(one36th * (1 + 3*v + 3*v*v));
				alldrops[x][y].getDots()[2][2].setVal(one36th * (1 - 3*v + 3*v*v));
				alldrops[x][y].getDots()[2][0].setVal(one36th * (1 - 3*v + 3*v*v));
				}
				else{
				alldrops[x][y].getDots()[1][1].setVal(four9ths * (1 - 1.5*v*v));
				alldrops[x][y].getDots()[0][1].setVal(one9th * (1 + 3*v + 3*v*v));
				alldrops[x][y].getDots()[2][1].setVal(one9th * (1 - 3*v + 3*v*v));
				alldrops[x][y].getDots()[1][2].setVal(one9th * (1 - 1.5*v*v));
				alldrops[x][y].getDots()[1][0].setVal(one9th * (1 - 1.5*v*v));
				alldrops[x][y].getDots()[0][2].setVal(one36th * (1 + 3*v + 3*v*v));
				alldrops[x][y].getDots()[0][0].setVal(one36th * (1 + 3*v + 3*v*v));
				alldrops[x][y].getDots()[2][2].setVal(one36th * (1 - 3*v + 3*v*v));
				alldrops[x][y].getDots()[2][0].setVal(one36th * (1 - 3*v + 3*v*v));		
				}
			}
		}
	}
		
	
	/**
	 * calculate all the drops in canvas
	 */
	private void doingCalculation() {
		// For fluid calculation
		//System.out.println("Begin to do calculation");
		double viscosity = currentFlow.getFlowType().getViscosity();
		// ...used for calculation
		int time = 0;
		int stepTime = 0;
		int collideTime = 0;
		int streamTime = 0;
		int paintTime = 0;
		long startTime = System.currentTimeMillis();
//		force();
		long forceTime = System.currentTimeMillis();
		collision(viscosity);
		long afterCollideTime = System.currentTimeMillis();
		collideTime = (int)(afterCollideTime - forceTime);		// 23-24 ms for 600x600 grid
		stream();
		streamTime = (int) (System.currentTimeMillis() - afterCollideTime);	// 9-10 ms for 600x600 grid
		bounce();
		stepTime = (int) (System.currentTimeMillis() - startTime);	// 33-35 ms for 600x600 grid
		time++;
		//WelcomePage.getCanvas().repaint();
		//dataCanvas.repaint();//ys9:replace with set repaint method in rendering
		
		// ...used for calculation
	}
	
	/**
	 * Calculating the density, velocity for each lattice 
	 * @param viscosity
	 */
	private void collision(double viscosity) {
		//System.out.println("Goin in Collision");
		
		double n, one9thn, one36thn, vx, vy, vx2, vy2, vx3, vy3, vxvy2, v2, v215;
		int xdim = UsrDataConfig.getUsrDataConfig().getLength();
		int ydim = UsrDataConfig.getUsrDataConfig().getWidth();
		SingleDrop[][] alldrops = currentFlow.getAllDrops();

		double omega = 1 / (0.3*viscosity+ 0.5);// reciprocal of tau, the relaxation time
		for (int x=0; x<xdim; x++) {//ys9:Get viscosity
			for (int y=0; y<ydim; y++) {
				if (alldrops[x][y].isEnable()) {
					n = alldrops[x][y].getDots()[1][1].getVal() + alldrops[x][y].getDots()[1][2].getVal() + alldrops[x][y].getDots()[1][0].getVal() + alldrops[x][y].getDots()[2][1].getVal() + alldrops[x][y].getDots()[0][1].getVal() + alldrops[x][y].getDots()[0][2].getVal() + alldrops[x][y].getDots()[2][2].getVal() + alldrops[x][y].getDots()[0][0].getVal() + alldrops[x][y].getDots()[2][0].getVal();
					alldrops[x][y].setDensity(n);		// macroscopic density may be needed for plotting
					one9thn = one9th * n;
					one36thn = one36th * n;
					if (n > 0) {
						vx = (alldrops[x][y].getDots()[2][1].getVal() + alldrops[x][y].getDots()[2][2].getVal() + alldrops[x][y].getDots()[2][0].getVal() - alldrops[x][y].getDots()[0][1].getVal() - alldrops[x][y].getDots()[0][2].getVal() - alldrops[x][y].getDots()[0][0].getVal()) / n;
					} else vx = 0;
					alldrops[x][y].setxVel(vx);		// may be needed for plotting
					if (n > 0) {
						vy = ( alldrops[x][y].getDots()[1][2].getVal()+ alldrops[x][y].getDots()[2][2].getVal() + alldrops[x][y].getDots()[0][2].getVal() - alldrops[x][y].getDots()[1][0].getVal() - alldrops[x][y].getDots()[2][0].getVal() - alldrops[x][y].getDots()[0][0].getVal()) / n;
					} else vy = 0;
					alldrops[x][y].setyVel(vy);		// may be needed for plotting
					vx3 = 3 * vx;
					vy3 = 3 * vy;
					vx2 = vx * vx;
					vy2 = vy * vy;
					vxvy2 = 2 * vx * vy;
					v2 = vx2 + vy2;
					v215 = 1.5 * v2;
					double temp;
					temp = alldrops[x][y].getDots()[1][1].getVal();
					temp  += omega * (four9ths*n * (1                              - v215) - temp);
					alldrops[x][y].getDots()[1][1].setVal(temp);
					temp = alldrops[x][y].getDots()[2][1].getVal();
					temp  += omega * (   one9thn * (1 + vx3       + 4.5*vx2        - v215) - temp);
					alldrops[x][y].getDots()[2][1].setVal(temp);
					temp = alldrops[x][y].getDots()[0][1].getVal();
					temp  += omega * (   one9thn * (1 - vx3       + 4.5*vx2        - v215) - temp);
					alldrops[x][y].getDots()[0][1].setVal(temp);
					temp = alldrops[x][y].getDots()[1][2].getVal();					
					temp  += omega * (   one9thn * (1 + vy3       + 4.5*vy2        - v215) - temp);
					alldrops[x][y].getDots()[1][2].setVal(temp);
					temp = alldrops[x][y].getDots()[1][0].getVal();
					temp  += omega * (   one9thn * (1 - vy3       + 4.5*vy2        - v215) - temp);
					alldrops[x][y].getDots()[1][0].setVal(temp);
					temp = alldrops[x][y].getDots()[2][2].getVal();
					temp += omega * (  one36thn * (1 + vx3 + vy3 + 4.5*(v2+vxvy2) - v215) - temp);
					alldrops[x][y].getDots()[2][2].setVal(temp);
					temp = alldrops[x][y].getDots()[0][2].getVal();
					temp += omega * (  one36thn * (1 - vx3 + vy3 + 4.5*(v2-vxvy2) - v215) - temp);
					alldrops[x][y].getDots()[0][2].setVal(temp);
					temp = alldrops[x][y].getDots()[2][0].getVal();
					temp += omega * (  one36thn * (1 + vx3 - vy3 + 4.5*(v2-vxvy2) - v215) - temp);
					alldrops[x][y].getDots()[2][0].setVal(temp);
					temp = alldrops[x][y].getDots()[0][0].getVal();
					temp += omega * (  one36thn * (1 - vx3 - vy3 + 4.5*(v2+vxvy2) - v215) - temp);
					alldrops[x][y].getDots()[0][0].setVal(temp);
				}
			}
		}
	}

	
	// Stream particles into neighboring cells:
	private void stream() {
		//System.out.println("Goin in Stream");
		int xdim = UsrDataConfig.getUsrDataConfig().getLength();
		int ydim = UsrDataConfig.getUsrDataConfig().getWidth();
		SingleDrop[][] alldrops = currentFlow.getAllDrops();
		String initForce = UsrDataConfig.getUsrDataConfig().getInitialForce();
		for (int x=0; x<xdim-1; x++) {// first start in NW corner...
			for (int y=ydim-1; y>0; y--) 
			{
				alldrops[x][y].getDots()[1][2].setVal(alldrops[x][y-1].getDots()[1][2].getVal());// and the north-moving particles
				alldrops[x][y].getDots()[0][2].setVal(alldrops[x+1][y-1].getDots()[0][2].getVal());// and the northwest-moving particles
			}
		}
		
		for (int x=xdim-1; x>0; x--) {		// now start in NE corner...
			for (int y=ydim-1; y>0; y--) {
				alldrops[x][y].getDots()[2][1].setVal(alldrops[x-1][y].getDots()[2][1].getVal());// and the east-moving particles
				alldrops[x][y].getDots()[2][2].setVal(alldrops[x-1][y-1].getDots()[2][2].getVal());// and the northeast-moving particles
			}
		}
		for (int x=xdim-1; x>0; x--) {		// now start in SE corner...
			for (int y=0; y<ydim-1; y++) {
				alldrops[x][y].getDots()[1][0].setVal(alldrops[x][y+1].getDots()[1][0].getVal());// and the south-moving particles
				alldrops[x][y].getDots()[2][0].setVal(alldrops[x-1][y+1].getDots()[2][0].getVal());// and the south-east-moving particles
			}
		}
		for (int x=0; x<xdim-1; x++) {		// now start in the SW corner...
			for (int y=0; y<ydim-1; y++) {
				alldrops[x][y].getDots()[0][1].setVal(alldrops[x+1][y].getDots()[0][1].getVal());// and the west-moving particles
				alldrops[x][y].getDots()[0][0].setVal(alldrops[x+1][y+1].getDots()[0][0].getVal());// and the south-west-moving particles
			}
		}
		// We missed a few at the left and right edges:
		for (int y=0; y<ydim-1; y++) {
			alldrops[0][y].getDots()[1][0].setVal(alldrops[0][y+1].getDots()[1][0].getVal());// and the south-moving particles
		}
		for (int y=ydim-1; y>0; y--) {
			alldrops[xdim-1][y].getDots()[1][2].setVal(alldrops[xdim-1][y-1].getDots()[1][2].getVal());// and the northeast-moving particles
		}
		// Now handle left boundary as in Pullan's example code:
		// Stream particles in from the non-existent space to the left, with the
		// user-determined speed:
		//double v = speedScroller.getValue();
		double v = Double.parseDouble(UsrDataConfig.getUsrDataConfig().getInitialSpeed().trim());
		if("Right".equals(initForce)){
		for (int y=0; y<ydim; y++) {
			if (alldrops[0][y].isEnable()) {
				alldrops[0][y].getDots()[0][1].setVal(one9th * (1 + 3*v + 3*v*v));
				alldrops[0][y].getDots()[0][2].setVal(one36th * (1 + 3*v + 3*v*v));
				alldrops[0][y].getDots()[0][0].setVal(one36th * (1 + 3*v + 3*v*v));
			}
		}
		// Try the same thing at the right edge and see if it works:
		for (int y=0; y<ydim; y++) {
			if (alldrops[0][y].isEnable()) {
				alldrops[xdim-1][y].getDots()[2][1].setVal(one9th * (1 - 3*v + 3*v*v));
				alldrops[xdim-1][y].getDots()[2][2].setVal(one36th * (1 - 3*v + 3*v*v));
				alldrops[xdim-1][y].getDots()[2][0].setVal(one36th * (1 - 3*v + 3*v*v));
			}
		}
		// Now handle top and bottom edges:
		for (int x=0; x<xdim; x++) {
			alldrops[x][0].getDots()[1][1].setVal(four9ths * (1 - 1.5*v*v));
			alldrops[x][0].getDots()[0][1].setVal(one9th * (1 + 3*v + 3*v*v));
			alldrops[x][0].getDots()[2][1].setVal(one9th * (1 - 3*v + 3*v*v));
			alldrops[x][0].getDots()[1][2].setVal(one9th * (1 - 1.5*v*v));
			alldrops[x][0].getDots()[1][0].setVal(one9th * (1 - 1.5*v*v));
			alldrops[x][0].getDots()[0][2].setVal(one36th * (1 + 3*v + 3*v*v));
			alldrops[x][0].getDots()[0][0].setVal(one36th * (1 + 3*v + 3*v*v));
			alldrops[x][0].getDots()[2][2].setVal(one36th * (1 - 3*v + 3*v*v));
			alldrops[x][0].getDots()[2][0].setVal(one36th * (1 - 3*v + 3*v*v));
			alldrops[x][ydim-1].getDots()[1][1].setVal(four9ths * (1 - 1.5*v*v));
			alldrops[x][ydim-1].getDots()[0][1].setVal(one9th * (1 + 3*v + 3*v*v));
			alldrops[x][ydim-1].getDots()[2][1].setVal(one9th * (1 - 3*v + 3*v*v));
			alldrops[x][ydim-1].getDots()[1][2].setVal(one9th * (1 - 1.5*v*v));
			alldrops[x][ydim-1].getDots()[1][0].setVal(one9th * (1 - 1.5*v*v));
			alldrops[x][ydim-1].getDots()[0][2].setVal(one36th * (1 + 3*v + 3*v*v));
			alldrops[x][ydim-1].getDots()[0][0].setVal(one36th * (1 + 3*v + 3*v*v));
			alldrops[x][ydim-1].getDots()[2][2].setVal(one36th * (1 - 3*v + 3*v*v));
			alldrops[x][ydim-1].getDots()[2][0].setVal(one36th * (1 - 3*v + 3*v*v));
		}
		}else{
			for (int y=0; y<ydim; y++) {
				if (alldrops[0][y].isEnable()) {
					alldrops[0][y].getDots()[2][1].setVal(one9th * (1 + 3*v + 3*v*v));
					alldrops[0][y].getDots()[2][2].setVal(one36th * (1 + 3*v + 3*v*v));
					alldrops[0][y].getDots()[2][0].setVal(one36th * (1 + 3*v + 3*v*v));
				}
			}
			// Try the same thing at the right edge and see if it works:
			for (int y=0; y<ydim; y++) {
				if (alldrops[0][y].isEnable()) {
					alldrops[xdim-1][y].getDots()[0][1].setVal(one9th * (1 - 3*v + 3*v*v));
					alldrops[xdim-1][y].getDots()[0][2].setVal(one36th * (1 - 3*v + 3*v*v));
					alldrops[xdim-1][y].getDots()[0][0].setVal(one36th * (1 - 3*v + 3*v*v));
				}
			}
			// Now handle top and bottom edges:
			for (int x=0; x<xdim; x++) {
				alldrops[x][0].getDots()[1][1].setVal(four9ths * (1 - 1.5*v*v));
				alldrops[x][0].getDots()[2][1].setVal(one9th * (1 + 3*v + 3*v*v));
				alldrops[x][0].getDots()[0][1].setVal(one9th * (1 - 3*v + 3*v*v));
				alldrops[x][0].getDots()[1][2].setVal(one9th * (1 - 1.5*v*v));
				alldrops[x][0].getDots()[1][0].setVal(one9th * (1 - 1.5*v*v));
				alldrops[x][0].getDots()[2][2].setVal(one36th * (1 + 3*v + 3*v*v));
				alldrops[x][0].getDots()[2][0].setVal(one36th * (1 + 3*v + 3*v*v));
				alldrops[x][0].getDots()[0][2].setVal(one36th * (1 - 3*v + 3*v*v));
				alldrops[x][0].getDots()[0][0].setVal(one36th * (1 - 3*v + 3*v*v));
				alldrops[x][ydim-1].getDots()[1][1].setVal(four9ths * (1 - 1.5*v*v));
				alldrops[x][ydim-1].getDots()[2][1].setVal(one9th * (1 + 3*v + 3*v*v));
				alldrops[x][ydim-1].getDots()[0][1].setVal(one9th * (1 - 3*v + 3*v*v));
				alldrops[x][ydim-1].getDots()[1][2].setVal(one9th * (1 - 1.5*v*v));
				alldrops[x][ydim-1].getDots()[1][0].setVal(one9th * (1 - 1.5*v*v));
				alldrops[x][ydim-1].getDots()[2][2].setVal(one36th * (1 + 3*v + 3*v*v));
				alldrops[x][ydim-1].getDots()[2][0].setVal(one36th * (1 + 3*v + 3*v*v));
				alldrops[x][ydim-1].getDots()[0][2].setVal(one36th * (1 - 3*v + 3*v*v));
				alldrops[x][ydim-1].getDots()[0][0].setVal(one36th * (1 - 3*v + 3*v*v));	
		}	
		}
	}
	
	// Bounce particles off of barriers:
	// (The ifs are needed to prevent array index out of bounds errors. Could handle edges
	//  separately to avoid this.)
	private void bounce() {
		//System.out.println("Goin in bounce");
		int xdim = UsrDataConfig.getUsrDataConfig().getLength();
		int ydim = UsrDataConfig.getUsrDataConfig().getWidth();
		double direction,oppdirection;
		SingleDrop[][] alldrops = currentFlow.getAllDrops();
		
		for (int x=0; x<xdim; x++) {
			for (int y=0; y<ydim; y++) {
				if (!alldrops[x][y].isEnable()) {
					direction= alldrops[x][y].getDots()[1][2].getVal();
					oppdirection = alldrops[x][y-1].getDots()[1][0].getVal(); 
					if (direction > 0) 
					{
						oppdirection += direction; direction = 0;
						alldrops[x][y].getDots()[1][2].setVal(direction);
						alldrops[x][y-1].getDots()[1][0].setVal(oppdirection);
					}
					
					direction= alldrops[x][y].getDots()[1][0].getVal();
					oppdirection = alldrops[x][y+1].getDots()[1][2].getVal(); 
					if (direction > 0) 
					{
						oppdirection += direction; direction = 0;
						alldrops[x][y].getDots()[1][0].setVal(direction);
						alldrops[x][y+1].getDots()[1][2].setVal(oppdirection);
					}
					
					direction= alldrops[x][y].getDots()[2][1].getVal();
					oppdirection = alldrops[x-1][y].getDots()[0][1].getVal(); 
					if (direction > 0) 
					{
						oppdirection += direction; direction = 0;
						alldrops[x][y].getDots()[2][1].setVal(direction);
						alldrops[x-1][y].getDots()[0][1].setVal(oppdirection);
					}
					
					direction= alldrops[x][y].getDots()[0][1].getVal();
					oppdirection = alldrops[x+1][y].getDots()[2][1].getVal(); 
					if (direction > 0) 
					{
						oppdirection += direction; direction = 0;
						alldrops[x][y].getDots()[0][1].setVal(direction);
						alldrops[x+1][y].getDots()[2][1].setVal(oppdirection);
					}
					
					direction= alldrops[x][y].getDots()[0][2].getVal();
					oppdirection = alldrops[x+1][y-1].getDots()[2][0].getVal(); 
					if (direction > 0) 
					{
						oppdirection += direction; direction = 0;
						alldrops[x][y].getDots()[0][2].setVal(direction);
						alldrops[x+1][y-1].getDots()[2][0].setVal(oppdirection);
					}
					
					direction= alldrops[x][y].getDots()[2][2].getVal();
					oppdirection = alldrops[x-1][y-1].getDots()[0][0].getVal(); 
					if (direction > 0) 
					{
						oppdirection += direction; direction = 0;
						alldrops[x][y].getDots()[2][2].setVal(direction);
						alldrops[x-1][y-1].getDots()[0][0].setVal(oppdirection);
					}
					
					direction= alldrops[x][y].getDots()[0][0].getVal();
					oppdirection = alldrops[x+1][y+1].getDots()[2][2].getVal(); 
					if (direction > 0) 
					{
						oppdirection += direction; direction = 0;
						alldrops[x][y].getDots()[0][0].setVal(direction);
						alldrops[x+1][y+1].getDots()[2][2].setVal(oppdirection);
					}
					
					direction= alldrops[x][y].getDots()[2][0].getVal();
					oppdirection = alldrops[x-1][y+1].getDots()[0][2].getVal(); 
					if (direction > 0) 
					{
						oppdirection += direction; direction = 0;
						alldrops[x][y].getDots()[2][0].setVal(direction);
						alldrops[x-1][y+1].getDots()[0][2].setVal(oppdirection);
					}
				}
			}
		}
		
	}

	/**
	 * Display calculated density back to UI
	 */
	private void renderingCanvas()
	{
//		if(Driver.DEBUG)
//		{
//			System.out.println("Size is " + height + ": " + width);
//		}		
		/**
		 * draw to the canvas
		 */
		FluidDefaultPage.getCurrentPage().getDisplay().asyncExec(new Runnable() 
		{
			@Override
			public void run() 
			{
				Canvas currentCanvas = FluidDefaultPage.getCurrentPage().getCanvas();
				GC gc = new GC(currentCanvas);
				int height = currentCanvas.getClientArea().height;
				int width = currentCanvas.getClientArea().width;
				int xdim = UsrDataConfig.getUsrDataConfig().getLength();
				int ydim = UsrDataConfig.getUsrDataConfig().getWidth();
				SingleDrop[][] alldrops = currentFlow.getAllDrops();
				if(Driver.DEBUG)
				{
					System.out.println("Height of canvas is : " + ydim + " Width is: " + xdim);
				}
				double[][] curl = new double[xdim][ydim];
				for (int x=1; x<xdim-1; x++) {
					for (int y=1; y<ydim-1; y++) {
						curl[x][y] = (alldrops[x+1][y].getyVel() - alldrops[x-1][y].getyVel()) - (alldrops[x][y+1].getxVel() - alldrops[x][y-1].getxVel());
					}
				}
				for (int y=1; y<ydim-1; y++) {
					curl[0][y] = 2*(alldrops[1][y].getyVel() - alldrops[0][y].getyVel()) - (alldrops[0][y+1].getxVel() - alldrops[0][y-1].getxVel());
					curl[xdim-1][y] = 2*(alldrops[xdim-1][y].getyVel() - alldrops[xdim-2][y].getyVel()) - (alldrops[xdim-1][y+1].getxVel() - alldrops[xdim-1][y-1].getxVel());
				}
				PaletteData paletteData = new PaletteData(0xff, 0xff00, 0xff0000);
			    float hue = 0; // range is 0-360
			    int nColors = 600;
		    	//Color[] shade = new Color[nColors];
		    	int[] colorInt = new int[nColors];		// colors stored as integers for MemoryImageSource
		    	int blackColorInt = Color.HSBtoRGB((float)0,(float)1,(float)0);		// an integer to represent the color black
		    	{	for (int c=0; c<nColors; c++) {
		    			double h = (2.0/3) * (1 - c*1.0/nColors);	// hue from blue->cyan->green->yellow->red
		    			h += 0.03 * Math.sin(6*Math.PI*h);					// for smoother color gradations
		    			//shade[c] = Color.getHSBColor((float)h,(float)1,(float)1);
		    			colorInt[c] = Color.HSBtoRGB((float)h,(float)1,(float)1);	// store each color as an integer
		    		}
		    	}
			    int pixel;
			    int xOffset = (width -(xdim*2))/2;
				int yOffset = ((height-(ydim*2))/2);
				ImageData md = new ImageData(width, height, 24, paletteData);
				//System.out.println("Height of canvas  : " + height + " Width : " + width);
				int colorIndex;
				int theColor;
				for(int x = 0; x < xdim; x++){
			        for(int y = 0; y < ydim; y++){
			        	//Check barrier and set black if barrier
			        	if(!alldrops[x][y].isEnable()){
			        		pixel = paletteData.getPixel(new RGB(0f, 0f, 0f));
			        		
}
			        	else{//Else do the colour based on density
			        		//hue = (float) ( (220 * ((alldrops[x][y].getDensity()))+0.5));//To plot density
				        	//hue = 200 + (float)alldrops[x][y].getxVel();//To plot x-velocity if needed
			        		//pixel = (int) (0xffb200 * ((alldrops[x][y].getDensity())+1));
			        		//pixel = (int) (0xffb200 * (curl[x][y]+1));
			        		colorIndex = (int) (nColors * (0.5 + (alldrops[x][y].getxVel()) * 25 * 0.2));
			        		
			        		if (colorIndex < 0) colorIndex = 0;
							if (colorIndex >= nColors) colorIndex = nColors - 1;
							pixel = colorInt[colorIndex];
							//pixel = (int) (( *(int)(alldrops[x][y].getxVel()+1)));
			        		//md.setPixel(x, y, pixel);
			        		System.out.println("curl is "+curl[x][y]);
			        	}
			        	md.setPixel(x*2+xOffset, y*2+yOffset, pixel);
		        		md.setPixel(((x*2)+1+xOffset), y*2+yOffset, pixel);
		        		md.setPixel(x*2+xOffset, (y*2)+1+yOffset, pixel);
		        		md.setPixel(((x*2)+1+xOffset), ((y*2)+1+yOffset), pixel);
			        	
			        	//md.setPixel(x, y, pixel);
			        }
			        //hue += 360f / md.width;
			        //hue += 0.03 * Math.sin(6*Math.PI*hue);
			        
			    }
	        	try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    Image image = new Image(FluidDefaultPage.getCurrentPage().getDisplay(), md);
			    gc.drawImage(image, 0, 0);
			    image.dispose();
			}
		});
	}
	
	public void run() {
		this.canvasInit();
		// TODO Auto-generated method stub
		while (isRunning) 
		{
			if (StateController.getCurrentState() == StateController.RUNNING) 
			{
				
				for (int s=0; s<10; s++)
				{
					this.doingCalculation();
					this.renderingCanvas();
				}
				try 
					{Thread.sleep(1);
					} catch (InterruptedException e) {
						
					}
				//repaint();//ys9:to add
			}
			else if(StateController.getCurrentState() == StateController.PAUSE)
			{
				try {Thread.sleep(200);} catch (InterruptedException e) {}
			}
			else if(StateController.getCurrentState() == StateController.TERMINAL)
			{
				isRunning = false;
			}
			//repaint();//ys9:to add// repeated painting when not running uses resources but is handy for graphics adjustments
		}
	
	}

}
