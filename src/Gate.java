// Generated by Together


/**
 * An individual gate's status.
 * See GateState diagram for operational details. This class has public static int identifiers for the
 * individual status codes.
 * An instance of GateInfoDatabase holds a collection of Gates, and sends the Gates messages to control/fetch their status.
 *
 * @stereotype entity
 * @url element://model:project::SAAMS/design:view:::id1un8dcko4qme4cko4sw27
 * @url element://model:project::SAAMS/design:node:::id1un8dcko4qme4cko4sw27.node61
 * @url element://model:project::SAAMS/design:view:::id2wdkkcko4qme4cko4svm2
 * @url element://model:project::SAAMS/design:view:::id1jkohcko4qme4cko4svww
 */
public class Gate {

    /**
     * Return the status code for this gate.
     *
     * @return The Status of Gate.
     */
    public int getStatus() {
        return status;
    } //END METHOD getStatus

    /**
     * The gate has been allocated to the given aircraft, identified by mCode: Change status from FREE to RESERVED and note the mCode.
     *
     * @preconditions Status must be Free
     */
    public void allocate(int mCode) {
        if (status == FREE) {
            status = RESERVED;
            this.mCode = mCode;
        } else {
            throw new IllegalStateException("Gate is not FREE");
        }//END IF
    }//END METHOD allocate

    /**
     * Change gate status from RESERVED to OCCUPIED to indicate that aircraft has now docked.
     *
     * @preconditions Status must be Reserved
     */
    public void docked() {
        if (status == RESERVED) {
            status = OCCUPIED;
        } else {
            throw new IllegalStateException("Gate is not RESERVED");
        }//END IF
    } //END METHOD docked

    /**
     * Status code representing the situation when the gate is currently allocated to no aircraft.
     */
    public static int FREE = 0;

    /**
     * Status code representing the situation when the gate has been allocated to an aircraft that has just landed, but the aircraft has not yet docked at the gate.
     */
    public static int RESERVED = 1;

    /**
     * Status code representing the situation when an aircraft is currently at the gate - either unloading passengers, being cleaned and maintained, loading new passengers or finished loading but no permission to taxi to the runway has yet been granted.
     */
    public static int OCCUPIED = 2;

    /**
     * Holds the code indicating the current status of this gate.
     */
    private int status = FREE;

    /**
     * If the gate is reserved or occupied, the mCode of the MR of the aircraft which is expected/present.
     */
    private int mCode;

    /**
     * Returns the assigned management record mCode.
     *
     * @return Assigned mCode
     */
    public int getmCode() {
        return mCode;
    }//END METHOD getmCode

    /**
     * Change status from OCCUPIED to FREE as the docked aircraft has now departed.
     *
     * @preconditions Status must be Occupied
     */
    public void departed() {
        if (status == OCCUPIED) {
            status = FREE;
        } else {
            throw new IllegalStateException("Gate is not OCCUPIED");
        }//END IF
    }//END METHOD departed
}
