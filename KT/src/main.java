
public class main {

	private static Encoder e;
	
	public static void main(String args[]) {
		e = new Encoder(3);
		e.m = new int[]{0, 1, 1, 0};
		
		for (int i = 0; i < e.matrix.length; i++) {
			for (int j = 0; j < e.matrix[i].length; j++)
				System.out.print(e.matrix[i][j] + "");
			System.out.print("\n");
		}
		
		e.Encode();
				
		for (int i = 0; i < e.encoded.length; i++) {
		   System.out.print(e.encoded[i] + " ");
		}
		
		
	}
	
}
