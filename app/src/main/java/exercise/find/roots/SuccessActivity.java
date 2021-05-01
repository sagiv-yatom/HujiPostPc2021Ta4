package exercise.find.roots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        TextView originalNumber = findViewById(R.id.originalNumber);
        TextView calculation = findViewById(R.id.calculation);
        TextView calcTime = findViewById(R.id.calcTime);

        Intent intent = getIntent();
        String number = Long.toString(intent.getLongExtra("original_number", 0));
        String root1 = Long.toString(intent.getLongExtra("root1", 0));
        String root2 = Long.toString(intent.getLongExtra("root2", 0));
        String time = Long.toString(intent.getLongExtra("time", 0));

        originalNumber.setText(number);
        calculation.setText(number + " = " + root1 + " * " + root2);
        calcTime.setText("Calculated in " + time + " seconds");
    }
}