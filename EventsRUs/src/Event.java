import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Blueprint of Event
 * @author yzk
 */
public class Event {
   private Calendar startDateTime;
   private Calendar endDateTime;
   String name;
   boolean dataProjectorRequired;
   private Venue venue;
   private Scanner scan;

    public Event() {

    }

    /**
     * Constructor for event
     */
    public Event(String name, Calendar startDateTime, Calendar endDateTime) {
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /* Different way to approach but changed my mind.
    public void readKeyboard() {
        System.out.println("Social Event name");
        scan = new Scanner(System.in);
        name = scan.nextLine();
        startDateTime = getDateTime();
        endDateTime = getDateTime();

    }*/

    /***
     *
     * @return startDateTime
     */

    public Calendar getStartDateTime() {
        return startDateTime;
    }

    /***
     *
     * @param startDateTime set the startDateTime
     */

    public void setStartDateTime(Calendar startDateTime) {
            this.startDateTime = startDateTime;
        }

    /***
     *
     * @return endDateTime
     */

    public Calendar getEndDateTime() {
        return endDateTime;
    }

    /**
     *
     * @param endDateTime sets the endDateTime
     */
    public void setEndDateTime(Calendar endDateTime) {
            this.endDateTime = endDateTime;
        }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name sets the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return boolean of dataProjector
     */
    public boolean isDataProjectorRequired() {
        return dataProjectorRequired;
    }

    /**
     *
     * @param venue sets the venue
     */
    public void setVenue(Venue venue) {
        // Only allow this if the venue spec matches the
        // the talk requirement
        if (dataProjectorRequired && !venue.hasDataProjector()) {
            System.err.println("Talk " + name + " requires a data projector. " +
                    "Venue " + venue.getName() + " does not have one");
        } else {
            this.venue = venue;
        }
    }

    /**
     *
     * @return venue information
     */
    public Venue getVenue() {
        return venue;
    }


    /***
     *
     * @param outfile saves it to our file
     */
    public void save(PrintWriter outfile) {
        if (outfile == null)
            throw new IllegalArgumentException("outfile must not be null");
        outfile.println(name);
        writeDateTime(outfile, startDateTime);
        writeDateTime(outfile, endDateTime);

        outfile.println(dataProjectorRequired);
    }

    /**
     *
     * @param infile loads the file
     */
    public void load(Scanner infile) {
        if (infile == null) {
            throw new IllegalArgumentException("infile must not be null");
        }
        name = infile.next();
        startDateTime = readDateTime(infile);
        endDateTime = readDateTime(infile);

        dataProjectorRequired = infile.nextBoolean();


    }

    /**
     *
     * @param outfile writes date to our file
     * @param dateTime gets the dateTime
     */
    private void writeDateTime(PrintWriter outfile, Calendar dateTime) {
        outfile.println(dateTime.get(Calendar.YEAR));
        outfile.println(dateTime.get(Calendar.MONTH));
        outfile.println(dateTime.get(Calendar.DAY_OF_MONTH));
        outfile.println(dateTime.get(Calendar.HOUR_OF_DAY));
        outfile.println(dateTime.get(Calendar.MINUTE));
    }

    /**
     *
     * @param scan reads our input
     * @return our input from scan
     */
    public Calendar readDateTime(Scanner scan) {
        Calendar result = Calendar.getInstance();

        int year = scan.nextInt();
        int month = scan.nextInt();
        int day = scan.nextInt();
        int hour = scan.nextInt();
        int minutes = scan.nextInt();
        result.clear();
        result.set(year, month, day, hour, minutes);
        return result;
    }


    /**
     * Sets the data projector requirement. This will only be allowed
     * if there is an associated venue that meets the requirement.
     * Otherwise displays an error message. This should really throw an exception
     *
     * @param dataProjectorRequired Whether required or not
     */
    public void setDataProjectorRequired(boolean dataProjectorRequired) {
        if (venue != null && (dataProjectorRequired && !venue.hasDataProjector())) {
            System.err.println("Talk " + name + " currently has a venue " +
                    venue.getName() + " that does not have a data projector. Change the venue first");
        } else {
            this.dataProjectorRequired = dataProjectorRequired;
        }
    }

    /**
     *
     * @return return information about events
     */
    @Override
    public String toString() {
        return "startDateTime=" + dateTimeToString(startDateTime) +
                ", endDateTime=" + dateTimeToString(endDateTime) +
                ", name='" + name + '\'' +
                ", venue=" + venue +
                ", dataProjectorRequired=" + dataProjectorRequired;

    }

    /**
     *
     * @param dateTime
     * @return information about our date
     */
    private String dateTimeToString(Calendar dateTime) {

        int year = dateTime.get(Calendar.YEAR);
        int month = dateTime.get(Calendar.MONTH) + 1; // We have to add 1 since months start from 0
        int day = dateTime.get(Calendar.DAY_OF_MONTH);
        int hour = dateTime.get(Calendar.HOUR_OF_DAY);
        int minutes = dateTime.get(Calendar.MINUTE);

        return "" + year + ":" + month + ":" + day + ":" + hour + ":" + minutes;
    }

    /**
     *
     * @param startDateTime start date parameters
     * @param endDateTime end date parameters
     * @return boolean of the check if the requirements are met
     */
    public boolean dateErrorCheck(Calendar startDateTime, Calendar endDateTime){
        boolean dateErrorCheck;
        if(endDateTime.compareTo(startDateTime)>0){
            dateErrorCheck = false;
        }
        else{
            System.err.println("Check failed, Please try again!");
            dateErrorCheck = true;
        }
        return dateErrorCheck;
    }


}
