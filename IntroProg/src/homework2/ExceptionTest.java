package homework2;


import javax.swing.JOptionPane;

import java.io.EOFException;

public class ExceptionTest
{
	public static void main( String[] args )
	{
		String s = "12345";

		int i = Integer.parseInt( s );

		
		try
		{
			doStuff();
		}
		catch( EOFException ex )
		{
			System.out.println( "" );
		}

	}

	public static void doStuff() throws EOFException
	{
		// read file
		throw new EOFException( "the end of the file has been reached" );
	}
}