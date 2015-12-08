import java.util.Arrays;


public class MatrixUtilities {
	
	public static int[] multiplyByInt(int multiplier, int[] vector) {
		if (multiplier == 0) 
			Arrays.fill(vector, 0);
		return vector;
	}
	
	public static int[][] multiplyMatrixByVector(int[] vector, int[][] matrix) throws VectorException {
		if (matrix.length != vector.length) {
			throw new VectorException("Matricos aukstis ir vektoriaus ilgis turi sutapti");
		}
		
		int[][] result = new int[matrix.length][matrix[0].length];
		for (int i = 0; i < vector.length; i++)
			result[i] = multiplyByInt(vector[i], matrix[i]);
		return result;
	}
}
