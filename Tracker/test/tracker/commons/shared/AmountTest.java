package tracker.commons.shared;

import org.junit.Assert;
import org.junit.Test;

public class AmountTest {

	@Test
	public void testAdd() {
		long amount_1_value = 100L;
		long amount_2_value = 200L;
		
		Amount amount_1 = new Amount( amount_1_value );
		Amount amount_2 = new Amount( amount_2_value );
		
		Amount totalAmount = amount_1.add( amount_2 );
		Assert.assertEquals( amount_1_value + amount_2_value, totalAmount.getValue() );
	}
	
}
