package prymm.gui;
/**Testing**/
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
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
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Slider;

public class WelcomePage {

	protected Shell shlFluidDynamicSimulation;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			WelcomePage window = new WelcomePage();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlFluidDynamicSimulation.open();
		shlFluidDynamicSimulation.layout();
		while (!shlFluidDynamicSimulation.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
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
		
		Composite userControlComp = new Composite(shlFluidDynamicSimulation, SWT.NONE);
		userControlComp.setLayout(new GridLayout(1, false));
		
		Group grpControlPanel = new Group(userControlComp, SWT.NONE);
		RowLayout rl_grpControlPanel = new RowLayout(SWT.HORIZONTAL);
		rl_grpControlPanel.justify = true;
		grpControlPanel.setLayout(rl_grpControlPanel);
		GridData gd_grpControlPanel = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_grpControlPanel.heightHint = 30;
		grpControlPanel.setLayoutData(gd_grpControlPanel);
		grpControlPanel.setText("Control Panel");
		grpControlPanel.setBounds(0, 0, 70, 82);
		
		Button btnNewButton = new Button(grpControlPanel, SWT.NONE);
		btnNewButton.setLayoutData(new RowData(100, SWT.DEFAULT));
		btnNewButton.setText("Run");
		
		Button btnNewButton_1 = new Button(grpControlPanel, SWT.NONE);
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setLayoutData(new RowData(100, SWT.DEFAULT));
		btnNewButton_1.setText("Stop");
		
		Button btnNewButton_2 = new Button(grpControlPanel, SWT.NONE);
		btnNewButton_2.setLayoutData(new RowData(100, SWT.DEFAULT));
		btnNewButton_2.setText("Reset");
		
		Group grpFluidSettings = new Group(userControlComp, SWT.NONE);
		grpFluidSettings.setLayout(new GridLayout(6, false));
		GridData gd_grpFluidSettings = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_grpFluidSettings.heightHint = 160;
		grpFluidSettings.setLayoutData(gd_grpFluidSettings);
		grpFluidSettings.setText("Fluid Settings");
		grpFluidSettings.setBounds(0, 0, 70, 82);
		
		Combo comboFluidType = new Combo(grpFluidSettings, SWT.NONE);
		GridData gd_comboFluidType = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
		gd_comboFluidType.widthHint = 137;
		comboFluidType.setLayoutData(gd_comboFluidType);
		comboFluidType.setItems(new String[] {"Water", "Glycerin", "User Defined"});
		comboFluidType.setText("Fluid Type");
		new Label(grpFluidSettings, SWT.NONE);
		
		Label lblViscosity = new Label(grpFluidSettings, SWT.NONE);
		lblViscosity.setEnabled(false);
		lblViscosity.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1));
		lblViscosity.setText("Viscosity");
		
		Scale scale = new Scale(grpFluidSettings, SWT.NONE);
		scale.setEnabled(false);
		GridData gd_scale = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_scale.widthHint = 127;
		scale.setLayoutData(gd_scale);
		
		Label lblTemperature = new Label(grpFluidSettings, SWT.NONE);
		lblTemperature.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTemperature.setText("Temperature");
		
		Scale scale_1 = new Scale(grpFluidSettings, SWT.NONE);
		scale_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Combo combo = new Combo(grpFluidSettings, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
		combo.setItems(new String[] {"Rectangular", "Circular"});
		combo.setBounds(0, 0, 91, 23);
		combo.setText("Barrier Shape");
		new Label(grpFluidSettings, SWT.NONE);
		new Label(grpFluidSettings, SWT.NONE);
		
		Combo combo_2 = new Combo(grpFluidSettings, SWT.NONE);
		combo_2.setItems(new String[] {"Right", "Top", "Bottom"});
		combo_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		combo_2.setText("Initial Force");
		
		Label lblInitialSpeed = new Label(grpFluidSettings, SWT.NONE);
		lblInitialSpeed.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblInitialSpeed.setText("Initial Speed");
		
		Scale scale_2 = new Scale(grpFluidSettings, SWT.NONE);
		
		Combo combo_1 = new Combo(grpFluidSettings, SWT.NONE);
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
		combo_1.setItems(new String[] {"200 x 80", "300 x 120", "600 x 240"});
		combo_1.setBounds(0, 0, 91, 23);
		combo_1.setText("Container Size");
		new Label(grpFluidSettings, SWT.NONE);
		new Label(grpFluidSettings, SWT.NONE);
		
		Button btnAddPipeEntry = new Button(grpFluidSettings, SWT.CHECK);
		btnAddPipeEntry.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1));
		btnAddPipeEntry.setText("Add Pipe Entry");
		
		Button btnAddPipeExit = new Button(grpFluidSettings, SWT.CHECK);
		btnAddPipeExit.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnAddPipeExit.setText("Add Pipe Exit");
		new Label(grpFluidSettings, SWT.NONE);
		
		Group grpLogReplay = new Group(userControlComp, SWT.NONE);
		grpLogReplay.setLayout(new GridLayout(6, false));
		GridData gd_grpLogReplay = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpLogReplay.heightHint = 30;
		grpLogReplay.setLayoutData(gd_grpLogReplay);
		grpLogReplay.setText("Log / SIM Replay");
		grpLogReplay.setBounds(0, 0, 70, 82);
		new Label(grpLogReplay, SWT.NONE);
		new Label(grpLogReplay, SWT.NONE);
		new Label(grpLogReplay, SWT.NONE);
		
		Button btnNewButton_3 = new Button(grpLogReplay, SWT.NONE);
		btnNewButton_3.setEnabled(false);
		btnNewButton_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		btnNewButton_3.setText("Get Log");
		
		Button btnReplay = new Button(grpLogReplay, SWT.CHECK);
		btnReplay.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		btnReplay.setText("Replay?");
		
		Button btnBrowseFile = new Button(grpLogReplay, SWT.NONE);
		btnBrowseFile.setEnabled(false);
		btnBrowseFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnBrowseFile.setText("Browse File");

	}
}
