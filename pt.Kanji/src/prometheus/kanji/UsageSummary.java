/**UsageSummary class
 * @author Ivana Marincic
 * @version 1, finalized 12/15/2012*/
package prometheus.kanji;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class UsageSummary extends Activity{
	
	private TextView score;
  
	private DatabaseMain db = new DatabaseMain(this);
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usage);
        
        score = (TextView) findViewById(R.id.textView2);
        
        int total = db.getCorrect();
        
        score.setText(Integer.toString(total));

}
}
