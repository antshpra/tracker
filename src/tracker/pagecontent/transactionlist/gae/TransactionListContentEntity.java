package tracker.pagecontent.transactionlist.gae;

import javax.jdo.annotations.PersistenceCapable;

import tracker.pagecontent.transactionlist.TransactionListContent;

import com.claymus.data.access.gae.PageContentEntity;

@SuppressWarnings("serial")
@PersistenceCapable
public class TransactionListContentEntity extends PageContentEntity
		implements TransactionListContent { }
