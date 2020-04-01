import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GateTest {

    private Gate gate;
    private final int mCode = 123;

    @Before
    public void before() {
        gate = new Gate();
    }

    @Test
    public void testCreate(){
        assertNotNull(gate, "Gate not created properly");
    }

    @Test
    public void getStatus() {
        assertEquals(0, gate.getStatus(), "Gate initial status should be free(0)");
    }

    @Test
    public void allocate() {
        gate.allocate(mCode);

        assertEquals(1, gate.getStatus(), "Gate status should be reserved(1)");
    }

    @Test
    public void allocateWhenReserved() {
        gate.allocate(mCode);

        assertThrows(IllegalStateException.class, () -> gate.allocate(mCode), "Attempt to allocate when gate status is reserved(1), and fails");
    }

    @Test
    public void allocateWhenOccupied() {
        gate.allocate(mCode);
        gate.docked();

        assertThrows(IllegalStateException.class, () -> gate.allocate(mCode), "Attempt to allocate when gate status is occupied(2), and fails");
    }

    @Test
    public void docked() {
        gate.allocate(mCode);
        gate.docked();

        assertEquals(2, gate.getStatus(), "Gate status should be occupied(2)");
    }

    @Test
    public void dockWhenFree() {
        assertThrows(IllegalStateException.class, () -> gate.docked(), "Attempt to dock when gate status is free(0), and fails");
    }

    @Test
    public void dockWhenOccupied() {
        gate.allocate(mCode);
        gate.docked();

        assertThrows(IllegalStateException.class, () -> gate.docked(), "Attempt to dock when gate status is occupied(2), and fails");
    }

    @Test
    public void departed() {
        gate.allocate(mCode);
        gate.docked();
        gate.departed();

        assertEquals(0, gate.getStatus(), "Gate status should be free(0)");
    }

    @Test
    public void departWhenFree() {
        assertThrows(IllegalStateException.class, () -> gate.departed(), "Attempt to depart when gate status is free(0), and fails");
    }

    @Test
    public void departWhenReserved() {
        gate.allocate(mCode);

        assertThrows(IllegalStateException.class, () -> gate.departed(), "Attempt to depart when gate status is reserved(1), and fails");
    }

    @Test
    public void getmCode(){
        gate.allocate(mCode);

        assertEquals(123, gate.getmCode(), "Gate mCode should be 123");
    }
}