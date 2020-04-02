import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItineraryTest {
    private final String from = "Glasgow";
    private final String to = "Stirling";
    private final String next = "Egypt";
    private final Itinerary itinerary = new Itinerary(from,to,next);

    /**
     * Check correct location is returned.
     */
    @Test
    void getFrom(){
        assertEquals(from, itinerary.getFrom(), "Is correct from location");
    }

    /**
     * Check correct location is returned.
     */
    @Test
    void getTo(){
        assertEquals(to, itinerary.getTo(), "Is correct to location");
    }

    /**
     * Check correct location is returned.
     */
    @Test
    void getNext(){
        assertEquals(next, itinerary.getNext(), "Is correct next location");
    }
}