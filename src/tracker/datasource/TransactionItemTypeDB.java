package tracker.datasource;

import tracker.commons.shared.Amount;
import tracker.commons.shared.TransactionReportType;
import tracker.datasource.jdo.TransactionItemTypeJDO;

import com.google.appengine.api.datastore.KeyFactory;

public enum TransactionItemTypeDB {

	REGULAR				( 1000, 1000, "Regular",   0, TransactionReportType.PERODIC ),
	REGULAR_BREAKFAST	( 1001, 1000, "Breakfast", 0, TransactionReportType.PERODIC ),
	REGULAR_LUNCH		( 1002, 1000, "Lunch",     0, TransactionReportType.PERODIC ),
	REGULAR_SNACKS		( 1003, 1000, "Snacks",    0, TransactionReportType.PERODIC ),
	REGULAR_DINNER		( 1004, 1000, "Dinner",    0, TransactionReportType.PERODIC ),
	
	HOUSE_HOLD				( 2000, 2000, "House Hold",    0, TransactionReportType.PERODIC ),
	HOUSE_HOLD_GLOSSARY		( 2001, 2000, "Glossary",      0, TransactionReportType.PERODIC ),
	HOUSE_HOLD_SNACKS		( 2002, 2000, "Snacks",        0, TransactionReportType.PERODIC ),
	HOUSE_HOLD_PERSONAL_CARE( 2003, 2000, "Personal Care", 0, TransactionReportType.PERODIC ),
	HOUSE_HOLD_HEALTH_CARE	( 2004, 2000, "Health Care",   0, TransactionReportType.PERODIC ),
	HOUSE_HOLD_KITCHEN		( 2005, 2000, "Kitchen",       0, TransactionReportType.PERODIC ),
	
	LIFE_STYLE			( 3000, 3000, "Life Style", 0, TransactionReportType.PERODIC ),
	LIFE_STYLE_APPARELS	( 3001, 3000, "Apparels",   0, TransactionReportType.PERODIC ),
	LIFE_STYLE_HANGOUTS	( 3002, 3000, "Hangouts",   0, TransactionReportType.PERODIC ),
	LIFE_STYLE_GIFTS	( 3003, 3000, "Gifts",      0, TransactionReportType.PERODIC ),
	LIFE_STYLE_TREATS	( 3004, 3000, "Treats",     0, TransactionReportType.PERODIC ),
	
	BIKE			( 4000, 4000, "Bike",        0, TransactionReportType.PERODIC ),
	BIKE_FUEL		( 4001, 4000, "Fuel",        0, TransactionReportType.PERODIC ),
	BIKE_SERVICE	( 4002, 4000, "Service",     0, TransactionReportType.PERODIC ),
	BIKE_REPAIR		( 4003, 4000, "Repair",      0, TransactionReportType.PERODIC ),
	BIKE_ACCESSORIES( 4004, 4000, "Accessories", 0, TransactionReportType.PERODIC ),
	
	SAVINGS_ACCOUNT			( 6000, 6000, "Savings Account",                 0, TransactionReportType.CUMULATIVE ),
	SAVINGS_ACCOUNT_SBBJ	( 6001, 6000, "Savings Account (SBBJ)",          0, TransactionReportType.CUMULATIVE ),
	SAVINGS_ACCOUNT_IDBI	( 6002, 6000, "Savings Account (IDBI)",   10238.82, TransactionReportType.CUMULATIVE ),
	SAVINGS_ACCOUNT_ICICI	( 6003, 6000, "Savings Account (ICICI)", 205764.05, TransactionReportType.CUMULATIVE ),
	SAVINGS_ACCOUNT_KMB		( 6004, 6000, "Savings Account (KMB)",   181902.00, TransactionReportType.CUMULATIVE ),
	
	FIXED_DEPOSIT		( 7000, 7000, "Fixed Deposit",         0, TransactionReportType.CUMULATIVE ),
	FIXED_DEPOSIT_ICICI	( 7001, 7000, "Fixed Deposit (ICICI)", 0, TransactionReportType.CUMULATIVE ),
	FIXED_DEPOSIT_KMB	( 7002, 7000, "Fixed Deposit (KMB)",   0, TransactionReportType.CUMULATIVE ),

	CREDIT_CARD			( 8000, 8000, "Credit Card",              0, TransactionReportType.CUMULATIVE ),
	CREDIT_CARD_HDFC	( 8001, 8000, "Credit Card (HDFC)",        0, TransactionReportType.CUMULATIVE ),
	CREDIT_CARD_SC		( 8002, 8000, "Credit Card (SC)", -25832.78, TransactionReportType.CUMULATIVE ),
	
	POCKET			( 9000, 9000, "Pocket", 0, TransactionReportType.CUMULATIVE ),
	/*
	 * PENDING EXPENSE REPORTING
	 * 
	 * 	04 APR Shuchi		  	  0.52
	 * 	07 APR Shuchi	 		 20.00
	 * 	14 APR Manish			 -4.00
	 * 	16 APR (Untracked)		 60.00
	 * 	21 APR Polo				  2.00
	 * 	23 APR Naveen			 16.00
	 * 	30 APR (Untracked)		-99.00
	 *  30 APR Booze            740.00
	 * 	01 MAY Prasads Parking	 15.00
	 *  -- --- To Mama,for Cab-2400.00
	 *  -- --- April Rent	   4000.00
	 *  -- --- April Phone Bill 497.77 ( 314.77 + 183.00 ) SC Points
	 *  -- --- May Phone Bill   317.00 ( 317.00 + 61.65) SC Points
	 *  -- --- SC Cashback		180.15 // Shuchi
	 *  -- --- Delhi Trip	   6374.24
	 *                                 = 9719.68
	 *  25 May Bike Air	          2.00
	 *  31 May Bike Air           2.00
	 *                                 = 9723.68
	 *  06 Jun Bang, Day 0       80.00
	 *  07 Jun Bang, Day 1      230.00
	 *  08 Jun Bang, Day 2      811.00
	 *  09 Jun Bang, Day 3      210.00
	 *  10 Jun Bang, Day 4       75.00
	 *                                 = 11129.68
	 *  20 Jun Bike Air           2.00
	 *  -- --- TOTAL		  11131.68
	 */
	
	//  -- --- June Phone Bill  343.60 ( 343.60 ) SC Points
	//  -- --- July Phone Bill  364.72 ( 94.75 + 269.97 ) SC Points + SC Card
	POCKET_CASH		( 9001, 9000, "Cash", ( 13855.55 + 321.10) - 11131.68, TransactionReportType.CUMULATIVE ),
	POCKET_SODEXO	( 9002, 9000, "Sodexo", 0, TransactionReportType.CUMULATIVE ),
	/*
	 * 	30 APR Lunch & Dinner	440.00 # http://www.billsup.com/bill?b=1218215
	 * 	13 MAY Lunch & Dinner	440.00 # http://www.billsup.com/bill?b=1228663
	 */
	POCKET_BILLSUP	( 9003, 9000, "Bills Up", -35975.62, TransactionReportType.CUMULATIVE ), // (6565.57 - 41933.84 - 321.10 + 593.75) - 440 -440
	
	// Initial POCKET_CASH + POCKET_BILLSUP must be equal to  Rs. -20,918.97
	
	SERVICES					( 10000, 10000, "Services",          0, TransactionReportType.PERODIC ),
	SERVICES_HOUSE_RENT			( 10001, 10000, "House Rent",        0, TransactionReportType.PERODIC ),
	SERVICES_HOUSE_MAINTENANCE	( 10002, 10000, "House Maintenance", 0, TransactionReportType.PERODIC ),
	SERVICES_HOUSE_ELECTRICITY	( 10003, 10000, "House Electricity", 0, TransactionReportType.PERODIC ),
	SERVICES_INTERNET			( 10004, 10000, "Internet",          0, TransactionReportType.PERODIC ),
	SERVICES_CELL_PHONE			( 10005, 10000, "Cell Phone",        0, TransactionReportType.PERODIC ),

	AMAZON			( 11000, 11000, "Amazon",          0, TransactionReportType.PERODIC ),
	AMAZON_CASH		( 11001, 11000, "Amazon Salary",   0, TransactionReportType.PERODIC ),
	AMAZON_SODEXO	( 11002, 11000, "Amazon Sodexoes", 0, TransactionReportType.PERODIC ),	

	BULK_PURCHASES	( 12000, 12000, "Bulk Purchases", 0, TransactionReportType.PERODIC ),

	EVENTS								( 13000, 13000, "Events",													   0, TransactionReportType.PERODIC ),
	EVENTS_BIKE_ACCIDENT_04APR14		( 13001, 13000, "Bike Accident near Naveen's Place (04 Apr'14)",			   0, TransactionReportType.PERODIC ),
	EVENTS_DELHI_TRIP_18APR14			( 13002, 13000, "Delhi trip for Shuchi's Marriage (18-21 Apr'14) (Cancelled)", 0, TransactionReportType.PERODIC ),
	EVENTS_BANGLORE_TRIP_06JUN14		( 13003, 13000, "Banglore Trip (07-09 Jun'14)",								   0, TransactionReportType.PERODIC ),
	EVENTS_MAHOBA_RAEBARELI_TRIP_25JUL14( 13004, 13000, "Mahoba-Raebareli Trip (25 Jul - 03 Aug'14)",				   0, TransactionReportType.PERODIC ),
	EVENTS_BANGLORE_TRIP_10JUL14		( 13005, 13000, "Banglore Trip for Pratilipi (11-13 Jul'14)",								   0, TransactionReportType.PERODIC ),
	EVENTS_BHONGIR_FORT_BIKE_TRIP_21JUN14(13006, 13000, "Bike Trip to Bhongir Fort (21 Jun'14)",					   0, TransactionReportType.PERODIC ),
	EVENTS_RELIEVING_AT_AMAZON_23JUL14  ( 13007, 13000, "Relieving at Amazon (23 Jul'14)",					           0, TransactionReportType.PERODIC ),
	EVENTS_MOM_TEETH_TREATMENT_04JUL14  ( 13008, 13000, "Mom's teeth treatment (04 Jul'14)",					       0, TransactionReportType.PERODIC ),
	EVENTS_BANGLORE_TRIP_19JUL14		( 13009, 13000, "Banglore Trip for Pratilipi (20-21 Jul'14)",								   0, TransactionReportType.PERODIC ),

	MISCELLANEOUS	( 14000, 14000, "Miscellaneous", 0, TransactionReportType.PERODIC ),

	HOME_SEND	( 15000, 15000, "Home Send", 0, TransactionReportType.PERODIC ),

	PHYZOK		( 16000, 16000, "Phyzok", 0, TransactionReportType.PERODIC ),

	CLAYMUS		( 17000, 17000, "Claymus", 0, TransactionReportType.PERODIC ),
	
	PRATILIPI	( 18000, 18000, "Pratilipi", 0, TransactionReportType.PERODIC ),
	
	SAVINGS_ACCOUNT_INTEREST	   ( 19000, 19000, "Savings Account Interest",         0, TransactionReportType.CUMULATIVE ),
	SAVINGS_ACCOUNT_INTEREST_SBBJ  ( 19001, 19000, "Savings Account Interest (SBBJ)",  0, TransactionReportType.CUMULATIVE ),
	SAVINGS_ACCOUNT_INTEREST_IDBI  ( 19002, 19000, "Savings Account Interest (IDBI)",  0, TransactionReportType.CUMULATIVE ),
	SAVINGS_ACCOUNT_INTEREST_ICICI ( 19003, 19000, "Savings Account Interest (ICICI)", 0, TransactionReportType.CUMULATIVE ),
	SAVINGS_ACCOUNT_INTEREST_KMB   ( 19004, 19000, "Savings Account Interest (KMB)",   0, TransactionReportType.CUMULATIVE ),

	SAVINGS_ACCOUNT_DEDUCTIONS	     ( 20000, 20000, "Savings Account Deductions",         0, TransactionReportType.CUMULATIVE ),
	SAVINGS_ACCOUNT_DEDUCTIONS_SBBJ  ( 20001, 20000, "Savings Account Deductions (SBBJ)",  0, TransactionReportType.CUMULATIVE ),
	SAVINGS_ACCOUNT_DEDUCTIONS_IDBI  ( 20002, 20000, "Savings Account Deductions (IDBI)",  0, TransactionReportType.CUMULATIVE ),
	SAVINGS_ACCOUNT_DEDUCTIONS_ICICI ( 20003, 20000, "Savings Account Deductions (ICICI)", 0, TransactionReportType.CUMULATIVE ),
	SAVINGS_ACCOUNT_DEDUCTIONS_KMB   ( 20004, 20000, "Savings Account Deductions (KMB)",   0, TransactionReportType.CUMULATIVE ),
	;
	
	private int id;
	private int parentId;
	private String title;
	private double initialAmount;
	private TransactionReportType transactionReportType;
	
	private TransactionItemTypeDB( int id, int parentId, String title, double initialAmount, TransactionReportType transactionReportType ) {
		this.id = id;
		this.parentId = parentId;
		this.title = title;
		this.initialAmount = initialAmount;
		this.transactionReportType = transactionReportType;
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
	
	public TransactionReportType transactionReportType() {
		return this.transactionReportType;
	}
	
	public TransactionItemTypeJDO toTransactionItemTypeJDO() {
		TransactionItemTypeJDO transactionItemType = new TransactionItemTypeJDO();
		transactionItemType.setId( KeyFactory.createKey( TransactionItemTypeJDO.class.getSimpleName(), getId() ) );
		if( getParentId() != getId() )
			transactionItemType.setParentId( KeyFactory.createKeyString( TransactionItemTypeJDO.class.getSimpleName(), getParentId() ) );
		transactionItemType.setTitle( getTitle() );
		transactionItemType.setInitialAmount( new Amount( Math.round( getInitialAmount() * 100 ) ) );
		transactionItemType.setTransactionReportType( this.transactionReportType );
		return transactionItemType;
	}

}
