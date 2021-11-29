package proj3;

import java.io.Serializable;
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

        if(rental instanceof Game) {

            // Case: No games in list yet
            if(top.getData() instanceof Console) {
                top = new Node(rental, top);
                return;
            }

            // Case: Game goes before top
            if(rental.dueBack.compareTo(temp.getData().getDueBack()) < 0
                    && rental.getNameOfRenter().compareTo(temp.getData().getNameOfRenter()) < 0) {
                top = new Node(rental, top);
                return;
            }

            // General Game Below

            // Put Temp in the correct position based on date
            while(temp.getNext() != null && temp.getNext().getData() instanceof Game) {

                // Stop when the temp node is at the correct node to add rental after
                if(rental.getDueBack().compareTo(temp.getNext().getData().getDueBack()) <= 0) {
                    break;
                } else {
                   temp = temp.getNext();
                }
            }

            // Put Temp in the correct position based on renters name
            while(temp.getNext() != null
                    && temp.getNext().getData() instanceof Game
                    && temp.getNext().getData().getDueBack().compareTo(rental.getDueBack()) == 0) {

                // Stop when the temp node is at the correct node to add rental after
                if(rental.getNameOfRenter().compareTo(temp.getNext().getData().getNameOfRenter()) <= 0) {
                    break;
                } else {
                    temp = temp.getNext();
                }
            }
        // Must be a console
        } else {
            temp = tail;
        }

        Node newNode = new Node(rental, temp.getNext());
        temp.setNext(newNode);
        //Lol
        while(tail.getNext() != null) {tail = tail.getNext();}
        return;


//        // attempt at good code #2
//
//        // Case: List is empty: just add the rental
//        if(top == null) {
//            top = tail = new Node(rental, null);
//            return;
//        } else {
//            if(rental instanceof Game) {
//                // Case: No Games in List: Just add new game at top
//                if(top.getData() instanceof Console) {
//                    // TODO I don't know if this works
//                    top = new Node(rental, top);
//                    return;
//                }
//
//                    // Case: Start of List: Add at top
//                    if(rental.getDueBack().compareTo(top.getData().getDueBack()) >= 0
//                            && rental.getNameOfRenter().compareTo(top.getData().getNameOfRenter()) >= 0) {
//                        // TODO I don't know if this works
//                        top = new Node(rental, top);
//                        return;
//                    }
//
//                    // Put temp at correct position based on dueDate
//                    while(temp.getNext() != null && temp.getNext().getData() instanceof Game) {
//                        if(rental.getDueBack().compareTo(temp.getNext().getData().getDueBack()) <= 0) {
//                            break;
//                        } else {
//                            temp = temp.getNext();
//                        }
//                    }
//
//
//                    // Put temp in correct position based on renter name
//                    while(temp.getNext() != null
//                            && rental.getDueBack().compareTo(top.getData().getDueBack()) >= 0
//                            && temp.getNext().getData() instanceof Game) {
//
//                        if(rental.getNameOfRenter().compareTo(temp.getNext().getData().getNameOfRenter()) <= 0) {
//                            break;
//                        } else {
//                            temp = temp.getNext();
//                        }
//                    }
//
//
//                    Node newNode = new Node(rental, temp.getNext());
//                    temp.setNext(newNode);
//                    return;
//
//
//            // Must be a Console
//            } else {
//
//            }
//        }
//
//
//
//        //attempt at good code #1 below
//
//        // no list
//        if(top == null) {
//            top = tail = new Node(rental, null);
//            return;
//        } else {
//            if(rental instanceof Game) {
//                // Special Case //
//                // Because of how I implemented the general case below this, I need a special case
//                // when the new rental goes at the very start of the list (replaces top)
//                // Both conditions below are to check when the new rental goes in the first index,
//                // and to handle it accordingly
//                // TODO this could be backwards, check back here first when things go wrong
//                if(rental.dueBack.compareTo(top.getData().dueBack) < 0 || top.getData() instanceof Console) {
//                    // TODO this should work but I'm not sure honestly
//                    top = new Node(rental, top);
//                    return;
//                }
//
//                // General Case //
//                // This loop runs from the start of the list until it gets to the end of games
//                // or end of the list. Temp always ends up at the node where rental gets added after
//                // temp node
//                while(temp.getNext() != null && temp.getNext().getData() instanceof Game) {
//                    if(temp.getNext().getData().dueBack.compareTo(rental.dueBack) <= 0) {
//                        break;
//                    }
//                }
//
//                // This works everywhere in the list (it just can't add something at the top),
//                // even at the very end, very poggers!
//                Node newNode = new Node(rental, temp.getNext());
//                temp.setNext(newNode);
//                return;
//
//            // Adding Console
//            } else {
//                // When adding consoles, temp should immediatly go to the node directly before
//                // the first console if possible
//                if(top.getData() instanceof Game) {
//                    while(temp.getNext().getData() instanceof Game && temp.getNext() != null) {
//                        temp = temp.getNext();
//                    }
//                // When there are only consoles and the new Rental is the earliest one
//                // there is a special case again due to my general case like with Games
//                } else if(rental.getDueBack().compareTo(top.getData().getDueBack()) <= 0) {
//                    // Special Case //
//                    // TODO this should work but I'm not sure honestly
//                    top = new Node(rental, top);
//                    return;
//                }
//
//
//
//                // General Case //
//                // This loop runs from the start of the list until it gets to the end of games
//                // or end of the list. Temp always ends up at the node where rental gets added after
//                // temp node
//
//                //Loop for
//                while() {
//
//                }
//
//                while(temp.getNext() != null && temp.getNext().getData() instanceof Console) {
//                    if(temp.getNext().getData().dueBack.compareTo(rental.dueBack) <= 0) {
//                        break;
//                    }
//                }
//
//                // This works everywhere in the list (it just can't add something at the top),
//                // even at the very end, very poggers!
//                Node newNode = new Node(rental, temp.getNext());
//                temp.setNext(newNode);
//                return;
//            }
//
//
//
//		    tail.setNext(new Node(rental, null));
//		    tail = tail.getNext();
//    	}
//
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

        // Saves
        removedRental = temp.getNext().getData();
        temp.setNext(temp.getNext().getNext());

        return removedRental;
    }

    public Rental get(int index) {
	    //Not sure if this is the best way,
	    //if index is invalid, this will still return

	    //null if top is null
        if (top == null) {
            return null;
	    }

	    //Index error checks
	    if(index >= size() || index < 0) {
	        throw new IllegalArgumentException();
	    } else {
		    //code to find rental at index
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

