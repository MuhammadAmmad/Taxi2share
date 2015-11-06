package taxi2share.eu;

import java.util.HashMap;
import java.util.Random;

import taxi2share.eu.biz.Function;



import taxi2share.eu.R;
import com.macrew.Utils.TransparentProgressDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Forget_password extends Activity {

	String code = "";
	EditText email;
	Function function;
	String ip = "http://belganetservices.com/tunet/chawki/webService_post/";
	Button send;
	String txt_email = "";
	
	/**
	 * 
	 * @return string 
	 * method to generate random code of 4 charachters
	 */

	public String get_random_code() {
		String str = "";
		Random localRandom = new Random();
		for (int i = 0;; i++) {
			if (i >= 4) {
				return str;
			}
			str = str
					+ "1234567890189012234567345556766789012347890"
							.charAt(localRandom
									.nextInt("1234567890189012234567345556766789012347890"
											.length()));
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);

		this.function = new Function();
		email = ((EditText) findViewById(R.id.email_forget));
		send = ((Button) findViewById(R.id.send));
		
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Log.e("text", Forget_password.this.email.getText().toString());
			        if (Forget_password.this.function.verif(Forget_password.this.email.getText().toString()))
			        {
			          code = get_random_code();
			          txt_email = email.getText().toString();
			          new send_mail().execute(new Void[0]);
			          Log.e("code=== ", code);
			          Log.d("code=== ", code);
			          Intent localIntent = new Intent(Forget_password.this, Validate_code.class);
			          localIntent.putExtra("code", code);
			          localIntent.putExtra("email", txt_email);
			          startActivity(localIntent);
			          finish();
			          return;
			        }
			        AlertDialog.Builder localBuilder = new AlertDialog.Builder(Forget_password.this);
			        localBuilder.setTitle("wrong .. !");
			        localBuilder.setMessage("Invalid Email.").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener()
			        {
			          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
			          {
			            paramAnonymous2DialogInterface.cancel();
			          }
			        });
			        localBuilder.create().show();
				
			}
		});

	}
	/**
	 * 
	 * @author Desktop
	 * async class
	 * sends code to user mail id
	 *
	 */
	public class send_mail
    extends AsyncTask<Void, Void, Void>
  {
    HashMap<String, String> client;
   TransparentProgressDialog db;
    
    public send_mail() {}
    
    protected Void doInBackground(Void... paramVarArgs)
    {
      this.client = new HashMap();
      this.client = Forget_password.this.function.send_mail(Forget_password.this.txt_email, Forget_password.this.code);
      return null;
    }
    
    protected void onPostExecute(Void paramVoid) {}
    
    protected void onPreExecute() {}
  }
}
