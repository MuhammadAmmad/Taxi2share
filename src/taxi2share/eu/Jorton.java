package taxi2share.eu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;

import taxi2share.eu.biz.Data_driver;

import taxi2share.eu.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Jorton extends FragmentActivity {
	TextView cash;
	TextView dep;
	TextView des;
	DecimalFormat df = new DecimalFormat();
	SupportMapFragment fm;
	GoogleMap mGoogleMap;
	TextView nbr_baga;
	TextView nbr_per;
	MarkerOptions option_taxi;

	public Void back_home(View paramView) {
		onBackPressed();
		return null;
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.notification);
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		fm = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map));
		fm.getMap();
		dep = ((TextView) findViewById(R.id.dep));
		des = ((TextView) findViewById(R.id.des));
		nbr_per = ((TextView) findViewById(R.id.nbr_per));
		nbr_baga = ((TextView) findViewById(R.id.nbr_baga));
		cash = ((TextView) findViewById(R.id.cash));
		dep.setText((CharSequence) Data_driver.clientxx.get("pick_up"));
		des.setText((CharSequence) Data_driver.clientxx.get("detination"));
		nbr_per.setText((CharSequence) Data_driver.clientxx
				.get("nbr_person"));
		nbr_baga.setText((CharSequence) Data_driver.clientxx
				.get("nbr_lagge"));
		cash.setText(df.format(Double
				.parseDouble((String) Data_driver.clientxx.get("Prix"))));
		mGoogleMap = fm.getMap();
		option_taxi = new MarkerOptions();
		LatLng localLatLng = new LatLng(
				Double.parseDouble((String) Data_driver.clientxx
						.get("latitude_client_dep")),
				Double.parseDouble((String) Data_driver.clientxx
						.get("longitude_client_dep")));
		option_taxi.position(localLatLng);
		option_taxi.icon(BitmapDescriptorFactory.fromResource(R.drawable.localisation_user));
		option_taxi.title("client");
		mGoogleMap.addMarker(this.option_taxi);
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(localLatLng));
		mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(16.0F));
	}
}
