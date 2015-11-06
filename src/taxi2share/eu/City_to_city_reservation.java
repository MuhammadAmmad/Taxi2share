package taxi2share.eu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class City_to_city_reservation extends Activity{
	
	Button make_resrvation , calendar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resrvation_choice);
		
		make_resrvation = (Button) findViewById(R.id.make_reservation);
		calendar = (Button) findViewById(R.id.calendar);
		
		calendar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Intent i = new Intent(City_to_city_reservation.this, Calendar_Activity.class);
			startActivity(i);
				
			}
		});
		
	}

}
