package myfragment;


import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import static extra.UrlEndPoints.URL_BOX_OFFICE;
import static extra.UrlEndPoints.URL_CHAR_QUESTION;
import static extra.UrlEndPoints.URL_PARA_API_KEY;
import static extra.Keys.EndPointBoxOffice.*;
import static extra.UrlEndPoints.URL_POSTER;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.materialtest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import adapters.AdapterBoxOffice;
import extra.Movies;
import extra.MyApplication;
import networks.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBoxOffice extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private ArrayList<Movies> listMovies = new ArrayList<>();
    private AdapterBoxOffice adapterBoxOffice;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView listMovieHits;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        Log.e("myApp", "no image");
        return fragment;

    }

    public static String getRequestUrl() {
        return URL_BOX_OFFICE
                + URL_CHAR_QUESTION
                + URL_PARA_API_KEY + MyApplication.API_KEY;
    }

    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

        sendJsonRequest();
    }

    public void sendJsonRequest() {


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                getRequestUrl(),
                null + "  ",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        listMovies = parseJSONResponse(response);

                        adapterBoxOffice.setMovieList(listMovies);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    private ArrayList<Movies> parseJSONResponse(JSONObject response) {

        ArrayList<Movies> listMovies = new ArrayList<>();

        if (response != null && response.length() > 0) {


            try {


                JSONArray arrayMovies = response.getJSONArray(KEY_RESULTS);

                for (int i = 0; i < arrayMovies.length(); i++) {

                    JSONObject currentMovies = arrayMovies.getJSONObject(i);

                    //get the id of the movies

                    long id = currentMovies.getLong(KEY_ID);

                    //get the title of the movies

                    String title = currentMovies.getString(KEY_TITLE);

                    //get the release date of the movies

                    String releaseDate = null;

                    if (currentMovies.has(KEY_RELEASE_DATE)) {

                        releaseDate = currentMovies.getString(KEY_RELEASE_DATE);
                    } else {

                        releaseDate = "NA";


                    }

                    //get the ratings of the movies

                    int audienceScore = -1;

                    if (currentMovies.has(KEY_RATINGS)) {

                        audienceScore = currentMovies.getInt(KEY_RATINGS);
                    }

                    //get the overview of movies

                    String overview = currentMovies.getString(KEY_OVERVIEW);

                    //get the posters of movies


                    String urlPoster = null;

                    if (currentMovies.has(KEY_POSTER)) {

                        urlPoster = URL_POSTER + currentMovies.getString(KEY_POSTER);
                    }

                    Movies movie = new Movies();

                    movie.setId(id);

                    movie.setTitle(title);

                    Date date = dateFormat.parse(releaseDate);

                    movie.setReleaseDate(date);

                    movie.setAudienceScore(audienceScore);

                    movie.setUrlPOster(urlPoster);

                    movie.setOverview(overview);


                    listMovies.add(movie);

                }


            } catch (JSONException e) {

                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return listMovies;


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_box_office, container, false);
        listMovieHits = (RecyclerView) view.findViewById(R.id.movieHits);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice = new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);
        sendJsonRequest();
        return view;

    }


}
