package application;
import java.io.*;
import java.util.Locale;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import phonebook.MapPhoneBook;
import phonebook.PhoneBook;

public class PhoneBookApplication extends Application{
	private PhoneBook phoneBook;
	private NameListView nameListView;

	/**
	 * The entry point for the Java program.
	 * @param args
	 */
	public static void main(String[] args) {	
		// launch() do the following:
		// - creates an instance of the Main class
		// - calls Main.init()
		// - create and start the javaFX application thread
		// - waits for the javaFX application to finish (close all windows)
		// the javaFX application thread do:
		// - calls Main.start(Stage s)
		// - runs the event handling loop
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        if (Dialogs.confirmDialog("Read from file?","Confirm read","Would you like to read from file?")) phoneBook = readFromFile();
        else phoneBook = new MapPhoneBook();
		
		// set default locale english 
		Locale.setDefault(Locale.ENGLISH);
		
		nameListView = new NameListView(phoneBook);
		BorderPane root = new BorderPane();
		root.setTop(new PhoneBookMenu(phoneBook, nameListView));
		root.setCenter(nameListView);		
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("PhoneBook");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

    private void writeToFile(){
        File file = new File("contacts");
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(phoneBook);
            out.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

	private PhoneBook readFromFile(){
        PhoneBook pb = new MapPhoneBook();
        File file = new File("contacts");
        if (file.exists()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                pb = (MapPhoneBook) in.readObject();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return pb;
    }

	@Override
	public void stop(){
		// Here you can place any action to be done when the application is closed, i.e. save phone book to file.
        if (Dialogs.confirmDialog("Confirm save","Save to file","Would you like to save to a file?")) writeToFile();
	}
	
}
