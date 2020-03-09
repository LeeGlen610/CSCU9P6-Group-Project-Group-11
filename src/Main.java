

/**
 * The Main class.
 *
 * The principal component is the usual main method required by Java application to launch the application.
 *
 * Instantiates the databases.
 * Instantiates and shows all the system interfaces as Frames.
 * @stereotype control
 */
public class Main {


/**
 * Launch SAAMS.
 */

public static void main(String[] args) {
    // Instantiate databases
    // Instantiate and show all interfaces as Frames
  AircraftManagementDatabase aircraftManagementDatabase = new AircraftManagementDatabase(); //database
  GateInfoDatabase gateInfoDatabase = new GateInfoDatabase();
  GOC c1 = new GOC(aircraftManagementDatabase, gateInfoDatabase);
  LATC c2 =new LATC(aircraftManagementDatabase);
  MaintenanceInspector c3 = new MaintenanceInspector(aircraftManagementDatabase);
  RefuellingSupervisor c4 = new RefuellingSupervisor(aircraftManagementDatabase);
  CleaningSupervisor c5 = new CleaningSupervisor(aircraftManagementDatabase);
  GateConsole c6 = new GateConsole(aircraftManagementDatabase,gateInfoDatabase);
  RadarTransceiver c7 = new RadarTransceiver (aircraftManagementDatabase);


  }

}