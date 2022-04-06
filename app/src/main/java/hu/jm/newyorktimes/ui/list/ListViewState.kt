package hu.jm.newyorktimes.ui.list

import hu.jm.newyorktimes.model.NYTimesResult

sealed class ListViewState
object InProgress : ListViewState()
data class ResponseSuccess(val data: NYTimesResult) : ListViewState()
data class ResponseError(val exceptionMsg: String) : ListViewState()