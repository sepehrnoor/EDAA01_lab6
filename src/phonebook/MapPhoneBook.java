package phonebook;

import java.io.Serializable;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Sepehr on 2/22/2017.
 */
public class MapPhoneBook implements PhoneBook, Serializable {
    private static final long serialVersionUID = 1L;
    private int size;
    private HashMap<String,TreeSet<String>> book;

    public MapPhoneBook() {
        size = 0;
        book = new HashMap<>();
    }

    public boolean put(String name, String number) {
        if (!book.containsKey(name)){
            book.put(name,new TreeSet<>());
            size++;
        }
        return book.get(name).add(number);
    }

    public boolean remove(String name) {
        if (book.containsKey(name)){
            book.remove(name);
            size--;
            return true;
        }
        return false;
    }

    public boolean removeNumber(String name, String number) {
        return (book.containsKey(name)) && book.get(name).remove(number);
    }

    public Set<String> findNumbers(String name) {
        if (!book.containsKey(name)) return new TreeSet<>();
        TreeSet<String> res = new TreeSet<>();
        res.addAll(book.get(name));
        return res;
    }

    public Set<String> findNames(String number) {
        return book.entrySet().stream().filter(x -> x.getValue().contains(number)).map(Map.Entry::getKey).collect(Collectors.toCollection(TreeSet::new));
    }

    public Set<String> names() {
        TreeSet<String> res = new TreeSet<>();
        res.addAll(book.keySet());
        return res;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
