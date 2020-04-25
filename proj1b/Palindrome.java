public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> ans = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            ans.addLast(word.charAt(i));
        }
        return ans;
    }

    private boolean isPalindromeHelper(Deque<Character> deque, CharacterComparator cc) {
        if (deque.size() <= 1) {
            return true;
        }
        if (!cc.equalChars(deque.removeFirst(), deque.removeLast())) {
            return false;
        }
        return isPalindromeHelper(deque, cc);
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> ans = wordToDeque(word);
        return isPalindromeHelper(ans, cc);
    }
    public boolean isPalindrome(String word) {
        Deque<Character> ans = wordToDeque(word);
        Character first = null;
        Character last = null;
        boolean condition = true;
        while (ans.size() > 1) {
            first = ans.removeFirst();
            last = ans.removeLast();
            if (first != last) {
                condition = false;
                break;
            }
        }
        return condition;
    }

}
