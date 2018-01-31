package cz.vutbr.fit.brnogo.ui.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cz.vutbr.fit.brnogo.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		startActivity(MainActivity.getStartIntent(this));
		finish();
	}
}
