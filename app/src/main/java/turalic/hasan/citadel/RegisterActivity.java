package turalic.hasan.citadel;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class RegisterActivity extends AppCompatActivity {

    Button registerBtn;
    EditText registerMail;
    EditText registerPw;
    EditText registerName;
    TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register);

        registerBtn = findViewById(R.id.registerBtn);
        registerMail = findViewById(R.id.registerMail);
        registerPw = findViewById(R.id.registerPw);
        registerName = findViewById(R.id.registerName);
        registerText = findViewById(R.id.registerTxt);

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendRegister sendRegister = new SendRegister();
                sendRegister.execute();
            }
        });
    }

    private JSONObject registerObject(){
        JSONObject registerObject = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Name", registerName.getText().toString());
            jsonObject.put("Mail", registerMail.getText().toString());
            jsonObject.put("Password", registerPw.getText().toString());
            registerObject.put("Register", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return registerObject;
    }

    class SendRegister extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url = new URL("http://URL:PORT/register");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                urlConnection.setUseCaches(false);
                urlConnection.setDoOutput(true);

                DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());

                os.writeBytes(registerObject().toString());
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
