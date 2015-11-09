package homework2;

public class CallTest
{
	public static void main( String[] args )
	{
		int a = 5;

		System.out.println( "a = " + a );

		triple( a );

		System.out.println( "a = " + a );
	}

	public static void triple( int x )
	{
		System.out.println( "x = " + x );
		
		x = x * 3;

		System.out.println( "x = " + x );
	}
}
