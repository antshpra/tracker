package tracker.commons.client;

import org.junit.Assert;
import org.junit.Test;

import tracker.commons.shared.TransactionReportUtil;

public class TransactionReportUtilTest {

	@Test
	public void getTransactionReportIndexTest() {
		Assert.assertEquals( "YEAR_2014", TransactionReportUtil.getTransactionReportIndex( 2014 ) );
		
		Assert.assertEquals( "MONTH_2013_11", TransactionReportUtil.getTransactionReportIndex( 2014, -1 ) );
		Assert.assertEquals( "MONTH_2014_06", TransactionReportUtil.getTransactionReportIndex( 2014, 6 ) );
		Assert.assertEquals( "MONTH_2015_00", TransactionReportUtil.getTransactionReportIndex( 2014, 12 ) );
	}

	@Test
	public void getTransactionReportYearTest() {
		Assert.assertEquals( 2014, TransactionReportUtil.getTransactionReportYear( "YEAR_2014" ) );
		Assert.assertEquals( 2014, TransactionReportUtil.getTransactionReportYear( "MONTH_2014_00" ) );
	}
    
	@Test
	public void getTransactionReportMonthTest() {
		Assert.assertEquals( 0, TransactionReportUtil.getTransactionReportMonth( "MONTH_2014_00" ) );
		Assert.assertEquals( 5, TransactionReportUtil.getTransactionReportMonth( "MONTH_2014_05" ) );
		Assert.assertEquals( 11, TransactionReportUtil.getTransactionReportMonth( "MONTH_2014_11" ) );
	}

}
