// this file formatted by JFormat - jlbalder@netscape.net
//  STARTER FILE FOR ARRBAG PROJECT #2

import java.io.*;
import java.util.*;

public class ArrBag<T>
{
    final int NOT_FOUND = -1;
    final int INITIAL_CAPACITY = 1;
    private int count; // LOGICAL SIZE
    
    // DEFINE & INITIALIZE REF TO OUR ARRAY OF T OBJECTS
    @SuppressWarnings("unchecked") // SUPRESSION APPLIES TO THE NEXT LINE OF CODE
    T[] theArray = (T[]) new Object[INITIAL_CAPACITY]; // CASTING TO T[] (creates warning we have to suppress)
    
    // DEFAULT C'TOR
    public ArrBag()
    {
        count = 0; // i.e. logical size, actual number of elems in the array
    }
    
    // SPECIFIC INITIAL LENGTH VERSION OF CONSTRUCTOR
    // only the union,intersect,diff call this version of constructor
    @SuppressWarnings("unchecked")
    public ArrBag( int optionalCapacity )
    {
        theArray = (T[]) new Object[optionalCapacity ];
        count = 0; // i.e. logical size, actual number of elems in the array
    }
    
    // C'TOR LOADS FROM FILE Of STRINGS
    @SuppressWarnings("unchecked")
    public ArrBag( String infileName ) throws Exception
    {
        count = 0; // i.e. logical size, actual number of elems in the array
        BufferedReader infile = new BufferedReader( new FileReader(  infileName ) );
        while ( infile.ready() )
            this.add( (T) infile.readLine() );
        infile.close();
    }
    
    // APPENDS NEW ELEM AT END OF LOGICAL ARRAY. UPSIZES FIRST IF NEEDED
    public boolean add( T element )
    {
        // THIS IS AN APPEND TO THE LOGICAL END OF THE ARRAY AT INDEX count
        if (size() == theArray.length) upSize(); // DOUBLES PHYSICAL CAPACITY
            theArray[ count++] = element; // ADD IS THE "setter"
        return true; // success. it was added
    }
    
    // INCR EVERY SUCESSFULL ADD, DECR EVERY SUCESSFUL REMOVE
    public int size()
    {
        return count;
    }
    
    // RETURNS TRUE IF THERE ARE NO ELEMENTS IN THE ARRAY, OTHERWISE FALSE
    public boolean isEmpty()
    {
        return count==0;
    }
    
    public T get( int index )
    {
        if ( index < 0 || index >=size() )
            die("FATAL ERROR IN .get() method. Index to uninitialized element or out of array bounds. Bogus index was: " + 
                 index + "\n" );
        return theArray[index];
    }
    
    // SEARCHES FOR THE KEY. TRUE IF FOUND, OTHERWISE FALSE
    public boolean contains( T key )
    {
        if (key == null) return false;
        for ( int i=0 ; i < size() ; ++i )
            if ( get(i).equals( key ) ) 
                return true;
            
        return false;
    }
    
    void die( String errMsg )
    {
        System.out.println( errMsg );
        System.exit(0);
    }
    
    
    // --------------------------------------------------------------------------------------------
    // # # # # # # # # # # #   Y O U   W R I T E   T H E S E   M E T H O D S  # # # # # # # # # # #
    // --------------------------------------------------------------------------------------------
    
    // PERFORMS LOGICAL (SHALLOW) REMOVE OF ALL THE ELEMENTS IN THE ARRAY (SIMPLE 1 LINER!)
    public void clear()
    {
        count=0;
    }
    
    // DOUBLE THE SIZE OF OUR ARRAY AND COPY ALL THE ELEMS INTO THE NEW ARRAY
    @SuppressWarnings("unchecked")
    private void upSize()
    {
        T[] newArray = (T[]) new Object [theArray.length * 2];
        for (int i =0; i < theArray.length; i++){
            newArray[i] = theArray[i];
        }
        theArray = newArray;
    }
    
    public String toString()
    {
         String toString  = "";
        for ( int i=0 ; i < size() ; ++i  )
        {
            toString += theArray[i];
            if ( i < size()-1 ) 
            toString += " ";
        }
        return toString;
    }
    
    // RETURNS A THIRD ARRBAG CONTAIING ONLY ONE COPY (NO DUPES) OF ALL THE ElEMENTS FROM BOTH BAGS
    // DOES -NOT- MODIFY THIS OR OTHER BAG
    public ArrBag<T> union( ArrBag<T> other )
    {
        ArrBag<T> unionArray = new ArrBag<T>(other.size() + this.size()); 

        int unionCount = 0; 
        for (int i = 0; i <this.size(); i++ ){
            unionArray.add(this.get(i));
            unionCount++;
        } for (int i = 0; i<other.size(); i++){
            if (!unionArray.contains(other.get(i))){
                unionArray.add(other.get(i));
                unionCount ++;
            }
        }
          return unionArray;
    } // END UNION
    
    // RETURNS A THIRD ARRBAG CONTAINING ONLY ONE COPY (NO DUPES) OF ALL THE ElEMENTS IN COMMON
    // DOES -NOT- MODIFY THIS OR OTHER
    public ArrBag<T> intersection( ArrBag<T> other )
    {
        int intersectCount = 0;
        ArrBag<T> intersectionArray;
         if (other.size() > this.size() )
         {
             intersectionArray = new ArrBag<T>(other.size()); 
         }
         else 
         {
             intersectionArray = new ArrBag<T>(this.size()); 

        for (int i = 0; i <other.size(); i++){
            if (this.contains (other.get(i))){
                intersectionArray.add(other.get(i));
                intersectCount ++; 
            }
        }
        // THIS ARRBAG WILL NEVER TRIGGER AN UPSIZE BECUASE YOU MADE IT JUST BIG ENOUGH FOR THE LARGEST POSSIBLE INTERSECT
        
    }
     return intersectionArray;
    }

    
    // RETURNS A THIRD ARRBAG CONTAIING ONLY ONE COPY (NO DUPES) OF ALL THE ElEMENTS
    // REMAINING AFTER THIS BAG - OTHER BAG
    // DOES -NOT- MODIFY THIS OR OTHER
    public ArrBag<T> difference( ArrBag<T> other )
    {
        // THIS ARRBAG WILL NEVER TRIGGER AN UPSIZE BECUASE YOU MADE IT JUST BIG ENOUGH FOR THE LARGEST POSSIBLE DIFF
        ArrBag<T> diffResult = new ArrBag<T>(this.size() + other.size());
        int differenceCount = 0;

        for(int i =0; i<this.size(); i++)
        {
            if(!other.contains(this.get(i)) && !diffResult.contains(this.get(i)))
            {
                diffResult.add(this.get(i));
            }
         // change the 0 to the right value for diff
        }
        return diffResult; 
    }
    
    // RETURNS A THIRD ARRBAG CONTAIING ONLY ONE COPY (NO DUPES) OF ALL THE ElEMENTS
    // CONTAINED IN THE UNION OF THIS AND OTHER - INTERSECTION OF THIS AND OTHER
    // DOES -NOT- MODIFY THE THIS OR OTHER
    public ArrBag<T> xor( ArrBag<T> other)
    {
        return this.union(other).difference(this.intersection(other));
        
    }
    }
// END ARRBAG CLASS