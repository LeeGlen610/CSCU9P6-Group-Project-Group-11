
// Generated by Together


import java.util.Observable;

/**
 * A central database ("model" class):
 * It is intended that there will be only one instance of this class.
 * Maintains an array of ManagementRecords (MRs), one per potential visiting aircraft. Some MRs hold information about aircraft currently being managed by SAAMS, and some may have the status "Free".
 * The index of each ManagementRecord in the array is its "management code" ("mCode"), and the mCode of any particular visiting aircraft's ManagementRecord must remain fixed once it is allocated.
 * Many classes register as observers of this class, and are notified whenever any aircraft's (MR's) state changes.
 *
 * @stereotype model
 * @url element://model:project::SAAMS/design:view:::id4tg7xcko4qme4cko4swuu
 * @url element://model:project::SAAMS/design:node:::id4tg7xcko4qme4cko4swuu.node149
 * @url element://model:project::SAAMS/design:view:::id1bl79cko4qme4cko4sw5j
 * @url element://model:project::SAAMS/design:view:::idwwyucko4qme4cko4sgxi
 * @url element://model:project::SAAMS/design:node:::id2wdkkcko4qme4cko4svm2.node39
 * @url element://model:project::SAAMS/design:view:::id2wdkkcko4qme4cko4svm2
 * @url element://model:project::SAAMS/design:node:::id3oolzcko4qme4cko4sx40.node169
 * @url element://model:project::SAAMS/design:view:::id2fh3ncko4qme4cko4swe5
 * @url element://model:project::SAAMS/design:view:::id28ykdcko4qme4cko4sx0e
 * @url element://model:project::SAAMS/design:node:::id15rnfcko4qme4cko4swib.node107
 * @url element://model:project::SAAMS/design:view:::id15rnfcko4qme4cko4swib
 * @url element://model:project::SAAMS/design:view:::id3oolzcko4qme4cko4sx40
 */
public class AircraftManagementDatabase extends Observable {

    /**
     * Constructor for AircraftManagementDatabase.
     */
    public AircraftManagementDatabase() {
        MRs = new ManagementRecord[maxMRs];
    }//END CONSTRUCTOR AircraftManagementDatabase

    /**
     * Return the status of the MR with the given mCode supplied as a parameter.
     *
     * @param mCode The MR's Index.
     * @return The Status of The MR.
     */
    public String getStatus(int mCode) {
        return MRs[mCode].getStatus(MRs[mCode].getStatus());
    }//END METHOD getStatus

    /**
     * The array of ManagementRecords. Attribute maxMRs specifies how large this array should be.
     * Initialize to a collection of MRs each in the FREE state.
     * Note: This array could be replaced by another other suitable collection data structure.
     *
     * @byValue
     * @clientCardinality 1
     * @directed true
     * @label contains
     * @shapeType AggregationLink
     * @supplierCardinality 0..*
     */
    private ManagementRecord[] MRs;

    /**
     * The size of the array MRs holding ManagementRecords.<br /><br />In this simple system 10 will do!
     */
    public int maxMRs = 10;


    /**
     * Forward a status change request to the MR given by the mCode supplied as a parameter. Parameter newStatus is the requested new status. No effect is expected if the current status is not a valid preceding status. This operation is appropriate when the status change does not need any additional information to be noted. It is present instead of a large collection of public operations for requesting specific status changes.
     *
     * @param mCode     The MR's Index.
     * @param newStatus The New Status Being Assigned to The MR.
     */
    public void setStatus(int mCode, int newStatus) {
        MRs[mCode].setStatus(newStatus);
        setChanged();
        notifyObservers();
    } //END METHOD setStatus

    /**
     * Return the flight code from the given MR supplied as a parameter.
     * The request is forwarded to the MR.
     *
     * @param mCode The MR's Index.
     * @return The Flight Code of The Flight.
     */
    public String getFlightCode(int mCode) {
        return MRs[mCode].getFlightCode();
    } //END METHOD getFlightCode

    /**
     * Returns an array of mCodes:
     * Just the mCodes of those MRs with the given status supplied as a parameter.
     * Principally for call by the various interface screens.
     *
     * @param statusCode The Status to Search For.
     * @return The MR's With The Same Status.
     * @throws NullPointerException If The MR Array isn't Initialised.
     */
    public int[] getWithStatus(int statusCode) throws NullPointerException {
        int[] mCodesStatus = new int[maxMRs];
        int counter = 1;
        for (int i = 0; i < maxMRs; i++) {
            if (MRs[i].getStatus() == statusCode) {
                mCodesStatus[counter] = i;
                counter++;
            }//END IF
        }//END FOR
        return mCodesStatus;
    }//END METHOD getWithStatus

    /**
     * Returns A MR Based of an Identifier.
     *
     * @param recordIdentifier The Index of an MR.
     * @return The Management Record That's Been Requested.
     */
    public ManagementRecord getMR(int recordIdentifier) {
        return MRs[recordIdentifier];
    } //END METHOD getMR

    /**
     * The radar has detected a new aircraft, and has obtained flight descriptor fd from it.
     * <p>
     * This operation finds a currently FREE MR and forwards the radarDetect request to it for recording.
     *
     * @param fd The Flight That Has Been Detected.
     */
    public void radarDetect(FlightDescriptor fd) {
        int freeMR = 0;
        for (int i = 0; i < MRs.length; i++) {
            if (MRs[i] == null) {
                freeMR = i;
                break;
            }
        }
        MRs[freeMR] = new ManagementRecord();
        MRs[freeMR].setStatus(0);
        MRs[freeMR].radarDetect(fd);
        setChanged();
        notifyObservers();
    } //END METHOD radarDetect

    /**
     * The aircraft in the MR given by mCode supplied as a parameter has departed from the local airspace. The message is forwarded to the MR, which can then delete/archive its contents and become FREE.
     *
     * @param mCode The MR's Index.
     */
    public void radarLostContact(int mCode) {
        MRs[mCode] = null;
        setChanged();
        notifyObservers();
    } //END METHOD radarLostContact.

    /**
     * A GOC has allocated the given gate to the aircraft with the given mCode supplied as a parameter for unloading passengers. The message is forwarded to the given MR for status update.
     *
     * @param mCode      The MR's Index.
     * @param gateNumber The Gate Number That The Aircraft Has Been Assigned To.
     */
    public void taxiTo(int mCode, int gateNumber) {
        MRs[mCode].taxiTo(gateNumber);
        setChanged();
        notifyObservers();
    } //END METHOD radarLostContact.

    /**
     * Returns the gate number assigned to a MR.
     *
     * @param mCode The MR's Index.
     * @return The Gate Number.
     */
    public int getGateNumber(int mCode) {
        return MRs[mCode].getGateNumber();
    } //END METHOD getGateNumber

    /**
     * The Maintenance Supervisor has reported faults with the given description in the aircraft with the given mCode. The message is forwarded to the given MR for status update.
     *
     * @param mCode       The MR's Index.
     * @param description The Description of The Fault.
     */
    public void faultsFound(int mCode, String description) {
        MRs[mCode].faultsFound(description);
        setChanged();
        notifyObservers();
    } //END METHOD faultsFound

    /**
     * The given passenger is boarding the aircraft with the given mCode. Forward the message to the given MR for recording in the passenger list.
     *
     * @param mCode   The MR's Index.
     * @param details The Passenger's Details.
     */
    public void addPassenger(int mCode, PassengerDetails details) {
        MRs[mCode].addPassenger(details);
        setChanged();
        notifyObservers();
    }//END METHOD addPassenger

    /**
     * Return the PassengerList of the aircraft with the given mCode.
     *
     * @param mCode The MR's Index.
     * @return The Passengers Of The MR.
     */
    public PassengerList getPassengerList(int mCode) {
        return MRs[mCode].getPassengerList();
    }//END METHOD getPassengerList

    /**
     * Clears the passengers from the Management Record.
     *
     * @param mCode The MR's Index.
     */
    public void clearPassengers(int mCode) {
        MRs[mCode].getPassengerList().passengersLeft();
        setChanged();
        notifyObservers();
    } //END METHOD clearPassenger

    /**
     * Return the Itinerary of the aircraft with the given mCode.
     *
     * @param mCode The MR's Index.
     * @return The Itinerary of The Flight.
     */
    public Itinerary getItinerary(int mCode) {
        return MRs[mCode].getItinerary();
    } //END METHOD getItinerary

} //END CLASS AircraftManagementDatabase
