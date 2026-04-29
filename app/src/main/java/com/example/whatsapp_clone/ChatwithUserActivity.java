package com.example.whatsapp_clone;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChatwithUserActivity extends AppCompatActivity {

    ImageView backbtn, currentuserdp, videocallbtn, voicecallbtn, openmenu,
            emojibtn, mediabtn, paymentbtn, camerabtn, sentandvoiceimage;
    CardView sentandvoicebtn;
    EditText message;
    TextView currentusername, currentuserstatus;
    LinearLayout chat_user_topbar,bottombar,blocklayout,deletechatbtn,unblockchatbtn;
    RecyclerView message_RecyclerView;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    int flag = 0;
    String reciever_email;
    Boolean isblock = false;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chatwith_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backbtn = findViewById(R.id.backbtn);
        currentuserdp = findViewById(R.id.currentuserdp);
        videocallbtn = findViewById(R.id.videocallbtn);
        voicecallbtn = findViewById(R.id.voicecallbtn);
        openmenu = findViewById(R.id.openmenu);
        currentusername = findViewById(R.id.currentusername);
        currentuserstatus = findViewById(R.id.currentuserstatus);
        sentandvoicebtn = findViewById(R.id.sendandvoicebtn);
        message = findViewById(R.id.message);
        emojibtn = findViewById(R.id.emogibtn);
        mediabtn = findViewById(R.id.mediabtn);
        paymentbtn = findViewById(R.id.paymentbtn);
        camerabtn = findViewById(R.id.camreabtn);
        sentandvoiceimage = findViewById(R.id.sentandvoicebtnimage);
        chat_user_topbar = findViewById(R.id.chat_user_topbar);
        message_RecyclerView = findViewById(R.id.chat_with_user_recyclerview);
        bottombar = findViewById(R.id.bottombar);
        blocklayout = findViewById(R.id.blocklayout);
        deletechatbtn = findViewById(R.id.deletechatbtn);
        unblockchatbtn = findViewById(R.id.unblockbtn);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        Intent next = getIntent();
        String name = next.getStringExtra("name");
        String imageurl = next.getStringExtra("imageurl");
        reciever_email = next.getStringExtra("email");
        final String sender_email = mAuth.getCurrentUser().getEmail();

        currentusername.setText(name);
        Glide.with(getApplicationContext())
                .load(imageurl)
                .placeholder(R.drawable.userplaceeholder)
                .into(currentuserdp);


        View mainLayout = findViewById(R.id.main);

        ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            Insets ime = insets.getInsets(WindowInsetsCompat.Type.ime()); // Keyboard ki height

            // Bottom padding ko keyboard ki height ke barabar set karna
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, ime.bottom);

            return WindowInsetsCompat.CONSUMED;
        });


        chat_user_topbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatwithUserActivity.this, View_User_Profile.class);
                intent.putExtra("name", name);
                intent.putExtra("imageurl", imageurl);
                intent.putExtra("email", reciever_email);
                startActivity(intent);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        videocallbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Video call start", Toast.LENGTH_SHORT).show();
            }
        });

        voicecallbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Voice call start", Toast.LENGTH_SHORT).show();
            }
        });

        mediabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "media btn clicked", Toast.LENGTH_SHORT).show();
            }
        });

        paymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "payment clicked", Toast.LENGTH_SHORT).show();
            }
        });

        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "camera clicked", Toast.LENGTH_SHORT).show();
            }
        });

        emojibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "emoji btn clicked", Toast.LENGTH_SHORT).show();
            }
        });

        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String msg = charSequence.toString().trim();
                if (msg.length() > 0) {
                    sentandvoiceimage.setImageResource(R.drawable.sent);
                    camerabtn.setVisibility(View.GONE);
                    paymentbtn.setVisibility(View.GONE);
                    flag = 1;
                } else {
                    sentandvoiceimage.setImageResource(R.drawable.microphone);
                    camerabtn.setVisibility(View.VISIBLE);
                    paymentbtn.setVisibility(View.VISIBLE);
                    flag = 0;
                }
            }
        });

        database.getReference("AllUsers")
                .child(sender_email.replace(".",","))
                .child("MyAllUsers")
                .child(reciever_email.replace(".",","))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() && snapshot.hasChild("isblock")) {
                            isblock = snapshot.child("isblock").getValue(Boolean.class);
                        }

                        if(isblock){
                            blocklayout.setVisibility(View.VISIBLE);
                            bottombar.setVisibility(View.GONE);
                        }else {
                            blocklayout.setVisibility(View.GONE);
                            bottombar.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        final ArrayList<message_modal> message_modalArrayList = new ArrayList<message_modal>();
        final message_adapter message_adapter = new message_adapter(ChatwithUserActivity.this, message_modalArrayList,reciever_email);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        message_RecyclerView.setLayoutManager(layoutManager);
        message_RecyclerView.setAdapter(message_adapter);
        message_adapter.notifyDataSetChanged();

        final String sender_room = sender_email.replace(".", ",") + reciever_email.replace(".", ",");
        final String reciever_room = reciever_email.replace(".", ",") + sender_email.replace(".", ",");

        database.getReference().child("chats")
                .child(sender_room).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        message_modalArrayList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            message_modal data = dataSnapshot.getValue(message_modal.class);
                            data.setMessageid(dataSnapshot.getKey());
                            if(!isblock){
                                message_modalArrayList.add(data);
                            }
                        }
                        message_adapter.notifyDataSetChanged();
                        message_RecyclerView.post(() -> {
                            if (!message_modalArrayList.isEmpty()) {
                                message_RecyclerView.scrollToPosition(message_modalArrayList.size() - 1);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        sentandvoicebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (flag == 1) {
                    String msg = message.getText().toString().trim();
                    if (msg.equals("")) {
                        return;
                    }
                    final message_modal modal = new message_modal(sender_email, msg);
                    modal.setMsgtime(new Date().getTime());
                    message.setText("");

                    database.getReference().child("chats")
                            .child(sender_room)
                            .push()
                            .setValue(modal).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    database.getReference().child("chats")
                                            .child(reciever_room)
                                            .push()
                                            .setValue(modal).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    message_RecyclerView.postDelayed(() -> {
                                                        message_RecyclerView.scrollToPosition(message_modalArrayList.size() - 1);
                                                    }, 100);
                                                }
                                            });
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "recording start", Toast.LENGTH_SHORT).show();
                }
            }
        });

        openmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContextThemeWrapper ctw = new ContextThemeWrapper(getApplicationContext(), R.style.MyPopupMenu2);
                PopupMenu popupMenu = new PopupMenu(ctw, view);
                popupMenu.getMenuInflater().inflate(R.menu.insidechatmenu, popupMenu.getMenu());

                database.getReference("AllUsers")
                        .child(sender_email.replace(".",","))
                        .child("MyAllUsers")
                        .child(reciever_email.replace(".",",")).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Boolean ismute = false;
                                if (snapshot.exists()) {
                                    ismute = snapshot.child("ismute").getValue(Boolean.class);
                                    isblock = snapshot.child("isblock").getValue(Boolean.class);
                                }

                                if (ismute == null && isblock == null) {
                                    return;
                                }

                                MenuItem mute = popupMenu.getMenu().findItem(R.id.mutenoti);
                                MenuItem unmute = popupMenu.getMenu().findItem(R.id.unmutenoti);
                                MenuItem unblock = popupMenu.getMenu().findItem(R.id.unblockchat);
                                MenuItem block = popupMenu.getMenu().findItem(R.id.blockchat);

                                if(ismute){
                                    mute.setVisible(false);
                                    unmute.setVisible(true);
                                }else
                                {
                                    mute.setVisible(true);
                                    unmute.setVisible(false);
                                }

                                if(isblock){
                                    block.setVisible(false);
                                    unblock.setVisible(true);
                                }else {
                                    block.setVisible(true);
                                    unblock.setVisible(false);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        int id = menuItem.getItemId();

                        if (id == R.id.insidechat_newgroup) {
                            Toast.makeText(getApplicationContext(), "new group", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (id == R.id.viewprofile) {
                            Intent intent = new Intent(ChatwithUserActivity.this, View_User_Profile.class);
                            intent.putExtra("name", name);
                            intent.putExtra("imageurl", imageurl);
                            intent.putExtra("email", reciever_email);
                            startActivity(intent);
                            return true;
                        } else if (id == R.id.search) {
                            Toast.makeText(getApplicationContext(), "search", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (id == R.id.medialinkanddocs) {
                            Toast.makeText(getApplicationContext(), "media and links", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (id == R.id.mutenoti) {

                            HashMap<String, Object> hashMap = new HashMap<String, Object>();
                            hashMap.put("ismute", true);
                            database.getReference("AllUsers")
                                    .child(sender_email.replace(".",","))
                                    .child("MyAllUsers")
                                    .child(reciever_email.replace(".",","))
                                    .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getApplicationContext(),name + " notification muted",Toast.LENGTH_SHORT).show();

                                        }
                                    });

                            return true;
                        } else if (id == R.id.unmutenoti) {

                            HashMap<String, Object> hashMap = new HashMap<String, Object>();
                            hashMap.put("ismute", false);
                            database.getReference("AllUsers")
                                    .child(sender_email.replace(".",","))
                                    .child("MyAllUsers")
                                    .child(reciever_email.replace(".",","))
                                    .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getApplicationContext(),name + " notification unmute",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            return true;

                        } else if (id == R.id.disappearingmsg) {
                            Toast.makeText(getApplicationContext(), "disappear msg", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (id == R.id.chattheme) {
                            Toast.makeText(getApplicationContext(), "chat theme", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (id == R.id.report) {
                            Toast.makeText(getApplicationContext(), "report", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (id == R.id.blockchat) {
                            HashMap<String, Object> hashMap = new HashMap<String, Object>();
                            hashMap.put("isblock", true);
                            database.getReference("AllUsers")
                                    .child(sender_email.replace(".",","))
                                    .child("MyAllUsers")
                                    .child(reciever_email.replace(".",","))
                                    .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                        }
                                    });
                            return true;
                        } else if (id == R.id.unblockchat) {
                            HashMap<String, Object> hashMap = new HashMap<String, Object>();
                            hashMap.put("isblock", false);
                            database.getReference("AllUsers")
                                    .child(sender_email.replace(".",","))
                                    .child("MyAllUsers")
                                    .child(reciever_email.replace(".",","))
                                    .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });
                            return true;
                        } else if (id == R.id.clearchat) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(ChatwithUserActivity.this);
                            builder.setTitle("Clear chat");
                            builder.setCancelable(false);
                            builder.setIcon(R.drawable.clearchat);
                            builder.setMessage("Are you sure to clear chat?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    database.getReference().child("chats")
                                            .child(sender_room)
                                            .setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                    }
                                                }
                                            });
                                }
                            });

                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });

                            builder.create();
                            builder.show();
                            return true;

                        } else if (id == R.id.Exportchat) {
                            Toast.makeText(getApplicationContext(), "export chat", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (id == R.id.addsortcut) {
                            Toast.makeText(getApplicationContext(), "add sortcut", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (id == R.id.addtolist) {
                            Toast.makeText(getApplicationContext(), "add to list", Toast.LENGTH_SHORT).show();
                            return true;
                        }

                        return false;
                    }
                });

                popupMenu.show();
                ;
            }
        });

        deletechatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ChatwithUserActivity.this);
                builder.setIcon(R.drawable.block);
                builder.setTitle("Delete chat");
                builder.setMessage("Are you sure you want to delete this chat? All messages will be removed.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProgressDialog dialog = new ProgressDialog(ChatwithUserActivity.this);
                        dialog.setTitle("Delete");
                        dialog.setMessage("Deleting your msg and chat");
                        dialog.setIcon(R.drawable.bin);
                        dialog.show();

                        if (mAuth.getCurrentUser().getEmail() == null || reciever_email == null) {
                            dialog.dismiss();
                            Toast.makeText(ChatwithUserActivity.this, "Error: User data missing", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String semail = mAuth.getCurrentUser().getEmail();
                        String sender_room1 = semail.replace(".",",") + reciever_email.replace(".",",");
                        database.getReference().child("chats")
                                .child(sender_room1)
                                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            database.getReference("AllUsers")
                                                    .child(semail.replace(".",","))
                                                    .child("MyAllUsers")
                                                    .child(reciever_email.replace(".",","))
                                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()) {
                                                                dialog.dismiss();
                                                                Toast.makeText(ChatwithUserActivity.this," Deleted from chats", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(ChatwithUserActivity.this, MainActivity.class);
                                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                startActivity(intent);
                                                                finish();
                                                            }else {
                                                                dialog.dismiss();
                                                                Toast.makeText(ChatwithUserActivity.this,"Something went wrong in chat!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                        }else {
                                            dialog.dismiss();
                                            Toast.makeText(ChatwithUserActivity.this,"Something went wrong in deleting messages!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.create();
                builder.show();

            }
        });

        unblockchatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AlertDialog.Builder builder = new AlertDialog.Builder(ChatwithUserActivity.this);
               builder.setIcon(R.drawable.block);
               builder.setTitle("Unblock");
               builder.setMessage("Are you sure to unblock this person.");
               builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       HashMap<String, Object> hashMap = new HashMap<String, Object>();
                       hashMap.put("isblock", false);
                       database.getReference("AllUsers")
                               .child(sender_email.replace(".",","))
                               .child("MyAllUsers")
                               .child(reciever_email.replace(".",","))
                               .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                      if(task.isSuccessful()){
                                          message_adapter.notifyItemInserted(message_modalArrayList.size()-1);
                                      }
                                   }
                               });
                   }
               });
               builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.dismiss();
                   }
               });

               builder.create();
               builder.show();
            }
        });


    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}