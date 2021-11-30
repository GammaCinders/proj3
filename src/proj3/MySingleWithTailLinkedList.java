package proj3;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

/**********************************************************************
 * Link List Class used for holding rentals. Stores Rentals
 * (Game and/or Console) by calling add() and automatically adds them
 * in the correct sorted position, based on their due date and the
 * renter's name if the due date is the same. (Also contains size(),
 * clear(), get(), remove(), toString(), and display() methods.)
 *
 * @author Keagen Talsma
 * @version 11/30/2020
 *********************************************************************/
public class MySingleWithTailLinkedList implements Serializable  {

    private Node top;
    private Node tail;

    public MySingleWithTailLinkedList() {
        top = tail = null;
    }

    // This method has been provided and you are not permitted to modify
    public int size() {
        if (top == null)
            return 0;

        int total = 0;
        Node temp = top;
        while (temp.getNext() != null) {
            total++;
            temp = temp.getNext();
        }

        if (temp != tail)
            throw new RuntimeException(
                    "Tail is not pointing at the end of the list");
        else
            total++;

        return total;
    }

    // This method has been provided and you are not permitted to modify
    public void clear () {
        Random rand = new Random(13);
        while (size() > 0) {
            int number = rand.nextInt(size());
            remove(number);
        }
    }

    /******************************************************************
     * Adds the argument rental to the Link List. Automatically puts
     * the rental in sorted position by due date and renter name
     * (if due date is the same).
     * @param rental the unit being rented
     *****************************************************************/
    public void add(Rental rental) {
        Node temp = top;

        // Case: Totally empty list
        if(top == null) {
            top = tail = new Node(rental, null);
            return;
        }

        // Moves temp to the correct position to add game rental,
        // or deals with a special case and returns
        if(rental instanceof Game) {

            // Case: No games in list yet (list has only consoles)
            if(top.getData() instanceof Console) {
                top = new Node(rental, top);
                return;
            }

            // Case: Game goes before top
            // If rental has an earlier date it goes first
            if(rental.getDueBack().compareTo(
                                top.getData().getDueBack()) < 0
                    // If the date is the same and the name goes before
                    || rental.getDueBack().compareTo(
                            top.getData().getDueBack()) == 0
                    && rental.getNameOfRenter().compareTo(
                            top.getData().getNameOfRenter()) < 0) {
                top = new Node(rental, top);
                return;
            }

            // General Game Case Below

            // Put Temp in the correct position based on date
            while(temp.getNext() != null
                    && temp.getNext().getData() instanceof Game
                    && rental.getDueBack().compareTo(
                            temp.getNext().getData()
                                    .getDueBack()) > 0) {
                   temp = temp.getNext();
            }

            // Put Temp in the correct position based on renters name
            while(temp.getNext() != null
                    && temp.getNext().getData() instanceof Game
                    // Stops at end of games with same date
                    && temp.getNext().getData().getDueBack().compareTo(
                            rental.getDueBack()) == 0
                    // Stops at pos to add game based on name
                    && rental.getNameOfRenter().compareTo(
                            temp.getNext().getData()
                                    .getNameOfRenter()) > 0) {
                temp = temp.getNext();
            }
        }

        // Moves temp to the correct position to add console rental,
        // or deals with a special case and returns
        if(rental instanceof Console) {

            // Case: Console goes before top
            // If first rental is a console and has an earlier date
            if(top.getData() instanceof Console
                    && rental.getDueBack().compareTo(
                            top.getData().getDueBack()) < 0
                    // OR first is console and
                    // rental has the same date and name goes before
                    || top.getData() instanceof Console
                    && rental.getDueBack().compareTo(
                            top.getData().getDueBack()) == 0
                    && rental.getNameOfRenter().compareTo(
                            top.getData().getNameOfRenter()) < 0) {
                top = new Node(rental, top);
                return;
            }

            // Move temp to the last game in list
            if(top.getData() instanceof Game) {
                while(temp.getNext() != null
                        && temp.getNext().getData() instanceof Game) {
                    temp = temp.getNext();
                }
            }

            // Move temp to correct due date position
            while(temp.getNext() != null
                    // Iterates until temp is before a rental
                    // with a date after rental
                    && rental.getDueBack().compareTo(
                            temp.getNext().getData()
                                    .getDueBack()) > 0) {
                temp = temp.getNext();
            }

            // Move temp to correct name position
            while(temp.getNext() != null
                    // Stops at end of consoles with same date
                    && rental.getDueBack().compareTo(
                    temp.getNext().getData().
                            getDueBack()) == 0
                    // Stops at pos to add console based on name
                    && rental.getNameOfRenter().compareTo(
                            temp.getNext().getData().
                                    getNameOfRenter()) > 0 ) {
                temp = temp.getNext();
            }
        }

        // At this point, temp is in the correct position to add rental
        // between temp and temp.next, and all special cases have been
        // dealt with, so it is added below

        // Love this line. It's neat and always works, even at the tail
        temp.setNext(new Node(rental, temp.getNext()));
        // Lol
        while(tail.getNext() != null) {tail = tail.getNext();}
        return;
    }


    /******************************************************************
     * Removes a node from this link list at the argument index and
     * returns the rental it contained.
     * @param index the index of node to remove
     * @return Rental that the removed node contained
     * @throws IllegalArgumentException if index is >= size() or < 0
     *                                  also if this list is empty
     ******************************************************************/
    public Rental remove(int index) {
        Rental removedRental;

        // Illegal argument case
	    if(size() == 0 || index >= size() || index < 0) {
		    throw new IllegalArgumentException();
	    }

        // Case: Just top exists
        if(size() == 1) {
            removedRental = top.getData();
            top = tail = null;
            return removedRental;
        }

        // Case: Removing top
        if(index == 0) {
            removedRental = top.getData();
            top = top.getNext();
            return removedRental;
        }

        // Case: Removing tail
        if(index == size()-1) {
            //have to get node before tail to remove tail
            Node temp = top;
            while(temp.getNext() != tail) {
                temp = temp.getNext();
            }

            removedRental = temp.getNext().getData();
            temp.setNext(null);
            tail = temp;

            return removedRental;
        }

        // Case: General middle removal
        Node temp = top;
        // Gets the node before the desired node
        for(int i=0; i<index-1; i++) {
            temp = temp.getNext();
        }

        // Saves, removes, and returns
        removedRental = temp.getNext().getData();
        temp.setNext(temp.getNext().getNext());

        return removedRental;
    }

    /******************************************************************
     * Returns the Rental in the node at the argument index
     * @param index index to retrieve Rental from
     * @return Rental in the node at the argument index
     *****************************************************************/
    public Rental get(int index) {

	    // Case: Empty list: Always returns null, index is irrelevant
        if (top == null) {
            return null;
	    }

	    // Case: Invalid index
	    if(index >= size() || index < 0) {
	        throw new IllegalArgumentException();
        }

        // Case: General: Iterate temp to index
        Node temp = top;
        while(index > 0) {
            temp = temp.getNext();
            index--;
        }

        return temp.getData();
    }

    public void display() {
        Node temp = top;
        while (temp != null) {
            System.out.println(temp.getData());
            temp = temp.getNext();
        }
    }

    @Override
    public String toString() {
        return "LL {" +
                "top=" + top +
                ", size=" + size() +
                '}';
    }
}

