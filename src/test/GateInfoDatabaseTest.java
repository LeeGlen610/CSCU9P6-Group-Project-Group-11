import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GateInfoDatabaseTest {
    private GateInfoDatabase gateInfoDatabase;

    @Before
    public void setUp(){
        gateInfoDatabase = new GateInfoDatabase();
    }

    @Test
    public void testCreate(){
        assertNotNull("GateInfoDatabase not created properly", gateInfoDatabase);
    }

    @Test
    public void getStatus(){
        assertEquals("The initial status must be free(0)",0, gateInfoDatabase.getStatus(0));

        gateInfoDatabase.allocate(0, 1);
        assertEquals("The status of the gate must be reserved(1)", 1, gateInfoDatabase.getStatus(0));

        gateInfoDatabase.docked(0);
        assertEquals("The status must be occupied(2)", 2, gateInfoDatabase.getStatus(0));

        gateInfoDatabase.departed(0);
        assertEquals("The status must be free(0)", 0, gateInfoDatabase.getStatus(0));
    }

    @Test
    public void getStatuses(){
        assertEquals("Get statuses array should be length 2", 2, gateInfoDatabase.getStatuses().length);
    }
}