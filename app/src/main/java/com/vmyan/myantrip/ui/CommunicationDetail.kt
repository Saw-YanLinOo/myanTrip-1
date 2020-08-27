package com.vmyan.myantrip.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.load.model.FileLoader
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.api.Api
import com.vmyan.myantrip.model.Word
import com.vmyan.myantrip.ui.viewmodel.CommunicationDetailViewModel
import com.vmyan.myantrip.utils.MyBounceInterpolator
import com.vmyan.myantrip.utils.unzip
import kotlinx.android.synthetic.main.activity_communication_detail.*
import okhttp3.ResponseBody
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*

class CommunicationDetail : AppCompatActivity() {

    private  val viewModel: CommunicationDetailViewModel by inject()
    private lateinit var mPlayer: MediaPlayer
    private lateinit var word : Word
    private var language = Hawk.get<String>("language","Myanmar")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_communication_detail)

        var audioFile = File(getExternalFilesDir(null).toString() + File.separator + "audio")
        if (!audioFile.exists()){
            loading_layout.visibility = View.VISIBLE
            setUpObserve()
        }

        init()

    }

    private fun init(){
        val gson: Gson = GsonBuilder().create()
        val word = gson.fromJson<Word>(intent.getStringExtra("word"), Word::class.java)
        this.word = word
        mPlayer = MediaPlayer()

        if (language.trim() == "Myanmar"){
            tv_from.text = word.myn
            tv_to.text = word.eng
        }else{
            tv_from.text = word.eng
            tv_to.text = word.myn
        }
    }

    @SuppressLint("StaticFieldLeak")
    private fun setUpObserve() {
        if (!File(getExternalFilesDir(null).toString() + File.separator + "audio.zip").exists()){
            println("File not exist!")
            var fileLocation = MutableLiveData<String>()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://communicationforaudio.000webhostapp.com/")
                .build()
            val api = retrofit.create(Api::class.java)
            val calls = api.downloadFileWithFixedUrl()
            try {
                calls.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        val execute = object : AsyncTask<Void, Void, String>() {
                            override fun onPreExecute() {
                                super.onPreExecute()
                                btn_speak.visibility = View.GONE
                                loading_layout.visibility = View.VISIBLE

                            }

                            override fun doInBackground(vararg voids: Void): String {
                                val writtenToDisk = writeResponseBodyToDisk(
                                    applicationContext,
                                    response.body(),
                                    "audio.zip"
                                )
                                Log.d("download", "file download was a success? $writtenToDisk")
                                Log.d("downloadFile", "success")

                                val zipFile = File(writtenToDisk)
                                zipFile.unzip()
                                Log.d("downloadFile", "extra success!!!")
                                return writtenToDisk
                            }

                            override fun onPostExecute(result: String?) {
                                super.onPostExecute(result)
                                loading_layout.visibility = View.GONE
                                btn_speak.visibility = View.VISIBLE
                            }
                        }.execute()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        println("${t.printStackTrace()}")
                    }

                })
            }catch (e: Exception){
                println("${e.printStackTrace()}")
            }
        }
    }

    fun onClick(view: View){
        animateButton()
        var speed = slider_speed.value
        var pitch = slider_pitch.value

        if(tv_to.text.toString() == word.eng){
            playSound(word.eng_file!!,speed,pitch)
        }else{
            playSound(word.myn_file!!,speed,pitch)
        }
    }

    fun animateButton() {
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
//        val animationDuration: Double = 0.02 * 1000
//        myAnim.duration = animationDuration.toLong()

        // Use custom animation interpolator to achieve the bounce effect
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator
        // Animate the button
        val button = findViewById<ImageButton>(R.id.btn_speak)
        button.startAnimation(myAnim)
    }

    fun playSound(fileName: String,speed : Float,pitch : Float) {

        println("Speed => ${(speed * 0.01)} Pitch ==> ${pitch * 0.01}")
        try {
            var params = PlaybackParams()
            params.speed = (speed * 0.01).toFloat()
            params.pitch = (pitch * 0.01).toFloat()

            if (mPlayer != null) {
                mPlayer.stop()
                mPlayer.reset()
            }

            val uri: Uri = Uri.parse(getExternalFilesDir(null).toString()+"/audio/audio/$fileName")
            mPlayer = MediaPlayer()
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mPlayer.setDataSource(this, uri)
            mPlayer.playbackParams = params
            mPlayer.prepare()
            mPlayer.start()

        } catch (e: Exception) {
            println(e.toString())
        }
    }

    fun finishActivity(view: View) {
        this.finish()
    }

    private fun writeResponseBodyToDisk(context: Context, body: ResponseBody?, fileName: String): String {
        try {
            var futureStudioIconFile = File(
                context.getExternalFilesDir(null).toString() + File.separator + fileName)
            Log.e("DownLoadFile===>", futureStudioIconFile.path)
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                val fileSize = body?.contentLength()
                var fileSizeDownloaded: Long = 0

                inputStream = body?.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)

                while (true) {
                    val read = inputStream!!.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                    Log.d(
                        "writeResponseBodyToDisk",
                        "file download: $fileSizeDownloaded of $fileSize"
                    )
                }

                outputStream.flush()

                return futureStudioIconFile.path
            } catch (e: IOException) {
                return e.message.toString()
            } finally {
                if (inputStream != null) {
                    inputStream.close()
                }

                if (outputStream != null) {
                    outputStream.close()
                }
            }
        } catch (e: IOException) {
            return e.message.toString()
        }
    }

    fun changeLanguage(view: View) {
        var test = tv_from.text.toString()
        tv_from.text = tv_to.text
        tv_to.text = test
    }
}