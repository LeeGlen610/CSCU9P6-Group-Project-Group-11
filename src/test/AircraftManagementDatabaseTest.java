import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AircraftManagementDatabaseTest {

    private final Itinerary itinerary = new Itinerary("Glasgow", "Stirling", "Egypt");
    private final Itinerary itinerary2 = new Itinerary("Madrid", "London", "null");
    private final String flightCode = "GLW512";
    private final String flightCode2 = "MAD349";
    private final PassengerList list = new PassengerList();
    private final PassengerList list2 = new PassengerList();
    private AircraftManagementDatabase aircraftManagementDatabase;
    private final FlightDescriptor flightDescriptor = new FlightDescriptor(flightCode, itinerary, list);
    private final FlightDescriptor flightDescriptor2 = new FlightDescriptor(flightCode2, itinerary2, list2);

    /**
     * Creates the database and instantiates a new Flight Descriptor.
     */
    @Before
    public void before() {
        aircraftManagementDatabase = new AircraftManagementDatabase();

        aircraftManagementDatabase.radarDetect(flightDescriptor);
        aircraftManagementDatabase.radarDetect(flightDescriptor2);
    }

    /**
     * Checks if the tests if initial state of a flight landing in Stirling is WAITING_TO_LAND.
     */
    @Test
    public void getStatusLandingStirling() {
        assertEquals("WAITING_TO_LAND", aircraftManagementDatabase.getStatus(0), "The initial state of the flight must be WAITING_TO_LAND(2)");
    }

    /**
     * Checks if the tests if initial state of a flight not landing in Stirling is WAITING_TO_LAND.
     */
    @Test
    public void getStatusNotLandingStirling() {
        assertEquals("IN_TRANSIT", aircraftManagementDatabase.getStatus(1), "The initial state of the flight must be IN_TRANSIT(1)");
    }

    /**
     * Checks if the status is actually changed.
     */
    @Test
    public void setStatus() {
        aircraftManagementDatabase.setStatus(0, 3);

        assertEquals("GROUND_CLEARANCE_GRANTED", aircraftManagementDatabase.getStatus(0), "Status not properly set to GROUND_CLEARANCE_GRANTED(3)");
    }

    /**
     * Checks if the flight is successfully retrieved.
     */
    @Test
    public void getFlightCode() {
        assertEquals(flightCode, aircraftManagementDatabase.getFlightCode(0));
    }

    /**
     * Checks if a newly detected flight is successfully added to the database.
     */
    @Test
    public void radarDetect1() {
        aircraftManagementDatabase.radarDetect(flightDescriptor);

        String flightCode = aircraftManagementDatabase.getFlightCode(2);

        assertEquals(flightCode, flightCode, "New aircraft code should match the one it was created.");
    }

    /**
     * Checks if the record is removed from the database after radar contact is lost.
     */
    @Test
    public void radarLostContact() {
        aircraftManagementDatabase.radarLostContact(1);

        assertThrows(NullPointerException.class, () -> aircraftManagementDatabase.getFlightCode(1));
    }

    /**
     * Checks if the the gate number is successfully set.
     */
    @Test
    public void taxiTo() {
        final int GATE_NUMBER = 2;

        aircraftManagementDatabase.setStatus(0, 5);
        aircraftManagementDatabase.taxiTo(0, GATE_NUMBER);

        assertEquals(
                GATE_NUMBER,
                aircraftManagementDatabase.getMR(0).getGateNumber(),
                "Target gate number not the same as the one it was set to."
        );
    }

    /**
     * Checks if a new passenger is successfully added to the passenger list.
     */
    @Test
    public  void addPassenger() {
        final String passenger1 = "Person 1";

        aircraftManagementDatabase.setStatus(0, 14);

        aircraftManagementDatabase.addPassenger(0, new PassengerDetails(passenger1));

        assertEquals(passenger1, aircraftManagementDatabase.getPassengerList(0).getDetails().get(0).getName(), "Passenger1 must match.");
    }

    /**
     * Checks if the passenger lists is successfully retrieved.
     */
    @Test
    public void getPassengerList() {
        PassengerList passengerList = aircraftManagementDatabase.getPassengerList(0);

        assertEquals(list, passengerList, "Lists must match.");
    }

    /**
     * Checks if the itinerary is successfully retrieved.
     */
    @Test
    public void getItinerary() {
        Itinerary itinerary = aircraftManagementDatabase.getItinerary(0);

        assertEquals(itinerary, itinerary, "Itinerary must match.");
    }
}