package prometheus.kanji;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class ListChapterContent extends ListActivity{
	
    static final ArrayList<HashMap<String,String>> list = 
		new ArrayList<HashMap<String,String>>();
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_chapters);
        SimpleAdapter adapter = new SimpleAdapter(
            	this,list,R.layout.crowview,
            	new String[] {"chapter","character","description"},
            	new int[] {R.id.text1,R.id.text2, R.id.text3}
            );     
            populateList();
            setListAdapter(adapter);    
	}

	private void populateList() {
		HashMap<String,String> map = new HashMap<String,String>();
    	map.put("chapter",
    				" 1. Japanese Geography ");
    	//1.1
        map.put("character", " 地理");
        map.put("description", " - ちり; chiri; geography");
        list.add(map);
       
        //1.2 
        map = new HashMap<String,String>();
        map.put("character", " 皆さん");
        map.put("description", " - みなさん; minasann; everyone");
        list.add(map);
       
        //1.3
        map = new HashMap<String,String>();
        map.put("character", " 大陸");
        map.put("description", " - たいりく; tairiku; continent");
        list.add(map);
       
        //1.4
        map = new HashMap<String,String>();
        map.put("character", " 都市");
        map.put("description", " - とし; tosi; city");
        list.add(map);
       
        //1.5
        map = new HashMap<String,String>();
        map.put("character", " 全体");
        map.put("description", " - ぜんたい; zenntai; entire");
        list.add(map);
        
        map=new HashMap<String,String>();
        map.put("character", "...");
        list.add(map);
       
        //2.1
        map = new HashMap<String,String>();
        map.put("chapter", " 2. Language Style ");
        map.put("character", " 敬語 ");
        map.put("description", " - けいご;	keigo; honorific language");
        list.add(map);
        //2.2
        map = new HashMap<String,String>();
        map.put("character", " 実は");
        map.put("description", " - じつわ; jituwa; in fact");
        list.add(map);
       
        //2.3
        map = new HashMap<String,String>();
        map.put("character", " 複雑");
        map.put("description", " - ふくざつ; fukuzatu; complicated");
        list.add(map);
       
        //2.4
        map = new HashMap<String,String>();
        map.put("character", " 課");
        map.put("description", " - か; ka; lesson");
        list.add(map);
       
        //2.5
        map = new HashMap<String,String>();
        map.put("character", " 言語");
        map.put("description", " - げんご; genngo; language");
        list.add(map);
        
        map=new HashMap<String,String>();
        map.put("character", "...");
        list.add(map);
       
        //3.1
        map = new HashMap<String,String>();
        map.put("chapter", " 3. Japanese Technology ");
        map.put("character", " 技術");
        map.put("description", " - ぎじゅつ; gijyutu; tequnology");
        list.add(map);
       
        //3.2
        map = new HashMap<String,String>();
        map.put("character", " 発達");
        map.put("description", " - はったつ; hattatu; development");
        list.add(map);
       
        //3.3
        map = new HashMap<String,String>();
        map.put("character", " 会場");
        map.put("description", " - かいじょう; kaijyou; event site");
        list.add(map);
       
        //3.4
        map = new HashMap<String,String>();
        map.put("character", " 描く");
        map.put("description", " - かく; kaku; paint");
        list.add(map);
       
        //3.5
        map = new HashMap<String,String>();
        map.put("character", " 手術");
        map.put("description", " - しゅじゅつ	; syujyutu; surgery");
        list.add(map);
        
        map=new HashMap<String,String>();
        map.put("character", "...");
        list.add(map);
       
        //4.1
        map = new HashMap<String,String>();
        map.put("chapter", " 4. Japanese Sports ");
        map.put("character", " 合格 ");
        map.put("description", " - ごうかく; goukaku; pass");
        list.add(map);
        //4.2
        map = new HashMap<String,String>();
        map.put("character", " お祝い");
        map.put("description", " - おいわい; oiwai; congratulatory gift");
        list.add(map);
       
        //4.3
        map = new HashMap<String,String>();
        map.put("character", " 学ぶ");
        map.put("description", " - まなぶ; manabu; to learn");
        list.add(map);
       
        //4.4
        map = new HashMap<String,String>();
        map.put("character", " 現代");
        map.put("description", " - げんだい; genndai; contemporary");
        list.add(map);
       
        //4.5
        map = new HashMap<String,String>();
        map.put("character", " 健康");
        map.put("description", " - けんこう; kennkou; health");
        list.add(map);
        
        map=new HashMap<String,String>();
        map.put("character", "...");
        list.add(map);
       
        //5.1
        map = new HashMap<String,String>();
        map.put("chapter", " 5. Japanese Food ");
        map.put("character", " 発明 ");
        map.put("description", " - はつめい; hatumei; invention");
        list.add(map);
        //5.2
        map = new HashMap<String,String>();
        map.put("character", " 物語");
        map.put("description", " - もにがたり; monogatari; story, tale");
        list.add(map);
        //5.3
        map = new HashMap<String,String>();
        map.put("character", " 全");
        map.put("description", " - ぜん; zenn; all, entire");
        list.add(map);
       
        //5.4
        map = new HashMap<String,String>();
        map.put("character", " 量");
        map.put("description", " - りょう; ryou; quantity");
        list.add(map);
       
        //5.5
        map = new HashMap<String,String>();
        map.put("character", " 値段");
        map.put("description", " - ねだん; nedann; price");
        list.add(map);
       
        map=new HashMap<String,String>();
        map.put("character", "...");
        list.add(map);
        
        //6.1
        map = new HashMap<String,String>();
        map.put("chapter", " 6. Religions ");
        map.put("character", " 宗教");
        map.put("description", " - しゅうきょう; syuukyou; religion");
        list.add(map);
       
        //6.2
        map = new HashMap<String,String>();
        map.put("character", " 苦しい");
        map.put("description", " - くるしい; kurusii; hard");
        list.add(map);
       
        //6.3
        map = new HashMap<String,String>();
        map.put("character", " 仏様");
        map.put("description", " - ことけさま	; kotokesama; Buddha");
        list.add(map);
       
        //6.4
        map = new HashMap<String,String>();
        map.put("character", " 両方");
        map.put("description", " - りょうほう; ryouhou; both");
        list.add(map);
       
        //6.5
        map = new HashMap<String,String>();
        map.put("character", " 神道");
        map.put("description", " - しんとう; sinntou; shintoism");
        list.add(map);
        
        map=new HashMap<String,String>();
        map.put("character", "...");
        list.add(map);
       
        //7.1
        map = new HashMap<String,String>();
        map.put("chapter", " 7. Pop Culture ");
        map.put("character", " 様々 ");
        map.put("description", " - さまざま; samazama; various");
        list.add(map);
       
        //7.2
        map = new HashMap<String,String>();
        map.put("character", " 経済");
        map.put("description", " - けいざい; keizai; economy");
        list.add(map);
       
        //7.3
        map = new HashMap<String,String>();
        map.put("character", " 影響");
        map.put("description", " - えいきょう; eikyou; influence");
        list.add(map);
       
        //7.4
        map = new HashMap<String,String>();
        map.put("character", " 元");
        map.put("description", " - もと; moto; origin");
        list.add(map);
       
        //7.5
        map = new HashMap<String,String>();
        map.put("character", " 翻訳");
        map.put("description", " - ほんやく; honnyaku; written translation");
        list.add(map);
        
        map=new HashMap<String,String>();
        map.put("character", "...");
        list.add(map);
       
        //8.1
        map = new HashMap<String,String>();
        map.put("chapter", " 8. Traditional Arts ");
        map.put("character", " 科学 ");
        map.put("description", " - かがく; kagaku; science");
        list.add(map);
        //8.2
        map = new HashMap<String,String>();
        map.put("character", " 証明");
        map.put("description", " - しょうめい	syoumei	proof");
        list.add(map);
       
        //8.3
        map = new HashMap<String,String>();
        map.put("character", " 患者");
        map.put("description", " - かんじゃ; kannjya; patient");
        list.add(map);
       
        //8.4
        map = new HashMap<String,String>();
        map.put("character", " 講義");
        map.put("description", " - こうぎ; kougi; lecture");
        list.add(map);
       
        //8.5
        map = new HashMap<String,String>();
        map.put("character", " 実験");
        map.put("description", " - じっけん; jikkenn; experiment");
        list.add(map);
        
        map=new HashMap<String,String>();
        map.put("character", "...");
        list.add(map);
	}

}
