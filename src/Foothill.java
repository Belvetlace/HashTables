import cs_1c.EBookEntry;
import cs_1c.EBookEntryReader;

import java.util.NoSuchElementException;
import java.util.Random;

// ----------- wrapper classes -------------
class EBookCompInt implements Comparable<Integer>
{
   private EBookEntry data;
   private State state;

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

   @Override
   public int compareTo(Integer o)
   {
      return (data.getETextNum() - o);
   }

   @Override
   public String toString()
   {
      return "# " + data.getETextNum() + "\n";
   }

   @Override
   public int hashCode()
   {
      Integer n = data.getETextNum();
      return n.hashCode();
   }

   @Override
   public boolean equals(Object obj)
   {
      if (obj instanceof EBookEntry)
      {
         EBookEntry o = (EBookEntry) obj;
         return (data.getETextNum() - o.getETextNum() == 0);
      }
      return false;
   }
}

class EBookCompString implements Comparable<String>
{
   private EBookEntry data;
   private State state;

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

   @Override
   public int compareTo(String o)
   {
      return (data.getTitle().compareToIgnoreCase(o));
   }

   @Override
   public String toString()
   {
      return "# " + data.getTitle() + "\n";
   }

   @Override
   public int hashCode()
   {
      String s = data.getTitle();
      return (s != null) ? s.hashCode() : 0;
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
      System.out.println(bookInput.getNumBooks());

      int range = bookInput.getNumBooks();
      Random rand = new Random();
      int[] randomIndices = new int[NUM_RANDOM_INDICES];
      for (int i = 0; i < randomIndices.length; i++)
         randomIndices[i] = rand.nextInt(range);

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

      EBookCompString book;
      EBookEntry.setSortType(EBookEntry.SORT_BY_TITLE);
      for (int b = 0; b < bookInput.getNumBooks(); b++)
      {
         book = new EBookCompString(bookInput.getBook(b), 1);
         // insert books into the hash table
         hashTable.insert(book);

      }

      EBookCompString bookResult;
      // display NUM_RANDOM_INDICES books from array ...


      // attempt to find on the selected key
      System.out.println("The same random books from the hash table ");
      for (int k = 0; k < NUM_RANDOM_INDICES; k++)
      {
         try
         {
            bookResult = hashTable.find(bookInput.getBook(randomIndices[k]).getTitle());

         } catch (NoSuchElementException e)
         {
            System.out.println(bookInput.getBook(randomIndices[k]).getTitle()
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

      EBookCompInt book;
      for (int b = 0; b < bookInput.getNumBooks(); b++)
      {
         book = new EBookCompInt(bookInput.getBook(b), 1);
         // insert all books into the hash table (if SORT_BY_ID)

      }


      // display NUM_RANDOM_INDICES books from array ...

      EBookCompInt bookResult;

      // attempt to find on the selected key
      System.out.println("The same random books from the hash table ");
      for (int k = 0; k < NUM_RANDOM_INDICES; k++)
      {
         //...
         try
         {
            bookResult = hashTable.find(bookInput.getBook(randomIndices[k]).getETextNum());

         } catch (NoSuchElementException e)
         {
            //...
         }
         System.out.println();
      }


      // test known successes failures exceptions:
      try
      {
         bookResult = hashTable.find(-3);

         //...

      } catch (NoSuchElementException e)
      {
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
}