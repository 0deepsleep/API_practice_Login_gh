package kun.hee.api_practice_login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kun.hee.api_practice_login.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun setupEvents() {

        loginBtn.setOnClickListener {
            val inputId = idEdt.text.toString() //아이디가 존재하지 않는다면 ?
            val inputPw = pwEdt.text.toString() //비밀번호가 틀리다면 ?

//          서버로 로그인 요청 => ServerUtil클래스 기능 활용
             ServerUtil.postRequestLogin(mContext, inputId, inputPw, object : ServerUtil.JsonResponseHandler{
                 override fun onResponse(json: JSONObject) {
//                   실제로 응답을 받은 걸 분석해서 => 대응 하는 코드

//                  임시로 서버 응답 확인하기 위한 코드
                     Log.d("서버응답JSON", json.toString())
                 }

             })
        }

    }

    override fun setValues() {

        }

}
