import java.util.ArrayList;

public class NNSolutionFour {

	private static double calculateAverage(ArrayList<Double> list) {
		Double sum = 0.0;
		if(!list.isEmpty()) {
			for (Double mark : list) {
				sum += mark;
			}
			return sum.doubleValue() / list.size();
		}
		return sum;
	}


	public static void main(String[] args) {
		ScanInput sc = new ScanInput();
		ArrayList<ArrayList<Double>> wbData = new ArrayList<>();
		ArrayList<Double> learningParameters = sc.scan();
		double epoch = learningParameters.get(0);
		double learningFactor = learningParameters.get(1);
		double ratioOfLearningInputs = learningParameters.get(2);


		ArrayList<Double> architecture = sc.scan();
		String arch = sc.theLine;
		for (int i = 1; i < architecture.size(); i++) {
			for (int j = 0; j < architecture.get(i); j++) {
				wbData.add(new ArrayList<Double>(sc.scan()));
			}
		}

		NeuronLayerManager nm = new NeuronLayerManager(architecture, wbData);

		double nInputs = sc.scan().get(0);
		double nLearningInputs = Math.floor(nInputs*ratioOfLearningInputs);
		double nValidationInputs = nInputs - nLearningInputs;
		
		ArrayList<ArrayList<Double>> teachinginput = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> validationinput = new ArrayList<ArrayList<Double>>();
		
		for (int i = 0; i < nLearningInputs; i++) {
			teachinginput.add(sc.scan());
		}
		
		for (int i = 0; i < nValidationInputs; i++) {
			validationinput.add(sc.scan());
		}

		
		for (int iepoch = 0; iepoch < epoch; iepoch++) {
			ArrayList<Double> errorvectors = new ArrayList<Double>();

			//teaching
			for (int i = 0; i < nLearningInputs; i++) {
				ArrayList<Double> InandOut = teachinginput.get(i);
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
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
			//validation
			for (int i = 0; i < nValidationInputs; i++) {
				ArrayList<Double> InandOut = validationinput.get(i);
				ArrayList<Double> inputs = new ArrayList<Double>(InandOut.subList(0, (int)(architecture.get(0)-0)));
				ArrayList<Double> expectedoutputs = new ArrayList<Double>( InandOut.subList((int)(architecture.get(0)-0), InandOut.size()));

				try {
					ArrayList<Double> outputs = nm.getOutputs(inputs);
					ArrayList<Double> epsilons = new ArrayList<Double>();
					ArrayList<Double> errorvector = new ArrayList<Double>();
					for (int j = 0; j < outputs.size(); j++) {
						epsilons.add(expectedoutputs.get(j)-outputs.get(j));
						errorvector.add(Math.pow((expectedoutputs.get(j)-outputs.get(j)),2));
					}

					double averageAsM = 0;
					for (Double error : errorvector) {
						averageAsM+=error;
					}
					averageAsM=averageAsM/outputs.size();
					errorvectors.add(averageAsM);

				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
			//átlagos hiba
			System.out.println(calculateAverage(errorvectors));
		}
		System.out.println(arch);
		nm.printAll();
	}
}
