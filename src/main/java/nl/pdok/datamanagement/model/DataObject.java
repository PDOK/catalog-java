
package nl.pdok.datamanagement.model;

import java.io.InputStream;

/**
 *
 * @author Raymond Kroon <raymond@k3n.nl>
 */
public class DataObject {
    public final InputStream data;
    public final String name;
    public final boolean zipped;
    
    public DataObject(String name, InputStream data, boolean zipped) {
        this.name = name;
        this.data = data;
        this.zipped = zipped;
    }
}
