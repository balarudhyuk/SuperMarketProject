import java.util.*;

public class Main {

    public static double total = 0.0;


    public static void main(String[] args){

        Map <String,SuperMarket> itemsMap = new HashMap<>();

        SuperMarket item1 = new SuperMarket("a",50,0,0);
        SuperMarket item2 = new SuperMarket("b",30,0,0);
        SuperMarket item3 = new SuperMarket("c",20,0,0);
        SuperMarket item4 = new SuperMarket("d",15,0,0);
        SuperMarket item5 = new SuperMarket("e",10,0,0);

        itemsMap.put(item1.getItemName(),item1);
        itemsMap.put(item2.getItemName(),item2);
        itemsMap.put(item3.getItemName(),item3);
        itemsMap.put(item4.getItemName(),item4);
        itemsMap.put(item5.getItemName(),item5);

        displayChoice(itemsMap);

        System.out.println("Thank you");

    }
    // Method to add new items
    public static void addItems(Map<String , SuperMarket> itemsMap){
        boolean condition = false;
        Scanner input = new Scanner(System.in);
        int noOfItems =0;
        int itemPrice =0;
        String next ="";
        String enterString  ="How many items do you want to add?";
        noOfItems = checkInt(input,noOfItems,enterString);
        for(int i=0;i<noOfItems;i++) {

            while (!condition) {
                System.out.println("Enter item name");
                 next = input.next();
                if (next.matches("[a-zA-Z\\s\'\"]+")) {
                    condition = true;
                } else{
                    System.out.println("Not a valid item name. Enter only alphabets and spaces. - Please try again!");
                }
            }
            condition = false;
            String ask ="Enter item Price: (Pence)";
            itemPrice = checkInt(input,itemPrice,ask);

            SuperMarket item = new SuperMarket(next,itemPrice,0,0);
            itemsMap.put(next,item);

        }

        displayItems(itemsMap);
    }
    //Method to display all the items in the Supermarket
    public static void displayItems(Map<String , SuperMarket> itemsMap) {
        String format = "%1$-10s%2$-10s%3$-20s\n";
        System.out.println("----------------------------------------------------------------------------");
        System.out.format (format,"Item Name", "         Unit Price (Pence)","        Special Price(pence)");
        System.out.println();
        System.out.println("----------------------------------------------------------------------------");
        //iterates over the list

        for (Map.Entry<String,SuperMarket> entry : itemsMap.entrySet()) {
            System.out.print(entry.getValue().getItemName());
            System.out.print("                    " + entry.getValue().getPrice());
            if(entry.getValue().getNoOfItems() > 0)
                System.out.print("                               " + entry.getValue().getNoOfItems() + " for " +entry.getValue().getSpecialPrice());

            System.out.println();



        }

        System.out.println("----------------------------------------------------------------------------");
        displayChoice(itemsMap);

        }
    //Method to add Special Offers for the day
    public static void addSpecialPrice(Map<String , SuperMarket> itemsMap){
        Scanner input = new Scanner(System.in);
        String ask = "How many offers do you want to enter?";
        int offerCount = 0;
        offerCount = checkInt (input,offerCount,ask);
        System.out.println("Enter the Special Offers of the day");
        for(int i=0; i< offerCount ; i++) {
            String item="";
            String enter = "Item Name:";
            item = checkString(input, item, itemsMap,enter);
            String price = "Item price:";
            double sPrice = 0.0;
            sPrice = checkDouble (input,sPrice, price);

            String items = "Enter the number of items the price applies to:";

            int numOfItems =0;
            numOfItems = checkInt(input,numOfItems, items);
            for (Map.Entry<String, SuperMarket> entry : itemsMap.entrySet()) {

                if (item.equalsIgnoreCase(entry.getKey())) {
                    entry.getValue().setSpecialPrice(sPrice);
                    entry.getValue().setNoOfItems(numOfItems);
                }


            }
        }

            displayChoice(itemsMap);
    }
    //Method to Scan items for billing
    public static void scanItems(Map<String , SuperMarket> itemsMap){

        Map<String,Double> scannedItems = new HashMap<>();
        System.out.println("Enter item to be scanned");
        Scanner input = new Scanner(System.in);
        String item = input.nextLine();
        checkItemAvailability( itemsMap,item ,scannedItems);
        System.out.println("Do you have more items to scan? Y/N");
        //input.nextLine();
        String moreScan = input.nextLine();
        if (moreScan.equalsIgnoreCase("y") || moreScan.equalsIgnoreCase("n")) {
            scanAndTotal(itemsMap, moreScan, item,scannedItems);
        }
        else {
            keepScanning(itemsMap, moreScan, item,scannedItems);
        }


    }
    // Method to check if the user input is valid
    public static void keepScanning(Map<String , SuperMarket> itemsMap, String moreScan,String item, Map<String,Double> scannedItems){
        Scanner input = new Scanner(System.in);

            System.out.println("Input is invalid. Press Y to continue scanning or Press N to print receipt and exit");
            System.out.println("Do you have more items to scan? Y/N");
            moreScan = input.nextLine();
            scanAndTotal(itemsMap, moreScan, item,scannedItems);


    }
    //Method to check the input and add more items for billing
    public static void scanAndTotal(Map<String , SuperMarket> itemsMap, String moreScan,String item, Map<String,Double> scannedItems){
        Scanner input = new Scanner(System.in);

        while (moreScan.equalsIgnoreCase("y")) {
            System.out.println("Scan the next item");
            item = input.nextLine();
            checkItemAvailability( itemsMap,item ,scannedItems);
            System.out.println("Do you have more items to scan? Y/N");
            moreScan = input.nextLine();
        }

        if( moreScan.equalsIgnoreCase("n")){
            printReceipt(itemsMap,scannedItems);
        }

        else
            keepScanning(itemsMap, moreScan, item,scannedItems);
    }
    //Method to check the availability of an item before billing it and finding the total
    public static void checkItemAvailability(Map<String , SuperMarket> itemsMap , String item ,
                                             Map<String,Double> scannedItems){

        double itemCount =0;
        boolean available = false;

            for (Map.Entry<String, SuperMarket> entry : itemsMap.entrySet()) {
                if(!available) {
                    if (entry.getKey().equalsIgnoreCase(item)) {
                        available = true;
                        if (scannedItems.containsKey(item)) {
                            double count = scannedItems.get(item);
                            scannedItems.put(item, ++count);
                        } else
                            scannedItems.put(item, ++itemCount);
                        if (entry.getValue().getSpecialPrice() == 0) {
                            total = total + entry.getValue().getPrice();
                            System.out.println("Total amount :"+"\u00a3" + total/100);
                        } else if ((entry.getValue().getNoOfItems() == scannedItems.get(item))) {
                            int count = entry.getValue().getNoOfItems() - 1;
                            total = total - (entry.getValue().getPrice() * count);
                            total = total + entry.getValue().getSpecialPrice();
                            System.out.println("Total amount :"+"\u00a3" + total/100);
                        } else {
                            total = total + entry.getValue().getPrice();
                            System.out.println("Total amount :"+"\u00a3"+ total/100);
                        }
                    }


                    }
                }


        if(!available)
            System.out.println("Please scan available items. Item " + item + " is not available");

    }
    //Method to print the receipt
    public static void printReceipt(Map<String , SuperMarket> itemsMap ,  Map<String,Double> scannedItems){
        System.out.println("--------------------------------------------------------------");
        System.out.printf("%10s %10s %5s%n","ITEM NAME     ","NO OF ITEMS           " ,"PRICE     ");
        System.out.println("---------------------------------------------------------------");
        for (Map.Entry<String,Double> entry : scannedItems.entrySet()) {
                System.out.print(entry.getKey()+"                 ");
                System.out.print(Math.round(entry.getValue())+"                   ");
                if(itemsMap.get(entry.getKey()).getSpecialPrice() ==0)
                    System.out.print("\u00a3"+(itemsMap.get(entry.getKey()).getPrice() * entry.getValue())/100);
                else{
                        int count = itemsMap.get(entry.getKey()).getNoOfItems();
                        if(entry.getValue() == count){
                            System.out.print("\u00a3"+(itemsMap.get(entry.getKey()).getSpecialPrice())/100);
                        }
                        else if(entry.getValue() < count){
                            System.out.print("\u00a3"+(itemsMap.get(entry.getKey()).getPrice() * entry.getValue())/100) ;
                        }
                        else  if(entry.getValue() > count){
                            double diff = entry.getValue()-count;
                            System.out.println("\u00a3"+(itemsMap.get(entry.getKey()).getSpecialPrice() + (diff * itemsMap.get(entry.getKey()).getPrice()))/100);

                        }


                }
            System.out.println("");
        }
        System.out.println("--------------------------------------------------------------");
        System.out.println("TOTAL:" +"\u00a3 "+ total/100);

        System.out.println("--------------------------------------------------------------");




    }
    //Method to display all the available operations
    public static void displayChoice(Map<String , SuperMarket> itemsMap){
        int choice = 0;
        boolean condition = false;

        while (!condition) {

            System.out.println("Welcome");
            System.out.println("Enter your choice");
            System.out.println("1.Display all the items");
            System.out.println("2.Add special offers");
            System.out.println("3.Scan items");
            System.out.println("4.Add new items");
            System.out.println("5.Exit");

            Scanner input = new Scanner(System.in);
            String next = input.next();
            try {
                choice = Integer.parseInt(next);
                condition = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter 1,2,3,4 or 5 : try again!");
            }


        }

        switch(choice){

            case 1: displayItems(itemsMap);
                break;
            case 2:addSpecialPrice(itemsMap);
                break;
            case 3:scanItems(itemsMap)  ;
                break;
            case 4:addItems(itemsMap);
                break;
            case 5: System.out.println("You have exited the system");
                break;
            default: System.out.println("Choice not available");
                break;
        }
    }

    // Method to check if the user input is an integer
    public static int checkInt(Scanner input , int count, String ask){

        boolean condition = false;

        while (!condition) {
            System.out.println(ask);
            String next = input.next();
            try {
                count = Integer.parseInt(next);
                condition = true;
            } catch (NumberFormatException e) {
                System.out.println("This input is not an integer - Please try again!");
            }
        }

            return count;
    }
    // Method to check if the user input is a double
    public static double checkDouble(Scanner input , double count, String ask){

        boolean condition = false;

        while (!condition) {
            System.out.println(ask);
            String next = input.next();
            try {
                count = Double.parseDouble(next);
                condition = true;
            } catch (NumberFormatException e) {
                System.out.println("This input is not an integer - Please try again!");
            }
        }

        return count;
    }


    // Method to check if the user input is a valid item
    public static String checkString(Scanner input, String line, Map<String, SuperMarket> itemsMap, String ask){


        boolean condition = false;

        while (!condition) {
            System.out.println(ask);
            line = input.next();

            if(itemsMap.containsKey( line)){
            condition = true;
            } else {
                System.out.println("This input is not a valid item - Please try again!");
            }
        }

        return line;

    }


}

