package loc.example.basicscodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import loc.example.basicscodelab.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClick = { shouldShowOnboarding = false })
    } else {
        Greetings(names = List(20) {
            "Name $it"
        }) // listOf("Android", "Compose"))
    }
}

@Composable
fun Greetings(names: List<String>) {
    Surface(
//        modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
    ) {
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = names) { name ->
                Greeting(name)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    var expanded: Boolean by remember<MutableState<Boolean>> { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
            targetValue = if (expanded) 48.dp else 0.dp,
            animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
            )
    )
    Surface(
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                    modifier = Modifier
                            .weight(1f)
                            .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello,")
                Text(
                        name, style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.Bold
                )
                )
            }
            OutlinedButton(onClick = { expanded = !expanded }) {
                Text(if (expanded) "Show less" else "Show more")
            }
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClick: () -> Unit) {
    // TODO: This state should be hoisted
    Surface {
        Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                    modifier = Modifier.padding(vertical = 24.dp),
                    onClick = onContinueClick
            ) {
                Text("Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClick = {})
    }
}

@Preview(showBackground = true, widthDp = 320, uiMode = UI_MODE_NIGHT_YES, name = "DefaultPreviewDark")
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        Greetings(names = listOf("Foo", "Bar"))
    }
}