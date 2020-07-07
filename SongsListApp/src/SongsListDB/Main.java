package SongsListDB;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        Datasource data = new Datasource();

        if (!data.open()) {
            System.out.println("Error, database connection lost");
        }
        data.welcome();
        boolean quit = false;
        boolean flag = true;
        while (flag) {
            try {
                while (!quit) {
                    System.out.println("Enter number to choose action (1 to see them all):");
                    int action = scanner.nextInt();
                    scanner.nextLine();
                    switch (action) {
                        case 0:
                            System.out.println("Application has been closed.");
                            quit = true;
                            data.close();
                            flag = false;
                            break;
                        case 1:
                            data.printAction();
                            break;
                        case 2:
                            data.printArtists();
                            break;
                        case 3:
                            data.printSongs();
                            break;
                        case 4:
                            data.printCDs();
                            break;
                        case 5:
                            System.out.println("Enter an artist name to find available songs:");
                            data.searchArtist(scanner.nextLine());
                            break;
                        case 6:
                            System.out.println("Enter a song name to find available songs:");
                            data.searchSong(scanner.nextLine());
                            break;
                        case 7:
                            System.out.println("Enter a CD name to find available songs:");
                            data.searchCD(scanner.nextLine());
                            break;
                        default:
                            System.out.println("Use proper numbers!");
                    }
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Invalid input. Please write proper value.");
            }
        }


    }

}
