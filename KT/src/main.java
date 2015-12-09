
public class main {

	private static Encoder e;
	
	public static void main(String args[]) {
		int m = 12;
		e = new Encoder(m);
		e.m = new int[]{1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0};
		e.Encode();

		int[] distored = ChannelSimulation.simulateNoise(0, e.encoded);
		
		int[] decoded = Decoder.optimizedDecode(distored, m);
		for (int i = 0; i < decoded.length; i++)
			System.out.print(decoded[i] + " ");
		System.out.println();
		int[] decodedNotOptimal = Decoder.decode(distored, m);
		for (int i = 0; i < decodedNotOptimal.length; i++)
			System.out.print(decodedNotOptimal[i] + " ");
		System.out.println();
	
		
	}
	
}
