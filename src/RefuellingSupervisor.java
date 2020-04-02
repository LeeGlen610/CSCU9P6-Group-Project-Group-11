
// Generated by Together


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectStreamException;
import java.util.Observable;
import java.util.Observer;

/**
 * An interface to SAAMS:
 * Refuelling Supervisor Screen:
 * Inputs events from the Refuelling Supervisor, and displays aircraft information.
 * This class is a controller for the AircraftManagementDatabase: sending it messages to change the aircraft status information.
 * This class also registers as an observer of the AircraftManagementDatabase, and is notified whenever any change occurs in that <<model>> element.
 * See written documentation.
 *
 * @stereotype boundary/view/controller
 * @url element://model:project::SAAMS/design:view:::id15rnfcko4qme4cko4swib
 * @url element://model:project::SAAMS/design:node:::id15rnfcko4qme4cko4swib.node107
 * @url element://model:project::SAAMS/design:view:::id3y5z3cko4qme4cko4sw81
 */
public class RefuellingSupervisor extends JFrame
        implements ActionListener, Observer {

    private AircraftManagementDatabase aircraftManagementDatabase;
/**
 * The Refuelling Supervisor Screen interface has access to the AircraftManagementDatabase.
 * @supplierCardinality 1
 * @clientCardinality 1
 * @label accesses/observes
 * @directed*/


    /**
     * await refuelling button
     */
    private JButton awaitRefuelling;

    private JPanel listPanel;
    /**
     * new aircraft list to hold the aircrafts that need refuelling
     */
    private JList<ManagementRecord> aircrafts;
    private DefaultListModel<ManagementRecord> listModelOfManagement;
    /**
     * labels for flight codes, status
     */
    private JLabel flightStatus;
    private JLabel labelForFlightStatus;
    private JLabel flightCodes;
    private JLabel labelForFlightCodes;
    boolean buttonAvailability;
    int managementRecordIndex;


    public RefuellingSupervisor(AircraftManagementDatabase aircraftManagementDatabase) {
        this.aircraftManagementDatabase = aircraftManagementDatabase;

        setTitle("Refuelling!");
        setSize(450, 650);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout());

    /*
      labels.
     */
        labelForFlightCodes = new JLabel("Flight Code: ");
        window.add(labelForFlightCodes);
        flightCodes = new JLabel("");
        window.add(flightCodes);

        labelForFlightStatus = new JLabel("Flight Status: ");
        window.add(labelForFlightStatus);
        flightStatus = new JLabel("");
        window.add(flightStatus);

    /*
      create the new button
     */
        awaitRefuelling = new JButton("Await Refuelling");
    /*
      add new button to the window
     */
        window.add(awaitRefuelling);
        awaitRefuelling.addActionListener(this);


    /*
      new list of aircrafts that need refuelling
     */
        listPanel = new JPanel();
        listModelOfManagement = new DefaultListModel<>();
        aircrafts = new JList<>(listModelOfManagement);
        aircrafts.addListSelectionListener(e -> itemSelected());
        JScrollPane scroll = new JScrollPane(aircrafts);
        scroll.setPreferredSize(new Dimension(500, 300));
        scroll.setMinimumSize(new Dimension(500, 300));

        listPanel.add(scroll);
    /*
      set list size
     */
        listModelOfManagement.setSize(aircraftManagementDatabase.maxMRs);

        updateRecords();
        window.add(listPanel);
        itemSelected();
    /*
      show labels, fields and buttons
     */
        setVisible(true);
        aircraftManagementDatabase.addObserver(this);
    } //end of refuelling supervisor method

    /**
     * update buttons method
     * button availability shows if a button can be pressed according to the flight status
     * if button is not available then set wait to refuel to false,
     * else if its ready for refuelling, the button can be pressed and the status can be set to "ready refuel"
     */
    private void updateButtons() {
        if (!buttonAvailability) {
            awaitRefuelling.setEnabled(false);
        } else {
            String status = aircraftManagementDatabase.getStatus(managementRecordIndex);
            if (status.equalsIgnoreCase("READY_REFUEL")) {
                awaitRefuelling.setEnabled(true);
            } else {
                awaitRefuelling.setEnabled(false);
            }
        }
    }//end of update buttons method

    /**
     * updating the records method
     */
    private void updateRecords() {
        //go through the list
        for (int i = 0; i < aircraftManagementDatabase.maxMRs; i++) {
            ManagementRecord managementRecord = aircraftManagementDatabase.getMR(i); //get each value
            //if empty
            if (managementRecord == null) {
                //set value to null (empty)
                listModelOfManagement.set(i, null);
                //else...
            } else if (managementRecord.getStatus(managementRecord.getStatus()).equalsIgnoreCase("READY_REFUEL")) {
                //get the status from the management record and set the status of that value in the list
                listModelOfManagement.set(i, managementRecord);
            }//end of else if
        } //end of for loop
    }//end of update records method

    /**
     * item selected method
     * if a specific value is pressed then it is highlighted as clicked on
     * if button is not available to be clicked and if the value selected is null then set the index as empty (-1),
     * and set the flight code and status to "unknown"
     * else, set flight code and status from that index
     * call update buttons method
     */
    private void itemSelected() {
        if (!aircrafts.getValueIsAdjusting()) {
            if (aircrafts.getSelectedValue() == null) {
                managementRecordIndex = -1;
                flightCodes.setText("UNKNOWN");
                flightStatus.setText("UNKNOWN");
                if (buttonAvailability) {
                    buttonAvailability = false;
                }
                updateButtons(); //update buttons
            } else {
                managementRecordIndex = aircrafts.getSelectedIndex();
                flightCodes.setText(aircraftManagementDatabase.getFlightCode(managementRecordIndex));
                flightStatus.setText(aircraftManagementDatabase.getStatus(managementRecordIndex));
                if (!buttonAvailability) {
                    buttonAvailability = true;
                }
                updateButtons(); //update buttons
            }
        }
    }//end of itemSelected method

    /**
     * action performed method
     * if specific button is pressed then set status to the string from the appropriate int given
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == awaitRefuelling) { //if button clicked
            aircraftManagementDatabase.setStatus(managementRecordIndex, 14); //set status to case 13: ready refuel
        } //end of if statement
    } //end of action performed

    /**
     * update method
     * calls update records and item selected methods
     */
    @Override
    public void update(Observable o, Object arg) {
        updateRecords(); //update the records and then..
        itemSelected();// update items selected
    } //end of update method
} //end of RefuellingSupervisor class
