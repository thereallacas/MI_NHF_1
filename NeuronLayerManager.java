import java.util.ArrayList;
import java.util.Random;

/**
 * ///GOD OBJECT CONFIRM???///.
 */

public class NeuronLayerManager {

	/** The neuron layer container. */
	ArrayList<ArrayList<Neuron>> neuronLayerContainer;


	/**
	 * Az architektúra alapján random súlyértékekkel, és 0 bias-al hozza létre a neuronhálózatot.
	 *
	 * @param architecture the architecture
	 */
	public NeuronLayerManager(ArrayList<Double> architecture){

		neuronLayerContainer = new ArrayList<ArrayList<Neuron>>();
		for (int i=0; i < architecture.size(); i++) {
			ArrayList<Neuron> nl = new ArrayList<Neuron>(architecture.size());
			for (int j = 0; j < architecture.get(i); j++) {
				Neuron n = new Neuron();
				if (i>0){
					for (int k = 0; k < neuronLayerContainer.get(i-1).size(); k++)
						n.inweights.add(new Random().nextGaussian()*0.1);
				}
				nl.add(n);
			}
			neuronLayerContainer.add(nl);

		}
	}

	/**
	 * Létrehoz egy megadott architektúrájú neuronhálózatot a megadott súlyok és biasok alapján.
	 *
	 * @param architecture the architecture
	 * @param wbData the weight and the bias data [[w1.,..wi,bias],[w1.,..wi,bias]]
	 */
	public NeuronLayerManager(ArrayList<Double> architecture, ArrayList<ArrayList<Double>> wbData){

		neuronLayerContainer = new ArrayList<ArrayList<Neuron>>();
		int u=0;
		for (int i=0; i < architecture.size(); i++) {
			ArrayList<Neuron> nl = new ArrayList<Neuron>();
			for (int j = 0; j < architecture.get(i); j++) {
				Neuron n;
				if (i==0){
					n = new Neuron();
				}
				else {
					n = new Neuron(wbData.get(u).get(wbData.get(u).size()-1));
					for (int k = 0; k < neuronLayerContainer.get(i-1).size(); k++)
						n.inweights.add(wbData.get(u).get(k));
					u++;
				}
				nl.add(n);
			}
			neuronLayerContainer.add(nl);
		}
	}

	/**
	 * Gets and sets the outputs.
	 *
	 * @param input A bemenet
	 * @return A kimenetek tömbjét, ezek a kimeneti neuronok kimenetei
	 * @throws Exception Amennyiben a bemeneti adatok nem konzisztensek a 0.réteg architektúrájával, kivételt dob
	 */
	ArrayList<Double> getOutputs(ArrayList<Double> input) throws Exception{

		ArrayList<Double> outputs = new ArrayList<Double>();

		if (neuronLayerContainer.get(0).size()!=input.size())
			throw new Exception("nem konzisztens a bemenet az architektúrával!");

		//az elso réteg outputját beállítjuk az inputra (Bad code -> needs comment)
		for (int i = 0; i< input.size(); i++){
			neuronLayerContainer.get(0).get(i).output = input.get(i);
		}

		for (int i = 1; i < neuronLayerContainer.size(); i++){
			for (int j = 0; j < neuronLayerContainer.get(i).size(); j++){
				Neuron n = neuronLayerContainer.get(i).get(j);
				double summa = 0;
				for (int k = 0; k < neuronLayerContainer.get(i-1).size(); k++) {
					double y_prev = neuronLayerContainer.get(i-1).get(k).output;
					double w = n.inweights.get(k);
					summa += y_prev * w;
				}
				summa+=n.bias;
				n.sum = summa;
				if (i!=neuronLayerContainer.size()-1){
					n.output = ReLu.calculateReLu(summa);
				}
				else n.output = summa;
			}
		}
		for (Neuron neuron : neuronLayerContainer.get(neuronLayerContainer.size()-1)) {
			outputs.add(neuron.output);
		}
		return outputs;
	}

	/**
	 * Gets and sets the derivatives.
	 *
	 * @param Epsilons the failures, these will be the deltas of the output neurons.
	 * @return the derivatives in an arraylist of arraylists, for further processing
	 * @throws Exception Kivétel, ha különbözik a megadott Epsilonok száma a kimeneti neuronok számától
	 */
	ArrayList<ArrayList<Double>> getDerivatives(ArrayList<Double> Epsilons) throws Exception{
		ArrayList<ArrayList<Double>> resultArray = new ArrayList<ArrayList<Double>>();
		if (Epsilons.size()!=neuronLayerContainer.get(neuronLayerContainer.size()-1).size())
			throw new Exception("Inkonzisztens az epsilonok száma a kimeneti neuronok számával!");
		//kimeneti neuronok deltáját explicit beállítjuk a megadott Epsilon értékre
		int e = 0;
		for (Neuron n: neuronLayerContainer.get(neuronLayerContainer.size()-1)){
			n.delta = Epsilons.get(e);
			e++;
		}
		//deltak beallitasa
		for (int i = neuronLayerContainer.size()-2; i >=0; i--) {
			for (int j = 0; j < neuronLayerContainer.get(i).size(); j++) {
				Neuron actneuron = neuronLayerContainer.get(i).get(j);
				double deltasum = 0;
				for (int k = 0; k < neuronLayerContainer.get(i+1).size(); k++) {
					double deltanext = neuronLayerContainer.get(i+1).get(k).delta;
					double weightnext = neuronLayerContainer.get(i+1).get(k).inweights.get(j);
					deltasum += deltanext * weightnext;
				}
				actneuron.delta = deltasum * ReLu.calculateDerivativeReLu(actneuron.sum);
			}
		}
		//derivaltak beallitasa
		for (int i = 1; i < neuronLayerContainer.size(); i++) {
			for (int j = 0; j < neuronLayerContainer.get(i).size(); j++) {
				Neuron actneuron = neuronLayerContainer.get(i).get(j);
				for (int k = 0; k < actneuron.inweights.size(); k++) {
					actneuron.derweights.add(actneuron.delta*neuronLayerContainer.get(i-1).get(k).output);
				}
				actneuron.dbias = actneuron.delta;
				ArrayList<Double> weightsAndBias = new ArrayList<Double>(actneuron.derweights);
				weightsAndBias.add(actneuron.dbias);
				resultArray.add(weightsAndBias);
			}
		}
		return resultArray;
	}

	/**
	 * Correct weights using the derivatives and the learning factor
	 *
	 * @param learningFactor the learning factor
	 */
	public void correctWeights(double learningFactor){
		for (int i = 1; i < neuronLayerContainer.size(); i++) {
			for (int j = 0; j < neuronLayerContainer.get(i).size(); j++) {
				ArrayList<Double> newInweights = new ArrayList<Double>();
				Neuron actualneuron = neuronLayerContainer.get(i).get(j);
				for (int k = 0; k < neuronLayerContainer.get(i).get(j).inweights.size(); k++) {
					double oldweight = actualneuron.inweights.get(k);
					double derivativeOfoldweight = actualneuron.derweights.get(k);
					newInweights.add(oldweight + 2 * learningFactor * derivativeOfoldweight);
					actualneuron.bias += (2 * learningFactor * actualneuron.dbias);
				}
				if (actualneuron.inweights.size()!=newInweights.size()){
					System.out.println("gáz van!");
				}
				actualneuron.inweights = newInweights;
			}
		}
	}

	/** Kiírja a hálózat adatait. (Architektúra, súlyok, bias) */
	public void printAll(){
		for (ArrayList<Neuron> arrayList : neuronLayerContainer) {
			for (Neuron neuron : arrayList) {
				neuron.printData();
			}
		}
	}





}
