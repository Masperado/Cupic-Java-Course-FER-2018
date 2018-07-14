package toric.josip.fer.hr.dz18;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents compose mail activity. It is used for sending mails.
 */
public class ComposeMailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_mail);

        EditText sendTo = findViewById(R.id.sendTo);
        EditText title = findViewById(R.id.title);
        EditText messsage = findViewById(R.id.message);
        Button send = findViewById(R.id.btn_send);

        send.setOnClickListener(view -> {
            String sendToText = sendTo.getText().toString();
            if (!checkEmail(sendToText)) {
                Toast.makeText(this, R.string.invalidEmail, Toast.LENGTH_LONG).show();
                return;
            }
            String titleText = title.getText().toString();
            if (titleText.length() == 0) {
                Toast.makeText(this, R.string.invalidTitle, Toast.LENGTH_LONG).show();
                return;
            }
            String messageText = messsage.getText().toString();
            if (messageText.length() == 0) {
                Toast.makeText(this, R.string.invalidMessage, Toast.LENGTH_LONG).show();
                return;
            }

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{sendToText});
            intent.putExtra(Intent.EXTRA_CC, new String[]{"ana@baotic.org","marcupic@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, titleText);
            intent.putExtra(Intent.EXTRA_TEXT, messageText);
            startActivity(intent);
            finish();

        });


    }

    /**
     * This method is used if given String represents valid email.
     *
     * @param email String to be checked
     * @return True if does, false otherwise
     */
    private boolean checkEmail(String email) {
        String pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

}
