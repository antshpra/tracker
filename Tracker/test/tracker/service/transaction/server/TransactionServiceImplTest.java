package tracker.service.transaction.server;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tracker.datasource.TransactionDataSource;
import tracker.datasource.TransactionDataSourceFactory;
import tracker.datasource.jdo.TransactionItemJDO;
import tracker.datasource.jdo.TransactionJDO;
import tracker.service.transaction.shared.CreateTransactionItemRequest;
import tracker.service.transaction.shared.CreateTransactionRequest;
import antshpra.gwt.rpc.shared.InvalidRequestException;
import antshpra.gwt.rpc.shared.ServerException;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TransactionServiceImplTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper( new LocalDatastoreServiceTestConfig() );
	
	@Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }
	
	@Test
	public void testCreateTransaction() throws InvalidRequestException, ServerException {
		
		// Transaction Data
		String transactionDescription = "Test Transaction";
		String transactionItem1Description = "Test Transaction Item 1";
		String transactionItem2Description = "Test Transaction Item 2";
		
		
		// Creating CreateTransactionRequest object
		CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest();
		createTransactionRequest.setDescription( transactionDescription );

		CreateTransactionItemRequest createTransactionItemRequest_1 = new CreateTransactionItemRequest();
		CreateTransactionItemRequest createTransactionItemRequest_2 = new CreateTransactionItemRequest();

		createTransactionItemRequest_1.setDescription( transactionItem1Description );
		createTransactionItemRequest_2.setDescription( transactionItem2Description );
		
		createTransactionRequest.addCreateTransactionItemRequest( createTransactionItemRequest_1 );
		createTransactionRequest.addCreateTransactionItemRequest( createTransactionItemRequest_2 );
		
		
		// Invoking createTransaction API
		TransactionServiceImpl transactionService = new TransactionServiceImpl();
		String transactionId = transactionService.createTransaction( createTransactionRequest );
		
		
		// Validating Data
		Assert.assertNotNull( transactionId );
		Assert.assertNotEquals( 0, transactionId.length() );
		
		TransactionDataSource transactionDataSource = new TransactionDataSourceFactory().getTransactionDataSource();

		TransactionJDO transaction = transactionDataSource.getTransaction( transactionId );
		Assert.assertNotNull( transaction );
		Assert.assertEquals( transactionDescription, transaction.getDescription() );
		
		List<TransactionItemJDO> transactionItemList = transaction.getTransactionItemJDOList();
		Assert.assertNotNull( transactionItemList );
		Assert.assertEquals( 2, transactionItemList.size() );

		TransactionItemJDO transactionItem_1 = transactionItemList.get( 0 );
		TransactionItemJDO transactionItem_2 = transactionItemList.get( 1 );

		Assert.assertEquals( transactionItem1Description, transactionItem_1.getDescription() );
		Assert.assertEquals( transactionItem2Description, transactionItem_2.getDescription() );

		
		// Validating parent-child relationship
		Key transactionItem1ParentKey = KeyFactory.stringToKey( transactionItem_1.getId() ).getParent(); 
		Key transactionItem2ParentKey = KeyFactory.stringToKey( transactionItem_2.getId() ).getParent(); 

		Assert.assertEquals( transactionId, KeyFactory.keyToString( transactionItem1ParentKey ) );
		Assert.assertEquals( transactionId, KeyFactory.keyToString( transactionItem2ParentKey ) );

		
		// Closing data source
		transactionDataSource.close();
	}
	
}
