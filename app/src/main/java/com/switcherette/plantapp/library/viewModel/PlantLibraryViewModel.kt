package com.switcherette.plantapp.library.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.switcherette.plantapp.data.PlantInfo
import com.switcherette.plantapp.data.repositories.PlantInfoRepository
import kotlinx.coroutines.flow.Flow


class PlantLibraryViewModel(
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
