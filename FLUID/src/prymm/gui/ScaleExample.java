package prymm.gui;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
 
public class ScaleExample {
  Display display = new Display();
  Shell shell = new Shell(display);
 
  Scale scale;
  Text value;
  public ScaleExample() {
    shell.setLayout(new GridLayout(1, true));
    Label label = new Label(shell, SWT.NULL);
    label.setText("Volume:");
    scale = new Scale(shell, SWT.VERTICAL);
    scale.setBounds(0, 0, 40, 200);
    scale.setMaximum(20);
    scale.setMinimum(0);
    scale.setIncrement(1);
    scale.setPageIncrement(5);
    scale.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event event) {
        int perspectiveValue = scale.getMaximum() - scale.getSelection() + scale.getMinimum();
        value.setText("Vol: " + perspectiveValue);
      }
    });
    value = new Text(shell, SWT.BORDER | SWT.SINGLE);
 
    value.setEditable(false);
    scale.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
    value.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
 
    shell.pack();
    shell.open();
    //textUser.forceFocus();
 
    // Set up the event loop.
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        // If no more entries in event queue
        display.sleep();
      }
    }
 
    display.dispose();
  }
 
  private void init() {
 
  }
 
  public static void main(String[] args) {
    new ScaleExample();
  }
}