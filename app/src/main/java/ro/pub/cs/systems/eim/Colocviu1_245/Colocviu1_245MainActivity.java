package ro.pub.cs.systems.eim.Colocviu1_245;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Colocviu1_245MainActivity extends AppCompatActivity {

    Button addButton, computeButton;
    TextView nextTerm, allTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_245_main);

        addButton = findViewById(R.id.Add);
        computeButton = findViewById(R.id.Compute);

        nextTerm = findViewById(R.id.NextTerm);
        allTerms = findViewById(R.id.AllTerms);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nextTerm.getText().toString().isEmpty()) {
                    if (allTerms.getText().toString().isEmpty()) {
                        allTerms.setText(nextTerm.getText());
                    } else {
                        allTerms.append(" + " + nextTerm.getText().toString());
                    }
                    nextTerm.setText("");
                }
            }
        });
    }
}
