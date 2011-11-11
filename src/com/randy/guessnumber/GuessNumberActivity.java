package com.randy.guessnumber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GuessNumberActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button startButton = (Button) findViewById(R.id.button1);
        startButton.setOnClickListener(
        	new OnClickListener(){
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(GuessNumberActivity.this, PlayActivity.class);
					startActivity(intent);
//					GuessNumberActivity.this.finish();
				}
        	}
        );
        	
    }
}