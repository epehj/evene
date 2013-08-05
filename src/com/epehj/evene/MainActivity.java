package com.epehj.evene;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final Button getCitationsButton = (Button) findViewById(R.id.button1);
		getCitationsButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(final View v) {
		Toast.makeText(MainActivity.this, "Téléchargements des citations", Toast.LENGTH_SHORT).show();
		final Intent intent = new Intent(MainActivity.this, ListeCitationsClean.class);
		startActivity(intent);
	}

}
