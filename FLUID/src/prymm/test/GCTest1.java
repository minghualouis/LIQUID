package prymm.test;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GCTest1 {

  public static void main(String[] a) {

    final Display d = new Display();
    final Shell shell = new Shell(d);

    shell.setSize(250, 200);

    shell.setLayout(new FillLayout());

    // Create a canvas
    Canvas canvas = new Canvas(shell, SWT.NONE);

    canvas.addPaintListener(new PaintListener() {
      public void paintControl(PaintEvent e) {
        Canvas canvas = (Canvas) e.widget;
        int maxX = canvas.getSize().x;
        int maxY = canvas.getSize().y;

        int halfY = (int) maxY / 2;

        e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLACK));
        e.gc.drawLine(0, halfY, maxX, halfY);
        
        for (int i = 0; i < maxX; i++) {
          e.gc.drawPoint(i, getNormalizedSine(i, halfY, maxX));
        }
      }
      int getNormalizedSine(int x, int halfY, int maxX) {
        double piDouble = 2 * Math.PI;
        double factor = piDouble / maxX;
        return (int) (Math.sin(x * factor) * halfY + halfY);
      }      
    });    
    
    
    shell.open();
    while (!shell.isDisposed()) {
      if (!d.readAndDispatch())
        d.sleep();
    }
    d.dispose();
  }
}