import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GateTest {

    private Gate gate;

    @Before
    public void setUp() {
        gate = new Gate();
    }

    @Test
    public void testCreate(){
        assertNotNull("Gate not created properly", gate);
    }

    @Test
    public void getStatus() {
        assertEquals("Gate initial status should be free(0)", 0, gate.getStatus());
    }

    @Test
    public void allocate() {
        gate.allocate(1);
        assertEquals("Gate status should be reserved(1)", 1, gate.getStatus());
    }

    @Test
    public void docked() {
        gate.docked();
        assertEquals("Gate status should be occupued(2)", 2, gate.getStatus());
    }

    @Test
    public void departed() {
        gate.departed();
        assertEquals("Gate status should be free(0)", 0, gate.getStatus());
    }
}