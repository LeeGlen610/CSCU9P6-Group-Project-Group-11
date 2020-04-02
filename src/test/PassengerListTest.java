import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PassengerListTest {

    private final String personName1 = "Person 1";
    private final String personName2 = "Person 2";
    private final String personName3 = "Person 3";

    private final PassengerList passengerList = new PassengerList();

    /**
     * After having several passengers, checks if the indexes are correct
     */
    @Test
    public void getPassenger() {
        passengerList.addPassenger(new PassengerDetails(personName1));
        passengerList.addPassenger(new PassengerDetails(personName2));
        passengerList.addPassenger(new PassengerDetails(personName3));

        assertEquals(personName1, passengerList.getDetails().get(0).getName(), "Names should match");
        assertEquals(personName2, passengerList.getDetails().get(1).getName(), "Names should match");
        assertEquals(personName3, passengerList.getDetails().get(2).getName(), "Names should match");
    }


    /**
     * Checks if the number of the passengers on board is the same as on the list.
     */
    @Test
    public void getNumberOfAllPassengers() {

        passengerList.addPassenger(new PassengerDetails(personName1));
        passengerList.addPassenger(new PassengerDetails(personName2));

        assertEquals(2, passengerList.getDetails().size(), "Should be 2 passengers");

    }

    /**
     * Checks to see if the passenger list is empty when nobody has entered a person
     */
    @Test
    public void getNumberOfAllPassengers1() {
        assertEquals(0, passengerList.getDetails().size(), "Should be no passengers");
    }

    /**
     * As the plane landed and the people went out, the list has been renewed for the next flight
     */
    @Test
    public void clearPassengerList() {
        passengerList.addPassenger(new PassengerDetails(personName1));
        passengerList.addPassenger(new PassengerDetails(personName2));
        assertEquals(2, passengerList.getDetails().size(), "Should be 2 passengers");

        passengerList.passengersLeft();
        assertEquals(0, passengerList.getDetails().size(), "Should be no passengers");

    }
}