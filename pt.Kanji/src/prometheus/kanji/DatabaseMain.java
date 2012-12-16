/**DatabaseMain - single database class
 * @author Ivana Marincic*/
package prometheus.kanji;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;
import android.util.Log;


public class DatabaseMain {

	private static final String TAG = "Kanji";
	/** columns */
	private static final String CHARACTER_ID = "characterId";
	private static final String CHARACTER = "characterText";
	private static final String DEFINITION = "definition";
	private static final String NUM_CORRECT = "num_correct";
	private static final String NUM_INCORRECT = "num_incorrect";
	private static final String CHAPTER_NUMBER = "chapterNumber";
	private static final String NUM_CHARACTER = "num_character";
	private static final String FIRST_ID = "first_id";
	private static final String ACTUAL_DEFINITION = "actual_definition";
	private static final String E_DEFINITION = "englishDef";
	/** table names, database name and version */
	private static final String DATABASE_NAME = "kanji2";
	private static final String TABLE_CHARACTER = "character";
	private static final String TABLE_CHAPTER = "table_chapters";
	private static final int DATABASE_VERSION = 3;
	
	/** other variables */
	private DbHelper myHelper;
	private Context context;
	private SQLiteDatabase myDatabase;
	private static final HashMap<String, String> mColumnMap = buildColumnMap();


	private class DbHelper extends SQLiteOpenHelper {
		public DbHelper(Context context2) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			context = context2;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			myDatabase = db;

			/** create main table that is based on the text file */

			try {
				String createQuery = "CREATE TABLE " + TABLE_CHARACTER + " ("
						+ CHAPTER_NUMBER + " INTEGER, " 
						+ NUM_CHARACTER+ " INTEGER, "
						+ CHARACTER_ID + " INTEGER, "
						+ CHARACTER + " TEXT NOT NULL, " 
						+ DEFINITION+ " TEXT NOT NULL, " 
						+ E_DEFINITION + " TEXT NOT NULL, "
						+ ACTUAL_DEFINITION + " TEXT NOT NULL, "
						+ NUM_CORRECT + " INTEGER, "
						+ NUM_INCORRECT + " INTEGER, " + "PRIMARY KEY ("
						+ CHAPTER_NUMBER + ", " + CHARACTER_ID + "));";
				Log.d(TAG, "Creating first table... : " + createQuery);
				db.execSQL(createQuery);

				String createQuery2 = "CREATE TABLE " + TABLE_CHAPTER + " ("
						+ CHAPTER_NUMBER + " INTEGER PRMIMARY KEY, "
						+ NUM_CHARACTER + " INTEGER, " + FIRST_ID
						+ " INTEGER);";
				Log.d(TAG, "Creating first table... : " + createQuery2);
				db.execSQL(createQuery2);

			} catch (SQLException e) {
				Log.e(TAG, e.getMessage());
			}
			Log.d(TAG, "loadChapter initialized...");
			try {
				loadCharacters();
			} catch (IOException e) {

				Log.e(TAG, e.getMessage());
			}
		}

		private void loadCharacters() throws IOException {
			Log.d(TAG, "Loading text..");
			final Resources resources = context.getResources();
			InputStream inputStream = resources
					.openRawResource(R.raw.j);
			InputStream inputStream2 = resources
					.openRawResource(R.raw.chapters);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			BufferedReader reader2 = new BufferedReader(new InputStreamReader(
					inputStream2));

			try {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] strings = TextUtils.split(line, "\t");
					Log.d(TAG, strings[0] + ", " + strings[1] + ", "
							+ strings[2] + ", " + strings[3] + ", " + strings[4]+ ", " + strings[5] + ", "+strings[6]);
					String ch_num = strings[0].toString().trim();
					String num_char = strings[1].trim().toString().trim();
					String char_id = strings[2].trim().toString().trim();
					long id = addWord(Integer.parseInt(ch_num.replaceAll("[^0-9.]","")),
							Integer.parseInt(num_char.replaceAll("[^0-9.]","")),
							Integer.parseInt(char_id.replaceAll("[^0-9.]","")), strings[3].trim(),
							strings[4].trim(),strings[5].trim(),strings[6].trim());
					if (id == -1) {
						Log.e(TAG, "Failed to insert in " + TABLE_CHARACTER);
					} else
						Log.d(TAG, "line number of inserted row is: " + id
								+ " in " + TABLE_CHARACTER);
				}
				String line2;
				while ((line2 = reader2.readLine()) != null) {
					String[] strings2 = TextUtils.split(line2, "\t");
					Log.d(TAG, strings2[0] + ", " + strings2[1] + ", "
							+ strings2[2]);
					String ch_num2 = strings2[0].trim();
					String num_char2 = strings2[1].trim();
					String firstId = strings2[2].trim();
					long id2 = addWord2(Integer.parseInt(ch_num2),
							Integer.parseInt(num_char2),
							Integer.parseInt(firstId));
					if (id2 == -1) {
						Log.e(TAG, "Failed to insert in " + TABLE_CHARACTER);
					} else
						Log.d(TAG, "line number of inserted row is: " + id2
								+ " in " + TABLE_CHARACTER);
				}
			} finally {
				reader.close();
				reader2.close();
				Log.d(TAG, "Done loading words...");
			}
		}

		/** the content from the text file is inserted into the SQLite table */
		public long addWord(int i, int j, int k, String character,
				String definition, String eDef, String actDef) {
			ContentValues initialValues = new ContentValues();
			initialValues.put(CHAPTER_NUMBER, i);
			initialValues.put(NUM_CHARACTER, j);
			initialValues.put(CHARACTER_ID, k);
			initialValues.put(CHARACTER, character);
			initialValues.put(DEFINITION, definition);
			initialValues.put(E_DEFINITION, eDef);
			initialValues.put(ACTUAL_DEFINITION, actDef);
			initialValues.put(NUM_CORRECT, 0);
			initialValues.put(NUM_INCORRECT, 0);
			long id = myDatabase.insert(TABLE_CHARACTER, null, initialValues);
			Log.d(TAG, "done inserting in" + TABLE_CHARACTER);
			return id;
		}

		public long addWord2(int i, int j, int k) {
			ContentValues initialValues = new ContentValues();
			initialValues.put(CHAPTER_NUMBER, i);
			initialValues.put(NUM_CHARACTER, j);
			initialValues.put(FIRST_ID, k);
			long id = myDatabase.insert(TABLE_CHAPTER, null, initialValues);
			Log.d(TAG, "done inserting in" + TABLE_CHAPTER);
			return id;
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS" + TABLE_CHARACTER);
			db.execSQL("DROP TABLE IF EXISTS" + TABLE_CHAPTER);
			onCreate(db);
		}
	}

	/** constructor of the CreatingDatabase class */
	public DatabaseMain(Context c) {
		context = c;
		myHelper = new DbHelper(c);
		Log.d(null, c.toString());
	}

	/**
	 * this provides the column map that is requested by the query builder in
	 * any of the methods that need to execute a query
	 */
	private static HashMap<String, String> buildColumnMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(CHAPTER_NUMBER, CHAPTER_NUMBER);
		map.put(NUM_CHARACTER, NUM_CHARACTER);
		map.put(CHARACTER_ID, CHARACTER_ID);
		map.put(CHARACTER, CHARACTER);
		map.put(DEFINITION, DEFINITION);
		map.put(E_DEFINITION, E_DEFINITION);
		map.put(ACTUAL_DEFINITION, ACTUAL_DEFINITION);
		map.put(NUM_CORRECT, NUM_CORRECT);
		map.put(NUM_INCORRECT, NUM_INCORRECT);
		map.put(FIRST_ID, FIRST_ID);
		return map;
	}

	public void close() {
		Log.d(TAG, "close initialized...");
		myHelper.close();
	}

	/**
	 * insertion of user's input into the other two tables
	 * 
	 * @param count
	 */
	
	public int getCorrectAtId(int count){
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(TABLE_CHARACTER);
		builder.setProjectionMap(mColumnMap);
		myDatabase = myHelper.getReadableDatabase();
		String[] columns = new String[] { CHAPTER_NUMBER, NUM_CHARACTER,
				CHARACTER_ID, CHARACTER, DEFINITION, E_DEFINITION, ACTUAL_DEFINITION, NUM_CORRECT, NUM_INCORRECT };
		String whereClause = CHARACTER_ID + "=" + count;
		Cursor c = builder.query(myDatabase, columns, whereClause.toString(),
				null, null, null, null);
		int result = 0;
		if (c.moveToFirst()) {
			result = c.getInt(c.getColumnIndex(NUM_CORRECT));
		}
		return result;
	}
	public void updateCorrect(int correct, int count,int result) {
		ContentValues newValues = new ContentValues();
		newValues.put(NUM_CORRECT, result + correct);
		myDatabase = myHelper.getWritableDatabase();
		myDatabase.update(TABLE_CHARACTER, newValues, CHARACTER_ID + "="
				+ count, null);
		Log.d(TAG, "updated correct by: " + count);
		close();
	}

	public void updateIncorrect(int incorrect, int count) {
		ContentValues newValues = new ContentValues();
		newValues.put(NUM_INCORRECT, incorrect);
		myDatabase = myHelper.getWritableDatabase();
		myDatabase.update(TABLE_CHARACTER, newValues, CHARACTER_ID + "="
				+ count, null);
		Log.d(TAG, "updated incorrect by: " + count);
		close();
	}

	/**
	 * method that displays a randomly chosen character from the main table into
	 * the Reading class
	 * 
	 * @param howManyCharsLeft
	 *            - holds the value of how many characters are left within one
	 *            chapter
	 * @param count
	 *            - how many times the activity Reading has been executed -
	 *            displays characters in that order
	 */
	public String toDisplay(int howManyCharsLeft, int count, String whatColumn) {
		String result = "not good";
		if (howManyCharsLeft == 0)
			result = "";
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(TABLE_CHARACTER);
		builder.setProjectionMap(mColumnMap);
		myDatabase = myHelper.getReadableDatabase();
		Log.d(TAG, "toDisplay initialized...");
		String[] columns = new String[] { CHAPTER_NUMBER, NUM_CHARACTER,
				CHARACTER_ID, CHARACTER, DEFINITION, E_DEFINITION, ACTUAL_DEFINITION, NUM_CORRECT, NUM_INCORRECT };
		String whereClause = CHARACTER_ID + "=" + count;
		Log.d(TAG, "whereClause: " + whereClause);
		Cursor c = builder.query(myDatabase, columns, whereClause.toString(),
				null, null, null, null);
		Log.d(TAG, "toDisplay: number of rows in cursor is: " + c.getCount());
		if (c.moveToFirst()) {
			if (whatColumn == "getChar")
				result = c.getString(c.getColumnIndex(CHARACTER));
			else if (whatColumn == "getDef")
				result = c.getString(c.getColumnIndex(DEFINITION));
		} else {
			Log.e(TAG, "toDisplay: moveToFirst failed");
		}
		if (c != null && !c.isClosed()) {
			c.close();
		}
		if (myDatabase != null) {
			myDatabase.close();
		}
		return result;
	}

	/**
	 * this function evaluates the correctness of the input from the Reading
	 * class
	 */
	public ArrayList<String> processAnswer(String prompt) {
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(TABLE_CHARACTER);
		builder.setProjectionMap(mColumnMap);
		myDatabase = myHelper.getReadableDatabase();
		String[] columns = new String[] { CHAPTER_NUMBER, NUM_CHARACTER,
				CHARACTER_ID, CHARACTER, DEFINITION, E_DEFINITION, ACTUAL_DEFINITION, NUM_CORRECT, NUM_INCORRECT };
		ArrayList<String> correct = new ArrayList<String>();
		String whereClause = CHARACTER + "='" + prompt + "'";
		Cursor c = builder.query(myDatabase, columns, whereClause, null, null,
				null, null);
		if (c.moveToFirst()) {
			correct.add(c.getString(c.getColumnIndex(DEFINITION)));
			correct.add(c.getString(c.getColumnIndex(E_DEFINITION)));
			Log.d(TAG, "the correct definition where CHARACTER = prompt is '"
					+ correct + "'");
		} else
			Log.e(TAG, "processAnswer: moveToFirst failed...");
		if (c != null && !c.isClosed()) {
			c.close();
		}
		if (myDatabase != null) {
			myDatabase.close();
		}
		return correct;
	}

	/**
	 * this function returns the amount of characters present in selected
	 * chapters
	 */
	public int howManyCharacters(ArrayList<Integer> chapters) {
		int numCharacter = 0;
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(TABLE_CHAPTER);
		builder.setProjectionMap(mColumnMap);
		myDatabase = myHelper.getReadableDatabase();
		String whereClause = "";
		for (int i = 0; i < chapters.size() - 1; i++) {
			int chp = chapters.get(i);
			Log.d(TAG, "chapter from array: " + chp);
			whereClause += CHAPTER_NUMBER + "=" + chp + " OR ";
		}
		whereClause += CHAPTER_NUMBER + "=" + chapters.get(chapters.size() - 1);

		String[] columns = new String[] { CHAPTER_NUMBER, NUM_CHARACTER,
				FIRST_ID };
		Cursor c = builder.query(myDatabase, columns, whereClause, null, null,
				null, null);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			numCharacter += c.getInt(c.getColumnIndex(NUM_CHARACTER));
		}
		if (c != null && !c.isClosed()) {
			c.close();
		}
		if (myDatabase != null) {
			myDatabase.close();
		}
		return numCharacter;
	}

	/** method for obtaining a where clause when an array is passed in **/
	public String makeWhereClauseFromArray(ArrayList<Integer> chapters) {
		String whereClause = "";
		for (int i = 0; i < chapters.size() - 1; i++) {
			int chp = chapters.get(i);
			Log.d(TAG, "chapter from array: " + chp);
			whereClause += CHAPTER_NUMBER + "=" + chp + " OR ";
		}
		whereClause += CHAPTER_NUMBER + "=" + chapters.get(chapters.size() - 1);
		Log.d(TAG, "whereClause: " + whereClause);
		return whereClause;
	}

	/**
	 * in some activities we need to know from where to start counting the
	 * characters, so this method returns the id of the first occuring character
	 * in the selected chapters
	 * 
	 * @param firstChapter
	 * @return
	 */
	public int getFirstId(int firstChapter) {
		int firstId = -1;
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(TABLE_CHAPTER);
		builder.setProjectionMap(mColumnMap);
		myDatabase = myHelper.getReadableDatabase();
		String[] columns = new String[] { CHAPTER_NUMBER, NUM_CHARACTER,
				FIRST_ID };
		String whereClause = CHAPTER_NUMBER + "=" + firstChapter;
		Cursor c = builder.query(myDatabase, columns, whereClause, null, null,
				null, null);
		if (c.moveToFirst()) {
			firstId = c.getInt(c.getColumnIndex(FIRST_ID));
		}
		if (c != null && !c.isClosed()) {
			c.close();
		}
		if (myDatabase != null) {
			myDatabase.close();
		}
		return firstId;
	}

	/** returns the number of correctly answered questions */
	public int getCorrect() {
		int correct = 0;
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(TABLE_CHARACTER);
		builder.setProjectionMap(mColumnMap);
		myDatabase = myHelper.getReadableDatabase();
		String[] columns = new String[] { CHAPTER_NUMBER, NUM_CHARACTER,
				CHARACTER_ID, CHARACTER, DEFINITION, E_DEFINITION, ACTUAL_DEFINITION, NUM_CORRECT, NUM_INCORRECT };

		Cursor c = builder.query(myDatabase, columns, null, null, null,
				null, null);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			correct += c.getInt(c.getColumnIndex(NUM_CORRECT));
			Log.d(TAG, "getCorrect: " + correct);
		}
		if (c != null && !c.isClosed()) {
			c.close();
		}
		if (myDatabase != null) {
			myDatabase.close();
		}
		return correct;
	}
}