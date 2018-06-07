package com.myself.wwk.mychat.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.myself.wwk.mychat.R
import com.myself.wwk.mychat.models.FriendlyMessage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    var userId: String? = null
    var mFirebaseDatebaseRef: DatabaseReference? = null
    var mFirebaseUser: FirebaseUser? = null
    var mLinearLayoutManager: LinearLayoutManager? =null
    var mFirebaseAdapter: FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mFirebaseUser = FirebaseAuth.getInstance().currentUser
        mFirebaseDatebaseRef = FirebaseDatabase.getInstance().reference

                userId = intent.extras.getString("userId")
        mLinearLayoutManager = LinearLayoutManager(this)
        mLinearLayoutManager!!.stackFromEnd = true

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mFirebaseAdapter = object : FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>(
                FriendlyMessage::class.java,
                R.layout.item_message,
                MessageViewHolder::class.java,
                mFirebaseDatebaseRef!!.child("messages")
        ){
            override fun populateViewHolder(viewHolder: MessageViewHolder?, friendlyMessage: FriendlyMessage?, position: Int) {
                if (friendlyMessage!!.text != null){
                    viewHolder!!.bindView(friendlyMessage)

                    var currentUserId = mFirebaseUser!!.uid
                    var isMe: Boolean = friendlyMessage!!.id!!.equals(currentUserId)
                    if (isMe) {
                        // 在右侧
                        viewHolder.profileImageViewRight!!.visibility = View.VISIBLE
                        viewHolder.profileImageView!!.visibility = View.GONE
                        viewHolder.messageTextView!!.gravity = (Gravity.CENTER_VERTICAL or Gravity.RIGHT)
                        viewHolder.messagerTextView!!.gravity = (Gravity.CENTER_VERTICAL or Gravity.RIGHT)

                        // Get ImageUrl
                        mFirebaseDatebaseRef!!.child("Users")
                                .child(currentUserId)
                                .addValueEventListener(object : ValueEventListener{

                                    override fun onDataChange(data: DataSnapshot?) {
                                        var imageUrl = data!!.child("thumb_image").value.toString()
                                        var userName = data!!.child("user_name").value
                                        viewHolder.messagerTextView!!.text = userName.toString()
                                        Picasso.get()
                                                .load(imageUrl)
                                                .placeholder(R.drawable.profile_image)
                                                .into(viewHolder.profileImageViewRight)
                                    }

                                    override fun onCancelled(error: DatabaseError?) {
                                        // 忽略
                                    }

                                })

                    } else {
                        // 在左侧
                        viewHolder.profileImageViewRight!!.visibility = View.GONE
                        viewHolder.profileImageView!!.visibility = View.VISIBLE
                        viewHolder.messageTextView!!.gravity = (Gravity.CENTER_VERTICAL or Gravity.LEFT)
                        viewHolder.messagerTextView!!.gravity = (Gravity.CENTER_VERTICAL or Gravity.LEFT)

                        // Get ImageUrl
                        mFirebaseDatebaseRef!!.child("Users")
                                .child(userId)
                                .addValueEventListener(object : ValueEventListener{

                                    override fun onDataChange(data: DataSnapshot?) {
                                        var imageUrl = data!!.child("thumb_image").value.toString()
                                        var userName = data!!.child("user_name").value
                                        viewHolder.messagerTextView!!.text = userName.toString()
                                        Picasso.get()
                                                .load(imageUrl)
                                                .placeholder(R.drawable.profile_image)
                                                .into(viewHolder.profileImageView)
                                    }

                                    override fun onCancelled(error: DatabaseError?) {
                                        // 忽略
                                    }

                                })

                    }

                }
            }
        }

        // 设置RecyclerView
        message_recyclerView.layoutManager = mLinearLayoutManager
        message_recyclerView.adapter = mFirebaseAdapter


        send_message_button.setOnClickListener {
            if (!intent.extras.get("name").toString().equals("")) {
                var currentUsername = intent.extras.get("name")
                var mCurrentUserId = mFirebaseUser!!.uid


                var friendlyMessage = FriendlyMessage(mCurrentUserId,
                        messages_edit.text.toString().trim(),
                        currentUsername.toString().trim())

                mFirebaseDatebaseRef!!.child("messages")
                        .push().setValue(friendlyMessage)

                messages_edit.setText("")


            }
        }
    }

    class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var messageTextView: TextView? = null
        var messagerTextView: TextView? = null
        var profileImageView: CircleImageView? = null
        var profileImageViewRight: CircleImageView? = null

        fun bindView(friendlyMessage: FriendlyMessage){

            messageTextView = itemView.findViewById(R.id.messages_text_view)
            messagerTextView = itemView.findViewById(R.id.messager_name_text_view)
            profileImageView = itemView.findViewById(R.id.messager_image_view)
            profileImageViewRight = itemView.findViewById(R.id.messager_image_view_right)

            messagerTextView!!.text = friendlyMessage.name
            messageTextView!!.text = friendlyMessage.text

        }
    }
}
