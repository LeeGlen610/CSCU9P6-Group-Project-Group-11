
// Generated by Together


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * An interface to SAAMS:
 * Cleaning Supervisor Screen:
 * Inputs events from the Cleaning Supervisor, and displays aircraft information.
 * This class is a controller for the AircraftManagementDatabase: sending it messages to change the aircraft status information.
 * This class also registers as an observer of the AircraftManagementDatabase, and is notified whenever any change occurs in that <<model>> element.
 * See written documentation.
 *
 * @stereotype boundary/view/controller
 * @url element://model:project::SAAMS/design:view:::id3y5z3cko4qme4cko4sw81
 * @url element://model:project::SAAMS/design:node:::id15rnfcko4qme4cko4swib.node107
 * @url element://model:project::SAAMS/design:view:::id15rnfcko4qme4cko4swib
 */
public class CleaningSupervisor extends JFrame
        implements ActionListener, Observer {
    private AircraftManagementDatabase aircraftManagementDatabase;
    /**
     * The Cleaning Supervisor Screen interface has access to the AircraftManagementDatabase.
     *
     * @clientCardinality 1
     * @supplierCardinality 1
     * @label accesses/observes
     * @directed
     */
    //private AircraftManagementDatabase lnkUnnamed;
    private JButton awaitMaintenance;
    private JButton awaitRepair;
    private JButton doneCleaning;

    private JPanel listPanel;
    private JList<ManagementRecord> aircrafts;
    private DefaultListModel<ManagementRecord> listModelOfManagement;
    private JLabel flightStatus;
    private JLabel labelForFlightStatus;
    private JLabel flightCodes;
    private JLabel labelForFlightCodes;
    private boolean buttonAvailability;
    private int managementRecordIndex;


    public CleaningSupervisor(AircraftManagementDatabase aircraftManagementDatabase) {
        this.aircraftManagementDatabase = aircraftManagementDatabase;

        setTitle("Cleaning!");
        setSize(450, 650);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout());

        /*
          labels
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
          maintenance button
         */
        awaitMaintenance = new JButton("Await Maintenance");
        window.add(awaitMaintenance);
        awaitMaintenance.addActionListener(this);


        /*
          waiting for repair button
         */
        awaitRepair = new JButton("Await Repair");
        window.add(awaitRepair);
        awaitRepair.addActionListener(this);

        /*
          done cleaning button
         */
        doneCleaning = new JButton("Done Cleaning");
        window.add(doneCleaning);
        doneCleaning.addActionListener(this);

        /*
          new list of aircrafts that need cleaning/repairs
         */
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
        window.add(listPanel);
        itemSelected();
        /*
          show labels, fields and buttons
         */
        setVisible(true);
        aircraftManagementDatabase.addObserver(this);
    } //end of cleaning supervisor method

    /**
     * update buttons method
     * take status from the database
     * and update buttons with the appropriate status
     */
    private void updateButtons() {

        if (!buttonAvailability) {
            awaitMaintenance.setEnabled(false);
            awaitRepair.setEnabled(false);
            doneCleaning.setEnabled(false);
        } else {

            String status = aircraftManagementDatabase.getStatus(managementRecordIndex);
            if (status.equalsIgnoreCase("READY_CLEAN_AND_MAINT")) {
                awaitMaintenance.setEnabled(true);
            } else {
                awaitMaintenance.setEnabled(false);
            } //end of else
            if (status.equalsIgnoreCase("CLEAN_AWAIT_MAINT")) {
                awaitRepair.setEnabled(true);
            } else {
                awaitRepair.setEnabled(false);
            } //end of else
            if (status.equalsIgnoreCase("OK_AWAIT_CLEAN")) {
                doneCleaning.setEnabled(true);
            } else {
                doneCleaning.setEnabled(false);
            } //end of else
        }//end of else
    }//end of update buttons method

    /**
     * update records method
     * go through the list and get records, if empty leave it
     * else, get status and if equals to each case then set management record status as appropriate
     */
    private void updateRecords() {
        for (int i = 0; i < aircraftManagementDatabase.maxMRs; i++) {
            ManagementRecord managementRecord = aircraftManagementDatabase.getMR(i);
            if (managementRecord == null) {
                listModelOfManagement.set(i, null);
            } else if (managementRecord.getStatus(managementRecord.getStatus()).equalsIgnoreCase("FAULTY_AWAIT_CLEAN")
                    || managementRecord.getStatus(managementRecord.getStatus()).equalsIgnoreCase("READY_CLEAN_AND_MAINT")
                    || managementRecord.getStatus(managementRecord.getStatus()).equalsIgnoreCase("CLEAN_AWAIT_MAINT")
                    || managementRecord.getStatus(managementRecord.getStatus()).equalsIgnoreCase("OK_AWAIT_CLEAN")
                    || managementRecord.getStatus(managementRecord.getStatus()).equalsIgnoreCase("AWAIT_REPAIR")) {
                listModelOfManagement.set(i, managementRecord);
            }
        }
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
                updateButtons();
            } else {
                managementRecordIndex = aircrafts.getSelectedIndex();
                flightCodes.setText(aircraftManagementDatabase.getFlightCode(managementRecordIndex));
                flightStatus.setText(aircraftManagementDatabase.getStatus(managementRecordIndex));
                if (!buttonAvailability) {
                    buttonAvailability = true;
                }
                updateButtons();
            }
        } //end of if statement
    } //end of item selected method

    /**
     * if buttons are pressed, then set the aircraft management database status
     * as the status from the management record index
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == awaitMaintenance) {
            aircraftManagementDatabase.setStatus(managementRecordIndex, 8);
        } else if (e.getSource() == awaitRepair) {
            aircraftManagementDatabase.setStatus(managementRecordIndex, 10);
        } else if (e.getSource() == doneCleaning) {
            aircraftManagementDatabase.setStatus(managementRecordIndex, 13);
        }
    }// end of action performed

    /**
     * update method
     * call the update records method
     * and the item selected method
     */
    @Override
    public void update(Observable o, Object arg) {
        updateRecords(); //update records
        itemSelected(); // call item selected
    } //end of update method
} //end of CleaningSupervisor class
