package prymm.test;

import org.junit.Test;

import prymm.control.UsrDataConfig;
import prymm.control.UsrDataProcessor;

public class InitialTest {

	@Test
	public void testInitial()
	{
		// initial the data configuration
		UsrDataConfig uc = UsrDataConfig.getUsrDataConfig();
		uc.setBarrierShape("Rectangle");
		uc.setContainerSize("200 x 80");
		uc.setEntryAdded(false);
		uc.setExitAdded(false);
		uc.setFluidType("Water");
		uc.setInitialForce("Left");
		uc.setTemperature("50");
		uc.setInitialSpeed("0.1");
		// start application
		UsrDataProcessor.processUsrData();
	}
}
