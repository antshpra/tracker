package antshpra.gae.datasource;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class GAEDataSource {

	private static final PersistenceManagerFactory pmfInstance =
			JDOHelper.getPersistenceManagerFactory( "transactions-optional" );

	protected final PersistenceManager pm;
	
	protected GAEDataSource() {
		this.pm = GAEDataSource.pmfInstance.getPersistenceManager();
	}

	public final void close() {
		this.pm.close();
	}
	
}