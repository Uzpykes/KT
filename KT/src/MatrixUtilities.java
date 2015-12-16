

public class MatrixUtilities {
	
	// pradine Hadamard matrica
	static int[][] H = {{1, 1}, {1, -1}};
	
	/*
	 * Atlieka: Daugina vektoriu vector is skaiciaus multiplier
	 * Ieitis: multiplier - skaicius, vector - vektorius
	 * Iseitis: result - vektorius kurio reiksmes padaugintos is skaiciaus multiplier
	 */
	public static int[] multiplyByInt(int multiplier, int[] vector) {
		int[] result = new int[vector.length];
		
		for (int i = 0; i < vector.length; i++)
			result[i] = multiplier * vector[i];
		
		return result;
	}
	
	/*
	 * Atlieka: Dauginam matrica is vektoriaus, taciau ne dot product budu o skirtu uzkodavimui 
	 * Ieitis: vector - vektorius, matrix - matrica
	 * Iseitis: result - matrico kurios kiekviena eilute padauginta is vektoriaus reiksmiu
	 */
	public static int[][] multiplyMatrixByVector(int[] vector, int[][] matrix) throws VectorException {
		if (matrix.length != vector.length) {
			throw new VectorException("Matricos aukstis ir vektoriaus ilgis turi sutapti");
		}
		
		int[][] result = new int[matrix.length][matrix[0].length];
		for (int i = 0; i < vector.length; i++)
			result[i] = multiplyByInt(vector[i], matrix[i]);
		return result;
	}
	
	/*
	 * Atlieka: tradicinis dot product, taciau skirtas dauginant matrica is vektoriaus
	 * Ieitis: A - vektorius, B - matrica
	 * Iseitis: result - vektorius
	 */
	public static int[] dotProduct(int[] A, int[][] B) {
		int[] result = new int[A.length];
		
		for (int i = 0; i < B[0].length; i++)
			for (int j = 0; j < A.length; j++)
				result[i] += A[j] * B[j][i];

		return result;		
	}
	
	/*
	 * Atlieka: Kronecher product
	 * Ieitis: A - matrica, B - matrica
	 * Iseitis: result - matrica
	 */
	public static int[][] kronecherProduct(int[][] A, int [][]B) {
		int[][] result = new int[A.length*B.length][A[0].length*B[0].length];
		
		for (int i = 0; i < result.length; i+=B.length)
			for (int j = 0; j < result[0].length; j+=B[0].length)
				for(int a = 0; a < B.length; a++)
					for(int b = 0; b < B[0].length; b++)
						result[i+a][j+b] = A[i/B.length][j/B[0].length]*B[a][b];
		
		return result;	
	}
	
	/*
	 * Atlieka: sukuria dydzio size I matrica
	 * Ieitis: size - norimas matricos dydis
	 * Iseitis: result - I matrica
	 */
	public static int[][] identityMatrix(int size) {
		int[][] result = new int[size][size];
		for (int i = 0; i < size; i++)
			result[i][i] = 1;
		return result;
	}
	
	
	/*
	 * Atlieka: Hadamard matrica gauta pagal apibrezimo, neoptimali, todel siame darbe nenaudojama
	 * Ieitis: i ir m Hadamard matricos parametrai
	 * Iseitis: HadamardMatrica
	 */
	public static int[][] hadamardMatrix(int i, int m) {
		return MatrixUtilities.kronecherProduct(MatrixUtilities.kronecherProduct(MatrixUtilities.identityMatrix((int) Math.pow(2, m-i)), H), MatrixUtilities.identityMatrix((int) Math.pow(2, i-1)));
	}
	
	/* 
	 * Grazina skaiciavimams reikalinga informacija (nenulines reiksmes)
	 * Taip apeinamas bereikalingas atminties naudojimas kuriant matrica kurios didele dalis nulines reiksmes
	 */
	public static OptimizedHadamard optimizedHadamardMatrix(int i, int m) {
		//Pagrindine istrizaina
		int[] mainDiagonal = new int[(int) Math.pow(2, m)];
		//sonine istrizaine, saugomos vienetu pozicijos, nes apart 1 kitos reiksmes nera
		int[][] side = new int[(int) Math.pow(2, m-1)][2];
		
		int signChange = (int) Math.pow(2, i-1);
		int sign = 1;
		int countX = 0;
		int countY = 0;
		for (int i1 = 0; i1 < mainDiagonal.length; i1++) {
			mainDiagonal[i1] = sign;
			//Soniniai vienetai "susiburiave" ties ta vieta kur istrizaine pereina is 1 i -1
			//Pagal tai apskaiciuojam ju pozicijas
			//Sias pozicijas naudos dekodavimo algoritmas dauginant gauta vektoriu is Hadamard matricos
			if (sign == 1) {
				//vieneto X pozicija
				side[countX][0] = i1;
				countX++;
			}
			if (sign == -1) {
				//vieneto Y pozicija
				side[countY][1] = i1;
				countY++;
			}
			//Skaiciuojant pagrindine istrizaine keiciam vieneta	
			if (((i1+1) % signChange) == 0) {
				sign = -sign;
			}
			
		}
		return new OptimizedHadamard(mainDiagonal, side);
	}
	
	/*
	 * Atlieka: Grazina dot product naudojant tik reikalingus HadamardMatricos duomenis. T.y. naudojama ne matrica o vektoriai su reikalinga informacija
	 * Ieitis: message - uzkoduota zinute, H - duomenys apie sugeneruota Hadamard matrica
	 * Iseitis: result - zinutes ir Hadamard matricos dot product
	 */
	public static int[] dotProductForOptimizedHadamard(int[] message, OptimizedHadamard H) {
		int[] result = new int[message.length];
		
		//Padauginam is istrizaines
		for (int j = 0; j < message.length; j++)
			result[j] += message[j] * H.diagonial[j];
		
		//Padauginam is soniniu istrizainiu
		for (int i = 0; i < H.side.length; i++) {
			result[H.side[i][0]] += message[H.side[i][1]];
			result[H.side[i][1]] += message[H.side[i][0]];
		}
		
		return result;	
	}
	
	
}
