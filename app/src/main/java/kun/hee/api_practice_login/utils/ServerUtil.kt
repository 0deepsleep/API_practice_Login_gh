package kun.hee.api_practice_login.utils

import android.content.Context
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

//    서버통신의 응답내용(json변수)을 액티비티로 넘겨주기 위한 인터페이스
    interface JsonResponseHandler{ //제이슨 응답을 처리해준다
        fun onResponse(json:JSONObject)
    }

    companion object {
//        어느 서버(호스트)로 가야하는지 적어두는 변수.
//        도메인 (api.naver.com 등) or IP주소 (192.168.0.243:5000) 등
//        메인 주소를 저장해두는 변수.
        val BASE_URL = "http://192.168.10.224:5000/"

//        필요한 변수를 넣는 요령
//        화면에서 > 서버로 전달해야하는 데이터
        fun postRequestLogin(context: Context, id:String, pw:String, handler: JsonResponseHandler?) {
            val client = OkHttpClient()

//    어떤기능을 수행하러 가는지 주소 완성.
//    http://192.168.0.243:5000/auth
            val urlStr = "${BASE_URL}/auth"

//    서버에 들고갈 데이터를 첨부. =>POST메쏘드의 예제
            val formBody = FormBody.Builder() //캐리어 열자 -> add로 짐넣자
                .add("login_id", id) //짐넣기 login_id는 저 주소에서 받는 이름.
                .add("password", pw) //저 링크에서 받겠다는 이름이 password
                .build() //캐리어닫자
    
//          화면 이동으로 따지면 Intent 객체를 만드는 것
            val request = Request.Builder() //비행기티켓   request는 okhttp3받기
                .url(urlStr) //어디로갈래?
                .post(formBody) //가는방법 (짐첨부?)
//                .header() //API가 header를 요구하면 추가해야 함.
                .build() //표출력
    
//          startActivity처럼 실제로 요청을 날리는 코드.
            client.newCall(request).enqueue(object : Callback { //object 알트 앤터
                override fun onFailure(call: Call, e: IOException) {
//                    서버연결 자체를 실패.

//                    연결에 실패한 경위를 로그로 출력
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
//                    서버의 응답은 보통 JSON양식으로 가공되어 옴.
//                    받을 때는 일단 String타입으로 받게 됨. => JSON으로 변환해서 액티비티에 전달.

                    val body = response.body!!.string()
                    val json = JSONObject(body)

                    handler?.onResponse(json)

                }
            })

        }

    }
}