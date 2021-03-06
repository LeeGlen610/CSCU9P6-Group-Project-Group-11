
// Generated by Together


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * An interface to SAAMS:
 * A Ground Operations Controller Screen:
 * Inputs events from GOC (a person), and displays aircraft and gate information.
 * This class is a controller for the GateInfoDatabase and the AircraftManagementDatabase: sending them messages to change the gate or aircraft status information.
 * This class also registers as an observer of the GateInfoDatabase and the AircraftManagementDatabase, and is notified whenever any change occurs in those <<model>> elements.
 * See written documentation.
 *
 * @stereotype boundary/view/controller
 * @url element://model:project::SAAMS/design:node:::id2wdkkcko4qme4cko4svm2.node36
 * @url element://model:project::SAAMS/design:view:::id2wdkkcko4qme4cko4svm2
 * @url element://model:project::SAAMS/design:view:::id1un8dcko4qme4cko4sw27
 * @url element://model:project::SAAMS/design:view:::id1bl79cko4qme4cko4sw5j
 * @url element://model:project::SAAMS/design:view:::id15rnfcko4qme4cko4swib
 * @url element://model:project::SAAMS/design:node:::id15rnfcko4qme4cko4swib.node107
 */
public class GOC extends JFrame implements ActionListener, Observer {
    /**
     * The Ground Operations Controller Screen interface has access to the GateInfoDatabase.
     *
     * @clientCardinality 1
     * @supplierCardinality 1
     * @label accesses/observes
     * @directed
     */
    private GateInfoDatabase gateInfoDatabase;
    /**
     * The Ground Operations Controller Screen interface has access to the AircraftManagementDatabase.
     *
     * @clientCardinality 1
     * @supplierCardinality 1
     * @label accesses/observes
     * @directed
     */
    private AircraftManagementDatabase aircraftManagementDatabase;

    /**
     * Label for the flight codes.
     */
    private JLabel labelForFlightCodes;

    /**
     * Label to be used by the flight code.
     */
    private JLabel flightCodes;

    /**
     * Label for the flight status.
     */
    private JLabel labelForFlightStatus;

    /**
     * label to be used by the flight status.
     */
    private JLabel flightStatus;

    /**
     * Label for gate one.
     */
    private JLabel firstGateLabel;

    /**
     * Label to be used for the gate one's status.
     */
    private JLabel firstGateCurrentStatus;

    /**
     * Label for gate two.
     */
    private JLabel secondGateLabel;

    /**
     * Label to be used for the gate two's status.
     */
    private JLabel secondGateCurrentStatus;

    /**
     * The panel that will be hold the JList.
     */
    private JPanel listPanel;

    /**
     * To allow the plane ground clearance.
     */
    private JButton okGroundClearance;

    /**
     * To allocate a plane to a gate.
     */
    private JButton allocateAGate;

    /**
     * Taxis the plane to the airstrip.
     */
    private JButton taxi;

    /**
     * The JList for the Management Records.
     */
    private JList<ManagementRecord> aircrafts;

    /**
     * The items of the JList - Management Records.
     */
    private DefaultListModel<ManagementRecord> listModelOfManagement;

    /**
     * The index of the selected management record.
     */
    private int managementRecordIndex = -1;

    /**
     * Depending on the management record index the buttons will activate or not.
     */
    private boolean buttonAvaliablity = false;

    /**
     * Constructor for the GOC to make the view for it.
     *
     * @param aircraftManagementDatabase Holds All The Management Records.
     * @param gateInfoDatabase           Holds All The Gates Information.
     */
    public GOC(AircraftManagementDatabase aircraftManagementDatabase, GateInfoDatabase gateInfoDatabase) {
        //Initialises the databases held.
        this.aircraftManagementDatabase = aircraftManagementDatabase;
        this.gateInfoDatabase = gateInfoDatabase;

        //Initialise the JFrame.
        setTitle("GOC View");
        setLocation(150, 150);
        setSize(1000, 500);
        Container window = getContentPane();
        window.setLayout(new FlowLayout());

        //Create the labels for the frame.
        labelForFlightCodes = new JLabel("Flight Code: ");
        window.add(labelForFlightCodes);
        flightCodes = new JLabel("");
        window.add(flightCodes);

        labelForFlightStatus = new JLabel("Flight Status: ");
        window.add(labelForFlightStatus);
        flightStatus = new JLabel("");
        window.add(flightStatus);

        firstGateLabel = new JLabel("Gate1: ");
        window.add(firstGateLabel);
        firstGateCurrentStatus = new JLabel("Status is FREE");
        window.add(firstGateCurrentStatus);

        secondGateLabel = new JLabel("Gate2: ");
        window.add(secondGateLabel);

        secondGateCurrentStatus = new JLabel("Status is FREE");
        window.add(secondGateCurrentStatus);

        //Create the buttons for the frame and uses the GOC as its action listener.
        okGroundClearance = new JButton("Good For Ground Clearance");
        window.add(okGroundClearance);
        okGroundClearance.addActionListener(this);

        allocateAGate = new JButton("Allocate A Gate To Flight");
        window.add(allocateAGate);
        allocateAGate.addActionListener(this);

        taxi = new JButton("Taxi Flight To Gate");
        window.add(taxi);
        taxi.addActionListener(this);

        //Create the JList for the management records.
        listPanel = new JPanel();

        listModelOfManagement = new DefaultListModel<>();

        aircrafts = new JList<>(listModelOfManagement);

        aircrafts.addListSelectionListener(e -> itemSelected());

        JScrollPane scroll = new JScrollPane(aircrafts);

        scroll.setPreferredSize(new Dimension(500, 300));
        scroll.setMinimumSize(new Dimension(500, 300));

        listPanel.add(scroll);

        listModelOfManagement.setSize(aircraftManagementDatabase.maxMRs);

        updateRecords();
        updateGates();
        //Adds the list to the JFrame.
        window.add(listPanel);
        itemSelected();
        setVisible(true);

        //Adds GOC as an observer.
        aircraftManagementDatabase.addObserver(this);
        gateInfoDatabase.addObserver(this);
    }//END CONSTRUCTOR GOC

    /**
     * Updates the JList to add the Management Records.
     */
    private void updateRecords() {
        //Goes through and adds all of the records held in the aircraft database.
        for (int i = 0; i < aircraftManagementDatabase.maxMRs; i++) {
            ManagementRecord managementRecord = aircraftManagementDatabase.getMR(i);
            if (managementRecord == null) {
                listModelOfManagement.set(i, null);
            } else {
                listModelOfManagement.set(i, managementRecord);
            }//END IF/ELSE
        }//END FOR
    }//END METHOD updateRecords

    /**
     * Depending on if the user has selected a record or not the flight code and status of the selected record
     * or will show unknown.
     */
    private void itemSelected() {
        //Checks to see if the aircraft values isn't currently changing.
        if (!aircrafts.getValueIsAdjusting()) {
            //If the user hasn't selected a flight.
            if (aircrafts.getSelectedValue() == null) {
                //Will set the values of the variables to represent an unknown record.
                managementRecordIndex = -1;
                flightCodes.setText("UNKNOWN");
                flightStatus.setText("UNKNOWN");
                //Will turn off the buttons as there is no record selected.
                if (buttonAvaliablity) {
                    buttonAvaliablity = false;
                } //END IF
                updateButtons();
            } else { //If the user has selected a flight.
                //Gets the management record selected and sets the labels to show the flight code and flight status.
                managementRecordIndex = aircrafts.getSelectedIndex();
                flightCodes.setText(aircraftManagementDatabase.getFlightCode(managementRecordIndex));
                flightStatus.setText(aircraftManagementDatabase.getStatus(managementRecordIndex));
                //Will turn on buttons depending on the records status.
                if (!buttonAvaliablity) {
                    buttonAvaliablity = true;
                }//END IF
                updateButtons();
            }//END IF/ELSE
        }//END IF
    }//END METHOD itemSelected

    /**
     * Will allow the user to click on the buttons depending on the status of the flight.
     */
    public void updateButtons() {
        //Checks to see if the user has clicked on a record.
        if (!buttonAvaliablity) {
            allocateAGate.setEnabled(false);
            okGroundClearance.setEnabled(false);
            taxi.setEnabled(false);

        } else {
            //Finds out the status of the record.
            String status = aircraftManagementDatabase.getStatus(managementRecordIndex);

            //Depending on the status of the flight the button will either turn on or off.
            if (status.equals("WAITING_TO_LAND")) {
                okGroundClearance.setEnabled(true);
            } else {
                okGroundClearance.setEnabled(false);
            }//END IF/ELSE

            if (status.equals("AWAITING_TAXI")) {
                taxi.setEnabled(true);
            } else {
                taxi.setEnabled(false);
            }//END IF/ELSE

            //Checks to see if there is a free gate.
            int gateStatus[] = gateInfoDatabase.getStatuses();
            int gateArraySize = gateStatus.length;
            int currentStatus = 0;
            //Looks for the first gate in the array that is free.
            for (int i = 0; i < gateArraySize; i++) {
                if (gateStatus[i] == 0) {
                    currentStatus = gateStatus[i];
                }//END IF
            }//END FOR
            //If the plane has landed and the gate is free it will allow for the allocation of a gate.
            if ((status.equals("LANDED")) && currentStatus == 0) {
                allocateAGate.setEnabled(true);
            } else {
                allocateAGate.setEnabled(false);
            }//END IF/ELSE
        }//END IF/ELSE
    }//END METHOD updateButtons

    /**
     * Updates the gates status in the frame.
     */
    public void updateGates() {
        int firstGateStatus = gateInfoDatabase.getStatus(0);
        int secondGateStatus = gateInfoDatabase.getStatus(1);
        firstGateCurrentStatus.setText(statusOfGate(firstGateStatus));
        secondGateCurrentStatus.setText(statusOfGate(secondGateStatus));
    }//END METHOD updateGates

    /**
     * Depending on what has been passed into the method it will return a status.
     *
     * @param status The status of the gate.
     * @return The Gate Status In String.
     */
    public String statusOfGate(int status) {
        switch (status) {
            case 0:
                return "FREE";
            case 1:
                return "RESERVED";
            case 2:
                return "OCCUPIED";
            default:
                return "UNKNOWN";
        }//END SWITCH
    }//END METHOD statusOfGate

    /**
     * Checks to see what button has been clicked, it will do the corresponding action.
     *
     * @param e {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //Gives aircraft ground clearance.
        if (e.getSource() == okGroundClearance) {
            aircraftManagementDatabase.setStatus(managementRecordIndex, 3);
        }//END IF

        //Allocates a gate to an aircraft.
        if (e.getSource() == allocateAGate) {
            int recordIndex = -1;
            int maxNumberOfGates = gateInfoDatabase.getStatuses().length;

            // search through and find a free gate
            for (int i = 0; i < maxNumberOfGates; i++) {
                if (gateInfoDatabase.getStatus(i) == 0) {
                    recordIndex = i;
                    break;
                }//END IF
            }//END FOR

            //Assigns the management record to the gate.
            if (recordIndex == -1) {
                JOptionPane.showMessageDialog(null, "No gates are currently free!");
            } else {
                gateInfoDatabase.allocate(recordIndex, managementRecordIndex);
                aircraftManagementDatabase.taxiTo(managementRecordIndex, recordIndex);
                aircraftManagementDatabase.setStatus(managementRecordIndex, 6);
            }//END IF/ELSE

        }//END IF

        if (e.getSource() == taxi) {
            //Aircraft departs from the gate and is taxied to the Airstrip.
            int assignedGate = aircraftManagementDatabase.getGateNumber(managementRecordIndex);

            aircraftManagementDatabase.setStatus(managementRecordIndex, 17);
            gateInfoDatabase.departed(assignedGate);
        }//END IF
    }//END METHOD actionPerformed

    /**
     * Used to update the JFrame and JList depending on the changes.
     *
     * @param observable {@inheritDoc}
     * @param o          {@inheritDoc}
     */
    @Override
    public void update(Observable observable, Object o) {
        updateRecords();
        updateGates();
        itemSelected();
    }//END METHOD update
}//END CLASS GOC
