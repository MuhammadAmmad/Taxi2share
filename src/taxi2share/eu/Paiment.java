package taxi2share.eu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.http.message.BasicNameValuePair;

import taxi2share.eu.Sui_taxi.cancel_client_reservation;
import taxi2share.eu.biz.Data;
import taxi2share.eu.biz.Function;
import taxi2share.eu.biz.Info_for_a_trip;

import taxi2share.eu.R;
import com.macrew.Utils.TransparentProgressDialog;

public class Paiment extends Activity {
	Spinner Card;
	AlertDialog ad;
	AlertDialog.Builder adb;
	LinearLayout add_card;
	LinearLayout annuler;
	Button btnStart;
	Button btnStop;
	TextView chek;
	Spinner spinner1;
	LinearLayout confirmer;
	View convertview;
	TransparentProgressDialog db;
	TextView destination;
	Function function = new Function();
	TextView pick_up;
	boolean run_statut = false;
	TextView textViewTime;
	CounterClass timer;
	String shareable_with;
	SharedPreferences sp;
	String spinnerValue = "";

	public void arriere(View paramView) {
		Intent i = new Intent(Paiment.this,Taxi_map.class);
		Info_for_a_trip.destination = "";
		
		startActivity(i);
		finish();
		onBackPressed();
	}

	public void home(View paramView) {
		Intent i = new Intent(Paiment.this,Taxi_map.class);
		Info_for_a_trip.destination = "";
		startActivity(i);
		finish();
		onBackPressed();
	}
	
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

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.paiment);
		confirmer = ((LinearLayout) findViewById(R.id.confirmer));
		annuler = ((LinearLayout) findViewById(R.id.annuler));
		pick_up = ((TextView) findViewById(R.id.pick_up));
		destination = ((TextView) findViewById(R.id.destination));
		pick_up.setText(Info_for_a_trip.depart);
		destination.setText(Info_for_a_trip.destination);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		
		
		spinnerValue =spinner1.getSelectedItem().toString();
		
		Intent intent = getIntent();
		shareable_with = intent.getStringExtra("shareable_with");
		sp = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		
		/**
		 * cancel the booking
		 */
		
		annuler.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Paiment.this,Taxi_map.class);
				startActivity(i);
				finish();
				onBackPressed();
				
				
			}

		});
		/**
		 * web service on confirm button click
		 * reservation_client.php
		 * 
		 * reserv_taxi_lunch()
		 */
		confirmer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				
				new Paiment.reserv_taxi_lunch().execute(new Void[0]);
			}
		});
		LayoutInflater localLayoutInflater = (LayoutInflater) getSystemService("layout_inflater");
		convertview = new View(this);
		convertview = localLayoutInflater.inflate(R.layout.timer_chauffeur, null);
		
		

		
		
		adb = new AlertDialog.Builder(this);
		
		
		adb.setCancelable(false);
		adb.setView(convertview);
		btnStart = ((Button) convertview.findViewById(R.id.btnStart));
		btnStop = ((Button) convertview.findViewById(R.id.btnStop));
		textViewTime = ((TextView) convertview
				.findViewById(R.id.textViewTime));
		textViewTime.setText("00:00:45");
		chek = ((TextView) this.convertview.findViewById(R.id.chek));
		chek.setText("Please wait for taxi confirmation... ");
		
	
		ad = this.adb.create();
		
		
		btnStart.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View paramAnonymousView) {
				
		
				new CountDownTimer(47000, 1000) {

					 public void onTick(long millisUntilFinished) {
						 textViewTime.setText("00:00:" + millisUntilFinished / 1000);
					  //here you can have your logic to set text to edittext
					 }

					 public void onFinish() {
						 textViewTime.setText("No response from the driver");
							btnStart.setText("Try again");
							btnStop.setText("Take another");
							btnStart.setVisibility(0);
							btnStop.setVisibility(0);
							chek.setVisibility(4);
					 }
					}
					.start();
			}
		});
		btnStop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				ad.dismiss();
				new cancel_client_reservation(sp.getString("id_reservation", "")).execute(new Void[0]);
			}
		});
	}

	public class CounterClass extends CountDownTimer {
		long countDownInterval;
		long millisInFuture;

		public CounterClass(long paramLong1, long paramLong2) {
			super(paramLong2, paramLong2);
		}

		public void onFinish() {
			textViewTime.setText("No response from the driver");
			btnStart.setText("Try again");
			btnStop.setText("Take another");
			btnStart.setVisibility(0);
			btnStop.setVisibility(0);
			chek.setVisibility(4);
		}

		public void onTick(long paramLong) {
			Object[] arrayOfObject = new Object[1];
			arrayOfObject[0] = Long.valueOf(TimeUnit.MILLISECONDS
					.toSeconds(paramLong)
					- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
							.toMinutes(paramLong)));
			String str = String.format("%02d", arrayOfObject);
			Paiment.this.textViewTime.setText(str);
		}
	}

	/**
	 * 
	 * @author Desktop
	 *	statut_reservation.php
	 */
	public class get_statut extends AsyncTask<Void, Void, Void> {
		String statut;
		
		 final MediaPlayer mp = MediaPlayer.create(Paiment.this, R.raw.sound);

		public get_statut() {
		}

		protected Void doInBackground(Void... paramVarArgs) {
			
			
			this.statut = Paiment.this.function.getstatut(sp.getString("id_reservation",""));
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			
			Log.i("staut===","===="+statut);
			if (this.statut.equalsIgnoreCase("1")) {
				Paiment.this.ad.dismiss();
				run_statut = true;
			
				try {
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(
						Paiment.this);
				localBuilder
						.setTitle("Your taxi is ordered, follow him on your phone.");
				localBuilder.setCancelable(false).setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymousDialogInterface,
									int paramAnonymousInt) {
								Paiment.this.finish();
								Intent localIntent = new Intent(Paiment.this,
										Sui_taxi.class);
								mp.stop();
								startActivity(localIntent);
								paramAnonymousDialogInterface.cancel();
							}
						});
				localBuilder.create().show();
				mp.start();
				return;
				}
				
				catch(Exception ae){
					
				}
			}
			
			else if(this.statut.equalsIgnoreCase("-1")) {
				Paiment.this.ad.dismiss();
				try {
					AlertDialog.Builder localBuilder = new AlertDialog.Builder(
							Paiment.this);
					localBuilder
							.setTitle("Your request is declined.Please try for another taxi.");
					localBuilder.setCancelable(false).setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface paramAnonymousDialogInterface,
										int paramAnonymousInt) {
									Paiment.this.finish();
									Intent localIntent = new Intent(Paiment.this,
											Taxi_map.class);
									startActivity(localIntent);
									paramAnonymousDialogInterface.cancel();
								}
							});
					localBuilder.create().show();
					return;
					}
					
					catch(Exception ae){
						
					}
			}
			this.statut.equalsIgnoreCase("fail_connection");
		}

		protected void onPreExecute() {
			super.onPreExecute();
		}
	}
	
	/**
	 * 
	 * @author Desktop
	 *	reservation_client.php
	 */
	public class reserv_taxi_lunch extends AsyncTask<Void, Void, Void> {
		String num_taxi;
		HashMap result;
		int somme;
		

		
		protected void onPreExecute() {
		
			super.onPreExecute();
			db = new TransparentProgressDialog(Paiment.this, R.drawable.loading);
			db.show();
		}
		protected Void doInBackground(Void... paramVarArgs) {
			
			result = new HashMap ();
			
			somme = (Info_for_a_trip.place_occupe + Integer
					.parseInt(Info_for_a_trip.nbr_passenger));
			
			
		
			result = function.insert_reser(
					sp.getString("id_client",""),
					String.valueOf(Info_for_a_trip.lat_dep),String.valueOf( Info_for_a_trip.lon_dep),
					String.valueOf(Info_for_a_trip.lat_des), String.valueOf(Info_for_a_trip.lon_des),
					Info_for_a_trip.nbr_bagage, String.valueOf(this.somme),
					Info_for_a_trip.num_taxi, Info_for_a_trip.depart,
					Info_for_a_trip.destination, spinnerValue, String.valueOf(Info_for_a_trip.price),shareable_with,
					sp.getString("locality_own", ""),sp.getString("strDate", ""),sp.getString("distance", ""));
			
			Log.e("result of paiment=",""+result);
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			db.dismiss();
			
			try{
			
			
			if (result.get("result").equals("OK")) {
				ad.show();
				
				String id = (String)result.get("id_reservation");
				
				Editor editor = sp.edit();
				editor.putString("id_reservation", id);
				editor.commit();
		
				new CountDownTimer(47000, 1000) {

					 public void onTick(long millisUntilFinished) {
						 textViewTime.setText("00:00:" + millisUntilFinished / 1000);
					 
					 }

					 public void onFinish() {
						 textViewTime.setText("No response from the driver");
							btnStart.setText("Try again");
							btnStop.setText("Take another");
							btnStart.setVisibility(0);
							btnStop.setVisibility(0);
							chek.setVisibility(4);
					 }
					}
					.start();
				final Handler localHandler = new Handler();
				localHandler.postDelayed(new Runnable() {
					public void run() {
						
						if (!Paiment.this.run_statut) {
						
							new Paiment.get_statut().execute(new Void[0]);
						}
						localHandler.postDelayed(this, 2000L);
					}
				}, 1000L);
			} else {
				showGPSDisabledAlertToUser("Error occurred.Please try again.");
			}
			
		}
			
			catch(Exception e){
				Log.e("exception on paiment confirm==",""+e);
				showGPSDisabledAlertToUser("Error occurred.Please try again.");
			}
		}

	
	}
	
	public class cancel_client_reservation extends AsyncTask<Void, Void, Void> {
    	
		String reservation_id;
    	HashMap<String, String> result;
    	ArrayList localArrayList = new ArrayList();
    	
    
    	public cancel_client_reservation(String string) {
			// TODO Auto-generated constructor stub
    		
    		reservation_id = string;
		}

		protected Void doInBackground(Void... paramVarArgs) {
    		
    		localArrayList.add(new BasicNameValuePair("id_reservation",this.reservation_id));
    		
    	
    		
    		result = function.cancel_client_reservation(localArrayList);
    		Log.e("result==",""+result);
    		return null;
    	}
    	
    	protected void onPostExecute(Void paramVoid) {
    		db.dismiss();
    		try{
    		if(result.get("result").toString().equalsIgnoreCase("OK")){
    		
    		showGPSDisabledAlertToUser("Your Booking has been cancelled successfully.");
    		
    		}
    		
    		else {
    			
    			showGPSDisabledAlertToUser(result.get("msg").toString());
    			
    		}
    		}
    		
    		catch(Exception ae){
    			Log.e("Exception==",""+ae);
    			showGPSDisabledAlertToUser("Something went wrong.Please try again.");
    		}
    		
    	}
    	
    	protected void onPreExecute() {
			super.onPreExecute();
			db = new TransparentProgressDialog(Paiment.this,
					R.drawable.loading);
			db.show();
		}
    	
    }
}
