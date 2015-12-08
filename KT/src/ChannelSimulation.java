
public class ChannelSimulation {

	public static int[] simulateNoise(double probability, int[] message) {
		
		for (int i = 0; i < message.length; i++)
			if (Math.random() < probability)
				message[i] = (message[i] + 1) % 2;
		return message;
	}
	
	
}
