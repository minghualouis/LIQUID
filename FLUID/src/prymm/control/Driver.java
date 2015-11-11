package prymm.control;

import prymm.gui.FluidDefaultPage;
import prymm.gui.WelcomePage;

public class Driver {

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
			System.out.println(window == null);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
