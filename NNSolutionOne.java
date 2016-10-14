import java.util.ArrayList;

public class NNSolutionOne {

	public static void main(String[] args) {
		
		ScanInput sc = new ScanInput();
		ArrayList<Double> architecture = sc.scan();
		
		NeuronLayerManager nm = new NeuronLayerManager(architecture);
		System.out.println(sc.theLine);
		nm.printAll();
		
		

	}

}
