package turalic.hasan.citadel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

@SuppressLint("ValidFragment")
public class Quiz3Fragment extends Fragment {

    String answer1;
    String answer2;
    String answer3;

    Button button1;
    Button button2;
    Button button3;

    public Quiz3Fragment(String answer1, String answer2, String answer3){
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        View retView = inflater.inflate(R.layout.fragment_quiz3, container, false);


        button1 = retView.findViewById(R.id.quiz3btn1);
        button1.setText(answer1);

        button2 = retView.findViewById(R.id.quiz3btn2);
        button2.setText(answer2);

        button3 = retView.findViewById(R.id.quiz3btn3);
        button3.setText(answer2);

        ((QuizActivity)getActivity()).setText1("xD");

        return retView;
    }
}
