import cs_1c.EBookEntry;

import java.util.NoSuchElementException;

// ----------- wrapper classes -------------
class EBookCompInt implements Comparable<Integer>
{
   private EBookEntry data;
   //int state; // better to use enum
   enum state {ACTIVE, EMPTY, DELETED};
   final int ACTIVE = 1;
   final int EMPTY = 2;
   final int DELETED = 3;

   @Override
   public int compareTo(Integer o)
   {
      return 0;
   }

   @Override
   public String toString()
   {

      return "";
   }

   @Override
   public int hashCode()
   {

      return super.hashCode();
   }

   @Override
   public boolean equals(Object obj)
   {

      return super.equals(obj);
   }
}

class EBookCompString implements Comparable<String>
{
   private EBookEntry data;
   //int state; // better to use enum
   enum state {ACTIVE, EMPTY, DELETED};

   @Override
   public int compareTo(String o)
   {
      return 0;
   }

   @Override
   public String toString()
   {

      return super.toString();
   }

   @Override
   public int hashCode()
   {

      return super.hashCode();
   }

   @Override
   public boolean equals(Object obj)
   {

      return super.equals(obj);
   }
}

//------------------------------------------------------
public class Foothill
{

   public static final int NUM_RANDOM_INDICES = 25;

   // -------  main --------------
   public static void main(String[] args) throws Exception
   {

      // FHhashQPwFind< Integer, EBookCompInt> hashTable 
      //    = new FHhashQPwFind<Integer, EBookCompInt>();

      FHhashQPwFind< String, EBookCompString> hashTable
              = new FHhashQPwFind<String, EBookCompString>();

      //...

      // create a QP hash table of EBooks ...
      // generate some random indices into the EBookEntryReader vector ...
      // insert all books into the hash table (if SORT_BY_ID) or fewer (If SORT_BY_CREATOR) ...
      // display NUM_RANDOM_INDICES books from array ...
      
      //...
      EBookCompString bookResult;


      // attempt to find on the selected key
      System.out.println( "The same random books from the hash table " );
      for (int k = 0; k < NUM_RANDOM_INDICES; k++)
      {
         //...
         try
         {
            // bookResult = hashTable.find( 
            // bookInput.getBook(randomIndices[k]).getCreator() );
            // bookResult = hashTable.find(bookInput.getBook(randomIndices[k]).getETextNum());

         }
         catch (NoSuchElementException e)
         {
            //...
         }
         System.out.println();
      }


      // test known successes failures exceptions:
      try
      {
         // bookResult = hashTable.find( "Jack Kerouac" );
         // bookResult = hashTable.find( -3 );
          
          //...

      }
      catch (NoSuchElementException e)
      {
      }

      // more failures
      try
      {
      }
      catch (NoSuchElementException e)
      {
      }

      try
      {
      }
      catch (NoSuchElementException e)
      {
      }


   }
}