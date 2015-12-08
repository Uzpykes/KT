
public class main {

	private static Encoder e;
	
	public static void main(String args[]) {
		e = new Encoder(10);
		e.m = new int[]{0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1};
		e.Encode();

		int[] distored = ChannelSimulation.simulateNoise(1, e.encoded);
		
		
		int[] r = Decoder.decode(distored, 10);
		for (int i = 0; i < r.length; i++)
			System.out.print(r[i]);
	}
	
}
