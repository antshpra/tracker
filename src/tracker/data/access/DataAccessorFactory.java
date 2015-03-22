package tracker.data.access;

import javax.servlet.http.HttpServletRequest;

import com.claymus.data.access.MemcacheClaymusImpl;

public class DataAccessorFactory
		extends com.claymus.data.access.DataAccessorFactory {

	@Deprecated
	public static DataAccessor getDataAccessor() {
		return new DataAccessorGaeImpl();
	}
	
	public static DataAccessor getDataAccessor( HttpServletRequest request ) {
		DataAccessor dataAccessor = cacheL1.get( "TrackerDataAccessor-" + request.hashCode() );
		if( dataAccessor == null ) {
			dataAccessor = new DataAccessorGaeImpl();
			dataAccessor = new DataAccessorWithMemcache( dataAccessor, cacheL2 );
			dataAccessor = new DataAccessorWithMemcache( dataAccessor, new MemcacheClaymusImpl() );
			cacheL1.put( "TrackerDataAccessor-" + request.hashCode(), dataAccessor );
		}
		return dataAccessor;
	}
	
}
