import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GateInfoDatabaseTest {

    private final int gate1 = 0;
    private final int gate2 = 1;

    private final int mCode1 = 123;
    private final int mCode2 = 456;

    private GateInfoDatabase gateInfoDatabase;

    @Before
    public void before(){
        gateInfoDatabase = new GateInfoDatabase();
    }

    @Test
    public void getStatusOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> gateInfoDatabase.getStatus(gateInfoDatabase.maxGateNumber),
                "Can't have more than 2 gates");
    }

    @Test
    public void checkInitialGatesStatus(){
        for(int i = 0; i < gateInfoDatabase.maxGateNumber; i++){
            assertEquals(0, gateInfoDatabase.getStatus(i),
                    "All gates status must be free when initialised");
        }
    }

    @Test
    public void checkGateOneStatus(){
        assertEquals(0, gateInfoDatabase.getStatus(gate1),
                "Gate 1 status should be free(0)");
    }

    @Test
    public void checkGateTwoStatus(){
        assertEquals(0, gateInfoDatabase.getStatus(gate2),
                "Gate 2 status should be free(0)");
    }

    @Test
    public void getStatus(){
        int[] getStatus = gateInfoDatabase.getStatuses();

        assertEquals(2, getStatus.length, "There should be 2 gate statuses");
    }

    @Test
    public void allocate(){
        gateInfoDatabase.allocate(gate1, mCode1);

        assertEquals(1, gateInfoDatabase.getStatus(gate1), "Gate 1 status should be reserved(1)");
    }

    @Test
    public void alreadyAllocated(){
        gateInfoDatabase.allocate(gate1, mCode1);

        assertThrows(IllegalStateException.class,
                () -> gateInfoDatabase.allocate(gate1, mCode2),
                "Attempt to allocate when gate is already reserved(1)");
    }

    @Test
    public void docked(){
        gateInfoDatabase.allocate(gate1, mCode1);
        gateInfoDatabase.docked(gate1);

        assertEquals(2, gateInfoDatabase.getStatus(gate1), "Gate 1 status should be occupied(2)");
    }

    @Test
    public void dockWhenFree(){
        assertEquals(0, gateInfoDatabase.getStatus(gate1), "Gate 1 status should be free(0)");

        assertThrows(IllegalStateException.class,
                () -> gateInfoDatabase.docked(gate1),
                "Attempt to dock when gate status is free, and fails");
    }

    @Test
    public void departed(){
        gateInfoDatabase.allocate(gate1, mCode1);
        gateInfoDatabase.docked(gate1);
        gateInfoDatabase.departed(gate1);

        assertEquals(0, gateInfoDatabase.getStatus(gate1), "Gate 1 status should be free(0)");
    }

    @Test
    public void departedWhenFree(){
        assertEquals(0, gateInfoDatabase.getStatus(gate1), "Gate 1 status should be free(0)");

        assertThrows(IllegalStateException.class,
                () -> gateInfoDatabase.departed(gate1),
                "Attempt to depart when gate status is free(0), and fails");
    }

    @Test
    public void assignedmCode(){
        gateInfoDatabase.allocate(gate1, mCode1);

        assertEquals(123, gateInfoDatabase.assignedmCode(gate1), "mCode for gate 1 should be 123");
    }
}