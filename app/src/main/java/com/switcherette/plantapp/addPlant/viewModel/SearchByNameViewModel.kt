package com.switcherette.plantapp.addPlant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.data.PlantInfo
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.data.repositories.PlantInfoRepository
import com.switcherette.plantapp.data.repositories.UserPlantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchByNameViewModel(
    private val plantInfoRepo: PlantInfoRepository
) : ViewModel() {
    var pagingDataFlow: Flow<PagingData<PlantInfo>>

    init {
        pagingDataFlow = searchPlantInfo("")
            .cachedIn(viewModelScope)
    }

    fun searchPlantInfo(query: String): Flow<PagingData<PlantInfo>> {
        val dbQuery = "%${query.replace(" ", "%")}%"
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 200
            ),
            pagingSourceFactory = {
                plantInfoRepo.getPagedPlantByName(dbQuery)
            }
        ).flow
    }

    fun update(query: String){
        pagingDataFlow = searchPlantInfo(query)
            .cachedIn(viewModelScope)
    }
}
