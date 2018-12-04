package turalic.hasan.citadel;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private int currentQuestion = 1;
    private ArrayList<Integer> answers;
    private Quiz3Fragment quiz3Fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        answers = new ArrayList<Integer>();

        fragmentManager = this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        quiz3Fragment = new Quiz3Fragment("1", "2", "3");

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
        fragmentTransaction.replace(R.id.dynamic_fragment_frame_layout, new Quiz3Fragment("4", "5", "6"));
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}
