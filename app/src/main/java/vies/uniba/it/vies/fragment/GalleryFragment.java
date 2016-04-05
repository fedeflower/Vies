package vies.uniba.it.vies.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

import vies.uniba.it.vies.R;
import vies.uniba.it.vies.activity.DetailPhotoActivity;
import vies.uniba.it.vies.adapter.GalleryAdapter;
import vies.uniba.it.vies.adapter.RecyclerItemClickListener;
import vies.uniba.it.vies.model.Album;
import vies.uniba.it.vies.model.ImageModel;

/**
 * Created by Marco on 4/4/2016.
 */
public class GalleryFragment extends Fragment {
        int color;
        GalleryAdapter adapter;
        ArrayList<ImageModel> data = new ArrayList<>();
    String album_name;

    /*
        public static String IMGS[] = {
           "https://images.unsplash.com/photo-1444090542259-0af8fa96557e?q=80&fm=jpg&w=1080&fit=max&s=4b703b77b42e067f949d14581f35019b",
            "https://dl.dropboxusercontent.com/u/66373804/IMG/Bari%20(1).jpg",
            "http://images.wired.it/wp-content/uploads/2016/01/1453282239_gatto-9-600x335.jpg",
            "http://images.wired.it/wp-content/uploads/2016/01/1453282239_gatto-9-600x335.jpg",
            "http://images.wired.it/wp-content/uploads/2016/01/1453282239_gatto-9-600x335.jpg",
            "http://images.wired.it/wp-content/uploads/2016/01/1453282239_gatto-9-600x335.jpg",
            "http://images.wired.it/wp-content/uploads/2016/01/1453282239_gatto-9-600x335.jpg",
            "http://images.wired.it/wp-content/uploads/2016/01/1453282239_gatto-9-600x335.jpg",
            "http://images.wired.it/wp-content/uploads/2016/01/1453282239_gatto-9-600x335.jpg",
            "https://images.unsplash.com/photo-1441155472722-d17942a2b76a?q=80&fm=jpg&w=1080&fit=max&s=80cb5dbcf01265bb81c5e8380e4f5cc1",
            "https://images.unsplash.com/photo-1437651025703-2858c944e3eb?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
            "https://images.unsplash.com/photo-1431538510849-b719825bf08b?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
            "https://images.unsplash.com/photo-1434873740857-1bc5653afda8?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
            "https://images.unsplash.com/photo-1439396087961-98bc12c21176?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
            "https://images.unsplash.com/photo-1433616174899-f847df236857?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
            "https://images.unsplash.com/photo-1438480478735-3234e63615bb?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
            "https://images.unsplash.com/photo-1438027316524-6078d503224b?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300"
    };
     */
        public static String IMGS[]={};

        public GalleryFragment() {
        }

        @SuppressLint("ValidFragment")
        public GalleryFragment(int color,String album_name) {
            this.color = color;
            this.album_name=album_name;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_gallery, container, false);

            final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.gallery_frag);
            frameLayout.setBackgroundColor(color);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.gallery_recycler);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getBaseContext(), 3));
            recyclerView.setHasFixedSize(true);
            Log.d("Comments", album_name);
           // Log.d("Comments", Boolean.toString(Prefs.getInstance(getActivity().getBaseContext()).getBoolean("album2_"+album_name, false)));
            //if(Prefs.getInstance(getActivity().getBaseContext()).getBoolean("album2_"+album_name, false)){
            switch(album_name.toUpperCase()){
                case "BARI": {
                    IMGS=Album.BARI;
                    break;
                }
                case "ROMA":{
                    IMGS=Album.ROMA;break;
                }
                case "PARIGI":{
                    IMGS=Album.PARIGI;break;
                }
                default:{
                    IMGS=Album.DEF;break;
                }
            }
        //}

            for (int i = 0; i < IMGS.length; i++) {

                ImageModel imageModel = new ImageModel();
                imageModel.setName("Image " + i);
                imageModel.setUrl(IMGS[i]);
                data.add(imageModel);

            }

            adapter = new GalleryAdapter(getActivity().getBaseContext(), data);
            recyclerView.setAdapter(adapter);


            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getBaseContext(),
                    new RecyclerItemClickListener.OnItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {

                            Intent intent = new Intent(getActivity(), DetailPhotoActivity.class);
                            intent.putParcelableArrayListExtra("data", data);
                            intent.putExtra("pos", position);
                            startActivity(intent);

                        }
                    }));

            return view;
        }
}