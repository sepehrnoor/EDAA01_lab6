package application;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import phonebook.PhoneBook;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PhoneBookMenu extends MenuBar {
    private PhoneBook phoneBook;
    private NameListView nameListView;

    /**
     * Creates the menu for the phone book application.
     *
     * @param phoneBook    the phone book with names and numbers
     * @param nameListView handles the list view for the names
     */
    public PhoneBookMenu(PhoneBook phoneBook, NameListView nameListView) {
        this.phoneBook = phoneBook;
        this.nameListView = nameListView;

        final Menu menuPhoneBook = new Menu("PhoneBook");
        final MenuItem menuQuit = new MenuItem("Quit");
        menuQuit.setOnAction(e -> Platform.exit());
        menuPhoneBook.getItems().addAll(menuQuit);

        final Menu menuFind = new Menu("Find");

        final MenuItem menuShowAll = new MenuItem("Show All");
        final MenuItem menuFindNumbers = new MenuItem("Find number(s)");
        final MenuItem menuFindNames = new MenuItem("Find name(s)");
        final MenuItem menuFindPersons = new MenuItem("Find person(s)");
        menuShowAll.setOnAction(e -> showAll());
        menuFindNumbers.setOnAction(e -> findNumbers());
        menuFindNames.setOnAction(e -> findNames());
        menuFindPersons.setOnAction(e -> findPersons());
        menuFind.getItems().addAll(menuShowAll, menuFindNames, menuFindNumbers, menuFindPersons);

        getMenus().addAll(menuPhoneBook, menuFind);
        //    setUseSystemMenuBar(true);  // if you want operating system rendered menus, uncomment this line
    }


    private void showAll() {
        nameListView.fillList(phoneBook.names());
        nameListView.clearSelection();
    }

    private void findNames() {
        Optional<String> number = Dialogs.oneInputDialog("Find names(s) for number", null, "Number");
        if (number.isPresent()) {
            Set<String> names = phoneBook.findNames(number.get());
            if (!names.isEmpty()) {
                nameListView.clearSelection();
                nameListView.fillList(names);
            } else {
                Dialogs.alert("Not found", null, "No names found for entered number.");
            }
        } else {
            Dialogs.alert("No number entered", null, "Please enter a number.");
        }
    }

    private void findNumbers() {
        Optional<String> name = Dialogs.oneInputDialog("Find number(s) for name", null, "Name");
        if (name.isPresent()) {
            Set<String> numbers = phoneBook.findNumbers(name.get());
            if (!numbers.isEmpty()) {
                HashSet<String> nameOnly = new HashSet<>();
                nameOnly.add(name.get());
                nameListView.fillList(nameOnly);
                nameListView.select(0);
            } else {
                Dialogs.alert("Not found", null, "No numbers found for entered name.");
            }
        } else {
            Dialogs.alert("No name entered", null, "Please enter name.");
        }
    }

    private void findPersons() {
        Optional<String> searchString = Dialogs.oneInputDialog("Find person(s) matching text", null, "Text");
        if (searchString.isPresent()) {
            Set<String> names = phoneBook.names().stream().filter(x -> x.startsWith(searchString.get())).collect(Collectors.toCollection(HashSet::new));
            if (!names.isEmpty()) {
                nameListView.fillList(names);
            } else {
                Dialogs.alert("Not found", null, "No names found for entered text.");
            }
        } else {
            Dialogs.alert("No number entered", null, "Please enter a number.");
        }
    }

}
