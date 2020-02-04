import java.util.Objects;

/**
 * @author yzk
 * blueprint for venue
 */
public class Venue {
    private String name;
    private boolean hasDataProjector;
    private boolean hasInvite;

    public Venue(){}

    /**
     *
     * @param name takes in venue name
     */
    public Venue(String name){
        this.name = name;
    }

    /**
     *
     * @return venue name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name set venue name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return boolean data projector
     */
    public boolean hasDataProjector() {
        return hasDataProjector;
    }

    /**
     *
     * @param hasDataProjector set dataproject boolean
     */
    public void setHasDataProjector(boolean hasDataProjector) {
        this.hasDataProjector = hasDataProjector;
    }
    /**
     * Note that this only compares equality based on a
     * venue's name.
     * @param o the other venue to compare against.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venue location = (Venue) o;
        return Objects.equals(name, location.name);
    }

    /**
     *
     * @return information about venue
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Venue{" +
                "name='" + name + '\'' +
                ", hasDataProjector=" + hasDataProjector +
                '}');
        return sb.toString();
    }
}
