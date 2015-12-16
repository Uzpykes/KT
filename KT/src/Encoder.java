


public class Encoder {

	public static int[][] matrix;
	public int height;
	public int width;
	public static int m;
	public static int k;
	
	/*
	 * Atlieka: 
	 * Ieitis: 
	 * Iseitis
	 */
	public Encoder(int m) {
		Encoder.m = m;
		Encoder.k = (int) Math.pow(2, m);
		matrix = new int[m+1][(int) Math.pow(2, m)];
		G();
		this.height = matrix[0].length;
		this.width = matrix.length;
	}
	
	/*
	 * Atlieka: Kviecia generaciju matricos algoritma
	 */
	public void G() {
		G(0, matrix[0].length, 0, matrix.length);
	}
	
	/*
	 * Atlieka: Sugeneruoja Generaciju matrica
	 */
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
	
	/*
	 * Atlieka: Uzkoduoja zinute message
	 * Ieitis: message - zinute kuria uzkoduojam
	 * Iseitis: result - uzkoduota zinute
	 */
	public static int[] Encode(int[] message) {
		int tmp = 0;
		int[] result = new int[matrix[0].length];
		int[][] mm = new int[matrix.length][matrix[0].length];
		
		if (message.length < m+1) {
			int[] appendedMessage = new int[m+1];
			for (int i = 0; i < message.length; i++) {
				appendedMessage[i] = message[i];
			}
			message = appendedMessage;
		}
		
		
		try {
			mm = MatrixUtilities.multiplyMatrixByVector(message, matrix);
			/*for (int i = 0; i < mm.length; i++){
				for (int j = 0; j < mm[0].length; i++)
					System.out.print(mm[j][i] + "");
			System.out.println();
			}*/
		} catch (VectorException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < matrix[0].length; i++) {
			tmp = 0;
			for(int j = 0; j < matrix.length; j++) {
				tmp = (tmp + mm[j][i]) % 2;
			}
			result[i] = tmp;
		}
		return result;
	}
	
	
	
}
