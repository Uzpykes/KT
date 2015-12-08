


public class Encoder {

	public int[][] matrix;
	public int[] m;
	public int[] encoded;
	public int height;
	public int width;
	
	
	public Encoder(int m) {
		try {
		matrix = new int[m+1][(int) Math.pow(2, m)];
		} catch (OutOfMemoryError e) {
			System.err.println("Neuztenka atminties generacijos matricai sukurti.");
			throw e;
		}
		G();
		this.height = matrix[0].length;
		this.width = matrix.length;
		encoded = new int[matrix[0].length];
	}
	

	public void G() {
		G(0, matrix[0].length, 0, matrix.length);
	}
	
	private void G(int startWidth, int width, int startHeight, int height) {
		
		/*
		 * Matrica gaminam nuo apacios 
		 * 
		 */

		for (int i = (startWidth + width)/2; i < width; i++) {
			matrix[height-1][i] = 1;
		}
		if (height > 1) {
			G((startWidth + width)/2, width, 0, height-1);
			G(startWidth, (startWidth + width)/2, 0, height-1);
		}
	}
	
	public void Encode() {
		int tmp = 0;
		int[][] mm = null;
		try {
			mm = MatrixUtilities.multiplyMatrixByVector(m, matrix);
		} catch (VectorException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < matrix[0].length; i++) {
			tmp = 0;
			for(int j = 0; j < matrix.length; j++) {
				tmp = (tmp + mm[j][i]) % 2;
			}
			encoded[i] = tmp;
		}
	}
	
	
	
}
