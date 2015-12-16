import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import javax.swing.border.BevelBorder;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import java.awt.TextField;

import javax.swing.JPanel;
import javax.swing.JFormattedTextField;

import java.awt.Button;
import java.awt.Label;

import javax.swing.SwingConstants;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.DropMode;
import javax.swing.JButton;


public class GUIMain {

	/*
	 * 0 - RM nebuvo generuota
	 * 1 - Sugeneruota RM, laukiam zinutes
	 * 2 - Zinute uzkoduota
	 * 3 - Zinute issiuta
	 * 4 - Zinute atokoduota 
	 */
	private static int GUIState = 0;
	private static Encoder en;
	
	static JFrame mainWindow = new JFrame();
	static JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	static JPanel FirstScenario = new JPanel();
	static JLabel mValue = new JLabel("m reiksme");
	final static TextField mValueTextField = new TextField();
	static Button mValueButton = new Button("Generuoti RM");
	static Label label = new Label("Ivedami bitai");
	static TextField inputBits = new TextField();
	static Label label_1 = new Label("Uzkoduoti bitai");
	final static JTextArea encodedBits = new JTextArea();
	static Button encode = new Button("Koduoti");
	static Button send = new Button("Siusti");
	static Label label_2 = new Label("Gauti bitai");
	static JTextArea receivedBits = new JTextArea();
	static Label label_3 = new Label("Atkoduoti bitai");
	static TextField decodedBits = new TextField();
	static Button decode = new Button("Atkoduoti");
	static TextField errorP = new TextField();
	static JLabel lblKlaidosTikimyb = new JLabel("Klaidos tikimybe");
	static JScrollPane outputConsole = new JScrollPane();
	static DefaultTableModel model = new DefaultTableModel();
	private static JTable table = new JTable(model);
	
	
	public static void main(String args[]) {
		
		mainWindow.setTitle("Reed-Muller kodavimas, Hadamard atkodavimas");
		mainWindow.getContentPane().setLayout(new BoxLayout(mainWindow.getContentPane(), BoxLayout.X_AXIS));
		
		tabbedPane.setPreferredSize(new Dimension(850, 500));
		mainWindow.setPreferredSize(tabbedPane.getPreferredSize());
		
		mainWindow.getContentPane().add(tabbedPane);
		
		model.addColumn("Veiksmai");
		tabbedPane.addTab("Pirmas scenarijus", null, FirstScenario, null);
		FirstScenario.setLayout(null);
		mValue.setHorizontalAlignment(SwingConstants.RIGHT);
		mValue.setBounds(10, 11, 82, 22);
		FirstScenario.add(mValue);
		mValueTextField.setBounds(98, 13, 29, 20);
		mValueTextField.setText("5");
		FirstScenario.add(mValueTextField);
		mValueButton.setBounds(133, 11, 82, 22);
		
		mValueButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIState = 1;
				int inputM = 0;
				try {
					inputM = Integer.parseInt(mValueTextField.getText());
				} catch (Exception exp) {
					model.addRow(new Object[] {"Netinkamas skaiciaus formatas. Iveskite teigiama sveikia skaiciu."});
					GUIState = 0;
					return;
				}
				
				if (inputM < 1) {
					model.addRow(new Object[] {"Ivestas skaicius m turi buti lygus ar didesnis uz 1"});
					return;
				}
				try {
				en = generateRM(Integer.parseInt(mValueTextField.getText()));
				model.addRow(new Object[] {"Sugeneruota RM(1, " + en.m + ")"});
				} catch (Error exp) {
					model.addRow(new Object[] {"Neužtenka atminties sugeneruoti RM(1, " + en.m + "). Sumazinkine m reiksme"});
					GUIState = 0;
					return;
				}
			}
		});
		FirstScenario.add(mValueButton);
		label.setBounds(209, 11, 167, 22);
		
		label.setAlignment(Label.RIGHT);
		FirstScenario.add(label);
		inputBits.setBounds(382, 11, 365, 22);
		FirstScenario.add(inputBits);
		label_1.setBounds(209, 39, 167, 22);
		
		label_1.setAlignment(Label.RIGHT);
		FirstScenario.add(label_1);
		encode.setBounds(753, 11, 56, 22);
		
		encode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (GUIState > 0) {
					model.addRow(new Object[] {"Pradeta koduoti...."});
					if(!checkIfValid(inputBits.getText())) {
						model.addRow(new Object[] {"Ivestame vektoriuje yra ne bitu"});
						return;
					}
					
					if (inputBits.getText().length() != (en.m + 1)) {
						model.addRow(new Object[] {"Nepavyko uzkoduoti bitu sekos. Iveskite " + (en.m + 1) + " bitu ilgio vektoriu. Dabar ivesta " + inputBits.getText().length() +" bitu."});
						GUIState = 1;
						return;
					}
					GUIState = 2;
					
					try {
					encodedBits.setText(encode(inputBits.getText()));
					model.addRow(new Object[] {"Uzbaigta"});
					} catch (Error exp) {
						model.addRow(new Object[] {"Nepavyko uzkoduoti bitu sekos. Neuztenka atminties. Sumazinkite m ir bandykite dar karta."});
						GUIState = 0;
					}
				} else {
					model.addRow(new Object[] {"Sugeneruokite RM(1, m)"});
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(382, 39, 365, 66);
		FirstScenario.add(scrollPane);
		scrollPane.setViewportView(encodedBits);
		encodedBits.setLineWrap(true);
		encodedBits.setWrapStyleWord(true);
		encodedBits.setRows(1);
		FirstScenario.add(encode);
		send.setBounds(753, 118, 56, 22);
		
		send.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!checkIfValid(encodedBits.getText())) {
					model.addRow(new Object[] {"Ivestame vektoriuje yra ne bitu"});
					return;
				}
				if (encodedBits.getText().length() != (en.k)) {
					model.addRow(new Object[] {"Netinkamas sekos ilgis. Iveskite " + (en.k) + " bitu ilgio vektoriu. Dabar ivesta " + encodedBits.getText().length() +" bitu."});
					GUIState = 1;
					return;
				}
				receivedBits.setText(simulateNoise(Double.parseDouble(errorP.getText()), encodedBits.getText()));
				model.addRow(new Object[] {"Bitai persiusti kanalu, su klaidos tikimybe " + errorP.getText()});
			}
		});
		FirstScenario.add(send);
		label_2.setBounds(209, 158, 167, 22);
		
		label_2.setAlignment(Label.RIGHT);
		FirstScenario.add(label_2);
		label_3.setBounds(209, 241, 167, 22);
		
		label_3.setAlignment(Label.RIGHT);
		FirstScenario.add(label_3);
		decodedBits.setBounds(382, 241, 365, 22);
		FirstScenario.add(decodedBits);
		decode.setBounds(753, 158, 56, 22);
		decode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!checkIfValid(receivedBits.getText())) {
					model.addRow(new Object[] {"Ivestame vektoriuje yra ne bitu"});
					return;
				}
				if (receivedBits.getText().length() != (en.k)) {
					model.addRow(new Object[] {"Netinkamas sekos ilgis. Iveskite " + (en.k) + " bitu ilgio vektoriu. Dabar ivesta " + receivedBits.getText().length() +" bitu."});
					GUIState = 1;
					return;
				}
				decodedBits.setText(decode(receivedBits.getText()));
				model.addRow(new Object[] {"Gauti bitai atkoduoti"});
			}
		});
		FirstScenario.add(decode);
		errorP.setBounds(694, 118, 52, 20);
		errorP.setText("0");
		FirstScenario.add(errorP);
		lblKlaidosTikimyb.setBounds(570, 116, 118, 22);
		
		lblKlaidosTikimyb.setHorizontalAlignment(SwingConstants.RIGHT);
		FirstScenario.add(lblKlaidosTikimyb);
		
		outputConsole.setBounds(10, 283, 819, 150);
		FirstScenario.add(outputConsole);
		outputConsole.getVerticalScrollBar().getModel().setMaximum(100);
		outputConsole.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
	        public void adjustmentValueChanged(AdjustmentEvent e) {  
	        	if (e.getValueIsAdjusting())
	        		return;
	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
	        }
	    });
		
		
		outputConsole.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(382, 155, 365, 66);
		FirstScenario.add(scrollPane_1);
		scrollPane_1.setViewportView(receivedBits);
		
		Button errorsAfterSending = new Button("Klaidos");
		errorsAfterSending.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				ArrayList<Integer> differences = compareErrors(encodedBits.getText(), receivedBits.getText());
				String r = "";
				if (differences == null) {
					model.addRow(new Object[] {"Uzkoduotu ir gautu bitu vektoriu ilgiai nesutampa"});
					return;
				}
				
				model.addRow(new Object[] {"Is viso yra " + differences.size() + " klaidu"});
				for (int d : differences) {
					r += d + " ";
				}
				model.addRow(new Object[] {"Bitai nesutampa siose pozicijose: " + r});

			}
		});
		errorsAfterSending.setBounds(753, 186, 56, 22);
		FirstScenario.add(errorsAfterSending);
		
		mainWindow.setResizable(false);
		mainWindow.pack();
		mainWindow.setVisible(true);
	}
	
	private static Encoder generateRM(int m) {
		return new Encoder(m);
	}
	
	private static String encode(String message) {
		String result = "";
		int[] m = new int[message.length()];
		for (int i = 0; i < m.length; i++)
			m[i] = Integer.parseInt(message.substring(i, i+1));
		
		int[] resultArray = Encoder.Encode(m);
		
		for (int i = 0; i < resultArray.length; i++)
			result += resultArray[i];
		
		return result;
	}
	
	private static String simulateNoise(double probability, String message) {
		String result = "";
		int[] m = new int[message.length()];
		for (int i = 0; i < message.length(); i++)
			m[i] = Integer.parseInt(message.substring(i, i+1));
		
		int[] noised = ChannelSimulation.simulateNoise(probability, m);
	
		for (int i = 0; i < noised.length; i++) {
			result += noised[i];
		}
		
		return result;
	}
	
	private static String decode(String message) {
		String result = "";
		int[] m = new int[message.length()];
		for (int i = 0; i < m.length; i++)
			m[i] = Integer.parseInt(message.substring(i, i+1));
		
		int[] resultArray = Decoder.optimizedDecode(m, en.m);
		
		for (int i = 0; i < resultArray.length; i++)
			result += resultArray[i];
		return result;
	}
	
	private static boolean checkIfValid(String input) {
		boolean isValid = true;
		int i = 0;
		while ((i < input.length()) && (isValid)) {
			try {
				if (((Integer.parseInt(input.substring(i, i+1)) != 0) && (Integer.parseInt(input.substring(i, i+1)) != 1))) {
					isValid = false;
				}
			} catch (Exception exp){
				isValid = false;
				return isValid;
			}
			i++;
		}
		return isValid;
	}
	
	private static ArrayList<Integer> compareErrors(String before, String after) {
		if (before.length() != after.length())
			return null;
		
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < before.length(); i++) {
			if (Integer.parseInt(before.substring(i, i+1)) != Integer.parseInt(after.substring(i, i+1))) {
				result.add(i+1);
			}
		}
		
		return result;
	}
}
