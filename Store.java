//I worked on the homework assignment alone, using only course materials.
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The Store class represents a store with an inventory system. It allows for
 * processing a shopping list, updating inventory, and creating a receipt.
 * @author Ayub Hunter
 * @version 1.0
 */
public class Store {
    private String[][] inventory;

    /**
     * Constructs a Store object with inventory data from a given file.
     *
     * @param inventoryFile The file containing inventory data.
     * @throws FileNotFoundException if the inventory file is not found.
     */
    public Store(File inventoryFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(inventoryFile);
        int lineCount = 0;

        while (scanner.hasNextLine()) {
            scanner.nextLine();
            lineCount++;
        }
        scanner.close();

        inventory = new String[lineCount][];

        scanner = new Scanner(inventoryFile);
        int index = 0;
        while (scanner.hasNextLine()) {
            inventory[index++] = scanner.nextLine().split(",");
        }
        scanner.close();
    }

    /**
     * Constructs a Store object with inventory data from a file specified by filename.
     *
     * @param inventoryFileName The name of the file containing inventory data.
     * @throws FileNotFoundException if the inventory file is not found.
     */
    public Store(String inventoryFileName) throws FileNotFoundException {
        this(new File(inventoryFileName));
    }

    /**
     * Processes a shopping list file and writes a receipt to the specified destination.
     *
     * @param shoppingList        The file containing the shopping list.
     * @param receiptDestination  The file path to write the receipt to.
     * @throws InvalidFormatException if the shopping list does not start with the correct header.
     */
    public void goShopping(File shoppingList, String receiptDestination) throws InvalidFormatException {
        int cumulativeTotal = 0;
        try (Scanner scanner = new Scanner(shoppingList);
             PrintWriter receiptWriter = new PrintWriter(receiptDestination)) {

            String header = scanner.hasNextLine() ? scanner.nextLine() : "";
            if (!"$hopping".equals(header)) {
                throw new InvalidFormatException("Invalid shopping list header.");
            }

            while (scanner.hasNextLine()) {
                String shoppingListItem = scanner.nextLine();
                cumulativeTotal += buyItem(shoppingListItem, receiptWriter);
            }

            receiptWriter.println("TOTAL," + cumulativeTotal);
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Attempts to purchase an item based on the shopping list item.
     *
     * @param shoppingListItem The item to purchase.
     * @param receiptWriter    The PrintWriter to write the receipt to.
     * @return The price of the item bought, or -1 if the item could not be bought.
     */
    private int buyItem(String shoppingListItem, PrintWriter receiptWriter) {
        try {
            String[] itemData = parseLine(shoppingListItem);
            String itemName = itemData[0];
            int maxPrice = Integer.parseInt(itemData[1]);

            for (String[] inventoryItem : inventory) {
                if (inventoryItem[0].equals(itemName)) {
                    int stock = Integer.parseInt(inventoryItem[1]);
                    int price = Integer.parseInt(inventoryItem[2]);

                    if (stock <= 0) {
                        throw new OutOfStockException(itemName + " is not in stock!");
                    }

                    if (price <= maxPrice) {
                        inventoryItem[1] = String.valueOf(stock - 1); // Reduce stock
                        receiptWriter.println(itemName + "," + price);
                        return price;
                    }
                    return -1;
                }
            }
        } catch (InvalidDataException e) {
            System.out.println(e.toString());
        }

        return -1;
    }

    /**
     * Parses a line from the shopping list and validates it.
     *
     * @param line The shopping list line to parse.
     * @return An array containing the item name and max price.
     * @throws InvalidDataException if the data is not valid.
     */
    private String[] parseLine(String line) throws InvalidDataException {
        String[] parts = line.split(",");
        if (parts.length != 2 || parts[0].isEmpty()) {
            throw new InvalidDataException("Item has no name");
        }

        int maxPrice;
        try {
            maxPrice = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new InvalidDataException(parts[1] + " cannot be parsed as an integer price");
        }

        if (maxPrice < 0) {
            throw new InvalidDataException("Max price cannot be negative");
        }

        return new String[]{parts[0], parts[1]};
    }
}

