import com.example.settleinn.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ZillowApiService {
    @Headers(
        "X-RapidAPI-Key:  Your Api key",
        "X-RapidAPI-Host: zillow-com1.p.rapidapi.com"
    )
    @GET("propertyExtendedSearch")
    fun searchProperties(
        @QueryMap filters: Map<String, String>
    ): Call<ApiResponse>
}
// also add the zillow api key in values/keys.xml