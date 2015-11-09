package homework2;

public class ArrayTest {
	public static void main( String... args ) {
		int x = 5;
		int y = x;
		y++; // y = y + 1;

		int[] a = { 2, 3, 5, 7, 11, 13 };
  		int a1[] = { 2, 3, 5, 7, 11, 13 };
		 System.out.println(a.equals(a1));
		 System.out.println(a);
		 System.out.println(a1);
		int[] b = a;
		// System.out.println(a.equals(b));
		b[ 3 ] = -50;
		System.out.println( a[ 3 ] );
		System.out.println( b[ 3 ] );
  		a =  new int[] { 17, 19, 23, 29, 31, };
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
		for (int i = 0; i < a.length; i++) {
			System.out.println(b[i]);
		}

	}
}

//   int length = a.length;
// 
//   System.out.println( "length = " + length );
// 
//   for( int i=0; i<length; i++ )
//   {
//    System.out.println( a[ i ] );
//   }