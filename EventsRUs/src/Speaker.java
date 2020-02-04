import java.util.Objects;

/** Blueprint for Speaker
 * @author yzk
 *
 */
public class Speaker {
    private String name;
    private String phone;

    /**
     *
     * @param name speakers name
     */
    public Speaker(String name){
        this.name = name;
    }

    /**
     *
     * @param name name of the speaker
     * @param phone phone of the speaker
     */
    public Speaker(String name, String phone){
        this(name);
        this.phone = phone;

    }

    /**
     *
     * @return name of the speaker
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name set name of the speaker
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return phone as string
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone set phone as string
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Note that this only compares equality based on a
     * speakers's name.
     * @param o the other speaker to compare against.
     *
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speaker speaker = (Speaker) o;
        return Objects.equals(name, speaker.name) &&
                Objects.equals(phone, speaker.phone);
    }

    /**
     *
     * @return information about speaker
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
         sb.append("Speaker{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}');
        return sb.toString();
    }
}
