package nl.pdok.catalog.workbench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class WorkbenchTest {

    @Test
    public void testAttributeName() {
        Workbench workbench = new Workbench("test", null);
        assertEquals("test", workbench.getName());
        assertEquals(0, workbench.getParameters().size());
    }

    @Test
    public void testAttributeParameters() {
        Workbench workbench = new Workbench("test", null);
        workbench.addParameter(new WorkbenchParameter("key", "value"));
        assertNotNull(workbench.getParameters());
        assertEquals(1, workbench.getParameters().size());
    }
}
