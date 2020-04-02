// Generated by Together


/**
 * An individual aircraft management record:
 * Either FREE or models an aircraft currently known to SAAMS.
 * See MRState diagram for operational details, and written documentation.
 * This class has public static int identifiers for the individual status codes.
 * An MR may be "FREE", or may contain a record of the status of an individual aircraft under the management of SAAMS.
 * An instance of AircraftManagementDatabase holds a collection of ManagementRecords, and sends the ManagementRecords messages to control/fetch their status.
 *
 * @stereotype entity
 * @url element://model:project::SAAMS/design:node:::id15rnfcko4qme4cko4swib.node107
 * @url element://model:project::SAAMS/design:view:::id3oolzcko4qme4cko4sx40
 * @url element://model:project::SAAMS/design:view:::id4tg7xcko4qme4cko4swuu
 * @url element://model:project::SAAMS/design:node:::id4tg7xcko4qme4cko4swuu.node152
 * @url element://model:project::SAAMS/design:node:::id3oolzcko4qme4cko4sx40.node171
 * @url element://model:project::SAAMS/design:view:::id2wdkkcko4qme4cko4svm2
 * @url element://model:project::SAAMS/design:view:::id15rnfcko4qme4cko4swib
 * @url element://model:project::SAAMS/design:node:::id2wdkkcko4qme4cko4svm2.node41
 */
public class ManagementRecord {

    /**
     * Status code: This MR is currently not managing any aircraft information
     * <p>
     * See MRState diagram.
     */
    public static int FREE = 0;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int IN_TRANSIT = 1;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int WANTING_TO_LAND = 2;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int GROUND_CLEARANCE_GRANTED = 3;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int LANDING = 4;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int LANDED = 5;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int TAXIING = 6;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int UNLOADING = 7;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int READY_CLEAN_AND_MAINT = 8;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int FAULTY_AWAIT_CLEAN = 9;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int OK_AWAIT_CLEAN = 11;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int CLEAN_AWAIT_MAINT = 10;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int AWAIT_REPAIR = 12;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int READY_REFUEL = 13;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int READY_PASSENGERS = 14;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int READY_DEPART = 15;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int AWAITING_TAXI = 16;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int AWAITING_TAKEOFF = 17;

    /**
     * Status code
     * <p>
     * See MRState diagram.
     */
    public static int DEPARTING_THROUGH_LOCAL_AIRSPACE = 18;

    /**
     * The status code for this ManagementRecord.
     */
    private int status;

    /**
     * The gate number allocated to this aircraft, when there is one.
     */
    private int gateNumber;

    /**
     * A short string identifying the flight:
     * <p>
     * Usually airline abbreviation plus number, e.g. BA127.
     * Obtained from the flight descriptor when the aircraft is first detected.
     * <p>
     * This is the code used in timetables, and is useful to show on public information screens.
     */
    private String flightCode;

    /**
     * Holds the aircraft's itinerary.
     * Downloaded from the flight descriptor when the aircraft is first detected.
     *
     * @clientCardinality 1
     * @directed true
     * @label contains
     * @shapeType AggregationLink
     * @supplierCardinality 1
     */
    private Itinerary itinerary;

    /**
     * The list of passengers on the aircraft.
     * Incoming flights supply their passenger list in their flight decsriptor.
     * Outbound flights have passenger lists built from passenger details supplied by the gate consoles.
     *
     * @clientCardinality 1
     * @directed true
     * @label contains
     * @shapeType AggregationLink
     * @supplierCardinality 1
     */
    private PassengerList passengerList;

    /**
     * Contains a description of what is wrong with the aircraft if it is found to be faulty during maintenance inspection.
     */
    private String faultDescription;


    /**
     * Request to set the MR into a new status.
     * <p>
     * Only succeeds if the state change conforms to the MRState diagram.
     * <p>
     * This is a general purpose state change request where no special details accompany the state change.
     * [Special status changers are, for example, "taxiTo", where a gate number is supplied.]
     *
     * @param newStatus The Status To Be Changed To.
     * @preconditions Valid transition requested
     */
    public void setStatus(int newStatus) {
        status = newStatus;
    } // END METHOD setStatus

    /**
     * Return the status code of this MR.
     * @return The Status code.
     */
    public int getStatus() {
        return status;
    }// END METHOD getStatus


    /**
     * Returns the status text of the MR.
     *
     * @param status The Current Status of The MR.
     * @return The status based of the Management Record's status code.
     */
    public String getStatus(int status) {
        switch (status) {
            case 0:
                return "FREE";
            case 1:
                return "IN_TRANSIT";
            case 2:
                return "WAITING_TO_LAND";
            case 3:
                return "GROUND_CLEARANCE_GRANTED";
            case 4:
                return "LANDING";
            case 5:
                return "LANDED";
            case 6:
                return "TAXING";
            case 7:
                return "UNLOADING";
            case 8:
                return "READY_CLEAN_AND_MAINT";
            case 9:
                return "FAULTY_AWAIT_CLEAN";
            case 10:
                return "CLEAN_AWAIT_MAINT";
            case 11:
                return "OK_AWAIT_CLEAN";
            case 12:
                return "AWAIT_REPAIR";
            case 13:
                return "READY_REFUEL";
            case 14:
                return "READY_PASSENGERS";
            case 15:
                return "READY_DEPART";
            case 16:
                return "AWAITING_TAXI";
            case 17:
                return "AWAITING_TAKEOFF";
            case 18:
                return "DEPARTING_THROUGH_LOCAL_AIRSPACE";
            default:
                return "UNKNOWN";
        }//END SWITCH
    }//END METHOD getStatus

    /**
     * Return the flight code of this MR.
     * @return The Flight Code.
     */
    public String getFlightCode() {
        return flightCode;
    }//END METHOD getFlightCode

    /**
     * Sets up the MR with details of newly detected flight
     * <p>
     * Status must be FREE now, and becomes either IN_TRANSIT or WANTING_TO_LAND depending on the details in the flight descriptor.
     *
     * @param fd The Flight That Has Been Detected.
     * @preconditions Status is FREE
     */
    public void radarDetect(FlightDescriptor fd) {
        if (status == 0) {
            flightCode = fd.getFlightCode();
            passengerList = fd.getList();
            itinerary = fd.getItinerary();
            if (itinerary.getTo().equals("Stirling")) {
                status = 2;
            } else {
                status = 1;
            }//END IF/ELSE
        }//END IF
    }//END METHOD radarDetect

    /**
     * Returns The Gate Number Assigned To The Management Record.
     *
     * @return The Gate Number
     */
    public int getGateNumber() {
        return gateNumber;
    }//END METHOD getGateNumber

    /**
     * This aircraft has departed from local airspace.
     * <p>
     * Status must have been either IN_TRANSIT or DEPARTING_THROUGH_LOCAL_AIRSPACE, and becomes FREE (and the flight details are cleared).
     *
     * @preconditions Status is IN_TRANSIT or DEPARTING_THROUGH_LOCAL_AIRSPACE
     */
    public void radarLostContact() {
        if (status == 1 || status == 18) {
            status = 0;
            flightCode = "";
            passengerList = null;
            faultDescription = "";
            itinerary = null;
            gateNumber = -1;
        }//END IF
    }// END METHOD radarLostContact

    /**
     * GOC has allocated the given gate for unloading passengers.
     * <p>
     * The gate number is recorded.The status must have been LANDED and becomes TAXIING.
     *
     * @param gateNumber The Gate Number To Taxi To.
     * @preconditions Status is LANDED
     */
    public void taxiTo(int gateNumber) {
        if (status == 5) {
            status = 6;
            this.gateNumber = gateNumber;
        } //END METHOD taxiTo
    }//END METHOD taxiTo

    /**
     * The Maintenance Supervisor has reported faults.
     * <p>
     * The problem description is recorded.
     * <p>
     * The status must have been READY_FOR_CLEAN_MAINT or CLEAN_AWAIT_MAINT and becomes FAULTY_AWAIT_CLEAN or AWAIT_REPAIR respectively.
     *
     * @param description The Fault That Has Been Found.
     * @preconditions Status is READY_FOR_CLEAN_MAINT or CLEAN_AWAIT_MAINT
     */
    public void faultsFound(String description) {
        if (status == 8) {
            status = 9;
            this.faultDescription = description;
        } else if (status == 10) {
            status = 12;
            this.faultDescription = description;
        }//END IF/ELSE
    }//END METHOD faultsFound

    /**
     * The given passenger is boarding this aircraft.
     * <p>
     * Their details are recorded in the passengerList.
     * <p>
     * For this operation to be applicable, the status must be READY_PASSENGERS, and it doesn't change.
     *
     * @param details The Passenger Details.
     * @preconditions Status is READY_PASSENGERS
     */
    public void addPassenger(PassengerDetails details) {
        passengerList.addPassenger(details);
    } // END METHOD addPassenger

    /**
     * Return the entire current PassengerList.
     * @return The Passenger List.
     */
    public PassengerList getPassengerList() {
        return passengerList;
    } //END METHOD getPassengerList

    /**
     * Return the aircraft's Itinerary.
     * @return THe Aircraft's Itinerary.
     */
    public Itinerary getItinerary() {
        return itinerary;
    }//END METHOD getItinerary


    /**
     * Returns where the plane came from, where it's landing
     * and depending on if the airplane will be going to another destination where it will go next.
     *
     * @return The plane destination information.
     */
    @Override
    public String toString() {
        if (!(itinerary.getNext() == null)) {
            return flightCode + " - From: " + itinerary.getFrom() + " Landing At: " + itinerary.getTo() + " Next Stop: " + itinerary.getNext();
        } else {
            return flightCode + " - From: " + itinerary.getFrom() + " Landing At: " + itinerary.getTo();
        } //END IF/ELSE
    } //END METHOD toString
} //END CLASS ManagementRecord
