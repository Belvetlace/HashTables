import cs_1c.EBookEntry;
import cs_1c.EBookEntryReader;

import java.util.NoSuchElementException;
import java.util.Random;

// ----------- wrapper classes -------------
class EBookCompInt implements Comparable<Integer>
{
   private EBookEntry data;
   private State state;
   static final String lineSeparator = System.lineSeparator();

   public enum State
   {
      ACTIVE,
      EMPTY,
      DELETED;
   }

   EBookCompInt()
   {
      data = null;
      state = State.EMPTY;
   }

   EBookCompInt(EBookEntry b)
   {
      data = b;
      state = State.ACTIVE;
   }


   EBookCompInt(EBookEntry b, int st)
   {
      data = b;
      setState(st);
   }

   private void setState(int st)
   {
      switch (st)
      {
         case 1:
            state = State.ACTIVE;
            break;
         case 2:
            state = State.EMPTY;
            break;
         case 3:
            state = State.DELETED;
            break;
      }
   }

   public int getState()
   {
      switch (state)
      {
         case ACTIVE:
            return 1;
         case EMPTY:
            return 2;
         case DELETED:
            return 3;
      }
      return 0;
   }

   public int compareTo(Integer o)
   {
      return (data.getETextNum() - o);
   }

   @Override
   public String toString()
   {
      return "   # " + data.getETextNum() + "  ---------------" + lineSeparator
              + "   \"" + data.getTitle() + "\""  + lineSeparator
              + "   by " + data.getCreator()  + lineSeparator
              + "   re: " + data.getSubject() + lineSeparator + lineSeparator;
//      return data.toString();
//      return "# " + data.getETextNum() + "\n";
   }

   @Override
   public int hashCode()
   {
      Integer n = data.getETextNum();
      return n.hashCode();
   }

   public boolean equals(EBookCompInt obj)
   {
//      if (obj instanceof EBookEntry)
//      {
//         EBookEntry o = (EBookEntry) obj;
//         return (data.getETextNum() - o.getETextNum() == 0);
//      }
      return data.equals(obj.data);
   }
}

class EBookCompString implements Comparable<String>
{
   private EBookEntry data;
   private State state;
   static final String lineSeparator = System.lineSeparator();

   public enum State
   {
      ACTIVE,
      EMPTY,
      DELETED;
   }

   EBookCompString()
   {
      data = null;
      state = State.EMPTY;
   }

   EBookCompString(EBookEntry b)
   {
      data = b;
      state = State.ACTIVE;
   }

   EBookCompString(EBookEntry b, int st)
   {
      data = b;
      setState(st);
   }

   private void setState(int st)
   {
      switch (st)
      {
         case 1:
            state = State.ACTIVE;
            break;
         case 2:
            state = State.EMPTY;
            break;
         case 3:
            state = State.DELETED;
            break;
      }
   }

   public int getState()
   {
      switch (state)
      {
         case ACTIVE:
            return 1;
         case EMPTY:
            return 2;
         case DELETED:
            return 3;
      }
      return 0;
   }

   public int compareTo(String o)
   {
      return (data.getCreator().compareTo(o));
   }

   @Override
   public String toString()
   {
      return "   # " + data.getETextNum() + "  ---------------" + lineSeparator
              + "   \"" + data.getTitle() + "\""  + lineSeparator
              + "   by " + data.getCreator()  + lineSeparator
              + "   re: " + data.getSubject() + lineSeparator + lineSeparator;
   }

   @Override
   public int hashCode()
   {
      String s = data.getCreator();
      return (s != null) ? s.hashCode() : 0;
   }

   public boolean equals(EBookCompString obj)
   {
      return data.equals(obj.data);
   }
}

//------------------------------------------------------
public class Foothill
{

   public static final int NUM_RANDOM_INDICES = 25;

   // -------  main --------------
   public static void main(String[] args) throws Exception
   {
      boolean keyString = false; // false to run with Integer, true - String

      EBookEntryReader bookInput =
              new EBookEntryReader("catalog-short4.txt");

      if (bookInput.readError())
      {
         System.out.println("couldn't open " + bookInput.getFileName()
                 + " for input.");
         return;
      }
      System.out.println(bookInput.getFileName());
      System.out.println("books in the array: " + bookInput.getNumBooks());

      int range = bookInput.getNumBooks();
      Random rand = new Random();
      int[] randomIndices = new int[NUM_RANDOM_INDICES];
      for (int i = 0; i < randomIndices.length; i++)
         randomIndices[i] = rand.nextInt(range);

      // display NUM_RANDOM_INDICES books from array ...
      System.out.println("\nRandom books from the array:");
      for (int k = 0; k < NUM_RANDOM_INDICES; k++)
      {
         System.out.println(bookInput.getBook(randomIndices[k]).getETextNum()
                 + " " + bookInput.getBook(randomIndices[k]).getCreator());
      }

      if (keyString)
      {
         hashTableString(bookInput, randomIndices);
      } else
      {
         hashTableInt(bookInput, randomIndices);
      }

   }

   // hash table with string key
   private static void hashTableString(EBookEntryReader bookInput,
                                       int[] randomIndices)
   {
      FHhashQPwFind<String, EBookCompString> hashTable
              = new FHhashQPwFind<String, EBookCompString>();

      int inserted = 0;
      EBookCompString book;
      EBookEntry.setSortType(EBookEntry.SORT_BY_CREATOR);
      for (int b = 0; b < bookInput.getNumBooks(); b++)
      {
         book = new EBookCompString(bookInput.getBook(b), 1);
         // insert books into the hash table
         hashTable.insert(book);
         inserted++;
      }
      System.out.println("books inserted in the hash table: " + inserted);

      EBookCompString bookResult;

      // attempt to find on the selected key
      System.out.println("The same random books from the hash table STRING");
      for (int k = 0; k < NUM_RANDOM_INDICES; k++)
      {
         System.out.println("Searching for: "
                 + bookInput.getBook(randomIndices[k]).getCreator());
         try
         {
            bookResult = hashTable.find(bookInput.getBook(randomIndices[k]).getCreator());
            System.out.println(bookResult.toString());

         }
         catch (NoSuchElementException e)
         {
            System.out.println(bookInput.getBook(randomIndices[k]).getCreator()
                    + " not found: " + e.getMessage() + " " + e.toString());
         }
         System.out.println();
      }


      // test known successes failures exceptions:
      try
      {
         bookResult = hashTable.find("Jack Kerouac");
         //...
         System.out.println(bookResult.toString());
      } catch (NoSuchElementException e)
      {
         System.out.println("\'Jack Kerouac\' not found: " + e.getMessage() + " " + e.toString());
      }

      // more failures
      try
      {
      } catch (NoSuchElementException e)
      {
      }

      try
      {
      } catch (NoSuchElementException e)
      {
      }

   }

   // hash table with integer key
   private static void hashTableInt(EBookEntryReader bookInput,
                                    int[] randomIndices)
   {
      FHhashQPwFind<Integer, EBookCompInt> hashTable
              = new FHhashQPwFind<Integer, EBookCompInt>();

      EBookEntry.setSortType(EBookEntry.SORT_BY_ID);

      int inserted = 0;
      EBookCompInt book;
      for (int b = 0; b < bookInput.getNumBooks(); b++)
      {
         book = new EBookCompInt(bookInput.getBook(b), 1);
         // insert all books into the hash table (if SORT_BY_ID)
         if (hashTable.insert(book))
         {
            //System.out.print("inserted " + book.toString());
            inserted++;
         } else
         {
            System.out.print("failed " + book.toString());
         }

      }
      System.out.println("books inserted in the hash table: " + inserted);

      EBookCompInt bookResult;
      // attempt to find on the selected key
      System.out.println("\nThe same random books from the hash table: INT");
      for (int k = 0; k < NUM_RANDOM_INDICES; k++)
      {
         System.out.println("Searching for: "
                 + bookInput.getBook(randomIndices[k]).getETextNum());
         try
         {
            bookResult = hashTable.find(bookInput.getBook(randomIndices[k]).getETextNum());
            System.out.print("found: " + bookResult.toString());
         }
         catch (NoSuchElementException e)
         {
            System.out.println(bookInput.getBook(randomIndices[k]).getETextNum()
                    + " not found: " + e.getMessage() + " " + e.toString());
         }
         System.out.println();
      }


      // test known successes failures exceptions:
      try
      {
         bookResult = hashTable.find(-3);
         //...
      }
      catch (NoSuchElementException e)
      {
         System.out.println("-3 not found: " + e.getMessage() + " " + e.toString());
      }

      // more failures
      try
      {

      } catch (NoSuchElementException e)
      {
         System.out.println("not found: " + e.getMessage() + " " + e.toString());
      }

      try
      {

      } catch (NoSuchElementException e)
      {
         System.out.println("not found: " + e.getMessage() + " " + e.toString());
      }

   }
}