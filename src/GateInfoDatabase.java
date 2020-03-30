
// Generated by Together


import java.util.Observable;

/**
 * A central database ("model" class):
 * It is intended that there will be only one instance of this class.
 * Maintains an array of Gates.
 * Each gate's number is its index in the array (0..)
 * GateConsoles and GroundOperationsControllers are controllers of this class: sending it messages when the gate status is to be changed.
 * GateConsoles and GroundOperationsControllers also register as observers of this class. Whenever a change occurs to any gate, the obervers are notified.
 * @stereotype model
 * @url element://model:project::SAAMS/design:view:::id2wdkkcko4qme4cko4svm2
 * @url element://model:project::SAAMS/design:view:::id1jkohcko4qme4cko4svww
 * @url element://model:project::SAAMS/design:node:::id1un8dcko4qme4cko4sw27.node61
 * @url element://model:project::SAAMS/design:view:::id1un8dcko4qme4cko4sw27
 */
public class GateInfoDatabase extends Observable {
  /**
 * Holds one gate object per gate in the airport.
 * @clientCardinality 1
 * @directed true
 * @label contains
 * @link aggregationByValue
 * @supplierCardinality 0..*
 */
  private Gate[] gates;


  /**
   *  A constant: the number of aircraft gates at the airport.
   */
  public int maxGateNumber = 2;

    public GateInfoDatabase() {
        gates = new Gate[maxGateNumber];
        for (int i = 0; i < maxGateNumber; i++) {
            gates[i] = new Gate();
        }
    }

    /**
 * Obtain and return the status of the given gate identified by the gateNumber parameter.
 */
  public int getStatus(int gateNumber){
	  return gates[gateNumber].getStatus();
  }


  public Gate getGate(int gateNumber){
      return gates[gateNumber];
  }

/**
 * Returns an array containing the status of all gates.
 * For data collection by the GOC.
 */
  public int[] getStatuses(){
	  int[] statuses = new int[maxGateNumber];
	  for(int i = 0; i < maxGateNumber; i++) {
		  statuses[i] = gates[i].getStatus();
	  }
	  return statuses;
  }

/**
 * Forward a status change request to the given gate identified by the gateNumber parameter. Called to allocate a free gate to the aircraft identified by mCode.
 */
  public void allocate(int gateNumber, int mCode){
	  gates[gateNumber].allocate(mCode);
      setChanged();
      notifyObservers();
  }

/**
 * Forward a status change request to the given gate identified by the gateNumber parameter. Called to indicate that the expected aircraft has arrived at the gate.
 */
  public void docked(int gateNumber){
	  gates[gateNumber].docked();
      setChanged();
      notifyObservers();
  }

/**
 * Forward a status change request to the given gate identified by the gateNumber parameter. Called to indicate that the aircraft has departed and that the gate is now free.
 */
  public void departed(int gateNumber){
	  gates[gateNumber].departed();
      setChanged();
      notifyObservers();
  }

    /**
     * Returns the assigned mCode for the assigned management record.
     *
     * @param gateNumber The gate number
     * @return //The mCode of the assigned management record
     */
  public int assignedmCode(int gateNumber){
      return gates[gateNumber].getmCode();
  }
}
