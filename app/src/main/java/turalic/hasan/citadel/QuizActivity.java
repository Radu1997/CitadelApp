package turalic.hasan.citadel;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    Quiz3Fragment quiz3Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        quiz3Fragment = new Quiz3Fragment("1", "2", "3");

        fragmentTransaction.replace(R.id.dynamic_fragment_frame_layout, quiz3Fragment);
        fragmentTransaction.commit();
    }

    public void setText1(String x){
        TextView textView = findViewById(R.id.textView);
        textView.setText(x);
    }
}
