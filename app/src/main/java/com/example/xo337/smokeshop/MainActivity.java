package com.example.xo337.smokeshop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.id.list;
import static com.example.xo337.smokeshop.R.id.order_Sum_Quantity;
import static com.example.xo337.smokeshop.R.layout.activity_chat;

public class MainActivity extends AppCompatActivity implements ChildEventListener {

    ArrayAdapter<String> fileDBAdapter;
    //    code start
    int pallmallRedNum = 0;
    int pricePallmallRed = 60;
    int pallmallblueNum = 0;
    int pricePallmallblue = 60;
    int pallmallgrayNum = 0;
    int pricePallmallgray = 60;
    int pallmallwhiteNum = 0;
    int pricePallmallwhite = 60;
    private FirebaseAuth fileAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "正在發送表單...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                sendbuttom(view);
            }
        });
//
        ListView list = (ListView) findViewById(R.id.listView);
        fileDBAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1);
        list.setAdapter(fileDBAdapter);

        fileAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.e("userLogIn", "onAuthStateChanged");
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Log.e("userLogIn", "page change to LogIn");
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    Log.e("userLogIn", "Login Success");
                    FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = fireDB.getReference("contacts");
                    myRef.addChildEventListener(MainActivity.this);
                }
            }
        };
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
        if (id == R.id.user_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //---------------------------buttonCode---------------------------
    public void pallmallClick(View view) {
        switch (view.getId()) {
            case R.id.imgButton_PMRed:
                pallmallRedNum = pallmallRedNum + 1;
                displaypallmallrad(pallmallRedNum, pricePallmallRed);
                break;
            case R.id.imgButton_PMBlue:
                pallmallblueNum = pallmallblueNum + 1;
                displaypallmallblue(pallmallblueNum, pricePallmallblue);
                break;
            case R.id.imgButton_PMgray:
                pallmallgrayNum = pallmallgrayNum + 1;
                displaypallmallgray(pallmallgrayNum, pricePallmallgray);
                break;
            case R.id.imgButton_PMwhite:
                pallmallwhiteNum = pallmallwhiteNum + 1;
                displaypallmallwhite(pallmallwhiteNum, pricePallmallwhite);
                break;
        }
        int Sum = pallmallRedNum + pallmallblueNum + pallmallgrayNum + pallmallwhiteNum;
        int price = (pallmallRedNum + pallmallblueNum + pallmallgrayNum + pallmallwhiteNum) * 10 * 60;
        TextView order_Sum_Quantity = (TextView) findViewById(R.id.order_Sum_Quantity);
        order_Sum_Quantity.setText("訂購數量： " + Sum + "條");
        TextView order_Sum_Price = (TextView) findViewById(R.id.order_Sum_Price);
        order_Sum_Price.setText("共： " + price + "元");
    }

    private void displaypallmallrad(int number, int price) {
        TextView numdisplay = (TextView) findViewById(R.id.num_pallmall_red);
        numdisplay.setText(number + " 條");
        TextView numdisplayforten = (TextView) findViewById(R.id.num_pallmall_red_ten);
        numdisplayforten.setText(number * 10 + " 包");
        TextView pricesumdisplay = (TextView) findViewById(R.id.price_pallmall_red_sum);
        pricesumdisplay.setText("單品金額： $" + number * 10 * price + " 元");
    }

    private void displaypallmallblue(int number, int price) {
        TextView numdisplay = (TextView) findViewById(R.id.num_pallmall_blue);
        numdisplay.setText(number + " 條");
        TextView numdisplayforten = (TextView) findViewById(R.id.num_pallmall_blue_ten);
        numdisplayforten.setText(number * 10 + " 包");
        TextView pricesumdisplay = (TextView) findViewById(R.id.price_pallmall_blue_sum);
        pricesumdisplay.setText("單品金額： $" + number * 10 * price + " 元");
    }

    private void displaypallmallgray(int number, int price) {
        TextView numdisplay = (TextView) findViewById(R.id.num_pallmall_gray);
        numdisplay.setText(number + " 條");
        TextView numdisplayforten = (TextView) findViewById(R.id.num_pallmall_gray_ten);
        numdisplayforten.setText(number * 10 + " 包");
        TextView pricesumdisplay = (TextView) findViewById(R.id.price_pallmall_gray_sum);
        pricesumdisplay.setText("單品金額： $" + number * 10 * price + " 元");
    }

    private void displaypallmallwhite(int number, int price) {
        TextView numdisplay = (TextView) findViewById(R.id.num_pallmall_white);
        numdisplay.setText(number + " 條");
        TextView numdisplayforten = (TextView) findViewById(R.id.num_pallmall_white_ten);
        numdisplayforten.setText(number * 10 + " 包");
        TextView pricesumdisplay = (TextView) findViewById(R.id.price_pallmall_white_sum);
        pricesumdisplay.setText("單品金額： $" + number * 10 * price + " 元");
    }

    //    send Emali
    public void sendbuttom(View view) {
        String ouderList = "";
        if (pallmallRedNum + pallmallblueNum + pallmallgrayNum + pallmallwhiteNum != 0) {
            if (pallmallRedNum != 0) {
                ouderList = ouderList + "寶馬(紅)： " + pallmallRedNum + " 條,";
            }
            if (pallmallblueNum != 0) {
                ouderList = ouderList + "寶馬(藍)： " + pallmallblueNum + " 條,";
            }
            if (pallmallgrayNum != 0) {
                ouderList = ouderList + "寶馬(灰)： " + pallmallgrayNum + " 條,";
            }
            if (pallmallwhiteNum != 0) {
                ouderList = ouderList + "寶馬(白)： " + pallmallwhiteNum + " 條";
            }
        }
        listSendTofirebase("s15115163@stu.edu.tw",
                "android email text",
                ouderList);
    }

    private void listSendTofirebase(String addresses, String subject, String Datelist) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, Datelist);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

//---------------------------------changePage-----------------------------
    public void loginPage(MenuItem item) {
        Intent page = new Intent(this, LoginActivity.class);
        startActivity(page);
    }

    public void chatPage(MenuItem item) {
        Intent page = new Intent(this, chatActivity.class);
        startActivity(page);
    }

//------------------------------firebase code--------------------------

    @Override
    protected void onStart() {
        super.onStart();
        fileAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        if (authStateListener != null) {
            fileAuth.removeAuthStateListener(authStateListener);
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        fileAuth.signOut();
        super.onDestroy();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        fileDBAdapter.add(
                String.valueOf(dataSnapshot.child("name").getValue()));
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        fileDBAdapter.remove(
                String.valueOf(dataSnapshot.child("name").getValue()));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
    }
//getVersionControl
}
