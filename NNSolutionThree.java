import java.util.ArrayList;
import java.util.StringJoiner;

public class NNSolutionThree {

	public static void main(String[] args) {

		ArrayList<ArrayList<Double>> wbData = new ArrayList<ArrayList<Double>>();


		ScanInput sc = new ScanInput();
		ArrayList<Double> architecture = sc.scan();
		System.out.println(sc.theLine);
		for (int i = 1; i < architecture.size(); i++) {
			for (int j = 0; j < architecture.get(i); j++) {
				wbData.add(new ArrayList<Double>(sc.scan()));
			}
		}

		double numberOfInputs = sc.scan().get(0);
		NeuronLayerManager nm = new NeuronLayerManager(architecture, wbData);
		for (int i = 0; i < numberOfInputs; i++) {
			try {
				nm.getOutputs(sc.scan());
				ArrayList<Double> epsilon = new ArrayList<Double>();
				epsilon.add(1.0);
				ArrayList<ArrayList<Double>> derivatives;
				derivatives = nm.getDerivatives(epsilon);

				for (ArrayList<Double> o : derivatives) {
					StringJoiner sj = new StringJoiner(",");
					for (Double d : o) {
						sj.add(Double.toString(d));
					}
					System.out.println(sj.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}






}
