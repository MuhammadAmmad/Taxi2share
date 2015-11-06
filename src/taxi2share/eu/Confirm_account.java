package taxi2share.eu;




import taxi2share.eu.biz.Data;
import taxi2share.eu.biz.Function;

import taxi2share.eu.R;
import com.macrew.Utils.TransparentProgressDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Confirm_account extends Activity{
	
	EditText code;
	  TransparentProgressDialog db;
	  ProgressDialog dialog;
	  Button envoi;
	  
	  /**
	   * 
	   * @param paramString (message)
	   * shows alert message to the useer
	   */
	  
	  private void showGPSDisabledAlertToUser(String paramString)
	  {
	    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
	    localBuilder.setMessage(paramString).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
	    {
	      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
	      {
	        paramAnonymousDialogInterface.cancel();
	      }
	    });
	    localBuilder.create().show();
	  }
	  
	  /**
	   * 
	   * @param paramView
	   * method to apply checks on code
	   */
	  public void confirm(View paramView)
	  {
	    if (this.code.getText().toString().length() != 4)
	    {
	      showGPSDisabledAlertToUser("Code with four characters!!");
	      return;
	    }
	    if (this.code.getText().toString().equalsIgnoreCase(Data.code))
	    {
	      new confirm_it().execute(new Void[0]);
	      return;
	    }
	    showGPSDisabledAlertToUser("Please enter the code from your email!!");
	  }
	  
	  
	  @Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.confirm_compte);
			
			code = ((EditText)findViewById(R.id.code));
			
	  }
	  
	  /**
	   * 
	   * @author Desktop
	   * async class to activate user profile
	   */
	  public class confirm_it
	    extends AsyncTask<Void, Void, Void>
	  {
	    Function function = new Function();
	    String result;
	    
	    public confirm_it() {}
	    
	    protected Void doInBackground(Void... paramVarArgs)
	    {
	      this.result = function.activate_profile(Data.id_client_inscri);
	      return null;
	    }
	    
	    protected void onPostExecute(Void paramVoid)
	    {
	      Confirm_account.this.dialog.dismiss();
	      if (this.result.equalsIgnoreCase("ERROR"))
	      {
	        Confirm_account.this.showGPSDisabledAlertToUser("Error, Please try again !!");
	        return;
	      }
	      if (this.result.equalsIgnoreCase("fail_connection"))
	      {
	        Confirm_account.this.showGPSDisabledAlertToUser("A problem has been occurred while sending your account ! Please try again");
	        return;
	      }
	      AlertDialog.Builder localBuilder = new AlertDialog.Builder(Confirm_account.this);
	      localBuilder.setMessage("Welcome to taxi2share apps, please connect to use our service. ").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener()
	      {
	        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
	        {
	          Confirm_account.this.finish();
	          Intent localIntent = new Intent(Confirm_account.this, Login.class);
	          Confirm_account.this.startActivity(localIntent);
	          paramAnonymousDialogInterface.cancel();
	        }
	      });
	      localBuilder.create().show();
	    }
	    
	    protected void onPreExecute()
	    {
	      super.onPreExecute();
	      Confirm_account.this.dialog = new ProgressDialog(Confirm_account.this);
	      Confirm_account.this.dialog.setMessage("Verify your code");
	      Confirm_account.this.dialog.setIndeterminate(true);
	      Confirm_account.this.dialog.setCancelable(false);
	      Confirm_account.this.dialog.show();
	    }
	  }
}
