 package cn.hadcn.firememo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

 public class ListViewActivity extends AppCompatActivity {

    Firebase mFireRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        mFireRef = new Firebase("https://fire-memo.firebaseio.com/list");
        ListView listView = (ListView)findViewById(R.id.listView);

        FirebaseListAdapter<String> listAdapter = new FirebaseListAdapter<String>(
                this,
                String.class,
                android.R.layout.simple_list_item_1,
                mFireRef
        ) {
            @Override
            protected void populateView(View view, String s, int i) {
                TextView tvContent = (TextView)view.findViewById(android.R.id.text1);
                tvContent.setText(s);
            }
        };
        listView.setAdapter(listAdapter);
    }

     AlertDialog addDialog;
     public void onAddClick(View view) {
         addDialog = new AlertDialog.Builder(this)
                 .setTitle("Add Item")
                 .setView(R.layout.dialog_add)
                 .setPositiveButton(android.R.string.ok, new OnOkClick())
                 .create();
         addDialog.show();
     }

     class OnOkClick implements DialogInterface.OnClickListener {

         @Override
         public void onClick(DialogInterface dialog, int which) {
             EditText et = (EditText)addDialog.findViewById(R.id.add_text);
             String content = et.getText().toString();
             if ( !content.isEmpty() ) {
                 mFireRef.push().setValue(content);
             }
         }
     }
}
