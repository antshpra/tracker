package tracker.service.transactionreport.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tracker.commons.shared.TransactionReportType;
import tracker.commons.shared.YearType;
import tracker.data.access.DataAccessor;
import tracker.data.access.DataAccessorFactory;
import tracker.data.access.gae.TransactionReportEntity;
import tracker.service.shared.data.TransactionItemTypeData;
import tracker.service.transactionreport.shared.data.TransactionReportData;

import com.claymus.commons.client.Amount;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TransactionReportServiceImplTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper( new LocalDatastoreServiceTestConfig() );

	private final String transactionItemTypeId = "transaction-item-type-id";
	private final String transactionItemTypeChildId = "transaction-item-type-child-id";
	private final TransactionItemTypeData transactionItemTypeData = new TransactionItemTypeData();
    private final TransactionItemTypeData transactionItemTypeChildData = new TransactionItemTypeData();
	private final SimpleDateFormat formatter = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss z" );
	
	@Before
    public void setUp() throws ParseException {
        helper.setTimeZone( TimeZone.getTimeZone( "IST" ) );
        helper.setUp();

        transactionItemTypeData.setId( transactionItemTypeId );
        transactionItemTypeData.setInitialAmount( new Amount( 100 ) );

        transactionItemTypeChildData.setId( transactionItemTypeChildId );
        transactionItemTypeChildData.setInitialAmount( new Amount( 100 ) );
        
        transactionItemTypeData.addChild( transactionItemTypeChildData );
        
        DataAccessor transactionDataSource = new DataAccessorFactory().getDataAccessor();
        
		createTransactionReport( "MONTH_2014_01", transactionItemTypeId, TransactionReportType.PERODIC, 11, "01-01-2014 00:00:00 IST", transactionDataSource );
		createTransactionReport( "MONTH_2014_02", transactionItemTypeId, TransactionReportType.PERODIC, 12, "01-01-2014 00:00:00 IST", transactionDataSource );
		
		createTransactionReport( "MONTH_2014_00", transactionItemTypeId, TransactionReportType.CUMULATIVE, 21, "01-01-2014 00:00:00 IST", transactionDataSource );
		createTransactionReport( "MONTH_2014_01", transactionItemTypeId, TransactionReportType.CUMULATIVE, 22, "01-01-2014 00:00:00 IST", transactionDataSource );
		
		createTransactionReport( "MONTH_2014_02", transactionItemTypeChildId, TransactionReportType.CUMULATIVE, 31, "01-01-2014 00:00:00 IST", transactionDataSource );
		
		transactionDataSource.destroy();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }
	
	@Test
	public void testGetYearlyReportData_Perodic() {
		TransactionReportServiceImpl transactionReportService = new TransactionReportServiceImpl();
		DataAccessor transactionDataSource = new DataAccessorFactory().getDataAccessor(); 

		transactionItemTypeData.setTransactionReportType( TransactionReportType.PERODIC );
        transactionItemTypeChildData.setTransactionReportType( TransactionReportType.PERODIC );

		TransactionReportData transactionReportData = transactionReportService.getYearlyReportData(
				2014, YearType.CALENDAR,
				transactionItemTypeData,
				transactionDataSource );
		Assert.assertNotNull( transactionReportData );
		Assert.assertEquals( 23, transactionReportData.getAmount().getValue() );
		Assert.assertNotNull( transactionReportData.getChildren().get( 0 ) );
		Assert.assertEquals( 0, transactionReportData.getChildren().get( 0 ).getAmount().getValue() );
		
		transactionDataSource.destroy();
	}

	@Test
	public void testGetYearlyReportData_Cumulative() {
		TransactionReportServiceImpl transactionReportService = new TransactionReportServiceImpl();
		DataAccessor transactionDataSource = new DataAccessorFactory().getDataAccessor(); 

		transactionItemTypeData.setTransactionReportType( TransactionReportType.CUMULATIVE );
		transactionItemTypeChildData.setTransactionReportType( TransactionReportType.CUMULATIVE );
        
		TransactionReportData transactionReportData = transactionReportService.getYearlyReportData(
				2014, YearType.CALENDAR,
				transactionItemTypeData,
				transactionDataSource );
		Assert.assertNotNull( transactionReportData );
		Assert.assertEquals( 122, transactionReportData.getAmount().getValue() );
		Assert.assertNotNull( transactionReportData.getChildren().get( 0 ) );
		Assert.assertEquals( 131, transactionReportData.getChildren().get( 0 ).getAmount().getValue() );
		
		transactionDataSource.destroy();
	}
	
	
	@Test
	public void testGetMonthlyReportData_Perodic() {
		TransactionReportServiceImpl transactionReportService = new TransactionReportServiceImpl();
		DataAccessor transactionDataSource = new DataAccessorFactory().getDataAccessor(); 

		transactionItemTypeData.setTransactionReportType( TransactionReportType.PERODIC );
        transactionItemTypeChildData.setTransactionReportType( TransactionReportType.PERODIC );

		TransactionReportData transactionReportData = transactionReportService.getMonthlyReportData(
				2014, 1, YearType.CALENDAR,
				transactionItemTypeData,
				transactionDataSource );
		Assert.assertNotNull( transactionReportData );
		Assert.assertEquals( 11, transactionReportData.getAmount().getValue() );
		Assert.assertNotNull( transactionReportData.getChildren().get( 0 ) );
		Assert.assertEquals( 0, transactionReportData.getChildren().get( 0 ).getAmount().getValue() );
		
		transactionDataSource.destroy();
	}

	@Test
	public void testGetMonthlyReportData_Cumulative() {
		TransactionReportServiceImpl transactionReportService = new TransactionReportServiceImpl();
		DataAccessor transactionDataSource = new DataAccessorFactory().getDataAccessor(); 

		transactionItemTypeData.setTransactionReportType( TransactionReportType.CUMULATIVE );
		transactionItemTypeChildData.setTransactionReportType( TransactionReportType.CUMULATIVE );
        
		TransactionReportData transactionReportData = transactionReportService.getMonthlyReportData(
				2014, 1, YearType.CALENDAR,
				transactionItemTypeData,
				transactionDataSource );
		Assert.assertNotNull( transactionReportData );
		Assert.assertEquals( 122, transactionReportData.getAmount().getValue() );
		Assert.assertNotNull( transactionReportData.getChildren().get( 0 ) );
		Assert.assertEquals( 100, transactionReportData.getChildren().get( 0 ).getAmount().getValue() );
		
		transactionDataSource.destroy();
	}

	
	@Test
	public void testGetTransactionReport() {
		TransactionReportServiceImpl transactionReportService = new TransactionReportServiceImpl();
		DataAccessor transactionDataSource = new DataAccessorFactory().getDataAccessor(); 
		
		TransactionReportEntity transactionReport = transactionReportService.getTransactionReport(
				"MONTH_2014_02",
				transactionItemTypeId,
				TransactionReportType.PERODIC,
				transactionDataSource );
		Assert.assertNotNull( transactionReport );
		Assert.assertEquals( 12, transactionReport.getAmount().getValue() );

		transactionReport = transactionReportService.getTransactionReport(
				"MONTH_2014_02",
				transactionItemTypeId,
				TransactionReportType.CUMULATIVE,
				transactionDataSource );
		Assert.assertNotNull( transactionReport );
		Assert.assertEquals( 22, transactionReport.getAmount().getValue() );
		
		transactionDataSource.destroy();
	}
	
	
	private void createTransactionReport(
			String transactionReportIndex,
			String transactionItemTypeId,
			TransactionReportType transactionReportType,
			long amount,
			String lastUpdationDateStr,
			DataAccessor transactionDataSource ) throws ParseException {
		
		TransactionReportEntity transactionReport = new TransactionReportEntity();
		transactionReport.setIndex( transactionReportIndex );
		transactionReport.setTransactionItemTypeId( transactionItemTypeId );
		transactionReport.setType( transactionReportType );
		transactionReport.setAmount( new Amount( amount ) );
		transactionReport.setLastUpdationDate( formatter.parse( lastUpdationDateStr ) );
		transactionDataSource.persistTransactionReport( transactionReport );
	}
	
}

