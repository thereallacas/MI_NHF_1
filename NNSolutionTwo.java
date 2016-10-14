import java.util.ArrayList;
import java.util.StringJoiner;

public class NNSolutionTwo {

	public static void main(String[] args) {

		ArrayList<ArrayList<Double>> wbData = new ArrayList<ArrayList<Double>>();

		ScanInput sc = new ScanInput();
		ArrayList<Double> architecture = sc.scan();
		for (int i = 1; i < architecture.size(); i++) {
			for (int j = 0; j < architecture.get(i); j++) {
				wbData.add(new ArrayList<Double>(sc.scan()));
			}
		}

		double numberOfInputs = sc.scan().get(0);
		System.out.println(numberOfInputs);
		NeuronLayerManager nm = new NeuronLayerManager(architecture, wbData);
		
		for (int i = 0; i < numberOfInputs; i++) {
			try {
				ArrayList<Double> outputs = nm.getOutputs(sc.scan());
				StringJoiner sj = new StringJoiner(",");
				for (Double o : outputs) {
					sj.add(Double.toString(o));
				}
				System.out.println(sj.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


	}

}
