package tracker.commons.server;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest {

	@Test
	public void testGetDateFor_1() {
		DateFormat dateFormat = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss z" );

		Date date = DateUtil.getDateFor( 2014, 00, 01 );
		String dateStr = dateFormat.format( date );
		Assert.assertEquals( "01-01-2014 00:00:00 IST", dateStr );
		
		date = DateUtil.getDateFor( 2014, 11, 31 );
		dateStr = dateFormat.format( date );
		Assert.assertEquals( "31-12-2014 00:00:00 IST", dateStr );

		date = DateUtil.getDateFor( 2014, 12, 01 );
		dateStr = dateFormat.format( date );
		Assert.assertEquals( "01-01-2015 00:00:00 IST", dateStr );
	}
	
	@Test
	public void testGetDateFor_2() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss z" );

		Date date = DateUtil.getDateFor( 2014, 00, 01 );
		Assert.assertEquals( formatter.parse( "01-01-2014 00:00:00 IST" ).getTime(), date.getTime() );
		
		date = DateUtil.getDateFor( 2014, 11, 31 );
		Assert.assertEquals( formatter.parse( "31-12-2014 00:00:00 IST" ).getTime(), date.getTime() );

		date = DateUtil.getDateFor( 2014, 12, 01 );
		Assert.assertEquals( formatter.parse( "01-01-2015 00:00:00 IST" ).getTime(), date.getTime() );
	}
	
	@Test
	public void testGetYear() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss z" );
		
		Date date = formatter.parse( "01-01-2014 00:00:00 IST" );
		int year = DateUtil.getYear( date );
		Assert.assertEquals( 2014, year );
		
		date = formatter.parse( "31-12-2014 23:59:59 IST" );
		year = DateUtil.getYear( date );
		Assert.assertEquals( 2014, year );
	}
	
	@Test
	public void testGetMonth() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss z" );
		
		Date date = formatter.parse( "01-01-2014 00:00:00 IST" );
		int month = DateUtil.getMonth( date );
		Assert.assertEquals( 0, month );
		
		date = formatter.parse( "31-12-2014 23:59:59 IST" );
		month = DateUtil.getMonth( date );
		Assert.assertEquals( 11, month );
	}
	
}
