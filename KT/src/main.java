
public class main {

	private static Encoder e;
	
	public static void main(String args[]) {
		e = new Encoder("101");
		e.G();
		
		
		
		for (int i = 0; i < 4; i++) {
		    for (int j = 0; j < 8; j++) {
		        System.out.print(e.matrix[j][i] + " ");
		    }
		    System.out.print("\n");
		}
		
		/*
		 * 0, 4, 0, 2
		 * 		0, 2, 0, 1
		 * 		2, 4, 0, 1
		 * 		2, 4, 1, 2
		 * 		0, 2, 1, 2
		 * 4, 8, 0, 2
		 * 		4, 6, 0, 2
		 * 		6, 8, 2, 4
		 * 4, 8, 2, 4
		 * 0, 8, 2, 4
		 * 
		 * 
		 * 
		 * 
		 */
	}
	
}
