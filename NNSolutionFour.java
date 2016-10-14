import java.util.ArrayList;

public class NNSolutionFour {

	public static void main(String[] args) {
		ScanInput sc = new ScanInput();
		ArrayList<ArrayList<Double>> wbData = new ArrayList<>();
		ArrayList<Double> learningParameters = sc.scan();
		double epoch = learningParameters.get(0);
		double learningFactor = learningParameters.get(1);
		double ratioOfLearningInputs = learningParameters.get(2);

		ArrayList<Double> architecture = sc.scan();
		for (int i = 1; i < architecture.size(); i++) {
			for (int j = 0; j < architecture.get(i); j++) {
				wbData.add(new ArrayList<Double>(sc.scan()));
			}
		}

		NeuronLayerManager nm = new NeuronLayerManager(architecture, wbData);

		double nInputs = sc.scan().get(0);
		double nLearningInputs = Math.floor(nInputs*ratioOfLearningInputs);
		double nValidationInputs = nInputs - nLearningInputs;

		for (int iepoch = 0; iepoch < epoch; iepoch++) {
			//teaching
			
			
			
			for (int i = 0; i < nLearningInputs; i++) {
				ArrayList<Double> InandOut = sc.scan();
				ArrayList<Double> inputs = new ArrayList<Double>(InandOut.subList(0, (int)(architecture.get(0)-0)));
				ArrayList<Double> expectedoutputs = new ArrayList<Double>( InandOut.subList((int)(architecture.get(0)-0), InandOut.size()));
				try {
					ArrayList<Double> outputs = nm.getOutputs(inputs);
					ArrayList<Double> epsilons = new ArrayList<Double>();
					for (int j = 0; j < outputs.size(); j++) {
						epsilons.add(expectedoutputs.get(j)-outputs.get(j));
					}
					nm.getDerivatives(epsilons);
					nm.correctWeights(learningFactor);
					
					System.out.println(architecture);
					nm.printAll();
					System.out.println();

				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
			
			
			//validation





















		}




	}

}
