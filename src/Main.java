

/**
 * The Main class.
 * <p>
 * The principal component is the usual main method required by Java application to launch the application.
 * <p>
 * Instantiates the databases.
 * Instantiates and shows all the system interfaces as Frames.
 *
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

        PassengerList passengerList = new PassengerList();

        passengerList.addPassenger(new PassengerDetails("Margarita Fogou"));
        passengerList.addPassenger(new PassengerDetails("Lee Glen"));
        passengerList.addPassenger(new PassengerDetails("Jon Law"));
        passengerList.addPassenger(new PassengerDetails("Mikey Lemetti"));

        aircraftManagementDatabase.radarDetect(new FlightDescriptor("GLW512", new Itinerary("Glasgow", "Stirling", "Egypt"), passengerList));
        aircraftManagementDatabase.radarDetect(new FlightDescriptor("STR202", new Itinerary("Florida", "Stirling", null), passengerList));
        aircraftManagementDatabase.radarDetect(new FlightDescriptor("STR207", new Itinerary("Stirling", "India", null), passengerList));
        aircraftManagementDatabase.radarDetect(new FlightDescriptor("MSW210", new Itinerary("Moscow", "Stirling", null), passengerList));

        GOC c1 = new GOC(aircraftManagementDatabase, gateInfoDatabase);
        LATC c2 = new LATC(aircraftManagementDatabase);
        PublicInfo c3 = new PublicInfo(aircraftManagementDatabase);
        MaintenanceInspector c4 = new MaintenanceInspector(aircraftManagementDatabase);
        RefuellingSupervisor c5 = new RefuellingSupervisor(aircraftManagementDatabase);
        CleaningSupervisor c6 = new CleaningSupervisor(aircraftManagementDatabase);
        for (int gateNumber = 0; gateNumber < gateInfoDatabase.maxGateNumber; gateNumber++) {
            GateConsole c7 = new GateConsole(aircraftManagementDatabase, gateInfoDatabase, gateNumber);
        } //END FOR
        RadarTransceiver c8 = new RadarTransceiver(aircraftManagementDatabase);
    }//END METHOD main
}//END CLASS Main