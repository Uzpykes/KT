
public class Encoder {

	public int[][] matrix;
	private String m;
	
	public Encoder(int width, int height) {
		matrix = new int[width][height];
	}
	
	public Encoder(String message) {
		matrix = new int[(int) Math.pow(2, message.length())][(int) Math.pow(2, message.length()-1)];
	}
	
	public void G() {
		G(0, matrix.length, 0, matrix[0].length);
	}
	
	private void G(int startWidth, int width, int startHeight, int height) {
		
		/*
		 * Matrica gaminam nuo apacios 
		 * 
		 */
		
		
		for (int i = (startWidth + width)/2; i < width; i++) {
			matrix[i][height-1] = 1;
		}
		if (height > 1) {
			G((startWidth + width)/2, width, 0, height-1);
			G(startWidth, (startWidth + width)/2, 0, height-1);
		}
	}
	
	
}
