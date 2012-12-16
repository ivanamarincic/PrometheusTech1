/**class Book - offers chapter selection
 * @author Ivana Marincic
 * @layout Jackson Struble, Ivana Marincic
 * */
package prometheus.kanji;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class Book extends Activity {

	private String TAG = "book";
	private CheckBox c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12;
	private Button go,content;
	public ArrayList<Integer> chapter_number = new ArrayList<Integer>();
	
	/**getter for the chapter_number array*/
	public ArrayList<Integer> getChapter_number() {
		return chapter_number;
	}
	/**setChapter_number - method to insert an integer in the chapter_number array
	 * @param int chapter - the chapter number to be inserted*/
	public void setChapter_number(int chapter) {
		if (chapter_number.contains(chapter) == false)
			this.chapter_number.add(chapter);
	}
	/**setter for the chapter_number array
	 * @param ArrayList<Integer> c*/
	public void setChapterArray(ArrayList<Integer> c){
		this.chapter_number=c;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book);
		/**defining buttons*/
		go = (Button) findViewById(R.id.start_button1);
		go.setOnClickListener(new handleClick());
		content = (Button) findViewById(R.id.content);
		content.setOnClickListener(new handleClick());
		/**defining the check boxes*/
		c1 = (CheckBox) findViewById(R.id.checkBox1);
		c2 = (CheckBox) findViewById(R.id.checkBox2);
		c3 = (CheckBox) findViewById(R.id.checkBox3);
		c4 = (CheckBox) findViewById(R.id.checkBox4);
		c5 = (CheckBox) findViewById(R.id.checkBox5);
		c6 = (CheckBox) findViewById(R.id.checkBox6);
		c7 = (CheckBox) findViewById(R.id.checkBox7);
		c8 = (CheckBox) findViewById(R.id.checkBox8);
		c9 = (CheckBox) findViewById(R.id.checkBox9);
		c10 = (CheckBox) findViewById(R.id.checkBox10);
		c11 = (CheckBox) findViewById(R.id.checkBox11);
		c12 = (CheckBox) findViewById(R.id.checkBox12);
		/**setting OnClickListeners*/
		c1.setOnClickListener(new handleClick());
		c2.setOnClickListener(new handleClick());
		c3.setOnClickListener(new handleClick());
		c4.setOnClickListener(new handleClick());
		c5.setOnClickListener(new handleClick());
		c6.setOnClickListener(new handleClick());
		c7.setOnClickListener(new handleClick());
		c8.setOnClickListener(new handleClick());
		c9.setOnClickListener(new handleClick());
		c10.setOnClickListener(new handleClick());
		c11.setOnClickListener(new handleClick());
		c12.setOnClickListener(new handleClick());
	}
/**subclass handleClick - responsible for ensuring the array gets properly filled*/
	class handleClick implements OnClickListener {

		public void onClick(View v) {
			if (c1.isChecked())
				setChapter_number(1);
			if (c2.isChecked())
				setChapter_number(2);
			else{
				ArrayList<Integer> temp = getChapter_number();
				if (temp.contains(2)){
					temp.remove(temp.indexOf(2));
					setChapterArray(temp);
				}
			}
			if (c3.isChecked()) 
				setChapter_number(3);
			else{
				ArrayList<Integer> temp = getChapter_number();
				if (temp.contains(3)){
					temp.remove(temp.indexOf(3));
					setChapterArray(temp);
				}
			}
			if (c4.isChecked()) {
				setChapter_number(4);
			}
			else{
				ArrayList<Integer> temp = getChapter_number();
				if (temp.contains(4)){
					temp.remove(temp.indexOf(4));
					setChapterArray(temp);
				}
			}
			
			if (c5.isChecked())
				setChapter_number(5);
			else{
				ArrayList<Integer> temp = getChapter_number();
				if (temp.contains(5)){
					temp.remove(temp.indexOf(5));
					setChapterArray(temp);
				}
			}
			if (c6.isChecked())
				setChapter_number(6);
			else{
				ArrayList<Integer> temp = getChapter_number();
				if (temp.contains(6)){
					temp.remove(temp.indexOf(6));
					setChapterArray(temp);
				}
			}
			if (c7.isChecked()) {
				setChapter_number(7);
			}
			else{
				ArrayList<Integer> temp = getChapter_number();
				if (temp.contains(7)){
					temp.remove(temp.indexOf(7));
					setChapterArray(temp);
				}
			}
			if (c8.isChecked()) {
				setChapter_number(8);
			}
			else{
				ArrayList<Integer> temp = getChapter_number();
				if (temp.contains(8)){
					temp.remove(temp.indexOf(8));
					setChapterArray(temp);
				}
			}
			if (c9.isChecked()) {
				Toast.makeText(getApplicationContext(),
						"This chapter is currently unavailable.",
						Toast.LENGTH_SHORT).show();
				c9.toggle();
			}
			if (c10.isChecked()) {
				Toast.makeText(getApplicationContext(),
						"This chapter is currently unavailable.",
						Toast.LENGTH_SHORT).show();
				c10.toggle();
			}
			if (c11.isChecked()) {
				Toast.makeText(getApplicationContext(),
						"This chapter is currently unavailable.",
						Toast.LENGTH_SHORT).show();
				c11.toggle();
			}
			if (c12.isChecked()) {
				Toast.makeText(getApplicationContext(),
						"This chapter is currently unavailable.",
						Toast.LENGTH_SHORT).show();
				c12.toggle();
			}
			Log.d(TAG, "arraylist created: " + getChapter_number());
			
			switch(v.getId()){
				case R.id.start_button1:
					if (getChapter_number().size() == 0) {
						Toast.makeText(getApplicationContext(),"Plase select a chapter", Toast.LENGTH_SHORT).show();
						break;
					}
					else{
						Intent n = new Intent(Book.this, Chapter.class);
						Bundle bundle = new Bundle();
						bundle.putIntegerArrayList("array", getChapter_number());
						n.putExtras(bundle);
						startActivity(n);
						finish();
						break;
					}
				case R.id.content:
					Intent c = new Intent(Book.this, ListChapterContent.class);
					startActivity(c);
					break;
			}

		}

	}
}