package taxi2share.eu;

import taxi2share.eu.biz.Function;

import taxi2share.eu.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;

public class Validate_code extends Activity {

	String code;
	String email;
	Function function;
	EditText txt_code_confirmatio;
	Button validate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_validate_code);

		txt_code_confirmatio = ((EditText) findViewById(R.id.code));
		validate = ((Button) findViewById(R.id.validate));
		Bundle localBundle = getIntent().getExtras();
		code = localBundle.getString("code");
		email = localBundle.getString("email");
		function = new Function();

		/**
		 * to validate the entered code with the code which is sent via mail to
		 * the client id
		 */

		validate.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				String str = txt_code_confirmatio.getText().toString();
				if (Validate_code.this.function.verif_chaine(str, 0)) {
					if (Validate_code.this.code.compareTo(str) == 0) {
						Intent localIntent = new Intent(Validate_code.this,
								Reinitialiser_pass.class);
						localIntent.putExtra("email", Validate_code.this.email);
						Validate_code.this.startActivity(localIntent);
						Validate_code.this.finish();
						return false;
					}
					Validate_code.this.txt_code_confirmatio.setText("");
					AlertDialog.Builder localBuilder2 = new AlertDialog.Builder(
							Validate_code.this);
					localBuilder2.setTitle("Code invalid");
					localBuilder2
							.setMessage("Code is incorrect. Please Try again.")
							.setCancelable(false)
							.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface paramAnonymous2DialogInterface,
												int paramAnonymous2Int) {
											paramAnonymous2DialogInterface
													.cancel();
										}
									});
					localBuilder2.create().show();
					return false;
				}
				AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(
						Validate_code.this);
				localBuilder1.setTitle("Code invalid");
				localBuilder1
						.setMessage("Please enter a code.")
						.setCancelable(false)
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface paramAnonymous2DialogInterface,
											int paramAnonymous2Int) {
										paramAnonymous2DialogInterface.cancel();
									}
								});
				localBuilder1.create().show();
				return false;
			}
		});

	}

}
