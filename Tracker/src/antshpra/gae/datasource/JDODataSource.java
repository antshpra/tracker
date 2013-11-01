package antshpra.gae.datasource;

import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

public class JDODataSource {

	private static final PersistenceManagerFactory pmfInstance =
			JDOHelper.getPersistenceManagerFactory( "transactions-optional" );

	private PersistenceManager pm;
	private final List<Query> queryList;
	
	protected JDODataSource() {
		this.pm = JDODataSource.pmfInstance.getPersistenceManager();
		this.queryList = new LinkedList<Query>();
	}

	protected Query newQuery( Class<?> classObject ) {
		Query query = pm.newQuery( classObject );
		queryList.add( query );
		return query;
	}

	protected PersistenceManager getPersistenceManager() {
		return this.pm;
	}
	
	public final void close() {
		for( Query query : queryList )
			query.closeAll();
		this.pm.close();
	}
	
}