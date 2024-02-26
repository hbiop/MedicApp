package com.example.medicapp

import android.annotation.SuppressLint
import android.graphics.Paint.Align
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.medicapp.models.Analys
import com.example.medicapp.retrofit.medicApi
import com.example.medicapp.ui.theme.DisabledButton
import com.example.medicapp.ui.theme.EnabledButton
import com.example.medicapp.ui.theme.Grey
import com.example.medicapp.ui.theme.MedicAppTheme
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicAppTheme {
                Init()
                Analyzi()
            }
        }
    }
}
var screenWidth: Int = -1
var screenHeight: Int = -1
@Composable
fun Init(){
    screenWidth = LocalConfiguration.current.screenWidthDp
    screenHeight = LocalConfiguration.current.screenHeightDp
}

@Composable
fun eMail() {
    val message = remember {
        mutableStateOf("")
    }
    val enabled = remember {
        mutableStateOf(false)
    }
    Column(verticalArrangement = Arrangement.Center,modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)){
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Image(modifier = Modifier.size(width = (screenWidth/15).dp, height = (screenHeight/15).dp),painter = painterResource(id = R.drawable.emojies), contentDescription = null)
            Text(text = "   Добро пожаловать!", fontWeight = FontWeight.Bold, fontSize = (screenWidth/15).sp)
        }
        Text(text = "\nВойдите чтобы пользоваться функциями", fontSize = (screenWidth/24).sp)
        Text(text = "Приложения", fontSize = (screenWidth/24).sp)
        Spacer(modifier = Modifier.size(width = screenWidth.dp, height = (screenHeight/10).dp))
        Text(text = "Вход по E-mail", fontSize = (screenWidth/24).sp)
        OutlinedTextField(colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.Gray, focusedBorderColor = Color.Gray,backgroundColor = Grey, cursorColor = Color.Transparent),shape = RoundedCornerShape((screenWidth/24).dp),modifier = Modifier.size(width = screenWidth.dp, height = (screenHeight/10).dp),value = message.value,
            onValueChange = {
                    text -> message.value = text.trim()
                    enabled.value = message.value.isNotEmpty()
            })
        Spacer(modifier = Modifier.size(width = screenWidth.dp,(screenHeight/20).dp))
        Button(shape = RoundedCornerShape((screenWidth/24).dp),colors = ButtonDefaults.buttonColors(backgroundColor = EnabledButton, disabledBackgroundColor = DisabledButton),enabled = enabled.value, modifier = Modifier.size(width = (screenWidth/1).dp, height = (screenHeight/10).dp),onClick = { }) {
            Text(text = "Далее", fontSize = (screenWidth/20).sp, color = Color.White)
        }
        Spacer(modifier = Modifier.size(width = screenWidth.dp,(screenHeight/5).dp))
        Text(text = "Или войдите с помощью", fontSize = (screenWidth/24).sp)
        Button(shape = RoundedCornerShape((screenWidth/24).dp),colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),modifier = Modifier.size(width = (screenWidth/1).dp, height = (screenHeight/10).dp),onClick = { }) {
            Text(color = Color.Black,text = "Войти с Яндекс", fontSize = (screenWidth/20).sp)
        }
    }
}

@Composable
fun KodIzEmail() {
    val block1 = remember {
        mutableStateOf("")
    }
    val block2 = remember {
        mutableStateOf("")
    }
    val block3 = remember {
        mutableStateOf("")
    }
    val block4 = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Введите код из E-mail\n", fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.width((screenWidth/1.5).dp), horizontalArrangement = Arrangement.SpaceEvenly){
            OutlinedTextField(colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.Gray, focusedBorderColor = Color.Gray,backgroundColor = Grey, cursorColor = Color.Transparent),shape = RoundedCornerShape((screenWidth/24).dp),modifier = Modifier.size(width = (screenWidth/7).dp, height = (screenWidth/7).dp),value = block1.value,
                onValueChange = {
                        text -> block1.value = text.trim()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
            OutlinedTextField(colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.Gray, focusedBorderColor = Color.Gray,backgroundColor = Grey, cursorColor = Color.Transparent),
                shape = RoundedCornerShape((screenWidth/24).dp),
                modifier = Modifier.size(width = (screenWidth/7).dp,
                    height = (screenWidth/7).dp),value = block2.value,
                onValueChange = {

                        text ->
                    if(text.length < 2) {
                        block2.value = text.trim()
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
            OutlinedTextField(textStyle = TextStyle(fontSize = (screenWidth/24).sp,textAlign = TextAlign.Center),colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.Gray, focusedBorderColor = Color.Gray,backgroundColor = Grey, cursorColor = Color.Transparent),shape = RoundedCornerShape((screenWidth/24).dp),modifier = Modifier.size(width = (screenWidth/7).dp, height = (screenWidth/7).dp),value = block3.value,
                onValueChange = {
                        text -> block3.value = text.trim()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
            OutlinedTextField(colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.Gray, focusedBorderColor = Color.Gray,backgroundColor = Grey, cursorColor = Color.Transparent),shape = RoundedCornerShape((screenWidth/24).dp),modifier = Modifier.size(width = (screenWidth/7).dp, height = (screenWidth/7).dp),value = block4.value,
                onValueChange = {
                        text -> block4.value = text.trim()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
        }
        Text(textAlign = TextAlign.Center,text = "\nОтправить код повторно можно \nбудет через 60 секунд")
    }
}

@Composable
fun SpecialButton() {
    Button(colors = ButtonDefaults.buttonColors(),modifier = Modifier
        .height((screenHeight / 8).dp)
        .width((screenWidth / 5).dp),shape = RoundedCornerShape(100),onClick = {}) {

    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun a(){
    val num1 = remember {
        mutableStateOf(R.drawable.unfilled)
    }
    val num2 = remember {
        mutableStateOf(R.drawable.unfilled)
    }
    val num3 = remember {
        mutableStateOf(R.drawable.unfilled)
    }
    val num4 = remember {
        mutableStateOf(R.drawable.unfilled)
    }
    Column(verticalArrangement = Arrangement.SpaceEvenly,modifier = Modifier
        .fillMaxSize()
        .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text( modifier = Modifier.fillMaxWidth(),text = "Пропустить", fontSize = ((screenWidth/24).sp),textAlign = TextAlign.Right,)
        Text(text = "Создайте пароль", fontSize = ((screenWidth/14).sp), fontWeight = FontWeight.Bold)
        Text(text = "Для защиты ваших персональных данных", fontSize = ((screenWidth/24).sp))
        Row(modifier = Modifier.width((screenWidth/3).dp), horizontalArrangement = Arrangement.SpaceEvenly){
            Image(imageVector = ImageVector.vectorResource(id = num1.value), contentDescription = null)
            Image(imageVector = ImageVector.vectorResource(id = num2.value), contentDescription = null)
            Image(imageVector = ImageVector.vectorResource(id = num3.value), contentDescription = null)
            Image(imageVector = ImageVector.vectorResource(id = num4.value), contentDescription = null)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(modifier = Modifier
                .height((screenHeight / 8).dp)
                .width((screenWidth / 5).dp),shape = RoundedCornerShape(100),onClick = {}) {
                Text(text = "1", fontSize = ((screenWidth/14).sp), color = Color.Black)
            }
            Button(modifier = Modifier
                .height((screenHeight / 8).dp)
                .width((screenWidth / 5).dp),shape = RoundedCornerShape(100),onClick = {}) {
                Text(text = "2", fontSize = ((screenWidth/14).sp), color = Color.Black)
            }
            Button(modifier = Modifier
                .height((screenHeight / 8).dp)
                .width((screenWidth / 5).dp),shape = RoundedCornerShape(100),onClick = {}) {
                Text(text = "3", fontSize = ((screenWidth/14).sp), color = Color.Black)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(modifier = Modifier
                .height((screenHeight / 8).dp)
                .width((screenWidth / 5).dp),shape = RoundedCornerShape(100),onClick = {}) {
                Text(text = "4", fontSize = ((screenWidth/14).sp), color = Color.Black)
            }
            Button(modifier = Modifier
                .height((screenHeight / 8).dp)
                .width((screenWidth / 5).dp),shape = RoundedCornerShape(100),onClick = {}) {
                Text(text = "5", fontSize = ((screenWidth/14).sp), color = Color.Black)
            }
            Button(modifier = Modifier
                .height((screenHeight / 8).dp)
                .width((screenWidth / 5).dp),shape = RoundedCornerShape(100),onClick = {}) {
                Text(text = "6", fontSize = ((screenWidth/14).sp), color = Color.Black)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(modifier = Modifier
                .height((screenHeight / 8).dp)
                .width((screenWidth / 5).dp),shape = RoundedCornerShape(100),onClick = {}) {
                Text(text = "7", fontSize = ((screenWidth/14).sp), color = Color.Black)
            }
            Button(modifier = Modifier
                .height((screenHeight / 8).dp)
                .width((screenWidth / 5).dp),shape = RoundedCornerShape(100),onClick = {}) {
                Text(text = "8", fontSize = ((screenWidth/14).sp), color = Color.Black)
            }
            Button(modifier = Modifier
                .height((screenHeight / 8).dp)
                .width((screenWidth / 5).dp),shape = RoundedCornerShape(100),onClick = {}) {
                Text(text = "9", fontSize = ((screenWidth/14).sp), color = Color.Black)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Spacer(modifier = Modifier
                .height((screenHeight / 8).dp)
                .width((screenWidth / 5).dp))
            Button(modifier = Modifier
                .height((screenHeight / 8).dp)
                .width((screenWidth / 5).dp),shape = RoundedCornerShape(100),onClick = {}) {
                Text(text = "0", fontSize = ((screenWidth/14).sp), color = Color.Black)
            }
            Button(modifier = Modifier
                .height((screenHeight / 8).dp)
                .width((screenWidth / 5).dp),shape = RoundedCornerShape(100),onClick = {}) {
                Image(modifier = Modifier.size(width = (screenWidth/20).dp, height = (screenHeight/20).dp),painter = painterResource(id = R.drawable.delicon), contentDescription = null)
            }
        }
    }
}

val spisokAnalizov = mutableStateListOf<Analys>()
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Analyzi(){
    getData()
    val analizi:List<Akcia> = listOf(
       Akcia(1,"aaaaaaaaaaaaa"),
       Akcia(2, "ssssssssssss"),
       Akcia(3,"aadsfsdfdsgsg"),
       Akcia(4, "zzzzzzzzzzzz")
    )
    val poisk = remember {
        mutableStateOf("")
    }
    Scaffold(bottomBar = { BottomAppBar(modifier = Modifier.fillMaxWidth()) {
        
    }}) {
        Column(modifier = Modifier.fillMaxSize()) {
            TextField(value = poisk.value, onValueChange = {text -> poisk.value = text} )
            Text(text = "Акции и новости")
            LazyRow{
                items(analizi){
                        analiz -> AkciiCard(akcia = analiz)
                }
            }
            Text(text = "Каталог анализов")
            LazyRow{
                items(analizi){
                        analiz -> AkciiCategoria()
                }
            }
            LazyColumn{
                items(spisokAnalizov.size){
                        index -> AkciiAnliz(spisokAnalizov[index])


                }
            }
        }
    }

}
@Composable
fun AkciiCard(akcia: Akcia){
    Card(modifier = Modifier
        .size(width = (screenWidth / 1.5).dp, height = (screenHeight / 4).dp)
        .padding(start = 10.dp, end = 10.dp)) {
        Box(modifier = Modifier.fillMaxSize()){
            Image(modifier = Modifier.fillMaxSize(),contentScale = ContentScale.Crop,painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = null)
            Text(text = "aaaaaaa")
        }
    }
}
@Composable
fun AkciiCategoria(){
    Card(modifier = Modifier
        .size(width = (screenWidth / 3).dp, height = (screenHeight / 16).dp)
        .padding(start = 10.dp, end = 10.dp)) {
        Box(modifier = Modifier.fillMaxSize()){
            Image(modifier = Modifier.fillMaxSize(),contentScale = ContentScale.Crop,painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = null)
            Text(text = "aaaaaaa")
        }
    }
}
@Composable
fun AkciiAnliz(analys: Analys){
    Card(modifier = Modifier
        .size(width = (screenWidth).dp, height = (screenHeight / 4).dp)
        .padding(bottom = 10.dp, top = 10.dp, start = 10.dp, end = 10.dp)) {
        Box(modifier = Modifier.fillMaxSize()){
            Image(modifier = Modifier.fillMaxSize(),contentScale = ContentScale.Crop,painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = null)
            Text(text = analys.name)
        }
    }
}
fun getData(){
    val inteceptor = HttpLoggingInterceptor()
    inteceptor.level = HttpLoggingInterceptor.Level.BODY

    val client = OkHttpClient.Builder().addInterceptor(inteceptor).build()
    val model = Retrofit.Builder().baseUrl("http://185.221.214.178:9991/").client(client)
        .addConverterFactory(GsonConverterFactory.create()).build()
    val weatherApi: medicApi = model.create(medicApi::class.java)
    val call: Call<List<Analys>> = weatherApi.getAnalyses()
    call.enqueue(object : Callback<List<Analys>> {
        override fun onResponse(call: Call<List<Analys>>, response: Response<List<Analys>>) {
            Log.d("a", "Success")
            Log.d("a", response.toString())
            response.body()?.let { spisokAnalizov.addAll(it) }
        }

        override fun onFailure(call: Call<List<Analys>>, t: Throwable) {
            Log.d("a", "Failure")
        }
    })
}