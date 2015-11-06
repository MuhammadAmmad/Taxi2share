package taxi2share.eu;

import taxi2share.eu.biz.Data;
import taxi2share.eu.biz.GPSTracker;
import taxi2share.eu.biz.Info_for_a_trip;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import taxi2share.eu.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class Home extends Activity {

	LinearLayout back, adds;
	LinearLayout back_di;
	GPSTracker gps;
	Button find_taxi,fidelity_card, t2s_res;
	double latitude;
	double longitude;
	private static final String AD_UNIT_ID ="ca-app-pub-6108373414800595/3258575664";
	private AdView adView;
	Button find_taxi_for_airport, partners;
	SharedPreferences sp;
	public void back_home(View paramView) {
		moveTaskToBack(true);
	}

	/**
	 * 
	 * @param paramView
	 *            method to logout from the screen
	 */
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
								Intent localIntent1 = new Intent(Home.this,
										Login.class);
								Home.this.startActivity(localIntent1);
								Home.this.finish();
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

	/**
	 * 
	 * @param paramView
	 *            method to find taxi location on google map
	 */
	public void find_taxi(View paramView) {
		this.gps = new GPSTracker(this);
		if (this.gps.canGetLocation()) {
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
			Data.vrai_lat = latitude;
			Data.vrai_lon = longitude;
			Info_for_a_trip.lat_dep = latitude;
			Info_for_a_trip.lon_dep = longitude;
			Data.latitude = latitude;
			Data.longitude = longitude;

		}

		Intent i = new Intent(Home.this, Taxi_map.class);
		startActivity(i);

	}

	public void onBackPressed() {
		moveTaskToBack(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home);

		find_taxi_for_airport = (Button) findViewById(R.id.find_taxi_for_airport);
		find_taxi = (Button) findViewById(R.id.find_taxi);
		fidelity_card = (Button) findViewById(R.id.fidelity_card);
		t2s_res = (Button) findViewById(R.id.t2s_res);
		partners = (Button) findViewById(R.id.partners);
		
		sp = PreferenceManager
				.getDefaultSharedPreferences(Home.this);

		
		partners.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Home.this,Partners.class);
				startActivity(i);
				
			}
		});
		
		fidelity_card.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Home.this,Fidelity.class);
				startActivity(i);
				
			}
		});
		
		t2s_res.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Home.this,City_reservation.class);
				//Intent i = new Intent(Home.this,City_to_city_reservation.class);
				startActivity(i);
				
			}
		});

		find_taxi_for_airport.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//String url = "http://www.taxi2share.eu/booking";
				Log.e("token==",""+sp.getString("token", ""));
				String url = "http://www.taxi2share.eu/booking/index.php?token="+sp.getString("token", "");

				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);

			}
		});

		
		  adds = (LinearLayout) findViewById(R.id.adds);
		  
		  AdView adView = new AdView(this);
		  adView.setAdSize(AdSize.SMART_BANNER);
		  adView.setAdUnitId(AD_UNIT_ID); 
		  adds.addView(adView);
		  
		  
		  AdRequest adRequest = new AdRequest.Builder()
		  .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		  .addTestDevice("D1A0260DC4E588FF3DCF5300F8B10795").build();
		  
		  // Start loading the ad in the background. adView.loadAd(adRequest);
		  adView.loadAd(adRequest);
	}
	/**
	 * 
	 * @param paramView
	 * navigate to profile screen
	 */
	public void profil_go(View paramView) {
		Intent i = new Intent(Home.this, Profile.class);
		startActivity(i);

	}

}
