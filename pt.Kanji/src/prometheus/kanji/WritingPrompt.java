/**class WritingPrompt - responsible for displaying the pronunciation whose character needs to be drawn
 * @author Ivana Marincic
 * @version 4, finalized 11/08/2012*/

package prometheus.kanji;

import java.util.ArrayList;
import java.util.Collections;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WritingPrompt extends Activity {

	private String TAG = "WritingPrompt";
	private TextView prompt;
	private Button draw;
	private String definition, tempDef;
	private int tempCount;
	private int count = 0;

	/**getter and setter of count*/
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	private int howManyCharacters, initial_num_char, what_chp;
	final Context context = this;
	private ArrayList<Integer> chaps = new ArrayList<Integer>();
	private DatabaseMain db = new DatabaseMain(this);

	/** getter of the definition that is displayed */
	public String getDefinition() {
		return definition;
	}

	/** setter of the definition displayed */
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	/** getter of the chapters selected in previous activities */
	public int getWhat_chp() {
		return what_chp;
	}

	/** setter of chapters selected */
	public void setWhat_chp(int what_chp) {
		this.what_chp = what_chp;
	}

	/** getter of the amount of characters from the chapters selected */
	public int getHowManyCharacters() {
		return howManyCharacters;
	}

	/** setter of how many characters there are to go over in this session */
	public void setHowManyCharacters(int howManyCharacters) {
		this.howManyCharacters = howManyCharacters;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.writing_prompt);
		Bundle bundle = getIntent().getExtras();
		chaps = bundle.getIntegerArrayList("array2");
		Collections.sort(chaps);
		setCount(db.getFirstId(chaps.get(0))); // we need to start counting from
												// where the character's id
												// starts for the smallest given
												// chapter
		prompt = (TextView) findViewById(R.id.writePrompt);
		draw = (Button) findViewById(R.id.draw);
		draw.setOnClickListener(new handleClick());
		
		findViewById(R.id.finish_c).setOnClickListener(new handleClick());
		initial_num_char = db.howManyCharacters(chaps);
		setHowManyCharacters(initial_num_char);
		//Log.d(TAG, "how many characters: " + getHowManyCharacters());
		
		setDefinition(db.toDisplay(getHowManyCharacters(), getCount(), "getDef"));
		//Log.d(TAG, "getDef: " + getDefinition());
		
		prompt.setText(getDefinition());
		tempDef = getDefinition();
		tempCount = getCount();
	}

	class handleClick implements OnClickListener {
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.draw: 
				Bundle bundle = new Bundle();
				bundle.putInt("noChar", getCount());
				Intent wr = new Intent(WritingPrompt.this, Writing.class);
				wr.putExtras(bundle);
				startActivityForResult(wr, 1);
				break;
			case R.id.finish_c:
				finish();
				break;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data.getExtras().containsKey("confirmation")) {
			//Log.d(TAG, "intent stuff: " + data.getStringExtra("confirmation"));
			mainFunction();
		}
		if (data.getExtras().containsKey("goBack")) {
			restore();
		}
	}

	private void restore() {
		prompt.setText(tempDef);
		setCount(tempCount);
	}

	private void mainFunction() {
		Log.d(TAG, "main Function called");
		setCount(count += 1);
		setHowManyCharacters(howManyCharacters -= 1);
		if (getHowManyCharacters() != 0) {
			setDefinition(db.toDisplay(getHowManyCharacters(), getCount(),
					"getDef"));
			prompt.setText(getDefinition());
			tempDef = getDefinition();
			tempCount = getCount();
		} else{
			 AlertDialog.Builder alertDialogBuilder = new
			 AlertDialog.Builder(context);		
			 alertDialogBuilder.setTitle("");
			 alertDialogBuilder.setMessage("Session Completed.");
			 alertDialogBuilder.setCancelable(true);
			 alertDialogBuilder.setNeutralButton("OK", new
			 DialogInterface.OnClickListener() {
			 public void onClick(DialogInterface dialog,int id) {
			 WritingPrompt.this.finish();		 
			 }});
			 AlertDialog alertDialog = alertDialogBuilder.create();
			 alertDialog.show();
			 
		}
			
	}
}