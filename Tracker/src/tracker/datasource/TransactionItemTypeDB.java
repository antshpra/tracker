package tracker.datasource;

import com.google.appengine.api.datastore.KeyFactory;

import tracker.datasource.jdo.TransactionItemTypeJDO;

public enum TransactionItemTypeDB {

	REGULAR				( 1000, 1000, "Regular", 0 ),
	REGULAR_BREAKFAST	( 1001, 1000, "Breakfast", 0 ),
	REGULAR_LUNCH		( 1002, 1000, "Lunch", 0 ),
	REGULAR_SNACKS		( 1003, 1000, "Snacks", 0 ),
	REGULAR_DINNER		( 1004, 1000, "Dinner", 0 ),
	
	HOUSE_HOLD				( 2000, 2000, "House Hold", 0 ),
	HOUSE_HOLD_GLOSSARY		( 2001, 2000, "Glossary", 0 ),
	HOUSE_HOLD_SNACKS		( 2002, 2000, "Snacks", 0 ),
	HOUSE_HOLD_PERSONAL_CARE( 2003, 2000, "Personal Care", 0 ),
	HOUSE_HOLD_HEALTH_CARE	( 2004, 2000, "Health Care", 0 ),
	
	LIFE_STYLE			( 3000, 3000, "Life Style", 0 ),
	LIFE_STYLE_APPARELS	( 3001, 3000, "Apparels", 0 ),
	LIFE_STYLE_HANGOUTS	( 3002, 3000, "Hangouts", 0 ),
	LIFE_STYLE_GIFTS	( 3003, 3000, "Gifts", 0 ),
	LIFE_STYLE_TREATS	( 3004, 3000, "Treats", 0 ),
	
	BIKE			( 4000, 4000, "Bike", 0 ),
	BIKE_FUEL		( 4001, 4000, "Fuel", 0 ),
	BIKE_SERVICE	( 4002, 4000, "Service", 0 ),
	BIKE_REPAIR		( 4003, 4000, "Repair", 0 ),
	BIKE_ACCESSORIES( 4004, 4000, "Accessories", 0 ),
	
	TRIPS_AND_EVENTS 						( 5000, 5000, "Trips & Events", 0 ),
	TRIPS_AND_EVENTS_EATABLES 				( 5001, 5000, "Eatables", 0 ),
	TRIPS_AND_EVENTS_GIFTS		 			( 5002, 5000, "Gifts", 0 ),
	TRIPS_AND_EVENTS_TRAVEL_AIR 			( 5003, 5000, "Air Travel", 0 ),
	TRIPS_AND_EVENTS_TRAVEL_TRAIN_SLEEPER 	( 5004, 5000, "Train (sleeper) Travel", 0 ),
	TRIPS_AND_EVENTS_TRAVEL_TRAIN_3AC 		( 5005, 5000, "Train (3rd AC) Travel", 0 ),
	TRIPS_AND_EVENTS_TRAVEL_BUS_AC 			( 5006, 5000, "Bus (AC) Travel", 0 ),
	TRIPS_AND_EVENTS_TRAVEL_BUS_NON_AC		( 5007, 5000, "Bus (Non AC) Travel", 0 ),
	TRIPS_AND_EVENTS_TRAVEL_LOCAL_TRANSPORT	( 5008, 5000, "Local Trasport", 0 ),
	
	
	SAVINGS_ACCOUNT			( 6000, 6000, "Savings Account", 0 ),
	SAVINGS_ACCOUNT_SBBJ	( 6001, 6000, "Savings Account (SBBJ)", 0 ),
	SAVINGS_ACCOUNT_IDBI	( 6002, 6000, "Savings Account (IDBI)", 10153.82 ),
	SAVINGS_ACCOUNT_ICICI	( 6003, 6000, "Savings Account (ICICI)", 205764.05 ),
	SAVINGS_ACCOUNT_KMB		( 6004, 6000, "Savings Account (KMB)", 200000.00 ),
	
	FIXED_DEPOSIT		( 7000, 7000, "Fixed Deposit", 0 ),
	FIXED_DEPOSIT_ICICI( 7001, 7000, "Fixed Deposit (ICICI)", 0 ),
	FIXED_DEPOSIT_KMB	( 7002, 7000, "Fixed Deposit (KMB)", 0 ),

	CREDIT_CARD			( 8000, 8000, "Credit Card", 0 ),
	CREDIT_CARD_HDFC	( 8001, 8000, "Credit Card (HDFC", 0 ),
	CREDIT_CARD_SC		( 8002, 8000, "Credit Card (SC)", -25832.78 ),
	;
	
	private int id;
	private int parentId;
	private String title;
	private double initialAmount;
	
	private TransactionItemTypeDB( int id, int parentId, String title, double initialAmount ) {
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

	public double getInitialAmount() {
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
