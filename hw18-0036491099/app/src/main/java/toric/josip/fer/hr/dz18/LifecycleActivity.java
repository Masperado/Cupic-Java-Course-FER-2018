package toric.josip.fer.hr.dz18;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

/**
 * This class represents LifecycleActivity. It is used as a main activity of our application.
 */
public class LifecycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);

        Button compose = findViewById(R.id.btn_compose);

        compose.setOnClickListener(view -> {
            Intent intent = new Intent(LifecycleActivity.this, ComposeMailActivity.class);
            startActivity(intent);
        });
    }
}
