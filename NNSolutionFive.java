import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.StringTokenizer;

public class NNSolutionFive {

	public static void main(String[] args) {

		//a fájl beolvasása és feldolgozása
		try {
			BufferedReader br = new BufferedReader(new FileReader("./src/spambase_train.csv"));
			ArrayList<ArrayList<Double>> inputData = new ArrayList<ArrayList<Double>>();
			String line;
			while ((line = br.readLine()) != null) {
				ArrayList<Double> splittedLine = new ArrayList<Double>();
				StringTokenizer st = new StringTokenizer(line,",");
				while (st.hasMoreTokens()){
					splittedLine.add(Double.parseDouble(st.nextToken()));
				}
				inputData.add(splittedLine);
			}
			//az adat beillesztése a neuronhálózatba
			
			double epoch = 2000;
			double learningFactor = 0.01;
			double ratioOfLearningInputs = 0.8;
			
			//architecture
			ArrayList<Double> architecture =  new ArrayList<Double>(Arrays.asList(new Double[]{57.0,2.0,1.0}));
			StringJoiner sj = new StringJoiner(",");
			for (Double duble : architecture) {
				sj.add(duble.toString());
			}
			String arch = sj.toString();
			
			NeuronLayerManager nm = new NeuronLayerManager(architecture);

			double nInputs = 4500;
			double nLearningInputs = Math.floor(nInputs*ratioOfLearningInputs);
			double nValidationInputs = nInputs - nLearningInputs;
			
			ArrayList<ArrayList<Double>> teachinginput = new ArrayList<ArrayList<Double>>();
			ArrayList<ArrayList<Double>> validationinput = new ArrayList<ArrayList<Double>>();
			
			for (int i = 0; i < nLearningInputs; i++) {
				teachinginput.add(inputData.get(i));
			}
			
			for (int i = (int) nLearningInputs; i < nInputs; i++) {
				validationinput.add(inputData.get(i));
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
				//System.out.println(NNSolutionFour.calculateAverage(errorvectors));
			}
			System.out.println(arch);
			nm.printAll();
			
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
