package com.example.contextmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.contextmenu.adapter.CustomAdapter;
import com.example.contextmenu.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList arrayList;
    private Button btnAdd;
    private ListView lvContact;
    private Adapter adapter;
    private CustomAdapter customAdapter;
    final Context context = this;
    int idd=-1;
    @SuppressLint({"WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        lvContact = (ListView) findViewById(R.id.lv_Contact);
        ArrayList<Contact> arrayList = new ArrayList<>();

        Contact contact1 = new Contact("Lê Xuân Viên","0988 933 xxx", Color.RED);
        Contact contact2 = new Contact("Hứa Đại Hậu","01667 585 545", Color.GREEN);
        Contact contact3 = new Contact("Lê Hoàng Long","0918 033 033", Color.GRAY);
        Contact contact4 = new Contact("Hà Thái Vũ","0978 102 102", Color.YELLOW);
        Contact contact5 = new Contact("Trương Ngọc Hoàng","01667 333 000", Color.BLACK);
        Contact contact6 = new Contact("Trần Văn Toàn","08 999 321", Color.BLUE);
        Contact contact7 = new Contact("Lại Thế Quang","01222 331 331", Color.CYAN);
        Contact contact8 = new Contact("le Xuan Vien","01222 331 331", Color.CYAN);

        arrayList.add(contact1);
        arrayList.add(contact2);
        arrayList.add(contact3);
        arrayList.add(contact4);
        arrayList.add(contact5);
        arrayList.add(contact6);
        arrayList.add(contact7);
        arrayList.add(contact8);

         customAdapter = new CustomAdapter(this,R.layout.row_listview,arrayList);
        lvContact.setAdapter(customAdapter);

        registerForContextMenu(lvContact);
        lvContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                idd=position;
                return false;
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemAdd) {

// get prompts.xml view
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.activity_add, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);
            final EditText tit = (EditText) promptsView
                    .findViewById(R.id.editAddname);
            final EditText des = (EditText) promptsView
                    .findViewById(R.id.editaddPhone);
            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // get user input and set it to result
                                    // edit text

                                    Contact contact = new Contact(tit.getText().toString(), des.getText().toString(), R.mipmap.ic_launcher);
                                    arrayList.add(contact);
                                    customAdapter.notifyDataSetChanged();

                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
        else if (item.getItemId() == R.id.itemDelete) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            // set prompts.xml to alertdialog builder
            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("DELETE",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // get user input and set it to result
                                    // edit text
                                    arrayList.remove(idd);
                                    idd = -1;
                                    customAdapter.notifyDataSetChanged();

                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
        else return false;
        return true;
        }

    }