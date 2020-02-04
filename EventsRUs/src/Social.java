import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Scanner;

/**
 * @author yzk *Yasin Zamani Konari*
 */
public class Social extends Event {
    private Scanner scan;
    private boolean foodDrink;
    private boolean invite;


    public Social() {
    }

    public Social(String name, Calendar startDateTime, Calendar endDateTime) {
        super(name, startDateTime, endDateTime);
    }

    public String getName() {

        return name;
    }
    // Null pointer exception (FIXED) I was accessing Event scanner that was the cause.

    public void readKeyboard() {
        scan = new Scanner(System.in);
        System.out.println("Is food required? (Y/N)");
        String answer = scan.nextLine().toUpperCase();
        if (answer.equals("N")) {
            foodDrink = false;
        } else {
            foodDrink = true;
        }
        System.out.println("is invite Required? (Y/N)");
        String answer2 = scan.nextLine().toUpperCase();
        if (answer2.equals("N")) {
            invite = false;

        } else {
            invite = true;
        }

    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFoodDrink() {

        return foodDrink;
    }

    public void setFoodDrink(boolean foodDrink) {
        this.foodDrink = foodDrink;
    }

    public boolean isInvite() {
        return invite;
    }

    public void setInvite(boolean invite) {
        this.invite = invite;
    }

    //This is buggy : When printing it duplicates every time.(FIXED had to put Stringbuilder inside the method)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()+ "=  " + super.toString() + " foodAndDrinkRequired= " + foodDrink + ", invitation required= " + invite);
        return sb.toString(); // I hope this is the right way.
    }
    /*
    loads the Socials bool
     */
    public void load(Scanner infile) {
        super.load(infile);
        foodDrink = infile.nextBoolean();
        invite = infile.nextBoolean();
    }

    public void save(PrintWriter outfile) {
        if (outfile == null) {
            throw new IllegalArgumentException("outfile must not be null");
        }
        outfile.println("socialevent"); // we have to split between socialevent and talkevent to prevent wrong values in diff. arrays.
        super.save(outfile);
        outfile.println(foodDrink);
        outfile.println(invite);
    }
}
