package com.myself.wwk.mychat.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.myself.wwk.mychat.R
import com.theartofdev.edmodo.cropper.CropImage
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.ByteArrayOutputStream
import java.io.File

class SettingsActivity : AppCompatActivity() {

    var mDatabase: DatabaseReference? = null
    var mCurrentUser: FirebaseUser? = null
    var mStorageRef: StorageReference? = null
    var GALLERY_ID: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        mCurrentUser = FirebaseAuth.getInstance().currentUser
        mStorageRef = FirebaseStorage.getInstance().reference
        var userId = mCurrentUser!!.uid
        mDatabase = FirebaseDatabase.getInstance().reference
                .child("Users")
                .child(userId)

        mDatabase!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                var userName = dataSnapshot!!.child("user_name").value
                var image = dataSnapshot!!.child("image").value
                var status = dataSnapshot!!.child("status").value
                var thumbImage = dataSnapshot!!.child("thumb_image").value

                // 获取用户名和状态
                settings_username.text = userName.toString()
                settings_status_tag.text = status.toString()


            }

            override fun onCancelled(dataBaseErrorSnapshot: DatabaseError?) {

            }
        })

        // 传值到StatusActivity去修改status
        settings_change_status.setOnClickListener {
            var intent = Intent(this, StatusActivity::class.java)
            intent.putExtra("status", settings_status_tag.text.toString().trim())
            startActivity(intent)
        }

        // 修改头像图片
        settings_image_change.setOnClickListener {
            var galleryIntent = Intent()
            galleryIntent.type = "image/*"
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(galleryIntent, "SELECT_IMAGE"), GALLERY_ID)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GALLERY_ID && resultCode == Activity.RESULT_OK) {
            var image: Uri = data!!.data
            CropImage.activity(image)
                    .setAspectRatio(1, 1)
                    .start(this)
        }

        if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                var userId = mCurrentUser!!.uid
                var thumbFile = File(resultUri.path)
                var thumbBitmap = Compressor(this)
                        .setMaxWidth(200)
                        .setMaxHeight(200)
                        .setQuality(65)
                        .compressToBitmap(thumbFile)

                // 上传图片到Firebase
                var byteArray = ByteArrayOutputStream()
                thumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray)
                var thumbByteArray: ByteArray
                thumbByteArray = byteArray.toByteArray()

                var filePath = mStorageRef!!.child("chat_profile_images")
                        .child(userId + ".jpg")
                // 为压缩过的thumb images创建新的目录
                var thumbFilePath = mStorageRef!!.child("chat_profile_images")
                        .child("thumbs")
                        .child(userId + ".jpg")

                filePath.putFile(resultUri).addOnCompleteListener {
                    task: Task<UploadTask.TaskSnapshot> ->
                    if (task.isSuccessful) {
                        // 获取图片Url
                        var downloadUrl = task.result.downloadUrl.toString()
                        // 上传Task
                        var uploadTask: UploadTask = thumbFilePath.putBytes(thumbByteArray)
                        uploadTask.addOnCompleteListener {
                            task: Task<UploadTask.TaskSnapshot> ->
                            var thumbUrl = task.result.downloadUrl.toString()
                            if (task.isSuccessful) {
                                var updateObj = HashMap<String, Any>()
                                updateObj.put("image", downloadUrl)
                                updateObj.put("thumb_image", thumbUrl)
                                // 存储profile image
                                mDatabase!!.updateChildren(updateObj).addOnCompleteListener {
                                    task: Task<Void> ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(this, "图片存储成功!", Toast.LENGTH_LONG)
                                                .show()
                                    } else {
                                        Toast.makeText(this, "图片存储失败..", Toast.LENGTH_LONG)
                                                .show()
                                    }
                                }


                            } else {
                                Toast.makeText(this, "图片上传失败..", Toast.LENGTH_LONG)
                                        .show()
                            }
                        }
                    }
                }

            }
        }
        //super.onActivityResult(requestCode, resultCode, data)
    }
}
