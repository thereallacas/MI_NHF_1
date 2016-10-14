import java.util.ArrayList;
/**
 * The Class Neuron.
 */
public class Neuron {
	
	/** The output. */
	double output;
	
	/** The sum. */
	double sum;
	
	/** The bias. */
	double bias;
	
	/** The dbias. */
	double dbias;
	
	/** The delta. */
	double delta;
	
	/** The derivatives of the weights of the input connections. */
	ArrayList<Double> derweights;
	
	/** The weights of the input connections. */
	ArrayList<Double> inweights;
	
	/**
	 * Instantiates a new neuron with the given bias.
	 *
	 * @param bias the bias
	 */
	public Neuron(double bias){
		this.bias=bias;
		output = 0;
		delta = 0;
		sum = 0;
		dbias = 0;
		inweights = new ArrayList<Double>();
		derweights = new ArrayList<Double>();
	}
	
	/**
	 * Instantiates a new neuron with a bias of 0.
	 */
	public Neuron(){
		bias=0;
		output = 0;
		delta = 0;
		dbias = 0;
		inweights = new ArrayList<Double>();
		derweights = new ArrayList<Double>();
	}
	
	/**
	 * Prints the data of the neuron (w0,...wi, bias)
	 */
	public void printData(){
		if (!inweights.isEmpty()){
		for (Double double1 : inweights) {
			System.out.print(double1+",");
		}
		System.out.println(bias);
		}
	}
	
}
