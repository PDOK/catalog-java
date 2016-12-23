package nl.pdok.catalog.workbench;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WorkbenchParameterTest {

    @Test
    public void testWorkbenchParameterStringStringBoolean() {
        WorkbenchParameter param = new WorkbenchParameter("name", "value");
        assertEquals("name", param.getName());
        assertEquals("value", param.getValue());
    }
    
    @Test
    public void testCompareTo() {
        WorkbenchParameter param1 = new WorkbenchParameter("abc", "def");
        WorkbenchParameter param2 = new WorkbenchParameter("bcd", "efg");
        WorkbenchParameter param3 = new WorkbenchParameter("", "");
        WorkbenchParameter param4 = new WorkbenchParameter(null, null);
        assertEquals(0, param1.compareTo(param1));
        assertEquals(-1, param1.compareTo(param2));
        assertEquals(1, param2.compareTo(param1));
        assertEquals(1, param2.compareTo(null));
        assertTrue(param1.compareTo(param3) > 0);
        assertTrue(param3.compareTo(param1) < 0);
        assertEquals(1, param1.compareTo(param4));
        assertEquals(-1, param4.compareTo(param1));
        assertEquals(0, param4.compareTo(param4));
        
    }
}
