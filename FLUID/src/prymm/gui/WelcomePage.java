package prymm.gui;
/**Testing**/
import java.io.File;
import java.sql.Driver;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import prymm.controller.StateController;
import prymm.controller.UsrDataConfig;
import prymm.controller.UsrDataProcessor;

import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Text;


/**
 *  UI design
 * @author Minghua
 *
 */
public class WelcomePage extends FluidDefaultPage{
	
	protected Shell shlFluidDynamicSimulation;
//	private Display display;
	
	private Button runButton, stopButton, resetButton, btnAddPipeEntry, btnAddPipeExit, getLogButton, btnBrowseFile;
	
	private Combo comboFluidType, barrierShape, initialForceDirection, containerSize;
	
	private Scale viscosityScale, tempScale;
	private Text tempText;
	private Text speedText;
	private Text viscoText;
	private Text fileText;

	UsrDataConfig usrcDataConfig = UsrDataConfig.getUsrDataConfig();
	/**
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		initalFlow();
		shlFluidDynamicSimulation.open();
		shlFluidDynamicSimulation.layout();
		while (!shlFluidDynamicSimulation.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		UsrDataProcessor.stopExecution();
	}


	/**
	 * iniital flow, give default value
	 */
	private void initalFlow()
	{

		/**
		 * Should also update these configuration in UI
		 */
		usrcDataConfig = UsrDataConfig.getUsrDataConfig();
		// barrier setting
		usrcDataConfig.setBarrierShape("Rectangular");
		// container size setting
		usrcDataConfig.setContainerSize("325 x 80");
		// pipe entry selected?
		usrcDataConfig.setEntryAdded(false);
		// pipe exit selected?
		usrcDataConfig.setExitAdded(false);
		// fluid type
		String fluidType = "Water";
		usrcDataConfig.setFluidType(fluidType);
		// set user defined viscosity if user 
//		if ("User Defined".equals(fluidType) && viscosityScale.isEnabled()) 
//		{
//			usrcDataConfig.setViscosity(String.valueOf(viscosityScale.getSelection()));
//		}
		usrcDataConfig.setInitialForce("Left");
		//speedScale.getSelection()
		usrcDataConfig.setInitialSpeed("0.1");
		usrcDataConfig.setTemperature("50");
		UsrDataProcessor.initalFlowMain();
	}
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlFluidDynamicSimulation = new Shell();
		shlFluidDynamicSimulation.setMinimumSize(new Point(675, 620));
		shlFluidDynamicSimulation.setSize(670, 492);
		shlFluidDynamicSimulation.setText("Fluid Dynamic Simulation - powered by PRYMM");
		shlFluidDynamicSimulation.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite fluidDisplayComp = new Composite(shlFluidDynamicSimulation, SWT.NONE);
		fluidDisplayComp.setLayout(new FillLayout(SWT.HORIZONTAL));
		fluidDisplayComp.setBounds(0, 0, 800, 320);
		
		canvas = new Canvas(fluidDisplayComp, SWT.NONE);
		canvas.setEnabled(false);
		canvas.setLayout(new FillLayout(SWT.HORIZONTAL));
		canvas.setBackground(display.getSystemColor(SWT.COLOR_LIST_SELECTION));
		
//		canvas.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
		
		Composite userControlComp = new Composite(shlFluidDynamicSimulation, SWT.NONE);
		userControlComp.setLayout(new GridLayout(1, false));
		
		Group grpLogReplay = new Group(userControlComp, SWT.NONE);
		grpLogReplay.setLayout(new GridLayout(5, false));
		GridData gd_grpLogReplay = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpLogReplay.heightHint = 30;
		grpLogReplay.setLayoutData(gd_grpLogReplay);
		grpLogReplay.setText("Log / SIM Replay");
		grpLogReplay.setBounds(0, 0, 70, 82);
		new Label(grpLogReplay, SWT.NONE);
		
		getLogButton = new Button(grpLogReplay, SWT.NONE);
		
		getLogButton.setEnabled(false);
		getLogButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		getLogButton.setText("Get Log");
			
		new Label(grpLogReplay, SWT.NONE);
		
		btnBrowseFile = new Button(grpLogReplay, SWT.NONE);
		btnBrowseFile.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		btnBrowseFile.setText("Browse File For Replay");
				
		fileText = new Text(grpLogReplay, SWT.BORDER);
		fileText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		
		Group grpFluidSettings = new Group(userControlComp, SWT.NONE);
		grpFluidSettings.setLayout(new GridLayout(8, false));
		GridData gd_grpFluidSettings = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_grpFluidSettings.heightHint = 160;
		grpFluidSettings.setLayoutData(gd_grpFluidSettings);
		grpFluidSettings.setText("Fluid Settings");
		grpFluidSettings.setBounds(0, 0, 70, 82);
		
		
		comboFluidType = new Combo(grpFluidSettings, SWT.NONE);

		GridData gd_comboFluidType = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		gd_comboFluidType.widthHint = 137;
		comboFluidType.setLayoutData(gd_comboFluidType);
		comboFluidType.setItems(new String[] {"Water", "Glycerin", "User Defined"});
		comboFluidType.setText("Select Fluid Type");
		new Label(grpFluidSettings, SWT.NONE);
		
		Label lblViscosity = new Label(grpFluidSettings, SWT.NONE);
		lblViscosity.setEnabled(false);
		lblViscosity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
		lblViscosity.setText("Viscosity");
		
		viscoText = new Text(grpFluidSettings, SWT.BORDER);
		viscoText.setEnabled(false);
		viscoText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		viscosityScale = new Scale(grpFluidSettings, SWT.NONE);
		viscosityScale.setMaximum(40);
		viscosityScale.setMinimum(1);
		viscosityScale.setToolTipText("fluid viscosity");
		viscosityScale.setEnabled(false);
		GridData gd_viscosityScale = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_viscosityScale.widthHint = 127;
		viscosityScale.setLayoutData(gd_viscosityScale);
		viscoText.setText(viscosityScale.getSelection() + "");
		
		Label lblTemperature = new Label(grpFluidSettings, SWT.NONE);
		lblTemperature.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblTemperature.setText("Temperature");
		
		tempText = new Text(grpFluidSettings, SWT.BORDER);
		tempText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		tempScale = new Scale(grpFluidSettings, SWT.NONE);
		tempScale.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));

		tempScale.setToolTipText("fluid temperature");
		tempScale.setSelection(50);
		tempText.setText(tempScale.getSelection() + "");
		
		barrierShape = new Combo(grpFluidSettings, SWT.NONE);

		barrierShape.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
		barrierShape.setItems(new String[] {"Rectangular", "Circular"});
		barrierShape.setBounds(0, 0, 91, 23);
		barrierShape.setText("Select Barrier Shape");
		new Label(grpFluidSettings, SWT.NONE);
		new Label(grpFluidSettings, SWT.NONE);
		new Label(grpFluidSettings, SWT.NONE);
		
		initialForceDirection = new Combo(grpFluidSettings, SWT.NONE);

		initialForceDirection.setItems(new String[] {"Left", "Right", "Top", "Bottom"});
		initialForceDirection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
		initialForceDirection.setText("Select Initial Force");
		
		Label lblInitialSpeed = new Label(grpFluidSettings, SWT.NONE);
		lblInitialSpeed.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblInitialSpeed.setText("Initial Speed");
		
		speedText = new Text(grpFluidSettings, SWT.BORDER);
		speedText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Scale speedScale = new Scale(grpFluidSettings, SWT.NONE);
		speedScale.setMaximum(120);
		speedScale.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		speedScale.setToolTipText("fluid initial speed");
		speedText.setText(speedScale.getSelection() + "");
		
		containerSize = new Combo(grpFluidSettings, SWT.NONE);

		containerSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
		containerSize.setItems(new String[] {"325 x 80","200 x 80", "300 x 120"});
		containerSize.setBounds(0, 0, 91, 23);
		containerSize.setText("Select Container Size");
		new Label(grpFluidSettings, SWT.NONE);
		new Label(grpFluidSettings, SWT.NONE);
		new Label(grpFluidSettings, SWT.NONE);
		
		btnAddPipeEntry = new Button(grpFluidSettings, SWT.CHECK);

		btnAddPipeEntry.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1));
		btnAddPipeEntry.setText("Add Pipe Entry");
		
		btnAddPipeExit = new Button(grpFluidSettings, SWT.CHECK);

		btnAddPipeExit.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnAddPipeExit.setText("Add Pipe Exit");
		new Label(grpFluidSettings, SWT.NONE);
		new Label(grpFluidSettings, SWT.NONE);
		

		
		Group grpControlPanel = new Group(userControlComp, SWT.NONE);
		RowLayout rl_grpControlPanel = new RowLayout(SWT.HORIZONTAL);
		rl_grpControlPanel.justify = true;
		grpControlPanel.setLayout(rl_grpControlPanel);
		GridData gd_grpControlPanel = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_grpControlPanel.heightHint = 30;
		grpControlPanel.setLayoutData(gd_grpControlPanel);
		grpControlPanel.setText("Control Panel");
		grpControlPanel.setBounds(0, 0, 70, 82);
		
		runButton = new Button(grpControlPanel, SWT.NONE);
		runButton.setLayoutData(new RowData(100, SWT.DEFAULT));
		runButton.setText("Run");
		
		stopButton = new Button(grpControlPanel, SWT.NONE);
		stopButton.setEnabled(false);
		stopButton.setLayoutData(new RowData(100, SWT.DEFAULT));
		stopButton.setText("Stop");
		
		resetButton = new Button(grpControlPanel, SWT.NONE);

		resetButton.setLayoutData(new RowData(100, SWT.DEFAULT));
		resetButton.setText("Reset");

		
		/**
		 * Event define area
		 */
		
		
		
		/**
		 * Browse file button
		 */
		btnBrowseFile.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				if (btnBrowseFile.isEnabled())
				{
					FileDialog fileChooser = new FileDialog(shlFluidDynamicSimulation, SWT.OpenDocument);
					String resultFile = fileChooser.open();
					if (resultFile != null) 
					{
						isReplay = true;
						replayFileName = resultFile;
						fileText.setText(resultFile);
						MessageBox replayBox = new MessageBox(shlFluidDynamicSimulation, SWT.ICON_INFORMATION);
						replayBox.setMessage("Replay file path is " + resultFile + "\nReplay mode is selected. Configuration of fluid is not allowed");
						replayBox.open();
						grpFluidSettings.setEnabled(false);
						//R:Set replay file path
						UsrDataConfig usrData = UsrDataConfig.getUsrDataConfig();
						usrData.setReplayPath(resultFile);
						UsrDataProcessor.replay();
					}
				}
			}
		});
		
		
		
		/**
		 * Run button
		 * Begin to run the application
		 * 1. get current value configured by user and use UsrDataConfig interface to create user config data object
		 * 2. change the properties of other widgets
		 * 3. change its-self to pause
		 */
		runButton.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				if(prymm.model.Driver.DEBUG)
				{
					System.out.println("WelcomePage---" + runButton.getText());
				}
				
				if(isReplay == false)
				{
					String currentButton = runButton.getText();
					if(currentButton.trim().equals("Run"))
					{
						runButton.setText("Pause");
						if(!stopButton.isEnabled())
						{
							stopButton.setEnabled(true);
						}
						if(resetButton.isEnabled())
						{
							resetButton.setEnabled(false);
						}
						// not allowing user to configure during execution
						grpFluidSettings.setEnabled(false);
						try 
						{
							UsrDataProcessor.processUsrData();
						} 
						catch (Exception e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if (currentButton.trim().equals("Pause")) 
					{
						runButton.setText("Continue");
						// pause current thread
						UsrDataProcessor.pauseExecution();
					}
					
					else if (currentButton.trim().equals("Continue")) 
					{
						runButton.setText("Pause");
						// pause current thread
						UsrDataProcessor.continueExecution();
					}
	
				}
				// for replay
				else
				{
					if (!"".equals(replayFileName)) 
					{
						File replayFile = new File(replayFileName);
						if (replayFile.exists()) 
						{   //R:Initate running while pressing run with selected log file
							try 
							{
								UsrDataProcessor.processUsrData();
							} 
							catch (Exception e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//use log file for replay
						}
					}
				}
			}
		});
		
		/**
		 * reset button event definition
		 */
		resetButton.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				grpFluidSettings.setEnabled(true);
				fileText.setText("");
				initalFlow();
			}
		});
		

		
		/**
		 * Get log button, if system state is STOP and log file is created and exist, this button should be enabled
		 */
		getLogButton.setEnabled(true);
		getLogButton.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				
				if (getLogButton.isEnabled()) 
				{
					FileDialog logFileDialog = new FileDialog(shlFluidDynamicSimulation, SWT.SAVE);
					logFileDialog.setFilterNames(new String[]{"Log Files", "All Files (*.*)"});
					logFileDialog.setFilterExtensions(new String[] {"*.log", "*.*"});
//					logFileDialog.setFilterPath("c:\\");
					logFileDialog.setFileName("FluidLog.log");
					String fullName = logFileDialog.open();
					
					// Output the log accoring to fullName
					
				}
			}
		});
		
		/**
		 * Stop button operation
		 */
		stopButton.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				if(stopButton.isEnabled() )
				{
					UsrDataProcessor.stopExecution();
					if (runButton.isEnabled()) 
					{
						runButton.setEnabled(false);
					}
				}
			}
		});
		
		
		/**
		 * Check temperature according to the type of fluid that user configured
		 */
		tempScale.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				int currentSelectedTemp = tempScale.getSelection();
				tempText.setText(currentSelectedTemp + "");
				
				String fluidType = comboFluidType.getText();
				if ("Water".equals(fluidType)) 
				{
					if (tempScale.getSelection() <= 0 || tempScale.getSelection() > 100) 
					{
						MessageBox messageBox = new MessageBox(shlFluidDynamicSimulation, SWT.ICON_ERROR);
						messageBox.setMessage("Temperature should not be configured below 0 for fluid water");
						int rc = messageBox.open();
						switch (rc) {
						case SWT.OK:
							tempScale.setSelection(50);
							break;

						default:
							tempScale.setSelection(50);
							break;
						}
						
					}
				}
				usrcDataConfig.setTemperature(tempScale.getSelection() + "");
			}
		});
		
		/**
		 * speed scale listener
		 */
		speedScale.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				//0.000-0.120 -> [0,120]
//				double currentSelectedSpeed = speedScale.getSelection() * 1.0 / 1000;
//				speedText.setText(String.format("%.3f", currentSelectedSpeed));
				int currentSelectedSpeed = speedScale.getSelection();
				speedText.setText(currentSelectedSpeed + "");
				usrcDataConfig.setViscosity(speedScale.getSelection() + "");
			}
		});
		

		
		/**
		 * Viscosity Scale
		 */
		viscosityScale.addSelectionListener(new SelectionAdapter() 
		{

			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				//0.005-0.200 -> [5-200] -> [1,40]
				double currentViscosity = viscosityScale.getSelection() * 5.0 / 1000;
				viscoText.setText(String.format("%.3f", currentViscosity));
				usrcDataConfig.setViscosity(viscosityScale.getSelection() + "");
			}	
		});
		
		/**
		 * fluid type selection
		 */
		comboFluidType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if ("User Defined".equals(comboFluidType.getText())) 
				{
					// enable viscosity widgets
					viscosityScale.setEnabled(true);
					viscoText.setEnabled(true);
					usrcDataConfig.setFluidType(comboFluidType.getText());
					
				}
				else 
				{
					// disable viscosity widgets
					viscosityScale.setEnabled(false);
					viscoText.setEnabled(false);
					usrcDataConfig.setFluidType(comboFluidType.getText());
				}
			}
		});
		
		/**
		 * Fluid barrier shape selection
		 */
		barrierShape.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				usrcDataConfig.setBarrierShape(barrierShape.getText());
			}
		});
		
		/**
		 * container size selection
		 */
		containerSize.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				usrcDataConfig.setContainerSize(containerSize.getText());
			}
		});
		
		/**
		 * Initial force direction settings
		 */
		initialForceDirection.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				usrcDataConfig.setInitialForce(initialForceDirection.getText());
			}
		});
		
		/**
		 * Pipe entry settings
		 */
		btnAddPipeEntry.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				if(btnAddPipeEntry.getSelection())
				{
					usrcDataConfig.setEntryAdded(true);
				}
				else
				{
					usrcDataConfig.setEntryAdded(false);
				}
			}
		});
		
		/**
		 * Pipe Exit settings
		 */
		btnAddPipeExit.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				if (btnAddPipeExit.getSelection())
				{
					usrcDataConfig.setExitAdded(true);
				}
				else 
				{
					usrcDataConfig.setExitAdded(false);
				}
			}
		});
		
		
	}
}
