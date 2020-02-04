import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * The main application class for the Conference. Has a command line menu.
 * @author Chris Loftus
 * @version 1.0 (27/02/19)
 */

public class ConferenceApp {

    private String filename;
    private Scanner scan;
    private Conference conference;

    /*
     * Notice how we can make this private, since we only call from main which
     * is in this class. We don't want this class to be used by any other class.
     */
    private ConferenceApp() {
        scan = new Scanner(System.in);
        System.out.print("Please enter the filename of conference information: ");
        filename = scan.next();

        conference = new Conference();
    }

    /*
     * initialise() method runs from the main and reads from a file
     */
    private void initialise() {
        System.out.println("Using file " + filename);

        try {
            conference.load(filename);
        } catch (FileNotFoundException e) {
            System.err.println("The file: " + " does not exist. Assuming first use and an empty file." +
                    " If this is not the first use then have you accidentally deleted the file?");
        } catch (IOException e) {
            System.err.println("An unexpected error occurred when trying to open the file " + filename);
            System.err.println(e.getMessage());
        }
    }

    /*
     * runMenu() method runs from the main and allows entry of data etc
     */
    private void runMenu() {
        String response;
        do {
            printMenu();
            System.out.println("What would you like to do:");
            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch (response) {
                case "1":
                    addEvent();
                    break;
                case "2":
                    changeConferenceName();
                    break;
                case "3":
                    searchForTalk();
                    break;
                case "4":
                    removeEvent();
                    break;
                case "5":
                    addVenue();
                    break;
                case "6":
                    printAll();
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Try again");
            }
        } while (!(response.equals("Q")));
    }
    /*
    prints the menu
     */
    private void printMenu() {
        System.out.println("1 -  add Event(Talk or Social)");
        System.out.println("2 -  change conference name ");
        System.out.println("3 -  search for a event");
        System.out.println("4 -  remove an event");
        System.out.println("5 -  add a venue");
        System.out.println("6 -  display everything");
        System.out.println("q -  Quit");
    }

    /*
    Adds events to the confference by users choice
     */
   public void addEvent(){
        int choice;
        System.out.println(" Press 1 to add Talk \n or \n Press 2 to add Social");
        choice = scan.nextInt();
        switch(choice){
            case 1:
                addTalk();
                break;
            case 2:
                addSocial();
                break;
             default:
                 System.err.print("Wrong choice! Please Select Between 1 or 2");
        }
    }
    /*
    adds talk to the event
     */

    private void addTalk() {
        System.out.println("Get speakers: ");
        Talk talk = new Talk();
        ArrayList<Speaker> speakers = getSpeakers();
        talk.setSpeakers(speakers);
        populateAndAddToConference(talk);
    }
    /*
    adds social to event
     */

    private void addSocial(){
        Social social = new Social();
        social.readKeyboard();
        populateAndAddToConference(social);
    }
    /*
    Sorts start of date time with help of Comparator
     */
    private void sortMethod(){
        Event[] getAllEvents;
        getAllEvents = conference.obtainAllEvents();
        Arrays.sort(getAllEvents, Comparator.comparing(Event::getStartDateTime)); // 21/03/2019 / Sort by startDate
        // iterate through the list
        for(Event newList: getAllEvents ) {
            System.out.println(newList);
        }
    }


    /*
    Må fikse addtoConff pga av den tar bare talk som argumenter. Gjør om koden at den kan ta imot begge Talk + Social
    //Ferdig 18 / 03 / 19
     */

    /*
     * Adds event general data. This is common to all events. Then
     * adds to the conference.
     */

    private void populateAndAddToConference(Event event){
        // use a while loop to our date error checking (? maybe done)
        String nameInput;
        System.out.print("Enter Event Name : ");
        nameInput = scan.next();
        boolean check = true;
        Calendar startDateTime = null;
        Calendar endDateTime = null;
        while(check){
            System.out.println("Enter start time für talk");
            startDateTime = getDateTime();
            System.out.println("Enter end time for talk ");
            endDateTime = getDateTime();
            if (event.dateErrorCheck(startDateTime, endDateTime)) {
                check = true;
            } else check = false;
        }


        System.out.println("Is a data projector required?(Y/N)");
        String answer = scan.nextLine().toUpperCase();
        boolean projectorRequired = true;
        if (answer.equals("N")){
            projectorRequired = false;
        }

        Venue venue = null;
        do {
            System.out.println("Enter venue name");
            String venueName = scan.nextLine();
            answer = "N";
            venue = conference.searchForVenue(venueName);
            if (venue != null) {
                if (projectorRequired && !venue.hasDataProjector()){
                    System.out.println("Selected venue does not have a data projector. Choose a different venue");
                    answer = "Y";
                } else{
                    event.setName(nameInput);
                    event.setDataProjectorRequired(projectorRequired);
                    event.setStartDateTime(startDateTime);
                    event.setEndDateTime(endDateTime);
                    event.setVenue(venue);
                    conference.addEvent(event);

                }
            } else {
                System.out.println("Venue " + venueName + " does not exist. Try a different venue? (Y/N)");
                answer = scan.nextLine().toUpperCase();
            }
        } while (answer.equals("Y"));
    }



    /*
    Gets users input as date to calendar
     */
    private Calendar getDateTime() {
        Calendar result = Calendar.getInstance();
        System.out.println("On one line (numbers): year month day hour minutes");

        // Note that an ArrayIndexOutOfBoundsException is thrown if an
        // illegal value is entered. For simplicity, we will pretend that won't happen.

        int year = scan.nextInt();
        // Note that months start from 0 so we have to subtract 1
        // when reading and then add 1 when displaying the result
        int month = scan.nextInt() - 1;
        int day = scan.nextInt();
        int hour = scan.nextInt();
        int minutes = scan.nextInt();
        scan.nextLine(); // Clear the end of line character

        result.clear();
        result.set(year, month, day, hour, minutes);

        System.out.println("The date/time you entered was: " +
                result.get(Calendar.YEAR) + "/" +
                (result.get(Calendar.MONTH) + 1) + "/" +
                result.get(Calendar.DAY_OF_MONTH) + ":" +
                result.get(Calendar.HOUR_OF_DAY) + ":" +
                result.get(Calendar.MINUTE));
        return result;
    }
    /*
    array list of speakres and we add it to our speaker array.
     */
    private ArrayList<Speaker> getSpeakers() {
        ArrayList<Speaker> speakers = new ArrayList<>();
        String answer;
        scan.nextLine();
        do {
            System.out.println("Enter on each new line: speaker-name speaker-phone");
            String speakerName = scan.nextLine();
            String speakerPhone = scan.nextLine();
            Speaker speaker = new Speaker(speakerName, speakerPhone);
            speakers.add(speaker);
            System.out.println("Another owner (Y/N)?");
            answer = scan.nextLine().toUpperCase();
        }
        while (!answer.equals("N"));
        return speakers;
    }
    /*
        change conference name
     */
    private void changeConferenceName() {
        String name = scan.nextLine();
        conference.setName(name);
    }
    /*
        iterate through list and search for EVENT!!!!!!!!
     */
    private void searchForTalk() {
        System.out.println("Which event do you want to search for");
        String name = scan.nextLine();
        Event event = conference.searchForEvent(name);
        if (event != null){
            System.out.println(event);
        } else {
            System.out.println("Could not find talk: " + name);
        }
    }

    private void removeEvent() {
        System.out.println("Which event do you want to remove");
        String talkToBeRemoved;
        talkToBeRemoved = scan.nextLine();
        conference.removeEvent(talkToBeRemoved);
    }

    private void addVenue() {
        Venue venue;
        String venueName;
        boolean tryAgain;
        do {
            tryAgain = false;
            System.out.println("Enter the venue name");
            venueName = scan.nextLine();
            venue = conference.searchForVenue(venueName);
            if (venue != null){
                System.out.println("This venue already exists. Give it a different name");
                tryAgain = true;
            }
        }while(tryAgain);

        System.out.println("Does it have a data projector?(Y/N)");
        String answer = scan.nextLine().toUpperCase();
        boolean hasDataProjector = answer.equals("Y");

        venue = new Venue(venueName);
        venue.setHasDataProjector(hasDataProjector);

        conference.addVenue(venue);
    }


    private void printAll() {
        // THIS IS NOT SORTED. YOU WILL NEED TO UPDATE THIS TO DISPLAY SORTED EVENTS ** Implement this later(Done 21/03/19) **
        // Created a method when obtaining all Events which is sorted.
        sortMethod();
    }


    /*
     * save() method runs from the main and writes back to file
     */
    private void save() {
        try{
            conference.save(filename);
        } catch (IOException e) {
            System.err.println("Problem when trying to write to file: " + filename);
        }

    }
    // /////////////////////////////////////////////////
    public static void main(String[] args) {
        System.out.println("**********HELLO***********");

        ConferenceApp app = new ConferenceApp();
        app.initialise();
        app.runMenu();
        app.printAll();
        // MAKE A BACKUP COPY OF conf.txt JUST IN CASE YOU CORRUPT IT
        app.save();

        System.out.println("***********GOODBYE**********");
    }


}
