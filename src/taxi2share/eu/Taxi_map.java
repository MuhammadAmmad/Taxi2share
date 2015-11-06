package taxi2share.eu;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.TimeZone;

import java.util.List;
import java.util.Locale;
import android.graphics.PorterDuff;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONObject;

import taxi2share.eu.Partners.get_partners_info;
import taxi2share.eu.biz.Data;
import taxi2share.eu.biz.Function;
import taxi2share.eu.biz.GPSTracker;
import taxi2share.eu.biz.Info_for_a_trip;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;

import android.view.View.OnClickListener;
import android.view.Window;
import android.view.View.OnTouchListener;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.MultiAutoCompleteTextView.Tokenizer;

import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import taxi2share.eu.R;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.macrew.Utils.TransparentProgressDialog;

import com.macrew.imageloader.ImageLoader;


public class Taxi_map extends FragmentActivity {

	Address ADD = null;
	boolean actuelposition = true;
	String address = "";
	List<Address> addresses;
	Button another;
	LinearLayout back;
	LinearLayout cc;
	String city = "", locality = "", locality_own = "";
	String country = "";
	String countryCode = "";
	TransparentProgressDialog db;
	boolean delete = false;
	MultiAutoCompleteTextView depart;
	Address depart_add = null;
	MultiAutoCompleteTextView destination;
	Address destination_add = null;
	DecimalFormat df = new DecimalFormat();
	Button down_baga;
	Button down_pass;
	boolean edit_depart = true;
	boolean edit_destianation = false;
	SupportMapFragment fm;
	Function function = new Function();
	Geocoder geocoder;
	//GPSTracker gps;
	boolean gps_verif = true;
	String lat_s;
	SharedPreferences sharedpreferences;
	double lat_taxi;
	double latitude;
	Button cancel_dfault_depart;
	Location location;
	LocationManager locationManager;
	String lon_s;
	double lon_taxi;
	double longitude;
	GoogleMap mGoogleMap;
	Button myloc;
	boolean mylocation = true;
	TextView nbr_baga;
	TextView nbr_km, boarding, driving;
	TextView nbr_pass;
	LatLng new_postion;
	int number_add_marquer = 0;
	int number_geo = 0;
	int number_marquer_my_location = 0;
	int number_of_ = 0;
	MarkerOptions option_taxi, option_client;
	TextView price;
	ProgressDialog progresse_clacul;
	String provider;
	boolean search_blasa = true;
	boolean stop_ly_location = true;
	boolean stop_other_location = true;
	LinearLayout strep_file;
	Boolean iscatch = true;
	public ImageLoader imageLoader;
	RatingBar ratingBar1;
	PlacesTask placesTask;
	ParserTask parserTask;
	String strDate = "";
	double distance_to_destination = 0;
	Boolean isShownFirstTime = true;
	Boolean pickupNotCustomize = true;
	LinearLayout LL1;
	Boolean IsDestination = false ,IsDepart = false;
	int no_of_times_accuracy_checked = 0;
	
	getLatLngTask getLatLngObj;
	String place_id;
	List<HashMap<String, String>> mapresult;

	ArrayList<HashMap<String, String>> taxi_for_all = new ArrayList();
	ArrayList<HashMap<String, String>> taxi_for_all_temp = new ArrayList();
	ArrayList<HashMap<String, String>> partners = new ArrayList();

	Button up_bage, cancel;
	Button up_pass;
	LatLng utilis;
	int y = 0, x = 0;
	int count = 0;
	int i = 1, j = 1;
	View localView;
	int marker_pos;
	int partner_pos;

	
	private HashMap<String, String> markers;
	
	

	private static final String API = "AIzaSyBHv_xhbxCdSUfK-g_fVG2cMlBmt7nAG5E";
	
	private static final String PLACES_URL = "https://maps.googleapis.com/maps/api/place/details/json?";
	ArrayAdapter<String> adapter;

	/**
	 * 
	 * @param paramView
	 * 
	 *            called when down button is pressed
	 */
	public void num_editdown(View paramView) {

		i = Integer.parseInt(nbr_pass.getText().toString());

		if (i <= 1) {
		}

		else {
			i--;

			nbr_pass.setText(String.valueOf(i));
			Info_for_a_trip.nbr_passenger = String.valueOf(i);
		}
	}

	/**
	 * 
	 * @param paramView
	 *            called when up button is pressed
	 */
	public void num_editup(View paramView) {

		i = Integer.parseInt(nbr_pass.getText().toString());

		if (i >= 3) {

		}

		else {
			i++;

			nbr_pass.setText(String.valueOf(i));
			Info_for_a_trip.nbr_passenger = String.valueOf(i);
		}
	}

	public void num_editdown1(View paramView) {

		j = Integer.parseInt(nbr_baga.getText().toString());

		if (j <= 1) {
		}

		else {
			j--;

			nbr_baga.setText(String.valueOf(j));
			Info_for_a_trip.nbr_bagage = String.valueOf(j);
		}
	}

	public void num_editup1(View paramView) {

		j = Integer.parseInt(nbr_baga.getText().toString());

		if (j >= 3) {

		}

		else {
			j++;

			nbr_baga.setText(String.valueOf(j));
			Info_for_a_trip.nbr_bagage = String.valueOf(j);
		}
	}



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
								finish();
								Intent localIntent1 = new Intent(Taxi_map.this,
										Home.class);
								startActivity(localIntent1);
								Intent localIntent2 = new Intent(
										"android.settings.LOCATION_SOURCE_SETTINGS");
								startActivity(localIntent2);
							}
						});
		localBuilder.create().show();
	}

	private void showAlertToUser(String paramString) {
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

	public void back_home(View paramView) {
		finish();
		onBackPressed();
	}

	/**
	 * bloc_recherche() means search block
	 */

	public void bloc_recherche() {

		y++;

		edit_destianation = false;
		if (y == 1) {
			localView = LayoutInflater.from(this).inflate(
					R.layout.bloc_recherche, null);
			depart = ((MultiAutoCompleteTextView) localView.findViewById(R.id.pik));
			destination = ((MultiAutoCompleteTextView) localView
					.findViewById(R.id.des));
			down_pass = ((Button) localView.findViewById(R.id.down_pass));
			up_pass = ((Button) localView.findViewById(R.id.up_pass));
			down_baga = ((Button) localView.findViewById(R.id.down_baga));
			up_bage = ((Button) localView.findViewById(R.id.up_bage));
			nbr_pass = ((TextView) localView.findViewById(R.id.nbr_pass));
			nbr_baga = ((TextView) localView.findViewById(R.id.nbr_baga));
			LL1 = ((LinearLayout)localView.findViewById(R.id.ll1));
			cancel_dfault_depart = ((Button) localView.findViewById(R.id.cancel_dfault_depart));

			cancel = ((Button) localView.findViewById(R.id.cancel));

		}

		// destination.setAdapter(adapter);

		depart.setText(Info_for_a_trip.depart);
		depart.setEnabled(false);
		Info_for_a_trip.depart = this.depart.getText().toString();
	
		if (this.number_of_ == 0) {
			this.destination
					.setHint("Choose your destination from our suggestion ");
		}
		
		cancel_dfault_depart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				depart.setEnabled(true);
				pickupNotCustomize = false;
				depart.setText("");
				Info_for_a_trip.depart = "";
				depart.setHint("Choose your pick up point from our suggestion ");
				LL1.setBackgroundResource(R.drawable.celule_order);
				
				Animation localAnimation = AnimationUtils.loadAnimation(Taxi_map.this,
						R.anim.animate_bloc_recherche_hide);
				localAnimation.setFillAfter(true);
				localAnimation.setFillEnabled(true);
				strep_file.startAnimation(localAnimation);
				
			}
		});


		
		
		depart.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
				
				
				IsDestination = false;
				IsDepart = true;
				
				placesTask = new PlacesTask();
				placesTask.execute(s.toString());
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});

		destination.addTextChangedListener(new TextWatcher() {
			
			

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
					if(Info_for_a_trip.depart.equals("") || Info_for_a_trip.depart == null){
						showAlertToUser("Please select Pick up point.");
						return;
					}
				IsDestination = true;
				IsDepart = false;
				
				placesTask = new PlacesTask();
				placesTask.execute(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		
		depart.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				IsDestination = false;
				IsDepart = true;
				
				Info_for_a_trip.depart = mapresult.get(position).get(
						"description");
				place_id = mapresult.get(position).get("place_id");

				depart.setText(Info_for_a_trip.depart);

				getLatLngObj = new getLatLngTask();

				getLatLngObj.execute();
			}
		});

		destination.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				IsDestination = true;
				IsDepart = false;

				Info_for_a_trip.destination = mapresult.get(position).get(
						"description");
				place_id = mapresult.get(position).get("place_id");

				destination.setText(Info_for_a_trip.destination);

				getLatLngObj = new getLatLngTask();

				getLatLngObj.execute();
			}
		});

		if (y == 1) {
			cc.addView(localView);

		} else {

		}
		search_blasa = true;

		edit_destianation = true;

	}

	private void showGPSDisabledAlertToUser1(String paramString) {
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

	public float get_distance(double paramDouble1, double paramDouble2,
			double paramDouble3, double paramDouble4) {
		float[] arrayOfFloat = new float[1];
		Location.distanceBetween(paramDouble1, paramDouble2, paramDouble3,
				paramDouble4, arrayOfFloat);
		float f = arrayOfFloat[0] / 1000.0F;
		// macrew
		String str = String.valueOf(f);
		if (str.length() > 6) {
			f = Float.parseFloat(str.substring(0, 5));
		}
		return f;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.taxi_map);
		
	
		
		markers = new HashMap<String, String>();

		imageLoader = new ImageLoader(getApplicationContext());

		sharedpreferences = PreferenceManager
				.getDefaultSharedPreferences(Taxi_map.this);
	

		int i = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());
		this.df.setMaximumFractionDigits(2);
		this.df.setMinimumFractionDigits(2);
		if (i == 0) {
			if (!((LocationManager) getSystemService("location"))
					.isProviderEnabled("gps")) {
				showGPSDisabledAlertToUser();
				return;
			}
			final ProgressDialog localProgressDialog = ProgressDialog.show(
					this, "Please wait", "Scannig");

			
			
			new get_partners_details(sharedpreferences.getString("id_client", "")).execute(new Void[0]);

			geocoder = new Geocoder(this, Locale.getDefault());
			cc = ((LinearLayout) findViewById(R.id.cc));
			back = ((LinearLayout) findViewById(R.id.back));
			strep_file = ((LinearLayout) findViewById(R.id.strep_file));
			nbr_km = ((TextView) findViewById(R.id.nbr_km));
			price = ((TextView) findViewById(R.id.price));
			boarding = ((TextView) findViewById(R.id.boarding));
			driving = ((TextView) findViewById(R.id.driving));

			bloc_recherche();
			
		
			Animation localAnimation = AnimationUtils.loadAnimation(this,
					R.anim.animate_bloc_recherche_hide);
			localAnimation.setFillAfter(true);
			localAnimation.setFillEnabled(true);
			this.strep_file.startAnimation(localAnimation);
			fm = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map));
			fm.getMap();
			mGoogleMap = fm.getMap();
			mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
			mGoogleMap.setMyLocationEnabled(true);
			
			
			mGoogleMap
					.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

						public void onMyLocationChange(
								Location paramAnonymousLocation) {
							
							Log.e("paramAnonymousLocation.hasAccuracy()==",""+paramAnonymousLocation.hasAccuracy());
							Log.e("paramAnonymousLocation.getAccuracy()==",""+paramAnonymousLocation.getAccuracy());
							
						
//							 if (!paramAnonymousLocation.hasAccuracy()) {
//						            return;
//						        }
							if(no_of_times_accuracy_checked<3){
								
								no_of_times_accuracy_checked++;
						        if (paramAnonymousLocation.getAccuracy() > 25) {
						        	
						            return;
						        }
							}

							latitude = paramAnonymousLocation.getLatitude();
						
							longitude = paramAnonymousLocation.getLongitude();
							
							Info_for_a_trip.lat_dep = latitude;
							Info_for_a_trip.lon_dep = longitude;
							
							location = paramAnonymousLocation;

							if (number_geo == 0) {
								localProgressDialog.dismiss();
								utilis = new LatLng(latitude, longitude);
								mGoogleMap.moveCamera(CameraUpdateFactory
										.newLatLng(utilis));
								mGoogleMap.animateCamera(CameraUpdateFactory
										.zoomTo(9.0F));

								Marker TP = mGoogleMap.addMarker(new MarkerOptions()
										.position(utilis)
										.title("client")
										.icon(BitmapDescriptorFactory
												.fromResource(R.drawable.localisation_user)));

							}
							try {

								// changes
								iscatch = true;
								addresses = geocoder.getFromLocation(latitude,
										longitude, 1);
								countryCode = ((Address) addresses.get(0))
										.getCountryCode();
								locality = ((Address) addresses.get(0))
										.getLocality();
								address = ((Address) addresses.get(0))
										.getAddressLine(0);
								city = ((Address) addresses.get(0))
										.getAddressLine(1);
								country = ((Address) addresses.get(0))
										.getAddressLine(2);
								
								if(pickupNotCustomize){
								Info_for_a_trip.depart = (address + " " + city
										+ " " + country).replace("null", "");
								}
								if(isShownFirstTime){
								depart.setText(Info_for_a_trip.depart);
								isShownFirstTime = false;
								}

								number_geo = 1;
								stop_ly_location = false;
								return;
							} catch (Exception localException) {
								Log.e("Exception ==", "" + localException);
								localException.printStackTrace();
								if (iscatch) {
								//	showAlertToUser("Not able to fetch nearby locations. Please Try Again.");
									iscatch = false;
								}

							}
						}

					});
			Info_for_a_trip.nbr_bagage = "1";
			Info_for_a_trip.nbr_passenger = "1";
			final Handler localHandler1 = new Handler();
			localHandler1.postDelayed(new Runnable() {
				public void run() {
					if ((!stop_ly_location)
							&& (((LocationManager) Taxi_map.this
									.getSystemService("location"))
									.isProviderEnabled("gps"))) {

						Taxi_map localTaxi_map = Taxi_map.this;
						localTaxi_map.number_marquer_my_location = (1 + localTaxi_map.number_marquer_my_location);
						new get_taxi(latitude, longitude).execute(new Void[0]);

					}
					localHandler1.postDelayed(this, 5000L);
				}
			}, 1000L);
			final Handler localHandler2 = new Handler();
			localHandler2.postDelayed(new Runnable() {
				public void run() {
					if (!stop_other_location) {
						Taxi_map localTaxi_map = Taxi_map.this;
						localTaxi_map.number_add_marquer = (1 + localTaxi_map.number_add_marquer);

					}
					localHandler2.postDelayed(this, 5000L);
				}
			}, 1000L);

			back.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					finish();
					onBackPressed();
					return false;
				}
			});
			this.mGoogleMap
					.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
						public boolean onMarkerClick(Marker paramAnonymousMarker) {
							try {
								if (!paramAnonymousMarker.getTitle()
										.equalsIgnoreCase("client")) {
									Info_for_a_trip.num_taxi = paramAnonymousMarker
											.getTitle();
									if (!edit_destianation) {
										Toast.makeText(
												Taxi_map.this,
												"Please choose your destination from our suggestion !",
												1).show();
										return false;
									}

								}
							} catch (Exception localException) {
							}
							return false;
						}
					});
			return;
		}
		GooglePlayServicesUtil.getErrorDialog(i, this, 10).show();
	}

	public class get_taxi extends AsyncTask<Void, Void, Void> {
		double latitude;
		double longitude;

		public get_taxi(double paramDouble1, double paramDouble2) {
			this.latitude = paramDouble1;
			this.longitude = paramDouble2;

		}

		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected Void doInBackground(Void... paramVarArgs) {

			x++;
			taxi_for_all_temp = function.get_taxi_behind(this.latitude,
					this.longitude);

			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			try {

				mGoogleMap.clear();
				taxi_for_all.clear();
				//markers.clear();

				/************ adding partners marker ***************************/

				marPartneres();
				/***************************************************************/

				if (taxi_for_all_temp.get(0).get("result")
						.equalsIgnoreCase("OK")) {

					taxi_for_all.addAll(taxi_for_all_temp);

					taxi_for_all_temp.clear();

					for (int i = 0; i < taxi_for_all.size(); i++) {
						double lati = Double.parseDouble(taxi_for_all.get(i)
								.get("geo_latitude"));
						double longLat = Double.parseDouble(taxi_for_all.get(i)
								.get("geo_longitude"));

					

							option_taxi = new MarkerOptions();
							option_client = new MarkerOptions();

						//	 mGoogleMap.clear();

							LatLng localLatLng4 = new LatLng(this.latitude,
									this.longitude);
							option_client.position(localLatLng4);
							option_client
									.icon(BitmapDescriptorFactory
											.fromResource(R.drawable.localisation_user));

							option_client.title("client");
							mGoogleMap.addMarker(Taxi_map.this.option_client);
							
							LatLng localLatLng3 = new LatLng(lati, longLat);
							option_taxi.position(localLatLng3);

							if (taxi_for_all.get(i).get("nbr_places_occupees")
									.equals("0")) {
								option_taxi.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.taxi_libre));
							} else {
								option_taxi
										.icon(BitmapDescriptorFactory
												.fromResource(R.drawable.localisation_taxi_occuper));
							}
							mGoogleMap.addMarker(Taxi_map.this.option_taxi);
						//}

					}
					
		
				}

				else {

					stop_ly_location = true;
					stop_other_location = true;
					AlertDialog.Builder localBuilder = new AlertDialog.Builder(
							Taxi_map.this);
					localBuilder
							.setMessage(
									"There is no taxi available in your area, please call our taxi dispatch to order!")
							.setCancelable(false)
							.setPositiveButton("Call",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface paramAnonymousDialogInterface,
												int paramAnonymousInt) {
											startActivity(new Intent(
													"android.intent.action.DIAL",
													Uri.parse("tel: 0032/483.053.195")));
											paramAnonymousDialogInterface
													.cancel();
										}
									});
					localBuilder.setNegativeButton("Try again later",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface paramAnonymousDialogInterface,
										int paramAnonymousInt) {
									finish();
									Intent localIntent = new Intent(
											Taxi_map.this, Home.class);
									startActivity(localIntent);
								}
							});
					localBuilder.create().show();
					return;
				}
				
				mGoogleMap
				.setOnMarkerClickListener(new OnMarkerClickListener() {

					@Override
					public boolean onMarkerClick(Marker arg0) {
						
						

					
						
						if (arg0.getTitle() == null&& arg0.getSnippet() == null) 
						{
							
						

							LatLng latlong = arg0.getPosition();
							double d1 = latlong.latitude;
							double d2 = latlong.longitude;

							for (int i = 0; i < taxi_for_all.size(); i++) {
								String geo_lat = taxi_for_all
										.get(i).get("geo_latitude");
								String geo_long = taxi_for_all.get(
										i).get("geo_longitude");

								if (geo_lat.contains(String
										.valueOf(d1))
										&& geo_long.contains(String
												.valueOf(d2))) {
									marker_pos = i;
									if (taxi_for_all
											.get(i)
											.get("nbr_places_occupees")
											.equals("0")) {
									
										DisplayBookingDialog();
									}

									else if (taxi_for_all
											.get(i)
											.get("nbr_places_occupees")
											.equals("1")) {
									
										DisplayBookingDialogWith1pass();
									}

									else if (taxi_for_all
											.get(i)
											.get("nbr_places_occupees")
											.equals("2")) {
										DisplayBookingDialogWith2pass();
									}
									break;
								}

							}
						}
						
						else if (arg0.getTitle()==null && arg0.getSnippet()!=null){
							
							LatLng latlong = arg0.getPosition();
							double d1 = latlong.latitude;
							double d2 = latlong.longitude;

							for (int i = 0; i < partners.size(); i++) {
								String geo_lat = partners
										.get(i).get("lat");
								String geo_long = partners.get(
										i).get("lon");

								if (geo_lat.contains(String
										.valueOf(d1))
										&& geo_long.contains(String
												.valueOf(d2))) {
									partner_pos = i;
									DisplayPartnersDialog();
								}
								
							}
						}
						
					
						return false;
					}

					private void DisplayBookingDialogWith2pass() {
						final Dialog dialog;
						Button reserver, cancel;
						ImageView pass1, pass2 , driver_pic;
						TextView client1, client2;

						final TextView matricule;
						TextView discription, plaque;
						;

						dialog = new Dialog(Taxi_map.this);
						dialog.setCancelable(false);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.getWindow().setFormat(
								PixelFormat.TRANSLUCENT);

						Drawable d = new ColorDrawable(Color.BLACK);
						d.setAlpha(0);
						dialog.getWindow().setBackgroundDrawable(d);

						dialog.setContentView(R.layout.two_pass);

						reserver = (Button) dialog
								.findViewById(R.id.reserver);
						cancel = (Button) dialog
								.findViewById(R.id.cancel);
						matricule = (TextView) dialog
								.findViewById(R.id.matricule);
						client1 = (TextView) dialog
								.findViewById(R.id.client1);
						client2 = (TextView) dialog
								.findViewById(R.id.client2);
						pass1 = (ImageView) dialog
								.findViewById(R.id.pass1);
						pass2 = (ImageView) dialog
								.findViewById(R.id.pass2);
						
						driver_pic = (ImageView) dialog
								.findViewById(R.id.driver_pic);

						discription = (TextView) dialog
								.findViewById(R.id.discription);

						plaque = (TextView) dialog
								.findViewById(R.id.plaque);

						ratingBar1 = (RatingBar) dialog
								.findViewById(R.id.ratingBar1);

						float rating = Float.valueOf(taxi_for_all
								.get(marker_pos).get("ratings"));

						ratingBar1.setRating(rating);

						discription.setText(taxi_for_all.get(
								marker_pos).get("discription"));

						plaque.setText(taxi_for_all.get(marker_pos)
								.get("plaque"));
						
						String driver_pic_url = "http://taxi2share.eu/apps/android/webService_post/uploads/"+
						taxi_for_all.get(marker_pos)
								.get("driver_image");
						
						imageLoader.DisplayImage(driver_pic_url, driver_pic);

						matricule.setText(String
								.valueOf(taxi_for_all.get(
										marker_pos).get("num_taxi")));
						client1.setText(taxi_for_all
								.get(marker_pos)
								.get("destinations"));

						client2.setText(taxi_for_all
								.get(marker_pos).get(
										"destinations2"));
						try {

							String url = "http://taxi2share.eu/apps/android/webService_post/uploads/"
									+ taxi_for_all.get(marker_pos)
											.get("image")
											.toString();
							imageLoader.DisplayImage(url, pass1);

						}

						catch (Exception e) {
							Log.e("Exception", "" + e);
							pass1.setImageResource(R.drawable.taxi2share90);
						}

						try {
							String url2 = "http://taxi2share.eu/apps/android/webService_post/uploads/"
									+ taxi_for_all.get(marker_pos)
											.get("image2")
											.toString();
							imageLoader.DisplayImage(url2, pass2);
						}

						catch (Exception e) {
							Log.e("Exception", "" + e);
							pass2.setImageResource(R.drawable.taxi2share90);
						}

						cancel.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {

								dialog.dismiss();

							}
						});
						reserver.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								try {

									if (!(Info_for_a_trip.destination == null || Info_for_a_trip.destination.equals(""))) {
										dialog.dismiss();

										if ((Integer
												.parseInt(taxi_for_all
														.get(marker_pos)
														.get("nbr_places_occupees")) + Integer
												.parseInt(Info_for_a_trip.nbr_passenger)) <= 3) {

											Info_for_a_trip.num_taxi = String
													.valueOf(matricule
															.getText());
											Info_for_a_trip.driver_id = taxi_for_all
													.get(marker_pos)
													.get("id_chauffeur");

											Intent i = new Intent(
													Taxi_map.this,
													Paiment.class);

											startActivity(i);

										}

										else {

											showGPSDisabledAlertToUser1("Number of places not enough! Choose another taxi");

										}
									}

									else {
										dialog.dismiss();
										AlertDialog.Builder localBuilder2 = new AlertDialog.Builder(
												Taxi_map.this);
										localBuilder2
												.setTitle("Destination Requried");
										localBuilder2
												.setMessage(
														"Please select the destination.")
												.setCancelable(
														false)
												.setPositiveButton(
														"Ok",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface paramAnonymous2DialogInterface,
																	int paramAnonymous2Int) {
																paramAnonymous2DialogInterface
																		.cancel();
															}
														});
										localBuilder2.create()
												.show();
										return;
									}

								}

								catch (Exception e) {
									Log.i("Exception on booking",
											"" + e);
									showGPSDisabledAlertToUser1("Error occurred.Please try again.");

								}

							}
						});
						dialog.show();

					}

					private void DisplayBookingDialogWith1pass() {
						final Dialog dialog;
						Button reserver, cancel;
						ImageView pass1 , driver_pic;

						final TextView matricule;
						TextView discription, client1;
						TextView plaque;
						dialog = new Dialog(Taxi_map.this);
						dialog.setCancelable(false);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.getWindow().setFormat(
								PixelFormat.TRANSLUCENT);

						Drawable d = new ColorDrawable(Color.BLACK);
						d.setAlpha(0);
						dialog.getWindow().setBackgroundDrawable(d);

						dialog.setContentView(R.layout.one_pass);
						

						reserver = (Button) dialog
								.findViewById(R.id.reserver);
						pass1 = (ImageView) dialog
								.findViewById(R.id.pass1);
						cancel = (Button) dialog
								.findViewById(R.id.cancel);
						matricule = (TextView) dialog
								.findViewById(R.id.matricule);
						plaque = (TextView) dialog
								.findViewById(R.id.plaque);
						discription = (TextView) dialog
								.findViewById(R.id.discription);
						client1 = (TextView) dialog
								.findViewById(R.id.client1);
						driver_pic = (ImageView) dialog.findViewById(R.id.driver_pic);

						ratingBar1 = (RatingBar) dialog
								.findViewById(R.id.ratingBar1);

						float rating = Float.valueOf(taxi_for_all
								.get(marker_pos).get("ratings"));

						ratingBar1.setRating(rating);

						try {

							String url = "http://taxi2share.eu/apps/android/webService_post/uploads/"
									+ taxi_for_all.get(marker_pos)
											.get("image")
											.toString();
							imageLoader.DisplayImage(url, pass1);
							
							

						}

						catch (Exception e) {
							Log.e("Exception", "" + e);
							pass1.setImageResource(R.drawable.taxi2share90);
						}
						discription.setText(taxi_for_all.get(
								marker_pos).get("discription"));

						plaque.setText(taxi_for_all.get(marker_pos)
								.get("plaque"));
						
						String driver_img_url = "http://taxi2share.eu/apps/android/webService_post/uploads/"+
						taxi_for_all.get(marker_pos).get("driver_image");
						imageLoader.DisplayImage(driver_img_url, driver_pic);

						client1.setText(taxi_for_all
								.get(marker_pos)
								.get("destinations"));

						matricule.setText(String
								.valueOf(taxi_for_all.get(
										marker_pos).get("num_taxi")));
						cancel.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								dialog.dismiss();

							}
						});
						reserver.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {

								if (!(Info_for_a_trip.destination == null || Info_for_a_trip.destination.equals("") )) {
									dialog.dismiss();

									if ((Integer
											.parseInt(taxi_for_all
													.get(marker_pos)
													.get("nbr_places_occupees")) + Integer
											.parseInt(Info_for_a_trip.nbr_passenger)) <= 3) {

										Info_for_a_trip.num_taxi = String
												.valueOf(matricule
														.getText());
										Info_for_a_trip.driver_id = taxi_for_all
												.get(marker_pos)
												.get("id_chauffeur");

										Intent i = new Intent(
												Taxi_map.this,
												Paiment.class);
										startActivity(i);

									}

									else {
										showGPSDisabledAlertToUser1("Number of places not enough! Choose another taxi");

									}
								}

								else {
									dialog.dismiss();
									AlertDialog.Builder localBuilder2 = new AlertDialog.Builder(
											Taxi_map.this);
									localBuilder2
											.setTitle("Destination Requried");
									localBuilder2
											.setMessage(
													"Please select the destination.")
											.setCancelable(false)
											.setPositiveButton(
													"Ok",
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

							}
						});
						dialog.show();

					}

					private void DisplayBookingDialog() {
						final Dialog dialog;
						Button reserver, cancel;
						TextView plaque;
						final TextView matricule;
						TextView discription;
						ImageView driver_pic;

						final RadioGroup share_taxi_option_group;

						RadioButton radioButton0, radioButton1, radioButton2;

						dialog = new Dialog(Taxi_map.this);
						dialog.setCancelable(false);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.getWindow().setFormat(
								PixelFormat.TRANSLUCENT);

						Drawable d = new ColorDrawable(Color.BLACK);
						d.setAlpha(0);
						dialog.getWindow().setBackgroundDrawable(d);

						dialog.setContentView(R.layout.marque);

						share_taxi_option_group = (RadioGroup) dialog
								.findViewById(R.id.share_taxi_option_group);
						radioButton0 = (RadioButton) dialog
								.findViewById(R.id.radioButton0);
						radioButton1 = (RadioButton) dialog
								.findViewById(R.id.radioButton1);
						radioButton2 = (RadioButton) dialog
								.findViewById(R.id.radioButton2);
						
						ratingBar1 = (RatingBar) dialog
								.findViewById(R.id.ratingBar1);
						if (Info_for_a_trip.nbr_passenger
								.equals("3")) {
							radioButton1.setEnabled(false);
							radioButton2.setEnabled(false);
						}

						else if (Info_for_a_trip.nbr_passenger
								.equals("2")) {
							radioButton2.setEnabled(false);
						}

						reserver = (Button) dialog
								.findViewById(R.id.reserver);
						cancel = (Button) dialog
								.findViewById(R.id.cancel);
						matricule = (TextView) dialog
								.findViewById(R.id.matricule);

						plaque = (TextView) dialog
								.findViewById(R.id.plaque);
						
						driver_pic = (ImageView) dialog
								.findViewById(R.id.driver_pic);

						discription = (TextView) dialog
								.findViewById(R.id.discription);

						discription.setText(taxi_for_all.get(
								marker_pos).get("discription"));
						float rating = Float.valueOf(taxi_for_all
								.get(marker_pos).get("ratings"));

						ratingBar1.setRating(rating);

						plaque.setText(taxi_for_all.get(marker_pos)
								.get("plaque"));
						
						String url_driver_pic = "http://taxi2share.eu/apps/android/webService_post/uploads/"+
						taxi_for_all.get(marker_pos)
								.get("driver_image");
						
					
						
						imageLoader.DisplayImage(url_driver_pic, driver_pic);
						

						matricule.setText(String
								.valueOf(taxi_for_all.get(
										marker_pos).get("num_taxi")));
						cancel.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {

								int selectedId = share_taxi_option_group
										.getCheckedRadioButtonId();

								RadioButton rb = (RadioButton) dialog
										.findViewById(selectedId);
								// Toast.makeText(Taxi_map.this,
								// rb.getText(), 1).show();
								dialog.dismiss();

							}

							private void addListenerRadioGroup1() {

								// get selected radio button from
								// radioGroupCricket
								int selected = share_taxi_option_group
										.getCheckedRadioButtonId();
								RadioButton rb = (RadioButton) findViewById(selected);
								String share_with = (String) rb
										.getText();
								Log.e("share with ==", "=="
										+ share_with);

							}

						});
						reserver.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {

								if (!(Info_for_a_trip.destination == null || Info_for_a_trip.destination.equals(""))) {
									dialog.dismiss();

									if ((Integer
											.parseInt(taxi_for_all
													.get(marker_pos)
													.get("nbr_places_occupees")) + Integer
											.parseInt(Info_for_a_trip.nbr_passenger)) <= 3) {
										Info_for_a_trip.num_taxi = String
												.valueOf(matricule
														.getText());

										Info_for_a_trip.driver_id = taxi_for_all
												.get(marker_pos)
												.get("id_chauffeur");
										// showGPSDisabledAlertToUser1("Testing");

										int selectedId = share_taxi_option_group
												.getCheckedRadioButtonId();

										RadioButton rb = (RadioButton) dialog
												.findViewById(selectedId);
										Intent i = new Intent(
												Taxi_map.this,
												Paiment.class);
										i.putExtra(
												"shareable_with",
												rb.getText());
										startActivity(i);
									}

									else {
										showGPSDisabledAlertToUser1("Number of places not enough! Choose another taxi");
										/*
										 * Toast.makeText(
										 * Taxi_map.this,
										 * "Number of places not enough! Choose another taxi"
										 * , 1).show();
										 */
									}
								}

								else {
									dialog.dismiss();
									AlertDialog.Builder localBuilder2 = new AlertDialog.Builder(
											Taxi_map.this);
									localBuilder2
											.setTitle("Destination Requried");
									localBuilder2
											.setMessage(
													"Please select the destination.")
											.setCancelable(false)
											.setPositiveButton(
													"Ok",
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

							}
						});
						dialog.show();

					}
				});
			} catch (Exception e) {
				Log.e("***************Excepiton==**********", "=" + e);

			}

		}

		protected void DisplayPartnersDialog() {
			final Dialog dialog;
			Button ok;
			TextView name , address,phone,hours;
			ImageView logo;
			final TextView website;
			TextView email, s_presentation, promo;
			dialog = new Dialog(Taxi_map.this);
			dialog.setCancelable(false);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setFormat(
					PixelFormat.TRANSLUCENT);

			Drawable d = new ColorDrawable(Color.BLACK);
			d.setAlpha(0);
			dialog.getWindow().setBackgroundDrawable(d);

			dialog.setContentView(R.layout.partners_info);
			
			
			ok = (Button)dialog.findViewById(R.id.ok);
			name = (TextView)dialog.findViewById(R.id.name);
			address = (TextView)dialog.findViewById(R.id.address);
			phone = (TextView)dialog.findViewById(R.id.phone);
			hours = (TextView)dialog.findViewById(R.id.hours);
			website = (TextView)dialog.findViewById(R.id.website);
			email = (TextView)dialog.findViewById(R.id.email);
			s_presentation = (TextView)dialog.findViewById(R.id.s_presentation);
			promo = (TextView)dialog.findViewById(R.id.promo);
			logo = (ImageView)dialog.findViewById(R.id.logo);
			
			
			
			name.setText(partners.get(partner_pos).get("name"));
			address.setText(partners.get(partner_pos).get("address"));
			phone.setText(partners.get(partner_pos).get("phone"));
			hours.setText(partners.get(partner_pos).get("opening_hours"));
			
			final String phn= partners.get(partner_pos).get("phone");
			SpannableString content1 = new SpannableString(phn);
			content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0); 
			phone.setText(content1);
			
			String path = partners.get(partner_pos).get("side_image");
			if(URLUtil.isValidUrl(path)){
				path =path;
			}
			
			else {
				path = "http://"+path;
			}
			
			imageLoader.DisplayImage(path, logo);
			
			String link= partners.get(partner_pos).get("website");
			SpannableString content = new SpannableString(link);
			content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
			website.setText(content);
			
			phone.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(new Intent(
							"android.intent.action.DIAL",
							Uri.parse("tel: "+phn)));
					
				}
			});
			
			try{
			
			website.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(website.getText()!="" || website.getText()!=null){
						String url = "http://"+website.getText().toString();
					
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(url));
						startActivity(i);
					}
					
				}
			});
			}
			
			catch(Exception e){
				showGPSDisabledAlertToUser1("Not able to open the link.Please try again.");
				
			}
			email.setText(partners.get(partner_pos).get("email"));
			promo.setText(partners.get(partner_pos).get("promotion"));
			s_presentation.setText(partners.get(partner_pos).get("small_presentation"));
			
			ok.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					
				}
			});
			dialog.show();
			
		}
	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	// Fetches all places from GooglePlaces AutoComplete Web Service
	private class PlacesTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... place) {
			// For storing data from web service
			String data = "";

			// Obtain browser key from https://code.google.com/apis/console

			String input = "";

			try {
				input = "input=" + URLEncoder.encode(place[0], "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();

			}

			// place type to be searched
			String types = "types=geocode";

			// Sensor enabled
			String sensor = "sensor=false";
			// String locale =
			// getapp\.getResources().getConfiguration().locale.getCountry();
			// Building the parameters to the web service
			String parameters = input + "&" + types + "&" + sensor + "&key="
					+ API + "&components=country:" + countryCode;
			// String parameters = input+"&"+types+"&"+sensor+"&key="+API;
			// Output format
			String output = "json";

			// Building the url to the web service
			String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"
					+ output + "?" + parameters;
			
			Log.e("places url==",""+url);

			try {
				// Fetching the data from we service
				data = downloadUrl(url);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			// Creating ParserTask
			parserTask = new ParserTask();

			// Starting Parsing the JSON string returned by Web Service
			parserTask.execute(result);
			Log.e("places result==",""+result);
		}
	}

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<HashMap<String, String>>> {

		JSONObject jObject;

		@Override
		protected List<HashMap<String, String>> doInBackground(
				String... jsonData) {

			List<HashMap<String, String>> places = null;

			PlaceJSONParser placeJsonParser = new PlaceJSONParser();

			try {
				jObject = new JSONObject(jsonData[0]);

				// Getting the parsed data as a List construct
				places = placeJsonParser.parse(jObject);

			} catch (Exception e) {
				Log.d("Exception", e.toString());
			}
			return places;
		}

		@Override
		protected void onPostExecute(List<HashMap<String, String>> result) {
			mapresult = new ArrayList<HashMap<String, String>>();

			String[] from = new String[] { "description" };
			mapresult = result;

			int[] to = new int[] { android.R.id.text1 };

			// Creating a SimpleAdapter for the AutoCompleteTextView
			SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), result,
					R.layout.list_item, from, to);

			// Setting the adapter
			
			if(IsDestination){
			destination.setAdapter(adapter);
			destination.setTokenizer(new SpaceTokenizer());
			}
			
			else if(IsDepart){
				depart.setAdapter(adapter);
				depart.setTokenizer(new SpaceTokenizer());
			}
		}
	}
	/**
	 * 
	 * @author Desktop
	 * get lat long web service
	 */
	private class getLatLngTask extends AsyncTask<Void, Void, String> {
		@Override
		protected void onPreExecute() {
			db = new TransparentProgressDialog(Taxi_map.this,
					R.drawable.loading);
			db.show();
		}

		protected String doInBackground(Void... placesURL) {

			HttpClient httpclient = new DefaultHttpClient();

			String u = PLACES_URL + "key=" + API + "&" + "placeid=" + place_id;

			HttpGet httpget = new HttpGet(u);

			String result = null;
			try {

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httpget);

				StatusLine statusLine = response.getStatusLine();

				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					out.close();

					result = out.toString();

				} else {
					// close connection
					response.getEntity().getContent().close();
					throw new IOException(statusLine.getReasonPhrase());
				}

			} catch (Exception e) {
				Log.e("Error:", e.getMessage());
				// return result;
			}

			return result;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			db.dismiss();
			Log.e("result==", "" + result);
			try {
				JSONObject jsonObj = new JSONObject(result);

				JSONObject jsn = jsonObj.getJSONObject("result");

			
				JSONObject c1 = jsn.getJSONObject("geometry");
				JSONObject c2 = c1.getJSONObject("location");
				String lat = c2.getString("lat");
				String lng = c2.getString("lng");

				double latitude = Double.parseDouble(lat);
				double longitude = Double.parseDouble(lng);
				
				if(IsDestination){

				Info_for_a_trip.lat_des = latitude;
				Info_for_a_trip.lon_des = longitude;
				}
				
				else if(IsDepart){
					Info_for_a_trip.lat_dep = latitude;
					Info_for_a_trip.lon_dep = longitude;
				}
				
				
				/******************* finding city and locality *****************************/
				if(IsDestination){
				
				addresses = geocoder.getFromLocation(latitude, longitude, 1);
				countryCode = ((Address) addresses.get(0)).getCountryCode();
				locality_own = get_locality(latitude, longitude);
				address = ((Address) addresses.get(0)).getAddressLine(0);
				city = ((Address) addresses.get(0)).getAddressLine(1);
				country = ((Address) addresses.get(0)).getAddressLine(2);
				
				
				Info_for_a_trip.locality_des = locality_own;
			

				Calendar cal = Calendar.getInstance();
				TimeZone tz = cal.getTimeZone();
				Log.d("Time zone", "=" + tz.getDisplayName());

				cal.setTimeZone(tz);
				Date date = cal.getTime();
				SimpleDateFormat df = new SimpleDateFormat("kk.mm");
				strDate = df.format(date);
				Log.e("date format==", "" + strDate);

				Editor editor = sharedpreferences.edit();
				editor.putString("locality_own", locality_own);
				editor.putString("strDate", strDate);
				editor.commit();
				
				}
				
				if(Info_for_a_trip.destination.equals("") || Info_for_a_trip.destination == null){
				}
				
				else {
					new getDistance().execute(new Void[0]);
				}
				
			
				/**************************************************************************/

			}

			catch (Exception ae) {
				Log.e("Exception=", "=" + ae);

			}
		}
	}

	private class SpaceTokenizer implements Tokenizer {

		private final char delimiter = ' ';

		public int findTokenStart(CharSequence text, int cursor) {
			int i = cursor;

			while (i > 0 && text.charAt(i - 1) != delimiter) {
				i--;
			}
			while (i < cursor && text.charAt(i) == delimiter) {
				i++;
			}

			return i;
		}

		public int findTokenEnd(CharSequence text, int cursor) {
			int i = cursor;
			int len = text.length();

			while (i < len) {
				if (text.charAt(i) == delimiter) {
					return i;
				} else {
					i++;
				}
			}

			return len;
		}

		public CharSequence terminateToken(CharSequence text) {
			int i = text.length();
			while (i > 0 && text.charAt(i - 1) == delimiter) {
				i--;
			}

			return text;

		}

	}

	public String get_locality(double latitude, double longitude) {
		try {
			addresses = geocoder.getFromLocation(latitude, longitude, 1);
		} catch (IOException e) {
			Log.e("Exception==", "" + e);
		}
		String loc = "";
		return loc = ((Address) addresses.get(0)).getLocality();
	}

	public class get_city_price extends AsyncTask<Void, Void, Void> {
		ArrayList localArrayList = new ArrayList();
		HashMap city_price;

		public get_city_price() {
			// TODO Auto-generated constructor stub
		}

		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected Void doInBackground(Void... paramVarArgs) {

			localArrayList.add(new BasicNameValuePair("city", locality_own));
			localArrayList.add(new BasicNameValuePair("time", strDate));
			city_price = function.get_city_price(localArrayList);

			Log.d("city price===>>", "" + city_price);
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			try {
				if (city_price.get("result").toString().equalsIgnoreCase("OK")) {
					
					

					double boarding_fee, price_per_minute, price_per_km;

					boarding_fee = Double.valueOf((String) city_price
							.get("boarding_fee"));
					price_per_minute = Double.valueOf((String) city_price
							.get("price_per_minute"));
					price_per_km = Double.valueOf((String) city_price
							.get("price_per_km"));

					Editor editor = sharedpreferences.edit();
					editor.putString("boarding_fee",
							(String) city_price.get("boarding_fee"));
					editor.putString("price_per_minute",
							(String) city_price.get("price_per_minute"));
					editor.putString("price_per_km",
							(String) city_price.get("price_per_km"));
					editor.commit();

				
					try {
						nbr_km.setText(distance_to_destination + " Km");
						Editor editor1 = sharedpreferences.edit();
						editor1.putString("distance",
								String.valueOf(distance_to_destination));

						boarding.setText("" + boarding_fee + " €");
						driving.setText("" + price_per_minute + " €/M");

						double roundOff = (double) Math
								.round((price_per_km * distance_to_destination) * 100) / 100;

						price.setText("" + roundOff + "€");

						editor1.putString("price", "" + roundOff);
						editor1.commit();
						// Info_for_a_trip.price = 1.8D * f;
						Info_for_a_trip.price = roundOff;

						Animation localAnimation = AnimationUtils
								.loadAnimation(Taxi_map.this,
										R.anim.animate_bloc_recherche_show);
						localAnimation.setFillAfter(true);
						localAnimation.setFillEnabled(true);
						strep_file.startAnimation(localAnimation);
						return;
					} catch (Exception localException) {

						nbr_km.setText("No Service");
						price.setText("No Service");
						Info_for_a_trip.price = 0.0D;

					}

				}
			} catch (Exception e) {
				Log.e("EXCEPTION=", "" + e);
			}
		}

	}

	public class get_partners_details extends AsyncTask<Void, Void, Void> {
		ArrayList result;
		String client_id;

		public get_partners_details(String paramString1) {
			this.client_id = paramString1;

		}

		protected Void doInBackground(Void... paramVarArgs) {

			this.result = function.get_partners_details(this.client_id);
			Log.i("result==", "" + result);

			partners = result;
			return null;
		}

		protected void onPostExecute(Void paramVoid) {

			try {
				marPartneres();


			}

			catch (Exception ae) {
				Log.e("Exception==", "" + ae);
			}
		}

		protected void onPreExecute() {

		}
	}
	public class InfoWindowAdapterMarker implements InfoWindowAdapter {

        private Marker markerShowingInfoWindow;
        private Context mContext;
        public InfoWindowAdapterMarker(Context context) {
            mContext = context;
        }
        
        @Override
        public View getInfoWindow(Marker marker) {

            markerShowingInfoWindow = marker;
            
            
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            
            // Getting view from the layout file info_window_layout
            View popUp = inflater.inflate(R.layout.partner_marker, null);

         /*  TextView popUpTitle = (TextView) popUp.findViewById(R.id.popup_title);
            TextView popUpContent = (TextView) popUp.findViewById(R.id.popup_content);*/
            ImageView popUpImage = (ImageView) popUp.findViewById(R.id.popup_image);

          /* popUpTitle.setText(marker.getTitle());
            popUpContent.setText(marker.getSnippet());*/
            
            // Load the image thumbnail
            final String imagePath = markers.get(markerShowingInfoWindow.getId());
            
            if (markerShowingInfoWindow != null && markerShowingInfoWindow.isInfoWindowShown()) {
              markerShowingInfoWindow.hideInfoWindow();
              markerShowingInfoWindow.showInfoWindow();
            }
            
            Log.d("****************************************","***********************************");
            Log.d("imagePath====",""+markers);
            
            Log.d("imagePath====",""+imagePath+"=="+markerShowingInfoWindow.getId());
            imageLoader.DisplayImage(imagePath, popUpImage);
            
            // Returning the view containing InfoWindow contents
            return popUp;
        }

        @Override
        public View getInfoContents(Marker marker) {
            
            return null;
        }
        
        /**
         * This method is called after the bitmap has been loaded. It checks if the currently displayed
         * info window is the same info window which has been saved. If it is, then refresh the window
         * to display the newly loaded image.
         */
//        private Callback onImageLoaded = new Callback() {
//            
//            @Override
//            public void execute(String result) {
//                if (markerShowingInfoWindow != null && markerShowingInfoWindow.isInfoWindowShown()) {
//                    markerShowingInfoWindow.hideInfoWindow();
//                    markerShowingInfoWindow.showInfoWindow();
//                }
//            }
//        };

    }
	
	private void marPartneres () {
		try {

			if (partners.size() > 0) {
				for (int i = 0; i < partners.size(); i++) {
					double lati = Double.parseDouble(partners.get(i)
							.get("lat"));
					double longLat = Double.parseDouble(partners.get(i)
							.get("lon"));

					


						// mGoogleMap.clear();

					LatLng localLatLng4 = new LatLng(lati, longLat);
					
					View marker_view = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.partner_marker, null);
					
					ImageView popup_image , marker;
				
					
					popup_image = (ImageView) marker_view.findViewById(R.id.popup_image);
					marker = (ImageView) marker_view.findViewById(R.id.marker);
					
					String color_name = (partners.get(i).get("color_name"));
					
					marker.getDrawable().setColorFilter(Color.parseColor(color_name),PorterDuff.Mode.MULTIPLY );
					
					String imagePath = partners.get(i).get("logo");
					Log.e("imagePath",""+imagePath);
					if(URLUtil.isValidUrl(imagePath)){
						imagePath =imagePath;
					}
					
					else {
						imagePath = "http://"+imagePath;
					}
					imageLoader.DisplayImage(imagePath, popup_image);
					Marker partner_marker = 
							mGoogleMap.addMarker(new MarkerOptions()
							.position(localLatLng4)
							.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_view)))

							.snippet(partners.get(i).get("name")));
					partner_marker.showInfoWindow();

					markers.put(partner_marker.getId(), imagePath);

				//	mGoogleMap.setInfoWindowAdapter(new InfoWindowAdapterMarker(getApplicationContext()));

					}
				}

			}

		catch (Exception ae) {
			Log.e("Exception==", "" + ae);
		}
	}
	
	

	// Convert a view to bitmap
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

	/**
	 * 
	 * @author Desktop
	 * web service to get distance
	 */
	private class getDistance extends AsyncTask<Void, Void, String> {
		@Override
		protected void onPreExecute() {
			db = new TransparentProgressDialog(Taxi_map.this,
					R.drawable.loading);
			db.show();
		}

		protected String doInBackground(Void... placesURL) {

			HttpClient httpclient = new DefaultHttpClient();
			
			String url =  "https://maps.googleapis.com/maps/api/distancematrix/json?";
			
	

			String u = url + "origins=" + URLEncoder.encode(Info_for_a_trip.depart)+"&"+"destinations="+
					URLEncoder.encode(Info_for_a_trip.destination)+"&"+"key="+API;
			
			Log.d("u***************************==",""+u);

			HttpGet httpget = new HttpGet(u);

			String result = null;
			try {

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httpget);

				StatusLine statusLine = response.getStatusLine();

				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					out.close();

					result = out.toString();

				} else {
					// close connection
					response.getEntity().getContent().close();
					throw new IOException(statusLine.getReasonPhrase());
				}

			} catch (Exception e) {
				Log.e("Error:", e.getMessage());
				
			}
			
			return result;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			db.dismiss();
			Log.e("Distance(web service)", "" + result);
			try{                           
                
                JSONObject d_Obj = new JSONObject(result.toString());
                if(d_Obj.getString("status").equals("OK")){
                    JSONArray row=new JSONArray(d_Obj.getString("rows"));
                    JSONObject dist_Obj1 = row.getJSONObject(0);
                    
                    JSONArray elements=new JSONArray(dist_Obj1.getString("elements"));
                    JSONObject dist_Obj2 = elements.getJSONObject(0);
                    
                    String distance = dist_Obj2.getString("distance");
                    JSONObject dist_res = new JSONObject(distance); 
                    String dis = dist_res.getString("text").toString();
                    
                    dis = dis.replace("km","");
                    dis = dis.replace(" ","");
                    distance_to_destination =Double.parseDouble(dis);
                  Log.e("resval===",""+distance_to_destination); 
                  if(Info_for_a_trip.destination.equals("") || Info_for_a_trip.destination== null){
                	  showAlertToUser("Please select destination");
                  }
                  
                  else {
                	  new get_city_price().execute(new Void[0]);
                  }
               }
			}catch(Exception e){
                 e.printStackTrace();
                 }
		}
	}
	
	}
