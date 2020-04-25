import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    //Uncomment this class once you've created your Palindrome class. */
    @Test
    public void testCommonIsPalindrome() {
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(!palindrome.isPalindrome("Horse"));
        assertTrue(!palindrome.isPalindrome("Rancor"));
        assertTrue(palindrome.isPalindrome("abcba"));
        assertTrue(palindrome.isPalindrome("abcdefedcba"));
    }

    @Test
    public void testCornerIsPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("c"));
        assertTrue(palindrome.isPalindrome("cc"));
        assertTrue(!palindrome.isPalindrome("cC"));
        assertTrue(!palindrome.isPalindrome("aA"));
        assertTrue(palindrome.isPalindrome("AA"));
    }


    @Test
    public void testNewIsPalindrome() {
        CharacterComparator comparator = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", comparator));
        assertTrue(palindrome.isPalindrome("acfdb", comparator));
        assertTrue(palindrome.isPalindrome("%afb&", comparator));
        assertTrue(!palindrome.isPalindrome("ff", comparator));
        assertTrue(!palindrome.isPalindrome("aA", comparator));
        assertTrue(palindrome.isPalindrome("AB", comparator));
    }
}
