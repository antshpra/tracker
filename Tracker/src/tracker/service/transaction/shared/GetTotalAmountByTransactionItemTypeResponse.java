package tracker.service.transaction.shared;

import antshpra.gwt.rpc.shared.Response;

public class GetTotalAmountByTransactionItemTypeResponse extends Response {

	private static final long serialVersionUID = -4515378099656339737L;

	private long amount;

	
	public double getAmount() {
		return ( (double) this.amount ) / 100;
	}

	public void setAmount( double amount ) {
		this.amount = (long) ( amount * 100 );
	}
	
}
