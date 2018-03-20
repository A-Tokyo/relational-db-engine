package jarvis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

public class Tests {
	/**
	 * A1 Tests by MET
	 * 
	 * @param myDB
	 * @throws Exception
	 */
	public static void M1Tests(DBApp myDB) throws Exception {
		// creating table "Faculty"

		Hashtable<String, String> fTblColNameType = new Hashtable<String, String>();
		fTblColNameType.put("ID", "Integer");
		fTblColNameType.put("Name", "String");

		Hashtable<String, String> fTblColNameRefs = new Hashtable<String, String>();

		myDB.createTable("Faculty", fTblColNameType, fTblColNameRefs, "ID");

		// creating table "Major"

		Hashtable<String, String> mTblColNameType = new Hashtable<String, String>(); // bug
																						// cost
																						// me
																						// 3.5
																						// hours
		mTblColNameType.put("ID", "Integer");
		mTblColNameType.put("Name", "String");
		mTblColNameType.put("Faculty_ID", "Integer");

		Hashtable<String, String> mTblColNameRefs = new Hashtable<String, String>();
		mTblColNameRefs.put("Faculty_ID", "Faculty.ID");

		myDB.createTable("Major", mTblColNameType, mTblColNameRefs, "ID");

		// creating table "Course"

		Hashtable<String, String> coTblColNameType = new Hashtable<String, String>();
		coTblColNameType.put("ID", "Integer");
		coTblColNameType.put("Name", "String");
		coTblColNameType.put("Code", "String");
		coTblColNameType.put("Hours", "Integer");
		coTblColNameType.put("Semester", "Integer");
		coTblColNameType.put("Major_ID", "Integer");

		Hashtable<String, String> coTblColNameRefs = new Hashtable<String, String>();
		coTblColNameRefs.put("Major_ID", "Major.ID");

		myDB.createTable("Course", coTblColNameType, coTblColNameRefs, "ID");

		// creating table "Student"

		Hashtable<String, String> stTblColNameType = new Hashtable<String, String>();
		stTblColNameType.put("ID", "Integer");
		stTblColNameType.put("First_Name", "String");
		stTblColNameType.put("Last_Name", "String");
		stTblColNameType.put("GPA", "Double");
		stTblColNameType.put("Age", "Integer");

		Hashtable<String, String> stTblColNameRefs = new Hashtable<String, String>();

		myDB.createTable("Student", stTblColNameType, stTblColNameRefs, "ID");

		// creating table "Student in Course"

		Hashtable<String, String> scTblColNameType = new Hashtable<String, String>();
		scTblColNameType.put("ID", "Integer");
		scTblColNameType.put("Student_ID", "Integer");
		scTblColNameType.put("Course_ID", "Integer");

		Hashtable<String, String> scTblColNameRefs = new Hashtable<String, String>();
		scTblColNameRefs.put("Student_ID", "Student.ID");
		scTblColNameRefs.put("Course_ID", "Course.ID");

		myDB.createTable("Student_in_Course", scTblColNameType, scTblColNameRefs, "ID");

		// insert in table "Faculty"

		Hashtable<String, Object> ftblColNameValue1 = new Hashtable<String, Object>();
		ftblColNameValue1.put("ID", Integer.valueOf("1"));
		ftblColNameValue1.put("Name", "Media Engineering and Technology");
		myDB.insertIntoTable("Faculty", ftblColNameValue1);

		Hashtable<String, Object> ftblColNameValue2 = new Hashtable<String, Object>();
		ftblColNameValue2.put("ID", Integer.valueOf("2"));
		ftblColNameValue2.put("Name", "Management Technology");
		myDB.insertIntoTable("Faculty", ftblColNameValue2);

		for (int i = 1; i < 1000 - 1; i++) {
			Hashtable<String, Object> ftblColNameValueI = new Hashtable<String, Object>();
			ftblColNameValueI.put("ID", Integer.valueOf(("" + (i + 2))));
			ftblColNameValueI.put("Name", "f" + (i + 2));
			myDB.insertIntoTable("Faculty", ftblColNameValueI);
		}

		// insert in table "Major"

		Hashtable<String, Object> mtblColNameValue1 = new Hashtable<String, Object>();
		mtblColNameValue1.put("ID", Integer.valueOf("1"));
		mtblColNameValue1.put("Name", "Computer Science & Engineering");
		mtblColNameValue1.put("Faculty_ID", Integer.valueOf("1"));
		myDB.insertIntoTable("Major", mtblColNameValue1);

		Hashtable<String, Object> mtblColNameValue2 = new Hashtable<String, Object>();
		mtblColNameValue2.put("ID", Integer.valueOf("2"));
		mtblColNameValue2.put("Name", "Business Informatics");
		mtblColNameValue2.put("Faculty_ID", Integer.valueOf("2"));
		myDB.insertIntoTable("Major", mtblColNameValue2);

		for (int i = 0; i < 1000; i++) {
			Hashtable<String, Object> mtblColNameValueI = new Hashtable<String, Object>();
			mtblColNameValueI.put("ID", Integer.valueOf(("" + (i + 2))));
			mtblColNameValueI.put("Name", "m" + (i + 2));
			mtblColNameValueI.put("Faculty_ID", Integer.valueOf(("" + (i + 2))));
			myDB.insertIntoTable("Major", mtblColNameValueI);
		}

		// insert in table "Course"

		Hashtable<String, Object> ctblColNameValue1 = new Hashtable<String, Object>();
		ctblColNameValue1.put("ID", Integer.valueOf("1"));
		ctblColNameValue1.put("Name", "Data Bases II");
		ctblColNameValue1.put("Code", "CSEN 604");
		ctblColNameValue1.put("Hours", Integer.valueOf("4"));
		ctblColNameValue1.put("Semester", Integer.valueOf("6"));
		ctblColNameValue1.put("Major_ID", Integer.valueOf("1"));
		myDB.insertIntoTable("Course", ctblColNameValue1);

		Hashtable<String, Object> ctblColNameValue2 = new Hashtable<String, Object>();
		ctblColNameValue2.put("ID", Integer.valueOf("1"));
		ctblColNameValue2.put("Name", "Data Bases II");
		ctblColNameValue2.put("Code", "CSEN 604");
		ctblColNameValue2.put("Hours", Integer.valueOf("4"));
		ctblColNameValue2.put("Semester", Integer.valueOf("6"));
		ctblColNameValue2.put("Major_ID", Integer.valueOf("2"));
		myDB.insertIntoTable("Course", ctblColNameValue2);

		for (int i = 0; i < 1000; i++) {
			Hashtable<String, Object> ctblColNameValueI = new Hashtable<String, Object>();
			ctblColNameValueI.put("ID", Integer.valueOf(("" + (i + 2))));
			ctblColNameValueI.put("Name", "c" + (i + 2));
			ctblColNameValueI.put("Code", "co " + (i + 2));
			ctblColNameValueI.put("Hours", Integer.valueOf("4"));
			ctblColNameValueI.put("Semester", Integer.valueOf("6"));
			ctblColNameValueI.put("Major_ID", Integer.valueOf(("" + (i + 2))));
			myDB.insertIntoTable("Course", ctblColNameValueI);
		}

		// insert in table "Student"

		for (int i = 0; i < 1000; i++) {
			Hashtable<String, Object> sttblColNameValueI = new Hashtable<String, Object>();
			sttblColNameValueI.put("ID", Integer.valueOf(("" + i)));
			sttblColNameValueI.put("First_Name", "FN" + i);
			sttblColNameValueI.put("Last_Name", "LN" + i);
			sttblColNameValueI.put("GPA", Double.valueOf("0.7"));
			sttblColNameValueI.put("Age", Integer.valueOf("20"));
			myDB.insertIntoTable("Student", sttblColNameValueI);
			// changed it to student instead of course
		}

		// selecting

		Hashtable<String, Object> stblColNameValue = new Hashtable<String, Object>();
		stblColNameValue.put("ID", Integer.valueOf("550"));
		stblColNameValue.put("Age", Integer.valueOf("20"));

		long startTime = System.currentTimeMillis();
		Iterator myIt = myDB.selectFromTable("Student", stblColNameValue, "AND");
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
		while (myIt.hasNext()) {
			System.out.println(myIt.next());
		}

		// feel free to add more tests
		Hashtable<String, Object> stblColNameValue3 = new Hashtable<String, Object>();
		stblColNameValue3.put("Name", "m7");
		stblColNameValue3.put("Faculty_ID", Integer.valueOf("7"));

		long startTime2 = System.currentTimeMillis();
		Iterator myIt2 = myDB.selectFromTable("Major", stblColNameValue3, "AND");
		long endTime2 = System.currentTimeMillis();
		long totalTime2 = endTime - startTime;
		System.out.println(totalTime2);
		while (myIt2.hasNext()) {
			System.out.println(myIt2.next());
		}

		Hashtable<String, Object> stblColNameValue4 = new Hashtable<String, Object>();
		stblColNameValue4.put("Name", "m7");
		stblColNameValue4.put("Faculty_ID", Integer.valueOf("8"));

		long startTime4 = System.currentTimeMillis();
		Iterator myIt4 = myDB.selectFromTable("Major", stblColNameValue4, "OR");
		long endTime4 = System.currentTimeMillis();
		long totalTime4 = endTime - startTime;
		System.out.println(totalTime2);
		while (myIt4.hasNext()) {
			System.out.println(myIt4.next());
		}
	}

	/**
	 * A worst case scenario test for M2
	 * 
	 * @param myDB
	 * @throws Exception
	 */
	public static void M2Tests(DBApp myDB) throws Exception {
		// checking for the worst case scenario
		/*
		 * updating the primary key of something (which updates the index) and
		 * then selecting it with old key to find nothing and then updating the
		 * key2 again to a new key which again updates again the index when
		 * selecting with the new key object is retrieved successfully then I
		 * delete it then Select it again not to find it then Viewing all the
		 * table by the select method with an empty hashtable as a parameter
		 * tests completed successfully
		 */
		Hashtable<String, Object> stblColNameValue4 = new Hashtable<String, Object>();
		stblColNameValue4.put("ID", Integer.valueOf(1243)); // update
		myDB.updateTable("Faculty", Integer.valueOf(1), stblColNameValue4);

		stblColNameValue4 = new Hashtable<String, Object>();
		stblColNameValue4.put("ID", Integer.valueOf(1)); // select with old key,
															// none found
		Iterator v = myDB.selectFromTable("Faculty", stblColNameValue4, "AND");
		while (v.hasNext())
			System.out.println(v.next());

		stblColNameValue4 = new Hashtable<String, Object>();
		stblColNameValue4.put("ID", Integer.valueOf(1234)); // updating key2 to
															// be to new key
		myDB.updateTable("Faculty", Integer.valueOf(1243), stblColNameValue4);

		stblColNameValue4 = new Hashtable<String, Object>();
		stblColNameValue4.put("ID", Integer.valueOf(1234)); // selecting new key
															// to actually find
		Iterator myIt4 = myDB.selectFromTable("Faculty", stblColNameValue4, "OR");
		while (myIt4.hasNext()) {
			System.out.println(myIt4.next());
		}

		stblColNameValue4 = new Hashtable<String, Object>(); // not finding
																// first key
		stblColNameValue4.put("ID", Integer.valueOf(1));
		myIt4 = myDB.selectFromTable("Faculty", stblColNameValue4, "OR");
		while (myIt4.hasNext()) {
			System.out.println(myIt4.next());
		}

		// entry
		stblColNameValue4 = new Hashtable<String, Object>();
		stblColNameValue4.put("ID", Integer.valueOf(1234));
		myDB.deleteFromTable("Faculty", stblColNameValue4, "OR");

		System.out.println("selecting all table this will take time");
		stblColNameValue4 = new Hashtable<String, Object>(); // not finding
		myIt4 = myDB.selectFromTable("Faculty", stblColNameValue4, "OR");
		while (myIt4.hasNext()) {
			System.out.println(myIt4.next());
		}
	}

	/**
	 * A Full test form the DBApp till now using a preDefined Schema
	 * 
	 * @param myDB
	 * @throws DBAppException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws DBEngineException
	 */
	public static void M2SchemaTest(DBApp myDB)
			throws DBAppException, IOException, ClassNotFoundException, DBEngineException {
		creationCMDs(myDB);
		System.out.println("Table Creation Complete!!!!");
		insertionCMDs(myDB);
		System.out.println("Table Insertion Complete!!!!");

		System.out.println("Table before manupilation!!!!");
		Iterator thePrinter1 = myDB.selectFromTable("Gamers", new Hashtable<String, Object>(), "and");
		while (thePrinter1.hasNext()) {
			System.out.println(thePrinter1.next().toString());
		}
		System.out.println("##############################################################################");

		manupilationCMDs(myDB);
		System.out.println("Table Manupilation Works!!!!");
		System.out.println("##############################################################################");
		// System.out.println("Table after manupilation!!!!");
		Iterator thePrinter2 = myDB.selectFromTable("Gamers", new Hashtable<String, Object>(), "and");
		while (thePrinter2.hasNext()) {
			System.out.println(thePrinter2.next().toString());
		}
		System.out.println("##############################################################################");

	}

	public static void creationCMDs(DBApp myDB) throws DBAppException, IOException {
		// Creating Game Genre
		Hashtable<String, String> genreType = new Hashtable<String, String>();
		Hashtable<String, String> emptyHashtable = new Hashtable<String, String>();
		genreType.put("GameGenre", "String");
		genreType.put("NumberOfGames", "Integer");
		myDB.createTable("GameGenre", genreType, emptyHashtable, "GameGenre");

		// Creating Gamers
		Hashtable<String, String> gamerType = new Hashtable<String, String>();
		gamerType.put("ID", "Integer");
		gamerType.put("FirstName", "String");
		gamerType.put("LastName", "String");
		gamerType.put("Country", "String");
		gamerType.put("BirthDate", "Date");
		gamerType.put("GameGenre_GameGenre", "String");
		Hashtable<String, String> gamerRefs = new Hashtable<String, String>();
		gamerRefs.put("GameGenre_GameGenre", "GameGenre.GameGenre");
		myDB.createTable("Gamers", gamerType, gamerRefs, "ID");

		// Creating Games
		Hashtable<String, String> gameType = new Hashtable<String, String>();
		gameType.put("Name", "String");
		gameType.put("SerialNo", "Integer");
		gameType.put("Genre_GameGenre", "String");
		Hashtable<String, String> gameRefs = new Hashtable<String, String>();
		gameRefs.put("Genre_GameGenre", "GameGenre.GameGenre");
		myDB.createTable("Games", gameType, gameRefs, "SerialNo");

		// Creating Characters
		Hashtable<String, String> characterType = new Hashtable<String, String>();
		characterType.put("CharacterName", "String");
		characterType.put("Game_GameName", "String");
		characterType.put("Ability", "String");
		Hashtable<String, String> characterRefs = new Hashtable<String, String>();
		characterRefs.put("Game_GameName", "Game.Name");
		myDB.createTable("Characters", characterType, characterRefs, "CharacterName");

		// Creating Consoles
		Hashtable<String, String> consoleType = new Hashtable<String, String>();
		consoleType.put("ConsoleName", "String");
		consoleType.put("ManufacturerName", "String");
		consoleType.put("ReleaseDate", "Date");
		consoleType.put("Released", "Boolean");
		myDB.createTable("Consoles", consoleType, emptyHashtable, "ConsoleName");

	}

	public static void insertionCMDs(DBApp myDB)
			throws FileNotFoundException, ClassNotFoundException, DBAppException, IOException {
		// Inserting into Game Genre
		Hashtable<String, Object> genreVal1 = new Hashtable<String, Object>();
		genreVal1.put("GameGenre", "Action");
		genreVal1.put("NumberOfGames", Integer.valueOf(3));
		myDB.insertIntoTable("GameGenre", genreVal1);

		Hashtable<String, Object> genreVal2 = new Hashtable<String, Object>();
		genreVal2.put("GameGenre", "Adevnture");
		genreVal2.put("NumberOfGames", Integer.valueOf(5));
		myDB.insertIntoTable("GameGenre", genreVal2);

		Hashtable<String, Object> genreVal3 = new Hashtable<String, Object>();
		genreVal3.put("GameGenre", "Sports");
		genreVal3.put("NumberOfGames", Integer.valueOf(2));
		myDB.insertIntoTable("GameGenre", genreVal3);

		Hashtable<String, Object> genreVal4 = new Hashtable<String, Object>();
		genreVal4.put("GameGenre", "RPG");
		genreVal4.put("NumberOfGames", Integer.valueOf(3));
		myDB.insertIntoTable("GameGenre", genreVal4);

		// Insertion into Gamers

		Hashtable<String, Object> gamerVal1 = new Hashtable<String, Object>();
		Date D1 = new Date(1997, 10, 10);
		gamerVal1.put("ID", Integer.valueOf(1));
		gamerVal1.put("FirstName", "Abdallah");
		gamerVal1.put("LastName", "Sameh");
		gamerVal1.put("Country", "Bahrian");
		gamerVal1.put("BirthDate", D1);
		gamerVal1.put("GameGenre_GameGenre", "Action");
		myDB.insertIntoTable("Gamers", gamerVal1);

		Hashtable<String, Object> gamerVal2 = new Hashtable<String, Object>();
		Date D2 = new Date(1996, 2, 4);
		gamerVal2.put("ID", Integer.valueOf(2));
		gamerVal2.put("FirstName", "Wafa");
		gamerVal2.put("LastName", "Sorour");
		gamerVal2.put("Country", "Oman");
		gamerVal2.put("BirthDate", D2);
		gamerVal2.put("GameGenre_GameGenre", "Adventure");
		myDB.insertIntoTable("Gamers", gamerVal2);

		Hashtable<String, Object> gamerVal3 = new Hashtable<String, Object>();
		Date D3 = new Date(2000, 5, 11);
		gamerVal3.put("ID", Integer.valueOf(3));
		gamerVal3.put("FirstName", "Jack");
		gamerVal3.put("LastName", "Bright");
		gamerVal3.put("Country", "USA");
		gamerVal3.put("BirthDate", D3);
		gamerVal3.put("GameGenre_GameGenre", "Sports");
		myDB.insertIntoTable("Gamers", gamerVal3);

		Hashtable<String, Object> gamerVal4 = new Hashtable<String, Object>();
		Date D4 = new Date(2003, 2, 6);
		gamerVal4.put("ID", Integer.valueOf(4));
		gamerVal4.put("FirstName", "Hugo");
		gamerVal4.put("LastName", "Flannigan");
		gamerVal4.put("Country", "Spain");
		gamerVal4.put("BirthDate", D4);
		gamerVal4.put("GameGenre_GameGenre", "RPG");
		myDB.insertIntoTable("Gamers", gamerVal4);

		Hashtable<String, Object> gamerVal5 = new Hashtable<String, Object>();
		Date D5 = new Date(1988, 3, 7);
		gamerVal5.put("ID", Integer.valueOf(5));
		gamerVal5.put("FirstName", "Utata");
		gamerVal5.put("LastName", "Hikaru");
		gamerVal5.put("Country", "Japan");
		gamerVal5.put("BirthDate", D5);
		gamerVal5.put("GameGenre_GameGenre", "RPG");
		myDB.insertIntoTable("Gamers", gamerVal5);

		Hashtable<String, Object> gamerVal6 = new Hashtable<String, Object>();
		Date D6 = new Date(1994, 8, 9);
		gamerVal6.put("ID", Integer.valueOf(6));
		gamerVal6.put("FirstName", "Tarek");
		gamerVal6.put("LastName", "AbdelRahman");
		gamerVal6.put("Country", "Egypt");
		gamerVal6.put("BirthDate", D6);
		gamerVal6.put("GameGenre_GameGenre", "Sports");
		myDB.insertIntoTable("Gamers", gamerVal6);

		Hashtable<String, Object> gamerVal7 = new Hashtable<String, Object>();
		Date D7 = new Date(1994, 5, 3);
		gamerVal7.put("ID", Integer.valueOf(7));
		gamerVal7.put("FirstName", "Nourhan");
		gamerVal7.put("LastName", "Rady");
		gamerVal7.put("Country", "Egypt");
		gamerVal7.put("BirthDate", D7);
		gamerVal7.put("GameGenre_GameGenre", "Adventure");
		myDB.insertIntoTable("Gamers", gamerVal7);

		Hashtable<String, Object> gamerVal8 = new Hashtable<String, Object>();
		Date D8 = new Date(1994, 5, 3);
		gamerVal8.put("ID", Integer.valueOf(8));
		gamerVal8.put("FirstName", "Youssef");
		gamerVal8.put("LastName", "Sebaee");
		gamerVal8.put("Country", "KSA");
		gamerVal8.put("BirthDate", D8);
		gamerVal8.put("GameGenre_GameGenre", "RPG");
		myDB.insertIntoTable("Gamers", gamerVal8);

		Hashtable<String, Object> gamerVal9 = new Hashtable<String, Object>();
		Date D9 = new Date(1995, 2, 25);
		gamerVal9.put("ID", Integer.valueOf(9));
		gamerVal9.put("FirstName", "Bassel");
		gamerVal9.put("LastName", "Saeed");
		gamerVal9.put("Country", "Egypt");
		gamerVal9.put("BirthDate", D9);
		gamerVal9.put("GameGenre_GameGenre", "Adventure");
		myDB.insertIntoTable("Gamers", gamerVal9);

		Hashtable<String, Object> gamerVal10 = new Hashtable<String, Object>();
		Date D10 = new Date(1998, 8, 8);
		gamerVal10.put("ID", Integer.valueOf(10));
		gamerVal10.put("FirstName", "Mahmoud");
		gamerVal10.put("LastName", "Bakr");
		gamerVal10.put("Country", "UAE");
		gamerVal10.put("BirthDate", D10);
		gamerVal10.put("GameGenre_GameGenre", "Sports");
		myDB.insertIntoTable("Gamers", gamerVal10);

		// Insertions into Consoles

		Hashtable<String, Object> consoleVal1 = new Hashtable<String, Object>();
		Date D11 = new Date(2014, 9, 12);
		consoleVal1.put("ConsoleName", "PS4");
		consoleVal1.put("ManufacturerName", "Sony");
		consoleVal1.put("ReleaseDate", D11);
		consoleVal1.put("Released", true);
		myDB.insertIntoTable("Consoles", consoleVal1);

		Hashtable<String, Object> consoleVal2 = new Hashtable<String, Object>();
		Date D12 = new Date(2009, 8, 6);
		consoleVal2.put("ConsoleName", "PS3");
		consoleVal2.put("ManufacturerName", "Sony");
		consoleVal2.put("ReleaseDate", D12);
		consoleVal2.put("Released", true);
		myDB.insertIntoTable("Consoles", consoleVal2);

		Hashtable<String, Object> consoleVal3 = new Hashtable<String, Object>();
		Date D13 = new Date(2005, 2, 5);
		consoleVal3.put("ConsoleName", "PS2");
		consoleVal3.put("ManufacturerName", "Sony");
		consoleVal3.put("ReleaseDate", D13);
		consoleVal3.put("Released", true);
		myDB.insertIntoTable("Consoles", consoleVal3);

		Hashtable<String, Object> consoleVal4 = new Hashtable<String, Object>();
		Date D14 = new Date(2000, 8, 6);
		consoleVal4.put("ConsoleName", "PS1");
		consoleVal4.put("ManufacturerName", "Sony");
		consoleVal4.put("ReleaseDate", D14);
		consoleVal4.put("Released", true);
		myDB.insertIntoTable("Consoles", consoleVal4);

		Hashtable<String, Object> consoleVal5 = new Hashtable<String, Object>();
		Date D15 = new Date(2000, 7, 6);
		consoleVal5.put("ConsoleName", "XBox");
		consoleVal5.put("ManufacturerName", "Microsoft");
		consoleVal5.put("ReleaseDate", D15);
		consoleVal5.put("Released", true);
		myDB.insertIntoTable("Consoles", consoleVal5);

		Hashtable<String, Object> consoleVal6 = new Hashtable<String, Object>();
		Date D16 = new Date(2010, 7, 8);
		consoleVal6.put("ConsoleName", "X360");
		consoleVal6.put("ManufacturerName", "Microsoft");
		consoleVal6.put("ReleaseDate", D16);
		consoleVal6.put("Released", true);
		myDB.insertIntoTable("Consoles", consoleVal6);

		Hashtable<String, Object> consoleVal7 = new Hashtable<String, Object>();
		Date D17 = new Date(2017, 6, 5);
		consoleVal7.put("ConsoleName", "X970");
		consoleVal7.put("ManufacturerName", "Microsoft");
		consoleVal7.put("ReleaseDate", D17);
		consoleVal7.put("Released", false);
		myDB.insertIntoTable("Consoles", consoleVal7);

		Hashtable<String, Object> consoleVal8 = new Hashtable<String, Object>();
		Date D18 = new Date(2005, 9, 2);
		consoleVal8.put("ConsoleName", "Wii");
		consoleVal8.put("ManufacturerName", "Nintendo");
		consoleVal8.put("ReleaseDate", D18);
		consoleVal8.put("Released", true);
		myDB.insertIntoTable("Consoles", consoleVal8);

		Hashtable<String, Object> consoleVal9 = new Hashtable<String, Object>();
		Date D19 = new Date(2018, 9, 4);
		consoleVal9.put("ConsoleName", "WiiMii");
		consoleVal9.put("ManufacturerName", "Nintendo");
		consoleVal9.put("ReleaseDate", D19);
		consoleVal9.put("Released", false);
		myDB.insertIntoTable("Consoles", consoleVal9);

		Hashtable<String, Object> consoleVal10 = new Hashtable<String, Object>();
		Date D20 = new Date(2018, 9, 4);
		consoleVal10.put("ConsoleName", "GameCube");
		consoleVal10.put("ManufacturerName", "Nintendo");
		consoleVal10.put("ReleaseDate", D20);
		consoleVal10.put("Released", true);
		myDB.insertIntoTable("Consoles", consoleVal10);

		// Insertion into Games

		Hashtable<String, Object> gameVal1 = new Hashtable<String, Object>();
		gameVal1.put("Name", "Digimon World 3");
		gameVal1.put("SerialNo", Integer.valueOf(764532));
		gameVal1.put("Genre_GameGenre", "Adventure");
		myDB.insertIntoTable("Games", gameVal1);

		Hashtable<String, Object> gameVal2 = new Hashtable<String, Object>();
		gameVal2.put("Name", "Naruto Shippuden 6");
		gameVal2.put("SerialNo", Integer.valueOf(324562));
		gameVal2.put("Genre_GameGenre", "RPG");
		myDB.insertIntoTable("Games", gameVal2);

		Hashtable<String, Object> gameVal3 = new Hashtable<String, Object>();
		gameVal3.put("Name", "Castlevania");
		gameVal3.put("SerialNo", Integer.valueOf(676786));
		gameVal3.put("Genre_GameGenre", "AdventureClass");
		myDB.insertIntoTable("Games", gameVal3);

		Hashtable<String, Object> gameVal4 = new Hashtable<String, Object>();
		gameVal4.put("Name", "No More Heroes");
		gameVal4.put("SerialNo", Integer.valueOf(85034922));
		gameVal4.put("Genre_GameGenre", "Adventure");
		myDB.insertIntoTable("Games", gameVal4);

		Hashtable<String, Object> gameVal5 = new Hashtable<String, Object>();
		gameVal5.put("Name", "Crash Bash");
		gameVal5.put("SerialNo", Integer.valueOf(435234234));
		gameVal5.put("Genre_GameGenre", "Adventure");
		myDB.insertIntoTable("Games", gameVal5);

		Hashtable<String, Object> gameVal6 = new Hashtable<String, Object>();
		gameVal6.put("Name", "Final Fantasy VII");
		gameVal6.put("SerialNo", Integer.valueOf(45663422));
		gameVal6.put("Genre_GameGenre", "RPG");
		myDB.insertIntoTable("Games", gameVal6);

		Hashtable<String, Object> gameVal7 = new Hashtable<String, Object>();
		gameVal7.put("Name", "Dynasty Warriors");
		gameVal7.put("SerialNo", Integer.valueOf(23423523));
		gameVal7.put("Genre_GameGenre", "Action");
		myDB.insertIntoTable("Games", gameVal7);

		Hashtable<String, Object> gameVal8 = new Hashtable<String, Object>();
		gameVal8.put("Name", "Barbie the chase of the gem");
		gameVal8.put("SerialNo", Integer.valueOf(56456464));
		gameVal8.put("Genre_GameGenre", "RPG");
		myDB.insertIntoTable("Games", gameVal8);

		// Inserting into Characters

		Hashtable<String, Object> characterVal1 = new Hashtable<String, Object>();
		characterVal1.put("CharacterName", "Barbie");
		characterVal1.put("Game_GameName", "Barbie the chase of the gem");
		characterVal1.put("Ability", "Searching for the Truth");
		myDB.insertIntoTable("Characters", characterVal1);

		Hashtable<String, Object> characterVal2 = new Hashtable<String, Object>();
		characterVal2.put("CharacterName", "Shikamaru");
		characterVal2.put("Game_GameName", "Naruto Shippuden 6");
		characterVal2.put("Ability", "Superior IQ");
		myDB.insertIntoTable("Characters", characterVal2);

		Hashtable<String, Object> characterVal3 = new Hashtable<String, Object>();
		characterVal3.put("CharacterName", "Xing");
		characterVal3.put("Game_GameName", "Dynasty Warriors");
		characterVal3.put("Ability", "Ice Making");
		myDB.insertIntoTable("Characters", characterVal3);

		Hashtable<String, Object> characterVal4 = new Hashtable<String, Object>();
		characterVal4.put("CharacterName", "Hector");
		characterVal4.put("Game_GameName", "CastleVania");
		characterVal4.put("Ability", "Devil Forging");
		myDB.insertIntoTable("Characters", characterVal4);

		Hashtable<String, Object> characterVal5 = new Hashtable<String, Object>();
		characterVal5.put("CharacterName", "X");
		characterVal5.put("Game_GameName", "Blade and Soul");
		characterVal5.put("Ability", "Blade Dancing");
		myDB.insertIntoTable("Characters", characterVal5);

		Hashtable<String, Object> characterVal6 = new Hashtable<String, Object>();
		characterVal6.put("CharacterName", "Trevor");
		characterVal6.put("Game_GameName", "Digimon World 3");
		characterVal6.put("Ability", "Digimon Training");
		myDB.insertIntoTable("Characters", characterVal6);

	}

	public static void manupilationCMDs(DBApp myDB)
			throws FileNotFoundException, ClassNotFoundException, DBAppException, IOException, DBEngineException {
		/*
		 * //Trying to insert into Non-Existent Table Hashtable <String,Object>
		 * errorMakerInerstion1 = new Hashtable <String,Object>();
		 * errorMakerInerstion1.put("Bad Key1", "Bad Value 1");
		 * myDB.insertIntoTable("None", errorMakerInerstion1);
		 */

		// Normal Selection
		Hashtable<String, Object> normalTesterSelector1 = new Hashtable<String, Object>();
		normalTesterSelector1.put("GameGenre", "Action");
		Iterator printer1 = myDB.selectFromTable("GameGenre", normalTesterSelector1, "AND");
		while (printer1.hasNext()) {
			System.out.println(printer1.next().toString());
		}
		System.out.println("Selection Works!!!");

		// //Updating entry and Selecting it
		//
		//
		// //Updating All Values of a Record
		//
		// Hashtable<String,Object> normalTesterSelector3 = new Hashtable
		// <String,Object>();
		// normalTesterSelector3.put("ID",2);
		// Iterator printer3 =
		// myDB.selectFromTable("Gamers",normalTesterSelector3, "OR");
		// if (!printer3.hasNext())
		// System.out.print("Empty Iterator");
		// while (printer3.hasNext()){
		// System.out.println(printer3.next().toString());
		// }
		//
		// Hashtable<String,Object> updatingHash1 = new Hashtable
		// <String,Object>();
		// Date Date1 = new Date(20222,5,11);
		// updatingHash1.put("ID",Integer.valueOf(11));
		// updatingHash1.put("FirstName","Isaac");
		// updatingHash1.put("LastName","Newton");
		// updatingHash1.put("Country","Italy");
		// updatingHash1.put("BirthDate",Date1);
		// updatingHash1.put("GameGenre_GameGenre","Adventure");
		// myDB.updateTable("Gamers",2, updatingHash1);
		// System.out.println("Updated Entry");
		//
		// //Duplicate primary key on update
		//// Hashtable<String,Object> updatingHashFail = new Hashtable
		// <String,Object>();
		//// Date1 = new Date(20222,5,11);
		//// updatingHashFail.put("ID",Integer.valueOf(3));
		//// updatingHashFail.put("FirstName","Isaac");
		//// updatingHashFail.put("LastName","Newton");
		//// updatingHashFail.put("Country","Italy");
		//// updatingHashFail.put("BirthDate",Date1);
		//// updatingHashFail.put("GameGenre_GameGenre","Adventure");
		//// myDB.updateTable("Gamers",5, updatingHashFail);
		//// System.out.println("Updated Entry");
		//
		// Hashtable<String,Object> normalTesterSelector2 = new Hashtable
		// <String,Object>();
		// normalTesterSelector2.put("ID",11);
		// Iterator printer2 =
		// myDB.selectFromTable("Gamers",normalTesterSelector2, "OR");
		// if (!printer2.hasNext())
		// System.out.print("Empty Iterator");
		// while (printer2.hasNext()){
		// System.out.println(printer2.next().toString());
		// }
		// System.out.println("Selecting After Updating All Successful");
		//
		// //Deleting 2 Entries with Or after changing one of them
		// Hashtable<String,Object> deletingHash1 = new Hashtable
		// <String,Object>();
		// deletingHash1.put("FirstName","Isaac");
		// deletingHash1.put("LastName", "Saeed");
		// myDB.deleteFromTable("Gamers", deletingHash1, "or");
		//
		// System.out.println("Deletion After Updating All Successful");
		//
		// Updating Some without key
		Hashtable<String, Object> normalTesterSelector5 = new Hashtable<String, Object>();
		normalTesterSelector5.put("ID", 4);
		Iterator printer5 = myDB.selectFromTable("Gamers", normalTesterSelector5, "and");
		if (!printer5.hasNext()) {
			System.out.println("Empty Iterator");
			System.out.println("Empty Iterator");
			System.out.println("Empty Iterator");
		}
		while (printer5.hasNext()) {
			System.out.println(printer5.next().toString());
		}

		Hashtable<String, Object> updatingHash2 = new Hashtable<String, Object>();
		updatingHash2.put("FirstName", "Ahmed");
		updatingHash2.put("LastName", "Tarek");
		updatingHash2.put("Country", "Germany");
		updatingHash2.put("GameGenre_GameGenre", "RPG");
		myDB.updateTable("Gamers", 4, updatingHash2);
		System.out.println("Updated Entry");
		//
		//
		// Hashtable<String,Object> normalTesterSelector6 = new Hashtable
		// <String,Object>();
		// normalTesterSelector6.put("ID",4);
		// Iterator printer6 =
		// myDB.selectFromTable("Gamers",normalTesterSelector6, "or");
		// if (!printer6.hasNext())
		// System.out.print("Empty Iterator");
		// while (printer6.hasNext()){
		// System.out.println(printer6.next().toString());
		// }
		//
		// System.out.println("Selecting After Updating Some without Key
		// Successful");
		//
		// //Deleting updated entry
		// Hashtable<String,Object> deletingHash2 = new Hashtable
		// <String,Object>();
		// deletingHash2.put("FirstName","Ahmed");
		// myDB.deleteFromTable("Gamers", deletingHash2, "or");
		//
		// System.out.println("Deletion After Updating without key Successful");
		//
		// //Updating Some Values with the Key
		// Hashtable<String,Object> normalTesterSelector7 = new Hashtable
		// <String,Object>();
		// normalTesterSelector7.put("ID",6);
		// Iterator printer7 =
		// myDB.selectFromTable("Gamers",normalTesterSelector7, "OR");
		// if (!printer7.hasNext())
		// System.out.print("Empty Iterator");
		// while (printer7.hasNext()){
		// System.out.println(printer7.next().toString());
		// }
		//
		// Hashtable<String,Object> updatingHash3 = new Hashtable
		// <String,Object>();
		// updatingHash3.put("ID",Integer.valueOf(55));
		// updatingHash3.put("FirstName","Zack");
		// updatingHash3.put("LastName","Fair");
		// updatingHash3.put("Country","Nebilhiem");
		// myDB.updateTable("Gamers", 6, updatingHash3);
		// System.out.println("Updated Entry");
		//
		//
		// Hashtable<String,Object> normalTesterSelector8 = new Hashtable
		// <String,Object>();
		// normalTesterSelector8.put("FirstName","Zack");
		// Iterator printer8 =
		// myDB.selectFromTable("Gamers",normalTesterSelector8,"or");
		// if (!printer8.hasNext())
		// System.out.print("Empty Iterator");
		// while (printer8.hasNext()){
		// System.out.println(printer8.next().toString());
		// }
		// System.out.println("Selecting Entry after updating Key with some
		// values Successful");

		// Deleting updated entry
		Hashtable<String, Object> deletingHash3 = new Hashtable<String, Object>();
		deletingHash3.put("ID", 55);
		myDB.deleteFromTable("Gamers", deletingHash3, "or");

		System.out.println("Deletion After Updating without key Successful");
	}

	public static void M4Tests(DBApp myDB) {
		// Tests.M1Tests(myDB);
		// System.out.println("M1 TESTS DONE");
		// Tests.M2Tests(myDB);
		// System.out.println("M2 TESTS DONE");
		// Tests.M2SchemaTest(myDB);
		// Enumeration<String> keys =
		// myDB.tableMeta.get("Gamers").colNameBTree.keys();
		// while(keys.hasMoreElements())
		// System.out.println(keys.nextElement());
		//
		// ArrayList x =
		// myDB.tableMeta.get("Gamers").colNameBTree.get("ID").search(Integer.valueOf(4));
		// for (int i = 0; i < x.size(); i++) {
		// System.out.println(x.get(i));
		//// }
		// Hashtable<String, Object> htbl= new Hashtable<String, Object>();
		// htbl.put("FirstName", "Ahmed");
		// Iterator<tuple> x;
		//// x= myDB.selectByIndex(myDB.tableMeta.get("Gamers"), htbl, "and");
		//// x= myDB.selectFromTable("Gamers", htbl, "and");
		//// myDB.deleteFromTable("Gamers", htbl, "or");
		// myDB.createIndex("Gamers", "FirstName");
		// x= myDB.selectFromTable("Gamers", htbl, "and");
		// while(x.hasNext()){
		// System.out.println(x.next());
		// }

		// myDB.deleteFromTable("Gamers", htbl, "and");
		// x= myDB.selectFromTable("Gamers", htbl, "and");
		// if(!x.hasNext())
		// System.out.println("yaay delete by index w msh mawgood");
		// while(x.hasNext()){
		// System.out.println(x.next());
		// }
		// System.out.println("M2 Schema Test DONE");
		// System.err.println("All the tests were completed !! :3");
		// Hashtable<String, String> htblColNameType = new
		// Hashtable<String,String>();
		// htblColNameType.put("id", "Integer");
		// htblColNameType.put("firstName", "String");
		// htblColNameType.put("lastName", "String");
		// Hashtable<String, String> htblColNameRefs = new
		// Hashtable<String,String>();
		// String strKeyColName = "id";
		// myDB.createTable("eval", htblColNameType, htblColNameRefs,
		// strKeyColName);
		//
		//
		// for (int i = 1; i < 1000; i++) {
		// Hashtable<String, Object> htblColNameValue = new Hashtable<String,
		// Object>();
		// htblColNameValue.put("id", Integer.valueOf(i));
		// htblColNameValue.put("firstName", "StudentFirst"+i);
		// htblColNameValue.put("lastName", "StudentLast"+i);
		// myDB.insertIntoTable("eval", htblColNameValue);
		// }
	}

	public static void evaluate(DBApp myDB)
			throws FileNotFoundException, ClassNotFoundException, DBEngineException, IOException {
		Hashtable<String, Object> htblColNameValue = new Hashtable<String, Object>();
		htblColNameValue.put("id", 3);
		long startTime1 = System.currentTimeMillis();
		Iterator x = myDB.selectFromTable("eval", htblColNameValue, "and");
		long endTime1 = System.currentTimeMillis();
		long totalTime1 = endTime1 - startTime1;
		System.out.println(totalTime1);
		while (x.hasNext())
			System.out.println(x.next());

		long startTime2 = System.currentTimeMillis();
		htblColNameValue = new Hashtable<String, Object>();
		htblColNameValue.put("firstName", "StudentFirst112");
		Iterator y = myDB.selectFromTable("eval", htblColNameValue, "and");
		long endTime2 = System.currentTimeMillis();
		long totalTime2 = endTime2 - startTime2;
		System.out.println(totalTime2);
		while (y.hasNext())
			System.out.println(y.next());
		System.out.println("Evaluation test cases were completed succesfully");
	}
}
