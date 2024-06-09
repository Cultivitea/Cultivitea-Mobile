package com.cultivitea.frontend.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cultivitea.frontend.R
import com.cultivitea.frontend.ui.composables.ClickableAuthText
import com.cultivitea.frontend.ui.theme.PrimaryBrown
import com.cultivitea.frontend.ui.theme.PrimaryGreen

@Composable
fun LoginScreen(onNavigateToRegister: () -> Unit) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }


    Column(
        modifier = Modifier.padding(vertical = 64.dp, horizontal = 22.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.logo),
            modifier = Modifier.size(200.dp).padding(bottom = 30.dp)

        )

        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.Start){

            Column(modifier = Modifier.padding(vertical = 16.dp)){
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.padding(4.dp))

                Text(
                    text = stringResource(id = R.string.login_description),
                    style = MaterialTheme.typography.titleSmall,
                )
            }

            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(weight = 1f, fill = false)) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                    text =  stringResource(id = R.string.username),
                    style = MaterialTheme.typography.labelSmall,
                )

                TextField(value = username,
                    onValueChange = { username = it },
                    modifier= Modifier
                        .fillMaxWidth()
                        .border(1.dp, PrimaryBrown, RoundedCornerShape(40.dp)),
                    shape = RoundedCornerShape(40.dp),
                    colors= TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,),
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                    text =  stringResource(id = R.string.password),
                    style = MaterialTheme.typography.labelSmall,
                )

                TextField(value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = {passwordVisible = !passwordVisible}){
                            Icon(imageVector  = image, description)
                        }
                    },
                    modifier= Modifier
                        .fillMaxWidth()
                        .border(1.dp, PrimaryBrown, RoundedCornerShape(40.dp)),
                    shape = RoundedCornerShape(40.dp),
                    colors= TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,),
                )
            }


        }

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 25.dp) ) {
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen), shape = RoundedCornerShape(4.dp)) {
                Text(text = stringResource(id = R.string.signin), modifier = Modifier.padding(horizontal = 40.dp, vertical = 4.dp), style = MaterialTheme.typography.labelMedium.copy(fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Normal))
            }

            Spacer(modifier = Modifier.padding(8.dp))

            ClickableAuthText(onNavigateToRegister, R.string.sign_up, R.string.dont_have_account)
        }
    }
}
