package enigma.engine.floatingpoint;

public class FPUtils
{
	static String getWholeNumberPortionString(float numberToParse)
	{
		Integer wholeNumber = (int) Math.floor(Math.abs(numberToParse));
		return wholeNumber.toString();
	}
	
	static String getFractionalPortionString(float numberToParse)
	{
		Integer wholeNumber = (int) Math.floor(Math.abs(numberToParse));
		Double fractionalPortion = (double) (Math.abs(numberToParse) - wholeNumber);
		String fracStr = fractionalPortion.toString();
		fracStr = fracStr.substring(0, fracStr.length() > 6 ? 6 : fracStr.length());
		return fracStr;
	}
}
