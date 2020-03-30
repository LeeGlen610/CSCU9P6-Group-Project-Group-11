
// Generated by Together


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An interface to SAAMS:
 * Refuelling Supervisor Screen:
 * Inputs events from the Refuelling Supervisor, and displays aircraft information.
 * This class is a controller for the AircraftManagementDatabase: sending it messages to change the aircraft status information.
 * This class also registers as an observer of the AircraftManagementDatabase, and is notified whenever any change occurs in that <<model>> element.
 * See written documentation.
 * @stereotype boundary/view/controller
 * @url element://model:project::SAAMS/design:view:::id15rnfcko4qme4cko4swib
 * @url element://model:project::SAAMS/design:node:::id15rnfcko4qme4cko4swib.node107
 * @url element://model:project::SAAMS/design:view:::id3y5z3cko4qme4cko4sw81
 */
public class RefuellingSupervisor extends JFrame
        implements ActionListener {

  private final AircraftManagementDatabase aircraftManagementDatabase;
/**
  * The Refuelling Supervisor Screen interface has access to the AircraftManagementDatabase.
  * @supplierCardinality 1
  * @clientCardinality 1
  * @label accesses/observes
  * @directed*/
 // private AircraftManagementDatabase lnkUnnamed;
  private JButton awaitRefuelling;

  private JTextField displayCodes;
  private JTextField displayStatus;

  public RefuellingSupervisor(AircraftManagementDatabase aircraftManagementDatabase) {
    this.aircraftManagementDatabase = aircraftManagementDatabase;

    setSize(350,150);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    Container window = getContentPane();
    window.setLayout(new FlowLayout());


    awaitRefuelling = new JButton("Await Refuelling");
    window.add(awaitRefuelling);
    awaitRefuelling.addActionListener(this);

    add(new JLabel("FLIGHT_CODES"));
    displayCodes = new JTextField("", 15);
    add(displayCodes);

    add(new JLabel("FLIGHT_STATUS"));
    displayStatus = new JTextField("", 15);
    add(displayStatus);

    setVisible(true);
    show();


    //new list of aircrafts that need refuelling
    listPanel = new JPanel();
    listModelOfManagement = new DefaultListModel<>();
    aircrafts = new JList<>(listModelOfManagement);
    aircrafts.addListSelectionListener(e -> itemSelected());
    JScrollPane scroll = new JScrollPane(aircrafts);
    scroll.setPrefferedSize(new Dimension(width:500, height:300));
    scroll.setMinimumSize(new Dimension(width:500, height 300));

    listPanel.add(scroll);
    listModeOfManegement.setSize(aircraftManagementDatabase.maxMRs);
  }
  private void updateButtons() {
    if (!buttonAvailability) {
      awaitRefuelling.setEnabled(false);
    } else {
      String status = aircraftManagementDatabase.getStatus(managementRecordIndex);
      if (status.equalsIgnoreCase(anotherString:"READY_REFUEL")){
        awaitRefuelling.setEnabled(true);
      } else{
        awaitRefuelling.setEnabled(false);
      }
    }
  }

  private void updateRecords() {
    for (int i=0; i<aircraftManagementDatabase.maxMRs; i++) {
      ManagementRecord managementRecord = aircraftManagementDatabase.getMR(i);
      if (managementRecord == null) {
        listModelOfManagement.set(i,null);
      } else if ( managementRecord.getStatus(managementRecord.getStatus().equalsIgnoreCase(anotherString:"READY_REFUEL") {
        listModelOfManagement.set(i,managementRecord);
      }
    }
  }

  private void itemSelected() {
    if (!aircrafts.getValueAdjusting()) {
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
        flightStatus.setText(aircraftManagementDatabase.getFlightStatus(managementRecordIndex));
        if (!buttonAvailability) {
          buttonAvailability = true;
        }
        updateButtons();
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == awaitRefuelling) {
      aircraftManagementDatabase.setStatus(managementRecordIndex, newStatus:"READY_REFUEL");
    }
  }

  @Override
  public void update(observable o, Object arg) {
    displayCodes.setText("Flight Codes: " + flightCodes);
    displayStatus.setText("Flight Status: " + flightStatus);

  }
}
