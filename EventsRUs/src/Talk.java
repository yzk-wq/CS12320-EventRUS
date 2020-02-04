/**
 * Blueprint of Talk
 * @author yzk
 */

import java.io.PrintWriter;
import java.util.*;

public class Talk extends Event {
    private ArrayList<Speaker> speakers = new ArrayList<>();
    StringBuffer as = new StringBuffer();

    public Talk(){
    }

    /**
     * Constructor for Talk
     * @param name The talk title
     * @param startDateTime When it starts
     * @param endDateTime When it ends
     */
    public Talk(String name, Calendar startDateTime, Calendar endDateTime) {
        super(name,startDateTime,endDateTime);
        /*
        this.name = name;
        // We should really check that the start time is before the end time
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;*/

    }

    /**
     *
     * @return Talk name
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public final void setSpeakers(List<Speaker> speakers) {
        // We make a true copy of the speakers ArrayList to make sure that we
        // don't break encapsulation: i.e. don't share object references with
        // other code
        if (speakers == null){
            throw new IllegalArgumentException("speakers must not be null");
        }
        this.speakers.clear();
        for (Speaker s : speakers) {
            Speaker copy = new Speaker(s.getName(), s.getPhone());
            this.speakers.add(copy);
        }
    }

    /**
     * Returns a copy of the speakers
     * @return A copy of the speakers as an array
     */
    public Speaker[] getSpeakers(){
        Speaker[] result = new Speaker[speakers.size()];
        result = speakers.toArray(result);
        return result;
    }

    /**
     * A basic implementation to just return all the data in string form.
     * CHANGE THIS TO USE StringBuffer
     * @return All the string data for the talk
     */

   /* public String toString() { duplicates !! bad
        as.append("\n  eventType=Talk , " + super.toString() + ", speakers=");
        for(Speaker speaker: speakers){
            as.append(speaker.toString());
        }

        return super.toString()+as;
    }*/

    /**
     *
     * @return information from THIS class and then from the super class!
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getClass().getName()+ "= " + super.toString() + ", speakers=");
        String result = "";
        for (Speaker speaker : speakers) {
            result += speaker;
        }
        return sb.toString() + result ;
    }


    /**
     * Reads in Talk specific information from the file
     * @param infile An open file
     * @throws IllegalArgumentException if infile is null
     */

    public void load(Scanner infile) {
        if (infile == null) {
            throw new IllegalArgumentException("infile must not be null");
        }
        super.load(infile);
        int numSpeakers = infile.nextInt();
        Speaker speaker = null;
        speakers.clear();
        for (int i=0; i<numSpeakers; i++){
            String speakerName = infile.next();
            String phone = infile.next();
            speaker = new Speaker(speakerName, phone);
            speakers.add(speaker);
        }
    }


    /**
     * Writes out information about the Talk to the file
     * @param outfile An open file
     * @throws IllegalArgumentException if outfile is null
     */

    public void save(PrintWriter outfile) {
        if (outfile == null)
            throw new IllegalArgumentException("outfile must not be null");
        outfile.println("talkevent");
        super.save(outfile);
        outfile.println(speakers.size());
        for (Speaker speaker: speakers){
            outfile.println(speaker.getName());
            outfile.println(speaker.getPhone());
        }
    }

    /**
     * Note that this only compares equality based on a
     * talks's name.
     * @param o the other talk to compare against.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // Are they the same object?
        if (o == null || getClass() != o.getClass()) return false; // Are they the same class?
        Talk talk = (Talk) o;  // Do the cast to Talk
        // Now just check the names
        return Objects.equals(name, talk.name); // Another way of checking equality. Also checks for nulls
    }

}
