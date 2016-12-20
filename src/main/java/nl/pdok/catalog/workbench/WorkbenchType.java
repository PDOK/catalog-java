package nl.pdok.catalog.workbench;

public enum WorkbenchType {

    FULL,
    DELTA,
    EXTRACT,
    DERIVATIVE,
    UNKNOWN;
    
    public String value() {
        return name();
    }

}
