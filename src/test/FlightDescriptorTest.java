import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlightDescriptorTest {

    private final String flightCode = "GLW512";;
    private final Itinerary itinerary = new Itinerary("Glasgow", "Stirling", "Egypt");
    private final PassengerList passengerList = new PassengerList();
    private final FlightDescriptor flightDescriptor = new FlightDescriptor(flightCode, itinerary, passengerList);

    /**
     * Check the passenger list is returned.
     */
    @Test
    public void getList(){
        assertEquals(passengerList, flightDescriptor.getList(), "Returned passenger list should match");
    }

    /**
     * Checks the itinerary is returned.
     */
    @Test
    public void getItinerary(){
        assertEquals(itinerary, flightDescriptor.getItinerary(), "Returned itinerary should match");
    }

    /**
     * Checks flight code is returned.
     */
    @Test
    public void getFlightCode(){
        assertEquals(flightCode, flightDescriptor.getFlightCode(), "Returned flight code should match");
    }
}