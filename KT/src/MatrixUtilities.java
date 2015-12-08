import java.util.Arrays;


public class MatrixUtilities {
	
	static int[][] H = {{1, 1}, {1, -1}};
	
	public static int[] multiplyByInt(int multiplier, int[] vector) {
		int[] result = vector;
		
		for (int i = 0; i < vector.length; i++)
			result[i] = multiplier * vector[i];
		
		return result;
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
	
	/* Apskaiciuojama vektoriaus ir matricos sandauga */
	public static int[] dotProduct(int[] A, int[][] B) {
		int[] result = new int[A.length];
		
		for (int i = 0; i < B[0].length; i++)
			for (int j = 0; j < A.length; j++)
				result[i] += A[j] * B[j][i];
		
		return result;		
	}
	
	
	public static int[][] kronecherProduct(int[][] A, int [][]B) {
		int[][] result = new int[A.length*B.length][A[0].length*B[0].length];
		
		for (int i = 0; i < result.length; i+=B.length)
			for (int j = 0; j < result[0].length; j+=B[0].length)
				for(int a = 0; a < B.length; a++)
					for(int b = 0; b < B[0].length; b++)
						result[i+a][j+b] = A[i/B.length][j/B[0].length]*B[a][b];
		
		return result;	
	}
	
	
	public static int[][] identityMatrix(int size) {
		int[][] result = new int[size][size];
		for (int i = 0; i < size; i++)
			result[i][i] = 1;
		return result;
	}
	
	public static int[][] hadamardMatrix(int i, int m) {
		return MatrixUtilities.kronecherProduct(MatrixUtilities.kronecherProduct(MatrixUtilities.identityMatrix((int) Math.pow(2, m-i)), H), MatrixUtilities.identityMatrix((int) Math.pow(2, i-1)));
	}
	
	
}
