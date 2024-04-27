import org.junit.Test;
import pl.tcs.oopproject.App;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class InitialTest {
    @Test
    public void testTest() {
        assertEquals("test1", App.test());
        assertNotEquals("test2", App.test());
    }
}
