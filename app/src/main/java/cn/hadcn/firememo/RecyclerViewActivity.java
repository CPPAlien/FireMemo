 package cn.hadcn.firememo;

 import android.content.DialogInterface;
 import android.os.Bundle;
 import android.support.v7.app.AlertDialog;
 import android.support.v7.app.AppCompatActivity;
 import android.support.v7.widget.LinearLayoutManager;
 import android.support.v7.widget.RecyclerView;
 import android.view.View;
 import android.widget.EditText;
 import android.widget.TextView;

 import com.firebase.client.Firebase;
 import com.firebase.ui.FirebaseRecyclerAdapter;

 public class RecyclerViewActivity extends AppCompatActivity {

    Firebase mFireRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        mFireRef = new Firebase("https://fire-memo.firebaseio.com/list");
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         FirebaseRecyclerAdapter<String, MemoViewHolder> adapter = new FirebaseRecyclerAdapter<String, MemoViewHolder>(
                String.class,
                 android.R.layout.two_line_list_item,
                 MemoViewHolder.class,
                 mFireRef
         ) {

             @Override
             protected void populateViewHolder(cn.hadcn.firememo.RecyclerViewActivity.MemoViewHolder memoViewHolder, String s, int i) {
                 memoViewHolder.tvContent.setText(s);
             }
         };
        recyclerView.setAdapter(adapter);
    }

     public static class MemoViewHolder extends RecyclerView.ViewHolder {
         TextView tvContent;

         public MemoViewHolder(View itemView) {
             super(itemView);
             tvContent = (TextView)itemView.findViewById(android.R.id.text1);
         }
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
