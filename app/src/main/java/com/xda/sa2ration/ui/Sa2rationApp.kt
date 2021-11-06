package com.xda.sa2ration.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xda.sa2ration.R
import com.xda.sa2ration.utils.getEnableDci
import com.xda.sa2ration.utils.getSaturationValue
import java.util.*

@Composable
fun Sa2rationApp(mainViewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current

    var saturationProcess by remember { mutableStateOf(getSaturationValue().toSaturationProcess()) }
    var enableDci by remember { mutableStateOf(getEnableDci()) }
    val applyOnBoot by mainViewModel.applyOnBoot.collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.app_name))
            })
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.size(50.dp))

                Image(
                    painter = painterResource(id = R.drawable.test_img),
                    contentDescription = "Test image",
                    modifier = Modifier.clickable {
                        Toast.makeText(context, R.string.photo_by_desc, Toast.LENGTH_SHORT).show()
                    }
                )

                Spacer(modifier = Modifier.size(50.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Slider(
                        value = saturationProcess,
                        modifier = Modifier.weight(7f),
                        onValueChange = {
                            saturationProcess = it
                            mainViewModel.updateSaturationValue(value = saturationProcess.toSaturationValue())
                        },
                        onValueChangeFinished = {
                            mainViewModel.updateSaturationValue(
                                value = saturationProcess.toSaturationValue(),
                                isApply = true
                            )
                        })
                    Text(
                        text = saturationProcess.toSaturationValue().toString(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 30.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.enable_dci),
                        modifier = Modifier.weight(7f)
                    )
                    Switch(checked = enableDci,
                        modifier = Modifier.weight(1f),
                        onCheckedChange = {
                            enableDci = it
                            mainViewModel.updateEnableDci(enableDci)
                        })
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.apply_on_boot),
                        modifier = Modifier.weight(7f)
                    )
                    Switch(checked = applyOnBoot,
                        modifier = Modifier.weight(1f),
                        onCheckedChange = {
                            mainViewModel.updateApplyOnBoot(it)
                        })
                }
            }
        }
    )
}

private fun Float.toSaturationProcess() = this / 2
private fun Float.toSaturationValue() = String.format(Locale.US, "%.2f", this * 2).toFloat()