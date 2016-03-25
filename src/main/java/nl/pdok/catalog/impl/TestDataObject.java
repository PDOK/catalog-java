package nl.pdok.catalog.impl;

import java.io.InputStream;

public class TestDataObject {
	public final InputStream data;
	public final String name;
	public final boolean zipped;

	public TestDataObject(String name, InputStream data, boolean zipped) {
		this.name = name;
		this.data = data;
		this.zipped = zipped;
	}
}
