import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

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

/**
 * @author miklo
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

    /**
     * The items of the JList - Management Records.
     */
    DefaultListModel<ManagementRecord> listModelOfManagement;

    JList<ManagementRecord> display;
    JScrollPane scrollPane;
    JPanel listPanel;

    // Index of the Management record currently selected.
    int managementRecordIndex = -1;
    /**
     * labels used for text field identification
     */
    JLabel labelForFlightCodes;
    JLabel flightCodes;
    JLabel itineraryLabel;
    JLabel fromLabel;
    JLabel toLabel;
    JLabel labelNext;
    JLabel labelForPassengerList;

    /**
     * text feilds used for user input
     */
    JTextField fieldForPassengerList;
    JTextField textNext;
    JTextField toText;
    JTextField fromText;

    /**
     * Buttons to trigger
     */
    JButton buttonAddPassenger;
    JButton buttonDetectPlane;
    JButton buttonLeavesAirspace;

    /**
     * constructor that builds the radar view
     *
     * @param aircraftManagementDatabase holds management records
     */
    public RadarTransceiver(AircraftManagementDatabase aircraftManagementDatabase) {
        this.aircraftManagementDatabase = aircraftManagementDatabase;
        listPanel = new JPanel();
        setTitle("Radar");
        setLocation(150, 150);
        setSize(1000, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        Container window = getContentPane();
        window.setLayout(new FlowLayout());

        labelForFlightCodes = new JLabel("FlightCode: ");
        window.add(labelForFlightCodes);

        flightCodes = new JLabel("");
        window.add(flightCodes);

        itineraryLabel = new JLabel("Itinerary - ");
        window.add(itineraryLabel);

        fromLabel = new JLabel("From: ");
        window.add(fromLabel);

        fromText = new JTextField("", 23);
        window.add(fromText);

        toLabel = new JLabel("To: ");
        window.add(toLabel);

        toText = new JTextField("", 13);
        window.add(toText);

        labelNext = new JLabel("Next: ");
        window.add(labelNext);

        textNext = new JTextField("", 13);
        window.add(textNext);

        labelForPassengerList = new JLabel("Passenger List: ");
        window.add(labelForPassengerList);
        fieldForPassengerList = new JTextField("", 20);
        window.add(fieldForPassengerList);
        buttonAddPassenger = new JButton("Add Passenger!");
        window.add(buttonAddPassenger);
        buttonAddPassenger.addActionListener(this);
        buttonDetectPlane = new JButton("Detect Plane!");
        window.add(buttonDetectPlane);
        buttonDetectPlane.addActionListener(this);

        listModelOfManagement = new DefaultListModel<>();

        display = new JList<>(listModelOfManagement);
        display.addListSelectionListener(evt -> itemSelected());

        scrollPane = new JScrollPane(display);

        scrollPane.setPreferredSize(new Dimension(350, 200));
        scrollPane.setMinimumSize(new Dimension(350, 200));

        listPanel.add(scrollPane);

        updateRecords();
        window.add(listPanel);
        itemSelected();
        setVisible(true);

        buttonLeavesAirspace = new JButton("Plane Leaves Airspace!");
        window.add(buttonLeavesAirspace);
        buttonLeavesAirspace.addActionListener(this);

        // add this as an observer.
        aircraftManagementDatabase.addObserver(this);
    }// end method

    /**
     * Adds the assigned management record to the JList
     */
    private void updateRecords() {
        listModelOfManagement.setSize(aircraftManagementDatabase.maxMRs);

        for (int i = 0; i < aircraftManagementDatabase.maxMRs; i++) {

            ManagementRecord record = aircraftManagementDatabase.getMR(i);

            if (record == null)
                listModelOfManagement.set(i, null);
            else {
                listModelOfManagement.set(i, record);
                managementRecordIndex = i;
            } // end else
        } // end for
    }// end method

    /**
     * sleects item on record
     */
    private void itemSelected() {
        if (!display.getValueIsAdjusting()) {
            if (!(display.getSelectedValue() == null)) {
                managementRecordIndex = display.getSelectedIndex();
            } // end if
        } // end if
    }// end method

    /**
     * Used to update the JFrame and JList depending on the changes.
     *
     * @param o   {@inheritDoc}
     * @param arg {@inheritDoc}
     */
    public void update(Observable o, Object arg) {
        updateRecords();
        itemSelected();
    }// end method update

    /**
     * Checks to see what button has been clicked, it will do the corresponding
     * action.
     *
     * @param e {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        itemSelected();
        if (e.getSource() == buttonAddPassenger) {
            if (fieldForPassengerList.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "You'll need to enter the name of the passenger first!");
            } // end if
            else {
                PassengerDetails passenger = new PassengerDetails(fieldForPassengerList.getText());
                fieldForPassengerList.setText("");
                passengerList.addPassenger(passenger);
            } // end else
        } // end if
        if (e.getSource() == buttonDetectPlane) {
            String code = flightCodes.getText();
            if (flightCodes.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "The Plane Must Have A Flight Code!");
            } // end if
            else if ((fromText.getText().equals("")) || (toText.getText().equals(""))) {
                JOptionPane.showMessageDialog(null,
                        "The radar needs to now where the plane came from and where it's going to!");
            } // end else if
            else {
                Itinerary current = new Itinerary(fromText.getText(), toLabel.getText(), textNext.getText());
                FlightDescriptor F = new FlightDescriptor(code, current, passengerList);
                aircraftManagementDatabase.radarDetect(F);
                flightCodes.setText("");
                fromText.setText("");
                toText.setText("");
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
                    || (aircraftManagementDatabase.getStatus(managementRecordIndex)
                    .equalsIgnoreCase("DEPARTING_THROUGH_LOCAL_AIRSPACE"))) {
                aircraftManagementDatabase.radarLostContact(managementRecordIndex);
            } else {
                JOptionPane.showMessageDialog(null,
                        "To leave the airspace, the aircraft must have the status of 1 or 18");
            } // end if/else
            updateRecords();
        } // end if
    } // end action performed
}// end class
