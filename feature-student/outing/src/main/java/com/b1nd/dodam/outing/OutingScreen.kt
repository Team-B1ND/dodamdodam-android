package com.b1nd.dodam.outing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.icons.Plus
import com.b1nd.dodam.outing.viewmodel.OutingViewModel

@Composable
fun OutingScreen(
    onAddClick: () -> Unit,
    viewModel: OutingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        DodamTopAppBar(
            title = "외출/외박",
            containerColor = MaterialTheme.colorScheme.surface,
            icon = Plus,
            onIconClick = onAddClick
        )
        DodamSegmentedButton(titles = listOf("외출", "외박"), onClick = {})
    }
}

@Preview
@Composable
fun PreviewOutingScreen() {
    OutingScreen(onAddClick = { /*TODO*/ })
}
