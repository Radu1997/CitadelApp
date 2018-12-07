package turalic.hasan.citadel;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";

    private int currentQuestion = 1;
    private ArrayList<Integer> answers;
    private Quiz3Fragment quiz3Fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_quiz);

        answers = new ArrayList<Integer>();

        fragmentManager = this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        quiz3Fragment = new Quiz3Fragment("0","1", "2", "3");

        fragmentTransaction.replace(R.id.dynamic_fragment_frame_layout, quiz3Fragment);
        fragmentTransaction.commit();
    }

    public void setText1(String x){
        TextView textView = findViewById(R.id.textView);
        textView.setText(x);
    }

    public void nextQuestion(int answer){
        answers.add(answer);
        currentQuestion++;

        fragmentTransaction = fragmentManager.beginTransaction();
        String[] qAndA;

        switch (currentQuestion){
            case 2:
                qAndA = getResources().getStringArray(R.array.question2);
                fragmentTransaction.replace(R.id.dynamic_fragment_frame_layout,
                        new Quiz3Fragment(qAndA[0], qAndA[1], qAndA[2], qAndA[3]));
                break;
            case 3:
                qAndA = getResources().getStringArray(R.array.question3);
                fragmentTransaction.replace(R.id.dynamic_fragment_frame_layout,
                        new Quiz3Fragment(qAndA[0], qAndA[1], qAndA[2], qAndA[3]));
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
        }

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    private JSONObject answers(){
        JSONObject results = new JSONObject();
        JSONArray jsonArray = new JSONArray(answers);
        try {
            results.put("Answers", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }

    class SendResult extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url = new URL("http://10.181.103.96:8080/login");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                urlConnection.setUseCaches(false);
                urlConnection.setDoOutput(true);

                DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());

                os.writeBytes(answers().toString());
                os.close();

                //Get Response
                InputStream is = null;
                try {
                    is = urlConnection.getInputStream();
                } catch (IOException ioe) {
                    if (urlConnection instanceof HttpURLConnection) {
                        HttpURLConnection httpConn = (HttpURLConnection) urlConnection;
                        int statusCode = httpConn.getResponseCode();
                        if (statusCode != 200) {
                            is = httpConn.getErrorStream();
                        }
                    }
                }

                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                Log.d(TAG, response.toString());
                Log.d(TAG, response.toString());
                rd.close();
                return response.toString();

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //TODO: If Accepted
            //TODO: If Denied
        }
    }
}
