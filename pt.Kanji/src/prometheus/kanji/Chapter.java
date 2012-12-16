/**class Chapter - its main purpose is to forward the array obtained in the Book class
 * @author Ivana Marincic
 * @layout Jackson Struble*/
package prometheus.kanji;


import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
public class Chapter extends Activity{
	

	private ArrayList<Integer> chaps = new ArrayList<Integer>();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter);
        
        Bundle bundle = getIntent().getExtras();
        chaps = bundle.getIntegerArrayList("array");
        
        
        findViewById(R.id.reading_button).setOnClickListener(new handleButton());
        findViewById(R.id.writing_button).setOnClickListener(new handleButton());
        
 }
 
 @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.startup, menu);
        return true;
    }
    
    public void onAttachedToWindow(){
    	super.onAttachedToWindow();
    	Window window = getWindow();
    	window.setFormat(PixelFormat.RGBA_8888);
    }
    
    class handleButton implements OnClickListener{
        
        public void onClick(View v){
        	switch(v.getId()){
        	
        	case R.id.reading_button:
        		Intent r = new Intent(Chapter.this,Reading.class);
        		Bundle bundle1 = new Bundle();
        		bundle1.putIntegerArrayList("array1", chaps); //this distinction is important for later when we perform a query in the database
        		r.putExtras(bundle1);
        		
        		startActivity(r);
        		finish();
        		break;
        	case R.id.writing_button:
        		Intent w = new Intent(Chapter.this,WritingPrompt.class);
        		Bundle bundle2 = new Bundle();
        		bundle2.putIntegerArrayList("array2", chaps); //same distinction made for the same purpose as stated above
        		w.putExtras(bundle2);
        		startActivity(w);
        		break;
        		
        	}
        }
     }

}
