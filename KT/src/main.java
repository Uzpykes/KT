
public class main {

	private static Encoder e;
	
	public static void main(String args[]) {
		e = new Encoder("1001000");
				
		for (int i = 0; i < e.height; i++) {
		    for (int j = 0; j < e.width; j++) {
		        System.out.print(e.matrix[j][i] + " ");
		    }
		    System.out.print("\n");
		}
		
	}
	
}
