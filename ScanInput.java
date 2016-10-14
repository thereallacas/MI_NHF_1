import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Szkennelő osztály, amely feldolgozza a formattált bemenetet.
 */
public class ScanInput {
	
	/** The input from console. */
	ArrayList<Double> inputFromConsole;
	
	/** The line. */
	String theLine;
	
	/** The sc. */
	Scanner sc;
	
	/**
	 * Instantiates a new scan input.
	 */
	public ScanInput() {
		sc = new Scanner(System.in);
	}
	
	/**
	 * Scan.
	 *
	 * @return the array list of Doubles
	 */
	ArrayList<Double> scan(){
		this.inputFromConsole = new ArrayList<>();
		theLine = sc.next();
		//System.out.println(theLine);
		StringTokenizer st = new StringTokenizer(theLine,",");
		while (st.hasMoreTokens()){
			inputFromConsole.add(Double.parseDouble(st.nextToken()));
		}
		return inputFromConsole;
	}
	
	
	
}
