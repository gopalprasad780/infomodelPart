package com.shailsoft.infomodelpart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shailsoft.infomodelpart.Model.ReviewModel;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_submit;
    InternetConnectivity internetConnectivity;

    private static final String URLS ="http://beta.gkninternational.life/webservice/review_of_store.php";

    ListView lvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMovies = (ListView) findViewById(R.id.lvMovies);

        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(this);

        internetConnectivity = new InternetConnectivity(MainActivity.this);
    }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_submit) {
                if (internetConnectivity.isInternetOn()) {
                       new AsyncTaskRunner().execute();
                    }

                }
            }

    public String connection(String action) {
        String result="";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URLS);// replace with your url
        httpPost.addHeader("Content-type",
                "application/x-www-form-urlencoded");


        List<NameValuePair> nameValuePairList = new ArrayList<>();

        nameValuePairList.add(new BasicNameValuePair(
                "action", action));

        nameValuePairList.add(new BasicNameValuePair(
                "StoreId", "66"));


        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
                    nameValuePairList);
            Log.e("nameValuePairList", " " + nameValuePairList);
            httpPost.setEntity(urlEncodedFormEntity);

            try {
                HttpResponse httpResponse = httpClient
                        .execute(httpPost);
                InputStream inputStream = httpResponse.getEntity()
                        .getContent();
                InputStreamReader inputStreamReader = new InputStreamReader(
                        inputStream);
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String bufferedStrChunk = null;
                while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                    stringBuilder.append(bufferedStrChunk);
                }
                Log.e("result", stringBuilder.toString());
                result=stringBuilder.toString();
                return stringBuilder.toString();

            } catch (ClientProtocolException cpe) {
                System.out
                        .println("First Exception coz of HttpResponese :"
                                + cpe);
                cpe.printStackTrace();
            } catch (IOException ioe) {
                System.out
                        .println("Second Exception coz of HttpResponse :"
                                + ioe);
                ioe.printStackTrace();
            }

        } catch (UnsupportedEncodingException uee) {
            System.out
                    .println("An Exception given because of UrlEncodedFormEntity argument :"
                            + uee);
            uee.printStackTrace();
        }
        return result;
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, List<ReviewModel>> {
        ProgressDialog mProgressBar;
        int success;
        String msg;

        @Override
        protected List<ReviewModel> doInBackground(String... params) {
            String result=connection("MainActivity");

            List<ReviewModel> reviewModelList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                success    = jsonObject.getInt("success");
                msg   =jsonObject.getString("message");


                JSONArray parentArray = jsonObject.getJSONArray("Review");



                for(int i = 0; i<parentArray.length();i++){

                    JSONObject finalObject = parentArray.getJSONObject(i);

                    ReviewModel reviewModel = new ReviewModel();
                    reviewModel.setUserImages(finalObject.optString("UserImages"));
                    reviewModel.setUserName(finalObject.optString("UserName"));
                    reviewModel.setStoreReview((float) finalObject.optDouble("StoreReview"));
                    reviewModel.setStoreReviewTitle(finalObject.optString("StoreReviewTitle"));
                    reviewModel.setStoreReviewDescription(finalObject.optString("StoreReviewDescription"));
                    reviewModel.setReviewLike(finalObject.optInt("ReviewLike"));
                    reviewModel.setTotalComments(finalObject.getInt("TotalComments"));
                    reviewModel.setReviewTime(finalObject.optString("ReviewTime"));
                    reviewModel.setUserImages(finalObject.optString("Comments"));
                /* one =    ;
                   two = finalObject.getString("UserName");
                    three =finalObject.getString("StoreReview");
                   four = finalObject.getString("StoreReviewTitle");
                    five = finalObject.getString("StoreReviewDescription");
                    six = finalObject.getInt("ReviewLike");
                    seven = finalObject.getInt("TotalComments");
                   eight =  finalObject.getString("ReviewTime");
                    nine = finalObject.getString("Comments");*/
                    reviewModelList.add(reviewModel);
                }

                return reviewModelList;

            }catch(Exception e){
                e.printStackTrace();
            }
            return reviewModelList;

        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(List<ReviewModel> result) {

            super.onPostExecute(result);
            // execution of result of Long time consuming operation
            mProgressBar.dismiss();
            //   Toast.makeText(SignUpActivity.this,resp,Toast.LENGTH_SHORT).show();
            if(success == 0){
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
            }
            else if(success == 1){
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

            }
            else if(success == 2){
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
            }
            ReviewAdapter reviewAdapter = new ReviewAdapter(getApplicationContext(),R.layout.row,result);
            lvMovies.setAdapter(reviewAdapter);
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            mProgressBar = new ProgressDialog(MainActivity.this);
            mProgressBar.setMessage("Connecting to server");

            mProgressBar.setCancelable(false);
            mProgressBar.show();
        }
    }

    public class ReviewAdapter extends ArrayAdapter{

        private List<ReviewModel> reviewModelList;
        private int resource;
        private LayoutInflater inflater;

        public ReviewAdapter(Context context, int resource, List<ReviewModel> objects) {
            super(context, resource, objects);

            reviewModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = inflater.inflate(resource,null);
            }

            ImageView userImages;
            TextView userName;
            RatingBar storeReview;
            TextView storeReviewdescription;
            TextView reviewLike;
            TextView storeReviewtitle;
            TextView totalComments;
            TextView riviewtime;
            TextView comments;

            userImages = (ImageView) convertView.findViewById(R.id.user_images);
            userName = (TextView) convertView.findViewById(R.id.user_name);
            storeReview = (RatingBar) convertView.findViewById(R.id.store_review);
            storeReviewtitle = (TextView) convertView.findViewById(R.id.store_review_title);
            storeReviewdescription = (TextView) convertView.findViewById(R.id.sotre_review_description);
            reviewLike = (TextView) convertView.findViewById(R.id.review_like);
            totalComments = (TextView) convertView.findViewById(R.id.total_comments);
            riviewtime = (TextView) convertView.findViewById(R.id.review_time);
            comments = (TextView) convertView.findViewById(R.id.comments);

            userName.setText(reviewModelList.get(position).getUserName());
            storeReviewtitle.setText(reviewModelList.get(position).getStoreReviewTitle());
            storeReviewdescription.setText(reviewModelList.get(position).getStoreReviewDescription());
            reviewLike.setText(reviewModelList.get(position).getReviewLike());
            totalComments.setText(reviewModelList.get(position).getTotalComments());
            riviewtime.setText("Year: " +reviewModelList.get(position).getReviewTime());
            comments.setText(reviewModelList.get(position).getComments());

            storeReview.setRating(reviewModelList.get(position).getStoreReview()/2);

            return convertView;
        }
    }

}
