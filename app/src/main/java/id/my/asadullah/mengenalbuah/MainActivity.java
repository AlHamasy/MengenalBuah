package id.my.asadullah.mengenalbuah;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    RecyclerView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO konek ID dengan variable
        view = findViewById(R.id.recyclerView);
        view.setHasFixedSize(true);

        //TODO implementasi adapter dengan recyler
        CustomRecyclerAdapter adapter = new CustomRecyclerAdapter();
        view.setAdapter(adapter);

        //TODO memilih linear layout di dalam recycler view
        view.setLayoutManager(new LinearLayoutManager(this));
    }

    //TODO membuat kelas adapter
    public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomViewHolder>{

        //TODO deklarasi gambar dan suara
        String data = "";
        String [] nama_buah = {"Alpukat", "Apel", "Ceri", "Durian", "Jambu Air", "Manggis", "Strawberry"};
        int [] gambar_buah = {R.drawable.alpukat,
                              R.drawable.apel,
                              R.drawable.ceri,
                              R.drawable.durian,
                              R.drawable.jambuair,
                              R.drawable.manggis,
                              R.drawable.strawberry};

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //TODO konek layout di recyclerView
            View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_view, parent,false);
            return new CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, final int position) {
            //TODO aksi untuk menset item
            holder.text.setText(nama_buah [position]);
            holder.image.setImageResource(gambar_buah [position]);

            //TODO aksi ketika item diklik
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO memanggil toast
                    Toast.makeText(MainActivity.this, nama_buah[position], Toast.LENGTH_SHORT).show();
                    playSong (position);

                }
            });

        }

        @Override
        public int getItemCount() {
            return nama_buah.length;
        }
    }

    //TODO method untuk memberikan suara ketika diklik
    private void playSong(int position) {
        // TODO deklarasi file suara
        int [] song_buah = {R.raw.alpukat,
                            R.raw.apel,
                            R.raw.ceri,
                            R.raw.durian,
                            R.raw.jambu_air,
                            R.raw.manggis,
                            R.raw.strawberry};

        // TODO parsing suara ke URI
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+song_buah[position]);

        //TODO memanggil Media Player
        MediaPlayer player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        //TODO konek URI ke Media Player
        try {
            player.setDataSource(getApplicationContext(),uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {
        //TODO deklarasi item
        ImageView image;
        TextView text;
        public CustomViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            text = itemView.findViewById(R.id.textView);
        }
    }

    //TODO membuat option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO memanggil menu di resource
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu, menu);
        return true;
    }

    // TODO membuat method pada item yang terpilih
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO cek id
        int id = item.getItemId();
        if (id == R.id.menu_grid) {
            view.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
            CustomRecyclerAdapter adapter = new CustomRecyclerAdapter();
            view.setAdapter(adapter);
        }
        else if (id == R.id.menu_list){
            view.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            CustomRecyclerAdapter adapter = new CustomRecyclerAdapter();
            view.setAdapter(adapter);
        }
        else if (id == R.id.menu_toast){
            Toast.makeText(this, "ini toast", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
