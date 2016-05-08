package pl.marchuck.myagh.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * @author Lukasz Marczak
 * @since 08.05.16.
 */
public class EmailSender {

    public static void fire(Activity activity) {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "lukmardev@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "myAgh app");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, Lukasz");
        activity.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}
