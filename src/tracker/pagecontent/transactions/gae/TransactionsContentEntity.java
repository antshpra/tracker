package tracker.pagecontent.transactions.gae;

import javax.jdo.annotations.PersistenceCapable;

import tracker.pagecontent.transactions.TransactionsContent;

import com.claymus.data.access.gae.PageContentEntity;

@SuppressWarnings("serial")
@PersistenceCapable
public class TransactionsContentEntity extends PageContentEntity implements TransactionsContent { }
