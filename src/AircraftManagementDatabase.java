
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

    public AircraftManagementDatabase() {
        MRs = new ManagementRecord[maxMRs];
    }

    /**
     * Return the status of the MR with the given mCode supplied as a parameter.
     */
    public int getStatus(int mCode) {
        return MRs[mCode].getStatus();
    }

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
     */
    public void setStatus(int mCode, int newStatus) {
        MRs[mCode].setStatus(newStatus);
    }

    /**
     * Return the flight code from the given MR supplied as a parameter.
     * The request is forwarded to the MR.
     */
    public String getFlightCode(int mCode) {
        return MRs[mCode].getFlightCode();
    }

    /**
     * Returns an array of mCodes:
     * Just the mCodes of those MRs with the given status supplied as a parameter.
     * Principally for call by the various interface screens.
     */
    public int[] getWithStatus(int statusCode) {
        int[] mCodesStatus = new int[maxMRs];
      for (int i = 0; i < maxMRs; i++) {
        if (MRs[i].getStatus() == statusCode){
          mCodesStatus[i] = i;
        }
      }
      return mCodesStatus;
    }

    /**
     * The radar has detected a new aircraft, and has obtained flight descriptor fd from it.
     * <p>
     * This operation finds a currently FREE MR and forwards the radarDetect request to it for recording.
     */
    public void radarDetect(FlightDescriptor fd) {
      for (int i = 0; i < MRs.length; i++) {
        if (MRs[i].getStatus() == 0){
          MRs[i].radarDetect(fd);
        }
      }
    }

    /**
     * The aircraft in the MR given by mCode supplied as a parameter has departed from the local airspace. The message is forwarded to the MR, which can then delete/archive its contents and become FREE.
     */
    public void radarLostContact(int mCode) {
      MRs[mCode].radarLostContact();
    }

    /**
     * A GOC has allocated the given gate to the aircraft with the given mCode supplied as a parameter for unloading passengers. The message is forwarded to the given MR for status update.
     */
    public void taxiTo(int mCode, int gateNumber) {
      MRs[mCode].taxiTo(gateNumber);
    }

    /**
     * The Maintenance Supervisor has reported faults with the given description in the aircraft with the given mCode. The message is forwarded to the given MR for status update.
     */
    public void faultsFound(int mCode, String description) {
      MRs[mCode].faultsFound(description);
    }

    /**
     * The given passenger is boarding the aircraft with the given mCode. Forward the message to the given MR for recording in the passenger list.
     */
    public void addPassenger(int mCode, PassengerDetails details) {
        MRs[mCode].addPassenger(details);
    }

    /**
     * Return the PassengerList of the aircraft with the given mCode.
     */
    public PassengerList getPassengerList(int mCode) {
        return MRs[mCode].getPassengerList();
    }

    /**
     * Return the Itinerary of the aircraft with the given mCode.
     */
    public Itinerary getItinerary(int mCode) {
        return MRs[mCode].getItinerary();
    }

}
