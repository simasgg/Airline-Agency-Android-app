package si.uni_lj.fri.pbd.userinterfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> data = new ArrayList<String>();
    ImageView imageView;
    private ArrayList<Bitmap> bImage = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView) findViewById(R.id.list_id);
        generateListContent();
        setImages();
        lv.setAdapter(new MyListAdaper(this, R.layout.mylist, data, bImage));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setImages(){
        //bImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.barca);
        bImage.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.barca));
        bImage.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.paris));
        bImage.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.madrid));
        bImage.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.london));
    }

    private void generateListContent() {
        data.add("Barcelona, Spain");
        data.add("Paris, France");
        data.add("Madrid, Spain");
        data.add("London, United Kingdom");
        //data.add("Berlin, Germany");
        //data.add("Rome, Italy");
        //data.add("Ljubljana, Slovenia");
        //data.add("Vienna, Austria");
        //data.add("Warsaw, Poland");
        //data.add("Hamburg, Germany");
    }


    private class MyListAdaper extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;
        private List<Bitmap> bImages;
        private MyListAdaper(Context context, int resource, List<String> objects, List<Bitmap> img) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
            bImages = img;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
                //viewHolder.thumbnail.setImageBitmap(bImage);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(v.getContext(), TicketSelectedActivity.class);
                    myIntent.putExtra("key", getItem(position)); //Optional parameters
                    //myIntent.putExtra("countryID", position); //Optional parameters
                    startActivity(myIntent);
                }
            });
            mainViewholder.title.setText(getItem(position));
            mainViewholder.thumbnail.setImageBitmap(bImages.get(position));

            return convertView;
        }
    }
    public class ViewHolder {

        ImageView thumbnail;
        TextView title;
        Button button;
    }
}