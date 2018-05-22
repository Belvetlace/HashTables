import cs_1c.EBookEntry;
import cs_1c.EBookEntryReader;

import java.util.NoSuchElementException;
import java.util.Random;

// ----------- wrapper classes -------------
class EBookCompInt implements Comparable<Integer>
{
   private EBookEntry data;
   static final String lineSeparator = System.lineSeparator();

   EBookCompInt(EBookEntry b)
   {
      data = b;
   }

   public EBookEntry getData()
   {
      return data;
   }

   public int compareTo(Integer o)
   {
      return (data.getETextNum() - o);
   }

   @Override
   public String toString()
   {
      return "   # " + data.getETextNum() + "  ---------------" + lineSeparator
              + "   \"" + data.getTitle() + "\"" + lineSeparator
              + "   by " + data.getCreator() + lineSeparator
              + "   re: " + data.getSubject() + lineSeparator + lineSeparator;
   }

   @Override
   public int hashCode()
   {
      Integer n = data.getETextNum();
      return n.hashCode();
   }

   public boolean equals(EBookCompInt obj)
   {
      return data.equals(obj.getData());
   }
}

class EBookCompString implements Comparable<String>
{
   private EBookEntry data;
   static final String lineSeparator = System.lineSeparator();

   EBookCompString(EBookEntry b)
   {
      data = b;
   }

   public EBookEntry getData()
   {
      return data;
   }

   public int compareTo(String o)
   {
      return (data.getCreator().compareTo(o));
   }

   @Override
   public String toString()
   {
      return "   # " + data.getETextNum() + "  ---------------" + lineSeparator
              + "   \"" + data.getTitle() + "\"" + lineSeparator
              + "   by " + data.getCreator() + lineSeparator
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
      return data.equals(obj.getData());
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
         book = new EBookCompString(bookInput.getBook(b));
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
            System.out.println("found:\n" + bookResult);

         } catch (NoSuchElementException e)
         {
            System.out.println(bookInput.getBook(randomIndices[k]).getCreator()
                    + " not found: " + e.getMessage() + " " + e.toString());
         }
         System.out.println();
      }

      // failures
      try
      {
         bookResult = hashTable.find("Jack Kerouac");
         System.out.println("found:\n" + bookResult);
      } catch (NoSuchElementException e)
      {
         System.out.println("\'Jack Kerouac\' not found: "
                 + e.getMessage() + " " + e.toString());
      }

      try
      {
         bookResult = hashTable.find("");
         System.out.println("found:\n" + bookResult);
      } catch (NoSuchElementException e)
      {
         System.out.println("empty string as a key: "
                 + e.getMessage() + " " + e.toString());
      }

      try
      {
         bookResult = hashTable.find(null);
         System.out.println("found:\n" + bookResult);
      } catch (NoSuchElementException e)
      {
         System.out.println("null as a key: "
                 + e.getMessage() + " " + e.toString());
      } catch (NullPointerException ex)
      {
         System.out.println("null as a key: "
                 + ex.getMessage() + " " + ex.toString());
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
         book = new EBookCompInt(bookInput.getBook(b));
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
            bookResult = hashTable.find(bookInput
                    .getBook(randomIndices[k]).getETextNum());
            System.out.print("found: " + bookResult);
         } catch (NoSuchElementException e)
         {
            System.out.println(bookInput.getBook(randomIndices[k]).getETextNum()
                    + " not found: " + e.getMessage() + " " + e.toString());
         }
         System.out.println();
      }

      // failures
      try
      {
         bookResult = hashTable.find(-3);
         System.out.print("found: " + bookResult);
      } catch (NoSuchElementException e)
      {
         System.out.println("-3 not found: " + e.getMessage() + " " + e.toString());
      }

      try
      {
         bookResult = hashTable.find(null);
         System.out.print("found: " + bookResult);
      } catch (NoSuchElementException e)
      {
         System.out.println("not found: " + e.getMessage() + " " + e.toString());
      } catch (NullPointerException ex)
      {
         System.out.println("argument is null: " + ex.getMessage() + " " + ex.toString());
      }

      try
      {
         bookResult = hashTable.find(0);
         System.out.print("found: " + bookResult);
      } catch (NoSuchElementException e)
      {
         System.out.println("not found: " + e.getMessage() + " " + e.toString());
      }

   }
}

/* RUN with String key -----------------
catalog-short4.txt
books in the array: 4863

Random books from the array:
28871 Rawlinson, George, 1812-1902
21400 Haslam, W. E.
28637 Oliphant, Mrs. (Margaret), 1828-1897
26461 Piper, H. Beam, 1904-1964
24882 (no data found)
27268 Ruskin, John, 1819-1900
27434 Chapman, S. E. (Samuel E.)
27408 (no data found)
26472 Tolstoy, Leo, graf, 1828-1910
14053 Various
26213 Twain, Mark, 1835-1910
365 Austin, Mary Hunter, 1868-1934
25954 Burnham, Clara Louise, 1854-1927
30051 (no data found)
12390 Smith, Logan Pearsall, 1865-1949
26054 Munsell, A. H. (Albert Henry), 1858-1918
30036 Hamilton, Frederick W. (Frederick William), 1860-1940
25738 Wilson, Ann
26288 Marvell, Andrew, 1621-1678
29786 Dunn, Byron A. (Byron Archibald), 1842-1926
27523 (no data found)
26799 Biggs, William, 1755-1827
28084 Jenkins, Herbert George, 1876-1923
10892 Haggard, Henry Rider, 1856-1925
26512 Allen, J. A. (Joel Asaph), 1838-1921
books inserted in the hash table: 4863
The same random books from the hash table STRING
Searching for: Rawlinson, George, 1812-1902
found:
   # 28871  ---------------
   "The Seven Great Monarchies Of The Ancient Asian WorldA Linked Index to the P
roject Gutenberg Editions"
   by Rawlinson, George, 1812-1902
   re: History, Ancient



Searching for: Haslam, W. E.
found:
   # 21400  ---------------
   "Style in Singing"
   by Haslam, W. E.
   re: (no data found)



Searching for: Oliphant, Mrs. (Margaret), 1828-1897
found:
   # 29891  ---------------
   "The Rector"
   by Oliphant, Mrs. (Margaret), 1828-1897
   re: Fiction



Searching for: Piper, H. Beam, 1904-1964
found:
   # 28792  ---------------
   "Omnilingual"
   by Piper, H. Beam, 1904-1964
   re: Science fiction



Searching for: (no data found)
found:
   # 10237  ---------------
   "Siegel-Myers School of Music - Vocal Record F"
   by (no data found)
   re: (no data found)



Searching for: Ruskin, John, 1819-1900
found:
   # 19980  ---------------
   "A Joy For Ever(And Its Price in the Market)"
   by Ruskin, John, 1819-1900
   re: Art



Searching for: Chapman, S. E. (Samuel E.)
found:
   # 27434  ---------------
   "Doctor Jones' Picnic"
   by Chapman, S. E. (Samuel E.)
   re: Science fiction



Searching for: (no data found)
found:
   # 10237  ---------------
   "Siegel-Myers School of Music - Vocal Record F"
   by (no data found)
   re: (no data found)



Searching for: Tolstoy, Leo, graf, 1828-1910
found:
   # 2637  ---------------
   "Youth"
   by Tolstoy, Leo, graf, 1828-1910
   re: Social problems -- Fiction



Searching for: Various
found:
   # 29767  ---------------
   "The Continental Monthly, Vol. 4, No. 2, August, 1863Devoted to Literature an
d National Policy"
   by Various
   re: Literature, Modern -- 19th century -- Periodicals



Searching for: Twain, Mark, 1835-1910
found:
   # 26213  ---------------
   "Fenimore Cooper's Literary Offenses"
   by Twain, Mark, 1835-1910
   re: American literature -- History and criticism -- Theory, etc.



Searching for: Austin, Mary Hunter, 1868-1934
found:
   # 365  ---------------
   "The Land of Little Rain"
   by Austin, Mary Hunter, 1868-1934
   re: California -- Social life and customs



Searching for: Burnham, Clara Louise, 1854-1927
found:
   # 25954  ---------------
   "The Opened Shutters"
   by Burnham, Clara Louise, 1854-1927
   re: PS



Searching for: (no data found)
found:
   # 10237  ---------------
   "Siegel-Myers School of Music - Vocal Record F"
   by (no data found)
   re: (no data found)



Searching for: Smith, Logan Pearsall, 1865-1949
found:
   # 12390  ---------------
   "Society for Pure English, Tract 03 (1920)A Few Practical Suggestions"
   by Smith, Logan Pearsall, 1865-1949
   re: (no data found)



Searching for: Munsell, A. H. (Albert Henry), 1858-1918
found:
   # 26054  ---------------
   "A Color NotationA measured color system, based on the three qualities Hue,Va
lue and Chroma"
   by Munsell, A. H. (Albert Henry), 1858-1918
   re: Color



Searching for: Hamilton, Frederick W. (Frederick William), 1860-1940
found:
   # 30036  ---------------
   "Word Study and English GrammarA Primer of Information about Words, Their Rel
ations and Their Uses"
   by Hamilton, Frederick W. (Frederick William), 1860-1940
   re: English language -- Grammar



Searching for: Wilson, Ann
found:
   # 25744  ---------------
   "TeamsA Terran Empire story"
   by Wilson, Ann
   re: Science fiction



Searching for: Marvell, Andrew, 1621-1678
found:
   # 26288  ---------------
   "To His Coy Mistress"
   by Marvell, Andrew, 1621-1678
   re: English poetry



Searching for: Dunn, Byron A. (Byron Archibald), 1842-1926
found:
   # 29786  ---------------
   "Raiding with Morgan"
   by Dunn, Byron A. (Byron Archibald), 1842-1926
   re: United States -- History -- Civil War, 1861-1865 -- Fiction



Searching for: (no data found)
found:
   # 10237  ---------------
   "Siegel-Myers School of Music - Vocal Record F"
   by (no data found)
   re: (no data found)



Searching for: Biggs, William, 1755-1827
found:
   # 26799  ---------------
   "Narrative of the Captivity of William Biggs among the Kickapoo Indians in Il
linois in 1788"
   by Biggs, William, 1755-1827
   re: Indian captivities



Searching for: Jenkins, Herbert George, 1876-1923
found:
   # 28084  ---------------
   "Malcolm Sage, Detective"
   by Jenkins, Herbert George, 1876-1923
   re: Detective and mystery stories



Searching for: Haggard, Henry Rider, 1856-1925
found:
   # 3096  ---------------
   "Beatrice"
   by Haggard, Henry Rider, 1856-1925
   re: English fiction



Searching for: Allen, J. A. (Joel Asaph), 1838-1921
found:
   # 26512  ---------------
   "Description of a New Vespertilionine Bat from YucatanAuthor's Edition, extra
cted from Bulletin of the AmericanMuseum of Natural History, Vol. IX, September
28, 1897"
   by Allen, J. A. (Joel Asaph), 1838-1921
   re: Vespertilionidae



'Jack Kerouac' not found: null java.util.NoSuchElementException
empty string as a key: null java.util.NoSuchElementException
null as a key: null java.lang.NullPointerException

 ------------------------------------------RUN with Integer key -----------------
 catalog-short4.txt
books in the array: 4863

Random books from the array:
25572 Caine, Hall, Sir, 1853-1931
28849 Blaine, John
28656 Melville, Herman, 1819-1891
26233 Richmond, Grace S. (Grace Smith), 1866-1959
15721 Conquest, Joan
4790 MacGrath, Harold, 1871-1932
27462 Campbell, John Wood, 1910-1971
26606 Various
25110 Perry, Ralph Barton, 1876-1957
28284 Hobson, J. A. (John Atkinson), 1858-1940
25356 May, Sophie, 1833-1906
28185 Various
26849 Gillespie, George, 1613-1648
28741 Hough, Emerson, 1857-1923
1704 Balzac, HonorÃ© de, 1799-1850
28098 Murray, W. H. H. (William Henry Harrison), 1840-1904
28728 Duffield, J. W.
28445 Abbott, John S. C. (John Stevens Cabot), 1805-1877
30132 Davidson, Samuel, 1806-1898
26924 Stidger, William L. (William Le Roy), 1885-1949
13974 Warren, G. B.
28304 Various
13648 Lear, Edward, 1812-1888
27761 Shakespeare, William, 1564-1616
25143 Garis, Howard Roger, 1873-1962
books inserted in the hash table: 4863

The same random books from the hash table: INT
Searching for: 25572
found:    # 25572  ---------------
   "Capt'n Davy's Honeymoon"
   by Caine, Hall, Sir, 1853-1931
   re: Isle of Man -- Fiction


Searching for: 28849
found:    # 28849  ---------------
   "Smugglers' Reef"
   by Blaine, John
   re: Detective and mystery stories


Searching for: 28656
found:    # 28656  ---------------
   "Typee"
   by Melville, Herman, 1819-1891
   re: Adventure stories


Searching for: 26233
found:    # 26233  ---------------
   "The Indifference of Juliet"
   by Richmond, Grace S. (Grace Smith), 1866-1959
   re: American fiction -- 19th century


Searching for: 15721
found:    # 15721  ---------------
   "The Hawk of Egypt"
   by Conquest, Joan
   re: (no data found)


Searching for: 4790
found:    # 4790  ---------------
   "Half a Rogue"
   by MacGrath, Harold, 1871-1932
   re: Fiction


Searching for: 27462
found:    # 27462  ---------------
   "The Last Evolution"
   by Campbell, John Wood, 1910-1971
   re: Science fiction


Searching for: 26606
found:    # 26606  ---------------
   "Uncanny Tales"
   by Various
   re: Short stories


Searching for: 25110
found:    # 25110  ---------------
   "The Approach to Philosophy"
   by Perry, Ralph Barton, 1876-1957
   re: Philosophy


Searching for: 28284
found:    # 28284  ---------------
   "The Evolution of Modern CapitalismA Study of Machine Production"
   by Hobson, J. A. (John Atkinson), 1858-1940
   re: Industries -- History


Searching for: 25356
found:    # 25356  ---------------
   "Aunt Madge's Story"
   by May, Sophie, 1833-1906
   re: Christian life -- Juvenile fiction


Searching for: 28185
found:    # 28185  ---------------
   "Harper's Young People, November 4, 1879An Illustrated Weekly"
   by Various
   re: Children's periodicals, American


Searching for: 26849
found:    # 26849  ---------------
   "The Works of Mr. George Gillespie (Vol. 1 of 2)"
   by Gillespie, George, 1613-1648
   re: Gillespie, George, 1613-1648


Searching for: 28741
found:    # 28741  ---------------
   "The Young Alaskans on the Trail"
   by Hough, Emerson, 1857-1923
   re: PZ


Searching for: 1704
found:    # 1704  ---------------
   "Pierrette"
   by Balzac, HonorÃ© de, 1799-1850
   re: French literature


Searching for: 28098
found:    # 28098  ---------------
   "Holiday TalesChristmas in the Adirondacks"
   by Murray, W. H. H. (William Henry Harrison), 1840-1904
   re: Christmas stories


Searching for: 28728
found:    # 28728  ---------------
   "Bert Wilson on the Gridiron"
   by Duffield, J. W.
   re: Football -- Juvenile fiction


Searching for: 28445
found:    # 28445  ---------------
   "Madame Roland, Makers of History"
   by Abbott, John S. C. (John Stevens Cabot), 1805-1877
   re: France -- History -- Revolution, 1789-1799


Searching for: 30132
found:    # 30132  ---------------
   "The Canon of the Bible"
   by Davidson, Samuel, 1806-1898
   re: Bible -- Canon


Searching for: 26924
found:    # 26924  ---------------
   "Flash-lights from the Seven Seas"
   by Stidger, William L. (William Le Roy), 1885-1949
   re: East Asia -- Description and travel


Searching for: 13974
found:    # 13974  ---------------
   "The Last West and Paolo's Virginia"
   by Warren, G. B.
   re: (no data found)


Searching for: 28304
found:    # 28304  ---------------
   "Harper's Young People, January 13, 1880An Illustrated Weekly"
   by Various
   re: Children's periodicals, American


Searching for: 13648
found:    # 13648  ---------------
   "More Nonsense"
   by Lear, Edward, 1812-1888
   re: Limericks


Searching for: 27761
found:    # 27761  ---------------
   "Hamlet"
   by Shakespeare, William, 1564-1616
   re: Tragedies


Searching for: 25143
found:    # 25143  ---------------
   "The Curlytops and Their Playmatesor Jolly Times Through the Holidays"
   by Garis, Howard Roger, 1873-1962
   re: Brothers and sisters -- Juvenile fiction


-3 not found: null java.util.NoSuchElementException
argument is null: null java.lang.NullPointerException
not found: null java.util.NoSuchElementException

*/