import java.io.UnsupportedEncodingException;
import java.math.BigInteger;


public class StringUtilities {
	
	//Pavercia zinute i int[] bitu seka
	public static int[] getBits(String message) {
		byte[] meesageBytes = message.getBytes();
		StringBuilder bits = new StringBuilder();
		
		for (byte b : meesageBytes) {
			int val = b;
			for (int j = 0; j < 8; j++) {
				bits.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}
		}
		
		int[] result = new int[bits.length()];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = Integer.parseInt(bits.substring(0, 1));
			bits.delete(0, 1);
		}
		
		return result;
		
	}
	
	//pavercia bitu seka i zinute
	public static String getString(int[] bits) {
	
		byte[] result = new byte[bits.length/8];
		String b = "";
		for (int i = 0; i < bits.length; i++)
			b += bits[i];
		
		result = new BigInteger(b, 2).toByteArray();
		
		try {
			return new String(result, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
