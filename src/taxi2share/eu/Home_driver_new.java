package taxi2share.eu;

import android.app.Activity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.xml.datatype.Duration;

import org.json.JSONArray;
import org.json.JSONObject;

import taxi2share.eu.biz.*;

import taxi2share.eu.R;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.macrew.Utils.TransparentProgressDialog;
import com.macrew.imageloader.ImageLoader;

public class Home_driver_new extends FragmentActivity {

	private static final int MY_NOTIFICATION_ID = 1;
	AlertDialog ad;
	AlertDialog.Builder adb;
	Button btnStart;
	Button btnStop;
	String id_client;
	TransparentProgressDialog charge;
	TransparentProgressDialog db;
	int client_all = 0;
	View convertview;
	private Handler customHandler = new Handler();
	private Handler customHandler1 = new Handler();
	private Handler customHandler2 = new Handler();
	DecimalFormat dec = new DecimalFormat("#.00");
	DecimalFormat df = new DecimalFormat();
	float dis = 0.0F;
	Function_driver function = new Function_driver();
	GPSTracker gps;
	int int_min_clien_1;
	int int_min_clien_2;
	int int_min_clien_3;
	Button librate;
	private Notification myNotification;
	private NotificationManager notificationManager;
	int number_client = 0;
	double prix_clien_1;
	double prix_clien_2;
	double prix_clien_3;
	ProgressDialog progresse_clacul;
	boolean run = true;
	boolean runs = true;
	private long startTime = 0L;
	private long startTime1 = 0L;
	private long startTime2 = 0L;
	boolean statut_clien_1 = false;
	boolean statut_clien_2 = false;
	boolean statut_clien_3 = false;
	TextView textViewTime;
	TextView time;
	TextView time1;
	TextView time2;
	long timeInMilliseconds = 0L;
	long timeInMilliseconds1 = 0L;
	long timeInMilliseconds2 = 0L;
	long timeSwapBuff = 0L;
	long timeSwapBuff1 = 0L;
	long timeSwapBuff2 = 0L;
	CounterClass timer;
	ImageView pic;
	public ImageLoader imageLoader;
	SupportMapFragment fm;
	LatLng utilis;
	GoogleMap mGoogleMap , mMap;
	private static final String MAP_FRAGMENT_TAG = "map";
	ScrollView vertical_scrollview_id3;
	LinearLayout LL1;
	ImageView transparent_image;
	int orderNo;
	Boolean isNotificationArrive = false;
	
	
	private TextView timerValue;
	ListView clients_list;
	
	TextView depar_client, destin_client;
	Button accept, decline;
	TextView date;
	SharedPreferences sp;
	float speed_km_hr;
	RatingBar ratingBar1;
	Integer arg = null;
	Data_driver driver;
	ImageView logout;
	String id_reservation;
	
	private void showAlertToUser(String paramString) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setMessage(paramString).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						paramAnonymousDialogInterface.cancel();
						
						if(isNotificationArrive == true){
						Intent i = new Intent(Home_driver_new.this , Home_driver_new.class);
			    		startActivity(i);
						}
						
					}
				});
		localBuilder.create().show();
	}
	
	public static ArrayList<HashMap<String, String>> client_list = new ArrayList<HashMap<String, String>>();
	Runnable updateTimerThread = new Runnable() {
		public void run() {
			timeInMilliseconds = (SystemClock.uptimeMillis() - startTime);
			updatedTime = (timeSwapBuff + timeInMilliseconds);
			int i = (int) (updatedTime / 1000L);
			int j = i / 60;
			int k = i % 60;
			int m = (int) (updatedTime % 1000L);
			TextView localTextView = time;
			StringBuilder localStringBuilder1 = new StringBuilder().append(j)
					.append(":");
			Object[] arrayOfObject1 = new Object[1];
			arrayOfObject1[0] = Integer.valueOf(k);
			StringBuilder localStringBuilder2 = localStringBuilder1.append(
					String.format("%02d", arrayOfObject1)).append(":");
			Object[] arrayOfObject2 = new Object[1];
			arrayOfObject2[0] = Integer.valueOf(m);
			localTextView.setText(String.format("%03d", arrayOfObject2));
			customHandler.postDelayed(this, 0L);
		}
	};
	Runnable updateTimerThread1 = new Runnable() {
		public void run() {
			timeInMilliseconds1 = (SystemClock.uptimeMillis() - startTime1);
			updatedTime1 = (timeSwapBuff1 + timeInMilliseconds1);
			int i = (int) (updatedTime1 / 1000L);
			int j = i / 60;
			int k = i % 60;
			int m = (int) (updatedTime1 % 1000L);
			TextView localTextView = time1;
			StringBuilder localStringBuilder1 = new StringBuilder().append(j)
					.append(":");
			Object[] arrayOfObject1 = new Object[1];
			arrayOfObject1[0] = Integer.valueOf(k);
			StringBuilder localStringBuilder2 = localStringBuilder1.append(
					String.format("%02d", arrayOfObject1)).append(":");
			Object[] arrayOfObject2 = new Object[1];
			arrayOfObject2[0] = Integer.valueOf(m);
			localTextView.setText(String.format("%03d", arrayOfObject2));
			customHandler1.postDelayed(this, 0L);
		}
	};
	Runnable updateTimerThread2 = new Runnable() {
		public void run() {
			timeInMilliseconds2 = (SystemClock.uptimeMillis() - startTime2);
			updatedTime2 = (timeSwapBuff2 + timeInMilliseconds2);

			/**
			 * i===> secs = (int) (updatedTime / 1000); j===> mins = secs / 60;
			 * hr = mins / 60; secs = secs % 60;
			 * 
			 */
			int i = (int) (updatedTime2 / 1000L);
			int j = i / 60;
			int k = i % 60;
			int m = (int) (updatedTime2 % 1000L);
			TextView localTextView = time2;
			StringBuilder localStringBuilder1 = new StringBuilder().append(j)
					.append(":");
			Object[] arrayOfObject1 = new Object[1];
			arrayOfObject1[0] = Integer.valueOf(k);
			StringBuilder localStringBuilder2 = localStringBuilder1.append(
					String.format("%02d", arrayOfObject1)).append(":");
			Object[] arrayOfObject2 = new Object[1];
			arrayOfObject2[0] = Integer.valueOf(m);
			localTextView.setText(String.format("%03d", arrayOfObject2));
			customHandler2.postDelayed(this, 0L);
		}
	};
	long updatedTime = 0L;
	long updatedTime1 = 0L;
	long updatedTime2 = 0L;
	LinearLayout vertical_outer_layout_id3;

	
	
	public float get_distance(double paramDouble1, double paramDouble2,
			double paramDouble3, double paramDouble4) {
		float[] arrayOfFloat = new float[1];
		Location.distanceBetween(paramDouble1, paramDouble2, paramDouble3,
				paramDouble4, arrayOfFloat);
		float f = arrayOfFloat[0] / 1000.0F;
		String str = "" + f;
		if (str.length() > 6) {
			f = Float.parseFloat(str.substring(0, 4));
		}
		return f;
	}

	public void onBackPressed() {
		moveTaskToBack(true);
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.home_driver_new);
		imageLoader = new ImageLoader(getApplicationContext());
		
		sp = PreferenceManager
				.getDefaultSharedPreferences(Home_driver_new.this);
		/**
		 * Libere.php
		 */
	//	new update_libere().execute(new Void[0]);

		vertical_outer_layout_id3 = ((LinearLayout) findViewById(R.id.vertical_outer_layout_id3));
		
		vertical_scrollview_id3  = (ScrollView) findViewById(R.id.vertical_scrollview_id3);
		clients_list = (ListView) findViewById(R.id.clients_list);
		LayoutInflater localLayoutInflater = (LayoutInflater) getSystemService("layout_inflater");
		convertview = new View(this);
		librate = ((Button) findViewById(R.id.librate));
		logout = (ImageView) findViewById(R.id.logout);
		convertview = localLayoutInflater.inflate(R.layout.timer_chauffeur,
				null);
		adb = new AlertDialog.Builder(this);
		adb.setCancelable(false);
		adb.setView(this.convertview);
		ad = adb.create();
		btnStart = ((Button) convertview.findViewById(R.id.btnStart));
		btnStop = ((Button) convertview.findViewById(R.id.btnStart));
		textViewTime = ((TextView) convertview.findViewById(R.id.textViewTime));
		textViewTime.setText("00:00:40");
		timer = new CounterClass(40000L, 1000L);
		btnStart.setVisibility(0);
		btnStop.setVisibility(4);
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		
		logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(Home_driver_new.this);
				localBuilder
						.setMessage("Are you sure you want to logout.!")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface paramAnonymousDialogInterface,
											int paramAnonymousInt) {
										
										paramAnonymousDialogInterface.cancel();
										new fin_de_service().execute(new Void[0]);
										
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
		});
		
		vertical_scrollview_id3.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		btnStart.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				new modify_notification_statut(Data_driver.id_reservation)
						.execute(new Void[0]);
				ad.dismiss();
				timer.onFinish();
				run = true;
			}
		});
		btnStop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				run = true;
				timer.cancel();
			}
		});
		librate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				new update_libere().execute(new Void[0]);
			}
		});
	
		final Handler localHandler1 = new Handler();
		localHandler1.postDelayed(new Runnable() {
			public void run() {
				if (Home_driver_new.this.run) {
					gps = new GPSTracker(Home_driver_new.this);
					double d1 = gps.getLatitude();
					double d2 = gps.getLongitude();
					Data_driver.latitude_chauf = d1;
					Data_driver.longitude_chauf = d2;
					
					new update_location().execute(new Void[0]);
				}
				localHandler1.postDelayed(this, 3000L);
			}
		}, 1000L);

		final Handler localHandler2 = new Handler();
		localHandler2.postDelayed(new Runnable() {
			public void run() {
				if (run) {
				
					new get_notification().execute(new Void[0]);
				}
				localHandler2.postDelayed(this, 6000L);
			}
		}, 1000L);
	}

	

	public class CounterClass extends CountDownTimer {
		long countDownInterval;
		long millisInFuture;

		public CounterClass(long paramLong1, long paramLong2) {
			super(paramLong2, paramLong2);
		}

		public void onFinish() {
			textViewTime.setText("Completed.");
			ad.dismiss();
		}

		@SuppressLint({ "NewApi" })
		@TargetApi(9)
		public void onTick(long paramLong) {
			Object[] arrayOfObject = new Object[1];
			arrayOfObject[0] = Long.valueOf(TimeUnit.MILLISECONDS
					.toSeconds(paramLong)
					- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
							.toMinutes(paramLong)));
			String str = String.format("%02d", arrayOfObject);
			textViewTime.setText(str);
		}
	}



	public class delete_client extends AsyncTask<Void, Void, Void> {
		String result;

		public delete_client(String paramString) {
			
			 id_client = paramString;
		}

		protected Void doInBackground(Void... paramVarArgs) {
			this.result = function.delete(id_client);
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			db.dismiss();
			
			
			try {
				
				if(result.equalsIgnoreCase("ok")) {
				vertical_outer_layout_id3.removeAllViews();
				Intent i = new Intent(Home_driver_new.this , Home_driver_new.class);
				startActivity(i);

			
			}
			
		}
			
			catch(Exception ae){
				LayoutInflater localLayoutInflater = (LayoutInflater) getSystemService("layout_inflater");
				convertview = new View(Home_driver_new.this);
			
				
				if (convertview != null) {
					ViewGroup parent = (ViewGroup) convertview.getParent();
			        if (parent != null){
			        
			            parent.removeView(convertview);
			        }
			    }
				try {
					convertview = localLayoutInflater.inflate(R.layout.no_client, null);
					fm = ((SupportMapFragment) getSupportFragmentManager()
							.findFragmentById(R.id.map));
					  fm.getMap();
						mGoogleMap = fm.getMap();
						
						date = (TextView) convertview.findViewById(R.id.date);
						ratingBar1 = (RatingBar) convertview.findViewById(R.id.ratingBar1);
						transparent_image = (ImageView) convertview.findViewById(R.id.transparent_image);
						
						transparent_image.setOnTouchListener(new View.OnTouchListener() {
							
							@Override
							public boolean onTouch(View v, MotionEvent event) {
							
								int action = event.getAction();
						        switch (action) {
						           case MotionEvent.ACTION_DOWN:
						                // Disallow ScrollView to intercept touch events.
						                vertical_scrollview_id3.requestDisallowInterceptTouchEvent(true);
						                // Disable touch on transparent view
						                return false;

						           case MotionEvent.ACTION_UP:
						                // Allow ScrollView to intercept touch events.
						        	   vertical_scrollview_id3.requestDisallowInterceptTouchEvent(false);
						                return true;

						           case MotionEvent.ACTION_MOVE:
						        	   vertical_scrollview_id3.requestDisallowInterceptTouchEvent(true);
						                return false;

						           default: 
						                return true;
						        }   
							}
						});
					
					
					
			      
					
					gps = new GPSTracker(Home_driver_new.this);
					double d1 = gps.getLatitude();
					double d2 = gps.getLongitude();
					
				
					Location location = new Location("Test");
					  location.setLatitude(d1);
					  location.setLongitude(d2);
		
					
					utilis = new LatLng(d1, d2);
					mGoogleMap.moveCamera(CameraUpdateFactory
							.newLatLng(utilis));
					mGoogleMap.animateCamera(CameraUpdateFactory
							.zoomTo(16.0F));

					Marker TP = mGoogleMap.addMarker(new MarkerOptions()
							.position(utilis)
							
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.localisation_taxi)));
					
					String rating = sp.getString("ratings", "");
					
//					/String rating = Data_driver.chauffeur.get("ratings");
				
					float rating_f = Float.valueOf(rating);
					
					ratingBar1.setRating(rating_f);
					

					Calendar c = Calendar.getInstance();
					System.out.println("Current time => " + c.getTime());

					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

					String formattedDate = df.format(c.getTime());
					
					date.setText("Journey  "+formattedDate);
					vertical_outer_layout_id3.addView(convertview);
			    } 
				
				catch (InflateException ae1) {
			        /* map is already there, just return view as it is  */
			    	Log.e("exception ae ==",""+ae1);
			    	
			    	
			    	
			    	try{
			//    	mGoogleMap.clear();
			    	}
			    	
			    	catch(Exception ee){
			    		Log.e("exception ee ==",""+ee);
			    	}
			    	
			    	gps = new GPSTracker(Home_driver_new.this);
					double d1 = gps.getLatitude();
					double d2 = gps.getLongitude();
					
					
					
					Location location = new Location("Test");
					  location.setLatitude(d1);
					  location.setLongitude(d2);
					  
					 

					
					utilis = new LatLng(d1, d2);
					
					try{
					mGoogleMap.moveCamera(CameraUpdateFactory
							.newLatLng(utilis));
					mGoogleMap.animateCamera(CameraUpdateFactory
							.zoomTo(16.0F));

					Marker TP = mGoogleMap.addMarker(new MarkerOptions()
							.position(utilis)
							
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.localisation_taxi)));
					}
					
					catch(Exception eee){
						Log.e("exceptn==",""+eee);
					}
			    }
				
			    

			}
					
		}

		protected void onPreExecute() {
			
			db = new TransparentProgressDialog(Home_driver_new.this, R.drawable.loading);
			db.show();
		}
	}

	public class fin_de_service extends AsyncTask<Void, Void, Void> {
		String result;

		
		protected Void doInBackground(Void... paramVarArgs) {
			
		
			this.result = function.fin_de_service(Data_driver.num_taxi);
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			db.dismiss();
			if (this.result.equalsIgnoreCase("ok")) {
				run = false;
				runs = false;
				finish();
				Intent localIntent = new Intent(Home_driver_new.this,
						Login.class);
				startActivity(localIntent);
			}
		}

		protected void onPreExecute() {
			db = new TransparentProgressDialog(Home_driver_new.this, R.drawable.loading);
			db.show();
		}
	}

	/**
	 * get_notify_reservation.php
	 * 
	 * @author Desktop
	 * 
	 */
	public class get_notification extends AsyncTask<Void, Void, Void> {
		ArrayList<HashMap<String, String>> client_push;

		

		public get_notification() {
		}

		protected Void doInBackground(Void... paramVarArgs) {
			
			this.client_push = new ArrayList<HashMap<String, String>>();

			//Data_driver.client_push1 = new ArrayList<HashMap<String, String>>();
			
			Data_driver.client_push11 = new ArrayList<HashMap<String, String>>();
		
	
			client_push.clear();
			//Data_driver.client_push1.clear();
	
			client_push = function.get_new_ride(Data_driver.num_taxi);

			//Data_driver.client_push1.addAll(client_push);
			Data_driver.client_push11.addAll(client_push);
			
			

		//	Log.e("background result client push==", "" + client_push);
			return null;
		}

		protected void onPostExecute(Void paramVoid) {

			try {
				if (((String) this.client_push.get(0).get("result"))
						.equalsIgnoreCase("OK")) {
					
					Log.e("Data_driver.client_push11", "" + Data_driver.client_push11);
					
				

					

					for (int i = 0; i < client_push.size(); i++) {
						// vertical_outer_layout_id3.removeAllViews();
						
						
						
						if(client_push.get(i).get("statut").equals("-2")){
						
						if(client_push.get(i).get("id_reservation").equals(id_reservation)){
							showAlertToUser("Reservation has been cancelled");
						}else {
						
							showViewAgain();
						}
						}
						
						else {
							Log.d("detail==",""+Data_driver.client_push11.get(i));
							orderNo = i;
							vertical_outer_layout_id3.removeAllViews();
							
							isNotificationArrive = true;
						LayoutInflater localLayoutInflater = (LayoutInflater) getSystemService("layout_inflater");
						convertview = new View(Home_driver_new.this);
						convertview = localLayoutInflater.inflate(
								R.layout.client, null);
						
						pic = (ImageView) convertview
								.findViewById(R.id.pic);
						depar_client = (TextView) convertview
								.findViewById(R.id.depar_client);
						destin_client = (TextView) convertview
								.findViewById(R.id.destin_client);
						accept = (Button) convertview.findViewById(R.id.accept);
						decline = (Button) convertview
								.findViewById(R.id.decline);
						date = (TextView) convertview.findViewById(R.id.date);

						accept.setTag(i);
						decline.setTag(i);

//						Log.i("******depar******",
//								"" + client_push.get(i).get("pick_up"));
//						Log.i("******desti******",
//								"" + client_push.get(i).get("detination"));
						depar_client.setText(client_push.get(i).get("pick_up"));
						destin_client.setText(client_push.get(i).get(
								"detination"));
						
						id_reservation = client_push.get(i).get("id_reservation");
						try{
							Log.e("img=============",""+client_push.get(i).get("image").toString());
						String url = "http://taxi2share.eu/apps/android/webService_post/uploads/"+client_push.get(i).get("image").toString();
						imageLoader.DisplayImage(url, pic);
						}
						
						catch(Exception e){
							Log.e("Exception",""+e);
						}

						Calendar c = Calendar.getInstance();
						System.out.println("Current time => " + c.getTime());

						SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

						String formattedDate = df.format(c.getTime());
						date.setText("Journey  "+formattedDate);

						int height = convertview.getHeight();

						LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
								LinearLayout.LayoutParams.FILL_PARENT,
								LinearLayout.LayoutParams.WRAP_CONTENT);

						layoutParams.setMargins(0, (10 + (height * i)), 0, 0);
						
						
						vertical_outer_layout_id3.addView(convertview,
								layoutParams);
						
						 final MediaPlayer mp = MediaPlayer.create(Home_driver_new.this, R.raw.sound);
						 mp.start();
						

						accept.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								run = false;
								arg = (Integer) v.getTag();
								 mp.stop();
								Log.i("arg arg arg===============", "" + arg);
								new modify_notification_statut(client_push.get(
										orderNo).get("id_reservation"))
										.execute(new Void[0]);

							}
						});
						
						decline.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								arg = (Integer) v.getTag();
								 mp.stop();
								new delete_client(client_push.get(orderNo).get("id_reservation")).execute(new Void[0]);
								
							}
						});
						
					}

					}

				}
				
				////////////////////////////////// NO CLIENT ////////////////////////////////////////////////////

				else {
					vertical_outer_layout_id3.removeAllViews();
					LayoutInflater localLayoutInflater = (LayoutInflater) getSystemService("layout_inflater");
					convertview = new View(Home_driver_new.this);
					convertview = localLayoutInflater.inflate(
							R.layout.no_client, null);
					
					
				//	Toast.makeText(getApplicationContext(), "111111", Toast.LENGTH_SHORT).show();
					
					date = (TextView) convertview.findViewById(R.id.date);
					ratingBar1 = (RatingBar) convertview.findViewById(R.id.ratingBar1);
					LL1 = (LinearLayout)convertview.findViewById(R.id.LL1);
					transparent_image = (ImageView) convertview.findViewById(R.id.transparent_image);
					
					fm = ((SupportMapFragment) getSupportFragmentManager()
							.findFragmentById(R.id.map));
					
					
					
					fm.getMap();
					mGoogleMap = fm.getMap();
					
					transparent_image.setOnTouchListener(new View.OnTouchListener() {
						
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							
							
							int action = event.getAction();
					        switch (action) {
					           case MotionEvent.ACTION_DOWN:
					                // Disallow ScrollView to intercept touch events.
					                vertical_scrollview_id3.requestDisallowInterceptTouchEvent(true);
					                // Disable touch on transparent view
					                return false;

					           case MotionEvent.ACTION_UP:
					                // Allow ScrollView to intercept touch events.
					        	   vertical_scrollview_id3.requestDisallowInterceptTouchEvent(false);
					                return true;

					           case MotionEvent.ACTION_MOVE:
					        	   vertical_scrollview_id3.requestDisallowInterceptTouchEvent(true);
					                return false;

					           default: 
					                return true;
					        }   
						}
					});
				
					
					gps = new GPSTracker(Home_driver_new.this);
					double d1 = gps.getLatitude();
					double d2 = gps.getLongitude();
					
					Location location = new Location("Test");
					  location.setLatitude(d1);
					  location.setLongitude(d2);
					   

					  
					
					
					utilis = new LatLng(d1, d2);
					mGoogleMap.moveCamera(CameraUpdateFactory
							.newLatLng(utilis));
					mGoogleMap.animateCamera(CameraUpdateFactory
							.zoomTo(16.0F));

					Marker TP = mGoogleMap.addMarker(new MarkerOptions()
							.position(utilis)
							.title("client")
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.localisation_taxi)));
				
					
					String rating = sp.getString("ratings", "");
					Log.e("rating======",""+rating);
					float rating_f = Float.valueOf(rating);
					Log.e("rating======",""+rating_f);
					ratingBar1.setRating(rating_f);
				
					
					Calendar c = Calendar.getInstance();
					System.out.println("Current time => " + c.getTime());

					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

					String formattedDate = df.format(c.getTime());
					Log.i("date==", "" + formattedDate);
					date.setText("Journey  "+formattedDate);
					vertical_outer_layout_id3.addView(convertview);
				}
			}

			catch (Exception e) {
				
			//	Toast.makeText(getApplicationContext(), "2222", Toast.LENGTH_SHORT).show();

				LayoutInflater localLayoutInflater = (LayoutInflater) getSystemService("layout_inflater");
				convertview = new View(Home_driver_new.this);
			
				
				if (convertview != null) {
					ViewGroup parent = (ViewGroup) convertview.getParent();
			        if (parent != null){
			        	Log.e("parent != null","parent != null");
			            parent.removeView(convertview);
			        }
			    }
				try {
					convertview = localLayoutInflater.inflate(R.layout.no_client, null);
					fm = ((SupportMapFragment) getSupportFragmentManager()
							.findFragmentById(R.id.map));
					  fm.getMap();
						mGoogleMap = fm.getMap();
						
						date = (TextView) convertview.findViewById(R.id.date);
						ratingBar1 = (RatingBar) convertview.findViewById(R.id.ratingBar1);
						transparent_image = (ImageView) convertview.findViewById(R.id.transparent_image);
						
						transparent_image.setOnTouchListener(new View.OnTouchListener() {
							
							@Override
							public boolean onTouch(View v, MotionEvent event) {
								
								Log.e("on LL1 touch","on LL1 touch");
								int action = event.getAction();
						        switch (action) {
						           case MotionEvent.ACTION_DOWN:
						                // Disallow ScrollView to intercept touch events.
						                vertical_scrollview_id3.requestDisallowInterceptTouchEvent(true);
						                // Disable touch on transparent view
						                return false;

						           case MotionEvent.ACTION_UP:
						                // Allow ScrollView to intercept touch events.
						        	   vertical_scrollview_id3.requestDisallowInterceptTouchEvent(false);
						                return true;

						           case MotionEvent.ACTION_MOVE:
						        	   vertical_scrollview_id3.requestDisallowInterceptTouchEvent(true);
						                return false;

						           default: 
						                return true;
						        }   
							}
						});
					
					
					
			      
					
					gps = new GPSTracker(Home_driver_new.this);
					double d1 = gps.getLatitude();
					double d2 = gps.getLongitude();
					
					Log.e("d1(Lat)",""+d1);
					Log.e("d2(Lng)",""+d2);
					
					Location location = new Location("Test");
					  location.setLatitude(d1);
					  location.setLongitude(d2);
					   

					
					utilis = new LatLng(d1, d2);
					mGoogleMap.moveCamera(CameraUpdateFactory
							.newLatLng(utilis));
					mGoogleMap.animateCamera(CameraUpdateFactory
							.zoomTo(16.0F));

					Marker TP = mGoogleMap.addMarker(new MarkerOptions()
							.position(utilis)
							
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.localisation_taxi)));
					
					String rating = sp.getString("ratings", "");
					

					Log.e("rating======",""+rating);
					float rating_f = Float.valueOf(rating);
					Log.e("rating======",""+rating_f);
					ratingBar1.setRating(rating_f);
					

					Calendar c = Calendar.getInstance();
					System.out.println("Current time => " + c.getTime());

					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

					String formattedDate = df.format(c.getTime());
					Log.i("date==", "" + formattedDate);
					date.setText("Journey  "+formattedDate);
					vertical_outer_layout_id3.addView(convertview);
			    } 
				
				catch (InflateException ae) {
			        /* map is already there, just return view as it is  */
			    	Log.e("exception ae ==",""+ae);
		//	    	Toast.makeText(getApplicationContext(), "33333", Toast.LENGTH_SHORT).show();
			    	
			    	try{
			    	mGoogleMap.clear();
			    	}
			    	
			    	catch(Exception ee){
			    		Log.e("exception ee ==",""+ee);
			    	}
			    	
			    	gps = new GPSTracker(Home_driver_new.this);
					double d1 = gps.getLatitude();
					double d2 = gps.getLongitude();
					
					Log.e("d1(Lat)",""+d1);
					Log.e("d2(Lng)",""+d2);
					
					Location location = new Location("Test");
					  location.setLatitude(d1);
					  location.setLongitude(d2);
					  
					 
	
					
					
					utilis = new LatLng(d1, d2);
					
					try{
						
					//	Toast.makeText(getApplicationContext(), "444444", Toast.LENGTH_SHORT).show();
					mGoogleMap.moveCamera(CameraUpdateFactory
							.newLatLng(utilis));
					mGoogleMap.animateCamera(CameraUpdateFactory
							.zoomTo(16.0F));

					Marker TP = mGoogleMap.addMarker(new MarkerOptions()
							.position(utilis)
							
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.localisation_taxi)));
					}
					
					catch(Exception eee){
						Log.e("exceptn=*******=",""+eee);
						
					//	Toast.makeText(getApplicationContext(), "55555", Toast.LENGTH_SHORT).show();
					}
			    }
				
			        
			      
			}
			/////////////////////////////////////////////////////////////////////////////////////////////
		}

		protected void onPreExecute() {
		}
	}

	/**
	 * confirm_it.php
	 * 
	 * @author Desktop
	 * 
	 */
	public class modify_notification_statut extends AsyncTask<Void, Void, Void> {
		String id_reservation;
		String result;

		public modify_notification_statut(String paramString) {
			this.id_reservation = paramString;
		}

		protected Void doInBackground(Void... paramVarArgs) {
			this.result = function.update_statut(this.id_reservation);
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			

			db.dismiss();
			if (this.result.equalsIgnoreCase("ok")) {

				finish();
			
				Intent i = new Intent(Home_driver_new.this,
						Home_driver_new1.class);
				i.putExtra("arg", orderNo);
				startActivity(i);

			}
		}

		protected void onPreExecute() {

			db = new TransparentProgressDialog(Home_driver_new.this,
					R.drawable.loading);
			db.show();
		}
	}

	/**
	 * libere.php
	 * 
	 * @author Desktop
	 * 
	 */
	public class update_libere extends AsyncTask<Void, Void, Void> {
		String result;

		public update_libere() {
		}

		protected Void doInBackground(Void... paramVarArgs) {
			this.result = function.update_libre(Data_driver.num_taxi);
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			charge.dismiss();
			if (this.result.equalsIgnoreCase("ok")) {
				charge.dismiss();
				number_client = 0;
				
				
				
				
				Rang_client.clien_1_with_client3 = false;
				Rang_client.clien_2_with_client3 = false;
				Rang_client.client1 = 0;
				Rang_client.client2 = 0;
				Rang_client.client3 = 0;
				
				
				//return;
				try{
				if(Data_driver.client_push11.size()>0){
					Log.e("client push11===",""+Data_driver.client_push11);
				Data_driver.client_push11.clear();
				vertical_outer_layout_id3.removeAllViews();
				Intent i = new Intent(Home_driver_new.this , Home_driver_new.class);
					startActivity(i);
				//showViewAgain();
				}
			//	Intent i = new Intent(Home_driver_new.this , Home_driver_new.class);
			//	startActivity(i);
				
				
					
				}
				catch(Exception e){
					Log.e("Exception e==",""+e);
				}
				
			}
			charge.dismiss();
		}

		protected void onPreExecute() {
			charge = new TransparentProgressDialog(Home_driver_new.this,
					R.drawable.loading);
			charge.show();
		}
	}

	/**
	 * update_location_taxi.php
	 */
	public class update_location extends AsyncTask<Void, Void, Void> {
		String result;

		public update_location() {
		}

		protected Void doInBackground(Void... paramVarArgs) {
			this.result = function.update_location(Data_driver.num_taxi,
					Data_driver.latitude_chauf, Data_driver.longitude_chauf);
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			this.result.equalsIgnoreCase("OK");
		}

		protected void onPreExecute() {
		}
	}


	public void showViewAgain() {

		LayoutInflater localLayoutInflater = (LayoutInflater) getSystemService("layout_inflater");
		convertview = new View(Home_driver_new.this);
	
		
		if (convertview != null) {
			ViewGroup parent = (ViewGroup) convertview.getParent();
	        if (parent != null){
	        	Log.e("parent != null","parent != null");
	            parent.removeView(convertview);
	        }
	    }
		try {
			convertview = localLayoutInflater.inflate(R.layout.no_client, null);
			fm = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map));
			  fm.getMap();
				mGoogleMap = fm.getMap();
				
				date = (TextView) convertview.findViewById(R.id.date);
				ratingBar1 = (RatingBar) convertview.findViewById(R.id.ratingBar1);
				transparent_image = (ImageView) convertview.findViewById(R.id.transparent_image);
				
				transparent_image.setOnTouchListener(new View.OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						
						Log.e("on LL1 touch","on LL1 touch");
						int action = event.getAction();
				        switch (action) {
				           case MotionEvent.ACTION_DOWN:
				                // Disallow ScrollView to intercept touch events.
				                vertical_scrollview_id3.requestDisallowInterceptTouchEvent(true);
				                // Disable touch on transparent view
				                return false;

				           case MotionEvent.ACTION_UP:
				                // Allow ScrollView to intercept touch events.
				        	   vertical_scrollview_id3.requestDisallowInterceptTouchEvent(false);
				                return true;

				           case MotionEvent.ACTION_MOVE:
				        	   vertical_scrollview_id3.requestDisallowInterceptTouchEvent(true);
				                return false;

				           default: 
				                return true;
				        }   
					}
				});
			
			
			
	      
			
			gps = new GPSTracker(Home_driver_new.this);
			double d1 = gps.getLatitude();
			double d2 = gps.getLongitude();
			
			Log.e("d1(Lat)",""+d1);
			Log.e("d2(Lng)",""+d2);
			
			Location location = new Location("Test");
			  location.setLatitude(d1);
			  location.setLongitude(d2);
			   

			
			utilis = new LatLng(d1, d2);
			mGoogleMap.moveCamera(CameraUpdateFactory
					.newLatLng(utilis));
			mGoogleMap.animateCamera(CameraUpdateFactory
					.zoomTo(16.0F));

			Marker TP = mGoogleMap.addMarker(new MarkerOptions()
					.position(utilis)
					
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.localisation_taxi)));
			
			String rating = sp.getString("ratings", "");
			

			Log.e("rating======",""+rating);
			float rating_f = Float.valueOf(rating);
			Log.e("rating======",""+rating_f);
			ratingBar1.setRating(rating_f);
			

			Calendar c = Calendar.getInstance();
			System.out.println("Current time => " + c.getTime());

			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			String formattedDate = df.format(c.getTime());
			Log.i("date==", "" + formattedDate);
			date.setText("Journey  "+formattedDate);
			vertical_outer_layout_id3.addView(convertview);
	    } 
		
		catch (InflateException ae) {
	        /* map is already there, just return view as it is  */
	    	Log.e("exception ae ==",""+ae);
//	    	Toast.makeText(getApplicationContext(), "33333", Toast.LENGTH_SHORT).show();
	    	
	    	try{
	    	mGoogleMap.clear();
	    	}
	    	
	    	catch(Exception ee){
	    		Log.e("exception ee ==",""+ee);
	    	}
	    	
	    	gps = new GPSTracker(Home_driver_new.this);
			double d1 = gps.getLatitude();
			double d2 = gps.getLongitude();
			
			Log.e("d1(Lat)",""+d1);
			Log.e("d2(Lng)",""+d2);
			
			Location location = new Location("Test");
			  location.setLatitude(d1);
			  location.setLongitude(d2);
			  
			 

			
			
			utilis = new LatLng(d1, d2);
			
			try{
				
			//	Toast.makeText(getApplicationContext(), "444444", Toast.LENGTH_SHORT).show();
			mGoogleMap.moveCamera(CameraUpdateFactory
					.newLatLng(utilis));
			mGoogleMap.animateCamera(CameraUpdateFactory
					.zoomTo(16.0F));

			Marker TP = mGoogleMap.addMarker(new MarkerOptions()
					.position(utilis)
					
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.localisation_taxi)));
			}
			
			catch(Exception eee){
				Log.e("exceptn=*******=",""+eee);
				
			//	Toast.makeText(getApplicationContext(), "55555", Toast.LENGTH_SHORT).show();
			}
	    }
		
	    
		
	}
	
	
}
