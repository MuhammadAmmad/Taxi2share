package taxi2share.eu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.message.BasicNameValuePair;


import taxi2share.eu.biz.Data_driver;
import taxi2share.eu.biz.Function_driver;
import taxi2share.eu.biz.GPSTracker;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.macrew.Utils.TransparentProgressDialog;
import com.macrew.imageloader.ImageLoader;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import taxi2share.eu.R;
public class Home_driver_new1 extends FragmentActivity {

	TextView date, timer;
	SupportMapFragment fm;
	GoogleMap mGoogleMap;
	Function_driver function = new Function_driver();
	int arg;
	MarkerOptions option_taxi;
	double latitude, longitude;
	LatLng utilis;
	LinearLayout vertical_outer_layout_id3;
	View convertview;
	TextView depar_client, destin_client, nbr_km, price, driving_time, name,
			f_name, phone_nmber1,phone_nmber2,phone_nmber3, pass;
	Button counter1,counter2,counter3;
	ImageView pic;
	TimerTask mTimerTask1;
	TimerTask mTimerTask2;
	TimerTask mTimerTask3;
	final Handler handler = new Handler();
	Timer t = new Timer();
	private int nCounter1 = 0;
	private int nCounter2 = 0;
	private int nCounter3 = 0;
	
	private int nCounter1_less = 0;
	private int nCounter2_less = 0;
	private int nCounter3_less = 0;
	
	private int nCounter1_more = 0;
	private int nCounter2_more = 0;
	private int nCounter3_more = 0;
	Button librate;
	TransparentProgressDialog charge;
	int number_client = 0;
	LatLng localLatLng3 , latLngClient2 , latLngClient3;
	TransparentProgressDialog db;
	
	Button stop1, stop2 , stop3;
	TextView timer1, timer2 , timer3;
	Boolean isShownFirstTime = true;
	Boolean isDialogShowing = false;
	GPSTracker gps;
	ArrayList<String> client_id = new ArrayList<String>();
	HashMap loyality_hashmap;	
	 MediaPlayer mp ;
	 public ImageLoader imageLoader;
	 float speed_km_hr;
	int counter_value[] = new int[3];

	int aloneTime1;
	Double lat1;
	Double lng1;

	Double lat2;
	Double lng2;

	Double lat3;
	Double lng3;
	double dist;
	int no_of_clients = 0;
	double actualPrice =  0.0;
	
	double totalTime1 = 0;
	double totalTime2 = 0;
	
	double totalDistance =0.0;
	
	double distance1=0 , distance2=0 , distance3=0;
	
	double price_km , price_min, boarding_fee;
	
	String id_reservation="";

	ArrayList<HashMap<String, String>> client_push2 = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> client_push3 = new ArrayList<HashMap<String, String>>();

	// macrew

	boolean run = true;
	boolean isOfferAccepted = false;
	Button accept, decline;
	boolean less = true;
	Boolean client1_status = false;
	Boolean client2_status = false;
	Boolean client3_status = false;
	
	Boolean client3 = false , client2 = false;
	
	
	/****** KEYS *********/
	String aloneDistance = "aloneDistance" , aloneTime = "aloneTime";
	String timeSharing12 = "timeSharing12" , distanceSharing12 = "distanceSharing12" ;
	String timeSharing13 = "timeSharing13" , distanceSharing13 = "distanceSharing13" ;
	String timeSharing23 = "timeSharing23" , distanceSharing23 = "distanceSharing23" ;
	String timeSharing123 = "timeSharing123" , distanceSharing123 = "distanceSharing123" ;
	
	String timeSharing12_g20 = "timeSharing12_g20" , timeSharing12_l20="timeSharing12_l20";
	String timeSharing13_g20 = "timeSharing13_g20" ,  timeSharing13_l20="timeSharing13_l20";
	String timeSharing123_g20 = "timeSharing123_g20" , timeSharing123_l20="timeSharing123_l20";
	String aloneTime_g20 = "aloneTime_g20" , aloneTime_l20 = "aloneTime_l20";
	
	String timeSharing23_g20 = "timeSharing23_g20" , timeSharing23_l20 = "timeSharing23_l20" ;
	
	double price1, price2, price3;

	
	ImageView transparent_image;
	ScrollView SS1;

	public void retour(View paramView) {
		moveTaskToBack(true);
	}
	
	private void showAlertToUser(String paramString) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setMessage(paramString).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						paramAnonymousDialogInterface.cancel();
						try{
						mp.stop();
						}
						catch(Exception e){
							
						}
						
					}
				});
		localBuilder.create().show();
	}
	
	private void showAlertToUser1(String paramString) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setMessage(paramString).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						paramAnonymousDialogInterface.cancel();
						try{
						mp.stop();
						}catch(Exception e){
							
						}
						Intent i = new Intent(Home_driver_new1.this , Home_driver_new.class);
						
			    		startActivity(i);
					}
				});
		localBuilder.create().show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_o);
		imageLoader = new ImageLoader(getApplicationContext());
		SS1 = (ScrollView) findViewById(R.id.SS1);
		transparent_image = (ImageView) findViewById(R.id.transparent_image);
		
		loyality_hashmap= new HashMap();

		librate = (Button) findViewById(R.id.librate);
		
		Intent intent = getIntent();
		//arg = intent.getIntExtra("arg", 0);
		arg = 0;
		
		int size = Data_driver.client_push11.size();
		Log.d("client_push11===", "" + Data_driver.client_push11);
		Log.e("size===", "" + Data_driver.client_push11.size());
		for (int i = 0; i < size; i++) {
			Log.e("i===", ""+i);
			if(Data_driver.client_push11.get(0).get("statut").equals("-2")){
				Data_driver.client_push11.remove(0);
			}
			
		}
		Log.d("client_push11===", "" + Data_driver.client_push11);
		Log.e("size===", "" + Data_driver.client_push11.size());
		
		transparent_image.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
			
				int action = event.getAction();
		        switch (action) {
		           case MotionEvent.ACTION_DOWN:
		                // Disallow ScrollView to intercept touch events.
		                SS1.requestDisallowInterceptTouchEvent(true);
		                // Disable touch on transparent view
		                return false;

		           case MotionEvent.ACTION_UP:
		                // Allow ScrollView to intercept touch events.
		        	   SS1.requestDisallowInterceptTouchEvent(false);
		                return true;

		           case MotionEvent.ACTION_MOVE:
		        	   SS1.requestDisallowInterceptTouchEvent(true);
		                return false;

		           default: 
		                return true;
		        }   
			}
		});
		
		HashMap map2;
		map2 = new HashMap();
		map2.put(timeSharing12,"0");
		map2.put(timeSharing23,"0");
		map2.put(distanceSharing23,"0");
		map2.put(distanceSharing12,"0");
		map2.put(distanceSharing13,"0");
		map2.put(timeSharing13, "0");
		map2.put(distanceSharing123, "0");
		map2.put(timeSharing123,"0");
		map2.put(aloneTime,"0");
		map2.put(aloneDistance,"0");
		
		map2.put(aloneTime_g20, "0");
		map2.put(aloneTime_l20, "0");
		map2.put(timeSharing12_g20, "0");
		map2.put(timeSharing12_l20, "0");
		map2.put(timeSharing13_g20, "0");
		map2.put(timeSharing13_l20, "0");
		map2.put(timeSharing123_g20, "0");
		map2.put(timeSharing123_l20, "0");
		map2.put(timeSharing23_g20, "0");
		map2.put(timeSharing23_l20, "0");
		client_push2.add(map2);
		
		
		HashMap map3;
		map3 = new HashMap();
		
		map3.put(timeSharing12,"0");
		map3.put(distanceSharing12,"0");
		map3.put(distanceSharing13,"0");
		map3.put(timeSharing13, "0");
		map3.put(distanceSharing123,"0");
		map3.put(timeSharing123,"0");
		map3.put(aloneTime, "0");
		map3.put(aloneDistance,"0");
		map3.put(timeSharing23,"0");
		map3.put(distanceSharing23,"0");
		
		map3.put(aloneTime_g20, "0");
		map3.put(aloneTime_l20, "0");
		map3.put(timeSharing12_g20, "0");
		map3.put(timeSharing12_l20, "0");
		map3.put(timeSharing13_g20, "0");
		map3.put(timeSharing13_l20, "0");
		map3.put(timeSharing123_g20, "0");
		map3.put(timeSharing123_l20, "0");
		map3.put(timeSharing23_g20, "0");
		map3.put(timeSharing23_l20, "0");
		client_push3.add(map3);
		
		fm = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map));
		fm.getMap();
		mGoogleMap = fm.getMap();
		mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
		mGoogleMap.setMyLocationEnabled(true);
		
		
		gps = new GPSTracker(Home_driver_new1.this);
		double d1 = gps.getLatitude();
		double d2 = gps.getLongitude();
		utilis = new LatLng(d1, d2);
		mGoogleMap.moveCamera(CameraUpdateFactory
				.newLatLng(utilis));
		mGoogleMap.animateCamera(CameraUpdateFactory
				.zoomTo(9.0F));

		Marker TP = mGoogleMap.addMarker(new MarkerOptions()
				.position(utilis)
				.title("client")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.localisation_user)));
		
		
	
		
		
		client_id.add(Data_driver.client_push11.get(arg).get("id_reservation"));

		final Handler localHandler2 = new Handler();
		localHandler2.postDelayed(new Runnable() {
			public void run() {
				new get_notification().execute(new Void[0]);
			
				localHandler2.postDelayed(this, 6000L);
				
			}
		}, 1000L);
		
		final Handler localHandler1 = new Handler();
		localHandler1.postDelayed(new Runnable() {
			public void run() {
				
					gps = new GPSTracker(Home_driver_new1.this);
					double d1 = gps.getLatitude();
					double d2 = gps.getLongitude();
					Data_driver.latitude_chauf = d1;
					Data_driver.longitude_chauf = d2;
					
					new update_location().execute(new Void[0]);
				
				localHandler1.postDelayed(this, 6000L);
			}
		}, 1000L);
		

		
		

		librate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// new update_libere().execute(new Void[0]);
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(
						Home_driver_new1.this);
				localBuilder
						.setMessage(
								"Are you sure you want to go to Home screen?")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface paramAnonymousDialogInterface,
											int paramAnonymousInt) {
										
										vertical_outer_layout_id3.removeAllViews();
										new update_libere().execute(new Void[0]);
						
										paramAnonymousDialogInterface
												.cancel();
									}
								});
				localBuilder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymousDialogInterface,
									int paramAnonymousInt) {
								paramAnonymousDialogInterface
								.cancel();
							}
						});
				localBuilder.create().show();

			}
		});

		
	

		date = (TextView) findViewById(R.id.date);
		vertical_outer_layout_id3 = (LinearLayout) findViewById(R.id.vertical_outer_layout_id3);

		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		String formattedDate = df.format(c.getTime());
		date.setText("Journey  " + formattedDate);

		fm = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map));
		fm.getMap();
		mGoogleMap = fm.getMap();
		mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
		mGoogleMap.setMyLocationEnabled(true);

		double lati = Double.parseDouble(Data_driver.client_push11.get(arg).get(
				"latitude_client_dep"));
		double longLat = Double.parseDouble(Data_driver.client_push11.get(arg)
				.get("longitude_client_dep"));

		option_taxi = new MarkerOptions();

		localLatLng3 = new LatLng(lati, longLat);
		
		
		

		mGoogleMap
				.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

					public void onMyLocationChange(
							Location paramAnonymousLocation) {
						latitude = paramAnonymousLocation.getLatitude();
						longitude = paramAnonymousLocation.getLongitude();

						mGoogleMap.clear();
						
					 
						  
						 
							
					speed_km_hr = ((paramAnonymousLocation.getSpeed()*3600)/1000);
						  
//						Toast.makeText(getApplicationContext(), "Current speed:" + speed_km_hr,
//								Toast.LENGTH_SHORT).show();

						utilis = new LatLng(latitude, longitude);
//						mGoogleMap.moveCamera(CameraUpdateFactory
//								.newLatLng(utilis));
//						mGoogleMap.animateCamera(CameraUpdateFactory
//								.zoomTo(11.0F));

						Marker TP = mGoogleMap.addMarker(new MarkerOptions()
								.position(utilis)

								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.localisation_taxi)));

						option_taxi.position(localLatLng3);
						option_taxi.title("Client 1");
						option_taxi.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.localisation_user));
						mGoogleMap.addMarker(Home_driver_new1.this.option_taxi);
						
						if(client2 && client3){


							Log.e("size 2==",""+client_push2.size());
							Log.e("size 3==",""+client_push3.size());
							option_taxi.position(latLngClient2);
							option_taxi.title("Client 2");
							option_taxi.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.localisation_user));
							mGoogleMap.addMarker(Home_driver_new1.this.option_taxi);
							
							option_taxi.position(latLngClient3);
							option_taxi.title("Client 3");
							option_taxi.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.localisation_user));
							mGoogleMap.addMarker(Home_driver_new1.this.option_taxi);
						}
						else if(client2 && client3==false){
							option_taxi.position(latLngClient2);
							option_taxi.title("Client 2");
							option_taxi.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.localisation_user));
							mGoogleMap.addMarker(Home_driver_new1.this.option_taxi);
						}
						
						else if(client2==false && client3){
							option_taxi.position(latLngClient3);
							option_taxi.title("Client 3");
							option_taxi.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.localisation_user));
							mGoogleMap.addMarker(Home_driver_new1.this.option_taxi);
						}
						
					}

				});
		
		final Handler localHandler = new Handler();
		localHandler.postDelayed(new Runnable() {
			public void run() {
				 if(speed_km_hr > 20){
						
					  nCounter1_more = nCounter1-nCounter1_less;
					 
					  nCounter2_more = nCounter2-nCounter2_less;
					  nCounter3_more = nCounter3-nCounter3_less;
				  }
				  
				  else if(speed_km_hr <= 20){
						 nCounter1_less = nCounter1-nCounter1_more;

						 
						 nCounter2_less = nCounter2-nCounter2_more;

						
						 nCounter3_less = nCounter3-nCounter3_more;
						
					  }
				localHandler.postDelayed(this, 1000L);
			}
		}, 1000L);

		/************************** CLIENT 1 **************************************************************************/

		LayoutInflater localLayoutInflater = (LayoutInflater) getSystemService("layout_inflater");
		convertview = new View(Home_driver_new1.this);
		TextView boarding1 , driving_time1;
		
	
		
		convertview = localLayoutInflater.inflate(R.layout.client_new_o, null);
		depar_client = (TextView) convertview.findViewById(R.id.depar_client);
		destin_client = (TextView) convertview.findViewById(R.id.destin_client);
		boarding1 = (TextView) convertview.findViewById(R.id.boarding1);
		driving_time1 = (TextView) convertview.findViewById(R.id.driving_time1);
		

		counter1 = (Button) convertview.findViewById(R.id.counter1);
		pic = (ImageView) convertview.findViewById(R.id.pic);
		stop1 = (Button) convertview.findViewById(R.id.stop1);
		timer1 = (TextView) convertview.findViewById(R.id.timer1);
		nbr_km = (TextView) convertview.findViewById(R.id.nbr_km);
		price = (TextView) convertview.findViewById(R.id.price);

		name = (TextView) convertview.findViewById(R.id.name);
		f_name = (TextView) convertview.findViewById(R.id.f_name);
		phone_nmber1 = (TextView) convertview.findViewById(R.id.phone_number1);

		
		depar_client.setText(Data_driver.client_push11.get(arg).get("pick_up"));
		destin_client.setText(Data_driver.client_push11.get(arg).get(
				"detination"));
		boarding1.setText(Data_driver.client_push11.get(arg).get("boarding_fee")+" €");
		driving_time1.setText(Data_driver.client_push11.get(arg).get("price_per_minute")+" €/M");
		
		price_km  = Double.parseDouble(Data_driver.client_push11.get(arg).get("price_per_km"));
		
		try{
		
			String url = "http://taxi2share.eu/apps/android/webService_post/uploads/"+Data_driver.client_push11.get(arg).get("image").toString();
			imageLoader.DisplayImage(url, pic);
			}
			
			catch(Exception e){
				Log.e("Exception",""+e);
			}

		name.setText(Data_driver.client_push11.get(arg).get("name_C"));
		f_name.setText(Data_driver.client_push11.get(arg).get("last_name_C"));
		
		SpannableString content1 = new SpannableString(Data_driver.client_push11.get(arg).get("tel_C"));
		content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0); 
		phone_nmber1.setText(content1);
		
		phone_nmber1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String number = "tel:" + Data_driver.client_push11.get(arg).get("tel_C");
				Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number)); 
		        startActivity(callIntent);
			
			}
		});
		
		

		vertical_outer_layout_id3.addView(convertview);

		Log.i("client_push1===", "" + Data_driver.client_push11);

		float f = get_distance(
				Double.parseDouble(Data_driver.client_push11.get(arg).get(
						"latitude_client_dep")),
				Double.parseDouble(Data_driver.client_push11.get(arg).get(
						"longitude_client_dep")),
				Double.parseDouble(Data_driver.client_push11.get(arg).get(
						"latitude_client_des")),
				Double.parseDouble(Data_driver.client_push11.get(arg).get(
						"longitude_client_des")));
		
		distance1 = f;
		
		Log.i("1 latitude dep==",""+Data_driver.client_push11.get(arg).get("latitude_client_dep"));
		Log.i("2 longitude dep==",""+Data_driver.client_push11.get(arg).get("longitude_client_dep"));
		Log.i("1 latitude des==",""+Data_driver.client_push11.get(arg).get("latitude_client_des"));
		Log.i("2 longitude des==",""+Data_driver.client_push11.get(arg).get("longitude_client_des"));

		double roundOff_dist = (double) Math.round(f * 100) / 100;
		nbr_km.setText(Data_driver.client_push11.get(arg).get("distance") + " Km");
		double roundOff = (double) Math.round((price_km * Double.parseDouble(Data_driver.client_push11.get(arg).get("distance"))) * 100) / 100;

		price.setText("" + roundOff + " €");
		
	
		counter1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				client1_status = true;
//				if (isOfferAccepted)
//					return;
				isOfferAccepted = true;
				doTimerTask1();
				stop1.setVisibility(View.VISIBLE);
				counter1.setVisibility(View.INVISIBLE);
				no_of_clients++;

			}
		});

		stop1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				no_of_clients--;
				
			

				stop1.setVisibility(View.INVISIBLE);

				lat1 = localLatLng3.latitude;
				lng1 = localLatLng3.longitude;

				stopTask1();

				

				
			}

			protected void stopTask1() {
				if (mTimerTask1 != null) {

					int secs = nCounter1;
					int mins = secs / 60;
					int hr = mins / 60;
					secs = secs % 60;

					counter_value[0] = nCounter1;

					timer1.setText("" + hr + ":" + String.format("%02d", mins)
							+ ":" + String.format("%02d", secs));
					// timer1.setText("" + nCounter1);

				
					mTimerTask1.cancel();
					
					price_km  = Double.parseDouble(Data_driver.client_push11.get(arg).get("price_per_km"));
					price_min =  Double.parseDouble(Data_driver.client_push11.get(arg).get("price_per_minute"));
					boarding_fee = Double.parseDouble(Data_driver.client_push11.get(arg).get("boarding_fee"));
					
					price1 = calculationOnStop(1);
					
					
					Log.e("client 1(calculation)",""+Data_driver.client_push11);
					
					 totalDistance  = Double.parseDouble(Data_driver.client_push11.get(0).get("distance"));
					 
					 actualPrice = Double.parseDouble(Data_driver.client_push11.get(0)
							.get("Prix"));
					 
					
				
					 totalTime1 = (double)nCounter1_more/60;
					 totalTime2 = (double)nCounter1_less/60;
					// totalTime = (double) Math.round(totalTime * 100) / 100;
					 
					new envoie_prix(String.valueOf(price1), Data_driver.client_push11
							.get(0).get("id_client"), Data_driver.client_push11
							.get(0).get("num_taxi"), Data_driver.client_push11.get(
							0).get("id_reservation"),String.valueOf(nCounter1),
							String.valueOf(nCounter1_more),String.valueOf(nCounter1_less)).execute(new Void[0]);

				}

			}
		});


		
	

	}




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

	protected void doTimerTask1() {
		calculationOnAccept(1);
		mTimerTask1 = new TimerTask() {
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						nCounter1++;
						// update TextView
						
				
						int secs = nCounter1;
						int mins = secs / 60;
						int hr = mins / 60;
						secs = secs % 60;

						timer1.setText("" + hr + ":"
								+ String.format("%02d", mins) + ":"
								+ String.format("%02d", secs));
					
					}
				});
			}
		};

		// public void schedule (TimerTask task, long delay, long period)
		t.schedule(mTimerTask1, 0, 1000); //
		
		id_reservation =Data_driver.client_push11.get(0).get("id_reservation");
		new startTimer(id_reservation).execute(new Void[0]);

	}

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
				vertical_outer_layout_id3.removeAllViews();
				Rang_client.clien_1_with_client3 = false;
				Rang_client.clien_2_with_client3 = false;
				Rang_client.client1 = 0;
				Rang_client.client2 = 0;
				Rang_client.client3 = 0;
				
				//Data_driver.client_push1.clear();
				
				Data_driver.client_push11.clear();
				try{
				if(client_push2.size()>0){
				client_push2.clear();
				}
				if(client_push3.size()>0){
				client_push3.clear();
				}
				}
				catch(Exception e){
					
				}
				Intent i = new Intent(Home_driver_new1.this,
						Home_driver_new.class);
				startActivity(i);
				return;
			}
			charge.dismiss();
		}

		protected void onPreExecute() {
			charge = new TransparentProgressDialog(Home_driver_new1.this,
					R.drawable.loading);
			charge.show();
		}
	}

	public class get_notification extends AsyncTask<Void, Void, Void> {
		ArrayList<HashMap<String, String>> client_push;

	

		public get_notification() {
		}

		protected Void doInBackground(Void... paramVarArgs) {

			this.client_push = new ArrayList<HashMap<String, String>>();

			//Data_driver.client_push1 = new ArrayList<HashMap<String, String>>();

			client_push.clear();
			//Data_driver.client_push1.clear();
			client_push = function.get_new_ride(Data_driver.num_taxi);

			//Data_driver.client_push1.addAll(client_push);

			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			
			Log.d("client_push1(1 screen)===", "" + Data_driver.client_push11);
			try {
				if (((String) this.client_push.get(0).get("result"))
						.equalsIgnoreCase("OK")) {
						
					
					
						
						for(int i = 0; i<client_push.size();i++){
								
								
								if(client_push.get(i).get("statut").equals("-2")){
									Log.d("res id",""+client_push.get(i).get("id_reservation"));
									Log.d("client_id",""+client_id);
								
							if(client_id.contains(client_push.get(i).get("id_reservation"))){
								
								
								int j = client_id.indexOf(client_push.get(i).get("id_reservation"));
								
								
									
						vertical_outer_layout_id3.removeViewAt(j);
						client_id.remove(j);
						 mp = MediaPlayer.create(Home_driver_new1.this, R.raw.sound);
						 mp.start();
						showAlertToUser("Reservation has been cancelled");
						
						//client_push.remove(i);
							if(j==0){
								
								Data_driver.client_push11.clear();
								//client1 = false;
							
								
								if(nCounter1==0 &&nCounter1==0){
									mp = MediaPlayer.create(Home_driver_new1.this, R.raw.sound);
									 mp.start();
									showAlertToUser1("Reservation has been cancelled");
									Log.e("j====","000");
								}
								
							}
							
							else if(j==1){
								
								client_push2.clear();
								HashMap map2;
								map2 = new HashMap();
								map2.put(timeSharing12,"0");
								map2.put(timeSharing23,"0");
								map2.put(distanceSharing23,"0");
								map2.put(distanceSharing12,"0");
								map2.put(distanceSharing13,"0");
								map2.put(timeSharing13, "0");
								map2.put(distanceSharing123, "0");
								map2.put(timeSharing123,"0");
								map2.put(aloneTime,"0");
								map2.put(aloneDistance,"0");
								
								map2.put(aloneTime_g20, "0");
								map2.put(aloneTime_l20, "0");
								map2.put(timeSharing12_g20, "0");
								map2.put(timeSharing12_l20, "0");
								map2.put(timeSharing13_g20, "0");
								map2.put(timeSharing13_l20, "0");
								map2.put(timeSharing123_g20, "0");
								map2.put(timeSharing123_l20, "0");
								map2.put(timeSharing23_g20, "0");
								map2.put(timeSharing23_l20, "0");
								client_push2.add(map2);
								
								
								
								
								
								isShownFirstTime = true;
								isDialogShowing = false;
								client2 = false;
							
								Log.e("j====","111");
							}
							
							else if (j==2){
							
								client_push3.clear();
								
								HashMap map3;
								map3 = new HashMap();
								
								map3.put(timeSharing12,"0");
								map3.put(distanceSharing12,"0");
								map3.put(distanceSharing13,"0");
								map3.put(timeSharing13, "0");
								map3.put(distanceSharing123,"0");
								map3.put(timeSharing123,"0");
								map3.put(aloneTime, "0");
								map3.put(aloneDistance,"0");
								map3.put(timeSharing23,"0");
								map3.put(distanceSharing23,"0");
								
								map3.put(aloneTime_g20, "0");
								map3.put(aloneTime_l20, "0");
								map3.put(timeSharing12_g20, "0");
								map3.put(timeSharing12_l20, "0");
								map3.put(timeSharing13_g20, "0");
								map3.put(timeSharing13_l20, "0");
								map3.put(timeSharing123_g20, "0");
								map3.put(timeSharing123_l20, "0");
								map3.put(timeSharing23_g20, "0");
								map3.put(timeSharing23_l20, "0");
								client_push3.add(map3);
								isDialogShowing = false;
								client3 = false;
							
								Log.e("j====","222");
							}
							
							else {
								
							}
								
							}
						}
								
								else {
					
					
					if (isShownFirstTime) {
						if(!isDialogShowing) {
							
							
						if(client_id.contains(client_push.get(i).get("id_reservation"))){
							
						
						}
						else {
						client_push2.add(0, client_push.get(i));
						Log.i("client_push2===",""+client_push2);
						
						if(client_push2.get(i).get("statut").equals("0")){
						showNotifictionDialog2();
						isDialogShowing = true;
						}
						
					
						}
						
						}
						
					}

					else {
						if(!isDialogShowing) {
						if(client_id.contains(client_push.get(i).get("id_reservation"))){
							
						}
						
						else {
							
						client_push3.add(0, client_push.get(i));
						Log.i("client_push3===",""+client_push3);
						
						if(client_push3.get(i).get("statut").equals("0")){
						showNotifictionDialog3();
						isDialogShowing = true;
						}
					
						}
						}
					}
					
						}
					
				}
				}

				else {

				}
			}

			catch (Exception e) {
					Log.e("Exception===",""+e);
			}

		}

		private void showNotifictionDialog3() {
			final Dialog dialog;
			
			isOfferAccepted = false;
			
			Button accept1, decline1;
			TextView depar_client1;
			TextView destin_client1;
			ImageView pic;

			dialog = new Dialog(Home_driver_new1.this);
			dialog.setCancelable(false);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setFormat(PixelFormat.TRANSLUCENT);

			Drawable d = new ColorDrawable(Color.BLACK);
			d.setAlpha(0);
			dialog.getWindow().setBackgroundDrawable(d);

			dialog.setContentView(R.layout.client_on_new_screen);

			accept1 = (Button) dialog.findViewById(R.id.accept1);
			decline1 = (Button) dialog.findViewById(R.id.decline1);
			depar_client1 = (TextView) dialog.findViewById(R.id.depar_client1);
			pic = (ImageView) dialog.findViewById(R.id.pic);
			destin_client1 = (TextView) dialog
					.findViewById(R.id.destin_client1);

			
			
			try{
				String url = "http://taxi2share.eu/apps/android/webService_post/uploads/"+client_push3.get(0).get("image").toString();
				imageLoader.DisplayImage(url, pic);
				}
				
				catch(Exception e){
					Log.e("Exception",""+e);
				}

			depar_client1.setText(client_push3.get(0).get("pick_up"));
			destin_client1.setText(client_push3.get(0).get("detination"));
			mp = MediaPlayer.create(Home_driver_new1.this,
					R.raw.sound);
			mp.start();

			accept1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					run = false;
					client_id.add(client_push3.get(0).get("id_reservation"));
					dialog.dismiss();
				
					mp.stop();
			
					showLayoutfor3();
				
					new modify_notification_statut(client_push3.get(0).get(
							"id_reservation")).execute(new Void[0]);

				}

			});

			decline1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				//	arg = (Integer) v.getTag();
					mp.stop();
					new delete_client(client_push3.get(0).get("id_reservation"))
							.execute(new Void[0]);

				}
			});
			dialog.show();

		}

		/**************************************** CLIENT 3 ******************************************************/
		protected void showLayoutfor3() {

			try {
				// TODO Auto-generated method stub
				client3 = true;
				double lati = Double.parseDouble(client_push3.get(0).get(
						"latitude_client_dep"));
				double longLat = Double.parseDouble(client_push3.get(0)
						.get("longitude_client_dep"));

				latLngClient3 = new LatLng(lati, longLat);
				
				option_taxi.position(latLngClient3);
				option_taxi.title("Client");
				option_taxi.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.localisation_user));
				mGoogleMap.addMarker(Home_driver_new1.this.option_taxi);
				
				
				//vertical_outer_layout_id3.removeAllViews();
				LayoutInflater localLayoutInflater = (LayoutInflater) getSystemService("layout_inflater");
				convertview = new View(Home_driver_new1.this);
				convertview = localLayoutInflater.inflate(R.layout.client_new_th,
						null);
				
				convertview.setId(3);
				
				TextView boarding3,driving_time3;
				depar_client = (TextView) convertview
						.findViewById(R.id.depar_client);
				destin_client = (TextView) convertview
						.findViewById(R.id.destin_client);
				pass = (TextView) convertview.findViewById(R.id.pass);
				boarding3 = (TextView) convertview.findViewById(R.id.boarding3);
				driving_time3 = (TextView) convertview.findViewById(R.id.driving_time3);
				
				pic = (ImageView) convertview.findViewById(R.id.pic);

				counter3 = (Button) convertview.findViewById(R.id.counter3);
				stop3 = (Button) convertview.findViewById(R.id.stop3);
				timer3 = (TextView) convertview
						.findViewById(R.id.timer3);
				nbr_km = (TextView) convertview.findViewById(R.id.nbr_km);
				price = (TextView) convertview.findViewById(R.id.price);

				name = (TextView) convertview.findViewById(R.id.name);
				f_name = (TextView) convertview.findViewById(R.id.f_name);
				phone_nmber3 = (TextView) convertview
						.findViewById(R.id.phone_number3);
				depar_client.setText(client_push3.get(0).get(
						"pick_up"));
				destin_client.setText(client_push3.get(0).get(
						"detination"));
				pass.setText("Client 3:");
				
				boarding3.setText(client_push3.get(0).get("boarding_fee")+" €");
				driving_time3.setText(client_push3.get(0).get("price_per_minute")+" €/M");
				
				
				price_km  = Double.parseDouble(client_push3.get(0).get("price_per_km"));
				price_min =  Double.parseDouble(client_push3.get(0).get("price_per_minute"));
				boarding_fee = Double.parseDouble(client_push3.get(0).get("boarding_fee"));
				
				try{
					String url = "http://taxi2share.eu/apps/android/webService_post/uploads/"+client_push3.get(0).get("image").toString();
					imageLoader.DisplayImage(url, pic);
					}
					
					catch(Exception e){
						Log.e("Exception",""+e);
					}

				name.setText(client_push3.get(0).get("name_C"));
				f_name.setText(client_push3.get(0).get(
						"last_name_C"));
				
				SpannableString content1 = new SpannableString(client_push3.get(0).get("tel_C"));
				content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0); 
				phone_nmber3.setText(content1);
				
				phone_nmber3.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String number = "tel:" + client_push3.get(0).get("tel_C");
						Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number)); 
				        startActivity(callIntent);
						
					}
				});
			

				vertical_outer_layout_id3.addView(convertview);

				float f = get_distance(
						Double.parseDouble(client_push3.get(0).get(
								"latitude_client_dep")),
						Double.parseDouble(client_push3.get(0).get(
								"longitude_client_dep")),
						Double.parseDouble(client_push3.get(0).get(
								"latitude_client_des")),
						Double.parseDouble(client_push3.get(0).get(
								"longitude_client_des")));
				
				distance3 = f;

				double roundOff_dist = (double) Math.round(f * 100) / 100;
				nbr_km.setText(client_push3.get(0).get("distance") + " Km");
				double roundOff = (double) Math.round((price_km * Double.parseDouble(client_push3.get(0).get("distance"))) * 100) / 100;

				

				price.setText("" + roundOff + " €");
				
				counter3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						client3_status = true;
//						if (isOfferAccepted)
//							return;
						isOfferAccepted = true;
						doTimerTask3();
						no_of_clients++;
					stop3.setVisibility(View.VISIBLE);
				counter3.setVisibility(View.INVISIBLE);
					

					}

				});
				
				stop3.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						stop3.setVisibility(View.INVISIBLE);
						stopTask3();
						no_of_clients--;
						
					}

					private void stopTask3() {
						if (mTimerTask3 != null) {

							int secs = nCounter3;
							int mins = secs / 60;
							int hr = mins / 60;
							secs = secs % 60;

							counter_value[2] = nCounter3;
							timer3.setText("" + hr + ":" + String.format("%02d", mins)
									+ ":" + String.format("%02d", secs));

							
							mTimerTask3.cancel();
							
							price_km  = Double.parseDouble(client_push3.get(0).get("price_per_km"));
							price_min =  Double.parseDouble(client_push3.get(0).get("price_per_minute"));
							boarding_fee = Double.parseDouble(client_push3.get(0).get("boarding_fee"));
							
							price3 = calculationOnStop(3);
							
							Log.e("client 3(calculation)",""+client_push3);
							
							totalDistance  = Double.parseDouble(client_push3.get(0).get("distance"));
							 actualPrice = Double.parseDouble(client_push3.get(0)
									.get("Prix"));
						 
							// totalTime = (double)nCounter3/60;
							 totalTime1 = (double)nCounter3_more/60;
							 totalTime2 = (double)nCounter3_less/60;
							// totalTime = (double) Math.round(totalTime * 100) / 100;
							
							new envoie_prix(String.valueOf(price3), client_push3.get(0).get(
									"id_client"), client_push3.get(0).get("num_taxi"),
									client_push3.get(0).get("id_reservation"),String.valueOf(nCounter3),
									String.valueOf(nCounter3_more),String.valueOf(nCounter3_less))
									.execute(new Void[0]);
							
						}
						
					}
				});


			}

			catch (Exception e) {
				Log.e("Exception show layout for 2 ====", "" + e);
			}

		}

	

		protected void doTimerTask3() {
			calculationOnAccept(3);
			mTimerTask3 = new TimerTask() {
				public void run() {
					handler.post(new Runnable() {
						public void run() {
							nCounter3++;
							// update TextView
							int secs = nCounter3;
							int mins = secs / 60;
							int hr = mins / 60;
							secs = secs % 60;

							timer3.setText("" + hr + ":"
									+ String.format("%02d", mins) + ":"
									+ String.format("%02d", secs));
							// timer_text.setText("Timer: 00:00:" + nCounter);

						
							
						}
					});
				}
			};

			// public void schedule (TimerTask task, long delay, long period)
			t.schedule(mTimerTask3, 0, 1000); //
			id_reservation =client_push3.get(0).get("id_reservation");
			new startTimer(id_reservation).execute(new Void[0]);
		}

		private void showNotifictionDialog2() {
			
			
			
			
			isOfferAccepted = false;
			final Dialog dialog;

			
			Button accept1, decline1;
			TextView depar_client1;
			TextView destin_client1;
			ImageView pic;

			dialog = new Dialog(Home_driver_new1.this);
			dialog.setCancelable(false);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setFormat(PixelFormat.TRANSLUCENT);

			Drawable d = new ColorDrawable(Color.BLACK);
			d.setAlpha(0);
			dialog.getWindow().setBackgroundDrawable(d);

			dialog.setContentView(R.layout.client_on_new_screen);

			accept1 = (Button) dialog.findViewById(R.id.accept1);
			decline1 = (Button) dialog.findViewById(R.id.decline1);
			depar_client1 = (TextView) dialog.findViewById(R.id.depar_client1);
			pic = (ImageView) dialog.findViewById(R.id.pic);
			destin_client1 = (TextView) dialog
					.findViewById(R.id.destin_client1);

			// accept.setTag(0);
			// decline.setTag(0);

			depar_client1.setText(client_push2.get(0).get("pick_up"));
			destin_client1.setText(client_push2.get(0).get("detination"));
			
			try{
				String url = "http://taxi2share.eu/apps/android/webService_post/uploads/"+client_push2.get(0).get("image").toString();
				imageLoader.DisplayImage(url, pic);
				}
				
				catch(Exception e){
					Log.e("Exception",""+e);
				}
			mp = MediaPlayer.create(Home_driver_new1.this,
					R.raw.sound);
			mp.start();

			accept1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					run = false;
					dialog.dismiss();
					client_id.add(client_push2.get(0).get("id_reservation"));
					isShownFirstTime = false;
					
					mp.stop();
					
					showLayoutfor2();
				
					new modify_notification_statut(client_push2.get(0).get(
							"id_reservation")).execute(new Void[0]);

				}

			});

			decline1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				
					isShownFirstTime = true;
					mp.stop();
					new delete_client(client_push2.get(0).get("id_reservation"))
							.execute(new Void[0]);

				}
			});
			dialog.show();

		}

		/********************************************** CLIENT 2 *************************************************/

		protected void showLayoutfor2() {

			try {
				
				client2 = true;
				double lati = Double.parseDouble(client_push2.get(0).get(
						"latitude_client_dep"));
				double longLat = Double.parseDouble(client_push2.get(0)
						.get("longitude_client_dep"));

				latLngClient2 = new LatLng(lati, longLat);
				
				option_taxi.position(latLngClient2);
				option_taxi.title("Client");
				option_taxi.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.localisation_user));
				mGoogleMap.addMarker(Home_driver_new1.this.option_taxi);
				
				// TODO Auto-generated method stub
				//vertical_outer_layout_id3.removeAllViews();
				LayoutInflater localLayoutInflater = (LayoutInflater) getSystemService("layout_inflater");
				convertview = new View(Home_driver_new1.this);
				convertview = localLayoutInflater.inflate(R.layout.client_new_t,
						null);
				
				convertview.setId(2);
				
				
				
				TextView boarding2, driving_time2;
				
				depar_client = (TextView) convertview
						.findViewById(R.id.depar_client);
				destin_client = (TextView) convertview
						.findViewById(R.id.destin_client);
				pass = (TextView) convertview.findViewById(R.id.pass);
				pic = (ImageView) convertview.findViewById(R.id.pic);
				
				boarding2 = (TextView) convertview.findViewById(R.id.boarding2);
				driving_time2 = (TextView) convertview.findViewById(R.id.driving_time2);

				counter2 = (Button) convertview.findViewById(R.id.counter2);
				stop2 = (Button) convertview.findViewById(R.id.stop2);
				timer2 = (TextView) convertview
						.findViewById(R.id.timer2);
				nbr_km = (TextView) convertview.findViewById(R.id.nbr_km);
				price = (TextView) convertview.findViewById(R.id.price);

				name = (TextView) convertview.findViewById(R.id.name);
				f_name = (TextView) convertview.findViewById(R.id.f_name);
				phone_nmber2 = (TextView) convertview
						.findViewById(R.id.phone_number2);


				depar_client.setText(client_push2.get(0).get(
						"pick_up"));
				destin_client.setText(client_push2.get(0).get(
						"detination"));
				pass.setText("Client 2:");
				boarding2.setText(client_push2.get(0).get("boarding_fee")+" €");
				driving_time2.setText(client_push2.get(0).get("price_per_minute")+" €/M");
				
				
				price_km  = Double.parseDouble(client_push2.get(0).get("price_per_km"));
				price_min =  Double.parseDouble(client_push2.get(0).get("price_per_minute"));
				boarding_fee = Double.parseDouble(client_push2.get(0).get("boarding_fee"));
				
				try{
					String url = "http://taxi2share.eu/apps/android/webService_post/uploads/"+client_push2.get(0).get("image").toString();
					imageLoader.DisplayImage(url, pic);
					}
					
					catch(Exception e){
						Log.e("Exception",""+e);
					}

				name.setText(client_push2.get(0).get("name_C"));
				f_name.setText(client_push2.get(0).get(
						"last_name_C"));
				
				SpannableString content1 = new SpannableString(client_push2.get(0).get("tel_C"));
				content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0); 
				phone_nmber2.setText(content1);
				
				phone_nmber2.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String number = "tel:" + client_push2.get(0).get("tel_C");
						Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number)); 
				        startActivity(callIntent);
						
					}
				});
				vertical_outer_layout_id3.addView(convertview);

				float f = get_distance(
						Double.parseDouble(client_push2.get(0).get(
								"latitude_client_dep")),
						Double.parseDouble(client_push2.get(0).get(
								"longitude_client_dep")),
						Double.parseDouble(client_push2.get(0).get(
								"latitude_client_des")),
						Double.parseDouble(client_push2.get(0).get(
								"longitude_client_des")));
				
				distance2 = f;

				double roundOff_dist = (double) Math.round(f * 100) / 100;
				
				nbr_km.setText(client_push2.get(0).get("distance") + " Km");
				double roundOff = (double) Math.round((price_km * Double.parseDouble(client_push2.get(0).get("distance"))) * 100) / 100;

				
				price.setText("" + roundOff + " €");
				
			

				counter2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						client2_status = true;
//						if (isOfferAccepted)
//							return;
						isOfferAccepted = true;
						stop2.setVisibility(View.VISIBLE);
						counter2.setVisibility(View.INVISIBLE);
						doTimerTask2();
						no_of_clients++;

						// AloneTaskFor1();

					}

				});
				
				stop2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						no_of_clients--;
						stop2.setVisibility(View.INVISIBLE);
						stopTask2();

						
					}

					private void stopTask2() {
						if (mTimerTask2 != null) {

							int secs = nCounter2;
							int mins = secs / 60;
							int hr = mins / 60;
							secs = secs % 60;

							counter_value[1] = nCounter2;
							timer2.setText("" + hr + ":" + String.format("%02d", mins)
									+ ":" + String.format("%02d", secs));
							
							mTimerTask2.cancel();
							
							price_km  = Double.parseDouble(client_push2.get(0).get("price_per_km"));
							price_min =  Double.parseDouble(client_push2.get(0).get("price_per_minute"));
							boarding_fee = Double.parseDouble(client_push2.get(0).get("boarding_fee"));
							
							price2 =calculationOnStop(2);
							Log.e("client 2(calculation)",""+client_push2);
							
							
							totalDistance  = Double.parseDouble(client_push2.get(0).get("distance"));
							 actualPrice = Double.parseDouble(client_push2.get(0)
									.get("Prix"));
						 
						//	 totalTime = (double)nCounter2/60;
							 
							 totalTime1 = (double)nCounter2_more/60;
							 totalTime2 = (double)nCounter2_less/60;
							// totalTime = (double) Math.round(totalTime * 100) / 100;
							
							new envoie_prix(String.valueOf(price2), client_push2.get(0).get(
									"id_client"), client_push2.get(0).get("num_taxi"),
									client_push2.get(0).get("id_reservation"),String.valueOf(nCounter2),
									String.valueOf(nCounter2_more),String.valueOf(nCounter2_less))
									.execute(new Void[0]);
						}
						
					}
				});

			}

			catch (Exception e) {
				Log.e("Exception show layout for 2 ====", "" + e);
			}

		}


		protected void doTimerTask2() {
			calculationOnAccept(2);
			mTimerTask2 = new TimerTask() {
				public void run() {
					handler.post(new Runnable() {
						public void run() {
							nCounter2++;
							// update TextView
							int secs = nCounter2;
							int mins = secs / 60;
							int hr = mins / 60;
							secs = secs % 60;

							timer2.setText("" + hr + ":"
									+ String.format("%02d", mins) + ":"
									+ String.format("%02d", secs));
							

						}
					});
				}
			};

			// public void schedule (TimerTask task, long delay, long period)
			t.schedule(mTimerTask2, 0, 1000); //
			
			id_reservation =client_push2.get(0).get("id_reservation");
			new startTimer(id_reservation).execute(new Void[0]);
			

		}

		protected void onPreExecute() {
		}
	}

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
				isDialogShowing = false;
				/**
				 * show counter for that passenger
				 */

			}
		}

		protected void onPreExecute() {
			
			db = new TransparentProgressDialog(Home_driver_new1.this,
					R.drawable.loading);
			db.show();
		}
	}

	public class delete_client extends AsyncTask<Void, Void, Void> {
		String result;
		String id_client;

		public delete_client(String paramString) {

			id_client = paramString;
		}

		protected Void doInBackground(Void... paramVarArgs) {
			this.result = function.delete(id_client);
			Log.e("decline result =================",""+this.result);
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			db.dismiss();
			isDialogShowing = false;
			this.result.equalsIgnoreCase("ok");
			if(this.result.equalsIgnoreCase("ok")){
				if(isShownFirstTime){
			client_push2.clear();
			HashMap map2;
			map2 = new HashMap();
			map2.put(timeSharing12,"0");
			map2.put(timeSharing23,"0");
			map2.put(distanceSharing23,"0");
			map2.put(distanceSharing12,"0");
			map2.put(distanceSharing13,"0");
			map2.put(timeSharing13, "0");
			map2.put(distanceSharing123, "0");
			map2.put(timeSharing123,"0");
			map2.put(aloneTime,"0");
			map2.put(aloneDistance,"0");
			
			map2.put(aloneTime_g20, "0");
			map2.put(aloneTime_l20, "0");
			map2.put(timeSharing12_g20, "0");
			map2.put(timeSharing12_l20, "0");
			map2.put(timeSharing13_g20, "0");
			map2.put(timeSharing13_l20, "0");
			map2.put(timeSharing123_g20, "0");
			map2.put(timeSharing123_l20, "0");
			map2.put(timeSharing23_g20, "0");
			map2.put(timeSharing23_l20, "0");
			client_push2.add(map2);
			
			
			
			Log.e("decline result =================","2 clear......");
				}
				
				else {
					client_push3.clear();
					Log.e("decline result =================","3 clear......");
					
					HashMap map3;
					map3 = new HashMap();
					
					map3.put(timeSharing12,"0");
					map3.put(distanceSharing12,"0");
					map3.put(distanceSharing13,"0");
					map3.put(timeSharing13, "0");
					map3.put(distanceSharing123,"0");
					map3.put(timeSharing123,"0");
					map3.put(aloneTime, "0");
					map3.put(aloneDistance,"0");
					map3.put(timeSharing23,"0");
					map3.put(distanceSharing23,"0");
					
					map3.put(aloneTime_g20, "0");
					map3.put(aloneTime_l20, "0");
					map3.put(timeSharing12_g20, "0");
					map3.put(timeSharing12_l20, "0");
					map3.put(timeSharing13_g20, "0");
					map3.put(timeSharing13_l20, "0");
					map3.put(timeSharing123_g20, "0");
					map3.put(timeSharing123_l20, "0");
					map3.put(timeSharing23_g20, "0");
					map3.put(timeSharing23_l20, "0");
					client_push3.add(map3);
				}
			}

		}

		protected void onPreExecute() {

			db = new TransparentProgressDialog(Home_driver_new1.this,
					R.drawable.loading);
			db.show();
		}
	}

	public class envoie_prix extends AsyncTask<Void, Void, Void> {
		String id_client;
		String num_taxi;
		String prix;
		String id_reservation;
		HashMap result;
		String time_more;
		String time_less;
		String total_time;

		public envoie_prix(String paramString1, String paramString2,
				String paramString3, String paramString4,String paramString5 ,String paramString6,String paramString7) {
			this.prix = paramString1;
			this.id_client = paramString2;
			this.num_taxi = paramString3;
			this.id_reservation = paramString4;
			this.total_time = paramString5;
			this.time_more = paramString6;
			this.time_less = paramString7;
		}

		protected Void doInBackground(Void... paramVarArgs) {

			this.result = function.envoyer_prix(this.id_client, this.num_taxi,
					this.prix, this.id_reservation,this.total_time,this.time_more,this.time_less);
			Log.i("result=", "" + result);
			
			loyality_hashmap = result;

			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			db.dismiss();
			
			try{
			if(loyality_hashmap.get("result").toString().equalsIgnoreCase("OK")){
			
			final Dialog dialog;
			
			TextView price_to_pay , distance,price1,sharing_price_to_pay,driving_time1, fidelity_card,boarding;
			Button button1;
			dialog = new Dialog(Home_driver_new1.this);
			dialog.setCancelable(false);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setFormat(
					PixelFormat.TRANSLUCENT);

			Drawable d = new ColorDrawable(Color.BLACK);
			d.setAlpha(0);
			dialog.getWindow().setBackgroundDrawable(d);

			dialog.setContentView(R.layout.driver_notification);
			price_to_pay = (TextView) dialog.findViewById(R.id.price_to_pay);
			sharing_price_to_pay = (TextView) dialog.findViewById(R.id.sharing_price_to_pay);
			driving_time1 = (TextView) dialog.findViewById(R.id.driving_time1);
			distance = (TextView) dialog.findViewById(R.id.distance);
			fidelity_card = (TextView) dialog.findViewById(R.id.fidelity_card);
			boarding = (TextView) dialog.findViewById(R.id.boarding);
			price1 = (TextView) dialog.findViewById(R.id.price1);
			button1 = (Button) dialog.findViewById(R.id.button1);
			Double prix1 = Double.parseDouble(prix);
		
		
			double price_nrml = boarding_fee+actualPrice ;
			
			Log.e("actualPrice=====",""+actualPrice );
			Log.e("boarding_fee=====",""+boarding_fee );
			Log.e("boarding_fee + actualPrice=====",""+price_nrml );
			
			double price = (double) Math.round(prix1 * 100) / 100;
			
			//double tT = (totalTime2 *price_min)+totalTime1;
			
			double tT = (totalTime2 *price_min);
			Log.e("tT =====",""+tT );
			
			price_nrml  += tT;
			
			Log.e("boarding_fee + actualPrice+ time=====",""+price_nrml );
			
			price_to_pay.setText(""+(double) Math.round((price_nrml ) * 100) / 100+" €");
			totalDistance = (double) Math.round(totalDistance * 100) / 100;
			distance.setText(""+totalDistance+" Km");
			boarding.setText(""+boarding_fee+" €");
			
			double price11 = (double) Math.round((price_km * totalDistance) * 100) / 100;
			
		
			price1.setText(""+price11 +" €");
			
			double totalTime = (double) Math.round( (totalTime1+totalTime2) * 100) / 100;
			
			driving_time1.setText("" +totalTime+"  m");
			
			double fidelity = Double.parseDouble(loyality_hashmap.get("loyality").toString());
			fidelity = (double) Math.round((fidelity) * 100) / 100;
			
			Log.d("price==",""+price);
			Log.d("fidelity==",""+fidelity);
			double price_fid =price-fidelity;
			Log.d("price_fid==",""+price_fid);
			price_fid = (double) Math.round(price_fid * 100) / 100;
			Log.d("price_fid(round off)==",""+price_fid);
			
			if(price_fid>0){
			sharing_price_to_pay.setText(""+price_fid+" €");
			}
			
			else {
				sharing_price_to_pay.setText("0.00"+" €");
			}
			
			
			fidelity_card.setText("-"+fidelity+" €");
			button1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					
				}
			});
			dialog.show();
		}
			
		
		
		else {
			showAlertToUser("Something went wrong");
		}
			
		}
		
		catch(Exception ae){
			Log.e("Exception====",""+ae);
		}
		}

		protected void onPreExecute() {
			super.onPreExecute();
			db = new TransparentProgressDialog(Home_driver_new1.this,
					R.drawable.loading);
			db.show();
		}
	}

	public void calculationOnAccept(int acceptedFor) {
		HashMap detail;
		if (acceptedFor == 1) {
			detail = new HashMap();
			if (client2_status && client3_status) {
				// add time for 2 and 3 for double sharing
			} else if (client2_status) {
				// add time for 2 alone
			} else if (client3_status) {
				// add time for 3 alone
			}
		} else if (acceptedFor == 2) {
			detail = new HashMap();
			if (client1_status && client3_status) {
				// add time for 1 and 3 for double sharing
			} else if (client1_status) {
				// add time for 1 alone
				HashMap p = Data_driver.client_push11.get(0);
				p.put(aloneTime, String.valueOf(nCounter1));
				p.put(aloneDistance, String.valueOf(get_distance(Double
						.parseDouble(Data_driver.client_push11.get(0)
								.get("latitude_client_dep")), Double
						.parseDouble(Data_driver.client_push11.get(0)
								.get("longitude_client_dep")), Double
						.parseDouble(client_push2.get(0)
								.get("latitude_client_dep")), Double
						.parseDouble(client_push2.get(0)
								.get("longitude_client_dep")))));
				
		//******************************chnages time for speed greater thn or less than 20**************************************//
				
				
				p.put(aloneTime_g20, String.valueOf(nCounter1_more));
				p.put(aloneTime_l20, String.valueOf(nCounter1_less));
				
				Data_driver.client_push11.clear();
				
				Data_driver.client_push11.add(p);
				
				
			} else if (client3_status) {
				// add time for 3 alone
			}
		} else if (acceptedFor == 3) {
			detail = new HashMap();
			if (client1_status && client2_status) {
				
				HashMap p = Data_driver.client_push11.get(0);
				HashMap q = client_push2.get(0);
			
				// add time for 1 and 2 for double sharing
				p.put(timeSharing12, String.valueOf(nCounter2));
				p.put(
						distanceSharing12,
						String.valueOf(get_distance(Double
								.parseDouble(client_push2.get(0)
										.get("latitude_client_dep")), Double
								.parseDouble(client_push2.get(0)
										.get("longitude_client_dep")), Double
								.parseDouble(client_push3.get(0)
										.get("latitude_client_dep")), Double
								.parseDouble(client_push3.get(0)
										.get("longitude_client_dep")))));
				

				p.put(timeSharing12_g20, String.valueOf(nCounter2_more));
				p.put(timeSharing12_l20, String.valueOf(nCounter2_less));
				
				q.put(timeSharing12, String.valueOf(nCounter2));
				q.put(
						distanceSharing12,
						String.valueOf(get_distance(Double
								.parseDouble(client_push2.get(0)
										.get("latitude_client_dep")), Double
								.parseDouble(client_push2.get(0)
										.get("longitude_client_dep")), Double
								.parseDouble(client_push3.get(0)
										.get("latitude_client_dep")), Double
								.parseDouble(client_push3.get(0)
										.get("longitude_client_dep")))));
				
				q.put(timeSharing12_g20, String.valueOf(nCounter2_more));
				q.put(timeSharing12_l20, String.valueOf(nCounter2_less));
				
				Data_driver.client_push11.clear();
				client_push2.clear();
				
				Data_driver.client_push11.add((HashMap) p);
				client_push2.add((HashMap) q);
				
			} else if (client1_status) {
				// add time for 1 alone
				

				HashMap p = Data_driver.client_push11.get(0);
				
			
				p.put(aloneTime, String.valueOf(nCounter1-nCounter2));
				double DS12 = Integer.parseInt(Data_driver.client_push11.get(0).get(distanceSharing12)); 
				p.put(
						aloneDistance,
						String.valueOf((get_distance(Double
								.parseDouble(Data_driver.client_push11.get(0)
										.get("latitude_client_dep")), Double
								.parseDouble(Data_driver.client_push11.get(0)
										.get("longitude_client_dep")), Double
								.parseDouble(client_push3.get(0)
										.get("latitude_client_dep")), Double
								.parseDouble(client_push3.get(0)
										.get("longitude_client_dep")))-DS12)));
				
				p.put(aloneTime_g20, String.valueOf(nCounter1_more-nCounter2_more));
				p.put(aloneTime_l20, String.valueOf(nCounter1_less-nCounter2_less));
				
				Data_driver.client_push11.clear();
				
				Data_driver.client_push11.add((HashMap) p);
				
			} else if (client2_status) {
				// add time for 2 alone
				HashMap p = client_push2.get(0);
				
				int TS12 = Integer.parseInt(client_push2.get(0).get(timeSharing12)); 
				
				int TS12_g20 = Integer.parseInt(client_push2.get(0).get(timeSharing12_g20)); 
				int TS12_l20 = Integer.parseInt(client_push2.get(0).get(timeSharing12_l20)); 
				

				p.put(aloneTime,String.valueOf( nCounter2-TS12));
				p.put(
						aloneDistance,
						String.valueOf(get_distance(Double
								.parseDouble(Data_driver.client_push11.get(0)
										.get("latitude_client_des")), Double
								.parseDouble(Data_driver.client_push11.get(0)
										.get("longitude_client_des")), Double
								.parseDouble(client_push3.get(0)
										.get("latitude_client_dep")), Double
								.parseDouble(client_push3.get(0)
										.get("longitude_client_dep")))));
				
				p.put(aloneTime_g20, String.valueOf(nCounter2_more-TS12_g20));
				p.put(aloneTime_l20, String.valueOf(nCounter2_less-TS12_l20));
				
				client_push2.clear();
				
				client_push2.add((HashMap) p);
			}
		}

	}

	public double calculationOnStop(int stoppedFor) {
		HashMap detailOnStop;
		
	

		double f_ad  = 0.0,f_at = 0.0,f_d12 = 0.0,f_d13 = 0.0,f_t12 = 0.0,f_t13 = 0.0,f_d123 = 0.0,f_t123 = 0.0;
		
		double  f_at_g20 = 0.0 , f_at_l20 = 0.0;
		double  f_t12_g20 = 0.0 , f_t12_l20 = 0.0;
		double  f_t13_g20 = 0.0 , f_t13_l20 = 0.0;
		double  f_t123_g20 = 0.0 , f_t123_l20 = 0.0;
		double price = 0 , price_20 = 0;
		
	double minDistance = 0.0;
	double distance1 = 0.0d, distance2 = 0.0d; distance3 = 0.0d;	
	if(Data_driver.client_push11.get(arg)
					.get("latitude_client_dep") != null) {

		distance1 = Double.parseDouble(Data_driver.client_push11.get(arg).get("distance"));
		
		Log.e("distance1================",""+distance1);
	}
	
	try{
	if(client_push2.get(0)
			.get("latitude_client_dep") != null) {
	
		distance2 = Double.parseDouble(client_push2.get(0).get("distance"));
		
		Log.e("distance2================",""+distance2);
	}
	}
	
	catch(Exception ae){
		Log.e("Exception================",""+ae);
	}
	
	try {
	if(client_push3.get(0)
			.get("latitude_client_dep") != null) {
		
		distance3 =  Double.parseDouble(client_push3.get(0).get("distance"));
		
		Log.e("distance3================",""+distance3);
	}
	
	}catch(Exception ae){
		Log.e("Exception================",""+ae);
	}
	
	
	
		int sharing = 1;
		if(client_push3.get(0)
				.get("latitude_client_dep") != null) {
			minDistance =	Math.min(Math.min(distance1, distance2),distance3);
			sharing = 3;
		} else  {
			minDistance  =  Math.min(distance1, distance2);
			sharing = 2;
		}
	
		if (stoppedFor == 1) {
			double min_dist = 0;
			
			if(client2_status && client3_status){
				min_dist =	Math.min(Math.min(distance1, distance2),distance3);
			}
			
			else if (client2_status && !client3_status){
				min_dist =	Math.min(distance1, distance2);
			}
			
			else if (!client2_status && client3_status ){
				min_dist =	Math.min(distance1, distance3);
			}
			
			else {
				min_dist = distance1;
			}
			
			
			detailOnStop = new HashMap();

			if (client2_status && client3_status) {
				// add time for 1,2 and 3 for 3 sharing
				
				HashMap p = Data_driver.client_push11.get(arg);
				HashMap q = client_push2.get(0);
				HashMap r = client_push3.get(0);
				
			
				
				p.put(timeSharing123, String.valueOf(nCounter3));
				p.put(distanceSharing123,String.valueOf(min_dist));
				
				p.put(timeSharing123_g20, String.valueOf(nCounter3_more));
				p.put(timeSharing123_l20, String.valueOf(nCounter3_less));
				
				q.put(timeSharing123, String.valueOf(nCounter3));
				q.put(distanceSharing123,String.valueOf(min_dist));
				
				q.put(timeSharing123_g20, String.valueOf(nCounter3_more));
				q.put(timeSharing123_l20, String.valueOf(nCounter3_less));
				
				r.put(timeSharing123, String.valueOf(nCounter3));
				r.put(distanceSharing123,String.valueOf(min_dist));
				
				r.put(timeSharing123_g20, String.valueOf(nCounter3_more));
				r.put(timeSharing123_l20, String.valueOf(nCounter3_less));
				
				Data_driver.client_push11.clear();
				client_push2.clear();
				client_push3.clear();
				
				Data_driver.client_push11.add((HashMap) p);
				client_push2.add((HashMap) q);
				client_push3.add((HashMap) r);
				
			
				
				
				
			} else if (client2_status) {
				// add time for 1, 2 sharing
				
				HashMap p = Data_driver.client_push11.get(arg);
				HashMap q = client_push2.get(0);
		
				p.put(timeSharing12, String.valueOf(nCounter2));
				p.put(distanceSharing12,String.valueOf(min_dist));
				
				p.put(timeSharing12_g20, String.valueOf(nCounter2_more));
				p.put(timeSharing12_l20, String.valueOf(nCounter2_less));
				
				q.put(timeSharing12, String.valueOf(nCounter2));
				q.put(distanceSharing12,String.valueOf(min_dist));
				
				q.put(timeSharing12_g20, String.valueOf(nCounter2_more));
				q.put(timeSharing12_l20, String.valueOf(nCounter2_less));
				
				Data_driver.client_push11.clear();
				client_push2.clear();
				
				Data_driver.client_push11.add((HashMap) p);
				client_push2.add((HashMap) q);
				
				
		
			} else if (client3_status) {
				// add time for 1,3 sharing
				
			
				
				HashMap p = Data_driver.client_push11.get(arg);
				HashMap q = client_push3.get(0);
				
				p.put(timeSharing13, String.valueOf(nCounter3));
				p.put(distanceSharing13,String.valueOf(min_dist));
				
				p.put(timeSharing13_g20, String.valueOf(nCounter3_more));
				p.put(timeSharing13_l20, String.valueOf(nCounter3_less));
				
				q.put(timeSharing13, String.valueOf(nCounter3));
				q.put(distanceSharing13,String.valueOf(min_dist));
				
				q.put(timeSharing13_g20, String.valueOf(nCounter3_more));
				q.put(timeSharing13_l20, String.valueOf(nCounter3_less));
				
				Data_driver.client_push11.clear();
				client_push3.clear();
				Data_driver.client_push11.add((HashMap) p);
				client_push3.add((HashMap) q);
				
			
				
				
			} else {
				// add time for 1 alone
				double TS12 = Double.parseDouble(Data_driver.client_push11.get(arg).get(timeSharing12)); 
				double TS13 =Double.parseDouble(Data_driver.client_push11.get(arg).get(timeSharing13));
				double TS123 = Double.parseDouble(Data_driver.client_push11.get(arg).get(timeSharing123));
				
				double TS12_g20 = Double.parseDouble(Data_driver.client_push11.get(arg).get(timeSharing12_g20)); 
				double TS13_g20 =Double.parseDouble(Data_driver.client_push11.get(arg).get(timeSharing13_g20));
				double TS123_g20 = Double.parseDouble(Data_driver.client_push11.get(arg).get(timeSharing123_g20));
				
				double TS12_l20 = Double.parseDouble(Data_driver.client_push11.get(arg).get(timeSharing12_l20)); 
				double TS13_l20 =Double.parseDouble(Data_driver.client_push11.get(arg).get(timeSharing13_l20));
				double TS123_l20 = Double.parseDouble(Data_driver.client_push11.get(arg).get(timeSharing123_l20));
				
				HashMap p = Data_driver.client_push11.get(arg);
				p.put(aloneTime,  String.valueOf(nCounter1-(TS12+TS13+TS123)));
				p.put(aloneTime_g20, String.valueOf(nCounter1_more-(TS12_g20+TS13_g20+TS123_g20)));
				p.put(aloneTime_l20, String.valueOf(nCounter1_less-(TS12_l20+TS13_l20+TS123_l20)));
				
			
				
				p.put(aloneDistance,(String.valueOf(Math.abs(min_dist-distance1))));
				
				Data_driver.client_push11.clear();
		
				Data_driver.client_push11.add((HashMap) p);
				
			
				
			}
			client1_status  = false;
			
			f_ad =   distance1 - minDistance;;
			f_at =  Double.parseDouble(Data_driver.client_push11.get(0).get(aloneTime));
			
			f_at_g20 = Double.parseDouble(Data_driver.client_push11.get(0).get(aloneTime_g20));
			f_at_l20 = Double.parseDouble(Data_driver.client_push11.get(0).get(aloneTime_l20));
			
			
			f_d12 =  Double.parseDouble(Data_driver.client_push11.get(0).get(distanceSharing12));
			f_d13 =  Double.parseDouble(Data_driver.client_push11.get(0).get(distanceSharing13));
			f_d123 =  minDistance;
			f_t12 =  Double.parseDouble(Data_driver.client_push11.get(0).get(timeSharing12));
			
			f_t12_g20 =  Double.parseDouble(Data_driver.client_push11.get(0).get(timeSharing12_g20));
			f_t12_l20 =  Double.parseDouble(Data_driver.client_push11.get(0).get(timeSharing12_l20));
			
			f_t13 =  Double.parseDouble(Data_driver.client_push11.get(0).get(timeSharing13));
			
			f_t13_g20 =  Double.parseDouble(Data_driver.client_push11.get(0).get(timeSharing13_g20));
			f_t13_l20 =  Double.parseDouble(Data_driver.client_push11.get(0).get(timeSharing13_l20));
			
			f_t123 =  Double.parseDouble(Data_driver.client_push11.get(0).get(timeSharing123));
			
			f_t123_g20 =  Double.parseDouble(Data_driver.client_push11.get(0).get(timeSharing123_g20));
			f_t123_l20 =  Double.parseDouble(Data_driver.client_push11.get(0).get(timeSharing123_l20));
			
		} else if (stoppedFor == 2) {
			detailOnStop = new HashMap();
			
			double min_dist = 0;
			
			if(!client3_status && client1_status){
			 min_dist =Math.min(distance1, distance2);
			}
			
			else if (client3_status && client1_status){
				min_dist =Math.min(Math.min(distance1, distance2),distance3);	
			} 
			
			else if (client3_status && !client1_status){
				 min_dist =Math.min(distance3, distance2);
			}
			
			else {
				min_dist = distance2;
			}
		
			if (client1_status && client3_status) {
				// add time for 1,2 and 3 for 3 sharing
				HashMap p = Data_driver.client_push11.get(arg);
				HashMap q = client_push2.get(0);
				HashMap r = client_push3.get(0);
				
			
				
				p.put(timeSharing123, String.valueOf(nCounter3));
				p.put(distanceSharing123,String.valueOf(min_dist));
				
				p.put(timeSharing123_g20, String.valueOf(nCounter3_more));
				p.put(timeSharing123_l20, String.valueOf(nCounter3_less));
				
				q.put(timeSharing123, String.valueOf(nCounter3));
				q.put(distanceSharing123,String.valueOf(min_dist));
				
				q.put(timeSharing123_g20, String.valueOf(nCounter3_more));
				q.put(timeSharing123_l20, String.valueOf(nCounter3_less));
				
				r.put(timeSharing123, String.valueOf(nCounter3));
				r.put(distanceSharing123,String.valueOf(min_dist));
				
				r.put(timeSharing123_g20, String.valueOf(nCounter3_more));
				r.put(timeSharing123_l20, String.valueOf(nCounter3_less));
				
				Data_driver.client_push11.clear();
				client_push2.clear();
				client_push3.clear();
				
				Data_driver.client_push11.add((HashMap) p);
				client_push2.add((HashMap) q);
				client_push3.add((HashMap) r);
				
			
				
			} else if (client1_status) {
				// add time for 1, 2 sharing
				HashMap p = Data_driver.client_push11.get(arg);
				HashMap q = client_push2.get(0);
				
				
				
				double t123= Double.parseDouble(client_push2.get(0).get(timeSharing123));
				double t23= Double.parseDouble(client_push2.get(0).get(timeSharing23));
				
				double t123_g20= Double.parseDouble(client_push2.get(0).get(timeSharing123_g20));
				double t123_l20= Double.parseDouble(client_push2.get(0).get(timeSharing123_l20));
				
				double t23_g20= Double.parseDouble(client_push2.get(0).get(timeSharing23_g20));
				double t23_l20= Double.parseDouble(client_push2.get(0).get(timeSharing23_l20));
				
				
				p.put(timeSharing12, String.valueOf(nCounter2-(t123+t23)));
				p.put(distanceSharing12,String.valueOf(min_dist));
				
				p.put(timeSharing12_g20, String.valueOf(nCounter2_more-(t123_g20+t23_g20)));
				p.put(timeSharing12_l20, String.valueOf(nCounter2_less-(t123_l20+t23_l20)));
				
				q.put(timeSharing12, String.valueOf(nCounter2-(t123+t23)));
				q.put(distanceSharing12,String.valueOf(min_dist));
				
				q.put(timeSharing12_g20, String.valueOf(nCounter2_more-(t123_g20+t23_g20)));
				q.put(timeSharing12_l20, String.valueOf(nCounter2_less-(t123_l20+t23_l20)));
			
				Data_driver.client_push11.clear();
				client_push2.clear();
				
				Data_driver.client_push11.add((HashMap) p);
				client_push2.add((HashMap) q);
				
		
				 
			} else if (client3_status) {
				// add time for 2,3 alone
				
				HashMap q = client_push2.get(0);
				HashMap r = client_push3.get(0);
				
				double t123= Double.parseDouble(client_push2.get(0).get(timeSharing123));
				
				double t123_g20= Double.parseDouble(client_push2.get(0).get(timeSharing123_g20));
				double t123_l20= Double.parseDouble(client_push2.get(0).get(timeSharing123_l20));
				
				
				q.put(timeSharing23, String.valueOf(nCounter3-t123));
				q.put(distanceSharing23,String.valueOf(min_dist));
				
				q.put(timeSharing23_g20, String.valueOf(nCounter3_more-t123_g20));
				q.put(timeSharing23_l20, String.valueOf(nCounter3_less-t123_l20));
			
				r.put(timeSharing23, String.valueOf(nCounter3-t123));
				r.put(distanceSharing23,String.valueOf(min_dist));
				
				r.put(timeSharing23_g20, String.valueOf(nCounter3_more-t123_g20));
				r.put(timeSharing23_l20, String.valueOf(nCounter3_less-t123_l20));
				
				client_push3.clear();
				client_push2.clear();
				
				client_push2.add((HashMap) q);
				client_push3.add((HashMap) r);
				
			} else {
				
				
				// add time for 2 alone
				HashMap q = client_push2.get(0);
				
				double TS12 = Double.parseDouble(client_push2.get(0).get(timeSharing12)); 
				double TS23 =Double.parseDouble(client_push2.get(0).get(timeSharing23));
				double TS123 =Double.parseDouble(client_push2.get(0).get(timeSharing123));
				
				double TS12_g20 = Double.parseDouble(client_push2.get(0).get(timeSharing12_g20)); 
				double TS123_g20 =Double.parseDouble(client_push2.get(0).get(timeSharing123_g20));
				
				double TS12_l20 = Double.parseDouble(client_push2.get(0).get(timeSharing12_l20)); 
				double TS123_l20 =Double.parseDouble(client_push2.get(0).get(timeSharing123_l20));
				
				double TS23_g20 =Double.parseDouble(client_push2.get(0).get(timeSharing23_g20));
				double TS23_l20 =Double.parseDouble(client_push2.get(0).get(timeSharing23_l20));
				
				q.put(aloneTime, String.valueOf(nCounter2-(TS12+TS23+TS123)));
				
				q.put(aloneTime_g20, String.valueOf(nCounter2_more-(TS12_g20+TS23_g20+TS123_g20)));
				q.put(aloneTime_l20, String.valueOf(nCounter2_less-(TS12_l20+TS23_l20+TS123_l20)));
				
				Log.i("======++++",TS12+"==="+TS23+"TS123"+TS123+"==="+nCounter2);
				
				
				q.put(aloneDistance, String.valueOf(Math.abs(distance2-min_dist)));
				client_push2.clear();
				client_push2.add((HashMap) q);
				
				
			}
			f_ad =  distance2 - minDistance;//Double.parseDouble(client_push2.get(0).get(aloneDistance));
			f_at =  Double.parseDouble(client_push2.get(0).get(aloneTime));
			
			f_at_g20 =  Double.parseDouble(client_push2.get(0).get(aloneTime_g20));
			f_at_l20 =  Double.parseDouble(client_push2.get(0).get(aloneTime_l20));
			
			f_d12 =  Double.parseDouble(client_push2.get(0).get(distanceSharing12));
			f_d13 =  Double.parseDouble(client_push2.get(0).get(distanceSharing13));
			f_d123 =  minDistance;
			f_t12 =  Double.parseDouble(client_push2.get(0).get(timeSharing12));
			
			f_t12_g20 =  Double.parseDouble(client_push2.get(0).get(timeSharing12_g20));
			f_t12_l20 =  Double.parseDouble(client_push2.get(0).get(timeSharing12_l20));
			
			f_t13 =  Double.parseDouble(client_push2.get(0).get(timeSharing13));
			
			f_t13_g20 =  Double.parseDouble(client_push2.get(0).get(timeSharing13_g20));
			f_t13_l20 =  Double.parseDouble(client_push2.get(0).get(timeSharing13_l20));
			
			f_t123 =  Double.parseDouble(client_push2.get(0).get(timeSharing123));
			
			f_t123_g20 =  Double.parseDouble(client_push2.get(0).get(timeSharing123_g20));
			f_t123_l20 =  Double.parseDouble(client_push2.get(0).get(timeSharing123_l20));
			
			client2_status  = false;
		} else if (stoppedFor == 3) {
			
			double min_dist = 0;
			
			if(client1_status && client2_status){
			 min_dist =	Math.min(Math.min(distance1, distance2),distance3);
			}
			
			else if(client1_status && !client2_status){
				 min_dist =	Math.min(distance1, distance3);
				}
			
			else if(!client1_status && client2_status){
				 min_dist =	Math.min(distance3, distance2);
				}
			
			else {
				min_dist = distance3;
			}
			detailOnStop = new HashMap();
			if (client1_status && client2_status) {
				// add time for 1,2 and 3 for 3 sharing
				HashMap p = Data_driver.client_push11.get(arg);
				HashMap q = client_push2.get(0);
				HashMap r = client_push3.get(0);
				
				
				
			
				
				p.put(timeSharing123, String.valueOf(nCounter3));
				p.put(distanceSharing123,String.valueOf(min_dist));
				
				p.put(timeSharing123_g20, String.valueOf(nCounter3_more));
				p.put(timeSharing123_l20, String.valueOf(nCounter3_less));
				
				q.put(timeSharing123, String.valueOf(nCounter3));
				q.put(distanceSharing123,String.valueOf(min_dist));
				
				q.put(timeSharing123_g20, String.valueOf(nCounter3_more));
				q.put(timeSharing123_l20, String.valueOf(nCounter3_less));
				
				r.put(timeSharing123, String.valueOf(nCounter3));
				r.put(distanceSharing123,String.valueOf(min_dist));
				
				r.put(timeSharing123_g20, String.valueOf(nCounter3_more));
				r.put(timeSharing123_l20, String.valueOf(nCounter3_less));
				
				Data_driver.client_push11.clear();
				client_push2.clear();
				client_push3.clear();
				
				Data_driver.client_push11.add((HashMap) p);
				client_push2.add((HashMap) q);
				client_push3.add((HashMap) r);
				
				

			} else if (client1_status) {
				// add time for 1, 3 sharing
				HashMap p = Data_driver.client_push11.get(arg);
				
				HashMap q = client_push3.get(0);
				double t1 = Double.parseDouble(client_push3.get(0).get(aloneTime));
				double t12 = Double.parseDouble(client_push3.get(0).get(timeSharing12));
				double t123 = Double.parseDouble(client_push3.get(0).get(timeSharing123));
				
				double t1_g20 = Double.parseDouble(client_push3.get(0).get(aloneTime_g20));
				double t12_g20 = Double.parseDouble(client_push3.get(0).get(timeSharing12_g20));
				double t123_g20 = Double.parseDouble(client_push3.get(0).get(timeSharing123_g20));
				
				double t1_l20 = Double.parseDouble(client_push3.get(0).get(aloneTime_l20));
				double t12_l20 = Double.parseDouble(client_push3.get(0).get(timeSharing12_l20));
				double t123_l20 = Double.parseDouble(client_push3.get(0).get(timeSharing123_l20));
				
				double D123 = Double.parseDouble(client_push3.get(0).get(distanceSharing123));
			
				
				D123 = (double) Math.round((D123) * 100) / 100;
				
				double dis13 = distance3 -D123;
				
				
				p.put(timeSharing13, String.valueOf(nCounter3- (t1+ t12+ t123)));
				p.put(distanceSharing13,String.valueOf(min_dist));
				
				p.put(timeSharing13_g20, String.valueOf(nCounter3_more- (t1_g20+ t12_g20+ t123_g20)));
				p.put(timeSharing13_l20, String.valueOf(nCounter3_less- (t1_l20+ t12_l20+ t123_l20)));
				
				q.put(timeSharing13, String.valueOf(nCounter3- (t1+ t12+ t123)));
				q.put(distanceSharing13,String.valueOf(min_dist));
				
				q.put(timeSharing13_g20, String.valueOf(nCounter3_more- (t1_g20+ t12_g20+ t123_g20)));
				q.put(timeSharing13_l20, String.valueOf(nCounter3_less- (t1_l20+ t12_l20+ t123_l20)));
				
				Data_driver.client_push11.clear();
				client_push3.clear();
				
				Data_driver.client_push11.add((HashMap) p);
				client_push3.add((HashMap) q);
				
			
				
			} else if (client2_status) {
				// add time for 2,3 alone
				double AT = Double.parseDouble(Data_driver.client_push11.get(arg).get(aloneTime));
				double T12 = Double.parseDouble(Data_driver.client_push11.get(arg).get(timeSharing12));
				
				double AT_g20 = Double.parseDouble(Data_driver.client_push11.get(arg).get(aloneTime_g20));
				double T12_g20 = Double.parseDouble(Data_driver.client_push11.get(arg).get(timeSharing12_l20));
				
				double AT_l20 = Double.parseDouble(Data_driver.client_push11.get(arg).get(aloneTime_l20));
				double T12_l20 = Double.parseDouble(Data_driver.client_push11.get(arg).get(timeSharing12_l20));
				
			
				HashMap p = client_push2.get(0);
				
				HashMap q = client_push3.get(0);
				
				p.put(timeSharing23, String.valueOf(nCounter3-(nCounter1-(AT+T12))));
				
				p.put(timeSharing23_g20, String.valueOf(nCounter3_more-(nCounter1_more-(AT_g20+T12_g20))));
				p.put(timeSharing23_l20, String.valueOf(nCounter3_less-(nCounter1_less-(AT_l20+T12_l20))));
				
				p.put(distanceSharing23,String.valueOf(min_dist));
				
				q.put(timeSharing23, String.valueOf(nCounter3-(nCounter1-(AT+T12))));
				q.put(distanceSharing23,String.valueOf(min_dist));
				
				q.put(timeSharing23_g20, String.valueOf(nCounter3_more-(nCounter1_more-(AT_g20+T12_g20))));
				q.put(timeSharing23_l20, String.valueOf(nCounter3_less-(nCounter1_less-(AT_l20+T12_l20))));
				
				client_push2.clear();
				client_push3.clear();
				
				client_push2.add((HashMap) p);
				client_push3.add((HashMap) q);
				
			
				
			} else {
				// add time for 3 alone
				
				HashMap p = client_push3.get(0);
			
				
				double TS13 = Double.parseDouble(client_push3.get(0).get(timeSharing13)); 
				double TS23 = Double.parseDouble(client_push3.get(0).get(timeSharing23));
				double TS123 = Double.parseDouble(client_push3.get(0).get(timeSharing123));
				
				double TS13_g20 = Double.parseDouble(client_push3.get(0).get(timeSharing13_g20)); 
				double TS123_g20 = Double.parseDouble(client_push3.get(0).get(timeSharing123_g20));
				
				double TS13_l20 = Double.parseDouble(client_push3.get(0).get(timeSharing13_l20)); 
				double TS123_l20 = Double.parseDouble(client_push3.get(0).get(timeSharing123_l20));
				
				double TS23_g20 = Double.parseDouble(client_push3.get(0).get(timeSharing23_g20));
				double TS23_l20 = Double.parseDouble(client_push3.get(0).get(timeSharing23_l20));
				
				p.put(aloneTime, String.valueOf(nCounter3-(TS13+TS23+TS123)));
				
				p.put(aloneTime_g20, String.valueOf(nCounter3-(TS13_g20+TS23_g20+TS123_g20)));
				p.put(aloneTime_l20, String.valueOf(nCounter3-(TS13_l20+TS23_l20+TS123_l20)));
				
			
				
				

				p.put(aloneDistance, String.valueOf(Math.abs(distance3-min_dist)));
				
				
				client_push3.clear();
				client_push3.add((HashMap) p);
				
				
				
			}

			f_ad =  distance3 -minDistance;  //Double.parseDouble(client_push3.get(0).get(aloneDistance));
		
			
			f_at =  Double.parseDouble(client_push3.get(0).get(aloneTime));
			
			f_at_g20 =  Double.parseDouble(client_push3.get(0).get(aloneTime_g20));
			f_at_l20 =  Double.parseDouble(client_push3.get(0).get(aloneTime_l20));
		
			
			f_d12 =  Double.parseDouble(client_push3.get(0).get(distanceSharing12));
		
			
			f_d13 =  Double.parseDouble(client_push3.get(0).get(distanceSharing13));
		
			
			f_d123 =  minDistance;//Double.parseDouble(client_push3.get(0).get(distanceSharing123));
			
			
			f_t12 =  Double.parseDouble(client_push3.get(0).get(timeSharing12));
			
			f_t12_g20 =  Double.parseDouble(client_push3.get(0).get(timeSharing12_g20));
			f_t12_l20 =  Double.parseDouble(client_push3.get(0).get(timeSharing12_l20));
			
			
			f_t13 =  Double.parseDouble(client_push3.get(0).get(timeSharing13));
			
			f_t13_g20 =  Double.parseDouble(client_push3.get(0).get(timeSharing13_g20));
			f_t13_l20 =  Double.parseDouble(client_push3.get(0).get(timeSharing13_l20));
			
			
			f_t123 =  Double.parseDouble(client_push3.get(0).get(timeSharing123));
			
			f_t123_g20 =  Double.parseDouble(client_push3.get(0).get(timeSharing123_g20));
			f_t123_l20 =  Double.parseDouble(client_push3.get(0).get(timeSharing123_l20));
			
			
			client3_status  = false;
		}
		
		f_ad = (double) Math.round(f_ad * 100) / 100;
		
		f_at = (double) Math.round(f_at * 100) / 100;
		
		f_at_g20 = (double) Math.round(f_at_g20 * 100) / 100;
		
		f_at_l20 = (double) Math.round(f_at_l20 * 100) / 100;
	
		f_d12 = (double) Math.round(f_d12 * 100) / 100;
		

		f_d13 = (double) Math.round(f_d13 * 100) / 100;
		

		f_d123 = (double) Math.round(f_d123 * 100) / 100;
		
		
		f_t12 = (double) Math.round(f_t12 * 100) / 100;
		
		f_t12_g20 = (double) Math.round(f_t12_g20 * 100) / 100;
		
		f_t12_l20 = (double) Math.round(f_t12_l20 * 100) / 100;
		
	
		f_t13 = (double) Math.round(f_t13 * 100) / 100;
		
		f_t13_g20 = (double) Math.round(f_t13_g20 * 100) / 100;
		
		f_t13_l20 = (double) Math.round(f_t13_l20 * 100) / 100;
		
		f_t123 = (double) Math.round(f_t123 * 100) / 100;
		
		f_t123_g20 = (double) Math.round(f_t123_g20 * 100) / 100;
		
		f_t123_l20 = (double) Math.round(f_t123_l20 * 100) / 100;
		
		
		Log.e("f_d123",""+f_d123);
		Log.e("f_ad",""+f_ad);
		Log.e("f_at",""+f_at);
		Log.e("f_ad*price_km",""+f_ad*price_km);
		Log.e("(f_at/60)*price_min",""+(f_at/60)*price_min);
		Log.e("boarding_fee+f_ad*price_km+(f_at/60)*price_min",""+(boarding_fee+f_ad*price_km+(f_at/60)*price_min));
		
		Log.e("boarding_fee==",""+boarding_fee);
		Log.e("price_km==",""+price_km);
		Log.e("price_min==",""+price_min);

		 	price_20 = boarding_fee+f_ad*price_km+(f_at/60)*price_min; // for single
		 	
		 // price = boarding_fee+f_ad*price_km+(f_at_g20/60+(f_at_l20/60*price_min)); // for single
		 	
		  price = boarding_fee+f_ad*price_km+((f_at_l20/60*price_min)); // for single
		 	
		 	
		if(sharing == 2) {
			price_20 = boarding_fee+Math.abs(f_ad)*price_km+(f_at/60)*price_min+(+Math.abs(f_d123)*price_km)/2+((f_t12/60)*price_min)/2; // for double 
			
		//	price =  boarding_fee+Math.abs(f_ad)*price_km+(f_at_g20/60+((f_at_l20/60)*price_min))+(+Math.abs(f_d123)*price_km)/2+(f_t12_g20/60+((f_t12_l20/60)*price_min))/2; // for double 
			
			price =  boarding_fee+Math.abs(f_ad)*price_km+(((f_at_l20/60)*price_min))+(+Math.abs(f_d123)*price_km)/2+(((f_t12_l20/60)*price_min))/2; // for double 
			
		} else {
			price_20 = boarding_fee+Math.abs(f_ad)*price_km+(f_at/60)*price_min+((f_t12/60)*price_min+(f_t13/60)*price_min)/2+(+Math.abs(f_d123)*price_km)/3+((f_t123/60)*price_min)/3;//  for tripple
			
		//	price = boarding_fee+Math.abs(f_ad)*price_km+(f_at_g20/60+((f_at_l20/60)*price_min))+((f_t12_g20/60+((f_t12_l20/60)*price_min))+(f_t13_g20/60+((f_t13_l20/60)*price_min)))/2+(+Math.abs(f_d123)*price_km)/3+(f_t123_g20/60+((f_t123_l20/60)*price_min))/3;//  for tripple
			
			price = boarding_fee+Math.abs(f_ad)*price_km+(((f_at_l20/60)*price_min))+((((f_t12_l20/60)*price_min))+(((f_t13_l20/60)*price_min)))/2+(+Math.abs(f_d123)*price_km)/3+(((f_t123_l20/60)*price_min))/3;//  for tripple
		}
		Log.e("Final Price",""+price);
		
	
		
		return price;
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
	
	public class startTimer extends AsyncTask<Void, Void, Void> {
    	
		String reservation_id;
    	HashMap<String, String> result;
    	ArrayList localArrayList = new ArrayList();
    	
    
    	public startTimer(String string) {
			// TODO Auto-generated constructor stub
    		
    		reservation_id = string;
		}

		protected Void doInBackground(Void... paramVarArgs) {
    		
    		localArrayList.add(new BasicNameValuePair("id_reservation",this.reservation_id));
    		
    	
    		
    		result = function.startTimer(localArrayList);
    		Log.e("result==",""+result);
    		return null;
    	}
    	
    	protected void onPostExecute(Void paramVoid) {
    		db.dismiss();
    		if(result.get("result").toString().equalsIgnoreCase("OK")){
    		
    		//showAlertToUser("One reservation has been cancelled successfully.");
    	//	vertical_outer_layout_id3.removeViewAt(0);
    		}
    		
    		else {
    			
    			showAlertToUser("Something went wrong please try again.");
    			
    		}
    		
    	}
    	
    	protected void onPreExecute() {
			super.onPreExecute();
			db = new TransparentProgressDialog(Home_driver_new1.this,
					R.drawable.loading);
			db.show();
		}
    	
    }
}
