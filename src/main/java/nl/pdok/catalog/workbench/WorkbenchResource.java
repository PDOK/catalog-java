package nl.pdok.catalog.workbench;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WorkbenchResource {

    private String name;
    private File target;

    public WorkbenchResource(String name, File target) {
        this.name = name;
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public InputStream getStream() throws IOException {
        return new FileInputStream(target);
    }

    public long getStreamLength() {
        return target.length();
    }
}
