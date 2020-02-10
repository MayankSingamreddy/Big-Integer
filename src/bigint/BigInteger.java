package bigint;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with
 * any number of digits, which overcomes the computer storage length limitation of
 * an integer.
 *
 * @author Mayank Singamreddy
 * @date 23 September 2019
 */
public class BigInteger {

    /**
     * True if this is a negative integer
     */
    boolean negative;

    /**
     * Number of digits in this integer
     */
    int numDigits;

    /**
     * Reference to the first node of this integer's linked list representation
     * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
     * For instance, the integer 235 would be stored as:
     * 5 --> 3  --> 2
     * <p>
     * Insignificant digits are not stored. So the integer 00235 will be stored as:
     * 5 --> 3 --> 2  (No zeros after the last 2)
     */
    DigitNode front;

    /**
     * Initializes this integer to a positive number with zero digits, in other
     * words this is the 0 (zero) valued integer.
     */
    public BigInteger() {
        negative = false;
        numDigits = 0;
        front = null;
    }


    /**
     * Parses an input integer string into a corresponding BigInteger instance.
     * A correctly formatted integer would have an optional sign as the first
     * character (no sign means positive), and at least one digit character
     * (including zero).
     * Examples of correct format, with corresponding values
     * Format     Value
     * +0            0
     * -0            0
     * +123        123
     * 1023       1023
     * 0012         12
     * 0             0
     * -123       -123
     * -001         -1
     * +000          0
     * <p>
     * Leading and trailing spaces are ignored. So "  +123  " will still parse
     * correctly, as +123, after ignoring leading and trailing spaces in the input
     * string.
     * <p>
     * Spaces between digits are not ignored. So "12  345" will not parse as
     * an integer - the input is incorrectly formatted.
     * <p>
     * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
     * constructor
     *
     * @param integer Integer string that is to be parsed
     * @return BigInteger instance that stores the input integer.
     * @throws IllegalArgumentException If input is incorrectly formatted
     */
    public static BigInteger parse(String integer)
            throws IllegalArgumentException {
        BigInteger x = new BigInteger();

        integer = integer.trim();
        //negative check
        if (integer.charAt(0) == '-')
            x.negative = true;
        else
            x.negative = false;


        //removing negative/positive sign
        if (integer.charAt(0) == '-' || integer.charAt(0) == '+')
            integer = integer.substring(1);

        while (integer.charAt(0) == '0' && integer.length() > 1) {
            integer = integer.substring(1);
        }


        x.numDigits = integer.length();

        //digit check
        if (Character.isDigit(integer.charAt(0)) == false)
            throw new IllegalArgumentException("Incorrect Format");

        //48 offset + placeholder value
        DigitNode prev = new DigitNode(integer.charAt(0) - 48, null);
        x.front = prev;

        if (integer.length() == 1 && integer.charAt(0) == '0') {
            x.front = null;
        }
        if (x.front == null)
            System.out.println("null");

        //insertion loop
        for (int i = 1; i < integer.length(); i++) {
            if (Character.isDigit(integer.charAt(i))) {
                x.front = new DigitNode((integer.charAt(i) - 48), (prev));
                prev = x.front;
            } else
                throw new IllegalArgumentException("Incorrect Format");
        }

        return x;
    }

    /**
     * Adds the first and second big integers, and returns the result in a NEW BigInteger object.
     * DOES NOT MODIFY the input big integers.
     * <p>
     * NOTE that either or both of the input big integers could be negative.
     * (Which means this method can effectively subtract as well.)
     *
     * @param first  First big integer
     * @param second Second big integer
     * @return Result big integer
     */
    public static BigInteger add(BigInteger first, BigInteger second) {
        BigInteger list = new BigInteger();
        list.front = new DigitNode(0, null);

        DigitNode nodeZ = list.front;
        DigitNode node1 = first.front;
        DigitNode node2 = second.front;


        DigitNode temporary = first.front;
        int lastFirst = 0;
        while (temporary != null) {
            lastFirst = temporary.digit;
            temporary = temporary.next;
        }

        temporary = second.front;
        int lastSecond = 0;
        while (temporary != null) {
            lastSecond = temporary.digit;
            temporary = temporary.next;
        }

        int biggerNumberStore = 0;

        //additions
        if ((!first.negative && !second.negative) || (first.negative && second.negative)) {
            while (node1 != null || node2 != null) {

                if (node1 != null) {
                    nodeZ.digit = nodeZ.digit + node1.digit;
                    node1 = node1.next;
                }
                if (node2 != null) {
                    nodeZ.digit = nodeZ.digit + node2.digit;
                    node2 = node2.next;
                }
                while (nodeZ.digit >= 10) {
                    if (nodeZ.next == null && nodeZ.digit >= 10) {
                        nodeZ.next = new DigitNode(0, null);
                    }
                    nodeZ.next.digit = nodeZ.digit / 10 + nodeZ.next.digit;
                    nodeZ.digit %= 10;
                }
                if (nodeZ.next == null) {
                    nodeZ.next = new DigitNode(0, null);
                }
                nodeZ = nodeZ.next;
            }
            if (first.negative) // if one is true, they both are in this check
                list.negative = true;
        }

        //subtractions
        else {

            //if 1>2
            //if first is greater than second
            if (((first.numDigits == second.numDigits) && (lastFirst > lastSecond)) || (first.numDigits > second.numDigits)) {


                while (node1 != null || node2 != null) {

                    if (node1 != null) {
                        nodeZ.digit = nodeZ.digit + node1.digit;
                        biggerNumberStore = node1.digit;
                        node1 = node1.next;
                    }
                    if (node2 != null) {
                        if (biggerNumberStore < node2.digit) {
                            nodeZ.digit = (10 + nodeZ.digit - (node2.digit));
                            node1.digit -= 1;
                        }
                        else nodeZ.digit = nodeZ.digit - node2.digit;
                        node2 = node2.next;
                    }
                    /*while (nodeZ.digit >= 10) {
                        if (nodeZ.next == null && nodeZ.digit >= 10) {
                            nodeZ.next = new DigitNode(0, null);
                        }
                        nodeZ.next.digit = nodeZ.digit / 10 + nodeZ.next.digit;
                        nodeZ.digit %= 10;
                    }
                    */

                    if (nodeZ.next == null) {
                        nodeZ.next = new DigitNode(0, null);
                    }
                    nodeZ = nodeZ.next;


                }
                if (first.negative)
                    list.negative = true;
                else
                    list.negative = false;
            }

            //if 2>1
            //if second is greater than first
            else if (((first.numDigits == second.numDigits) && (lastFirst < lastSecond)) || (second.numDigits > first.numDigits)) {
                while (node1 != null || node2 != null) {

                    if (node2 != null) {
                        nodeZ.digit = nodeZ.digit + node2.digit;
                        biggerNumberStore = node2.digit;
                        node2 = node2.next;
                    }
                    if (node1 != null) {
                        if (biggerNumberStore < node1.digit) {
                            nodeZ.digit = (10 + nodeZ.digit - (node1.digit));
                            node2.digit -= 1;
                        } else nodeZ.digit = nodeZ.digit - node1.digit;
                        node1 = node1.next;
                    }
                    /*while (nodeZ.digit >= 10) {
                        if (nodeZ.next == null && nodeZ.digit >= 10) {
                            nodeZ.next = new DigitNode(0, null);
                        }
                        nodeZ.next.digit = nodeZ.digit / 10 + nodeZ.next.digit;
                        nodeZ.digit %= 10;
                    }
                    */

                    if (nodeZ.next == null) {
                        nodeZ.next = new DigitNode(0, null);
                    }
                    nodeZ = nodeZ.next;

                }
                if (second.negative)
                    list.negative = true;
                else
                    list.negative = false;

            }






            //end of subtractions
        }



        //remove zeros
        DigitNode iteration = list.front;


            while (iteration.next!=null) {

                    if (iteration.next.digit == 0 && iteration.next.next == null) {
                        iteration.next = null;
                        iteration = list.front;

                    }
                iteration = iteration.next;
            }



        //new try of remove zero
/*
        boolean checker = false;
        iteration=list.front;
        int zct =0;
        while(iteration!=null){
            if(iteration.digit ==0)
                zct++;
            else
                zct=0;
            iteration = iteration.next;
        }
        while(zct>0) { //DOES THIS NEED EQUALS OR NOT
            iteration = list.front;
            checker =false;
            while(!checker){
                if(iteration.next.next==null&&iteration.next.digit==0){
                    iteration.next = null;
                    zct--;
                    checker = true;
                }
                iteration = iteration.next;
            }

        }
        */






        //count numDigits
        iteration = list.front;
        int counter = 0;
        while (iteration != null) {
            counter++;
            iteration = iteration.next;
        }
        list.numDigits = counter;



        return list;
    }



    /**
     * Returns the BigInteger obtained by multiplying the first big integer
     * with the second big integer
     * <p>
     * This method DOES NOT MODIFY either of the input big integers
     *
     * @param first  First big integer
     * @param second Second big integer
     * @return A new BigInteger which is the product of the first and second big integers
     */
    public static BigInteger multiply(BigInteger first, BigInteger second) {
        DigitNode current1 = first.front;
        DigitNode current2 = second.front;
        BigInteger ans = new BigInteger();
        ans.front = new DigitNode(0,null);
        int current = 0;
        DigitNode startNode = ans.front;
        DigitNode currentNode = ans.front;

        for (int outer = 0; outer < second.numDigits; outer++) { //outer loop
            for (int inner = 0; inner < first.numDigits; inner++) { //inner loop
                current = current1.digit * current2.digit + currentNode.digit; //multiplication
                currentNode.digit = current%10;
                if(currentNode.next == null)
                    currentNode.next = new DigitNode(0,null);
                currentNode = currentNode.next;
                DigitNode head = currentNode;
                //carry
                while(current>=10){
                    current = current/10+head.digit;
                    head.digit = current%10;
                    if(head.next == null&&current>=10){
                        head.next = new DigitNode(0,null);
                    }
                    head = head.next;
                }

                current1 = current1.next;
                //end carry
            }
            current1 = first.front; //reset
            startNode = startNode.next;
            currentNode = startNode;
            if(current2.next !=null)
                current2 = current2.next;//iteration of outer
        }

        if (first.negative == true && second.negative == true)
            ans.negative = false;
        else if (first.negative == true || second.negative == true)
            ans.negative = true;
        else
            ans.negative = false;

        //remove extra zeroes
        DigitNode temporary = ans.front;

        boolean check = false;
        while(check ==false){
            if(temporary.next.next==null&&temporary.next.digit==0){
                temporary.next=null;
                if(temporary.digit==0)
                    check =false;
                else check = true;
            }
            else if(temporary.next.next==null&&temporary.next.digit!=0)
                check = true;
            temporary = temporary.next;

        }

        temporary = ans.front;
        int counter =0;
        while(temporary!=null){
            counter++;
            temporary=temporary.next;
        }
        ans.numDigits = counter;
        return ans;
    }



    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        if (front == null) {
            return "0";
        }
        String retval = front.digit + "";
        for (DigitNode curr = front.next; curr != null; curr = curr.next) {
            retval = curr.digit + retval;
        }

        if (negative) {
            retval = '-' + retval;
        }
        return retval;
    }
}
