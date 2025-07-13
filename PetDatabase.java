import java.util.ArrayList;
import java.util.Scanner;

public class PetDatabase {
    private ArrayList<Pet> pets;
    private Scanner scanner;
    
    public PetDatabase() {
        pets = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    
    public void run() {
        System.out.println("Pet Database Program.");
        
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (choice) {
                case 1:
                    viewAllPets();
                    break;
                case 2:
                    addPets();
                    break;
                case 3:
                    updatePet();
                    break;
                case 4:
                    removePet();
                    break;
                case 5:
                    searchByName();
                    break;
                case 6:
                    searchByAge();
                    break;
                case 7:
                    System.out.println("Goodbye...");
                    return;
                default:
                    System.out.println("Invalid choice by you. Please try again with new chpice.");
            }
        }
    }
    
    private void showMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1) View all pets");
        System.out.println("2) Add more pets");
        System.out.println("3) Update an existing pet");
        System.out.println("4) Remove an existing pet");
        System.out.println("5) Search pets by name");
        System.out.println("6) Search pets by age");
        System.out.println("7) Exit program");
        System.out.print("ENTER Your choice -: ");
    }
    
    private void addPets() {
        int count = 0;
        System.out.println();
        
        while (true) {
            System.out.print("add pet (name, age): ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("done")) {
                break;
            }
            
            String[] parts = input.split("\\s+");
            if (parts.length == 2) {
                try {
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    pets.add(new Pet(name, age));
                    count++;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age format. Please try again.");
                }
            } else {
                System.out.println("Invalid format. Please enter: name age");
            }
        }
        
        System.out.println(count + " pets added.");
    }
    
    private void viewAllPets() {
        printTable(pets);
    }
    
    private void updatePet() {
        if (pets.isEmpty()) {
            System.out.println("No pets in database.");
            return;
        }
        
        printTable(pets);
        System.out.print("Enter the pet ID you want to update: ");
        
        try {
            int id = scanner.nextInt();
            scanner.nextLine(); 
            
            if (id >= 0 && id < pets.size()) {
                Pet oldPet = pets.get(id);
                String oldInfo = oldPet.toString();
                
                System.out.print("Enter new name and new age: ");
                String input = scanner.nextLine().trim();
                String[] parts = input.split("\\s+");
                
                if (parts.length == 2) {
                    try {
                        String newName = parts[0];
                        int newAge = Integer.parseInt(parts[1]);
                        
                        oldPet.setName(newName);
                        oldPet.setAge(newAge);
                        
                        System.out.println(oldInfo + " changed to " + oldPet.toString() + ".");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid age format.");
                    }
                } else {
                    System.out.println("Invalid format. Please enter: name age");
                }
            } else {
                System.out.println("Invalid pet ID.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
        }
    }
    
    private void removePet() {
        if (pets.isEmpty()) {
            System.out.println("No pets in database.");
            return;
        }
        
        printTable(pets);
        System.out.print("Enter the pet ID to remove: ");
        
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            
            if (id >= 0 && id < pets.size()) {
                Pet removedPet = pets.remove(id);
                System.out.println(removedPet.toString() + " is removed.");
            } else {
                System.out.println("Invalid pet ID.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input.");
            scanner.nextLine(); 
        }
    }
    
    private void searchByName() {
        System.out.print("Enter a name to search: ");
        String searchName = scanner.nextLine().trim();
        
        ArrayList<Pet> results = new ArrayList<>();
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getName().equalsIgnoreCase(searchName)) {
                results.add(pets.get(i));
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("No pets found with name: " + searchName);
        } else {
            printSearchResults(results, searchName);
        }
    }
    
    private void searchByAge() {
        System.out.print("Enter age to search: ");
        
        try {
            int searchAge = scanner.nextInt();
            scanner.nextLine(); 
            
            ArrayList<Pet> results = new ArrayList<>();
            for (int i = 0; i < pets.size(); i++) {
                if (pets.get(i).getAge() == searchAge) {
                    results.add(pets.get(i));
                }
            }
            
            if (results.isEmpty()) {
                System.out.println("No pets found with age: " + searchAge);
            } else {
                printSearchResults(results, String.valueOf(searchAge));
            }
        } catch (Exception e) {
            System.out.println("Invalid age format.");
            scanner.nextLine(); 
        }
    }
    
    private void printTable(ArrayList<Pet> petList) {
        System.out.println("+----------------------+");
        System.out.printf("| %2s | %-10s | %3s |\n", "ID", "NAME", "AGE");
        System.out.println("+----------------------+");
        
        for (int i = 0; i < petList.size(); i++) {
            Pet pet = petList.get(i);
            System.out.printf("| %2d | %-10s | %3d |\n", i, pet.getName(), pet.getAge());
        }
        
        System.out.println("+----------------------+");
        System.out.println(petList.size() + " rows in set.");
    }
    
    private void printSearchResults(ArrayList<Pet> results, String searchTerm) {
        System.out.println("+----------------------+");
        System.out.printf("| %2s | %-10s | %3s |\n", "ID", "NAME", "AGE");
        System.out.println("+----------------------+");
        
        // Find original indices for display
        for (Pet result : results) {
            for (int i = 0; i < pets.size(); i++) {
                if (pets.get(i) == result) {
                    System.out.printf("| %2d | %-10s | %3d |\n", i, result.getName(), result.getAge());
                    break;
                }
            }
        }
        
        System.out.println("+----------------------+");
        System.out.println(results.size() + " rows in set.");
    }
    
    public static void main(String[] args) {
        PetDatabase database = new PetDatabase();
        database.run();
    }
}