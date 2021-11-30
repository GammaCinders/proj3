package proj3;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

public class MySingleWithTailLinkedList implements Serializable
{
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
            throw new RuntimeException("Tail is not pointing at the end of the list");
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

    /********************************************************************************************
     *
     *    Your task is to complete this method.
     *
     *
     *
     * @param rental the unit begin rented
     */
    public void add(Rental rental) {
        Node temp = top;

        // attempt at good code #3
        if(top == null) {
            top = tail = new Node(rental, null);
            return;
        }

        // Case: Game: Handles Special Cases and
        // moves temp to correct position
        if(rental instanceof Game) {

            // Case: No games in list yet
            if(top.getData() instanceof Console) {
                top = new Node(rental, top);
                return;
            }

            // Case: Game goes before top
            if(rental.getDueBack().compareTo(top.getData().getDueBack()) < 0
                    || rental.getDueBack().compareTo(top.getData().getDueBack()) == 0
                    && rental.getNameOfRenter().compareTo(top.getData().getNameOfRenter()) < 0) {
                top = new Node(rental, top);
                return;
            }


            // General Game Case Below

            // Put Temp in the correct position based on date
            while(temp.getNext() != null
                    && temp.getNext().getData() instanceof Game
                    && rental.getDueBack().compareTo(temp.getNext().getData().getDueBack()) > 0) {
                   temp = temp.getNext();
            }

            // Put Temp in the correct position based on renters name
            while(temp.getNext() != null
                    && temp.getNext().getData() instanceof Game
                    && temp.getNext().getData().getDueBack().compareTo(rental.getDueBack()) == 0
                    && rental.getNameOfRenter().compareTo(temp.getNext().getData().getNameOfRenter()) > 0) {
                temp = temp.getNext();
            }
        }

        // Case: Game: Handles Special Cases and
        // moves temp to correct position
        if(rental instanceof Console) {

            // First move temp to end of consoles

            // TODO important Can still have to add if only consoles exist
            // TODO have to change later when adding Name sorting
            // Case: Console goes before top
            if(top.getData() instanceof Console
                    && rental.getDueBack().compareTo(top.getData().getDueBack()) < 0) {
                top = new Node(rental, top);
                return;
            }

            // Move temp to the last game in list
            if(top.getData() instanceof Game) {
                while(temp.getNext() != null && temp.getNext().getData() instanceof Game) {
                    temp = temp.getNext();
                }
            }

            // Move temp to correct due date position
            while(temp.getNext() != null
                    && rental.getDueBack().compareTo(temp.getNext().getData().getDueBack()) > 0) {
                temp = temp.getNext();
            }

            // Move temp to correct name position
            while(temp.getNext() != null
                    && rental.getNameOfRenter().compareTo(temp.getNext().getData().getNameOfRenter()) > 0
                    && rental.getDueBack().compareTo(temp.getNext().getData().getDueBack()) == 0) {
                temp = temp.getNext();
            }


        }

        // Love this line. It's neat and always works, even at the tail
        temp.setNext(new Node(rental, temp.getNext()));
        // Lol
        while(tail.getNext() != null) {tail = tail.getNext();}
        return;
    }


    public Rental remove(int index) {
        Rental removedRental;

        // Illegal Arg case
	    if(size() == 0 || index >= size() || index < 0) {
		    throw new IllegalArgumentException();
	    }

        // Case for just top
        if(size() == 1) {
            removedRental = top.getData();
            top = tail = null;
            return removedRental;
        }

        // Case removing top
        if(index == 0) {
            removedRental = top.getData();
            top = top.getNext();
            return removedRental;
        }

        // Case removing tail
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

        // Case general middle removal
        Node temp = top;
        // Gets the node before the desired node
        for(int i=0; i<index-1; i++) {
            temp = temp.getNext();
        }

        // Saves and removes
        removedRental = temp.getNext().getData();
        temp.setNext(temp.getNext().getNext());

        return removedRental;
    }

    public Rental get(int index) {
	    // null if top is null
        if (top == null) {
            return null;
	    }

	    // Index error checks
	    if(index >= size() || index < 0) {
	        throw new IllegalArgumentException();
	    } else {
		    // code to find rental at index
            Node temp = top;
            while(index > 0) {
			    temp = temp.getNext();
			    index--;
		    }

            return temp.getData();
	    }
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

