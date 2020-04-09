package ro.pub.cs.systems.eim.Colocviu1_245;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.ConfigurationStats;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Colocviu1_245MainActivity extends AppCompatActivity {

    Button addButton, computeButton;
    TextView nextTerm, allTerms;

    boolean notServiced = true;
    boolean broughtFromMemory = false;
    int result = 0;
    String allTermsSaved = "";
    Colocviu1_245Service service;
    Intent serviceIntent;

    protected void printResult(boolean fromMemory) {
        if (fromMemory) {
            Toast.makeText(this, "Saved computed value is: " + result, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Computed value by second activity is: " + result, Toast.LENGTH_LONG).show();
        }
    }

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

        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (broughtFromMemory && allTerms.getText().toString().equals(allTermsSaved)) {
                    printResult(true);
                } else {
                    Intent intent = new Intent(getApplicationContext(), Colocviu1_245SecondaryActivity.class);
                    intent.putExtra(Constants.COMPUTE_ME, allTerms.getText().toString());
                    startActivityForResult(intent, Constants.SECONDARY_REQ_CODE);
                }
                if (result > 10 && notServiced) {
                    notServiced = false;
                    serviceIntent = new Intent();
                    serviceIntent.setComponent(new ComponentName("ro.pub.cs.systems.eim.Colocviu1_245", "ro.pub.cs.systems.eim.Colocviu1_245.Colocviu1_245Service"));
                    serviceIntent.putExtra(Constants.TO_SERVICE, result);
                    startService(serviceIntent);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.SECONDARY_REQ_CODE) {
            result = resultCode;
            printResult(false);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceBundle) {
        super.onSaveInstanceState(savedInstanceBundle);
        savedInstanceBundle.putInt(Constants.COMPUTED_VALUE, result);
        savedInstanceBundle.putString(Constants.SAVED_TERMS, allTerms.getText().toString());
    }

    @Override
    protected  void onRestoreInstanceState(Bundle savedInstanceBundle) {
        super.onRestoreInstanceState(savedInstanceBundle);
        if (savedInstanceBundle.containsKey(Constants.COMPUTED_VALUE)) {
            broughtFromMemory = true;
            result = savedInstanceBundle.getInt(Constants.COMPUTED_VALUE);
            allTermsSaved = savedInstanceBundle.getString(Constants.SAVED_TERMS);
        }
    }

    protected  void onDestroy() {
        super.onDestroy();
        if (!notServiced)
            stopService(serviceIntent);
    }
}
