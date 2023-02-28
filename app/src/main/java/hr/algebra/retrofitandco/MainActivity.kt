package hr.algebra.retrofitandco

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import hr.algebra.retrofitandco.databinding.ActivityMainBinding
import hr.algebra.retrofitandco.model.Joke
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding : ActivityMainBinding
    private val API_BASE_URL             = "https://api.chucknorris.io"
    private var disposable : Disposable? = null
    private var newJoke    : Joke?       = null

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView( binding.root )

        binding.bJoke.setOnClickListener {
            fetchNewJoke( )
        }



    }

    private fun fetchNewJoke( ) {
        val interceptor = ChuckerInterceptor
            .Builder(this)
            .build()

        val httpClient = OkHttpClient
            .Builder( )
                .addInterceptor( interceptor )
                .build()


        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client( httpClient )
            .addConverterFactory( MoshiConverterFactory.create( ) )
            .addCallAdapterFactory( RxJava2CallAdapterFactory.create( ) )
            .build( )

        val service = retrofit.create( JokeService::class.java )

        service
            .getNewJoke( )
            .subscribeOn( Schedulers.io( ) )
            .observeOn( AndroidSchedulers.mainThread( ) )
            .subscribe( object : Observer<Joke> {
                            override fun onSubscribe( d: Disposable ) {
                                disposable = d
                            }

                            override fun onNext( joke : Joke ) {
                                newJoke = joke
                                Log.i( TAG, "Next joke is here!" )
                            }

                            override fun onError( e: Throwable ) {
                                e.printStackTrace( )
                            }

                            override fun onComplete( ) {
                                binding.tvJoke.text = newJoke?.value
                                Timber.i( "Joke shown to the user!" )
                            }
                        } )
            }
}