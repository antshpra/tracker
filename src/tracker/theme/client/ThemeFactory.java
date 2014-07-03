package tracker.theme.client;

import tracker.theme.clean.client.CleanTheme;

import com.google.gwt.core.client.GWT;

public class ThemeFactory {

	private static final Theme themeInstance = GWT.create( CleanTheme.class );

	private ThemeFactory() {}
	
	public static Theme getTheme() {
		return themeInstance;
	}

}
