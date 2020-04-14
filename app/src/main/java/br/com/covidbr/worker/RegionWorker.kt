package br.com.covidbr.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.com.covidbr.data.Memory
import br.com.covidbr.data.region.RegionMemory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader

class RegionWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val pdvType = object : TypeToken<List<RegionMemory>>() {}.type
        var jsonReader: JsonReader? = null

        return try {
            val inputStream = applicationContext.assets.open("region.json")
            jsonReader = JsonReader(inputStream.reader())
            val regionMemories: MutableList<RegionMemory> = Gson().fromJson(jsonReader, pdvType)
            Memory.REGION_MEMORIES.addAll(regionMemories)
            Result.success()
        } catch (ex: Exception) {
            Log.e(this.javaClass.toString(), "Error worker state", ex)
            Result.failure()
        } finally {
            jsonReader?.close()
        }
    }
}
