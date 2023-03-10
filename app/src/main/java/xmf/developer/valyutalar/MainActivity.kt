package xmf.developer.valyutalar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xmf.developer.valyutalar.databinding.ActivityMainBinding
import xmf.developer.valyutalar.models.MyMoney
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var rvAdapter: RvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getCurrency()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                binding.rv.adapter = rvAdapter
            }
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        val job = coroutineScope.launch {
            delay(1000)
        }

    }
    private fun getCurrency(): Observable<List<MyMoney>> {

        return Observable.create{
            val url = URL("http://cbu.uz/uzc/arkhiv-kursov-valyut/json/")
            val list = ArrayList<MyMoney>()
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream = connection.inputStream
            val bufferReader = inputStream.bufferedReader()
            val gsonString = bufferReader.readLine()
            val gson = Gson()
            rvAdapter = RvAdapter(list)
            val type = object : TypeToken<ArrayList<MyMoney>>() {}.type
            list.addAll(gson.fromJson<ArrayList<MyMoney>>(gsonString, type))

            it.onNext(list)
        }
    }
}