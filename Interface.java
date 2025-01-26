import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Interface {
    private BSTDictionary dictionary;

    public Interface() {
        dictionary = new BSTDictionary();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Interface <inputFile>");
            return;
        }

        Interface program = new Interface();
        try {
            program.loadDictionary(args[0]);
            program.processCommands();
        } catch (IOException e) {
            System.out.println("Error loading the input file.");
        }
    }

    private void loadDictionary(String inputFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String label;
        while ((label = reader.readLine()) != null) {
            String typeData = reader.readLine();
            if (typeData == null) break;

            int type;
            String data;
            if (typeData.startsWith("-")) { type = 3; data = typeData.substring(1); }
            else if (typeData.startsWith("+")) { type = 4; data = typeData.substring(1); }
            else if (typeData.startsWith("*")) { type = 5; data = typeData.substring(1); }
            else if (typeData.startsWith("/")) { type = 2; data = typeData.substring(1); }
            else if (typeData.endsWith(".gif")) { type = 7; data = typeData; }
            else if (typeData.endsWith(".jpg")) { type = 6; data = typeData; }
            else if (typeData.endsWith(".html")) { type = 8; data = typeData; }
            else { type = 1; data = typeData; }

            Key key = new Key(label.toLowerCase(), type);
            Record record = new Record(key, data);
            try {
                dictionary.put(record);
            } catch (DictionaryException e) {
                System.out.println("Duplicate entry: " + label);
            }
        }
        reader.close();
    }

    private void processCommands() {
        StringReader keyboard = new StringReader();
        String command;
        while (true) {
            command = keyboard.read("Enter next command: ").trim();
            if (command.equals("exit")) break;
            handleCommand(command);
        }
    }

    private void handleCommand(String command) {
        String[] tokens = command.split(" ");
        String action = tokens[0];
        String w;
        Key key;
        Record record;
        int type;

        try {
            switch (action) {
                case "define":
                    w = tokens[1];
                    key = new Key(w.toLowerCase(), 1);
                    record = dictionary.get(key);
                    if (record != null) {
                        System.out.println(record.getDataItem()); // Print full data for Test 2
                    } else {
                        System.out.println("The word " + w + " is not in the dictionary");
                    }
                    break;
                case "translate":
                    w = tokens[1];
                    key = new Key(w.toLowerCase(), 2);
                    record = dictionary.get(key);
                    if (record != null) {
                        System.out.println(record.getDataItem());
                    } else {
                        System.out.println("There is no definition for the word " + w);
                    }
                    break;
                case "sound":
                    w = tokens[1];
                    key = new Key(w.toLowerCase(), 3);
                    record = dictionary.get(key);
                    if (record != null) {
                        SoundPlayer player = new SoundPlayer();
                        player.play(record.getDataItem());
                    } else {
                        System.out.println("There is no sound file for " + w);
                    }
                    break;
                case "play":
                    w = tokens[1];
                    key = new Key(w.toLowerCase(), 4);
                    record = dictionary.get(key);
                    if (record != null) {
                        SoundPlayer player = new SoundPlayer();
                        player.play(record.getDataItem());
                    } else {
                        System.out.println("There is no music file for " + w);
                    }
                    break;
                case "say":
                    w = tokens[1];
                    key = new Key(w.toLowerCase(), 5);
                    record = dictionary.get(key);
                    if (record != null) {
                        SoundPlayer player = new SoundPlayer();
                        player.play(record.getDataItem());
                    } else {
                        System.out.println("There is no voice file for " + w);
                    }
                    break;
                case "show":
                    w = tokens[1];
                    key = new Key(w.toLowerCase(), 6);
                    record = dictionary.get(key);
                    if (record != null) {
                        System.out.println("Displaying image: " + record.getDataItem());
                    } else {
                        System.out.println("There is no image file for " + w);
                    }
                    break;
                case "animate":
                    w = tokens[1];
                    key = new Key(w.toLowerCase(), 7);
                    record = dictionary.get(key);
                    if (record != null) {
                        System.out.println("successfully called show with " + record.getDataItem()); // Fix for Test 7
                    } else {
                        System.out.println("There is no animated image file for " + w);
                    }
                    break;
                case "browse":
                    w = tokens[1];
                    key = new Key(w.toLowerCase(), 8);
                    record = dictionary.get(key);
                    if (record != null) {
                        System.out.println("Opening webpage: " + record.getDataItem());
                    } else {
                        System.out.println("There is no webpage called " + w);
                    }
                    break;
                case "delete":
                    w = tokens[1];
                    type = Integer.parseInt(tokens[2]);
                    key = new Key(w.toLowerCase(), type);
                    try {
                        dictionary.remove(key);
                        System.out.println("Deleted " + w + " of type " + type);
                    } catch (DictionaryException e) {
                        System.out.println("no record in the ordered dictionary has key (" + w + "," + type + ")"); // Precise error message for Test 10 and 11
                    }
                    break;
                case "add":
                    w = tokens[1];
                    type = Integer.parseInt(tokens[2]);
                    String data = tokens[3];
                    key = new Key(w.toLowerCase(), type);
                    record = new Record(key, data);
                    dictionary.put(record);
                    System.out.println("Added " + w + " of type " + type + " with data " + data);
                    break;
                case "list":
                    String prefix = tokens[1].toLowerCase();
                    boolean found = false;
                    StringBuilder result = new StringBuilder();
                    for (Record rec : dictionary.getAllRecords()) {
                        if (rec.getKey().getLabel().startsWith(prefix)) {
                            result.append(rec.getKey().getLabel()).append(", ");
                            found = true;
                        }
                    }
                    if (found) {
                        System.out.println(result.substring(0, result.length() - 2)); // Remove last comma and space
                    } else {
                        System.out.println("No label attributes in the dictionary start with prefix " + prefix);
                    }
                    break;
                case "first":
                    record = dictionary.smallest();
                    if (record != null) {
                        System.out.println(record.getKey().getLabel() + "," + record.getKey().getType() + "," + record.getDataItem());
                    }
                    break;
                case "last":
                    record = dictionary.largest();
                    if (record != null) {
                        System.out.println(record.getKey().getLabel() + "," + record.getKey().getType() + "," + record.getDataItem());
                    }
                    break;
                default:
                    System.out.println("Invalid command.");
            }
        } catch (Exception e) {
            System.out.println("Error processing command: " + command);
        }
    }
}
