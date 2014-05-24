package tracker.commons.shared;

import java.io.Serializable;

public class Amount implements Serializable {
	
	private static final long serialVersionUID = 1283793419217594014L;
	
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
		return "Rs. " + ( (double) this.getValue() ) / 100; // TODO: I18n
	}

}

