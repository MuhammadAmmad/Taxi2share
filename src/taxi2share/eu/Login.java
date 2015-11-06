package taxi2share.eu;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import taxi2share.eu.biz.Data;
import taxi2share.eu.biz.Data_driver;
import taxi2share.eu.biz.Function;
import taxi2share.eu.biz.Function_driver;
import taxi2share.eu.biz.GPSTracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import taxi2share.eu.R;
import com.macrew.Utils.TransparentProgressDialog;

public class Login extends Activity {
	String acces = "client";
	ImageView air;
	Button connect;
	LinearLayout connect_blog;
	LinearLayout container;
	LinearLayout creer_nouveau;
	TransparentProgressDialog db;
	EditText email;
	LinearLayout for_taxi;
	TextView forget_pass;
	Function function = new Function();

	Function_driver function_chauff = new Function_driver();
	GPSTracker gps;
	ImageView left;
	LinearLayout logo;
	ImageView number;
	EditText pass;
	TextView register_tupe;
	ImageView right;
	TextView utilisateur;
	ImageView v_beta;
	SharedPreferences sp;
	
	/**
	 * to check GPS is enabled or not
	 */
	private void showGPSDisabledAlertToUser() {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder
				.setMessage(
						"GPS is disabled in your device. Would you like to enable it?")
				.setCancelable(false)
				.setPositiveButton("Goto Settings Page To Enable GPS",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymousDialogInterface,
									int paramAnonymousInt) {
								paramAnonymousDialogInterface.cancel();
								Intent localIntent = new Intent(
										"android.settings.LOCATION_SOURCE_SETTINGS");
								startActivity(localIntent);
							}
						});
		localBuilder.create().show();
	}
	
	/**
	 * 
	 * @param paramString (message)
	 * to show alert message to the user
	 */

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
		setContentView(R.layout.login);
		
		sp = PreferenceManager
				.getDefaultSharedPreferences(Login.this);
		
		
	    
		this.logo = ((LinearLayout) findViewById(R.id.logo));
		this.connect_blog = ((LinearLayout) findViewById(R.id.connect_blog));
		this.for_taxi = ((LinearLayout) findViewById(R.id.for_taxi));
		this.creer_nouveau = ((LinearLayout) findViewById(R.id.creer_nouveau));
		this.email = ((EditText) findViewById(R.id.email));
		this.connect = ((Button) findViewById(R.id.connect));
		this.pass = ((EditText) findViewById(R.id.pass));
		this.container = ((LinearLayout) findViewById(R.id.container));
		this.utilisateur = ((TextView) findViewById(R.id.utilisateur));
		this.register_tupe = ((TextView) findViewById(R.id.register_tupe));
		this.forget_pass = ((TextView) findViewById(R.id.forget_pass));
		this.v_beta = ((ImageView) findViewById(R.id.v_beta));

		Rang_client.clien_1_with_client3 = false;
		Rang_client.clien_2_with_client3 = false;
		Rang_client.client1 = 0;
		Rang_client.client2 = 0;
		Rang_client.client3 = 0;
		InputMethodManager localInputMethodManager = (InputMethodManager) getSystemService("input_method");
		localInputMethodManager.hideSoftInputFromWindow(
				this.email.getWindowToken(), 0);
		localInputMethodManager.hideSoftInputFromWindow(
				this.pass.getWindowToken(), 0);
		Animation localAnimation = AnimationUtils.loadAnimation(Login.this,
				R.anim.translate_de);
		localAnimation.setFillAfter(true);
		localAnimation.setFillEnabled(true);
		logo.startAnimation(localAnimation);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				Animation localAnimation = AnimationUtils.loadAnimation(
						Login.this, R.anim.translate);
				localAnimation.setFillAfter(true);
				localAnimation.setFillEnabled(true);
				logo.startAnimation(localAnimation);
			}
		}, 1000L);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Animation localAnimation = AnimationUtils.loadAnimation(
						Login.this, R.anim.fade_in);
				localAnimation.setFillAfter(true);
				localAnimation.setFillEnabled(true);
				connect_blog.startAnimation(localAnimation);
				for_taxi.startAnimation(localAnimation);
			}
		}, 1500L);
		this.for_taxi.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if (Login.this.acces.equalsIgnoreCase("client")) {
					container.setBackgroundResource(R.drawable.background_comp);
					utilisateur.setText("Access client");
					acces = "chauffeur";
					email.setText("");
					pass.setText("");
					email.setHint("Number Taxi");
					register_tupe.setText(R.string.register_comp);
					email.setHintTextColor(Color.parseColor("#ffffff"));
					pass.setHintTextColor(Color.parseColor("#ffffff"));

					v_beta.setImageResource(R.drawable.v_beta_txi);
					return;
				}
				container.setBackgroundResource(R.drawable.background);
				utilisateur.setText("Access to the driver");
				acces = "client";
				register_tupe.setText(R.string.register);
				email.setText("");
				pass.setText("");
				email.setHint("E-mail");
				email.setHintTextColor(Color.parseColor("#696969"));
				pass.setHintTextColor(Color.parseColor("#696969"));
				v_beta.setImageResource(R.drawable.v_beta);

			}
		});
		
		/**
		 * this button sends user to the signup screen
		 */
		this.creer_nouveau.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if (Login.this.acces.equalsIgnoreCase("client")) {
					Intent localIntent1 = new Intent(Login.this,
							Signin_client.class);
					startActivity(localIntent1);
					return;
				}
				Intent localIntent2 = new Intent(Login.this, Signin_comp.class);
				startActivity(localIntent2);
			}
		});
		
		/**
		 * sends user to the forgot password screen
		 */
		this.forget_pass.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramAnonymousView,
					MotionEvent paramAnonymousMotionEvent) {
				if (Login.this.acces.equalsIgnoreCase("client")) {
					Intent localIntent = new Intent(Login.this,
							Forget_password.class);
					startActivity(localIntent);
				}
				return false;
			}
		});
		
		/**
		 * Starts Login process
		 */

		this.connect.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if (acces.equalsIgnoreCase("client")) {
					String str3 = email.getText().toString();
					String str4 = pass.getText().toString();
					if ((!function.verif(str3)) || (str4.length() < 6)) {
						AlertDialog.Builder localBuilder2 = new AlertDialog.Builder(
								Login.this);
						localBuilder2.setTitle("Please try again");
						localBuilder2
								.setMessage(
										"The email or password you entered is incorrect.")
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
						return;
					}
					new Login_ex().execute(new Void[0]);
					return;
				}
				String str1 = email.getText().toString();
				String str2 = pass.getText().toString();
				if ((str1.length() < 1) || (str2.length() < 1)) {
					AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(
							Login.this);
					localBuilder1.setTitle("Please try again");
					localBuilder1
							.setMessage(
									"The number or password you entered is incorrect.")
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
					localBuilder1.create().show();
					return;
				}
				if (((LocationManager) Login.this.getSystemService("location"))
						.isProviderEnabled("gps")) {
					gps = new GPSTracker(Login.this);
					double d1 = gps.getLatitude();
					double d2 = gps.getLongitude();
					
					//double d1 = 31.1033;
					//double d2 = 77.1722;

					Log.i("latitude(d1==)", "==" + d1);
					Log.i("longitude(d2==)", "==" + d2);
					Data_driver.latitude_chauf = d1;
					Data_driver.longitude_chauf = d2;
					new connexion__taxi().execute(new Void[0]);
					return;
				}
				showGPSDisabledAlertToUser();
			}
		});
	}
	
	/**
	 * 
	 * @author Desktop
	 * in case  of DRIVER
	 * Aync task to initiate login process 
	 *
	 */

	public class connexion__taxi extends AsyncTask<Void, Void, Void> {
		HashMap<String, String> taxi_driver;

		public connexion__taxi() {
		}

		protected Void doInBackground(Void... paramVarArgs) {
			this.taxi_driver = new HashMap();
			this.taxi_driver = function_chauff.connect(email.getText()
					.toString(), pass.getText().toString(),
					Data_driver.latitude_chauf, Data_driver.longitude_chauf);
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			db.dismiss();
			if (((String) this.taxi_driver.get("result"))
					.equalsIgnoreCase("OK")) {
				Log.e("taxiiiiiiiiii==",""+taxi_driver.get("num_taxi"));
				Data_driver.chauffeur = this.taxi_driver;
				Data_driver.num_taxi = taxi_driver.get("num_taxi");
				
				Editor e = sp.edit();
				e.putString("ratings",Data_driver.chauffeur.get("ratings"));
				e.commit();
				Intent localIntent = new Intent(Login.this,
						Home_driver_new.class);
				startActivity(localIntent);
				finish();
			}
			while (!((String) this.taxi_driver.get("result"))
					.equalsIgnoreCase("ERROR")) {
				Intent localIntent;
				return;
			}
			AlertDialog.Builder localBuilder = new AlertDialog.Builder(
					Login.this);
			localBuilder
					.setMessage(
							"Taxi number or password you entered is incorrect.")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface paramAnonymousDialogInterface,
										int paramAnonymousInt) {
									paramAnonymousDialogInterface.cancel();
								}
							});
			localBuilder.create().show();
		}

		protected void onPreExecute() {
			db = new TransparentProgressDialog(Login.this, R.drawable.loading);
			db.show();
		}
	}

	/**
	 * 
	 * @author Desktop
	 * in case  of DRIVER
	 * Aync task to initiate login process 
	 *
	 */

	public class Login_ex extends AsyncTask<Void, Void, Void> {
		HashMap<String, String> client;

		public Login_ex() {
		}

		protected void onPreExecute() {
			db = new TransparentProgressDialog(Login.this, R.drawable.loading);
			db.show();
		}

		protected Void doInBackground(Void... paramVarArgs) {
			this.client = new HashMap();
			this.client = function.connect(email.getText().toString(), pass
					.getText().toString());
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			db.dismiss();
			try {
				Log.i("status====", "==" + ((String) this.client.get("statut")));
				if (((String) this.client.get("result")).equalsIgnoreCase("OK")) {

					if (((String) this.client.get("statut"))
							.equalsIgnoreCase("0")) {

						Builder localBuilder2 = new AlertDialog.Builder(
								Login.this);
						localBuilder2
								.setMessage("your account is not active yet!.")
								.setCancelable(false)
								.setPositiveButton("Activate",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface paramAnonymousDialogInterface,
													int paramAnonymousInt) {
												Data.id_client_inscri = (String) client
														.get("id_client");
												Data.client = client;
												
												new mail_to_client()
														.execute(new Void[0]);
												paramAnonymousDialogInterface
														.cancel();
											}
										});
						localBuilder2.create().show();
					}

					else {
						while (!((String) this.client.get("result"))
								.equalsIgnoreCase("ERROR")) {
							AlertDialog.Builder localBuilder2;
							// return;
							Data.client = this.client;
							Editor e = sp.edit();
							e.putString("id_client",Data.client.get("id_client"));
							e.putString("token",Data.client.get("token"));
							Log.e("id===",""+sp.getString("id_client", ""));
							e.commit();
							Intent localIntent = new Intent(Login.this,
									Home.class);
							startActivity(localIntent);
							finish();
							return;
						}
					}
				} else {
					AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(
							Login.this);
					localBuilder1
							.setMessage(
									"The email or password you entered is incorrect.")
							.setCancelable(false)
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface paramAnonymousDialogInterface,
												int paramAnonymousInt) {
											paramAnonymousDialogInterface
													.cancel();
										}
									});
					localBuilder1.create().show();
				}
			}

			catch (Exception ae) {
				Log.i("Exception(on post execute)==", "==" + ae);
				AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(
						Login.this);
				localBuilder1
						.setMessage(
								"Something went wrong while processing this request, Please try again!")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface paramAnonymousDialogInterface,
											int paramAnonymousInt) {
										paramAnonymousDialogInterface.cancel();
									}
								});
				localBuilder1.create().show();
			}

		}

	}
	
	/**
	 * 
	 * @author Desktop
	 *async task to send mail to client
	 */
	public class mail_to_client extends AsyncTask<Void, Void, Void> {
		String code;

		public mail_to_client() {
		}

		protected Void doInBackground(Void... paramVarArgs) {
			this.code = function.envoie_mail((String) Data.client.get("email"));
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			db.dismiss();
			if (this.code.equalsIgnoreCase("erreur")) {
				showGPSDisabledAlertToUser("Error, Please try again !!");
				return;
			}
			if (this.code.equalsIgnoreCase("fail_connection")) {
				showGPSDisabledAlertToUser("A problem has been occurred while sending your account ! Please try again");
				return;
			}
			AlertDialog.Builder localBuilder = new AlertDialog.Builder(
					Login.this);
			localBuilder
					.setMessage(
							"Please check your account e-mail to activate it. ")
					.setCancelable(false)
					.setPositiveButton("Confirm",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface paramAnonymousDialogInterface,
										int paramAnonymousInt) {
									finish();
									Data.code = code;
									Intent localIntent = new Intent(Login.this,
											Confirm_account.class);
									startActivity(localIntent);
									paramAnonymousDialogInterface.cancel();
								}
							});
			localBuilder.create().show();
		}

		protected void onPreExecute() {
			super.onPreExecute();
			db = new TransparentProgressDialog(Login.this, R.drawable.loading);
			db.show();
		}
	}
	
	private String getDate(long milliSeconds) {
        // Create a DateFormatter object for displaying date in specified
        // format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy hh:mm aa");
        // Create a calendar object that will convert the date and time value in
        // milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((int) milliSeconds);
        return formatter.format(calendar.getTime());
    } 
	
	@Override
	public void onStop() {
	
		super.onStop();

	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("Destroy","destroy");
	}
	
	public void onPause() {
		super.onPause();
		Log.e("pause","pause");
	}
	

}
