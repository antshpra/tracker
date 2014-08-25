package tracker.module.pagecontent.transactionlist.gae;

import javax.jdo.annotations.PersistenceCapable;

import tracker.module.pagecontent.transactionlist.TransactionListContent;

import com.claymus.data.access.gae.PageContentEntity;

@PersistenceCapable
public class TransactionListContentEntity extends PageContentEntity
		implements TransactionListContent { }
