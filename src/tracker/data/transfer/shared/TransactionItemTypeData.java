package tracker.data.transfer.shared;

import com.claymus.commons.client.Amount;
import com.google.gwt.user.client.rpc.IsSerializable;

public class TransactionItemTypeData implements IsSerializable {

	private String id;

	private String title;

	private Amount initialAmount;

	
	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}
	
	public Amount getInitialAmount() {
		return initialAmount;
	}
	
	public void setInitialAmount( Amount initialAmount ) {
		this.initialAmount = initialAmount;
	}

}
