/**Reading class - one of the two main use cases
 * @author Ivana Marincic
 * @version 4, finalized 11/30/2012*/

package prometheus.kanji;

import java.util.ArrayList;
import java.util.Collections;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Reading extends Activity {

	private String TAG = "Reading";
	
	private Button help, finish, done;	
	private String theAnswer; // the answer recorded from the users' input
	public EditText answer; // name of the editText field
	private String data; // what is displayed on the screen
	private TextView tv; // name of the textview "data" will go into
	private DatabaseMain displayDatabase = new DatabaseMain(this); // new instance of CreatingDatabase
	private int count, howManyCharacters, initial_num_char, what_chp, firstChapter, count2;
	final Context context = this;
	private ArrayList<String> correct = new ArrayList<String>();
	private ArrayList<Integer> numbers = new ArrayList<Integer>();
	private ArrayList<Integer> chaps = new ArrayList<Integer>();

	/**getter and setter for the specific chapter number to be retrieved*/
	public int getWhat_chp() {
		return what_chp;
	}

	public void setWhat_chp(int what_chp) {
		this.what_chp = what_chp;
	}
	/**getter and setter of the number of characters in all the chapters selected*/
	public int getHowManyCharacters() {
		return howManyCharacters;
	}

	public void setHowManyCharacters(int howManyCharacters) {
		this.howManyCharacters = howManyCharacters;
	}

	private int nCorrect = 0; //initialize the number of correct answers
	private int nIncorrect = 0; //initialize the number of incorrect answers

	/**getter and setter of the count*/
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	/**getter and setter for the number of correct answers*/
	public int getnCorrect() {
		return nCorrect;
	}

	public void setnCorrect(int nCorrect) {
		this.nCorrect = nCorrect;
	}

	/** Getter for theAnswer */
	public String getTheAnswer() {
		return theAnswer;
	}

	/** Setter for theAnswer */
	public void setTheAnswer(String theAnswer) {
		this.theAnswer = theAnswer;
	}

	/** Getter for data */
	public String getData() {
		return data;
	}

	/** Setter for data */
	public void setData(String data) {
		this.data = data;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reading);
		
		Bundle bundle = getIntent().getExtras();
		chaps = bundle.getIntegerArrayList("array1");
		
		help = (Button) findViewById(R.id.help);
		help.setOnClickListener(new handleClick());
		tv = (TextView) findViewById(R.id.display_char);
		finish = (Button) findViewById(R.id.finish_current);
		finish.setOnClickListener(new handleClick());
		
		Log.d(TAG, "the array retrived is: " + chaps);
		Log.d(TAG, "bundle from chapter: " + getWhat_chp());
		
		Collections.sort(chaps); //we want to sort the chapters, so we can more easily determine where to start the count
		firstChapter = chaps.get(0);
		setCount(displayDatabase.getFirstId(firstChapter));
		
		initial_num_char = displayDatabase.howManyCharacters(chaps);
		
		for (int i = 1;i<=initial_num_char;i++){
			numbers.add(i);
		}
		Collections.shuffle(numbers); //randomize the array - basis for the characters being displayed in a random order
		Log.d(TAG,"numbers shuffled are: "+ numbers);
		
		setHowManyCharacters(displayDatabase.howManyCharacters(chaps));
		setData(displayDatabase.toDisplay(howManyCharacters, numbers.get(0), "getChar")); //calling the toDisplay method from the database class to display a prompt
		
		String retrieved = getData();
		tv.setText(retrieved);	
		
		count2=1; //start the count
		
		done = (Button) findViewById(R.id.done_button);
		done.setOnClickListener(new handleClick());
		
		answer = (EditText) findViewById(R.id.edit_text);
	}
	
	/**onKeyDown - handle back button; we want to warn the user that if the back button is pressed
	 * the session will finish and we will start over.
	 * @param int keyCode, KeyEvent event
	 * @return alert dialog*/
	 public boolean onKeyDown(int keyCode, KeyEvent event){
		 if(keyCode == KeyEvent.KEYCODE_BACK){
			 AlertDialog.Builder alertDialogBuilder = new
				 AlertDialog.Builder(context);
	
		 alertDialogBuilder.setTitle("");
		 alertDialogBuilder.setMessage("This will exit current session. Procced?");
		 alertDialogBuilder.setCancelable(false);
		 alertDialogBuilder.setPositiveButton("Yes",new
				 DialogInterface.OnClickListener() {
			 public void onClick(DialogInterface dialog,int id) {
				 // if this button is clicked, close
				 // current activity
				 Reading.this.finish();
			 }
		 });
		 alertDialogBuilder.setNegativeButton("No",new
				 DialogInterface.OnClickListener() {
			 public void onClick(DialogInterface dialog,int id) {
				 // if this button is clicked, just close
				 // the dialog box and do nothing
				 dialog.cancel();
			 }
		 });
		 // create alert dialog
		 AlertDialog alertDialog = alertDialogBuilder.create();
		 alertDialog.show();
	 }
	 return true;
	 }
	 
	 
	class handleClick implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			
			case R.id.done_button:
				String name = answer.getText().toString();
				//Log.d(TAG, "text typed: " + name);
				correct = displayDatabase.processAnswer(data);
				//Log.d(TAG, "correct answer obtained is: " + correct);
				mainFunction(name, correct);
				//Log.d(TAG, "count is: " + count);
				//Log.d(TAG, "remaining character number is: "+ howManyCharacters);
				break;
				
			case R.id.help:
				Intent n = new Intent(Reading.this, Help.class);
				startActivity(n);
				break;
				
			case R.id.finish_current:
				displayDatabase.makeWhereClauseFromArray(chaps);
				Intent end = new Intent(Reading.this, EndOfSession.class);
				Bundle bundle = new Bundle();
				bundle.putIntegerArrayList("chp", chaps);
				//Log.d(TAG, "initial number of chars: " + initial_num_char);
				bundle.putInt("totals", count2);
				int numberOfCorrect;
				numberOfCorrect = getnCorrect();
				//Log.d(TAG, "number of correct" + numberOfCorrect);
				bundle.putInt("corrects", numberOfCorrect);
				end.putExtras(bundle);
				startActivity(end);
				finish();
				
			}
		}
	}
	/**mainFunction
	 * @param String name, ArrayList<String> correct
	 * @return if there are still characters left to iterate through, display a new character
	 * otherwise, finish this activity and start a new one*/
	public void mainFunction(String name, ArrayList<String> correct) {

		String japanese = correct.get(0);
		String english = correct.get(1);
		
		if (name.toLowerCase().trim().equals(japanese.toLowerCase().trim()) || name.toLowerCase().trim().equals(english.toLowerCase().trim())) {

			setCount(count += 1); //increment the overall count
			setnCorrect(nCorrect += 1); //increment the correct count
			int result = displayDatabase.getCorrectAtId(count); //we need to check how many correct answers are already stored in the database
			displayDatabase.updateCorrect(getnCorrect(), getCount(),result); //update database
			Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
		
		}
		else {
			setCount(count += 1); //increment the overall count
			nIncorrect += 1; //increment the amount incorrect
			displayDatabase.updateIncorrect(nIncorrect, getCount()); //update database
			Toast.makeText(getApplicationContext(), "Sorry, that's wrong!", Toast.LENGTH_SHORT).show();
		}
		
		answer.setText(""); //reset the text view
		setHowManyCharacters(howManyCharacters -= 1); //decrement the amount of characters left
		
		if (getHowManyCharacters() != 0) { //if more characters are left
			int size = numbers.size();
			if(getCount()<size){ //if we haven't gone through all the numbers in the randomized array
			setData(displayDatabase.toDisplay(getHowManyCharacters(),
					numbers.get(getCount()-1), "getChar"));
			}
			else setData(displayDatabase.toDisplay(getHowManyCharacters(),
					numbers.get(size-1), "getChar")); //we then want to just finish with getting the chracter at the last position of the randomized array
			tv.setText(getData()); //set a new prompt
			count2 +=1; //increment count
		} else {
			displayDatabase.makeWhereClauseFromArray(chaps);
			Intent end = new Intent(Reading.this, EndOfSession.class);
			Bundle bundle = new Bundle();
			bundle.putIntegerArrayList("chp", chaps); //here we will send the infomation about that chapter have been selected
			//Log.d(TAG, "initial number of chars: " + initial_num_char);
			bundle.putInt("totals", initial_num_char); //we need to send info about how many characters have been seen in this session
			int numberOfCorrect;
			numberOfCorrect = getnCorrect();
			//Log.d(TAG, "number of correct" + numberOfCorrect);
			bundle.putInt("corrects", numberOfCorrect);
			end.putExtras(bundle);

			startActivity(end);
			finish();
		}
	}
	
/**onAttachedWindow - since we have a custom background, this method makes sure the background is nicely rendered*/	
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window window = getWindow();
		window.setFormat(PixelFormat.RGBA_8888);
	}
}