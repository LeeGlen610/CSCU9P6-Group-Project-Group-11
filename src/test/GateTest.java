import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GateTest {
    /**
     * Reference to Gate
     */
    private Gate gate;

    /**
     * Example mCode for the gate
     */
    private final int mCode = 123;

    /**
     * Set up new Gate
     */
    @Before
    public void before() {
        gate = new Gate();
    }

    /**
     * Make sure the gate was created correctly
     */
    @Test
    public void testCreate(){
        assertNotNull(gate, "Gate not created properly");
    }

    /**
     * Make sure correct status is returned
     */
    @Test
    public void getStatus() {
        assertEquals(0, gate.getStatus(), "Gate initial status should be free(0)");
    }

    /**
     * Check gate status is updated correctly and mCode is allocated
     */
    @Test
    public void allocate() {
        gate.allocate(mCode);

        assertEquals(1, gate.getStatus(), "Gate status should be reserved(1)");
    }

    /**
     * Make sure gate cannot be allocated when the gate is already reserved
     */
    @Test
    public void allocateWhenReserved() {
        gate.allocate(mCode);

        assertThrows(IllegalStateException.class, () -> gate.allocate(mCode), "Attempt to allocate when gate status is reserved(1), and fails");
    }

    /**
     * Make sure gate cannot be allocated when the gate is already occupied
     */
    @Test
    public void allocateWhenOccupied() {
        gate.allocate(mCode);
        gate.docked();

        assertThrows(IllegalStateException.class, () -> gate.allocate(mCode), "Attempt to allocate when gate status is occupied(2), and fails");
    }

    /**
     * Make sure an aircraft is able to dock correctly
     */
    @Test
    public void docked() {
        gate.allocate(mCode);
        gate.docked();

        assertEquals(2, gate.getStatus(), "Gate status should be occupied(2)");
    }

    /**
     * Make sure aircraft cannot dock when the gate is free
     */
    @Test
    public void dockWhenFree() {
        assertThrows(IllegalStateException.class, () -> gate.docked(), "Attempt to dock when gate status is free(0), and fails");
    }

    /**
     * Make sure aircraft cannot dock when the gate is occupied
     */
    @Test
    public void dockWhenOccupied() {
        gate.allocate(mCode);
        gate.docked();

        assertThrows(IllegalStateException.class, () -> gate.docked(), "Attempt to dock when gate status is occupied(2), and fails");
    }

    /**
     * Make sure an aircraft is able to depart correctly
     */
    @Test
    public void departed() {
        gate.allocate(mCode);
        gate.docked();
        gate.departed();

        assertEquals(0, gate.getStatus(), "Gate status should be free(0)");
    }

    /**
     * Make sure aircraft cannot depart when the gate is free
     */
    @Test
    public void departWhenFree() {
        assertThrows(IllegalStateException.class, () -> gate.departed(), "Attempt to depart when gate status is free(0), and fails");
    }

    /**
     * Make sure aircraft cannot depart when the gate is reserved
     */
    @Test
    public void departWhenReserved() {
        gate.allocate(mCode);

        assertThrows(IllegalStateException.class, () -> gate.departed(), "Attempt to depart when gate status is reserved(1), and fails");
    }

    /**
     * Check the correct mCode is returned
     */
    @Test
    public void getmCode(){
        gate.allocate(mCode);

        assertEquals(123, gate.getmCode(), "Gate mCode should be 123");
    }
}