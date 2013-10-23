package antshpra.gwtrpc.client;

import org.junit.Test;

import antshpra.gwt.rpc.server.RequestValidator;
import antshpra.gwt.rpc.shared.InvalidRequestException;
import antshpra.gwt.rpc.shared.Request;
import antshpra.gwt.rpc.shared.RequiredField;

public class RequestValidatorTest {

	@Test
	public void testValidate_1() {
		@SuppressWarnings("serial")
		Request request = new Request() {
			@SuppressWarnings("unused")
			private String optionalStr;
		};
		
		RequestValidator.validate(request);
	}
	
	@Test( expected = InvalidRequestException.class )
	public void testValidate_2() {
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
	public void testValidate_3() {
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
