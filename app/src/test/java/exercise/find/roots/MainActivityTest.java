package exercise.find.roots;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class MainActivityTest extends TestCase {

  @Test
  public void when_activityIsLaunching_then_theButtonShouldStartDisabled(){
    // create a MainActivity and let it think it's currently displayed on the screen
    MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();

    // test: make sure that the "calculate" button is disabled
    Button button = mainActivity.findViewById(R.id.buttonCalculateRoots);
    assertFalse(button.isEnabled());
  }

  @Test
  public void when_activityIsLaunching_then_theEditTextShouldStartEmpty(){
    // create a MainActivity and let it think it's currently displayed on the screen
    MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();

    // test: make sure that the "input" edit-text has no text
    EditText inputEditText = mainActivity.findViewById(R.id.editTextInputNumber);
    String input = inputEditText.getText().toString();
    assertTrue(input == null || input.isEmpty());
  }

  @Test
  public void when_userIsEnteringNumberInput_and_noCalculationAlreadyHappned_then_theButtonShouldBeEnabled(){
    // create a MainActivity and let it think it's currently displayed on the screen
    MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();

    // find the edit-text and the button
    EditText inputEditText = mainActivity.findViewById(R.id.editTextInputNumber);
    Button button = mainActivity.findViewById(R.id.buttonCalculateRoots);

    // test: insert input to the edit text and verify that the button is enabled
    inputEditText.setText("1");
    assertTrue(button.isEnabled());
  }

  @Test
  public void when_activityIsLaunching_then_theProgressBarStartHidden(){
    // create a MainActivity and let it think it's currently displayed on the screen
    MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();

    // test: make sure that the "progress" bar is hidden
    ProgressBar progressBar = mainActivity.findViewById(R.id.progressBar);
    assertTrue(progressBar.getVisibility() == View.GONE);
  }

  @Test
  public void when_userIsEnteringAGoodNumberAndClickingButton_theProgressBarShouldBeDisplayed(){
    // create a MainActivity and let it think it's currently displayed on the screen
    MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();

    // find the edit-text, the button and the progress bar
    EditText inputEditText = mainActivity.findViewById(R.id.editTextInputNumber);
    Button button = mainActivity.findViewById(R.id.buttonCalculateRoots);
    ProgressBar progressBar = mainActivity.findViewById(R.id.progressBar);

    // test: insert a good input to the edit text, click the button and verify that the progress bar should be displayed
    inputEditText.setText("187");
    button.performClick();
    assertTrue(progressBar.isEnabled());
  }

  @Test
  public void when_userIsEnteringABadNumberAndClickingButton_theButtonShouldBeDisabled(){
    // create a MainActivity and let it think it's currently displayed on the screen
    MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();

    // find the edit-text and the button
    EditText inputEditText = mainActivity.findViewById(R.id.editTextInputNumber);
    Button button = mainActivity.findViewById(R.id.buttonCalculateRoots);
    ProgressBar progressBar = mainActivity.findViewById(R.id.progressBar);

    // test: insert a good input to the edit text, click the button and verify that the progress bar should be displayed
    inputEditText.setText("18.7");
    assertFalse(button.isEnabled());
  }

  @Test
  public void when_userIsEnteringNumberAndThenDeleteIt_theButtonShouldBeEnabledAndThenDisabled(){
    // create a MainActivity and let it think it's currently displayed on the screen
    MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();

    // find the edit-text and the button
    EditText inputEditText = mainActivity.findViewById(R.id.editTextInputNumber);
    Button button = mainActivity.findViewById(R.id.buttonCalculateRoots);
    ProgressBar progressBar = mainActivity.findViewById(R.id.progressBar);

    // test: insert a good input to the edit text, click the button and verify that the progress bar should be displayed
    assertFalse(button.isEnabled());
    inputEditText.setText("1");
    assertTrue(button.isEnabled());
    inputEditText.setText("");
    assertFalse(button.isEnabled());
  }
}