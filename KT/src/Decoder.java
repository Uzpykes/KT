
public class Decoder {

	/*
	 * Atlieka: Pakeicia 0 i -1
	 * Ieitis: message - zinute kurioje pakeiciami nuliai
	 * Iseitis: resul - zinute kurioje 0 pakeisti i -1
	 */
	private static int[] replaceZeros(int[] message) {
		int[] result = message;
		
		for (int i = 0; i < result.length; i++)
			if (result[i] == 0)
				result[i] = -1;
		
		return result;		
	}
	
	/*
	 * Nenaudojamas programoje, vietoj sito naudojam optimizedDecode
	 * Atlieka: Paprastas iskodavimas. Iskoduoja zinute.
	 * Ieitis: message - zinute, m - RM(1, m) m parametras
	 * Iseitis: result - iskoduota m+1 ilgio zinute
	 */
	public static int[] decode(int[] message, int m) {
		int[] result = new int[m+1];
		int[] _message = replaceZeros(message);
		int mostExtreme = 0;
		int position = 0;
		
		for (int i = 1; i <= m; i++) {
			_message = MatrixUtilities.dotProduct(_message, MatrixUtilities.hadamardMatrix(i, m));
			for (int j = 0; j < _message.length; j++)
				if (Math.abs(_message[j]) > Math.abs(mostExtreme)) {
					mostExtreme = _message[j];
					position = j;
				}
		}
		int c = 1;
		while(position != 0) {
			result[c] = position % 2;
			position /= 2;
			c++;
		}
		if (mostExtreme > 0)
			result[0] = 1;
		
		return result;		
	}
	
	/*
	 * Naudojamas vietoj decode
	 * Atlieka: Paprastas iskodavimas. Iskoduoja zinute.
	 * Ieitis: message - zinute, m - RM(1, m) m parametras
	 * Iseitis: result - iskoduota m+1 ilgio zinute
	 */
	public static int[] optimizedDecode(int[] message, int m) {
		int[] result = new int[m+1];
		int[] _message = replaceZeros(message);
		int mostExtreme = 0;
		int position = 0;
		
		for (int i = 1; i <= m; i++) {
			_message = MatrixUtilities.dotProductForOptimizedHadamard(_message, MatrixUtilities.optimizedHadamardMatrix(i, m));
			for (int j = 0; j < _message.length; j++)
				if (Math.abs(_message[j]) > Math.abs(mostExtreme)) {
					mostExtreme = _message[j];
					position = j;
				}
		}
		int c = 1;
		while(position != 0) {
			result[c] = position % 2;
			position /= 2;
			c++;
		}
		if (mostExtreme > 0)
			result[0] = 1;
		
		return result;		
	}
	
}
