package taxi2share.eu;

import taxi2share.eu.biz.Data;
import taxi2share.eu.biz.Function;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import taxi2share.eu.R;
import com.macrew.Utils.TransparentProgressDialog;

public class Signin_client extends Activity {

	EditText Email;
	String Emails;
	EditText Name;
	String Names;
	LinearLayout back;
	EditText confirmpass;
	String confirmpasss;
	TransparentProgressDialog db;
	Function function = new Function();
	EditText last_name;
	String last_names;
	Button log_in;
	EditText numberphone;
	String numberphones;
	EditText pass;
	String passs;

	private void showGPSDisabledAlertToUser(String paramString) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setMessage(paramString).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						paramAnonymousDialogInterface.cancel();
					}
				});
		localBuilder.create().show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_client);

		Name = ((EditText) findViewById(R.id.Name));
		last_name = ((EditText) findViewById(R.id.last_name));
		Email = ((EditText) findViewById(R.id.Email));
		numberphone = ((EditText) findViewById(R.id.numberphone));
		pass = ((EditText) findViewById(R.id.pass));
		confirmpass = ((EditText) findViewById(R.id.confirmpass));
		log_in = ((Button) findViewById(R.id.log_in));
		back = ((LinearLayout) findViewById(R.id.back));

		/**
		 * to add back functionality on a button click
		 */
		back.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				onBackPressed();
				return false;

			}
		});
		/*
		 * initiate web service on button click
		 */
		log_in.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Names = Signin_client.this.Name.getText().toString();
				last_names = Signin_client.this.last_name.getText().toString();
				Emails = Signin_client.this.Email.getText().toString();
				numberphones = Signin_client.this.numberphone.getText()
						.toString();
				passs = Signin_client.this.pass.getText().toString();
				confirmpasss = Signin_client.this.confirmpass.getText()
						.toString();
				if ((Names.length() == 0) || (last_names.length() == 0)) {
					showGPSDisabledAlertToUser("Please enter your first name and last name");
					return;
				}
				if (!function.verif(Signin_client.this.Emails)) {
					showGPSDisabledAlertToUser("Please enter a correct Email");
					return;
				}
				if (numberphones.length() < 7) {
					showGPSDisabledAlertToUser("Please enter a correct Phone Number");
					return;
				}
				if (passs.length() < 6) {
					showGPSDisabledAlertToUser("password is too short! Please try again");
					return;
				}
				if (!passs.equalsIgnoreCase(Signin_client.this.confirmpasss)) {
					showGPSDisabledAlertToUser("Password and confirm password are not identical! Please try again");
					return;
				}
				new insert_client().execute(new Void[0]);

			}
		});

	}

	/**
	 * 
	 * @author Desktop
	 * 
	 *         web service for signup
	 * 
	 */
	public class insert_client extends AsyncTask<Void, Void, Void> {
		String result;

		public insert_client() {
		}

		protected Void doInBackground(Void... paramVarArgs) {
			this.result = Signin_client.this.function.insert(
					Signin_client.this.Name.getText().toString(),
					Signin_client.this.last_name.getText().toString(),
					Signin_client.this.Email.getText().toString(),
					Signin_client.this.numberphone.getText().toString(),
					Signin_client.this.pass.getText().toString(), "");
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			Signin_client.this.db.dismiss();

			if (this.result.equalsIgnoreCase("exist")) {
				Signin_client.this
						.showGPSDisabledAlertToUser("Your E-mail exist");
				return;
			}
			if (this.result.equalsIgnoreCase("erreur_insertion")) {
				Signin_client.this
						.showGPSDisabledAlertToUser("A problem has been occurred while registering your account ! Please try again");
				return;
			}
			if (this.result.equalsIgnoreCase("fail_connection")) {
				Signin_client.this
						.showGPSDisabledAlertToUser("You seem have problem with connection please try again");
				return;
			}

			Data.id_client_inscri = this.result;
			new mail_to_client().execute(new Void[0]);
		}

		protected void onPreExecute() {
			super.onPreExecute();
			db = new TransparentProgressDialog(Signin_client.this,
					R.drawable.loading);
			db.show();
		}
	}

	/**
	 * 
	 * @author Desktop async class to send mail to the driver
	 */

	public class mail_to_client extends AsyncTask<Void, Void, Void> {
		String code;

		public mail_to_client() {
		}

		protected Void doInBackground(Void... paramVarArgs) {
			code = function.envoie_mail(Signin_client.this.Email.getText()
					.toString());
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			Signin_client.this.db.dismiss();
			if (this.code.equalsIgnoreCase("erreur")) {
				Signin_client.this
						.showGPSDisabledAlertToUser("Error, Please try again !!");
				return;
			}
			if (this.code.equalsIgnoreCase("fail_connection")) {
				Signin_client.this
						.showGPSDisabledAlertToUser("A problem has been occurred while sending your account ! Please try again");
				return;
			}
			AlertDialog.Builder localBuilder = new AlertDialog.Builder(
					Signin_client.this);
			localBuilder
					.setMessage(
							"Your account has been sucessfully registered. Please check your e-mail to activate taxi2share.")
					.setCancelable(false)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface paramAnonymousDialogInterface,
										int paramAnonymousInt) {
									finish();
									Data.code = code;
									Intent localIntent = new Intent(
											Signin_client.this,
											Confirm_account.class);
									startActivity(localIntent);
									paramAnonymousDialogInterface.cancel();
								}
							});
			localBuilder.create().show();
		}

		protected void onPreExecute() {
			super.onPreExecute();
			Signin_client.this.db = new TransparentProgressDialog(
					Signin_client.this, R.drawable.loading);
			db.show();
		}
	}
}
