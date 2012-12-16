/**class Startup - first activity launched
 * @author Ivana Marincic
 * @version 1, finalized 11/05/2012*/

package prometheus.kanji;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class Startup extends Activity {
	
	private Button sum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);
        
        
		findViewById(R.id.practice_problems_button).setOnClickListener(new handleButton());
		sum = (Button) findViewById(R.id.sum_button);
		sum.setOnClickListener(new handleButton());		//findViewById(R.id.flashcards_button).setOnClickListener(new handleButton());
        
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    }
    
    public void onAttachedToWindow(){
    	super.onAttachedToWindow();
    	Window window = getWindow();
    	window.setFormat(PixelFormat.RGBA_8888);
    }
class handleButton implements OnClickListener {
	public void onClick(View v){
    	switch(v.getId()){
    	case R.id.practice_problems_button:
    		 Intent pp = new Intent(Startup.this, PracticeProblems.class);
             startActivityForResult(pp, 0);
             break;
             
    	case R.id.sum_button:
    		Intent u = new Intent(Startup.this, UsageSummary.class);
    		startActivity(u);
            break;
    	}
    	
    }

		
}
}

