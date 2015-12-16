
public class ChannelSimulation {

	/*
	 * Atlieka: Su tikimybe probability iskraipo zinute message
	 * Ieitis: probability - tikimybe klaidai, message - siunciama zinute
	 * Iseitis: message - iskraipyta zinute
	 */
	public static int[] simulateNoise(double probability, int[] message) {
		
		for (int i = 0; i < message.length; i++)
			if (Math.random() < probability)
				message[i] = (message[i] + 1) % 2;
		return message;
	}
	
	
}
