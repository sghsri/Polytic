package io.github.sghsri.representativeapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private Toolbar myToolbar;
    ArrayList<Representative> reps;
    List<CandidateInfo> mCandidateInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reps = new ArrayList<>();
        CivicTask task = new CivicTask();
        task.execute();


    }


    private class CivicTask extends AsyncTask<Void, Void, String>{
        String currentcivil;

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL currentlink = new URL("https://www.googleapis.com/civicinfo/v2/representatives?address=17531 Bending%20Post%20Drive%2077095&levels=country&key="+getResources().getString(R.string.api_key));
                HttpsURLConnection civilinfo = (HttpsURLConnection) currentlink.openConnection();
                civilinfo.connect();
                if (civilinfo.getResponseCode() == 200) {
                    //if connection had no problem
                    Log.i("CIVIL_TASK", "Success");
                    //reading in current civil_info
                    StringBuffer buffer = new StringBuffer();
                    InputStream is = civilinfo.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = br.readLine()) != null)
                        buffer.append(line + "\n");
                    is.close();
                    civilinfo.disconnect();
                    currentcivil = buffer.toString();
                    Log.d("CIVIL_OUTPUT", currentcivil);

                    mCandidateInfos = Scrapper.getCandidateInfo();
                    return "Success";
                } else {
                    return "Fail";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception";

            }
        }

        private String getOffice(JSONObject civildata, int pos){
            try {
                JSONArray offices = civildata.getJSONArray("offices");
                for(int i = 0; i< offices.length();i++){
                    JSONObject off = offices.getJSONObject(i);
                    JSONArray indices = off.getJSONArray("officialIndices");
                    for(int j = 0; j<indices.length();j++){
                        if((int)indices.get(j) == pos){
                            return (String)off.get("name");
                        }
                    }
                }
            }catch (Exception e){
             e.printStackTrace();
            }
            return"error";
        }
        private String getAddress(JSONObject jsonObject){
            String val = "";
            try {
                val = jsonObject.getString("line1") + "\n"+jsonObject.getString("city")+","+jsonObject.getString("state")+jsonObject.getString("zip");
            }catch (Exception e){
                e.printStackTrace();
            }
            return val;
        }
        private ArrayList<Representative.SocialMedia> getSocMedia(JSONArray arr){
            ArrayList<Representative.SocialMedia> sm = new ArrayList<>();
            for(int i = 0; i<arr.length();i++){
                try{
                String type = ((JSONObject)arr.get(i)).getString("type");
                String id = ((JSONObject)arr.get(i)).getString("id");
                sm.add(new Representative.SocialMedia(type,id));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return sm;
        }

        @Override
        protected void onPostExecute(String s) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            switch (s) {
                case "Success":
                    intent.putExtra("connection", "Success");
                    try {
                        JSONObject civildata = new JSONObject(currentcivil);
                        JSONArray jsonArray = civildata.getJSONArray("officials");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonobject = jsonArray.getJSONObject(i);
                            String name = jsonobject.getString("name");
                            String office = getOffice(civildata,i);
                            String party = jsonobject.getString("party");
                            String phone = (String)jsonobject.getJSONArray("phones").get(0);
                            String url = (String)jsonobject.getJSONArray("urls").get(0);
                            String photoUrl = "default";
                            if(jsonobject.has("photoUrl")) {
                                photoUrl = jsonobject.getString("photoUrl");
                            }
                            String address = getAddress(jsonobject.getJSONArray("address").getJSONObject(0));
                            Representative rep = new Representative(name,office,party,phone,url,photoUrl,address,getSocMedia(jsonobject.getJSONArray("channels")));
                            reps.add(rep);
                        }


                        ListView lv = findViewById(R.id.representativelist);
                        lv.setAdapter(new RepAdapter(getApplicationContext(),reps));
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                CandidateInfo c = null;
                                for (CandidateInfo ci : mCandidateInfos) {
                                    if (ci.getName().equals(reps.get(i).getName())) c = ci;
                                }

                                Intent intent = new Intent(getApplicationContext(),RepresentativeInfo.class);
                                intent.putExtra("repinfo",reps.get(i));
                                intent.putExtra("repactivities", c);
                                startActivity(intent);
                            }
                        });
                        break;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Fail":
                    Log.e("CURRENT_WEATHER_TASK", "FAIL CONNECTION");
                    Toast.makeText(getApplicationContext(), "There was an error getting weather information, please retry later.", Toast.LENGTH_SHORT).show();
                    intent.putExtra("connection", "Error");
                    break;
                case "Exception":
                    Log.e("CURRENT_WEATHER_TASK", "EXCEPTION");
                    Toast.makeText(getApplicationContext(), "You're offline. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    intent.putExtra("connection", "Error");
                    break;
            }
//            startActivity(intent);
//            finish();
        }
    }
}
