package prymm.model;

import prymm.gui.FluidDefaultPage;
import prymm.gui.WelcomePage;

/**
 * Program Enterance
 * @author Minghua
 *
 */
public class Driver {

	public static final boolean DEBUG = false;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			/**
			 * When other UI page is applied, simply let their page extend FluidDefaultPage and add open function
			 * which contains all the process of creating UI elements
			 */
			FluidDefaultPage window = new WelcomePage();
			window.setCurrentPage(window);
			window.open();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
