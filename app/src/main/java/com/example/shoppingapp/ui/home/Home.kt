package com.example.shoppingapp.ui.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shoppingapp.data.model.local.Recipe

@Composable
fun Home(
    viewModel: HomeViewModel = hiltViewModel(),
    bottomCurrentItem: String,
    bottomItemClicked: (String) -> Unit,
    backPressed: () -> Unit,
    onClickRecipe: (Recipe) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar() { backPressed() } },
        bottomBar = {
            BottomNavigationBar(bottomCurrentItem = bottomCurrentItem) {
                bottomItemClicked(it)
            }
        }
    ) { paddings ->
        HomeContent(
            modifier = Modifier.padding(paddings),
            bottomCurrentItem = bottomCurrentItem,
            viewModel = viewModel,
            onClickRecipe = { onClickRecipe(it) },
        )
    }
}

@Composable
fun HomeContent(
    modifier: Modifier,
    bottomCurrentItem: String,
    viewModel: HomeViewModel,
    onClickRecipe: (Recipe) -> Unit
) {
    Box(modifier = modifier) {
        if (bottomCurrentItem == "Dashboard") {
            Dashboard(viewModel = viewModel) {
                onClickRecipe(it)
            }
        } else {
            AddRecipe(viewModel = viewModel)
        }
    }
}

@Composable
fun Dashboard(viewModel: HomeViewModel, onClickRecipe: (Recipe) -> Unit) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getRecipes()
    }
    DashboardCollector(viewModel = viewModel) {
        onClickRecipe(it)
    }
}

@Composable
fun DashboardCollector(viewModel: HomeViewModel, onClickRecipe: (Recipe) -> Unit) {
    val recipes by viewModel.homeUiState.collectAsState()
    recipes.loading?.let {
        if (it) {
            ShowLoader()
        }
    }
    recipes.data?.let { listOfRecipes ->
        PopulateRecipes(list = listOfRecipes) {
            onClickRecipe(it)
        }
    }

}

@Composable
fun ShowLoader() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(50.dp))
    }
}

@Composable
fun PopulateRecipes(
    modifier: Modifier = Modifier,
    list: List<Recipe>,
    onClickRecipe: (Recipe) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(list) { recipe ->
            PopulateRecipeItem(recipe) {
                onClickRecipe(it)
            }
        }
    }
}

@Composable
fun PopulateRecipeItem(recipe: Recipe, onClickRecipe: (Recipe) -> Unit) {
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(10.dp)
            .clickable { onClickRecipe(recipe) }) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(recipe.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = recipe.title.toString(), fontSize = 10.sp, modifier = Modifier.weight(0.2f))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface",
                modifier = Modifier.weight(1f),
                maxLines = 2
            )
        }
    }
}

@Composable
fun AddRecipe(viewModel: HomeViewModel) {
    var title by remember { mutableStateOf("") }
    var servings by remember { mutableStateOf(1) }

    RecipeCollector(viewModel = viewModel)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column() {
            OutlinedTextField(value = title, onValueChange = {
                title = it
            }, label = { Text(text = "Title") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = servings.toString(), onValueChange = {
                servings = it.toInt()
            }, label = { Text(text = "Servings") })
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.addRecipe(title = title, serving = servings) }) {
                Text(text = "Submit")
            }
        }
    }
}

@Composable
fun RecipeCollector(viewModel: HomeViewModel) {
    val result = viewModel.homeUiState.collectAsState()
    var showSuccessDialog by remember {
        mutableStateOf(false)
    }
    result.value.successfulRecipeAdd?.let {
        showSuccessDialog = it
        viewModel.consumeSuccessfulAddTag()
    }
    showAlertDialog(show = showSuccessDialog) {
        showSuccessDialog = false
        Log.i("mani", "RecipeCollector: $showSuccessDialog")
    }
}

@Composable
fun showAlertDialog(show: Boolean?, dismiss: () -> Unit) {
    if (show != null && show == true) {
        AlertDialog(
            onDismissRequest = { dismiss() },
            title = {
                Text(text = "Successful")
            },
            text = {
                Text("Here is a text ")
            },
            confirmButton = {
                Button(

                    onClick = {
                        dismiss()
                    }) {
                    Text("ok")
                }
            },
            dismissButton = {
                Button(

                    onClick = {
                        dismiss()
                    }) {
                    Text("close")
                }
            })
    }
}

@Composable
fun BottomNavigationBar(bottomCurrentItem: String, bottomItemClicked: (String) -> Unit) {
    BottomNavigation() {
        BottomNavigationItem(
            selected = bottomCurrentItem == "Dashboard",
            onClick = { bottomItemClicked("Dashboard") },
            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = null) },
            label = { Text(text = "Dashboard") })
        BottomNavigationItem(
            selected = bottomCurrentItem == "Recipe",
            onClick = { bottomItemClicked("Recipe") },
            icon = { Icon(imageVector = Icons.Filled.Face, contentDescription = null) },
            label = { Text(text = "Recipe") })
    }
}

@Composable
fun TopBar(backPressed: () -> Unit) {
    TopAppBar(title = {
        Text("My App")
    }, navigationIcon = {
        IconButton(onClick = { backPressed() }) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
        }
    })
}