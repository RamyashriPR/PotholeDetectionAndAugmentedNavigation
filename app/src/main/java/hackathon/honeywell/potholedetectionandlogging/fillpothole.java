package hackathon.honeywell.potholedetectionandlogging;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

public class fillpothole extends AppCompatActivity {

    Intent i,j;
    String pot,pot1;


    Button backfill;
    TextView tab1,tab2,tab3;
    DBHandler dbHandler = new DBHandler(this,null,null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillpothole);

        j=getIntent();
        backfill=(Button)findViewById(R.id.message1);
        tab1=(TextView) findViewById(R.id.tv11);
        tab2=(TextView) findViewById(R.id.tv22);
        tab3=(TextView) findViewById(R.id.tv33);

        pot1=j.getStringExtra("id");

        if(pot1 !=null) {
            pot1 = (String) pot1;
            String[] array1 = pot1.split(",");
            tab1.setText(array1[0]);
            tab2.setText(array1[1]);
            tab3.setText(array1[2]);
        }
        backfill.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent i=new Intent(fillpothole.this,PotholeList.class);
                startActivity(i);
            }
        });
    }

    public void fillPothole(View view) {
        i = getIntent();

        pot = i.getStringExtra("id");
     //   Toast.makeText(getApplicationContext(),pot,Toast.LENGTH_LONG).show();
        String[] array= pot.split(",");
     //   Toast.makeText(getApplicationContext(),array[0],Toast.LENGTH_LONG).show();
        Location location = new Location("Test");
        location.setLatitude(Double.parseDouble(array[0]));
        location.setLongitude(Double.parseDouble(array[1]));
        dbHandler.deleteLocation(location);
        Toast.makeText(getApplicationContext(),"POTHOLE FILLED",Toast.LENGTH_LONG).show();
        //marker.remove();
    }



}