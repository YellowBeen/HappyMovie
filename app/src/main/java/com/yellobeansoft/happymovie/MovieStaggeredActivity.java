package com.yellobeansoft.happymovie;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;

/**
 * Created by Beboyz on 1/18/15 AD.
 */

public class MovieStaggeredActivity extends ActionBarActivity {

    private StaggeredGridView mGridView;
    private MovieStaggeredAdapter mAdapter;
    private ArrayList<String> mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_staggered);

        mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
        mAdapter = new MovieStaggeredAdapter(this, R.id.image);

        mDataset = generateSampleData();
        for (String data : mDataset) {
            mAdapter.add(data);
        }

        mGridView.setAdapter(mAdapter);

    }

    private ArrayList<String> generateSampleData() {
        final ArrayList<String> data = new ArrayList<String>();

        data.add("http://www.majorcineplex.com/uploads/movie/931/thumb_931.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/932/thumb_932.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/933/thumb_933.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/934/thumb_934.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/935/thumb_935.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/936/thumb_936.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/937/thumb_937.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/938/thumb_938.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/939/thumb_939.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/940/thumb_940.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/941/thumb_941.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/942/thumb_942.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/943/thumb_943.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/944/thumb_944.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/945/thumb_945.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/946/thumb_946.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/947/thumb_947.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/948/thumb_948.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/949/thumb_949.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/950/thumb_950.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/951/thumb_951.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/952/thumb_952.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/953/thumb_953.jpg");
        data.add("http://www.majorcineplex.com/uploads/movie/954/thumb_954.jpg");

        return data;
    }

}