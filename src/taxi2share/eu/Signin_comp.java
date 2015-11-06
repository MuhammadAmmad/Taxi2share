package taxi2share.eu;


import taxi2share.eu.biz.Data_driver;
import taxi2share.eu.biz.Function_driver;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import taxi2share.eu.R;
import com.macrew.Utils.TransparentProgressDialog;

public class Signin_comp extends Activity {

	EditText Email;
	String Emails;
	EditText Name_company;
	String Name_companys;
	EditText adress;
	String adresss;
	LinearLayout back;
	Boolean  success = false;
	TransparentProgressDialog db;
	Function_driver function = new Function_driver();
	Button log_in;

	EditText name_manager;
	String name_managers;
	EditText number_taxi;
	String number_taxis;
	EditText numberphone;
	String numberphones;

	/**
	 * 
	 * @param paramString
	 *            (message) to show alert message to the user
	 */

	private void showGPSDisabledAlertToUser(String paramString) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setMessage(paramString).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						paramAnonymousDialogInterface.cancel();
						if(success){
							Intent i = new Intent(Signin_comp.this, Login.class);
							startActivity(i);
						}
					}
				});
		localBuilder.create().show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_comp);

		Name_company = ((EditText) findViewById(R.id.Name_company));
		name_manager = ((EditText) findViewById(R.id.name_manager));
		Email = ((EditText) findViewById(R.id.Email));
		numberphone = ((EditText) findViewById(R.id.numberphone));
		adress = ((EditText) findViewById(R.id.adress));
		number_taxi = ((EditText) findViewById(R.id.number_taxi));

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
			public void onClick(View v) {
				Name_companys = Name_company.getText().toString();
				name_managers = name_manager.getText().toString();
				Emails = Email.getText().toString();
				numberphones = numberphone.getText().toString();
				adresss = adress.getText().toString();
				number_taxis = number_taxi.getText().toString();

				if ((Name_companys.length() == 0)
						|| (name_managers.length() == 0)) {
					showGPSDisabledAlertToUser("Please enter company name and manager name");
					return;
				}
				if (!function.verif(Emails)) {
					showGPSDisabledAlertToUser("Please enter a correct Email");
					return;
				}
				if (numberphones.length() < 7) {
					showGPSDisabledAlertToUser("Please enter a correct Phone Number");
					return;
				}
				if (adresss.length() < 1) {
					showGPSDisabledAlertToUser("Please enter your address");
					return;
				}
				if (number_taxis.length() < 1) {
					showGPSDisabledAlertToUser("Please enter the taxi number");
					return;
				}
				/*
				 * if (bank_accounts.length() < 14) {
				 * showGPSDisabledAlertToUser(
				 * "Please enter the right number of your bank account");
				 * return; } if (name_banks.length() < 1) {
				 * showGPSDisabledAlertToUser("Please enter the name of bank");
				 * return; }
				 */
				new insert_client().execute(new Void[0]);

			}
		});
	}
	/**
	 * 
	 * @author Desktop
	 * 
	 * web service for signup
	 *
	 */
	public class insert_client extends AsyncTask<Void, Void, Void> {
		String result;

		public insert_client() {
		}

		protected Void doInBackground(Void... paramVarArgs) {
		
			this.result = function.insert(Name_companys, name_managers,
					number_taxis, numberphones, adresss, Emails);
			
			Log.e("result===",""+result);
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			db.dismiss();
			if (this.result.equalsIgnoreCase("exist")) {
				showGPSDisabledAlertToUser("Your E-mail exist");
				return;
			}
			if (this.result.equalsIgnoreCase("erreur_insertion")) {
				showGPSDisabledAlertToUser("A problem has been occurred while registering your account ! Please try again");
				return;
			}
			if (this.result.equalsIgnoreCase("fail_connection")) {
				// showGPSDisabledAlertToUser("You seem have problem with connection please try again");
				// return;
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(
						Signin_comp.this);
				localBuilder
						.setMessage(
								"You seem have problem with connection please try again")
						.setCancelable(false)
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface paramAnonymousDialogInterface,
											int paramAnonymousInt) {
										finish();
										paramAnonymousDialogInterface.cancel();
									}
								});
				localBuilder.create().show();

			}
			
			else {
				Data_driver.id_driver_inscri = this.result;
				success = true;
			showGPSDisabledAlertToUser("Successfully registered.");
			
			
			}
		//	new mail_to_comp().execute(new Void[0]);
		
		}

		protected void onPreExecute() {
			super.onPreExecute();
			db = new TransparentProgressDialog(Signin_comp.this,
					R.drawable.loading);
			db.show();
		}
	}


///**
// * 
// * @author Desktop
// * async class to send mail to the driver
// */
//
//	public class mail_to_comp extends AsyncTask<Void, Void, Void> {
//		String status;
//
//		public mail_to_comp() {
//		}
//
//		protected Void doInBackground(Void... paramVarArgs) {
//			status = function.envoie_mail("info@taxi2share.eu");
//			Log.e("status of driver==", "==" + status);
//			return null;
//		}
//
//		protected void onPostExecute(Void paramVoid) {
//			db.dismiss();
//			Log.e("status(comp)==", "==" + status);
//
//			if (this.status.equalsIgnoreCase("Failure")) {
//				showGPSDisabledAlertToUser("A problem has been occurred while sending your account ! Please try again");
//				return;
//			}
//			AlertDialog.Builder localBuilder = new AlertDialog.Builder(
//					Signin_comp.this);
//			localBuilder
//					.setMessage(
//							"Your account has been registered successfully.")
//					.setCancelable(false)
//					.setPositiveButton("Ok",
//							new DialogInterface.OnClickListener() {
//								public void onClick(
//										DialogInterface paramAnonymousDialogInterface,
//										int paramAnonymousInt) {
//									finish();
//									paramAnonymousDialogInterface.cancel();
//								}
//							});
//			localBuilder.create().show();
//		}
//
//		protected void onPreExecute() {
//			super.onPreExecute();
//			db = new TransparentProgressDialog(Signin_comp.this,
//					R.drawable.loading);
//			db.show();
//		}
//	}
}
