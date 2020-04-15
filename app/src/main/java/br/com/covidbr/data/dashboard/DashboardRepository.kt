package br.com.covidbr.data.dashboard

import br.com.covidbr.extension.toCalendar

interface DashboardRepository {

    suspend fun getItens(): MutableList<Dashboard>

}

class DashboardRepositoryImpl(val service: DashboardService) : DashboardRepository {
    override suspend fun getItens(): MutableList<Dashboard> {
        val itens = service.getItens()
        return itens.mapIndexed { index, dashboard ->
            val dashboardDate = dashboard.lastUpdatedAtSource.toCalendar()
            if (index + 1 <= itens.size) {
                val nextDashboard = itens[index + 1]
                val nextDashboardDate = nextDashboard.lastUpdatedAtSource.toCalendar()
                if (dashboardDate == nextDashboardDate) {
                    null
                } else {
                    dashboard
                }
            } else {
                dashboard
            }
        }.filterNotNull()
            .toMutableList()
    }
}