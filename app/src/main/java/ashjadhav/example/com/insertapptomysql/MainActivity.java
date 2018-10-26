package ashjadhav.example.com.insertapptomysql;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String ServerURL = "http://192.168.1.14/Project1/saveName2.php" ;
    EditText ed_name;
    Button btn_submit;
    String tempName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_name=(EditText)findViewById(R.id.ed_name);
        btn_submit=(Button)findViewById(R.id.btn_Submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tempName=ed_name.getText().toString();
                insertData(tempName);
            }
        });

    }

    private void insertData(final String name_here) {

        class SendPostReqAsyncTask extends AsyncTask<String,Void,String>{


            @Override
            protected String doInBackground(String... params) {
                String nameHolder=name_here;
                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
                BasicNameValuePair obj=new BasicNameValuePair("send_name",nameHolder);
                nameValuePairs.add(obj);

                try{
                    HttpClient httpClient=new DefaultHttpClient();
                    HttpPost httpPost=new HttpPost(ServerURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse httpResponse=httpClient.execute(httpPost);
                    HttpEntity httpEntity=httpResponse.getEntity();
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "Exception occur: "+e, Toast.LENGTH_SHORT).show();
                }

                return "Data Inserted successfully";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MainActivity.this, "Data submitted successfully", Toast.LENGTH_SHORT).show();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask=new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(name_here);
    }

   // public void getData(){
        //tempName=ed_name.getText().toString();
   // }
}
