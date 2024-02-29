package com.example.companydata

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.companydata.ui.theme.CompanyDataTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompanyDataTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android",this)
                }
            }
        }
    }
}

data class comp(val name: String,val cgpa : String)

@Composable
fun Greeting(name: String, context: Context, modifier: Modifier = Modifier) {
    var show by remember {
        mutableStateOf(false)
    }
    var nameInput by remember {
        mutableStateOf("")
    }
    var cgpaInput by remember {
        mutableStateOf("")
    }

    val compList = remember {
        mutableStateListOf<comp>()
    }

    // Read and parse JSON data
    val file = context.assets.open("data.json")
    val jsonString = file.bufferedReader().use { it.readText() }
    val gson = Gson()
    val compListFromJson: List<comp> = gson.fromJson(jsonString, object : TypeToken<List<comp>>() {}.type)
    compList.addAll(compListFromJson)

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
        TextField(value = nameInput, onValueChange = { nameInput = it }, label = { Text(text = "Name") })
        TextField(value = cgpaInput, onValueChange = { cgpaInput = it }, label = { Text(text = "CGPA") })
        Button(onClick = {
            compList.add(comp(nameInput, cgpaInput))
            nameInput = ""
            cgpaInput = ""
        }, modifier = Modifier) {
            Text(text = "Send Data")
        }
        Button(onClick = { show = !show }, modifier = Modifier) {
            Text(text = "Show Data")
        }
        if (show) {
            LazyColumn {
                items(compList) { comp ->
                    ShowData(x = comp)
                }
            }
        }
    }
}

@Composable
fun ShowData(x : comp, modifier: Modifier= Modifier){
    Column(modifier = modifier) {
        Text(text = x.name, modifier=Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Text(text = x.cgpa.toString(),modifier=Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CompanyDataTheme {
        Greeting("Android", LocalContext.current)
    }
}