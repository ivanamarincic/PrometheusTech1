/**class PracticeProblems - offers book selection. The names of this class are based on where they came from, 
 * so where we select books is PracticeProblems, where we select chapter is the Book class, and where we select the practice mode
 * we are in Chapter class.
 * @author Ivana Marincic
 * @layout Jackson Struble, Ivana Marincic */
package prometheus.kanji;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;



public class PracticeProblems extends Activity{
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_problems);
 
        
		findViewById(R.id.book_button).setOnClickListener(new handleButton());
		findViewById(R.id.empty1_button).setOnClickListener(new handleButton());
		findViewById(R.id.empty2_button).setOnClickListener(new handleButton());

}
    public void onAttachedToWindow(){
    	super.onAttachedToWindow();
    	Window window = getWindow();
    	window.setFormat(PixelFormat.RGBA_8888);
    }
    
class handleButton implements OnClickListener{        
    
    public void onClick(View v){
    	switch(v.getId()){
    	
    	case R.id.book_button:
    		Intent b = new Intent(PracticeProblems.this, Book.class);
    		startActivity(b);
    		PracticeProblems.this.finish();
    		break;
    	case R.id.empty1_button:
    		Toast.makeText(getApplicationContext(), "Currently unavailable.", Toast.LENGTH_SHORT).show();
    		break;
    	case R.id.empty2_button:
    		Toast.makeText(getApplicationContext(), "Currently unavailable.", Toast.LENGTH_SHORT).show();
    		
    }
}
}
}
