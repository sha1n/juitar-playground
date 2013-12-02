package org.juitar.flags;

/**
 * @author sha1n
 * Date: 12/2/13
 */
public class Scope extends Identified {

    public Scope(String object) {
        super(object);
    }

    public final Object getObject() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scope scope = (Scope) o;

        if (!id.equals(scope.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return 31 + super.hashCode();
    }

    @Override
    public String toString() {
        return "scope:" + id;
    }
}
