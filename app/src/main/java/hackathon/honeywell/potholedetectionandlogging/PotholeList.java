package hackathon.honeywell.potholedetectionandlogging;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PotholeList extends AppCompatActivity {

    ListView lv1;
    Button back;
    String pothole;
    //DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pothole_list);

        back=(Button)findViewById(R.id.message);

        DBHandler dbHandler=new DBHandler(this,null,null,1);
        //if(dbHandler.getList() != null) {
            lv1 = (ListView) findViewById(R.id.lv);
        ArrayList<String> items = dbHandler.getList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PotholeList.this, android.R.layout.simple_list_item_1, items);
            lv1.setAdapter(adapter);
     //   lv1.setTextColor(Color.WHITE);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pothole =lv1.getItemAtPosition(position).toString();

                Toast.makeText(getApplicationContext(), pothole, Toast.LENGTH_SHORT).show();
                Intent i= new Intent(PotholeList.this,fillpothole.class);
                i.putExtra("id",pothole);
                startActivity(i);
            }
        });








        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent i=new Intent(PotholeList.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
