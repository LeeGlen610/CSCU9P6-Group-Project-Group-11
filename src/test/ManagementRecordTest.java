import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagementRecordTest {

    private final int gateNumber = 1;
    private final Itinerary itinerary = new Itinerary("Glasgow", "Stirling", "Egypt");
    private final String flightCode = "GLW512";
    private final PassengerList list = new PassengerList();
    private final FlightDescriptor flightDescriptor = new FlightDescriptor(flightCode, itinerary, list);

    private ManagementRecord MR;

    /**
     * Sets up a new management record and sets the status to FREE(0)
     */
    @Before
    public void before(){
        MR = new ManagementRecord();

        MR.setStatus(0);
    }

    /**
     * Get the status as an int.
     */
    @Test
    public void getIntStatus(){
        assertEquals(0, MR.getStatus(), "Status should be FREE(0)");
    }

    /**
     * Get the status as a String.
     */
    @Test
    public void getStringStatus(){
        assertEquals("FREE", MR.getStatus(0), "Status should be FREE(0)");
    }

    /**
     * Check the status is set correctly.
     */
    @Test
    public void setStatus(){
        MR.setStatus(1);

        assertEquals(1, MR.getStatus(), "Status should be IN_TRANSIT(1)");
    }

    /**
     * Check radar detect adds a flight descriptor to the management record.
     */
    @Test
    public void testRadarDetect(){
        MR.radarDetect(flightDescriptor);

        assertEquals(flightCode, MR.getFlightCode(), "Returned flight code must match");
        assertEquals(itinerary, MR.getItinerary(), "Returned itinerary must match");
        assertEquals(list, MR.getPassengerList(), "Returned passenger list must match");
    }

    /**
     * Check taxi to allocates the correct gate.
     */
    @Test
    public void testTaxiTo(){
        MR.setStatus(5);
        MR.taxiTo(gateNumber);

        assertEquals(6, MR.getStatus(), "Status should be TAXIING(6)");
        assertEquals(1, MR.getGateNumber(), "Gate number should be 0");
    }

    /**
     * Check radar lost connection removes the flight descriptor from the management record.
     */
    @Test
    public void testRadarLostContact(){
        MR.radarDetect(flightDescriptor);
        MR.setStatus(18);
        MR.radarLostContact();

        assertEquals(0, MR.getStatus(), "Status should be FREE(0)");
        assertEquals("", MR.getFlightCode(), "Status should be empty");
        assertEquals(null, MR.getPassengerList(), "Passenger list should be empty");
        assertEquals(-1, MR.getGateNumber(), "Should not be assigned a gate");
    }

    /**
     * Check faults found changes the status.
     */
    @Test
    public void faultsFound(){
        MR.setStatus(8);
        MR.faultsFound("Test description");

        assertEquals(9, MR.getStatus(), "Status should be READY_CLEAN_AND_MAINT(9)");
    }

    /**
     * Check faults found changes the status.
     */
    @Test
    public void faultsFound2(){
        MR.setStatus(10);
        MR.faultsFound("Test description");

        assertEquals(12, MR.getStatus(), "Status should be CLEAN_AWAIT_MAINT(12)");
    }

    /**
     * Check passenger has been added properly.
     */
    @Test
    public void addPassenger() {
        final String passenger1 = "PersonA";
        MR.radarDetect(flightDescriptor);
        MR.setStatus(14);

        MR.addPassenger(new PassengerDetails(passenger1));

        assertEquals(passenger1, MR.getPassengerList().getDetails().get(0).getName(), "Passenger1 must match.");
    }
}