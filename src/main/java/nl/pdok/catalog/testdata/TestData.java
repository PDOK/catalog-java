package nl.pdok.catalog.testdata;

import java.io.InputStream;

public class TestData {

    public final InputStream data;
    public final String name;
    public final boolean zipped;

    public TestData(String name, InputStream data, boolean zipped) {
        this.name = name;
        this.data = data;
        this.zipped = zipped;
    }
}
