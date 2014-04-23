package tracker.datasource;

import com.google.appengine.api.datastore.KeyFactory;

import tracker.datasource.jdo.TransactionItemTypeJDO;

public enum TransactionItemTypeDB {

	REGULAR				( 1000, 1000, "Regular", 0F ),
	REGULAR_BREAKFAST	( 1001, 1000, "Breakfast", 0F ),
	REGULAR_LUNCH		( 1002, 1000, "Lunch", 0F ),
	REGULAR_SNACKS		( 1003, 1000, "Snacks", 0F ),
	REGULAR_DINNER		( 1004, 1000, "Dinner", 0F ),
	
	HOUSE_HOLD				( 2000, 2000, "House Hold", 0F ),
	HOUSE_HOLD_GLOSSARY		( 2001, 2000, "Glossary", 0F ),
	HOUSE_HOLD_SNACKS		( 2002, 2000, "Snacks", 0F ),
	HOUSE_HOLD_PERSONAL_CARE( 2003, 2000, "Personal Care", 0F ),
	HOUSE_HOLD_HEALTH_CARE	( 2004, 2000, "Health Care", 0F ),
	
	LIFE_STYLE			( 3000, 3000, "Life Style", 0F ),
	LIFE_STYLE_APPARELS	( 3001, 3000, "Apparels", 0F ),
	LIFE_STYLE_HANGOUTS	( 3002, 3000, "Hangouts", 0F ),
	LIFE_STYLE_GIFTS	( 3003, 3000, "Gifts", 0F ),
	LIFE_STYLE_TREATS	( 3004, 3000, "Treats", 0F ),
	
	BIKE			( 4000, 4000, "Bike", 0F ),
	BIKE_FUEL		( 4001, 4000, "Fuel", 0F ),
	BIKE_SERVICE	( 4002, 4000, "Service", 0F ),
	BIKE_REPAIR		( 4003, 4000, "Repair", 0F ),
	BIKE_ACCESSORIES( 4004, 4000, "Accessories", 0F ),
	
	TRIPS_AND_EVENTS 						( 5000, 5000, "Trips & Events", 0F ),
	TRIPS_AND_EVENTS_EATABLES 				( 5001, 5000, "Eatables", 0F ),
	TRIPS_AND_EVENTS_GIFTS		 			( 5002, 5000, "Gifts", 0F ),
	TRIPS_AND_EVENTS_TRAVEL_AIR 			( 5003, 5000, "Air Travel", 0F ),
	TRIPS_AND_EVENTS_TRAVEL_TRAIN_SLEEPER 	( 5004, 5000, "Train (sleeper) Travel", 0F ),
	TRIPS_AND_EVENTS_TRAVEL_TRAIN_3AC 		( 5005, 5000, "Train (3rd AC) Travel", 0F ),
	TRIPS_AND_EVENTS_TRAVEL_BUS_AC 			( 5006, 5000, "Bus (AC) Travel", 0F ),
	TRIPS_AND_EVENTS_TRAVEL_BUS_NON_AC		( 5007, 5000, "Bus (Non AC) Travel", 0F ),
	TRIPS_AND_EVENTS_TRAVEL_LOCAL_TRANSPORT	( 5008, 5000, "Local Trasport", 0F ),
	
	
	SAVINGS_ACCOUNT			( 6000, 6000, "Savings Account", 0F ),
	SAVINGS_ACCOUNT_SBBJ	( 6001, 6000, "Savings Account (SBBJ)", 0F ),
	SAVINGS_ACCOUNT_IDBI	( 6002, 6000, "Savings Account (IDBI)", 10153.82F ),
	SAVINGS_ACCOUNT_ICICI	( 6003, 6000, "Savings Account (ICICI)", 205764.05F ),
	SAVINGS_ACCOUNT_KMB		( 6004, 6000, "Savings Account (KMB)", 200000.00F ),
	
	FIXED_DEPOSITE		( 7000, 7000, "Fixed Deposite", 0F ),
	FIXED_DEPOSITE_ICICI( 7001, 7000, "Fixed Deposite (ICICI)", 0F ),
	FIXED_DEPOSITE_KMB	( 7002, 7000, "Fixed Deposite (KMB)", 0F ),

	CREDIT_CARD			( 8000, 8000, "Credit Card", 0F ),
	CREDIT_CARD_HDFC	( 8001, 8000, "Credit Card (HDFC", 0F ),
	CREDIT_CARD_SC		( 8002, 8000, "Credit Card (SC)", -25832.78F ),
	;
	
	private int id;
	private int parentId;
	private String title;
	private float initialAmount;
	
	private TransactionItemTypeDB( int id, int parentId, String title, float initialAmount ) {
		this.id = id;
		this.parentId = parentId;
		this.title = title;
		this.initialAmount = initialAmount;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getParentId() {
		return this.parentId;
	}

	public String getTitle() {
		return this.title;
	}

	public float getInitialAmount() {
		return this.initialAmount;
	}
	
	public TransactionItemTypeJDO toTransactionItemTypeJDO() {
		TransactionItemTypeJDO transactionItemType = new TransactionItemTypeJDO();
		transactionItemType.setId( KeyFactory.createKey( TransactionItemTypeJDO.class.getSimpleName(), getId() ) );
		if( getParentId() != getId() )
			transactionItemType.setParentId( KeyFactory.createKeyString( TransactionItemTypeJDO.class.getSimpleName(), getParentId() ) );
		transactionItemType.setTitle( getTitle() );
		transactionItemType.setInitialAmount( getInitialAmount() );
		return transactionItemType;
	}

}
