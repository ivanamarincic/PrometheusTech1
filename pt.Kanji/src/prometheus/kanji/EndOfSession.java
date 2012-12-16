/**EndOfSession class - displays progress of an individual session
 * @author Ivana Marincic 
 * @version 2, finalized 11/30/2012*/

package prometheus.kanji;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class EndOfSession extends Activity{
	
	//private String TAG = "endOfSession";
	
	private TextView chapters,total,correct;
	private ArrayList<Integer> chpn = new ArrayList<Integer>();
	private int totals, corrects;
	private Button finish;
	
	 @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.end_of_session);
	      
	      finish = (Button) findViewById(R.id.finish_session);
	      finish.setOnClickListener(new handleClick());
	      Bundle bundle = getIntent().getExtras();
	      chpn = bundle.getIntegerArrayList("chp");
	      totals = bundle.getInt("totals");
	      corrects = bundle.getInt("corrects");
	      
	      chapters = (TextView) findViewById(R.id.chp_number);
	      total = (TextView) findViewById(R.id.total_chars_num);
	      correct = (TextView) findViewById(R.id.total_correct_num);
	      
	      String c = "";
	      
	      for(int i=0;i<chpn.size()-1;i++){ //iterate through the chapter array, each number has to be displayed
	    	  c +=chpn.get(i)+", ";
	      }
	      c += chpn.get(chpn.size()-1);
	      String t= Integer.toString(totals);
	      String cr = Integer.toString(corrects);
	      
	      //Log.d(TAG, "what chapters: "+ c);
	      //Log.d(TAG, "totals: " +t);
	      //Log.d(TAG, "corrects: " + cr);
	      
	      chapters.setText(c);
	      total.setText(t);
	      correct.setText(cr);	      
	   }
	 
	    public void onAttachedToWindow(){
	    	super.onAttachedToWindow();
	    	Window window = getWindow();
	    	window.setFormat(PixelFormat.RGBA_8888);
	    }
	    
	    class handleClick implements OnClickListener{
	    	public void onClick(View v){
	    		EndOfSession.this.finish();
	    	}
	    }
}