package br.com.covidbr.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.com.covidbr.data.Memory
import br.com.covidbr.data.contry.ContryMemory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader

class ContryWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val pdvType = object : TypeToken<List<ContryMemory>>() {}.type
        var jsonReader: JsonReader? = null

        return try {
            val inputStream = applicationContext.assets.open("contry.json")
            jsonReader = JsonReader(inputStream.reader())
            val contryMemories: MutableList<ContryMemory> = Gson().fromJson(jsonReader, pdvType)
            Memory.CONTRY_MEMORIES.addAll(contryMemories)
            Result.success()
        } catch (ex: Exception) {
            Log.e(this.javaClass.toString(), "Error worker contry", ex)
            Result.failure()
        } finally {
            jsonReader?.close()
        }
    }
}
