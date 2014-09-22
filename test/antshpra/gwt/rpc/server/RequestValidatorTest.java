package antshpra.gwt.rpc.server;

import org.junit.Test;

import antshpra.gwt.rpc.shared.InvalidRequestException;
import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

import com.claymus.commons.client.UnexpectedServerException;

public class RequestValidatorTest {

	@Test
	public void testValidate_1() throws InvalidRequestException, UnexpectedServerException {
		@SuppressWarnings("serial")
		Request request = new Request() {
			@SuppressWarnings("unused")
			private String optionalStr;
		};
		
		RequestValidator.validate(request);
	}
	
	@Test( expected = InvalidRequestException.class )
	public void testValidate_2() throws InvalidRequestException, UnexpectedServerException {
		@SuppressWarnings("serial")
		Request request = new Request() {
			@SuppressWarnings("unused")
			private String optionalStr;

			@RequiredField
			private String requiredStr;
		};
		
		RequestValidator.validate(request);
	}
	
	@Test
	public void testValidate_3() throws InvalidRequestException, UnexpectedServerException {
		@SuppressWarnings("serial")
		Request request = new Request() {
			@SuppressWarnings("unused")
			private String optionalStr;

			@RequiredField
			private String requiredStr = "str";
		};
		
		RequestValidator.validate(request);
	}
	
}
