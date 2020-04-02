import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

// Generated by Together

/**
 * An interface to SAAMS: Radar tracking of arriving and departing aircraft, and
 * transceiver for downloading of flight descriptors (by aircraft entering the
 * local airspace) and uploading of passenger lists (to aircraft about to
 * depart). A screen simulation of the radar/transceiver system. This class is a
 * controller for the AircraftManagementDatabase: it sends messages to notify
 * when a new aircraft is detected (message contains a FlightDescriptor), and
 * when radar contact with an aircraft is lost. It also registers as an observer
 * of the AircraftManagementDatabase, and is notified whenever any change occurs
 * in that <<model>> element. See written documentation.
 * 
 * @stereotype boundary/view/controller
 * @url element://model:project::SAAMS/design:view:::idwwyucko4qme4cko4sgxi
 * @url element://model:project::SAAMS/design:node:::id15rnfcko4qme4cko4swib.node107
 * @url element://model:project::SAAMS/design:node:::id3oolzcko4qme4cko4sx40.node167
 * @url element://model:project::SAAMS/design:view:::id3oolzcko4qme4cko4sx40
 * @url element://model:project::SAAMS/design:view:::id15rnfcko4qme4cko4swib
 */
public class RadarTransceiver extends JFrame implements ActionListener, Observer {
	/**
	 * The Radar Transceiver interface has access to the AircraftManagementDatabase.
	 * 
	 * @clientCardinality 1
	 * @supplierCardinality 1
	 * @label accesses/observes
	 * @directed
	 */
	private AircraftManagementDatabase aircraftManagementDatabase;
	private PassengerList passengerList = new PassengerList();

	Container window;

	DefaultListModel<ManagementRecord> listModelOfManagement; // used by JList
	JList<ManagementRecord> display;
	JScrollPane scrollPane;
	JPanel listPanel;

	// Management record currently selected
	int managementRecordIndex = -1;

	JLabel labelForFlightCodes;
	JTextField fieldForFlightCodes;

	JLabel itinearyLabel;
	JLabel fromLabel;
	JTextField fromText;
	JLabel toLabel;
	JTextField textField;
	JLabel labelNext;
	JTextField textNext;

	JLabel labelForPassengerList;
	JTextField fieldForPassengerList;

	JButton buttonAddPassenger;
	JButton buttonDetectPlane;

	JButton buttonLeavesAirspace;

	public RadarTransceiver(AircraftManagementDatabase aircraftManagementDatabase) {
		this.aircraftManagementDatabase = aircraftManagementDatabase;
		listPanel = new JPanel();
		setTitle("Radar");
		setLocation(150, 150);
		setSize(1000, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);

		window = getContentPane();
		window.setLayout(new FlowLayout());

		labelForFlightCodes = new JLabel("FlightCode: ");
		window.add(labelForFlightCodes);
		fieldForFlightCodes = new JTextField("", 25);
		window.add(fieldForFlightCodes);

		itinearyLabel = new JLabel("Itineary: ");
		window.add(itinearyLabel);
		fromLabel = new JLabel("From: ");

		fromText = new JTextField("", 23);
		toLabel = new JLabel("To: ");
		textField = new JTextField("", 13);
		labelNext = new JLabel("Next: ");
		textNext = new JTextField("", 13);

		window.add(fromLabel);
		window.add(fromText);
		window.add(toLabel);
		window.add(textField);
		window.add(labelNext);
		window.add(textNext);

		labelForPassengerList = new JLabel("    Passenger List: ");
		window.add(labelForPassengerList);
		fieldForPassengerList = new JTextField("", 20);
		window.add(fieldForPassengerList);
		buttonAddPassenger = new JButton("Add Passenger to passenger list");
		window.add(buttonAddPassenger);
		buttonAddPassenger.addActionListener(this);
		buttonDetectPlane = new JButton("Detect added plane in radar");
		window.add(buttonDetectPlane);
		buttonDetectPlane.addActionListener(this);

		createPanelList();
		window.add(listPanel);
		changeIndex();
		setVisible(true);

		buttonLeavesAirspace = new JButton("Plane left airspace");
		window.add(buttonLeavesAirspace);
		buttonLeavesAirspace.addActionListener(this);

		// add this view as an observer
		aircraftManagementDatabase.addObserver(this);
	}// end method

	private void createPanelList() {
		listModelOfManagement = new DefaultListModel<>();

		display = new JList<>(listModelOfManagement);
		// add a list listener - to check if an item has been selected
		display.addListSelectionListener(evt -> changeIndex());

		scrollPane = new JScrollPane(display); // scrollable list of records to display

		// set the size of the panel
		scrollPane.setPreferredSize(new Dimension(350, 200));
		scrollPane.setMinimumSize(new Dimension(350, 200));

		listPanel.add(scrollPane);
		// add all relevant records to scrollable list
		updatePlaneRecords();
	}// end method

	private void updatePlaneRecords() {
		listModelOfManagement.setSize(aircraftManagementDatabase.maxMRs);

		for (int i = 0; i < aircraftManagementDatabase.maxMRs; i++) {

			ManagementRecord record = aircraftManagementDatabase.getMR(i);

			if (record == null)
				listModelOfManagement.set(i, null);
			else {

				listModelOfManagement.set(i, record); // need to change to a string so we can add to the list add
														// toString method
				managementRecordIndex = i;
			}//end else
		} // end for
	}// end method

	private void changeIndex() {
		if (!display.getValueIsAdjusting()) {
			if (!(display.getSelectedValue() == null)) {
				managementRecordIndex = display.getSelectedIndex();
			} // end if
		} // end if
	}// end method

	public void update(Observable o, Object arg) {
		updatePlaneRecords();
		changeIndex();
	}// end method update

	public void errorMessage(String message) {

		JLabel messageLabel = new JLabel();

		JFrame newJFrame = new JFrame();
		newJFrame.setTitle("ERROR");
		newJFrame.setLocation(100, 400);
		newJFrame.setSize(400, 100);
		newJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newJFrame.setLayout(new FlowLayout());
		messageLabel.setText(message);
		newJFrame.add(messageLabel);
		newJFrame.setVisible(true);

	}// end method

	@Override
	public void actionPerformed(ActionEvent e) {
		changeIndex();
		if (e.getSource() == buttonAddPassenger) {
			if (fieldForPassengerList.getText().equals("")) {
				errorMessage("A Passenger must be added");
			} // end if
			else {
				PassengerDetails passenger = new PassengerDetails(fieldForPassengerList.getText());
				fieldForPassengerList.setText("");
				passengerList.addPassenger(passenger);
			} // end else
		} // end if
		if (e.getSource() == buttonDetectPlane) {
			String code = fieldForFlightCodes.getText();

			if (fieldForFlightCodes.getText().equals("")) {
				errorMessage("The incoming plane must have a Flight Code");
			} // end if
			else if ((fromText.getText().equals("")) || (toLabel.getText().equals(""))) {
				errorMessage("The 'from' and 'to' fields must be not be empty");
			} // end else if

			else {
				Itinerary current = new Itinerary(fromText.getText(), toLabel.getText(), textNext.getText());
				FlightDescriptor F = new FlightDescriptor(code, current, passengerList);
				aircraftManagementDatabase.radarDetect(F);
				fieldForFlightCodes.setText("");
				fromText.setText("");
				toLabel.setText("");
				textNext.setText("");
				passengerList = new PassengerList();
				if (aircraftManagementDatabase.getItinerary(managementRecordIndex).getTo()
						.equals(aircraftManagementDatabase.getFlightCode(managementRecordIndex))) {
					aircraftManagementDatabase.setStatus(managementRecordIndex, 2);
				} // end if
				else {
					aircraftManagementDatabase.setStatus(managementRecordIndex, 1);
				} // end else
			} // end else

		} // end if

		if (e.getSource() == buttonLeavesAirspace) {
			if ((aircraftManagementDatabase.getStatus(managementRecordIndex).equalsIgnoreCase("FREE"))
					|| (aircraftManagementDatabase.getStatus(managementRecordIndex).equalsIgnoreCase("DEPARTING_THROUGH_LOCAL_AIRSPACE"))) {
				aircraftManagementDatabase.radarLostContact(managementRecordIndex);
			} else {
				errorMessage("Aircraft has status " + aircraftManagementDatabase.getStatus(managementRecordIndex)
						+ ". Must have a status of 1 or 18 to leave airspace");
			} // end if/else
			updatePlaneRecords();
		}//end if
	} // end action performed
}// end class
