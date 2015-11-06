package taxi2share.eu;

import taxi2share.eu.Home_driver_new.fin_de_service;
import taxi2share.eu.Home_driver_new.update_libere;
import taxi2share.eu.biz.Data_driver;
import taxi2share.eu.biz.Function_driver;

import taxi2share.eu.R;
import com.google.android.gms.internal.db;
import com.macrew.Utils.TransparentProgressDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Price_calculation extends Activity {

	TextView nbr_km, price, driving_time, distance, price1, driving_time1,
			name, f_name, phone_nmber, price_to_pay, depar_client,
			destin_client;
	int arg, nCounter;
	float nCounter1;
	TransparentProgressDialog db;

	Function_driver function = new Function_driver();
	
//	public void fin_de_service(View paramView) {
//		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
//		localBuilder
//				.setMessage("Are you sure you want to logout.!")
//				.setCancelable(false)
//				.setPositiveButton("Yes",
//						new DialogInterface.OnClickListener() {
//							public void onClick(
//									DialogInterface paramAnonymousDialogInterface,
//									int paramAnonymousInt) {
//								new fin_de_service().execute(new Void[0]);
//								paramAnonymousDialogInterface.cancel();
//							}
//						});
//
//		localBuilder.setNegativeButton("No",
//				new DialogInterface.OnClickListener() {
//					public void onClick(
//							DialogInterface paramAnonymousDialogInterface,
//							int paramAnonymousInt) {
//						paramAnonymousDialogInterface.cancel();
//					}
//				});
//		localBuilder.create().show();
//	}
	
	public void retour(View paramView) {
		moveTaskToBack(true);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.price_calculation);

		Intent intent = getIntent();
		arg = intent.getIntExtra("arg", 0);
		nCounter = intent.getIntExtra("nCounter", 0);

		Log.e("nCounter===", "" + nCounter);

		nCounter1 = (nCounter / 60) * 50;

		nbr_km = (TextView) findViewById(R.id.nbr_km);
		price = (TextView) findViewById(R.id.price);
		driving_time = (TextView) findViewById(R.id.driving_time);
		distance = (TextView) findViewById(R.id.distance);
		price1 = (TextView) findViewById(R.id.price1);
		driving_time1 = (TextView) findViewById(R.id.driving_time1);
		depar_client = (TextView) findViewById(R.id.depar_client);
		destin_client = (TextView) findViewById(R.id.destin_client);

		name = (TextView) findViewById(R.id.name);
		f_name = (TextView) findViewById(R.id.f_name);
		phone_nmber = (TextView) findViewById(R.id.phone_number);
		price_to_pay = (TextView) findViewById(R.id.price_to_pay);
//
//		depar_client.setText(Data_driver.client_push1.get(arg).get("pick_up"));
//		destin_client.setText(Data_driver.client_push1.get(arg).get(
//				"detination"));
//
//		float f = get_distance(
//				Double.parseDouble(Data_driver.client_push1.get(arg).get(
//						"latitude_client_dep")),
//				Double.parseDouble(Data_driver.client_push1.get(arg).get(
//						"longitude_client_dep")),
//				Double.parseDouble(Data_driver.client_push1.get(arg).get(
//						"latitude_client_des")),
//				Double.parseDouble(Data_driver.client_push1.get(arg).get(
//						"longitude_client_des")));
//
//		double roundOff_dist = (double) Math.round(f * 100) / 100;
//		nbr_km.setText(roundOff_dist + " Km");
//		distance.setText(roundOff_dist + " Km");
//		double roundOff = (double) Math.round((1.8D * f) * 100) / 100;
//
//		price.setText("" + roundOff + "€");
//		price1.setText("" + roundOff + "€");
//
//		name.setText(Data_driver.client_push1.get(arg).get("name_C"));
//		f_name.setText(Data_driver.client_push1.get(arg).get("last_name_C"));
//		phone_nmber.setText(Data_driver.client_push1.get(arg).get("tel_C"));
//
//		int secs = nCounter;
//		int mins = secs / 60;
//		int hr = mins / 60;
//		secs = secs % 60;
//
//		driving_time1.setText("" + hr + ":" + String.format("%02d", mins) + ":"
//				+ String.format("%02d", secs));
//
//		float p = (float) (roundOff + 2.40 + nCounter1);
//
//		price_to_pay.setText("" + p + " €");
//
////		new envoie_prix(String.valueOf(p),Data_driver.client_push1.get(arg).get("id_client"),
////				Data_driver.client_push1.get(arg).get("num_taxi"),Data_driver.client_push1.get(arg).get("id_reservation")
////				).execute(new Void[0]);
//
//	}
//
//	public float get_distance(double paramDouble1, double paramDouble2,
//			double paramDouble3, double paramDouble4) {
//		float[] arrayOfFloat = new float[1];
//		Location.distanceBetween(paramDouble1, paramDouble2, paramDouble3,
//				paramDouble4, arrayOfFloat);
//		float f = arrayOfFloat[0] / 1000.0F;
//		String str = "" + f;
//		if (str.length() > 6) {
//			f = Float.parseFloat(str.substring(0, 4));
//		}
//		return f;

	}

//	public class envoie_prix extends AsyncTask<Void, Void, Void> {
//		String id_client;
//		String num_taxi;
//		String prix;
//		String id_reservation;
//		String result;
//
//		public envoie_prix(String paramString1, String paramString2,
//				String paramString3, String paramString4) {
//			this.prix = paramString1;
//			this.id_client = paramString2;
//			this.num_taxi = paramString3;
//			this.id_reservation = paramString4;
//		}
//
//		protected Void doInBackground(Void... paramVarArgs) {
//
//			this.result = function.envoyer_prix(this.id_client, this.num_taxi,
//					this.prix, this.id_reservation);
//			Log.i("result=",""+result);
//
//			return null;
//		}
//
//		protected void onPostExecute(Void paramVoid) {
//			db.dismiss();
//			this.result.equalsIgnoreCase("OK");
//			
//		}
//
//		protected void onPreExecute() {
//			super.onPreExecute();
//			db = new TransparentProgressDialog(Price_calculation.this, R.drawable.loading);
//			db.show();
//		}
//	}

}
