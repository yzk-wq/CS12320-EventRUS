import java.io.*;
import java.util.*;

/**
 * To model a Conference - a collection of talks
 *
 * @author Chris Loftus
 * @version 1 (20th February 2019)
 */
public class Conference  {
    private String name;
    private ArrayList<Event> events;
    private ArrayList<Venue> venues;
    private StringBuilder stringbuilder;


    /**
     * Creates a conference
     */
    public Conference(){
        venues = new ArrayList<>();
        events = new ArrayList<>();
        stringbuilder = new StringBuilder();
    }

    /**
     * This method gets the value for the name attribute. The purpose of the
     * attribute is: The name of the Conference e.g. "QCon London 2019"
     *
     * @return String The name of the conference
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets the value for the name attribute. The purpose of the
     * attribute is: The name of the conference e.g. "QCon London 2019"
     *
     * @param name The name of the conference
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Enables a user to add a Talk to the Conference
     *
     * @param event A new event
     */
    public void addTalk(Event event){
        events.add(event);
    }



    /**
     * Add a new venue to the conference
     *
     * @param venue Must be a unique name
     * @return true if venue successfully added or false if the venue already exists
     */
    public boolean addVenue(Venue venue){
        boolean success = false;
        if (!venues.contains(venue)){
            venues.add(venue);
            success = true;
        }
        return success;
    }

    /**
     * Enables a user to delete a Talk from the Conference.
     *
     @param eventName set the event name
     @return boolean value of removing event or not
     */
    public boolean removeEvent(String eventName){
        // Search for the talk by name
        Event which = null;
        for (Event event: events){
            if (eventName.equals(event.getName())){
                which = event;
                break;
            }
        }
        if (which != null){
            events.remove(which); // Requires that Talk has an equals method
            System.out.println("removed " + which);
            return true;
        } else {
            System.err.println("cannot remove " + eventName +
                    " - not in conference " + name);
            return false;
        }
    }

    /**
     * Returns an array of the talks in the conference
     *
     * @return An array of the correct size
     */
    public Event[] obtainAllEvents() {
        // ENTER CODE HERE (POSSIBLY CHANGE SOME, YOU MAY CHANGE THE SIGNATURE TO DEAL
        // WITH ALL KINDS OF EVENTS: TALKS AND SOCIALS)
        // SEE Talk.getSpeakers METHOD FOR SIMILAR CODE
        // YOU MUST IMPLEMENT THIS METHOD, EVEN IF IT IS NOT CURRENTLY USED: YOU WILL NEED TO TEST IT
        // BY ADDING CODE TO ConferenceApp
       Event[] getAllEvents = new Event[events.size()];
       getAllEvents = events.toArray(getAllEvents);
        return getAllEvents;
    }
    public void addEvent(Event event){
        events.add(event);

    }

    /**
     * Searches for and returns the talk, if found
     * @param name The name of the talk
     * @return The talk or else null if not found
     */
    public Event searchForEvent(String name) {
        // ENTER CODE HERE (POSSIBLY CHANGE SOME, YOU MAY CHANGE THE SIGNATURE TO DEAL
        // WITH ALL KINDS OF EVENTS: TALKS AND SOCIALS)
        // for each loop where we iterate through the array list.
        Event result = null;
        for(Event e: events){
            if(e.getName().equals(name)){
                result = e;
            }
        }

        return result;
    }

    /**
     * Searches for and returns the venue, if found
     * @param name The name of the venue
     * @return The venue or else null if not found
     */
    public Venue searchForVenue(String name) {
        // ENTER CODE HERE (POSSIBLY CHANGE SOME, YOU MUST NOT CHANGE THE SIGNATURE)
        Venue result = null;
        for(Venue s: venues){
            if(s.getName().equals(name)){
                result = s;
            }
        }
        return result;
    }

    /**
     * @return String showing all the information in the kennel
     */

   public String toString() {
        // CHANGE TO USE StringBuilder TO MAKE MORE EFFICIENT
       StringBuilder sb = new StringBuilder();
        sb.append("Data in Conference "+ name+" is: \n");
        for (Event event : events) {

             sb.append(event + "\n");
        }
        return sb.toString();
    }


    /**
     *
     * @param filename file to read from
     * @throws FileNotFoundException ""
     * @throws IOException ""
     *
     */
    /*
    basic switch statement, go through the list  find anything that start with talkevent then create a new event for talk
    if it is socialevent create a new Social for it.
     */
    public void load(String filename) throws FileNotFoundException, IOException {
        // finally done 21/03/19
        try (FileReader fr = new FileReader(filename);
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br)) {

            events.clear();
            venues.clear();

            // Use the delimiter pattern so that we don't have to clear end of line
            // characters after doing a infile.nextInt or infile.nextBoolean and can use infile.next
            infile.useDelimiter("\r?\n|\r");

            name = infile.next();

            while (infile.hasNext()) {
                Event event = null;
                String eventType = infile.next();
                switch (eventType) {
                    case "talkevent":
                        event = new Talk();
                        break;
                    case "socialevent":
                        event = new Social();
                        break;
                }
                if (event != null) {
                    event.load(infile);

                    // Read the venue data
                    String venueName = infile.next();
                    boolean hasDataProjector = infile.nextBoolean();
                    Venue theVenue = searchForVenue(venueName);
                    if (theVenue == null) {
                        theVenue = new Venue(venueName);
                        theVenue.setHasDataProjector(hasDataProjector);
                        venues.add(theVenue);
                    }
                    event.setVenue(theVenue);
                    events.add(event);
                }
            }
        }
    }


    /**
     * Write out Conference information to the outfile
     *
     * @param outfileName The file to write to
     * @throws IOException ""
     * @throws IllegalArgumentException if outfileName is null or empty
     */
    public void save(String outfileName) throws IOException {
        // Again using try-with-resource so that I don't need to close the file explicitly
        try (FileWriter fw = new FileWriter(outfileName);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw)) {

            outfile.print(name);
            for (Event event : events) {
                outfile.println();
                event.save(outfile);

                Venue venue = event.getVenue();
                outfile.println(venue.getName());

                // Print rather than println so that we don't leave a blank line at the end
                outfile.print(venue.hasDataProjector());
            }
        }
    }


}
