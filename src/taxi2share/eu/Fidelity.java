package taxi2share.eu;

import java.util.HashMap;

import taxi2share.eu.Sui_taxi.get_information_to_show;
import taxi2share.eu.biz.Data;
import taxi2share.eu.biz.Data_driver;
import taxi2share.eu.biz.Function;
import taxi2share.eu.biz.Info_for_a_trip;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.macrew.Utils.TransparentProgressDialog;
import com.macrew.alertUtils.AlertsUtils;
import com.macrew.alertUtils.NetConnection;

public class Fidelity extends Activity{
	
	TextView total_journey, loyalty_card, promo;
	EditText promo_code;
	Button confirm;
	TransparentProgressDialog db;
	Function function;
	FidelityTask FidelityObj;
	Boolean isConnected, run= true;
	String total_order_amount,total_fidelity_amount,total_orders,want_to_take_fidelity , loyality , loyality_value_for_next_order;
	HashMap fidelity_detail ;
	Boolean webServiceResult = false;
	LinearLayout adds;
	private static final String AD_UNIT_ID ="ca-app-pub-6108373414800595/3258575664";
	private AdView adView;
	
	
	/***
	 * 
	 * @param paramView
	 * on back button click
	 */
	public void back_home(View paramView) {
		onBackPressed();
	}
	
	private void showAlertToUser(String paramString) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setMessage(paramString).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						paramAnonymousDialogInterface.cancel();
						if(webServiceResult){
						Intent i = new Intent(Fidelity.this, Home.class);
						startActivity(i);
						}
						
					}
				});
		localBuilder.create().show();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fidelity);
		
		fidelity_detail = new HashMap();
		
		function = new Function();
		
		total_journey = (TextView) findViewById(R.id.total_journey);
		loyalty_card = (TextView) findViewById(R.id.loyalty_card);
		promo_code = (EditText) findViewById(R.id.promo_code);
		promo = (TextView) findViewById(R.id.promo);
		confirm = (Button) findViewById(R.id.confirm);
		
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
		
			
				FidelityObj = new FidelityTask();

				isConnected = NetConnection.checkInternetConnectionn(Fidelity.this);
				if (isConnected == true) {
					FidelityObj.execute();
				}

				else {
					new AlertsUtils(Fidelity.this, AlertsUtils.TRY_AGAIN);
				}

					
			
		
		confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String promoCode = promo_code.getText().toString();
				
				if(promoCode.equals("") || promoCode==null){
					showAlertToUser("Please enter promo code.");
				}
				
				else {
					new reedem_loyality(Data.client.get("id_client") ,promoCode ).execute(new Void[0]);
				}
				
				
			}
		});
		
		
	}
	
	public class FidelityTask extends AsyncTask<Void, Void, Void> {
		
		
		HashMap<String, String> result;
		
		

		protected void onPreExecute() {
			db = new TransparentProgressDialog(Fidelity.this, R.drawable.loading);
			db.show();
		}
		
		protected Void doInBackground(Void... paramVarArgs) {
			
			this.result = function.get_fidelity(Data.client.get("id_client"));
			Log.e("result==",""+result);
			
			fidelity_detail = result;
			return null;
		}
		protected void onPostExecute(Void result) {
			db.dismiss();
			
			if(fidelity_detail.get("result").toString().equalsIgnoreCase("OK")){
			
			total_order_amount=(String) fidelity_detail.get("total_order_amount");
			total_fidelity_amount=(String) fidelity_detail.get("total_fidelity_amount");
			total_orders=(String) fidelity_detail.get("total_orders");
			want_to_take_fidelity=(String) fidelity_detail.get("want_to_take_fidelity");
			loyality = (String) fidelity_detail.get("loyality");
			loyality_value_for_next_order = (String) fidelity_detail.get("loyality_value_for_next_order");
			promo_code.setText((String) fidelity_detail.get("codename"));
			Log.i("total_order_amountDbl","===total_order_amountDbl"+total_order_amount);
			double total_order_amountDbl =(double) Math.round((Double.parseDouble(total_order_amount)) * 100) / 100;
			double loyalityDbl = (double)Math.round((Double.parseDouble(loyality)) * 100) / 100;
			double loyality_for_nxt_ordr = (double)Math.round((Double.parseDouble(loyality_value_for_next_order)) * 100) / 100;
			double temp = loyality_for_nxt_ordr+loyalityDbl;
			 
			if(temp ==0){
				loyalty_card.setText("Loyalty Card : "+temp+" €");
			}
			
			else {
				if(loyality_for_nxt_ordr> 0 && loyalityDbl >0){
			loyalty_card.setText("Loyalty Card : "+loyality_for_nxt_ordr+"+"+loyalityDbl+" ="+temp+" €");
				}
				
				else if(loyality_for_nxt_ordr> 0 && loyalityDbl ==0){
					loyalty_card.setText("Loyalty Card : "+loyality_for_nxt_ordr+" €");
				}
				
				else if(loyality_for_nxt_ordr==0 && loyalityDbl >0){
					loyalty_card.setText("Loyalty Card : "+loyalityDbl+" €");
				}
			}
			total_journey.setText("Total Journey : "+" "+total_order_amountDbl+" €");
			promo.setText("Insert Promo Code");
			}
			
			else {
				webServiceResult = true;
				showAlertToUser("Something Went Wrong.Please Try Again");
			}
			
	}
		
	}
	
	public class reedem_loyality extends AsyncTask<Void, Void, Void> {
		String result;
		String id;
		String code;

		public reedem_loyality(String paramString1 ,String paramString2 ) {
			this.id = paramString1;
			this.code = paramString2;
		}

		protected Void doInBackground(Void... paramVarArgs) {
			this.result = function.reedem_loyality(this.id , this.code);
			Log.e("***RESULT***",""+this.result);
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			db.dismiss();
			try{
			if (result.equals("")) {
				webServiceResult = true;
				showAlertToUser("Your request has been processed successfully.");
			
			}
			
			else if (result.equalsIgnoreCase("fail_connection")){
				showAlertToUser("Something went wrong while processing the request. Please try again.");
			}
			
			else {
				showAlertToUser(result);
			}
			}
			catch(Exception ae){
				Log.e("EXCEPTION==",""+ae);
				showAlertToUser("Something went wrong while processing the request. Please try again.");
			}
		}

		protected void onPreExecute() {
			db = new TransparentProgressDialog(Fidelity.this,
					R.drawable.loading);
			db.show();
		}
	}

}
