package taxi2share.eu;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.message.BasicNameValuePair;

import taxi2share.eu.City_reservation.reservation;
import taxi2share.eu.biz.Data;
import taxi2share.eu.biz.Function;
import taxi2share.eu.biz.Info_for_a_trip;

import taxi2share.eu.R;
import com.macrew.Utils.TransparentProgressDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class client_notification extends Activity {
	SharedPreferences sharedpreferences;
	TextView price_to_pay, distance, price1, sharing_price_to_pay,
			driving_time1, fidelity_card;
	RatingBar ratingBar1;
	Button rate , librate;
	EditText comment;
	Function function;
	TransparentProgressDialog db;
	String rating;
	
	
	String time_taken , prix , loyality,rating_value;
	
	public void back_home(View paramView) {
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
						Intent i = new Intent(client_notification.this , Home.class);
			    		startActivity(i);
					}
				});
		localBuilder.create().show();
	}
	
	public void deconnect(View paramView) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder
				.setMessage(
						"Are you sure you want to disconnect from TAXI2SHARE ?")
				.setCancelable(false)
				.setPositiveButton("YES",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymousDialogInterface,
									int paramAnonymousInt) {
								paramAnonymousDialogInterface.cancel();
								Intent localIntent1 = new Intent(client_notification.this,
										Login.class);
								client_notification.this.startActivity(localIntent1);
								client_notification.this.finish();
								Intent localIntent2 = new Intent(
										"android.intent.action.MAIN");
								localIntent2
										.addCategory("android.intent.category.HOME");
								localIntent2.setFlags(268435456);
								startActivity(localIntent2);
							}
						});
		localBuilder.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						paramAnonymousDialogInterface.cancel();
					}
				});
		localBuilder.create().show();
	}

	protected void onCreate(Bundle paramBundle) {

		sharedpreferences = PreferenceManager
				.getDefaultSharedPreferences(client_notification.this);

		super.onCreate(paramBundle);
		setContentView(R.layout.client_notification1);
		
		function = new Function();
		
		time_taken = sharedpreferences.getString("time_taken", "");
		prix = sharedpreferences.getString("prix", "");
		loyality = sharedpreferences.getString("loyality", "");

		price_to_pay = (TextView) findViewById(R.id.price_to_pay);
		distance = (TextView) findViewById(R.id.distance);
		price1 = (TextView) findViewById(R.id.price1);
		sharing_price_to_pay = (TextView) findViewById(R.id.sharing_price_to_pay);
		fidelity_card = (TextView) findViewById(R.id.fidelity_card);
		driving_time1 = (TextView) findViewById(R.id.driving_time1);
		ratingBar1 = (RatingBar) findViewById(R.id.ratingBar1);
		rate = (Button) findViewById(R.id.rate);
		comment = (EditText) findViewById(R.id.comment);
		librate = (Button) findViewById(R.id.librate);
		
		distance.setText(sharedpreferences.getString("distance", ""));
		price1.setText((sharedpreferences.getString("price", ""))+" €");
		
		double time = Double.parseDouble(time_taken);
		time = (double)time/60;
		time = (double) Math.round(time * 100) / 100;
		driving_time1.setText(""+time+"m");
		
		double price_nrml = (double) Math.round((2.40+Double.parseDouble(sharedpreferences.getString("price", ""))+ time *0.5) * 100) / 100;
		price_to_pay.setText(price_nrml+" €");
		
		
		
		double price_prix = Double.parseDouble(sharedpreferences.getString("prix", ""));
		price_prix = (double) Math.round(price_prix * 100) / 100;
		
		
		double fidelity = Double.parseDouble(sharedpreferences.getString("loyality", ""));
		fidelity = (double) Math.round((fidelity) * 100) / 100;
		
		double price_fid = price_prix-fidelity;
		price_fid = (double) Math.round(price_fid * 100) / 100;
		
		sharing_price_to_pay.setText(""+price_fid+" €");
		fidelity_card.setText("-"+fidelity+" €");
		
		ratingBar1.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
					rating_value =String.valueOf(rating);
				
			}
		});
		
		rate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rating =String.valueOf(ratingBar1.getRating());
			   
			 
			new rating().execute(new Void[0]);
				
			}
		});
		

	}
	
    public class rating extends AsyncTask<Void, Void, Void> {
    	
    	HashMap<String, String> result;
    	ArrayList localArrayList = new ArrayList();
    		
    	protected Void doInBackground(Void... paramVarArgs) {
    		
    		localArrayList.add(new BasicNameValuePair("id_client",Data.client.get("id_client")));
    		localArrayList.add(new BasicNameValuePair("id_driver",Info_for_a_trip.driver_id));
    		localArrayList.add(new BasicNameValuePair("rating", rating_value));
    		localArrayList.add(new BasicNameValuePair("comments", comment.getText().toString()));
    		
    		
    		Log.e("localArrayList==",""+localArrayList);
    		
    		result = function.rating(localArrayList);
    		Log.e("result==",""+result);
    		return null;
    	}
    	
    	protected void onPostExecute(Void paramVoid) {
    		db.dismiss();
    		if(result.get("result").toString().equalsIgnoreCase("OK")){
    		showGPSDisabledAlertToUser("Your request has been processed successfully.");
    		
    		}
    		
    		else {
    			showGPSDisabledAlertToUser("Something went wrong please try again.");
    		}
    		
    	}
    	
    	protected void onPreExecute() {
			super.onPreExecute();
			db = new TransparentProgressDialog(client_notification.this,
					R.drawable.loading);
			db.show();
		}
    	
    }
}
