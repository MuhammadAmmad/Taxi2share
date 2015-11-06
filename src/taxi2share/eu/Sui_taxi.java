package taxi2share.eu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.message.BasicNameValuePair;

import taxi2share.eu.Home_driver_new.fin_de_service;
import taxi2share.eu.biz.Data;
import taxi2share.eu.biz.Function;
import taxi2share.eu.biz.Info_for_a_trip;
import taxi2share.eu.client_notification.rating;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.macrew.Utils.TransparentProgressDialog;

public class Sui_taxi extends FragmentActivity {

	SupportMapFragment fm;
	Function function = new Function();
	GoogleMap mGoogleMap;
	MarkerOptions option_taxi;
	boolean run = true;
	SharedPreferences sharedpreferences;
	String price;
	String time_taken;
//	/String time_taken_more;
	String time_taken_less;
	String rating,rating_value;
	TransparentProgressDialog db;
	EditText comment;
	HashMap prix;
	Context context;
	Boolean isTaxiCanceled = false;
	Button cancel ;
	Boolean isDialogShown = false;
	
	
	HashMap<String, String> taxi = new HashMap();
	
	private void showGPSDisabledAlertToUser(String paramString) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setMessage(paramString).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						paramAnonymousDialogInterface.cancel();
						
					
						Intent i = new Intent(Sui_taxi.this , Home.class);
			    		startActivity(i);
						
					}
				});
		localBuilder.create().show();
	}
	
	private void showGPSDisabledAlertToUser1(String paramString) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setMessage(paramString).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						paramAnonymousDialogInterface.cancel();
						
						if(isTaxiCanceled){
						Intent i = new Intent(Sui_taxi.this , Home.class);
			    		startActivity(i);
						}
					}
				});
		localBuilder.create().show();
	}
	

	public void home(View paramView) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setTitle("Exit !!");
		localBuilder.setMessage("You can not track your taxi any more !");
		localBuilder.setPositiveButton("Exit",
				new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						Sui_taxi.this.finish();
						Intent localIntent = new Intent(Sui_taxi.this,
								Home.class);
						Sui_taxi.this.startActivity(localIntent);
					}
				});
		localBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						paramAnonymousDialogInterface.dismiss();
					}
				});
		localBuilder.show();
	}

	public void onBackPressed() {
		moveTaskToBack(true);
	}

	protected void onCreate(Bundle paramBundle) {
		
		super.onCreate(paramBundle);
		setContentView(R.layout.sui_taxi);
		
		context = Sui_taxi.this;
		sharedpreferences = PreferenceManager
				.getDefaultSharedPreferences(Sui_taxi.this);

		this.fm = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map));
		this.fm.getMap();
		this.mGoogleMap = this.fm.getMap();
		final Handler localHandler = new Handler();
		localHandler.postDelayed(new Runnable() {
			public void run() {
				if (Sui_taxi.this.run) {
					new get_information_to_show().execute(new Void[0]);
					new get_prix_final().execute(new Void[0]);
				}
				localHandler.postDelayed(this, 1000L);
			}
		}, 1000L);
	}

	public class get_information_to_show extends AsyncTask<Void, Void, Void> {
		Function function = new Function();
		String num_taxi;

		public get_information_to_show() {
		}

		protected Void doInBackground(Void... paramVarArgs) {
			try {
				taxi.clear();
				taxi = this.function.get_info_reserve(Info_for_a_trip.num_taxi);
				// label27:
				// return null;
			} catch (Exception localException) {
				// break label27;
			}

			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			try {
				View marker_view = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.taxi_marker, null);
				
				
				cancel = (Button) marker_view.findViewById(R.id.cancel);
				
			cancel.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						new cancel_client_reservation(sharedpreferences.getString("id_reservation", "")).execute(new Void[0]);
						
					}
				});
				
				Sui_taxi.this.mGoogleMap.clear();
				Sui_taxi.this.option_taxi = new MarkerOptions();
				LatLng localLatLng = new LatLng(
						Double.parseDouble((String) Sui_taxi.this.taxi
								.get("geo_latitude")),
						Double.parseDouble((String) Sui_taxi.this.taxi
								.get("geo_longitude")));
				Sui_taxi.this.option_taxi.position(localLatLng);
//				Sui_taxi.this.option_taxi.icon(BitmapDescriptorFactory
//						.fromResource(R.drawable.localisation_taxi));
				Sui_taxi.this.option_taxi.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(Sui_taxi.this, marker_view)));
				Sui_taxi.this.mGoogleMap.addMarker(Sui_taxi.this.option_taxi);
				Sui_taxi.this.option_taxi.title("client");
			Sui_taxi.this.mGoogleMap.moveCamera(CameraUpdateFactory
						.newLatLng(localLatLng));
			Sui_taxi.this.mGoogleMap.animateCamera(CameraUpdateFactory
						.zoomTo(16.0F));
			
			mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
				
				@Override
				public boolean onMarkerClick(Marker arg0) {
					new cancel_client_reservation(sharedpreferences.getString("id_reservation", "")).execute(new Void[0]);
					return false;
				}
			});
				return;
			} catch (Exception localException) {
			}
		}

		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	public class get_prix_final extends AsyncTask<Void, Void, Void> {
		Function function = new Function();
		

		public get_prix_final() {
		}

		protected Void doInBackground(Void... paramVarArgs) {
			try {
				
				prix = new HashMap ();
				
				prix = this.function.prix_course(
						sharedpreferences.getString("id_reservation", ""),
						Info_for_a_trip.num_taxi);
				
				Log.e("this.Prix==",""+prix);
				
			} catch (Exception localException) {
				
			}
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			
			
			 price = (String) prix.get("prix");
			 time_taken = (String) prix.get("time_taken");
		//	 time_taken_more = (String) prix.get("total_time_for_over_speed");
			 time_taken_less = (String) prix.get("total_time_for_limited_speed");
			
			if (price.equals("null") || price.equals("")) {
				return;
				
			} else {
				if (price.equals("fail_connection")) {
					Toast.makeText(Sui_taxi.this.getApplicationContext(),
							"fail_connection", 1).show();
				}
				
				else {
				Sui_taxi.this.run = false;
				Editor editor1 = sharedpreferences.edit();
				editor1.putString("prix", price);
				editor1.putString("time_taken", time_taken);
				editor1.putString("loyality",(String) prix.get("loyality"));
				editor1.commit();
				//Intent i = new Intent(Sui_taxi.this , client_notification.class);
				//startActivity(i);
				
				if(isDialogShown){
					
				}
				else {
				DisplayPriceDialog();
				}
				
				}
				
			}
			
			
			

		}



		protected void onPreExecute() {
			super.onPreExecute();
		}
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
			db = new TransparentProgressDialog(Sui_taxi.this,
					R.drawable.loading);
			db.show();
		}
    	
    }

		private void DisplayPriceDialog() {
			final Dialog dialog;
			
			
			 final MediaPlayer mp = MediaPlayer.create(Sui_taxi.this, R.raw.sound);
			TextView price_to_pay , distance,price1,sharing_price_to_pay,driving_time1,fidelity_card,boarding_fees;
			Button button1;
			isDialogShown = true;
			final RatingBar ratingBar1;
			Button rate;
			dialog = new Dialog(Sui_taxi.this);
			dialog.setCancelable(false);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setFormat(
					PixelFormat.TRANSLUCENT);

			Drawable d = new ColorDrawable(Color.BLACK);
			d.setAlpha(0);
			dialog.getWindow().setBackgroundDrawable(d);

			dialog.setContentView(R.layout.client_notification);
			price_to_pay = (TextView) dialog.findViewById(R.id.price_to_pay);
			distance = (TextView) dialog.findViewById(R.id.distance);
			price1 = (TextView) dialog.findViewById(R.id.price1);
			boarding_fees = (TextView) dialog.findViewById(R.id.boarding);
			rate = (Button) dialog.findViewById(R.id.rate);
			ratingBar1 = (RatingBar) dialog.findViewById(R.id.ratingBar1);
			
			comment = (EditText) dialog.findViewById(R.id.comment);
	
			sharing_price_to_pay = (TextView) dialog.findViewById(R.id.sharing_price_to_pay);
			fidelity_card = (TextView) dialog.findViewById(R.id.fidelity_card);
			driving_time1 = (TextView) dialog.findViewById(R.id.driving_time1);
			
			double fidelity = Double.parseDouble(prix.get("loyality").toString());
			fidelity = (double) Math.round((fidelity) * 100) / 100;
			
			fidelity_card.setText("-"+fidelity+" €");
			
			
			
			distance.setText(sharedpreferences.getString("distance", "")+" Km");
			Log.e("***prix****",""+prix);
			
			price1.setText((sharedpreferences.getString("price", ""))+" €");
			
			
			double price_prix = Double.parseDouble(price);
			price_prix = (double) Math.round(price_prix * 100) / 100;
			
			double price_fid = price_prix-fidelity;
			price_fid = (double) Math.round(price_fid * 100) / 100;
			
			if(price_fid >0){
			sharing_price_to_pay.setText(""+price_fid+" €");
			}
			else {
				sharing_price_to_pay.setText("0.00"+" €");
			}
			
			double time = Integer.parseInt(time_taken);
			time = (double)time/60;
			double time_without_roundoff = time;
			time = (double) Math.round(time * 100) / 100;
			driving_time1.setText(""+time+"m");
			
		//	double time_more = Integer.parseInt(time_taken_more);
		//	time_more = (double)time_more/60;
		//	double time_without_roundoff_more = time_more;
			
			double time_less = 	Integer.parseInt(time_taken_less);
			time_less = (double)time_less/60;
			double time_without_roundoff_less = time_less;
			
			double price_min = Double.parseDouble(sharedpreferences.getString("price_per_minute", ""));
			double boarding = Double.parseDouble(sharedpreferences.getString("boarding_fee", ""));
			double price_km = Double.parseDouble(sharedpreferences.getString("price_per_km", ""));
			
		//	double price_nrml = (double) Math.round((boarding+Double.parseDouble(sharedpreferences.getString("price", ""))+ ((time_without_roundoff_less *price_min)+time_without_roundoff_more)) * 100) / 100;
			
			double price_nrml = (double) Math.round((boarding+Double.parseDouble(sharedpreferences.getString("price", ""))+ ((time_without_roundoff_less *price_min))) * 100) / 100;
			
			price_to_pay.setText(price_nrml+" €");
			Log.e("boarding f==",""+boarding_fees);
			Log.e("boarding feee==",""+boarding);
			boarding_fees.setText(boarding+" €");
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
					//dialog.dismiss();
					rating =String.valueOf(ratingBar1.getRating());
					   
					 
					new rating().execute(new Void[0]);
					
				}
			});
			 if (! ((Activity) context).isFinishing()) {
			    
			dialog.show();
			 }
			
			 mp.start();

			
		}


		public static Bitmap createDrawableFromView(Context context, View view) {
			DisplayMetrics displayMetrics = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
			view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
			view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
			view.buildDrawingCache();
			Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
	 
			Canvas canvas = new Canvas(bitmap);
			view.draw(canvas);
	 
			return bitmap;
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
	    			isTaxiCanceled = true;
	    		showGPSDisabledAlertToUser1("Your Booking has been cancelled successfully.");
	    		
	    		}
	    		
	    		else {
	    			isTaxiCanceled = false;
	    			showGPSDisabledAlertToUser1(result.get("msg").toString());
	    			
	    		}
	    		}
	    		
	    		catch(Exception ae){
	    			Log.e("Exception==",""+ae);
	    			showGPSDisabledAlertToUser1("Something went wrong.Please try again.");
	    		}
	    		
	    	}
	    	
	    	protected void onPreExecute() {
				super.onPreExecute();
				db = new TransparentProgressDialog(Sui_taxi.this,
						R.drawable.loading);
				db.show();
			}
	    	
	    }
}