package com.drozdova.catapi

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.drozdova.catapi.data.Cat
import retrofit2.HttpException

class CatsPageSource(
    private val catService: CatApiImpl
) : PagingSource<Int, Cat> (){
    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        // получить номер страницы (page)
        val page: Int = params.key ?: 0
        // получить количество элементов на странице (pageSize)
        val pageSize: Int = params.loadSize
        val response = catService.catService.getCatsList(page, pageSize)

        if(response.isSuccessful) {
            val listOfCats = checkNotNull(response.body()).map { cat ->
                Cat(cat.id, cat.url, cat.width, cat.height)
            }
            val nextKey = if (listOfCats.size < pageSize) null else page + 1
            val prevKey = if (page == 0) null else page - 1

            return LoadResult.Page(listOfCats, prevKey, nextKey)
        } else {
            return LoadResult.Error(HttpException(response))
        }
    }
}