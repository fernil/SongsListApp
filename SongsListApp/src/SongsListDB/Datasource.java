package SongsListDB;

import java.sql.*;

public class Datasource {
    public static final String DB_NAME = "SongsList.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\User\\eclipse-workspace1\\" + DB_NAME;
    public static final String TABLE_SINGSTAR = "Singstar";
    public static final String COLUMN_ARTIST = "Artist";
    public static final String COLUMN_SONG = "Song";
    public static final String COLUMN_CDNAME = "CD_Name";
    //public static final String COLUMN_CONSOLE="Console";
    //public static final String COLUMN_LANGUAGE="Language";


    public static final String ARTIST_QUERY = "SELECT " + COLUMN_ARTIST + ", " + COLUMN_SONG + ", " + COLUMN_CDNAME + " FROM " + TABLE_SINGSTAR
            + " WHERE " + COLUMN_ARTIST + " LIKE ? ORDER BY " + COLUMN_SONG;
    public static final String SONG_QUERY = "SELECT " + COLUMN_SONG + ", " + COLUMN_ARTIST + ", " + COLUMN_CDNAME + " FROM " + TABLE_SINGSTAR
            + " WHERE " + COLUMN_SONG + " LIKE ? ORDER BY " + COLUMN_SONG;
    public static final String CD_QUERY = "SELECT " + COLUMN_CDNAME + ", " + COLUMN_ARTIST + ", " + COLUMN_SONG + " FROM " + TABLE_SINGSTAR
            + " WHERE " + COLUMN_CDNAME + " LIKE ? ORDER BY " + COLUMN_CDNAME + ", " + COLUMN_ARTIST;

    private Connection connection;
    private Statement statement;
    private PreparedStatement prepQueryArtist;
    private PreparedStatement prepQuerySong;
    private PreparedStatement prepQueryCD;


    public boolean open() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            prepQueryArtist = connection.prepareStatement(ARTIST_QUERY);
            prepQuerySong = connection.prepareStatement(SONG_QUERY);
            prepQueryCD = connection.prepareStatement(CD_QUERY);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void welcome() throws SQLException {
        statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT COUNT(DISTINCT(" + COLUMN_ARTIST + ")), COUNT(DISTINCT(" + COLUMN_SONG
                + ")) FROM " + TABLE_SINGSTAR);
        System.out.println("Welcome to Karaoke Songs List.");
        while (result.next()) {
            System.out.println("There are " + result.getInt(1) + " artists and " + result.getInt(2) + " songs on the list.");
        }
        printAction();
    }

    public void printAction() {
        System.out.println("Action to choose:");
        System.out.println("0 - Close application");
        System.out.println("1 - Show list of actions");
        System.out.println("2 - Show list of the artists");
        System.out.println("3 - Show list of the songs");
        System.out.println("4 - Show list of the CDs");
        System.out.println("5 - Search by an artist");
        System.out.println("6 - Search by a song");
        System.out.println("7 - Search by a CD name");
    }

    public void printArtists() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT(" + COLUMN_ARTIST + ") FROM " + TABLE_SINGSTAR
                + " ORDER BY " + COLUMN_ARTIST);
        int i = 1;
        while (resultSet.next()) {
            System.out.println(i + ". " + resultSet.getString(1));
            i++;
        }
    }

    public void printSongs() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT(" + COLUMN_SONG + "), " + COLUMN_ARTIST
                + " FROM " + TABLE_SINGSTAR + " ORDER BY " + COLUMN_ARTIST + ", " + COLUMN_SONG);
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " - " + resultSet.getString(2));
        }
    }

    public void printCDs() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT(" + COLUMN_CDNAME + ") FROM " + TABLE_SINGSTAR + " ORDER BY " + COLUMN_CDNAME);
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }

    public void searchArtist(String artistName) throws SQLException {
        prepQueryArtist.setString(1, "%" + artistName + "%");
        ResultSet resultSet = prepQueryArtist.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " - " + resultSet.getString(2) + " on " + resultSet.getString(3));
        }
    }

    public void searchSong(String songName) throws SQLException {
        prepQuerySong.setString(1, "%" + songName + "%");
        ResultSet resultSet = prepQuerySong.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " - " + resultSet.getString(2) + " on " + resultSet.getString(3));
        }
    }

    public void searchCD(String cdName) throws SQLException {
        prepQueryCD.setString(1, "%" + cdName + "%");
        ResultSet resultSet = prepQueryCD.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + ": " + resultSet.getString(2) + " - " + resultSet.getString(3));
        }
    }
}
