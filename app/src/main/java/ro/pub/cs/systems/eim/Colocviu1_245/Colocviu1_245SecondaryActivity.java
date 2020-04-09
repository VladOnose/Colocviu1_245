package ro.pub.cs.systems.eim.Colocviu1_245;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Colocviu1_245SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int returnedValue = -1;
        if (intent != null && intent.getExtras().containsKey(Constants.COMPUTE_ME)) {
            returnedValue = 0;
            String toCompute = intent.getStringExtra(Constants.COMPUTE_ME);
            String[] splits = toCompute.split(" ");
            boolean expectNumber = true;
            boolean ok = true;
            for (int i = 0; i < splits.length; ++i) {
                if (splits[i].equals(Constants.ADD_SIGN)) {
                    if (expectNumber) {
                        returnedValue = -1;
                        break;
                    }
                    expectNumber = true;
                } else {
                    if (!expectNumber) {
                        returnedValue = -1;
                        break;
                    }
                    int currentValue = Integer.parseInt(splits[i]);
                    returnedValue += currentValue;
                    expectNumber = false;
                }
            }
        }
        setResult(returnedValue);
        finish();
    }
}
