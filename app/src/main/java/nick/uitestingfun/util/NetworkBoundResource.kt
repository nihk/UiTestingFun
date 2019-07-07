package nick.uitestingfun.util

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val _liveData = MediatorLiveData<Resource<ResultType>>()
    val liveData: LiveData<Resource<ResultType>> get() = _liveData

    init {
        _liveData.value = Resource.Loading()
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        _liveData.addSource(dbSource) { data ->
            _liveData.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                _liveData.addSource(dbSource) { newData ->
                    setValue(Resource.Success(newData))
                }
            }
        }
    }

    private fun setValue(newValue: Resource<ResultType>) {
        if (_liveData.value != newValue) {
            _liveData.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        createCall()
            .flatMapCompletable { items -> saveCallResult(items) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    _liveData.addSource(dbSource) { newData ->
                        setValue(Resource.Loading(newData))
                    }
                }

                override fun onComplete() {
                    _liveData.removeSource(dbSource)
                    _liveData.addSource(loadFromDb()) {
                        _liveData.value = Resource.Success(it)
                    }
                }

                override fun onError(e: Throwable) {
                    onFetchFailed()
                    _liveData.removeSource(dbSource)
                    _liveData.addSource(dbSource) {
                        _liveData.value = Resource.Error(e, it)
                    }
                }
            })
    }

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected open fun shouldFetch(data: ResultType?): Boolean = true

    @MainThread
    protected abstract fun createCall(): Single<RequestType>

    protected open fun onFetchFailed() {}

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType): Completable
}