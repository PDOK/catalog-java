package nl.pdok.catalog.workbench;

import java.io.Serializable;

public class WorkbenchParameter implements Serializable, Comparable<WorkbenchParameter> {

    private static final long serialVersionUID = 812456893649L;

    private String name;

    private String value;

    public WorkbenchParameter(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        WorkbenchParameter other = (WorkbenchParameter) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(WorkbenchParameter other) {
        if (other == null) {
            return 1;
        }

        int result = nullSafeStringComparator(this.name, other.name);
        if (result != 0) {
            return result;
        }

        return nullSafeStringComparator(this.value, other.value);
    }

    private static int nullSafeStringComparator(final String one, final String two) {
        if (one == null ^ two == null) {
            return (one == null) ? -1 : 1;
        }

        if (one == null && two == null) {
            return 0;
        }

        return one.compareToIgnoreCase(two);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public WorkbenchParameter clone() {
        return new WorkbenchParameter(this.name, this.value);
    }
}
