package tracker.commons.shared;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.IsSerializable;

public class Amount implements IsSerializable {
	
	private long value;
	
	
	@SuppressWarnings("unused")
	private Amount() {} // Required for Serialization
	
	public Amount( long value ) {
		this.value = value;
	}
	
	public long getValue() { return this.value; }
	

	public void setValue( long value ) { this.value = value; }

	
	public Amount add( Amount amount ) {
		return new Amount( this.value + amount.value );
	}

	@Override
	public String toString() {
		return this.getValue() == 0
				? "-"
				: "Rs. " + NumberFormat.getFormat( "#.00" ).format( ( (double) this.getValue() ) / 100 );
	}

}

