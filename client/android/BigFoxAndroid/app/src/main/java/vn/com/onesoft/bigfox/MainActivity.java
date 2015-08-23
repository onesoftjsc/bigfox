package vn.com.onesoft.bigfox;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import bigfox.onesoft.com.vn.bigfoxandroid.R;
import vn.com.onesoft.bigfox.io.core.session.ConnectionManager;
import vn.com.onesoft.bigfox.io.message.user.cs.CSBigData;
import vn.com.onesoft.bigfox.io.message.user.cs.CSChat;
import vn.com.onesoft.bigfox.io.message.user.cs.CSName;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private static MainActivity _instance;

    private ListView lView;
    private EditText eText;
    ArrayList<String> listChat = new ArrayList<>();
    ArrayAdapter<String> adapter;
    public static MainActivity getInstance(){
        return _instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _instance = this;
        setContentView(R.layout.activity_main);

        ConnectionManager.getInstance();

        lView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,listChat);
        lView.setAdapter(adapter);
        findViewById(R.id.button).setOnClickListener(this);
        eText = (EditText) findViewById(R.id.editText);
        showMessage();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        ConnectionManager.getInstance().write(new CSChat(eText.getText().toString()));
        eText.setText("");
        adapter.notifyDataSetChanged();
    }

    public void receiveChat(String msg) {
         listChat.add(msg);
        adapter.notifyDataSetChanged();
        lView.setSelection(adapter.getCount() - 1);
    }

    private void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your name");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mText = input.getText().toString();
//                ConnectionManager.getInstance().write(new CSName(mText));
                sendBigData();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void sendBigData(){
//        for (int i = 0; i < 2; i++)
        ConnectionManager.getInstance().write(new CSBigData(new byte[8000000]));
    }
}
