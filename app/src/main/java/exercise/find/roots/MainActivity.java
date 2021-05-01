package exercise.find.roots;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  private BroadcastReceiver broadcastReceiverForSuccess = null;
  private BroadcastReceiver broadcastReceiverForAbort = null;

  public static boolean isLong(String str) {
    try {
      Long.parseLong(str);
      return true;
    } catch(NumberFormatException e){
      return false;
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ProgressBar progressBar = findViewById(R.id.progressBar);
    EditText editTextUserInput = findViewById(R.id.editTextInputNumber);
    Button buttonCalculateRoots = findViewById(R.id.buttonCalculateRoots);

    // set initial UI:
    progressBar.setVisibility(View.GONE); // hide progress
    editTextUserInput.setText(""); // cleanup text in edit-text
    editTextUserInput.setEnabled(true); // set edit-text as enabled (user can input text)
    buttonCalculateRoots.setEnabled(false); // set button as disabled (user can't click)

    // set listener on the input written by the keyboard to the edit-text
    editTextUserInput.addTextChangedListener(new TextWatcher() {
      public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
      public void onTextChanged(CharSequence s, int start, int before, int count) { }
      public void afterTextChanged(Editable s) {
        // text did change
        String newText = editTextUserInput.getText().toString();
        buttonCalculateRoots.setEnabled(isLong(newText));
      }
    });

    // set click-listener to the button
    buttonCalculateRoots.setOnClickListener(v -> {
      Intent intentToOpenService = new Intent(MainActivity.this, CalculateRootsService.class);
      String userInputString = editTextUserInput.getText().toString();

      if (isLong(userInputString)) {
        buttonCalculateRoots.setEnabled(true);
        long userInputLong = Long.parseLong(userInputString);
        intentToOpenService.putExtra("number_for_service", userInputLong);
        startService(intentToOpenService);
      } else {
        buttonCalculateRoots.setEnabled(false);
      }

      progressBar.setVisibility(View.VISIBLE);
      editTextUserInput.setEnabled(false);
      buttonCalculateRoots.setEnabled(false);
    });

    // register a broadcast-receiver to handle action "found_roots"
    broadcastReceiverForSuccess = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent incomingIntent) {
        if (incomingIntent == null || !incomingIntent.getAction().equals("found_roots")) return;
        // success finding roots!

        progressBar.setVisibility(View.GONE);
        editTextUserInput.setEnabled(true);
        buttonCalculateRoots.setEnabled(true);
        long number = incomingIntent.getLongExtra("original_number", 0);
        long root1 = incomingIntent.getLongExtra("root1", 0);
        long root2 = incomingIntent.getLongExtra("root2", 0);
        long time = incomingIntent.getLongExtra("time_until_calculating", 0);
        Intent intentToOpenSuccessActivity = new Intent(MainActivity.this, SuccessActivity.class);
        intentToOpenSuccessActivity.putExtra("original_number", number);
        intentToOpenSuccessActivity.putExtra("root1", root1);
        intentToOpenSuccessActivity.putExtra("root2", root2);
        intentToOpenSuccessActivity.putExtra("time", time);
        startActivity(intentToOpenSuccessActivity);
      }
    };
    registerReceiver(broadcastReceiverForSuccess, new IntentFilter("found_roots"));

    broadcastReceiverForAbort = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent incomingIntent) {
        if (incomingIntent == null || !incomingIntent.getAction().equals("stopped_calculations")) return;

        progressBar.setVisibility(View.GONE);
        editTextUserInput.setEnabled(true);
        buttonCalculateRoots.setEnabled(true);
        String calcTime = Long.toString(incomingIntent.getLongExtra("time_until_give_up_seconds", 0));
        Toast.makeText(context, "calculation aborted after " + calcTime + " seconds", Toast.LENGTH_SHORT).show();
      }
    };
    registerReceiver(broadcastReceiverForAbort, new IntentFilter("stopped_calculations"));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    this.unregisterReceiver(broadcastReceiverForSuccess);
    this.unregisterReceiver(broadcastReceiverForAbort);
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);

    ProgressBar progressBar = findViewById(R.id.progressBar);
    EditText editTextUserInput = findViewById(R.id.editTextInputNumber);
    Button buttonCalculateRoots = findViewById(R.id.buttonCalculateRoots);

    boolean progressBarVisibility = false;
    boolean buttonCalculateRootsEnabled = false;
    boolean editTextEnabled = false;

    String userInputText = editTextUserInput.getText().toString();
    if (progressBar.getVisibility() == View.VISIBLE) {
      progressBarVisibility = true;
    }
    if (buttonCalculateRoots.isEnabled()) {
      buttonCalculateRootsEnabled = true;
    }
    if (editTextUserInput.isEnabled()) {
      editTextEnabled = true;
    }

    outState.putBoolean("progress bar visibility", progressBarVisibility);
    outState.putBoolean("button enabled", buttonCalculateRootsEnabled);
    outState.putBoolean("edit text enabled", editTextEnabled);
    outState.putString("input text", userInputText);
  }

  @Override
  protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);

    String userInputText = savedInstanceState.getString("input text");
    boolean progressBarVisibility = savedInstanceState.getBoolean("progress bar visibility");
    boolean editTextEnabled = savedInstanceState.getBoolean("edit text enabled");
    boolean buttonCalculateRootsEnabled = savedInstanceState.getBoolean("button enabled");

    ProgressBar progressBar = findViewById(R.id.progressBar);
    EditText editTextUserInput = findViewById(R.id.editTextInputNumber);
    Button buttonCalculateRoots = findViewById(R.id.buttonCalculateRoots);

    editTextUserInput.setText(userInputText);

    if (progressBarVisibility) {
      progressBar.setVisibility(View.VISIBLE);
    } else {
      progressBar.setVisibility(View.GONE);
    }

    editTextUserInput.setEnabled(editTextEnabled);

    buttonCalculateRoots.setEnabled(buttonCalculateRootsEnabled);
  }
}