package tracker.data.access;

public class DataAccessorFactory {

	public static DataAccessor getDataAccessor() {
		return new DataAccessorGaeImpl();
	}
	
}
