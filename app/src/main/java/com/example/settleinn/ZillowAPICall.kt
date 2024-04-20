import com.example.settleinn.HouseDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ZillowApiService {
    @Headers(
        "X-RapidAPI-Key: 9c5c3415ccmshf8d198ed0eab45bp1b1d77jsnaa4c9db208ea",
        "X-RapidAPI-Host: zillow-com1.p.rapidapi.com"
    )
    @GET("propertyExtendedSearch")
    fun searchProperties(
        @QueryMap filters: Map<String, String>
    ): Call<ApiResponse>
}

data class ApiResponse(val props: List<HouseDetail>)
