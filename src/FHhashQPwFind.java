import cs_1c.FHhashQP;

public class FHhashQPwFind<KeyType, E extends Comparable<KeyType> >
        extends FHhashQP<E>
{

   //E find(KeyType key) - returns the found object, or throws a java.util.NoSuchElementException
   public E find(KeyType key)
   {

      return null;
   }

   //int myHashKey( KeyType key)  - a trivial modification to myHash() which uses the key rather than the object, to hash.
   protected int myHashKey( KeyType key)
   {

      return 0;
   }

   //int findPosKey( KeyType key ) - a trivial modification to findPos() which uses the key rather than the object, to get a position.
   protected int findPosKey( KeyType key )
   {

      return 0;
   }


}