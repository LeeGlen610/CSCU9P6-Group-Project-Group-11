// Generated by Together


import java.util.ArrayList;

/**
 * Contains an array of PassengerDetails objects - one per passenger on a flight.
 * Incoming flights supply their passenger list in their flight descriptor, and the ManagementRecord for the flight extracts the PassengerList and holds it separately.
 * Outbound flights have PassengerLists built from passenger details supplied by the gate consoles, and the list is uploaded to the aircraft as it departs in a newly built FlightDescriptor.
 * @stereotype entity
 * @url element://model:project::SAAMS/design:view:::id1un8dcko4qme4cko4sw27
 * @url element://model:project::SAAMS/design:view:::id1jkohcko4qme4cko4svww
 */
public class PassengerList {
  /**
 * The array of PassengerDetails objects.
 * @byValue
 * @clientCardinality 1
 * @directed true
 * @label contains
 * @shapeType AggregationLink
 * @supplierCardinality 0..*
 */
  /**
  * new array list with passengers
   */
  private ArrayList<PassengerDetails> details;

  public PassengerList() {
    details = new ArrayList<>();
  }
  /**
   * return the list
   */
  public ArrayList<PassengerDetails> getDetails() {
    return details;
  }

  /**
 * The given passenger is boarding.
 * Their details are recorded, in the passenger list.
 * @preconditions Status is READY_PASSENGERS
 */

  public void addPassenger(PassengerDetails details){
    this.details.add(details);
  }

  public void passengersLeft() {
    details = new ArrayList<>();
  }

}
