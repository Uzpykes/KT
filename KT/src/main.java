import java.util.BitSet;


public class main {

	private static Encoder e;
	
	public static void main(String args[]) {
		
		String s = "Laba diena, žinutė";
		byte[] bytes = s.getBytes();
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes)
		{
		   int val = b;
		   for (int i = 0; i < 8; i++)
		   {
		      binary.append((val & 128) == 0 ? 0 : 1);
		      val <<= 1;
		   }
		}
		  
		
		
		int m = 4;
		e = new Encoder(m);
		int[] m2e = new int[m+1];
		while(binary.length() > 0) {
			for (int i = 0; i < m; i++)
				m2e[i] = Integer.parseInt(binary.substring(i, i+1));
			binary.delete(0, m+1);
			int[] distored = ChannelSimulation.simulateNoise(0, Encoder.Encode(m2e));
			int[] decoded = Decoder.optimizedDecode(distored, m);
			for (int i = 0; i < decoded.length; i++)
				System.out.print(decoded[i] + " ");
			System.out.println();
		}
		


		
	}
	
}
