
public class main {

	private static Encoder e;
	
	public static void main(String args[]) {
		e = new Encoder(3);
		e.m = new int[]{0, 1, 1, 0};
		e.Encode();

		int[] distored = ChannelSimulation.simulateNoise(0.5, e.encoded);
		
		
		int[][] k = MatrixUtilities.hadamardMatrix(1, 3);
		
		for (int i = 0; i < k.length; i++) {
			for (int j = 0; j < k[i].length; j++)
				System.out.print(k[i][j] + " ");
			System.out.print("\n");
		}
	}
	
}
