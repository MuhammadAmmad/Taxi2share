package taxi2share.eu.biz;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Function_driver {
	JSONParser json = new JSONParser();
//	String uri = "http://www.taxi2share.eu/apps/android/dev/";
	String uri = "http://www.taxi2share.eu/apps/android/webService_post/";
	/****** KEYS *********/
	String aloneDistance = "aloneDistance", aloneTime = "aloneTime";
	String timeSharing12 = "timeSharing12",
			distanceSharing12 = "distanceSharing12";
	String timeSharing13 = "timeSharing13",
			distanceSharing13 = "distanceSharing13";
	String timeSharing23 = "timeSharing23",
			distanceSharing23 = "distanceSharing23";
	String timeSharing123 = "timeSharing123",
			distanceSharing123 = "distanceSharing123";
	
	String timeSharing12_g20 = "timeSharing12_g20" , timeSharing12_l20="timeSharing12_l20";
	String timeSharing13_g20 = "timeSharing13_g20" ,  timeSharing13_l20="timeSharing13_l20";
	String timeSharing123_g20 = "timeSharing123_g20" , timeSharing123_l20="timeSharing123_l20";
	
	String timeSharing23_g20 = "timeSharing23_g20" , timeSharing23_l20 = "timeSharing23_l20" ;
	String aloneTime_g20 = "aloneTime_g20" , aloneTime_l20 = "aloneTime_l20";
	// String uri = "http://dev.macrew.net/taxi2share/";

	public static ArrayList<HashMap<String, String>> ClientDetails = new ArrayList<HashMap<String, String>>();

	public HashMap<String, String> connect(String paramString1,
			String paramString2, double latitude_chauf, double longitude_chauf)

	{
		JSONParser localJSONParser = new JSONParser();
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("num_taxi", paramString1));
		localArrayList.add(new BasicNameValuePair("abr", paramString2));
		localArrayList.add(new BasicNameValuePair("geo_latitude", ""
				+ latitude_chauf));
		localArrayList.add(new BasicNameValuePair("geo_longitude", ""
				+ longitude_chauf));
		HashMap localHashMap = new HashMap();
		try {
			JSONObject localJSONObject = new JSONArray(Html.fromHtml(
					localJSONParser.makeHttpRequest(this.uri
							+ "connect_taxi.php", "POST", localArrayList))
					.toString()).getJSONObject(0);
			if (localJSONObject.getString("result").toString()
					.equalsIgnoreCase("OK")) {
				localHashMap.put("result", "OK");
				localHashMap.put("num_taxi",
						localJSONObject.getString("num_taxi"));
				localHashMap.put("id_chauffeur",
						localJSONObject.getString("id_chauffeur"));
				localHashMap.put("name", localJSONObject.getString("name"));
				localHashMap.put("ratings", localJSONObject.getString("ratings"));
				return localHashMap;
			}
			localHashMap.put("result", "ERROR");
			return localHashMap;
		} catch (JSONException localJSONException) {
			localHashMap.put("result", "no_connection");
		}

		catch (Exception e) {
			localHashMap.put("result", "no_connection");
		}
		return localHashMap;
	}
	
	
		public HashMap<String, String> startTimer(ArrayList localArrayList) {
		
		
		HashMap localHashMap = new HashMap();
		try {
	
		 JSONObject localJSONObject = new JSONArray(Html.fromHtml(this.json.makeHttpRequest(this.uri + "startTimer.php", "POST", localArrayList)).toString()).getJSONObject(0);
	      if (localJSONObject.getString("result").toString().equalsIgnoreCase("OK"))
	      {
	        localHashMap.put("result", "OK");
	        
	        return localHashMap;
	      }
			localHashMap.put("result", "ERROR");
			return localHashMap;
		} catch (JSONException localJSONException) {
			Log.e("localJSONException==",""+localJSONException);
			localHashMap.put("result", "no_connection");
		}
		
		catch(Exception ae){
			Log.e("ae==",""+ae);
			localHashMap.put("result", "no_connection");
		}
		
		return localHashMap;
		
		
	}

	public String delete(String paramString) {
		ArrayList localArrayList = new ArrayList();
		localArrayList
				.add(new BasicNameValuePair("id_reservation", paramString));
		try {
			new JSONArray(Html.fromHtml(
					this.json.makeHttpRequest(this.uri
							+ "delete_client_reserv.php", "POST",
							localArrayList)).toString()).getJSONObject(0)
					.getString("result");
			return "ok";
		} catch (JSONException localJSONException) {
		} catch (Exception localJSONException) {
		}
		return "fail_connection";
	}

	public HashMap envoyer_prix(String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5,String paramString6,String paramString7) {
		
		JSONParser localJSONParser = new JSONParser();
		
		
		
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("id_client", paramString1));
		localArrayList.add(new BasicNameValuePair("num_taxi", paramString2));
		localArrayList.add(new BasicNameValuePair("prix", paramString3));
		localArrayList.add(new BasicNameValuePair("id_reservation",
				paramString4));
		localArrayList.add(new BasicNameValuePair("total_time", paramString5));
		localArrayList.add(new BasicNameValuePair("total_time_for_over_speed", paramString6));
		localArrayList.add(new BasicNameValuePair("total_time_for_limited_speed", paramString7));
		
		
		HashMap localHashMap = new HashMap();
		
		try {
			JSONObject jsonObj = new JSONArray(Html.fromHtml(
					localJSONParser.makeHttpRequest(this.uri + "paiment_notif.php",
							"POST", localArrayList)).toString()).getJSONObject(0);
			
			Log.i("jsonobj===",""+jsonObj);
			
			if(jsonObj.get("result").toString().equalsIgnoreCase("OK")){
				
				localHashMap.put("result", "OK");
				localHashMap.put("loyality", jsonObj.get("loyality"));
				Log.i("localHashMap*********",""+localHashMap);				
			}
			
			else{
				localHashMap.put("result", "ERROR");
			}
			
			
			
		} catch (Exception localJSONException) {
			Log.i("localHashMap*********",""+localJSONException);
			localHashMap.put("result", "fail_connection");
		}
		
		return localHashMap;
	}

	public String fin_de_service(String paramString) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("num_taxi", paramString));
		try {
			new JSONArray(Html.fromHtml(
					this.json.makeHttpRequest(
							this.uri + "fin_service_chef.php", "POST",
							localArrayList)).toString()).getJSONObject(0)
					.getString("result");
			return "ok";
		} catch (JSONException localJSONException) {
		} catch (Exception localJSONException) {
		}
		return "fail_connection";
	}

	public ArrayList<HashMap<String, String>> get_new_ride(String paramString) {
		JSONParser localJSONParser = new JSONParser();
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("num_taxi", paramString));
		HashMap localHashMap = new HashMap();
		try {
			JSONObject localJSONObject1 = new JSONArray(Html.fromHtml(
					localJSONParser.makeHttpRequest(this.uri
							+ "get_notify_reservation.php", "POST",
							localArrayList)).toString()).getJSONObject(0);

			// Log.e("localJSONObject1=",""+localJSONObject1);

			ClientDetails.clear();
			if (localJSONObject1.getString("result").toString()
					.equalsIgnoreCase("OK")) {
				JSONArray jsonArray = localJSONObject1.getJSONArray("data");

				for (int i = 0; i < jsonArray.length(); i++) {
					// JSONObject localJSONObject2 =
					// localJSONObject1.getJSONArray("data").getJSONObject(0);
					HashMap localHashMap2 = new HashMap();
					JSONObject c = jsonArray.getJSONObject(i);
					localHashMap2.put("result", "OK");
					localHashMap2.put("id_reservation",
							c.getString("id_reservation"));
					localHashMap2.put("id_client", c.getString("id_client"));
					localHashMap2.put("latitude_client_dep",
							c.getString("latitude_client_dep"));
					localHashMap2.put("longitude_client_dep",
							c.getString("longitude_client_dep"));
					localHashMap2.put("latitude_client_des",
							c.getString("latitude_client_des"));
					localHashMap2.put("longitude_client_des",
							c.getString("longitude_client_des"));
					localHashMap2.put("pick_up", c.getString("pick_up"));
					localHashMap2.put("image", c.getString("image"));
					localHashMap2.put("detination", c.getString("detination"));
					localHashMap2.put("nbr_lagge", c.getString("nbr_lagge"));
					localHashMap2.put("nbr_person", c.getString("nbr_person"));
					localHashMap2.put("num_taxi", c.getString("num_taxi"));
					localHashMap2.put("Prix", c.getString("Prix"));
					localHashMap2.put("distance", c.getString("distance"));
					localHashMap2.put("statut", c.getString("statut"));
					localHashMap2.put("type_paiment",
							c.getString("type_paiment"));
					localHashMap2.put("name_C", c.getString("name_C"));
					localHashMap2
							.put("last_name_C", c.getString("last_name_C"));
					localHashMap2.put("tel_C", c.getString("tel_C"));
					localHashMap2.put("city", c.getString("city"));
					localHashMap2.put("time", c.getString("time"));
					localHashMap2.put("boarding_fee", c.getString("boarding_fee"));
					localHashMap2.put("price_per_minute", c.getString("price_per_minute"));
					localHashMap2.put("price_per_km", c.getString("price_per_km"));
					localHashMap2.put(timeSharing12, "0");
					localHashMap2.put(distanceSharing12, "0");
					localHashMap2.put(distanceSharing13, "0");
					localHashMap2.put(timeSharing13, "0");
					localHashMap2.put(distanceSharing123, "0");
					localHashMap2.put(timeSharing123, "0");
					localHashMap2.put(aloneTime, "0");
					localHashMap2.put(aloneDistance, "0");
					localHashMap2.put(timeSharing23, "0");
					localHashMap2.put(distanceSharing23, "0");
					
					localHashMap2.put(aloneTime_g20, "0");
					localHashMap2.put(aloneTime_l20, "0");
					localHashMap2.put(timeSharing12_g20, "0");
					localHashMap2.put(timeSharing12_l20, "0");
					localHashMap2.put(timeSharing13_g20, "0");
					localHashMap2.put(timeSharing13_l20, "0");
					localHashMap2.put(timeSharing123_g20, "0");
					localHashMap2.put(timeSharing123_l20, "0");
					localHashMap2.put(timeSharing23_g20, "0");
					localHashMap2.put(timeSharing23_l20, "0");
				
					
					
					ClientDetails.add((HashMap) localHashMap2);
				}
				
				return ClientDetails;
			}
			
		} catch (JSONException localJSONException) {
			localHashMap.put("result", "no_connection");
			ClientDetails.add((HashMap) localHashMap);
			return ClientDetails;
		}

		catch (Exception localJSONException) {
			localHashMap.put("result", "no_connection");
			ClientDetails.add((HashMap) localHashMap);
			Log.e("**************exception********", "" + localJSONException);
		}
		return ClientDetails;
	}

	// public String insert(String paramString1, String paramString2, String
	// paramString3, String paramString4, String paramString5, String
	// paramString6, String paramString7, String paramString8)

	public String insert(String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5,
			String paramString6) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("company", paramString1));
		localArrayList
				.add(new BasicNameValuePair("manager_name", paramString2));
		localArrayList.add(new BasicNameValuePair("number_taxi", paramString3));
		localArrayList.add(new BasicNameValuePair("tel", paramString4));
		localArrayList.add(new BasicNameValuePair("adresse", paramString5));
		localArrayList.add(new BasicNameValuePair("email", paramString6));
		// localArrayList.add(new BasicNameValuePair("number_account",
		// paramString7));
		// localArrayList.add(new BasicNameValuePair("nom_banque",
		// paramString8));
		try {
			String str = new JSONArray(Html.fromHtml(
					this.json.makeHttpRequest(
							this.uri + "register_company.php", "POST",
							localArrayList)).toString()).getJSONObject(0)
					.getString("id_taxi_company");
			return str;
		} catch (JSONException localJSONException) {
		} catch (Exception localJSONException) {
		}
		return "fail_connection";
	}

	/******************** to send mail to company ************************************/
	public String envoie_mail(String paramString) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("email", paramString));
		try {
			Log.e("test test", "DRIVER DRIVER");
			String localJSONObject = new JSONArray(Html.fromHtml(
					this.json.makeHttpRequest(this.uri + "email_comp.php",
							"POST", localArrayList)).toString()).getJSONObject(
					0).getString("status");

			Log.i("driver test str==", "==" + localJSONObject);
			return localJSONObject;
		} catch (JSONException localJSONException) {
		} catch (Exception localJSONException) {
		}
		return "Fail_connectionxx";
	}

	/****************************************************************************************/

	public String update_libre(String paramString) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("num_taxi", paramString));
		try {
			String str = new JSONArray(Html.fromHtml(
					this.json.makeHttpRequest(this.uri + "libere.php", "POST",
							localArrayList)).toString()).getJSONObject(0)
					.getString("result");
			return str;
		} catch (JSONException localJSONException) {
		} catch (Exception localJSONException) {
		}
		return "fail_connection";
	}

	public String update_location(String paramString1, double latitude_chauf,
			double longitude_chauf) {
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new BasicNameValuePair("num_taxi", paramString1));
		localArrayList.add(new BasicNameValuePair("geo_latitude", ""
				+ latitude_chauf));
		localArrayList.add(new BasicNameValuePair("geo_longitude", ""
				+ longitude_chauf));
		try {
			String str = new JSONArray(Html.fromHtml(
					this.json.makeHttpRequest(this.uri
							+ "update_location_taxi.php", "POST",
							localArrayList)).toString()).getJSONObject(0)
					.getString("result");
			return str;
		} catch (JSONException localJSONException) {
		} catch (Exception localJSONException) {
		}
		return "fail_connection";
	}

	public String update_statut(String paramString) {
		ArrayList localArrayList = new ArrayList();
		localArrayList
				.add(new BasicNameValuePair("id_reservation", paramString));
		try {
			String str = new JSONArray(Html.fromHtml(
					this.json.makeHttpRequest(this.uri + "confirm_it.php",
							"POST", localArrayList)).toString()).getJSONObject(
					0).getString("result");

			return str;
		} catch (JSONException localJSONException) {
		} catch (Exception localJSONException) {
		}
		return "fail_connection";
	}

	public boolean verif(String paramString) {
		return paramString
				.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
	}
}

/*
 * Location: E:\TaxiService\dex2jar\classes-dex2jar.jar
 * 
 * Qualified Name: taxi.shared.be.metier.Fonction_chauffur
 * 
 * JD-Core Version: 0.7.0.1
 */