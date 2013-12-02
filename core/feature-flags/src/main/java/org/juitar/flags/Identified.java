package org.juitar.flags;

/**
 * @author sha1n
 * Date: 12/2/13
 */
public class Identified {

    final String id;

    protected Identified(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Identified that = (Identified) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
