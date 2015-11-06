package taxi2share.eu;

import java.util.HashMap;

import taxi2share.eu.Sui_taxi.get_information_to_show;
import taxi2share.eu.Sui_taxi.get_prix_final;
import taxi2share.eu.biz.Data;
import taxi2share.eu.biz.Data_driver;
import taxi2share.eu.biz.Function;
import taxi2share.eu.biz.Info_for_a_trip;

import taxi2share.eu.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

public class Sui_client extends FragmentActivity{
	SupportMapFragment fm;
	Function function = new Function();
	GoogleMap mGoogleMap;
	MarkerOptions option_client;
	boolean run = true;
	HashMap<String, String> taxi = new HashMap();

	public void home(View paramView) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setTitle("Exit !!");
		localBuilder.setMessage("You can not track client any more !");
		localBuilder.setPositiveButton("Exit",
				new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						finish();
						Intent localIntent = new Intent(Sui_client.this,
								Home.class);
					startActivity(localIntent);
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
		this.fm = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map));
		this.fm.getMap();
		this.mGoogleMap = this.fm.getMap();
		final Handler localHandler = new Handler();
		/*localHandler.postDelayed(new Runnable() {
			public void run() {
				if (Sui_client.this.run) {
					new get_information_to_show().execute(new Void[0]);
					new get_prix_final().execute(new Void[0]);
				}
				localHandler.postDelayed(this, 5000L);
			}
		}, 1000L);*/
		
		
		try {
			Sui_client.this.mGoogleMap.clear();
			Sui_client.this.option_client = new MarkerOptions();
			LatLng localLatLng = new LatLng(Data_driver.latitude_dep_clien,Data_driver.longitude_dep_clien);
			option_client.position(localLatLng);
			option_client.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.localisation_taxi));
			mGoogleMap.addMarker(Sui_client.this.option_client);
			option_client.title("client");
			mGoogleMap.moveCamera(CameraUpdateFactory
					.newLatLng(localLatLng));
			mGoogleMap.animateCamera(CameraUpdateFactory
					.zoomTo(16.0F));
			return;
		} catch (Exception localException) {
		}
	}

//	public class get_information_to_show extends AsyncTask<Void, Void, Void> {
//		Function function = new Function();
//		String num_taxi;
//
//		public get_information_to_show() {
//		}
//
//		protected Void doInBackground(Void... paramVarArgs) {
//			try {
//				taxi.clear();
//				taxi = this.function.get_info_reserve(Info_for_a_trip.num_taxi);
//				// label27:
//				// return null;
//			} catch (Exception localException) {
//				// break label27;
//			}
//
//			return null;
//		}
//
//		protected void onPostExecute(Void paramVoid) {
//			try {
//				Sui_taxi.this.mGoogleMap.clear();
//				Sui_taxi.this.option_taxi = new MarkerOptions();
//				LatLng localLatLng = new LatLng(
//						Double.parseDouble((String) Sui_taxi.this.taxi
//								.get("geo_latitude")),
//						Double.parseDouble((String) Sui_taxi.this.taxi
//								.get("geo_longitude")));
//				Sui_taxi.this.option_taxi.position(localLatLng);
//				Sui_taxi.this.option_taxi.icon(BitmapDescriptorFactory
//						.fromResource(2130837628));
//				Sui_taxi.this.mGoogleMap.addMarker(Sui_taxi.this.option_taxi);
//				Sui_taxi.this.option_taxi.title("client");
//				Sui_taxi.this.mGoogleMap.moveCamera(CameraUpdateFactory
//						.newLatLng(localLatLng));
//				Sui_taxi.this.mGoogleMap.animateCamera(CameraUpdateFactory
//						.zoomTo(16.0F));
//				return;
//			} catch (Exception localException) {
//			}
//		}
//
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//	}
//
//	public class get_prix_final extends AsyncTask<Void, Void, Void> {
//		Function function = new Function();
//		String prix = "";
//
//		public get_prix_final() {
//		}
//
//		protected Void doInBackground(Void... paramVarArgs) {
//			try {
//				this.prix = this.function.prix_course(
//						(String) Data.client.get("id_client"),
//						Info_for_a_trip.num_taxi);
//				// label25:
//				// return null;
//			} catch (Exception localException) {
//				// break label25;
//			}
//			return null;
//		}
//
//		protected void onPostExecute(Void paramVoid) {
//			if (!this.prix.equalsIgnoreCase("null")) {
//				if (this.prix.equalsIgnoreCase("fail_connection")) {
//					Toast.makeText(Sui_taxi.this.getApplicationContext(),
//							"fail_connection", 1).show();
//				}
//			} else {
//				return;
//			}
//			Sui_taxi.this.run = false;
//			AlertDialog.Builder localBuilder = new AlertDialog.Builder(
//					Sui_taxi.this);
//			localBuilder
//					.setTitle("You arrived at your destination PLease pay the following amount to the driver : "
//							+ this.prix + " €");
//			localBuilder.setMessage("Hope to see you soon on TaxiShare");
//			localBuilder.setPositiveButton("Exit",
//					new DialogInterface.OnClickListener() {
//						public void onClick(
//								DialogInterface paramAnonymousDialogInterface,
//								int paramAnonymousInt) {
//							Intent localIntent = new Intent(Sui_taxi.this,
//									Home.class);
//							Sui_taxi.this.startActivity(localIntent);
//						}
//					});
//		}
//
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//	}
}
