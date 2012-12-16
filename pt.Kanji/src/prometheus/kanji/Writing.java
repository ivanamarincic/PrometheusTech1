/**Writing class - enables finger drawing*
 * @author Ivana Marincic
 * @version 4, finalized 12/15/2012
 * */

package prometheus.kanji;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Writing extends Activity {

	/**global instances for buttons, imageViews
	 * a new instance of the DrawPanel class, that will enable finger drawing*/
	private Button back, next;

	private DrawPanel drawing;
	
	private ImageView image;
	
	//private String TAG ="writing";
	
	/**hugeCharList - a list of all the drawables that will make up the hint for each character*/
	private final int[] hugeCharList = 
		{0,R.drawable.oone,R.drawable.otwo,0,R.drawable.othree,0,0,0,0,0,0,0,0,0,0,0,
			R.drawable.twone,R.drawable.twtwo,0,0,0,0,0,0,0,0,0,0,0,0,0,
			R.drawable.tone,R.drawable.ttwo,R.drawable.tthree,0,0,0,0,0,0,0,0,0,0,0,0,
			R.drawable.foone,R.drawable.fotwo,0,0,0,0,0,0,0,0,0,0,0,0,0,
			R.drawable.fone,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

	
	/**onCreate
	 * The key point of this method happens when we locate the desired index from the drawables. 
	 * This index is given to us from the previous activity where we pass the count in a bundle that we are retriving here.
	 * LinearLayout ll - In this method we also set up the new linear layout we are going to draw on. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.writing);
		
		Bundle bundle = getIntent().getExtras();
		int currentChp = bundle.getInt("noChar");
		
		image = (ImageView) findViewById(R.id.imageView1);
		
		if(hugeCharList[currentChp]!=0){
			image.setImageResource(hugeCharList[currentChp]);
		}
		else image.setImageDrawable(null);
		
		back = (Button) findViewById(R.id.back_char);
		next = (Button) findViewById(R.id.next_char);
		next.setOnClickListener(new handleClick());
		back.setOnClickListener(new handleClick());
		findViewById(R.id.clear).setOnClickListener(new handleClick());
		
		LinearLayout ll = (LinearLayout) findViewById(R.id.draw_panel);
		drawing = new DrawPanel(getApplicationContext());
		ll.addView(drawing);

	}
	
	/**onKeyDown - to handle the back button 
	 * @param keyCode
	 * @param event of type KeyEvent*/
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent b = getIntent();
			b.putExtra("goBack", "back");
			setResult(RESULT_OK, b);
			finish();
		}
		return true;
	}
	
/**handleClick - subclass to handle button clicks
 * onClick- method that handles clicks
 * @param View v
 * @return send result back to the previous activity*/
	class handleClick implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.next_char:
				Intent k = getIntent();
				k.putExtra("confirmation", "ok");
				setResult(RESULT_OK, k);
				finish();
				break;
			case R.id.back_char:
				Intent b = getIntent();
				b.putExtra("goBack", "back");
				setResult(RESULT_OK, b);
				finish();
				break;
			case R.id.clear:
				drawing.clear();
				break;

				}
			}
		}
	}
