package io.github.hexarchbook.bluezone.lib.javautils;


public final class StringUtils {

	private StringUtils() { }


	public static boolean isBlank ( String aString ) {
		return ( aString==null || aString.trim().isEmpty() );
	}

	
	public static boolean equalsIgnoreCaseTrimmed ( String aString, String anotherString ) {
		if ( (aString==null) && (anotherString==null) ) {
			return true;
		}
		if ( (aString==null) || (anotherString==null) ) {
			return false;
		}
		return aString.trim().equalsIgnoreCase ( anotherString.trim() );
	}

	
	public static boolean containsWhiteSpaces ( String aString ) {
		return ( (aString!=null) && (aString.indexOf(" ")>=0) );
	}

	public static String leftPad ( String string, int length, char paddingCharacter ) {
		if ( (string==null) || (length <= string.length()) ) {
			return string;
		}
		String paddingString = "";
		for ( int i=1; i <= (length - string.length()); i++ ) {
			paddingString = paddingString + String.valueOf(paddingCharacter);
		}
		return (paddingString + string);
	}

}
