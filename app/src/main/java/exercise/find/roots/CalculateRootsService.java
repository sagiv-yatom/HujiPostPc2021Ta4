package exercise.find.roots;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class CalculateRootsService extends IntentService {


  public CalculateRootsService() {
    super("CalculateRootsService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    if (intent == null) return;
    long timeStartMs = System.currentTimeMillis();
    long numberToCalculateRootsFor = intent.getLongExtra("number_for_service", 0);
    if (numberToCalculateRootsFor <= 0) {
      Log.e("CalculateRootsService", "can't calculate roots for non-positive input" + numberToCalculateRootsFor);
      return;
    }

    long endTime = timeStartMs + 20000;
    long i = 2;
    boolean isRootsFound = false;
    Intent newIntent = new Intent();

    while (System.currentTimeMillis() < endTime) {
      if (numberToCalculateRootsFor % i == 0) {
        isRootsFound = true;
        break;
      }
      i += 1;
    }

    if (isRootsFound) {
      newIntent.setAction("found_roots");
      newIntent.putExtra("original_number", numberToCalculateRootsFor);
      newIntent.putExtra("root1", i);
      newIntent.putExtra("root2", numberToCalculateRootsFor / i);
      newIntent.putExtra("time_until_calculating", System.currentTimeMillis() - timeStartMs);
    }
    else {
      newIntent.setAction("stopped_calculations");
      newIntent.putExtra("original_number", numberToCalculateRootsFor);
      newIntent.putExtra("time_until_give_up_seconds", (System.currentTimeMillis() - timeStartMs)/1000);
    }

    sendBroadcast(newIntent);
  }
}