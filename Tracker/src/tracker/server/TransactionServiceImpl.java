package tracker.server;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import tracker.PMF;
import tracker.client.TransactionService;
import tracker.server.jdo.Transaction;
import tracker.server.jdo.TransactionItem;
import tracker.shared.CreateTransactionItemRequest;
import tracker.shared.CreateTransactionRequest;
import tracker.shared.TransactionDetail;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TransactionServiceImpl extends RemoteServiceServlet implements TransactionService {

	public long createTransaction(CreateTransactionRequest request) throws IllegalArgumentException {

		request.validate();

		Transaction transaction = new Transaction();
		transaction.setDescription(request.getDescription());
		transaction.setTransactionDate(request.getTransactionDate());
		transaction.setCreationDate(new Date());
		transaction.setCreatedBy("prashant@claymus.com");

		transaction = PMF.get().makePersistent(transaction);

		return transaction.getId();
	}

	public long createTransactionItem(CreateTransactionItemRequest request) throws IllegalArgumentException {

		request.validate();

		TransactionItem transactionItem = new TransactionItem();
		transactionItem.setDescription(request.getDescription());
		transactionItem.setCreationDate(new Date());
		transactionItem.setCreatedBy("prashant@claymus.com");

		transactionItem = PMF.get().makePersistent(transactionItem);

		return transactionItem.getId();
	}

	@SuppressWarnings("unchecked")
	public List<TransactionDetail> getTransactions(Date olderThan, int count) {

		List<TransactionDetail> transactionDtailList = new LinkedList<TransactionDetail>();
		Map<Long, TransactionDetail> transactionDetailMap = new HashMap<Long, TransactionDetail>();

		PersistenceManager pm = PMF.get();

		Query query = pm.newQuery(Transaction.class);
		query.setFilter("creationDate < olderThan");
		query.declareParameters(Date.class.getName() + " olderThan");
		query.setOrdering("creationDate DESC");
		query.setRange(0, count);

		List<Transaction> transactionList = (List<Transaction>) query.execute(olderThan);

		for (Transaction transaction : transactionList) {
			TransactionDetail transactionDetail = new TransactionDetail();

			transactionDetail.setId(transaction.getId());
			transactionDetail.setDescription(transaction.getDescription());
			transactionDetail.setCreationDate(new Date(transaction.getCreationDate().getTime()));
			transactionDetail.setCreatedBy(transaction.getCreatedBy());

			transactionDtailList.add(transactionDetail);
			transactionDetailMap.put(transactionDetail.getId(),transactionDetail);
		}

		query.closeAll();

		pm.close();

		return transactionDtailList;
	}

}
