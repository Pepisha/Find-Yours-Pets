package company.pepisha.find_yours_pets;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.news.News;
import company.pepisha.find_yours_pets.db.opinion.Opinion;

public class AnimalNewsActivity extends BaseActivity {

    private ListView newsListView;

    private class GetAnimalsNewsDbOperation extends ServerDbOperation {
        public GetAnimalsNewsDbOperation(Context c) {
            super(c, "getNewsFromAnimal");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
           addNews(result);
        }
    }

    private void addNews(HashMap<String, Object> news) {
        List<News> newsList = new ArrayList<>();

        for (Map.Entry<String, Object> entry : news.entrySet()) {
            News n = new News((JSONObject) entry.getValue());
            newsList.add(n);
        }

        ArrayAdapter<News> listAdapter = new ArrayAdapter<News>(this, android.R.layout.simple_list_item_1, newsList);
        newsListView.setAdapter(listAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_news);
        newsListView = (ListView) findViewById(R.id.newsList);

        int idAnimal = getIntent().getIntExtra("idAnimal",1);

        HashMap<String, String> request = new HashMap<String, String>();
        request.put("idAnimal", Integer.toString(idAnimal));
        new GetAnimalsNewsDbOperation(this).execute(request);
    }

}
