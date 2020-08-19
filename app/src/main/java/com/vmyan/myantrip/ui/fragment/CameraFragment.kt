package com.vmyan.myantrip.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.SharePost
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.log.logcat
import io.fotoapparat.log.loggers
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.back
import io.fotoapparat.selector.front
import io.fotoapparat.view.CameraView
import kotlinx.android.synthetic.main.fragment_camera.*
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList


class CameraFragment : Fragment() {

    val permissions = arrayOf(Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)

    var fotoapparat: Fotoapparat? = null
    var fotoapparatState : FotoapparatState? = null
    var cameraStatus : CameraState? = null
    //var flashState: FlashState? = null

    private val REQUEST_CODE_READ_STORAGE = 2
    private  var arrayList =ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createFotoapparat(view)

        cameraStatus = CameraState.BACK
        //flashState = FlashState.OFF
        fotoapparatState = FotoapparatState.OFF

        fab_camera.setOnClickListener {
            if (hasNoPermissions()) {

                val permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(requireActivity(), permissions,0)
            }else{
               takePhoto()
            }
        }

        fab_switch_camera.setOnClickListener {
            switchCamera()
        }

        fab_pick_photo.setOnClickListener {
            if (hasNoPermissions()) {

                val permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(requireActivity(), permissions,0)
            }else{
                showChooser()
            }
        }

        fab_cancel.setOnClickListener{
            layout_2.visibility = View.GONE

            camer_layout.visibility = View.VISIBLE
            fab_camera.visibility = View.VISIBLE
        }

    }

    private fun comfire(bitresize: Bitmap) {
        fab_comfirm.setOnClickListener{

            val compress = compressBitmap(bitresize,25)
            val image = MediaStore.Images.Media.insertImage(requireContext().getContentResolver(),compress,"${UUID.randomUUID()}",".jpg")
            Log.e("Photo Uri===>",image)
            toSingleUploadFragment(image)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (resultCode === Activity.RESULT_OK) {
            if (requestCode === REQUEST_CODE_READ_STORAGE) {
                if (resultData != null) {
                    arrayList.clear()
                    if (resultData.getClipData() != null) {
                        val count: Int = resultData.getClipData()!!.getItemCount()
                        var currentItem = 0
                        while (currentItem < count) {
                            var imageUri: Uri = resultData.getClipData()!!.getItemAt(currentItem).getUri()
                            currentItem = currentItem + 1
                            Log.d("Uri Selected", imageUri.toString())
                            try {
                                arrayList.add(imageUri.toString())
                                Log.e("imageUrl=====>",arrayList.toString())

                            } catch (e: Exception) {
                                Log.e("", "File select error", e)
                            }
                        }
                        toMultiUploadFragment(arrayList)
                    } else if (resultData.getData() != null) {
                        val uri: Uri = resultData.getData()!!
                        Log.i("", "Uri = " + uri.toString())
                        try {
                            arrayList.add(uri.toString())
                            Log.e("imageUrl=====>",arrayList.toString())

                            toSingleUploadFragment(uri.toString())

                        } catch (e: Exception) {
                            Log.e("", "File select error", e)
                        }
                    }
                }
            }
        }
    }
    private fun toSingleUploadFragment(data: String){
        val bundle = Bundle()
        bundle.putString("single_Photo",data)

        val fragment = UploadPost()
        fragment.setArguments(bundle)
        requireFragmentManager().beginTransaction()
            .replace(R.id.share_containger, fragment)
            .commit()
    }

    private fun toMultiUploadFragment(data: ArrayList<String>){
        val bundle = Bundle()
        bundle.putStringArrayList("multiple_Photo",data)
        val fragment = UploadPost()
        fragment.setArguments(bundle)
        requireFragmentManager().beginTransaction()
            .replace(R.id.share_containger, fragment)
            .commit()
    }

    private fun showChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_READ_STORAGE);
    }

    fun rotate(`in`: Bitmap, angle: Int): Bitmap {
        val mat = Matrix()
        mat.postRotate(angle.toFloat())
        return Bitmap.createBitmap(`in`, 0, 0, `in`.width, `in`.height, mat, true)
    }

    // Method to compress a bitmap
    private fun compressBitmap(bitmap:Bitmap, quality:Int):Bitmap{
        // Initialize a new ByteArrayStream
        val stream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)

        val byteArray = stream.toByteArray()


        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    // Method to save an bitmap to a file
    private fun compressBitmapToUri(bitmap:Bitmap):Uri{
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, bytes)
        val path = MediaStore.Images.Media.insertImage(requireContext().contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }

    // Method to resize a bitmap programmatically
    private fun resizeBitmap(bitmap:Bitmap, width:Int, height:Int):Bitmap{
        return Bitmap.createScaledBitmap(bitmap, width, height, false)
    }

    private fun createFotoapparat(view: View){
        val cameraView =view.findViewById<CameraView>(R.id.camera_view)

        fotoapparat = context?.let {
            Fotoapparat(
                context = it,
                view = cameraView,
                scaleType = ScaleType.CenterCrop,
                lensPosition = back(),
                logger = loggers(
                    logcat()
                ),
                cameraErrorCallback = { error ->
                    println("Recorder errors: $error")
                }
            )
        }
    }

//    private fun changeFlashState() {
//        fotoapparat?.updateConfiguration(
//            CameraConfiguration(
//                flashMode = if(flashState == FlashState.TORCH) off() else torch()
//            )
//        )
//
//        if(flashState == FlashState.TORCH) flashState = FlashState.OFF
//        else flashState = FlashState.TORCH
//    }

    private fun switchCamera() {
        fotoapparat?.switchTo(
            lensPosition =  if (cameraStatus == CameraState.BACK) front() else back(),
            cameraConfiguration = CameraConfiguration()
        )

        if(cameraStatus == CameraState.BACK) cameraStatus = CameraState.FRONT
        else cameraStatus = CameraState.BACK
    }

    private fun takePhoto() {
        if (hasNoPermissions()) {

            val permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(requireActivity(), permissions,0)
        }else{
            val photoResult = fotoapparat!!.takePicture()

            //val uri = File(sd_main,LocalDateTime.now().toString()+".png")
            // Asynchronously saves photo to file
            //photoResult.saveToFile(uri)
            // Asynchronously converts photo to bitmap and returns the result on the main thread
            photoResult
                .toBitmap()
                .whenAvailable { bitmapPhoto ->
                    var imageView = requireView().findViewById<ImageView>(R.id.imageView_result)
                    camer_layout.visibility = View.GONE
                    fab_camera.visibility = View.GONE
                    layout_2.visibility = View.VISIBLE

                    // rotation
                    val bitmap = rotate(bitmapPhoto!!.bitmap,  -bitmapPhoto.rotationDegrees)

                    // get device dimensions
                    val display  = requireActivity().getWindowManager().getDefaultDisplay()

                    //resize image
                    val bitresize = resizeBitmap(bitmap,display.width,display.height)
//
                    imageView.setImageBitmap(bitresize)
                    comfire(bitresize)
                }
        }
    }



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()

        println("Onstart")

        if (hasNoPermissions()) {
            requestPermission()
            println("Requrie Permission!!!!")
        }else{
            fotoapparat?.start()
            fotoapparatState = FotoapparatState.ON
        }
    }

    private fun hasNoPermissions(): Boolean{
        return ContextCompat.checkSelfPermission(requireActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(requireActivity(),
            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(requireActivity(), permissions,0)
    }

    override fun onStop() {
        super.onStop()
        fotoapparat?.stop()
        FotoapparatState.OFF
    }

    override fun onPause() {
        super.onPause()
        println("OnPause")
    }

    override fun onResume() {
        super.onResume()
        println("OnResume")
        println(fotoapparatState)
        if(!hasNoPermissions() && fotoapparatState == FotoapparatState.OFF){
            val intent = Intent(context, SharePost::class.java)
            startActivity(intent)
        }
    }
}

enum class CameraState{
    FRONT, BACK
}

//enum class FlashState{
//    TORCH, OFF
//}

enum class FotoapparatState{
    ON, OFF
}