package prymm.control;

import org.eclipse.swt.widgets.Canvas;

import prymm.databean.Flow;
import prymm.databean.SingleDrop;
import prymm.gui.FluidDefaultPage;

/**
 * Class rendering machine is used for calculation of all the single drops of flow in the application
 * @author Minghua
 *
 */
public class RenderingMachine implements Runnable{

	Flow currentFlow = null;
	double four9ths = 4.0 / 9;
	double one9th = 1.0 / 9;
	double one36th = 1.0 / 36;
	
	public RenderingMachine(Flow initialFlow) {
		// TODO Auto-generated constructor stub
		currentFlow = initialFlow;
	}

	/**
	 * calculate all the drops in canvas
	 */
	private void doingCalculation() {
		// For fluid calculation
		double viscosity = currentFlow.getFlowType().getViscosity();
		//SingleDrop[][] alldrops = currentFlow.getAllDrops();
		double[][] density = new double[alldrops.length][alldrops[0].length]; // storing result
		
		// ...used for calculation
		int time = 0;
		int stepTime = 0;
		int collideTime = 0;
		int streamTime = 0;
		int paintTime = 0;
		long startTime = System.currentTimeMillis();
		//force();
		long forceTime = System.currentTimeMillis();
		collision();
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
		renderingCanvas(density);
	}
	void collision() {
		double n, one9thn, one36thn, vx, vy, vx2, vy2, vx3, vy3, vxvy2, v2, v215;
		double viscosity = Double.valueOf(UsrDataConfig.getUsrDataConfig().getViscosity()).doubleValue();
		double xdim = Double.valueOf(UsrDataConfig.getUsrDataConfig().getLength()).doubleValue();
		double ydim = Double.valueOf(UsrDataConfig.getUsrDataConfig().getWidth()).doubleValue();
		SingleDrop[][] alldrops = currentFlow.getAllDrops();
		
		double omega = 1 / (3*viscosity+ 0.5);// reciprocal of tau, the relaxation time
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
					//speed2[x][y] = v2;	//ys9:not needed for us	// may be needed for plotting
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
	void stream() {
		int xdim = Integer.valueOf(UsrDataConfig.getUsrDataConfig().getLength()).intValue();
		int ydim = Integer.valueOf(UsrDataConfig.getUsrDataConfig().getWidth()).intValue();
		SingleDrop[][] alldrops = currentFlow.getAllDrops();
		for (int x=0; x<xdim-1; x++) {		// first start in NW corner...
			for (int y=ydim-1; y>0; y--) {
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
		double v = Double.valueOf(UsrDataConfig.getUsrDataConfig().getInitialSpeed());
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
	
	// Bounce particles off of barriers:
	// (The ifs are needed to prevent array index out of bounds errors. Could handle edges
	//  separately to avoid this.)
	void bounce() {
		double xdim = Double.valueOf(UsrDataConfig.getUsrDataConfig().getLength()).doubleValue();
		double ydim = Double.valueOf(UsrDataConfig.getUsrDataConfig().getWidth()).doubleValue();
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
	private void renderingCanvas(double[][] density)
	{
		// canvas which need for rendering result to UI
		Canvas currentCanvas = FluidDefaultPage.getCurrentPage().getCanvas();

	}
	
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (StateController.getCurrentState() == StateController.RUNNING) {
				for (int s=0; s<10; s++) this.doingCalculation();
				try {Thread.sleep(1);} catch (InterruptedException e) {}
				//repaint();//ys9:to add
			} else {
				try {Thread.sleep(200);} catch (InterruptedException e) {}
			}
			//repaint();//ys9:to add// repeated painting when not running uses resources but is handy for graphics adjustments
		}
	
	}
	
	/**
	 * Cherry picking test
	 * feature1
	 */
	
	public void feature1()
	{
		System.out.println("This is a test for cherry picking - feature1 ");
	}

	/**
	 * Cherry picking test
	 * bug fix
	 */
	public void bugfix()
	{
		System.out.println("This is a test for cherry picking - bugfix ");
	}
}
