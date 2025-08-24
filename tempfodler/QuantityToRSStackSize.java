package demo; import gov.nasa.jpf.symbc.Debug;public class QuantityToRSStackSize {
public static String quantityToRSStackSize(int quantity)
	{
		if (quantity == Integer.MIN_VALUE)
		{
			// Integer.MIN_VALUE = Integer.MIN_VALUE * -1 so we need to correct for it.
			return "-" + quantityToRSStackSize(Integer.MAX_VALUE);
		}
		else if (quantity < 0)
		{
			return "-" + quantityToRSStackSize(-quantity);
		}
		else if (quantity < 100_000)
		{
			return Integer.toString(quantity);
		}
		else if (quantity < 10_000_000)
		{
			return quantity / 1_000 + "K";
		}
		else
		{
			return quantity / 1_000_000 + "M";
		}
	}
public static void main(String[] args){
int x0 = Debug.makeSymbolicInteger("x0");
quantityToRSStackSize(x0);
}}