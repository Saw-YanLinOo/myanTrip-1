package com.vmyan.myantrip.data

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import co.metalab.asyncawait.async
import com.google.firebase.storage.FirebaseStorage
import com.vmyan.myantrip.api.Api
import com.vmyan.myantrip.utils.Resource
import com.vmyan.myantrip.utils.unzip
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import java.lang.Exception

class CommunicationDetailRepoImpl : CommunicationDetailRepo {
    override suspend fun getFileR(context: Context): Resource<MutableLiveData<String>> {

        var fileLocation = MutableLiveData<String>()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://communicationforaudio.000webhostapp.com/")
            .build()
        val api = retrofit.create(Api::class.java)
        val calls = api.downloadFileWithFixedUrl().await()
        try {
                val execute = object : AsyncTask<Void, Void, String>() {
                    override fun doInBackground(vararg voids: Void): String {
                        val writtenToDisk = writeResponseBodyToDisk(context,calls, "audio.zip")
                        Log.d("download", "file download was a success? $writtenToDisk")
                        Log.d("downloadFile", "sucess")

                        val zipFile =File(writtenToDisk)
                        zipFile.unzip()
                        Log.d("downloadFile","extra success!!!")
                        return writtenToDisk
                    }

                }.execute()
        }catch (e : Exception){
            println("${e.printStackTrace()}")
        }

        return Resource.Success(fileLocation)
    }

    private fun writeResponseBodyToDisk(context: Context,body: ResponseBody?, fileName: String): String {
        try {
            var futureStudioIconFile = File(context.getExternalFilesDir(null).toString() + File.separator + fileName)
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
                    //Nó được sử dụng để trả về một ký tự trong mẫu ASCII. Nó trả về -1 vào cuối tập tin.
                    if (read == -1) {
                        break
                    }
                    outputStream!!.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                    Log.d("writeResponseBodyToDisk", "file download: $fileSizeDownloaded of $fileSize")
                }

                outputStream!!.flush()

                return futureStudioIconFile.path
            } catch (e: IOException) {
                return e.message.toString()
            } finally {
                if (inputStream != null) {
                    inputStream!!.close()
                }

                if (outputStream != null) {
                    outputStream!!.close()
                }
            }
        } catch (e: IOException) {
            return e.message.toString()
        }
    }
}