package kun.hee.api_practice_login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kun.hee.api_practice_login.utils.ServerUtil
import org.json.JSONObject

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        signUpBtn.setOnClickListener {
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }

        loginBtn.setOnClickListener {
            val inputId = idEdt.text.toString() //아이디가 존재하지 않는다면 ?
            val inputPw = pwEdt.text.toString() //비밀번호가 틀리다면 ?

//          서버로 로그인 요청 => ServerUtil클래스 기능 활용
             ServerUtil.postRequestLogin(mContext, inputId, inputPw, object : ServerUtil.JsonResponseHandler {
                 override fun onResponse(json: JSONObject) {
//                   실제로 응답을 받은 걸 분석해서 => 대응 하는 코드

//                  임시로 서버 응답 확인하기 위한 코드
                     Log.d("서버응답JSON", json.toString())

                     val code = json.getInt("code")

                     if (code == 200) {
//                         로그인 성공
                         val data = json.getJSONObject("data")
                         val user = data.getJSONObject("user")
                         val name = user.getString("name")

                         val myIntent = Intent(mContext,MainActivity::class.java)
                         myIntent.putExtra("userName", name)
                         startActivity(myIntent)
                     }
                     else {
                         val message = json.getString("message")
                         runOnUiThread {
                             Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show()
                         }

                     }

                 }
             })
        }
    }

    override fun setValues() {

        }

}
