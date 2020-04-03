package br.com.covidbr.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.com.covidbr.data.region.Region
import br.com.covidbr.data.region.regionMemory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.lang.Exception

class RegionWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val pdvType = object : TypeToken<List<Region>>() {}.type
        var jsonReader: JsonReader? = null

        return try {
            val inputStream = applicationContext.assets.open("region.json")
            jsonReader = JsonReader(inputStream.reader())
            val regions: MutableList<Region> = Gson().fromJson(jsonReader, pdvType)
            regionMemory.regions.addAll(regions)
            Result.success()
        } catch (ex: Exception) {
            Log.e(this.javaClass.toString(), "Error seeding database", ex)
            Result.failure()
        } finally {
            jsonReader?.close()
        }
    }
}
